package me.vinitagrawal.posts

import me.vinitagrawal.posts.di.DaggerTestComponent

class TestApplication : PostsApplication() {

    override fun setupGraph() {
        DaggerTestComponent.builder()
                .with(applicationContext)
                .build()
                .inject(this)
    }
}