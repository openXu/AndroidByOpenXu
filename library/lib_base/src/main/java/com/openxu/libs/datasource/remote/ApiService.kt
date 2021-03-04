package com.openxu.libs.datasource.remote

import com.openxu.libs.datasource.bean.Article
import com.openxu.libs.datasource.bean.Pagination
import com.openxu.libs.datasource.bean.Tree
import com.openxu.libs.datasource.bean.UserInfo
import com.openxu.libs.datasource.remote.ApiResult
import io.reactivex.Observable
import kotlinx.coroutines.Deferred
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
    fun getArticleTrees1(): Call<ApiResult<MutableList<Tree>>>

    /*根据二级目录id获取知识体系下的文章*/
    @GET("article/list/{page}/json")
    fun getArticleListByCid1(
        @Path("page") page: Int,
        @Query("cid") cid: Int
    ): Observable<ApiResult<Pagination<Article>>>

    /*获取知识体系树*/
    @GET("tree/json")
    suspend fun getArticleTrees(): ApiResult<MutableList<Tree>>

    /*根据二级目录id获取知识体系下的文章*/
    @GET("article/list/{page}/json")
    suspend fun getArticleListByCid(
        @Path("page") page: Int,
        @Query("cid") cid: Int
    ): ApiResult<Pagination<Article>>














}