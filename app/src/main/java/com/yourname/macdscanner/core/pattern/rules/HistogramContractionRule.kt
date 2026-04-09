package com.yourname.macdscanner.core.pattern.rules

import com.yourname.macdscanner.core.model.MacdPoint
import kotlin.math.abs

class HistogramContractionRule {
    fun matches(previous: MacdPoint, current: MacdPoint): Boolean {
        return abs(current.histogram) < abs(previous.histogram)
    }
}
