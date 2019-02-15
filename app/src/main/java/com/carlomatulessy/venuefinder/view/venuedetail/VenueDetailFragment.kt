package com.carlomatulessy.venuefinder.view.venuedetail

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.carlomatulessy.venuefinder.R
import com.carlomatulessy.venuefinder.model.Contact
import com.carlomatulessy.venuefinder.model.Venue
import com.carlomatulessy.venuefinder.util.Extra
import com.carlomatulessy.venuefinder.viewmodel.VenueDetailViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.venue_detail_fragment.*


class VenueDetailFragment : Fragment() {

    private lateinit var venueId: String
    private lateinit var venueName: String

    companion object {
        fun newInstance(venue: Venue): VenueDetailFragment {
            val frag = VenueDetailFragment()
            val bundle = Bundle()
            bundle.putString(Extra.VENUE_ID_KEY, venue.id)
            bundle.putString(Extra.VENUE_NAME, venue.name)
            frag.arguments = bundle
            return frag
        }
    }

    private lateinit var viewModel: VenueDetailViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProviders.of(this).get(VenueDetailViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            venueId = it.getString(Extra.VENUE_ID_KEY, "0")
            venueName = it.getString(
                Extra.VENUE_NAME,
                getString(com.carlomatulessy.venuefinder.R.string.venue_detail_title_unknown)
            )
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(com.carlomatulessy.venuefinder.R.layout.venue_detail_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getVenueDetails(venueId).observe(this, Observer { result ->
            result?.let { safeVenue ->
                venueDetailTitle.text =
                    if (safeVenue.name.isNotEmpty()) safeVenue.name else venueName

                venueDetailDescription.text =
                    if (safeVenue.description.isNullOrEmpty()) getString(com.carlomatulessy.venuefinder.R.string.venue_detail_description_unknown)
                    else safeVenue.description

                venueDetailAddress.text = safeVenue.location.formattedAddress.joinToString(",\n")

                venueDetailRatingBar.rating = (safeVenue.rating / 5).toFloat()

                safeVenue.bestPhoto?.let {
                    Picasso
                        .get()
                        .load(getString(R.string.venue_detail_image_url, it.prefix, it.width, it.height, it.suffix))
                        .into(venueDetailBestPhoto)
                }

                setupContactView(safeVenue.contact)
            }
        })
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            Extra.REQUEST_CALL_CODE -> {

            }
        }
    }

    private fun setupContactView(contact: Contact) {
        venueDetailTwitterBtn.apply {
            visibility = getVisibility(contact.twitter)
            setOnClickListener {
                goToUrl(
                    getString(
                        com.carlomatulessy.venuefinder.R.string.venue_detail_twitter_prefix,
                        contact.twitter
                    )
                )
            }
        }

        venueDetailInstagramBtn.apply {
            visibility = getVisibility(contact.instagram)
            setOnClickListener {
                goToUrl(
                    getString(
                        com.carlomatulessy.venuefinder.R.string.venue_detail_instagram_prefix,
                        contact.instagram
                    )
                )
            }
        }

        venueDetailFacebookBtn.apply {
            visibility = getVisibility(contact.facebook)
            setOnClickListener {
                goToUrl(
                    getString(
                        com.carlomatulessy.venuefinder.R.string.venue_detail_facebook_prefix,
                        contact.facebook
                    )
                )
            }
        }

        venueDetailCallBtn.apply {
            visibility = getVisibility(contact.phone)
            setOnClickListener {
                val permissionCheck = ContextCompat.checkSelfPermission(it.context, Manifest.permission.CALL_PHONE)

                if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                    activity?.let { safeActivity ->
                        ActivityCompat.requestPermissions(
                            safeActivity, arrayOf(Manifest.permission.CALL_PHONE),
                            Extra.REQUEST_CALL_CODE
                        )
                    }

                } else {
                    startActivity(
                        Intent(Intent.ACTION_CALL).setData(
                            Uri.parse(
                                getString(
                                    com.carlomatulessy.venuefinder.R.string.venue_detail_call_prefix,
                                    contact.phone
                                )
                            )
                        )
                    )
                }
            }
        }
    }

    private fun getVisibility(contactType: String?): Int {
        return if (contactType.isNullOrEmpty()) View.GONE else View.VISIBLE
    }

    private fun goToUrl(url: String) {
        val uriUrl = Uri.parse(url)
        val launchBrowser = Intent(Intent.ACTION_VIEW, uriUrl)
        startActivity(launchBrowser)
    }
}
