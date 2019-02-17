package com.carlomatulessy.venuefinder.webservice.model

/**
 * Created by Carlo Matulessy on 11/02/2019.
 * Copyright © 2019 Carlo Matulessy. All rights reserved.
 *
 * Description: This data class represents a retrofit model of Venue
 */
data class Venue(
    val id: String,
    val name: String,
    val description: String,
    val contact: Contact,
    val location: Location,
    val rating: Float,
    val bestPhoto: Photo
)