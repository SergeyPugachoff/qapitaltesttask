package com.sergeyp.presentation.ui.activities.view

import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sergeyp.data.db.DbFactory
import com.sergeyp.data.network.api.ApiFactory
import com.sergeyp.data.network.api.ApiService
import com.sergeyp.data.repository.RepositoryImpl
import com.sergeyp.databinding.ActivityActivitiesBinding
import com.sergeyp.domain.model.Activity
import com.sergeyp.domain.usecase.GetActivitiesUseCase
import com.sergeyp.presentation.ui.activities.presenter.ActivitiesPresenter
import com.sergeyp.presentation.ui.activities.view.adapter.ActivitiesAdapter
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class ActivitiesActivity : MvpAppCompatActivity(), ActivitiesView {

    private lateinit var binding: ActivityActivitiesBinding

    @InjectPresenter
    lateinit var presenter: ActivitiesPresenter

    @ProvidePresenter
    fun providePresenter(): ActivitiesPresenter {
        val db = DbFactory.getInstance(this)
        return ActivitiesPresenter(
            getActivitiesUseCase = GetActivitiesUseCase(
                repository = RepositoryImpl(
                    api = ApiFactory.provideApiService(ApiService::class.java),
                    activityDao = db.activityDao(),
                    userDao = db.userDao()
                )
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityActivitiesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUi()
    }

    override fun showError(throwable: Throwable) {
        Toast.makeText(this, throwable.message ?: "error", Toast.LENGTH_SHORT).show()
    }

    override fun showLoading(show: Boolean) {
        binding.loading.isInvisible = show.not()
    }

    override fun submitList(activities: List<Activity>) {
        (binding.activityList.adapter as ActivitiesAdapter).submitList(activities)
    }

    private fun initUi() = with(binding) {
        activityList.adapter = ActivitiesAdapter()
        activityList.layoutManager = LinearLayoutManager(this@ActivitiesActivity)
        activityList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)) {
                    presenter.loadActivities()
                }
            }
        })
    }
}