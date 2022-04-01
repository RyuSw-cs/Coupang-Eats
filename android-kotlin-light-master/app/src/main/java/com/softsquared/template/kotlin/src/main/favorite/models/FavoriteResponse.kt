package com.softsquared.template.kotlin.src.main.favorite.models

import com.softsquared.template.kotlin.config.BaseResponse

data class FavoriteResponse(
    val result: FavoriteGetResponse
) : BaseResponse()
