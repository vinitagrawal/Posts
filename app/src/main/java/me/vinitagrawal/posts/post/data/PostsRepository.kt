package me.vinitagrawal.posts.post.data

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import me.vinitagrawal.posts.post.model.Post
import javax.inject.Inject

interface PostsRepository {
    fun getPosts(): Single<List<Post>>
}

class PostsRepositoryImpl @Inject constructor(private val service: PostsService) :
    PostsRepository {

    override fun getPosts(): Single<List<Post>> {
        return service.getPosts()
            .subscribeOn(Schedulers.io())
    }

}