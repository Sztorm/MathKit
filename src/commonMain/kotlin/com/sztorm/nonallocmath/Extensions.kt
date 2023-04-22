@file:Suppress("unused")

package com.sztorm.nonallocmath

import kotlin.math.absoluteValue
import kotlin.math.max

/**
 * Returns a value indicating whether this value is approximately the [other] value given the
 * specified [epsilon] tolerance.
 */
inline fun Float.isApproximately(other: Float, epsilon: Float = 0.00001f) =
    (this - other).absoluteValue <= epsilon * max(this.absoluteValue, other.absoluteValue)

/**
 * Returns a value indicating whether this value is approximately the [other] value given the
 * specified [epsilon] tolerance.
 */
inline fun Double.isApproximately(other: Double, epsilon: Double = 0.0000000001) =
    (this - other).absoluteValue <= epsilon * max(this.absoluteValue, other.absoluteValue)

/**
 * Returns a linearly interpolated value between [a] and [b].
 *
 * @param [a] Source value.
 * @param [b] Destination value.
 * @param [t] Interpolator value with an expected range of 0..1.
 */
@Suppress("SpellCheckingInspection")
inline fun Float.Companion.lerp(a: Float, b: Float, t: Float) = a + (b - a) * t

/**
 * Returns a linearly interpolated value between [a] and [b].
 *
 * @param [a] Source value.
 * @param [b] Destination value.
 * @param [t] Interpolator value with an expected range of 0..1.
 */
@Suppress("SpellCheckingInspection")
inline fun Double.Companion.lerp(a: Double, b: Double, t: Double) = a + (b - a) * t

/**
 * Returns an interpolator value of the linearly interpolated value [t] between [a] and
 * [b].
 *
 * @param [a] Source value.
 * @param [b] Destination value.
 * @param [t] Interpolated value.
 */
@Suppress("SpellCheckingInspection")
inline fun Float.Companion.inverseLerp(a: Float, b: Float, t: Float) = (t - a) / (b - a)

/**
 * Returns an interpolator value of the linearly interpolated value [t] between [a] and
 * [b].
 *
 * @param [a] Source value.
 * @param [b] Destination value.
 * @param [t] Interpolated value.
 */
@Suppress("SpellCheckingInspection")
inline fun Double.Companion.inverseLerp(a: Double, b: Double, t: Double) = (t - a) / (b - a)