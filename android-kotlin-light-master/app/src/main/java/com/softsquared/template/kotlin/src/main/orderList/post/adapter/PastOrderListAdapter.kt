package com.softsquared.template.kotlin.src.main.orderList.post.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.databinding.ItemOrderDetailInfoBinding
import com.softsquared.template.kotlin.src.main.order.OrderActivity
import com.softsquared.template.kotlin.src.main.orderList.adapter.OrderMenuListAdapter
import com.softsquared.template.kotlin.src.main.orderList.dialog.ReviewReceiptDialog
import com.softsquared.template.kotlin.src.main.orderList.models.OrderListDetailResponse
import com.softsquared.template.kotlin.src.main.orderList.models.reOrder.ReOrderResponse
import com.softsquared.template.kotlin.src.main.orderList.models.reOrder.ReOrderService
import com.softsquared.template.kotlin.src.main.orderList.models.reOrder.ReOrderView
import com.softsquared.template.kotlin.src.main.reviewWrite.ReviewWriteActivity
import com.softsquared.template.kotlin.src.main.storeInfo.StoreInfoActivity
import com.softsquared.template.kotlin.src.main.storeInfo.StoreInfoService

class PastOrderListAdapter(val context: Context, val result: List<OrderListDetailResponse>) :
    RecyclerView.Adapter<PastOrderListAdapter.PastOrderHolder>(), ReOrderView {
    inner class PastOrderHolder(val binding: ItemOrderDetailInfoBinding) :
        RecyclerView.ViewHolder(binding.root)

    var userOrderIdx = 0
    var totalPrice = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PastOrderHolder {
        val view =
            ItemOrderDetailInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PastOrderHolder(view)
    }

    override fun onBindViewHolder(holder: PastOrderHolder, position: Int) {
        with(holder) {
            with(result[position]) {
                binding.tvStoreTitle.text = storeName
                binding.tvOrderTime.text = orderTime
                binding.tvOrderState.text = status
                var price = 0
                for(priceData in orderMenuInfo){
                    price += priceData.mulPrice
                }
                //?????? ??????
                binding.tvTotalPrice.text = "${ApplicationClass.DEC.format(price+deliveryFee)}???"

                Glide.with(context)
                    .load(storeImgUrl)
                    .centerCrop()
                    .into(binding.ivFoodImg)

                //?????????????????? ??????
                binding.rcvOrderList.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                binding.rcvOrderList.adapter = OrderMenuListAdapter(orderMenuInfo)

                //?????????
                binding.tvReceipt.setOnClickListener {
                    val intent = Intent(context, ReviewReceiptDialog::class.java)
                    intent.putExtra("data", result[position])
                    //?????? ??????
                    intent.putExtra("totalPrice", price)
                    //?????????
                    intent.putExtra("deliveryFee",deliveryFee)
                    context.startActivity(intent)
                }
                //?????????,
                binding.btReOrder.setOnClickListener {
                    ReOrderService(this@PastOrderListAdapter).tryPostReOrder(userOrderIdx)
                    this@PastOrderListAdapter.userOrderIdx = userOrderIdx
                }

                //???????????? ??????.
                if (reviewScore == 0) {
                    binding.btReview.visibility = View.VISIBLE
                    binding.btReview.setOnClickListener {
                        //???????????? ????????????
                        val intent = Intent(context, ReviewWriteActivity::class.java)
                        intent.putExtra("data", result[position])
                        context.startActivity(intent)
                    }
                } else {
                    binding.rbReview.rating = reviewScore.toFloat()
                    binding.rbReview.visibility = View.VISIBLE
                    binding.btReview.visibility = View.VISIBLE
                    binding.btReview.text = "????????? ?????? ??????"
                    binding.btReview.setOnClickListener {
                        val intent = Intent(context, ReviewWriteActivity::class.java)
                        intent.putExtra("data", result[position])
                        context.startActivity(intent)
                    }
                }
            }
        }
    }

    override fun getItemCount() = result.size

    override fun onPostReOrderSuccess(response: ReOrderResponse) {
        val intent = Intent(context, OrderActivity::class.java)
        context.startActivity(intent)
    }

    override fun onPostReOrderFailure(error: String) {
        //???????????????
        val pDialog = AlertDialog.Builder(context)
        pDialog.setTitle("????????? ????????? ????????? ????????????.")
        pDialog.setMessage("???????????? ????????? ?????? ????????? ?????? ????????? ???????????????.")
        //??? ???????????? ????????????.
        pDialog.setPositiveButton("????????????") { dialog, _ ->
            ReOrderService(this).tryNewPostReOrder(userOrderIdx)
            dialog.dismiss()
        }
        pDialog.setNegativeButton("??????") { dialog, _ -> dialog.dismiss() }
        pDialog.show()
    }

    override fun onNewPostReOrderSuccess(response: ReOrderResponse) {
        val intent = Intent(context, OrderActivity::class.java)
        context.startActivity(intent)
    }

    override fun onNewPostReOrderFailure(error: String) {

    }
}