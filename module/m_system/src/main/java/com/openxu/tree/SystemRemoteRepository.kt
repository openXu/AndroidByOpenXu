package com.openxu.register

import com.openxu.core.utils.FLog
import com.openxu.libs.datasource.bean.Tree
import com.openxu.libs.datasource.remote.ApiResult
import com.openxu.libs.datasource.remote.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SystemRemoteRepository : ISystemRepository {

    override fun getArticleTree(responseBack: (result:MutableList<Tree>?) -> Unit) {
        val call:Call<ApiResult<MutableList<Tree>>> = RetrofitClient.apiService.getArticleTrees1()
        //同步（需要自己手动切换线程）
//        val response : Response<ApiResult<MutableList<Tree>>> = call.execute()
        //异步回调
        call.enqueue(object : Callback<ApiResult<MutableList<Tree>>> {
            override fun onFailure(call: Call<ApiResult<MutableList<Tree>>>, t: Throwable) {
                FLog.w("请求失败："+Thread.currentThread())
                FLog.w("请求失败："+t.message)
            }
            override fun onResponse(
                call: Call<ApiResult<MutableList<Tree>>>,
                response: Response<ApiResult<MutableList<Tree>>>) {
                FLog.v("请求成功："+Thread.currentThread())
                FLog.v("请求成功："+response.body())
                responseBack(response.body()?.apiData())
            }
        })

    }




}