package com.example.gitea.adaptersandother

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gitea.R
import com.example.gitea.dataclasses.Repository
import com.example.gitea.dataclasses.User

class UserReposAdapter(val context : Context, val data : ArrayList<Repository>) : RecyclerView.Adapter<UserReposAdapter.VH>() {

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val repo : TextView = itemView.findViewById(R.id.repoName)
        val user : TextView = itemView.findViewById(R.id.userNameEmail)
        val url : TextView = itemView.findViewById(R.id.repoUrl)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(LayoutInflater.from(parent.context).inflate(R.layout.one_user_repo, parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.user.text = data[position].owner.username + "(" + data[position].owner.email + ")"
        holder.repo.text = data[position].name
        holder.url.text = data[position].html_url
    }

    override fun getItemCount(): Int {
        return data.size
    }
}