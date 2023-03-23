package sk.figlar.postsapp.api

import com.squareup.moshi.JsonClass
import sk.figlar.postsapp.db.PostDbModel

@JsonClass(generateAdapter = true)
data class PostApiModel(
    val id: Int,
    val userId: Int,
    val title: String,
    val body: String,
)

fun List<PostApiModel>.toDbModel(): List<PostDbModel> {
    return map {
        PostDbModel(
            id = it.id,
            userId = it.userId,
            title = it.title,
            body = it.body,
        )
    }
}

fun PostApiModel.toDbModel(): PostDbModel {
    return PostDbModel(
        id = id,
        userId = userId,
        title = title,
        body = body,
    )
}