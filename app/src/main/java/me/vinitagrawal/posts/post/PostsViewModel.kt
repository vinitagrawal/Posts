package me.vinitagrawal.posts.post

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import me.vinitagrawal.common.core.BaseViewModel
import me.vinitagrawal.common.utils.Logger
import me.vinitagrawal.common.utils.doOnEvent
import me.vinitagrawal.posts.post.model.PostsState
import me.vinitagrawal.posts.post.model.PostsState.*
import me.vinitagrawal.posts.post.domain.PostsUseCase
import javax.inject.Inject

class PostsViewModel @Inject constructor(private val postsUseCase: PostsUseCase,
                                         private val logger: Logger) : BaseViewModel() {

    private var postList: MutableLiveData<PostsState> = MutableLiveData()

    fun getData(): LiveData<PostsState> {
        return postList
    }

    @SuppressLint("CheckResult")
    override fun onRender() {
        if (postList.value !is Data)
            postsUseCase.getPosts()
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe { postList.value = Loading }
                    .doOnEvent { postList.value = LoadComplete }
                    .subscribe({
                        if (it.isNotEmpty())
                            postList.value = Data(it)
                        else
                            postList.value = Empty
                    }, {
                        logger.logException(it)
                        postList.value = Error
                    })
                    .autoDispose()
    }
}