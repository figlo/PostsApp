package sk.figlar.postsapp.api

import com.squareup.moshi.JsonClass
import sk.figlar.postsapp.db.PostDbModel
import kotlin.text.Typography.copyright

@JsonClass(generateAdapter = true)
data class UserApiModel(
    val id: Int,
)