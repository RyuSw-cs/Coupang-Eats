package com.softsquared.template.kotlin.src.main.home.models.rising.store.models

import java.io.Serializable

data class HomeStoreTypeResponse(
    val getOnlyEatsStore:List<HomeStoreInfoResponse>,
    val getFranchiseStore:List<HomeStoreInfoResponse>,
    val getNewStore:List<HomeStoreInfoResponse>,
    val getStoreHomeRes:List<HomeStoreInfoResponse>
):Serializable
