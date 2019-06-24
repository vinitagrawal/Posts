package me.vinitagrawal.posts.di

import dagger.Module
import dagger.Provides
import me.vinitagrawal.network.NetworkFactory

@Module
class NetworkModule {

    @Provides
    fun providesNetworkFactory(): NetworkFactory {
        return NetworkFactory()
    }
}