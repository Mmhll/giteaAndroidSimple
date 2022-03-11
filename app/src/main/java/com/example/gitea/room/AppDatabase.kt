package com.example.gitea.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Server::class], version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun serverDao() : ServerDao
}