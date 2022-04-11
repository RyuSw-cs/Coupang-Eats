package com.softsquared.template.kotlin.src.main.home.type.models

import com.softsquared.template.kotlin.src.main.home.models.rising.store.models.HomeStoreInfoResponse
import java.io.Serializable

data class HomeStoreGetTypeResponse(
    val getStoreHomeRes:List<HomeStoreInfoResponse>
):Serializable