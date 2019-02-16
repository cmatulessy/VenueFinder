package com.carlomatulessy.venuefinder


import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.*
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.carlomatulessy.venuefinder.database.VenueFinderDatabase
import com.carlomatulessy.venuefinder.database.VenueResult
import com.carlomatulessy.venuefinder.database.VenueResultsDao
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
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
    private lateinit var database: VenueFinderDatabase
    private lateinit var venueResultsDao: VenueResultsDao

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context, VenueFinderDatabase::class.java
        ).build()
        venueResultsDao = database.venueResultsDao()
    }

    @After
    fun tearDown() {
        database.clearAllTables()
        database.close()
    }

    @Test
    fun validateIfDataIsInsertedInDatabase() {
        // Arrange
        val searchValue = "New York"
        val venueResult = getDummyVenueResult(searchValue)

        // Act
        venueResultsDao.insert(venueResult)

        // Assert
        venueResultsDao.getVenueResultsForSearchValue(searchValue).observeOnce {
            assertEquals(venueResult, it.first())
        }
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
}

fun <T> LiveData<T>.observeOnce(onChangeHandler: (T) -> Unit) {
    val observer = OneTimeObserver(handler = onChangeHandler)
    observe(observer, observer)
}

private class OneTimeObserver<T>(private val handler: (T) -> Unit) : Observer<T>, LifecycleOwner {
    private val lifecycle = LifecycleRegistry(this)
    init {
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
    }

    override fun getLifecycle(): Lifecycle = lifecycle

    override fun onChanged(t: T) {
        handler(t)
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    }
}

