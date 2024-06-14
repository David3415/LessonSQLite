package com.blocknote.blocknote

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blocknote.blocknote.databinding.ActivityMainBinding
import com.blocknote.blocknote.db.MyAdapter
import com.blocknote.blocknote.db.MyDbManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    val myDbManager = MyDbManager(this)
    val myAdapter = MyAdapter(ArrayList(), this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    override fun onResume() {
        super.onResume()
        myDbManager.openDb()
        fillAdapter()
    }

    fun init() {
        binding.rcView.layoutManager = LinearLayoutManager(this)
        binding.rcView.adapter = myAdapter
    }

    fun okFun(view: View) {
        val intent=Intent(this,EditActivity::class.java)
        startActivity(intent)
    }


    fun fillAdapter() {

        val list = myDbManager.readDbData()
        myAdapter.updateAdapter(list)
    }
}