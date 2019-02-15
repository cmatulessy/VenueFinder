package com.carlomatulessy.venuefinder.viewmodel

import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.carlomatulessy.venuefinder.model.Venue
import com.carlomatulessy.venuefinder.repository.VenueRepository

class VenueFinderViewModel : ViewModel() {

    private lateinit var venueResultList: List<Venue>

    private val venueResults: MutableLiveData<List<Venue>> by lazy {
        MutableLiveData<List<Venue>>()
    }

    private val venueNoResults: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    private val venueProgress: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    fun restoreDataIfNecessary() {
        if(::venueResultList.isInitialized)
            updateVenueResults(venueResultList, true)
    }

    fun setResultsObserver(
        fragment: Fragment,
        venueResultsObserver: Observer<List<Venue>>,
        venueNoResultsObserver: Observer<Boolean>,
        venueProgressObserver: Observer<Boolean>
    ) {
        venueResults.observe(fragment, venueResultsObserver)
        venueNoResults.observe(fragment, venueNoResultsObserver)
        venueProgress.observe(fragment, venueProgressObserver)
    }

    fun getResultsFromValue(fragment: Fragment,
                            value: String, radius: Int = 1000, limit: Int = 10) {
        venueProgress.value = true
        VenueRepository().getVenues(value, radius, limit)
            .observe(fragment, Observer { result ->
                venueResultList = result
                updateVenueResults(result, result.isNotEmpty())
            })
    }

    fun resetResultsList() {
        updateVenueResults(emptyList(), false)
    }

    private fun updateVenueResults(results: List<Venue>, hasResults: Boolean) {
        venueResults.value = results
        venueNoResults.value = hasResults
        venueProgress.value = false
    }
}
