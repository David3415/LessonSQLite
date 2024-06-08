package com.example.lessonsqllite.db

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

    suspend fun insertToDb(title: String, content: String, uri: String, time: String)= withContext(Dispatchers.IO) {
        val values = ContentValues().apply {
            put(MyDbNameClass.COLUMN_NAME_TITLE, title)
            put(MyDbNameClass.COLUMN_NAME_CONTENT, content)
            put(MyDbNameClass.COLUMN_NAME_IMAGE_URI, uri)
            put(MyDbNameClass.COLUMN_NAME_TIME, time)

        }
        db?.insert(MyDbNameClass.TABLE_NAME, null, values)
    }

    suspend fun updateItem(title: String, content: String, uri: String, id: Int, time: String) =
        withContext(Dispatchers.IO)
        {
            val selection = BaseColumns._ID + "=$id"
            val values = ContentValues().apply {
                put(MyDbNameClass.COLUMN_NAME_TITLE, title)
                put(MyDbNameClass.COLUMN_NAME_CONTENT, content)
                put(MyDbNameClass.COLUMN_NAME_IMAGE_URI, uri)
                put(MyDbNameClass.COLUMN_NAME_TIME, time)

            }
            db?.update(MyDbNameClass.TABLE_NAME, values, selection, null)
        }

    fun removeItemFromDb(id: String) {
        val selection = BaseColumns._ID + "=$id"
        Log.d("MyLog", selection)
        db?.delete(MyDbNameClass.TABLE_NAME, selection, null)
    }

    @SuppressLint("Range")
    suspend fun readDbData(searchText: String): ArrayList<ListItem> = withContext(Dispatchers.IO)
    {
        val dataList = ArrayList<ListItem>()//лист из базы
        val selection = "${MyDbNameClass.COLUMN_NAME_TITLE} LIKE ?"
        val cursor = db?.query(
            MyDbNameClass.TABLE_NAME, null, selection,
            arrayOf("%$searchText%"), null, null, null
        )

        while (cursor?.moveToNext()!!) {

            var dataTitle = cursor.getString(cursor.getColumnIndex(MyDbNameClass.COLUMN_NAME_TITLE))
            val dataContent =
                cursor.getString(cursor.getColumnIndex(MyDbNameClass.COLUMN_NAME_CONTENT))
            val dataUri =
                cursor.getString(cursor.getColumnIndex(MyDbNameClass.COLUMN_NAME_IMAGE_URI))
            val time = cursor.getString(cursor.getColumnIndex(MyDbNameClass.COLUMN_NAME_TIME))
            val dataId = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID))
            var item = ListItem()
            item.title = dataTitle
            item.desc = dataContent
            item.uri = dataUri
            item.time = time
            item.id = dataId

            dataList.add(item)
        }
        cursor.close()
        return@withContext dataList
    }

    fun closeDB() {
        myDbHelper.close()
    }
}