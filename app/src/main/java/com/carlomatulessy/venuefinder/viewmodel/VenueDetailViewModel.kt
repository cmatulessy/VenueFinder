package com.carlomatulessy.venuefinder.viewmodel

import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.carlomatulessy.venuefinder.database.VenueDetailResult
import com.carlomatulessy.venuefinder.repository.VenueRepository

/**
 * Created by Carlo Matulessy on 16/02/2019.
 * Copyright © 2019 Carlo Matulessy. All rights reserved.
 *
 * Description: This viewmodel handles the data binding with the view and repository
 */
class VenueDetailViewModel : ViewModel() {

    private val venueDetails: MutableLiveData<VenueDetailResult> by lazy {
        MutableLiveData<VenueDetailResult>()
    }

    fun setVenueDetailsObserver(
        fragment: Fragment,
        venueDetailResultObserver: Observer<VenueDetailResult>) {
        venueDetails.observe(fragment, venueDetailResultObserver)
    }

    fun getVenueDetails(fragment: Fragment, id: String) {
        fragment.context?.let{safeContext ->
            VenueRepository().getVenueDetails(safeContext, id)
                .observe(fragment, Observer{
                    venueDetails.value = it
                })
        }
    }
}
