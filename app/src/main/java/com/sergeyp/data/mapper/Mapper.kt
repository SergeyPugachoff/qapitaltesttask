package com.sergeyp.data.mapper

import com.sergeyp.data.db.entity.ActivityAndUser
import com.sergeyp.data.db.entity.ActivityEntity
import com.sergeyp.data.db.entity.UserEntity
import com.sergeyp.data.network.dto.ActivityDto
import com.sergeyp.data.network.dto.UserDto
import com.sergeyp.domain.model.Activity
import com.sergeyp.domain.model.User

fun ActivityDto.toActivity(user: User?): Activity {
    return Activity(
        user = user,
        message = this.message ?: "",
        amount = this.amount ?: 0F,
        timestamp = this.timestamp
    )
}

fun UserDto.toUser(): User {
    return User(
        userId = this.userId,
        displayName = this.displayName ?: "",
        avatarUrl = this.avatarUrl ?: ""
    )
}

fun UserDto.toUserEntity(): UserEntity {
    return UserEntity(
        id = this.userId,
        displayName = this.displayName,
        avatarUrl = this.avatarUrl
    )
}

fun ActivityDto.toActivityEntity(): ActivityEntity {
    return ActivityEntity(
        userId = this.userId,
        message = this.message,
        amount = this.amount,
        timestamp = this.timestamp
    )
}

fun UserEntity.toUser(): User {
    return User(
        userId = id,
        displayName = displayName ?: "",
        avatarUrl = avatarUrl ?: ""
    )
}

fun ActivityAndUser.toActivity(): Activity {
    return Activity(
        user = userEntity?.toUser(),
        message = activityEntity.message ?: "",
        amount = activityEntity.amount ?: 0F,
        timestamp = activityEntity.timestamp
    )
}