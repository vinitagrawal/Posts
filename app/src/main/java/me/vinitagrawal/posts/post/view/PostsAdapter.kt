package me.vinitagrawal.posts.post.view

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import me.vinitagrawal.common.utils.bindView
import me.vinitagrawal.common.utils.inflate
import me.vinitagrawal.posts.R
import me.vinitagrawal.posts.post.model.Post

class PostsAdapter(private val postList: List<Post>,
                   private val onItemClick: onItemClick) : RecyclerView.Adapter<PostsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent.inflate(R.layout.post_tile))

    override fun getItemCount() = postList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(postList[position], onItemClick)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title by bindView<TextView>(R.id.title)
        private val preview by bindView<TextView>(R.id.preview)

        fun bind(post: Post, onItemClick: onItemClick) {
            title.text = post.title
            preview.text = post.body
            itemView.setOnClickListener { onItemClick(post.id) }
        }
    }
}

typealias onItemClick = (Long) -> Unit