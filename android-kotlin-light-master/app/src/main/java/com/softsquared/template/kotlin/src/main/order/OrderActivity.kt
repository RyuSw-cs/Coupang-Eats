package com.softsquared.template.kotlin.src.main.order

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.config.BaseActivity
import com.softsquared.template.kotlin.databinding.ActivityOrderBinding
import com.softsquared.template.kotlin.src.main.MainActivity
import com.softsquared.template.kotlin.src.main.delivery.DeliveryActivity
import com.softsquared.template.kotlin.src.main.home.models.rising.address.models.HomeAddressResponse
import com.softsquared.template.kotlin.src.main.order.adapter.OrderFoodAdapter
import com.softsquared.template.kotlin.src.main.order.adapter.OrderRecommendAdapter
import com.softsquared.template.kotlin.src.main.order.dialog.OrderCountDialog
import com.softsquared.template.kotlin.src.main.order.dialog.OrderOverCountDialog
import com.softsquared.template.kotlin.src.main.order.service.address.OrderAddressView
import com.softsquared.template.kotlin.src.main.order.service.cart.OrderCartService
import com.softsquared.template.kotlin.src.main.order.service.cart.OrderCartView
import com.softsquared.template.kotlin.src.main.order.service.cart.models.OrderCartDeleteResponse
import com.softsquared.template.kotlin.src.main.order.service.menu.OrderMenuService
import com.softsquared.template.kotlin.src.main.order.service.menu.OrderMenuView
import com.softsquared.template.kotlin.src.main.order.models.OrderDeliveryResponse
import com.softsquared.template.kotlin.src.main.order.models.OrderModifyResponse
import com.softsquared.template.kotlin.src.main.order.orderType.OrderDeliveryFragment
import com.softsquared.template.kotlin.src.main.order.orderType.OrderTakeOutFragment
import com.softsquared.template.kotlin.src.main.storeInfo.StoreInfoActivity
import com.softsquared.template.kotlin.src.main.storeInfo.menu.cart.models.StoreInfoMenuCartGetResponse
import com.softsquared.template.kotlin.src.main.storeInfo.menu.cart.models.StoreInfoMenuCartPostResponse
import com.softsquared.template.kotlin.src.main.storeInfo.models.StoreInfoMenuDetailResponse
import com.softsquared.template.kotlin.src.main.storeInfo.models.StoreInfoResponse

