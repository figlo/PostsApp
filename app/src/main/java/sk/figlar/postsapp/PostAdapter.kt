package sk.figlar.postsapp

import android.hardware.biometrics.BiometricManager
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import sk.figlar.postsapp.databinding.PostItemBinding

class PostViewHolder(
    private val binding: PostItemBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(postDomainModel: PostDomainModel, onDeletePost: (postId: Int) -> Unit) {
        with(binding) {
            postId.text = "Id: " + postDomainModel.id.toString()
            postUserId.text = "UserId: " + postDomainModel.userId.toString()
            postTitle.text = "Title: " + postDomainModel.title.toString()
            postBody.text = "Body: " + postDomainModel.body.toString()

//            root.setOnClickListener { onDeletePost(postDomainModel.id) }
            btnPostDelete.setOnClickListener {
                onDeletePost(postDomainModel.id)
            }
        }
    }
}

class PostAdapter(
    private val posts: List<PostDomainModel>,
    private val onDeletePost: (postId: Int) -> Unit,
) : RecyclerView.Adapter<PostViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PostViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PostItemBinding.inflate(inflater, parent, false)
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val item = posts[position]
        holder.bind(item, onDeletePost)
    }

    override fun getItemCount() = posts.size
}
