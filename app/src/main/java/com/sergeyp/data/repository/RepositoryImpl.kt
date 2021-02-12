package com.sergeyp.data.repository

import com.sergeyp.data.db.dao.ActivityDao
import com.sergeyp.data.db.dao.UserDao
import com.sergeyp.data.db.entity.ActivityAndUser
import com.sergeyp.data.mapper.toActivity
import com.sergeyp.data.mapper.toActivityEntity
import com.sergeyp.data.mapper.toUser
import com.sergeyp.data.mapper.toUserEntity
import com.sergeyp.data.network.api.ApiService
import com.sergeyp.data.network.dto.ActivityDto
import com.sergeyp.data.network.dto.UserDto
import com.sergeyp.domain.model.Activity
import com.sergeyp.domain.model.User
import com.sergeyp.domain.repository.Repository
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*
import java.util.concurrent.Executors

class RepositoryImpl(
    private val api: ApiService,
    private val activityDao: ActivityDao,
    private val userDao: UserDao
) : Repository {

    private var oldest: Date? = null
    private val cachedUsers = mutableSetOf<User>()

    override fun getActivities(from: Date, to: Date): Single<List<Activity>> {
        return if (oldest == null || from.after(oldest)) {
            api.getActivities(from, to)
                .flatMap { response ->
                    oldest = response.oldest
                    val activities = response.activities

                    activityDao.replace(from, to, activities.map(ActivityDto::toActivityEntity))

                    val activityUserIds = activities.map { activity -> activity.userId }.distinct()
                    val cachedUserIds = cachedUsers.map { it.userId }
                    val userIdsToLoad = activityUserIds.filterNot { cachedUserIds.contains(it) }

                    fetchUsers(userIdsToLoad).map { users ->
                        cachedUsers.addAll(users.map(UserDto::toUser))
                        userDao.insertAll(users.map(UserDto::toUserEntity))
                    }
                }.flatMap {
                    getActivitiesFromDb(from, to)
                }
        } else {
            getActivitiesFromDb(from, to)
        }.map {
            it.sortedByDescending { activity -> activity.timestamp }
        }
    }

    private fun fetchUsers(ids: List<Int>): Single<List<UserDto>> {
        return Observable.fromIterable(ids)
            .flatMap { userId -> api.getUser(userId).subscribeOn(fixedPoolScheduler) }
            .toList()
    }

    private fun getActivitiesFromDb(from: Date, to: Date): Single<List<Activity>> {
        return activityDao.getActivitiesAndUsers(from, to)
            .map {
                it.map(ActivityAndUser::toActivity)
            }
    }

    companion object {
        private val fixedPoolScheduler = Schedulers.from(Executors.newFixedThreadPool(5))
    }
}