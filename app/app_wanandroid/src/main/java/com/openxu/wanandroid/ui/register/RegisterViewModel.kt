package com.openxu.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.openxu.core.base.BaseViewModel
import com.openxu.core.network.ApiException
import com.openxu.core.utils.FLog
import com.openxu.core.utils.toasty.FToast
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.JsonEncodingException
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLHandshakeException

/**
 * Author: openXu
 * Time: 2021/2/26 12:23
 * class: LoginVM
 * Description:
 */
class RegisterViewModel : BaseViewModel(){
    private val registerRepository by lazy { RegisterRepository() }
    val submitting = MutableLiveData<Boolean>()
    val registerResult = MutableLiveData<Boolean>()

    fun register(account: String, password: String, confirmPassword: String) {
        viewModelScope.launch {  }
        GlobalScope.launch(Dispatchers.IO) {
            FLog.w("协程开始请求数据："+Thread.currentThread())
            try {
                val userInfo = registerRepository.register(account, password, confirmPassword)
                FLog.w("数据请求完毕："+userInfo)
                FLog.w("数据请求完毕："+Thread.currentThread())
                registerResult.value = true
            } catch (e: Exception) {
                launch(Dispatchers.Main) {
                    onError(e, true)
                }
            }


//            launch(Dispatchers.Main) {
//                btnLogin.text = result.data.username
//            }
        }
    }


    /**
     * 统一处理错误
     * @param e 异常
     * @param showErrorToast 是否显示错误吐司
     */
    private fun onError(e: Exception, showErrorToast: Boolean) {
        FLog.w("统一错误处理："+Thread.currentThread()+"   "+e)
        when (e) {
            is ApiException -> {
                when (e.code) {
                    -1001 -> {
                        // 登录失效，清除用户信息、清除cookie/token
//                        UserInfoStore.clearUserInfo()
//                        RetrofitClient.clearCookie()
//                        loginStatusInvalid.value = true
                    }
                    // 其他api错误
//                    -1 -> if (showErrorToast) App.instance.showToast(e.message)
                    // 其他错误
                    else ->{
                        if (showErrorToast)
                            FToast.error(e.message)
                    }
                }
            }
            // 网络请求失败
            is ConnectException,
            is SocketTimeoutException,
            is UnknownHostException,
            is HttpException,
            is SSLHandshakeException ->
                FToast.error("网络请求失败")
            // 数据解析错误
            is JsonDataException, is JsonEncodingException ->
                if (showErrorToast)
                    FToast.error("数据解析错误")
            // 其他错误
            else ->
                if (showErrorToast)
                    FToast.error(e.message?: return)
        }
    }

}