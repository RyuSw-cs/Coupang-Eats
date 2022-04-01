package com.softsquared.template.kotlin.src.main.storeInfo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.softsquared.template.kotlin.databinding.ItemFoodDetailRcvBinding
import com.softsquared.template.kotlin.databinding.ItemStoreInfoDetailBinding
import com.softsquared.template.kotlin.src.main.storeInfo.models.StoreInfoDetailResponse
import com.softsquared.template.kotlin.src.main.storeInfo.models.StoreInfoMenuCategoryResponse
import com.softsquared.template.kotlin.src.main.storeInfo.models.StoreInfoResponse

class StoreInfoListAdapter(val storeIdx: Int, val menuInfo: List<StoreInfoMenuCategoryResponse>) :
    RecyclerView.Adapter<StoreInfoListAdapter.StoreInfoDetailHolder>() {
    class StoreInfoDetailHolder(val binding: ItemStoreInfoDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreInfoDetailHolder {
        val view =
            ItemStoreInfoDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoreInfoDetailHolder(view)
    }

    override fun onBindViewHolder(holder: StoreInfoDetailHolder, position: Int) {
        with(holder) {
            with(menuInfo[position]) {
                //음식 카테고리
                binding.tvListTitle.text = categoryName
                with(binding.rcvFoodList) {
                    layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    adapter =
                        StoreInfoDetailListAdapter(context, storeIdx, menuInfo[position].menuDetail)
                }
            }
        }
    }

    fun moveToPosition(idx: Int) {
        //만약 클릭하면 해당 아이템의 위치로

    }

    override fun getItemCount() = menuInfo.size
}