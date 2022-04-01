package com.softsquared.template.kotlin.src.main.storeInfo

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.softsquared.template.kotlin.databinding.DialogDeliveryInfoBinding
import com.softsquared.template.kotlin.src.main.storeInfo.models.StoreInfoDeliveryResponse

class StoreDeliveryInfoDialog(context: Context, val deliveryFeeInfo: List<StoreInfoDeliveryResponse>) : Dialog(context) {

    lateinit var binding: DialogDeliveryInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogDeliveryInfoBinding.inflate(layoutInflater)

        for(idx in deliveryFeeInfo.indices){
            binding.tvDeliveryDialogPriceContent.append(deliveryFeeInfo[idx].orderPrice)
            binding.tvDeliveryDialogFeeContent.append(deliveryFeeInfo[idx].deliveryFee)
        }


        binding.btDeliveryApply.setOnClickListener {
            dismiss()
        }

        setContentView(binding.root)
    }
}