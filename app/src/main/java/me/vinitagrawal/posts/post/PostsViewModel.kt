package me.vinitagrawal.posts.post

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import me.vinitagrawal.common.core.BaseViewModel
import me.vinitagrawal.common.utils.Logger
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
        postsUseCase.getPosts()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { postList.setValue(LoadingState) }
            .doOnEvent { _, _ -> postList.setValue(LoadCompleteState) }
            .subscribe({
                postList.setValue(DataState(it))
            }, {
                logger.logException(it)
                postList.setValue(ErrorState)
            })
    }
}