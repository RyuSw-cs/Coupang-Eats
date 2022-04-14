package com.softsquared.template.kotlin.src.main.review

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.softsquared.template.kotlin.config.BaseActivity
import com.softsquared.template.kotlin.databinding.ActivityReviewBinding
import com.softsquared.template.kotlin.src.main.review.adapter.ReviewAdapter
import com.softsquared.template.kotlin.src.main.review.models.ReviewResponse

class ReviewActivity : BaseActivity<ActivityReviewBinding>(ActivityReviewBinding::inflate),
    ReviewActivityView {

    var sort = "recent"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    fun init() {
        ReviewActivityService(this).tryGetReview(intent.getIntExtra("storeIdx", 0), sort, "N")
    }

    override fun onGetReviewSuccess(response: ReviewResponse) {

        binding.cbPhotoReview.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                ReviewActivityService(this).tryGetReview(
                    intent.getIntExtra("storeIdx", 0),
                    sort,
                    "Y"
                )
            }
        }

        binding.btReviewSort.setOnClickListener {
            ReviewBottomSheet(this, sort).show(supportFragmentManager, "reviewSheet")
        }

        binding.tvRateScore.text = intent.getDoubleExtra("totalRate", 0.0).toString()
        binding.tvAppbarTitle.text = intent.getStringExtra("storeName")
        binding.rbRating.rating = intent.getDoubleExtra("totalRate", 0.0).toFloat()
        binding.rcvReview.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rcvReview.adapter = ReviewAdapter(this, response.result)
        binding.tvReviewCount.text = response.result.size.toString()

        binding.svSticky.setOnScrollChangeListener { _, _, _, _, _ ->
            if (binding.svSticky.isHeaderSticky) {
                binding.lyPhotoReview.elevation = 10.0f
            } else {
                binding.lyPhotoReview.elevation = 0.0f
            }
        }
    }

    override fun onGetReviewFailure(error: String) {

    }

    fun checkHelpBtn() {

    }

    fun checkNonHelpBtn() {

    }

    fun reSortReview(sort: String) {
        this.sort = sort
        ReviewActivityService(this).tryGetReview(intent.getIntExtra("storeIdx", 0), sort, "Y")
        val sort = when (sort) {
            "recent" -> {
                "최신순"
            }
            "review" -> {
                "리뷰 도움 순"
            }
            "highScore" -> {
                "별점 높은 순"
            }
            else -> {
                "별점 낮은 순"
            }
        }
        binding.btReviewSort.text = sort
    }
}