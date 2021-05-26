package com.openxu.core.base

import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.openxu.core.common.dialog.ProgressDialogFragment
import com.openxu.core.utils.toasty.FToast
import java.lang.reflect.ParameterizedType

/**
 * Author: openXu
 * Time: 2021/2/26 11:39
 * class: BaseVmActivity
 * Description:
 */
abstract class BaseVmActivity<V : ViewDataBinding, VM : BaseViewModel> : BaseActivity<V>() {

    protected lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        baseObserve()
        observe()
        initView()
        initData()
    }
    private fun baseObserve(){
        viewModel.dialog.observe(this, Observer<Boolean> {
            if(it) {showProgressDialog(null)} else{ dismissProgressDialog()}
        })
        viewModel.wToast.observe(this, Observer<String> {
            FToast.warning(it)
        })
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
        viewModel = ViewModelProvider(this).get(modelClass) as VM
    }

}