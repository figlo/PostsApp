package sk.figlar.postsapp

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import sk.figlar.postsapp.db.PostDbModel
import sk.figlar.postsapp.db.toDomainModel
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val postRepository: PostRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val navArgs = PostFragmentArgs.fromSavedStateHandle(savedStateHandle)
    private val id = navArgs.id

    var post: PostDomainModel? = null

    init {
        viewModelScope.launch {
            post = getPost()
        }
    }


    suspend fun getPost(): PostDomainModel {
        return postRepository.getPost(id)
    }

    suspend fun updatePost(post: PostDbModel) {
        postRepository.updatePost(post)
    }

    suspend fun deletePost(id: Int) {
        postRepository.deletePost(id)
    }
}
