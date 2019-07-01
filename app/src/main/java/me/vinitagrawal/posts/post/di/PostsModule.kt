package me.vinitagrawal.posts.post.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import me.vinitagrawal.common.di.ViewModelModule.ViewModelKey
import me.vinitagrawal.common.utils.Logger
import me.vinitagrawal.network.NetworkFactory
import me.vinitagrawal.posts.post.PostDetailViewModel
import me.vinitagrawal.posts.post.PostsViewModel
import me.vinitagrawal.posts.post.data.PostsRepository
import me.vinitagrawal.posts.post.data.PostsRepositoryImpl
import me.vinitagrawal.posts.post.data.PostsService
import me.vinitagrawal.posts.post.domain.PostsInteractor
import me.vinitagrawal.posts.post.domain.PostsUseCase
import me.vinitagrawal.posts.profile.domain.ProfileUseCase

@Module(includes = [PostsDepsModule::class])
abstract class PostsModule {

    @Binds
    abstract fun bindsPostsRepository(postsRepositoryImpl: PostsRepositoryImpl): PostsRepository

    @Binds
    abstract fun bindsPostsUseCase(postsInteractor: PostsInteractor): PostsUseCase
}

@Module
class PostsDepsModule {

    @Provides
    @IntoMap
    @ViewModelKey(PostsViewModel::class)
    fun providesPostViewModel(postsUseCase: PostsUseCase,
                              logger: Logger): ViewModel {
        return PostsViewModel(postsUseCase, logger)
    }

    @Provides
    @IntoMap
    @ViewModelKey(PostDetailViewModel::class)
    fun providesPostDetailViewModel(postsUseCase: PostsUseCase,
                                    profileUseCase: ProfileUseCase,
                                    logger: Logger): ViewModel {
        return PostDetailViewModel(postsUseCase, profileUseCase, logger)
    }

    @Provides
    fun providesPostsService(networkFactory: NetworkFactory): PostsService {
        return networkFactory.getService(PostsService::class.java)
    }
}