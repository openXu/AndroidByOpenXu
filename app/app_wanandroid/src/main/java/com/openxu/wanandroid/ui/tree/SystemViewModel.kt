package com.openxu.wanandroid.ui.tree

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.openxu.core.base.BaseViewModel
import com.openxu.core.bean.Article
import com.openxu.core.bean.Pagination
import com.openxu.core.bean.Category
import com.openxu.core.network.ApiResult
import com.openxu.core.network.ApiService
import com.openxu.core.network.RetrofitClient
import com.openxu.core.utils.FLog
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

/**
 * Author: openXu
 * Time: 2021/2/26 12:23
 * class: SystemViewModel
 * Description:
 */
class SystemViewModel : BaseViewModel(){
    private val remoteRepository : SystemRemoteRepository by lazy { SystemRemoteRepository() }
    private val localRepository : ISystemRepository by lazy { SystemLocalRepository() }

    val categoryList = MutableLiveData<MutableList<Category>>()
    val articlePage = MutableLiveData<Pagination<Article>>()

   /* fun getTree() {
        viewModelScope
        //这里怎么写？？
        //先从本地拿，没有再从服务器去
        //本地
//        localRepository.getArticleTree()
//        remoteRepository.getArticleTree{
//            treeList.value = it
//        }
    }*/
    /**
     * 1. 展示回调嵌套，回调地狱
     */
    fun getArticleListByCallBack() {
        /**1. 获取文章目录结构*/
        val call: Call<ApiResult<MutableList<Category>>> = RetrofitClient.apiService.getTreeByCall()
        //同步调用（需要自己手动切换线程）
        //val response : Response<ApiResult<MutableList<Tree>>> = call.execute()
        //异步调用，自动切换线程，通过回调拿到数据
        call.enqueue(object : Callback<ApiResult<MutableList<Category>>> {
            //★ 第一层回调
            override fun onFailure(call: Call<ApiResult<MutableList<Category>>>, t: Throwable) {
                FLog.w("抛异常$t")
            }
            override fun onResponse(call: Call<ApiResult<MutableList<Category>>>, response: Response<ApiResult<MutableList<Category>>>) {
                FLog.v("请求文章目录成功："+response.body())
                categoryList.value = response.body()?.data
                //获取文章目录的第一个类目id
                val treeid = response.body()?.data?.get(0)?.id
                treeid?.let {
                    /**2. 获取第一个目录下的第一页文章列表*/
                    RetrofitClient.apiService.getArticleListByCall(0, treeid)
                            .enqueue(object : Callback<ApiResult<Pagination<Article>>> {
                                //★ 第二层回调
                                override fun onFailure(call: Call<ApiResult<Pagination<Article>>>, t: Throwable) {
                                }
                                override fun onResponse(call: Call<ApiResult<Pagination<Article>>>, response: Response<ApiResult<Pagination<Article>>>) {
                                    //返回获取的文章列表
                                    articlePage.value = response.body()?.data
                                }
                            })
                }
            }
        })
    }

    /**
     * 2. Retrofit+RxJava消除回调嵌套
     */
    fun getArticleListByRx() {
        /**1. 获取文章目录结构*/
        val observable1: Observable<ApiResult<MutableList<Category>>> = RetrofitClient.apiService.getTreeByRx()
        observable1.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                //使用当前Observable发出的值调用给定的Consumer，然后将其转发给下游
                .doOnNext {
                    FLog.v("1请求文章目录成功，切换到主线程处理数据：${Thread.currentThread()}")
                }
                .observeOn(Schedulers.io())
                .flatMap {
                    FLog.v("2请求文章目录成功，IO线程中获取接口1的数据，然后将被观察者变换为接口2的Observable：${Thread.currentThread()}")
                    if(it?.errorCode == 0){
                        categoryList.value = it?.data!!
                        it?.data?.get(0)?.id?.let { it1 -> RetrofitClient.apiService.getArticleListByRx(0, it1) }
                    }else{
                        Observable.error { Throwable("获取文章目录失败：${it.errorCode}:${it.errorMsg}")}
                    }
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object: Observer<ApiResult<Pagination<Article>>> {
                    override fun onComplete() {}
                    override fun onSubscribe(d: Disposable?) {}
                    override fun onNext(result: ApiResult<Pagination<Article>>?) {
                        FLog.v("3请求文章列表成功：${result?.data}")
                        articlePage.value = result?.data
                    }
                    override fun onError(e: Throwable?) {
                        FLog.e("3请求失败：${e?.message}")
                    }
                })
    }

