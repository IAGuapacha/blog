package com.iaguapacha.blog.repository

import com.iaguapacha.blog.core.InternetCheck
import com.iaguapacha.blog.data.local.LocalPostDetailDataSource
import com.iaguapacha.blog.data.model.Commentary
import com.iaguapacha.blog.data.model.toCommentaryEntity
import com.iaguapacha.blog.data.remote.RemotePostDetailDataSource
import com.iaguapacha.blog.exception.NoInternetException

class PostDetailRepositoryImpl(
    private val dataSourceRemote: RemotePostDetailDataSource,
    private val dataSourceLocal: LocalPostDetailDataSource
) :
    PostDetailRepository {

    override suspend fun getComments(postId: Int): List<Commentary> {
        return if (InternetCheck.isNetworkAvailable()){
            dataSourceRemote.getComments(postId).forEach { commentary ->
                dataSourceLocal.saveCommentary(commentary.toCommentaryEntity())
            }
            dataSourceLocal.getComments(postId)
        }else {
            val commetns = dataSourceLocal.getComments(postId)
            if(commetns.isEmpty()){
                throw NoInternetException()
            }else{
                commetns
            }

        }
    }

    override suspend fun saveCommentary(commentary: Commentary) {
        dataSourceLocal.saveCommentary(commentary.toCommentaryEntity())
    }
}