package sk.figlar.postsapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import sk.figlar.postsapp.databinding.FragmentEditPostBinding

@AndroidEntryPoint
class EditPostFragment : Fragment() {

    private var _binding: FragmentEditPostBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private val viewModel: EditPostViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditPostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            val post = viewModel.getPost()
            if (post != null) {
                binding.id.text = "Id: " + post.id
                binding.userId.text = "UserId: " + post.userId
                binding.etTitle.setText(post.title)
                binding.etBody.setText(post.body)
            }
        }

        setupSaveButton()
    }

    private fun setupSaveButton() {
        binding.btnSave.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val body = binding.etBody.text.toString()
            if (!isValidText(title)) {
                Toast.makeText(
                    context,
                    "Please enter valid title.",
                    Toast.LENGTH_LONG
                ).show()
            } else if (!isValidText(body)) {
                Toast.makeText(
                    context,
                    "Please enter valid body.",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                GlobalScope.launch {
                    val post = viewModel.getPost()
                    if (post != null) {
                        val newPost = post.copy(title = title, body = body)
                        viewModel.updatePost(newPost.toDbModel())
                    }
                }
                findNavController().navigate(EditPostFragmentDirections.actionEditPostFragmentToMainFragment())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}