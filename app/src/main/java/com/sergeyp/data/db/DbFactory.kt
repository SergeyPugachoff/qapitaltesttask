package com.sergeyp.data.db

import android.content.Context
import androidx.room.Room

object DbFactory {
    private lateinit var dataBase: DataBase

    fun getInstance(context: Context): DataBase {
        if (!::dataBase.isInitialized) {
            dataBase = Room.databaseBuilder(context, DataBase::class.java, "quapital_db").build()
        }
        return dataBase
    }
}