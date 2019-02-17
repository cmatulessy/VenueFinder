package com.carlomatulessy.venuefinder.instrumentedTests


import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.carlomatulessy.venuefinder.database.*
import com.carlomatulessy.venuefinder.repository.VenueDetailResultCachedDataTask
import com.carlomatulessy.venuefinder.repository.VenueResultCachedDataTask
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Carlo Matulessy on 16/02/2019.
 * Copyright © 2019 Carlo Matulessy. All rights reserved.
 */
@RunWith(AndroidJUnit4::class)
class VenueFinderDatabaseInstrumentedTest {
    private lateinit var context: Context
    private lateinit var database: VenueFinderDatabase
    private lateinit var venueResultsDao: VenueResultsDao
    private lateinit var venueDetailDao: VenueDetailDao

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context, VenueFinderDatabase::class.java
        ).build()
        venueResultsDao = database.venueResultsDao()
        venueDetailDao = database.venueDetailDao()
    }

    @After
    fun tearDown() {
        database.clearAllTables()
        database.close()
    }

    @Test
    fun validateIfDataVenueResultIsInsertedInDatabase() {
        // Arrange
        val searchValue = "New York"
        val venueResult = getDummyVenueResult(searchValue)

        // Act
        venueResultsDao.insert(venueResult)
        val results = venueResultsDao.getVenueResultsForSearchValue(searchValue)

        // Assert
        assertEquals(venueResult, results.first())
    }

    @Test
    fun validateIfDataVenueDetailIsInsertedInDatabase() {
        // Arrange
        val venueDetailResult = getDummyVenueDetailResult()

        // Act
        venueDetailDao.insert(venueDetailResult)
        val result = venueDetailDao.getVenueDetail(venueDetailResult.id)

        // Assert
        assertEquals(venueDetailResult, result)
    }

    @Test
    fun validateIfCachedDataVenueResultCanBeObtainedFromDatabase() {
        // Arrange
        val searchValue = "New York"
        val venueResult = getDummyVenueResult(searchValue)
        venueResultsDao.insert(venueResult)

        // Act
        VenueResultCachedDataTask(context, searchValue,
            object : VenueResultCachedDataTask.ObtainCacheListener {

                // Assert
                override fun onObtainedResults(results: List<VenueResult>) {
                    assertEquals(venueResult, results.first())
                }

                override fun onObtainedError() {
                    assertTrue(false)
                }
            })
    }

    @Test
    fun validateIfCachedDataVenueDetailCanBeObtainedFromDatabase() {
        // Arrange
        val venueDetailResult = getDummyVenueDetailResult()
        venueDetailDao.insert(venueDetailResult)

        // Act
        VenueDetailResultCachedDataTask(context, venueDetailResult.id,
            object : VenueDetailResultCachedDataTask.ObtainCacheListener {

                // Assert
                override fun onObtainedResults(result: VenueDetailResult) {
                    assertEquals(venueDetailResult, result)
                }

                override fun onObtainedError() {
                    assertTrue(false)
                }
            })
    }

    private fun getDummyVenueResult(value: String) =
        VenueResult(
            id = "5642aef9498e51025cf4a7a5",
            requestId = "59a45921351e3d43b07028b5",
            name = "Mr. Purple",
            locationCC = "US",
            locationCity = "New York",
            locationState = "NY",
            searchValue = value
        )

    private fun getDummyVenueDetailResult() =
        VenueDetailResult(
            id = "5a187743ccad6b307315e6fe",
            name = "Foursquare HQ",
            description = "Foursquare helps you find places you’ll love, anywhere in the world.",
            rating = 0.0F,
            contactPhone = null,
            contactTwitter = "foursquare",
            contactInstagram = "foursquare",
            contactFacebook = null,
            locationCC = "US",
            locationCity = "New York",
            locationState = "New York",
            formattedAddress = "50 W 23rd St, New York, NY 10010, United States",
            photoPrefix = "https://fastly.4sqi.net/img/general/",
            photoSuffix = "/5435652_tVudly9wn9jCMpn9N6qT54RBpyx-rc3BGWg9o4E1gOk.jpg",
            photoWidth = 1440,
            photoHeight = 1828,
            photoVisibility = "public"
        )
}

