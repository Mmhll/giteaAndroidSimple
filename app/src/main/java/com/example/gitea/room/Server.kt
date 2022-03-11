package com.example.gitea.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "server")
data class Server(
    @PrimaryKey val uid : Int,
    @ColumnInfo(name = "serverName") val serverName : String?
)
