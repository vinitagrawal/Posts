package me.vinitagrawal.posts.post.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import me.vinitagrawal.common.core.BaseFragment
import me.vinitagrawal.common.utils.bindView
import me.vinitagrawal.posts.R
import me.vinitagrawal.posts.post.PostsViewModel

class PostsFragment : BaseFragment<PostsViewModel>(PostsViewModel::class.java) {

    private val postsView by bindView<RecyclerView>(R.id.postsView)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_post, container, false)
    }

    override fun observeData() {
        viewModel.getPosts()
            .observe({ lifecycle }, {
                postsView?.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = PostsAdapter(it)
                }
            })
    }

    companion object {
        fun newInstance() = PostsFragment()
    }
}