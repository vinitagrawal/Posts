package me.vinitagrawal.network.di

import dagger.Module
import dagger.Provides
import me.vinitagrawal.network.NetworkFactory
import me.vinitagrawal.network.UrlMap
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun providesNetworkFactory(): NetworkFactory {
        return NetworkFactory(
            object : NetworkFactory.Dependency {
                override fun getBaseUrl() = UrlMap.SERVER_URL
            })
    }
}