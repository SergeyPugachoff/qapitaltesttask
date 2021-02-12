package com.sergeyp.data.db.dao

import androidx.room.*
import com.sergeyp.data.db.entity.ActivityAndUser
import com.sergeyp.data.db.entity.ActivityEntity
import io.reactivex.rxjava3.core.Single
import java.util.*

@Dao
interface ActivityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(activities: List<ActivityEntity>)

    @Transaction
    @Query("SELECT * FROM activities WHERE timestamp BETWEEN :from AND :to ")
    fun getActivitiesAndUsers(from: Date, to: Date): Single<List<ActivityAndUser>>

    @Query("DELETE FROM activities WHERE timestamp BETWEEN :from AND :to ")
    fun deleteActivities(from: Date, to: Date)

    @Transaction
    fun replace(from: Date, to: Date, activities: List<ActivityEntity>) {
        deleteActivities(from, to)
        insertAll(activities)
    }
}