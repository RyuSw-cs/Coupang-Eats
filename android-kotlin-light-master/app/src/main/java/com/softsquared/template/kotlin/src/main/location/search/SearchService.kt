package com.softsquared.template.kotlin.src.main.location.search

import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.src.main.location.search.models.SearchResultResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class SearchService(val view: SearchFragmentView) {
    fun tryGetSearchResult(input: String, page: Int) {
        val searchRetrofitInstance =
            ApplicationClass.kRetrofit.create(SearchRetrofitInstance::class.java)
        searchRetrofitInstance.getSearchResult(
            "KakaoAK ${ApplicationClass.KAKAO_REST_API_KEY}",
            input,
            page
        ).enqueue(object : Callback<SearchResultResponse> {
            override fun onResponse(
                call: Call<SearchResultResponse>,
                response: Response<SearchResultResponse>
            ) {
                if (response.isSuccessful) {
                    view.onGetSearchResultSuccess(response.body() as SearchResultResponse)
                } else {
                    view.onGetSearchResultFailure(response.errorBody().toString())
                }
            }

            override fun onFailure(call: Call<SearchResultResponse>, t: Throwable) {
                view.onGetSearchResultFailure(t.message ?: "결과 통신 실패")
            }
        })
    }

    fun tryGetContinueSearchResult(input: String, page: Int) {
        val searchRetrofitInstance =
            ApplicationClass.kRetrofit.create(SearchRetrofitInstance::class.java)
        searchRetrofitInstance.getSearchResult(
            "KakaoAK ${ApplicationClass.KAKAO_REST_API_KEY}",
            input,
            page
        ).enqueue(object : Callback<SearchResultResponse> {
            override fun onResponse(
                call: Call<SearchResultResponse>,
                response: Response<SearchResultResponse>
            ) {
                if (response.isSuccessful) {
                    view.onGetSearchResultSuccess(response.body() as SearchResultResponse)
                } else {
                    view.onGetSearchResultFailure(response.errorBody().toString())
                }
            }

            override fun onFailure(call: Call<SearchResultResponse>, t: Throwable) {
                view.onGetSearchResultFailure(t.message ?: "결과 통신 실패")
            }
        })
    }
}