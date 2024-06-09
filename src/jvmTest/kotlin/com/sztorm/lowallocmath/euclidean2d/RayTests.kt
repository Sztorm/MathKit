package com.sztorm.lowallocmath.euclidean2d

import com.sztorm.lowallocmath.AngleF
import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F
import com.sztorm.lowallocmath.euclidean2d.RectangleTests.Companion.mapRectanglesToDefaultRectangles
import com.sztorm.lowallocmath.euclidean2d.RoundedRectangleTests.Companion.mapRoundedRectanglesToDefaultRoundedRectangles
import com.sztorm.lowallocmath.euclidean2d.SquareTests.Companion.mapSquaresToDefaultSquares
import com.sztorm.lowallocmath.euclidean2d.TriangleTests.Companion.mapTrianglesToDefaultTriangles
import com.sztorm.lowallocmath.euclidean2d.utils.DefaultRay
import com.sztorm.lowallocmath.euclidean2d.utils.assertImmutabilityOf
import com.sztorm.lowallocmath.utils.Wrapper
import com.sztorm.lowallocmath.utils.assertApproximation
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

class RayTests {
    @ParameterizedTest
    @MethodSource("constructorArgs")
    fun constructorCreatesCorrectRay(
        origin: Wrapper<Vector2F>, direction: Wrapper<Vector2F>
    ) {
        val mutableRay = MutableRay(origin.value, direction.value)
        val ray = Ray(origin.value, direction.value)

        assertEquals(origin.value, mutableRay.origin)
        assertEquals(direction.value, mutableRay.direction)

        assertEquals(origin.value, ray.origin)
        assertEquals(direction.value, ray.direction)
    }

    @ParameterizedTest
    @MethodSource("originArgs")
    fun originReturnsCorrectValue(ray: Ray, expected: Wrapper<Vector2F>) =
        assertImmutabilityOf(ray) {
            assertApproximation(expected.value, ray.origin)
        }

    @ParameterizedTest
    @MethodSource("directionArgs")
    fun directionReturnsCorrectValue(ray: Ray, expected: Wrapper<Vector2F>) =
        assertImmutabilityOf(ray) {
            assertApproximation(expected.value, ray.direction)
        }

    @ParameterizedTest
    @MethodSource("positionArgs")
    fun positionReturnsCorrectValue(ray: Ray, expected: Wrapper<Vector2F>) =
        assertImmutabilityOf(ray) {
            assertApproximation(expected.value, ray.position)
        }

    @ParameterizedTest
    @MethodSource("orientationArgs")
    fun orientationReturnsCorrectValue(ray: Ray, expected: Wrapper<ComplexF>) =
        assertImmutabilityOf(ray) {
            assertApproximation(expected.value, ray.orientation)
        }

    @ParameterizedTest
    @MethodSource("setArgs")
    fun setMutatesRayCorrectly(
        ray: MutableRay,
        origin: Wrapper<Vector2F>,
        direction: Wrapper<Vector2F>,
        expected: MutableRay
    ) = assertEquals(expected, ray.apply { set(origin.value, direction.value) })

    @ParameterizedTest
    @MethodSource("interpolatedArgs")
    fun interpolatedReturnsCorrectValue(ray: Ray, to: Ray, by: Float, expected: Ray) =
        assertImmutabilityOf(ray) {
            assertImmutabilityOf(to) {
                assertApproximation(expected, ray.interpolated(to, by))
            }
        }

    @ParameterizedTest
    @MethodSource("interpolateArgs")
    fun interpolateMutatesRayCorrectly(
        ray: MutableRay, from: Ray, to: Ray, by: Float, expected: MutableRay
    ) = assertImmutabilityOf(from) {
        assertImmutabilityOf(to) {
            assertApproximation(expected, ray.apply { interpolate(from, to, by) })
        }
    }

    @ParameterizedTest
    @MethodSource("closestPointToArgs")
    fun closestPointToReturnsCorrectValue(
        ray: Ray, point: Wrapper<Vector2F>, expected: Wrapper<Vector2F>
    ) = assertImmutabilityOf(ray) {
        assertApproximation(expected.value, ray.closestPointTo(point.value))
    }

    @ParameterizedTest
    @MethodSource("intersectsAnnulusArgs")
    fun intersectsReturnsCorrectValue(ray: Ray, annulus: Annulus, expected: Boolean) =
        assertImmutabilityOf(ray) {
            assertImmutabilityOf(annulus) {
                assertEquals(expected, ray.intersects(annulus))
            }
        }

    @ParameterizedTest
    @MethodSource("intersectsCircleArgs")
    fun intersectsReturnsCorrectValue(ray: Ray, circle: Circle, expected: Boolean) =
        assertImmutabilityOf(ray) {
            assertImmutabilityOf(circle) {
                assertEquals(expected, ray.intersects(circle))
            }
        }

    @ParameterizedTest
    @MethodSource("intersectsLineSegmentArgs")
    fun intersectsReturnsCorrectValue(ray: Ray, lineSegment: LineSegment, expected: Boolean) =
        assertImmutabilityOf(ray) {
            assertImmutabilityOf(lineSegment) {
                assertEquals(expected, ray.intersects(lineSegment))
            }
        }

    @ParameterizedTest
    @MethodSource("intersectsRayArgs")
    fun intersectsReturnsCorrectValue(ray: Ray, otherRay: Ray, expected: Boolean) =
        assertImmutabilityOf(ray) {
            assertImmutabilityOf(otherRay) {
                assertEquals(expected, ray.intersects(otherRay))
            }
        }

    @ParameterizedTest
    @MethodSource("intersectsRectangleArgs")
    fun intersectsRectangleReturnsCorrectValue(ray: Ray, rectangle: Rectangle, expected: Boolean) =
        assertImmutabilityOf(ray) {
            assertImmutabilityOf(rectangle) {
                assertEquals(expected, ray.intersects(rectangle))
            }
        }

    @ParameterizedTest
    @MethodSource("intersectsRoundedRectangleArgs")
    fun intersectsRoundedRectangleReturnsCorrectValue(
        ray: Ray, rectangle: RoundedRectangle, expected: Boolean
    ) = assertImmutabilityOf(ray) {
        assertImmutabilityOf(rectangle) {
            assertEquals(expected, ray.intersects(rectangle))
        }
    }

    @ParameterizedTest
    @MethodSource("intersectsSquareArgs")
    fun intersectsSquareReturnsCorrectValue(ray: Ray, square: Square, expected: Boolean) =
        assertImmutabilityOf(ray) {
            assertImmutabilityOf(square) {
                assertEquals(expected, ray.intersects(square))
            }
        }

