package sk.figlar.postsapp

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import sk.figlar.postsapp.api.PostApi
import sk.figlar.postsapp.api.toDbModel
import sk.figlar.postsapp.db.PostDao
import sk.figlar.postsapp.db.PostDbModel
import sk.figlar.postsapp.db.toApiModel
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

    suspend fun deleteAllPosts() {
        try {
            dao.deleteAllPosts()
        } catch (ex: Exception) {
            Log.e("Repository", "Failed to delete all posts: $ex")
        }
    }
}
