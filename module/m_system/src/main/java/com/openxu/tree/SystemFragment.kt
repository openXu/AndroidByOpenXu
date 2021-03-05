package com.openxu.register

import androidx.lifecycle.Observer
import com.openxu.core.base.BaseVmFragment
import com.openxu.system.R
import com.openxu.system.databinding.FragmentSystemBinding
import kotlinx.android.synthetic.main.fragment_system.*

/**
 * Author: openXu
 * Time: 2021/3/1 11:43
 * class: SystemFragment
 * Description:
 */
class SystemFragment : BaseVmFragment<FragmentSystemBinding, SystemViewModel>() {


    override fun layoutRes() = R.layout.fragment_system

    override fun initView() {
        super.initView()
    }
    override fun initData() {
        super.initData()
    }


    override fun lazyLoadData() {



//        mViewModel.getArticleList()
    }


    override fun observe() {
        super.observe()
        mViewModel.treeList.observe(this, Observer {
            tvResult1.text = "获取到知识体系数量："+it.size
        })
        mViewModel.page.observe(this, Observer {
            tvResult1.text = "获取到文章："+it
        })
//        mViewModel.submitting.observe(this, Observer {
//            if (it) showProgressDialog(R.string.registerring) else dismissProgressDialog()
//        })
    }



}