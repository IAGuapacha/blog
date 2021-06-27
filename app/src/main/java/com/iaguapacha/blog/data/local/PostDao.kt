package com.iaguapacha.blog.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.iaguapacha.blog.data.model.PostEntity

@Dao
interface PostDao {

    @Query("SELECT * FROM PostEntity")
    suspend fun getPost(): List<PostEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePost(post:PostEntity)
}