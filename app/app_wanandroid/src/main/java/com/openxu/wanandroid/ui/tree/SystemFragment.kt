package com.openxu.wanandroid.ui.tree

import android.view.View
import android.widget.ImageView
import androidx.lifecycle.Observer
import com.google.android.material.appbar.AppBarLayout
import com.openxu.core.base.BaseVmFragment
import com.openxu.core.ext.toArrayList
import com.openxu.core.utils.FLog
import com.openxu.wanandroid.R
import com.openxu.wanandroid.databinding.FragmentSystemBinding
import com.openxu.wanandroid.ui.MainActivity
import kotlinx.android.synthetic.main.fragment_system.*
import kotlinx.coroutines.*
import retrofit2.*
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.coroutineContext
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

/**
 * Author: openXu
 * Time: 2021/3/1 11:43
 * class: SystemFragment
 * Description:
 */
class SystemFragment : BaseVmFragment<FragmentSystemBinding, SystemViewModel>() {

    private val titles = mutableListOf<String>()
    private val fragments = mutableListOf<ArticleListFragment>()

    override fun layoutRes() = R.layout.fragment_system
    private var currentOffset = 0
    override fun initView() {
        super.initView()
        binding.titleLayout.appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, offset ->
            if (activity is MainActivity && this.currentOffset != offset) {
                //向上滚动，隐藏底部导航。
                //向下滚动，显示
                (activity as MainActivity).animateBottomNavigationView(offset > currentOffset)
                currentOffset = offset
            }
        })
        //重新加载
        binding.reloadView.btnReload.setOnClickListener{
            viewModel.getTree()
        }


    }
    override fun lazyLoadData() {
        viewModel.getTree()
    }

    override fun observe() {
        super.observe()
        viewModel.run {
            //网络不可用，重新加载按钮
            reloadStatus.observe(viewLifecycleOwner, Observer {
                reloadView.visibility = if (it) View.VISIBLE else View.GONE
            })
            categoryList.observe(viewLifecycleOwner, Observer {categoryList->
                binding.titleLayout.tabLayout.visibility = View.VISIBLE
                viewPager.visibility = View.VISIBLE
                //获取子类别不为空的类别
                val categoryList = categoryList.filter {it.children.isNotEmpty()}
                categoryList.forEach {
                    titles.add(it.name)
                    fragments.add(ArticleListFragment.newInstance(it.children.toArrayList()))
                }
                viewPager.adapter = SimpleFragmentPagerAdapter(childFragmentManager, fragments, titles)
//                viewPager.offscreenPageLimit = titles.size
                binding.titleLayout.tabLayout.setupWithViewPager(viewPager)
            })

        }
    }
    override fun showProgressDialog(message: String?) {
        FLog.w("显示dialog")
        progressBar.show()
    }
    override fun dismissProgressDialog() {
        FLog.w("dismissProgressDialog")
        progressBar.hide()
    }
}