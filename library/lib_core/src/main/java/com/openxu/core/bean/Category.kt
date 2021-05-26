package com.openxu.core.bean

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize


//"data":[
//    {
//        "children":[
//            {
//                "children":[],
//                "courseId":13,
//                "id":60,
//                "name":"Android Studio相关",
//                "order":1000,
//                "parentChapterId":150,
//                "userControlSetTop":false,
//                "visible":1
//            },
//            ...
//        ],
//        "courseId":13,
//        "id":150,
//        "name":"开发环境",
//        "order":1,
//        "parentChapterId":0,
//        "userControlSetTop":false,
//        "visible":1
//    },
//    ...
//]
/**
 * 体系
 */
@Keep
@Parcelize
data class Category(
    val courseId: Int,
    var id: Int?,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val userControlSetTop: Boolean,
    val visible: Int,
    val children: MutableList<Category>
):Parcelable