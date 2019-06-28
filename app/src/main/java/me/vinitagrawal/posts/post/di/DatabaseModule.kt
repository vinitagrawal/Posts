package me.vinitagrawal.posts.post.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import me.vinitagrawal.posts.post.data.PostDao
import me.vinitagrawal.posts.post.data.PostDatabase

@Module
class DatabaseModule {

    @Provides
    fun providesPostDatabase(context: Context): PostDatabase {
        return Room.databaseBuilder(context, PostDatabase::class.java, PostDatabase.DATABASE_NAME)
                .build()
    }

    @Provides
    fun providesPostDao(postDatabase: PostDatabase): PostDao {
        return postDatabase.postDao()
    }
}