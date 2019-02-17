package com.carlomatulessy.venuefinder.usertest

import android.view.View
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.Matcher

/**
 * Created by Carlo Matulessy on 17/02/2019.
 * Copyright © 2019 Carlo Matulessy. All rights reserved.
 *
 * Description: This extension is used to wait for a given number of seconds. See TODO in VenueFinderEspressoTest
 */
fun waitForAction(millis: Long): ViewAction {
    return object : ViewAction {
        override fun getConstraints(): Matcher<View> = ViewMatchers.isRoot()

        override fun getDescription(): String = "Wait for $millis milliseconds."

        override fun perform(uiController: UiController?, view: View?) {
            uiController?.loopMainThreadForAtLeast(millis)
        }
    }
}