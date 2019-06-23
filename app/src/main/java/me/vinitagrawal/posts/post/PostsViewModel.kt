package me.vinitagrawal.posts.post

import android.annotation.SuppressLint
import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import me.vinitagrawal.posts.post.model.Post

class PostsViewModel : ViewModel() {

    private var useCase: PostsUseCase = PostsInteractor()

    private var postList: MutableLiveData<List<Post>> = MutableLiveData()

    fun getPosts(): LiveData<List<Post>> {
        fetch()
        return postList
    }

    @SuppressLint("CheckResult")
    private fun fetch() {
        useCase.getPosts()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { postList.postValue(it) },
                { Log.e("PostViewModel", it.message) }
            )
    }

    @VisibleForTesting
    fun setUseCase(postsUseCase: PostsUseCase) {
        useCase = postsUseCase
    }
}