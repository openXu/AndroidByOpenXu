package com.openxu.register

import android.util.Log
import com.openxu.core.utils.FLog
import com.openxu.libs.datasource.bean.Article
import com.openxu.libs.datasource.bean.Pagination
import com.openxu.libs.datasource.bean.Tree
import com.openxu.libs.datasource.remote.ApiResult
import com.openxu.libs.datasource.remote.RetrofitClient
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SystemRemoteRepository : ISystemRepository {

    /**
     * 1. 展示回调嵌套，回调地狱
     */
    fun getArticleList(responseBack: (result: Pagination<Article>?) -> Unit) {
        /**1. 获取文章树结构*/
        val call:Call<ApiResult<MutableList<Tree>>> = RetrofitClient.apiService.getTree()
        //同步（需要自己手动切换线程）
        //val response : Response<ApiResult<MutableList<Tree>>> = call.execute()
        //异步回调
        call.enqueue(object : Callback<ApiResult<MutableList<Tree>>> {
            override fun onFailure(call: Call<ApiResult<MutableList<Tree>>>, t: Throwable) {
            }
            override fun onResponse(call: Call<ApiResult<MutableList<Tree>>>, response: Response<ApiResult<MutableList<Tree>>>) {
                FLog.v("请求文章树结构成功："+response.body())
                val treeid = response.body()?.data?.get(0)?.id
                //当treeid不为null执行
                treeid?.let {
                    /**2. 获取某个文章数下的第一页文章*/
                    RetrofitClient.apiService.getArticleList(0, treeid)
                            .enqueue(object : Callback<ApiResult<Pagination<Article>>> {
                                override fun onFailure(call: Call<ApiResult<Pagination<Article>>>, t: Throwable) {
                                }
                                override fun onResponse(call: Call<ApiResult<Pagination<Article>>>, response: Response<ApiResult<Pagination<Article>>>) {
                                    //返回获取的文章列表
                                    responseBack(response.body()?.data)
                                }
                            })
                }
            }
        })
    }

    /**
     * 2. Retrofit+RxJava消除回调嵌套
     */
    fun getArticleListByRx(responseBack: (result: Pagination<Article>?) -> Unit) {
        /**1. 获取文章树结构*/
        val observable1: Observable<ApiResult<MutableList<Tree>>> = RetrofitClient.apiService.getTreeByRx()
        observable1.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                //使用当前Observable发出的值调用给定的Consumer，然后将其转发给下游
                .doOnNext({
                    FLog.v("1请求文章树成功，切换到主线程处理数据：${Thread.currentThread()}")
                })
                .observeOn(Schedulers.io())
                .flatMap({
                    FLog.v("2请求文章树成功，IO线程中获取接口1的数据，然后将被观察者变换为接口2的Observable：${Thread.currentThread()}")
                    if(it?.errorCode == 0){
                        //当treeid不为null执行
                        it?.data?.get(0)?.id?.let { it1 -> RetrofitClient.apiService.getArticleListByRx(0, it1) }
                    }else{
                        //请求错误的情况，发射一个Error
                        Observable.error({
                            Throwable("获取文章树失败：${it.errorCode}:${it.errorMsg}")
                        })
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object: Observer<ApiResult<Pagination<Article>>> {
                    override fun onComplete() {}
                    override fun onSubscribe(d: Disposable?) {}
                    override fun onNext(t: ApiResult<Pagination<Article>>?) {
                        FLog.v("3请求文章列表成功：${t?.data}")
                        responseBack(t?.data)
                    }
                    override fun onError(e: Throwable?) {
                        FLog.e("3请求失败：${e?.message}")
                    }
                })
    }

    /**
     * 3. 同步方式调用
     */


    override fun getTree(responseBack: (result:MutableList<Tree>?) -> Unit) {

//        RetrofitClient.apiService.getTreeByCoroutines()

    }




}