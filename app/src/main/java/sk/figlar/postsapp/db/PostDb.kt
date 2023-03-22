package sk.figlar.postsapp.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [PostDbModel::class],
    version = 1,
    exportSchema = false,
)
abstract class PostDb: RoomDatabase() {
    abstract val postsDao: PostDao
}