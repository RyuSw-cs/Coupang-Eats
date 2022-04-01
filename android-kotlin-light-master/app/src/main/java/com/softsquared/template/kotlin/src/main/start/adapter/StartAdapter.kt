package com.softsquared.template.kotlin.src.main.start.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.softsquared.template.kotlin.src.main.start.StartImgFragment

class StartAdapter(fragmentActivity : FragmentActivity, private val imgList: List<Int>) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount() = imgList.size
    override fun createFragment(position: Int) = StartImgFragment(imgList[position])
}