package com.sergeyp.domain.model

import java.util.*

data class Activity(
    val user: User?,
    val message: String,
    val amount: Float,
    val timestamp: Date?
)