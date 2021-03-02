package com.openxu.register

import com.openxu.libs.datasource.remote.RetrofitClient


class RegisterRepository {


    suspend fun register(username: String, password: String, repassword: String) =
        RetrofitClient.apiService.register(username, password, repassword).apiData()

}