package sk.figlar.postsapp

import sk.figlar.postsapp.db.PostDbModel

data class PostDomainModel(
    val id: Int,
    val userId: Int,
    val title: String,
    val body: String,
)

fun PostDomainModel.toDbModel(): PostDbModel {
    return PostDbModel(
        id = id,
        userId = userId,
        title = title,
        body = body,
    )
}
