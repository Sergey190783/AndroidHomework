package ru.netology.nmedia.presentation.feed

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ru.netology.nmedia.R
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.presentation.PostViewModel
import ru.netology.nmedia.utils.BundleConstants.POST_KEY
import ru.netology.nmedia.utils.StringArg

class FeedFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var fab: FloatingActionButton

    private val viewModel: PostViewModel by activityViewModels()
    private val adapter = PostsAdapter(object : OnInteractionListener {
        override fun onLike(id: Long) {
            viewModel.like(id)
        }

        override fun onEdit(post: Post) {
            viewModel.edit(post)
            findNavController().navigate(R.id.action_feedFragment_to_editPostFragment,
                Bundle().apply
                {
                    textArg = post.content
                })
        }

        override fun onRemove(id: Long) {
            viewModel.remove(id)
        }

        override fun onShare(post: Post) {
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, post.content)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(intent, getString(R.string.chooser_share_post))
            startActivity(shareIntent)
        }

        override fun onClickVideoPreview(url: String) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }

        override fun onRequestPostDetails(post: Post) {
            findNavController().navigate(R.id.action_feedFragment_to_detailsFragment,
                Bundle().apply
                {
                    putSerializable(POST_KEY, post)
                })
        }
    }
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_feed, container, false)
        recyclerView = view.findViewById(R.id.recycler)
        fab = view.findViewById(R.id.fab)

        recyclerView.adapter = adapter

        viewModel.data.observe(viewLifecycleOwner) { posts ->
            adapter.submitList(posts)
        }

        fab.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_createPostFragment)
        }

        return view
    }

    companion object {
        var Bundle.textArg by StringArg
    }
}