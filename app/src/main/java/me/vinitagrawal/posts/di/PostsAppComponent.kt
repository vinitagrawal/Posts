package me.vinitagrawal.posts.di

import dagger.Component
import dagger.android.AndroidInjectionModule
import me.vinitagrawal.common.di.ViewModelModule
import me.vinitagrawal.network.di.NetworkModule
import me.vinitagrawal.posts.PostsApplication
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        NetworkModule::class,
        ViewModelModule::class,
        ActivityBindingModule::class]
)
interface PostsAppComponent {

    fun inject(application: PostsApplication)
}