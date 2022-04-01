package com.softsquared.template.kotlin.src.main.home.models.rising.store

import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.src.main.home.models.rising.store.models.HomeStoreResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeStoreService(val view: HomeStoreFragmentView) {

    fun tryGetSortStore(long: Double, lat: Double, categoryIdx: Int) {
        val homeRetrofitInterface =
            ApplicationClass.sRetrofit.create(HomeStoreRetrofitInterface::class.java)
        homeRetrofitInterface.getStore(long, lat, categoryIdx)
            .enqueue(object : Callback<HomeStoreResponse> {
                override fun onResponse(
                    call: Call<HomeStoreResponse>,
                    response: Response<HomeStoreResponse>
                ) {
                    if (response.body()?.isSuccess == true) {
                        view.onGetStoreSuccess(response.body() as HomeStoreResponse)
                    } else {
                        view.onGetStoreFailure(response.errorBody().toString())
                    }
                }

                override fun onFailure(call: Call<HomeStoreResponse>, t: Throwable) {
                    view.onGetStoreFailure(t.message ?: "가게 정보 실패")
                }
            })
    }
}
