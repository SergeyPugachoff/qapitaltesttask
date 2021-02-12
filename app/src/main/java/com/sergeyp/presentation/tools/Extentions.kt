package com.sergeyp.presentation.tools

import android.os.Build
import android.text.Html
import android.text.format.DateUtils
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

fun TextView.setHtmlText(formattedText: String) {
    text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(formattedText, Html.FROM_HTML_MODE_LEGACY)
    } else {
        Html.fromHtml(formattedText)
    }
}

fun Date.format(pattern: String): String {
    val simpleDateFormat = SimpleDateFormat(pattern, Locale.getDefault())
    return simpleDateFormat.format(this)
}

fun Date.isToday(): Boolean {
    return DateUtils.isToday(this.time)
}

fun Date.isYesterday(): Boolean {
    val now = Calendar.getInstance()
    val thisDate = Calendar.getInstance()
    thisDate.timeInMillis = this.time
    now.add(Calendar.DATE, -1)
    return now[Calendar.YEAR] == thisDate[Calendar.YEAR] && now[Calendar.MONTH] == thisDate[Calendar.MONTH] && now[Calendar.DATE] == thisDate[Calendar.DATE]
}