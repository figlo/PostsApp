package sk.figlar.postsapp.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import sk.figlar.postsapp.PostDomainModel
import sk.figlar.postsapp.api.PostApiModel

@Entity(tableName = "posts")
data class PostDbModel(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "user_id")
    val userId: Int = 0,

    @ColumnInfo(name = "title")
    val title: String = "",

    @ColumnInfo(name = "body")
    val body: String = "",
)

fun List<PostDbModel>.toApiModel(): List<PostApiModel> {
    return map {
        PostApiModel(
            id = it.id,
            userId = it.userId,
            title = it.title,
            body = it.body,
        )
    }
}

fun PostDbModel.toDomainModel(): PostDomainModel {
    return PostDomainModel(
        id = id,
        userId = userId,
        title = title,
        body = body,
    )
}