package me.vinitagrawal.posts.post.data

import io.reactivex.Single
import me.vinitagrawal.posts.UrlMap.Companion.POSTS_URL
import me.vinitagrawal.posts.post.model.Post
import retrofit2.http.GET

interface PostsService {

    @GET(POSTS_URL)
    fun getPosts(): Single<List<Post>>
}