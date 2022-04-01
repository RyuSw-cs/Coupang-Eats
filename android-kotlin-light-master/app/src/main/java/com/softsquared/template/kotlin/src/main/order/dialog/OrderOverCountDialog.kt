package com.softsquared.template.kotlin.src.main.order.dialog

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.PaintDrawable
import android.os.Bundle
import android.view.Window
import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.config.BaseActivity
import com.softsquared.template.kotlin.databinding.DialogOrderOverCountBinding
import com.softsquared.template.kotlin.src.main.order.OrderActivity

class OrderOverCountDialog :
    BaseActivity<DialogOrderOverCountBinding>(DialogOrderOverCountBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        init()
    }

    fun init() {
        with(binding){
            tvOrderCount.text = intent.getIntExtra("count",0).toString()
            /* 마이너스 버튼 */
            btMinus.setOnClickListener {
                if (tvOrderCount.text.toString().toInt() == 1) {
                    /* background 변경 */
                    btMinus.isEnabled = false
                } else {
                    tvOrderCount.text = "${tvOrderCount.text.toString().toInt() - 1}"
                }
            }
            /* 플러스 버튼 */
            btPlus.setOnClickListener {
                tvOrderCount.text = "${tvOrderCount.text.toString().toInt() + 1}"
                btMinus.isEnabled = true
            }
            btApply.setOnClickListener {
                val intent = Intent(this@OrderOverCountDialog, OrderActivity::class.java)
                intent.putExtra("count",binding.tvOrderCount.text)
                setResult(RESULT_OK,intent)
                finish()
            }
            btCancel.setOnClickListener {
                finish()
            }
        }
    }
}