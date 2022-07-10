package ru.netology.nmedia.data

import java.io.Serializable

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    val likedByMe: Boolean,
    val likes: Int,
    val reposts: Int,
    val views: Int,
    var video: String? = null
): Serializable
