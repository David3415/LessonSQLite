package com.example.lessonsqllite

import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.transition.Visibility
import com.example.lessonsqllite.constance.Constance
import com.example.lessonsqllite.databinding.ActivityMainBinding
import com.example.lessonsqllite.db.MyDbManager

class EditActivity : AppCompatActivity() {
    var tempImageUri = "empty"
    val myDbManager = MyDbManager(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit)

    }

    override fun onResume() {
        super.onResume()
        myDbManager.openDb()
    }

    override fun onDestroy() {
        super.onDestroy()
        myDbManager.closeDB()
    }

    ////////-----------------------------
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == Constance.IMAGE_REQUEST_CODE) {
            val imMainImage: ImageView = findViewById(R.id.imMainImage)
            imMainImage.setImageURI(data?.data)
            tempImageUri = data?.data.toString()
        }
    }

    fun onClickChooseImage(view: View) {
        // val intent = Intent(Intent.ACTION_PICK)
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, Constance.IMAGE_REQUEST_CODE)
    }

    fun onClickAddImage(view: View) {
        var mainImageLayout: ConstraintLayout = findViewById(R.id.mainImageLayout);
        mainImageLayout.visibility = View.VISIBLE
    }

    fun onClickDeleteImage(view: View) {
        var mainImageLayout: ConstraintLayout = findViewById(R.id.mainImageLayout);
        mainImageLayout.visibility = View.GONE
    }

    fun onClickSave(view: View) {
        val tmp: TextView = findViewById(R.id.edDescription)
        val tmp1: TextView = findViewById(R.id.edTitle)
        val myTitle = tmp.text.toString()
        val myDesk = tmp1.text.toString()
        if (myTitle != "" && myDesk != "") {
            myDbManager.insertToDb(myTitle, myDesk, tempImageUri)
        }
    }
}