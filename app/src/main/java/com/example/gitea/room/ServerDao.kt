package com.example.gitea.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface ServerDao {
    @Insert
    fun insert(server : Server)
    @Query("SELECT * FROM server WHERE serverName = :name")
    fun getOne(name : String) : Server
    @Query("SELECT * FROM server")
    fun getAll() : List<Server>
}