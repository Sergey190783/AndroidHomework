package ru.netology.nmedia.presentation.feed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import ru.netology.nmedia.R
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.utils.PostDiffCallback

class PostsAdapter(
    val interaction: OnInteractionListener
) : ListAdapter<Post, PostsAdapter.PostViewHolder>(PostDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.card, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val btnLike: MaterialButton = itemView.findViewById(R.id.btn_like)
        private val btnShare: MaterialButton = itemView.findViewById(R.id.btn_share)
        private val btnViews: MaterialButton = itemView.findViewById(R.id.btn_views)
        private val menu: MaterialButton = itemView.findViewById(R.id.card_menu)
        private val postLayout: ConstraintLayout = itemView.findViewById(R.id.post_layout)

        private val tvGreeting: TextView = itemView.findViewById(R.id.tv_greeting)
        private val tvDate: TextView = itemView.findViewById(R.id.tv_date)
        private val tvAuthor: TextView = itemView.findViewById(R.id.tv_author)

        private val videoPreview: LinearLayout = itemView.findViewById(R.id.video_layout)

        fun bind(
            post: Post
        ) {
            tvGreeting.text = post.content
            tvDate.text = post.published
            tvAuthor.text = post.author
            btnLike.isChecked = post.likedByMe

            btnLike.text = post.likes.toString()
            btnShare.text = post.reposts.toString()

            btnViews.text = post.views.toString()

            btnLike.setOnClickListener {
                interaction.onLike(post.id)
            }

            btnShare.setOnClickListener {
                interaction.onShare(post)
            }

            postLayout.setOnClickListener {
                interaction.onRequestPostDetails(post)
            }

            if (post.video != null) {
                videoPreview.visibility = View.VISIBLE
                videoPreview.setOnClickListener {
                    interaction.onClickVideoPreview(post.video!!)
                }
            } else {
                videoPreview.visibility = View.GONE
            }

            menu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.options_post)
                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.remove -> {
                                interaction.onRemove(post.id)
                                true
                            }
                            R.id.edit -> {
                                interaction.onEdit(post)
                                true
                            }
                            else -> false
                        }
                    }
                }.show()
            }
        }
    }
}