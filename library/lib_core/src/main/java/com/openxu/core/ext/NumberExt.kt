package com.openxu.core.ext

import com.openxu.core.utils.density
import com.openxu.core.utils.scaledDensity


/**
 * dp、sp、px相互换算
 */
fun Number?.dpToPx() = (this?.toFloat() ?: 0f) * density
fun Number?.spToPx() = (this?.toFloat() ?: 0f) * scaledDensity
fun Number?.pxToDp() = (this?.toFloat() ?: 0f) / density
fun Number?.pxToSp() = (this?.toFloat() ?: 0f) / scaledDensity