package me.vinitagrawal.posts.post.model

import me.vinitagrawal.common.core.UIState

sealed class PostsState : UIState {

    object LoadingState : PostsState()
    object LoadCompleteState : PostsState()
    object ErrorState : PostsState()
    data class DataState(val posts: List<Post>) : PostsState()
}