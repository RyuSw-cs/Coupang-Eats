package com.softsquared.template.kotlin.src.main.storeInfo.favorite

import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.src.main.favorite.FavoriteActivityRetrofitInstance
import com.softsquared.template.kotlin.src.main.favorite.models.FavoritePostResponse
import com.softsquared.template.kotlin.src.main.favorite.models.FavoriteResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FavoriteStoreInfoService(val view: FavoriteStoreInfoView) {

    fun tryPostFavorite(storeIdx: Int) {
        val favoriteActivityService =
            ApplicationClass.sRetrofit.create(FavoriteActivityRetrofitInstance::class.java)
        favoriteActivityService.postFavorite(storeIdx)
            .enqueue(object : Callback<FavoritePostResponse> {
                override fun onResponse(
                    call: Call<FavoritePostResponse>,
                    response: Response<FavoritePostResponse>
                ) {
                    if (response.body()?.isSuccess == true) {
                        view.onPostFavoriteSuccess(response.body() as FavoritePostResponse)
                    } else {
                        view.onPostFavoriteFailure(response.body()?.message!!)
                    }
                }

                override fun onFailure(call: Call<FavoritePostResponse>, t: Throwable) {
                    view.onPostFavoriteFailure(t.message ?: "즐겨찾기 등록 실패")
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