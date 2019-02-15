package com.carlomatulessy.venuefinder.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.carlomatulessy.venuefinder.model.Venue
import com.carlomatulessy.venuefinder.repository.VenueRepository

class VenueDetailViewModel : ViewModel() {

    fun getVenueDetails(id: String): MutableLiveData<Venue> =
        VenueRepository().getVenueDetails(id) as MutableLiveData<Venue>
}
