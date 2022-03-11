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
import com.example.gitea.dataclasses.User

class UsersAdapter(val context : Context, val data : ArrayList<User>) : RecyclerView.Adapter<UsersAdapter.VH>() {

    private lateinit var myListener : onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClick(listener : onItemClickListener){
        myListener = listener
    }

    class VH(itemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val name : TextView = itemView.findViewById(R.id.userName)
        val image : ImageView = itemView.findViewById(R.id.userImage)
        val registration : TextView = itemView.findViewById(R.id.userRegistration)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(LayoutInflater.from(parent.context).inflate(R.layout.one_user, parent, false), myListener)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.name.text = "Nickname\n" + data[position].username
        Glide
            .with(context)
            .load(data[position].avatar_url)
            .circleCrop()
            .into(holder.image)
        holder.registration.text = "Registration date\n" + data[position].created
    }

    override fun getItemCount(): Int {
        return data.size
    }
}