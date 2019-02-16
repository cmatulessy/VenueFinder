package com.carlomatulessy.venuefinder.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.carlomatulessy.venuefinder.repository.VenueRepository
import com.carlomatulessy.venuefinder.webservice.FoursquareAPIResponse

class VenueDetailViewModel : ViewModel() {

    fun getVenueDetails(id: String): MutableLiveData<FoursquareAPIResponse> =
        VenueRepository().getVenueDetails(id) as MutableLiveData<FoursquareAPIResponse>
}
