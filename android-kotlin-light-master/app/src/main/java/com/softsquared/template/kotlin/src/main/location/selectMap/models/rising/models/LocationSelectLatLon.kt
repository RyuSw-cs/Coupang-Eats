package com.softsquared.template.kotlin.src.main.location.selectMap.models.rising.models

import com.google.gson.annotations.SerializedName

data class LocationSelectLatLon(
    @SerializedName("userLongitude")
    val userLongitude : Double,

    @SerializedName("SerializedName")
    val userLatitude : Double
)
