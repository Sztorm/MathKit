package com.sztorm.mathkit

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ExtensionsTests {

    @ParameterizedTest
    @MethodSource("floatIsApproximatelyArgs")
    fun isApproximatelyReturnsCorrectValue(
        value: Float, other: Float, epsilon: Float, expected: Boolean
    ) = assertEquals(expected, value.isApproximately(other, epsilon))

    @ParameterizedTest
    @MethodSource("doubleIsApproximatelyArgs")
    fun isApproximatelyReturnsCorrectValue(
        value: Double, other: Double, epsilon: Double, expected: Boolean
    ) = assertEquals(expected, value.isApproximately(other, epsilon))

    @Suppress("SpellCheckingInspection")
    @ParameterizedTest
    @MethodSource("floatLerpArgs")
    fun lerpReturnsCorrectValue(a: Float, b: Float, t: Float, expected: Float) =
        assertTrue(expected.isApproximately(Float.lerp(a, b, t)))

    @Suppress("SpellCheckingInspection")
    @ParameterizedTest
    @MethodSource("doubleLerpArgs")
    fun lerpReturnsCorrectValue(a: Double, b: Double, t: Double, expected: Double) =
        assertTrue(expected.isApproximately(Double.lerp(a, b, t)))

    @Suppress("SpellCheckingInspection")
    @ParameterizedTest
    @MethodSource("floatInverseLerpArgs")
    fun inverseLerpReturnsCorrectValue(a: Float, b: Float, t: Float, expected: Float) =
        assertTrue(expected.isApproximately(Float.inverseLerp(a, b, t)))

    @Suppress("SpellCheckingInspection")
    @ParameterizedTest
    @MethodSource("doubleInverseLerpArgs")
    fun inverseLerpReturnsCorrectValue(a: Double, b: Double, t: Double, expected: Double) =
        assertTrue(expected.isApproximately(Double.inverseLerp(a, b, t)))

    companion object {
        @JvmStatic
        fun floatIsApproximatelyArgs(): List<Arguments> = listOf(
            Arguments.of(3f, 3f, 0f, true),
            Arguments.of(3f, 3.001f, 0.01f, true),
            Arguments.of(3f, 2.999f, 0.01f, true),
            Arguments.of(3f, 3.001f, 0.0001f, false),
            Arguments.of(3f, 2.999f, 0.0001f, false),
        )

        @JvmStatic
        fun doubleIsApproximatelyArgs(): List<Arguments> = listOf(
            Arguments.of(3.0, 3.0, 0.0, true),
            Arguments.of(3.0, 3.0000000001, 0.000000001, true),
            Arguments.of(3.0, 2.9999999999, 0.000000001, true),
            Arguments.of(3.0, 3.0000000001, 0.00000000001, false),
            Arguments.of(3.0, 2.9999999999, 0.00000000001, false),
        )

        @Suppress("SpellCheckingInspection")
        @JvmStatic
        fun floatLerpArgs(): List<Arguments> = listOf(
            Arguments.of(2f, 4f, 0.5f, 3f),
            Arguments.of(2f, -4f, 0.8f, -2.8f),
            Arguments.of(-2.5f, -0.5f, 0.1f, -2.3f),
        )

        @Suppress("SpellCheckingInspection")
        @JvmStatic
        fun doubleLerpArgs(): List<Arguments> = listOf(
            Arguments.of(2.0, 4.0, 0.5, 3.0),
            Arguments.of(2.0, -4.0, 0.8, -2.8),
            Arguments.of(-2.5, -0.5, 0.1, -2.3),
        )

        @Suppress("SpellCheckingInspection")
        @JvmStatic
        fun floatInverseLerpArgs(): List<Arguments> = listOf(
            Arguments.of(2f, 4f, 3f, 0.5f),
            Arguments.of(2f, -4f, -2.8f, 0.8f),
            Arguments.of(-2.5f, -0.5f, -2.3f, 0.1f),
        )

        @Suppress("SpellCheckingInspection")
        @JvmStatic
        fun doubleInverseLerpArgs(): List<Arguments> = listOf(
            Arguments.of(2.0, 4.0, 3.0, 0.5),
            Arguments.of(2.0, -4.0, -2.8, 0.8),
            Arguments.of(-2.5, -0.5, -2.3, 0.1),
        )
    }
}