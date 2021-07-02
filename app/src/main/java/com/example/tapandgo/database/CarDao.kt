package com.example.tapandgo.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CarDao {

    /*@Query("SELECT * FROM cars")
    fun getAll(): Flow<List<Car>>

    @Query("SELECT * FROM cars")
    suspend fun getAllSuspended(): List<Car>

    @Query("SELECT * FROM cars WHERE id IN (:messageIds)")
    fun loadAllByIds(messageIds: IntArray): Flow<List<Car>>

    @Insert
    fun insertAll(vararg messages: Car)

    @Delete
    fun delete(message: Car)

    @Query("DELETE FROM cars")
    fun deleteAll()*/
}