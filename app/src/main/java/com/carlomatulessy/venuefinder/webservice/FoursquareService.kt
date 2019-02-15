package com.carlomatulessy.venuefinder.webservice

import com.carlomatulessy.venuefinder.BuildConfig
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

/**
 * Created by Carlo Matulessy on 11/02/2019.
 * Copyright © 2019 Carlo Matulessy. All rights reserved.
 */
interface FoursquareService {

    companion object {
        val instance: FoursquareService by lazy {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.foursquare.com")
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
            retrofit.create(FoursquareService::class.java)
        }
    }

    // TODO hide secrets + fix date
    @GET("/v2/venues/search")
    fun getVenueResults(
        @Query("near") near: String,
        @Query("radius") radius: Int,
        @Query("limit") limit: Int,
        @Query("v") version: Int = 20190211,
        @Query("client_id") clientId: String = BuildConfig.FS_Client_ID,
        @Query("client_secret") clientSecret: String = BuildConfig.FS_Client_Secret
    ): Call<FoursquareAPIResponse>

    @GET("/v2/venues/{venueId}")
    fun getVenueDetails(
        @Path("venueId") id: String,
        @Query("v") version: Int = 20190211,
        @Query("client_id") clientId: String = BuildConfig.FS_Client_ID,
        @Query("client_secret") clientSecret: String = BuildConfig.FS_Client_Secret
    ): Call<FoursquareAPIResponse>
}