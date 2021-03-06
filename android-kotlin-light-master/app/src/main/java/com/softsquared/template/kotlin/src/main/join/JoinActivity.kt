package com.softsquared.template.kotlin.src.main.join

import android.os.Bundle
import android.text.InputType
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.CompoundButton
import android.widget.TextView
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.BaseActivity
import com.softsquared.template.kotlin.databinding.ActivityJoinBinding
import com.softsquared.template.kotlin.src.main.join.models.JoinResponse

class JoinActivity : BaseActivity<ActivityJoinBinding>(ActivityJoinBinding::inflate),
    JoinActivityView {

    var mustCheckAge = false
    var mustCheckUse = false
    var mustCheckTrade = false
    var mustCheckInfo = false
    var mustCheckAnotherInfo = false

    var pwVisibility = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        with(binding) {
            cbAllAgree.setOnCheckedChangeListener { _, isChecked ->
                cbAgree1.isChecked = isChecked
                cbAgree2.isChecked = isChecked
                cbAgree3.isChecked = isChecked
                cbAgree4.isChecked = isChecked
                cbAgree5.isChecked = isChecked
                cbAgree6.isChecked = isChecked
                cbAgree7.isChecked = isChecked
                cbAgree8.isChecked = isChecked
                cbAgreeAge.isChecked = isChecked
                cbAgreeUse.isChecked = isChecked
                mustCheckAge = isChecked
                mustCheckAnotherInfo = isChecked
                mustCheckInfo = isChecked
                mustCheckTrade = isChecked
                mustCheckUse = isChecked
            }
            cbAgreeUse.setOnCheckedChangeListener { _, isChecked ->
                mustCheckUse = isChecked
            }
            cbAgreeAge.setOnCheckedChangeListener { _, isChecked ->
                mustCheckAge = isChecked
            }
            cbAgree1.setOnCheckedChangeListener { _, isChecked ->
                mustCheckTrade = isChecked
            }
            cbAgree2.setOnCheckedChangeListener { _, isChecked ->
                mustCheckInfo = isChecked
            }
            cbAgree3.setOnCheckedChangeListener { _, isChecked ->
                mustCheckAnotherInfo = isChecked
            }
            cbAgree5.setOnCheckedChangeListener { _, isChecked ->
                cbAgree6.isChecked = isChecked
                cbAgree7.isChecked = isChecked
                cbAgree8.isChecked = isChecked
            }
            btJoin.setOnClickListener {
                if (mustCheckAge && mustCheckAnotherInfo && mustCheckInfo && mustCheckTrade && mustCheckUse) {
                    //?????? ???????????? ??????, ????????? ?????? ??? ????????????
                    JoinService(this@JoinActivity).tryPostJoin(
                        binding.etEmail.text.toString(),
                        binding.etPw.text.toString(),
                        binding.etName.text.toString(),
                        binding.etPhoneNumber.text.toString()
                    )
                } else {
                    tvMustCheck.visibility = View.VISIBLE
                }
            }
            ivCancel.setOnClickListener {
                finish()
            }
            ivPwVisibility.setOnClickListener {
                //???????????? ???????????? ???????????????
                if (etPw.text.toString() != "" && !pwVisibility) {
                    ivPwVisibility.setImageResource(R.drawable.ic_join_pw_visible)
                    etPw.inputType = InputType.TYPE_CLASS_TEXT
                    etPw.setSingleLine()
                    etPw.setSelection(etPw.text.toString().length)
                    pwVisibility = true
                }
                //???????????? ?????????
                else if (pwVisibility) {
                    ivPwVisibility.setImageResource(R.drawable.ic_join_pw_invisible)
                    etPw.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
                    etPw.setSingleLine()
                    etPw.transformationMethod = PasswordTransformationMethod.getInstance()
                    etPw.setSelection(etPw.text.toString().length)
                    pwVisibility = false
                }
            }
        }
    }

    override fun onPostJoinSuccess(data: JoinResponse) {
        //???????????? ??????
        showCustomToast("??????????????? ??????????????? ?????????????????????.")
        finish()
    }

    override fun onPostJoinFailure(error: String) {
        //???????????? ?????? -> ????????? ??????????????? ?????? ?????? ????????????
        showCustomToast("???????????? ??????.")
    }
}