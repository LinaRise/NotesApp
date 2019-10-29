package com.nikak.linadom.notesapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHandler(context: Context) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSIOM) {


    var db: SQLiteDatabase? = null

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE = "CREATE TABLE $TABLE_USERS " +
                "($ID Integer PRIMARY KEY, $LOGIN TEXT UNIQUE, $PASSWORD TEXT)"

        db?.execSQL(CREATE_TABLE)
        val insertValues = ContentValues()
        insertValues.put(LOGIN, "login")
        insertValues.put(PASSWORD, "password")
        val success = db?.insert(TABLE_USERS, null, insertValues)
//        db?.close()
        Log.v("InsertedID", "$success")

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        onCreate(db)
    }

    fun addUser(user: User): Boolean {
        val db = this.writableDatabase
        var success = 0L
        if(checkIfLoginPresent(user.login)) {
            val values = ContentValues()
            values.put(LOGIN, user.login)
            values.put(PASSWORD, user.password)
            success = db.insert(TABLE_USERS, null, values)
            db.close()
            Log.v("InsertedID", "$success")
        }
        return (Integer.parseInt("$success") != -1)
    }

    fun getAllUsers(): String {
        var allUser = ""
        val db = readableDatabase
        val selectALLQuery = "SELECT * FROM $TABLE_USERS"
        val cursor = db.rawQuery(selectALLQuery, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    val id = cursor.getString(cursor.getColumnIndex(ID))
                    val firstName = cursor.getString(cursor.getColumnIndex(LOGIN))
                    val lastName = cursor.getString(cursor.getColumnIndex(PASSWORD))

                    allUser = "$allUser\n$id $firstName $lastName"
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        db.close()
        return allUser
    }

//    fun checkIfUserPresents(user: User):Boolean{
//        val cursor = db?.query(
//            TABLE_NAME,
//            arrayOf(LOGIN, PASSWORD),
//            "$LOGIN = ? AND $PASSWORD = ?",
//            arrayOf(user.login,user.password), null, null, null
//        )
//        cursor?.close()
//        return cursor?.count != 0
//    }

    //проверка есть ли такой пользователь в бд
     fun checkIfUserPresent(user: User):Boolean {
        val login = user.login
        val password = user.password

        val database = readableDatabase
        val projection = arrayOf(
            ID,
            LOGIN,
            PASSWORD
        )
        val selection = LOGIN + " like ? and " +
                PASSWORD + " like ?"
        val selectionArgs = arrayOf(login, password)
        val cursor = database.query(
            TABLE_USERS,
            projection,
            selection,
            selectionArgs,
            null
            , null, null
        )

        val number =  cursor.count
        database.close()
        cursor.close()
        println(number)
        return number!=0
    }


    private fun checkIfLoginPresent(login: String):Boolean {
        val database = readableDatabase
        val projection = arrayOf(
            ID,
            LOGIN,
            PASSWORD
        )
        val cursor = database.query(
            TABLE_USERS,
            projection,
            "$LOGIN = ?",
            arrayOf(login), null, null, null
        )

        val number =  cursor?.count
        cursor?.close()
        println(number)
        return number!=0
    }


    companion object {
        private val DB_NAME = "UsersDatabase"
        private val DB_VERSIOM = 1
        private val TABLE_USERS = "users"
        private val ID = "id"
        private val LOGIN = "login"
        private val PASSWORD = "password"

    }
}