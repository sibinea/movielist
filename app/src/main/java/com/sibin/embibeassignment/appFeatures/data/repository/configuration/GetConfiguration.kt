package com.sibin.embibeassignment.appFeatures.data.repository.configuration

import com.sibin.embibeassignment.appFeatures.data.model.cofiguration.Configurations
import com.sibin.embibeassignment.appFeatures.data.model.movie.MovieListResponseModel
import com.sibin.embibeassignment.base.exception.Failure
import com.sibin.embibeassignment.base.data.functional.Either
import com.sibin.embibeassignment.base.data.functional.UseCase
import javax.inject.Inject

class GetConfiguration
@Inject constructor(
    private val configurationRepository: ConfigurationRepository
) :
    UseCase<Configurations, UseCase.None>() {
    override suspend fun run(params: None): Either<Failure, Configurations> {
        return configurationRepository.getConfiguration()
    }
}
