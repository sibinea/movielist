package com.sibin.embibeassignment.appFeatures.data.repository.configuration


import com.sibin.embibeassignment.BuildConfig
import com.sibin.embibeassignment.appFeatures.data.model.cofiguration.Configurations
import com.sibin.embibeassignment.base.exception.Failure
import com.sibin.embibeassignment.base.data.functional.Either
import com.sibin.embibeassignment.base.data.network.NetworkHandler
import javax.inject.Inject

interface ConfigurationRepository {

    fun getConfiguration(
        apiKey: String = BuildConfig.API_KEY
    ): Either<Failure, Configurations>


    class Network
    @Inject constructor(
        private val networkHandler: NetworkHandler,
        private val configurationService: ConfigurationService
    ) : ConfigurationRepository {

        override fun getConfiguration(apiKey: String): Either<Failure, Configurations> {
            return when (networkHandler.isConnected()) {
                true -> {
                    networkHandler.request(
                        configurationService.getConfigurations(apiKey), {
                            it
                        }, Configurations.empty()
                    )
                }
                false -> Either.Left(Failure.NetworkConnection)
            }
        }
    }
}