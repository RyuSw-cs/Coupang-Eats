package com.softsquared.template.kotlin.src.main.storeInfo.menu

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.config.BaseActivity
import com.softsquared.template.kotlin.databinding.ActivityStoreMenuInfoBinding
import com.softsquared.template.kotlin.src.main.login.LoginActivity
import com.softsquared.template.kotlin.src.main.storeInfo.menu.adapter.StoreInfoMenuAdapter
import com.softsquared.template.kotlin.src.main.storeInfo.menu.adapter.StoreInfoMenuBannerAdapter
import com.softsquared.template.kotlin.src.main.storeInfo.menu.cart.StoreInfoMenuCartService
import com.softsquared.template.kotlin.src.main.storeInfo.menu.cart.StoreInfoMenuCartView
import com.softsquared.template.kotlin.src.main.storeInfo.menu.cart.models.StoreInfoMenuCartGetResponse
import com.softsquared.template.kotlin.src.main.storeInfo.menu.cart.models.StoreInfoMenuCartPostResponse
import com.softsquared.template.kotlin.src.main.storeInfo.menu.models.StoreInfoMenuResponse

class StoreInfoMenuDetailActivity :
    BaseActivity<ActivityStoreMenuInfoBinding>(ActivityStoreMenuInfoBinding::inflate),
    StoreInfoMenuView, StoreInfoMenuCartView {

    var bannerPosition = 0
    lateinit var bannerHandler: Handler
    lateinit var sliderRunnable: Runnable
    lateinit var data: StoreInfoMenuResponse
    var changePrice = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    fun init() {
        showLoadingDialog(this)
        StoreInfoMenuService(this).tryGetMenu(
            intent.getIntExtra("storeIdx", 0),
            intent.getIntExtra("menuIdx", 0)
        )

        binding.btApply.bringToFront()

        StoreInfoMenuCartService(this).tryGetCart()

        binding.btApply.setOnClickListener {
            if (ApplicationClass.X_ACCESS_TOKEN == "TOKEN") {
                startActivity(Intent(this, LoginActivity::class.java))
            } else {
                var content = ""
                val iterator = ApplicationClass.CART.iterator()
                while (iterator.hasNext()) {
                    content += iterator.next() + ", "
                }
                if (content != "") {
                    content = content.substring(0, content.length - 2)
                }
                StoreInfoMenuCartService(this).tryPostCart(
                    storeIdx = intent.getIntExtra("storeIdx", 0),
                    menuIdx = intent.getIntExtra("menuIdx", 0),
                    content,
                    orderCount = binding.tvCount.text.toString().toInt(),
                    orderPrice = data.result.menuPrice
                )
                ApplicationClass.CART.clear()
            }
        }
    }

    override fun onGetStoreInfoMenuSuccess(response: StoreInfoMenuResponse) {
        dismissLoadingDialog()
        data = response
        changePrice = data.result.menuPrice
        //????????? ????????? ?????? ?????? ?????? ?????????.
        with(binding) {
            tvMenuTitle.text = response.result.menuName
            tvMenuContent.text = response.result.menuDetail
            tvPrice.text = "${ApplicationClass.DEC.format(response.result.menuPrice)}???"

            rcvFoodList.layoutManager = LinearLayoutManager(
                this@StoreInfoMenuDetailActivity,
                LinearLayoutManager.VERTICAL,
                false
            )
            rcvFoodList.adapter = StoreInfoMenuAdapter(this@StoreInfoMenuDetailActivity, response)

            /* ???????????? ?????? */
            btMinus.setOnClickListener {
                if (tvCount.text.toString().toInt() == 1) {
                    /* background ?????? */
                    btMinus.isEnabled = false
                } else {
                    changePrice -= data.result.menuPrice
                    tvPrice.text = "${ApplicationClass.DEC.format(changePrice)}???"
                    tvCount.text = "${tvCount.text.toString().toInt() - 1}"
                }
            }

            /* ????????? ?????? */
            btPlus.setOnClickListener {
                tvCount.text = "${tvCount.text.toString().toInt() + 1}"
                changePrice += data.result.menuPrice
                tvPrice.text = "${ApplicationClass.DEC.format(changePrice)}???"
                btMinus.isEnabled = true
            }

            //????????? ??????????
            when {
                response.result.menuImgUrl.isEmpty() -> {
                    tvBannerCount.visibility = View.GONE
                    vp2Banner.visibility = View.GONE
                }
                response.result.menuImgUrl.size == 1 -> {
                    vp2Banner.adapter =
                        StoreInfoMenuBannerAdapter(
                            this@StoreInfoMenuDetailActivity,
                            response.result
                        )
                }
                else -> {
                    vp2Banner.adapter =
                        StoreInfoMenuBannerAdapter(
                            this@StoreInfoMenuDetailActivity,
                            response.result
                        )
                    sliderRunnable = Runnable {
                        vp2Banner.setCurrentItem(++bannerPosition, true)
                    }
                    bannerHandler = Handler(Looper.getMainLooper())

                    vp2Banner.registerOnPageChangeCallback(object :
                        ViewPager2.OnPageChangeCallback() {
                        override fun onPageSelected(position: Int) {
                            binding.tvBannerCount.text =
                                "${(position % response.result.menuImgUrl.size) + 1}/${response.result.menuImgUrl.size}"
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

                                //????????? ???
                                ViewPager2.SCROLL_STATE_SETTLING -> {
                                    bannerHandler.removeCallbacks(sliderRunnable)
                                }
                            }
                        }
                    })
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onGetStoreInfoMenuFailure(error: String) {
        dismissLoadingDialog()
    }

    override fun onPostStoreInfoMenuCartSuccess(postResponse: StoreInfoMenuCartPostResponse) {
        showCustomToast("????????? ????????? ???????????????.")
        finish()
    }

    override fun onPostStoreInfoMenuCartFailure(error: String) {
        if (error == "JWT??? ??????????????????.") {
            return
        }
        var content = ""
        var iterator = ApplicationClass.CART.iterator()
        while (iterator.hasNext()) {
            content += iterator.next() + ","
        }
        //?????? ?????? ??? ???????????? ????????????.
        val pDialog = AlertDialog.Builder(this)
        /* ????????? ??????????????? */
        pDialog.setTitle("?????? ????????? ????????? ?????? ??? ????????????.")
        pDialog.setMessage("????????? ????????? ???????????? ?????? ????????? ?????? ????????? ???????????????.")
        pDialog.setPositiveButton("??????") { dialog, _ ->
            StoreInfoMenuCartService(this).tryPostNewCart(
                storeIdx = intent.getIntExtra("storeIdx", 0),
                menuIdx = intent.getIntExtra("menuIdx", 0),
                content,
                orderCount = binding.tvCount.text.toString().toInt(),
                orderPrice = data.result.menuPrice
            )
            dialog.dismiss()
        }
        pDialog.setNegativeButton("??????") { dialog, _ ->
            dialog.dismiss()
        }
        pDialog.show()
    }

    override fun onPostStoreInfoMenuNewCartSuccess(postResponse: StoreInfoMenuCartPostResponse) {
        finish()
    }

    override fun onPostStoreInfoMenuNewCartFailure(error: String) {
        showCustomToast("????????? ?????? ??????")
    }

    override fun onGetStoreInfoMenuCartSuccess(response: StoreInfoMenuCartGetResponse) {
        //??????
        if (response.result.storeIdx != 0) {
            binding.btApply.visibility = View.GONE
            binding.tvSeeCart.text = "?????? ????????? ??????"
            binding.lyCartPrice.visibility = View.VISIBLE
            binding.tvCartCount.text = response.result.cartMenu.size.toString()
            binding.tvDiscountPrice.text =
                "??????????????? ?????? ??? ${ApplicationClass.DEC.format((response.result.totalPrice * 0.005).toInt())} ??? ??????"
            binding.tvTotalPrice.text =
                "${ApplicationClass.DEC.format(response.result.totalPrice)}???"

            binding.lyCartPrice.setOnClickListener {
                if (ApplicationClass.X_ACCESS_TOKEN == "TOKEN") {
                    startActivity(Intent(this, LoginActivity::class.java))
                } else {
                    var content = ""
                    var iterator = ApplicationClass.CART.iterator()
                    while (iterator.hasNext()) {
                        content += iterator.next() + ", "
                    }
                    if (content != "") {
                        content = content.substring(0, content.length - 2)
                    }
                    StoreInfoMenuCartService(this).tryPostCart(
                        storeIdx = intent.getIntExtra("storeIdx", 0),
                        menuIdx = intent.getIntExtra("menuIdx", 0),
                        content,
                        orderCount = binding.tvCount.text.toString().toInt(),
                        orderPrice = data.result.menuPrice
                    )
                    ApplicationClass.CART.clear()
                }
            }
        }
    }

    override fun onGetPostStoreInfoMenuCartFailure(error: String) {

    }


    fun changePrice(addPrice: Int) {
        //????????? ?????? ?????? ??????
        data.result.menuPrice += (addPrice * binding.tvCount.text.toString().toInt())
        changePrice += (addPrice * binding.tvCount.text.toString().toInt())
        binding.tvPrice.text = "${ApplicationClass.DEC.format(changePrice)}???"
    }
}