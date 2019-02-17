package com.carlomatulessy.venuefinder.view.venuefinder

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.carlomatulessy.venuefinder.R
import com.carlomatulessy.venuefinder.database.VenueResult
import com.carlomatulessy.venuefinder.util.Analytics.ANALYTICS_VENUE_SEARCH_COMMAND
import com.carlomatulessy.venuefinder.util.Analytics.ANALYTICS_VENUE_SEARCH_HEADER
import com.carlomatulessy.venuefinder.util.Analytics.ANALYTICS_VENUE_SEARCH_SCREEN
import com.carlomatulessy.venuefinder.util.Analytics.ANALYTICS_VENUE_SEARCH_SELECTED_VENUE
import com.carlomatulessy.venuefinder.util.Analytics.ANALYTICS_VENUE_SEARCH_SELECTED_VENUE_HEADER
import com.carlomatulessy.venuefinder.view.venuedetail.VenueDetailFragment
import com.carlomatulessy.venuefinder.viewmodel.VenueFinderViewModel
import com.microsoft.appcenter.analytics.Analytics
import kotlinx.android.synthetic.main.venue_finder_fragment.*

/**
 * Created by Carlo Matulessy on 17/02/2019.
 * Copyright © 2019 Carlo Matulessy. All rights reserved.
 *
 * Description: This class is responsible for displaying the search results of user.
 */
class VenueFinderFragment : Fragment() {

    companion object {
        fun newInstance() = VenueFinderFragment()
    }

    private lateinit var viewModel: VenueFinderViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProviders.of(this).get(VenueFinderViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.venue_finder_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Analytics.trackEvent(ANALYTICS_VENUE_SEARCH_SCREEN)

        viewModel.setResultsObserver(this,
            Observer { data ->
                setVenueResults(data)
            },
            Observer { setVisibility(it) },
            Observer { isInProgress ->
                venueProgressBar.visibility = if (isInProgress) View.VISIBLE else View.GONE
            })

        venueSearchField.apply {
            setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    Analytics.trackEvent(ANALYTICS_VENUE_SEARCH_COMMAND,
                        hashMapOf(ANALYTICS_VENUE_SEARCH_HEADER to text.toString()))

                    requestResultsFromInput(text.toString())
                    closeKeyboard()
                    true
                } else
                    false
            }
        }

        viewModel.restoreDataIfNecessary()
    }

    private fun requestResultsFromInput(value: String) {
        viewModel.getResultsFromValue(this, value.toLowerCase())
    }

    private fun setVenueResults(data: List<VenueResult>) {
        activity?.let { safeActivity ->
            venueResultList.adapter = VenueFinderAdapter(
                safeActivity, data,
                object : VenueFinderAdapter.VenueSelectionListener {
                    override fun onVenueSelected(venueResult: VenueResult) {
                        Analytics.trackEvent(ANALYTICS_VENUE_SEARCH_SELECTED_VENUE,
                            hashMapOf(ANALYTICS_VENUE_SEARCH_SELECTED_VENUE_HEADER to venueResult.name))

                        safeActivity.supportFragmentManager.beginTransaction().apply {
                            replace(R.id.fragmentContainer, VenueDetailFragment.newInstance(venueResult))
                            addToBackStack(null)
                            commit()
                        }
                    }
                })
        }
    }

    private fun closeKeyboard() {
        activity?.let {
            venueSearchField.clearFocus()
            (it.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                .hideSoftInputFromWindow(venueSearchField.windowToken, 0)
        }
    }

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
