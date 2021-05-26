package com.openxu.wanandroid.ui.tree

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.CheckedTextView
import androidx.core.view.updateLayoutParams
import androidx.lifecycle.Observer
import com.openxu.core.base.BaseVmFragment
import com.openxu.core.bean.Article
import com.openxu.core.bean.Category
import com.openxu.wanandroid.R
import com.openxu.wanandroid.databinding.FragmentArticleListBinding
import com.openxu.core.common.adapter.CommandRecyclerAdapter
import com.openxu.core.common.adapter.RecyclerViewPaginator
import com.openxu.core.common.adapter.ViewHolder
import com.openxu.core.ext.dpToPx
import com.openxu.core.ext.htmlToSpanned
import com.openxu.core.utils.FLog
import kotlinx.android.synthetic.main.fragment_article_list.reloadView
import kotlinx.android.synthetic.main.item_article_simple.view.*
import kotlinx.coroutines.*
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.random.Random

/**
 * Author: openXu
 * Time: 2021/3/1 11:43
 * class: ArticleListFragment
 * Description: 带分类的 文章列表
 */
class ArticleListFragment : BaseVmFragment<FragmentArticleListBinding, ArticleListViewModel>() {

    companion object {
        const val CATEGORY_LIST = "categoryList"
        fun newInstance(categoryList: ArrayList<Category>): ArticleListFragment {
            return ArticleListFragment().apply {
                arguments = Bundle().apply {putParcelableArrayList(CATEGORY_LIST, categoryList) }
            }
        }

    }
    private var checkedPosition = 0
    private lateinit var categoryList: List<Category>
    private lateinit var adapter: CommandRecyclerAdapter<Article>

    override fun layoutRes() = R.layout.fragment_article_list

    override fun initView() {
        super.initView()
        categoryList = arguments?.getParcelableArrayList(CATEGORY_LIST)!!

        //下拉刷新
        binding.swipeRefreshLayout.run {
            setColorSchemeResources(R.color.textColorPrimary)
            setProgressBackgroundColorSchemeResource(R.color.colorPrimary)
            setOnRefreshListener {
              lazyLoadData()
            }
        }
        binding.reloadView.btnReload.setOnClickListener {
            viewModel.getArticleList(categoryList[checkedPosition].id!!, true)
        }

        binding.recyclerviewCategory.adapter = object: CommandRecyclerAdapter<Category>(context,
                R.layout.item_category_sub, categoryList){

            override fun convert(holder: ViewHolder, item: Category, position: Int) {
                val textView = holder.getView<CheckedTextView>(R.id.tv_catagory)
                textView.text = item.name.htmlToSpanned()
                textView.isChecked = checkedPosition == holder.adapterPosition
                holder.itemView.run {
//                    textView.text = item.name.htmlToSpanned()
//                    textView.isChecked = checkedPosition == holder.adapterPosition
                    updateLayoutParams<ViewGroup.MarginLayoutParams> {
                        marginStart = if (holder.adapterPosition == 0) 8.dpToPx().toInt() else 0.dpToPx().toInt()
                    }
                }
            }
            override fun onItemClick(data: Category, position: Int) {
                checkedPosition = position
                notifyDataSetChanged()
                viewModel.getArticleList(categoryList[checkedPosition].id!!, true)
            }

        }
        adapter = object: CommandRecyclerAdapter<Article>(context,
                R.layout.item_article_simple, null){
            override fun convert(holder: ViewHolder, item: Article, position: Int) {
                holder.itemView.run {
                    tv_author.text = when {
                        !item.author.isNullOrEmpty() -> {
                            item.author
                        }
                        !item.shareUser.isNullOrEmpty() -> {
                            item.shareUser
                        }
                        else -> "匿名"
                    }
                    tv_fresh.visibility = if(item.fresh) View.VISIBLE else View.INVISIBLE
                    tv_title.text = item.title.htmlToSpanned()
                    tv_time.text = item.niceDate
                    iv_collect.isSelected = item.collect
                }
            }
            override fun onItemClick(data: Article, position: Int) {
            }
        }

        binding.recyclerView.adapter = adapter
        //分页
        RecyclerViewPaginator(
            recyclerView = binding.recyclerView,
            isLoading = { viewModel.isLoading.get() },
            loadMore = { viewModel.getArticleList(categoryList[checkedPosition].id!!, false) },
            onLast = { false }
        )

    }
    override fun lazyLoadData() {
        viewModel.getArticleList(categoryList[checkedPosition].id!!, true)

    }

    override fun observe() {
        super.observe()

        viewModel.articleList.observe(viewLifecycleOwner, Observer{
            adapter.data = it
        })
        viewModel.reloadStatus.observe(viewLifecycleOwner, Observer {
            reloadView.visibility = if (it) View.VISIBLE else View.GONE
        })
    }


    override fun showProgressDialog(message: String?) {
        binding.swipeRefreshLayout.isRefreshing = true
    }
    override fun dismissProgressDialog() {
        binding.swipeRefreshLayout.isRefreshing = false
    }
}