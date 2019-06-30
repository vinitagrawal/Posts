package me.vinitagrawal.posts.post.data

import io.reactivex.Single
import me.vinitagrawal.posts.UrlMap.Companion.COMMENTS_URL
import me.vinitagrawal.posts.UrlMap.Companion.POSTS_URL
import me.vinitagrawal.posts.post.model.Comment
import me.vinitagrawal.posts.post.model.Post
import retrofit2.http.GET
import retrofit2.http.Query

interface PostsService {

    @GET(POSTS_URL)
    fun getPosts(): Single<List<Post>>

    @GET(COMMENTS_URL)
    fun getCommentsForPost(@Query("postId") postId: Long): Single<List<Comment>>
}