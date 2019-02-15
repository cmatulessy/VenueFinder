package com.carlomatulessy.venuefinder

import com.carlomatulessy.venuefinder.webservice.FoursquareService
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Created by Carlo Matulessy on 11/02/2019.
 * Copyright © 2019 Carlo Matulessy. All rights reserved.
 */
class FoursquareWebServiceUnitTest {

    @Test
    fun obtainVenuesFromInputUserTest() {
        // Arrange
        val userInput= "Gorinchem"
        val limit = 10
        val radius = 1000
        val expectedResultListSize = 10

        // Act
        val response = FoursquareService.instance.getVenueResults(userInput, radius, limit).execute()
        val result = response.body()?.response?.venues

        // Assert
        assertEquals(expectedResultListSize, result?.size)
    }

    @Test
    fun obtainVenueDetailsFromIdTest() {
        // Arrange
        val venueId = "4b0fcd76f964a5200a6523e3"
        val expectedName = "UvA P.C. Hoofthuis"

        // Act
        val response = FoursquareService.instance.getVenueDetails(venueId).execute()
        val result = response.body()?.response?.venue

        // Assert
        assertEquals(expectedName, result?.name)
    }
}