    fun getArticleList() {
        /*
        //同步调用接口1
        val response1 : Response<ApiResult<MutableList<Tree>>> = RetrofitClient.apiService.getTree().execute()
        val cid = response1.body()?.data?.get(0)?.id
        if(cid!=null){
            //同步调用接口2
            val response2 : Response<ApiResult<Pagination<Article>>> = RetrofitClient.apiService.getArticleList(0, cid).execute()
            page.value = response2.body()?.data!!
        }
        */

        viewModelScope.launch {
            FLog.v("1开启协程：${Thread.currentThread()}")
            val category : ApiResult<MutableList<Category>> = RetrofitClient.apiService.getTree()
            categoryList.value = category.data!!
            FLog.v("2请求文章目录成功：${category.data}")

            category?.data?.get(0)?.id?.let {
                FLog.v("3开始请求第二个接口：${Thread.currentThread()}")
                val pageResult : ApiResult<Pagination<Article>> = RetrofitClient.apiService.getArticleList(0, it)
                FLog.v("4请求文章列表成功：${pageResult.data}")
                articlePage.value = pageResult.data!!
            }
        }
    }

    val reloadStatus = MutableLiveData<Boolean>()

    suspend fun plugsOne(fial:Boolean, base:Int) : Int = withContext(Dispatchers.IO){
        if(fial)
            throw Exception("抛异常了")
        else
            return@withContext base+1
    }
    /*suspend fun plugsOne(fial:Boolean, base:Int) = suspendCancellableCoroutine<Int> {continuation->
        Thread{
            Thread.sleep(1000)
            if(fial) {
                continuation.resumeWithException(Exception("抛异常了")) //以异常恢复协程
            }else{
                continuation.resume(1)
            }
        }.start()
    }*/
    class AndroidDontKillExceptionHandler :
            AbstractCoroutineContextElement(CoroutineExceptionHandler), CoroutineExceptionHandler{
        override fun handleException(context: CoroutineContext, exception: Throwable) {
            Log.w("openXu", "根协程异常处理器，避免应用被kill。捕获到异常:$exception")
        }
    }
    fun getTree(){
        //示例1： 下面的程序运行后会闪退
        viewModelScope.launch(AndroidDontKillExceptionHandler()) {
            try{
                var deferred1:Deferred<Int>? = null
                var deferred2:Deferred<Int>? = null
                //async{}外层嵌套coroutineScope{}让异常抛出，不向上传给根协程
                try {
                    deferred1 = coroutineScope {
                        async {
                            plugsOne(true, 0)
                        }
                    }
                }catch (e:Exception){
                    Log.w("openXu","避免async异常传递给根协程导致程序退出：${e.message}")
                }
                try {
                    deferred2 = coroutineScope {
                        async {
                            plugsOne(false, 0)
                        }
                    }
                }catch (e:Exception){
                    Log.w("openXu","避免async异常传递给根协程导致程序退出：${e.message}")
                }
                var data1 : Int? = null
                var data2 : Int? = null
                //对await()分别try-catch，避免异常导致其他任务执行获取不到结果
                try {
                    data1 = deferred1?.await()
                }catch (e:Exception){
                    //交给统一错误处理方法提示用户
                    //onError(e, showErrorToast)
                }
                try {
                    data2 = deferred2?.await()
                }catch (e:Exception){
                    //交给统一错误处理方法提示用户
                    //onError(e, showErrorToast)
                }
                Log.v("openXu", "获取数据成功:$data1  $data2")
            }catch (e:Exception){
                //捕获协程代码块中其他异常，统一处理
                //onError(e, showErrorToast)
            }
        }
        /*
        launch(
            block = {
                reloadStatus.value = false
                val category : ApiResult<MutableList<Category>> = RetrofitClient.apiService.getTree()
                category.data?.let { categoryList.value = it }
                categoryList.value = category.data!!
                FLog.v("2请求文章目录成功：${category.data}")

                FLog.w("------------viewModelScope：$viewModelScope")
                FLog.w("------------coroutineContext：${viewModelScope.coroutineContext}")
                val job = viewModelScope.coroutineContext[Job]  //SupervisorJobImpl
                FLog.w("------------Job：$job")
                var clazz = job?.javaClass?.kotlin
                FLog.w("------------clazz：$clazz")
                clazz?.let {
                    FLog.w("------------simpleName：${clazz.simpleName}")
                    FLog.w("------------qualifiedName：${clazz.qualifiedName}")
                }

                val clazz1 = clazz?.java
                val declaredFields = clazz1?.declaredFields
                FLog.w("------------${declaredFields?.size}")
                if (declaredFields != null) {
                    for(f in declaredFields){
                        FLog.w("-111-----------${f.name}：${f.get(job)}")
                    }
                }

            },
            error = {
                reloadStatus.value = true
            }
        )*/


    }

    fun get111() {
        /**1. 获取文章目录结构*/
        val call: Call<ApiResult<MutableList<Category>>> = RetrofitClient.apiService.getTreeByCall()
        call.execute()

    }


}