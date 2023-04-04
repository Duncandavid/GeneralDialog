package com.primary.generaldialog

import android.content.Context

/**
 * Created by David at 2023/4/4
 */
object Utils {
    fun dipToPx(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }
}