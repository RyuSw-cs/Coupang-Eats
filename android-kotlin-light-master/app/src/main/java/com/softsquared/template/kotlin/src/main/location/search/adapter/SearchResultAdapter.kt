package com.softsquared.template.kotlin.src.main.location.search.adapter

import android.annotation.SuppressLint
import android.app.appsearch.SearchResult
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.softsquared.template.kotlin.databinding.ItemLocationSearchBinding
import com.softsquared.template.kotlin.src.main.location.add.LocationAddActivity
import com.softsquared.template.kotlin.src.main.location.search.models.SearchDocumentsResponse
import com.softsquared.template.kotlin.src.main.location.search.models.SearchResultResponse

class SearchResultAdapter(
    val context: Context,
    dataList: MutableList<SearchDocumentsResponse>
) :
    RecyclerView.Adapter<SearchResultAdapter.SearchResultHolder>() {

    var getList = dataList

    inner class SearchResultHolder(val binding: ItemLocationSearchBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultHolder {
        val view =
            ItemLocationSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchResultHolder(view)
    }

    override fun onBindViewHolder(holder: SearchResultHolder, position: Int) {
        with(holder) {
            with(getList[position]) {
                binding.tvAddressDetail.text = placeName
                //도로명 주소가 없다면 지번으로
                if (roadAddressName == "") {
                    binding.tvType.text = "지번"
                    binding.tvAddressDetail.text = addressName
                } else {
                    binding.tvAddress.text = roadAddressName
                }
                //해당 위치 설정, 이름과 주소, 좌표를 전달해야함.
                itemView.setOnClickListener {
                    val intent = Intent(context, LocationAddActivity::class.java)
                    intent.putExtra("placeName", binding.tvAddressDetail.text)
                    intent.putExtra("address", binding.tvAddress.text)
                    intent.putExtra("latitude", latitude.toDouble())
                    intent.putExtra("longitude", longitude.toDouble())
                    context.startActivity(intent)
                }
            }
        }
    }

    override fun getItemCount() = getList.size

    @SuppressLint("NotifyDataSetChanged")
    fun changeData(list: MutableList<SearchDocumentsResponse>) {
        //RecyclerView 에 데이터가 변경됐음을 알림
        getList = list
        notifyDataSetChanged()
    }
}