    @ParameterizedTest
    @MethodSource("intersectsTriangleArgs")
    fun intersectsTriangleReturnsCorrectValue(ray: Ray, triangle: Triangle, expected: Boolean) =
        assertImmutabilityOf(ray) {
            assertImmutabilityOf(triangle) {
                assertEquals(expected, ray.intersects(triangle))
            }
        }

    @ParameterizedTest
    @MethodSource("containsVector2FArgs")
    fun containsReturnsCorrectValue(ray: Ray, point: Wrapper<Vector2F>, expected: Boolean) =
        assertImmutabilityOf(ray) {
            assertEquals(expected, ray.contains(point.value))
        }

    @ParameterizedTest
    @MethodSource("copyArgs")
    fun copyReturnsCorrectValue(
        ray: Ray,
        origin: Wrapper<Vector2F>,
        direction: Wrapper<Vector2F>,
        expected: Ray
    ) = assertEquals(expected, ray.copy(origin.value, direction.value))

    @ParameterizedTest
    @MethodSource("equalsAnyArgs")
    fun equalsReturnsCorrectValue(ray: MutableRay, other: Any?, expected: Boolean) =
        assertImmutabilityOf(ray) {
            assertEquals(expected, ray == other)
        }

    @ParameterizedTest
    @MethodSource("equalsMutableLineSegmentArgs")
    fun equalsReturnsCorrectValue(ray: MutableRay, other: MutableRay, expected: Boolean) =
        assertImmutabilityOf(ray) {
            assertImmutabilityOf(other) {
                assertEquals(expected, ray.equals(other))
            }
        }

    @ParameterizedTest
    @MethodSource("hashCodeArgs")
    fun hashCodeReturnsCorrectValue(ray: MutableRay, other: MutableRay) =
        assertImmutabilityOf(ray) {
            assertImmutabilityOf(other) {
                assertEquals(ray.hashCode(), other.hashCode())
            }
        }

    @ParameterizedTest
    @MethodSource("toStringArgs")
    fun toStringReturnsCorrectValue(ray: MutableRay, expected: String) =
        assertImmutabilityOf(ray) {
            assertEquals(expected, ray.toString())
        }

    @ParameterizedTest
    @MethodSource("componentsArgs")
    fun componentsReturnCorrectValues(
        ray: Ray,
        expectedComponent1: Wrapper<Vector2F>,
        expectedComponent2: Wrapper<Vector2F>
    ) = assertImmutabilityOf(ray) {
        val (actualComponent1: Vector2F, actualComponent2: Vector2F) = ray

        assertEquals(expectedComponent1.value, actualComponent1)
        assertEquals(expectedComponent2.value, actualComponent2)
    }

