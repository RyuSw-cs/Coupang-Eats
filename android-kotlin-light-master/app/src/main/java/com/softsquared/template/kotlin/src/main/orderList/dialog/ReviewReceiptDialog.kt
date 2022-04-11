package com.softsquared.template.kotlin.src.main.orderList.dialog

import android.os.Bundle
import android.view.Window
import android.view.WindowManager
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

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT)

        var data = intent.getSerializableExtra("data") as OrderListDetailResponse

        with(binding) {
            ivCancel.setOnClickListener {
                finish()
            }
            tvStoreName.text = data.storeName
            tvOrderNum.text = data.userOrderIdx.toString()
            tvDate.text = data.orderTime
            tvOrderContent.text = "${ApplicationClass.DEC.format(intent.getIntExtra("totalPrice",0))}원"
            tvDeliveryCostContent.text = "${ApplicationClass.DEC.format(intent.getIntExtra("deliveryFee",0))}원"

            //리사이클러뷰 해야함
            rcvMenuList.layoutManager =
                LinearLayoutManager(this@ReviewReceiptDialog, LinearLayoutManager.VERTICAL, false)
            rcvMenuList.adapter = ReviewReceiptAdapter(data.orderMenuInfo)
            //배달비 해야함
            tvDiscountContent.text = "0원"
            tvTotalPrice.text = "${ApplicationClass.DEC.format(intent.getIntExtra("totalPrice",0) + intent.getIntExtra("deliveryFee",0))}원"
            tvAddress.text =
                "(배달주소) ${data.userDeliveryAddress.address}\n${data.userDeliveryAddress.addressDetail}"
        }
    }
}
