package me.vinitagrawal.posts.post.data

import io.reactivex.Single
import me.vinitagrawal.posts.post.model.Post
import retrofit2.http.GET

interface PostsService {

    @GET("posts")
    fun getPosts(): Single<List<Post>>
}