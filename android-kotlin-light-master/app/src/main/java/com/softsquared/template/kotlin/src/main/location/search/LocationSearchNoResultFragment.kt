package com.softsquared.template.kotlin.src.main.location.search

import android.os.Bundle
import android.view.View
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.BaseFragment
import com.softsquared.template.kotlin.databinding.FragmentLocationNoResultBinding

//결과 없음은 그냥 보여주면 됨.
class LocationSearchNoResultFragment : BaseFragment<FragmentLocationNoResultBinding>(
    FragmentLocationNoResultBinding::bind,
    R.layout.fragment_location_no_result
) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}