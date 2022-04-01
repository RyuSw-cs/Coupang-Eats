package com.softsquared.template.kotlin.src.main.location.select.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.softsquared.template.kotlin.databinding.ItemLocationBinding
import com.softsquared.template.kotlin.src.main.home.models.rising.address.models.HomeAddressResponse
import com.softsquared.template.kotlin.src.main.location.select.LocationSelectFragment

class LocationSelectFragmentAdapter(
    val fragment: LocationSelectFragment,
    private val addressData: HomeAddressResponse
) :
    RecyclerView.Adapter<LocationSelectFragmentAdapter.LocationFragmentHolder>() {
    inner class LocationFragmentHolder(val binding: ItemLocationBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationFragmentHolder {
        val view = ItemLocationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LocationFragmentHolder(view)
    }

    override fun onBindViewHolder(holder: LocationFragmentHolder, position: Int) {
        with(holder) {

            with(addressData) {
                with(result) {
                    //기타 주소지 설정
                    if(otherAddress.isNotEmpty()){
                        if(otherAddress[position].addressTitle == ""){
                            binding.tvLocation.text = otherAddress[position].buildingName
                        }
                        else{
                            binding.tvLocation.text = otherAddress[position].addressTitle
                        }
                        binding.tvDetailLocation.text = "${otherAddress[position].address} ${otherAddress[position].addressDetail}"
                        if (nowAddress.userAddressIdx == otherAddress[position].userAddressIdx) {
                            binding.ivCheck.visibility = View.VISIBLE
                        }
                    }

                    itemView.setOnClickListener {
                        //주소선택 함.
                        fragment.changeCurrentAddress(otherAddress[position])
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int = addressData.result.otherAddress.size
}