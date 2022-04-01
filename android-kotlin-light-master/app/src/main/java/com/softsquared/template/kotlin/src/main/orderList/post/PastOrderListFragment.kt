package com.softsquared.template.kotlin.src.main.orderList.post

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.BaseFragment
import com.softsquared.template.kotlin.databinding.FragmentOrderListPastOrderBinding
import com.softsquared.template.kotlin.src.main.orderList.models.OrderListDetailResponse
import com.softsquared.template.kotlin.src.main.orderList.models.OrderListResponse
import com.softsquared.template.kotlin.src.main.orderList.post.adapter.PastOrderListAdapter


class PastOrderListFragment(val response: OrderListResponse) :
    BaseFragment<FragmentOrderListPastOrderBinding>(
        FragmentOrderListPastOrderBinding::bind,
        R.layout.fragment_order_list_past_order
    ) {

    private val dataList = mutableListOf<OrderListDetailResponse>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    fun init() {
        for (idx in response.result.indices) {
            if (response.result[idx].status == "배달 완료") {
                dataList.add(response.result[idx])
            }
        }

        binding.rcvPastList.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rcvPastList.adapter = PastOrderListAdapter(requireContext(), dataList)
    }
}