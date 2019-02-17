package com.carlomatulessy.venuefinder.view.venuefinder

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.carlomatulessy.venuefinder.R
import com.carlomatulessy.venuefinder.database.VenueResult
import kotlinx.android.synthetic.main.venue_list_item.view.*

/**
 * Created by Carlo Matulessy on 11/02/2019.
 * Copyright © 2019 Carlo Matulessy. All rights reserved.
 *
 * Description: This class is responsible for populating the listview in the VenueFinderFragment.
 */
class VenueFinderAdapter(
    private val context: Context,
    private var results: List<VenueResult>,
    private val listener: VenueSelectionListener
) : RecyclerView.Adapter<VenueFinderAdapter.ViewHolder>() {

    interface VenueSelectionListener {
        fun onVenueSelected(venueResult: VenueResult)
    }

    class ViewHolder(
        val rootView: View,
        val venueName: TextView,
        val venueLocationCity: TextView
    ) : RecyclerView.ViewHolder(rootView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.venue_list_item, parent, false)

        return ViewHolder(view, view.venueName, view.venueLocationCity)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        results[position].let { venue ->
            holder.venueName.text = venue.name
            holder.venueLocationCity.text = context.getString(
                R.string.venue_location_list_item,
                venue.locationCity, venue.locationState, venue.locationCC
            )

            holder.rootView.setOnClickListener { listener.onVenueSelected(venue) }
        }
    }

    override fun getItemCount() = results.size
}