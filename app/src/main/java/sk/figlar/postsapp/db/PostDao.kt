package sk.figlar.postsapp.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {

    @Query("SELECT * FROM posts ORDER BY id DESC")
    fun getPostsFlow(): Flow<List<PostDbModel>>

    @Query("SELECT * FROM posts WHERE id = :id")
    suspend fun getPost(id: Int): PostDbModel?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPost(post: PostDbModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPosts(posts: List<PostDbModel>)

    @Update
    suspend fun updatePost(post: PostDbModel)

    @Query("DELETE FROM posts WHERE id = :id")
    suspend fun deletePost(id: Int)

    @Query("DELETE FROM posts")
    suspend fun deleteAllPosts()

    @Query("SELECT COUNT(*) FROM posts")
    suspend fun count(): Long
}