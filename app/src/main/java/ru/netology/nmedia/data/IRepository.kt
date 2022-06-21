package ru.netology.nmedia.data

import androidx.lifecycle.LiveData

interface IRepository {
    fun get(): LiveData<Post>
    fun like()
    fun share()
}