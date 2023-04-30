package com.example.userlogin

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "myapp.db"
        private const val DATABASE_VERSION = 1

        private const val TABLE_USERS = "users"
        private const val COLUMN_USER_ID = "_id"
        private const val COLUMN_USER_NAME = "name"
        private const val COLUMN_USER_EMAIL = "email"
        private const val COLUMN_USER_PHONE = "phone"
        private const val COLUMN_USER_PASSWORD = "password"

        private const val CREATE_TABLE_USERS = """
            CREATE TABLE $TABLE_USERS (
                $COLUMN_USER_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_USER_NAME TEXT NOT NULL,
                $COLUMN_USER_EMAIL TEXT NOT NULL UNIQUE,
                $COLUMN_USER_PHONE TEXT NOT NULL,
                $COLUMN_USER_PASSWORD TEXT NOT NULL
            )
        """
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE_USERS)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        onCreate(db)
    }

    fun addUser(name: String, email: String, phone: String, password: String): Long {
        val values = ContentValues().apply {
            put(COLUMN_USER_NAME, name)
            put(COLUMN_USER_EMAIL, email)
            put(COLUMN_USER_PHONE, phone)
            put(COLUMN_USER_PASSWORD, password)
        }
        return writableDatabase.insert(TABLE_USERS, null, values)
    }

    @SuppressLint("Range")
    fun getUserByEmail(email: String): User? {
        val columns = arrayOf(
            COLUMN_USER_ID,
            COLUMN_USER_NAME,
            COLUMN_USER_EMAIL,
            COLUMN_USER_PHONE,
            COLUMN_USER_PASSWORD
        )
        val selection = "$COLUMN_USER_EMAIL = ?"
        val selectionArgs = arrayOf(email)
        val cursor =
            readableDatabase.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null)
        return if (cursor.moveToFirst()) {
            val id = cursor.getLong(cursor.getColumnIndex(COLUMN_USER_ID))
            val name = cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME))
            val phone = cursor.getString(cursor.getColumnIndex(COLUMN_USER_PHONE))
            val password = cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD))
            User(id, name, email, phone, password)
        } else {
            null
        }
    }

    @SuppressLint("Range")
    fun getAllUsers(): List<User> {
        val users = mutableListOf<User>()
        val columns = arrayOf(
            COLUMN_USER_ID,
            COLUMN_USER_NAME,
            COLUMN_USER_EMAIL,
            COLUMN_USER_PHONE,
            COLUMN_USER_PASSWORD
        )
        val cursor = readableDatabase.query(TABLE_USERS, columns, null, null, null, null, null)
        while (cursor.moveToNext()) {
            val id = cursor.getLong(cursor.getColumnIndex(COLUMN_USER_ID))
            val name = cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME))
            val email = cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL))
            val phone = cursor.getString(cursor.getColumnIndex(COLUMN_USER_PHONE))
            val password = cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD))
            users.add(User(id, name, email, phone, password))
        }
        cursor.close()
        return users
    }
}
