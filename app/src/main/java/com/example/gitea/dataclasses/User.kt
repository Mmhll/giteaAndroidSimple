package com.example.gitea.dataclasses

data class User(
        val id : Int,
        val login : String,
        val full_name : String,
        val email : String,
        val avatar_url : String,
        val language : String,
        val is_admin : Boolean,
        val last_login : String,
        val created : String,
        val restricted : Boolean,
        val active : Boolean,
        val prohibit_login : Boolean,
        val location : String,
        val website : String,
        val description : String,
        val visibility : String,
        val followers_count : Int,
        val following_count : Int,
        val starred_repos_count :Int,
        val username : String
    )
