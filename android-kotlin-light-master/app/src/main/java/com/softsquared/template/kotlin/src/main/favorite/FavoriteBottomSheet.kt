package com.softsquared.template.kotlin.src.main.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.softsquared.template.kotlin.databinding.FragmentFavoriteSheetBinding

class FavoriteBottomSheet(val activity: FavoriteActivity, private val sort: String) :
    BottomSheetDialogFragment() {

    lateinit var binding: FragmentFavoriteSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteSheetBinding.inflate(inflater, container, false)
        init()
        return binding.root
    }

    fun init() {
        when (sort) {
            "frequent" -> {
                binding.ivFrequent.visibility = View.VISIBLE
            }
            "recentOrder" -> {
                binding.ivRecentOrder.visibility = View.VISIBLE
            }
            "recentAdd" -> {
                binding.ivRecentAdd.visibility = View.VISIBLE
            }
        }

        binding.ivCancel.setOnClickListener {
            dismiss()
        }

        binding.lyFrequent.setOnClickListener {
            activity.reSortFavorite("frequent", "자주 주문한 순")
            dismiss()
        }
        binding.lyRecentAdd.setOnClickListener {
            activity.reSortFavorite("recentAdd", "최근 추가한 순")
            dismiss()
        }
        binding.lyRecentOrder.setOnClickListener {
            activity.reSortFavorite("recentOrder", "최근 주문한 순")
            dismiss()
        }
    }
}