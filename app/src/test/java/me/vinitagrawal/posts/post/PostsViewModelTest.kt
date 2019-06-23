package me.vinitagrawal.posts.post

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import io.reactivex.Single
import me.vinitagrawal.posts.post.model.Post
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PostsViewModelTest {

    @get:Rule
    val rx2SchedulersRule = Rx2SchedulersOverrideRule()

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var useCase: PostsUseCase

    private lateinit var viewModel: PostsViewModel

    @Before
    fun setUp() {
        viewModel = PostsViewModel()
        viewModel.setUseCase(useCase)
    }

    @After
    fun tearDown() {
        verifyNoMoreInteractions(useCase)
    }

    @Test
    fun `should observe posts for updates`() {
        val postList = listOf<Post>()
        `when`(useCase.getPosts()).thenReturn(Single.just(postList))

        assertEquals(0, viewModel.getPosts().value?.size)
        verify(useCase).getPosts()
    }
}