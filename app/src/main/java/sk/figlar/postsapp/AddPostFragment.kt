package sk.figlar.postsapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import sk.figlar.postsapp.databinding.FragmentAddPostBinding
import sk.figlar.postsapp.db.PostDbModel

@AndroidEntryPoint
class AddPostFragment : Fragment() {

    private var _binding: FragmentAddPostBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private val viewModel: AddPostViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddPostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSaveButton()
    }

    private fun setupSaveButton() {
        binding.btnSave.setOnClickListener {
            val userIdString = binding.etUserId.text.toString()
            val title = binding.etTitle.text.toString()
            val body = binding.etBody.text.toString()
            if (!isValidUserId(userIdString)) {
                Toast.makeText(
                    context,
                    "Please enter valid userId.",
                    Toast.LENGTH_LONG
                ).show()
            } else if (!isValidText(title)) {
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
                    val newPost = PostDbModel(
                        userId = userIdString.toInt(),
                        title = title,
                        body = body,
                    )
                    viewModel.addPost(newPost)
                }
                findNavController().navigate(AddPostFragmentDirections.actionAddPostFragmentToMainFragment())
            }
        }
    }

    private fun isValidUserId(userIdString: String): Boolean {
        val userId = userIdString.toIntOrNull()

        return if (userId == null)          // user id is not Int
            false
        else {                              // validation through api
            var isValid: Boolean
            runBlocking {
                isValid = viewModel.validateUser(userId)
            }
            isValid
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}