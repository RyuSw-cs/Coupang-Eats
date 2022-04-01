package com.softsquared.template.kotlin.src.main.orderList.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.softsquared.template.kotlin.src.main.orderList.OrderListFragment
import com.softsquared.template.kotlin.src.main.orderList.models.OrderListResponse
import com.softsquared.template.kotlin.src.main.orderList.post.PastOrderListFragment
import com.softsquared.template.kotlin.src.main.orderList.preparing.PreparingOrderListFragment

class OrderListAdapter(val fragment: Fragment, val response: OrderListResponse) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> PastOrderListFragment(response)
            else -> PreparingOrderListFragment(fragment as OrderListFragment, response)
        }
    }
}