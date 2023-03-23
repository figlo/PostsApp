package sk.figlar.postsapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
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

        val navHostFragment = requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(navController.graph)

        val toolbar = binding.toolbar
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
        toolbar.setupWithNavController(navController, appBarConfiguration)
        toolbar.title = "Add Post"

        setupSaveButton()
    }

    private fun setupSaveButton() {
        binding.btnSave.setOnClickListener {
            val userIdString = binding.etUserId.text.toString()
            if (!isValidUserId(userIdString)) {
                Toast.makeText(
                    context,
                    "Please enter valid userId.",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                val title = binding.etTitle.text.toString()
                val body = binding.etBody.text.toString()
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
        return userId != null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
