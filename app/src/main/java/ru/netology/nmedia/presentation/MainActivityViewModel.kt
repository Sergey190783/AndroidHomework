package ru.netology.nmedia.presentation

import androidx.lifecycle.ViewModel
import ru.netology.nmedia.data.IRepository
import ru.netology.nmedia.data.Repository

class MainActivityViewModel(
    private val repository: IRepository = Repository()
) : ViewModel() {
    val data = repository.getAll()

    fun like(id: Long) = repository.like(id)

    fun share(id: Long) = repository.share(id)
}