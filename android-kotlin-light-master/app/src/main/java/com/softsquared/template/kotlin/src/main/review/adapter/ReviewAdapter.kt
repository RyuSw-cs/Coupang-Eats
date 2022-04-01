package com.softsquared.template.kotlin.src.main.review.adapter

import android.graphics.Color
import android.graphics.Typeface
import android.text.Spannable
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.databinding.ItemReviewListBinding
import com.softsquared.template.kotlin.src.main.review.ReviewActivity
import com.softsquared.template.kotlin.src.main.review.models.ReviewDetailResponse

class ReviewAdapter(
    private val fragmentActivity: ReviewActivity,
    val result: List<ReviewDetailResponse>
) :
    RecyclerView.Adapter<ReviewAdapter.ReviewHolder>() {
    inner class ReviewHolder(val binding: ItemReviewListBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewHolder {
        val view = ItemReviewListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReviewHolder(view)
    }

    override fun onBindViewHolder(holder: ReviewHolder, position: Int) {
        with(holder) {
            with(result[position]) {
                binding.tvId.text = reviewUserName
                binding.rbReview.rating = score.toFloat()
                /* 조건 추가 해야함 */
                binding.tvReviewDate.text = createdAt
                if (reviewImg.isEmpty()) {
                    binding.vp2ReviewImg.visibility = View.GONE
                    binding.tvHelpCount.visibility = View.GONE
                    binding.lyHelpBtn.visibility = View.GONE
                    binding.tvBannerCount.visibility = View.GONE
                } else {
                    if (reviewImg.size == 1) {
                        binding.tvBannerCount.visibility = View.GONE
                    }
                    binding.vp2ReviewImg.adapter = ReviewImgAdapter(reviewImg, fragmentActivity)
                    binding.vp2ReviewImg.registerOnPageChangeCallback(object :
                        ViewPager2.OnPageChangeCallback() {
                        override fun onPageSelected(position: Int) {
                            binding.tvBannerCount.text =
                                "${(position) + 1}/${reviewImg.size}"
                        }
                    })
                    binding.tvReviewContent.text = content
                    binding.tvMenuContent.text = orderMenuListString
                    if (helpedCount != 0) {
                        binding.tvHelpCount.text = "${helpedCount}명에게 도움이 되었습니다."
                        val span = binding.tvHelpCount.text as Spannable
                        span.setSpan(
                            StyleSpan(Typeface.BOLD),
                            0,
                            1,
                            Spanned.SPAN_EXCLUSIVE_INCLUSIVE
                        )
                    }

                    when(isMyHelped){
                        "G" -> {
                            binding.ivReviewHelp.setColorFilter(R.color.blueForBtn)
                        }
                        "B" -> {
                            binding.ivNoReviewHelp.setColorFilter(R.color.blueForBtn)
                        }
                    }

                    binding.lyHelpBtn.setOnClickListener {
                        fragmentActivity.checkHelpBtn()
                    }
                    binding.lyNoHelpReview.setOnClickListener {
                        fragmentActivity.checkNonHelpBtn()
                    }
                }
            }
        }
    }

    override fun getItemCount() = result.size
}