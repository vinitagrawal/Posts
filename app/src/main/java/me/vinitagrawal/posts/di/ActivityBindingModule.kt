package me.vinitagrawal.posts.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import me.vinitagrawal.posts.HomeActivity
import me.vinitagrawal.posts.post.di.FragmentBindingModule

@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = [FragmentBindingModule::class])
    abstract fun homeActivity(): HomeActivity
}