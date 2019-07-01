package me.vinitagrawal.posts.profile.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import me.vinitagrawal.network.NetworkFactory
import me.vinitagrawal.posts.profile.data.ProfileRepository
import me.vinitagrawal.posts.profile.data.ProfileRepositoryImpl
import me.vinitagrawal.posts.profile.data.ProfileService
import me.vinitagrawal.posts.profile.domain.ProfileInteractor
import me.vinitagrawal.posts.profile.domain.ProfileUseCase

@Module(includes = [ProfileDepsModule::class])
abstract class ProfileModule {

    @Binds
    abstract fun bindsProfileRepository(profileRepositoryImpl: ProfileRepositoryImpl): ProfileRepository

    @Binds
    abstract fun bindsProfileUseCase(profileInteractor: ProfileInteractor): ProfileUseCase
}

@Module
class ProfileDepsModule {

    @Provides
    fun providesProfileService(networkFactory: NetworkFactory): ProfileService {
        return networkFactory.getService(ProfileService::class.java)
    }
}