package me.vinitagrawal.posts.post.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import me.vinitagrawal.common.core.BaseFragment
import me.vinitagrawal.common.utils.bindView
import me.vinitagrawal.posts.R
import me.vinitagrawal.posts.post.PostDetailViewModel
import me.vinitagrawal.posts.post.model.Comment
import me.vinitagrawal.posts.post.model.Post
import me.vinitagrawal.posts.post.model.PostDetailState.Data

class PostDetailFragment : BaseFragment<PostDetailViewModel>(PostDetailViewModel::class.java) {

    private val postTitle by bindView<TextView>(R.id.postTitle)
    private val postBody by bindView<TextView>(R.id.postBody)
    private val commentCount by bindView<TextView>(R.id.commentCount)
    private val commentsView by bindView<RecyclerView>(R.id.commentsView)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_post_detail, container, false)
    }

    override fun observeData() {
        viewModel.getData(arguments?.getLong(KEY_POST_ID) ?: 0)
                .observe { state ->
                    when (state) {
                        is Data -> renderPost(state.post, state.comments)
                    }
                }
    }

    private fun renderPost(post: Post, comments: List<Comment>?) {
        postTitle.text = post.title
        postBody.text = post.body

        comments?.let { renderComments(it) }
    }

    private fun renderComments(comments: List<Comment>) {
        commentCount.text = getString(R.string.numbet_of_comments, comments.size)
        commentsView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = CommentAdapter(comments, requireContext())
            visibility = View.VISIBLE
        }
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