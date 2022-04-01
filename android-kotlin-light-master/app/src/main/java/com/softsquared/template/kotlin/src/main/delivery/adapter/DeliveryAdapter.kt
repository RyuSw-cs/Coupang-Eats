package com.softsquared.template.kotlin.src.main.delivery.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.softsquared.template.kotlin.databinding.ItemOrderDetailRcvBinding
import com.softsquared.template.kotlin.src.main.storeInfo.menu.cart.models.StoreInfoMenuCartInfoResponse

class DeliveryAdapter(private val cartMenu: List<StoreInfoMenuCartInfoResponse>) :
    RecyclerView.Adapter<DeliveryAdapter.DeliveryHolder>() {
    inner class DeliveryHolder(val binding: ItemOrderDetailRcvBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeliveryHolder {
        val view =
            ItemOrderDetailRcvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DeliveryHolder(view)
    }

    override fun onBindViewHolder(holder: DeliveryHolder, position: Int) {
        with(holder) {
            with(cartMenu[position]) {
                binding.tvCount.text = "${position + 1}"
                binding.tvFoodTitle.text = menuName
                if(menuOptions == ""){
                    binding.tvFoodDetail.visibility = View.GONE
                }
                else{
                    binding.tvFoodDetail.text = menuOptions
                }
            }
        }
    }

    override fun getItemCount() = cartMenu.size
}