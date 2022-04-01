package com.softsquared.template.kotlin.src.main.review

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.softsquared.template.kotlin.databinding.FragmentHomeLeastCostSheetBinding
import com.softsquared.template.kotlin.databinding.FragmentReviewSortSheetBinding

class ReviewBottomSheet(val activity: ReviewActivity, val sort: String) :
    BottomSheetDialogFragment() {
    lateinit var binding: FragmentReviewSortSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReviewSortSheetBinding.inflate(inflater, container, false)
        init()
        return binding.root
    }

    fun init() {
        when (sort) {
            "recent" -> {
                binding.ivPopular.visibility = View.VISIBLE
            }
            "review" -> {
                binding.ivReviewHelp.visibility = View.VISIBLE
            }
            "highScore" -> {
                binding.ivHighRate.visibility = View.VISIBLE
            }
            "rowScore" -> {
                binding.ivRowRate.visibility = View.VISIBLE
            }
        }

        binding.lyCurrent.setOnClickListener {
            activity.reSortReview("recent")
            dismiss()
        }
        binding.lyReviewHelp.setOnClickListener {
            activity.reSortReview("review")
            dismiss()
        }
        binding.lyHighRate.setOnClickListener {
            activity.reSortReview("highScore")
            dismiss()
        }
        binding.lyRowRate.setOnClickListener {
            activity.reSortReview("rowScore")
            dismiss()
        }
    }
}