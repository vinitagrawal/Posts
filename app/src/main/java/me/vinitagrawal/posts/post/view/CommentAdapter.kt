package me.vinitagrawal.posts.post.view

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import me.vinitagrawal.common.utils.bindView
import me.vinitagrawal.common.utils.inflate
import me.vinitagrawal.posts.R
import me.vinitagrawal.posts.UrlMap.Companion.AVATAR_URL
import me.vinitagrawal.posts.post.model.Comment

class CommentAdapter(private val comments: List<Comment>,
                     private val context: Context) : RecyclerView.Adapter<CommentAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(parent.inflate(R.layout.comment_tile))

    override fun getItemCount() = comments.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(comments[position], context)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val body by bindView<TextView>(R.id.body)
        private val email by bindView<TextView>(R.id.email)
        private val avatar by bindView<AppCompatImageView>(R.id.avatar)

        fun bind(comment: Comment, context: Context) {
            body.text = comment.body
            email.text = comment.email

            Glide.with(context)
                .load(AVATAR_URL + comment.email)
                .placeholder(R.drawable.horizontal_shimmer)
                .into(avatar)
        }
    }
}
