package com.sergeyp.data.network.dto

import java.util.*

data class ActivitiesApiResponse(
    val oldest: Date,
    val activities: List<ActivityDto>
)