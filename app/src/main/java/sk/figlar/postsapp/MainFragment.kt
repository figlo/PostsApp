package sk.figlar.postsapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import sk.figlar.postsapp.databinding.FragmentMainBinding


@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        // setting up recycler view
        val layoutManager = LinearLayoutManager(context)
        binding.rvPosts.layoutManager = layoutManager
        val mDividerItemDecoration = DividerItemDecoration(
            context,
            layoutManager.orientation
        )
        binding.rvPosts.addItemDecoration(mDividerItemDecoration)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // setting up buttons listeners
        setupAddPostButton()
        setupGetApiPostsButton()
        setupDeleteAllPostsButton()
        setupSearchByUserIdButton()
        setupSearchByPostIdButton()

        // collecting and displaying data in recycler view
        setupRecyclerView()
    }

    private fun setupAddPostButton() {
        binding.btnAddPost.setOnClickListener {
            deleteSearchInputs()
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToAddPostFragment())
        }
    }

    private fun setupGetApiPostsButton() {
        binding.btnGetApiPosts.setOnClickListener {
            deleteSearchInputs()
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.getApiPosts()
            }
        }
    }

    private fun setupDeleteAllPostsButton() {
        binding.btnDeletePosts.setOnClickListener {
            deleteSearchInputs()
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.deleteAllPosts()
            }
        }
    }

    private fun setupSearchByUserIdButton() {
        binding.btnSearchByUserId.setOnClickListener {
            viewModel.mUserId = binding.etSearchByUserId.text.toString().toIntOrNull()
            binding.etSearchByPostId.setText("")
            viewModel.mPostId = null
            viewModel.refreshPosts()
        }
    }

    private fun setupSearchByPostIdButton() {
        binding.btnSearchByPostId.setOnClickListener {
            viewModel.mPostId = binding.etSearchByPostId.text.toString().toIntOrNull()
            binding.etSearchByUserId.setText("")
            viewModel.mUserId = null
            viewModel.refreshPosts()
        }
    }

    private fun setupRecyclerView(){
        val deleteCallback: ((postId: Int) -> Unit) = { postId -> onDeletePost(postId) }
        val editCallback: ((postId: Int) -> Unit) = { postId -> onEditPost(postId) }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.postsFlow.collect { posts ->
                    val adapter = PostAdapter(posts, deleteCallback, editCallback)
                    binding.rvPosts.adapter = adapter
                }
            }
        }
    }

    private fun deleteSearchInputs() {
        binding.etSearchByUserId.setText("")
        binding.etSearchByPostId.setText("")
        viewModel.mUserId = null
        viewModel.mPostId = null
        viewModel.refreshPosts()
    }

    private fun onEditPost(id: Int) {
        findNavController().navigate(MainFragmentDirections.actionMainFragmentToPostFragment(id))
    }

    private fun onDeletePost(id: Int) {
        deleteSearchInputs()
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.deletePost(id)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}