package com.example.madlevel4task2.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.madlevel4task2.model.GameLog

@Dao
interface GameLogDao {

    @Query("SELECT * FROM gameLogTable")
    fun getAllGames(): List<GameLog>

    @Insert
    fun insertGame(reminder: GameLog)

    @Query("DELETE FROM gameLogTable")
    fun deleteAllGames()

    @Query("SELECT COUNT(*) FROM gameLogTable WHERE result = 'Draw'")
    fun getDrawCount(): String

    @Query("SELECT COUNT(*) FROM gameLogTable WHERE result = 'Computer wins!'")
    fun getLoseCount(): String

    @Query("SELECT COUNT(*) FROM gameLogTable WHERE result = 'You win!'")
    fun getWinCount(): String

}
