package com.softsquared.template.kotlin.src.main.favorite

import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.src.main.favorite.models.FavoritePostResponse
import com.softsquared.template.kotlin.src.main.favorite.models.FavoriteResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FavoriteActivityService(val view: FavoriteActivityView) {
    fun tryGetFavorite(userLongitude: Double, userLatitude: Double, sort: String) {
        val favoriteActivityService =
            ApplicationClass.sRetrofit.create(FavoriteActivityRetrofitInstance::class.java)
        favoriteActivityService.getFavorite(userLongitude, userLatitude, sort).enqueue(object :
            Callback<FavoriteResponse> {
            override fun onResponse(
                call: Call<FavoriteResponse>,
                response: Response<FavoriteResponse>
            ) {
                if (response.body()?.isSuccess == true) {
                    view.onGetFavoriteSuccess(response.body() as FavoriteResponse)
                } else {
                    view.onGetFavoriteFailure(response.body()?.message!!)
                }
            }

            override fun onFailure(call: Call<FavoriteResponse>, t: Throwable) {
                view.onGetFavoriteFailure(t.message ?: "즐겨찾기 가져오기 실패")
            }
        })
    }
    fun tryDeleteFavorite(storeIdx: List<String>) {
        val favoriteActivityService =
            ApplicationClass.sRetrofit.create(FavoriteActivityRetrofitInstance::class.java)
        favoriteActivityService.deleteFavorite(storeIdx)
            .enqueue(object : Callback<FavoritePostResponse> {
                override fun onResponse(
                    call: Call<FavoritePostResponse>,
                    response: Response<FavoritePostResponse>
                ) {
                    if (response.body()?.isSuccess == true) {
                        view.onDeleteFavoriteSuccess(response.body() as FavoritePostResponse)
                    } else {
                        view.onDeleteFavoriteFailure(response.body()?.message!!)
                    }
                }

                override fun onFailure(call: Call<FavoritePostResponse>, t: Throwable) {
                    view.onDeleteFavoriteFailure(t.message ?: "즐겨찾기 삭제 실패")
                }
            })
    }
}