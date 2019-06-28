package me.vinitagrawal.posts.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import me.vinitagrawal.common.di.CommonsModule
import me.vinitagrawal.network.di.NetworkModule
import me.vinitagrawal.posts.PostsApplication
import me.vinitagrawal.posts.post.di.DatabaseModule
import javax.inject.Singleton

@Singleton
@Component(
        modules = [
            AndroidInjectionModule::class,
            NetworkModule::class,
            CommonsModule::class,
            ActivityBindingModule::class,
            DatabaseModule::class]
)
interface PostsAppComponent {

    fun inject(application: PostsApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun with(context: Context): Builder

        fun build(): PostsAppComponent
    }
}