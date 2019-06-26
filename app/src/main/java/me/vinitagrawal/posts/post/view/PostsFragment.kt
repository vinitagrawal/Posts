package me.vinitagrawal.posts.post.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import me.vinitagrawal.common.core.BaseFragment
import me.vinitagrawal.common.utils.bindView
import me.vinitagrawal.posts.R
import me.vinitagrawal.posts.post.PostsViewModel
import me.vinitagrawal.posts.post.model.Post
import me.vinitagrawal.posts.post.model.PostsState.*

class PostsFragment : BaseFragment<PostsViewModel>(PostsViewModel::class.java) {

    private val postsView by bindView<RecyclerView>(R.id.postsView)
    private val loadingView by bindView<ProgressBar>(R.id.loadingView)
    private val errorView by bindView<TextView>(R.id.errorView)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_post, container, false)
    }

    override fun observeData() {
        viewModel.getData().observe { state ->
            when (state) {
                is LoadingState -> showLoadingView()
                is LoadCompleteState -> hideLoadingView()
                is ErrorState -> showErrorView()
                is DataState -> renderPosts(state.posts)
            }
        }
    }

    private fun showLoadingView() {
        loadingView.visibility = View.VISIBLE
    }

    private fun hideLoadingView() {
        loadingView.visibility = View.GONE
    }

    private fun showErrorView() {
        errorView.visibility = View.VISIBLE
    }

    private fun renderPosts(posts: List<Post>) {
        postsView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = PostsAdapter(posts)
            visibility = View.VISIBLE
        }
    }

    companion object {
        fun newInstance() = PostsFragment()
    }
}