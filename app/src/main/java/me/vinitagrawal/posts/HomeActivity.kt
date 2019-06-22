package me.vinitagrawal.posts

import android.os.Bundle
import me.vinitagrawal.common.BaseActivity
import me.vinitagrawal.posts.post.PostsFragment

class HomeActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        addFragment(R.id.rootContainer, PostsFragment.newInstance())
    }
}
