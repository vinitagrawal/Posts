package me.vinitagrawal.posts.post.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import me.vinitagrawal.posts.post.model.Post.Contract.Column.Companion.BODY
import me.vinitagrawal.posts.post.model.Post.Contract.Column.Companion.ID
import me.vinitagrawal.posts.post.model.Post.Contract.Column.Companion.TITLE
import me.vinitagrawal.posts.post.model.Post.Contract.Column.Companion.USER_ID
import me.vinitagrawal.posts.post.model.Post.Contract.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class Post(@SerializedName("id")
                @PrimaryKey
                @ColumnInfo(name = ID)
                val id: Long,
                @SerializedName("userId")
                @ColumnInfo(name = USER_ID)
                val userId: Long,
                @SerializedName("title")
                @ColumnInfo(name = TITLE)
                val title: String,
                @SerializedName("body")
                @ColumnInfo(name = BODY)
                val body: String) {

    fun getPreview() = body.substring(0, 50)

    class Contract {
        companion object {
            const val TABLE_NAME = "Post"
        }

        class Column {
            companion object {
                const val ID = "id"
                const val USER_ID = "user_id"
                const val TITLE = "title"
                const val BODY = "body"
            }
        }
    }

}