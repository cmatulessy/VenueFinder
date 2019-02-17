package com.carlomatulessy.venuefinder.repository

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import com.carlomatulessy.venuefinder.R
import com.carlomatulessy.venuefinder.database.VenueDetailResult
import com.carlomatulessy.venuefinder.database.VenueFinderDatabase
import com.carlomatulessy.venuefinder.util.Extra
import com.carlomatulessy.venuefinder.util.Extra.VENUE_FINDER_KEY
import com.carlomatulessy.venuefinder.webservice.model.Venue

/**
 * Created by Carlo Matulessy on 17/02/2019.
 * Copyright © 2019 Carlo Matulessy. All rights reserved.
 */
open class VenueDetailResultInsertTask(
    private val context: Context,
    private val venue: Venue,
    private val listener: InsertListener? = null
) : AsyncTask<Unit, Unit, VenueDetailResult?>() {

    interface InsertListener {
        fun onInserted(results: VenueDetailResult)
        fun onInsertionError() {}
    }

    private val venueDetailDao =
        VenueFinderDatabase.getInstance(context).venueDetailDao()

    override fun onPreExecute() {
        super.onPreExecute()
        Log.d(Extra.VENUE_FINDER_KEY, "Inserting venue detail result for search request {${venue.name}}")
    }

    override fun doInBackground(vararg params: Unit?): VenueDetailResult? {
        Log.d(VENUE_FINDER_KEY, "VenueDetailResult: $venue")

        venueDetailDao.insert(
            VenueDetailResult(
                id = venue.id,
                name = venue.name,
                description =
                    if (venue.description.isNotEmpty()) venue.description
                    else context.getString(R.string.venue_detail_description_unknown),
                rating = (venue.rating / 5),
                contactPhone = venue.contact.phone,
                contactTwitter = venue.contact.twitter,
                contactInstagram = venue.contact.instagram,
                contactFacebook = venue.contact.facebook,
                locationCC = venue.location.cc,
                locationCity = venue.location.city,
                locationState = venue.location.state,
                formattedAddress = venue.location.formattedAddress.joinToString(",\n"),
                photoPrefix = venue.bestPhoto.prefix,
                photoSuffix = venue.bestPhoto.suffix,
                photoWidth = venue.bestPhoto.width,
                photoHeight = venue.bestPhoto.height,
                photoVisibility = venue.bestPhoto.visibility
            )
        )

        return venueDetailDao.getVenueDetail(venue.id)
    }

    override fun onPostExecute(results: VenueDetailResult?) {
        super.onPostExecute(results)
        results?.let {
            Log.d(Extra.VENUE_FINDER_KEY, "Result details for: ${it.name}")
            listener?.onInserted(it)
        } ?: run {
            listener?.onInsertionError()
        }
    }
}