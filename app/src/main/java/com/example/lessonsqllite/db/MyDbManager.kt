package com.example.lessonsqllite.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase

class MyDbManager(context: Context) {
    val myDbHelper = MyDbHelper(context)
    var db: SQLiteDatabase? = null
    fun openDb() {
        db = myDbHelper.writableDatabase
    }

    fun insertToDb(title: String, content: String, uri: String) {
        val values = ContentValues().apply {
            put(MyDbNameClass.COLUMN_NAME_TITLE, title)
            put(MyDbNameClass.COLUMN_NAME_CONTENT, content)
            put(MyDbNameClass.COLUMN_NAME_IMAGE_URI, uri)
        }
        db?.insert(MyDbNameClass.TABLE_NAME, null, values)
    }

    fun readDbData(): ArrayList<ListItem> {
        val dataList = ArrayList<ListItem>()//лист из базы
        val cursor = db?.query(MyDbNameClass.TABLE_NAME, null, null, null, null, null, null)

        while (cursor?.moveToNext()!!) {

            var dataTitle = cursor.getString(cursor.getColumnIndex(MyDbNameClass.COLUMN_NAME_TITLE))
            val dataContent =
                cursor.getString(cursor.getColumnIndex(MyDbNameClass.COLUMN_NAME_CONTENT))
            val dataUri =
                cursor.getString(cursor.getColumnIndex(MyDbNameClass.COLUMN_NAME_IMAGE_URI))
            var item = ListItem()
            item.title = dataTitle
            item.desc = dataContent
            item.uri = dataUri
            dataList.add(item)
        }
        cursor.close()
        return dataList
    }

    fun closeDB() {
        myDbHelper.close()
    }
}