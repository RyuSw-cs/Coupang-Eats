package com.softsquared.template.kotlin.src.main.storeInfo.menu.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.softsquared.template.kotlin.databinding.ItemMenuListBinding
import com.softsquared.template.kotlin.src.main.storeInfo.menu.StoreInfoMenuDetailActivity
import com.softsquared.template.kotlin.src.main.storeInfo.menu.models.StoreInfoMenuResponse


class StoreInfoMenuAdapter(
    val acitivity: StoreInfoMenuDetailActivity,
    val data: StoreInfoMenuResponse
) : RecyclerView.Adapter<StoreInfoMenuAdapter.StoreInfoMenuHolder>() {


    inner class StoreInfoMenuHolder(val binding: ItemMenuListBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreInfoMenuHolder {
        val view = ItemMenuListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoreInfoMenuHolder(view)
    }

    override fun onBindViewHolder(holder: StoreInfoMenuHolder, position: Int) {
        with(holder) {
            with(data.result.menuOptions[position]) {
                binding.tvMenuTitle.text = optionsTitle
                if (isRequired == "Y") {
                    binding.tvMustCheck.visibility = View.VISIBLE
                }
                binding.rcvMenuDetail.layoutManager =
                    LinearLayoutManager(acitivity, LinearLayoutManager.VERTICAL, false)
                binding.rcvMenuDetail.adapter = StoreInfoMenuDetailAdapter(
                    acitivity,
                    data.result.menuOptions[position].menuOptionsDetail,
                    isRequired
                )
            }
        }
    }
    override fun getItemCount() = data.result.menuOptions.size

}