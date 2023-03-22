package sk.figlar.postsapp

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import sk.figlar.postsapp.db.PostDao
import sk.figlar.postsapp.db.PostDb
import sk.figlar.postsapp.db.PostDbModel

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class PostsDaoTest {

    private lateinit var db: PostDb
    private lateinit var dao: PostDao

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, PostDb::class.java)
            .allowMainThreadQueries()
            .build()
        dao = db.postsDao
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertAndGetPost() = runBlocking {
        val post = PostDbModel()
        dao.insert(post)
        assertEquals(1, dao.count())

        val loadedPost = dao.get(1)                     // autoincrement primary key starts from 1
        assertEquals(0, loadedPost?.userId)

        dao.clear()
        assertEquals(0, dao.count())
    }
}