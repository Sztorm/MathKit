package com.sztorm.nonallocmath

import kotlin.math.absoluteValue
import kotlin.math.max

inline fun Float.isApproximately(other: Float, epsilon: Float = 0.00001f) =
    (this - other).absoluteValue <= epsilon * max(this.absoluteValue, other.absoluteValue)

inline fun Float.Companion.lerp(a: Float, b: Float, t: Float) = a + (b - a) * t

inline fun Float.Companion.inverseLerp(a: Float, b: Float, t: Float) = (t - a) / (b - a)