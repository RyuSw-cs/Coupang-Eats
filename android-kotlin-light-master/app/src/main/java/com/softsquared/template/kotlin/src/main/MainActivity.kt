package com.softsquared.template.kotlin.src.main

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.config.BaseActivity
import com.softsquared.template.kotlin.databinding.ActivityMainBinding
import com.softsquared.template.kotlin.src.main.favorite.FavoriteActivity
import com.softsquared.template.kotlin.src.main.location.nonPermissionLocation.NonPermissionLocationFragment
import com.softsquared.template.kotlin.src.main.home.HomeFragment
import com.softsquared.template.kotlin.src.main.login.LoginBottomSheetDialog
import com.softsquared.template.kotlin.src.main.myPage.MyPageFragment
import com.softsquared.template.kotlin.src.main.orderList.OrderListFragment

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    var permissionCheck = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkPermission()
    }

    private fun init() {
        //권한 확인 -> 안되면 다른 프래그먼트를 나오게 함
        initFragment()
        with(binding) {
            bnvMainBottomNavi.setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.menu_main_btm_nav_home -> {
                        initFragment()
                        true
                    }
                    R.id.menu_main_btm_nav_my_search -> {
                        //검색
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.ly_main_frame, HomeFragment()).commitAllowingStateLoss()
                        true
                    }
                    R.id.menu_main_btm_nav_favorite -> {
                        //즐겨찾기
                        startActivity(Intent(this@MainActivity,FavoriteActivity::class.java))
                        false
                    }
                    R.id.menu_main_btm_nav_order_list -> {
                        //로그인 정보 존재
                        if (ApplicationClass.X_ACCESS_TOKEN != "TOKEN"
                        ) {
                            supportFragmentManager.beginTransaction()
                                .replace(R.id.ly_main_frame, OrderListFragment())
                                .commitAllowingStateLoss()
                            true
                        }
                        else{
                            //없다면 다이얼로그 보여주고 로그인 해야함.(선택 되서도 안됨)
                            LoginBottomSheetDialog().show(supportFragmentManager, "LoginBottomSheet")
                            false
                        }
                    }
                    R.id.menu_main_btm_nav_my_page -> {
                        if (ApplicationClass.X_ACCESS_TOKEN != "TOKEN") {
                            supportFragmentManager.beginTransaction()
                                .replace(R.id.ly_main_frame, MyPageFragment())
                                .commitAllowingStateLoss()
                            true
                        }
                        else{
                            //없다면 다이얼로그 보여주고 로그인 해야함.(선택 되서도 안됨)
                            LoginBottomSheetDialog().show(supportFragmentManager, "LoginBottomSheet")
                            false
                        }
                    }
                    else -> {
                        false
                    }
                }
            }
        }
    }

    private fun initFragment() {
        permissionCheck = ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED

        when (permissionCheck) {
            true -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.ly_main_frame, HomeFragment()).commitAllowingStateLoss()
            }
            false -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.ly_main_frame, NonPermissionLocationFragment())
                    .commitAllowingStateLoss()
            }
        }
    }

    private fun checkPermission() {
        //권한 확인 됨 -> 위치 잡고 정상적인 액티비티 나옴
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            init()
        }
        //권한이 없음 -> 권한 요청
        else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    android.Manifest.permission.ACCESS_COARSE_LOCATION,
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                ),
                1
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        //권한이 거부
        if (requestCode == 1) {
            permissionCheck =
                grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED
            init()
        }
    }
}