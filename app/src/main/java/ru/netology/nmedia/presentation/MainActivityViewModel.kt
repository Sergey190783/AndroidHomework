package ru.netology.nmedia.presentation

import androidx.lifecycle.ViewModel
import ru.netology.nmedia.data.IRepository
import ru.netology.nmedia.data.Repository

class MainActivityViewModel(
    private val repository: IRepository = Repository()
) : ViewModel() {
    val data = repository.get()

    fun like() = repository.like()

    fun share() = repository.share()
}