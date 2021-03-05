package com.openxu.core.base

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.openxu.core.common.dialog.ProgressDialogFragment
import java.lang.reflect.ParameterizedType

/**
 * Author: openXu
 * Time: 2021/2/26 11:39
 * class: BaseVmFragment
 * Description:
 */
abstract class BaseVmFragment<V : ViewDataBinding, VM : BaseViewModel> : BaseFragment<V>() {

    protected lateinit var mViewModel: VM
    private var lazyLoaded = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        observe()
        initView()
        initData()
    }
    override fun onResume() {
        super.onResume()
        // 实现懒加载
        if (!lazyLoaded) {
            lazyLoadData()
            lazyLoaded = true
        }
    }
    open fun observe() {
        // Override if need
    }
    open fun initView() {
        // Override if need
    }
    open fun initData() {
        // Override if need
    }
    open fun lazyLoadData() {
        // Override if need
    }

    private fun initViewModel() {
        val type = javaClass.genericSuperclass
        val modelClass = if (type is ParameterizedType) {
            //获取第2个泛型的类文件
            val types = type.actualTypeArguments
            // modelClass = (types[1] instanceof ParameterizedType)?types[1].getClass():(Class)types[1];
            types[1] as Class<ViewModel>
        } else {
            //如果没有指定泛型参数，则默认使用BaseViewModel
            BaseViewModel::class.java
        }
        //通过ViewModelProviders，传入生命周期对象，获取ViewModel实例
        mViewModel = ViewModelProvider(this).get(modelClass) as VM
    }


}