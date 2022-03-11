package com.example.gitea.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.room.Room
import com.example.gitea.databinding.ActivityMainBinding
import com.example.gitea.retrofit.MyRetrofit
import com.example.gitea.retrofit.RetrofitApi
import com.example.gitea.room.AppDatabase
import com.example.gitea.room.Server
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val db = Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "myDb"
        ).allowMainThreadQueries().build()

        if (db.serverDao().getAll().isNotEmpty()){
            var array = ArrayList<String>()
            array.add("")
            for (i in 0 until db.serverDao().getAll().size)
            {
                array.add(db.serverDao().getAll()[i].serverName!!)
            }
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, array)
            binding.spinner.adapter = adapter
        }
        else{
            binding.spinner.visibility = View.INVISIBLE
        }
        binding.spinner.selectedItem
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val selectedItem = p0?.getItemAtPosition(p2).toString()
                binding.editAddress.setText(selectedItem)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }
        binding.button.setOnClickListener {
            val uri = binding.editAddress.text.toString()
            if (uri.isNotEmpty()) {
                try {
                    val retrofit = MyRetrofit().getRetrofit(uri).create(RetrofitApi::class.java)
                    retrofit.getMain().enqueue(object : Callback<Void> {
                        override fun onResponse(call: Call<Void>, response: Response<Void>) {
                            Log.d("RESPONSE CODE", response.code().toString())
                            if (response.isSuccessful) {
                                var uid = 0
                                try {
                                    uid = db.serverDao().getAll()[db.serverDao()
                                        .getAll().size - 1].uid
                                } catch (e: Exception) {

                                }
                                db.serverDao().insert(Server(uid + 1, uri))
                                var array = ArrayList<String>()
                                array.add("")
                                for (i in 0 until db.serverDao().getAll().size) {
                                    array.add(db.serverDao().getAll()[i].serverName!!)
                                }
                                Log.d("SIZE", array.size.toString())
                                val adapter = ArrayAdapter(
                                    this@MainActivity,
                                    android.R.layout.simple_spinner_item,
                                    array
                                )
                                binding.spinner.adapter = adapter
                                binding.spinner.visibility = View.VISIBLE

                                val prefs = getSharedPreferences("URI", Context.MODE_PRIVATE)
                                prefs.edit().putString("uri", uri).apply()
                                if (binding.editToken.text.toString().isNotEmpty()){
                                    prefs.edit().putString("token", binding.editToken.text.toString()).apply()
                                }
                                startActivity(Intent(this@MainActivity, SignedActivity::class.java))
                            }
                        }

                        override fun onFailure(call: Call<Void>, t: Throwable) {
                            Log.d("FAIL", t.message.toString())
                        }

                    })
                }
                catch (e : Exception){
                    Log.d("EXCEPTION", e.toString())
                }
            }
        }
    }
}