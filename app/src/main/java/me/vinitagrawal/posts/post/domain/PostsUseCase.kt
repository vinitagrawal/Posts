package me.vinitagrawal.posts.post.domain

import io.reactivex.Observable
import io.reactivex.Single
import me.vinitagrawal.posts.post.data.PostsRepository
import me.vinitagrawal.posts.post.model.Comment
import me.vinitagrawal.posts.post.model.Post
import javax.inject.Inject

interface PostsUseCase {
    fun getPosts(): Observable<List<Post>>
    fun getPostById(postId: Long): Single<Post>
    fun getCommentsForPost(postId: Long): Single<List<Comment>>
}

class PostsInteractor @Inject constructor(private val repository: PostsRepository) : PostsUseCase {

    override fun getPosts(): Observable<List<Post>> {
        return repository.getPosts()
    }

    override fun getPostById(postId: Long): Single<Post> {
        return repository.getPostById(postId)
    }

    override fun getCommentsForPost(postId: Long): Single<List<Comment>> {
        return repository.getCommentsForPost(postId)
    }
}