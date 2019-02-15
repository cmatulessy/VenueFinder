package com.carlomatulessy.venuefinder.view.venuedetail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.carlomatulessy.venuefinder.R
import com.carlomatulessy.venuefinder.model.Contact
import com.carlomatulessy.venuefinder.model.Venue
import com.carlomatulessy.venuefinder.util.Extra
import com.carlomatulessy.venuefinder.viewmodel.VenueDetailViewModel
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
            venueName = it.getString(Extra.VENUE_NAME, getString(R.string.venue_detail_title_unknown))
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.venue_detail_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getVenueDetails(venueId).observe(this, Observer { result ->
            result?.let { safeVenue ->
                venueDetailTitle.text =
                    if (safeVenue.name.isNotEmpty()) safeVenue.name else venueName

                venueDetailDescription.text =
                    if (safeVenue.description.isNullOrEmpty()) getString(R.string.venue_detail_description_unknown)
                    else safeVenue.description

                venueDetailAddress.text = safeVenue.location.formattedAddress.joinToString(",\n")

                venueDetailRatingBar.rating = (safeVenue.rating / 5).toFloat()

                setupContactView(safeVenue.contact)
            }
        })
    }

    private fun setupContactView(contact: Contact) {
        venueDetailTwitterBtn.visibility = getVisibility(contact.twitter)
        venueDetailInstagramBtn.visibility = getVisibility(contact.instagram)
        venueDetailFacebookBtn.visibility = getVisibility(contact.facebook)
        venueDetailCallBtn.visibility = getVisibility(contact.phone)
    }

    private fun getVisibility(contactType: String?): Int {
        return if (contactType.isNullOrEmpty()) View.VISIBLE else View.GONE
    }
}
