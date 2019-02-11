package com.carlomatulessy.venuefinder.webservice

import com.carlomatulessy.venuefinder.model.Venue

/**
 * Created by Carlo Matulessy on 11/02/2019.
 * Copyright © 2019 Carlo Matulessy. All rights reserved.
 */
class FoursquareAPIResponse(val meta: FoursquareMeta, val response: FoursquareResponse)

class FoursquareMeta(val code: Int, val requestId: String)

class FoursquareResponse(val venues: List<Venue>)