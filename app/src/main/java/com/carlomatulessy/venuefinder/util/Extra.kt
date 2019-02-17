package com.carlomatulessy.venuefinder.util

/**
 * Created by Carlo Matulessy on 14/02/2019.
 * Copyright © 2019 Carlo Matulessy. All rights reserved.
 *
 * Description: This object is used to define keys which are used for logging or obtaining data from a Bundle() or for
 * AppCenter analytics
 */
object Extra {
    const val VENUE_FINDER_KEY = "com.carlomatulessy.venuefinder.VENUE_FINDER_KEY"
    const val VENUE_ID_KEY = "com.carlomatulessy.venuefinder.VENUE_ID_KEY"
    const val VENUE_NAME = "com.carlomatulessy.venuefinder.VENUE_NAME"
    const val REQUEST_CALL_CODE = 911
}

object Analytics {
    const val ANALYTICS_VENUE_SEARCH_SCREEN = "Show search screen"
    const val ANALYTICS_VENUE_SEARCH_COMMAND = "Search command"
    const val ANALYTICS_VENUE_SEARCH_HEADER = "City name"
    const val ANALYTICS_VENUE_SEARCH_SELECTED_VENUE = "Selected venue"
    const val ANALYTICS_VENUE_SEARCH_SELECTED_VENUE_HEADER = "Venue name"
    const val ANALYTICS_VENUE_DETAIL_SCREEN = "Show detail screen"
    const val ANALYTICS_VENUE_DETAIL_TWITTER_BTN = "Clicked on Twitter button"
    const val ANALYTICS_VENUE_DETAIL_FACEBOOK_BTN = "Clicked on Facebook button"
    const val ANALYTICS_VENUE_DETAIL_INSTAGRAM_BTN = "Clicked on Instagram button"
    const val ANALYTICS_VENUE_DETAIL_PHONE_BTN = "Clicked on phone button"
}