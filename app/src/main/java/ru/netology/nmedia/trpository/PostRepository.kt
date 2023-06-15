package ru.netology.nmedia.trpository

import ru.netology.nmedia.dto.Post

interface PostRepository {

    fun get(): Post

    fun like()

    fun share()
}