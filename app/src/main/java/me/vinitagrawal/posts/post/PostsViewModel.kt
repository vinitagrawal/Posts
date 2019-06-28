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
import me.vinitagrawal.posts.post.usecase.PostsUseCase
import javax.inject.Inject

class PostsViewModel @Inject constructor(private val postsUseCase: PostsUseCase,
                                         private val logger: Logger) : BaseViewModel<PostsState>() {

    private var postList: MutableLiveData<PostsState> = MutableLiveData()

    override fun getData(): LiveData<PostsState> {
        return postList
    }

    @SuppressLint("CheckResult")
    override fun onRender() {
        if (postList.value !is DataState)
            postsUseCase.getPosts()
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe { postList.value = LoadingState }
                    .doOnEvent { postList.value = LoadCompleteState }
                    .subscribe({
                        if (it.isNotEmpty())
                            postList.value = DataState(it)
                        else
                            postList.value = EmptyState
                    }, {
                        logger.logException(it)
                        postList.value = ErrorState
                    })
    }
}