package com.softsquared.template.kotlin.src.main.orderList.models.reOrder

interface ReOrderView {
    fun onPostReOrderSuccess(response:ReOrderResponse)
    fun onPostReOrderFailure(error:String)

    fun onNewPostReOrderSuccess(response:ReOrderResponse)
    fun onNewPostReOrderFailure(error:String)
}