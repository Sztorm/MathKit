package com.sztorm.nonallocmath

import kotlin.math.absoluteValue
import kotlin.math.max

inline fun Float.isApproximately(other: Float, epsilon: Float = 0.0001f) =
    (this - other).absoluteValue <= epsilon * max(this.absoluteValue, other.absoluteValue)