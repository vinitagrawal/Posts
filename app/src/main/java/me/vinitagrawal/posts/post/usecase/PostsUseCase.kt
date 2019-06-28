package me.vinitagrawal.posts.post.usecase

import io.reactivex.Observable
import me.vinitagrawal.posts.post.data.PostsRepository
import me.vinitagrawal.posts.post.model.Post
import javax.inject.Inject

interface PostsUseCase {
    fun getPosts(): Observable<List<Post>>
}

class PostsInteractor @Inject constructor(private val repository: PostsRepository) : PostsUseCase {

    override fun getPosts(): Observable<List<Post>> {
        return repository.getPosts()
    }
}