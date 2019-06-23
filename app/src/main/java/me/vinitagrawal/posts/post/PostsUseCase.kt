package me.vinitagrawal.posts.post

import androidx.annotation.VisibleForTesting
import io.reactivex.Single
import me.vinitagrawal.posts.post.model.Post

interface PostsUseCase {
    fun getPosts(): Single<List<Post>>
}

class PostsInteractor : PostsUseCase {

    private var repository: PostsRepository = PostsRepositoryImpl()

    @VisibleForTesting
    fun setRepository(postsRepository: PostsRepository) {
        repository = postsRepository
    }

    override fun getPosts(): Single<List<Post>> {
        return repository.getPosts()
    }

}