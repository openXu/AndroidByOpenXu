package com.openxu.wanandroid.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.ViewPropertyAnimator
import androidx.fragment.app.Fragment
import com.google.android.material.animation.AnimationUtils
import com.openxu.wanandroid.databinding.ActivityMainBinding
import com.openxu.core.base.BaseViewModel
import com.openxu.core.base.BaseVmActivity
import com.openxu.wanandroid.ui.tree.SystemFragment
import com.openxu.wanandroid.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseVmActivity<ActivityMainBinding, BaseViewModel>() {

    private lateinit var fragments: Map<Int, Fragment>

    override fun layoutRes(): Int = R.layout.activity_main

    override fun initView() {

        fragments = mapOf(
            R.id.system to createFragment(SystemFragment::class.java),
            R.id.mine to createFragment(Fragment::class.java)
        )

        binding.bottomNavigationView.run {
            setOnNavigationItemSelectedListener { menuItem ->
                showFragment(menuItem.itemId)
                true
            }
            setOnNavigationItemReselectedListener { menuItem ->
                val fragment = fragments[menuItem.itemId]
              /*  if (fragment is ScrollToTop) {
                    fragment.scrollToTop()
                }*/
            }
        }

        binding.bottomNavigationView.selectedItemId =
            R.id.home
        showFragment(binding.bottomNavigationView.selectedItemId)
    }

    private fun createFragment(clazz: Class<out Fragment>): Fragment {
        var fragment = supportFragmentManager.fragments.find { it.javaClass == clazz }
        if (fragment == null) {
            fragment = when (clazz) {
                SystemFragment::class.java -> SystemFragment()
                Fragment::class.java -> Fragment()
                else -> throw IllegalArgumentException("argument ${clazz.simpleName} is illegal")
            }
        }
        return fragment
    }

    private fun showFragment(menuItemId: Int) {
        val currentFragment = supportFragmentManager.fragments.find {
            it.isVisible && it in fragments.values
        }
        val targetFragment = fragments[menuItemId]
        supportFragmentManager.beginTransaction().apply {
            currentFragment?.let { if (it.isVisible) hide(it) }
            targetFragment?.let {
                if (it.isAdded) show(it) else add(R.id.frameLayout, it)
            }
        }.commit()
    }
    private var bottomNavigationViewAnimtor: ViewPropertyAnimator? = null
    private var currentBottomNavagtionState = true
    fun animateBottomNavigationView(show: Boolean) {
        if (currentBottomNavagtionState == show) {
            return
        }
        if (bottomNavigationViewAnimtor != null) {
            bottomNavigationViewAnimtor?.cancel()
            bottomNavigationView.clearAnimation()
        }
        currentBottomNavagtionState = show
        val targetY = if (show) 0F else bottomNavigationView.measuredHeight.toFloat()
        val duration = if (show) 225L else 175L
        bottomNavigationViewAnimtor = bottomNavigationView.animate()
            .translationY(targetY)
            .setDuration(duration)
            .setInterpolator(AnimationUtils.LINEAR_OUT_SLOW_IN_INTERPOLATOR)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    bottomNavigationViewAnimtor = null
                }
            })
    }


    override fun onDestroy() {
        bottomNavigationViewAnimtor?.cancel()
        bottomNavigationView.clearAnimation()
        bottomNavigationViewAnimtor = null
        super.onDestroy()
    }

}