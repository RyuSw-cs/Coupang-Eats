package com.softsquared.template.kotlin.src.main.review.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.softsquared.template.kotlin.src.main.review.ReviewImgFragment

class ReviewImgAdapter(private val reviewImg: List<String>, fragment: FragmentActivity) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount() = reviewImg.size

    override fun createFragment(position: Int): Fragment {
        return ReviewImgFragment(reviewImg[position])
    }
}