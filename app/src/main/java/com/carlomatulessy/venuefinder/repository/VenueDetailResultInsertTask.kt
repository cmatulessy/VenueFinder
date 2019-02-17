package com.carlomatulessy.venuefinder.repository

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import com.carlomatulessy.venuefinder.R
import com.carlomatulessy.venuefinder.database.VenueDetailResult
import com.carlomatulessy.venuefinder.database.VenueFinderDatabase
import com.carlomatulessy.venuefinder.util.Extra
import com.carlomatulessy.venuefinder.util.Extra.VENUE_FINDER_KEY
import com.carlomatulessy.venuefinder.webservice.model.Photo
import com.carlomatulessy.venuefinder.webservice.model.PhotoEnum
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
                    if (venue.description.isNullOrEmpty()) venue.description
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
                photoPrefix = validatePhotoObjectString(venue.bestPhoto, PhotoEnum.PREFIX),
                photoSuffix = validatePhotoObjectString(venue.bestPhoto, PhotoEnum.SUFFIX),
                photoWidth = validatePhotoObjectInt(venue.bestPhoto, PhotoEnum.WIDTH),
                photoHeight = validatePhotoObjectInt(venue.bestPhoto, PhotoEnum.HEIGHT),
                photoVisibility = validatePhotoObjectString(venue.bestPhoto, PhotoEnum.VISIBILITY)
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

    private fun validatePhotoObjectString(photo: Photo?, field: PhotoEnum): String? {
        Log.d(VENUE_FINDER_KEY, "Photo: ${photo?.toString()}")
       return photo?.let { safePhoto ->
           when(field) {
               PhotoEnum.PREFIX -> safePhoto.prefix
               PhotoEnum.SUFFIX -> safePhoto.suffix
               PhotoEnum.VISIBILITY -> safePhoto.visibility
               else -> "" // We return an empty string
           }
       }
    }

    private fun validatePhotoObjectInt(photo: Photo?, field: PhotoEnum): Int? {
        return photo?.let { safePhoto ->
            when(field) {
                PhotoEnum.WIDTH -> safePhoto.width
                PhotoEnum.HEIGHT -> safePhoto.height
                else -> -1 // We return a negative value if field can't be found
            }
        }
    }
}