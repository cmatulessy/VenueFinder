package com.carlomatulessy.venuefinder.usertest

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.matcher.BoundedMatcher
import com.carlomatulessy.venuefinder.view.venuefinder.VenueFinderAdapter
import org.hamcrest.Description
import org.hamcrest.Matcher

/**
 * Created by Carlo Matulessy on 17/02/2019.
 * Copyright © 2019 Carlo Matulessy. All rights reserved.
 *
 * Description: This object is used to match the list item in the recyclerview of VenueFinderFragment
 */
object VenueSelectionMatcher {
    fun withTitle(title: String): Matcher<RecyclerView.ViewHolder> =
        object :
            BoundedMatcher<RecyclerView.ViewHolder, VenueFinderAdapter.ViewHolder>(VenueFinderAdapter.ViewHolder::class.java) {
            override fun matchesSafely(item: VenueFinderAdapter.ViewHolder) =
                item.venueName.text.equals(title)

            override fun describeTo(description: Description) {
                description.appendText("view holder with title: $title")
            }
        }
}