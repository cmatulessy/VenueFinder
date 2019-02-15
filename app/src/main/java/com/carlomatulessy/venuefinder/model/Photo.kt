package com.carlomatulessy.venuefinder.model

/**
 * Created by Carlo Matulessy on 15/02/2019.
 * Copyright © 2019 Carlo Matulessy. All rights reserved.
 */
data class Photo(
    val prefix: String,
    val suffix: String,
    val width: Int,
    val height: Int,
    val visibility: String
)