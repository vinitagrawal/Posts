package me.vinitagrawal.posts.profile.data

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import me.vinitagrawal.posts.profile.model.Profile
import javax.inject.Inject

interface ProfileRepository {
    fun getProfileById(userId: Long): Single<Profile>
}

class ProfileRepositoryImpl @Inject constructor(private val service: ProfileService) : ProfileRepository {

    override fun getProfileById(userId: Long): Single<Profile> {
        return service.getProfile(userId)
            .subscribeOn(Schedulers.io())
    }

}