class OrderActivity : BaseActivity<ActivityOrderBinding>(ActivityOrderBinding::inflate),
    OrderAddressView, OrderCartView, OrderMenuView, OrderActivityView {

    var storeIdx = 0
    var cartIdx = 0
    lateinit var recommendData: MutableList<StoreInfoMenuDetailResponse>
    var deliveryCheck = false
    var isSpoon = false
    lateinit var data: StoreInfoMenuCartGetResponse
    lateinit var resultLauncherActivityInfo: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    fun init() {
        binding.ivCancel.setOnClickListener {
            finish()
        }
        resultLauncherActivityInfo =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = result.data
                    //수량 변경 하면됨.
                    OrderActivityService(this).tryPutOrder(
                        data?.getStringExtra("count")?.toInt()!!,
                        storeIdx,
                        cartIdx
                    )
                }
            }
    }

    override fun onGetOrderAddressSuccess(response: HomeAddressResponse) {

    }

    override fun onGetOrderAddressFailure(error: String) {

    }

    override fun onGetOrderCartSuccess(response: StoreInfoMenuCartGetResponse) {
        //카트 성공
        data = response

        if (response.result.storeIdx == 0) {
            binding.lyNoCart.visibility = View.VISIBLE
            binding.nsvMain.visibility = View.GONE
            binding.btOrder.visibility = View.GONE

            binding.btGoHome.setOnClickListener {
                finishAffinity()
                startActivity(Intent(this, MainActivity::class.java))
            }
        } else {

            binding.lyOrderTab.removeAllTabs()
            storeIdx = response.result.storeIdx

            if (response.result.totalPrice > response.result.minimumPrice) {
                binding.btOrder.text =
                    "배달주문 ${ApplicationClass.DEC.format(response.result.totalPrice)}원 결제하기"
            } else {
                binding.btOrder.text =
                    "${ApplicationClass.DEC.format(response.result.minimumPrice)}원 이상 주문 가능"
            }

            binding.cbRecycle.setOnCheckedChangeListener { _, isChecked ->
                isSpoon = isChecked
            }

            binding.btOrder.setOnClickListener {
                //최소 조건
                if (response.result.totalPrice < response.result.minimumPrice) {
                    noLeastPriceDialog(response.result.minimumPrice, response)
                } else {
                    /* 주문 */
                    val recycle = when (isSpoon) {
                        true -> "Y"
                        else -> "N"
                    }

                    val cart = mutableListOf<String>()

                    for (idx in response.result.cartMenu) {
                        cart.add(idx.cartIdx.toString())
                    }

                    OrderActivityService(this).tryPostOrder(
                        response.result.nowAddress.userAddressIdx,
                        response.result.storeIdx,
                        0,
                        "맛있게 해주세요.",
                        recycle,
                        8,
                        "빠르게 와주세요.",
                        cart
                    )
                }
            }
            binding.tvOrderPriceContent.text =
                "${ApplicationClass.DEC.format(response.result.totalPrice)}원"
            binding.tvTotalPrice.text =
                "${ApplicationClass.DEC.format(response.result.totalPrice)}원"

            if (response.result.distance > 5.0) {
                if (response.result.timeToGo == "N") {
                    //포장 안됨.
                    binding.lyOrderTab.visibility = View.GONE
                    //그냥 주소지만 보여주면됨.
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.ly_address_result, OrderDeliveryFragment(response.result))
                        .commitAllowingStateLoss()
                } else {
                    binding.lyOrderTab.addTab(
                        binding.lyOrderTab.newTab().setText("배달 현주소 배달불가")
                    )
                    binding.lyOrderTab.addTab(
                        binding.lyOrderTab.newTab().setText("포장 ${response.result.timeToGo}")
                    )
                    noDeliveryDialog()
                    deliveryCheck = false
                }
            } else {
                if (response.result.timeToGo == "N") {
                    //포장 안됨.
                    binding.lyOrderTab.visibility = View.GONE
                    //그냥 주소지만 보여주면됨.
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.ly_address_result, OrderDeliveryFragment(response.result))
                        .commitAllowingStateLoss()
                } else {
                    deliveryCheck = true
                    binding.lyOrderTab.addTab(
                        binding.lyOrderTab.newTab().setText("배달 ${response.result.timeDelivery}")
                    )
                    binding.lyOrderTab.addTab(
                        binding.lyOrderTab.newTab().setText("포장 ${response.result.timeToGo}")
                    )
                }
            }

            supportFragmentManager.beginTransaction()
                .replace(R.id.ly_address_result, OrderDeliveryFragment(response.result))
                .commitAllowingStateLoss()
            binding.lyOrderTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    if (tab?.position == 0) {
                        supportFragmentManager.beginTransaction()
                            .replace(
                                R.id.ly_address_result,
                                OrderDeliveryFragment(response.result)
                            )
                            .commitAllowingStateLoss()
                    } else {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.ly_address_result, OrderTakeOutFragment(response.result))
                            .commitAllowingStateLoss()
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {

                }

                override fun onTabReselected(tab: TabLayout.Tab?) {

                }
            })

            binding.tvOrderPlaceName.text = response.result.storeName
            if (response.result.isCheetah == "N") {
                binding.ivFastDelivery.visibility = View.GONE
            }
            binding.rcvMenuList.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            binding.rcvMenuList.adapter = OrderFoodAdapter(this, response)


            binding.btMoreMenu.setOnClickListener {
                val intent = Intent(this@OrderActivity, StoreInfoActivity::class.java)
                intent.putExtra("longitude", response.result.storeLongitude)
                intent.putExtra("latitude", response.result.storeLatitude)
                intent.putExtra("storeIdx", response.result.storeIdx)
                startActivity(intent)
            }
            /* 위치 수정 후 적용 */
            OrderMenuService(this).tryGetMenu(
                response.result.storeLongitude,
                response.result.storeLatitude,
                response.result.storeIdx
            )
        }
    }


    override fun onGetOrderCartFailure(error: String) {
        showCustomToast("카트 정보 실패")
    }

    override fun onGetOrderMenuSuccess(response: StoreInfoResponse) {
        binding.rcvOrderTogether.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val noOptionList = mutableListOf<StoreInfoMenuDetailResponse>()
        for (mainIdx in response.result.menuCategory.indices) {
            for (subIdx in response.result.menuCategory[mainIdx].menuDetail.indices) {
                if (response.result.menuCategory[mainIdx].menuDetail[subIdx].isOption == "N") {
                    noOptionList.add(response.result.menuCategory[mainIdx].menuDetail[subIdx])
                }
            }
        }
        if (!::recommendData.isInitialized) {
            recommendData = noOptionList
        }
        binding.rcvOrderTogether.adapter = OrderRecommendAdapter(this, recommendData)
    }


    override fun onGetOrderMenuFailure(error: String) {

    }

    override fun onPostOrderMenuSuccess(response: StoreInfoMenuCartPostResponse) {
        OrderCartService(this).tryGetCart()
    }

    override fun onPostOrderMenuFailure(error: String) {
        showCustomToast(error)
    }

    override fun onDeleteOrderMenuSuccess(response: OrderCartDeleteResponse) {
        OrderCartService(this).tryGetCart()
    }

    override fun onDeleteOrderMenuFailure(error: String) {
        showCustomToast(error)
    }

    override fun onResume() {
        super.onResume()
        OrderCartService(this).tryGetCart()
    }

    fun addRecommendFood(
        orderPrice: Int,
        menuIdx: Int,
        data: MutableList<StoreInfoMenuDetailResponse>
    ) {
        //음식 추가
        OrderCartService(this).tryPostCartRecommendMenu("", 1, orderPrice, storeIdx, menuIdx)
        recommendData = data
    }

    fun removeRcv() {
        binding.lyOrderTogether.visibility = View.GONE
        binding.divOrderTogether.visibility = View.GONE
        binding.tvOrderTogether.visibility = View.GONE
    }

    fun removeCartItem(cartIdx: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("선택하신 메뉴를 삭제하시겠습니까?")
        builder.setPositiveButton("삭제") { dialog, _ ->
            dialog.dismiss()
            OrderCartService(this).tryDeleteCart(cartIdx)
        }
        builder.setNegativeButton("취소") { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }

    private fun noLeastPriceDialog(minimumPrice: Int, response: StoreInfoMenuCartGetResponse) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("최소 주문 금액 ${ApplicationClass.DEC.format(minimumPrice)}원 이상 주문 가능합니다.\n메뉴 추가 후 다시 주문해주세요.")
        builder.setPositiveButton("확인") { dialog, _ ->
            dialog.dismiss()
            val intent = Intent(this@OrderActivity, StoreInfoActivity::class.java)
            intent.putExtra("longitude", response.result.storeLongitude)
            intent.putExtra("latitude", response.result.storeLatitude)
            intent.putExtra("storeIdx", response.result.storeIdx)
            startActivity(intent)
        }
        builder.show()
    }

    private fun noDeliveryDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("현재 주소로 배달할 수 없습니다. 포장주문을 이용해보세요")
        builder.setPositiveButton("확인") { dialog, _ -> dialog.dismiss() }
        builder.show()
    }

    override fun onPostOrderSuccess(response: OrderDeliveryResponse) {
        finishAffinity()
        val homeIntent = Intent(this, MainActivity::class.java)
        val intent = Intent(this, DeliveryActivity::class.java)
        intent.putExtra("userLongitude", data.result.nowAddress.addressLongitude)
        intent.putExtra("userLatitude", data.result.nowAddress.addressLatitude)
        intent.putExtra("storeLongitude", data.result.storeLongitude)
        intent.putExtra("storeLatitude", data.result.storeLatitude)
        intent.putExtra("cartInfo", data)
        intent.putExtra("orderIdx", response.result.userOrderIdx)
        startActivity(homeIntent)
        startActivity(intent)
        /* 주문창 표시 */
    }

    override fun onPostOrderFailure(error: String) {
        //실패
    }

    override fun onPutOrderSuccess(response: OrderModifyResponse) {
        //다시 통신
        OrderCartService(this).tryGetCart()
    }

    override fun onPutOrderFailure(error: String) {

    }

    fun showChangeOrderCountDialog(count: Int, cartIdx: Int) {
        this.cartIdx = cartIdx
        when (count) {
            in 1..9 -> {
                val intent = Intent(this, OrderCountDialog::class.java)
                intent.putExtra("count", count)
                resultLauncherActivityInfo.launch(intent)
            }
            else -> {
                val intent = Intent(this, OrderOverCountDialog::class.java)
                intent.putExtra("count", count)
                resultLauncherActivityInfo.launch(intent)
            }
        }
    }

}