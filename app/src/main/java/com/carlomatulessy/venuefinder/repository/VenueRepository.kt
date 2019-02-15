package com.carlomatulessy.venuefinder.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.carlomatulessy.venuefinder.model.Venue
import com.carlomatulessy.venuefinder.util.Extra.VENUE_FINDER_KEY
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

        FoursquareService.instance.getVenueResults(value, radius, limit)
            .enqueue(object : Callback<FoursquareAPIResponse> {

                override fun onResponse(call: Call<FoursquareAPIResponse>, response: Response<FoursquareAPIResponse>) {
                    response.body()?.response?.venues?.let {
                        data.value = it
                    }
                }

                override fun onFailure(call: Call<FoursquareAPIResponse>, t: Throwable) {
                    // TODO show cached result
                    data.value = emptyList()
                    Log.e(VENUE_FINDER_KEY, "Venues onFailure: "+t.message)
                }
            })

        return data
    }

    fun getVenueDetails(id: String): LiveData<FoursquareAPIResponse> {
        val data = MutableLiveData<FoursquareAPIResponse>()

        FoursquareService.instance.getVenueDetails(id).enqueue(object : Callback<FoursquareAPIResponse> {

            override fun onResponse(call: Call<FoursquareAPIResponse>, response: Response<FoursquareAPIResponse>) {
                // TODO show cached result
                data.value = response.body()
            }

            override fun onFailure(call: Call<FoursquareAPIResponse>, t: Throwable) {
                Log.e(VENUE_FINDER_KEY, "Venue details onFailure: "+t.message)
                data.value = null
            }
        })

        return data
    }
}