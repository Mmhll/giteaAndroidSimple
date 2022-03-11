package com.example.gitea.fragments

import android.content.Context
import android.os.Binder
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.gitea.R
import com.example.gitea.adaptersandother.UsersAdapter
import com.example.gitea.databinding.FragmentUsersBinding
import com.example.gitea.dataclasses.User
import com.example.gitea.retrofit.MyRetrofit
import com.example.gitea.retrofit.RetrofitApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UsersFragment : Fragment() {

    private lateinit var binding : FragmentUsersBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUsersBinding.inflate(inflater)
        val prefs = requireActivity().getSharedPreferences("URI", Context.MODE_PRIVATE)
        val retrofit = MyRetrofit().getRetrofit(prefs.getString("uri", "http://192.168.207.37:3000").toString())
        Log.d("PREFS", prefs.getString("token", "").toString())
        retrofit.create(RetrofitApi::class.java)
            .getUsers(prefs.getString("token", "").toString())
            .enqueue(
                object : Callback<ArrayList<User>>{
                    override fun onResponse(
                        call: Call<ArrayList<User>>,
                        response: Response<ArrayList<User>>
                    ) {
                        if (response.isSuccessful){
                            response.body()!!.let {
                                val adapter = UsersAdapter(requireContext(), it)
                                adapter.setOnItemClick(object :UsersAdapter.onItemClickListener{
                                    override fun onItemClick(position: Int) {
                                        prefs.edit().putString("USERNAME", it[position].username).apply()
                                        requireActivity().supportFragmentManager.beginTransaction().replace(R.id.container, UserReposFragment()).commit()
                                    }
                                })
                                binding.userRecycler.adapter = adapter
                                binding.userRecycler.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
                            }
                        }
                        Log.d("TAG", response.code().toString())
                    }

                    override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {

                    }

                })

        return binding.root
    }
}