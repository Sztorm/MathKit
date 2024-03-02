package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.AngleF
import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F
import com.sztorm.lowallocmath.isApproximately
import com.sztorm.lowallocmath.utils.Wrapper
import com.sztorm.lowallocmath.utils.assertApproximation
import com.sztorm.lowallocmath.world2d.utils.DefaultRectangle
import com.sztorm.lowallocmath.world2d.utils.assertImmutabilityOf
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

class RectangleTests {
    @ParameterizedTest
    @MethodSource("centerArgs")
    fun centerReturnsCorrectValue(rectangle: Rectangle, expected: Wrapper<Vector2F>) =
        assertImmutabilityOf(rectangle) {
            assertApproximation(expected.value, rectangle.center)
        }

    @ParameterizedTest
    @MethodSource("orientationArgs")
    fun orientationReturnsCorrectValue(rectangle: Rectangle, expected: Wrapper<ComplexF>) =
        assertImmutabilityOf(rectangle) {
            assertApproximation(expected.value, rectangle.orientation)
        }

    @ParameterizedTest
    @MethodSource("widthArgs")
    fun widthReturnsCorrectValue(rectangle: Rectangle, expected: Float) =
        assertImmutabilityOf(rectangle) {
            assertApproximation(expected, rectangle.width)
        }

    @ParameterizedTest
    @MethodSource("heightArgs")
    fun heightReturnsCorrectValue(rectangle: Rectangle, expected: Float) =
        assertImmutabilityOf(rectangle) {
            assertApproximation(expected, rectangle.height)
        }

    @ParameterizedTest
    @MethodSource("pointsArgs")
    fun pointsReturnCorrectValues(
        rectangle: Rectangle,
        expectedPointA: Wrapper<Vector2F>,
        expectedPointB: Wrapper<Vector2F>,
        expectedPointC: Wrapper<Vector2F>,
        expectedPointD: Wrapper<Vector2F>
    ) = assertImmutabilityOf(rectangle) {
        assertApproximation(expectedPointA.value, rectangle.pointA)
        assertApproximation(expectedPointB.value, rectangle.pointB)
        assertApproximation(expectedPointC.value, rectangle.pointC)
        assertApproximation(expectedPointD.value, rectangle.pointD)
    }

    @ParameterizedTest
    @MethodSource("areaArgs")
    fun areaReturnsCorrectValue(rectangle: Rectangle, expected: Float) =
        assertImmutabilityOf(rectangle) {
            assertApproximation(expected, rectangle.area)
        }

    @ParameterizedTest
    @MethodSource("perimeterArgs")
    fun perimeterReturnsCorrectValue(rectangle: Rectangle, expected: Float) =
        assertImmutabilityOf(rectangle) {
            assertApproximation(expected, rectangle.perimeter)
        }

    @ParameterizedTest
    @MethodSource("positionArgs")
    fun positionReturnsCorrectValue(rectangle: Rectangle, expected: Wrapper<Vector2F>) =
        assertImmutabilityOf(rectangle) {
            assertApproximation(expected.value, rectangle.position)
        }

    @ParameterizedTest
    @MethodSource("setArgs")
    fun setMutatesRectangleCorrectly(
        rectangle: MutableRectangle,
        center: Wrapper<Vector2F>,
        orientation: Wrapper<ComplexF>,
        width: Float,
        height: Float,
        expected: MutableRectangle
    ) = assertEquals(
        expected,
        rectangle.apply { set(center.value, orientation.value, width, height) }
    )

    @ParameterizedTest
    @MethodSource("interpolatedArgs")
    fun interpolatedReturnsCorrectValue(
        rectangle: Rectangle, to: Rectangle, by: Float, expected: Rectangle
    ) = assertImmutabilityOf(rectangle) {
        assertImmutabilityOf(to) {
            assertApproximation(expected, rectangle.interpolated(to, by))
        }
    }

