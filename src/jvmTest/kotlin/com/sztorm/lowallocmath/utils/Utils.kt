package com.sztorm.lowallocmath.utils

import com.sztorm.lowallocmath.AngleF
import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F
import com.sztorm.lowallocmath.isApproximately
import kotlin.test.assertTrue

fun assertApproximation(expected: Float, actual: Float, tolerance: Float = 0.00001f) = assertTrue(
    expected.isApproximately(actual, tolerance),
    "Expected <$expected> with absolute tolerance <$tolerance>, actual <$actual>."
)

fun assertApproximation(
    expected: Vector2F, actual: Vector2F, tolerance: Float = 0.00001f
) = assertTrue(
    expected.isApproximately(actual, tolerance),
    "Expected <$expected> with absolute tolerance <$tolerance>, actual <$actual>."
)

fun assertApproximation(
    expected: ComplexF, actual: ComplexF, tolerance: Float = 0.00001f
) = assertTrue(
    expected.isApproximately(actual, tolerance),
    "Expected <$expected> with absolute tolerance <$tolerance>, actual <$actual>."
)

fun assertApproximation(
    expected: AngleF, actual: AngleF, tolerance: AngleF = AngleF(0.00001f)
) = assertTrue(
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