package com.carlomatulessy.venuefinder.webservice

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

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

    // TODO hide secrets
    @GET("/v2/venues/search?client_id=TY23JDLBXI5V4PS0BJE1GLHWRDYMJQ302TEQIEY2Y3M4C2KL&client_secret=WXVK1VECTKHC52NML15CMH1ZCVMWDY2Z3B2VB5RUUGKCMUDE&v=20190211")
    fun getVenueResults(@Query("near") near: String,
                        @Query("radius") radius: Int,
                        @Query("limit") limit: Int): Call<FoursquareAPIResponse>

}