package me.vinitagrawal.posts.post.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import me.vinitagrawal.common.BaseFragment
import me.vinitagrawal.posts.R
import me.vinitagrawal.posts.post.PostsViewModel

class PostsFragment : BaseFragment<PostsViewModel>(PostsViewModel::class.java) {

    private lateinit var postsView: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_post, container, false)
    }

    override fun bindViews(view: View) {
        postsView = view.findViewById(R.id.postsView)
    }

    override fun observeData() {
        viewModel.getPosts()
            .observe(
                { lifecycle },
                { postsView.text = it.toString() }
            )
    }

    companion object {
        fun newInstance() = PostsFragment()
    }
}