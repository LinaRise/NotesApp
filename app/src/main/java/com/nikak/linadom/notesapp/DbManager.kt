package com.nikak.linadom.notesapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.widget.Toast

class  DbManager(context: Context) {

    val dbName = "MyNotes"
    val dbTable = "Notes"
    val colID = "id"
    val colTitle = "title"
    val colDes = "description"
    val dbVersion = 1
    //CREATE TABLE IF NOT EXISTS MyNotes (ID INTEGER PRIMARY KEY,title TEXT, Description TEXT);"
    val sqlCreateTable = "CREATE TABLE IF NOT EXISTS " + dbTable + " (" + colID + " INTEGER PRIMARY KEY," +
            colTitle + " TEXT, " + colDes + " TEXT);"
    var sqlDB: SQLiteDatabase? = null

    init {
        val db = DatabaseHelperNotes(context)
        sqlDB = db.writableDatabase
    }


    inner class DatabaseHelperNotes(context: Context) : SQLiteOpenHelper(context, dbName, null, dbVersion) {
        var context: Context? = context
        override fun onCreate(p0: SQLiteDatabase?) {
//            context!!.deleteDatabase("MyNotes.db")
//            p0!!.execSQL(t)
            p0!!.execSQL(sqlCreateTable)
//            context!!.deleteDatabase(dbName)

            Toast.makeText(this.context, " database is created", Toast.LENGTH_LONG).show()

        }

        override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
//            context!!.deleteDatabase("MyNotes.db")

            p0!!.execSQL("Drop table IF EXISTS $dbTable")
//            context!!.deleteDatabase(dbName)
        }

    }


    fun insert(values: ContentValues): Long {

        return sqlDB!!.insert(dbTable, "", values)
    }

    fun query(projection: Array<String>, selection: String, selectionArgs: Array<String>, sorOrder: String): Cursor {

        val qb = SQLiteQueryBuilder()
        qb.tables = dbTable
        return qb.query(sqlDB, projection, selection, selectionArgs, null, null, sorOrder)
    }

    fun delete(selection: String, selectionArgs: Array<String>): Int {

        return sqlDB!!.delete(dbTable, selection, selectionArgs)
    }

    fun update(values: ContentValues, selection: String, selectionargs: Array<String>): Int {

        return sqlDB!!.update(dbTable, values, selection, selectionargs)
    }
}

