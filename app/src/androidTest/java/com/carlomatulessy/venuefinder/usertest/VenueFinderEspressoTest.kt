package com.carlomatulessy.venuefinder.usertest

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.carlomatulessy.venuefinder.R
import com.carlomatulessy.venuefinder.view.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Carlo Matulessy on 17/02/2019.
 * Copyright © 2019 Carlo Matulessy. All rights reserved.
 *
 * Description: Make sure you turn off animations on the testdevice. For now it needs to be done manually. If you want to
 * run it automatically you can build a gradle command for that (usecase: Run tests on AppCenter). For this assessment it
 * is out of scope.
 *
 * TODO: For now we wait 2 seconds during a call to the backend. Due to deadline reasons, I couldn't implement an idling resource
 * function. For the future I would recommend to use idling resource instead of waiting for 2 seconds.
 * source: https://developer.android.com/training/testing/espresso/idling-resource
 */
@RunWith(AndroidJUnit4::class)
class VenueFinderEspressoTest {

    @get:Rule
    val activityTestRule = ActivityTestRule<MainActivity>(MainActivity::class.java)

    @Test
    fun validateVenueDetailSearchHappyFlow() {
        // Arrange
        val searchCommand = "New York"
        val venueTitle = "Foursquare HQ"
        val matcher = VenueSelectionMatcher.withTitle(venueTitle)

        // Act
        onView(withId(R.id.venueSearchField))
            .perform(typeText(searchCommand))
            .perform(pressImeActionButton())

        waitFor(2000)

        onView(withId(R.id.venueResultList))
            .perform(RecyclerViewActions.actionOnHolderItem(matcher, click()))

        waitFor(2000)

        // Assert
        onView(withId(R.id.venueDetailTitle))
            .check(matches(withText(venueTitle)))
    }

    private fun waitFor(millis: Long) = onView(isRoot()).perform(waitForAction(millis))
}