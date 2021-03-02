package com.openxu.register

import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import com.openxu.core.base.BaseActivity
import com.openxu.core.utils.FLog
import com.openxu.login.R
import com.openxu.login.databinding.ActivityRegisterBinding
import kotlinx.android.synthetic.main.activity_register.*

/**
 * Author: openXu
 * Time: 2021/3/1 11:43
 * class: RegisterActivity
 * Description:
 */
class RegisterActivity : BaseActivity<ActivityRegisterBinding, RegisterViewModel>() {


    override fun layoutRes() = R.layout.activity_register


    override fun initView() {
        super.initView()

        editAccount.setText("openXu")
        editPassword.setText("123456")
        editConfirmPssword.setText("123456")

        //layout中不可跨模块使用include，编译报错：Unresolved reference: xxx
        mBinding.titleLayout.ivBack.setOnClickListener { finish() }

        editConfirmPssword.setOnEditorActionListener{view, actionId, event->
            when(actionId){
                EditorInfo.IME_ACTION_GO -> {
                    btnRegister.performClick()
                    true
                }
                else -> false
            }
        }

        btnRegister.setOnClickListener{
            val account = editAccount.text.toString().trim()
            val password = editPassword.text.toString().trim()
            val confirmPassword = editConfirmPssword.text.toString().trim()

            when {
                account.isEmpty() -> inputAccount.error = getString(R.string.account_can_not_be_empty)
                account.length < 3 -> inputAccount.error =
                    getString(R.string.account_length_over_three)
                password.isEmpty() -> inputPassword.error =
                    getString(R.string.password_can_not_be_empty)
                password.length < 6 -> inputPassword.error =
                    getString(R.string.password_length_over_six)
                confirmPassword.isEmpty() -> inputConfirmPssword.error =
                    getString(R.string.confirm_password_can_not_be_empty)
                password != confirmPassword -> inputConfirmPssword.error =
                    getString(R.string.two_password_are_inconsistent)
                else -> mViewModel.register(account, password, confirmPassword)
            }
        }

    }

    override fun observe() {
        super.observe()
        mViewModel.submitting.observe(this, Observer {
            if (it) showProgressDialog(R.string.registerring) else dismissProgressDialog()
        })
        mViewModel.registerResult.observe(this@RegisterActivity, Observer {
            if (it) {

                FLog.v("》》》》》》》》》》》》》》》》》注册成功")

//                ActivityHelper.finish(LoginActivity::class.java, RegisterActivity::class.java)
            }
        })
    }



}