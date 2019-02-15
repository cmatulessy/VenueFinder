package com.carlomatulessy.venuefinder.view

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.carlomatulessy.venuefinder.view.venuefinder.VenueFinderFragment


/**
 * Created by Carlo Matulessy on 11/02/2019.
 * Copyright © 2019 Carlo Matulessy. All rights reserved.
 */
class MainActivity : AppCompatActivity(), FragmentManager.OnBackStackChangedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.carlomatulessy.venuefinder.R.layout.activity_main)
        supportFragmentManager.addOnBackStackChangedListener(this)
        shouldDisplayHomeUp()

        supportFragmentManager.beginTransaction().apply {
            replace(com.carlomatulessy.venuefinder.R.id.fragmentContainer, VenueFinderFragment.newInstance())
            commit()
        }
    }

    override fun onBackStackChanged() {
        shouldDisplayHomeUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if(supportFragmentManager.backStackEntryCount == 0) super.onBackPressed()
        else supportFragmentManager.popBackStack()
    }

    private fun shouldDisplayHomeUp() {
        val canGoBack = supportFragmentManager.backStackEntryCount > 0
        supportActionBar?.setDisplayHomeAsUpEnabled(canGoBack)
    }
}
