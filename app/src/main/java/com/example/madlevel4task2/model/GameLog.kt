package com.example.madlevel4task2.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "gameLogTable")
data class GameLog(

    @ColumnInfo(name = "date")
    var gameDate: String,

    @ColumnInfo(name = "move_computer")
    var moveComputer: String,

    @ColumnInfo(name = "move_player")
    var movePlayer: String,

    @ColumnInfo(name = "result")
    var result: String,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null

)
