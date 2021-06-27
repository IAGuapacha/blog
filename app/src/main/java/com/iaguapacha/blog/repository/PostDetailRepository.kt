package com.iaguapacha.blog.repository

import com.iaguapacha.blog.data.model.Commentary

interface PostDetailRepository {

    suspend fun getComments(postId: Int): List<Commentary>
    suspend fun saveCommentary(commentary:Commentary)
}