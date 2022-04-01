package com.softsquared.template.kotlin.src.main.login

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.config.BaseActivity
import com.softsquared.template.kotlin.databinding.ActivityLoginBinding
import com.softsquared.template.kotlin.src.main.join.JoinActivity
import com.softsquared.template.kotlin.src.main.login.models.LoginResponse
import java.util.regex.Pattern

class LoginActivity : BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate),
    LoginActivityView {

    private val validation by lazy { "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$" }
    var pwVisibility = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun checkNotEmail(email: String): Boolean {
        if (Pattern.matches(validation, email)) {
            return false
        }
        return true
    }

    private fun init() {
        with(binding) {
            //이메일 포커스 -> border 색 변경
            etEmail.setOnFocusChangeListener { _, hasFocus ->
                //텍스트 입력 시, 취소 사진이 보여야함.
                //포커스가 된다면 테두리 색상 변경
                if (hasFocus) {
                    lyEmail.setBackgroundResource(R.drawable.bg_login_et_selected)
                    ivEmail.visibility = View.VISIBLE
                } else {
                    lyEmail.setBackgroundResource(R.drawable.bg_login_et)
                    ivEmail.visibility = View.INVISIBLE
                }
            }

            //이메일 입력란 변화에 따른 이벤트
            etEmail.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (s != "") {
                        ivCancel.visibility = View.VISIBLE
                    } else {
                        ivCancel.visibility = View.INVISIBLE
                    }
                }

                override fun afterTextChanged(s: Editable?) {

                }

            })

            //비밀번호 포커스 -> border 색 변경
            etPw.setOnFocusChangeListener { _, hasFocus ->
                //텍스트 입력 시, 비밀번호 가리는 사진이 사용가능, 누르면 색상변화
                //포커스가 된다면 테두리 색상 변경
                if (hasFocus) {
                    lyPw.setBackgroundResource(R.drawable.bg_login_et_selected)
                } else {
                    lyPw.setBackgroundResource(R.drawable.bg_login_et)
                }
            }

            //비밀번호 보여주는 view 클릭 이벤트
            ivPw.setOnClickListener {
                //텍스트가 존재하고 꺼져있다면
                if (etPw.text.toString() != "" && !pwVisibility) {
                    ivPw.setImageResource(R.drawable.ic_login_pw)
                    etPw.inputType = InputType.TYPE_CLASS_TEXT
                    etPw.setSingleLine()
                    pwVisibility = true
                }
                //켜있다면 꺼주기
                else if (pwVisibility) {
                    ivPw.setImageResource(R.drawable.ic_login_non_pw)
                    etPw.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
                    etPw.setSingleLine()
                    etPw.transformationMethod = PasswordTransformationMethod.getInstance()
                    pwVisibility = false
                }
            }

            ivEmail.setOnClickListener {
                etEmail.setText("")
            }

            //로그인
            btLogin.setOnClickListener {
                //현재 이메일 형식 구하기
                val notEmail = checkNotEmail(etEmail.text.toString())

                //아이디와 비밀번호가 비어있음.
                if (etEmail.text.toString() == "" && etPw.text.toString() == "") {
                    tvLoginFail.text = "아이디를 입력해주세요."
                    tvLoginFail.visibility = View.VISIBLE
                    Handler(mainLooper).postDelayed({
                        tvLoginFail.visibility = View.INVISIBLE
                    }, 2000)
                }
                //아이디는 입력됨, 근데 형식이 맞지 않음.
                else if (notEmail) {
                    tvLoginFail.text = "아이디는 이메일주소 형식으로 입력해주세요."
                    tvLoginFail.visibility = View.VISIBLE
                    Handler(mainLooper).postDelayed({
                        tvLoginFail.visibility = View.INVISIBLE
                    }, 2000)
                }
                //비밀번호만 입력됨.
                else if (etPw.text.toString() == "" && etEmail.text.toString() != "") {
                    tvLoginFail.text = "비밀번호를 입력해주세요."
                    tvLoginFail.visibility = View.VISIBLE
                    Handler(mainLooper).postDelayed({
                        tvLoginFail.visibility = View.INVISIBLE
                    }, 2000)
                }
                //아이디는 있고 비밀번호가 없음
                else if (!notEmail && etPw.text.toString() == "") {
                    tvLoginFail.text = "비밀번호를 입력해주세요."
                    tvLoginFail.visibility = View.VISIBLE
                    Handler(mainLooper).postDelayed({
                        tvLoginFail.visibility = View.INVISIBLE
                    }, 2000)
                } else {
                    //만약 형식을 다 지켰다면?
                    LoginActivityService(this@LoginActivity).tryPostLogin(
                        etEmail.text.toString(),
                        etPw.text.toString()
                    )
                }
            }

            //아이디 찾기 버튼
            tvFindId.setOnClickListener {

            }

            //비밀번호 찾기 버튼
            tvFindPw.setOnClickListener {

            }

            //회원 가입 버튼
            tvJoin.setOnClickListener {
                startActivity(Intent(this@LoginActivity, JoinActivity::class.java))
            }

            //취소 버튼
            ivCancel.setOnClickListener {
                finish()
            }
        }
    }

    private fun loginFailDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("입력하신 아이디 또는 비밀번호가 일치하지\n않습니다.")
        builder.setPositiveButton("확인") { dialog, _ -> dialog.dismiss() }
        builder.show()
    }

    override fun postLoginSuccess(data: LoginResponse) {
        ApplicationClass.sSharedPreferences.edit().putString("X-ACCESS-TOKEN", data.result.jwt)
            .apply()
        ApplicationClass.sSharedPreferences.edit()
            .putString("REFRESH-TOKEN", data.result.refreshToken).apply()

        ApplicationClass.X_ACCESS_TOKEN = data.result.jwt
        ApplicationClass.REFRESH_TOKEN = data.result.refreshToken
        showCustomToast("로그인 되었습니다.")
        finish()
    }

    override fun postLoginFailure(message: String) {
        loginFailDialog()
    }
}