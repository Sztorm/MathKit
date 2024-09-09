package com.sztorm.lowallocmath.euclidean2d

import com.sztorm.lowallocmath.AngleF
import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F
import com.sztorm.lowallocmath.euclidean2d.RayTests.Companion.mapRaysToDefaultRays
import com.sztorm.lowallocmath.euclidean2d.utils.DefaultLineSegment
import com.sztorm.lowallocmath.euclidean2d.utils.assertImmutabilityOf
import com.sztorm.lowallocmath.isApproximately
import com.sztorm.lowallocmath.utils.Wrapper
import com.sztorm.lowallocmath.utils.assertApproximation
import com.sztorm.lowallocmath.utils.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

class LineSegmentTests {
    @ParameterizedTest
    @MethodSource("constructorVector2FComplexFFloatArgs")
    fun constructorCreatesCorrectLineSegment(
        center: Wrapper<Vector2F>, orientation: Wrapper<ComplexF>, length: Float,
    ) {
        val mutableLineSegment = MutableLineSegment(center.value, orientation.value, length)
        val lineSegment = LineSegment(center.value, orientation.value, length)

        assertEquals(center.value, mutableLineSegment.center)
        assertEquals(orientation.value, mutableLineSegment.orientation)
        assertEquals(length, mutableLineSegment.length)

        assertEquals(center.value, lineSegment.center)
        assertEquals(orientation.value, lineSegment.orientation)
        assertEquals(length, lineSegment.length)

        assertApproximation(mutableLineSegment.pointA, lineSegment.pointA)
        assertApproximation(mutableLineSegment.pointB, lineSegment.pointB)
    }

    @ParameterizedTest
    @MethodSource("constructorVector2Fx2Args")
    fun constructorCreatesCorrectLineSegment(
        pointA: Wrapper<Vector2F>, pointB: Wrapper<Vector2F>
    ) {
        val mutableLineSegment = MutableLineSegment(pointA.value, pointB.value)
        val lineSegment = LineSegment(pointA.value, pointB.value)

        assertApproximation(pointA.value, mutableLineSegment.pointA)
        assertApproximation(pointB.value, mutableLineSegment.pointB)

        assertApproximation(pointA.value, lineSegment.pointA)
        assertApproximation(pointB.value, lineSegment.pointB)

        assertApproximation(mutableLineSegment.center, lineSegment.center)
        assertApproximation(mutableLineSegment.orientation, lineSegment.orientation)
        assertApproximation(mutableLineSegment.length, lineSegment.length)
    }

    @ParameterizedTest
    @MethodSource("constructorThrowsExceptionArgs")
    fun constructorThrowsCorrectException(
        center: Wrapper<Vector2F>,
        orientation: Wrapper<ComplexF>,
        length: Float,
        expectedExceptionClass: Class<Throwable>
    ) {
        assertThrows(expectedExceptionClass) {
            MutableLineSegment(center.value, orientation.value, length)
        }
        assertThrows(expectedExceptionClass) {
            LineSegment(center.value, orientation.value, length)
        }
    }

    @ParameterizedTest
    @MethodSource("centerArgs")
    fun centerReturnsCorrectValue(lineSegment: LineSegment, expected: Wrapper<Vector2F>) =
        assertImmutabilityOf(lineSegment) {
            assertApproximation(expected.value, lineSegment.center)
        }

    @ParameterizedTest
    @MethodSource("orientationArgs")
    fun orientationReturnsCorrectValue(lineSegment: LineSegment, expected: Wrapper<ComplexF>) =
        assertImmutabilityOf(lineSegment) {
            assertApproximation(expected.value, lineSegment.orientation)
        }

    @ParameterizedTest
    @MethodSource("lengthArgs")
    fun lengthReturnsCorrectValue(lineSegment: LineSegment, expected: Float) =
        assertImmutabilityOf(lineSegment) {
            assertApproximation(expected, lineSegment.length)
        }

    @ParameterizedTest
    @MethodSource("pointsArgs")
    fun pointsReturnCorrectValues(
        lineSegment: LineSegment,
        expectedPointA: Wrapper<Vector2F>,
        expectedPointB: Wrapper<Vector2F>
    ) = assertImmutabilityOf(lineSegment) {
        assertApproximation(expectedPointA.value, lineSegment.pointA)
        assertApproximation(expectedPointB.value, lineSegment.pointB)
    }

    @ParameterizedTest
    @MethodSource("positionArgs")
    fun positionReturnsCorrectValue(lineSegment: LineSegment, expected: Wrapper<Vector2F>) =
        assertImmutabilityOf(lineSegment) {
            assertApproximation(expected.value, lineSegment.position)
        }

    @ParameterizedTest
    @MethodSource("calibrateArgs")
    fun calibrateMutatesLineSegmentCorrectly(
        lineSegment: MutableLineSegment, expected: MutableLineSegment
    ) = assertApproximation(expected, lineSegment.apply { calibrate() })

    @ParameterizedTest
    @MethodSource("setArgs")
    fun setMutatesLineSegmentCorrectly(
        lineSegment: MutableLineSegment,
        center: Wrapper<Vector2F>,
        orientation: Wrapper<ComplexF>,
        length: Float,
        expected: MutableLineSegment
    ) = assertEquals(expected, lineSegment.apply { set(center.value, orientation.value, length) })

    @ParameterizedTest
    @MethodSource("interpolatedArgs")
    fun interpolatedReturnsCorrectValue(
        lineSegment: LineSegment, to: LineSegment, by: Float, expected: LineSegment
    ) = assertImmutabilityOf(lineSegment) {
        assertImmutabilityOf(to) {
            assertApproximation(expected, lineSegment.interpolated(to, by))
        }
    }

    @ParameterizedTest
    @MethodSource("interpolateArgs")
    fun interpolateMutatesLineSegmentCorrectly(
        lineSegment: MutableLineSegment,
        from: LineSegment,
        to: LineSegment,
        by: Float,
        expected: MutableLineSegment
    ) = assertImmutabilityOf(from) {
        assertImmutabilityOf(to) {
            assertApproximation(expected, lineSegment.apply { interpolate(from, to, by) })
        }
    }

    @ParameterizedTest
    @MethodSource("closestPointToArgs")
    fun closestPointToReturnsCorrectValue(
        lineSegment: LineSegment, point: Wrapper<Vector2F>, expected: Wrapper<Vector2F>
    ) = assertImmutabilityOf(lineSegment) {
        assertApproximation(expected.value, lineSegment.closestPointTo(point.value))
    }

    @ParameterizedTest
    @MethodSource("intersectsRayArgs")
    fun intersectsReturnsCorrectValue(lineSegment: LineSegment, ray: Ray, expected: Boolean) =
        assertImmutabilityOf(lineSegment) {
            assertImmutabilityOf(ray) {
                assertEquals(expected, lineSegment.intersects(ray))
            }
        }

    @ParameterizedTest
    @MethodSource("containsVector2FArgs")
    fun containsReturnsCorrectValue(
        lineSegment: LineSegment, point: Wrapper<Vector2F>, expected: Boolean
    ) = assertImmutabilityOf(lineSegment) {
        assertEquals(expected, lineSegment.contains(point.value))
    }

    @ParameterizedTest
    @MethodSource("copyArgs")
    fun copyReturnsCorrectValue(
        lineSegment: LineSegment,
        center: Wrapper<Vector2F>,
        orientation: Wrapper<ComplexF>,
        length: Float,
        expected: LineSegment
    ) = assertEquals(expected, lineSegment.copy(center.value, orientation.value, length))

    @ParameterizedTest
    @MethodSource("equalsAnyArgs")
    fun equalsReturnsCorrectValue(
        lineSegment: MutableLineSegment, other: Any?, expected: Boolean
    ) = assertImmutabilityOf(lineSegment) {
        assertEquals(expected, lineSegment == other)
    }

