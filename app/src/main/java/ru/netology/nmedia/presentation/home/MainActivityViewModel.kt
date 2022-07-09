package ru.netology.nmedia.presentation.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.data.IRepository
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.data.Repository

class MainActivityViewModel(
    private val repository: IRepository = Repository()
) : ViewModel() {
    private val empty = Post(
        id = 0,
        content = "",
        author = "",
        published = "",
        likedByMe = false,
        likes = 0,
        reposts = 0,
        views = 0
    )
    val data = repository.getAll()
    val edited = MutableLiveData(empty)

    fun save() {
        edited.value?.let {
            repository.save(it)
        }
        edited.value = empty
    }

    fun changeContent(content: String) {
        edited.value?.let {
            val text = content.trim()
            if (it.content == text)
                return
            edited.value = it.copy(content = text)
        }
    }

    fun like(id: Long) = repository.like(id)

    fun share(id: Long) = repository.share(id)

    fun remove(id: Long) = repository.remove(id)

    fun edit(post: Post) {
        edited.value = post
    }
}