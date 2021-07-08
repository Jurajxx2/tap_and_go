package com.example.tapandgo.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.tapandgo.model.Rental
import kotlinx.coroutines.flow.Flow

@Dao
interface RentalDao {

    @Query("SELECT * FROM rentals")
    fun getAll(): Flow<List<Rental>>

    @Query("SELECT * FROM rentals WHERE id IN (:messageIds)")
    fun loadAllByIds(messageIds: IntArray): Flow<List<Rental>>

    @Insert
    suspend fun insertAll(vararg messages: Rental)

    @Delete
    suspend fun delete(message: Rental)

    @Query("DELETE FROM rentals")
    suspend fun deleteAll()
}