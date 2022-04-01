package com.softsquared.template.kotlin.src.main.orderList.preparing.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.databinding.ItemOrderDetailInfoBinding
import com.softsquared.template.kotlin.src.main.orderList.models.OrderListDetailResponse
import com.softsquared.template.kotlin.src.main.orderList.adapter.OrderMenuListAdapter

class PreparingOrderListAdapter(val context: Context, val result: List<OrderListDetailResponse>) :
    RecyclerView.Adapter<PreparingOrderListAdapter.PreparingOrderHolder>() {
    inner class PreparingOrderHolder(val binding: ItemOrderDetailInfoBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreparingOrderHolder {
        val view =
            ItemOrderDetailInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PreparingOrderHolder(view)
    }

    override fun onBindViewHolder(holder: PreparingOrderHolder, position: Int) {
        with(holder) {
            with(result[position]) {
                binding.tvStoreTitle.text = storeName
                binding.tvOrderTime.text = orderTime
                binding.tvOrderState.text = status
                binding.tvOrderState.setTextColor(
                    context.resources.getColor(
                        R.color.blueForBtn,
                        null
                    )
                )
                binding.tvTotalPrice.text = totalPrice

                binding.ivStatus.visibility = View.VISIBLE

                Glide.with(context)
                    .load(storeImgUrl)
                    .centerCrop()
                    .into(binding.ivFoodImg)
                //리사이클러뷰 연결
                binding.rcvOrderList.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                binding.rcvOrderList.adapter = OrderMenuListAdapter(orderMenuInfo)
                //버튼 상태 변경

                binding.btReOrder.setBackgroundResource(R.drawable.bg_order_round_btn)
                binding.btReOrder.setTextColor(context.resources.getColor(R.color.white, null))
                binding.btReOrder.text = "배달 현황 보기"

                //영수증
                binding.tvReceipt.setOnClickListener {

                }
            }

        }
    }

    override fun getItemCount() = result.size
}
