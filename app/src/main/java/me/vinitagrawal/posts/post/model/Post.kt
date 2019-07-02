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
data class Post(@PrimaryKey
                @ColumnInfo(name = ID)
                @SerializedName("id") val id: Long,
                @ColumnInfo(name = USER_ID)
                @SerializedName("userId") val userId: Long,
                @ColumnInfo(name = TITLE)
                @SerializedName("title") val title: String,
                @ColumnInfo(name = BODY)
                @SerializedName("body") val body: String) {

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