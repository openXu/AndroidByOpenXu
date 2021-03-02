package com.openxu.core.base

import android.os.Bundle
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
 * class: LoginActivity
 * Description:
 */
abstract class BaseActivity<V : ViewDataBinding, VM : BaseViewModel> : AppCompatActivity() {

    protected lateinit var mBinding: V
    protected lateinit var mViewModel: VM
    private lateinit var progressDialogFragment: ProgressDialogFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initDataBinding()
        initViewModel()
        observe()
        initView()
        initData()
    }

    abstract fun layoutRes(): Int
    open fun observe() {
        // Override if need
    }
    open fun initView() {
        // Override if need
    }
    open fun initData() {
        // Override if need
    }

    private fun initDataBinding(){
        mBinding = DataBindingUtil.setContentView(this, layoutRes())
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


    /**
     * 显示加载(转圈)对话框
     */
    fun showProgressDialog(@StringRes message: Int) {
        if (!this::progressDialogFragment.isInitialized) {
            progressDialogFragment = ProgressDialogFragment()
        }
        if (!progressDialogFragment.isAdded) {
            progressDialogFragment.show(supportFragmentManager, message, false)
        }
    }

    /**
     * 隐藏加载(转圈)对话框
     */
    fun dismissProgressDialog() {
        if (this::progressDialogFragment.isInitialized && progressDialogFragment.isVisible) {
            progressDialogFragment.dismissAllowingStateLoss()
        }
    }

}