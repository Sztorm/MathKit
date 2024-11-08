package com.sztorm.lowallocmath.utils

import com.sztorm.lowallocmath.AngleF
import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F
import com.sztorm.lowallocmath.isApproximately
import kotlin.math.sign
import kotlin.test.assertTrue

private fun Float.hasSymbolIdenticalTo(other: Float) =
    (isNaN() and other.isNaN()) || (isInfinite() && other.isInfinite() && sign == other.sign)

private fun messagePrefix(message: String?) = if (message == null) "" else "$message. "

/**
 * Asserts that the [expected] value is approximately equal to the [actual] value within an
 * [absoluteTolerance], with an optional [message].
 */
fun assertApproximation(
    expected: Float,
    actual: Float,
    absoluteTolerance: Float = 0.00001f,
    message: String? = null
) {
    val mainMessage =
        "Expected <$expected> with absolute tolerance <$absoluteTolerance>, actual <$actual>."

    assertTrue(
        expected.hasSymbolIdenticalTo(actual) ||
                expected.isApproximately(actual, absoluteTolerance),
        messagePrefix(message) + mainMessage
    )
}

/**
 * Asserts that the [expected] value is approximately equal to the [actual] value within an
 * [absoluteTolerance], with an optional [message].
 */
fun assertApproximation(
    expected: Vector2F,
    actual: Vector2F,
    absoluteTolerance: Float = 0.00001f,
    message: String? = null
) {
    val (eX: Float, eY: Float) = expected
    val (aX: Float, aY: Float) = actual
    val mainMessage =
        "Expected <$expected> with absolute tolerance <$absoluteTolerance>, actual <$actual>."

    assertTrue(
        (eX.hasSymbolIdenticalTo(aX) || eX.isApproximately(aX, absoluteTolerance)) &&
                (eY.hasSymbolIdenticalTo(aY) || eY.isApproximately(aY, absoluteTolerance)),
        messagePrefix(message) + mainMessage
    )
}

/**
 * Asserts that the [expected] value is approximately equal to the [actual] value within an
 * [absoluteTolerance], with an optional [message].
 */
fun assertApproximation(
    expected: ComplexF,
    actual: ComplexF,
    absoluteTolerance: Float = 0.00001f,
    message: String? = null
) {
    val (eR: Float, eI: Float) = expected
    val (aR: Float, aI: Float) = actual
    val mainMessage =
        "Expected <$expected> with absolute tolerance <$absoluteTolerance>, actual <$actual>."

    assertTrue(
        (eR.hasSymbolIdenticalTo(aR) || eR.isApproximately(aR, absoluteTolerance)) &&
                (eI.hasSymbolIdenticalTo(aI) || eI.isApproximately(aI, absoluteTolerance)),
        messagePrefix(message) + mainMessage
    )
}

/**
 * Asserts that the [expected] value is approximately equal to the [actual] value within an
 * [absoluteTolerance], with an optional [message].
 */
fun assertApproximation(
    expected: AngleF,
    actual: AngleF,
    absoluteTolerance: AngleF = AngleF(0.00001f),
    message: String? = null
) {
    val mainMessage =
        "Expected <$expected> with absolute tolerance <$absoluteTolerance>, actual <$actual>."

    assertTrue(
        expected.radians.hasSymbolIdenticalTo(actual.radians) ||
                expected.isApproximately(actual, absoluteTolerance),
        messagePrefix(message) + mainMessage
    )
}