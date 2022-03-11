package com.example.gitea.retrofit

import com.example.gitea.dataclasses.Repository
import com.example.gitea.dataclasses.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitApi {
    @GET("/")
    fun getMain() : Call<Void>
    @GET("/api/v1/admin/users")
    fun getUsers(@Query("access_token") access_token : String) : Call<ArrayList<User>>
    @GET("/api/v1/users/{user}/repos")
    fun getRepos(@Path("user") user : String) : Call<ArrayList<Repository>>
}