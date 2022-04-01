package com.softsquared.template.kotlin.src.main.storeInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.config.BaseFragment
import com.softsquared.template.kotlin.databinding.FragmentStoreDeliveryBinding
import com.softsquared.template.kotlin.src.main.storeInfo.adapter.StoreInfoDetailReviewAdapter
import com.softsquared.template.kotlin.src.main.storeInfo.models.StoreInfoResponse

class StoreInfoDeliveryFragment(val data: StoreInfoResponse) :
    BaseFragment<FragmentStoreDeliveryBinding>(
        FragmentStoreDeliveryBinding::bind,
        R.layout.fragment_store_delivery
    ) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    fun init() {
        binding.tvDeliveryTime.text = "도착까지 약 ${data.result.timeDelivery}"
        if (data.result.deliveryFeeInfo.size > 1) {
            if (data.result.minimumDeliveryFee == 0) {
                binding.tvDeliveryCostContent.text = "무료배달"
            } else {
                binding.tvDeliveryCostContent.text =
                    "${ApplicationClass.DEC.format(data.result.minimumDeliveryFee)}원~"
            }

        } else {
            if (data.result.minimumDeliveryFee == 0) {
                binding.tvDeliveryCostContent.text = "무료배달"
            } else {
                binding.tvDeliveryCostContent.text =
                    "${ApplicationClass.DEC.format(data.result.minimumDeliveryFee)}원"
            }
        }
        binding.tvDeliveryLeastCostContent.text =
            "${ApplicationClass.DEC.format(data.result.minimumPrice)}원"

        binding.btDeliveryContent.setOnClickListener {
            val layoutInflater = LayoutInflater.from(requireContext())
            val view = layoutInflater.inflate(R.layout.dialog_delivery_info, null)

            val dialog = AlertDialog.Builder(requireContext())
                .setView(view)
                .create()

            val deliveryFee = view.findViewById<TextView>(R.id.tv_delivery_dialog_price_content)
            val deliveryPrice = view.findViewById<TextView>(R.id.tv_delivery_dialog_fee_content)
            val closeBtn = view.findViewById<Button>(R.id.bt_delivery_apply)

            for (idx in data.result.deliveryFeeInfo.indices) {
                if (idx == data.result.deliveryFeeInfo.size - 1) {
                    deliveryPrice.append(data.result.deliveryFeeInfo[idx].deliveryFee)
                    deliveryFee.append(data.result.deliveryFeeInfo[idx].orderPrice)
                } else {
                    deliveryPrice.append("${data.result.deliveryFeeInfo[idx].deliveryFee}\n")
                    deliveryFee.append("${data.result.deliveryFeeInfo[idx].orderPrice}\n")
                }
            }
            closeBtn.setOnClickListener {
                dialog.dismiss()
            }
            dialog.show()
        }

        if (data.result.photoReview.size >= 3) {
            binding.rcvReview.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            binding.rcvReview.adapter =
                StoreInfoDetailReviewAdapter(requireContext(), data.result.photoReview)
        }

        /* 원산지 예정 */
        binding.lyStoreMoreInfo.setOnClickListener {

        }
    }
}