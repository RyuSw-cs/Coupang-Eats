package com.softsquared.template.kotlin.src.main.start

import android.os.Bundle
import android.view.View
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.BaseFragment
import com.softsquared.template.kotlin.databinding.FragmentBannerBinding

class StartImgFragment(private val img : Int) :
    BaseFragment<FragmentBannerBinding>(FragmentBannerBinding::bind, R.layout.fragment_banner) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        binding.ivStartImg.setImageResource(img)
    }
}