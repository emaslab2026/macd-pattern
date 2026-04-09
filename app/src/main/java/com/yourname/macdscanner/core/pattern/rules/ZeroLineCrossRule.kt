package com.yourname.macdscanner.core.pattern.rules

import com.yourname.macdscanner.core.model.MacdPoint

class ZeroLineCrossRule {
    fun matches(previous: MacdPoint, current: MacdPoint): Boolean {
        return (previous.macd <= 0.0 && current.macd > 0.0) ||
            (previous.macd >= 0.0 && current.macd < 0.0)
    }
}
