package com.carlomatulessy.venuefinder.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.carlomatulessy.venuefinder.model.Venue

/**
 * Created by Carlo Matulessy on 15/02/2019.
 * Copyright © 2019 Carlo Matulessy. All rights reserved.
 */
@Database(entities = [VenueTable::class], version = 1, exportSchema = false)
abstract class VenueFinderDatabase(): RoomDatabase() {
    abstract fun venueResultsDao(): VenueResultsDao
    abstract fun venueDetailDao(): VenueDetailDao

    companion object {
        private var instance: VenueFinderDatabase? = null

        fun getInstance(context: Context) {
            if(instance == null) {
                synchronized(VenueFinderDatabase::class) {
                    instance = Room.databaseBuilder(context.applicationContext,
                        VenueFinderDatabase::class.java, "venuefinder.db")
                        .build()
                }
            }
        }
    }
}