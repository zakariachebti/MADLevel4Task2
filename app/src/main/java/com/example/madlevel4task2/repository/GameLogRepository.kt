package com.example.madlevel4task2.repository

import android.content.Context
import com.example.madlevel4task2.dao.GameLogDao
import com.example.madlevel4task2.database.GameLogRoomDatabase
import com.example.madlevel4task2.model.GameLog

class GameLogRepository(context: Context) {

    private lateinit var gameLogDao: GameLogDao

    init {
        val gameLogRoomDatabase = GameLogRoomDatabase.getDatabase(context)
        gameLogDao = gameLogRoomDatabase!!.gameLogDao()
    }

    fun getAllGames(): List<GameLog> {
        return gameLogDao.getAllGames()
    }

    fun insertGame(game: GameLog) {
        gameLogDao.insertGame(game)
    }

    fun deleteAllGames() {
        gameLogDao.deleteAllGames()
    }

    fun getDraw(): String {
        return gameLogDao.getDrawCount()
    }

    fun getWin(): String {
        return gameLogDao.getWinCount()
    }

    fun getLose(): String {
        return gameLogDao.getLoseCount()
    }

}
