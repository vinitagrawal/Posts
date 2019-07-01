package me.vinitagrawal.posts.profile.data

import io.reactivex.Single
import me.vinitagrawal.posts.UrlMap.Companion.PROFILE_URL
import me.vinitagrawal.posts.profile.model.Profile
import retrofit2.http.GET
import retrofit2.http.Path

interface ProfileService {

    @GET(PROFILE_URL)
    fun getProfile(@Path("id") userId: Long): Single<Profile>
}