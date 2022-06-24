package ru.netology.nmedia.data

import androidx.lifecycle.LiveData

interface IRepository {
    fun getAll(): LiveData<List<Post>>
    fun like(id: Long)
    fun share(id: Long)
}