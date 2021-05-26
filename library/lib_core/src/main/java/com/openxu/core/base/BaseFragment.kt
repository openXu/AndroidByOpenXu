package com.openxu.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.openxu.core.common.dialog.ProgressDialogFragment


/**
 * Author: openXu
 * Time: 2021/2/26 11:39
 * class: BaseFragment
 * Description:
 */
open class BaseFragment<V : ViewDataBinding> : Fragment() {

    lateinit var binding: V

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        return inflater.inflate(layoutRes(), container, false)
        binding = DataBindingUtil.inflate(inflater, layoutRes(), container, false)
        //        if(binding.getRoot().getParent()!=null)
//            ((ViewGroup)binding.getRoot().getParent()).removeView(binding.getRoot());
        return binding.root

    }

    open fun layoutRes() = 0

    /**显示加载(转圈)对话框*/
    open fun showProgressDialog(message: String?) {
        if(activity is BaseActivity<*>){
            (activity as BaseActivity<*>).showProgressDialog(message)
        }
    }

    /**隐藏加载(转圈)对话框*/
    open fun dismissProgressDialog() {
        if(activity is BaseActivity<*>){
            (activity as BaseActivity<*>).dismissProgressDialog()
        }
    }

}
