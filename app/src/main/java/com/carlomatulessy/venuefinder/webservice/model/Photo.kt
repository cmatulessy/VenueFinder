package com.carlomatulessy.venuefinder.webservice.model

/**
 * Created by Carlo Matulessy on 15/02/2019.
 * Copyright © 2019 Carlo Matulessy. All rights reserved.
 *
 * Description: This data class represents a retrofit model of Photo. It also has a PhotoEnum class. This one is used
 * in the async task where we insert a venue detail result into the database for caching.
 */
data class Photo(
    val prefix: String,
    val suffix: String,
    val width: Int,
    val height: Int,
    val visibility: String
)

enum class PhotoEnum {
    PREFIX, SUFFIX, WIDTH, HEIGHT, VISIBILITY
}