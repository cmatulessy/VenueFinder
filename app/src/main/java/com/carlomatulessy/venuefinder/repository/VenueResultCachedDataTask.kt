package com.carlomatulessy.venuefinder.repository

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.LiveData
import com.carlomatulessy.venuefinder.database.VenueFinderDatabase
import com.carlomatulessy.venuefinder.database.VenueResult
import com.carlomatulessy.venuefinder.util.Extra

/**
 * Created by Carlo Matulessy on 16/02/2019.
 * Copyright © 2019 Carlo Matulessy. All rights reserved.
 */
open class VenueResultCachedDataTask(
    context: Context,
    private val value: String,
    private val listener: ObtainCacheListener? = null
) : AsyncTask<Unit, Unit, LiveData<List<VenueResult>>?>() {

    interface ObtainCacheListener {
        fun onObtainedResults(results: LiveData<List<VenueResult>>?)
        fun onObtainedError() {}
    }

    private val venueResultsDao =
        VenueFinderDatabase.getInstance(context).venueResultsDao()

    override fun onPreExecute() {
        super.onPreExecute()
        Log.d(Extra.VENUE_FINDER_KEY, "Get venue results cached data for search request {$value}")
    }

    override fun doInBackground(vararg params: Unit?): LiveData<List<VenueResult>>? =
        venueResultsDao.getVenueResultsForSearchValue(value)

    override fun onPostExecute(results: LiveData<List<VenueResult>>?) {
        super.onPostExecute(results)
        results?.let {
            listener?.onObtainedResults(it)
        } ?: run {
            listener?.onObtainedError()
        }
    }


}