package com.carlomatulessy.venuefinder.repository

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import com.carlomatulessy.venuefinder.database.VenueDetailResult
import com.carlomatulessy.venuefinder.database.VenueFinderDatabase
import com.carlomatulessy.venuefinder.database.VenueResult
import com.carlomatulessy.venuefinder.util.Extra

/**
 * Created by Carlo Matulessy on 17/02/2019.
 * Copyright © 2019 Carlo Matulessy. All rights reserved.
 */
open class VenueDetailResultCachedDataTask(
    context: Context,
    private val id: String,
    private val listener: ObtainCacheListener? = null
) : AsyncTask<Unit, Unit, VenueDetailResult?>() {

    interface ObtainCacheListener {
        fun onObtainedResults(results: VenueDetailResult)
        fun onObtainedError() {}
    }

    private val venueDetailResultDao =
        VenueFinderDatabase.getInstance(context).venueDetailDao()

    override fun onPreExecute() {
        super.onPreExecute()
        Log.d(
            Extra.VENUE_FINDER_KEY,
            "VenueDetailResultCachedDataTask: Get venue detail result cached data for id {$id}"
        )
    }

    override fun doInBackground(vararg params: Unit?): VenueDetailResult? =
        venueDetailResultDao.getVenueDetail(id)

    override fun onPostExecute(result: VenueDetailResult?) {
        super.onPostExecute(result)
        result?.let {
            listener?.onObtainedResults(it)
        } ?: run {
            listener?.onObtainedError()
        }
    }
}


