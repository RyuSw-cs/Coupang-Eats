package com.softsquared.template.kotlin.src.main.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.softsquared.template.kotlin.databinding.FragmentLoginSheetBinding
import com.softsquared.template.kotlin.src.main.join.JoinActivity

class LoginBottomSheetDialog : BottomSheetDialogFragment() {

    lateinit var binding: FragmentLoginSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginSheetBinding.inflate(inflater, container, false)
        init()
        return binding.root
    }

    private fun init() {
        with(binding) {
            btAppLogin.setOnClickListener {
                //소셜 로그인 대체 예정
            }
            btEmailLogin.setOnClickListener {
                startActivity(Intent(context, LoginActivity::class.java))
                dismiss()
            }
            lyJoin.setOnClickListener {
                startActivity(Intent(context, JoinActivity::class.java))
                dismiss()
            }
        }
    }
}