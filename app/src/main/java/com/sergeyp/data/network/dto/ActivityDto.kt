package com.sergeyp.data.network.dto

import java.util.*

data class ActivityDto(
    val userId: Int,
    val message: String?,
    val amount: Float?,
    val timestamp: Date?
)