package com.softsquared.template.kotlin.src.main.location.add

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.softsquared.template.kotlin.databinding.FragmentLocationBinding
import com.softsquared.template.kotlin.databinding.FragmentLocationSheetBinding
import com.softsquared.template.kotlin.databinding.FragmentLoginSheetBinding
import com.softsquared.template.kotlin.src.main.join.JoinActivity
import com.softsquared.template.kotlin.src.main.login.LoginActivity

class LocationBottomSheetDialog(val activity: LocationAddActivity) : BottomSheetDialogFragment() {
    lateinit var binding: FragmentLocationSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLocationSheetBinding.inflate(inflater, container, false)
        init()
        return binding.root
    }

    private fun init() {
        with(binding) {
            btInputDetailAddress.setOnClickListener {
                //포커스 변화 존재함.
            }
            btIgnore.setOnClickListener {
                dismiss()
                activity.addAddress()
            }
        }
    }
}