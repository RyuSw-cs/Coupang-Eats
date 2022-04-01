package com.softsquared.template.kotlin.src.main.storeInfo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.softsquared.template.kotlin.databinding.FragmentHomeBannerBinding
import com.softsquared.template.kotlin.src.main.home.adpater.HomeBannerAdapter

class StoreInfoDetailBannerAdapter(val context: Context, private val imgList: List<String>) :
    RecyclerView.Adapter<StoreInfoDetailBannerAdapter.StoreInfoDetailBannerHolder>() {
    inner class StoreInfoDetailBannerHolder(val binding: FragmentHomeBannerBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreInfoDetailBannerHolder {
        val view =
            FragmentHomeBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoreInfoDetailBannerHolder(view)
    }

    override fun onBindViewHolder(holder: StoreInfoDetailBannerHolder, position: Int) {
        Glide.with(context)
            .load(imgList[position % imgList.size])
            .centerCrop()
            .into(holder.binding.ivBanner)
    }

    override fun getItemCount() = Int.MAX_VALUE
}
