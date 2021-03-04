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
 * class: BaseActivity
 * Description:
 */
abstract class BaseActivity<V : ViewDataBinding> : AppCompatActivity() {

    protected lateinit var mBinding: V
    private lateinit var progressDialogFragment: ProgressDialogFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDataBinding()
    }

    abstract fun layoutRes(): Int

    private fun initDataBinding(){
        mBinding = DataBindingUtil.setContentView(this, layoutRes())
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