package com.sergeyp.presentation.ui.activities.presenter

import com.sergeyp.domain.model.Activity
import com.sergeyp.domain.usecase.GetActivitiesUseCase
import com.sergeyp.presentation.ui.activities.view.ActivitiesView
import io.reactivex.rxjava3.disposables.CompositeDisposable
import moxy.InjectViewState
import moxy.MvpPresenter
import java.util.*

@InjectViewState
class ActivitiesPresenter(
    private val getActivitiesUseCase: GetActivitiesUseCase
) : MvpPresenter<ActivitiesView>() {

    private val activities = mutableListOf<Activity>()
    private val disposables = CompositeDisposable()

    private var to = Date()
    private var from = Date()

    override fun onFirstViewAttach() {
        refresh()
    }

    override fun onDestroy() {
        disposables.clear()
    }

    fun loadActivities() {
        viewState.showLoading(true)
        to = from
        from = Date(from.time - REQUEST_PERIOD)
        disposables.add(
            getActivitiesUseCase(from = from, to = to)
                .subscribe(
                    { items ->
                        activities.addAll(items)
                        viewState.showLoading(false)
                        viewState.showRefreshing(false)
                        viewState.submitList(activities)
                    }, { error ->
                        viewState.showLoading(false)
                        viewState.showRefreshing(false)
                        viewState.showError(error)
                    }
                )
        )
    }

    fun refresh() {
        viewState.showRefreshing(true)
        to = Date()
        from = Date()
        activities.clear()
        loadActivities()
    }

    companion object {
        private const val REQUEST_PERIOD = 14 * 24 * 60 * 60 * 1000
    }

}