package me.vinitagrawal.posts.post

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.check
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import io.reactivex.Single
import me.vinitagrawal.common.utils.Logger
import me.vinitagrawal.posts.post.model.Post
import me.vinitagrawal.posts.post.model.PostsState
import me.vinitagrawal.posts.post.model.PostsState.*
import me.vinitagrawal.posts.post.usecase.PostsUseCase
import me.vinitagrawal.posts.utils.TestObserver
import me.vinitagrawal.posts.utils.test
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.HttpException
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class PostsViewModelTest {

    @get:Rule
    val rxSchedulersRule = RxSchedulersOverrideRule()

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var useCase: PostsUseCase
    @Mock
    private lateinit var logger: Logger

    private lateinit var viewModel: PostsViewModel

    private lateinit var testObserver: TestObserver<PostsState>

    @Before
    fun setUp() {
        viewModel = PostsViewModel(useCase, logger)
        testObserver = viewModel.getData().test()
    }

    @After
    fun tearDown() {
        verifyNoMoreInteractions(useCase, logger)
    }

    @Test
    fun `should fetch all posts`() {
        val postList = listOf<Post>(mock(Post::class.java))
        `when`(useCase.getPosts()).thenReturn(Single.just(postList))

        viewModel.onRender()

        verify(useCase).getPosts()
        testObserver.assertValues {
            assertEquals(listOf(LoadingState, LoadCompleteState, DataState(postList)), it)
        }
    }

    @Test
    fun `should handle post fetch failure`() {
        val response = Response.error<String>(400,
            ResponseBody.create(MediaType.parse("application/json"), "Something went wrong"))
        val exception = HttpException(response)
        `when`(useCase.getPosts()).thenReturn(Single.error(exception))

        viewModel.onRender()

        verify(useCase).getPosts()
        verify(logger).logException(check { it is HttpException })
        testObserver.assertValues {
            assertEquals(listOf(LoadingState, LoadCompleteState, ErrorState), it)
        }
    }
}