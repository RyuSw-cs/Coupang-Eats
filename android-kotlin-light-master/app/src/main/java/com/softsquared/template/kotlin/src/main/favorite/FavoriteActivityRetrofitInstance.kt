package com.softsquared.template.kotlin.src.main.favorite

import com.softsquared.template.kotlin.src.main.favorite.models.FavoritePostResponse
import com.softsquared.template.kotlin.src.main.favorite.models.FavoriteResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface FavoriteActivityRetrofitInstance {
    @GET("stores/favorite-list")
    fun getFavorite(
        @Query("longitude") longitude: Double,
        @Query("latitude") latitude: Double,
        @Query("sort") sort: String
    ): Call<FavoriteResponse>

    @POST("stores/favorite")
    fun postFavorite(
        @Query("storeIdx") storeIdx: Int
    ): Call<FavoritePostResponse>

    @PUT("stores/favorite")
    fun deleteFavorite(
        @Query("storeIdx") storeIdx: List<String>
    ): Call<FavoritePostResponse>
}