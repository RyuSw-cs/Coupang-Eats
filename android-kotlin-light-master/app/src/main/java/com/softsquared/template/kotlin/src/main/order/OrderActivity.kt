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
    var deliveryCost = 0
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
                    //?????? ?????? ?????????.
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
        //?????? ??????
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


            //????????? ??????
            for (idx in data.result.deliveryFeeList.indices) {
                if (data.result.totalPrice < data.result.minimumPrice) {
                    break
                }
                if (data.result.totalPrice >= data.result.deliveryFeeList[idx].minPrice) {
                    deliveryCost = data.result.deliveryFeeList[idx].deliveryFee
                } else {
                    continue
                }
            }

            if (deliveryCost == -1) {
                deliveryCost =
                    data.result.deliveryFeeList[data.result.deliveryFeeList.size - 1].deliveryFee
            }

            if (response.result.totalPrice > response.result.minimumPrice) {
                binding.btOrder.text =
                    "???????????? ${ApplicationClass.DEC.format(response.result.totalPrice + deliveryCost)}??? ????????????"
            } else {
                binding.btOrder.text =
                    "${ApplicationClass.DEC.format(response.result.minimumPrice)}??? ?????? ?????? ??????"
            }

            binding.cbRecycle.setOnCheckedChangeListener { _, isChecked ->
                isSpoon = isChecked
            }

            binding.tvDeliveryFee.text = "+${ApplicationClass.DEC.format(deliveryCost)}???"

            binding.btOrder.setOnClickListener {
                //?????? ??????
                if (response.result.totalPrice < response.result.minimumPrice) {
                    noLeastPriceDialog(response.result.minimumPrice, response)
                } else {
                    /* ?????? */
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
                        "????????? ????????????.",
                        recycle,
                        8,
                        "????????? ????????????.",
                        cart,
                        deliveryCost
                    )
                }
            }
            binding.tvOrderPriceContent.text =
                "${ApplicationClass.DEC.format(response.result.totalPrice)}???"

            binding.tvTotalPrice.text =
                "${ApplicationClass.DEC.format(response.result.totalPrice + deliveryCost)}???"

            if (response.result.distance > 5.0) {
                noDeliveryDialog()
                if (response.result.timeToGo == "N") {
                    //?????? ??????.
                    binding.lyOrderTab.visibility = View.GONE
                    //?????? ???????????? ???????????????.
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.ly_address_result, OrderDeliveryFragment(response.result))
                        .commitAllowingStateLoss()
                } else {
                    binding.lyOrderTab.addTab(
                        binding.lyOrderTab.newTab().setText("?????? ????????? ????????????")
                    )
                    binding.lyOrderTab.addTab(
                        binding.lyOrderTab.newTab().setText("?????? ${response.result.timeToGo}")
                    )
                    deliveryCheck = false
                }
            } else {
                if (response.result.timeToGo == "N") {
                    //?????? ??????.
                    binding.lyOrderTab.visibility = View.GONE
                    //?????? ???????????? ???????????????.
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.ly_address_result, OrderDeliveryFragment(response.result))
                        .commitAllowingStateLoss()
                } else {
                    deliveryCheck = true
                    binding.lyOrderTab.addTab(
                        binding.lyOrderTab.newTab().setText("?????? ${response.result.timeDelivery}")
                    )
                    binding.lyOrderTab.addTab(
                        binding.lyOrderTab.newTab().setText("?????? ${response.result.timeToGo}")
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
            /* ?????? ?????? ??? ?????? */
            OrderMenuService(this).tryGetMenu(
                response.result.storeLongitude,
                response.result.storeLatitude,
                response.result.storeIdx
            )
        }
    }


    override fun onGetOrderCartFailure(error: String) {
        showCustomToast("?????? ?????? ??????")
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
        //?????? ??????
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
        builder.setMessage("???????????? ????????? ?????????????????????????")
        builder.setPositiveButton("??????") { dialog, _ ->
            dialog.dismiss()
            OrderCartService(this).tryDeleteCart(cartIdx)
        }
        builder.setNegativeButton("??????") { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }

    private fun noLeastPriceDialog(minimumPrice: Int, response: StoreInfoMenuCartGetResponse) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("?????? ?????? ?????? ${ApplicationClass.DEC.format(minimumPrice)}??? ?????? ?????? ???????????????.\n?????? ?????? ??? ?????? ??????????????????.")
        builder.setPositiveButton("??????") { dialog, _ ->
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
        builder.setMessage("?????? ????????? ????????? ??? ????????????. ??????????????? ??????????????????")
        builder.setPositiveButton("??????") { dialog, _ ->
            dialog.dismiss()
            finishAffinity()
            val homeIntent = Intent(this, MainActivity::class.java)
            startActivity(homeIntent)
        }
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
        intent.putExtra("deliveryFee", deliveryCost)
        intent.putExtra("orderIdx", response.result.userOrderIdx)
        startActivity(homeIntent)
        startActivity(intent)
    }

    override fun onPostOrderFailure(error: String) {
        //??????
    }

    override fun onPutOrderSuccess(response: OrderModifyResponse) {
        //?????? ??????
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