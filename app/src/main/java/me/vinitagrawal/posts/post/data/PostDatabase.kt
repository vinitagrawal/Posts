package me.vinitagrawal.posts.post.data

import androidx.room.Database
import androidx.room.RoomDatabase
import me.vinitagrawal.posts.post.data.PostDatabase.Companion.VERSION
import me.vinitagrawal.posts.post.model.Post
import javax.inject.Singleton

@Singleton
@Database(entities = [Post::class], version = VERSION)
abstract class PostDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao

    companion object {
        internal const val DATABASE_NAME = "PostDatabase"
        internal const val VERSION = 1
    }
}