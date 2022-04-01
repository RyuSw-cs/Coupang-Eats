package com.softsquared.template.kotlin.src.main.location

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.BaseActivity
import com.softsquared.template.kotlin.databinding.ActivityLocationBinding
import com.softsquared.template.kotlin.src.main.home.models.rising.address.models.HomeAddressResponse
import com.softsquared.template.kotlin.src.main.location.search.*
import com.softsquared.template.kotlin.src.main.location.search.models.SearchDocumentsResponse
import com.softsquared.template.kotlin.src.main.location.search.models.SearchResultResponse
import com.softsquared.template.kotlin.src.main.location.select.LocationSelectFragment

class LocationActivity : BaseActivity<ActivityLocationBinding>(ActivityLocationBinding::inflate),
    SearchFragmentView,LocationView {

    private var firstSearchBarTouch = true
    private var currentPage: Int = 1
    var totalPage = 0
    private lateinit var keyword: String
    private lateinit var getDocumentList: List<SearchDocumentsResponse>

    private lateinit var searchResultFragment: LocationSearchResultFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LocationService(this).tryGetLocation()

    }

    fun init(response: HomeAddressResponse) {
        //서버 통신해야함.
        supportFragmentManager.beginTransaction()
            .replace(R.id.ly_result, LocationSelectFragment(response)).commitAllowingStateLoss()

        with(binding) {
            ivCancel.setOnClickListener {
                finish()
            }
            with(etSearch) {
                setOnFocusChangeListener { _, hasFocus ->
                    if (hasFocus) {
                        if (firstSearchBarTouch) {
                            supportFragmentManager.beginTransaction()
                                .replace(R.id.ly_result, LocationSearchFragment())
                                .commitAllowingStateLoss()
                            firstSearchBarTouch = false
                            //임시 이미지
                            ivCancel.setImageResource(R.drawable.ic_map_back)
                        }
                    } else {
                        ivCancel.setImageResource(R.drawable.ic_cancel)
                    }
                }
                addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {

                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                        if (text.toString() != "") {
                            ivSearchDelete.visibility = View.VISIBLE
                        } else {
                            ivSearchDelete.visibility = View.INVISIBLE
                        }
                    }

                    override fun afterTextChanged(s: Editable?) {

                    }
                })
                setOnEditorActionListener { _, actionId, _ ->
                    //빈칸이 아니고
                    if (text.toString() != "") {
                        //검색 버튼을 누르면
                        currentPage = 1
                        keyword = text.toString()
                        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                            SearchService(this@LocationActivity).tryGetSearchResult(
                                keyword,
                                currentPage
                            )
                        }
                    }
                    true
                }
            }

            ivSearchDelete.setOnClickListener {
                etSearch.setText("")
            }

            ivCancel.setOnClickListener {
                etSearch.setText("")
                firstSearchBarTouch = true
                val manager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                manager.hideSoftInputFromWindow(
                    currentFocus?.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
                supportFragmentManager.beginTransaction()
                    .replace(R.id.ly_result, LocationSelectFragment(response))
                    .commitAllowingStateLoss()
                etSearch.clearFocus()
            }
        }
    }

    fun continueGetSearchResult(keyword: String) {
        if (currentPage <= totalPage) {
            SearchService(this).tryGetContinueSearchResult(keyword, currentPage++)
        }
    }

    override fun onGetSearchResultSuccess(resultResponse: SearchResultResponse) {
        //통신 성공
        //데이터가 있는지?
        if (resultResponse.documents.isEmpty()) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.ly_result, LocationSearchNoResultFragment()).commitAllowingStateLoss()
        } else {
            //전체 페이지
            getDocumentList = resultResponse.documents

            //기존 페이지가 존재!
            if (totalPage != 0 && searchResultFragment.keyword == keyword) {
                searchResultFragment.continueAddListData(getDocumentList)

            } else {
                searchResultFragment = LocationSearchResultFragment(this, keyword, getDocumentList)
                supportFragmentManager.beginTransaction()
                    .replace(R.id.ly_result, searchResultFragment).commitAllowingStateLoss()
                currentPage = 1
            }
            currentPage++
            totalPage = resultResponse.meta.pageableCount
        }
    }

    override fun onGetSearchResultFailure(message: String) {
        //첫 통신 실패
    }

    override fun onGetContinueSearchResultSuccess(resultResponse: SearchResultResponse) {
        //프래그먼트로 데이터 줘야함.
        searchResultFragment.continueAddListData(resultResponse.documents)
    }

    override fun onGetContinueSearchResultFailure(message: String) {
        //다른 페이지 가져오기 실패
    }

    override fun onGetLocationSuccess(response: HomeAddressResponse) {
        init(response)
    }

    override fun onGetLocationFailure(error: String) {
        showCustomToast("주소 가져오기 실패")
    }
}