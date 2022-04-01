package com.softsquared.template.kotlin.src.main.favorite.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.databinding.ItemFavoriteListBinding
import com.softsquared.template.kotlin.src.main.favorite.FavoriteActivity
import com.softsquared.template.kotlin.src.main.favorite.models.FavoriteDetailResponse

class FavoriteAdapter(
    val activity: FavoriteActivity,
    val result: List<FavoriteDetailResponse>,
    val remove: Boolean
) :
    RecyclerView.Adapter<FavoriteAdapter.FavoriteHolder>() {

    inner class FavoriteHolder(val binding: ItemFavoriteListBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteHolder {
        val view =
            ItemFavoriteListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteHolder(view)
    }


    override fun onBindViewHolder(holder: FavoriteHolder, position: Int) {
        with(holder) {
            with(result[position]) {
                if (remove) {
                    binding.cbDelete.visibility = View.VISIBLE
                    binding.cbDelete.setOnCheckedChangeListener { _, isChecked ->
                        if (isChecked) {
                            ApplicationClass.FAVORITE.add(storeIdx.toString())
                            activity.changeDeleteCount()
                        } else {
                            ApplicationClass.FAVORITE.remove(storeIdx.toString())
                            activity.changeDeleteCount()
                        }
                    }
                }
                Glide.with(activity)
                    .load(storeImgUrl)
                    .centerCrop()
                    .into(binding.ivStoreImg)

                binding.tvStoreTitle.text = storeName

                if (isCheetah == "N") {
                    binding.ivFastDelivery.visibility = View.GONE
                }

                if (reviewCount == 0) {
                    binding.lyStoreRate.visibility = View.GONE
                } else {
                    binding.tvRate.text = "$reviewScore(${reviewCount})"
                }
                binding.tvDistance.text = "${distance}km · "
                binding.tvDeliveryTime.text = "$timeDelivery · "
                binding.tvDeliveryFee.text = "배달비 $deliveryFee"

                if (storeBestCoupon != "") {
                    binding.tvItemCoupon.text = storeBestCoupon
                }
                if (isNewStore == "Y") {
                    binding.tvNewStore.visibility = View.VISIBLE
                }
                if (isToGo == "Y") {
                    binding.tvItemTakeOut.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun getItemCount() = result.size
}