package com.softsquared.template.kotlin.src.main.order.orderType

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.BaseFragment
import com.softsquared.template.kotlin.databinding.FragmentOrderDeliveryBinding
import com.softsquared.template.kotlin.src.main.location.LocationActivity
import com.softsquared.template.kotlin.src.main.storeInfo.menu.cart.models.StoreInfoMenuCartGetDataResponse

class OrderDeliveryFragment(val result: StoreInfoMenuCartGetDataResponse) :
    BaseFragment<FragmentOrderDeliveryBinding>(
        FragmentOrderDeliveryBinding::bind,
        R.layout.fragment_order_delivery
    ) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    fun init() {
        with(binding) {
            //집, 회사, 기타 구분해야함.
            when (result.nowAddress.addressType) {
                "H" -> {
                    tvStoreTitle.text = "집(으)로 배달"
                }
                "C" -> {
                    tvStoreTitle.text = "회사(으)로 배달"
                }
                else -> {
                    tvStoreTitle.text = "${result.nowAddress.addressTitle}"
                }
            }

            tvAddress.text = "${result.nowAddress.address} ${result.nowAddress.addressDetail}"

            btAddressModify.setOnClickListener {
                //주소 선택으로 넘어감
                val intent = Intent(context, LocationActivity::class.java)
                intent.putExtra("cart", true)
                startActivity(intent)
            }
        }
    }
}