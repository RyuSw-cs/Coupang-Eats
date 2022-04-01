package com.softsquared.template.kotlin.src.main.start

import android.content.Intent
import android.os.Bundle
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.config.BaseActivity
import com.softsquared.template.kotlin.databinding.ActivitySplashBinding
import com.softsquared.template.kotlin.databinding.ActivityStartBinding
import com.softsquared.template.kotlin.src.main.MainActivity
import com.softsquared.template.kotlin.src.main.start.adapter.StartAdapter

class StartActivity : BaseActivity<ActivityStartBinding>(ActivityStartBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        val imgList = listOf(
            R.drawable.bg_start_banner_1,
            R.drawable.bg_start_banner_2,
            R.drawable.bg_start_banner_3
        )
        with(binding) {
            vp2Banner.adapter = StartAdapter(this@StartActivity, imgList)
            ciIndicator.setViewPager(vp2Banner)
            btStart.setOnClickListener {
                startActivity(Intent(this@StartActivity,MainActivity::class.java))
                finish()
            }
        }
    }
}