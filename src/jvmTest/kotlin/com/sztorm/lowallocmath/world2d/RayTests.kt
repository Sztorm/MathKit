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
    @MethodSource("closestPointToArgs")
    fun closestPointToReturnsCorrectValue(
        ray: Ray, point: Wrapper<Vector2F>, expected: Wrapper<Vector2F>
    ) = assertApproximation(expected.value, ray.closestPointTo(point.value))

    @ParameterizedTest
    @MethodSource("containsVector2FArgs")
    fun containsReturnsCorrectValue(ray: Ray, point: Wrapper<Vector2F>, expected: Boolean) =
        assertEquals(expected, ray.contains(point.value))

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
        fun closestPointToArgs(): List<Arguments> {
            val rayA = Ray(Vector2F(-2f, 5f), Vector2F(1f, 0f))
            val rayAArgs = listOf(
                Arguments.of(
                    rayA, Wrapper(Vector2F(-2.1f, 5.1f)), Wrapper(rayA.origin)
                ),
                Arguments.of(
                    rayA, Wrapper(Vector2F(-2.1f, 5f)), Wrapper(rayA.origin)
                ),
                Arguments.of(
                    rayA, Wrapper(Vector2F(-2.1f, 4.9f)), Wrapper(rayA.origin)
                ),
                Arguments.of(
                    rayA, Wrapper(Vector2F(-2f, 5.1f)), Wrapper(rayA.origin)
                ),
                Arguments.of(
                    rayA, Wrapper(rayA.origin), Wrapper(rayA.origin)
                ),
                Arguments.of(
                    rayA, Wrapper(Vector2F(-2f, 4.9f)), Wrapper(rayA.origin)
                ),
                Arguments.of(
                    rayA, Wrapper(Vector2F(-1.9f, 5.1f)), Wrapper(Vector2F(-1.9f, 5f))
                ),
                Arguments.of(
                    rayA, Wrapper(Vector2F(-1.9f, 5f)), Wrapper(Vector2F(-1.9f, 5f))
                ),
                Arguments.of(
                    rayA, Wrapper(Vector2F(-1.9f, 4.9f)), Wrapper(Vector2F(-1.9f, 5f))
                ),
                Arguments.of(
                    rayA, Wrapper(Vector2F(0f, 5.1f)), Wrapper(Vector2F(0f, 5f))
                ),
                Arguments.of(
                    rayA, Wrapper(Vector2F(0f, 5f)), Wrapper(Vector2F(0f, 5f))
                ),
                Arguments.of(
                    rayA, Wrapper(Vector2F(0f, 4.9f)), Wrapper(Vector2F(0f, 5f))
                ),
                Arguments.of(
                    rayA, Wrapper(Vector2F(2f, 5.1f)), Wrapper(Vector2F(2f, 5f))
                ),
                Arguments.of(
                    rayA, Wrapper(Vector2F(2f, 5f)), Wrapper(Vector2F(2f, 5f))
                ),
                Arguments.of(
                    rayA, Wrapper(Vector2F(2f, 4.9f)), Wrapper(Vector2F(2f, 5f))
                ),
                Arguments.of(
                    rayA, Wrapper(Vector2F(4f, 5.1f)), Wrapper(Vector2F(4f, 5f))
                ),
                Arguments.of(
                    rayA, Wrapper(Vector2F(4f, 5f)), Wrapper(Vector2F(4f, 5f))
                ),
                Arguments.of(
                    rayA, Wrapper(Vector2F(4f, 4.9f)), Wrapper(Vector2F(4f, 5f))
                ),
            )
            val rayB = Ray(Vector2F(3f, 2f), Vector2F(0f, -1f))
            val rayBArgs = listOf(
                Arguments.of(
                    rayB, Wrapper(Vector2F(2.9f, 2.1f)), Wrapper(rayB.origin)
                ),
                Arguments.of(
                    rayB, Wrapper(Vector2F(3f, 2.1f)), Wrapper(rayB.origin)
                ),
                Arguments.of(
                    rayB, Wrapper(Vector2F(3.1f, 2.1f)), Wrapper(rayB.origin)
                ),
                Arguments.of(
                    rayB, Wrapper(Vector2F(2.9f, 2f)), Wrapper(rayB.origin)
                ),
                Arguments.of(
                    rayB, Wrapper(rayB.origin), Wrapper(rayB.origin)
                ),
                Arguments.of(
                    rayB, Wrapper(Vector2F(3.1f, 2f)), Wrapper(rayB.origin)
                ),
                Arguments.of(
                    rayB, Wrapper(Vector2F(2.9f, 1.9f)), Wrapper(Vector2F(3f, 1.9f))
                ),
                Arguments.of(
                    rayB, Wrapper(Vector2F(3f, 1.9f)), Wrapper(Vector2F(3f, 1.9f))
                ),
                Arguments.of(
                    rayB, Wrapper(Vector2F(3.1f, 1.9f)), Wrapper(Vector2F(3f, 1.9f))
                ),
                Arguments.of(
                    rayB, Wrapper(Vector2F(2.9f, -0.5f)), Wrapper(Vector2F(3f, -0.5f))
                ),
                Arguments.of(
                    rayB, Wrapper(Vector2F(3f, -0.5f)), Wrapper(Vector2F(3f, -0.5f))
                ),
                Arguments.of(
                    rayB, Wrapper(Vector2F(3.1f, -0.5f)), Wrapper(Vector2F(3f, -0.5f))
                ),
                Arguments.of(
                    rayB, Wrapper(Vector2F(2.9f, -3f)), Wrapper(Vector2F(3f, -3f))
                ),
                Arguments.of(
                    rayB, Wrapper(Vector2F(3f, -3f)), Wrapper(Vector2F(3f, -3f))
                ),
                Arguments.of(
                    rayB, Wrapper(Vector2F(3.1f, -3f)), Wrapper(Vector2F(3f, -3f))
                ),
                Arguments.of(
                    rayB, Wrapper(Vector2F(2.9f, -5.5f)), Wrapper(Vector2F(3f, -5.5f))
                ),
                Arguments.of(
                    rayB, Wrapper(Vector2F(3f, -5.5f)), Wrapper(Vector2F(3f, -5.5f))
                ),
                Arguments.of(
                    rayB, Wrapper(Vector2F(3.1f, -5.5f)), Wrapper(Vector2F(3f, -5.5f))
                ),
            )
            val rayC = Ray(Vector2F(-2f, -1f), Vector2F(-0.4472136f, 0.8944272f))
            val rayCArgs = listOf(
                Arguments.of(
                    rayC,
                    Wrapper(Vector2F(-2.0447214f, -1.1341641f)),
                    Wrapper(rayC.origin)
                ),
                Arguments.of(
                    rayC,
                    Wrapper(Vector2F(-1.9552786f, -1.0894427f)),
                    Wrapper(rayC.origin)
                ),
                Arguments.of(
                    rayC,
                    Wrapper(Vector2F(-1.8658359f, -1.0447214f)),
                    Wrapper(rayC.origin)
                ),
                Arguments.of(
                    rayC,
                    Wrapper(Vector2F(-2.0894427f, -1.0447214f)),
                    Wrapper(rayC.origin)
                ),
                Arguments.of(
                    rayC,
                    Wrapper(rayC.origin),
                    Wrapper(rayC.origin)
                ),
                Arguments.of(
                    rayC,
                    Wrapper(Vector2F(-1.9105573f, -0.95527864f)),
                    Wrapper(rayC.origin)
                ),
                Arguments.of(
                    rayC,
                    Wrapper(Vector2F(-2.134164f, -0.95527864f)),
                    Wrapper(Vector2F(-2.0447214f, -0.91055727f))
                ),
                Arguments.of(
                    rayC,
                    Wrapper(Vector2F(-2.0447214f, -0.91055727f)),
                    Wrapper(Vector2F(-2.0447214f, -0.91055727f))
                ),
                Arguments.of(
                    rayC,
                    Wrapper(Vector2F(-1.9552786f, -0.8658359f)),
                    Wrapper(Vector2F(-2.0447214f, -0.91055727f))
                ),
                Arguments.of(
                    rayC,
                    Wrapper(Vector2F(-3.0894427f, 0.95527864f)),
                    Wrapper(Vector2F(-3f, 1f))
                ),
                Arguments.of(
                    rayC,
                    Wrapper(Vector2F(-3f, 1f)),
                    Wrapper(Vector2F(-3f, 1f))
                ),
                Arguments.of(
                    rayC,
                    Wrapper(Vector2F(-2.9105573f, 1.0447214f)),
                    Wrapper(Vector2F(-3f, 1f))
                ),
                Arguments.of(
                    rayC,
                    Wrapper(Vector2F(-4.0894427f, 2.9552786f)),
                    Wrapper(Vector2F(-4f, 3f))
                ),
                Arguments.of(
                    rayC,
                    Wrapper(Vector2F(-4f, 3f)),
                    Wrapper(Vector2F(-4f, 3f))
                ),
                Arguments.of(
                    rayC,
                    Wrapper(Vector2F(-3.9105573f, 3.0447214f)),
                    Wrapper(Vector2F(-4f, 3f))
                ),
                Arguments.of(
                    rayC,
                    Wrapper(Vector2F(-5.0894427f, 4.9552784f)),
                    Wrapper(Vector2F(-5f, 5f))
                ),
                Arguments.of(
                    rayC,
                    Wrapper(Vector2F(-5f, 5f)),
                    Wrapper(Vector2F(-5f, 5f))
                ),
                Arguments.of(
                    rayC,
                    Wrapper(Vector2F(-4.9105573f, 5.0447216f)),
                    Wrapper(Vector2F(-5f, 5f))
                ),
            )
            return rayAArgs + rayBArgs + rayCArgs
        }

        @JvmStatic
        fun containsVector2FArgs(): List<Arguments> {
            val rayA = Ray(Vector2F(-2f, 5f), Vector2F(1f, 0f))
            val rayAArgs = listOf(
                Arguments.of(rayA, Wrapper(Vector2F(-2.1f, 5.1f)), false),
                Arguments.of(rayA, Wrapper(Vector2F(-2.1f, 5f)), false),
                Arguments.of(rayA, Wrapper(Vector2F(-2.1f, 4.9f)), false),
                Arguments.of(rayA, Wrapper(Vector2F(-2f, 5.1f)), false),
                Arguments.of(rayA, Wrapper(rayA.origin), true),
                Arguments.of(rayA, Wrapper(Vector2F(-2f, 4.9f)), false),
                Arguments.of(rayA, Wrapper(Vector2F(-1.9f, 5.1f)), false),
                Arguments.of(rayA, Wrapper(Vector2F(-1.9f, 5f)), true),
                Arguments.of(rayA, Wrapper(Vector2F(-1.9f, 4.9f)), false),
                Arguments.of(rayA, Wrapper(Vector2F(0f, 5.1f)), false),
                Arguments.of(rayA, Wrapper(Vector2F(0f, 5f)), true),
                Arguments.of(rayA, Wrapper(Vector2F(0f, 4.9f)), false),
                Arguments.of(rayA, Wrapper(Vector2F(2f, 5.1f)), false),
                Arguments.of(rayA, Wrapper(Vector2F(2f, 5f)), true),
                Arguments.of(rayA, Wrapper(Vector2F(2f, 4.9f)), false),
                Arguments.of(rayA, Wrapper(Vector2F(4f, 5.1f)), false),
                Arguments.of(rayA, Wrapper(Vector2F(4f, 5f)), true),
                Arguments.of(rayA, Wrapper(Vector2F(4f, 4.9f)), false),
            )
            val rayB = Ray(Vector2F(3f, 2f), Vector2F(0f, -1f))
            val rayBArgs = listOf(
                Arguments.of(rayB, Wrapper(Vector2F(2.9f, 2.1f)), false),
                Arguments.of(rayB, Wrapper(Vector2F(3f, 2.1f)), false),
                Arguments.of(rayB, Wrapper(Vector2F(3.1f, 2.1f)), false),
                Arguments.of(rayB, Wrapper(Vector2F(2.9f, 2f)), false),
                Arguments.of(rayB, Wrapper(rayB.origin), true),
                Arguments.of(rayB, Wrapper(Vector2F(3.1f, 2f)), false),
                Arguments.of(rayB, Wrapper(Vector2F(2.9f, 1.9f)), false),
                Arguments.of(rayB, Wrapper(Vector2F(3f, 1.9f)), true),
                Arguments.of(rayB, Wrapper(Vector2F(3.1f, 1.9f)), false),
                Arguments.of(rayB, Wrapper(Vector2F(2.9f, -0.5f)), false),
                Arguments.of(rayB, Wrapper(Vector2F(3f, -0.5f)), true),
                Arguments.of(rayB, Wrapper(Vector2F(3.1f, -0.5f)), false),
                Arguments.of(rayB, Wrapper(Vector2F(2.9f, -3f)), false),
                Arguments.of(rayB, Wrapper(Vector2F(3f, -3f)), true),
                Arguments.of(rayB, Wrapper(Vector2F(3.1f, -3f)), false),
                Arguments.of(rayB, Wrapper(Vector2F(2.9f, -5.5f)), false),
                Arguments.of(rayB, Wrapper(Vector2F(3f, -5.5f)), true),
                Arguments.of(rayB, Wrapper(Vector2F(3.1f, -5.5f)), false),
            )
            val rayC = Ray(Vector2F(-2f, -1f), Vector2F(-0.4472136f, 0.8944272f))
            val rayCArgs = listOf(
                Arguments.of(rayC, Wrapper(Vector2F(-2.0447214f, -1.1341641f)), false),
                Arguments.of(rayC, Wrapper(Vector2F(-1.9552786f, -1.0894427f)), false),
                Arguments.of(rayC, Wrapper(Vector2F(-1.8658359f, -1.0447214f)), false),
                Arguments.of(rayC, Wrapper(Vector2F(-2.0894427f, -1.0447214f)), false),
                Arguments.of(rayC, Wrapper(rayC.origin), true),
                Arguments.of(rayC, Wrapper(Vector2F(-1.9105573f, -0.95527864f)), false),
                Arguments.of(rayC, Wrapper(Vector2F(-2.134164f, -0.95527864f)), false),
                Arguments.of(rayC, Wrapper(Vector2F(-2.0447214f, -0.91055727f)), true),
                Arguments.of(rayC, Wrapper(Vector2F(-1.9552786f, -0.8658359f)), false),
                Arguments.of(rayC, Wrapper(Vector2F(-3.0894427f, 0.95527864f)), false),
                Arguments.of(rayC, Wrapper(Vector2F(-3f, 1f)), true),
                Arguments.of(rayC, Wrapper(Vector2F(-2.9105573f, 1.0447214f)), false),
                Arguments.of(rayC, Wrapper(Vector2F(-4.0894427f, 2.9552786f)), false),
                Arguments.of(rayC, Wrapper(Vector2F(-4f, 3f)), true),
                Arguments.of(rayC, Wrapper(Vector2F(-3.9105573f, 3.0447214f)), false),
                Arguments.of(rayC, Wrapper(Vector2F(-5.0894427f, 4.9552784f)), false),
                Arguments.of(rayC, Wrapper(Vector2F(-5f, 5f)), true),
                Arguments.of(rayC, Wrapper(Vector2F(-4.9105573f, 5.0447216f)), false),
            )
            return rayAArgs + rayBArgs + rayCArgs
        }

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