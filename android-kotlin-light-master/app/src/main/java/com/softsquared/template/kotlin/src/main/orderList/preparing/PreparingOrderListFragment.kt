package com.softsquared.template.kotlin.src.main.orderList.preparing

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.BaseFragment
import com.softsquared.template.kotlin.databinding.FragmentOrderListPreparingBinding
import com.softsquared.template.kotlin.src.main.home.HomeFragment
import com.softsquared.template.kotlin.src.main.orderList.OrderListFragment
import com.softsquared.template.kotlin.src.main.orderList.models.OrderListDetailResponse
import com.softsquared.template.kotlin.src.main.orderList.models.OrderListResponse
import com.softsquared.template.kotlin.src.main.orderList.preparing.adapter.PreparingOrderListAdapter

class PreparingOrderListFragment(val fragment:OrderListFragment, val response: OrderListResponse) :
    BaseFragment<FragmentOrderListPreparingBinding>(
        FragmentOrderListPreparingBinding::bind,
        R.layout.fragment_order_list_preparing
    ) {
    var dataCheck = false

    private val dataList = mutableListOf<OrderListDetailResponse>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    fun init() {
        for (idx in response.result.indices) {
            if (response.result[idx].status == "메뉴 준비중" || response.result[idx].status == "배달 중" || response.result[idx].status == "주문 수락됨") {
                dataList.add(response.result[idx])
                dataCheck = true
            }
        }
        if (dataCheck) {
            binding.rcvPreparing.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            binding.rcvPreparing.adapter =
                PreparingOrderListAdapter(requireContext(), dataList)
        } else {
            binding.icNoPreparing.visibility = View.VISIBLE
            binding.btGoPastList.visibility = View.VISIBLE
            binding.btGoPastList.setOnClickListener {
                //뷰페이저 다른 곳 으로
                fragment.slideTab()
            }

            binding.rcvPreparing.visibility = View.GONE
        }
    }
}