package com.softsquared.template.kotlin.src.main.orderList.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.softsquared.template.kotlin.databinding.ItemOrderDetailRcvBinding
import com.softsquared.template.kotlin.src.main.orderList.models.OrderListMenuInfoResponse

class OrderMenuListAdapter(private val orderMenuInfo: List<OrderListMenuInfoResponse>) :
    RecyclerView.Adapter<OrderMenuListAdapter.PastOrderMenuHolder>() {
    inner class PastOrderMenuHolder(val binding: ItemOrderDetailRcvBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PastOrderMenuHolder {
        val view =
            ItemOrderDetailRcvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PastOrderMenuHolder(view)
    }

    override fun onBindViewHolder(holder: PastOrderMenuHolder, position: Int) {
        with(holder) {
            with(orderMenuInfo[position]) {
                binding.tvCount.text = orderCount.toString()
                binding.tvFoodTitle.text = menuName
                if (menuOptions == "") {
                    binding.tvFoodDetail.visibility = android.view.View.GONE
                } else {
                    binding.tvFoodDetail.text = menuOptions
                }
            }
        }
    }

    override fun getItemCount() = orderMenuInfo.size

}