package com.softsquared.template.kotlin.src.main.home

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.config.BaseFragment
import com.softsquared.template.kotlin.databinding.FragmentHomeBinding
import com.softsquared.template.kotlin.src.main.home.adpater.*
import com.softsquared.template.kotlin.src.main.home.bottomSheet.HomeDeliveryBottomSheetDialog
import com.softsquared.template.kotlin.src.main.home.bottomSheet.HomeLeastCostBottomSheetDialog
import com.softsquared.template.kotlin.src.main.home.bottomSheet.HomeSortBottomSheetDialog
import com.softsquared.template.kotlin.src.main.home.models.kakao.HomeRoadAddressService
import com.softsquared.template.kotlin.src.main.home.models.kakao.HomeRoadAddressView
import com.softsquared.template.kotlin.src.main.home.models.kakao.models.HomeLocationResultResponse
import com.softsquared.template.kotlin.src.main.home.models.rising.address.HomeAddressService
import com.softsquared.template.kotlin.src.main.home.models.rising.address.HomeAddressView
import com.softsquared.template.kotlin.src.main.home.models.rising.address.models.HomeAddressResponse
import com.softsquared.template.kotlin.src.main.home.models.rising.autoLogin.AutoLoginResponse
import com.softsquared.template.kotlin.src.main.home.models.rising.autoLogin.AutoLoginService
import com.softsquared.template.kotlin.src.main.home.models.rising.autoLogin.AutoLoginView
import com.softsquared.template.kotlin.src.main.home.models.rising.cart.CartService
import com.softsquared.template.kotlin.src.main.home.models.rising.cart.CartView
import com.softsquared.template.kotlin.src.main.home.models.rising.store.HomeStoreFragmentView
import com.softsquared.template.kotlin.src.main.home.models.rising.store.HomeStoreService
import com.softsquared.template.kotlin.src.main.home.models.rising.store.models.HomeStoreInfoResponse
import com.softsquared.template.kotlin.src.main.home.models.rising.store.models.HomeStoreResponse
import com.softsquared.template.kotlin.src.main.location.LocationActivity
import com.softsquared.template.kotlin.src.main.login.LoginBottomSheetDialog
import com.softsquared.template.kotlin.src.main.order.OrderActivity
import com.softsquared.template.kotlin.src.main.storeInfo.menu.cart.StoreInfoMenuCartService
import com.softsquared.template.kotlin.src.main.storeInfo.menu.cart.StoreInfoMenuCartView
import com.softsquared.template.kotlin.src.main.storeInfo.menu.cart.models.StoreInfoMenuCartGetResponse
import com.softsquared.template.kotlin.src.main.storeInfo.menu.cart.models.StoreInfoMenuCartPostResponse
import com.softsquared.template.kotlin.util.DistanceManager

