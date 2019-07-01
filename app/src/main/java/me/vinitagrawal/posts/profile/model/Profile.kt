package me.vinitagrawal.posts.profile.model

import com.google.gson.annotations.SerializedName
import me.vinitagrawal.posts.UrlMap.Companion.AVATAR_URL

data class Profile(@SerializedName("id") val id: Long,
                   @SerializedName("name") val name: String,
                   @SerializedName("username") val username: String,
                   @SerializedName("email") val email: String,
                   @SerializedName("address") val address: Address,
                   @SerializedName("phone") val phone: String,
                   @SerializedName("website") val website: String,
                   @SerializedName("company") val company: Company) {

    fun getAvatarUrl() = AVATAR_URL + email
}

data class Address(@SerializedName("street") val street: String,
                   @SerializedName("city") val city: String,
                   @SerializedName("suite") val suite: String)

data class Company(@SerializedName("name") val name: String,
                   @SerializedName("catchPhrase") val catchPhrase: String)