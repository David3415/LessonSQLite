package com.blocknote.blocknote.db

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MyDbManager(context: Context) {
    val myDbHelper = MyDbHelper(context)
    var db: SQLiteDatabase? = null
    fun openDb() {
        db = myDbHelper.writableDatabase
    }

    fun insertToDb(title: String, content: String) {

            val values = ContentValues().apply {
                put(MyDbNameClass.COLUMN_NAME_TITLE, title)
                put(MyDbNameClass.COLUMN_NAME_CONTENT, content)
                           }
            db?.insert(MyDbNameClass.TABLE_NAME, null, values)
        }


     fun readDbData(): ArrayList<ListItem>
    {
        val dataList = ArrayList<ListItem>()//лист из базы

        val cursor = db?.query(
            MyDbNameClass.TABLE_NAME, null, null,
            null, null, null, null
        )

        while (cursor?.moveToNext()!!) {

            var dataTitle = cursor.getString(cursor.getColumnIndex(MyDbNameClass.COLUMN_NAME_TITLE))
            val dataContent = cursor.getString(cursor.getColumnIndex(MyDbNameClass.COLUMN_NAME_CONTENT))

            var item = ListItem()
            item.title = dataTitle
            item.desc = dataContent

            dataList.add(item)
        }
        cursor.close()
        return dataList
    }
}