package com.openxu.core.common.adapter

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.openxu.core.utils.FLog

/**
 * Author: openXu
 * Time: 2021/4/1 9:39
 * class: RecyclerViewPaginator
 * Description:
 */
class RecyclerViewPaginator(
    private val recyclerView: RecyclerView,
    private val isLoading: () -> Boolean,
    private val loadMore: (Int) -> Unit,
    private val onLast: () -> Boolean = { true }
) : RecyclerView.OnScrollListener() {

    var threshold = 1
    var currentPage: Int = 0
    var endWithAuto = false

    init {
        recyclerView.addOnScrollListener(this)
    }


    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val layoutManager = recyclerView.layoutManager
        layoutManager?.let {
            val visibleItemCount = it.childCount
            val totalItemCount = it.itemCount
            val firstVisibleItemPosition = when (layoutManager) {
                is LinearLayoutManager -> layoutManager.findLastVisibleItemPosition()
                is GridLayoutManager -> layoutManager.findLastVisibleItemPosition()
                else -> return
            }
            FLog.d("监听RecyclerView滚动，可见条数$visibleItemCount  总条数$totalItemCount  第一个可见位置$firstVisibleItemPosition")
            if (onLast() || isLoading()) return
            if (endWithAuto) {
                if (visibleItemCount + firstVisibleItemPosition == totalItemCount) return
            }

            if ((visibleItemCount + firstVisibleItemPosition + threshold) >= totalItemCount) {
                loadMore(++currentPage)
            }
        }
    }

    fun resetCurrentPage() {
        this.currentPage = 0
    }
}