package com.example.lessonsqllite

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.lessonsqllite.databinding.ActivityMainBinding
import com.example.lessonsqllite.db.MyDbManager


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    val myDbManager = MyDbManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        myDbManager.closeDB()
    }

    fun onClickNew(view: View) {

    }

    override fun onDestroy() {
        super.onDestroy()
        myDbManager.closeDB()
    }

}