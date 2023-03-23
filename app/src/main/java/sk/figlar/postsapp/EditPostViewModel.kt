package sk.figlar.postsapp

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import sk.figlar.postsapp.db.PostDbModel
import javax.inject.Inject

@HiltViewModel
class EditPostViewModel @Inject constructor(
    private val repository: PostRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val navArgs = EditPostFragmentArgs.fromSavedStateHandle(savedStateHandle)
    private val id = navArgs.id

    var post: PostDomainModel? = null

    init {
        viewModelScope.launch {
            post = getPost()
        }
    }

    suspend fun getPost(): PostDomainModel {
        return repository.getPost(id)
    }

    suspend fun updatePost(post: PostDbModel) {
        repository.updatePost(post)
    }

    suspend fun deletePost(id: Int) {
        repository.deletePost(id)
    }
}
