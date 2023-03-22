package sk.figlar.postsapp.api

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PostApiModel(
    val id: String,
    val userId: String,
    val title: String,
    val body: String,
)