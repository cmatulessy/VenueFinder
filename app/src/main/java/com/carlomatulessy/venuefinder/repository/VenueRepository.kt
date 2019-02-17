package com.carlomatulessy.venuefinder.repository

import android.content.Context
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.carlomatulessy.venuefinder.database.VenueDetailResult
import com.carlomatulessy.venuefinder.database.VenueResult
import com.carlomatulessy.venuefinder.webservice.model.Venue
import com.carlomatulessy.venuefinder.util.Extra.VENUE_FINDER_KEY
import com.carlomatulessy.venuefinder.webservice.FoursquareAPIResponse
import com.carlomatulessy.venuefinder.webservice.FoursquareService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

/**
 * Created by Carlo Matulessy on 12/02/2019.
 * Copyright © 2019 Carlo Matulessy. All rights reserved.
 *
 * Description: This class is responsible for managing the data. It determines if the data comes from an HTTP request
 * or from the database itself. If there is no internet connection, it will look for cached data. If it can't find it,
 * it will return an empty list
 *
 */
class VenueRepository {

    fun getVenues(context: Context, value: String, radius: Int, limit: Int): MutableLiveData<List<VenueResult>> {
        val data = MutableLiveData<List<VenueResult>>()
        FoursquareService.instance.getVenueResults(value, radius, limit).enqueue(
            object : Callback<FoursquareAPIResponse> {

                override fun onResponse(
                    call: Call<FoursquareAPIResponse>,
                    response: Response<FoursquareAPIResponse>
                ) {
                    Log.d(VENUE_FINDER_KEY, "getVenues(): RetroFit response code: ${response.code()}")

                    if (response.code() == 200) {
                        response.body()?.let { apiResponse ->
                            VenueResultInsertTask(context, value, apiResponse,
                                object : VenueResultInsertTask.InsertListener {

                                    override fun onInserted(results: List<VenueResult>) {
                                        data.value = results
                                        Log.d(VENUE_FINDER_KEY, "VenueRepository : getVenues(): Obtain data from url")
                                    }

                                    override fun onInsertionError() {
                                        super.onInsertionError()
                                        Log.e(
                                            VENUE_FINDER_KEY,
                                            "VenueRepository | getVenues(): Something went wrong with insert data for getVenues()"
                                        )
                                    }
                                }).execute()
                        }
                    } else {
                        onFailure(call, IOException())
                    }
                }

                override fun onFailure(call: Call<FoursquareAPIResponse>, t: Throwable) {
                    VenueResultCachedDataTask(context, value,
                        object : VenueResultCachedDataTask.ObtainCacheListener {

                            override fun onObtainedResults(results: List<VenueResult>) {
                                Log.d(VENUE_FINDER_KEY, "VenueRepository | onFailure(): Obtain data from cache")
                                data.value = results
                            }

                            override fun onObtainedError() {
                                super.onObtainedError()
                                Log.e(
                                    VENUE_FINDER_KEY,
                                    "VenueRepository | onFailureVenues: Something went wrong with VenueResultCachedDataTask"
                                )
                            }
                        }).execute()

                    Log.e(
                        VENUE_FINDER_KEY,
                        "VenueRepository: Something went wrong with FoursquareAPIResponse: ${t.message}"
                    )
                }
            }
        )

        return data
    }

    fun getVenueDetails(context: Context, id: String): MutableLiveData<VenueDetailResult> {
        val data = MutableLiveData<VenueDetailResult>()

        FoursquareService.instance.getVenueDetails(id).enqueue(object : Callback<FoursquareAPIResponse> {
            override fun onResponse(call: Call<FoursquareAPIResponse>, response: Response<FoursquareAPIResponse>) {
                Log.d(VENUE_FINDER_KEY, "getVenueDetails(): RetroFit response code: ${response.code()}")

                if (response.code() == 200) {
                    response.body()?.response?.let {
                        VenueDetailResultInsertTask(context, it.venue,
                            object : VenueDetailResultInsertTask.InsertListener {
                                override fun onInserted(results: VenueDetailResult) {
                                    data.value = results
                                    Log.d(VENUE_FINDER_KEY, "VenueRepository | getVenueDetails(): Obtain data from url")
                                }

                                override fun onInsertionError() {
                                    super.onInsertionError()
                                    Log.e(
                                        VENUE_FINDER_KEY,
                                        "VenueRepository | getVenueDetails(): Something went wrong with insert data for getVenueDetails()"
                                    )
                                }
                            }).execute()
                    }
                } else {
                    onFailure(call, IOException())
                }
            }

            override fun onFailure(call: Call<FoursquareAPIResponse>, t: Throwable) {
                VenueDetailResultCachedDataTask(context, id,
                    object : VenueDetailResultCachedDataTask.ObtainCacheListener {
                        override fun onObtainedResults(results: VenueDetailResult) {
                            data.value = results
                        }

                        override fun onObtainedError() {
                            super.onObtainedError()
                            Log.e(
                                VENUE_FINDER_KEY,
                                "VenueRepository | onFailureVenueDetails: Something went wrong with VenueDetailResultCachedDataTask"
                            )
                        }
                    }
                )
            }
        })

        return data
    }
}