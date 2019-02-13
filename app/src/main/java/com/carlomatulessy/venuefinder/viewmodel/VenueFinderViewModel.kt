package com.carlomatulessy.venuefinder.viewmodel

import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.carlomatulessy.venuefinder.model.Venue
import com.carlomatulessy.venuefinder.repository.VenueRepository

class VenueFinderViewModel : ViewModel() {

    private val venueResults: MutableLiveData<List<Venue>> by lazy {
        MutableLiveData<List<Venue>>()
    }

    private val venueNoResults: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    fun setResultsObserver(
        fragment: Fragment,
        venueResultsObserver: Observer<List<Venue>>,
        venueNoResultsObserver: Observer<Boolean>
    ) {
        venueResults.observe(fragment, venueResultsObserver)
        venueNoResults.observe(fragment, venueNoResultsObserver)
    }

    fun getResultsFromValue(fragment: Fragment, value: String, radius: Int = 1000, limit: Int = 10) {
        VenueRepository().getVenues(value, radius, limit)
            .observe(fragment, Observer { result ->
                updateVenueResults(result, result.isNotEmpty())
            })
    }

    fun resetResultsList() {
        updateVenueResults(emptyList(), false)
    }

    private fun updateVenueResults(results: List<Venue>, noResultsValue: Boolean) {
        venueResults.value = results
        venueNoResults.value = noResultsValue
    }
}
