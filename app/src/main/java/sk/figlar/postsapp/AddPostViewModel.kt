package sk.figlar.postsapp

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import sk.figlar.postsapp.db.PostDbModel
import javax.inject.Inject

@HiltViewModel
class AddPostViewModel @Inject constructor(
    private val postRepository: PostRepository,
) : ViewModel() {

    suspend fun addPost(post: PostDbModel) {
        postRepository.addPost(post)
    }
}
