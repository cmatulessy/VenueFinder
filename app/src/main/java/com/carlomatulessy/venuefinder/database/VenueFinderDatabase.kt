package com.carlomatulessy.venuefinder.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Created by Carlo Matulessy on 15/02/2019.
 * Copyright © 2019 Carlo Matulessy. All rights reserved.
 */
@Database(entities = [VenueResult::class, VenueDetailResult::class], version = 1, exportSchema = false)
abstract class VenueFinderDatabase: RoomDatabase() {

    abstract fun venueResultsDao(): VenueResultsDao
    abstract fun venueDetailDao(): VenueDetailDao

    companion object {
        private var instance: VenueFinderDatabase? = null

        fun getInstance(context: Context) : VenueFinderDatabase {
            instance?.let {
                return it
            } ?: run {
                synchronized(VenueFinderDatabase::class) {
                    Room.databaseBuilder(context.applicationContext,
                        VenueFinderDatabase::class.java, "venuefinder.db")
                        .build().let {
                            return it
                        }
                }
            }
        }
    }
}