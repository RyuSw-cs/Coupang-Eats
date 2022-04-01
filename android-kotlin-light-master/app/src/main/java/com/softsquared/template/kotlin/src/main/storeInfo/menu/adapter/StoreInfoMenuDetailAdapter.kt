package com.softsquared.template.kotlin.src.main.storeInfo.menu.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.RadioGroup
import androidx.recyclerview.widget.RecyclerView
import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.databinding.ItemMenuInfoCheckBinding
import com.softsquared.template.kotlin.databinding.ItemMenuInfoRadioBinding
import com.softsquared.template.kotlin.src.main.storeInfo.menu.StoreInfoMenuDetailActivity
import com.softsquared.template.kotlin.src.main.storeInfo.menu.models.StoreInfoMenuDetailOptionResponse

const val requireOption = 0
const val nonRequireOption = 1

class StoreInfoMenuDetailAdapter(
    val acitivity: StoreInfoMenuDetailActivity,
    val optionList: List<StoreInfoMenuDetailOptionResponse>,
    val isRequired: String
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var lastCheckBox: CheckBox
    var radioTitle = ""
    var checkRadio = false

    inner class StoreMenuCheckHolder(val checkBinding: ItemMenuInfoCheckBinding) :
        RecyclerView.ViewHolder(checkBinding.root)

    inner class StoreMenuRadioHolder(val radioBinding: ItemMenuInfoRadioBinding) :
        RecyclerView.ViewHolder(radioBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        //만약 필수면 라디오 홀더, 아니면 그냥 홀더.
        val holder = when (viewType) {
            requireOption -> {
                val view = ItemMenuInfoRadioBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                StoreMenuRadioHolder(view)
            }
            else -> {
                val view = ItemMenuInfoCheckBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                StoreMenuCheckHolder(view)
            }
        }
        return holder
    }

    override fun getItemViewType(position: Int): Int {
        return when (isRequired) {
            "Y" -> {
                requireOption
            }
            else -> {
                nonRequireOption
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is StoreMenuRadioHolder) {
            with(holder) {
                //메뉴 1개
                with(optionList[position]) {
                    radioBinding.tvMenuTitle.text = optionsContent
                    radioBinding.rbItem.setOnClickListener {
                        val view = it as CheckBox
                        if (view.isChecked) {
                            if (::lastCheckBox.isInitialized) {
                                //첫선택이 아님
                                lastCheckBox.isChecked = false
                                ApplicationClass.CART.add(radioBinding.tvMenuTitle.text.toString())
                                ApplicationClass.CART.remove(radioTitle)
                                radioTitle = radioBinding.tvMenuTitle.text.toString()
                                //꺼짐
                                Log.d("123", ApplicationClass.CART.toString())
                            }
                            //첫 선택
                            lastCheckBox = radioBinding.rbItem
                            //현재 선택된 타이틀
                            radioTitle = radioBinding.tvMenuTitle.text.toString()
                            ApplicationClass.CART.add(radioTitle)
                            Log.d("123", ApplicationClass.CART.toString())
                        } else {
                            view.isChecked = true
                        }
                    }
                }
            }
        } else if (holder is StoreMenuCheckHolder) {
            with(holder) {
                with(optionList[position]) {
                    checkBinding.tvMenuTitle.text = optionsContent
                    /* 라디오 버튼 해결해야함. */
                    if (addPrice == 0) {
                        checkBinding.tvMenuContent.visibility = View.GONE
                    } else {
                        checkBinding.tvMenuContent.text =
                            " (+${ApplicationClass.DEC.format(addPrice)}원)"
                    }
                    checkBinding.cbItem.setOnCheckedChangeListener { _, isChecked ->
                        if (isChecked) {
                            if (checkBinding.tvMenuContent.visibility != View.GONE) {
                                ApplicationClass.CART.add("${checkBinding.tvMenuTitle.text}${checkBinding.tvMenuContent.text}")
                            } else {
                                ApplicationClass.CART.add("${checkBinding.tvMenuTitle.text}")
                            }
                            Log.d("check", ApplicationClass.CART.toString())
                            acitivity.changePrice(addPrice)
                        } else {
                            if (checkBinding.tvMenuContent.visibility != View.GONE) {
                                ApplicationClass.CART.remove("${checkBinding.tvMenuTitle.text}${checkBinding.tvMenuContent.text}")
                            } else {
                                ApplicationClass.CART.remove("${checkBinding.tvMenuTitle.text}")
                            }
                            Log.d("unCheck", ApplicationClass.CART.toString())
                            acitivity.changePrice(-addPrice)
                        }
                    }
                }
            }
        }
    }

    override fun getItemCount() = optionList.size
}
