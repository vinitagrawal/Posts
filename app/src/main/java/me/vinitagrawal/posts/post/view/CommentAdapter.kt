package me.vinitagrawal.posts.post.view

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import me.vinitagrawal.common.utils.bindView
import me.vinitagrawal.common.utils.inflate
import me.vinitagrawal.posts.R
import me.vinitagrawal.posts.post.model.Comment

class CommentAdapter(private val comments: List<Comment>) : RecyclerView.Adapter<CommentAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(parent.inflate(R.layout.comment_tile))

    override fun getItemCount() = comments.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(comments[position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val body by bindView<TextView>(R.id.body)
        private val email by bindView<TextView>(R.id.email)

        fun bind(comment: Comment) {
            body.text = comment.body
            email.text = comment.email
        }
    }
}
