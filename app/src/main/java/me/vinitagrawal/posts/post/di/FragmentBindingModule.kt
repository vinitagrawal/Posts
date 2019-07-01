package me.vinitagrawal.posts.post.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import me.vinitagrawal.posts.post.view.PostDetailFragment
import me.vinitagrawal.posts.post.view.PostsFragment
import me.vinitagrawal.posts.profile.di.ProfileModule

@Module(includes = [ProfileModule::class])
abstract class FragmentBindingModule {

    @ContributesAndroidInjector(modules = [PostsModule::class])
    abstract fun postsFragment(): PostsFragment

    @ContributesAndroidInjector(modules = [PostsModule::class])
    abstract fun postDetailFragment(): PostDetailFragment
}