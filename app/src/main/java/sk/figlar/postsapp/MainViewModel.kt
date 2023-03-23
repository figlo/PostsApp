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

    var mUserId: Int? = null
    var mPostId: Int? = null

    init {
        refreshPosts()
    }

    fun refreshPosts() {
        viewModelScope.launch {
            postRepository.getPostsFlow().collect { postDbModelList ->
                val posts = postDbModelList.map { it.toDomainModel() }
                if (mUserId != null) {
                    _postsFlow.value = posts.filter { it.userId == mUserId }
                } else if(mPostId != null) {
                    if (!postRepository.isPostInDb(mPostId!!)) {
                        postRepository.getPostFromApi(mPostId!!)
                    }
                    _postsFlow.value = posts.filter { it.id == mPostId }
                } else {
                    _postsFlow.value = posts
                }
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
