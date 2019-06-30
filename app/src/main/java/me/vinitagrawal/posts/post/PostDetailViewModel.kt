package me.vinitagrawal.posts.post

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import me.vinitagrawal.common.core.BaseViewModel
import me.vinitagrawal.common.utils.Logger
import me.vinitagrawal.posts.post.model.PostDetailState
import me.vinitagrawal.posts.post.model.PostDetailState.*
import me.vinitagrawal.posts.post.usecase.PostsUseCase
import javax.inject.Inject

class PostDetailViewModel @Inject constructor(private val postsUseCase: PostsUseCase,
                                              private val logger: Logger) : BaseViewModel() {

    private var postDetail: MutableLiveData<PostDetailState> = MutableLiveData()
    private var postId: Long = 0

    fun getData(postId: Long): LiveData<PostDetailState> {
        this.postId = postId
        return postDetail
    }

    override fun onRender() {
        if (postDetail.value !is Data)
            postsUseCase.getPostById(postId)
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe { postDetail.value = Loading }
                    .doOnEvent { _, _ -> postDetail.value = LoadComplete }
                    .subscribe({
                        postDetail.value = Data(it)
                    }, {
                        logger.logException(it)
                        postDetail.value = Error
                    })
                    .autoDispose()
    }
}