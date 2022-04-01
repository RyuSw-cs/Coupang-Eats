package com.softsquared.template.kotlin.src.main.storeInfo.menu.cart

import com.softsquared.template.kotlin.src.main.storeInfo.menu.cart.models.StoreInfoMenuCartGetResponse
import com.softsquared.template.kotlin.src.main.storeInfo.menu.cart.models.StoreInfoMenuCartPostResponse

interface StoreInfoMenuCartView {
    fun onPostStoreInfoMenuCartSuccess(postResponse : StoreInfoMenuCartPostResponse)
    fun onPostStoreInfoMenuCartFailure(error : String)

    fun onPostStoreInfoMenuNewCartSuccess(postResponse : StoreInfoMenuCartPostResponse)
    fun onPostStoreInfoMenuNewCartFailure(error : String)

    fun onGetStoreInfoMenuCartSuccess(response : StoreInfoMenuCartGetResponse)
    fun onGetPostStoreInfoMenuCartFailure(error : String)
}