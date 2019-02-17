package com.carlomatulessy.venuefinder.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.carlomatulessy.venuefinder.repository.VenueRepository
import com.carlomatulessy.venuefinder.webservice.FoursquareAPIResponse

/**
 * Created by Carlo Matulessy on 16/02/2019.
 * Copyright © 2019 Carlo Matulessy. All rights reserved.
 *
 * Description: This viewmodel handles the data binding with the view and repository
 */
class VenueDetailViewModel : ViewModel() {

    fun getVenueDetails(id: String): MutableLiveData<FoursquareAPIResponse> =
        VenueRepository().getVenueDetails(id) as MutableLiveData<FoursquareAPIResponse>
}
