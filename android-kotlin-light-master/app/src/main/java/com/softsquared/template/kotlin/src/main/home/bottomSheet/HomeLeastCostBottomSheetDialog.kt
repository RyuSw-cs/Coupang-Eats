package com.softsquared.template.kotlin.src.main.home.bottomSheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.softsquared.template.kotlin.databinding.FragmentHomeDeliverySheetBinding
import com.softsquared.template.kotlin.databinding.FragmentHomeLeastCostSheetBinding
import com.softsquared.template.kotlin.src.main.home.HomeFragment

class HomeLeastCostBottomSheetDialog(fragment: HomeFragment) : BottomSheetDialogFragment() {
    lateinit var binding : FragmentHomeLeastCostSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeLeastCostSheetBinding.inflate(inflater,container,false)
        return binding.root
    }
    fun init(){
        with(binding){
            btApply.setOnClickListener {
                //다시 홈으로 -> 데이터 정렬 -> 다시 뿌려주기
                dismiss()
            }
            ivCancel.setOnClickListener {
                dismiss()
            }
        }
    }
}