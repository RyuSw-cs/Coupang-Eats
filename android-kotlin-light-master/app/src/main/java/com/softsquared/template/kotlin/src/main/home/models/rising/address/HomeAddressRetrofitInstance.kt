package com.softsquared.template.kotlin.src.main.home.models.rising.address

import com.softsquared.template.kotlin.src.main.home.models.rising.address.models.HomeAddressResponse
import retrofit2.Call
import retrofit2.http.GET

interface HomeAddressRetrofitInstance {
    @GET("users/address-list")
    fun getAddress(): Call<HomeAddressResponse>
}