    companion object {
        @JvmStatic
        fun areApproximatelyEqual(a: Ray, b: Ray, tolerance: Float = 0.00001f): Boolean =
            a.origin.isApproximately(b.origin, tolerance) and
                    a.direction.isApproximately(b.direction, tolerance)

        @JvmStatic
        fun clone(ray: Ray) = ray.copy()

        @JvmStatic
        fun List<Arguments>.mapRaysToDefaultRays() = map { args ->
            val argArray = args.get().map {
                if (it is Ray) DefaultRay(it.origin, it.direction)
                else it
            }.toTypedArray()

            Arguments.of(*argArray)
        }

        @JvmStatic
        fun constructorArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2F(-2f, 5f)),
                Wrapper(Vector2F(1f, 0f))
            ),
            Arguments.of(
                Wrapper(Vector2F(3f, 2f)),
                Wrapper(Vector2F(0f, 1f))
            ),
            Arguments.of(
                Wrapper(Vector2F(-4f, 3f)),
                Wrapper(Vector2F(1f, -1f).normalized)
            ),
        )

        @JvmStatic
        fun originArgs(): List<Arguments> {
            val mutableRayArgs = listOf(
                Arguments.of(
                    MutableRay(Vector2F(-2f, 5f), Vector2F(1f, 0f)),
                    Wrapper(Vector2F(-2f, 5f))
                ),
                Arguments.of(
                    MutableRay(Vector2F(3f, 2f), Vector2F(0f, 1f)),
                    Wrapper(Vector2F(3f, 2f))
                ),
                Arguments.of(
                    MutableRay(Vector2F(-4f, 3f), Vector2F(1f, -1f).normalized),
                    Wrapper(Vector2F(-4f, 3f))
                ),
            )
            val defaultRayArgs = mutableRayArgs.mapRaysToDefaultRays()

            return listOf(
                mutableRayArgs,
                defaultRayArgs
            ).flatten()
        }

        @JvmStatic
        fun directionArgs(): List<Arguments> {
            val mutableRayArgs = listOf(
                Arguments.of(
                    MutableRay(Vector2F(-2f, 5f), Vector2F(1f, 0f)),
                    Wrapper(Vector2F(1f, 0f))
                ),
                Arguments.of(
                    MutableRay(Vector2F(3f, 2f), Vector2F(0f, 1f)),
                    Wrapper(Vector2F(0f, 1f))
                ),
                Arguments.of(
                    MutableRay(Vector2F(-4f, 3f), Vector2F(0.7071068f, -0.7071068f)),
                    Wrapper(Vector2F(0.7071068f, -0.7071068f))
                ),
            )
            val defaultRayArgs = mutableRayArgs.mapRaysToDefaultRays()

            return listOf(
                mutableRayArgs,
                defaultRayArgs
            ).flatten()
        }

        @JvmStatic
        fun positionArgs(): List<Arguments> = originArgs()

        @JvmStatic
        fun orientationArgs(): List<Arguments> {
            val mutableRayArgs = listOf(
                Arguments.of(
                    MutableRay(Vector2F(-2f, 5f), Vector2F(1f, 0f)),
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(0f)))
                ),
                Arguments.of(
                    MutableRay(Vector2F(3f, 2f), Vector2F(0f, 1f)),
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(90f)))
                ),
                Arguments.of(
                    MutableRay(Vector2F(-4f, 3f), Vector2F(0.7071068f, -0.7071068f)),
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(-45f)))
                ),
            )
            val defaultRayArgs = mutableRayArgs.mapRaysToDefaultRays()

            return listOf(
                mutableRayArgs,
                defaultRayArgs
            ).flatten()
        }

        @JvmStatic
        fun setArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableRay(Vector2F(-2f, 5f), Vector2F(1f, 0f)),
                Wrapper(Vector2F(-2f, 5f)),
                Wrapper(Vector2F(1f, 0f)),
                MutableRay(Vector2F(-2f, 5f), Vector2F(1f, 0f))
            ),
            Arguments.of(
                MutableRay(Vector2F(-2f, 5f), Vector2F(1f, 0f)),
                Wrapper(Vector2F(-4f, 3f)),
                Wrapper(Vector2F(1f, 0f)),
                MutableRay(Vector2F(-4f, 3f), Vector2F(1f, 0f))
            ),
            Arguments.of(
                MutableRay(Vector2F(-2f, 5f), Vector2F(1f, 0f)),
                Wrapper(Vector2F(-4f, 3f)),
                Wrapper(Vector2F(1f, -1f).normalized),
                MutableRay(Vector2F(-4f, 3f), Vector2F(1f, -1f).normalized)
            ),
        )

        @JvmStatic
        fun interpolatedArgs(): List<Arguments> {
            val mutableRayArgs = interpolateArgs().map {
                Arguments.of(*it.get().drop(1).toTypedArray())
            }
            val defaultRayArgs = mutableRayArgs.mapRaysToDefaultRays()

            return listOf(
                mutableRayArgs,
                defaultRayArgs
            ).flatten()
        }

        @JvmStatic
        fun interpolateArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableRay(origin = Vector2F.ZERO, direction = Vector2F(1f, 0f)),
                MutableRay(Vector2F(3f, 2f), Vector2F(0f, 1f)),
                MutableRay(Vector2F(3f, 2f), Vector2F(0f, 1f)),
                0.5f,
                MutableRay(Vector2F(3f, 2f), Vector2F(0f, 1f))
            ),
            Arguments.of(
                MutableRay(origin = Vector2F.ZERO, direction = Vector2F(1f, 0f)),
                MutableRay(Vector2F(3f, 2f), Vector2F(0f, 1f)),
                MutableRay(Vector2F(-4f, 3f), Vector2F(0.7071068f, -0.7071068f)),
                0f,
                MutableRay(Vector2F(3f, 2f), Vector2F(0f, 1f))
            ),
            Arguments.of(
                MutableRay(origin = Vector2F.ZERO, direction = Vector2F(1f, 0f)),
                MutableRay(Vector2F(3f, 2f), Vector2F(0f, 1f)),
                MutableRay(Vector2F(-4f, 3f), Vector2F(0.7071068f, -0.7071068f)),
                1f,
                MutableRay(Vector2F(-4f, 3f), Vector2F(0.7071068f, -0.7071068f))
            ),
            Arguments.of(
                MutableRay(origin = Vector2F.ZERO, direction = Vector2F(1f, 0f)),
                MutableRay(Vector2F(3f, 2f), Vector2F(0f, 1f)),
                MutableRay(Vector2F(-4f, 3f), Vector2F(0.7071068f, -0.7071068f)),
                0.5f,
                MutableRay(Vector2F(-0.5f, 2.5f), Vector2F(0.9238795f, 0.38268343f))
            ),
        )

        @JvmStatic
        fun closestPointToArgs(): List<Arguments> {
            val rayA = MutableRay(Vector2F(-2f, 5f), Vector2F(1f, 0f))
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
            val rayB = MutableRay(Vector2F(3f, 2f), Vector2F(0f, -1f))
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
            val rayC = MutableRay(
                Vector2F(-2f, -1f), Vector2F(-0.4472136f, 0.8944272f)
            )
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
            val mutableRayArgs = listOf(
                rayAArgs,
                rayBArgs,
                rayCArgs
            ).flatten()
            val defaultRayArgs = mutableRayArgs.mapRaysToDefaultRays()

            return listOf(
                mutableRayArgs,
                defaultRayArgs
            ).flatten()
        }

        @JvmStatic
        fun intersectsAnnulusArgs(): List<Arguments> = AnnulusTests.intersectsRayArgs().map {
            val args = it.get()
            Arguments.of(args[1], args[0], args[2])
        }

        @JvmStatic
        fun intersectsCircleArgs(): List<Arguments> = CircleTests.intersectsRayArgs().map {
            val args = it.get()
            Arguments.of(args[1], args[0], args[2])
        }

        @JvmStatic
        fun intersectsLineSegmentArgs(): List<Arguments> =
            LineSegmentTests.intersectsRayArgs().map {
                val args = it.get()
                Arguments.of(args[1], args[0], args[2])
            }

        @JvmStatic
        fun intersectsRayArgs(): List<Arguments> {
            fun List<Arguments>.withRayArgsSwapped() = map {
                val args = it.get()
                Arguments.of(args[1], args[0], args[2])
            }

            fun createMutableRayArgs(): List<Arguments> {
                val identicalRaysArgs = listOf(
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-2f, 5f), direction = Vector2F(1f, 0f)
                        ),
                        MutableRay(
                            origin = Vector2F(-2f, 5f), direction = Vector2F(1f, 0f)
                        ),
                        true
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(5f, 2f), direction = Vector2F(0f, 1f)
                        ),
                        MutableRay(
                            origin = Vector2F(5f, 2f), direction = Vector2F(0f, 1f)
                        ),
                        true
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-2f, -5f),
                            direction = ComplexF.fromAngle(AngleF.fromDegrees(45f))
                                .toVector2F()
                        ),
                        MutableRay(
                            origin = Vector2F(-2f, -5f),
                            direction = ComplexF.fromAngle(AngleF.fromDegrees(45f))
                                .toVector2F()
                        ),
                        true
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-2f, -5f),
                            direction = ComplexF.fromAngle(AngleF.fromDegrees(135f))
                                .toVector2F()
                        ),
                        MutableRay(
                            origin = Vector2F(-2f, -5f),
                            direction = ComplexF.fromAngle(AngleF.fromDegrees(135f))
                                .toVector2F()
                        ),
                        true
                    ),
                )
                val sameDirectionRaysArgs = listOf(
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-2f, 5f), direction = Vector2F(1f, 0f)
                        ),
                        MutableRay(
                            origin = Vector2F(-2.1f, 5f), direction = Vector2F(1f, 0f)
                        ),
                        true
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-2f, 5f), direction = Vector2F(1f, 0f)
                        ),
                        MutableRay(
                            origin = Vector2F(-1.9f, 5f), direction = Vector2F(1f, 0f)
                        ),
                        true
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-2f, 5f), direction = Vector2F(1f, 0f)
                        ),
                        MutableRay(
                            origin = Vector2F(-2f, 5.1f), direction = Vector2F(1f, 0f)
                        ),
                        false
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-2f, 5f), direction = Vector2F(1f, 0f)
                        ),
                        MutableRay(
                            origin = Vector2F(-2f, 4.9f), direction = Vector2F(1f, 0f)
                        ),
                        false
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(5f, 2f), direction = Vector2F(0f, 1f)
                        ),
                        MutableRay(
                            origin = Vector2F(5f, 2.1f), direction = Vector2F(0f, 1f)
                        ),
                        true
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(5f, 2f), direction = Vector2F(0f, 1f)
                        ),
                        MutableRay(
                            origin = Vector2F(5f, 1.9f), direction = Vector2F(0f, 1f)
                        ),
                        true
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(5f, 2f), direction = Vector2F(0f, 1f)
                        ),
                        MutableRay(
                            origin = Vector2F(5.1f, 2f), direction = Vector2F(0f, 1f)
                        ),
                        false
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(5f, 2f), direction = Vector2F(0f, 1f)
                        ),
                        MutableRay(
                            origin = Vector2F(4.9f, 2f), direction = Vector2F(0f, 1f)
                        ),
                        false
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-2f, -5f),
                            direction = ComplexF.fromAngle(AngleF.fromDegrees(45f))
                                .toVector2F()
                        ),
                        MutableRay(
                            origin = Vector2F(-2.1f, -5.1f),
                            direction = ComplexF.fromAngle(AngleF.fromDegrees(45f))
                                .toVector2F()
                        ),
                        true
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-2f, -5f),
                            direction = ComplexF.fromAngle(AngleF.fromDegrees(45f))
                                .toVector2F()
                        ),
                        MutableRay(
                            origin = Vector2F(-1.9f, -4.9f),
                            direction = ComplexF.fromAngle(AngleF.fromDegrees(45f))
                                .toVector2F()
                        ),
                        true
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-2f, -5f),
                            direction = ComplexF.fromAngle(AngleF.fromDegrees(45f))
                                .toVector2F()
                        ),
                        MutableRay(
                            origin = Vector2F(-2f, -5.1f),
                            direction = ComplexF.fromAngle(AngleF.fromDegrees(45f))
                                .toVector2F()
                        ),
                        false
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-2f, -5f),
                            direction = ComplexF.fromAngle(AngleF.fromDegrees(45f))
                                .toVector2F()
                        ),
                        MutableRay(
                            origin = Vector2F(-2.1f, -5f),
                            direction = ComplexF.fromAngle(AngleF.fromDegrees(45f))
                                .toVector2F()
                        ),
                        false
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-2f, -5f),
                            direction = ComplexF.fromAngle(AngleF.fromDegrees(135f))
                                .toVector2F()
                        ),
                        MutableRay(
                            origin = Vector2F(-2.1f, -4.9f),
                            direction = ComplexF.fromAngle(AngleF.fromDegrees(135f))
                                .toVector2F()
                        ),
                        true
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-2f, -5f),
                            direction = ComplexF.fromAngle(AngleF.fromDegrees(135f))
                                .toVector2F()
                        ),
                        MutableRay(
                            origin = Vector2F(-1.9f, -5.1f),
                            direction = ComplexF.fromAngle(AngleF.fromDegrees(135f))
                                .toVector2F()
                        ),
                        true
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-2f, -5f),
                            direction = ComplexF.fromAngle(AngleF.fromDegrees(135f))
                                .toVector2F()
                        ),
                        MutableRay(
                            origin = Vector2F(-2f, -4.9f),
                            direction = ComplexF.fromAngle(AngleF.fromDegrees(135f))
                                .toVector2F()
                        ),
                        false
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-2f, -5f),
                            direction = ComplexF.fromAngle(AngleF.fromDegrees(135f))
                                .toVector2F()
                        ),
                        MutableRay(
                            origin = Vector2F(-1.9f, -5f),
                            direction = ComplexF.fromAngle(AngleF.fromDegrees(135f))
                                .toVector2F()
                        ),
                        false
                    ),
                )
                val oppositeDirectionRaysArgs = listOf(
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-2f, 5f), direction = Vector2F(1f, 0f)
                        ),
                        MutableRay(
                            origin = Vector2F(-2.1f, 5f), direction = Vector2F(-1f, 0f)
                        ),
                        false
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-2f, 5f), direction = Vector2F(1f, 0f)
                        ),
                        MutableRay(
                            origin = Vector2F(-1.9f, 5f), direction = Vector2F(-1f, 0f)
                        ),
                        true
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(5f, 2f), direction = Vector2F(0f, 1f)
                        ),
                        MutableRay(
                            origin = Vector2F(5f, 2.1f), direction = Vector2F(0f, -1f)
                        ),
                        true
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(5f, 2f), direction = Vector2F(0f, 1f)
                        ),
                        MutableRay(
                            origin = Vector2F(5f, 1.9f), direction = Vector2F(0f, -1f)
                        ),
                        false
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-2f, -5f),
                            direction = ComplexF.fromAngle(AngleF.fromDegrees(45f))
                                .toVector2F()
                        ),
                        MutableRay(
                            origin = Vector2F(-1.9f, -4.9f),
                            direction = ComplexF.fromAngle(AngleF.fromDegrees(-135f))
                                .toVector2F()
                        ),
                        true
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-2f, -5f),
                            direction = ComplexF.fromAngle(AngleF.fromDegrees(45f))
                                .toVector2F()
                        ),
                        MutableRay(
                            origin = Vector2F(-2.1f, -5.1f),
                            direction = ComplexF.fromAngle(AngleF.fromDegrees(-135f))
                                .toVector2F()
                        ),
                        false
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-2f, -5f),
                            direction = ComplexF.fromAngle(AngleF.fromDegrees(135f))
                                .toVector2F()
                        ),
                        MutableRay(
                            origin = Vector2F(-2.1f, -4.9f),
                            direction = ComplexF.fromAngle(AngleF.fromDegrees(-45f))
                                .toVector2F()
                        ),
                        true
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-2f, -5f),
                            direction = ComplexF.fromAngle(AngleF.fromDegrees(135f))
                                .toVector2F()
                        ),
                        MutableRay(
                            origin = Vector2F(-1.9f, -5.1f),
                            direction = ComplexF.fromAngle(AngleF.fromDegrees(-45f))
                                .toVector2F()
                        ),
                        false
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-2f, -5f),
                            direction = ComplexF.fromAngle(AngleF.fromDegrees(135f))
                                .toVector2F()
                        ),
                        MutableRay(
                            origin = Vector2F(-2.000001f, -5.000001f),
                            direction = ComplexF.fromAngle(AngleF.fromDegrees(-45f))
                                .toVector2F()
                        ),
                        true
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-2f, -5f),
                            direction = ComplexF.fromAngle(AngleF.fromDegrees(135f))
                                .toVector2F()
                        ),
                        MutableRay(
                            origin = Vector2F(-1.999999f, -4.999999f),
                            direction = ComplexF.fromAngle(AngleF.fromDegrees(-45f))
                                .toVector2F()
                        ),
                        true
                    ),
                )
                val allDirectionRayArgs = listOf(
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(1f, 4f),
                            direction = ComplexF.fromAngle(AngleF.fromDegrees(150f))
                                .toVector2F()
                        ),
                        MutableRay(
                            origin = Vector2F(-2f, 5f),
                            direction = ComplexF.fromAngle(AngleF.fromDegrees(20f))
                                .toVector2F()
                        ),
                        true
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(1f, 4f),
                            direction = ComplexF.fromAngle(AngleF.fromDegrees(150f))
                                .toVector2F()
                        ),
                        MutableRay(
                            origin = Vector2F(-2f, 5f),
                            direction = ComplexF.fromAngle(AngleF.fromDegrees(60f))
                                .toVector2F()
                        ),
                        true
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(1f, 4f),
                            direction = ComplexF.fromAngle(AngleF.fromDegrees(150f))
                                .toVector2F()
                        ),
                        MutableRay(
                            origin = Vector2F(-2f, 5f),
                            direction = ComplexF.fromAngle(AngleF.fromDegrees(100f))
                                .toVector2F()
                        ),
                        true
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(1f, 4f),
                            direction = ComplexF.fromAngle(AngleF.fromDegrees(150f))
                                .toVector2F()
                        ),
                        MutableRay(
                            origin = Vector2F(-2f, 5f),
                            direction = ComplexF.fromAngle(AngleF.fromDegrees(140f))
                                .toVector2F()
                        ),
                        true
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(1f, 4f),
                            direction = ComplexF.fromAngle(AngleF.fromDegrees(150f))
                                .toVector2F()
                        ),
                        MutableRay(
                            origin = Vector2F(-2f, 5f),
                            direction = ComplexF.fromAngle(AngleF.fromDegrees(180f))
                                .toVector2F()
                        ),
                        false
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(1f, 4f),
                            direction = ComplexF.fromAngle(AngleF.fromDegrees(150f))
                                .toVector2F()
                        ),
                        MutableRay(
                            origin = Vector2F(-2f, 5f),
                            direction = ComplexF.fromAngle(AngleF.fromDegrees(220f))
                                .toVector2F()
                        ),
                        false
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(1f, 4f),
                            direction = ComplexF.fromAngle(AngleF.fromDegrees(150f))
                                .toVector2F()
                        ),
                        MutableRay(
                            origin = Vector2F(-2f, 5f),
                            direction = ComplexF.fromAngle(AngleF.fromDegrees(260f))
                                .toVector2F()
                        ),
                        false
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(1f, 4f),
                            direction = ComplexF.fromAngle(AngleF.fromDegrees(150f))
                                .toVector2F()
                        ),
                        MutableRay(
                            origin = Vector2F(-2f, 5f),
                            direction = ComplexF.fromAngle(AngleF.fromDegrees(300f))
                                .toVector2F()
                        ),
                        false
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(1f, 4f),
                            direction = ComplexF.fromAngle(AngleF.fromDegrees(150f))
                                .toVector2F()
                        ),
                        MutableRay(
                            origin = Vector2F(-2f, 5f),
                            direction = ComplexF.fromAngle(AngleF.fromDegrees(340f))
                                .toVector2F()
                        ),
                        false
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(1f, 4f),
                            direction = ComplexF.fromAngle(AngleF.fromDegrees(150f))
                                .toVector2F()
                        ),
                        MutableRay(
                            origin = Vector2F(-2f, 5f),
                            direction = ComplexF.fromAngle(AngleF.fromDegrees(342f))
                                .toVector2F()
                        ),
                        true
                    ),
                )
                val result = listOf(
                    identicalRaysArgs,
                    sameDirectionRaysArgs,
                    oppositeDirectionRaysArgs,
                    allDirectionRayArgs
                ).flatten()

                return result + result.withRayArgsSwapped()
            }

            val mutableRayArgs = createMutableRayArgs()
            val defaultRayArgs = mutableRayArgs.mapRaysToDefaultRays()

            return listOf(
                mutableRayArgs,
                defaultRayArgs
            ).flatten()
        }

        @JvmStatic
        fun intersectsRectangleArgs(): List<Arguments> {
            val mutableRayMutableRectangleArgs = listOf(
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(2f, 4f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(135f)).toVector2F()
                    ),
                    MutableRectangle(
                        center = Vector2F(-2f, 4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        width = 4f,
                        height = 2f
                    ),
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(2f, 2f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(135f)).toVector2F()
                    ),
                    MutableRectangle(
                        center = Vector2F(-2f, 4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        width = 4f,
                        height = 2f
                    ),
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(2f, -2f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(135f)).toVector2F()
                    ),
                    MutableRectangle(
                        center = Vector2F(-2f, 4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        width = 4f,
                        height = 2f
                    ),
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(0f, -2f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(135f)).toVector2F()
                    ),
                    MutableRectangle(
                        center = Vector2F(-2f, 4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        width = 4f,
                        height = 2f
                    ),
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-4f, 0f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(45f)).toVector2F()
                    ),
                    MutableRectangle(
                        center = Vector2F(-2f, 4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        width = 4f,
                        height = 2f
                    ),
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-5f, 0f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(45f)).toVector2F()
                    ),
                    MutableRectangle(
                        center = Vector2F(-2f, 4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        width = 4f,
                        height = 2f
                    ),
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-6f, 1f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(45f)).toVector2F()
                    ),
                    MutableRectangle(
                        center = Vector2F(-2f, 4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        width = 4f,
                        height = 2f
                    ),
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-6f, 2f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(45f)).toVector2F()
                    ),
                    MutableRectangle(
                        center = Vector2F(-2f, 4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        width = 4f,
                        height = 2f
                    ),
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-2f, 8f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(-45f)).toVector2F()
                    ),
                    MutableRectangle(
                        center = Vector2F(-2f, 4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        width = 4f,
                        height = 2f
                    ),
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-4f, 8f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(-45f)).toVector2F()
                    ),
                    MutableRectangle(
                        center = Vector2F(-2f, 4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        width = 4f,
                        height = 2f
                    ),
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-6f, 6f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(-45f)).toVector2F()
                    ),
                    MutableRectangle(
                        center = Vector2F(-2f, 4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        width = 4f,
                        height = 2f
                    ),
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-6f, 4f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(-45f)).toVector2F()
                    ),
                    MutableRectangle(
                        center = Vector2F(-2f, 4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        width = 4f,
                        height = 2f
                    ),
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(2f, 6f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(-135f)).toVector2F()
                    ),
                    MutableRectangle(
                        center = Vector2F(-2f, 4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        width = 4f,
                        height = 2f
                    ),
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(2f, 7f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(-135f)).toVector2F()
                    ),
                    MutableRectangle(
                        center = Vector2F(-2f, 4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        width = 4f,
                        height = 2f
                    ),
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(1f, 8f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(-135f)).toVector2F()
                    ),
                    MutableRectangle(
                        center = Vector2F(-2f, 4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        width = 4f,
                        height = 2f
                    ),
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(2f, 10f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(-135f)).toVector2F()
                    ),
                    MutableRectangle(
                        center = Vector2F(-2f, 4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        width = 4f,
                        height = 2f
                    ),
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(11f, 8f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(-172f)).toVector2F()
                    ),
                    MutableRectangle(
                        center = Vector2F(-2f, 4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        width = 4f,
                        height = 2f
                    ),
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(11f, 8f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(-170f)).toVector2F()
                    ),
                    MutableRectangle(
                        center = Vector2F(-2f, 4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        width = 4f,
                        height = 2f
                    ),
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-4f, -7f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(70f)).toVector2F()
                    ),
                    MutableRectangle(
                        center = Vector2F(-2f, 4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        width = 4f,
                        height = 2f
                    ),
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-4f, -7f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(72f)).toVector2F()
                    ),
                    MutableRectangle(
                        center = Vector2F(-2f, 4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        width = 4f,
                        height = 2f
                    ),
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-1.5f, 3.5f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(86f)).toVector2F()
                    ),
                    MutableRectangle(
                        center = Vector2F(-2f, 4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        width = 4f,
                        height = 2f
                    ),
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-2.75f, 4f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(-173f)).toVector2F()
                    ),
                    MutableRectangle(
                        center = Vector2F(-2f, 4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        width = 4f,
                        height = 2f
                    ),
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-2.25f, 3f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(-66f)).toVector2F()
                    ),
                    MutableRectangle(
                        center = Vector2F(-2f, 4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        width = 4f,
                        height = 2f
                    ),
                    true
                ),
            )
            val defaultRayMutableRectangleArgs = mutableRayMutableRectangleArgs
                .mapRaysToDefaultRays()
            val mutableRayDefaultRectangleArgs = mutableRayMutableRectangleArgs
                .mapRectanglesToDefaultRectangles()
            val defaultRayDefaultRectangleArgs = defaultRayMutableRectangleArgs
                .mapRectanglesToDefaultRectangles()

            return listOf(
                mutableRayMutableRectangleArgs,
                defaultRayMutableRectangleArgs,
                mutableRayDefaultRectangleArgs,
                defaultRayDefaultRectangleArgs
            ).flatten()
        }

        @JvmStatic
        fun intersectsRoundedRectangleArgs(): List<Arguments> {
            val mutableRayMutableRoundedRectangleArgs = listOf(
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-4.0f, -9.2f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(29.54f))
                            .toVector2F()
                    ),
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-4.0f, -9.0f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(29.54f))
                            .toVector2F()
                    ),
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-7.75f, -2.0f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(29.74f))
                            .toVector2F()
                    ),
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-7.4f, -2.0f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(29.74f))
                            .toVector2F()
                    ),
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-2.75f, -9.0f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(119.78f))
                            .toVector2F()
                    ),
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-2.25f, -9.0f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(120.26f))
                            .toVector2F()
                    ),
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(1.9f, -8.0f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(120.49f))
                            .toVector2F()
                    ),
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(1.5f, -8.0f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(120.26f))
                            .toVector2F()
                    ),
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-4.0f, -7.75f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(-14.04f))
                            .toVector2F()
                    ),
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-4.0f, -7.5f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(-14.93f))
                            .toVector2F()
                    ),
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(0.15f, -8.0f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(80.33f))
                            .toVector2F()
                    ),
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-0.1f, -8.0f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(79.92f))
                            .toVector2F()
                    ),
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-2.0f, -0.25f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(162.12f))
                            .toVector2F()
                    ),
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-2.0f, -0.45f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(161.29f))
                            .toVector2F()
                    ),
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-6.0f, -0.5f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(-109.18f))
                            .toVector2F()
                    ),
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-5.8f, -0.6f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(-109.18f))
                            .toVector2F()
                    ),
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(2.0f, -5.8f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(-150.46f))
                            .toVector2F()
                    ),
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(2.0f, -5.6f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(-150.46f))
                            .toVector2F()
                    ),
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-2.5f, 1.0f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(-150.26f))
                            .toVector2F()
                    ),
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-2.5f, 0.8f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(-150.26f))
                            .toVector2F()
                    ),
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-7.9f, 0.0f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(-60.22f))
                            .toVector2F()
                    ),
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-7.5f, 0.0f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(-59.74f))
                            .toVector2F()
                    ),
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-3.4f, 1.0f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(-59.51f))
                            .toVector2F()
                    ),
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-3.75f, 1.0f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(-59.74f))
                            .toVector2F()
                    ),
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-1.0f, -8.5f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(165.96f))
                            .toVector2F()
                    ),
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-1.0f, -8.3f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(165.07f))
                            .toVector2F()
                    ),
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(0.9f, -3.6f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(-99.67f))
                            .toVector2F()
                    ),
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(0.7f, -3.5f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(-100.08f))
                            .toVector2F()
                    ),
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-5.1f, 0.75f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(-17.88f))
                            .toVector2F()
                    ),
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-5.1f, 0.6f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(-18.71f))
                            .toVector2F()
                    ),
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-6.8f, -2.8f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(70.82f))
                            .toVector2F()
                    ),
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-6.6f, -2.9f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(70.82f))
                            .toVector2F()
                    ),
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-3.3f, -0.6f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(72.12f))
                            .toVector2F()
                    ),
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-5.0f, -3.0f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(-116.57f))
                            .toVector2F()
                    ),
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-2.8f, -4.3f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(-22.99f))
                            .toVector2F()
                    ),
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-1.3f, -7.0f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(-50.91f))
                            .toVector2F()
                    ),
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    true
                ),
            )
            val defaultRayMutableRoundedRectangleArgs = mutableRayMutableRoundedRectangleArgs
                .mapRaysToDefaultRays()
            val mutableRayDefaultRoundedRectangleArgs = mutableRayMutableRoundedRectangleArgs
                .mapRoundedRectanglesToDefaultRoundedRectangles()
            val defaultRayDefaultRoundedRectangleArgs = defaultRayMutableRoundedRectangleArgs
                .mapRoundedRectanglesToDefaultRoundedRectangles()

            return listOf(
                mutableRayMutableRoundedRectangleArgs,
                defaultRayMutableRoundedRectangleArgs,
                mutableRayDefaultRoundedRectangleArgs,
                defaultRayDefaultRoundedRectangleArgs
            ).flatten()
        }

        @JvmStatic
        fun intersectsSquareArgs(): List<Arguments> {
            val mutableRayMutableSquareArgs = listOf(
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(7.5f, -5f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(45f)).toVector2F()
                    ),
                    MutableSquare(
                        center = Vector2F(8f, -2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-135f)),
                        sideLength = 3f
                    ),
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(7f, -5f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(45f)).toVector2F()
                    ),
                    MutableSquare(
                        center = Vector2F(8f, -2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-135f)),
                        sideLength = 3f
                    ),
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(5f, -3f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(45f)).toVector2F()
                    ),
                    MutableSquare(
                        center = Vector2F(8f, -2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-135f)),
                        sideLength = 3f
                    ),
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(4.5f, -3f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(45f)).toVector2F()
                    ),
                    MutableSquare(
                        center = Vector2F(8f, -2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-135f)),
                        sideLength = 3f
                    ),
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(8.5f, -5f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(135f)).toVector2F()
                    ),
                    MutableSquare(
                        center = Vector2F(8f, -2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-135f)),
                        sideLength = 3f
                    ),
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(9f, -5f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(135f)).toVector2F()
                    ),
                    MutableSquare(
                        center = Vector2F(8f, -2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-135f)),
                        sideLength = 3f
                    ),
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(11f, -3f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(135f)).toVector2F()
                    ),
                    MutableSquare(
                        center = Vector2F(8f, -2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-135f)),
                        sideLength = 3f
                    ),
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(11.5f, -3f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(135f)).toVector2F()
                    ),
                    MutableSquare(
                        center = Vector2F(8f, -2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-135f)),
                        sideLength = 3f
                    ),
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(13f, 0.5f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(-135f)).toVector2F()
                    ),
                    MutableSquare(
                        center = Vector2F(8f, -2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-135f)),
                        sideLength = 3f
                    ),
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(12.5f, 0.5f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(-135f)).toVector2F()
                    ),
                    MutableSquare(
                        center = Vector2F(8f, -2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-135f)),
                        sideLength = 3f
                    ),
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(10f, 2f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(-135f)).toVector2F()
                    ),
                    MutableSquare(
                        center = Vector2F(8f, -2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-135f)),
                        sideLength = 3f
                    ),
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(9.5f, 2f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(-135f)).toVector2F()
                    ),
                    MutableSquare(
                        center = Vector2F(8f, -2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-135f)),
                        sideLength = 3f
                    ),
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(4.5f, -1f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(-45f)).toVector2F()
                    ),
                    MutableSquare(
                        center = Vector2F(8f, -2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-135f)),
                        sideLength = 3f
                    ),
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(5f, -1f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(-45f)).toVector2F()
                    ),
                    MutableSquare(
                        center = Vector2F(8f, -2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-135f)),
                        sideLength = 3f
                    ),
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(7f, 1f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(-45f)).toVector2F()
                    ),
                    MutableSquare(
                        center = Vector2F(8f, -2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-135f)),
                        sideLength = 3f
                    ),
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(7.5f, 1f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(-45f)).toVector2F()
                    ),
                    MutableSquare(
                        center = Vector2F(8f, -2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-135f)),
                        sideLength = 3f
                    ),
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(2f, -4f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(-1.5f)).toVector2F()
                    ),
                    MutableSquare(
                        center = Vector2F(8f, -2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-135f)),
                        sideLength = 3f
                    ),
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(2f, -4f),
                        direction = ComplexF.ONE.toVector2F()
                    ),
                    MutableSquare(
                        center = Vector2F(8f, -2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-135f)),
                        sideLength = 3f
                    ),
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(14f, -5f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(139f)).toVector2F()
                    ),
                    MutableSquare(
                        center = Vector2F(8f, -2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-135f)),
                        sideLength = 3f
                    ),
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(14f, -5f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(141f)).toVector2F()
                    ),
                    MutableSquare(
                        center = Vector2F(8f, -2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-135f)),
                        sideLength = 3f
                    ),
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(8.5f, -1.5f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(6f)).toVector2F()
                    ),
                    MutableSquare(
                        center = Vector2F(8f, -2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-135f)),
                        sideLength = 3f
                    ),
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(7f, -2f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(117f)).toVector2F()
                    ),
                    MutableSquare(
                        center = Vector2F(8f, -2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-135f)),
                        sideLength = 3f
                    ),
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(8f, -3f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(-135f)).toVector2F()
                    ),
                    MutableSquare(
                        center = Vector2F(8f, -2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-135f)),
                        sideLength = 3f
                    ),
                    true
                ),
            )
            val defaultRayMutableSquareArgs = mutableRayMutableSquareArgs
                .mapRaysToDefaultRays()
            val mutableRayDefaultSquareArgs = mutableRayMutableSquareArgs
                .mapSquaresToDefaultSquares()
            val defaultRayDefaultSquareArgs = defaultRayMutableSquareArgs
                .mapSquaresToDefaultSquares()

            return listOf(
                mutableRayMutableSquareArgs,
                defaultRayMutableSquareArgs,
                mutableRayDefaultSquareArgs,
                defaultRayDefaultSquareArgs
            ).flatten()
        }

        @JvmStatic
        fun intersectsTriangleArgs(): List<Arguments> {
            val mutableRayMutableTriangleArgs = listOf(
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(3.4f, -2f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(108.43f))
                            .toVector2F()
                    ),
                    MutableTriangle(
                        Vector2F(-4f, 2f), Vector2F(2f, 2f), Vector2F(1f, 5f)
                    ),
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(3.2f, -2f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(108.43f))
                            .toVector2F()
                    ),
                    MutableTriangle(
                        Vector2F(-4f, 2f), Vector2F(2f, 2f), Vector2F(1f, 5f)
                    ),
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-8f, 1.9f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(0f)).toVector2F()
                    ),
                    MutableTriangle(
                        Vector2F(-4f, 2f), Vector2F(2f, 2f), Vector2F(1f, 5f)
                    ),
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-8f, 2.1f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(0f)).toVector2F()
                    ),
                    MutableTriangle(
                        Vector2F(-4f, 2f), Vector2F(2f, 2f), Vector2F(1f, 5f)
                    ),
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-7.5f, 0f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(30.96f))
                            .toVector2F()
                    ),
                    MutableTriangle(
                        Vector2F(-4f, 2f), Vector2F(2f, 2f), Vector2F(1f, 5f)
                    ),
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-7.2f, 0f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(30.96f))
                            .toVector2F()
                    ),
                    MutableTriangle(
                        Vector2F(-4f, 2f), Vector2F(2f, 2f), Vector2F(1f, 5f)
                    ),
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(0.4f, 7f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(-71.57f))
                            .toVector2F()
                    ),
                    MutableTriangle(
                        Vector2F(-4f, 2f), Vector2F(2f, 2f), Vector2F(1f, 5f)
                    ),
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(0.2f, 7f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(-71.57f))
                            .toVector2F()
                    ),
                    MutableTriangle(
                        Vector2F(-4f, 2f), Vector2F(2f, 2f), Vector2F(1f, 5f)
                    ),
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(4f, 1.9f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(180f)).toVector2F()
                    ),
                    MutableTriangle(
                        Vector2F(-4f, 2f), Vector2F(2f, 2f), Vector2F(1f, 5f)
                    ),
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(4f, 2.1f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(180f)).toVector2F()
                    ),
                    MutableTriangle(
                        Vector2F(-4f, 2f), Vector2F(2f, 2f), Vector2F(1f, 5f)
                    ),
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(2.5f, 6f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(-149.04f))
                            .toVector2F()
                    ),
                    MutableTriangle(
                        Vector2F(-4f, 2f), Vector2F(2f, 2f), Vector2F(1f, 5f)
                    ),
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(2.8f, 6f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(-149.04f))
                            .toVector2F()
                    ),
                    MutableTriangle(
                        Vector2F(-4f, 2f), Vector2F(2f, 2f), Vector2F(1f, 5f)
                    ),
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(5f, 1f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(132.88f))
                            .toVector2F()
                    ),
                    MutableTriangle(
                        Vector2F(-4f, 2f), Vector2F(2f, 2f), Vector2F(1f, 5f)
                    ),
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(5f, 1f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(136.97f))
                            .toVector2F()
                    ),
                    MutableTriangle(
                        Vector2F(-4f, 2f), Vector2F(2f, 2f), Vector2F(1f, 5f)
                    ),
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-5f, -1f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(21.8f))
                            .toVector2F()
                    ),
                    MutableTriangle(
                        Vector2F(-4f, 2f), Vector2F(2f, 2f), Vector2F(1f, 5f)
                    ),
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-5f, -1f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(24.23f))
                            .toVector2F()
                    ),
                    MutableTriangle(
                        Vector2F(-4f, 2f), Vector2F(2f, 2f), Vector2F(1f, 5f)
                    ),
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(0.5f, 3.5f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(28.3f))
                            .toVector2F()
                    ),
                    MutableTriangle(
                        Vector2F(-4f, 2f), Vector2F(2f, 2f), Vector2F(1f, 5f)
                    ),
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-1f, 3f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(149.04f))
                            .toVector2F()
                    ),
                    MutableTriangle(
                        Vector2F(-4f, 2f), Vector2F(2f, 2f), Vector2F(1f, 5f)
                    ),
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(1f, 2.5f),
                        direction = ComplexF.fromAngle(AngleF.fromDegrees(-102.53f))
                            .toVector2F()
                    ),
                    MutableTriangle(
                        Vector2F(-4f, 2f), Vector2F(2f, 2f), Vector2F(1f, 5f)
                    ),
                    true
                ),
            )
            val defaultRayMutableTriangleArgs = mutableRayMutableTriangleArgs
                .mapRaysToDefaultRays()
            val mutableRayDefaultTriangleArgs = mutableRayMutableTriangleArgs
                .mapTrianglesToDefaultTriangles()
            val defaultRayDefaultTriangleArgs = defaultRayMutableTriangleArgs
                .mapTrianglesToDefaultTriangles()

            return listOf(
                mutableRayMutableTriangleArgs,
                defaultRayMutableTriangleArgs,
                mutableRayDefaultTriangleArgs,
                defaultRayDefaultTriangleArgs
            ).flatten()
        }

        @JvmStatic
        fun containsVector2FArgs(): List<Arguments> {
            val rayA = MutableRay(Vector2F(-2f, 5f), Vector2F(1f, 0f))
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
            val rayB = MutableRay(Vector2F(3f, 2f), Vector2F(0f, -1f))
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
            val rayC = MutableRay(
                Vector2F(-2f, -1f), Vector2F(-0.4472136f, 0.8944272f)
            )
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
            val mutableRayArgs = listOf(
                rayAArgs,
                rayBArgs,
                rayCArgs
            ).flatten()
            val defaultRayArgs = mutableRayArgs.mapRaysToDefaultRays()

            return listOf(
                mutableRayArgs,
                defaultRayArgs
            ).flatten()
        }

        @JvmStatic
        fun copyArgs(): List<Arguments> {
            val mutableRayArgs = setArgs()
            val defaultRayArgs = mutableRayArgs.mapRaysToDefaultRays()

            return listOf(
                mutableRayArgs,
                defaultRayArgs
            ).flatten()
        }

        @JvmStatic
        fun equalsAnyArgs(): List<Arguments> = equalsMutableLineSegmentArgs() + listOf(
            Arguments.of(
                MutableRay(Vector2F(-2f, 5f), Vector2F(1f, 0f)),
                null,
                false
            ),
            Arguments.of(
                MutableRay(Vector2F(-2f, 5f), Vector2F(1f, 0f)),
                DefaultRay(Vector2F(-2f, 5f), Vector2F(1f, 0f)),
                true
            ),
            Arguments.of(
                MutableRay(Vector2F(-2f, 5f), Vector2F(1f, 0f)),
                DefaultRay(Vector2F(-2f, 5f), Vector2F(1.1f, 0f)),
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
        fun componentsArgs(): List<Arguments> {
            val mutableRayArgs = listOf(
                Arguments.of(
                    MutableRay(Vector2F(-2f, 5f), Vector2F(1f, 0f)),
                    Wrapper(Vector2F(-2f, 5f)),
                    Wrapper(Vector2F(1f, 0f))
                ),
                Arguments.of(
                    MutableRay(Vector2F(-4f, 3f), Vector2F(1f, -1f).normalized),
                    Wrapper(Vector2F(-4f, 3f)),
                    Wrapper(Vector2F(1f, -1f).normalized)
                ),
            )
            val defaultRayArgs = mutableRayArgs.mapRaysToDefaultRays()

            return listOf(
                mutableRayArgs,
                defaultRayArgs
            ).flatten()
        }
    }
}