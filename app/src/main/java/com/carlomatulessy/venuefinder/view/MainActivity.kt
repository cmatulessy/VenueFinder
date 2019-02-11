package com.carlomatulessy.venuefinder.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.carlomatulessy.venuefinder.R
import com.carlomatulessy.venuefinder.view.venuefinder.VenueFinderFragment

/**
 * Created by Carlo Matulessy on 11/02/2019.
 * Copyright © 2019 Carlo Matulessy. All rights reserved.
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainer, VenueFinderFragment.newInstance())
            commit()
        }
    }
}
