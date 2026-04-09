package com.yourname.macdscanner.core.pattern.rules

import com.yourname.macdscanner.core.model.MacdPoint

class BearishCrossoverRule {
    fun matches(previous: MacdPoint, current: MacdPoint): Boolean {
        return previous.macd >= previous.signal && current.macd < current.signal
    }
}
