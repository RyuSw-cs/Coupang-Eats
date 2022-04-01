package com.softsquared.template.kotlin.src.main.home.adpater

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.softsquared.template.kotlin.databinding.FragmentHomeBannerBinding
import com.softsquared.template.kotlin.src.main.home.HomeFragment

class HomeBannerAdapter(private val imgList: List<Int>) :
    RecyclerView.Adapter<HomeBannerAdapter.HomeBannerHolder>() {
    inner class HomeBannerHolder(val binding: FragmentHomeBannerBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeBannerHolder {
        val view =
            FragmentHomeBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeBannerHolder(view)
    }

    override fun onBindViewHolder(holder: HomeBannerHolder, position: Int) {
        holder.binding.ivBanner.setImageResource(imgList[position % imgList.size])
    }

    override fun getItemCount() = Int.MAX_VALUE
}