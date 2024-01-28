package com.sztorm.lowallocmath.utils

import com.sztorm.lowallocmath.AngleF
import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F
import com.sztorm.lowallocmath.isApproximately
import kotlin.math.sign
import kotlin.test.assertTrue

private fun Float.hasSymbolIdenticalTo(other: Float) =
    (isNaN() and other.isNaN()) || (isInfinite() && other.isInfinite() && sign == other.sign)

fun assertApproximation(expected: Float, actual: Float, tolerance: Float = 0.00001f) = assertTrue(
    expected.hasSymbolIdenticalTo(actual) || expected.isApproximately(actual, tolerance),
    "Expected <$expected> with absolute tolerance <$tolerance>, actual <$actual>."
)

fun assertApproximation(
    expected: Vector2F, actual: Vector2F, tolerance: Float = 0.00001f
) {
    val (eX: Float, eY: Float) = expected
    val (aX: Float, aY: Float) = actual

    assertTrue(
        (eX.hasSymbolIdenticalTo(aX) || eX.isApproximately(aX, tolerance)) &&
                (eY.hasSymbolIdenticalTo(aY) || eY.isApproximately(aY, tolerance)),
        "Expected <$expected> with absolute tolerance <$tolerance>, actual <$actual>."
    )
}

fun assertApproximation(
    expected: ComplexF, actual: ComplexF, tolerance: Float = 0.00001f
) {
    val (eR: Float, eI: Float) = expected
    val (aR: Float, aI: Float) = actual

    assertTrue(
        (eR.hasSymbolIdenticalTo(aR) || eR.isApproximately(aR, tolerance)) &&
                (eI.hasSymbolIdenticalTo(aI) || eI.isApproximately(aI, tolerance)),
        "Expected <$expected> with absolute tolerance <$tolerance>, actual <$actual>."
    )
}

fun assertApproximation(
    expected: AngleF, actual: AngleF, tolerance: AngleF = AngleF(0.00001f)
) = assertTrue(
    expected.radians.hasSymbolIdenticalTo(actual.radians) ||
            expected.isApproximately(actual, tolerance),
    "Expected <$expected> with absolute tolerance <$tolerance>, actual <$actual>."
)

inline fun <reified T> assertEquals(
    expected: T, actual: T, equalityComparator: (T, T) -> Boolean, message: String? = null
) {
    val prefix: String = if (message == null) "" else "$message. "

    assertTrue(
        equalityComparator(expected, actual),
        prefix + "Expected <$expected>, actual <$actual>."
    )
}