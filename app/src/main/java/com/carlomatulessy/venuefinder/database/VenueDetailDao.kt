package com.carlomatulessy.venuefinder.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * Created by Carlo Matulessy on 15/02/2019.
 * Copyright © 2019 Carlo Matulessy. All rights reserved.
 */
@Dao
interface VenueDetailDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(venueResult: VenueDetailResult)

    @Query("SELECT * FROM venueDetailResult  WHERE id = :id ")
    fun getVenueDetail(id: String): VenueDetailResult
}