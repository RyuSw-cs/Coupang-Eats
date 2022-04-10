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
                for(priceData in orderMenuInfo){
                    this@PastOrderListAdapter.totalPrice += priceData.mulPrice
                }
                binding.tvTotalPrice.text = "${ApplicationClass.DEC.format(this@PastOrderListAdapter.totalPrice)}원"

                Glide.with(context)
                    .load(storeImgUrl)
                    .centerCrop()
                    .into(binding.ivFoodImg)

                //리사이클러뷰 연결
                binding.rcvOrderList.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                binding.rcvOrderList.adapter = OrderMenuListAdapter(orderMenuInfo)
                //버튼 상태 변경
                binding.tvReceipt.setOnClickListener {
                    val intent = Intent(context, ReviewReceiptDialog::class.java)
                    intent.putExtra("data", result[position])
                    intent.putExtra("totalPrice",binding.tvTotalPrice.text)
                    context.startActivity(intent)
                }
                //재주문,
                binding.btReOrder.setOnClickListener {
                    ReOrderService(this@PastOrderListAdapter).tryPostReOrder(userOrderIdx)
                    this@PastOrderListAdapter.userOrderIdx = userOrderIdx
                }

                //리뷰작성 가능.
                if (reviewScore == 0) {
                    binding.btReview.visibility = View.VISIBLE
                    binding.btReview.setOnClickListener {
                        //리뷰작성 액티비티
                        val intent = Intent(context, ReviewWriteActivity::class.java)
                        intent.putExtra("data", result[position])
                        context.startActivity(intent)
                    }
                } else {
                    binding.rbReview.rating = reviewScore.toFloat()
                    binding.rbReview.visibility = View.VISIBLE
                    binding.btReview.visibility = View.VISIBLE
                    binding.btReview.text = "작성한 리뷰 보기"
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
        //다이얼로그
        val pDialog = AlertDialog.Builder(context)
        pDialog.setTitle("카트에 메뉴가 담겨져 있습니다.")
        pDialog.setMessage("재주문을 원하실 경우 이전에 담은 메뉴가 삭제됩니다.")
        //앱 설정으로 들어가짐.
        pDialog.setPositiveButton("새로담기") { dialog, _ ->
            ReOrderService(this).tryNewPostReOrder(userOrderIdx)
            dialog.dismiss()
        }
        pDialog.setNegativeButton("취소") { dialog, _ -> dialog.dismiss() }
        pDialog.show()
    }

    override fun onNewPostReOrderSuccess(response: ReOrderResponse) {
        val intent = Intent(context, OrderActivity::class.java)
        context.startActivity(intent)
    }

    override fun onNewPostReOrderFailure(error: String) {

    }
}