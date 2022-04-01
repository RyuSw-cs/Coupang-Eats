package com.softsquared.template.kotlin.src.main.orderList.dialog

import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.databinding.DialogReceiptBinding
import com.softsquared.template.kotlin.src.main.orderList.models.OrderListDetailResponse

class ReviewReceiptDialog : AppCompatActivity() {

    lateinit var binding: DialogReceiptBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        binding = DialogReceiptBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var data = intent.getSerializableExtra("data") as OrderListDetailResponse

        with(binding) {
            ivCancel.setOnClickListener {
                finish()
            }
            tvStoreName.text = data.storeName
            tvOrderNum.text = data.userOrderIdx.toString()
            tvDate.text = data.orderTime
            tvOrderContent.text = data.totalPrice
            //리사이클러뷰 해야함
            rcvMenuList.layoutManager =
                LinearLayoutManager(this@ReviewReceiptDialog, LinearLayoutManager.VERTICAL, false)
            rcvMenuList.adapter = ReviewReceiptAdapter(data.orderMenuInfo)
            //배달비 해야함
            tvDiscountContent.text = "0원"
            tvTotalPrice.text = data.totalPrice
            tvAddress.text =
                "(상세주소) ${data.userDeliveryAddress.address}\n${data.userDeliveryAddress.addressDetail}"
        }
    }
}
