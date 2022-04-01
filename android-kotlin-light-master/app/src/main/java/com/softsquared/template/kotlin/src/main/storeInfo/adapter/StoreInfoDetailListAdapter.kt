package com.softsquared.template.kotlin.src.main.storeInfo.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.marginEnd
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.config.ApplicationClass.Companion.FOOTER
import com.softsquared.template.kotlin.config.ApplicationClass.Companion.ITEM
import com.softsquared.template.kotlin.databinding.ItemFoodDetailRcvBinding
import com.softsquared.template.kotlin.databinding.ItemFoodDetailRcvFooterBinding
import com.softsquared.template.kotlin.src.main.storeInfo.menu.StoreInfoMenuDetailActivity
import com.softsquared.template.kotlin.src.main.storeInfo.models.StoreInfoMenuDetailResponse

//foot
class StoreInfoDetailListAdapter(
    val context: Context,
    val storeInx: Int,
    val menuInfo: List<StoreInfoMenuDetailResponse>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    inner class StoreInfoListDetailHolder(val mainBinding: ItemFoodDetailRcvBinding) :
        RecyclerView.ViewHolder(mainBinding.root)

    inner class StoreInfoListDetailFooterHolder(val footerBinding: ItemFoodDetailRcvFooterBinding) :
        RecyclerView.ViewHolder(footerBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val holder = when (viewType) {
            FOOTER -> {
                val binding = ItemFoodDetailRcvFooterBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                StoreInfoListDetailFooterHolder(binding)
            }
            else -> {
                val binding = ItemFoodDetailRcvBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                StoreInfoListDetailHolder(binding)
            }
        }
        return holder
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            menuInfo.size -> FOOTER
            else -> ITEM
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is StoreInfoListDetailHolder) {
            with(holder) {
                if (position == 0) {
                    mainBinding.divFoodDetail.visibility = View.GONE
                }
                with(menuInfo[position]) {
                    itemView.setOnClickListener {
                        val intent = Intent(context, StoreInfoMenuDetailActivity::class.java)
                        intent.putExtra("storeIdx", storeInx)
                        intent.putExtra("menuIdx", menuIdx)
                        context.startActivity(intent)
                    }
                    mainBinding.tvFoodTitle.text = menuName
                    mainBinding.tvFoodContent.text = menuDetail
                    mainBinding.tvFoodPrice.text = "${ApplicationClass.DEC.format(menuPrice)}원"
                    //카트 확인

                    if (isManyOrder == "Y") {
                        mainBinding.tvManyOrder.visibility = View.VISIBLE
                    }
                    if (isManyReview == "Y") {
                        mainBinding.tvManyReview.visibility = View.VISIBLE
                    }
                    if (menuImgUrl != "") {
                        //이미지가 존재
                        Glide.with(context)
                            .load(menuImgUrl)
                            .fitCenter()
                            .into(mainBinding.ivFoodDetailImg)
                    } else {
                        mainBinding.ivFoodDetailImg.visibility = View.INVISIBLE
                        //이미지가 없으면 가운대로 변경
                        val constraint = mainBinding.lyFoodDetailMain
                        val constraintSet = ConstraintSet()
                        constraintSet.clone(constraint)
                        constraintSet.connect(
                            R.id.ib_food_cart,
                            ConstraintSet.TOP,
                            R.id.ly_popular,
                            ConstraintSet.TOP, 0
                        )
                        constraintSet.connect(
                            R.id.ib_food_cart,
                            ConstraintSet.BOTTOM,
                            R.id.tv_food_content,
                            ConstraintSet.BOTTOM, 0
                        )
                        constraintSet.connect(
                            R.id.ib_food_cart,
                            ConstraintSet.END,
                            R.id.ly_food_detail_main,
                            ConstraintSet.END, mainBinding.divFoodDetail.marginEnd
                        )
                        constraintSet.applyTo(constraint)
                    }
                }
            }
        }
    }

    override fun getItemCount() = menuInfo.size + 1
}