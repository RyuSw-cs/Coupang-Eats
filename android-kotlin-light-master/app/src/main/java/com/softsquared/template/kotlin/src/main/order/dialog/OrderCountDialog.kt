package com.softsquared.template.kotlin.src.main.order.dialog

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.PaintDrawable
import android.os.Bundle
import android.text.Spannable
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.Window
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.BaseActivity
import com.softsquared.template.kotlin.databinding.DialogOrderCountBinding
import com.softsquared.template.kotlin.src.main.order.OrderActivity

class OrderCountDialog : AppCompatActivity() {

    lateinit var binding: DialogOrderCountBinding
    var firstCheck = false

    override fun onCreate(savedInstanceState: Bundle?) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        binding = DialogOrderCountBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    fun init() {
        with(binding) {
            when (intent.getIntExtra("count", 0)) {
                1 -> {
                    check1.isChecked = true
                }
                2 -> {
                    check2.isChecked = true
                }
                3 -> {
                    check3.isChecked = true
                }
                4 -> {
                    check4.isChecked = true
                }
                5 -> {
                    check5.isChecked = true
                }
                6 -> {
                    check6.isChecked = true
                }
                7 -> {
                    check7.isChecked = true
                }
                8 -> {
                    check8.isChecked = true
                }
                9 -> {
                    check9.isChecked = true
                }
            }

            check1.setOnCheckedChangeListener { _, isChecked ->
                if(isChecked){
                    check1.setBackgroundColor(resources.getColor(R.color.skyForBackground,null))
                    check1.setTextColor(resources.getColor(R.color.blueForBtn,null))
                    firstCheck = true
                }
                if(firstCheck){
                    val intent = Intent(this@OrderCountDialog,OrderActivity::class.java)
                    intent.putExtra("count",binding.check1.text)
                    setResult(RESULT_OK,intent)
               }
            }

            check2.setOnCheckedChangeListener { _, isChecked ->
                if(isChecked){
                    check2.setBackgroundColor(resources.getColor(R.color.skyForBackground,null))
                    check2.setTextColor(resources.getColor(R.color.blueForBtn,null))
                    firstCheck = true
                }
                if(firstCheck){
                    val intent = Intent(this@OrderCountDialog,OrderActivity::class.java)
                    intent.putExtra("count",binding.check2.text)
                    setResult(RESULT_OK,intent)
                }
            }

            check3.setOnCheckedChangeListener { _, isChecked ->
                if(isChecked){
                    check3.setBackgroundColor(resources.getColor(R.color.skyForBackground,null))
                    check3.setTextColor(resources.getColor(R.color.blueForBtn,null))
                    firstCheck = true
                }
                if(firstCheck){
                    val intent = Intent(this@OrderCountDialog,OrderActivity::class.java)
                    intent.putExtra("count",binding.check3.text)
                    setResult(RESULT_OK,intent)
                }
            }

            check4.setOnCheckedChangeListener { _, isChecked ->
                if(isChecked){
                    check4.setBackgroundColor(resources.getColor(R.color.skyForBackground,null))
                    check4.setTextColor(resources.getColor(R.color.blueForBtn,null))
                    firstCheck = true
                }
                if(firstCheck){
                    val intent = Intent(this@OrderCountDialog,OrderActivity::class.java)
                    intent.putExtra("count",binding.check4.text)
                    setResult(RESULT_OK,intent)
                }
            }

            check5.setOnCheckedChangeListener { _, isChecked ->
                if(isChecked){
                    check5.setBackgroundColor(resources.getColor(R.color.skyForBackground,null))
                    check5.setTextColor(resources.getColor(R.color.blueForBtn,null))
                    firstCheck = true
                }
                if(firstCheck){
                    val intent = Intent(this@OrderCountDialog,OrderActivity::class.java)
                    intent.putExtra("count",binding.check5.text)
                    setResult(RESULT_OK,intent)
                    finish()
                }
            }

            check6.setOnCheckedChangeListener { _, isChecked ->
                if(isChecked){
                    check6.setBackgroundColor(resources.getColor(R.color.skyForBackground,null))
                    check6.setTextColor(resources.getColor(R.color.blueForBtn,null))
                    firstCheck = true
                }
                if(firstCheck){
                    val intent = Intent(this@OrderCountDialog,OrderActivity::class.java)
                    intent.putExtra("count",binding.check6.text)
                    setResult(RESULT_OK,intent)
                }
            }

            check7.setOnCheckedChangeListener { _, isChecked ->
                if(isChecked){
                    check7.setBackgroundColor(resources.getColor(R.color.skyForBackground,null))
                    check7.setTextColor(resources.getColor(R.color.blueForBtn,null))
                    firstCheck = true
                }
                if(firstCheck){
                    val intent = Intent(this@OrderCountDialog,OrderActivity::class.java)
                    intent.putExtra("count",binding.check7.text)
                    setResult(RESULT_OK,intent)
                }
            }

            check8.setOnCheckedChangeListener { _, isChecked ->
                if(isChecked){
                    check8.setBackgroundColor(resources.getColor(R.color.skyForBackground,null))
                    check8.setTextColor(resources.getColor(R.color.blueForBtn,null))
                    firstCheck = true
                }
                if(firstCheck){
                    val intent = Intent(this@OrderCountDialog,OrderActivity::class.java)
                    intent.putExtra("count",binding.check8.text)
                    setResult(RESULT_OK,intent)
                }
            }

            check9.setOnCheckedChangeListener { _, isChecked ->
                if(isChecked){
                    check9.setBackgroundColor(resources.getColor(R.color.skyForBackground,null))
                    check9.setTextColor(resources.getColor(R.color.blueForBtn,null))
                    firstCheck = true
                }
                if(firstCheck){
                    val intent = Intent(this@OrderCountDialog,OrderActivity::class.java)
                    intent.putExtra("count",binding.check9.text)
                    setResult(RESULT_OK,intent)
                }
            }
        }
    }
}