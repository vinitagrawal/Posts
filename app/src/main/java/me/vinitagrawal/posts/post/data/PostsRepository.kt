package me.vinitagrawal.posts.post.data

import android.annotation.SuppressLint
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import me.vinitagrawal.common.utils.Logger
import me.vinitagrawal.posts.post.model.Comment
import me.vinitagrawal.posts.post.model.Post
import javax.inject.Inject

interface PostsRepository {
    fun getPosts(): Observable<List<Post>>
    fun getPostById(postId: Long): Single<Post>
    fun getCommentsForPost(postId: Long): Single<List<Comment>>
}

class PostsRepositoryImpl @Inject constructor(private val service: PostsService,
                                              private val postDao: PostDao,
                                              private val logger: Logger) : PostsRepository {

    override fun getPosts(): Observable<List<Post>> {
        return postDao.getAllPosts()
                .subscribeOn(Schedulers.io())
                .doOnNext {
                    if (it.isEmpty())
                        updatePosts()
                }
    }

    override fun getPostById(postId: Long): Single<Post> {
        return postDao.getPostById(postId)
            .subscribeOn(Schedulers.io())
    }

    override fun getCommentsForPost(postId: Long): Single<List<Comment>> {
        return service.getCommentsForPost(postId)
                .subscribeOn(Schedulers.io())
    }

    @SuppressLint("CheckResult")
    private fun updatePosts() {
        service.getPosts()
                .subscribeOn(Schedulers.io())
                .subscribe({
                    postDao.insert(it)
                }, {
                    logger.logException(it)
                })
    }
}