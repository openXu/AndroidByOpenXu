package com.openxu.core.common.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.openxu.core.R
import kotlinx.android.synthetic.main.core_fragment_progress_dialog.*
import java.io.File

/**
 * Author: openXu
 * Time: 2021/3/2 11:35
 * class: ProgressDialogFragment
 * Description:
 *
 * Android 官方推荐使用 DialogFragment 来代替 Dialog ，可以让它具有更高的可复用性
 * （降低耦合）和更好的便利性（很好的处理屏幕翻转的情况）
 *
 * 如果使用传统Dialog，当Activity意外销毁(屏幕翻转)会报错，虽然不会崩溃，但是如果Activity重建
 * 后需要重新显示Dialog，就要在onCreate中手动恢复。
 * 屏幕旋转是很正常的操作，旋转前旋转后保持一样的界面UI是很正常的事情，要是每次涉及到屏幕旋转都做
 * 一遍上面的操作，那真的让人抓狂。而且,如果在Activity的onDestory()方法里销毁了Dialog还好，万
 * 一忘记销毁了，Dialog里面又有一些复杂操作，还有可能造成内存泄露，所以没办法自动管理Dialog的生
 * 命周期是传统Dialog的第一个缺陷。
 *
 */
class ProgressDialogFragment : DialogFragment() {

    private var messageResId: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.core_fragment_progress_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvMessage.text = messageResId?.let { getString(it) } ?: "loading..."
    }

    fun show(
        fragmentManager: FragmentManager,
        @StringRes messageResId: Int,
        isCancelable: Boolean = false
    ) {
        this.messageResId = messageResId
        this.isCancelable = isCancelable
        try {
            show(fragmentManager, "progressDialogFragment")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}