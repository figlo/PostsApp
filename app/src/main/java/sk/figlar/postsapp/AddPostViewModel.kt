package sk.figlar.postsapp

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import sk.figlar.postsapp.db.PostDbModel
import javax.inject.Inject

@HiltViewModel
class AddPostViewModel @Inject constructor(
    private val repository: PostRepository,
) : ViewModel() {

    suspend fun addPost(post: PostDbModel) {
        repository.addPost(post)
    }

    suspend fun validateUser(userId: Int): Boolean {
        return repository.validateUser(userId)
    }
}
