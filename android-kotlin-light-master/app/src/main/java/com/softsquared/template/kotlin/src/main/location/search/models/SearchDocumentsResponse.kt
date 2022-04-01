package com.softsquared.template.kotlin.src.main.location.search.models

import com.google.gson.annotations.SerializedName

data class SearchDocumentsResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("place_name")
    val placeName: String,
    @SerializedName("category_name")
    val categoryName: String,
    @SerializedName("category_group_code")
    val categoryGroupCode: String,
    @SerializedName("category_group_name")
    val categoryGroupName: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("address_name")
    val addressName: String,
    @SerializedName("road_address_name")
    val roadAddressName: String,
    @SerializedName("x")
    val longitude: String,
    @SerializedName("y")
    val latitude: String,
    @SerializedName("place_url")
    val placeUrl: String,
    @SerializedName("distance")
    val distance: String
)
