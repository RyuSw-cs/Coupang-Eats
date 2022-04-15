package com.softsquared.template.kotlin.src.main.home.adpater

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.databinding.ItemHomeMoreCategoryBinding
import com.softsquared.template.kotlin.databinding.ItemHomeMoreCategoryFooterBinding
import com.softsquared.template.kotlin.src.main.home.models.rising.store.models.HomeStoreDetailResponse
import com.softsquared.template.kotlin.src.main.home.models.rising.store.models.HomeStoreInfoResponse
import com.softsquared.template.kotlin.src.main.home.models.rising.store.models.HomeStoreTypeResponse

class HomeFranchiseeAdapter(val context: Context, val data: List<HomeStoreInfoResponse>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    inner class FranchiseeHolder(val binding: ItemHomeMoreCategoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    inner class FranchiseeFooter(val footerBinding: ItemHomeMoreCategoryFooterBinding) :
        RecyclerView.ViewHolder(footerBinding.root)

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            data.size -> {
                ApplicationClass.FOOTER
            }
            else -> {
                ApplicationClass.ITEM
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val holder = when (viewType) {
            ApplicationClass.FOOTER -> {
                val view = ItemHomeMoreCategoryFooterBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                FranchiseeFooter(view)
            }
            else -> {
                val view = ItemHomeMoreCategoryBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                FranchiseeHolder(view)
            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is FranchiseeHolder)
            with(holder) {
                with(data[position].storeInfo) {
                    Glide.with(context)
                        .load(storeImgUrl)
                        .centerCrop()
                        .into(binding.ivMainImg)

                    binding.tvDistance.text = "${distance}km"
                    binding.tvDeliveryFee.text = "배달비 $deliveryFee"
                    if (reviewCount != 0) {
                        binding.tvReviewCount.text = "(${reviewCount})"
                    } else {
                        binding.ivReviewStar.visibility = View.GONE
                        binding.tvReviewCount.visibility = View.GONE
                        binding.tvReviewDiv.visibility = View.GONE
                    }
                }
            }
        else {
            with(holder as FranchiseeFooter) {
                footerBinding.ivMoreInfoBtn.setOnClickListener {
                    /* 인텐트로 활성화 */
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return if (data.size > 9) {
            10
        } else {
            data.size + 1
        }
    }
}