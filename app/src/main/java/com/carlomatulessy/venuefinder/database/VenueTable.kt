package com.carlomatulessy.venuefinder.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.net.Inet4Address

/**
 * Created by Carlo Matulessy on 15/02/2019.
 * Copyright © 2019 Carlo Matulessy. All rights reserved.
 */
@Entity
data class VenueTable(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "requestId") val requestId: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "rating") val rating: Int,
    @ColumnInfo(name = "contact_phone") val contactPhone: String,
    @ColumnInfo(name = "contact_twitter") val contactTwitter: String,
    @ColumnInfo(name = "contact_instagram") val contactInstagram: String,
    @ColumnInfo(name = "contact_facebook") val contactFacebook: String,
    @ColumnInfo(name = "location_cc") val locationCC: String,
    @ColumnInfo(name = "location_city") val locationCity: String,
    @ColumnInfo(name = "location_state") val locationState: String,
    @ColumnInfo(name = "location_formattedAddress") val formattedAddress: String,
    @ColumnInfo(name = "photo_prefix") val photoPrefix: String,
    @ColumnInfo(name = "photo_suffix") val photoSuffix: String,
    @ColumnInfo(name = "photo_width") val photoWidth: Int,
    @ColumnInfo(name = "photo_height") val photoHeight: Int,
    @ColumnInfo(name = "photo_visibility") val photoVisibility: String
)