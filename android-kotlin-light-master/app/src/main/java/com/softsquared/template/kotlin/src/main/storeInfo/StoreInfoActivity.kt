package com.softsquared.template.kotlin.src.main.storeInfo

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.config.BaseActivity
import com.softsquared.template.kotlin.databinding.ActivityStoreInfoBinding
import com.softsquared.template.kotlin.src.main.favorite.models.FavoritePostResponse
import com.softsquared.template.kotlin.src.main.favorite.models.FavoriteResponse
import com.softsquared.template.kotlin.src.main.order.OrderActivity
import com.softsquared.template.kotlin.src.main.review.ReviewActivity
import com.softsquared.template.kotlin.src.main.storeInfo.adapter.StoreInfoDetailBannerAdapter
import com.softsquared.template.kotlin.src.main.storeInfo.adapter.StoreInfoListAdapter
import com.softsquared.template.kotlin.src.main.storeInfo.favorite.FavoriteStoreInfoService
import com.softsquared.template.kotlin.src.main.storeInfo.favorite.FavoriteStoreInfoView
import com.softsquared.template.kotlin.src.main.storeInfo.menu.cart.StoreInfoMenuCartService
import com.softsquared.template.kotlin.src.main.storeInfo.menu.cart.StoreInfoMenuCartView
import com.softsquared.template.kotlin.src.main.storeInfo.menu.cart.models.StoreInfoMenuCartGetResponse
import com.softsquared.template.kotlin.src.main.storeInfo.menu.cart.models.StoreInfoMenuCartPostResponse
import com.softsquared.template.kotlin.src.main.storeInfo.models.StoreInfoMenuCategoryResponse
import com.softsquared.template.kotlin.src.main.storeInfo.models.StoreInfoResponse
import com.softsquared.template.kotlin.util.changeStatusBar

