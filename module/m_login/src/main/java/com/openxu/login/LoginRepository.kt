package com.openxu.login

import com.openxu.libs.net.NetworkManager


/**
 * Created by xiaojianjun on 2019-11-24.
 */
class LoginRepository {
    suspend fun login(username: String, password: String) =
        NetworkManager.getInstance().apiService.login(username, password).apiData()
}