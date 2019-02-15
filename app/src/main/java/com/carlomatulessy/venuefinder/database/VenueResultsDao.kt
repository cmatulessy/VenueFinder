package com.carlomatulessy.venuefinder.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.carlomatulessy.venuefinder.model.Venue

/**
 * Created by Carlo Matulessy on 15/02/2019.
 * Copyright © 2019 Carlo Matulessy. All rights reserved.
 */
@Dao
interface VenueResultsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(venueResult: VenueTable)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(venueResults: List<VenueTable>)

    @Query("SELECT * FROM venueTable WHERE requestId = :requestId")
    fun getVenueResultsFor(requestId: String): LiveData<List<VenueTable>>
}