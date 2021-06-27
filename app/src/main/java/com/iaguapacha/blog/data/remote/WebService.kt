package com.iaguapacha.blog.data.remote

import com.google.gson.GsonBuilder
import com.iaguapacha.blog.application.AppConstans
import com.iaguapacha.blog.data.model.Commentary
import com.iaguapacha.blog.data.model.Post
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface WebService {

    @GET("/posts")
    suspend fun getPosts(): List<Post>

    @GET("/posts/{postId}/comments")
    suspend fun getComments(@Path("postId") postId:Int):List<Commentary>
}

object RetrofitClient{

    val webservice by lazy {
        Retrofit.Builder()
            .baseUrl(AppConstans.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build().create(WebService::class.java)
    }
}