package sk.figlar.postsapp

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import sk.figlar.postsapp.api.PostApi
import sk.figlar.postsapp.api.UserApiModel
import sk.figlar.postsapp.api.toDbModel
import sk.figlar.postsapp.db.PostDao
import sk.figlar.postsapp.db.PostDbModel
import sk.figlar.postsapp.db.toApiModel
import sk.figlar.postsapp.db.toDomainModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostRepository @Inject constructor(
    private val dao: PostDao,
    private val postApi: PostApi,
) {
    fun getPostsFlow(): Flow<List<PostDbModel>> = dao.getPostsFlow()

//    suspend fun getPost(id: Int): PostDomainModel? = dao.get(id)?.toDomainModel()

    suspend fun getApiPosts() {
        try {
            val apiPosts = postApi.getPostApiModels()
            val dbPosts = dao.getPostsFlow().first()
            val newApiPosts = apiPosts - dbPosts.toApiModel().toSet()
            dao.insertAll(newApiPosts.toDbModel())
            Log.d("Repository", "Gettting API posts, size: ${newApiPosts.size}")
        } catch (ex: Exception) {
            Log.e("Repository", "Failed to fetch posts: $ex")
        }
    }

    suspend fun validateUser(userId: Int): Boolean {
        val users = getApiUsers()
        return users?.any { it.id == userId } ?: false
    }

    private suspend fun getApiUsers(): List<UserApiModel>? {
        return try {
            val apiUsers = postApi.getUserApiModels()
            Log.d("Repository", "Gettting API users, size: ${apiUsers.size}")
            apiUsers
        } catch (ex: Exception) {
            Log.e("Repository", "Failed to fetch users: $ex")
            null
        }
    }

    suspend fun deleteAllPosts() {
        try {
            dao.deleteAllPosts()
        } catch (ex: Exception) {
            Log.e("Repository", "Failed to delete all posts: $ex")
        }
    }

    suspend fun updatePost(postDbModel: PostDbModel) {
        try {
            dao.update(postDbModel)
        } catch (ex: Exception) {
            Log.e("Repository", "Failed to update post(${postDbModel.id}): $ex")
        }
    }

    suspend fun addPost(postDbModel: PostDbModel) {
        try {
            dao.addPost(postDbModel)
        } catch (ex: Exception) {
            Log.e("Repository", "Failed to add post(${postDbModel.id}): $ex")
        }
    }

    suspend fun deletePost(id: Int) {
        try {
            dao.deletePost(id)
        } catch (ex: Exception) {
            Log.e("Repository", "Failed to delete post(id: $id): $ex")
        }
    }

    suspend fun getPost(id: Int): PostDomainModel {
//        try {
        return dao.getPost(id)!!.toDomainModel()
//        } catch (ex: Exception) {
//            Log.e("Repository", "Failed to get post($id): $ex")
//        }
    }
}
