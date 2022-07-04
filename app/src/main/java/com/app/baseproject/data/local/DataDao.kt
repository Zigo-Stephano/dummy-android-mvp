package com.app.baseproject.data.local

import androidx.room.*
import com.app.baseproject.data.model.local.User
import io.reactivex.Flowable

@Dao
interface DataDao {

    @Insert
    fun addUser(user: User)

    @Insert
    fun addAllUser(user: List<User>)

    @Update
    fun updateUser(user: User)

    @Delete
    fun deleteUser(user: User)

    @Query("SELECT * FROM users")
    fun getAllUser(): Flowable<List<User>?>

    @Query("SELECT * FROM users WHERE id=:user_id")
    fun getUser(user_id: Int): Flowable<User>

    @Query("DELETE FROM users")
    fun deleteAllUser()

}