package ru.netology.nmedia.presentation.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import ru.netology.nmedia.R
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.presentation.PostViewModel
import ru.netology.nmedia.presentation.feed.FeedFragment.Companion.textArg
import ru.netology.nmedia.utils.BundleConstants.POST_KEY

class DetailsFragment : Fragment() {
    private lateinit var btnLike: MaterialButton
    private lateinit var btnShare: MaterialButton
    private lateinit var btnViews: MaterialButton
    private lateinit var menu: MaterialButton

    private lateinit var tvGreeting: TextView
    private lateinit var tvDate: TextView
    private lateinit var tvAuthor: TextView

    private lateinit var videoPreview: LinearLayout

    private val viewModel: PostViewModel by activityViewModels()

    private lateinit var post: Post

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_details, container, false)
        btnLike = view.findViewById(R.id.btn_like)
        btnShare = view.findViewById(R.id.btn_share)
        btnViews = view.findViewById(R.id.btn_views)
        menu = view.findViewById(R.id.card_menu)
        tvGreeting = view.findViewById(R.id.tv_greeting)
        tvDate = view.findViewById(R.id.tv_date)
        tvAuthor = view.findViewById(R.id.tv_author)
        videoPreview = view.findViewById(R.id.video_layout)

        post = arguments?.getSerializable(POST_KEY) as? Post ?: Post(
            -1, "", "", "", false, 0, 0, 0
        )

        updateFragment(post)

        btnLike.setOnClickListener {
            viewModel.like(post.id)
        }

        btnShare.setOnClickListener {
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, post.content)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(intent, getString(R.string.chooser_share_post))
            startActivity(shareIntent)
        }

        if (post.video != null) {
            videoPreview.visibility = View.VISIBLE
            videoPreview.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.video))
                startActivity(intent)
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
                            viewModel.remove(post.id)
                            findNavController().navigate(R.id.action_detailsFragment_to_feedFragment)
                            true
                        }
                        R.id.edit -> {
                            viewModel.edit(post)
                            findNavController().navigate(R.id.action_detailsFragment_to_editPostFragment,
                                Bundle().apply
                                {
                                    textArg = post.content
                                })
                            true
                        }
                        else -> false
                    }
                }
            }.show()
        }

        viewModel.data.observe(viewLifecycleOwner) { posts ->
            val newPost = posts.find { it.id == post.id }
            newPost?.let {
                if (newPost != post)
                    updateFragment(it)
            }
        }
        return view
    }

    fun updateFragment(post: Post) {
        tvGreeting.text = post.content
        tvDate.text = post.published
        tvAuthor.text = post.author
        btnLike.isChecked = post.likedByMe

        btnLike.text = post.likes.toString()
        btnShare.text = post.reposts.toString()

        btnViews.text = post.views.toString()
    }
}