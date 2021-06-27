package com.iaguapacha.blog.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.iaguapacha.blog.data.model.CommentaryEntity

@Dao
interface CommentaryDao {

    @Query("SELECT * FROM CommentaryEntity WHERE postId = :postId")
    suspend fun getComments(postId:Int):List<CommentaryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCommentary(commentary: CommentaryEntity)
}