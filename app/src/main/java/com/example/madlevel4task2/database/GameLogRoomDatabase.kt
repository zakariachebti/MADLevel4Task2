package com.example.madlevel4task2.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.madlevel4task2.dao.GameLogDao
import com.example.madlevel4task2.model.GameLog

@Database(entities = [GameLog::class], version = 1, exportSchema = false)
abstract class GameLogRoomDatabase : RoomDatabase() {

    abstract fun gameLogDao(): GameLogDao

    companion object {
        private const val DATABASE_NAME = "REMINDER_DATABASE"

        @Volatile
        private var gameLogRoomDatabase: GameLogRoomDatabase? = null

        fun getDatabase(context: Context): GameLogRoomDatabase? {
            if (gameLogRoomDatabase == null) {
                synchronized(GameLogRoomDatabase::class.java) {
                    if (gameLogRoomDatabase == null) {
                        gameLogRoomDatabase = Room.databaseBuilder(
                            context.applicationContext,
                            GameLogRoomDatabase::class.java, DATABASE_NAME
                        )
                            .build()
                    }
                }
            }
            return gameLogRoomDatabase
        }
    }

}
