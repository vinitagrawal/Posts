package me.vinitagrawal.posts.post.model

import me.vinitagrawal.posts.profile.model.Profile

sealed class PostsState {

    object Loading : PostsState()
    object LoadComplete : PostsState()
    object Error : PostsState()
    object Empty : PostsState()
    data class Data(val posts: List<Post>) : PostsState()
}

sealed class PostDetailState {

    object Loading : PostDetailState()
    object LoadComplete : PostDetailState()
    object Error : PostDetailState()
    data class Data(val post: Post,
                    val profile: Profile? = null,
                    val comments: List<Comment>? = null) : PostDetailState()
}