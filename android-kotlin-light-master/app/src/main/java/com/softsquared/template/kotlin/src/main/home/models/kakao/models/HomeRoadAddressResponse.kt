package com.softsquared.template.kotlin.src.main.home.models.kakao.models

import com.google.gson.annotations.SerializedName

data class HomeRoadAddressResponse(
    @SerializedName("address_name")
    val addressName: String,
    @SerializedName("region_1depth_name")
    val region1depthName: String,
    @SerializedName("region_2depth_name")
    val region2depthName: String,
    @SerializedName("region_3depth_name")
    val region3depthName: String,
    @SerializedName("road_name")
    val roadName: String,
    @SerializedName("underground_yn")
    val undergroundYn: String,
    @SerializedName("sub_building_no")
    val subBuildingNo: String,
    @SerializedName("building_name")
    val buildingName: String,
    @SerializedName("zone_no")
    val zoneNo: String
)
