package me.vinitagrawal.posts.post.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.facebook.shimmer.ShimmerFrameLayout
import me.vinitagrawal.common.core.BaseFragment
import me.vinitagrawal.common.utils.bindView
import me.vinitagrawal.posts.R
import me.vinitagrawal.posts.post.PostDetailViewModel
import me.vinitagrawal.posts.post.model.Comment
import me.vinitagrawal.posts.post.model.Post
import me.vinitagrawal.posts.post.model.PostDetailState.*
import me.vinitagrawal.posts.profile.model.Profile

class PostDetailFragment : BaseFragment<PostDetailViewModel>(PostDetailViewModel::class.java) {

    private val loadingView by bindView<ShimmerFrameLayout>(R.id.loadingView)
    private val postDetailView by bindView<NestedScrollView>(R.id.detailLayout)
    private val userDetailsView by bindView<ConstraintLayout>(R.id.userDetails)
    private val errorView by bindView<TextView>(R.id.errorView)
    private val commentErrorView by bindView<TextView>(R.id.commentErrorView)
    private val postTitle by bindView<TextView>(R.id.postTitle)
    private val postBody by bindView<TextView>(R.id.postBody)
    private val avatar by bindView<AppCompatImageView>(R.id.avatar)
    private val userName by bindView<TextView>(R.id.userName)
    private val emailAddress by bindView<TextView>(R.id.emailAddress)
    private val commentCount by bindView<TextView>(R.id.commentCount)
    private val commentsView by bindView<RecyclerView>(R.id.commentsView)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_post_detail, container, false)
    }

    override fun observeData() {
        viewModel.getData(arguments?.getLong(KEY_POST_ID) ?: 0)
                .observe { state ->
                    when (state) {
                        is Loading -> showLoadingView()
                        is LoadComplete -> hideLoadingView()
                        is Error -> showErrorView()
                        is Data -> renderPost(state.post, state.profile, state.comments)
                    }
                }
    }

    private fun showLoadingView() {
        loadingView.visibility = VISIBLE
    }

    private fun hideLoadingView() {
        loadingView.visibility = GONE
    }

    private fun showErrorView() {
        errorView.visibility = VISIBLE
    }

    private fun showCommentErrorView() {
        commentErrorView.visibility = VISIBLE
    }

    private fun renderPost(post: Post, profile: Profile?, comments: List<Comment>?) {
        postDetailView.visibility = VISIBLE
        postTitle.text = post.title
        postBody.text = post.body

        profile?.let { renderProfile(it) }
        comments?.let { renderComments(it) } ?: showCommentErrorView()
    }

    private fun renderProfile(profile: Profile) {
        userDetailsView.visibility = VISIBLE
        profile.run {
            Glide.with(requireContext())
                    .load(getAvatarUrl())
                    .placeholder(R.drawable.horizontal_shimmer)
                    .into(avatar)

            userName.text = username
            emailAddress.text = email
        }
    }

    private fun renderComments(comments: List<Comment>) {
        commentCount.text = getString(R.string.number_of_comments, comments.size)
        commentsView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = CommentAdapter(comments, requireContext())
            visibility = VISIBLE
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