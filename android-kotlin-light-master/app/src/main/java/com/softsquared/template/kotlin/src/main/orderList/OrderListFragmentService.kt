package com.softsquared.template.kotlin.src.main.orderList

import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.src.main.orderList.models.OrderListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class OrderListFragmentService(val view:OrderListFragmentView) {

    fun tryGetOrderList(){
        val orderListFragmentRetrofitInstance = ApplicationClass.sRetrofit.create(OrderListFragmentRetrofitInstance::class.java)
        orderListFragmentRetrofitInstance.getOrderList().enqueue(object : Callback<OrderListResponse>{
            override fun onResponse(
                call: Call<OrderListResponse>,
                response: Response<OrderListResponse>
            ) {
                if(response.body()?.isSuccess == true){
                    view.onGetOrderListSuccess(response.body() as OrderListResponse)
                }
                else{
                    view.onGetOrderListFailure(response.body()?.message!!)
                }
            }

            override fun onFailure(call: Call<OrderListResponse>, t: Throwable) {
                view.onGetOrderListFailure(t.message ?: "주문 내역 조회 실패")
            }
        })
    }
}