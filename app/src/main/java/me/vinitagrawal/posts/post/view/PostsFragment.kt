package me.vinitagrawal.posts.post.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import dagger.android.support.AndroidSupportInjection
import me.vinitagrawal.posts.R
import me.vinitagrawal.posts.post.PostsViewModel
import me.vinitagrawal.posts.post.usecase.PostsUseCase
import javax.inject.Inject

class PostsFragment : Fragment() {

    private lateinit var postsView: TextView

    private val viewModel by lazy { ViewModelProviders.of(this).get(PostsViewModel::class.java) }

    @Inject
    lateinit var postsUseCase: PostsUseCase

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_post, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        postsView = view.findViewById(R.id.postsView)

        super.onViewCreated(view, savedInstanceState)

        viewModel.setUseCase(postsUseCase)
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