package com.carlomatulessy.venuefinder.instrumentedTests

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.*
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.carlomatulessy.venuefinder.repository.VenueRepository
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Created by Carlo Matulessy on 17/02/2019.
 * Copyright © 2019 Carlo Matulessy. All rights reserved.
 */
@RunWith(AndroidJUnit4::class)
class VenueFinderRepositoryInstrumentedTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun validateVenueResultsFromRepository() {
        // Arrange
        val repository = VenueRepository()
        val context = ApplicationProvider.getApplicationContext<Context>()
        val value = "Gorinchem"

        // Act
        val results =
            repository.getVenues(context, value, 1000, 10)

        // Assert
        results.observeOnce {
            assertEquals(10, it.size)
        }
    }

    @Test
    fun validateVenueDetailResultFromRepository() {
        // Arrange
        val repository = VenueRepository()
        val context = ApplicationProvider.getApplicationContext<Context>()
        val value = "5642aef9498e51025cf4a7a5"

        // Act
        val results = repository.getVenueDetails(context, value)

        // Assert
        results.observeOnce {
            assertEquals(value, it.id)
        }

    }
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