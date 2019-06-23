package me.vinitagrawal.posts.post

import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import io.reactivex.Single
import me.vinitagrawal.posts.post.model.Post
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
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.HttpException
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class PostsRepositoryImplTest {

    @get:Rule
    val rx2SchedulersRule = Rx2SchedulersOverrideRule()

    @Mock
    private lateinit var service: PostsService

    private lateinit var repository: PostsRepositoryImpl

    @Before
    fun setUp() {
        repository = PostsRepositoryImpl()
        repository.setService(service)
    }

    @After
    fun tearDown() {
        verifyNoMoreInteractions(service)
    }

    @Test
    fun `should fetch posts`() {
        val posts = listOf<Post>()
        `when`(service.getPosts()).thenReturn(Single.just(posts))

        repository.getPosts()
            .test()
            .assertComplete()
            .assertNoErrors()
            .assertValue {
                assertEquals(posts, it)
                true
            }

        verify(service).getPosts()
    }

    @Test
    fun `should handle error received while fetching posts`() {
        val response = Response.error<String>(400,
            ResponseBody.create(MediaType.parse("application/json"), "Something went wrong"))
        val exception = HttpException(response)
        `when`(service.getPosts()).thenReturn(Single.error(exception))

        repository.getPosts()
            .test()
            .assertNotComplete()
            .assertError {
                it is HttpException && it.code() == 400
            }

        verify(service).getPosts()
    }
}