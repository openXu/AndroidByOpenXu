package com.openxu.wanandroid.ui.tree

import com.openxu.core.bean.Category
import com.openxu.wanandroid.ui.tree.ISystemRepository


class SystemLocalRepository : ISystemRepository {
   fun getTree(responseBack: (result:MutableList<Category>?) -> Unit) {
        responseBack(null)
    }


}