package com.sergeyp.domain.usecase

import com.sergeyp.domain.model.Activity
import com.sergeyp.domain.repository.Repository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*

class GetActivitiesUseCase(
    private val repository: Repository,
    private val subscribeOnScheduler: Scheduler = Schedulers.io(),
    private val observeOnScheduler: Scheduler = AndroidSchedulers.mainThread()
) {

    operator fun invoke(from: Date, to: Date): Single<List<Activity>> {
        return repository.getActivities(from, to)
            .subscribeOn(subscribeOnScheduler)
            .observeOn(observeOnScheduler)
    }
}