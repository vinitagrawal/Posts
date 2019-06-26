package me.vinitagrawal.common.di

import dagger.Module
import dagger.Provides
import me.vinitagrawal.common.utils.Logger
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class CommonsModule {
    @Provides
    @Singleton
    fun providesLogger() = Logger()
}