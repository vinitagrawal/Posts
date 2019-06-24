package me.vinitagrawal.posts.post

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import me.vinitagrawal.posts.post.model.Post
import me.vinitagrawal.posts.post.usecase.PostsUseCase
import javax.inject.Inject

class PostsViewModel @Inject constructor(private val postsUseCase: PostsUseCase) : ViewModel() {

    private var postList: MutableLiveData<List<Post>> = MutableLiveData()

    fun getPosts(): LiveData<List<Post>> {
        fetch()
        return postList
    }

    @SuppressLint("CheckResult")
    private fun fetch() {
        postsUseCase.getPosts()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { postList.postValue(it) },
                { Log.e("PostViewModel", it.message) }
            )
    }

}