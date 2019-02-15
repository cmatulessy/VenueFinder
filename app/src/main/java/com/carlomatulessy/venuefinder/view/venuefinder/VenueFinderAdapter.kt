package com.carlomatulessy.venuefinder.view.venuefinder

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.carlomatulessy.venuefinder.R
import com.carlomatulessy.venuefinder.model.Venue
import kotlinx.android.synthetic.main.venue_list_item.view.*

/**
 * Created by Carlo Matulessy on 11/02/2019.
 * Copyright © 2019 Carlo Matulessy. All rights reserved.
 */
class VenueFinderAdapter(
    private val context: Context,
    private var results: List<Venue>,
    private val listener: VenueSelectionListener
) : RecyclerView.Adapter<VenueFinderAdapter.ViewHolder>() {

    interface VenueSelectionListener {
        fun onVenueSelected(venue: Venue)
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
                venue.location.city, venue.location.state, venue.location.cc
            )

            holder.rootView.setOnClickListener { listener.onVenueSelected(venue) }
        }
    }

    override fun getItemCount() = results.size
}