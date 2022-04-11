package com.softsquared.template.kotlin.src.main.home.adpater

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.databinding.ItemHomeStoreFooterBinding

import com.softsquared.template.kotlin.databinding.ItemHomeStoreListBinding
import com.softsquared.template.kotlin.src.main.home.models.rising.store.models.HomeStoreInfoResponse
import com.softsquared.template.kotlin.src.main.storeInfo.StoreInfoActivity

class HomeStoreAdapter(
    val context: Context,
    private val homeStoreInfoList: MutableList<HomeStoreInfoResponse>,
    private val long: Double,
    private val lat: Double
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    inner class HomeStoreHolder(val binding: ItemHomeStoreListBinding) :
        RecyclerView.ViewHolder(binding.root)

    inner class HomeStoreFooterHolder(val footerBinding: ItemHomeStoreFooterBinding) :
        RecyclerView.ViewHolder(footerBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val holder = when (viewType) {
            ApplicationClass.FOOTER -> {
                val view =
                    ItemHomeStoreFooterBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                HomeStoreFooterHolder(view)
            }
            else -> {
                val view =
                    ItemHomeStoreListBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                HomeStoreHolder(view)
            }
        }
        return holder
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            homeStoreInfoList.size -> ApplicationClass.FOOTER
            else -> {
                ApplicationClass.ITEM
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is HomeStoreHolder) {
            with(holder) {
                with(homeStoreInfoList[position]) {
                    //이미지, 배달비, 거리 해야함.
                    with(storeInfo) {
                        binding.tvHomeItemStoreTitle.text = storeName
                        if (reviewCount == 0) {
                            binding.tvItemHomeRating.visibility = View.GONE
                            binding.tvItemReviewCount.visibility = View.GONE
                            binding.ivHomeItemStar.visibility = View.GONE
                            binding.tvItemDistance.text = "${distance}km · "
                        } else {
                            binding.tvItemHomeRating.text = "$reviewScore"
                            binding.tvItemReviewCount.text = "(${reviewCount})"
                            binding.tvItemDistance.text = " · ${distance}km · "
                        }

                        binding.tvItemDeliveryTime.text = timeDelivery
                        //배달비 작업 해야함.
                        binding.tvItemDeliveryPrice.text = deliveryFee

                        Glide.with(context)
                            .load(storeImgUrl[0])
                            .centerCrop()
                            .into(binding.ivHomeItemMainImg)

                        Glide.with(context)
                            .load(storeImgUrl[1])
                            .centerCrop()
                            .into(binding.ivHomeItemSecondImg)

                        Glide.with(context)
                            .load(storeImgUrl[2])
                            .centerCrop()
                            .into(binding.ivHomeItemThirdImg)

                        if (isCheetah == "Y") {
                            binding.ivHomeItemFastDelivery.visibility = View.VISIBLE
                        }
                        if (isToGo == "Y") {
                            binding.tvItemTakeOut.visibility = View.VISIBLE
                        }
                        if (isCoupon == "Y") {
                            binding.tvItemCoupon.visibility = View.VISIBLE
                            //수정예정
                            binding.tvItemCoupon.text = "${storeBestCoupon.maxDiscountPrice} 쿠폰"
                        }
                        if (isNewStore == "Y") {
                            binding.tvNewStore.visibility = View.VISIBLE
                        }
                        itemView.setOnClickListener {
                            //가게 상세보기 실행
                            val intent = Intent(context, StoreInfoActivity::class.java)
                            intent.putExtra("storeIdx", storeIdx)
                            intent.putExtra("longitude", long)
                            intent.putExtra("latitude", lat)
                            intent.putExtra("distance", distance)
                            intent.putExtra("deliveryFee",deliveryFee)
                            intent.putExtra("storeInfo", this)
                            context.startActivity(intent)
                        }
                    }
                }
            }
        } else if (holder is HomeStoreFooterHolder) {
            /* 클릭 이벤트 */
        }
    }

    override fun getItemCount() = homeStoreInfoList.size + 1

}