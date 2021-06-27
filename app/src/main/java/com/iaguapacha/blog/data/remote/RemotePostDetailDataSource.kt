package com.iaguapacha.blog.data.remote

import com.iaguapacha.blog.data.model.Commentary

class RemotePostDetailDataSource(private val webService: WebService) {

    suspend fun getComments(postId:Int):List<Commentary> = webService.getComments(postId)
}