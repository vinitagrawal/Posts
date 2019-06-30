package me.vinitagrawal.posts.post.model

import com.google.gson.annotations.SerializedName

data class Comment(@SerializedName("id")
                   val id: Long,
                   @SerializedName("postId")
                   val postId: Long,
                   @SerializedName("name")
                   val name: String,
                   @SerializedName("email")
                   val email: String,
                   @SerializedName("body")
                   val body: String)