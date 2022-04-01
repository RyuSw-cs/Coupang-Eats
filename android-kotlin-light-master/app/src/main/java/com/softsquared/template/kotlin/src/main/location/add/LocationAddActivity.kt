package com.softsquared.template.kotlin.src.main.location.add

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.softsquared.template.kotlin.config.BaseActivity
import com.softsquared.template.kotlin.databinding.ActivityLocationAddBinding
import com.softsquared.template.kotlin.src.main.MainActivity
import com.softsquared.template.kotlin.src.main.location.add.map.LocationAddMapActivity
import com.softsquared.template.kotlin.src.main.location.add.models.LocationAddResponse
import com.softsquared.template.kotlin.src.main.location.add.models.LocationModifyResponse
import com.softsquared.template.kotlin.src.main.location.selectMap.SelectMapActivity

class LocationAddActivity :
    BaseActivity<ActivityLocationAddBinding>(ActivityLocationAddBinding::inflate), LocationView {

    var category = "O"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    fun init() {
        with(binding) {
            tvLocation.text = intent.getStringExtra("placeName")
            tvDetailLocation.text = intent.getStringExtra("address")

            etDetailAddressHint.setOnFocusChangeListener { _, hasFocus ->
                //포커스가 생겼어
                if (hasFocus) {
                    //다른텍스트가 존재하면 안됨.
                    if (etAddressDetailAnnounce.text.toString() == "") {
                        etAddressDetailAnnounceHint.visibility = View.VISIBLE
                        tvAddressDetailAnnounceFocus.visibility = View.GONE
                        etAddressDetailAnnounce.visibility = View.GONE
                    }

                    if (etDetailAnnounceNickname.text.toString() == "") {
                        etDetailAnnounceNicknameHint.visibility = View.VISIBLE
                        tvDetailAnnounceNickname.visibility = View.GONE
                        etDetailAnnounceNickname.visibility = View.GONE
                    }
                    etDetailAddress.visibility = View.VISIBLE
                    tvDetailAddressFocus.visibility = View.VISIBLE
                    etDetailAddress.requestFocus()
                    etDetailAddressHint.visibility = View.INVISIBLE
                    val manager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    manager.showSoftInput(
                        etDetailAddress,
                        InputMethodManager.SHOW_IMPLICIT
                    )
                }
            }
            etDetailAddress.setOnFocusChangeListener { _, hasFocus ->

            }

            etAddressDetailAnnounceHint.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    if (etDetailAddress.text.toString() == "") {
                        etDetailAddressHint.visibility = View.VISIBLE
                        tvDetailAddressFocus.visibility = View.GONE
                        etDetailAddress.visibility = View.GONE
                    }

                    if (etDetailAnnounceNickname.text.toString() == "") {
                        etDetailAnnounceNicknameHint.visibility = View.VISIBLE
                        tvDetailAnnounceNickname.visibility = View.GONE
                        etDetailAnnounceNickname.visibility = View.GONE
                    }
                    etAddressDetailAnnounce.visibility = View.VISIBLE
                    tvAddressDetailAnnounceFocus.visibility = View.VISIBLE
                    etAddressDetailAnnounce.requestFocus()
                    etAddressDetailAnnounceHint.visibility = View.INVISIBLE
                    val manager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    manager.showSoftInput(
                        etAddressDetailAnnounce,
                        InputMethodManager.SHOW_IMPLICIT
                    )
                }
            }
            etDetailAnnounceNicknameHint.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    if (etDetailAddress.text.toString() == "") {
                        etDetailAddressHint.visibility = View.VISIBLE
                        tvDetailAddressFocus.visibility = View.GONE
                        etDetailAddress.visibility = View.GONE
                    }

                    if (etDetailAnnounceNickname.text.toString() == "") {
                        etDetailAnnounceNicknameHint.visibility = View.VISIBLE
                        tvDetailAnnounceNickname.visibility = View.GONE
                        etDetailAnnounceNickname.visibility = View.GONE
                    }
                    etDetailAnnounceNickname.visibility = View.VISIBLE
                    tvDetailAnnounceNickname.visibility = View.VISIBLE
                    etDetailAnnounceNickname.requestFocus()
                    etDetailAddressHint.visibility = View.INVISIBLE
                    val manager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    manager.showSoftInput(
                        etDetailAddress,
                        InputMethodManager.SHOW_IMPLICIT
                    )
                }
            }

            ibCancel.setOnClickListener {
                finish()
            }

            btAnother.setOnClickListener {
                category = "O"
                etDetailAnnounceNickname.visibility = View.VISIBLE
                showCustomToast("기타 선택")
            }

            btCompany.setOnClickListener {
                category = "C"
                etDetailAnnounceNickname.visibility = View.GONE
                showCustomToast("회사 선택")
            }

            btHome.setOnClickListener {
                category = "H"
                etDetailAnnounceNickname.visibility = View.GONE
                showCustomToast("집 선택")
            }

            //데이터 확인 후 서버 통신신
            btOkay.setOnClickListener {
                if (etDetailAddress.text.toString() == "") {
                    LocationBottomSheetDialog(this@LocationAddActivity).show(
                        supportFragmentManager,
                        "LocationBottomSheet"
                    )
                } else {
                    addAddress()
                }
            }

            lyMapBtn.setOnClickListener {
                val intent = Intent(this@LocationAddActivity, LocationAddMapActivity::class.java)
                intent.putExtra("latitude", getIntent().getDoubleExtra("latitude", 0.0))
                intent.putExtra("longitude", getIntent().getDoubleExtra("longitude", 0.0))
                startActivity(intent)
            }
        }
    }

    fun addAddress() {
        showLoadingDialog(this)
        LocationService(this).tryPostAddress(
            binding.tvLocation.text.toString(),
            binding.tvDetailLocation.text.toString(),
            binding.etDetailAddress.text.toString(),
            binding.etAddressDetailAnnounce.text.toString(),
            intent.getDoubleExtra("longitude", 0.0),
            intent.getDoubleExtra("latitude", 0.0),
            binding.tvLocation.text.toString(),
            category
        )
    }

    override fun onPostLocationSuccess(data: LocationAddResponse) {
        //지도 데이터 전송 성공
        showLoadingDialog(this)
        LocationService(this).tryPutAddress(
            data.result.userAddressIdx
        )
    }

    override fun onPostLocationFailure(error: String) {
        //지도 데이터 전송 실패
        dismissLoadingDialog()
    }

    override fun onPutLocationSuccess(data: LocationModifyResponse) {
        dismissLoadingDialog()
        //선택한 지도로 변경 성공
        showCustomToast("주소가 성공적으로 반영되었습니다.")
        /* 여러주소 실패 */
        finishAffinity()
        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun onPutLocationFailure(error: String) {
        //선택한 지도로 변경 실패
        showCustomToast("주소 등록 실패")
        dismissLoadingDialog()
    }
}
