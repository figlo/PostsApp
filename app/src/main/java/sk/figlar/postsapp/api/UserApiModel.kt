package sk.figlar.postsapp.api

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserApiModel(
    val id: Int,
)