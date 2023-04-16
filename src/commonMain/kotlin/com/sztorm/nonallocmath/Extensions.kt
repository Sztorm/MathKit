@file:Suppress("unused")

package com.sztorm.nonallocmath

import kotlin.math.absoluteValue
import kotlin.math.max

inline fun Float.isApproximately(other: Float, epsilon: Float = 0.00001f) =
    (this - other).absoluteValue <= epsilon * max(this.absoluteValue, other.absoluteValue)

inline fun Double.isApproximately(other: Double, epsilon: Double = 0.0000000001) =
    (this - other).absoluteValue <= epsilon * max(this.absoluteValue, other.absoluteValue)

@Suppress("SpellCheckingInspection")
inline fun Float.Companion.lerp(a: Float, b: Float, t: Float) = a + (b - a) * t

@Suppress("SpellCheckingInspection")
inline fun Double.Companion.lerp(a: Double, b: Double, t: Double) = a + (b - a) * t

@Suppress("SpellCheckingInspection")
inline fun Float.Companion.inverseLerp(a: Float, b: Float, t: Float) = (t - a) / (b - a)

@Suppress("SpellCheckingInspection")
inline fun Double.Companion.inverseLerp(a: Double, b: Double, t: Double) = (t - a) / (b - a)