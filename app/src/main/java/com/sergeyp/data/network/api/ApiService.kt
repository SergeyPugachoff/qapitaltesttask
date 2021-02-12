package com.sergeyp.data.network.api

import com.sergeyp.data.network.dto.ActivitiesApiResponse
import com.sergeyp.data.network.dto.UserDto
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.*

interface ApiService {

    @GET("activities")
    fun getActivities(
        @Query("from") from: Date,
        @Query("to") to: Date
    ): Single<ActivitiesApiResponse>

    @GET("users/{id}")
    fun getUser(@Path("id") userId: Int): Observable<UserDto>

}