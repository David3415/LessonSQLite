package com.blocknote.blocknote

import android.content.Intent
import android.icu.util.Calendar
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.blocknote.blocknote.constance.Constance
import com.blocknote.blocknote.constance.MyIntentConstances
import com.blocknote.blocknote.databinding.ActivityEditBinding
import com.blocknote.blocknote.db.MyDbManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class EditActivity : AppCompatActivity() {
    lateinit var binding: ActivityEditBinding

    var id = 0
    var isEditState = false
    val myDbManager = MyDbManager(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
          }

    override fun onResume() {
        super.onResume()
        myDbManager.openDb()
    }

    fun onClickSave(view: View) {
        val tmp1: TextView = findViewById(R.id.edDescription)
        val tmp: TextView = findViewById(R.id.edTitle)
        val myTitle = tmp.text.toString()
        val myDesk = tmp1.text.toString()

        myDbManager.insertToDb(myTitle, myDesk)

        finish()
    }

    /* fun getMyIntents() {
         val i = intent
         binding.fbEditEnable.visibility = View.GONE
         if (i != null) {
             if (i.getStringExtra(MyIntentConstances.I_TITLE_KEY) != null) {
                 binding.fbAddImage.visibility = View.GONE
                 binding.edTitle.setText(i.getStringExtra(MyIntentConstances.I_TITLE_KEY))
                 isEditState = true
                 binding.edTitle.isEnabled = false
                 binding.edDescription.isEnabled = false
                 binding.fbEditEnable.visibility = View.VISIBLE
                 id = i.getIntExtra(MyIntentConstances.I_ID_KEY, 0)
                 binding.edDescription.setText(i.getStringExtra(MyIntentConstances.I_DESK_KEY))
                 if (i.getStringExtra(MyIntentConstances.I_URI_KEY) != "empty") {
                     binding.mainImageLayout.visibility = View.VISIBLE
                     tempImageUri = i.getStringExtra(MyIntentConstances.I_URI_KEY)!!
                     binding.imMainImage.setImageURI(Uri.parse(tempImageUri))

                     binding.inButtImgDelete.visibility = View.GONE
                     binding.inButtImgEdit.visibility = View.GONE
                 }
             }
         }
     }*/
}