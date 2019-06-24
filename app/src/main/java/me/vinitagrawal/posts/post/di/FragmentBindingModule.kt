package me.vinitagrawal.posts.post.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import me.vinitagrawal.posts.post.view.PostsFragment

@Module
abstract class FragmentBindingModule {

    @ContributesAndroidInjector(modules = [PostsModule::class])
    abstract fun postsFragment(): PostsFragment
}