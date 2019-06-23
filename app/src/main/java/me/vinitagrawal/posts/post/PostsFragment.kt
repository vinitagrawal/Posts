package me.vinitagrawal.posts.post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import me.vinitagrawal.posts.R

class PostsFragment : Fragment() {

    private lateinit var postsView: TextView

    private val viewModel by lazy { ViewModelProviders.of(this).get(PostsViewModel::class.java) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_post, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        postsView = view.findViewById(R.id.postsView)

        super.onViewCreated(view, savedInstanceState)

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