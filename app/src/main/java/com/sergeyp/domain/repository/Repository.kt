package com.sergeyp.domain.repository

import com.sergeyp.domain.model.Activity
import io.reactivex.rxjava3.core.Single
import java.util.*

interface Repository {

    fun getActivities(from: Date, to: Date): Single<List<Activity>>

}