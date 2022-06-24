package ru.netology.nmedia.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
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
        private val btnLike: ImageButton = itemView.findViewById(R.id.btn_like)
        private val btnShare: ImageButton = itemView.findViewById(R.id.btn_share)

        private val tvLikes: TextView = itemView.findViewById(R.id.tv_likes)
        private val tvShares: TextView = itemView.findViewById(R.id.tv_share)
        private val tvGreeting: TextView = itemView.findViewById(R.id.tv_greeting)
        private val tvDate: TextView = itemView.findViewById(R.id.tv_date)
        private val tvAuthor: TextView = itemView.findViewById(R.id.tv_author)
        private val tvViews: TextView = itemView.findViewById(R.id.tv_views)
        private val menu: ImageButton = itemView.findViewById(R.id.card_menu)

        fun bind(
            post: Post
        ) {
            tvGreeting.text = post.content
            tvDate.text = post.published
            tvAuthor.text = post.author

            tvLikes.text = post.likes.toString()
            tvShares.text = post.reposts.toString()
            tvViews.text = post.views.toString()

            btnLike.setOnClickListener {
                interaction.onLike(post.id)
            }

            btnShare.setOnClickListener {
                interaction.onShare(post.id)
            }

            menu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.options_post)
                    setOnMenuItemClickListener { item ->
                        when(item.itemId){
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