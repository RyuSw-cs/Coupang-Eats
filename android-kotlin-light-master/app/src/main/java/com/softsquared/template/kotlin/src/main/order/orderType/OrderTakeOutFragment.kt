package com.softsquared.template.kotlin.src.main.order.orderType

import android.os.Bundle
import android.view.View
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.BaseFragment
import com.softsquared.template.kotlin.databinding.FragmentOrderDeliveryTakeOutBinding
import com.softsquared.template.kotlin.src.main.storeInfo.menu.cart.models.StoreInfoMenuCartGetDataResponse

class OrderTakeOutFragment(val result: StoreInfoMenuCartGetDataResponse) :
    BaseFragment<FragmentOrderDeliveryTakeOutBinding>(
        FragmentOrderDeliveryTakeOutBinding::bind,
        R.layout.fragment_order_delivery_take_out
    ) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    fun init() {
        with(binding) {
            tvOrderTakeoutContent.text =
                "${result.storeAddress} ${result.storeAddressDetail} (주소지로부터 ${result.distance}km, 자동차로 약 5분)"

            //주소 복사
            tvOrderTakeoutTitle.setOnClickListener {

            }

            //지도 나오게 하기
            mOrderMap.setOnClickListener {

            }
        }
    }
}