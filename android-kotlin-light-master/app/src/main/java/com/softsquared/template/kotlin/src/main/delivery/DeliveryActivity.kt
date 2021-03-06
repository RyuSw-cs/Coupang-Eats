package com.softsquared.template.kotlin.src.main.delivery

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.FusedLocationSource
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.config.BaseActivity
import com.softsquared.template.kotlin.databinding.ActivityDeliveryBinding
import com.softsquared.template.kotlin.src.main.MainActivity
import com.softsquared.template.kotlin.src.main.delivery.adapter.DeliveryAdapter
import com.softsquared.template.kotlin.src.main.delivery.models.DeliveryCancelResponse
import com.softsquared.template.kotlin.src.main.storeInfo.StoreInfoActivity
import com.softsquared.template.kotlin.src.main.storeInfo.menu.cart.models.StoreInfoMenuCartGetResponse

class DeliveryActivity : BaseActivity<ActivityDeliveryBinding>(ActivityDeliveryBinding::inflate),
    OnMapReadyCallback, DeliveryActivityView {

    private val mapView: MapView by lazy { binding.mMap }
    lateinit var naverMap: NaverMap
    private lateinit var locationSource: FusedLocationSource

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
        init()
    }

    fun init() {
        with(binding) {
            val data = intent.getSerializableExtra("cartInfo") as StoreInfoMenuCartGetResponse
            ivCancel.setOnClickListener {
                startActivity(Intent(this@DeliveryActivity, MainActivity::class.java))
                finishAffinity()
            }

            btOrderCancel.setOnClickListener {
                deleteOrderDialog()
            }
            tvOrderAddressContent.text = data.result.nowAddress.address
            tvOrderAddressDetailContent.text = data.result.nowAddress.addressDetail
            tvOrderPlaceName.text = data.result.storeName
            var deliveryFee = intent.getIntExtra("deliveryFee",0)
            tvTotalPrice.text = "??????: ${ApplicationClass.DEC.format(data.result.totalPrice + deliveryFee)}"

            rcvOrderList.layoutManager =
                LinearLayoutManager(this@DeliveryActivity, LinearLayoutManager.VERTICAL, false)
            rcvOrderList.adapter = DeliveryAdapter(data.result.cartMenu)
        }
    }

    private fun deleteOrderDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("????????? ?????????????????????????")
        builder.setPositiveButton("??????") { dialog, _ ->
            dialog.dismiss()
            DeliveryActivityService(this@DeliveryActivity).tryDeleteDelivery(
                intent.getIntExtra(
                    "orderIdx",
                    0
                )
            )
        }
        builder.setNegativeButton("??????") { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }

    override fun onMapReady(map: NaverMap) {
        naverMap = map

        val nowLocation = LatLng(
            intent.getDoubleExtra("userLatitude", 0.0),
            intent.getDoubleExtra("userLongitude", 0.0)
        )

        val storeLocation =
            LatLng(
                intent.getDoubleExtra("storeLatitude", 0.0),
                intent.getDoubleExtra("storeLongitude", 0.0)
            )
        val middleLocation = CameraUpdate.scrollTo(
            LatLng(
                (intent.getDoubleExtra("userLatitude", 0.0) + intent.getDoubleExtra(
                    "storeLatitude",
                    0.0
                )) / 2,
                (intent.getDoubleExtra("userLongitude", 0.0) + intent.getDoubleExtra(
                    "storeLongitude",
                    0.0
                )) / 2
            )
        )


        val storeMarker = Marker()
        storeMarker.position = storeLocation
        storeMarker.map = naverMap
        storeMarker.icon = OverlayImage.fromResource(R.drawable.ic_food_location)

        val currentMarker = Marker()
        currentMarker.position = nowLocation
        currentMarker.map = naverMap
        currentMarker.icon = OverlayImage.fromResource(R.drawable.ic_map_current_location)

        //ui ??????
        val uiSetting = naverMap.uiSettings
        uiSetting.isLocationButtonEnabled = false
        uiSetting.isScaleBarEnabled = false
        uiSetting.isZoomControlEnabled = false

        naverMap.locationSource = locationSource

        with(naverMap) {
            //?????? ??????
            maxZoom = 21.0
            minZoom = 10.0
            //????????? ??????
            moveCamera(middleLocation)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (locationSource.onRequestPermissionsResult(requestCode, permissions, grantResults)) {
            //?????? ??????
            if (!locationSource.isActivated) {
                naverMap.locationTrackingMode = LocationTrackingMode.None
            }
            return
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDeleteDeliveryCancelSuccess(response: DeliveryCancelResponse) {
        showCustomToast("????????? ?????????????????????.")
        finishAffinity()
        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun onDeleteDeliveryCancelFailure(error: String) {
        showCustomToast("?????? ?????? ??????")
    }
}


