package com.openxu.login

import android.content.Intent
import android.graphics.Color
import com.openxu.core.base.BaseActivity
import com.openxu.core.base.BaseVmActivity
import com.openxu.login.databinding.ActivityLoginBinding
import com.openxu.register.RegisterActivity
import kotlinx.android.synthetic.main.activity_login.*

/**
 * Author: openXu
 * Time: 2021/2/26 11:39
 * class: LoginActivity
 * Description:
 */
class LoginActivity : BaseVmActivity<ActivityLoginBinding, LoginViewModel>() {


    override fun layoutRes() = R.layout.activity_login


    override fun initView() {
        super.initView()

        btnLogin.setOnClickListener{
            val account = editAccount.text.toString().trim()
            val pwd = editPassword.text.toString().trim()
            //when 也可以用来取代 if-else if链。 如果不提供参数，所有的分支条件都是简单的布尔表达式，而当一个分支的条件为真时则执行该分支：
            when {
                account.isEmpty() -> inputAccount.error = getString(R.string.account_can_not_be_empty)
                pwd.isEmpty() -> inputPassword.error = getString(R.string.password_can_not_be_empty)
                else -> viewModel.login(account, pwd)
            }
        }

        btnRegister.setOnClickListener{
            startActivity(Intent(this, RegisterActivity::class.java))
        }


    }





}