package com.sibin.embibeassignment.appFeatures.data.model.cofiguration

import com.google.gson.annotations.SerializedName

data class Configurations(
    @SerializedName("images") val images: Images?
){
    companion object {
        fun empty() = Configurations(null)
    }
}