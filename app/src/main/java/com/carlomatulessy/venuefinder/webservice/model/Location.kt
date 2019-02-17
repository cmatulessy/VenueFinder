package com.carlomatulessy.venuefinder.webservice.model

/**
 * Created by Carlo Matulessy on 12/02/2019.
 * Copyright © 2019 Carlo Matulessy. All rights reserved.
 *
 * Description: This data class represents a retrofit model of Location
 */
class Location(
    val cc: String,
    val city: String,
    val state: String,
    val formattedAddress: Array<String>
)