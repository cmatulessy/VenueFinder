package com.carlomatulessy.venuefinder.repository

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import com.carlomatulessy.venuefinder.database.VenueFinderDatabase
import com.carlomatulessy.venuefinder.database.VenueResult
import com.carlomatulessy.venuefinder.util.Extra.VENUE_FINDER_KEY
import com.carlomatulessy.venuefinder.webservice.FoursquareAPIResponse
import com.carlomatulessy.venuefinder.webservice.model.Venue

/**
 * Created by Carlo Matulessy on 15/02/2019.
 * Copyright © 2019 Carlo Matulessy. All rights reserved.
 *
 *  Description: This async task class is used to insert a Venue result object to the database
 */
open class VenueResultInsertTask(
    context: Context,
    private val value: String,
    private val apiResponse: FoursquareAPIResponse,
    private val listener: InsertListener? = null
) : AsyncTask<Unit, Unit, List<VenueResult>?>() {

    interface InsertListener {
        fun onInserted(results: List<VenueResult>)
        fun onInsertionError() {}
    }

    private val venueResultsDao =
        VenueFinderDatabase.getInstance(context).venueResultsDao()

    override fun onPreExecute() {
        super.onPreExecute()
        Log.d(VENUE_FINDER_KEY, "Inserting venue results for search request {$value}")
    }

    override fun doInBackground(vararg params: Unit?): List<VenueResult> {
        for (venue: Venue in apiResponse.response.venues) {
            venueResultsDao.insert(
                VenueResult(
                    id = venue.id,
                    requestId = apiResponse.meta.requestId,
                    name = venue.name,
                    locationCC = venue.location.cc,
                    locationCity = venue.location.city,
                    locationState = venue.location.state,
                    searchValue = value
                )
            )
        }

        Log.d(VENUE_FINDER_KEY, "requestId: ${apiResponse.meta.requestId}")
        return venueResultsDao.getVenueResultsForRequestId(apiResponse.meta.requestId)
    }

    override fun onPostExecute(results: List<VenueResult>?) {
        super.onPostExecute(results)
        results?.let {
            Log.d(VENUE_FINDER_KEY, "Results size: " + it.size)
            listener?.onInserted(it)
        } ?: run {
            listener?.onInsertionError()
        }
    }
}