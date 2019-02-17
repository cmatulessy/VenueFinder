package com.carlomatulessy.venuefinder.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * Created by Carlo Matulessy on 15/02/2019.
 * Copyright © 2019 Carlo Matulessy. All rights reserved.
 *
 * Description: This interface is used to access data from the Room database related to venue results of a search query
 */
@Dao
interface VenueResultsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(venueResult: VenueResult): Long

    @Query("SELECT * FROM venueResult WHERE requestId = :requestId")
    fun getVenueResultsForRequestId(requestId: String): List<VenueResult>

    @Query("SELECT * FROM VenueResult WHERE search_value = :value")
    fun getVenueResultsForSearchValue(value: String): List<VenueResult>
}