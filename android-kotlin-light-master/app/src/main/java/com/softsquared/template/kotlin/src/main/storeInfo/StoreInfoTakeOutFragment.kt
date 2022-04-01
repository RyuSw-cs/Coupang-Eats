package com.softsquared.template.kotlin.src.main.storeInfo

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.config.BaseFragment
import com.softsquared.template.kotlin.databinding.FragmentStoreTakeOutBinding
import com.softsquared.template.kotlin.src.main.storeInfo.map.StoreInfoMapActivity
import com.softsquared.template.kotlin.src.main.storeInfo.models.StoreInfoResponse

class StoreInfoTakeOutFragment(
    val data: StoreInfoResponse,
    private val userLong: Double,
    private val userLat: Double,
    private val storeLong:Double,
    private val storeLat:Double
) :
    BaseFragment<FragmentStoreTakeOutBinding>(
        FragmentStoreTakeOutBinding::bind,
        R.layout.fragment_store_take_out
    ) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    fun init() {
        with(binding) {
            tvDeliveryTime.text = "포장까지 약${data.result.timeToGo}"
            if (ApplicationClass.X_ACCESS_TOKEN != "TOKEN") {
                tvLeastDeliveryCostContent.text =
                    "${data.result.storeAddress} ${data.result.storeAddressDetail} ${data.result.buildingName} (주소지로부터 ${data.result.distance}km, 자동차 약 10분)"
            } else {
                tvLeastDeliveryCostContent.text =
                    "${data.result.storeAddress} ${data.result.storeAddressDetail} ${data.result.buildingName} (현위치로부터 ${data.result.distance}km, 자동차 약 10분)"
            }
            ivMap.setOnClickListener {
                //맵 실행
                val intent = Intent(requireContext(),StoreInfoMapActivity::class.java)
                intent.putExtra("userLongitude", userLong)
                intent.putExtra("userLatitude",userLat)
                intent.putExtra("storeLongitude", storeLong)
                intent.putExtra("storeLatitude",storeLat)
                intent.putExtra("storeAddress","${data.result.storeAddress} ${data.result.storeAddressDetail}")
                startActivity(intent)
            }
        }
    }
}