package com.openxu.register

import com.openxu.libs.datasource.bean.Tree


class SystemLocalRepository : ISystemRepository {
    override fun getArticleTree(responseBack: (result:MutableList<Tree>?) -> Unit) {
        responseBack(null)
    }


}