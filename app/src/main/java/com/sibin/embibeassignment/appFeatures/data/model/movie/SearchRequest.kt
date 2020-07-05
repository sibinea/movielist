package com.sibin.embibeassignment.appFeatures.data.model.movie

import com.google.gson.annotations.SerializedName

data class SearchRequest(
    val keyword: String, val page: Int
)