package com.softsquared.template.kotlin.src.main.location.search

import android.os.Bundle
import android.view.View
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.BaseFragment
import com.softsquared.template.kotlin.databinding.FragmentLocationSearchBinding

//처음 텍스트 부분
class LocationSearchFragment : BaseFragment<FragmentLocationSearchBinding>(
    FragmentLocationSearchBinding::bind,
    R.layout.fragment_location_search
) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}