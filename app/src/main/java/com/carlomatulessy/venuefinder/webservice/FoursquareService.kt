package com.carlomatulessy.venuefinder.webservice

import com.carlomatulessy.venuefinder.BuildConfig
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Carlo Matulessy on 11/02/2019.
 * Copyright © 2019 Carlo Matulessy. All rights reserved.
 *
 * Description: This interface represents the Foursquare service. If you want to make an HTTP request, you would likely
 * update this class to support your request. For now we make two requests:
 * 1) getVenueResults() -> get venue results for a specific search command
 * 2) getVenueDetails() -> get detailed information of a venue by a given id
 */
interface FoursquareService {

    companion object {
        val instance: FoursquareService by lazy {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.foursquare.com/v2/venues/")
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
            retrofit.create(FoursquareService::class.java)
        }
    }

    @GET("search")
    fun getVenueResults(
        @Query("near") near: String,
        @Query("radius") radius: Int,
        @Query("limit") limit: Int,
        @Query("v") version: Int = 20190211,
        @Query("client_id") clientId: String = BuildConfig.FS_Client_ID,
        @Query("client_secret") clientSecret: String = BuildConfig.FS_Client_Secret
    ): Call<FoursquareAPIResponse>

    @GET("{venueId}")
    fun getVenueDetails(
        @Path("venueId") id: String,
        @Query("v") version: Int = 20190211,
        @Query("client_id") clientId: String = BuildConfig.FS_Client_ID,
        @Query("client_secret") clientSecret: String = BuildConfig.FS_Client_Secret
    ): Call<FoursquareAPIResponse>
}