package com.softsquared.template.kotlin.src.main.order.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.databinding.ItemOrderRecommendBinding
import com.softsquared.template.kotlin.src.main.order.OrderActivity
import com.softsquared.template.kotlin.src.main.storeInfo.models.StoreInfoMenuDetailResponse

class OrderRecommendAdapter(
    val activity: OrderActivity,
    val data: MutableList<StoreInfoMenuDetailResponse>
) : RecyclerView.Adapter<OrderRecommendAdapter.OrderRecommendHolder>() {

    init {
        if (data.size == 0) {
            activity.removeRcv()
        }
    }

    inner class OrderRecommendHolder(val binding: ItemOrderRecommendBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderRecommendHolder {
        val view =
            ItemOrderRecommendBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderRecommendHolder(view)
    }

    override fun onBindViewHolder(holder: OrderRecommendHolder, position: Int) {
        with(holder) {
            with(data[position]) {
                if (menuImgUrl == "") {
                    binding.ivFoodImg.visibility = View.GONE
                } else {
                    Glide.with(activity)
                        .load(menuImgUrl)
                        .fitCenter()
                        .into(binding.ivFoodImg)
                }
                binding.tvFoodTitle.text = menuName
                binding.tvFoodPrice.text = "${ApplicationClass.DEC.format(menuPrice)}원"
                binding.ivFoodAdd.setOnClickListener {
                    data.removeAt(position)
                    notifyDataSetChanged()
                    //카트에 추가 해야함.
                    activity.addRecommendFood(menuPrice, menuIdx, data)
                }
            }
        }
    }

    override fun getItemCount() = data.size

}