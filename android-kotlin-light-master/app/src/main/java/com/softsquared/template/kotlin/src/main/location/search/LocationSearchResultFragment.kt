package com.softsquared.template.kotlin.src.main.location.search

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.BaseFragment
import com.softsquared.template.kotlin.databinding.FragmentLocationSearchResultBinding
import com.softsquared.template.kotlin.src.main.location.LocationActivity
import com.softsquared.template.kotlin.src.main.location.search.adapter.SearchResultAdapter
import com.softsquared.template.kotlin.src.main.location.search.models.SearchDocumentsResponse

class LocationSearchResultFragment(
    private val activity: LocationActivity,
    val keyword: String,
    private val getSearchDataList: List<SearchDocumentsResponse>
) :
    BaseFragment<FragmentLocationSearchResultBinding>(
        FragmentLocationSearchResultBinding::bind,
        R.layout.fragment_location_search_result
    ) {

    lateinit var searchAdapter: SearchResultAdapter
    private val searchList: MutableList<SearchDocumentsResponse> = mutableListOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //데이터 추가 됨.
        addListData(getSearchDataList)
        init()
    }

    fun searchContinueData() {
        activity.continueGetSearchResult(keyword)
    }

    fun addListData(list: List<SearchDocumentsResponse>) {
        for (data in list.indices) {
            searchList.add(list[data])
        }
    }

    fun continueAddListData(list: List<SearchDocumentsResponse>) {
        addListData(list)
        binding.rcvList.smoothScrollToPosition(searchAdapter.getList.size - 16)
        searchAdapter.changeData(searchList)
    }

    fun init() {
        with(binding.rcvList) {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            searchAdapter = SearchResultAdapter(requireContext(),searchList)
            adapter = searchAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (!canScrollVertically(1)) {
                        searchContinueData()
                    }
                }
            })
        }
    }
}