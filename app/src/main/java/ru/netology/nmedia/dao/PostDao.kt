package ru.netology.nmedia.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import ru.netology.nmedia.dto.PostEntity

@Dao
interface PostDao {
    @Query("SELECT * FROM PostEntity WHERE id = :id")
    fun getById(id: Int): PostEntity?

    @Query("SELECT * FROM PostEntity ORDER BY id DESC")
    fun getAll(): List<PostEntity>

    @Insert
    fun insert(post: PostEntity)

    fun save(post: PostEntity) = if (post.id == 0) {
            insert(post)
        } else {
            update(post)
        }

    @Update
    fun update(post: PostEntity)

    @Query("""
        UPDATE PostEntity SET
        likeCount = likeCount + CASE WHEN likeCount THEN -1 ELSE 1 END,
        likeByMe = CASE WHEN likeByMe THEN 0 ELSE 1 END
        WHERE id = :id
        """)
    fun likeById(id: Int)

    @Query("DELETE FROM PostEntity WHERE id = :id")
    fun removeById(id: Int)

    @Query("""
        UPDATE PostEntity SET
        shareCount = shareCount + 1
        WHERE id = :id
        """)
    fun shareById(id: Int)
}