    @ParameterizedTest
    @MethodSource("equalsMutableLineSegmentArgs")
    fun equalsReturnsCorrectValue(
        lineSegment: MutableLineSegment, other: MutableLineSegment, expected: Boolean
    ) = assertImmutabilityOf(lineSegment) {
        assertImmutabilityOf(other) {
            assertEquals(expected, lineSegment.equals(other))
        }
    }

    @ParameterizedTest
    @MethodSource("hashCodeArgs")
    fun hashCodeReturnsCorrectValue(lineSegment: MutableLineSegment, other: MutableLineSegment) =
        assertImmutabilityOf(lineSegment) {
            assertImmutabilityOf(other) {
                assertEquals(lineSegment.hashCode(), other.hashCode())
            }
        }

    @ParameterizedTest
    @MethodSource("toStringArgs")
    fun toStringReturnsCorrectValue(lineSegment: MutableLineSegment, expected: String) =
        assertImmutabilityOf(lineSegment) {
            assertEquals(expected, lineSegment.toString())
        }

    @ParameterizedTest
    @MethodSource("componentsArgs")
    fun componentsReturnCorrectValues(
        lineSegment: LineSegment,
        expectedComponent1: Wrapper<Vector2F>,
        expectedComponent2: Wrapper<ComplexF>,
        expectedComponent3: Float,
    ) = assertImmutabilityOf(lineSegment) {
        val (
            actualComponent1: Vector2F,
            actualComponent2: ComplexF,
            actualComponent3: Float
        ) = lineSegment

        assertEquals(expectedComponent1.value, actualComponent1)
        assertEquals(expectedComponent2.value, actualComponent2)
        assertEquals(expectedComponent3, actualComponent3)
    }

