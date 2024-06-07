package com.example.lessonsqllite

import android.content.Intent
import android.media.Image
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.transition.Visibility
import com.example.lessonsqllite.constance.Constance
import com.example.lessonsqllite.constance.MyIntentConstances
import com.example.lessonsqllite.databinding.ActivityEditBinding
import com.example.lessonsqllite.db.MyDbManager


class EditActivity : AppCompatActivity() {
    lateinit var binding: ActivityEditBinding
    var tempImageUri = "empty"
    var id = 0
    var isEditState = false
    val myDbManager = MyDbManager(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getMyIntents()
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
            contentResolver.takePersistableUriPermission(
                data?.data!!,
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
        }
    }

    fun onClickChooseImage(view: View) {
        // val intent = Intent(Intent.ACTION_PICK)
        //val intent = Intent(Intent.ACTION_GET_CONTENT)
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.type = "image/*"
        // intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
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
        val tmp1: TextView = findViewById(R.id.edDescription)
        val tmp: TextView = findViewById(R.id.edTitle)
        val myTitle = tmp.text.toString()
        val myDesk = tmp1.text.toString()
        if (myTitle != "" && myDesk != "") {
            if (isEditState) {
                binding.fbSave.isEnabled = true
                myDbManager.updateItem(myTitle, myDesk, tempImageUri, id)
            } else {
                myDbManager.insertToDb(myTitle, myDesk, tempImageUri)
            }
        }
        finish()
    }

    fun onEditEnable(view: View) {
        binding.edTitle.isEnabled = true
        binding.edDescription.isEnabled = true
    }

    fun getMyIntents() {
        val i = intent
        if (i != null) {
            if (i.getStringExtra(MyIntentConstances.I_TITLE_KEY) != null) {
                binding.fbAddImage.visibility = View.GONE
                binding.edTitle.setText(i.getStringExtra(MyIntentConstances.I_TITLE_KEY))
                isEditState = true
                binding.edTitle.isEnabled = false
                binding.edDescription.isEnabled = false
                id = i.getIntExtra(MyIntentConstances.I_ID_KEY, 0)
                binding.edDescription.setText(i.getStringExtra(MyIntentConstances.I_DESK_KEY))
                if (i.getStringExtra(MyIntentConstances.I_URI_KEY) != "empty") {
                    binding.mainImageLayout.visibility = View.VISIBLE
                    binding.imMainImage.setImageURI(Uri.parse(i.getStringExtra(MyIntentConstances.I_URI_KEY)))

                    binding.inButtImgDelete.visibility = View.GONE
                    binding.inButtImgEdit.visibility = View.GONE
                    // binding.fbSave.visibility = View.GONE

                }
            }
        }
    }
}