package com.softsquared.template.kotlin.src.main.location.search

import com.softsquared.template.kotlin.src.main.location.search.models.SearchResultResponse

interface SearchFragmentView {
    fun onGetSearchResultSuccess(resultResponse: SearchResultResponse)

    fun onGetSearchResultFailure(message: String)

    fun onGetContinueSearchResultSuccess(resultResponse: SearchResultResponse)

    fun onGetContinueSearchResultFailure(message: String)
}