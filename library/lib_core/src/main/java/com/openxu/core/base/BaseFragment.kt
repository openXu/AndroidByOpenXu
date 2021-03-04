package com.openxu.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment


/**
 * Author: openXu
 * Time: 2021/2/26 11:39
 * class: BaseFragment
 * Description:
 */
open class BaseFragment<V : ViewDataBinding> : Fragment() {

    protected lateinit var binding: V

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

}
