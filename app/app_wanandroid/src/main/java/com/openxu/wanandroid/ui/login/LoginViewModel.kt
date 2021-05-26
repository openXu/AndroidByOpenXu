package com.openxu.wanandroid.ui.login

import com.openxu.core.base.BaseViewModel

/**
 * Author: openXu
 * Time: 2021/2/26 12:23
 * class: LoginVM
 * Description:
 */
class LoginViewModel : BaseViewModel(){
    fun login(account: String, password: String) {
       /* launch(
            block = {
                submitting.value = true
                val userInfo = loginRepository.login(account, password)
                UserInfoStore.setUserInfo(userInfo)
                Bus.post(USER_LOGIN_STATE_CHANGED, true)
                submitting.value = false
                loginResult.value = true
            },
            error = {
                submitting.value = false
                loginResult.value = false
            }
        )*/
    }
}