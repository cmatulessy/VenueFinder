package com.carlomatulessy.venuefinder.webservice.model

/**
 * Created by Carlo Matulessy on 14/02/2019.
 * Copyright © 2019 Carlo Matulessy. All rights reserved.
 *
 * Description: This data class represents a retrofit model of Contact
 */
data class Contact(
    val phone: String,
    val twitter: String,
    val instagram: String,
    val facebook: String
)