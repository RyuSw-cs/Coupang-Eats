package com.softsquared.template.kotlin.src.main.storeInfo.favorite

import com.softsquared.template.kotlin.src.main.favorite.models.FavoritePostResponse
import com.softsquared.template.kotlin.src.main.favorite.models.FavoriteResponse

interface FavoriteStoreInfoView {
    fun onPostFavoriteSuccess(response: FavoritePostResponse)
    fun onPostFavoriteFailure(error: String)

    fun onDeleteFavoriteSuccess(response: FavoritePostResponse)
    fun onDeleteFavoriteFailure(error: String)
}