package ru.netology.nmedia.data

import androidx.lifecycle.LiveData

interface Repository {
    fun getAll(): LiveData<List<Post>>
    fun like(id: Long)
    fun share(id: Long)
    fun remove(id: Long)
    fun save(post:Post)
}