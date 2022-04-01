package com.softsquared.template.kotlin.src.main.location.add.map

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.util.FusedLocationSource
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.BaseActivity
import com.softsquared.template.kotlin.databinding.ActivityLocationMapBinding
import com.softsquared.template.kotlin.src.main.location.add.LocationAddActivity
import com.softsquared.template.kotlin.src.main.location.selectMap.models.kakao.LocationSelectRoadAddressService
import com.softsquared.template.kotlin.src.main.location.selectMap.models.kakao.LocationSelectRoadAddressView
import com.softsquared.template.kotlin.src.main.location.selectMap.models.kakao.models.LocationSelectAddressResponse
import com.softsquared.template.kotlin.src.main.location.selectMap.models.kakao.models.LocationSelectResultResponse

class LocationAddMapActivity
    : BaseActivity<ActivityLocationMapBinding>(ActivityLocationMapBinding::inflate),
    OnMapReadyCallback, NaverMap.OnCameraChangeListener, NaverMap.OnCameraIdleListener,
    LocationSelectRoadAddressView {

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }

    private val mapView: MapView by lazy { binding.mMap }
    lateinit var naverMap: NaverMap
    private lateinit var locationSource: FusedLocationSource
    var cameraLongitude: Double = 0.0
    var cameraLatitude: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
        init()
    }

    fun init() {
        with(binding) {
            btRelocation.setOnClickListener {
                //카메라가 내 현재 좌표로 이동
                naverMap.locationTrackingMode = LocationTrackingMode.Follow
                naverMap.locationTrackingMode = LocationTrackingMode.NoFollow
            }
            btApply.setOnClickListener {
                //데이터 전달, 주소상세로 이동
                val intent = Intent(this@LocationAddMapActivity, LocationAddActivity::class.java)
                intent.putExtra("placeName", binding.tvDetailAddress.text)
                intent.putExtra("address", binding.tvAddress.text)
                intent.putExtra("latitude", cameraLatitude)
                intent.putExtra("longitude", cameraLongitude)
                startActivity(intent)
                finish()
            }
        }
    }

    override fun onMapReady(map: NaverMap) {
        naverMap = map

        naverMap.addOnCameraChangeListener(this)
        naverMap.addOnCameraIdleListener(this)

        //현재 위치로 카메라 좌표 설정 -> 가져온 데이터
        val currentLocation = CameraUpdate.scrollTo(
            LatLng(
                intent.getDoubleExtra("latitude",0.0),
                intent.getDoubleExtra("longitude",0.0)
            )
        )

        //ui 셋팅
        val uiSetting = naverMap.uiSettings
        uiSetting.isLocationButtonEnabled = false
        uiSetting.isScaleBarEnabled = false
        uiSetting.isZoomControlEnabled = false


        naverMap.locationSource = locationSource

        with(naverMap) {
            //크기 설정
            maxZoom = 21.0
            minZoom = 5.0
            //카메라 이동
            moveCamera(currentLocation)

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

    override fun onCameraChange(reason: Int, animated: Boolean) {
        //카메라 이동 중 레이아웃 변경
        binding.ivCurrentLocation.setImageResource(R.drawable.ic_map_pressed_marker)
        Log.d("camera change", naverMap.cameraPosition.toString())
        binding.btApply.setBackgroundResource(R.drawable.bg_map_check_location)
        binding.btApply.setTextColor(ContextCompat.getColor(this, R.color.grayForDivider))
        binding.tvRelocation.visibility = View.VISIBLE
        binding.tvAddress.visibility = View.INVISIBLE
        binding.tvDetailAddress.visibility = View.INVISIBLE
    }

    override fun onCameraIdle() {
        //카메라 땐 상황 -> 위치값 가져오기
        binding.ivCurrentLocation.setImageResource(R.drawable.ic_map_non_pressed_marker)
        //카메라의 좌표값 구하는 방법.
        cameraLatitude = naverMap.cameraPosition.target.latitude
        cameraLongitude = naverMap.cameraPosition.target.longitude
        Log.d("camera end", naverMap.cameraPosition.target.latitude.toString())
        //서버통신으로 좌표값을 구하세요.
        LocationSelectRoadAddressService(this).tryGetLocation(
            cameraLongitude.toString(),
            cameraLatitude.toString()
        )
    }

    override fun onGetLocationSuccess(data: LocationSelectResultResponse) {
        //위치를 다시 알려줌.

        //배경 변경
        binding.btApply.setBackgroundResource(R.drawable.bg_app_login)
        binding.btApply.setTextColor(ContextCompat.getColor(this, R.color.white))

        //visibility 변경
        binding.tvRelocation.visibility = View.INVISIBLE
        binding.tvAddress.visibility = View.VISIBLE
        binding.tvDetailAddress.visibility = View.VISIBLE

        if (data.documents[0].locationSelectRoadAddress.buildingName == "") {
            binding.tvDetailAddress.text =
                data.documents[0].locationSelectRoadAddress.addressName
        } else {
            binding.tvDetailAddress.text =
                data.documents[0].locationSelectRoadAddress.buildingName
        }
        binding.tvAddress.text = data.documents[0].locationSelectRoadAddress.addressName
    }

    override fun onGetLocationFailure(message: String) {
        //유지.
    }

    override fun onGetLocationSuccessWithoutRoadAddress(data: LocationSelectAddressResponse) {
        //배경 변경
        binding.btApply.setBackgroundResource(R.drawable.bg_app_login)
        binding.btApply.setTextColor(ContextCompat.getColor(this, R.color.white))

        //visibility 변경
        binding.tvRelocation.visibility = View.INVISIBLE
        binding.tvAddress.visibility = View.VISIBLE
        binding.tvDetailAddress.visibility = View.VISIBLE

        //지번만 존재하는 경우가 있다.(구주소)
        binding.tvDetailAddress.text = data.addressName
        binding.tvAddress.text = data.addressName
    }
}