class HomeFragment :
    BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::bind, R.layout.fragment_home),
    HomeRoadAddressView, HomeStoreFragmentView, HomeAddressView, CartView, AutoLoginView {

    lateinit var homeAddressResponse: HomeAddressResponse
    var addressDataCheck = false
    var noAddressLong = 0.0
    var noAddressLat = 0.0

    var deliveryFee = 0

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
        init()
    }

    private fun init() {
        with(binding) {
            binding.lyCartPrice.setOnClickListener {
                //여기에 배달비를 줘야함!
                val intent = Intent(context, OrderActivity::class.java)
                intent.putExtra("deliveryFee",deliveryFee)
                startActivity(intent)
            }
            svSticky.setOnScrollChangeListener { _, _, _, _, _ ->
                if (svSticky.isHeaderSticky) {
                    divSort.visibility = View.VISIBLE
                } else {
                    divSort.visibility = View.GONE
                }
            }
            tvBannerCount.text = "1/${bannerList.size}"

            tvAppbarTitle.setOnClickListener {
                //로그인이 안됨!
                if (ApplicationClass.X_ACCESS_TOKEN == "TOKEN") {
                    LoginBottomSheetDialog().show(parentFragmentManager, "LoginBottomSheet")
                } else {
                    val intent = Intent(context, LocationActivity::class.java)
                    startActivity(intent)
                }
            }

            vp2Banner.adapter = HomeBannerAdapter(
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
            btPopular.setOnClickListener {
                HomeSortBottomSheetDialog(this@HomeFragment).show(childFragmentManager, "popular")
            }
            btDeliveryPrice.setOnClickListener {
                HomeDeliveryBottomSheetDialog(this@HomeFragment).show(
                    childFragmentManager,
                    "delivery"
                )
            }
            btLeastPrice.setOnClickListener {
                HomeLeastCostBottomSheetDialog(this@HomeFragment).show(
                    childFragmentManager,
                    "leastCost"
                )
            }
        }
    }

    private fun noUserLoginLocation() {
        //로그인 상황이 아니라면 이렇게 좌표를 받아주세요!
        showLoadingDialog(requireContext())
        val location = DistanceManager.getMyLocation(requireContext())
        noAddressLat = location?.latitude ?: 37.2460483
        noAddressLong = location?.longitude ?: 127.2302746
        HomeRoadAddressService(this).tryGetLocation(
            noAddressLong.toString(),
            noAddressLat.toString()
        )
    }

    override fun onGetLocationSuccess(data: HomeLocationResultResponse) {
        //위치 정보 성공
        dismissLoadingDialog()
        if (data.documents[0].homeRoadAddress == null) {
            binding.tvAppbarTitle.text = data.documents[0].homeOldAddress?.addressName
        } else {
            if (data.documents[0].homeRoadAddress?.buildingName == "") {
                binding.tvAppbarTitle.text = data.documents[0].homeRoadAddress?.addressName
            } else {
                binding.tvAppbarTitle.text = data.documents[0].homeRoadAddress?.buildingName
            }
        }
        addressDataCheck = false
        //위치 가져오고 그다음 상점을 가져오기(전체는 0)
        HomeStoreService(this).tryGetSortStore(noAddressLong, noAddressLat, 0)
    }

    override fun onGetLocationFailure(message: String) {
        //위치 정보 실패
        dismissLoadingDialog()
        addressDataCheck = false
        showCustomToast("위치 정보 실패")
    }

    override fun onGetStoreSuccess(response: HomeStoreResponse) {
        //상점 성공
        dismissLoadingDialog()
        with(binding) {

            rcvFranchiseeList.adapter = HomeFranchiseeAdapter(requireContext(),response.result.getFranchiseStore)
            rcvNewStoreList.adapter = HomeNewStoreAdapter(requireContext(),response.result.getFranchiseStore)
            rcvOnlyEatsList.adapter = HomeOnlyEatsAdapter(requireContext(),response.result.getFranchiseStore)

            rcvFranchiseeList.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
            rcvNewStoreList.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
            rcvOnlyEatsList.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)

            with(rcvHomeList) {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                val list = mutableListOf<HomeStoreInfoResponse>()
                for (data in response.result.getStoreHomeRes.indices) {
                    //거리가 10km 이상 차이남
                    if (response.result.getStoreHomeRes[data].storeInfo.distance < 5.0) {
                        list.add(response.result.getStoreHomeRes[data])
                    }
                }
                //사용자의 위도 경도값도 넣어주기.
                adapter = if (!addressDataCheck) {
                    HomeStoreAdapter(requireContext(), list, noAddressLong, noAddressLat)
                } else {
                    HomeStoreAdapter(
                        requireContext(),
                        list,
                        homeAddressResponse.result.nowAddress.addressLongitude,
                        homeAddressResponse.result.nowAddress.addressLatitude
                    )
                }
            }
        }
    }

    override fun onGetStoreFailure(message: String) {
        //상점 실패
        dismissLoadingDialog()
    }

    override fun getAddressSuccess(data: HomeAddressResponse) {
        //주소 성공
        dismissLoadingDialog()
        addressDataCheck = true
        homeAddressResponse = data
        //선택된 주소가 없다면? -> 좌표로 위치 구해서 표시(임시 좌표임. 테이블 변경 후 수정)
        if (homeAddressResponse.result.nowAddress.address == "") {
            noUserLoginLocation()
        }
        //선택된 주소가 있다면? -> 선택된 위치 표시
        else {
            HomeStoreService(this).tryGetSortStore(
                data.result.nowAddress.addressLongitude,
                data.result.nowAddress.addressLatitude,
                0
            )
            binding.ivTitleStart.setImageResource(R.drawable.ic_location)

            with(homeAddressResponse.result) {
                //앱바 타이틀 선택 됨.
                when {
                    companyAddress.userAddressIdx == nowAddress.userAddressIdx -> {
                        binding.tvAppbarTitle.text = "회사"
                    }
                    homeAddress.userAddressIdx == nowAddress.userAddressIdx -> {
                        binding.tvAppbarTitle.text = "집"
                    }
                    nowAddress.addressTitle != "" -> {
                        binding.tvAppbarTitle.text = nowAddress.addressTitle
                    }
                    else -> {
                        binding.tvAppbarTitle.text = nowAddress.buildingName
                    }
                }
            }
        }
    }

    override fun getAddressFailure(error: String) {
        //주소 실패
        dismissLoadingDialog()
    }

    override fun onResume() {
        super.onResume()
        //로그인 여부 확인
        AutoLoginService(this).tryAutoLogin(
            ApplicationClass.sSharedPreferences.getString("X-ACCESS-TOKEN", null),
            ApplicationClass.sSharedPreferences.getString("REFRESH-TOKEN", null)
        )
    }

    override fun onPause() {
        super.onPause()
        bannerHandler.removeCallbacks(sliderRunnable)
    }

    override fun onGetStoreInfoMenuCartSuccess(response: StoreInfoMenuCartGetResponse) {
        if (response.result.storeIdx != 0) {
            binding.lyCartPrice.visibility = View.VISIBLE
            binding.tvCartCount.text = response.result.cartMenu.size.toString()
            binding.tvDiscountPrice.text =
                "쿠페이머니 결제 시 ${ApplicationClass.DEC.format((response.result.totalPrice * 0.005).toInt())} 원 적립"
            binding.tvTotalPrice.text =
                "${ApplicationClass.DEC.format(response.result.totalPrice)}원"
        } else {
            binding.lyCartPrice.visibility = View.GONE
        }

    }

    override fun onGetPostStoreInfoMenuCartFailure(error: String) {

    }

    override fun onGetAutoLoginSuccess(response: AutoLoginResponse) {
        //재 발급 완료. 그대로 진행
        ApplicationClass.sSharedPreferences.edit().putString("X-ACCESS-TOKEN", response.result.jwt)
            .apply()
        ApplicationClass.sSharedPreferences.edit()
            .putString("REFRESH-TOKEN", response.result.refreshToken).apply()
        showLoadingDialog(requireContext())
        HomeAddressService(this).tryGetAddress()
        CartService(this@HomeFragment).tryGetCart()
    }

    override fun onGetAutoLoginFailure(error: String) {
        noUserLoginLocation()
    }

    override fun onGetAutoLoginRefresh(code: Int) {
        if (code == 2011 || code == 2014) {
            showLoadingDialog(requireContext())
            HomeAddressService(this).tryGetAddress()
            CartService(this@HomeFragment).tryGetCart()
        } else {
            noUserLoginLocation()
        }
    }
}