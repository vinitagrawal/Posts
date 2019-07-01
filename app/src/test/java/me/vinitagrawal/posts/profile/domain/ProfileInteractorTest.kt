package me.vinitagrawal.posts.profile.domain

import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import io.reactivex.Single
import me.vinitagrawal.posts.profile.data.ProfileRepository
import me.vinitagrawal.posts.profile.model.Profile
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.HttpException
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class ProfileInteractorTest {

    @Mock
    private lateinit var repository: ProfileRepository

    private lateinit var useCase: ProfileUseCase

    @Before
    fun setUp() {
        useCase = ProfileInteractor(repository)
    }

    @After
    fun tearDown() {
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun `should fetch user profile`() {
        val profile = mock(Profile::class.java)
        val userId = 1L
        `when`(repository.getProfileById(userId)).thenReturn(Single.just(profile))

        useCase.getProfileById(userId)
                .test()
                .assertComplete()
                .assertNoErrors()
                .assertValue {
                    assertEquals(profile, it)
                    true
                }

        verify(repository).getProfileById(userId)
    }

    @Test
    fun `should handle error received while fetching profile`() {
        val response = Response.error<String>(400,
                ResponseBody.create(MediaType.parse("application/json"), "Something went wrong"))
        val exception = HttpException(response)
        val userId = 1L
        `when`(repository.getProfileById(userId)).thenReturn(Single.error(exception))

        useCase.getProfileById(userId)
                .test()
                .assertNotComplete()
                .assertError {
                    it is HttpException && it.code() == 400
                }

        verify(repository).getProfileById(userId)
    }
}