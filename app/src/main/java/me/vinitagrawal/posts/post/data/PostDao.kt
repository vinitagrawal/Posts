package me.vinitagrawal.posts.post.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Observable
import io.reactivex.Single
import me.vinitagrawal.posts.post.model.Post
import me.vinitagrawal.posts.post.model.Post.Contract.Column.Companion.ID
import me.vinitagrawal.posts.post.model.Post.Contract.Companion.TABLE_NAME

@Dao
interface PostDao {

    @Insert
    fun insert(posts: List<Post>)

    @Query("SELECT * FROM $TABLE_NAME")
    fun getAllPosts(): Observable<List<Post>>

    @Query("SELECT * FROM $TABLE_NAME WHERE $ID LIKE :postId")
    fun getPostById(postId: Long): Single<Post>
}