package com.softsquared.template.kotlin.src.main.orderList

import android.os.Bundle
import android.view.View
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.BaseFragment
import com.softsquared.template.kotlin.databinding.FragmentOrderListBinding

import com.softsquared.template.kotlin.src.main.orderList.adapter.OrderListAdapter
import com.softsquared.template.kotlin.src.main.orderList.models.OrderListResponse

class OrderListFragment : BaseFragment<FragmentOrderListBinding>(
    FragmentOrderListBinding::bind,
    R.layout.fragment_order_list
), OrderListFragmentView {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    fun init() {
        showLoadingDialog(requireContext())
        OrderListFragmentService(this).tryGetOrderList()
    }

    override fun onGetOrderListSuccess(response: OrderListResponse) {
        dismissLoadingDialog()
        binding.lyTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })
        binding.vp2Order.adapter = OrderListAdapter(this,response)
        TabLayoutMediator(binding.lyTab, binding.vp2Order) { tab, position ->
            when (position) {
                0 -> tab.text = "과거 주문 내역"
                1 -> tab.text = "준비중"
            }
        }.attach()
    }


    fun slideTab(){
        binding.vp2Order.setCurrentItem(0)
    }

    override fun onGetOrderListFailure(error: String) {
        dismissLoadingDialog()
        showCustomToast("주문 내용 실패")
    }
}