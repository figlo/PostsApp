package sk.figlar.postsapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import sk.figlar.postsapp.db.toDomainModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val postRepository: PostRepository,
) : ViewModel() {

    private var _postsFlow: MutableStateFlow<List<PostDomainModel>> = MutableStateFlow(emptyList())
    val postsFlow: StateFlow<List<PostDomainModel>>
        get() = _postsFlow.asStateFlow()

    init {
        viewModelScope.launch {
            postRepository.getPostsFlow().collect { postDbModelList ->
                _postsFlow.value = postDbModelList.map { it.toDomainModel() }
            }
        }
    }

    suspend fun getApiPosts() {
        postRepository.getApiPosts()
    }

    suspend fun deleteAllPosts() {
        postRepository.deleteAllPosts()
    }

    suspend fun deletePost(id: Int) {
        postRepository.deletePost(id)
    }
}
