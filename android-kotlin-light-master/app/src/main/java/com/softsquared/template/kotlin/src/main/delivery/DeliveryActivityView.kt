package com.softsquared.template.kotlin.src.main.delivery

import com.softsquared.template.kotlin.src.main.delivery.models.DeliveryCancelResponse

interface DeliveryActivityView {
    fun onDeleteDeliveryCancelSuccess(response: DeliveryCancelResponse)
    fun onDeleteDeliveryCancelFailure(error: String)
}