package me.vinitagrawal.posts.post

import com.nhaarman.mockitokotlin2.check
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import io.reactivex.Observable
import io.reactivex.Single
import me.vinitagrawal.common.utils.Logger
import me.vinitagrawal.posts.post.data.PostDao
import me.vinitagrawal.posts.post.data.PostsRepositoryImpl
import me.vinitagrawal.posts.post.data.PostsService
import me.vinitagrawal.posts.post.model.Comment
import me.vinitagrawal.posts.post.model.Post
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.HttpException
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class PostsRepositoryImplTest {

    @get:Rule
    val rx2SchedulersRule = RxSchedulersOverrideRule()

    @Mock
    private lateinit var service: PostsService
    @Mock
    private lateinit var postDao: PostDao
    @Mock
    private lateinit var logger: Logger

    @InjectMocks
    private lateinit var repository: PostsRepositoryImpl

    @After
    fun tearDown() {
        verifyNoMoreInteractions(service, postDao)
    }

    @Test
    fun `should fetch posts and update database`() {
        val posts = listOf<Post>(mock(Post::class.java))
        `when`(postDao.getAllPosts()).thenReturn(Observable.just(emptyList(), posts))
        `when`(service.getPosts()).thenReturn(Single.just(posts))

        repository.getPosts()
                .test()
                .assertComplete()
                .assertNoErrors()
                .assertValues(emptyList(), posts)

        verify(service).getPosts()
        verify(postDao).insert(posts)
        verify(postDao).getAllPosts()
    }

    @Test
    fun `should fetch posts from database`() {
        val posts = listOf<Post>(mock(Post::class.java))
        `when`(postDao.getAllPosts()).thenReturn(Observable.just(posts))

        repository.getPosts()
                .test()
                .assertComplete()
                .assertNoErrors()
                .assertValue {
                    assertEquals(posts, it)
                    true
                }

        verify(postDao).getAllPosts()
    }

    @Test
    fun `should handle error received while fetching posts`() {
        val response = Response.error<String>(400,
                ResponseBody.create(MediaType.parse("application/json"), "Something went wrong"))
        val exception = HttpException(response)
        `when`(postDao.getAllPosts()).thenReturn(Observable.just(emptyList()))
        `when`(service.getPosts()).thenReturn(Single.error(exception))

        repository.getPosts()
                .test()
                .assertComplete()
                .assertNoErrors()
                .assertValue {
                    assertEquals(emptyList<List<Post>>(), it)
                    true
                }

        verify(service).getPosts()
        verify(postDao).getAllPosts()
        verify(logger).logException(check {
            it is HttpException && it.code() == 400
        })
    }

    @Test
    fun `should handle failure while fetching posts from database`() {
        `when`(postDao.getAllPosts()).thenReturn(Observable.error(Exception()))

        repository.getPosts()
                .test()
                .assertNotComplete()
                .assertError {
                    it is Exception
                }

        verify(postDao).getAllPosts()
    }

    @Test
    fun `should fetch post by id`() {
        val post = Post(1, 1, "title", "body")
        val postId = 1L
        `when`(postDao.getPostById(postId)).thenReturn(Single.just(post))

        repository.getPostById(postId)
                .test()
                .assertComplete()
                .assertNoErrors()
                .assertValue {
                    assertEquals("title", it.title)
                    true
                }

        verify(postDao).getPostById(postId)
    }

    @Test
    fun `should handle failure while fetching post by id`() {
        val postId = 1L
        `when`(postDao.getPostById(postId)).thenReturn(Single.error(Exception()))

        repository.getPostById(postId)
                .test()
                .assertNotComplete()
                .assertError {
                    it is Exception
                }

        verify(postDao).getPostById(postId)
    }

    @Test
    fun `should fetch comments for post id`() {
        val comments = listOf(mock(Comment::class.java))
        val postId = 1L
        `when`(service.getCommentsForPost(postId)).thenReturn(Single.just(comments))

        repository.getCommentsForPost(postId)
            .test()
            .assertComplete()
            .assertNoErrors()
            .assertValue {
                assertEquals(comments, it)
                true
            }

        verify(service).getCommentsForPost(postId)
    }

    @Test
    fun `should handle failure on fetching comments`() {
        val response = Response.error<String>(400,
            ResponseBody.create(MediaType.parse("application/json"), "Something went wrong"))
        val exception = HttpException(response)
        val postId = 1L
        `when`(service.getCommentsForPost(postId)).thenReturn(Single.error(exception))

        repository.getCommentsForPost(postId)
            .test()
            .assertNotComplete()
            .assertError {
                it is Exception
            }

        verify(service).getCommentsForPost(postId)
    }

}