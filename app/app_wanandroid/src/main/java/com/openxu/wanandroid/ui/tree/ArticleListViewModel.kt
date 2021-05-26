package com.openxu.wanandroid.ui.tree

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import com.openxu.core.base.BaseViewModel
import com.openxu.core.bean.Article
import com.openxu.core.network.RetrofitClient
import com.openxu.core.utils.FLog
import kotlinx.coroutines.Job

/**
 * Author: openXu
 * Time: 2021/2/26 12:23
 * class: ArticleListFragmentVM
 * Description:
 */
class ArticleListViewModel : BaseViewModel(){

    val articleList = MutableLiveData<MutableList<Article>>()
//    val loadMoreStatus = MutableLiveData<LoadMoreStatus>()
    val reloadStatus = MutableLiveData<Boolean>()
    val isLoading: ObservableBoolean = ObservableBoolean(false)

    companion object {
        const val INITIAL_PAGE = 0
    }

    private var page = INITIAL_PAGE
    private var id: Int = -1
    private var refreshJob: Job? = null

    fun getArticleList(cid:Int, refresh: Boolean) {
        if (cid != id || refresh) {
            cancelJob(refreshJob)
            id = cid
            page = INITIAL_PAGE
            articleList.value = mutableListOf()
            FLog.w("刷新列表：$cid  $page")
        }else{
            FLog.w("加载更多：$cid   $page")
        }

        refreshJob = launch(
                showDialog = page == INITIAL_PAGE,
                block = {
                    isLoading.set(true)
                    reloadStatus.value = false
                    val pagination = RetrofitClient.apiService.getArticleList(page, cid).apiData()
                    page = pagination.curPage
                    //结果类加
                    articleList.value = (articleList.value!!.toList() + pagination.datas).toMutableList()
                    FLog.e("当前数据：${articleList.value!!.size}")
                    isLoading.set(false)
                },
                error = {
                    reloadStatus.value = articleList.value?.isEmpty()
                    isLoading.set(false)
                }
        )
    }





}