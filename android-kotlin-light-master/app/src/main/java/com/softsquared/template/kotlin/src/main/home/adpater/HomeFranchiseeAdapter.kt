package com.softsquared.template.kotlin.src.main.home.adpater

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.databinding.ItemHomeMoreCategoryBinding
import com.softsquared.template.kotlin.databinding.ItemHomeMoreCategoryFooterBinding
import com.softsquared.template.kotlin.src.main.home.models.rising.store.models.HomeStoreDetailResponse
import com.softsquared.template.kotlin.src.main.home.models.rising.store.models.HomeStoreInfoResponse
import com.softsquared.template.kotlin.src.main.home.models.rising.store.models.HomeStoreTypeResponse
import com.softsquared.template.kotlin.src.main.storeInfo.StoreInfoActivity

class HomeFranchiseeAdapter(
    val context: Context, val data: List<HomeStoreInfoResponse>, private val long: Double,
    private val lat: Double
) :
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
                        .load(storeImgUrl[0])
                        .centerCrop()
                        .into(binding.ivMainImg)

                    Glide.with(context)
                        .load(storeLogoUrl)
                        .centerCrop()
                        .into(binding.ivType)

                    binding.ivType.background = ShapeDrawable(OvalShape())
                    binding.ivType.clipToOutline = true

                    //투명도 변경
                    //binding.ivMainImg.alpha = 0.2f

                    if (storeLogoUrl == "N") {
                        binding.ivType.visibility = View.GONE
                    }

                    binding.tvStoreName.text = storeName

                    binding.tvDistance.text = "${distance}km"
                    if (deliveryFee.contains("무료배달")) {
                        binding.tvDeliveryFee.text = deliveryFee
                    } else {
                        binding.tvDeliveryFee.text = "배달비 $deliveryFee"
                    }
                    if (reviewCount != 0) {
                        binding.tvReviewCount.text = "(${reviewCount})"
                    } else {
                        binding.ivReviewStar.visibility = View.GONE
                        binding.tvReviewCount.visibility = View.GONE
                        binding.tvReviewDiv.visibility = View.GONE
                    }
                    itemView.setOnClickListener {
                        //가게 상세보기 실행
                        val intent = Intent(context, StoreInfoActivity::class.java)
                        intent.putExtra("storeIdx", storeIdx)
                        intent.putExtra("longitude",long)
                        intent.putExtra("latitude", lat)
                        intent.putExtra("distance", distance)
                        intent.putExtra("deliveryFee", deliveryFee)
                        intent.putExtra("storeInfo", this)
                        context.startActivity(intent)
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