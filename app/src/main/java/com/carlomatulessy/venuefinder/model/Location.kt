package com.carlomatulessy.venuefinder.model

/**
 * Created by Carlo Matulessy on 12/02/2019.
 * Copyright © 2019 Carlo Matulessy. All rights reserved.
 */
class Location(
    val address: String,
    val postalCode: String,
    val cc: String,
    val city: String,
    val state: String,
    val country: String,
    val formattedAddress: Array<String>
)