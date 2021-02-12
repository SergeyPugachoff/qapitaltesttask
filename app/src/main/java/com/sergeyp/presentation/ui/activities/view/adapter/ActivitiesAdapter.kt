package com.sergeyp.presentation.ui.activities.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sergeyp.R
import com.sergeyp.databinding.ListItemActivityBinding
import com.sergeyp.domain.model.Activity
import com.sergeyp.presentation.tools.format
import com.sergeyp.presentation.tools.isToday
import com.sergeyp.presentation.tools.isYesterday
import com.sergeyp.presentation.tools.setHtmlText
import java.util.*

class ActivitiesAdapter :
    ListAdapter<Activity, ActivitiesAdapter.ActivityViewHolder>(ActivityDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityViewHolder {
        val binding = ListItemActivityBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ActivityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ActivityViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun submitList(list: List<Activity>?) {
        super.submitList(list?.toList())
    }

    private fun getDateLabel(context: Context, date: Date?): String {
        return when {
            date == null -> ""
            date.isToday() -> context.getString(R.string.date_today_label)
            date.isYesterday() -> context.getString(R.string.date_yesterday_label)
            else -> date.format(context.getString(R.string.activities_date_format))
        }
    }

    inner class ActivityViewHolder(
        private val binding: ListItemActivityBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(activity: Activity) = with(binding) {
            message.setHtmlText(activity.message)
            date.text = getDateLabel(root.context, activity.timestamp)
            amount.text =
                root.context.getString(R.string.activity_amount, activity.amount.toString())

            Glide.with(root.context)
                .load(activity.user?.avatarUrl)
                .circleCrop()
                .into(userAvatar)
        }
    }

    class ActivityDiffCallback : DiffUtil.ItemCallback<Activity>() {
        override fun areItemsTheSame(oldItem: Activity, newItem: Activity): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Activity, newItem: Activity): Boolean {
            return oldItem == newItem
        }
    }
}