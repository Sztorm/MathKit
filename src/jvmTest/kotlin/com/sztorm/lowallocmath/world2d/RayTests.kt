package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.Vector2F
import com.sztorm.lowallocmath.utils.Wrapper
import com.sztorm.lowallocmath.utils.assertApproximation
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

class RayTests {
    @ParameterizedTest
    @MethodSource("originArgs")
    fun originReturnsCorrectValue(ray: Ray, expected: Wrapper<Vector2F>) =
        assertApproximation(expected.value, ray.origin)

    @ParameterizedTest
    @MethodSource("directionArgs")
    fun directionReturnsCorrectValue(ray: Ray, expected: Wrapper<Vector2F>) =
        assertApproximation(expected.value, ray.direction)

    @ParameterizedTest
    @MethodSource("copyArgs")
    fun copyReturnsCorrectValue(
        ray: Ray,
        origin: Wrapper<Vector2F>,
        direction: Wrapper<Vector2F>,
        expected: Ray
    ) = assertEquals(expected, ray.copy(origin.value, direction.value))

    @ParameterizedTest
    @MethodSource("equalsArgs")
    fun equalsReturnsCorrectValue(ray: MutableRay, other: Any?, expected: Boolean) =
        assertEquals(expected, ray == other)

    @ParameterizedTest
    @MethodSource("equalsMutableLineSegmentArgs")
    fun equalsReturnsCorrectValue(ray: MutableRay, other: MutableRay, expected: Boolean) =
        assertEquals(expected, ray.equals(other))

    @ParameterizedTest
    @MethodSource("hashCodeArgs")
    fun hashCodeReturnsCorrectValue(ray: MutableRay, other: MutableRay) =
        assertEquals(ray.hashCode(), other.hashCode())

    @ParameterizedTest
    @MethodSource("toStringArgs")
    fun toStringReturnsCorrectValue(ray: MutableRay, expected: String) =
        assertEquals(expected, ray.toString())

    @ParameterizedTest
    @MethodSource("componentsArgs")
    fun componentsReturnCorrectValues(
        ray: Ray,
        expectedComponent1: Wrapper<Vector2F>,
        expectedComponent2: Wrapper<Vector2F>
    ) {
        val (actualComponent1, actualComponent2) = ray

        assertEquals(expectedComponent1.value, actualComponent1)
        assertEquals(expectedComponent2.value, actualComponent2)
    }

    companion object {
        @JvmStatic
        fun originArgs(): List<Arguments> = listOf(
            Arguments.of(
                Ray(Vector2F(-2f, 5f), Vector2F(1f, 0f)),
                Wrapper(Vector2F(-2f, 5f))
            ),
            Arguments.of(
                Ray(Vector2F(3f, 2f), Vector2F(0f, 1f)),
                Wrapper(Vector2F(3f, 2f))
            ),
            Arguments.of(
                Ray(Vector2F(-4f, 3f), Vector2F(1f, -1f).normalized),
                Wrapper(Vector2F(-4f, 3f))
            ),
        )

        @JvmStatic
        fun directionArgs(): List<Arguments> = listOf(
            Arguments.of(
                Ray(Vector2F(-2f, 5f), Vector2F(1f, 0f)),
                Wrapper(Vector2F(1f, 0f))
            ),
            Arguments.of(
                Ray(Vector2F(3f, 2f), Vector2F(0f, 1f)),
                Wrapper(Vector2F(0f, 1f))
            ),
            Arguments.of(
                Ray(Vector2F(-4f, 3f), Vector2F(1f, -1f).normalized),
                Wrapper(Vector2F(1f, -1f).normalized)
            ),
        )

        @JvmStatic
        fun copyArgs(): List<Arguments> = listOf(
            Arguments.of(
                Ray(Vector2F(-2f, 5f), Vector2F(1f, 0f)),
                Wrapper(Vector2F(-2f, 5f)),
                Wrapper(Vector2F(1f, 0f)),
                Ray(Vector2F(-2f, 5f), Vector2F(1f, 0f))
            ),
            Arguments.of(
                Ray(Vector2F(-2f, 5f), Vector2F(1f, 0f)),
                Wrapper(Vector2F(-4f, 3f)),
                Wrapper(Vector2F(1f, 0f)),
                Ray(Vector2F(-4f, 3f), Vector2F(1f, 0f))
            ),
            Arguments.of(
                Ray(Vector2F(-2f, 5f), Vector2F(1f, 0f)),
                Wrapper(Vector2F(-4f, 3f)),
                Wrapper(Vector2F(1f, -1f).normalized),
                Ray(Vector2F(-4f, 3f), Vector2F(1f, -1f).normalized)
            ),
        )

        @JvmStatic
        fun equalsArgs(): List<Arguments> = equalsMutableLineSegmentArgs() + listOf(
            Arguments.of(
                MutableRay(Vector2F(-2f, 5f), Vector2F(1f, 0f)),
                null,
                false
            ),
        )

        @JvmStatic
        fun equalsMutableLineSegmentArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableRay(Vector2F(-2f, 5f), Vector2F(1f, 0f)),
                MutableRay(Vector2F(-2f, 5f), Vector2F(1f, 0f)),
                true
            ),
            Arguments.of(
                MutableRay(Vector2F(-2f, 5f), Vector2F(1f, 0f)),
                MutableRay(Vector2F(-2f, 5f), Vector2F(1.1f, 0f)),
                false
            ),
        )

        @JvmStatic
        fun hashCodeArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableRay(Vector2F(-2f, 5f), Vector2F(1f, 0f)),
                MutableRay(Vector2F(-2f, 5f), Vector2F(1f, 0f))
            ),
            Arguments.of(
                MutableRay(Vector2F(-4f, 3f), Vector2F(1f, -1f).normalized),
                MutableRay(Vector2F(-4f, 3f), Vector2F(1f, -1f).normalized)
            ),
        )

        @JvmStatic
        fun toStringArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableRay(Vector2F(-2f, 5f), Vector2F(1f, 0f)),
                "Ray(origin=${Vector2F(-2f, 5f)}, direction=${Vector2F(1f, 0f)})"
            ),
            Arguments.of(
                MutableRay(Vector2F(-4f, 3f), Vector2F(1f, -1f).normalized),
                "Ray(" +
                        "origin=${Vector2F(-4f, 3f)}, " +
                        "direction=${Vector2F(1f, -1f).normalized})"
            ),
        )

        @JvmStatic
        fun componentsArgs(): List<Arguments> = listOf(
            Arguments.of(
                Ray(Vector2F(-2f, 5f), Vector2F(1f, 0f)),
                Wrapper(Vector2F(-2f, 5f)),
                Wrapper(Vector2F(1f, 0f))
            ),
            Arguments.of(
                Ray(Vector2F(-4f, 3f), Vector2F(1f, -1f).normalized),
                Wrapper(Vector2F(-4f, 3f)),
                Wrapper(Vector2F(1f, -1f).normalized)
            ),
        )
    }
}