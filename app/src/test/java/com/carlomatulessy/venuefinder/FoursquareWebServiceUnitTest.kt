package com.carlomatulessy.venuefinder

import com.carlomatulessy.venuefinder.webservice.FoursquareAPICalls
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Created by Carlo Matulessy on 11/02/2019.
 * Copyright © 2019 Carlo Matulessy. All rights reserved.
 */
class FoursquareWebServiceUnitTest {

    @Test
    fun testWebservice() {
        // Arrange
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.foursquare.com")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        // Act
        val webservice = retrofit.create(FoursquareAPICalls::class.java)
        val callResponse = webservice.getVenueResults("Gorinchem")
        val response = callResponse.execute()
        val result = response.body()?.response?.venues

        // Assert
        checkNotNull(result)
    }
}