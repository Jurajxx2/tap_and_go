package com.example.tapandgo.database

import androidx.room.*
import com.example.tapandgo.model.Profile
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM user WHERE id=:id")
    fun loadProfile(id: String): Flow<Profile>

    @Query("SELECT * FROM user")
    fun loadProfile(): Flow<Profile>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(profile: Profile)

    @Query("DELETE FROM user")
    suspend fun deleteAll()
}