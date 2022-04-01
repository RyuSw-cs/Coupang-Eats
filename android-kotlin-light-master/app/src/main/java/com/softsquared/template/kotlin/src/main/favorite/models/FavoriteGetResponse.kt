package com.softsquared.template.kotlin.src.main.favorite.models

import com.softsquared.template.kotlin.config.BaseResponse

data class FavoriteGetResponse(
    val getFavoriteListList: List<FavoriteDetailResponse>,
    val sortType: String
):BaseResponse()
