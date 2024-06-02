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

    fun onClickSave(view: View) {
        myDbManager.openDb()
        myDbManager.insertToDb(binding.edTitle.text.toString(), binding.edContent.text.toString())
        val dataList = myDbManager.readDbData()
        for (item in dataList) {
            binding.tvTest.append(item)
            binding.tvTest.append("\n")
        }
    }
}