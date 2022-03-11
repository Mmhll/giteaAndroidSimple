package com.example.gitea.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.gitea.R
import com.example.gitea.adaptersandother.UserReposAdapter
import com.example.gitea.databinding.FragmentUserReposBinding
import com.example.gitea.dataclasses.Repository
import com.example.gitea.retrofit.MyRetrofit
import com.example.gitea.retrofit.RetrofitApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserReposFragment : Fragment() {

    private lateinit var binding : FragmentUserReposBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentUserReposBinding.inflate(inflater)
        val prefs = requireActivity().getSharedPreferences("URI", Context.MODE_PRIVATE)
        val retrofit = MyRetrofit()
            .getRetrofit(prefs.getString("uri", "").toString())
            .create(RetrofitApi::class.java)
            .getRepos(prefs.getString("USERNAME", "").toString())
            .enqueue(object : Callback<ArrayList<Repository>>{
                override fun onResponse(
                    call: Call<ArrayList<Repository>>,
                    response: Response<ArrayList<Repository>>
                ) {
                    if (response.isSuccessful){
                        response.body()!!.let {
                            binding.reposRecycler.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
                            binding.reposRecycler.adapter = UserReposAdapter(requireContext(), it)
                        }
                    }
                }

                override fun onFailure(call: Call<ArrayList<Repository>>, t: Throwable) {

                }

            })


        return binding.root
    }
}