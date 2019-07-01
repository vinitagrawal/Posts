package me.vinitagrawal.posts.profile.data

import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import io.reactivex.Single
import me.vinitagrawal.posts.post.RxSchedulersOverrideRule
import me.vinitagrawal.posts.profile.model.Profile
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
class ProfileRepositoryImplTest {

    @get:Rule
    val rx2SchedulersRule = RxSchedulersOverrideRule()

    @Mock
    private lateinit var service: ProfileService

    private lateinit var repository: ProfileRepository

    @Before
    fun setUp() {
        repository = ProfileRepositoryImpl(service)
    }

    @After
    fun tearDown() {
        verifyNoMoreInteractions(service)
    }

    @Test
    fun `should fetch user profile`() {
        val profile = mock(Profile::class.java)
        val userId = 1L
        `when`(service.getProfile(userId)).thenReturn(Single.just(profile))

        repository.getProfileById(userId)
                .test()
                .assertComplete()
                .assertNoErrors()
                .assertValue {
                    assertEquals(profile, it)
                    true
                }

        verify(service).getProfile(userId)
    }

    @Test
    fun `should handle error received while fetching profile`() {
        val response = Response.error<String>(400,
                ResponseBody.create(MediaType.parse("application/json"), "Something went wrong"))
        val exception = HttpException(response)
        val userId = 1L
        `when`(service.getProfile(userId)).thenReturn(Single.error(exception))

        repository.getProfileById(userId)
                .test()
                .assertNotComplete()
                .assertError {
                    it is HttpException && it.code() == 400
                }

        verify(service).getProfile(userId)
    }
}