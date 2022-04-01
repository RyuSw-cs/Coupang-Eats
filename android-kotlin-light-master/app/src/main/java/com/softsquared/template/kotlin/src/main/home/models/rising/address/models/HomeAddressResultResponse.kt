package com.softsquared.template.kotlin.src.main.home.models.rising.address.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class HomeAddressResultResponse(
    @SerializedName("homeAddress")
    val homeAddress: HomeAddressModel,

    @SerializedName("companyAddress")
    val companyAddress: HomeAddressModel,

    @SerializedName("nowAddress")
    val nowAddress: HomeAddressModel,

    @SerializedName("otherAddress")
    val otherAddress: List<HomeAddressModel>
):Serializable
