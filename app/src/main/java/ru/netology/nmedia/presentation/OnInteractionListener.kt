package ru.netology.nmedia.presentation

import ru.netology.nmedia.data.Post

interface OnInteractionListener {
    fun onLike(id: Long) {}
    fun onEdit(post: Post) {}
    fun onRemove(id: Long) {}
    fun onShare(id: Long) {}
}