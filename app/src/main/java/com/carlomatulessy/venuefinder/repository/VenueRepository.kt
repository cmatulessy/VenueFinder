package com.carlomatulessy.venuefinder.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.carlomatulessy.venuefinder.database.VenueResult
import com.carlomatulessy.venuefinder.webservice.model.Venue
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

    fun getVenues(context: Context, value: String, radius: Int, limit: Int): LiveData<List<VenueResult>> {
        var data = MutableLiveData<List<VenueResult>>()

        FoursquareService.instance.getVenueResults(value, radius, limit).enqueue(
            object : Callback<FoursquareAPIResponse> {
                override fun onResponse(call: Call<FoursquareAPIResponse>, response: Response<FoursquareAPIResponse>) {
                    Log.d(VENUE_FINDER_KEY, "RetroFit response code: ${response.code()}")

                    if (response.code() == 200) {
                        response.body()?.let { apiResponse ->
                            VenueResultInsertTask(context, value, apiResponse,
                                object : VenueResultInsertTask.InsertListener {

                                    override fun onInserted(results: LiveData<List<VenueResult>>?) {
                                        results?.let {
                                            data.value = it.value
                                        }
                                    }

                                    override fun onInsertionError() {
                                        super.onInsertionError()
                                        Log.e(
                                            VENUE_FINDER_KEY,
                                            "VenueRepository: Something went wrong with VenueResultInsertTask"
                                        )
                                    }
                                }).execute()
                        }
                    } else {
                        data = getCachedDataForVenueResults(context, value)
                    }
                }

                override fun onFailure(call: Call<FoursquareAPIResponse>, t: Throwable) {
                    data = getCachedDataForVenueResults(context, value)
                    Log.e(
                        VENUE_FINDER_KEY,
                        "VenueRepository: Something went wrong with FoursquareAPIResponse: ${t.message}"
                    )
                }
            }
        )

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
                Log.e(VENUE_FINDER_KEY, "Venue details onFailure: " + t.message)
                data.value = null
            }
        })

        return data
    }

    private fun getCachedDataForVenueResults(context: Context, value: String): MutableLiveData<List<VenueResult>> {
        var data = MutableLiveData<List<VenueResult>>()

        VenueResultCachedDataTask(context, value,
            object : VenueResultCachedDataTask.ObtainCacheListener {

                override fun onObtainedResults(results: LiveData<List<VenueResult>>?) {
                    results?.let {
                        data.value = it.value
                    }
                }

                override fun onObtainedError() {
                    super.onObtainedError()
                    Log.e(
                        VENUE_FINDER_KEY,
                        "VenueRepository: Something went wrong with VenueResultCachedDataTask"
                    )
                }
            }).execute()

        return data
    }
}