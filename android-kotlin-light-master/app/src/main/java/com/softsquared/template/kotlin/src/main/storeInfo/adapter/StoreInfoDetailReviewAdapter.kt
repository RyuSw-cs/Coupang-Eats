package com.softsquared.template.kotlin.src.main.storeInfo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.databinding.ItemFoodDetailRcvFooterBinding
import com.softsquared.template.kotlin.databinding.ItemStoreInfoReviewBinding
import com.softsquared.template.kotlin.databinding.ItemStoreInfoReviewFooterBinding
import com.softsquared.template.kotlin.src.main.storeInfo.models.StoreInfoDetailResponse
import com.softsquared.template.kotlin.src.main.storeInfo.models.StoreInfoPhotoReviewResponse

class StoreInfoDetailReviewAdapter(
    val context: Context,
    val reviewData: List<StoreInfoPhotoReviewResponse>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class StoreInfoDetailReviewHolder(val mainBinding: ItemStoreInfoReviewBinding) :
        RecyclerView.ViewHolder(mainBinding.root)

    inner class StoreInfoDetailReviewFooterHolder(val footerBinding: ItemStoreInfoReviewFooterBinding) :
        RecyclerView.ViewHolder(footerBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val holder = when (viewType) {
            ApplicationClass.FOOTER -> {
                val view = ItemStoreInfoReviewFooterBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                StoreInfoDetailReviewFooterHolder(view)
            }
            else -> {
                val view = ItemStoreInfoReviewBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                StoreInfoDetailReviewHolder(view)
            }
        }
        return holder
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            3 -> ApplicationClass.FOOTER
            else -> ApplicationClass.ITEM
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is StoreInfoDetailReviewHolder) {
            with(holder) {
                itemView.setOnClickListener {
                    //해당 리뷰로 가기
                }
                with(reviewData[position]) {
                    mainBinding.rbReview.rating = score.toFloat()
                    mainBinding.tvReviewContent.text = content
                    Glide.with(context)
                        .load(reviewImgUrl)
                        .centerCrop()
                        .into(mainBinding.ivReview)
                }
            }
        }
        if(holder is StoreInfoDetailReviewFooterHolder){
            holder.footerBinding.lyContentWrap.setOnClickListener {
                //전체리뷰 보러가기
            }
        }
    }

    override fun getItemCount() = 4
}