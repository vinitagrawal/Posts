package me.vinitagrawal.posts.post.data

import android.annotation.SuppressLint
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import me.vinitagrawal.common.utils.Logger
import me.vinitagrawal.posts.post.model.Post
import javax.inject.Inject

interface PostsRepository {
    fun getPosts(): Observable<List<Post>>
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