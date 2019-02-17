package com.carlomatulessy.venuefinder.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Carlo Matulessy on 15/02/2019.
 * Copyright © 2019 Carlo Matulessy. All rights reserved.
 *
 * Description: This data class is an Entity and representation of the VenueResult table
 */
@Entity
data class VenueResult(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "requestId") val requestId: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "location_cc") val locationCC: String?,
    @ColumnInfo(name = "location_city") val locationCity: String?,
    @ColumnInfo(name = "location_state") val locationState: String?,
    @ColumnInfo(name = "search_value") val searchValue: String
)