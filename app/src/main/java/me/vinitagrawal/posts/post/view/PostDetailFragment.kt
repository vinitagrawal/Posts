package me.vinitagrawal.posts.post.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import me.vinitagrawal.common.core.BaseFragment
import me.vinitagrawal.common.utils.bindView
import me.vinitagrawal.posts.R
import me.vinitagrawal.posts.post.PostDetailViewModel
import me.vinitagrawal.posts.post.model.Post
import me.vinitagrawal.posts.post.model.PostDetailState

class PostDetailFragment : BaseFragment<PostDetailViewModel>(PostDetailViewModel::class.java) {

    private val postTitle by bindView<TextView>(R.id.postTitle)
    private val postBody by bindView<TextView>(R.id.postBody)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_post_detail, container, false)
    }

    override fun observeData() {
        viewModel.getData(arguments?.getLong(KEY_POST_ID) ?: 0)
                .observe { state ->
                    when (state) {
                        is PostDetailState.Data -> renderPost(state.post)
                    }
                }
    }

    private fun renderPost(post: Post) {
        postTitle.text = post.title
        postBody.text = post.body
    }

    companion object {
        private const val KEY_POST_ID = "post_id"

        fun newInstance(postId: Long) =
                PostDetailFragment().apply {
                    arguments = Bundle().apply {
                        putLong(KEY_POST_ID, postId)
                    }
                }
    }
}