    @ParameterizedTest
    @MethodSource("interpolateArgs")
    fun interpolateMutatesRectangleCorrectly(
        rectangle: MutableRectangle,
        from: Rectangle,
        to: Rectangle,
        by: Float,
        expected: MutableRectangle
    ) = assertImmutabilityOf(from) {
        assertImmutabilityOf(to) {
            assertApproximation(expected, rectangle.apply { interpolate(from, to, by) })
        }
    }

    @ParameterizedTest
    @MethodSource("closestPointToArgs")
    fun closestPointToReturnsCorrectValue(
        rectangle: Rectangle, point: Wrapper<Vector2F>, expected: Wrapper<Vector2F>
    ) = assertImmutabilityOf(rectangle) {
        assertApproximation(expected.value, rectangle.closestPointTo(point.value))
    }

    @ParameterizedTest
    @MethodSource("containsVector2FArgs")
    fun containsReturnsCorrectValue(
        rectangle: Rectangle, point: Wrapper<Vector2F>, expected: Boolean
    ) = assertImmutabilityOf(rectangle) {
        assertEquals(expected, rectangle.contains(point.value))
    }

    @ParameterizedTest
    @MethodSource("copyArgs")
    fun copyReturnsCorrectValue(
        rectangle: Rectangle,
        center: Wrapper<Vector2F>,
        orientation: Wrapper<ComplexF>,
        width: Float,
        height: Float,
        expected: Rectangle
    ) = assertEquals(expected, rectangle.copy(center.value, orientation.value, width, height))

    @ParameterizedTest
    @MethodSource("equalsAnyArgs")
    fun equalsReturnsCorrectValue(
        rectangle: MutableRectangle, other: Any?, expected: Boolean
    ) = assertImmutabilityOf(rectangle) {
        assertEquals(expected, rectangle == other)
    }

    @ParameterizedTest
    @MethodSource("equalsMutableRectangleArgs")
    fun equalsReturnsCorrectValue(
        rectangle: MutableRectangle, other: MutableRectangle, expected: Boolean
    ) = assertImmutabilityOf(rectangle) {
        assertImmutabilityOf(other) {
            assertEquals(expected, rectangle.equals(other))
        }
    }

    @ParameterizedTest
    @MethodSource("hashCodeArgs")
    fun hashCodeReturnsCorrectValue(rectangle: MutableRectangle, other: MutableRectangle) =
        assertImmutabilityOf(rectangle) {
            assertImmutabilityOf(other) {
                assertEquals(rectangle.hashCode(), other.hashCode())
            }
        }

    @ParameterizedTest
    @MethodSource("toStringArgs")
    fun toStringReturnsCorrectValue(rectangle: MutableRectangle, expected: String) =
        assertImmutabilityOf(rectangle) {
            assertEquals(expected, rectangle.toString())
        }

    @ParameterizedTest
    @MethodSource("componentsArgs")
    fun componentsReturnCorrectValues(
        rectangle: Rectangle,
        expectedComponent1: Wrapper<Vector2F>,
        expectedComponent2: Wrapper<ComplexF>,
        expectedComponent3: Float,
        expectedComponent4: Float
    ) = assertImmutabilityOf(rectangle) {
        val (
            actualComponent1: Vector2F,
            actualComponent2: ComplexF,
            actualComponent3: Float,
            actualComponent4: Float
        ) = rectangle

        assertEquals(expectedComponent1.value, actualComponent1)
        assertEquals(expectedComponent2.value, actualComponent2)
        assertEquals(expectedComponent3, actualComponent3)
        assertEquals(expectedComponent4, actualComponent4)
    }

