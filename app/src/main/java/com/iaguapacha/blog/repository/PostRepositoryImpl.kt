package com.iaguapacha.blog.repository

import com.iaguapacha.blog.core.InternetCheck
import com.iaguapacha.blog.data.local.LocalPostDataSource
import com.iaguapacha.blog.data.model.Post
import com.iaguapacha.blog.data.model.toPostEntity
import com.iaguapacha.blog.data.remote.RemotePostsDataSource

class PostRepositoryImpl(
    private val dataSourceRemote: RemotePostsDataSource,
    private val dataSourceLocal: LocalPostDataSource
) : PostRepository {

    override suspend fun getPosts(): List<Post> {
        return if (InternetCheck.isNetworkAvailable()) {
            dataSourceRemote.getPosts().forEach { post ->
                dataSourceLocal.savePost(post.toPostEntity())
            }
            dataSourceLocal.getPost()
        } else {
            dataSourceLocal.getPost()
        }
    }
}