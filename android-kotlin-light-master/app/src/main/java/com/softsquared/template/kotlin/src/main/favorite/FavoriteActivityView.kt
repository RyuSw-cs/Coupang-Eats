package com.softsquared.template.kotlin.src.main.favorite

import com.softsquared.template.kotlin.src.main.favorite.models.FavoritePostResponse
import com.softsquared.template.kotlin.src.main.favorite.models.FavoriteResponse

interface FavoriteActivityView {
    fun onGetFavoriteSuccess(response: FavoriteResponse)
    fun onGetFavoriteFailure(error: String)

    fun onDeleteFavoriteSuccess(response: FavoritePostResponse)
    fun onDeleteFavoriteFailure(error: String)
}