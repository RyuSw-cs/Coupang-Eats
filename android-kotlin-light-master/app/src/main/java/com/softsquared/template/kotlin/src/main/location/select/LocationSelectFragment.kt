package com.softsquared.template.kotlin.src.main.location.select

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.BaseFragment
import com.softsquared.template.kotlin.databinding.FragmentLocationBinding
import com.softsquared.template.kotlin.src.main.home.models.rising.address.models.HomeAddressModel
import com.softsquared.template.kotlin.src.main.home.models.rising.address.models.HomeAddressResponse
import com.softsquared.template.kotlin.src.main.location.add.models.LocationModifyResponse
import com.softsquared.template.kotlin.src.main.location.select.adapter.LocationSelectFragmentAdapter
import com.softsquared.template.kotlin.src.main.location.selectMap.SelectMapActivity
import com.softsquared.template.kotlin.src.main.location.selectMap.models.rising.LocationSelectView
import com.softsquared.template.kotlin.util.DistanceManager


//Activity 에서 받아오는 데이터를 생성자에 넣어줘야함.
class LocationSelectFragment(private val addressData: HomeAddressResponse) :
    BaseFragment<FragmentLocationBinding>(
        FragmentLocationBinding::bind,
        R.layout.fragment_location
    ), LocationSelectListView {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    fun changeCurrentAddress(changeAddress: HomeAddressModel) {
        LocationSelectService(this).tryPutCurrentAddress(changeAddress.userAddressIdx)
    }

    private fun init() {
        with(binding) {
            //기타 리스트
            with(rcvLocationList) {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapter = LocationSelectFragmentAdapter(this@LocationSelectFragment, addressData)
            }
            //지도 나오기
            lyMapBtn.setOnClickListener {
                //현재 위치 가져오기
                val location = DistanceManager.getMyLocation(requireContext())
                val intent = Intent(requireContext(), SelectMapActivity::class.java)
                intent.putExtra("latitude", location?.latitude)
                intent.putExtra("longitude", location?.longitude)
                startActivity(intent)
            }

            lyLocationCompany.setOnClickListener {
                //회사로 설정
                if (addressData.result.companyAddress.addressTitle != "") {
                    LocationSelectService(this@LocationSelectFragment).tryPutCurrentAddress(
                        addressData.result.companyAddress.userAddressIdx
                    )
                }
            }

            lyLocationHome.setOnClickListener {
                //홈으로 설정
                if (addressData.result.homeAddress.addressTitle != "") {
                    LocationSelectService(this@LocationSelectFragment).tryPutCurrentAddress(
                        addressData.result.homeAddress.userAddressIdx
                    )
                }
            }

            //집 주소 없음
            if (addressData.result.homeAddress.address == "") {
                tvHomeDetailLocation.visibility = View.GONE

                val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                params.setMargins(0, 20, 0, 20)

                tvHomeLocation.layoutParams = params
                tvHomeLocation.text = "집 추가"
            } else {
                //집 데이터 추가
                tvHomeLocation.text = "집"
                tvHomeDetailLocation.text =
                    "${addressData.result.homeAddress.address} ${addressData.result.homeAddress.addressDetail}"

                //만약 선택? -> 체크 표시 on
                if (addressData.result.nowAddress.userAddressIdx == addressData.result.homeAddress.userAddressIdx) {
                    ivCheck.visibility = View.VISIBLE
                }
            }

            //회사 주소 없음
            if (addressData.result.companyAddress.address == "") {
                binding.tvCompanyDetailLocation.visibility = View.GONE

                val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                params.setMargins(0, 20, 0, 20)

                binding.tvCompanyLocation.layoutParams = params
                binding.tvCompanyLocation.text = "회사 추가"
            } else {
                tvCompanyLocation.text = "회사"
                tvCompanyDetailLocation.text =
                    "${addressData.result.companyAddress.address} ${addressData.result.companyAddress.addressDetail}"

                //만약 선택? -> 체크 표시 on
                if (addressData.result.nowAddress.userAddressIdx == addressData.result.companyAddress.userAddressIdx) {
                    ivCompanyCheck.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onPutLocationSuccess(data: LocationModifyResponse) {
        showCustomToast("주소가 변경되었습니다.")
        activity?.finish()
    }

    override fun onPutLocationFailure(error: String) {
        showCustomToast("주소선택 실패 $error")
    }
}