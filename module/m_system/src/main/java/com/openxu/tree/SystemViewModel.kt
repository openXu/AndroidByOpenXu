package com.openxu.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.openxu.core.base.BaseViewModel
import com.openxu.core.utils.FLog
import com.openxu.core.utils.toasty.FToast
import com.openxu.libs.datasource.bean.Tree
import com.openxu.libs.datasource.remote.ApiException
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.JsonEncodingException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLHandshakeException

/**
 * Author: openXu
 * Time: 2021/2/26 12:23
 * class: SystemViewModel
 * Description:
 */
class SystemViewModel : BaseViewModel(){
    private val remoteRepository : ISystemRepository by lazy { SystemRemoteRepository() }
    private val localRepository : ISystemRepository by lazy { SystemLocalRepository() }

    val treeList = MutableLiveData<List<Tree>>()

    fun getArticleTree() {
        //这里怎么写？？
        //先从本地拿，没有再从服务器去
        //本地
//        localRepository.getArticleTree()

        remoteRepository.getArticleTree{
            treeList.value = it
        }

    }

    /*fun register(account: String, password: String, confirmPassword: String) {
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
        }
    }*/


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