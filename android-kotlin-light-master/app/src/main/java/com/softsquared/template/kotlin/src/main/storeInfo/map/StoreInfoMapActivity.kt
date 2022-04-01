package com.softsquared.template.kotlin.src.main.storeInfo.map

import android.os.Bundle
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.overlay.PathOverlay
import com.naver.maps.map.util.FusedLocationSource
import com.softsquared.template.kotlin.config.BaseActivity
import com.softsquared.template.kotlin.databinding.ActivityTakeOutMapBinding
import com.softsquared.template.kotlin.R


class StoreInfoMapActivity :
    BaseActivity<ActivityTakeOutMapBinding>(ActivityTakeOutMapBinding::inflate),
    OnMapReadyCallback {

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }

    private val mapView: MapView by lazy { binding.mMap }
    lateinit var naverMap: NaverMap
    private lateinit var locationSource: FusedLocationSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
        init()
        binding.tvAddress.text = intent.getStringExtra("storeAddress")
    }

    fun init() {
        with(binding) {
            btCurrentLocation.setOnClickListener {
                //카메라가 내 현재 좌표로 이동
                naverMap.locationTrackingMode = LocationTrackingMode.Follow
                naverMap.locationTrackingMode = LocationTrackingMode.NoFollow
            }
        }
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
                )
                        ) / 2
            )
        )


        val storeMarker = Marker()
        storeMarker.position = storeLocation
        storeMarker.map = naverMap
        storeMarker.icon = OverlayImage.fromResource(R.drawable.ic_store_take_out_location)

        val currentMarker = Marker()
        currentMarker.position = nowLocation
        currentMarker.map = naverMap
        currentMarker.icon = OverlayImage.fromResource(R.drawable.ic_map_current_location)

        val path = PathOverlay()
        path.coords = listOf(
            nowLocation,
            storeLocation
        )

        path.patternImage = OverlayImage.fromResource(R.drawable.ic_path_pattern)

        path.map = naverMap

        //ui 셋팅
        val uiSetting = naverMap.uiSettings
        uiSetting.isLocationButtonEnabled = false
        uiSetting.isScaleBarEnabled = false
        uiSetting.isZoomControlEnabled = false

        naverMap.locationSource = locationSource

        with(naverMap) {
            //크기 설정
            maxZoom = 21.0
            minZoom = 10.0
            //카메라 이동
            moveCamera(middleLocation)

            //그냥 점으로 현재 위치 표시
            locationTrackingMode = LocationTrackingMode.NoFollow
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (locationSource.onRequestPermissionsResult(requestCode, permissions, grantResults)) {
            //권한 거부
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
}