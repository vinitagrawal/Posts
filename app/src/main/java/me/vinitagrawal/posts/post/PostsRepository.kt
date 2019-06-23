package me.vinitagrawal.posts.post

import androidx.annotation.VisibleForTesting
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import me.vinitagrawal.network.NetworkFactory
import me.vinitagrawal.posts.post.model.Post

interface PostsRepository {
    fun getPosts(): Single<List<Post>>
}

class PostsRepositoryImpl : PostsRepository {

    private var service = NetworkFactory().getService(PostsService::class.java)

    @VisibleForTesting
    fun setService(postsService: PostsService) {
        service = postsService
    }

    override fun getPosts(): Single<List<Post>> {
        return service.getPosts()
            .subscribeOn(Schedulers.io())
    }

}