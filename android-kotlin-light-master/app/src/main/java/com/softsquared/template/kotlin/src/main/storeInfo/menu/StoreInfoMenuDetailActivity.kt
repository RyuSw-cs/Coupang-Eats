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
        //이미지 개수에 따른 배너 설정 다르게.
        with(binding) {
            tvMenuTitle.text = response.result.menuName
            tvMenuContent.text = response.result.menuDetail
            tvPrice.text = "${ApplicationClass.DEC.format(response.result.menuPrice)}원"

            rcvFoodList.layoutManager = LinearLayoutManager(
                this@StoreInfoMenuDetailActivity,
                LinearLayoutManager.VERTICAL,
                false
            )
            rcvFoodList.adapter = StoreInfoMenuAdapter(this@StoreInfoMenuDetailActivity, response)

            /* 마이너스 버튼 */
            btMinus.setOnClickListener {
                if (tvCount.text.toString().toInt() == 1) {
                    /* background 변경 */
                    btMinus.isEnabled = false
                } else {
                    changePrice -= data.result.menuPrice
                    tvPrice.text = "${ApplicationClass.DEC.format(changePrice)}원"
                    tvCount.text = "${tvCount.text.toString().toInt() - 1}"
                }
            }

            /* 플러스 버튼 */
            btPlus.setOnClickListener {
                tvCount.text = "${tvCount.text.toString().toInt() + 1}"
                changePrice += data.result.menuPrice
                tvPrice.text = "${ApplicationClass.DEC.format(changePrice)}원"
                btMinus.isEnabled = true
            }

            //사진이 없다면?
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
                                //멈춤
                                ViewPager2.SCROLL_STATE_IDLE -> {
                                    bannerHandler.postDelayed(sliderRunnable, 2000)
                                }

                                //드래그
                                ViewPager2.SCROLL_STATE_DRAGGING -> {
                                    bannerHandler.removeCallbacks(sliderRunnable)
                                }

                                //끝까지 감
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
        showCustomToast("카트에 음식이 담겼습니다.")
        finish()
    }

    override fun onPostStoreInfoMenuCartFailure(error: String) {
        if (error == "JWT를 입력해주세요.") {
            return
        }
        var content = ""
        var iterator = ApplicationClass.CART.iterator()
        while (iterator.hasNext()) {
            content += iterator.next() + ","
        }
        //다른 음식 할 것이냐고 물어보기.
        val pDialog = AlertDialog.Builder(this)
        /* 메세지 변경해야함 */
        pDialog.setTitle("같은 가게의 메뉴만 담을 수 있습니다.")
        pDialog.setMessage("주문할 가게를 변경하실 경우 이전에 담은 메뉴가 삭제됩니다.")
        pDialog.setPositiveButton("변경") { dialog, _ ->
            StoreInfoMenuCartService(this).tryPostNewCart(
                storeIdx = intent.getIntExtra("storeIdx", 0),
                menuIdx = intent.getIntExtra("menuIdx", 0),
                content,
                orderCount = binding.tvCount.text.toString().toInt(),
                orderPrice = data.result.menuPrice
            )
            dialog.dismiss()
        }
        pDialog.setNegativeButton("취소") { dialog, _ ->
            dialog.dismiss()
        }
        pDialog.show()
    }

    override fun onPostStoreInfoMenuNewCartSuccess(postResponse: StoreInfoMenuCartPostResponse) {
        finish()
    }

    override fun onPostStoreInfoMenuNewCartFailure(error: String) {
        showCustomToast("음식점 변경 실패")
    }

    override fun onGetStoreInfoMenuCartSuccess(response: StoreInfoMenuCartGetResponse) {
        //성공
        if (response.result.storeIdx != 0) {
            binding.btApply.visibility = View.GONE
            binding.tvSeeCart.text = "배달 카트에 담기"
            binding.lyCartPrice.visibility = View.VISIBLE
            binding.tvCartCount.text = response.result.cartMenu.size.toString()
            binding.tvDiscountPrice.text =
                "쿠페이머니 결제 시 ${ApplicationClass.DEC.format((response.result.totalPrice * 0.005).toInt())} 원 적립"
            binding.tvTotalPrice.text =
                "${ApplicationClass.DEC.format(response.result.totalPrice)}원"

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
        //옵션에 따른 가격 변동
        data.result.menuPrice += (addPrice * binding.tvCount.text.toString().toInt())
        changePrice += (addPrice * binding.tvCount.text.toString().toInt())
        binding.tvPrice.text = "${ApplicationClass.DEC.format(changePrice)}원"
    }
}