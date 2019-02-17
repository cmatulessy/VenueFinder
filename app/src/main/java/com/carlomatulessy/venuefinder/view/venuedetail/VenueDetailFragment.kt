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
import com.carlomatulessy.venuefinder.database.VenueDetailResult
import com.carlomatulessy.venuefinder.database.VenueResult
import com.carlomatulessy.venuefinder.util.Extra
import com.carlomatulessy.venuefinder.viewmodel.VenueDetailViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.venue_detail_fragment.*

/**
 * Created by Carlo Matulessy on 17/02/2019.
 * Copyright © 2019 Carlo Matulessy. All rights reserved.
 *
 * Description: This class is responsible for the view of a venue detail
 */
class VenueDetailFragment : Fragment() {

    private lateinit var venueId: String
    private lateinit var venueName: String

    companion object {
        fun newInstance(venueResult: VenueResult): VenueDetailFragment {
            val frag = VenueDetailFragment()
            val bundle = Bundle()
            bundle.putString(Extra.VENUE_ID_KEY, venueResult.id)
            bundle.putString(Extra.VENUE_NAME, venueResult.name)
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

        viewModel.setVenueDetailsObserver(this,
            Observer { venueDetailResult ->
                venueDetailTitle.text =
                    if (venueDetailResult.name.isNotEmpty()) venueDetailResult.name else venueName

                venueDetailDescription.text = venueDetailResult.description
                venueDetailAddress.text = venueDetailResult.formattedAddress
                venueDetailResult.rating?.let { venueDetailRatingBar.rating = it }

                Picasso.get().load(
                    getString(
                        R.string.venue_detail_image_url,
                        venueDetailResult.photoPrefix,
                        venueDetailResult.photoWidth,
                        venueDetailResult.photoHeight,
                        venueDetailResult.photoSuffix
                    )
                ).into(venueDetailBestPhoto)

                setupContactView(venueDetailResult)
            })

        viewModel.getVenueDetails(this, venueId)
    }

    private fun setupContactView(venueDetailResult: VenueDetailResult) {
        venueDetailTwitterBtn.apply {
            visibility = getVisibility(venueDetailResult.contactTwitter)
            setOnClickListener {
                goToUrl(getString(R.string.venue_detail_twitter_prefix, venueDetailResult.contactTwitter))
            }
        }

        venueDetailInstagramBtn.apply {
            visibility = getVisibility(venueDetailResult.contactInstagram)
            setOnClickListener {
                goToUrl(getString(R.string.venue_detail_instagram_prefix, venueDetailResult.contactInstagram))
            }
        }

        venueDetailFacebookBtn.apply {
            visibility = getVisibility(venueDetailResult.contactFacebook)
            setOnClickListener {
                goToUrl(getString(R.string.venue_detail_facebook_prefix, venueDetailResult.contactFacebook))
            }
        }

        venueDetailCallBtn.apply {
            visibility = getVisibility(venueDetailResult.contactPhone)
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
                            Uri.parse(getString(R.string.venue_detail_call_prefix, venueDetailResult.contactPhone))
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
