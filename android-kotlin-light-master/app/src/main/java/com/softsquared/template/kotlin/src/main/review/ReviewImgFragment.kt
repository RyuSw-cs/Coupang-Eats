package com.softsquared.template.kotlin.src.main.review

import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.BaseFragment
import com.softsquared.template.kotlin.databinding.FragmentBannerBinding

class ReviewImgFragment(private val imgUrl:String):BaseFragment<FragmentBannerBinding>(FragmentBannerBinding::bind, R.layout.fragment_banner) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(this)
            .load(imgUrl)
            .centerCrop()
            .into(binding.ivStartImg)
    }
}