    companion object {
        @JvmStatic
        fun areApproximatelyEqual(
            a: LineSegment, b: LineSegment, tolerance: Float = 0.00001f
        ): Boolean = a.center.isApproximately(b.center, tolerance) and
                a.orientation.isApproximately(b.orientation, tolerance) and
                a.length.isApproximately(b.length, tolerance) and
                a.pointA.isApproximately(b.pointA, tolerance) and
                a.pointB.isApproximately(b.pointB, tolerance)

        @JvmStatic
        fun areEqual(a: LineSegment, b: LineSegment): Boolean =
            (a.center == b.center) and
                    (a.orientation == b.orientation) and
                    (a.length == b.length) and
                    (a.pointA == b.pointA) and
                    (a.pointB == b.pointB)

        @JvmStatic
        fun clone(lineSegment: LineSegment) = lineSegment.copy()

        @JvmStatic
        fun List<Arguments>.mapLineSegmentsToDefaultLineSegments() = map { args ->
            val argArray = args.get().map {
                if (it is LineSegment) DefaultLineSegment(it.center, it.orientation, it.length)
                else it
            }.toTypedArray()

            Arguments.of(*argArray)
        }

        @JvmStatic
        fun constructorVector2FComplexFFloatArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2F(0f, 5f)),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(180f))),
                4f
            ),
            Arguments.of(
                Wrapper(Vector2F(3f, -0.5f)),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(90f))),
                5f
            ),
            Arguments.of(
                Wrapper(Vector2F(-3f, 1f)),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(116.56505f))),
                4.472136f
            ),
        )

        @JvmStatic
        fun constructorVector2Fx2Args(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2F(-2f, 5f)),
                Wrapper(Vector2F(2f, 5f))
            ),
            Arguments.of(
                Wrapper(Vector2F(3f, 2f)),
                Wrapper(Vector2F(3f, -3f))
            ),
            Arguments.of(
                Wrapper(Vector2F(-4f, 3f)),
                Wrapper(Vector2F(-2f, -1f))
            ),
        )

        @JvmStatic
        fun constructorThrowsExceptionArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2F.ZERO),
                Wrapper(ComplexF.ONE),
                -1f,
                IllegalArgumentException::class.java
            ),
            Arguments.of(
                Wrapper(Vector2F.ZERO),
                Wrapper(ComplexF.ONE),
                -0.1f,
                IllegalArgumentException::class.java
            ),
        )

        @JvmStatic
        fun centerArgs(): List<Arguments> {
            val mutableLineSegmentArgs = listOf(
                Arguments.of(
                    MutableLineSegment(
                        center = Vector2F(0f, 5f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(180f)),
                        length = 4f
                    ),
                    Wrapper(Vector2F(0f, 5f))
                ),
                Arguments.of(
                    MutableLineSegment(
                        center = Vector2F(3f, -0.5f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                        length = 5f
                    ),
                    Wrapper(Vector2F(3f, -0.5f))
                ),
                Arguments.of(
                    MutableLineSegment(
                        center = Vector2F(-3f, 1f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(116.56505f)),
                        length = 4.472136f
                    ),
                    Wrapper(Vector2F(-3f, 1f))
                ),
            )
            val defaultLineSegmentArgs = mutableLineSegmentArgs
                .mapLineSegmentsToDefaultLineSegments()

            return listOf(
                mutableLineSegmentArgs,
                defaultLineSegmentArgs
            ).flatten()
        }

        @JvmStatic
        fun orientationArgs(): List<Arguments> {
            val mutableLineSegmentArgs = listOf(
                Arguments.of(
                    MutableLineSegment(
                        center = Vector2F(0f, 5f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(180f)),
                        length = 4f
                    ),
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(180f)))
                ),
                Arguments.of(
                    MutableLineSegment(
                        center = Vector2F(3f, -0.5f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                        length = 5f
                    ),
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(90f)))
                ),
                Arguments.of(
                    MutableLineSegment(
                        center = Vector2F(-3f, 1f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(116.56505f)),
                        length = 4.472136f
                    ),
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(116.56505f)))
                ),
            )
            val defaultLineSegmentArgs = mutableLineSegmentArgs
                .mapLineSegmentsToDefaultLineSegments()

            return listOf(
                mutableLineSegmentArgs,
                defaultLineSegmentArgs
            ).flatten()
        }

        @JvmStatic
        fun lengthArgs(): List<Arguments> {
            val mutableLineSegmentArgs = listOf(
                Arguments.of(
                    MutableLineSegment(
                        center = Vector2F(0f, 5f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(180f)),
                        length = 4f
                    ),
                    4f,
                ),
                Arguments.of(
                    MutableLineSegment(
                        center = Vector2F(3f, -0.5f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                        length = 5f
                    ),
                    5f
                ),
                Arguments.of(
                    MutableLineSegment(
                        center = Vector2F(-3f, 1f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(116.56505f)),
                        length = 4.472136f
                    ),
                    4.472136f
                ),
            )
            val defaultLineSegmentArgs = mutableLineSegmentArgs
                .mapLineSegmentsToDefaultLineSegments()

            return listOf(
                mutableLineSegmentArgs,
                defaultLineSegmentArgs
            ).flatten()
        }

        @JvmStatic
        fun pointsArgs(): List<Arguments> {
            val mutableLineSegmentArgs = listOf(
                Arguments.of(
                    MutableLineSegment(
                        center = Vector2F(0f, 5f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(180f)),
                        length = 4f
                    ),
                    Wrapper(Vector2F(-2f, 5f)),
                    Wrapper(Vector2F(2f, 5f))
                ),
                Arguments.of(
                    MutableLineSegment(
                        center = Vector2F(3f, -0.5f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                        length = 5f
                    ),
                    Wrapper(Vector2F(3f, 2f)),
                    Wrapper(Vector2F(3f, -3f))
                ),
                Arguments.of(
                    MutableLineSegment(
                        center = Vector2F(-3f, 1f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(116.56505f)),
                        length = 4.472136f
                    ),
                    Wrapper(Vector2F(-4f, 3f)),
                    Wrapper(Vector2F(-2f, -1f))
                ),
            )
            val defaultLineSegmentArgs = mutableLineSegmentArgs
                .mapLineSegmentsToDefaultLineSegments()

            return listOf(
                mutableLineSegmentArgs,
                defaultLineSegmentArgs
            ).flatten()
        }

        @JvmStatic
        fun positionArgs(): List<Arguments> = centerArgs()

        @JvmStatic
        fun calibrateArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableLineSegment(
                    center = Vector2F(0f, 5f),
                    orientation = ComplexF.fromPolar(
                        magnitude = 5.8f, phase = AngleF.fromDegrees(180f).radians
                    ),
                    length = 4f
                ),
                MutableLineSegment(
                    center = Vector2F(0f, 5f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(180f)),
                    length = 4f
                )
            ),
            Arguments.of(
                MutableLineSegment(
                    center = Vector2F(0f, 5f),
                    orientation = ComplexF.fromPolar(
                        magnitude = 0.58f, phase = AngleF.fromDegrees(180f).radians
                    ),
                    length = 4f
                ),
                MutableLineSegment(
                    center = Vector2F(0f, 5f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(180f)),
                    length = 4f
                )
            ),
            Arguments.of(
                MutableLineSegment(
                    center = Vector2F(0f, 5f),
                    orientation = ComplexF.ZERO,
                    length = 4f
                ),
                MutableLineSegment(
                    center = Vector2F(0f, 5f),
                    orientation = ComplexF.ONE,
                    length = 4f
                )
            ),
        )

        @JvmStatic
        fun setArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableLineSegment(
                    center = Vector2F(0f, 5f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(180f)),
                    length = 4f
                ),
                Wrapper(Vector2F(0f, 5f)),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(180f))),
                4f,
                MutableLineSegment(
                    center = Vector2F(0f, 5f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(180f)),
                    length = 4f
                )
            ),
            Arguments.of(
                MutableLineSegment(
                    center = Vector2F(0f, 5f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(180f)),
                    length = 4f
                ),
                Wrapper(Vector2F(-2f, 2f)),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(90f))),
                6f,
                MutableLineSegment(
                    center = Vector2F(-2f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                    length = 6f
                )
            ),
            Arguments.of(
                MutableLineSegment(
                    center = Vector2F(0f, 5f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(180f)),
                    length = 4f
                ),
                Wrapper(Vector2F(-3f, 1f)),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(116.56505f))),
                4.472136f,
                MutableLineSegment(
                    center = Vector2F(-3f, 1f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(116.56505f)),
                    length = 4.472136f
                )
            ),
        )

        @JvmStatic
        fun interpolatedArgs(): List<Arguments> {
            val mutableLineSegmentArgs = interpolateArgs().map {
                Arguments.of(*it.get().drop(1).toTypedArray())
            }
            val defaultLineSegmentArgs =
                mutableLineSegmentArgs.mapLineSegmentsToDefaultLineSegments()

            return listOf(
                mutableLineSegmentArgs,
                defaultLineSegmentArgs
            ).flatten()
        }

        @JvmStatic
        fun interpolateArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableLineSegment(
                    center = Vector2F.ZERO,
                    orientation = ComplexF.ONE,
                    length = 1f
                ),
                MutableLineSegment(
                    center = Vector2F(0f, 5f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(180f)),
                    length = 4f
                ),
                MutableLineSegment(
                    center = Vector2F(0f, 5f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(180f)),
                    length = 4f
                ),
                0.5f,
                MutableLineSegment(
                    center = Vector2F(0f, 5f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(180f)),
                    length = 4f
                )
            ),
            Arguments.of(
                MutableLineSegment(
                    center = Vector2F.ZERO,
                    orientation = ComplexF.ONE,
                    length = 1f
                ),
                MutableLineSegment(
                    center = Vector2F(0f, 5f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(180f)),
                    length = 4f
                ),
                MutableLineSegment(
                    center = Vector2F(-3f, 1f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(116.56505f)),
                    length = 4.472136f
                ),
                0f,
                MutableLineSegment(
                    center = Vector2F(0f, 5f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(180f)),
                    length = 4f
                ),
            ),
            Arguments.of(
                MutableLineSegment(
                    center = Vector2F.ZERO,
                    orientation = ComplexF.ONE,
                    length = 1f
                ),
                MutableLineSegment(
                    center = Vector2F(0f, 5f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(180f)),
                    length = 4f
                ),
                MutableLineSegment(
                    center = Vector2F(-3f, 1f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(116.56505f)),
                    length = 4.472136f
                ),
                0.25f,
                MutableLineSegment(
                    center = Vector2F(-0.75f, 4f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(164.1413f)),
                    length = 4.118034f
                ),
            ),
            Arguments.of(
                MutableLineSegment(
                    center = Vector2F.ZERO,
                    orientation = ComplexF.ONE,
                    length = 1f
                ),
                MutableLineSegment(
                    center = Vector2F(0f, 5f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(180f)),
                    length = 4f
                ),
                MutableLineSegment(
                    center = Vector2F(-3f, 1f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(116.56505f)),
                    length = 4.472136f
                ),
                0.5f,
                MutableLineSegment(
                    center = Vector2F(-1.5f, 3f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(148.28253f)),
                    length = 4.236068f
                ),
            ),
            Arguments.of(
                MutableLineSegment(
                    center = Vector2F.ZERO,
                    orientation = ComplexF.ONE,
                    length = 1f
                ),
                MutableLineSegment(
                    center = Vector2F(0f, 5f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(180f)),
                    length = 4f
                ),
                MutableLineSegment(
                    center = Vector2F(-3f, 1f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(116.56505f)),
                    length = 4.472136f
                ),
                0.75f,
                MutableLineSegment(
                    center = Vector2F(-2.25f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(132.4238f)),
                    length = 4.354102f
                ),
            ),
            Arguments.of(
                MutableLineSegment(
                    center = Vector2F.ZERO,
                    orientation = ComplexF.ONE,
                    length = 1f
                ),
                MutableLineSegment(
                    center = Vector2F(0f, 5f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(180f)),
                    length = 4f
                ),
                MutableLineSegment(
                    center = Vector2F(-3f, 1f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(116.56505f)),
                    length = 4.472136f
                ),
                1f,
                MutableLineSegment(
                    center = Vector2F(-3f, 1f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(116.56505f)),
                    length = 4.472136f
                ),
            ),
        )

        @JvmStatic
        fun closestPointToArgs(): List<Arguments> {
            val lineSegmentA = MutableLineSegment(
                center = Vector2F(0f, 5f),
                orientation = ComplexF.fromAngle(AngleF.fromDegrees(180f)),
                length = 4f
            )
            val segmentAArgs = listOf(
                Arguments.of(
                    lineSegmentA,
                    Wrapper(Vector2F(-1.9f, 5f)),
                    Wrapper(Vector2F(-1.9f, 5f))
                ),
                Arguments.of(
                    lineSegmentA,
                    Wrapper(Vector2F(-1.9f, 5.1f)),
                    Wrapper(Vector2F(-1.9f, 5f))
                ),
                Arguments.of(
                    lineSegmentA,
                    Wrapper(Vector2F(-1.9f, 4.9f)),
                    Wrapper(Vector2F(-1.9f, 5f))
                ),
                Arguments.of(
                    lineSegmentA,
                    Wrapper(Vector2F(-2f, 5f)),
                    Wrapper(Vector2F(-2f, 5f))
                ),
                Arguments.of(
                    lineSegmentA,
                    Wrapper(Vector2F(-2f, 5.1f)),
                    Wrapper(Vector2F(-2f, 5f))
                ),
                Arguments.of(
                    lineSegmentA,
                    Wrapper(Vector2F(-2f, 4.9f)),
                    Wrapper(Vector2F(-2f, 5f))
                ),
                Arguments.of(
                    lineSegmentA,
                    Wrapper(Vector2F(-2.1f, 5f)),
                    Wrapper(Vector2F(-2f, 5f))
                ),
                Arguments.of(
                    lineSegmentA,
                    Wrapper(Vector2F(-2.1f, 5.1f)),
                    Wrapper(Vector2F(-2f, 5f))
                ),
                Arguments.of(
                    lineSegmentA,
                    Wrapper(Vector2F(-2.1f, 4.9f)),
                    Wrapper(Vector2F(-2f, 5f))
                ),
                Arguments.of(
                    lineSegmentA,
                    Wrapper(Vector2F(1.9f, 5f)),
                    Wrapper(Vector2F(1.9f, 5f))
                ),
                Arguments.of(
                    lineSegmentA,
                    Wrapper(Vector2F(1.9f, 5.1f)),
                    Wrapper(Vector2F(1.9f, 5f))
                ),
                Arguments.of(
                    lineSegmentA,
                    Wrapper(Vector2F(1.9f, 4.9f)),
                    Wrapper(Vector2F(1.9f, 5f))
                ),
                Arguments.of(
                    lineSegmentA,
                    Wrapper(Vector2F(2f, 5f)),
                    Wrapper(Vector2F(2f, 5f))
                ),
                Arguments.of(
                    lineSegmentA,
                    Wrapper(Vector2F(2f, 5.1f)),
                    Wrapper(Vector2F(2f, 5f))
                ),
                Arguments.of(
                    lineSegmentA,
                    Wrapper(Vector2F(2f, 4.9f)),
                    Wrapper(Vector2F(2f, 5f))
                ),
                Arguments.of(
                    lineSegmentA,
                    Wrapper(Vector2F(2.1f, 5f)),
                    Wrapper(Vector2F(2f, 5f))
                ),
                Arguments.of(
                    lineSegmentA,
                    Wrapper(Vector2F(2.1f, 5.1f)),
                    Wrapper(Vector2F(2f, 5f))
                ),
                Arguments.of(
                    lineSegmentA,
                    Wrapper(Vector2F(2.1f, 4.9f)),
                    Wrapper(Vector2F(2f, 5f))
                ),
                Arguments.of(
                    lineSegmentA,
                    Wrapper(Vector2F(0f, 5f)),
                    Wrapper(Vector2F(0f, 5f))
                ),
                Arguments.of(
                    lineSegmentA,
                    Wrapper(Vector2F(0f, 5.1f)),
                    Wrapper(Vector2F(0f, 5f))
                ),
                Arguments.of(
                    lineSegmentA,
                    Wrapper(Vector2F(0f, 4.9f)),
                    Wrapper(Vector2F(0f, 5f))
                ),
            )
            val lineSegmentB = MutableLineSegment(
                center = Vector2F(3f, -0.5f),
                orientation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                length = 5f
            )
            val segmentBArgs = listOf(
                Arguments.of(
                    lineSegmentB,
                    Wrapper(Vector2F(3f, 1.9f)),
                    Wrapper(Vector2F(3f, 1.9f))
                ),
                Arguments.of(
                    lineSegmentB,
                    Wrapper(Vector2F(2.9f, 1.9f)),
                    Wrapper(Vector2F(3f, 1.9f))
                ),
                Arguments.of(
                    lineSegmentB,
                    Wrapper(Vector2F(3.1f, 1.9f)),
                    Wrapper(Vector2F(3f, 1.9f))
                ),
                Arguments.of(
                    lineSegmentB,
                    Wrapper(Vector2F(3f, 2f)),
                    Wrapper(Vector2F(3f, 2f))
                ),
                Arguments.of(
                    lineSegmentB,
                    Wrapper(Vector2F(2.9f, 2f)),
                    Wrapper(Vector2F(3f, 2f))
                ),
                Arguments.of(
                    lineSegmentB,
                    Wrapper(Vector2F(3.1f, 2f)),
                    Wrapper(Vector2F(3f, 2f))
                ),
                Arguments.of(
                    lineSegmentB,
                    Wrapper(Vector2F(3f, 2.1f)),
                    Wrapper(Vector2F(3f, 2f))
                ),
                Arguments.of(
                    lineSegmentB,
                    Wrapper(Vector2F(2.9f, 2.1f)),
                    Wrapper(Vector2F(3f, 2f))
                ),
                Arguments.of(
                    lineSegmentB,
                    Wrapper(Vector2F(3.1f, 2.1f)),
                    Wrapper(Vector2F(3f, 2f))
                ),
                Arguments.of(
                    lineSegmentB,
                    Wrapper(Vector2F(3f, -2.9f)),
                    Wrapper(Vector2F(3f, -2.9f))
                ),
                Arguments.of(
                    lineSegmentB,
                    Wrapper(Vector2F(2.9f, -2.9f)),
                    Wrapper(Vector2F(3f, -2.9f))
                ),
                Arguments.of(
                    lineSegmentB,
                    Wrapper(Vector2F(3.1f, -2.9f)),
                    Wrapper(Vector2F(3f, -2.9f))
                ),
                Arguments.of(
                    lineSegmentB,
                    Wrapper(Vector2F(3f, -3f)),
                    Wrapper(Vector2F(3f, -3f))
                ),
                Arguments.of(
                    lineSegmentB,
                    Wrapper(Vector2F(2.9f, -3f)),
                    Wrapper(Vector2F(3f, -3f))
                ),
                Arguments.of(
                    lineSegmentB,
                    Wrapper(Vector2F(3.1f, -3f)),
                    Wrapper(Vector2F(3f, -3f))
                ),
                Arguments.of(
                    lineSegmentB,
                    Wrapper(Vector2F(3f, -3.1f)),
                    Wrapper(Vector2F(3f, -3f))
                ),
                Arguments.of(
                    lineSegmentB,
                    Wrapper(Vector2F(2.9f, -3.1f)),
                    Wrapper(Vector2F(3f, -3f))
                ),
                Arguments.of(
                    lineSegmentB,
                    Wrapper(Vector2F(3.1f, -3.1f)),
                    Wrapper(Vector2F(3f, -3f))
                ),
                Arguments.of(
                    lineSegmentB,
                    Wrapper(Vector2F(3f, -0.5f)),
                    Wrapper(Vector2F(3f, -0.5f))
                ),
                Arguments.of(
                    lineSegmentB,
                    Wrapper(Vector2F(2.9f, -0.5f)),
                    Wrapper(Vector2F(3f, -0.5f))
                ),
                Arguments.of(
                    lineSegmentB,
                    Wrapper(Vector2F(3.1f, -0.5f)),
                    Wrapper(Vector2F(3f, -0.5f))
                ),
            )
            val lineSegmentC = MutableLineSegment(
                center = Vector2F(-3f, 1f),
                orientation = ComplexF.fromAngle(AngleF.fromDegrees(116.56505f)),
                length = 4.472136f
            )
            val segmentCArgs = listOf(
                Arguments.of(
                    lineSegmentC,
                    Wrapper(Vector2F(-2.134164f, -0.95527864f)),
                    Wrapper(Vector2F(-2.0447214f, -0.91055727f))
                ),
                Arguments.of(
                    lineSegmentC,
                    Wrapper(Vector2F(-2.0447214f, -0.91055727f)),
                    Wrapper(Vector2F(-2.0447214f, -0.91055727f))
                ),
                Arguments.of(
                    lineSegmentC,
                    Wrapper(Vector2F(-1.9552786f, -0.8658359f)),
                    Wrapper(Vector2F(-2.0447214f, -0.91055727f))
                ),
                Arguments.of(
                    lineSegmentC,
                    Wrapper(Vector2F(-2.0894427f, -1.0447214f)),
                    Wrapper(Vector2F(-2f, -1f))
                ),
                Arguments.of(
                    lineSegmentC,
                    Wrapper(Vector2F(-2f, -1f)),
                    Wrapper(Vector2F(-2f, -1f))
                ),
                Arguments.of(
                    lineSegmentC,
                    Wrapper(Vector2F(-1.9105573f, -0.95527864f)),
                    Wrapper(Vector2F(-2f, -1f))
                ),
                Arguments.of(
                    lineSegmentC,
                    Wrapper(Vector2F(-2.0447214f, -1.1341641f)),
                    Wrapper(Vector2F(-2f, -1f))
                ),
                Arguments.of(
                    lineSegmentC,
                    Wrapper(Vector2F(-1.9552786f, -1.0894427f)),
                    Wrapper(Vector2F(-2f, -1f))
                ),
                Arguments.of(
                    lineSegmentC,
                    Wrapper(Vector2F(-1.8658359f, -1.0447214f)),
                    Wrapper(Vector2F(-2f, -1f))
                ),
                Arguments.of(
                    lineSegmentC,
                    Wrapper(Vector2F(-4.0447216f, 2.865836f)),
                    Wrapper(Vector2F(-3.9552786f, 2.9105573f))
                ),
                Arguments.of(
                    lineSegmentC,
                    Wrapper(Vector2F(-3.9552786f, 2.9105573f)),
                    Wrapper(Vector2F(-3.9552786f, 2.9105573f))
                ),
                Arguments.of(
                    lineSegmentC,
                    Wrapper(Vector2F(-3.865836f, 2.9552786f)),
                    Wrapper(Vector2F(-3.9552786f, 2.9105573f))
                ),
                Arguments.of(
                    lineSegmentC,
                    Wrapper(Vector2F(-4.0894427f, 2.9552786f)),
                    Wrapper(Vector2F(-4f, 3f))
                ),
                Arguments.of(
                    lineSegmentC,
                    Wrapper(Vector2F(-4f, 3f)),
                    Wrapper(Vector2F(-4f, 3f))
                ),
                Arguments.of(
                    lineSegmentC,
                    Wrapper(Vector2F(-3.9105573f, 3.0447214f)),
                    Wrapper(Vector2F(-4f, 3f))
                ),
                Arguments.of(
                    lineSegmentC,
                    Wrapper(Vector2F(-4.134164f, 3.0447214f)),
                    Wrapper(Vector2F(-4f, 3f))
                ),
                Arguments.of(
                    lineSegmentC,
                    Wrapper(Vector2F(-4.0447216f, 3.0894427f)),
                    Wrapper(Vector2F(-4f, 3f))
                ),
                Arguments.of(
                    lineSegmentC,
                    Wrapper(Vector2F(-3.9552786f, 3.134164f)),
                    Wrapper(Vector2F(-4f, 3f))
                ),
                Arguments.of(
                    lineSegmentC,
                    Wrapper(Vector2F(-3.0894427f, 0.95527864f)),
                    Wrapper(Vector2F(-3.0f, 1.0f))
                ),
                Arguments.of(
                    lineSegmentC,
                    Wrapper(Vector2F(-3.0f, 1.0f)),
                    Wrapper(Vector2F(-3.0f, 1.0f))
                ),
                Arguments.of(
                    lineSegmentC,
                    Wrapper(Vector2F(-2.9105573f, 1.0447214f)),
                    Wrapper(Vector2F(-3.0f, 1.0f))
                ),
            )
            val lineSegmentD = MutableLineSegment(
                center = Vector2F(4.0000005f, 4f),
                orientation = ComplexF.fromAngle(AngleF.fromDegrees(0f)),
                length = 0.000001f
            )
            val segmentDArgs = listOf(
                Arguments.of(
                    lineSegmentD,
                    Wrapper(Vector2F(3.9f, 4f)),
                    Wrapper(Vector2F(4f, 4f))
                ),
                Arguments.of(
                    lineSegmentD,
                    Wrapper(Vector2F(4f, 4f)),
                    Wrapper(Vector2F(4f, 4f))
                ),
                Arguments.of(
                    lineSegmentD,
                    Wrapper(Vector2F(4.1f, 4f)),
                    Wrapper(Vector2F(4f, 4f))
                ),
                Arguments.of(
                    lineSegmentD,
                    Wrapper(Vector2F(3.9f, 4.1f)),
                    Wrapper(Vector2F(4f, 4f))
                ),
                Arguments.of(
                    lineSegmentD,
                    Wrapper(Vector2F(4f, 4.1f)),
                    Wrapper(Vector2F(4f, 4f))
                ),
                Arguments.of(
                    lineSegmentD,
                    Wrapper(Vector2F(4.1f, 4.1f)),
                    Wrapper(Vector2F(4f, 4f))
                ),
                Arguments.of(
                    lineSegmentD,
                    Wrapper(Vector2F(3.9f, 3.9f)),
                    Wrapper(Vector2F(4f, 4f))
                ),
                Arguments.of(
                    lineSegmentD,
                    Wrapper(Vector2F(4f, 3.9f)),
                    Wrapper(Vector2F(4f, 4f))
                ),
                Arguments.of(
                    lineSegmentD,
                    Wrapper(Vector2F(4.1f, 3.9f)),
                    Wrapper(Vector2F(4f, 4f))
                ),
            )
            val mutableLineSegmentArgs = listOf(
                segmentAArgs,
                segmentBArgs,
                segmentCArgs,
                segmentDArgs,
            ).flatten()
            val defaultLineSegmentArgs = mutableLineSegmentArgs
                .mapLineSegmentsToDefaultLineSegments()

            return listOf(
                mutableLineSegmentArgs,
                defaultLineSegmentArgs
            ).flatten()
        }

        @JvmStatic
        fun intersectsRayArgs(): List<Arguments> {
            fun List<Arguments>.withLineSegmentPointsSwapped() = map { args ->
                val argArray = args.get().map {
                    if (it is LineSegment) LineSegment(
                        center = it.center,
                        orientation = it.orientation * ComplexF.fromAngle(AngleF.STRAIGHT),
                        length = it.length
                    )
                    else it
                }.toTypedArray()

                Arguments.of(*argArray)
            }

            fun createMutableLineSegmentMutableRayArgs(): List<Arguments> {
                val lineSegmentA = MutableLineSegment(
                    center = Vector2F(0.5f, 5f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(180f)),
                    length = 5f
                )
                val lineSegmentB = MutableLineSegment(
                    center = Vector2F(-2f, -3.5f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                    length = 5f
                )
                val lineSegmentC = MutableLineSegment(
                    center = Vector2F(3f, 0f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(135f)),
                    length = 5.656854f
                )
                val lineSegmentD = MutableLineSegment(
                    center = Vector2F(6f, -6.5f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(9.462322f)),
                    length = 6.0827627f
                )
                val parallelArgs = listOf(
                    Arguments.of(
                        lineSegmentA,
                        MutableRay(
                            origin = Vector2F(-2.1f, 5f), direction = Vector2F(1f, 0f)
                        ),
                        true
                    ),
                    Arguments.of(
                        lineSegmentA,
                        MutableRay(
                            origin = Vector2F(2.9f, 5f), direction = Vector2F(1f, 0f)
                        ),
                        true
                    ),
                    Arguments.of(
                        lineSegmentA,
                        MutableRay(
                            origin = Vector2F(3.1f, 5f), direction = Vector2F(1f, 0f)
                        ),
                        false
                    ),
                    Arguments.of(
                        lineSegmentA,
                        MutableRay(
                            origin = Vector2F(-2.1f, 5.1f), direction = Vector2F(1f, 0f)
                        ),
                        false
                    ),
                    Arguments.of(
                        lineSegmentA,
                        MutableRay(
                            origin = Vector2F(3.1f, 5f), direction = Vector2F(-1f, 0f)
                        ),
                        true
                    ),
                    Arguments.of(
                        lineSegmentA,
                        MutableRay(
                            origin = Vector2F(-1.9f, 5f), direction = Vector2F(-1f, 0f)
                        ),
                        true
                    ),
                    Arguments.of(
                        lineSegmentA,
                        MutableRay(
                            origin = Vector2F(-2.1f, 5f), direction = Vector2F(-1f, 0f)
                        ),
                        false
                    ),
                    Arguments.of(
                        lineSegmentA,
                        MutableRay(
                            origin = Vector2F(3.1f, 4.9f), direction = Vector2F(-1f, 0f)
                        ),
                        false
                    ),
                    Arguments.of(
                        lineSegmentB,
                        MutableRay(
                            origin = Vector2F(-2f, -6.1f), direction = Vector2F(0f, 1f)
                        ),
                        true
                    ),
                    Arguments.of(
                        lineSegmentB,
                        MutableRay(
                            origin = Vector2F(-2f, -1.1f), direction = Vector2F(0f, 1f)
                        ),
                        true
                    ),
                    Arguments.of(
                        lineSegmentB,
                        MutableRay(
                            origin = Vector2F(-2f, -0.9f), direction = Vector2F(0f, 1f)
                        ),
                        false
                    ),
                    Arguments.of(
                        lineSegmentB,
                        MutableRay(
                            origin = Vector2F(-2.1f, -6.1f),
                            direction = Vector2F(0f, 1f)
                        ),
                        false
                    ),
                    Arguments.of(
                        lineSegmentB,
                        MutableRay(
                            origin = Vector2F(-2f, -0.9f), direction = Vector2F(0f, -1f)
                        ),
                        true
                    ),
                    Arguments.of(
                        lineSegmentB,
                        MutableRay(
                            origin = Vector2F(-2f, -5.9f), direction = Vector2F(0f, -1f)
                        ),
                        true
                    ),
                    Arguments.of(
                        lineSegmentB,
                        MutableRay(
                            origin = Vector2F(-2f, -6.1f), direction = Vector2F(0f, -1f)
                        ),
                        false
                    ),
                    Arguments.of(
                        lineSegmentB,
                        MutableRay(
                            origin = Vector2F(-1.9f, -0.9f),
                            direction = Vector2F(0f, -1f)
                        ),
                        false
                    ),
                    Arguments.of(
                        lineSegmentC,
                        MutableRay(
                            origin = Vector2F(5.1f, -2.1f),
                            direction = ComplexF.fromAngle(AngleF.fromDegrees(135f))
                                .toVector2F()
                        ),
                        true
                    ),
                    Arguments.of(
                        lineSegmentC,
                        MutableRay(
                            origin = Vector2F(1.1f, 1.9f),
                            direction = ComplexF.fromAngle(AngleF.fromDegrees(135f))
                                .toVector2F()
                        ),
                        true
                    ),
                    Arguments.of(
                        lineSegmentC,
                        MutableRay(
                            origin = Vector2F(0.9f, 2.1f),
                            direction = ComplexF.fromAngle(AngleF.fromDegrees(135f))
                                .toVector2F()
                        ),
                        false
                    ),
                    Arguments.of(
                        lineSegmentC,
                        MutableRay(
                            origin = Vector2F(5.2f, -2.1f),
                            direction = ComplexF.fromAngle(AngleF.fromDegrees(135f))
                                .toVector2F()
                        ),
                        false
                    ),
                    Arguments.of(
                        lineSegmentC,
                        MutableRay(
                            origin = Vector2F(0.9f, 2.1f),
                            direction = ComplexF.fromAngle(AngleF.fromDegrees(-45f))
                                .toVector2F()
                        ),
                        true
                    ),
                    Arguments.of(
                        lineSegmentC,
                        MutableRay(
                            origin = Vector2F(4.9f, -1.9f),
                            direction = ComplexF.fromAngle(AngleF.fromDegrees(-45f))
                                .toVector2F()
                        ),
                        true
                    ),
                    Arguments.of(
                        lineSegmentC,
                        MutableRay(
                            origin = Vector2F(5.1f, -2.1f),
                            direction = ComplexF.fromAngle(AngleF.fromDegrees(-45f))
                                .toVector2F()
                        ),
                        false
                    ),
                    Arguments.of(
                        lineSegmentC,
                        MutableRay(
                            origin = Vector2F(0.9f, 2.2f),
                            direction = ComplexF.fromAngle(AngleF.fromDegrees(-45f))
                                .toVector2F()
                        ),
                        false
                    ),
                )
                val nonParallelArgs = listOf(
                    Arguments.of(
                        lineSegmentD,
                        MutableRay(
                            origin = Vector2F(8f, -3f),
                            direction = ComplexF.fromAngle(AngleF.fromDegrees(0f))
                                .toVector2F()
                        ),
                        false
                    ),
                    Arguments.of(
                        lineSegmentD,
                        MutableRay(
                            origin = Vector2F(8f, -3f),
                            direction = ComplexF.fromAngle(AngleF.fromDegrees(30f))
                                .toVector2F()
                        ),
                        false
                    ),
                    Arguments.of(
                        lineSegmentD,
                        MutableRay(
                            origin = Vector2F(8f, -3f),
                            direction = ComplexF.fromAngle(AngleF.fromDegrees(60f))
                                .toVector2F()
                        ),
                        false
                    ),
                    Arguments.of(
                        lineSegmentD,
                        MutableRay(
                            origin = Vector2F(8f, -3f),
                            direction = ComplexF.fromAngle(AngleF.fromDegrees(90f))
                                .toVector2F()
                        ),
                        false
                    ),
                    Arguments.of(
                        lineSegmentD,
                        MutableRay(
                            origin = Vector2F(8f, -3f),
                            direction = ComplexF.fromAngle(AngleF.fromDegrees(120f))
                                .toVector2F()
                        ),
                        false
                    ),
                    Arguments.of(
                        lineSegmentD,
                        MutableRay(
                            origin = Vector2F(8f, -3f),
                            direction = ComplexF.fromAngle(AngleF.fromDegrees(150f))
                                .toVector2F()
                        ),
                        false
                    ),
                    Arguments.of(
                        lineSegmentD,
                        MutableRay(
                            origin = Vector2F(8f, -3f),
                            direction = ComplexF.fromAngle(AngleF.fromDegrees(180f))
                                .toVector2F()
                        ),
                        false
                    ),
                    Arguments.of(
                        lineSegmentD,
                        MutableRay(
                            origin = Vector2F(8f, -3f),
                            direction = ComplexF.fromAngle(AngleF.fromDegrees(-150f))
                                .toVector2F()
                        ),
                        false
                    ),
                    Arguments.of(
                        lineSegmentD,
                        MutableRay(
                            origin = Vector2F(8f, -3f),
                            direction = ComplexF.fromAngle(AngleF.fromDegrees(-120f))
                                .toVector2F()
                        ),
                        true
                    ),
                    Arguments.of(
                        lineSegmentD,
                        MutableRay(
                            origin = Vector2F(8f, -3f),
                            direction = ComplexF.fromAngle(AngleF.fromDegrees(-90f))
                                .toVector2F()
                        ),
                        true
                    ),
                    Arguments.of(
                        lineSegmentD,
                        MutableRay(
                            origin = Vector2F(8f, -3f),
                            direction = ComplexF.fromAngle(AngleF.fromDegrees(-60f))
                                .toVector2F()
                        ),
                        false
                    ),
                    Arguments.of(
                        lineSegmentD,
                        MutableRay(
                            origin = Vector2F(8f, -3f),
                            direction = ComplexF.fromAngle(AngleF.fromDegrees(-30f))
                                .toVector2F()
                        ),
                        false
                    ),
                    Arguments.of(
                        lineSegmentD,
                        MutableRay(
                            origin = Vector2F(8f, -3f),
                            direction = ComplexF.fromAngle(AngleF.fromDegrees(-66.8f))
                                .toVector2F()
                        ),
                        false
                    ),
                    Arguments.of(
                        lineSegmentD,
                        MutableRay(
                            origin = Vector2F(8f, -3f),
                            direction = ComplexF.fromAngle(AngleF.fromDegrees(-74.05f))
                                .toVector2F()
                        ),
                        true
                    ),
                    Arguments.of(
                        lineSegmentD,
                        MutableRay(
                            origin = Vector2F(8f, -3f),
                            direction = ComplexF.fromAngle(AngleF.fromDegrees(-144.46f))
                                .toVector2F()
                        ),
                        false
                    ),
                    Arguments.of(
                        lineSegmentD,
                        MutableRay(
                            origin = Vector2F(8f, -3f),
                            direction = ComplexF.fromAngle(AngleF.fromDegrees(-140.19f))
                                .toVector2F()
                        ),
                        true
                    ),
                )
                return listOf(
                    parallelArgs,
                    parallelArgs.withLineSegmentPointsSwapped(),
                    nonParallelArgs,
                    nonParallelArgs.withLineSegmentPointsSwapped()
                ).flatten()
            }

            val mutableLineSegmentMutableRayArgs = createMutableLineSegmentMutableRayArgs()
            val defaultLineSegmentMutableRayArgs = mutableLineSegmentMutableRayArgs
                .mapLineSegmentsToDefaultLineSegments()
            val mutableLineSegmentDefaultRayArgs = mutableLineSegmentMutableRayArgs
                .mapRaysToDefaultRays()
            val defaultLineSegmentDefaultRayArgs = defaultLineSegmentMutableRayArgs
                .mapRaysToDefaultRays()

            return listOf(
                mutableLineSegmentMutableRayArgs,
                defaultLineSegmentMutableRayArgs,
                mutableLineSegmentDefaultRayArgs,
                defaultLineSegmentDefaultRayArgs
            ).flatten()
        }

        @JvmStatic
        fun containsVector2FArgs(): List<Arguments> {
            val lineSegmentA = MutableLineSegment(
                center = Vector2F(0f, 5f),
                orientation = ComplexF.fromAngle(AngleF.fromDegrees(180f)),
                length = 4f
            )
            val segmentAArgs = listOf(
                Arguments.of(lineSegmentA, Wrapper(Vector2F(-1.9f, 5f)), true),
                Arguments.of(lineSegmentA, Wrapper(Vector2F(-1.9f, 5.1f)), false),
                Arguments.of(lineSegmentA, Wrapper(Vector2F(-1.9f, 4.9f)), false),
                Arguments.of(lineSegmentA, Wrapper(Vector2F(-2f, 5f)), true),
                Arguments.of(lineSegmentA, Wrapper(Vector2F(-2f, 5.1f)), false),
                Arguments.of(lineSegmentA, Wrapper(Vector2F(-2f, 4.9f)), false),
                Arguments.of(lineSegmentA, Wrapper(Vector2F(-2.1f, 5f)), false),
                Arguments.of(lineSegmentA, Wrapper(Vector2F(-2.1f, 5.1f)), false),
                Arguments.of(lineSegmentA, Wrapper(Vector2F(-2.1f, 4.9f)), false),
                Arguments.of(lineSegmentA, Wrapper(Vector2F(1.9f, 5f)), true),
                Arguments.of(lineSegmentA, Wrapper(Vector2F(1.9f, 5.1f)), false),
                Arguments.of(lineSegmentA, Wrapper(Vector2F(1.9f, 4.9f)), false),
                Arguments.of(lineSegmentA, Wrapper(Vector2F(2f, 5f)), true),
                Arguments.of(lineSegmentA, Wrapper(Vector2F(2f, 5.1f)), false),
                Arguments.of(lineSegmentA, Wrapper(Vector2F(2f, 4.9f)), false),
                Arguments.of(lineSegmentA, Wrapper(Vector2F(2.1f, 5f)), false),
                Arguments.of(lineSegmentA, Wrapper(Vector2F(2.1f, 5.1f)), false),
                Arguments.of(lineSegmentA, Wrapper(Vector2F(2.1f, 4.9f)), false),
                Arguments.of(lineSegmentA, Wrapper(Vector2F(0f, 5f)), true),
                Arguments.of(lineSegmentA, Wrapper(Vector2F(0f, 5.1f)), false),
                Arguments.of(lineSegmentA, Wrapper(Vector2F(0f, 4.9f)), false),
            )
            val lineSegmentB = MutableLineSegment(
                center = Vector2F(3f, -0.5f),
                orientation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                length = 5f
            )
            val segmentBArgs = listOf(
                Arguments.of(lineSegmentB, Wrapper(Vector2F(3f, 1.9f)), true),
                Arguments.of(lineSegmentB, Wrapper(Vector2F(2.9f, 1.9f)), false),
                Arguments.of(lineSegmentB, Wrapper(Vector2F(3.1f, 1.9f)), false),
                Arguments.of(lineSegmentB, Wrapper(Vector2F(3f, 2f)), true),
                Arguments.of(lineSegmentB, Wrapper(Vector2F(2.9f, 2f)), false),
                Arguments.of(lineSegmentB, Wrapper(Vector2F(3.1f, 2f)), false),
                Arguments.of(lineSegmentB, Wrapper(Vector2F(3f, 2.1f)), false),
                Arguments.of(lineSegmentB, Wrapper(Vector2F(2.9f, 2.1f)), false),
                Arguments.of(lineSegmentB, Wrapper(Vector2F(3.1f, 2.1f)), false),
                Arguments.of(lineSegmentB, Wrapper(Vector2F(3f, -2.9f)), true),
                Arguments.of(lineSegmentB, Wrapper(Vector2F(2.9f, -2.9f)), false),
                Arguments.of(lineSegmentB, Wrapper(Vector2F(3.1f, -2.9f)), false),
                Arguments.of(lineSegmentB, Wrapper(Vector2F(3f, -3f)), true),
                Arguments.of(lineSegmentB, Wrapper(Vector2F(2.9f, -3f)), false),
                Arguments.of(lineSegmentB, Wrapper(Vector2F(3.1f, -3f)), false),
                Arguments.of(lineSegmentB, Wrapper(Vector2F(3f, -3.1f)), false),
                Arguments.of(lineSegmentB, Wrapper(Vector2F(2.9f, -3.1f)), false),
                Arguments.of(lineSegmentB, Wrapper(Vector2F(3.1f, -3.1f)), false),
                Arguments.of(lineSegmentB, Wrapper(Vector2F(3f, -0.5f)), true),
                Arguments.of(lineSegmentB, Wrapper(Vector2F(2.9f, -0.5f)), false),
                Arguments.of(lineSegmentB, Wrapper(Vector2F(3.1f, -0.5f)), false),
            )
            val lineSegmentC = MutableLineSegment(
                center = Vector2F(-3f, 1f),
                orientation = ComplexF.fromAngle(AngleF.fromDegrees(116.56505f)),
                length = 4.472136f
            )
            val segmentCArgs = listOf(
                Arguments.of(
                    lineSegmentC, Wrapper(Vector2F(-2.134164f, -0.95527864f)), false
                ),
                Arguments.of(
                    lineSegmentC, Wrapper(Vector2F(-2.0447214f, -0.91055727f)), true
                ),
                Arguments.of(
                    lineSegmentC, Wrapper(Vector2F(-1.9552786f, -0.8658359f)), false
                ),
                Arguments.of(
                    lineSegmentC, Wrapper(Vector2F(-2.0894427f, -1.0447214f)), false
                ),
                Arguments.of(
                    lineSegmentC, Wrapper(Vector2F(-4f, 3f)), true
                ),
                Arguments.of(
                    lineSegmentC, Wrapper(Vector2F(-1.9105573f, -0.95527864f)), false
                ),
                Arguments.of(
                    lineSegmentC, Wrapper(Vector2F(-2.0447214f, -1.1341641f)), false
                ),
                Arguments.of(
                    lineSegmentC, Wrapper(Vector2F(-1.9552786f, -1.0894427f)), false
                ),
                Arguments.of(
                    lineSegmentC, Wrapper(Vector2F(-1.8658359f, -1.0447214f)), false
                ),
                Arguments.of(
                    lineSegmentC, Wrapper(Vector2F(-4.0447216f, 2.865836f)), false
                ),
                Arguments.of(
                    lineSegmentC, Wrapper(Vector2F(-3.9552786f, 2.9105573f)), true
                ),
                Arguments.of(
                    lineSegmentC, Wrapper(Vector2F(-3.865836f, 2.9552786f)), false
                ),
                Arguments.of(
                    lineSegmentC, Wrapper(Vector2F(-4.0894427f, 2.9552786f)), false
                ),
                Arguments.of(
                    lineSegmentC, Wrapper(Vector2F(-2f, -1f)), true
                ),
                Arguments.of(
                    lineSegmentC, Wrapper(Vector2F(-3.9105573f, 3.0447214f)), false
                ),
                Arguments.of(
                    lineSegmentC, Wrapper(Vector2F(-4.134164f, 3.0447214f)), false
                ),
                Arguments.of(
                    lineSegmentC, Wrapper(Vector2F(-4.0447216f, 3.0894427f)), false
                ),
                Arguments.of(
                    lineSegmentC, Wrapper(Vector2F(-3.9552786f, 3.134164f)), false
                ),
                Arguments.of(
                    lineSegmentC, Wrapper(Vector2F(-3.0894427f, 0.95527864f)), false
                ),
                Arguments.of(
                    lineSegmentC, Wrapper(Vector2F(-3.0f, 1.0f)), true
                ),
                Arguments.of(
                    lineSegmentC, Wrapper(Vector2F(-2.9105573f, 1.0447214f)), false
                ),
            )
            val lineSegmentD = MutableLineSegment(
                center = Vector2F(4.0000005f, 4f),
                orientation = ComplexF.fromAngle(AngleF.fromDegrees(0f)),
                length = 0.000001f
            )
            val segmentDArgs = listOf(
                Arguments.of(lineSegmentD, Wrapper(Vector2F(3.9f, 4f)), false),
                Arguments.of(lineSegmentD, Wrapper(Vector2F(4f, 4f)), true),
                Arguments.of(lineSegmentD, Wrapper(Vector2F(4.1f, 4f)), false),
                Arguments.of(lineSegmentD, Wrapper(Vector2F(3.9f, 4.1f)), false),
                Arguments.of(lineSegmentD, Wrapper(Vector2F(4f, 4.1f)), false),
                Arguments.of(lineSegmentD, Wrapper(Vector2F(4.1f, 4.1f)), false),
                Arguments.of(lineSegmentD, Wrapper(Vector2F(3.9f, 3.9f)), false),
                Arguments.of(lineSegmentD, Wrapper(Vector2F(4f, 3.9f)), false),
                Arguments.of(lineSegmentD, Wrapper(Vector2F(4.1f, 3.9f)), false),
                Arguments.of(lineSegmentD, Wrapper(Vector2F(4.000001f, 4f)), true),
            )
            val mutableLineSegmentArgs = listOf(
                segmentAArgs,
                segmentBArgs,
                segmentCArgs,
                segmentDArgs,
            ).flatten()
            val defaultLineSegmentArgs = mutableLineSegmentArgs
                .mapLineSegmentsToDefaultLineSegments()

            return listOf(
                mutableLineSegmentArgs,
                defaultLineSegmentArgs
            ).flatten()
        }

        @JvmStatic
        fun copyArgs(): List<Arguments> {
            val mutableLineSegmentArgs = setArgs()
            val defaultLineSegmentArgs = mutableLineSegmentArgs
                .mapLineSegmentsToDefaultLineSegments()

            return listOf(
                mutableLineSegmentArgs,
                defaultLineSegmentArgs
            ).flatten()
        }

        @JvmStatic
        fun equalsAnyArgs(): List<Arguments> = equalsMutableLineSegmentArgs() + listOf(
            Arguments.of(
                MutableLineSegment(
                    center = Vector2F(0f, 5f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(180f)),
                    length = 4f
                ),
                null,
                false
            ),
            Arguments.of(
                MutableLineSegment(
                    center = Vector2F(0f, 5f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(180f)),
                    length = 4f
                ),
                DefaultLineSegment(
                    center = Vector2F(0f, 5f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(180f)),
                    length = 4f
                ),
                true
            ),
            Arguments.of(
                MutableLineSegment(
                    center = Vector2F(0f, 5f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(180f)),
                    length = 4f
                ),
                DefaultLineSegment(
                    center = Vector2F(0.05f, 5f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(180f)),
                    length = 4f
                ),
                false
            ),
        )

        @JvmStatic
        fun equalsMutableLineSegmentArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableLineSegment(
                    center = Vector2F(0f, 5f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(180f)),
                    length = 4f
                ),
                MutableLineSegment(
                    center = Vector2F(0f, 5f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(180f)),
                    length = 4f
                ),
                true
            ),
            Arguments.of(
                MutableLineSegment(
                    center = Vector2F(0f, 5f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(180f)),
                    length = 4f
                ),
                MutableLineSegment(
                    center = Vector2F(0.05f, 5f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(180f)),
                    length = 4f
                ),
                false
            ),
        )

        @JvmStatic
        fun hashCodeArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableLineSegment(
                    center = Vector2F(0f, 5f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(180f)),
                    length = 4f
                ),
                MutableLineSegment(
                    center = Vector2F(0f, 5f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(180f)),
                    length = 4f
                )
            ),
            Arguments.of(
                MutableLineSegment(
                    center = Vector2F(-3f, 1f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(116.56505f)),
                    length = 4.472136f
                ),
                MutableLineSegment(
                    center = Vector2F(-3f, 1f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(116.56505f)),
                    length = 4.472136f
                )
            ),
        )

        @JvmStatic
        fun toStringArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableLineSegment(
                    center = Vector2F(0f, 5f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(180f)),
                    length = 4f
                ),
                "LineSegment(" +
                        "center=${Vector2F(0f, 5f)}, " +
                        "orientation=${ComplexF.fromAngle(AngleF.fromDegrees(180f))}, " +
                        "length=${4f})"
            ),
            Arguments.of(
                MutableLineSegment(
                    center = Vector2F(-3f, 1f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(116.56505f)),
                    length = 4.472136f
                ),
                "LineSegment(" +
                        "center=${Vector2F(-3f, 1f)}, " +
                        "orientation=${ComplexF.fromAngle(AngleF.fromDegrees(116.56505f))}, " +
                        "length=${4.472136f})"
            ),
        )

        @JvmStatic
        fun componentsArgs(): List<Arguments> {
            val mutableLineSegmentArgs = listOf(
                Arguments.of(
                    MutableLineSegment(
                        center = Vector2F(0f, 5f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(180f)),
                        length = 4f
                    ),
                    Wrapper(Vector2F(0f, 5f)),
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(180f))),
                    4f
                ),
                Arguments.of(
                    MutableLineSegment(
                        center = Vector2F(-3f, 1f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(116.56505f)),
                        length = 4.472136f
                    ),
                    Wrapper(Vector2F(-3f, 1f)),
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(116.56505f))),
                    4.472136f
                ),
            )
            val defaultLineSegmentArgs = mutableLineSegmentArgs
                .mapLineSegmentsToDefaultLineSegments()

            return listOf(
                mutableLineSegmentArgs,
                defaultLineSegmentArgs
            ).flatten()
        }
    }
}