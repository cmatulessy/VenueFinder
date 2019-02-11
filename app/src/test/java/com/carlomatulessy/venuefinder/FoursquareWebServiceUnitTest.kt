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
        val expectedResultListSize = 30
        val callResponse = FoursquareService.instance.getVenueResults(userInput)

        // Act
        val response = callResponse.execute()
        val result = response.body()?.response?.venues

        // Assert
        assertEquals(expectedResultListSize, result?.size)
    }
}