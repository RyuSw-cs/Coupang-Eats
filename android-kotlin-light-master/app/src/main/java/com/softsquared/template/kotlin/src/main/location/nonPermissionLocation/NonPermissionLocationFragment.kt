package com.softsquared.template.kotlin.src.main.location.nonPermissionLocation

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.BaseFragment
import com.softsquared.template.kotlin.databinding.FragmentLocationPermissonBinding
import com.softsquared.template.kotlin.src.main.home.HomeFragment

class NonPermissionLocationFragment : BaseFragment<FragmentLocationPermissonBinding>(
    FragmentLocationPermissonBinding::bind,
    R.layout.fragment_location_permisson
) {
    var permissionCheck = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        permissionDinedDialog()
        binding.tvYongin.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.ly_main_frame, HomeFragment()).commit()
        }
    }

    override fun onResume() {
        super.onResume()
        //위치 권한이 생긴다면 홈 프래그먼트로 변경
        initFragment()
    }

    private fun initFragment() {
        permissionCheck = ContextCompat.checkSelfPermission(
            requireContext(),
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED

        if (permissionCheck) {
            parentFragmentManager.beginTransaction()
                .replace(R.id.ly_main_frame, HomeFragment()).commit()
        }
    }

    private fun permissionDinedDialog() {
        val pDialog = AlertDialog.Builder(requireContext())
        pDialog.setMessage("위치 서비스를 사용하려면\n접근권한을 허용해 주세요.")
        //앱 설정으로 들어가짐.
        pDialog.setPositiveButton("설정") { dialog, _ ->
            startActivity(
                Intent(
                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:" + activity?.packageName)
                )
            )
            dialog.dismiss()
        }
        pDialog.setNegativeButton("취소") { dialog, _ -> dialog.dismiss() }
        pDialog.show()
    }
}