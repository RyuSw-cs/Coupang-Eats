package com.softsquared.template.kotlin.src.main.location.search

import com.softsquared.template.kotlin.src.main.location.search.models.SearchResultResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface SearchRetrofitInstance {
    @GET("v2/local/search/keyword.json")
    fun getSearchResult(
        @Header("Authorization") token: String,
        @Query("query") keyword: String,
        @Query("page") page: Int
    ): Call<SearchResultResponse>
}