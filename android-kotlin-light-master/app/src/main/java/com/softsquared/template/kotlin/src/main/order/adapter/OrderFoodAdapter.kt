package com.softsquared.template.kotlin.src.main.order.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioGroup
import android.widget.RelativeLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.databinding.ItemOrderListBinding
import com.softsquared.template.kotlin.src.main.order.OrderActivity
import com.softsquared.template.kotlin.src.main.order.dialog.OrderCountDialog
import com.softsquared.template.kotlin.src.main.order.dialog.OrderOverCountDialog
import com.softsquared.template.kotlin.src.main.storeInfo.menu.cart.models.StoreInfoMenuCartGetResponse
import com.softsquared.template.kotlin.src.main.storeInfo.menu.cart.models.StoreInfoMenuCartInfoResponse

class OrderFoodAdapter(val activity: OrderActivity, val data: StoreInfoMenuCartGetResponse) :
    RecyclerView.Adapter<OrderFoodAdapter.OrderFoodHolder>() {

    inner class OrderFoodHolder(val binding: ItemOrderListBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderFoodHolder {
        val view = ItemOrderListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderFoodHolder(view)
    }

    override fun onBindViewHolder(holder: OrderFoodHolder, position: Int) {
        with(holder) {
            with(binding) {
                with(data.result.cartMenu[position]) {
                    tvCount.text = orderCount.toString()
                    tvFoodPrice.text = "${ApplicationClass.DEC.format(mulPrice)}원"
                    tvFoodTitle.text = menuName
                    if (menuOptions != "") {
                        tvFoodContent.visibility = View.VISIBLE
                        tvFoodContent.text = menuOptions
                    }

                    lySpinner.setOnClickListener {
                        activity.showChangeOrderCountDialog(orderCount,cartIdx)
                    }

                    ivCancel.setOnClickListener {
                        activity.removeCartItem(cartIdx)
                    }
                }
            }
        }
    }

    override fun getItemCount() = data.result.cartMenu.size
}