package com.sibin.embibeassignment.appFeatures.data.repository.configuration

import com.sibin.embibeassignment.appFeatures.data.api.ConfigurationApi
import com.sibin.embibeassignment.appFeatures.data.api.MovieApi
import com.sibin.embibeassignment.appFeatures.data.model.cofiguration.Configurations
import retrofit2.Call
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConfigurationService
@Inject constructor(retrofit: Retrofit) : ConfigurationApi {

    private val configurationApi by lazy { retrofit.create(ConfigurationApi::class.java) }

    override fun getConfigurations(apiKey: String) = configurationApi.getConfigurations(apiKey)
}