package com.softsquared.template.kotlin.src.main.home.models.rising.store

import com.softsquared.template.kotlin.src.main.home.models.rising.store.models.HomeStoreResponse

interface HomeStoreFragmentView {
    fun onGetStoreSuccess(response: HomeStoreResponse)
    fun onGetStoreFailure(message: String)
}