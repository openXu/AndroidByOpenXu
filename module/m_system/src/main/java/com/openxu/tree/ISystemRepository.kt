package com.openxu.register

import com.openxu.libs.datasource.bean.Tree


interface ISystemRepository {
    fun getArticleTree(responseBack: (result:MutableList<Tree>?) -> Unit)
}