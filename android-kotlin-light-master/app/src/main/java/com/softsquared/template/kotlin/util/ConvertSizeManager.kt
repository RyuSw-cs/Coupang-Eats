package com.softsquared.template.kotlin.util

import android.content.Context
import android.util.TypedValue

object ConvertSizeManager {
    fun dpToPx(context: Context, dp: Float) =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.resources.displayMetrics)
            .toInt()
}