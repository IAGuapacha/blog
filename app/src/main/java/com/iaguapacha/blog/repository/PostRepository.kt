package com.iaguapacha.blog.repository

import com.iaguapacha.blog.data.model.Post

interface PostRepository {

    suspend fun getPosts():List<Post>
}