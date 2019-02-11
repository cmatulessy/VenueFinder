package com.carlomatulessy.venuefinder.viewmodel

import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel;
import com.carlomatulessy.venuefinder.model.Venue

class VenueFinderViewModel : ViewModel() {

    private val venueResults: MutableLiveData<ArrayList<Venue>> by lazy {
        MutableLiveData<ArrayList<Venue>>()
    }

    private val venueNoResults: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    fun setResultsObserver(
        fragment: Fragment,
        venueResultsObserver: Observer<ArrayList<Venue>>,
        venueNoResultsObserver: Observer<Boolean>
    ) {
        venueResults.observe(fragment, venueResultsObserver)
        venueNoResults.observe(fragment, venueNoResultsObserver)
    }

    fun getResultsFromValue(value: String) {
        // TODO call to repo for data
        updateVenueResults(getDummyList(), value.isNotEmpty())
    }

    private fun updateVenueResults(results: ArrayList<Venue>, noResultsValue: Boolean) {
        venueResults.value = results
        venueNoResults.value = noResultsValue
    }

    private fun getDummyList(): ArrayList<Venue> = arrayListOf(
        Venue("Straat 1"),
        Venue("Straat 2"),
        Venue("Straat 3"),
        Venue("Straat 4"),
        Venue("Straat 5"),
        Venue("Straat 6"),
        Venue("Straat 7"),
        Venue("Straat 8"),
        Venue("Straat 9"),
        Venue("Straat 10")
    )
}
