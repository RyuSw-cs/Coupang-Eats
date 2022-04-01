package com.softsquared.template.kotlin.src.main.storeInfo.menu.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.softsquared.template.kotlin.databinding.FragmentHomeBannerBinding
import com.softsquared.template.kotlin.src.main.storeInfo.menu.models.StoreInfoMenuDetailResponse

class StoreInfoMenuBannerAdapter(val context: Context, val list: StoreInfoMenuDetailResponse) :
    RecyclerView.Adapter<StoreInfoMenuBannerAdapter.StoreInfoMenuBannerHolder>() {

    inner class StoreInfoMenuBannerHolder(val binding: FragmentHomeBannerBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreInfoMenuBannerHolder {
        val view =
            FragmentHomeBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoreInfoMenuBannerHolder(view)
    }

    override fun onBindViewHolder(holder: StoreInfoMenuBannerHolder, position: Int) {
        if(list.menuImgUrl.size > 1){
            Glide.with(context)
                .load(list.menuImgUrl[position % list.menuImgUrl.size])
                .centerCrop()
                .into(holder.binding.ivBanner)
        }
        else{
            Glide.with(context)
                .load(list.menuImgUrl[position])
                .centerCrop()
                .into(holder.binding.ivBanner)
        }
    }

    override fun getItemCount() = Int.MAX_VALUE
}