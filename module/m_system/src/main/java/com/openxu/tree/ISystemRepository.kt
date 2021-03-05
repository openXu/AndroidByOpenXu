package com.openxu.register

import com.openxu.libs.datasource.bean.Article
import com.openxu.libs.datasource.bean.Pagination
import com.openxu.libs.datasource.bean.Tree


interface ISystemRepository {
    fun getTree(responseBack: (result:MutableList<Tree>?) -> Unit)

//    fun getArticleList(responseBack: (result: Pagination<Article>?) -> Unit)


}