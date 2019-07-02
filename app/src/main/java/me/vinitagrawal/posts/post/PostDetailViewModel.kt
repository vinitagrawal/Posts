package me.vinitagrawal.posts.post

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.Singles
import me.vinitagrawal.common.core.BaseViewModel
import me.vinitagrawal.common.utils.Logger
import me.vinitagrawal.posts.post.domain.PostsUseCase
import me.vinitagrawal.posts.post.model.Post
import me.vinitagrawal.posts.post.model.PostDetailState
import me.vinitagrawal.posts.post.model.PostDetailState.*
import me.vinitagrawal.posts.profile.domain.ProfileUseCase
import javax.inject.Inject

class PostDetailViewModel @Inject constructor(private val postsUseCase: PostsUseCase,
                                              private val profileUseCase: ProfileUseCase,
                                              private val logger: Logger) : BaseViewModel() {

    private var postDetail: MutableLiveData<PostDetailState> = MutableLiveData()
    private var postId: Long = -1

    fun getData(postId: Long): LiveData<PostDetailState> {
        this.postId = postId
        return postDetail
    }

    override fun onRender() {
        if (postDetail.value !is Data)
            postsUseCase.getPostById(postId)
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe { postDetail.value = Loading }
                    .doOnError { postDetail.value = LoadComplete }
                    .subscribe({
                        getComments(it)
                    }, {
                        logger.logException(it)
                        postDetail.value = Error
                    })
                    .autoDispose()
    }

    private fun getComments(post: Post) {
        Singles.zip(
            profileUseCase.getProfileById(post.userId),
            postsUseCase.getCommentsForPost(post.id))
                .observeOn(AndroidSchedulers.mainThread())
                .doOnEvent { _, _ -> postDetail.value = LoadComplete }
                .subscribe({
                    postDetail.value = Data(post, it.first, it.second)
                }, {
                    logger.logException(it)
                    postDetail.value = Data(post)
                })
                .autoDispose()
    }
}