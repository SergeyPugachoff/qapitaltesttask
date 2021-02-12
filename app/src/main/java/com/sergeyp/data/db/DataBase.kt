package com.sergeyp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sergeyp.data.db.converters.DateConverter
import com.sergeyp.data.db.dao.ActivityDao
import com.sergeyp.data.db.dao.UserDao
import com.sergeyp.data.db.entity.ActivityEntity
import com.sergeyp.data.db.entity.UserEntity

@Database(entities = [ActivityEntity::class, UserEntity::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class DataBase : RoomDatabase() {

    abstract fun activityDao(): ActivityDao

    abstract fun userDao(): UserDao

}