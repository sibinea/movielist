package com.sibin.embibeassignment.appFeatures.data.model.movie

import com.google.gson.annotations.SerializedName

data class MovieListResponseModel(
    @SerializedName("results") val results: List<MovieDetails>,
    @SerializedName("page") val page: Int,
    @SerializedName("total_results") val total_results: Int,
    @SerializedName("dates") val dates: Dates?,
    @SerializedName("total_pages") val total_pages: Int
) {
    companion object {
        fun empty() = MovieListResponseModel(emptyList(), 0, 0, null, 0)
    }
}

