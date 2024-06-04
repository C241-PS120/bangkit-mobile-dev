package com.example.coffeeprotectionandanalysissystem.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface HistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addHistory(history: History)

    @Query("SELECT * FROM History_Scan ORDER BY id DESC")
    suspend fun getAllHistory(): List<History>

    @Query("DELETE FROM History_Scan WHERE id = :id")
    suspend fun removeHistory(id: Int)
}
