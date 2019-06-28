package me.vinitagrawal.posts.post

import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import io.reactivex.Observable
import me.vinitagrawal.posts.post.data.PostsRepository
import me.vinitagrawal.posts.post.model.Post
import me.vinitagrawal.posts.post.usecase.PostsInteractor
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.HttpException
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class PostsInteractorTest {

    @get:Rule
    val rx2SchedulersRule = RxSchedulersOverrideRule()

    @Mock
    private lateinit var repository: PostsRepository

    private lateinit var useCase: PostsInteractor

    @Before
    fun setUp() {
        useCase = PostsInteractor(repository)
    }

    @After
    fun tearDown() {
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun `should fetch posts`() {
        val posts = listOf<Post>()
        `when`(repository.getPosts()).thenReturn(Observable.just(posts))

        useCase.getPosts()
            .test()
            .assertComplete()
            .assertNoErrors()
            .assertValue {
                Assert.assertEquals(posts, it)
                true
            }

        verify(repository).getPosts()
    }

    @Test
    fun `should handle error received while fetching posts`() {
        val response = Response.error<String>(400,
            ResponseBody.create(MediaType.parse("application/json"), "Something went wrong"))
        val exception = HttpException(response)
        `when`(repository.getPosts()).thenReturn(Observable.error(exception))

        repository.getPosts()
            .test()
            .assertNotComplete()
            .assertError {
                it is HttpException && it.code() == 400
            }

        verify(repository).getPosts()
    }
}