class StoreInfoActivity : BaseActivity<ActivityStoreInfoBinding>(ActivityStoreInfoBinding::inflate),
    StoreInfoView, StoreInfoMenuCartView, FavoriteStoreInfoView {

    lateinit var storeInfo: List<StoreInfoMenuCategoryResponse>
    var bannerPosition = 0
    var storeIdx = 0
    var toolbarCheck = false
    lateinit var bannerHandler: Handler
    lateinit var sliderRunnable: Runnable
    var storeFavoriteCheck = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        showLoadingDialog(this)
        //????????????, ????????? ????????????.
        StoreInfoService(this).tryGetStoreInfo(
            intent.getDoubleExtra("longitude", 0.0),
            intent.getDoubleExtra("latitude", 0.0),
            intent.getIntExtra("storeIdx", 0)
        )
    }

    fun init() {
        with(binding) {
            lyCartPrice.bringToFront()
            binding.lyCartPrice.setOnClickListener {
                this@StoreInfoActivity.startActivity(
                    Intent(
                        this@StoreInfoActivity,
                        OrderActivity::class.java
                    )
                )
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
            R.id.it_toolbar_like -> {
                if (storeFavoriteCheck == "N") {
                    FavoriteStoreInfoService(this).tryPostFavorite(storeIdx)
                    if (toolbarCheck) {
                        item.setIcon(R.drawable.ic_food_info_like_flip)
                    } else {
                        item.setIcon(R.drawable.ic_food_info_like)
                    }

                } else {
                    var list = listOf<String>(storeIdx.toString())
                    FavoriteStoreInfoService(this).tryDeleteFavorite(list)

                    if (toolbarCheck) {
                        item.setIcon(R.drawable.ic_food_info_non_like)
                    } else {
                        item.setIcon(R.drawable.ic_food_info_non_like_flip)
                    }
                }
            }
            R.id.it_toolbar_share -> {
                Log.d("??????", "??????")
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        if (toolbarCheck) {
            if (storeFavoriteCheck == "Y") {
                inflater.inflate(R.menu.menu_food_info_toolbar_collapse_like, menu)
            } else {
                inflater.inflate(R.menu.menu_food_info_toolbar_collapse, menu)
            }

        } else {
            if (storeFavoriteCheck == "Y") {
                inflater.inflate(R.menu.menu_food_info_toolbar_expanded_like, menu)
            } else {
                inflater.inflate(R.menu.menu_food_info_toolbar_expanded, menu)
            }
        }
        return true
    }


    override fun onGetStoreInfoSuccess(response: StoreInfoResponse) {
        dismissLoadingDialog()

        storeFavoriteCheck = response.result.isFavoriteStore
        storeIdx = response.result.storeIdx

        binding.tvRate.setOnClickListener {
            val intent = Intent(this@StoreInfoActivity, ReviewActivity::class.java)
            intent.putExtra("storeIdx", response.result.storeIdx)
            intent.putExtra("totalRate", response.result.reviewScore)
            intent.putExtra("storeName", response.result.storeName)
            startActivity(intent)
        }

        storeInfo = response.result.menuCategory
        //?????????????????? ??????
        with(binding) {
            rcvFoodList.layoutManager =
                LinearLayoutManager(this@StoreInfoActivity, LinearLayoutManager.VERTICAL, false)
            rcvFoodList.adapter = StoreInfoListAdapter(response.result.storeIdx, storeInfo)

            //?????? ????????? ?????? visibility ??????
            if (response.result.reviewCount == 0) {
                binding.lyStoreRate.visibility = View.GONE
            }

            tbToolbar.title = ""
            setSupportActionBar(tbToolbar)
            tbToolbar.showOverflowMenu()
            toolbarCheck = false
            changeStatusBar(toolbarCheck)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)

            //????????? ????????? ??????
            svNested.setOnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
                //?????????
                if (tbToolbar.height in (oldScrollY + 1)..scrollY) {
                    tbToolbar.title = response.result.storeName
                    tbToolbar.setTitleTextColor(Color.BLACK)
                    setSupportActionBar(tbToolbar)
                    tbToolbar.showOverflowMenu()
                    toolbarCheck = true
                    changeStatusBar(toolbarCheck)
                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
                }
                //?????????
                else if (tbToolbar.height in (scrollY + 1)..oldScrollY) {
                    tbToolbar.title = ""
                    setSupportActionBar(tbToolbar)
                    tbToolbar.showOverflowMenu()
                    toolbarCheck = false
                    changeStatusBar(toolbarCheck)
                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
                }
            }

            //???????????? ??????
            tvStoreTitle.text = response.result.storeName
            if (response.result.isCheetah == "N") {
                ivFastDelivery.visibility = View.GONE
            }

            //?????? ??????
            tvRate.text = "${response.result.reviewScore}(${response.result.reviewCount})"

            //????????? ??????????
            if (response.result.isToGo == "N") {
                lyStoreTab.visibility = View.GONE
                supportFragmentManager.beginTransaction()
                    .replace(R.id.ly_store_delivery, StoreInfoDeliveryFragment(response))
                    .commitAllowingStateLoss()
            } else {
                //??????????????? ?????????
                lyStoreTab.addTab(lyStoreTab.newTab().setText("?????? ${response.result.timeDelivery}"))
                lyStoreTab.addTab(lyStoreTab.newTab().setText("?????? ${response.result.timeToGo}"))
                supportFragmentManager.beginTransaction()
                    .replace(R.id.ly_store_delivery, StoreInfoDeliveryFragment(response))
                    .commitAllowingStateLoss()
                lyStoreTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                    override fun onTabSelected(tab: TabLayout.Tab?) {
                        if (tab?.position == 0) {
                            supportFragmentManager.beginTransaction()
                                .replace(
                                    R.id.ly_store_delivery,
                                    StoreInfoDeliveryFragment(response)
                                )
                                .commitAllowingStateLoss()
                        } else {
                            supportFragmentManager.beginTransaction()
                                .replace(
                                    R.id.ly_store_delivery, StoreInfoTakeOutFragment(
                                        response,
                                        intent.getDoubleExtra("longitude", 0.0),
                                        intent.getDoubleExtra("latitude", 0.0),
                                        response.result.storeLongitude,
                                        response.result.storeLatitude
                                    )
                                )
                                .commitAllowingStateLoss()
                        }
                    }

                    override fun onTabUnselected(tab: TabLayout.Tab?) {

                    }

                    override fun onTabReselected(tab: TabLayout.Tab?) {

                    }
                })
            }

            //??????????????? ??? ???????????? ??????

            for (idx in response.result.menuCategory) {
                lyFoodCategory.addTab(lyFoodCategory.newTab().setText(idx.categoryName))
            }

            /* ???????????? ?????? ??? ????????? ????????? */
            lyFoodCategory.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    //?????? ??????????????? ??????.

                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {

                }

                override fun onTabReselected(tab: TabLayout.Tab?) {

                }
            })

            /* ?????? ?????? ??? ???????????? ????????? */
            lyStoreRate.setOnClickListener {

            }

            //?????? ????????? ????????? ??????
            if (response.result.storeImgUrl.size == 1) {
                tvBannerCount.visibility = View.GONE
                vp2Banner.visibility = View.GONE
            } else {
                //????????? ?????????
                vp2Banner.adapter =
                    StoreInfoDetailBannerAdapter(
                        this@StoreInfoActivity,
                        response.result.storeImgUrl
                    )
                //?????? ?????? ?????? ????????????
                sliderRunnable = Runnable {
                    vp2Banner.setCurrentItem(++bannerPosition, true)
                }
                bannerHandler = Handler(Looper.getMainLooper())

                vp2Banner.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        binding.tvBannerCount.text =
                            "${(position % response.result.storeImgUrl.size) + 1}/${response.result.storeImgUrl.size}"
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
            }
        }
    }

    override fun onGetStoreInfoFailure(error: String) {
        dismissLoadingDialog()
        showCustomToast("?????? ?????? ?????? ??????")
        finish()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        //?????? ????????? ??????????
        StoreInfoMenuCartService(this).tryGetCart()
    }

    override fun onPostStoreInfoMenuCartSuccess(postResponse: StoreInfoMenuCartPostResponse) {

    }

    override fun onPostStoreInfoMenuCartFailure(error: String) {

    }

    override fun onPostStoreInfoMenuNewCartSuccess(postResponse: StoreInfoMenuCartPostResponse) {

    }

    override fun onPostStoreInfoMenuNewCartFailure(error: String) {

    }

    override fun onGetStoreInfoMenuCartSuccess(response: StoreInfoMenuCartGetResponse) {
        //????????? ???????????? ??????!
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

    override fun onPostFavoriteSuccess(response: FavoritePostResponse) {
        //???????????? ??????
        StoreInfoService(this).tryGetStoreInfo(
            intent.getDoubleExtra("longitude", 0.0),
            intent.getDoubleExtra("latitude", 0.0),
            intent.getIntExtra("storeIdx", 0)
        )
    }

    override fun onPostFavoriteFailure(error: String) {

    }

    override fun onDeleteFavoriteSuccess(response: FavoritePostResponse) {
        StoreInfoService(this).tryGetStoreInfo(
            intent.getDoubleExtra("longitude", 0.0),
            intent.getDoubleExtra("latitude", 0.0),
            intent.getIntExtra("storeIdx", 0)
        )
    }

    override fun onDeleteFavoriteFailure(error: String) {

    }
}