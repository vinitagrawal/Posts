package me.vinitagrawal.posts

import android.app.Activity
import android.app.Application
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import me.vinitagrawal.posts.di.DaggerPostsAppComponent
import javax.inject.Inject

open class PostsApplication : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingActivityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        setupGraph()
    }

    open fun setupGraph() {
        DaggerPostsAppComponent.builder()
            .with(applicationContext)
            .build()
            .inject(this)
    }

    override fun activityInjector() = dispatchingActivityInjector
}