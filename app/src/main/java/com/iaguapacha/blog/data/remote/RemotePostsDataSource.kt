package com.iaguapacha.blog.data.remote

import com.iaguapacha.blog.data.model.Post

class RemotePostsDataSource (private val webService:WebService){

    suspend fun getPosts(): List<Post> = webService.getPosts()
}