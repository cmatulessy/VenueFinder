package com.carlomatulessy.venuefinder.view.venuefinder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.carlomatulessy.venuefinder.R
import com.carlomatulessy.venuefinder.viewmodel.VenueFinderViewModel
import kotlinx.android.synthetic.main.venue_finder_fragment.*

class VenueFinderFragment : Fragment() {

    companion object {
        fun newInstance() = VenueFinderFragment()
    }

    private lateinit var viewModel: VenueFinderViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.venue_finder_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(VenueFinderViewModel::class.java)

        viewModel.setResultsObserver(this,
            Observer {
                venueResultList.adapter = VenueFinderAdapter(it)
            },
            Observer {
                setVisibility(it)
            })

        venueSearchButton.setOnClickListener {
            viewModel.getResultsFromValue(this, venueSearchField.text.toString())
        }
    }

    // TODO binding
    private fun setVisibility(value: Boolean) {
        if (value) {
            venueResultList.visibility = View.VISIBLE
            venueEmptyView.visibility = View.GONE
        } else {
            venueResultList.visibility = View.GONE
            venueEmptyView.visibility = View.VISIBLE
        }
    }

}
