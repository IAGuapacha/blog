package com.iaguapacha.blog.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

data class Commentary(
    val postId:Int = -1,
    val id:Int = -1,
    val name:String = "",
    val email:String = "",
    val body:String = ""
 )

@Entity
data class CommentaryEntity(
    @ColumnInfo(name = "postId")
    val postId:Int = -1,
    @PrimaryKey(autoGenerate = true)
    val id:Int = -1,
    @ColumnInfo(name = "name")
    val name:String = "",
    @ColumnInfo(name = "email")
    val email:String = "",
    @ColumnInfo(name = "body")
    val body:String = ""
)

fun CommentaryEntity.toCommentary():Commentary = Commentary(
    this.postId,
    this.id,
    this.name,
    this.email,
    this.body
)

fun Commentary.toCommentaryEntity():CommentaryEntity = CommentaryEntity(
    this.postId,
    this.id,
    this.name,
    this.email,
    this.body
)
