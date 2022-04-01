package com.softsquared.template.kotlin.src.main.myPage

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.viewpager2.widget.ViewPager2
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.config.BaseFragment
import com.softsquared.template.kotlin.databinding.FragmentMyPageBinding
import com.softsquared.template.kotlin.src.main.favorite.FavoriteActivity
import com.softsquared.template.kotlin.src.main.home.HomeFragment
import com.softsquared.template.kotlin.src.main.myPage.adapter.MyPageBannerAdapter
import com.softsquared.template.kotlin.src.main.myPage.models.MyEatsDetailResponse
import com.softsquared.template.kotlin.src.main.myPage.models.MyEatsResponse

class MyPageFragment :
    BaseFragment<FragmentMyPageBinding>(FragmentMyPageBinding::bind, R.layout.fragment_my_page),
    MyPageView {

    val bannerList = listOf(
        R.drawable.bg_home_banner_1,
        R.drawable.bg_home_banner_2,
        R.drawable.bg_home_banner_3
    )

    var bannerPosition = 0
    lateinit var bannerHandler: Handler
    lateinit var sliderRunnable: Runnable

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        MyPageService(this).tryGetMyEats()
    }

    fun init(result: MyEatsDetailResponse) {
        with(binding) {
            tvAppbarTitle.text = result.userName
            tvPhoneNumber.text = result.phoneNumber

            binding.lyFavorite.setOnClickListener {
                startActivity(Intent(requireContext(), FavoriteActivity::class.java))
            }

            binding.lyLogout.setOnClickListener {
                val pDialog = AlertDialog.Builder(requireContext())
                pDialog.setMessage("로그아웃 하시겠습니까?")
                //앱 설정으로 들어가짐.
                pDialog.setPositiveButton("확인") { dialog, _ ->
                    ApplicationClass.sSharedPreferences.edit().clear().apply()
                    ApplicationClass.X_ACCESS_TOKEN = "TOKEN"
                    ApplicationClass.REFRESH_TOKEN = "TOKEN"
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.ly_main_frame, HomeFragment()).commitAllowingStateLoss()
                    showCustomToast("로그아웃 되었습니다.")
                    dialog.dismiss()
                }
                pDialog.setNegativeButton("취소") { dialog, _ -> dialog.dismiss() }
                pDialog.show()
            }

            vp2Banner.adapter = MyPageBannerAdapter(
                bannerList
            )

            //홈 배너 자동 슬라이딩
            sliderRunnable = Runnable {
                vp2Banner.setCurrentItem(++bannerPosition, true)
            }
            bannerHandler = Handler(Looper.getMainLooper())

            vp2Banner.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    binding.tvBannerCount.text =
                        "${(position % bannerList.size) + 1}/${bannerList.size}"
                    bannerPosition = position
                    bannerHandler.postDelayed(sliderRunnable, 2000)
                }

                override fun onPageScrollStateChanged(state: Int) {
                    super.onPageScrollStateChanged(state)
                    when (state) {
                        //멈춤
                        ViewPager2.SCROLL_STATE_IDLE -> {
                            bannerHandler.postDelayed(sliderRunnable, 2000)
                        }

                        //드래그
                        ViewPager2.SCROLL_STATE_DRAGGING -> {
                            bannerHandler.removeCallbacks(sliderRunnable)
                        }

                        ViewPager2.SCROLL_STATE_SETTLING -> {
                            bannerHandler.removeCallbacks(sliderRunnable)
                        }
                    }
                }
            })
        }
    }

    override fun onGetMyEatsSuccess(response: MyEatsResponse) {
        init(response.result)
    }

    override fun onGetMyEatsFailure(error: String) {

    }

    override fun onPause() {
        super.onPause()
        bannerHandler.removeCallbacks(sliderRunnable)
    }
}