package com.openxu.android

import androidx.fragment.app.Fragment
import com.openxu.android.databinding.ActivityMainBinding
import com.openxu.core.base.BaseViewModel
import com.openxu.core.base.BaseVmActivity
import com.openxu.register.SystemFragment

class MainActivity : BaseVmActivity<ActivityMainBinding, BaseViewModel>() {

    private lateinit var fragments: Map<Int, Fragment>

    override fun layoutRes(): Int = R.layout.activity_main

    override fun initView() {

        fragments = mapOf(
            R.id.system to createFragment(SystemFragment::class.java),
            R.id.mine to createFragment(Fragment::class.java)
        )

        mBinding.bottomNavigationView.run {
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

        mBinding.bottomNavigationView.selectedItemId = R.id.home
        showFragment(mBinding.bottomNavigationView.selectedItemId)
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



}