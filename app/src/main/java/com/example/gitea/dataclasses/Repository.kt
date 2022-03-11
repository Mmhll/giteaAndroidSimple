package com.example.gitea.dataclasses

data class Repository (val owner: Owner, val name : String, val html_url : String)

data class Owner(val username : String, val email : String)