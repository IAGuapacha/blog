package com.iaguapacha.blog.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.iaguapacha.blog.data.model.CommentaryEntity
import com.iaguapacha.blog.data.model.PostEntity

@Database(entities = [CommentaryEntity::class,PostEntity::class],version = 1)
abstract class AppDatabase:RoomDatabase() {

    abstract fun postDao():PostDao
    abstract fun commentaryDao():CommentaryDao

    companion object{

        private  var INSTANCE:AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase{
            INSTANCE = INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "blog_database"
            ).build()

            return INSTANCE!!
        }

        fun destroyInstance(){
            INSTANCE = null
        }
    }
}