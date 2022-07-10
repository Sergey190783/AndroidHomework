package ru.netology.nmedia.data

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.JsonIOException
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken

class FileRepositoryImpl(
    private val context: Context
) : Repository {
    private val gson = Gson()
    private val type = TypeToken.getParameterized(List::class.java, Post::class.java).type
    private val fileName = "posts.json"
    private var nextId: Long = 1
    private var posts = emptyList<Post>()
    private val data = MutableLiveData(posts)

    init {
        val file = context.filesDir.resolve(fileName)
        if (file.exists()) {
            context.openFileInput(fileName).bufferedReader().use {
                try {
                    posts = gson.fromJson(it, type)
                } catch (e: Exception) {
                    if (e is JsonSyntaxException || e is JsonIOException) {
                        posts = emptyList()
                    }
                } finally {
                    data.value = posts
                    //find max index, in latter launches of the app, this prevent repeated indexes
                    nextId = posts.sortedWith(compareBy{it.id}).last().id + 1
                }
            }
        } else {
            sync()
        }
    }

    private fun sync() {
        context.openFileOutput(fileName, Context.MODE_PRIVATE).bufferedWriter().use {
            it.write(gson.toJson(posts))
        }
    }

    override fun getAll(): LiveData<List<Post>> = data

    override fun like(id: Long) {
        Log.d("mytag", posts.toString())
        posts = posts.map {
            when {
                it.id != id -> it
                it.likedByMe -> it.copy(likedByMe = !it.likedByMe, likes = it.likes - 1)
                else -> it.copy(likedByMe = !it.likedByMe, likes = it.likes + 1)
            }

        }
        data.value = posts
        sync()
    }

    override fun share(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(reposts = it.reposts + 1)
        }
        data.value = posts
        sync()
    }

    override fun remove(id: Long) {
        posts = posts.filter { it.id != id }
        data.value = posts
        sync()
    }

    override fun save(post: Post) {
        if (post.id == 0L) {
            posts = listOf(
                post.copy(
                    id = nextId++,
                    author = "Me",
                    likedByMe = false,
                    published = "now"
                )
            ) + posts
            data.value = posts
            sync()
            return
        }

        posts = posts.map {
            if (it.id != post.id) it else it.copy(content = post.content)
        }
        data.value = posts
        sync()
    }
}
