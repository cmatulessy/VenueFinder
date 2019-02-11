package com.carlomatulessy.venuefinder.webservice

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Carlo Matulessy on 11/02/2019.
 * Copyright © 2019 Carlo Matulessy. All rights reserved.
 */
interface FoursquareAPICalls {

    // TODO hide secrets
    @GET("/v2/venues/search?client_id=TY23JDLBXI5V4PS0BJE1GLHWRDYMJQ302TEQIEY2Y3M4C2KL&client_secret=WXVK1VECTKHC52NML15CMH1ZCVMWDY2Z3B2VB5RUUGKCMUDE&v=20190211")
    fun getVenueResults(@Query("near") near: String) : Call<FoursquareAPIResponse>

}