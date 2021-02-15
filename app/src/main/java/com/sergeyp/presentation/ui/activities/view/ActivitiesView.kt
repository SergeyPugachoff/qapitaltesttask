package com.sergeyp.presentation.ui.activities.view

import com.sergeyp.domain.model.Activity
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle


interface ActivitiesView : MvpView {

    @AddToEndSingle
    fun showRefreshing(show: Boolean)

    @AddToEndSingle
    fun showLoading(show: Boolean)

    @AddToEndSingle
    fun submitList(activities: List<Activity>)

    @AddToEndSingle
    fun showError(throwable: Throwable)

}