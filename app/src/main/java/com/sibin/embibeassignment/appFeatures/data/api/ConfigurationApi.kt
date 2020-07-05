package com.sibin.embibeassignment.appFeatures.data.api

import com.sibin.embibeassignment.appFeatures.data.model.cofiguration.Configurations
import com.sibin.embibeassignment.appFeatures.data.model.movie.MovieListResponseModel
import retrofit2.Call
import retrofit2.http.*

interface ConfigurationApi {
    companion object {
        private const val GET_CONFIGURATION = "configuration"
    }

    @GET(GET_CONFIGURATION)
    fun getConfigurations(
        @Query("api_key") apiKey: String
    ): Call<Configurations>

}