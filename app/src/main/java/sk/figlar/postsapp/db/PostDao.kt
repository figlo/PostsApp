package sk.figlar.postsapp.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {

    @Query("SELECT * FROM posts ORDER BY id DESC")
    fun getPostsFlow(): Flow<List<PostDbModel>>

    @Query("SELECT * FROM posts ORDER BY id DESC")
    fun getPostByUserIdFlow(): Flow<List<PostDbModel>>

    @Query("SELECT * FROM posts WHERE id = :id")
    suspend fun get(id: Int): PostDbModel?

    @Insert
    suspend fun insert(post: PostDbModel)

    @Delete
    suspend fun delete(post: PostDbModel)

    @Query("DELETE FROM posts WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("DELETE FROM posts")
    suspend fun clear()

    @Query("SELECT COUNT(*) FROM posts")
    suspend fun count(): Long
}