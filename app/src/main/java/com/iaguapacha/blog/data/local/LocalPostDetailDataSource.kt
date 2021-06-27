package com.iaguapacha.blog.data.local

import com.iaguapacha.blog.data.model.*

class LocalPostDetailDataSource(private val commentaryDao: CommentaryDao) {

    suspend fun getComments(postId: Int): List<Commentary> {
        var comments = mutableListOf<Commentary>()
        commentaryDao.getComments(postId).forEach { commentaryEntity ->
            comments.add(commentaryEntity.toCommentary())
        }
        return comments
    }

    suspend fun saveCommentary(commentary: CommentaryEntity) {
        commentaryDao.saveCommentary(commentary)
    }
}