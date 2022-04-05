package com.softsquared.template.kotlin.src.main.favorite

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.config.BaseActivity
import com.softsquared.template.kotlin.databinding.ActivityFavoriteBinding
import com.softsquared.template.kotlin.src.main.MainActivity
import com.softsquared.template.kotlin.src.main.favorite.adapter.FavoriteAdapter
import com.softsquared.template.kotlin.src.main.favorite.address.FavoriteAddressService
import com.softsquared.template.kotlin.src.main.favorite.address.FavoriteAddressView
import com.softsquared.template.kotlin.src.main.favorite.models.FavoritePostResponse
import com.softsquared.template.kotlin.src.main.favorite.models.FavoriteResponse
import com.softsquared.template.kotlin.src.main.home.models.rising.address.models.HomeAddressResponse

class FavoriteActivity : BaseActivity<ActivityFavoriteBinding>(ActivityFavoriteBinding::inflate),
    FavoriteActivityView, FavoriteAddressView {

    var sort = "frequent"
    var lat = 0.0
    var long = 0.0
    var modify = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    fun init() {
        showLoadingDialog(this)
        FavoriteAddressService(this).tryGetAddress()
    }

    fun reSortFavorite(sort: String,text:String) {
        FavoriteActivityService(this).tryGetFavorite(
            lat,
            long,
            sort
        )
        binding.tvSort.text = text
    }

    fun changeDeleteCount() {
        if (ApplicationClass.FAVORITE.isNotEmpty()) {
            binding.lyDelete.visibility = View.VISIBLE
        } else {
            binding.lyDelete.visibility = View.GONE
        }
        binding.tvDeleteCount.text = ApplicationClass.FAVORITE.size.toString()
    }

    override fun onGetFavoriteSuccess(response: FavoriteResponse) {
        dismissLoadingDialog()
        sort = response.result.sortType

        binding.lyDelete.visibility = View.GONE

        if (response.result.getFavoriteListList.isEmpty()) {
            //즐겨찾기 없음
            binding.ivCancel.setOnClickListener {
                finish()
            }
            binding.lyNoFavorite.visibility = View.VISIBLE
            binding.lyTotal.visibility = View.GONE
            binding.rcvStoreList.visibility = View.GONE
            binding.tvModify.visibility = View.GONE
            binding.divFavorite.visibility = View.GONE
            binding.btGoPastList.setOnClickListener {
                finish()
            }
        } else {
            binding.lyDelete.setOnClickListener {
                FavoriteActivityService(this).tryDeleteFavorite(ApplicationClass.FAVORITE)
                ApplicationClass.FAVORITE.clear()
            }

            binding.tvCount.text = "총 ${response.result.getFavoriteListList.size}개"

            binding.tvModify.setOnClickListener {
                if (modify) {
                    modify = false
                    binding.rcvStoreList.adapter =
                        FavoriteAdapter(this, response.result.getFavoriteListList, modify)
                    binding.tvModify.text = "수정"
                    binding.lyTotal.visibility = View.VISIBLE
                } else {
                    modify = true
                    binding.tvModify.text = "취소"
                    binding.rcvStoreList.adapter =
                        FavoriteAdapter(this, response.result.getFavoriteListList, modify)
                    binding.lyTotal.visibility = View.GONE
                }
            }

            binding.rcvStoreList.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            binding.rcvStoreList.adapter =
                FavoriteAdapter(this, response.result.getFavoriteListList, modify)

            binding.tvSort.setOnClickListener {
                FavoriteBottomSheet(this, sort).show(supportFragmentManager, "favoriteSheet")
            }
        }
    }

    override fun onGetFavoriteFailure(error: String) {
        dismissLoadingDialog()
    }

    override fun onDeleteFavoriteSuccess(response: FavoritePostResponse) {
        FavoriteActivityService(this).tryGetFavorite(
            lat,
            long,
            "frequent"
        )
    }

    override fun onDeleteFavoriteFailure(error: String) {

    }

    override fun onGetAddressSuccess(response: HomeAddressResponse) {
        dismissLoadingDialog()
        showLoadingDialog(this)
        lat = response.result.nowAddress.addressLatitude
        long = response.result.nowAddress.addressLongitude
        FavoriteActivityService(this).tryGetFavorite(
            lat,
            long,
            "frequent"
        )
    }

    override fun onGetAddressFailure(error: String) {
        dismissLoadingDialog()
        //토큰값이 없을때가 존재함.
        binding.ivCancel.setOnClickListener {
            finish()
        }
        binding.lyNoFavorite.visibility = View.VISIBLE
        binding.lyTotal.visibility = View.GONE
        binding.rcvStoreList.visibility = View.GONE
        binding.tvModify.visibility = View.GONE
        binding.divFavorite.visibility = View.GONE
        binding.btGoPastList.setOnClickListener {
            finish()
        }
    }
}