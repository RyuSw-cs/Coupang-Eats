package com.softsquared.template.kotlin.util

import android.app.Activity
import android.content.Context

fun Activity.changeStatusBar(check: Boolean) {
    if (check) {
        window.apply {
            setFlags(
                android.view.WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
                android.view.WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
            )
        }
        if (android.os.Build.VERSION.SDK_INT >= 30) {
            androidx.core.view.WindowCompat.setDecorFitsSystemWindows(window, true)
        }
    } else {
        window.apply {
            setFlags(
                android.view.WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
                android.view.WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
            )
        }
        if (android.os.Build.VERSION.SDK_INT >= 30) {
            androidx.core.view.WindowCompat.setDecorFitsSystemWindows(window, true)
        }
    }
}

fun Context.setStatusBar(): Int {
    val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
    return if (resourceId > 0) resources.getDimensionPixelOffset(resourceId)
    else 0
}
