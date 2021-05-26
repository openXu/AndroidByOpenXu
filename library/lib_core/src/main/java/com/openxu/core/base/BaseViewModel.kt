package com.openxu.core.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openxu.core.network.ApiException
import com.openxu.core.utils.FLog
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.JsonEncodingException
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLHandshakeException

/**
 * Author: openXu
 * Time: 2021/2/26 18:07
 * class: BaseViewModel
 * Description:
 */

typealias Block<T> = suspend (CoroutineScope) -> T
typealias Error = suspend (Exception) -> Unit
typealias Cancel = suspend (Exception) -> Unit

open class BaseViewModel : ViewModel() {

    var dialog = MutableLiveData<Boolean>() //请求数据显示进度框
    var wToast = MutableLiveData<String>()      //弹出警告土司

    /**
     * 创建并执行协程
     * @param block 协程中执行
     * @param error 错误时执行
     * @param cancel 取消时只需
     * @param showErrorToast 是否弹出错误吐司
     * @return Job
     */
    protected fun launch(
            showDialog: Boolean = true,
            showErrorToast: Boolean = true,
            block: Block<Unit>,
            error: Error? = null,
            cancel: Cancel? = null
    ): Job {
        return viewModelScope.launch {
            try {
                if(showDialog)
                    dialog.value = true
                block.invoke(this)
                if(showDialog)
                    dialog.value = false
            } catch (e: Exception) {
                e.printStackTrace()
                FLog.e("block执行异常：$e")
                if(showDialog)
                    dialog.value = false
                when (e) {
                    is CancellationException -> {
                        cancel?.invoke(e)
                    }
                    else -> {
                        onError(e, showErrorToast)
                        error?.invoke(e)
                    }
                }
            }
        }
    }
    protected fun cancelJob(job: Job?) {
        if (job != null && job.isActive && !job.isCompleted && !job.isCancelled) {
            job.cancel()
        }
    }

    /**
     * 统一处理错误
     * @param e 异常
     * @param showErrorToast 是否显示错误吐司
     */
    private fun onError(e: Exception, showErrorToast: Boolean) {
        when (e) {
            //接口业务错误
            is ApiException -> {
                if (showErrorToast) wToast.value = e.message
            }
            // 网络请求失败
            is ConnectException,
            is SocketTimeoutException,
            is UnknownHostException,
            is HttpException,
            is SSLHandshakeException ->
                if (showErrorToast) wToast.value="网络请求失败"
            // 数据解析错误
            is JsonDataException, is JsonEncodingException ->
                wToast.value="数据解析错误"
            else -> // 其他错误
                wToast.value=e.message
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}