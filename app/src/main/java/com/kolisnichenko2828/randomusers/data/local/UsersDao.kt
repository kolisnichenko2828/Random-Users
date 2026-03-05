package com.kolisnichenko2828.randomusers.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UsersDao {
    @Query("SELECT * FROM users ORDER BY localId ASC LIMIT :limit OFFSET :offset")
    suspend fun getUsers(limit: Int, offset: Int): List<UsersEntity>

    @Query("SELECT * FROM users WHERE uuid = :uuid LIMIT 1")
    suspend fun getUserById(uuid: String): UsersEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<UsersEntity>)

    @Query("DELETE FROM users")
    suspend fun clearAll()
}