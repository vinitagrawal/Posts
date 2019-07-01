package me.vinitagrawal.posts.profile.domain

import io.reactivex.Single
import me.vinitagrawal.posts.profile.data.ProfileRepository
import me.vinitagrawal.posts.profile.model.Profile
import javax.inject.Inject

interface ProfileUseCase {
    fun getProfileById(userId: Long): Single<Profile>
}

class ProfileInteractor @Inject constructor(private val repository: ProfileRepository) : ProfileUseCase {

    override fun getProfileById(userId: Long): Single<Profile> {
        return repository.getProfileById(userId)
    }

}