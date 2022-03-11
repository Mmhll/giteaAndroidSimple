package com.example.gitea.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gitea.R
import com.example.gitea.fragments.UsersFragment

class SignedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logined)
        supportFragmentManager.beginTransaction().replace(R.id.container, UsersFragment()).commit()
    }
}