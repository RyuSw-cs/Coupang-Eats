package com.softsquared.template.kotlin.src.main.orderList.dialog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.databinding.ItemReceiptBinding
import com.softsquared.template.kotlin.src.main.orderList.models.OrderListMenuInfoResponse

class ReviewReceiptAdapter(private val orderMenuInfo: List<OrderListMenuInfoResponse>) :
    RecyclerView.Adapter<ReviewReceiptAdapter.ReviewReceiptHolder>() {
    inner class ReviewReceiptHolder(val binding: ItemReceiptBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewReceiptHolder {
        val view = ItemReceiptBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReviewReceiptHolder(view)
    }

    override fun onBindViewHolder(holder: ReviewReceiptHolder, position: Int) {
        with(holder) {
            with(orderMenuInfo[position]) {
                binding.tvTitle.text = menuName
                if(menuOptions == ""){
                    binding.tvMenuOption.visibility = View.GONE
                }
                else{
                    binding.tvMenuOption.text = menuOptions
                }
                binding.tvTotalPrice.text = "${ApplicationClass.DEC.format(mulPrice)}원"
            }
        }
    }

    override fun getItemCount() = orderMenuInfo.size
}