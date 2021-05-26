package com.openxu.core.network

import com.openxu.core.bean.Article
import com.openxu.core.bean.Pagination
import com.openxu.core.bean.Category
import com.openxu.core.bean.UserInfo
import io.reactivex.rxjava3.core.Observable
import retrofit2.Call
import retrofit2.http.*

/**
 * Author: openXu
 * Time: 2021/3/2 10:33
 * class: ApiService
 * Description:
 */
interface ApiService {

    companion object {
        const val BASE_URL = "https://www.wanandroid.com"
    }

    @FormUrlEncoded
    @POST("user/register")
    suspend fun register(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("repassword") repassword: String
    ): ApiResult<UserInfo>

    @FormUrlEncoded
    @POST("user/login")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): ApiResult<UserInfo>

    /**体系*/

    /*获取知识体系树*/
    @GET("tree/json")
    fun getTreeByCall(): Call<ApiResult<MutableList<Category>>>

    /*根据二级目录id获取知识体系下的文章*/
    @GET("article/list/{page}/json")
    fun getArticleListByCall(
            @Path("page") page: Int,
            @Query("cid") cid: Int
    ): Call<ApiResult<Pagination<Article>>>

    /**RxJava方式*/
    @GET("tree/json")
    fun getTreeByRx(): Observable<ApiResult<MutableList<Category>>>

    @GET("article/list/{page}/json")
    fun getArticleListByRx(
        @Path("page") page: Int,
        @Query("cid") cid: Int
    ): Observable<ApiResult<Pagination<Article>>>

    /*获取知识体系树*/
    @GET("tree/json")
    suspend fun getTree(): ApiResult<MutableList<Category>>
    /*根据二级目录id获取知识体系下的文章*/
    @GET("article/list/{page}/json")
    suspend fun getArticleList(
        @Path("page") page: Int,
        @Query("cid") cid: Int
    ): ApiResult<Pagination<Article>>














}