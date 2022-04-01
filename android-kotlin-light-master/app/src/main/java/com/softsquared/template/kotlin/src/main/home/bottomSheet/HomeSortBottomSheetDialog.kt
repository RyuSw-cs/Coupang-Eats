package com.softsquared.template.kotlin.src.main.home.bottomSheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.softsquared.template.kotlin.databinding.FragmentHomeDeliverySheetBinding
import com.softsquared.template.kotlin.databinding.FragmentHomeStoreSortSheetBinding
import com.softsquared.template.kotlin.src.main.home.HomeFragment

class HomeSortBottomSheetDialog(fragment: HomeFragment) : BottomSheetDialogFragment() {
    lateinit var binding : FragmentHomeStoreSortSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeStoreSortSheetBinding.inflate(inflater,container,false)
        return binding.root
    }
    fun init(){
        with(binding){
            tvDistance.setOnClickListener {

            }

            tvNew.setOnClickListener {

            }

            tvOrder.setOnClickListener {

            }

            tvPopular.setOnClickListener {

            }

            tvRate.setOnClickListener {

            }

            ivCancel.setOnClickListener {
                dismiss()
            }
        }
    }
}