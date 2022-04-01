package com.softsquared.template.kotlin.config

import android.app.Activity
import android.app.Application
import android.content.SharedPreferences
import android.os.Build
import android.view.WindowManager
import androidx.core.view.WindowCompat
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.DecimalFormat
import java.util.concurrent.TimeUnit

// 앱이 실행될때 1번만 실행이 됩니다.
class ApplicationClass : Application() {

    // 테스트 서버 주소
    val API_URL = "http://dodo-hannah.shop/"

    // 실 서버 주소
    // val API_URL = "http://api.test.com/"

    val NAVER_API_URL = "https://naveropenapi.apigw.ntruss.com/"

    val KAKAO_API_RUL = "https://dapi.kakao.com/"

    // 코틀린의 전역변수 문법
    companion object {
        // 만들어져있는 SharedPreferences 를 사용해야합니다. 재생성하지 않도록 유념해주세요
        lateinit var sSharedPreferences: SharedPreferences
        lateinit var firstSharedPreferences: SharedPreferences

        val DEC = DecimalFormat("#,###")

        // JWT Token Header 키 값
        var X_ACCESS_TOKEN : String? = "TOKEN"
        var REFRESH_TOKEN : String = "TOKEN"

        var IS_FIRST_CHECK = true

        // Retrofit 인스턴스, 앱 실행시 한번만 생성하여 사용합니다.
        lateinit var sRetrofit: Retrofit
        lateinit var nRetrofit: Retrofit
        lateinit var kRetrofit: Retrofit

        // Naver 헤더 값
        val NAVER_CLIENT_ID = "je7fg2vbzp"
        val NAVER_CLIENT_SECRET = "1zdAo5RwwZadSobCLWNjH6MRNQyiSYuomSfo5oiB"

        // Kakao 헤더 값
        val KAKAO_REST_API_KEY = "6892cc144dfc9cbb99620cc98b1bd3b9"

        const val FOOTER = 2
        const val ITEM = 1

        val CART = mutableSetOf<String>()
        val FAVORITE = mutableListOf<String>()
    }


    // 앱이 처음 생성되는 순간, SP를 새로 만들어주고, 레트로핏 인스턴스를 생성합니다.
    override fun onCreate() {
        super.onCreate()
        sSharedPreferences =
            applicationContext.getSharedPreferences("X-ACCESS-TOKEN", MODE_PRIVATE)

        firstSharedPreferences =
            applicationContext.getSharedPreferences("IS_FIRST_RUN", MODE_PRIVATE)

        X_ACCESS_TOKEN = sSharedPreferences.getString("X-ACCESS-TOKEN","TOKEN")
        IS_FIRST_CHECK = firstSharedPreferences.getBoolean("IS_FIRST_RUN",true)
        // 레트로핏 인스턴스 생성
        initRetrofitInstance()
        initKRetrofitInstance()
        initNRetrofitInstance()
    }

    // 레트로핏 인스턴스를 생성하고, 레트로핏에 각종 설정값들을 지정해줍니다.
    // 연결 타임아웃시간은 5초로 지정이 되어있고, HttpLoggingInterceptor를 붙여서 어떤 요청이 나가고 들어오는지를 보여줍니다.
    private fun initRetrofitInstance() {
        val client: OkHttpClient = OkHttpClient.Builder()
            .readTimeout(5000, TimeUnit.MILLISECONDS)
            .connectTimeout(5000, TimeUnit.MILLISECONDS)
            // 로그캣에 okhttp.OkHttpClient로 검색하면 http 통신 내용을 보여줍니다.
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addNetworkInterceptor(XAccessTokenInterceptor()) // JWT 자동 헤더 전송
            .build()

        // sRetrofit 이라는 전역변수에 API url, 인터셉터, Gson을 넣어주고 빌드해주는 코드
        // 이 전역변수로 http 요청을 서버로 보내면 됩니다.
        sRetrofit = Retrofit.Builder()
            .baseUrl(API_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun initNRetrofitInstance() {
        val client: OkHttpClient = OkHttpClient.Builder()
            .readTimeout(5000, TimeUnit.MILLISECONDS)
            .connectTimeout(5000, TimeUnit.MILLISECONDS)
            // 로그캣에 okhttp.OkHttpClient로 검색하면 http 통신 내용을 보여줍니다.
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addNetworkInterceptor(XAccessTokenInterceptor()) // JWT 자동 헤더 전송
            .build()

        // sRetrofit 이라는 전역변수에 API url, 인터셉터, Gson을 넣어주고 빌드해주는 코드
        // 이 전역변수로 http 요청을 서버로 보내면 됩니다.

        nRetrofit = Retrofit.Builder()
            .baseUrl(NAVER_API_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    private fun initKRetrofitInstance() {
        val client: OkHttpClient = OkHttpClient.Builder()
            .readTimeout(5000, TimeUnit.MILLISECONDS)
            .connectTimeout(5000, TimeUnit.MILLISECONDS)
            // 로그캣에 okhttp.OkHttpClient로 검색하면 http 통신 내용을 보여줍니다.
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addNetworkInterceptor(XAccessTokenInterceptor()) // JWT 자동 헤더 전송
            .build()

        // sRetrofit 이라는 전역변수에 API url, 인터셉터, Gson을 넣어주고 빌드해주는 코드
        // 이 전역변수로 http 요청을 서버로 보내면 됩니다.

        kRetrofit = Retrofit.Builder()
            .baseUrl(KAKAO_API_RUL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
