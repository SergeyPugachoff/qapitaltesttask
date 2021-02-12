package com.sergeyp.data.db.entity

import androidx.room.Embedded
import androidx.room.Relation

data class ActivityAndUser(
    @Embedded val activityEntity: ActivityEntity,
    @Relation(
        parentColumn = "userId",
        entityColumn = "id"
    )
    val userEntity: UserEntity?
)