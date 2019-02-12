package com.carlomatulessy.venuefinder.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.carlomatulessy.venuefinder.model.Venue
import com.carlomatulessy.venuefinder.webservice.FoursquareAPIResponse
import com.carlomatulessy.venuefinder.webservice.FoursquareService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Carlo Matulessy on 12/02/2019.
 * Copyright © 2019 Carlo Matulessy. All rights reserved.
 */
class VenueRepository {

    fun getVenues(value: String, radius: Int, limit: Int): LiveData<List<Venue>> {
        val data = MutableLiveData<List<Venue>>()

        FoursquareService.instance.getVenueResults(value, radius, limit).enqueue(object: Callback<FoursquareAPIResponse>{

            override fun onResponse(call: Call<FoursquareAPIResponse>, response: Response<FoursquareAPIResponse>) {
                response.body()?.response?.venues?.let {
                    data.value = it
                }
            }

            override fun onFailure(call: Call<FoursquareAPIResponse>, t: Throwable) {
                // TODO show cached result
                data.value = emptyList()
            }
        })

        return data
    }
}