package ru.netology.nmedia.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class Repository : IRepository {
    private var post: Post = Post(
        id = 1,
        author = "Нетология. Университет интернет-профессий будущего",
        content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу." +
                " Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растем сами и " +
                "помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остается" +
                " с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, " +
                "бежать быстрее. Наша миссия - помочь встать на пусть роста и начать цепочку перемен",
        published = "21 мая в 18:36",
        likedByMe = false,
        99,
        50,
        200
    )
    private val data = MutableLiveData(post)

    override fun get(): LiveData<Post> = data

    override fun like() {
        post = if(post.likedByMe)
            post.copy(likedByMe = !post.likedByMe, likes = post.likes - 1)
        else
            post.copy(likedByMe = !post.likedByMe, likes = post.likes + 1)

        data.value = post
    }

    override fun share() {
        post = post.copy(reposts = post.reposts + 1)
        data.value = post
    }
}