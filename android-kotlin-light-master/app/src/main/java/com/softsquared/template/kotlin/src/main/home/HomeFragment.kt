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
    var subBannerPosition = 0
    lateinit var bannerHandler: Handler
    lateinit var sliderRunnable: Runnable
    lateinit var subSliderRunnable: Runnable

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        with(binding) {
            binding.lyCartPrice.setOnClickListener {
                //????????? ???????????? ?????????!
                val intent = Intent(context, OrderActivity::class.java)
                intent.putExtra("deliveryFee", deliveryFee)
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
                //???????????? ??????!
                if (ApplicationClass.X_ACCESS_TOKEN == "TOKEN") {
                    LoginBottomSheetDialog().show(parentFragmentManager, "LoginBottomSheet")
                } else {
                    val intent = Intent(context, LocationActivity::class.java)
                    startActivity(intent)
                }
            }

            vp2SubBanner.adapter = HomeBannerAdapter(bannerList)

            vp2Banner.adapter = HomeBannerAdapter(
                bannerList
            )
            subSliderRunnable = Runnable {
                vp2SubBanner.setCurrentItem(++subBannerPosition, true)
            }
            //??? ?????? ?????? ????????????
            sliderRunnable = Runnable {
                vp2Banner.setCurrentItem(++bannerPosition, true)
            }
            bannerHandler = Handler(Looper.getMainLooper())


            vp2SubBanner.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    binding.tvSubBannerCount.text =
                        "${(position % bannerList.size) + 1}/${bannerList.size}"
                    subBannerPosition = position
                    bannerHandler.postDelayed(subSliderRunnable, 2000)
                }

                override fun onPageScrollStateChanged(state: Int) {
                    super.onPageScrollStateChanged(state)
                    when (state) {
                        //??????
                        ViewPager2.SCROLL_STATE_IDLE -> {
                            bannerHandler.postDelayed(subSliderRunnable, 2000)
                        }

                        //?????????
                        ViewPager2.SCROLL_STATE_DRAGGING -> {
                            bannerHandler.removeCallbacks(subSliderRunnable)
                        }

                        ViewPager2.SCROLL_STATE_SETTLING -> {
                            bannerHandler.removeCallbacks(subSliderRunnable)
                        }
                    }
                }
            })

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
                        //??????
                        ViewPager2.SCROLL_STATE_IDLE -> {
                            bannerHandler.postDelayed(sliderRunnable, 2000)
                        }

                        //?????????
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
        //????????? ????????? ???????????? ????????? ????????? ???????????????!
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
        //?????? ?????? ??????
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
        //?????? ???????????? ????????? ????????? ????????????(????????? 0)
        HomeStoreService(this).tryGetSortStore(noAddressLong, noAddressLat, 0)
    }

    override fun onGetLocationFailure(message: String) {
        //?????? ?????? ??????
        dismissLoadingDialog()
        addressDataCheck = false
        showCustomToast("?????? ?????? ??????")
    }

    override fun onGetStoreSuccess(response: HomeStoreResponse) {
        //?????? ??????
        dismissLoadingDialog()
        with(binding) {

            with(rcvFranchiseeList) {
                if (response.result.getFranchiseStore.isEmpty()) {
                    tvFranchiseeTitle.visibility = View.GONE
                    ibFranchiseeBackBtn.visibility = View.GONE
                }
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                val list = mutableListOf<HomeStoreInfoResponse>()
                for (data in response.result.getFranchiseStore.indices) {
                    //????????? 10km ?????? ?????????
                    if (response.result.getFranchiseStore[data].storeInfo.distance < 5.0) {
                        list.add(response.result.getFranchiseStore[data])
                    }
                }
                //???????????? ?????? ???????????? ????????????.
                adapter = if (!addressDataCheck) {
                    HomeFranchiseeAdapter(requireContext(), list, noAddressLong, noAddressLat)
                } else {
                    HomeFranchiseeAdapter(
                        requireContext(),
                        list,
                        homeAddressResponse.result.nowAddress.addressLongitude,
                        homeAddressResponse.result.nowAddress.addressLatitude
                    )
                }
            }

            with(rcvNewStoreList) {
                if (response.result.getFranchiseStore.isEmpty()) {
                    tvNewStoreTitle.visibility = View.GONE
                }
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                val list = mutableListOf<HomeStoreInfoResponse>()
                for (data in response.result.getNewStore.indices) {
                    //????????? 10km ?????? ?????????
                    if (response.result.getNewStore[data].storeInfo.distance < 5.0) {
                        list.add(response.result.getNewStore[data])
                    }
                }
                //???????????? ?????? ???????????? ????????????.
                adapter = if (!addressDataCheck) {
                    HomeNewStoreAdapter(requireContext(), list, noAddressLong, noAddressLat)
                } else {
                    HomeNewStoreAdapter(
                        requireContext(),
                        list,
                        homeAddressResponse.result.nowAddress.addressLongitude,
                        homeAddressResponse.result.nowAddress.addressLatitude
                    )
                }
            }

            with(rcvOnlyEatsList) {
                if (response.result.getFranchiseStore.isEmpty()) {
                    tvOnlyEatsTitle.visibility = View.GONE
                    ibOnlyEatsBackBtn.visibility = View.GONE
                }
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                val list = mutableListOf<HomeStoreInfoResponse>()
                for (data in response.result.getOnlyEatsStore.indices) {
                    //????????? 10km ?????? ?????????
                    if (response.result.getOnlyEatsStore[data].storeInfo.distance < 5.0) {
                        list.add(response.result.getOnlyEatsStore[data])
                    }
                }
                //???????????? ?????? ???????????? ????????????.
                adapter = if (!addressDataCheck) {
                    HomeOnlyEatsAdapter(requireContext(), list, noAddressLong, noAddressLat)
                } else {
                    HomeOnlyEatsAdapter(
                        requireContext(),
                        list,
                        homeAddressResponse.result.nowAddress.addressLongitude,
                        homeAddressResponse.result.nowAddress.addressLatitude
                    )
                }
            }

            with(rcvHomeList) {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                val list = mutableListOf<HomeStoreInfoResponse>()
                for (data in response.result.getStoreHomeRes.indices) {
                    //????????? 10km ?????? ?????????
                    if (response.result.getStoreHomeRes[data].storeInfo.distance < 5.0) {
                        list.add(response.result.getStoreHomeRes[data])
                    }
                }
                //???????????? ?????? ???????????? ????????????.
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
        //?????? ??????
        dismissLoadingDialog()
    }

    override fun getAddressSuccess(data: HomeAddressResponse) {
        //?????? ??????
        dismissLoadingDialog()
        addressDataCheck = true
        homeAddressResponse = data
        //????????? ????????? ?????????? -> ????????? ?????? ????????? ??????(?????? ?????????. ????????? ?????? ??? ??????)
        if (homeAddressResponse.result.nowAddress.address == "") {
            noUserLoginLocation()
        }
        //????????? ????????? ?????????? -> ????????? ?????? ??????
        else {
            HomeStoreService(this).tryGetSortStore(
                data.result.nowAddress.addressLongitude,
                data.result.nowAddress.addressLatitude,
                0
            )
            binding.ivTitleStart.setImageResource(R.drawable.ic_location)

            with(homeAddressResponse.result) {
                //?????? ????????? ?????? ???.
                when {
                    companyAddress.userAddressIdx == nowAddress.userAddressIdx -> {
                        binding.tvAppbarTitle.text = "??????"
                    }
                    homeAddress.userAddressIdx == nowAddress.userAddressIdx -> {
                        binding.tvAppbarTitle.text = "???"
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
        //?????? ??????
        dismissLoadingDialog()
    }

    override fun onResume() {
        super.onResume()
        //????????? ?????? ??????
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
                "??????????????? ?????? ??? ${ApplicationClass.DEC.format((response.result.totalPrice * 0.005).toInt())} ??? ??????"
            binding.tvTotalPrice.text =
                "${ApplicationClass.DEC.format(response.result.totalPrice)}???"
        } else {
            binding.lyCartPrice.visibility = View.GONE
        }

    }

    override fun onGetPostStoreInfoMenuCartFailure(error: String) {

    }

    override fun onGetAutoLoginSuccess(response: AutoLoginResponse) {
        //??? ?????? ??????. ????????? ??????
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