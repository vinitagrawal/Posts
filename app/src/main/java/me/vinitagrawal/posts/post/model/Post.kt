package me.vinitagrawal.posts.post.model

import com.google.gson.annotations.SerializedName

data class Post(@SerializedName("id")
                val id: Long,
                @SerializedName("userId")
                val userId: Long,
                @SerializedName("title")
                val title: String,
                @SerializedName("body")
                val body: String) {

    fun getPreview() = body.substring(0, 50)
}