package me.vinitagrawal.posts.post.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import me.vinitagrawal.network.NetworkFactory
import me.vinitagrawal.posts.post.data.PostsRepository
import me.vinitagrawal.posts.post.data.PostsRepositoryImpl
import me.vinitagrawal.posts.post.data.PostsService
import me.vinitagrawal.posts.post.usecase.PostsInteractor
import me.vinitagrawal.posts.post.usecase.PostsUseCase

@Module(includes = [PostsDepsModule::class])
abstract class PostsModule {

    @Binds
    abstract fun bindsPostsRepository(postsRepositoryImpl: PostsRepositoryImpl) : PostsRepository

    @Binds
    abstract fun bindsPostsUseCase(postsInteractor: PostsInteractor) : PostsUseCase
}

@Module
class PostsDepsModule {

    @Provides
    fun providesPostsService(networkFactory: NetworkFactory): PostsService {
        return networkFactory.getService(PostsService::class.java)
    }
}