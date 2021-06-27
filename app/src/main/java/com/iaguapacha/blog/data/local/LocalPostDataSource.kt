package com.iaguapacha.blog.data.local

import com.iaguapacha.blog.data.model.Post
import com.iaguapacha.blog.data.model.PostEntity
import com.iaguapacha.blog.data.model.toPost

class LocalPostDataSource(private val postDao: PostDao ) {

    suspend fun getPost():List<Post>{
        var posts= mutableListOf<Post>()
        postDao.getPost().forEach{ postEntity ->
            posts.add(postEntity.toPost())
        }
       return posts
    }

    suspend fun savePost(post:PostEntity){
        postDao.savePost(post)
    }
}