package sk.figlar.postsapp.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import sk.figlar.postsapp.db.PostDao
import sk.figlar.postsapp.db.PostDb
import javax.inject.Singleton

private const val DB_NAME = "posts_db"

@InstallIn(SingletonComponent::class)
@Module
object DbModule {

    @Singleton
    @Provides
    fun providePostsDb(@ApplicationContext appContext: Context): PostDb =
        Room.databaseBuilder(
            appContext,
            PostDb::class.java,
            DB_NAME,
        ).build()

    @Singleton
    @Provides
    fun provideApodDao(db: PostDb): PostDao = db.postsDao
}