    companion object {
        @JvmStatic
        fun areApproximatelyEqual(
            a: Rectangle, b: Rectangle, tolerance: Float = 0.00001f
        ): Boolean = a.center.isApproximately(b.center, tolerance) and
                a.orientation.isApproximately(b.orientation, tolerance) and
                a.width.isApproximately(b.width, tolerance) and
                a.height.isApproximately(b.height, tolerance) and
                a.pointA.isApproximately(b.pointA, tolerance) and
                a.pointB.isApproximately(b.pointB, tolerance) and
                a.pointC.isApproximately(b.pointC, tolerance) and
                a.pointD.isApproximately(b.pointD, tolerance)

        @JvmStatic
        fun clone(rectangle: Rectangle) = rectangle.copy()

        @JvmStatic
        fun List<Arguments>.mapRectanglesToDefaultRectangles() = map { args ->
            val argArray = args.get().map {
                if (it is Rectangle) DefaultRectangle(
                    it.center, it.orientation, it.width, it.height
                )
                else it
            }.toTypedArray()

            Arguments.of(*argArray)
        }

        @JvmStatic
        fun centerArgs(): List<Arguments> {
            val mutableRectangleArgs = listOf(
                Arguments.of(
                    MutableRectangle(
                        center = Vector2F(-1f, -2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        width = 3f,
                        height = 5f
                    ),
                    Wrapper(Vector2F(-1f, -2f))
                ),
                Arguments.of(
                    MutableRectangle(
                        center = Vector2F(-2f, 4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        width = 4f,
                        height = 2f
                    ),
                    Wrapper(Vector2F(-2f, 4f))
                ),
            )
            val defaultRectangleArgs = mutableRectangleArgs.mapRectanglesToDefaultRectangles()

            return listOf(
                mutableRectangleArgs,
                defaultRectangleArgs
            ).flatten()
        }

        @JvmStatic
        fun orientationArgs(): List<Arguments> {
            val mutableRectangleArgs = listOf(
                Arguments.of(
                    MutableRectangle(
                        center = Vector2F(-1f, -2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        width = 3f,
                        height = 5f
                    ),
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(120f)))
                ),
                Arguments.of(
                    MutableRectangle(
                        center = Vector2F(-2f, 4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        width = 4f,
                        height = 2f
                    ),
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(45f)))
                ),
            )
            val defaultRectangleArgs = mutableRectangleArgs.mapRectanglesToDefaultRectangles()

            return listOf(
                mutableRectangleArgs,
                defaultRectangleArgs
            ).flatten()
        }

        @JvmStatic
        fun widthArgs(): List<Arguments> {
            val mutableRectangleArgs = listOf(
                Arguments.of(
                    MutableRectangle(
                        center = Vector2F(-1f, -2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        width = 3f,
                        height = 5f
                    ),
                    3f
                ),
                Arguments.of(
                    MutableRectangle(
                        center = Vector2F(-2f, 4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        width = 4f,
                        height = 2f
                    ),
                    4f
                ),
            )
            val defaultRectangleArgs = mutableRectangleArgs.mapRectanglesToDefaultRectangles()

            return listOf(
                mutableRectangleArgs,
                defaultRectangleArgs
            ).flatten()
        }

        @JvmStatic
        fun heightArgs(): List<Arguments> {
            val mutableRectangleArgs = listOf(
                Arguments.of(
                    MutableRectangle(
                        center = Vector2F(-1f, -2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        width = 3f,
                        height = 5f
                    ),
                    5f
                ),
                Arguments.of(
                    MutableRectangle(
                        center = Vector2F(-2f, 4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        width = 4f,
                        height = 2f
                    ),
                    2f
                ),
            )
            val defaultRectangleArgs = mutableRectangleArgs.mapRectanglesToDefaultRectangles()

            return listOf(
                mutableRectangleArgs,
                defaultRectangleArgs
            ).flatten()
        }

        @JvmStatic
        fun pointsArgs(): List<Arguments> {
            val mutableRectangleArgs = listOf(
                Arguments.of(
                    MutableRectangle(
                        center = Vector2F.ZERO,
                        orientation = ComplexF.ONE,
                        width = 4f,
                        height = 2f
                    ),
                    Wrapper(Vector2F(2f, 1f)),
                    Wrapper(Vector2F(-2f, 1f)),
                    Wrapper(Vector2F(-2f, -1f)),
                    Wrapper(Vector2F(2f, -1f))
                ),
                Arguments.of(
                    MutableRectangle(
                        center = Vector2F(-2f, 4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        width = 4f,
                        height = 2f
                    ),
                    Wrapper(Vector2F(-1.292893f, 6.12132f)),
                    Wrapper(Vector2F(-4.12132f, 3.292893f)),
                    Wrapper(Vector2F(-2.707107f, 1.87868f)),
                    Wrapper(Vector2F(0.12132f, 4.707107f))
                ),
                Arguments.of(
                    MutableRectangle(
                        center = Vector2F(-1f, -2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        width = 3f,
                        height = 5f
                    ),
                    Wrapper(Vector2F(-3.915063f, -1.950962f)),
                    Wrapper(Vector2F(-2.415063f, -4.549038f)),
                    Wrapper(Vector2F(1.915063f, -2.049038f)),
                    Wrapper(Vector2F(0.415063f, 0.549038f))
                ),
            )
            val defaultRectangleArgs = mutableRectangleArgs.mapRectanglesToDefaultRectangles()

            return listOf(
                mutableRectangleArgs,
                defaultRectangleArgs
            ).flatten()
        }

        @JvmStatic
        fun areaArgs(): List<Arguments> {
            val mutableRectangleArgs = listOf(
                Arguments.of(
                    MutableRectangle(
                        center = Vector2F.ZERO,
                        orientation = ComplexF.ONE,
                        width = 4f,
                        height = 2f
                    ),
                    8f
                ),
                Arguments.of(
                    MutableRectangle(
                        center = Vector2F(-2f, 4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        width = 4f,
                        height = 2f
                    ),
                    8f
                ),
                Arguments.of(
                    MutableRectangle(
                        center = Vector2F.ZERO,
                        orientation = ComplexF.ONE,
                        width = 1.5f,
                        height = 3f
                    ),
                    4.5f
                ),
            )
            val defaultRectangleArgs = mutableRectangleArgs.mapRectanglesToDefaultRectangles()

            return listOf(
                mutableRectangleArgs,
                defaultRectangleArgs
            ).flatten()
        }

        @JvmStatic
        fun perimeterArgs(): List<Arguments> {
            val mutableRectangleArgs = listOf(
                Arguments.of(
                    MutableRectangle(
                        center = Vector2F.ZERO,
                        orientation = ComplexF.ONE,
                        width = 4f,
                        height = 2f
                    ),
                    12f
                ),
                Arguments.of(
                    MutableRectangle(
                        center = Vector2F(-2f, 4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        width = 4f,
                        height = 2f
                    ),
                    12f
                ),
                Arguments.of(
                    MutableRectangle(
                        center = Vector2F.ZERO,
                        orientation = ComplexF.ONE,
                        width = 1.5f,
                        height = 3f
                    ),
                    9f
                ),
            )
            val defaultRectangleArgs = mutableRectangleArgs.mapRectanglesToDefaultRectangles()

            return listOf(
                mutableRectangleArgs,
                defaultRectangleArgs
            ).flatten()
        }

        @JvmStatic
        fun positionArgs(): List<Arguments> = centerArgs()

        @JvmStatic
        fun setArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableRectangle(
                    center = Vector2F(-2f, 4f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    width = 4f,
                    height = 2f
                ),
                Wrapper(Vector2F(-2f, 4f)),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(45f))),
                4f,
                2f,
                MutableRectangle(
                    center = Vector2F(-2f, 4f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    width = 4f,
                    height = 2f
                )
            ),
            Arguments.of(
                MutableRectangle(
                    center = Vector2F(-2f, 4f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    width = 4f,
                    height = 2f
                ),
                Wrapper(Vector2F(-2.5f, 1f)),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(45f))),
                4f,
                1f,
                MutableRectangle(
                    center = Vector2F(-2.5f, 1f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    width = 4f,
                    height = 1f
                )
            ),
            Arguments.of(
                MutableRectangle(
                    center = Vector2F(-2f, 4f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    width = 4f,
                    height = 2f
                ),
                Wrapper(Vector2F(0f, 8f)),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(30f))),
                1f,
                3f,
                MutableRectangle(
                    center = Vector2F(0f, 8f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                    width = 1f,
                    height = 3f
                )
            ),
        )

        @JvmStatic
        fun interpolatedArgs(): List<Arguments> {
            val mutableRectangleArgs = interpolateArgs().map {
                Arguments.of(*it.get().drop(1).toTypedArray())
            }
            val defaultRectangleArgs = mutableRectangleArgs.mapRectanglesToDefaultRectangles()

            return listOf(
                mutableRectangleArgs,
                defaultRectangleArgs
            ).flatten()
        }

        @JvmStatic
        fun interpolateArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableRectangle(
                    center = Vector2F.ZERO,
                    orientation = ComplexF.ONE,
                    width = 1f,
                    height = 0.5f
                ),
                MutableRectangle(
                    center = Vector2F(-2f, 4f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    width = 4f,
                    height = 2f
                ),
                MutableRectangle(
                    center = Vector2F(-2f, 4f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    width = 4f,
                    height = 2f
                ),
                0.5f,
                MutableRectangle(
                    center = Vector2F(-2f, 4f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    width = 4f,
                    height = 2f
                )
            ),
            Arguments.of(
                MutableRectangle(
                    center = Vector2F.ZERO,
                    orientation = ComplexF.ONE,
                    width = 1f,
                    height = 0.5f
                ),
                MutableRectangle(
                    center = Vector2F(-2f, 4f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    width = 4f,
                    height = 2f
                ),
                MutableRectangle(
                    center = Vector2F(-1f, -2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                    width = 3f,
                    height = 5f
                ),
                0f,
                MutableRectangle(
                    center = Vector2F(-2f, 4f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    width = 4f,
                    height = 2f
                )
            ),
            Arguments.of(
                MutableRectangle(
                    center = Vector2F.ZERO,
                    orientation = ComplexF.ONE,
                    width = 1f,
                    height = 0.5f
                ),
                MutableRectangle(
                    center = Vector2F(-2f, 4f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    width = 4f,
                    height = 2f
                ),
                MutableRectangle(
                    center = Vector2F(-1f, -2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                    width = 3f,
                    height = 5f
                ),
                1f,
                MutableRectangle(
                    center = Vector2F(-1f, -2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                    width = 3f,
                    height = 5f
                )
            ),
            Arguments.of(
                MutableRectangle(
                    center = Vector2F.ZERO,
                    orientation = ComplexF.ONE,
                    width = 1f,
                    height = 0.5f
                ),
                MutableRectangle(
                    center = Vector2F(-2f, 4f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    width = 4f,
                    height = 2f
                ),
                MutableRectangle(
                    center = Vector2F(-1f, -2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                    width = 3f,
                    height = 5f
                ),
                0.5f,
                MutableRectangle(
                    center = Vector2F(-1.5f, 1f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(82.5f)),
                    width = 3.5f,
                    height = 3.5f
                )
            ),
        )

        @JvmStatic
        fun closestPointToArgs(): List<Arguments> {
            val pointA = Vector2F(2f, 7f)
            val pointB = Vector2F(-2f, 5f)
            val pointC = Vector2F(-1f, 3f)
            val pointD = Vector2F(3f, 5f)
            val a = (pointA.y - pointB.y) / (pointA.x - pointB.x)
            val rectangle = MutableRectangle(
                center = (pointB + pointD) * 0.5f,
                orientation = ComplexF.fromAngle(AngleF.atan(a)),
                width = pointA.distanceTo(pointB),
                height = pointB.distanceTo(pointC)
            )
            val mutableRectangleArgs = listOf(
                Arguments.of(
                    rectangle,
                    Wrapper(Vector2F(4.5f, 7f)),
                    Wrapper(Vector2F(2.5f, 6f))
                ),
                Arguments.of(
                    rectangle,
                    Wrapper(Vector2F(2.1f, 7.1f)),
                    Wrapper(Vector2F(2f, 7f))
                ),
                Arguments.of(
                    rectangle,
                    Wrapper(Vector2F(0.5f, 8f)),
                    Wrapper(Vector2F(1.2f, 6.6f))
                ),
                Arguments.of(
                    rectangle,
                    Wrapper(Vector2F(-5f, 6f)),
                    Wrapper(Vector2F(-2f, 5f))
                ),
                Arguments.of(
                    rectangle,
                    Wrapper(Vector2F(-3.5f, 3f)),
                    Wrapper(Vector2F(-1.5f, 4f))
                ),
                Arguments.of(
                    rectangle,
                    Wrapper(Vector2F(-0.1f, 1f)),
                    Wrapper(Vector2F(-1f, 3f))
                ),
                Arguments.of(
                    rectangle,
                    Wrapper(Vector2F(1.5f, 3f)),
                    Wrapper(Vector2F(1f, 4f))
                ),
                Arguments.of(
                    rectangle,
                    Wrapper(Vector2F(5f, 4f)),
                    Wrapper(Vector2F(3f, 5f))
                ),
                Arguments.of(
                    rectangle,
                    Wrapper(Vector2F(1.2f, 6.5f)),
                    Wrapper(Vector2F(1.2f, 6.5f))
                ),
                Arguments.of(
                    rectangle,
                    Wrapper(Vector2F(-1f, 3.1f)),
                    Wrapper(Vector2F(-1f, 3.1f))
                ),
                Arguments.of(
                    rectangle,
                    Wrapper(Vector2F(-1.4f, 4f)),
                    Wrapper(Vector2F(-1.4f, 4f))
                ),
            )
            val defaultRectangleArgs = mutableRectangleArgs.mapRectanglesToDefaultRectangles()

            return listOf(
                mutableRectangleArgs,
                defaultRectangleArgs
            ).flatten()
        }

        @JvmStatic
        fun containsVector2FArgs(): List<Arguments> {
            val pointA = Vector2F(2f, 7f)
            val pointB = Vector2F(-2f, 5f)
            val pointC = Vector2F(-1f, 3f)
            val pointD = Vector2F(3f, 5f)
            val a = (pointA.y - pointB.y) / (pointA.x - pointB.x)
            val rectangle = MutableRectangle(
                center = (pointB + pointD) * 0.5f,
                orientation = ComplexF.fromAngle(AngleF.atan(a)),
                width = pointA.distanceTo(pointB),
                height = pointB.distanceTo(pointC)
            )
            val mutableRectangleArgs = listOf(
                Arguments.of(
                    rectangle, Wrapper(Vector2F(1.2f, 6.5f)), true
                ),
                Arguments.of(
                    rectangle, Wrapper(Vector2F(1.2f, 6.7f)), false
                ),
                Arguments.of(
                    rectangle, Wrapper(Vector2F(-1f, 3.1f)), true
                ),
                Arguments.of(
                    rectangle, Wrapper(Vector2F(-0.9f, 3f)), false
                ),
                Arguments.of(
                    rectangle, Wrapper(Vector2F(-1.4f, 4f)), true
                ),
                Arguments.of(
                    rectangle, Wrapper(Vector2F(-1.6f, 4f)), false
                ),
                Arguments.of(
                    MutableRectangle(
                        center = Vector2F.ZERO, orientation = ComplexF.ONE, width = 4f, height = 2f
                    ),
                    Wrapper(Vector2F(-1.9f, -0.9f)),
                    true
                ),
                Arguments.of(
                    MutableRectangle(
                        center = Vector2F.ZERO, orientation = ComplexF.ONE, width = 4f, height = 2f
                    ),
                    Wrapper(Vector2F(-2.1f, -1f)),
                    false
                ),
                Arguments.of(
                    MutableRectangle(
                        center = Vector2F(-2f, 4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        width = 4f,
                        height = 2f
                    ),
                    Wrapper(Vector2F(0.02132f, 4.707107f)),
                    true
                ),
                Arguments.of(
                    MutableRectangle(
                        center = Vector2F(-2f, 4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        width = 4f,
                        height = 2f
                    ),
                    Wrapper(Vector2F(0.22132f, 4.707107f)),
                    false
                ),
            )
            val defaultRectangleArgs = mutableRectangleArgs.mapRectanglesToDefaultRectangles()

            return listOf(
                mutableRectangleArgs,
                defaultRectangleArgs
            ).flatten()
        }

        @JvmStatic
        fun copyArgs(): List<Arguments> {
            val mutableRectangleArgs = setArgs()
            val defaultRectangleArgs = mutableRectangleArgs.mapRectanglesToDefaultRectangles()

            return listOf(
                mutableRectangleArgs,
                defaultRectangleArgs
            ).flatten()
        }

        @JvmStatic
        fun equalsAnyArgs(): List<Arguments> = equalsMutableRectangleArgs() + listOf(
            Arguments.of(
                MutableRectangle(
                    center = Vector2F(-2f, 4f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    width = 4f,
                    height = 2f
                ),
                null,
                false
            ),
            Arguments.of(
                MutableRectangle(
                    center = Vector2F(-2f, 4f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    width = 4f,
                    height = 2f
                ),
                DefaultRectangle(
                    center = Vector2F(-2f, 4f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    width = 4f,
                    height = 2f
                ),
                true
            ),
            Arguments.of(
                MutableRectangle(
                    center = Vector2F(-2f, 4f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    width = 4f,
                    height = 2f
                ),
                DefaultRectangle(
                    center = Vector2F(-2f, 4f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    width = 4f,
                    height = 2.1f
                ),
                false
            ),
        )

        @JvmStatic
        fun equalsMutableRectangleArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableRectangle(
                    center = Vector2F(-2f, 4f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    width = 4f,
                    height = 2f
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
                MutableRectangle(
                    center = Vector2F(-2f, 4f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    width = 4f,
                    height = 2f
                ),
                MutableRectangle(
                    center = Vector2F(-2f, 4f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    width = 4f,
                    height = 2.1f
                ),
                false
            ),
        )

        @JvmStatic
        fun hashCodeArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableRectangle(
                    center = Vector2F(-2f, 4f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    width = 4f,
                    height = 2f
                ),
                MutableRectangle(
                    center = Vector2F(-2f, 4f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    width = 4f,
                    height = 2f
                )
            ),
            Arguments.of(
                MutableRectangle(
                    center = Vector2F(0f, 8f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                    width = 1f,
                    height = 3f
                ),
                MutableRectangle(
                    center = Vector2F(0f, 8f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                    width = 1f,
                    height = 3f
                )
            ),
        )

        @JvmStatic
        fun toStringArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableRectangle(
                    center = Vector2F(-2f, 4f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    width = 4f,
                    height = 2f
                ),
                "Rectangle(" +
                        "center=${Vector2F(-2f, 4f)}, " +
                        "orientation=${ComplexF.fromAngle(AngleF.fromDegrees(45f))}, " +
                        "width=${4f}, " +
                        "height=${2f})"
            ),
            Arguments.of(
                MutableRectangle(
                    center = Vector2F(0f, 8f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                    width = 1f,
                    height = 3f
                ),
                "Rectangle(" +
                        "center=${Vector2F(0f, 8f)}, " +
                        "orientation=${ComplexF.fromAngle(AngleF.fromDegrees(30f))}, " +
                        "width=${1f}, " +
                        "height=${3f})"
            ),
        )

        @JvmStatic
        fun componentsArgs(): List<Arguments> {
            val mutableRectangleArgs = listOf(
                Arguments.of(
                    MutableRectangle(
                        center = Vector2F(-2f, 4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        width = 4f,
                        height = 2f
                    ),
                    Wrapper(Vector2F(-2f, 4f)),
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(45f))),
                    4f,
                    2f
                ),
                Arguments.of(
                    MutableRectangle(
                        center = Vector2F(0f, 8f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                        width = 1f,
                        height = 3f
                    ),
                    Wrapper(Vector2F(0f, 8f)),
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(30f))),
                    1f,
                    3f
                ),
            )
            val defaultRectangleArgs = mutableRectangleArgs.mapRectanglesToDefaultRectangles()

            return listOf(
                mutableRectangleArgs,
                defaultRectangleArgs
            ).flatten()
        }
    }
}