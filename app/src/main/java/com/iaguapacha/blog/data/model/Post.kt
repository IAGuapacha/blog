package com.iaguapacha.blog.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

data class Post(
    val userId:Int = -1,
    val id:Int = -1,
    val title:String = "",
    val body:String = "",
)

@Entity
data class PostEntity(
    @ColumnInfo(name = "userId")
    val userId:Int = -1,
    @PrimaryKey
    val id:Int = -1,
    @ColumnInfo(name = "title")
    val title:String = "",
    @ColumnInfo(name = "body")
    val body:String = "",
)

fun PostEntity.toPost():Post = Post(
    this.userId,
    this.id,
    this.title,
    this.body
)

fun Post.toPostEntity():PostEntity = PostEntity(
    this.userId,
    this.id,
    this.title,
    this.body
)
