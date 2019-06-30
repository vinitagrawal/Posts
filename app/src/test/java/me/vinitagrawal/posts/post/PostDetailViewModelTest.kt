package me.vinitagrawal.posts.post

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.check
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import io.reactivex.Single
import me.vinitagrawal.common.utils.Logger
import me.vinitagrawal.posts.post.model.Comment
import me.vinitagrawal.posts.post.model.Post
import me.vinitagrawal.posts.post.model.PostDetailState
import me.vinitagrawal.posts.post.model.PostDetailState.*
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
class PostDetailViewModelTest {
    @get:Rule
    val rxSchedulersRule = RxSchedulersOverrideRule()

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var useCase: PostsUseCase
    @Mock
    private lateinit var logger: Logger

    private lateinit var viewModel: PostDetailViewModel

    private lateinit var testObserver: TestObserver<PostDetailState>

    private val postId = 1L

    @Before
    fun setUp() {
        viewModel = PostDetailViewModel(useCase, logger)
        testObserver = viewModel.getData(postId).test()
    }

    @After
    fun tearDown() {
        verifyNoMoreInteractions(useCase, logger)
    }

    @Test
    fun `should fetch post details`() {
        val post = Post(1, 1, "title", "body")
        val comments = listOf(mock(Comment::class.java))
        `when`(useCase.getPostById(postId)).thenReturn(Single.just(post))
        `when`(useCase.getCommentsForPost(post.id)).thenReturn(Single.just(comments))

        viewModel.onRender()

        verify(useCase).getPostById(postId)
        verify(useCase).getCommentsForPost(post.id)
        testObserver.assertValues {
            assertEquals(listOf(Loading, LoadComplete, Data(post, comments)), it)
        }
    }

    @Test
    fun `should handle post fetch failure`() {
        val response = Response.error<String>(400,
                ResponseBody.create(MediaType.parse("application/json"), "Something went wrong"))
        val exception = HttpException(response)
        `when`(useCase.getPostById(postId)).thenReturn(Single.error(exception))

        viewModel.onRender()

        verify(useCase).getPostById(postId)
        verify(logger).logException(check { it is HttpException })
        testObserver.assertValues {
            assertEquals(listOf(Loading, LoadComplete, Error), it)
        }
    }

    @Test
    fun `should handle comments fetch failure and render received post details`() {
        val response = Response.error<String>(400,
                ResponseBody.create(MediaType.parse("application/json"), "Something went wrong"))
        val exception = HttpException(response)
        val post = Post(1, 1, "title", "body")
        `when`(useCase.getPostById(postId)).thenReturn(Single.just(post))
        `when`(useCase.getCommentsForPost(post.id)).thenReturn(Single.error(exception))

        viewModel.onRender()

        verify(useCase).getPostById(postId)
        verify(useCase).getCommentsForPost(post.id)
        verify(logger).logException(check { it is HttpException })
        testObserver.assertValues {
            assertEquals(listOf(Loading, LoadComplete, Data(post)), it)
        }
    }
}