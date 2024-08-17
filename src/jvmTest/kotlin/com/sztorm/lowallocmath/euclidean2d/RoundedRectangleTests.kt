package com.sztorm.lowallocmath.euclidean2d

import com.sztorm.lowallocmath.AngleF
import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F
import com.sztorm.lowallocmath.euclidean2d.utils.DefaultRoundedRectangle
import com.sztorm.lowallocmath.euclidean2d.utils.assertImmutabilityOf
import com.sztorm.lowallocmath.isApproximately
import com.sztorm.lowallocmath.utils.Wrapper
import com.sztorm.lowallocmath.utils.assertApproximation
import com.sztorm.lowallocmath.utils.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

class RoundedRectangleTests {
    @ParameterizedTest
    @MethodSource("constructorArgs")
    fun constructorCreatesCorrectRoundedRectangle(
        center: Wrapper<Vector2F>,
        orientation: Wrapper<ComplexF>,
        width: Float,
        height: Float,
        cornerRadius: Float
    ) {
        val mutableRectangle = MutableRoundedRectangle(
            center.value, orientation.value, width, height, cornerRadius
        )
        val rectangle = RoundedRectangle(
            center.value, orientation.value, width, height, cornerRadius
        )
        assertEquals(center.value, mutableRectangle.center)
        assertEquals(orientation.value, mutableRectangle.orientation)
        assertEquals(width, mutableRectangle.width)
        assertEquals(height, mutableRectangle.height)
        assertEquals(cornerRadius, mutableRectangle.cornerRadius)

        assertEquals(center.value, rectangle.center)
        assertEquals(orientation.value, rectangle.orientation)
        assertEquals(width, rectangle.width)
        assertEquals(height, rectangle.height)
        assertEquals(cornerRadius, rectangle.cornerRadius)
    }

    @ParameterizedTest
    @MethodSource("constructorThrowsExceptionArgs")
    fun constructorThrowsCorrectException(
        center: Wrapper<Vector2F>,
        orientation: Wrapper<ComplexF>,
        width: Float,
        height: Float,
        cornerRadius: Float,
        expectedExceptionClass: Class<Throwable>
    ) {
        assertThrows(expectedExceptionClass) {
            MutableRoundedRectangle(center.value, orientation.value, width, height, cornerRadius)
        }
        assertThrows(expectedExceptionClass) {
            RoundedRectangle(center.value, orientation.value, width, height, cornerRadius)
        }
    }

    @ParameterizedTest
    @MethodSource("centerArgs")
    fun centerReturnsCorrectValue(rectangle: RoundedRectangle, expected: Wrapper<Vector2F>) =
        assertImmutabilityOf(rectangle) {
            assertApproximation(expected.value, rectangle.center)
        }

    @ParameterizedTest
    @MethodSource("orientationArgs")
    fun orientationReturnsCorrectValue(rectangle: RoundedRectangle, expected: Wrapper<ComplexF>) =
        assertImmutabilityOf(rectangle) {
            assertApproximation(expected.value, rectangle.orientation)
        }

    @ParameterizedTest
    @MethodSource("widthArgs")
    fun widthReturnsCorrectValue(rectangle: RoundedRectangle, expected: Float) =
        assertImmutabilityOf(rectangle) {
            assertApproximation(expected, rectangle.width)
        }

    @ParameterizedTest
    @MethodSource("heightArgs")
    fun heightReturnsCorrectValue(rectangle: RoundedRectangle, expected: Float) =
        assertImmutabilityOf(rectangle) {
            assertApproximation(expected, rectangle.height)
        }

    @ParameterizedTest
    @MethodSource("cornerRadiusArgs")
    fun cornerRadiusReturnsCorrectValue(rectangle: RoundedRectangle, expected: Float) =
        assertImmutabilityOf(rectangle) {
            assertApproximation(expected, rectangle.cornerRadius)
        }

    @ParameterizedTest
    @MethodSource("pointsArgs")
    fun pointsReturnCorrectValues(
        rectangle: RoundedRectangle,
        expectedPointA: Wrapper<Vector2F>,
        expectedPointB: Wrapper<Vector2F>,
        expectedPointC: Wrapper<Vector2F>,
        expectedPointD: Wrapper<Vector2F>,
        expectedPointE: Wrapper<Vector2F>,
        expectedPointF: Wrapper<Vector2F>,
        expectedPointG: Wrapper<Vector2F>,
        expectedPointH: Wrapper<Vector2F>,
    ) = assertImmutabilityOf(rectangle) {
        assertApproximation(expectedPointA.value, rectangle.pointA)
        assertApproximation(expectedPointB.value, rectangle.pointB)
        assertApproximation(expectedPointC.value, rectangle.pointC)
        assertApproximation(expectedPointD.value, rectangle.pointD)
        assertApproximation(expectedPointE.value, rectangle.pointE)
        assertApproximation(expectedPointF.value, rectangle.pointF)
        assertApproximation(expectedPointG.value, rectangle.pointG)
        assertApproximation(expectedPointH.value, rectangle.pointH)
    }

    @ParameterizedTest
    @MethodSource("cornerCentersArgs")
    fun cornerCentersReturnCorrectValues(
        rectangle: RoundedRectangle,
        expectedCornerCenterA: Wrapper<Vector2F>,
        expectedCornerCenterB: Wrapper<Vector2F>,
        expectedCornerCenterC: Wrapper<Vector2F>,
        expectedCornerCenterD: Wrapper<Vector2F>
    ) = assertImmutabilityOf(rectangle) {
        assertApproximation(expectedCornerCenterA.value, rectangle.cornerCenterA)
        assertApproximation(expectedCornerCenterB.value, rectangle.cornerCenterB)
        assertApproximation(expectedCornerCenterC.value, rectangle.cornerCenterC)
        assertApproximation(expectedCornerCenterD.value, rectangle.cornerCenterD)
    }

    @ParameterizedTest
    @MethodSource("areaArgs")
    fun areaReturnsCorrectValue(rectangle: RoundedRectangle, expected: Float) =
        assertImmutabilityOf(rectangle) {
            assertApproximation(expected, rectangle.area)
        }

    @ParameterizedTest
    @MethodSource("perimeterArgs")
    fun perimeterReturnsCorrectValue(rectangle: RoundedRectangle, expected: Float) =
        assertImmutabilityOf(rectangle) {
            assertApproximation(expected, rectangle.perimeter)
        }

    @ParameterizedTest
    @MethodSource("positionArgs")
    fun positionReturnsCorrectValue(rectangle: RoundedRectangle, expected: Wrapper<Vector2F>) =
        assertImmutabilityOf(rectangle) {
            assertApproximation(expected.value, rectangle.position)
        }

    @ParameterizedTest
    @MethodSource("calibrateArgs")
    fun calibrateMutatesRoundedRectangleCorrectly(
        rectangle: MutableRoundedRectangle, expected: MutableRoundedRectangle
    ) = assertApproximation(expected, rectangle.apply { calibrate() })

    @ParameterizedTest
    @MethodSource("setArgs")
    fun setMutatesRoundedRectangleCorrectly(
        rectangle: MutableRoundedRectangle,
        center: Wrapper<Vector2F>,
        orientation: Wrapper<ComplexF>,
        width: Float,
        height: Float,
        cornerRadius: Float,
        expected: MutableRoundedRectangle
    ) = assertEquals(
        expected,
        rectangle.apply { set(center.value, orientation.value, width, height, cornerRadius) }
    )

    @ParameterizedTest
    @MethodSource("setThrowsExceptionArgs")
    fun setThrowsCorrectException(
        center: Wrapper<Vector2F>,
        orientation: Wrapper<ComplexF>,
        width: Float,
        height: Float,
        cornerRadius: Float,
        expectedExceptionClass: Class<Throwable>
    ) {
        val rectangle = MutableRoundedRectangle(
            center = Vector2F.ZERO,
            orientation = ComplexF.ONE,
            width = 1f,
            height = 0.5f,
            cornerRadius = 0.1f
        )
        assertThrows(expectedExceptionClass) {
            rectangle.set(center.value, orientation.value, width, height, cornerRadius)
        }
    }

    @ParameterizedTest
    @MethodSource("setDoesNotThrowExceptionArgs")
    fun setDoesNotThrowException(
        center: Wrapper<Vector2F>,
        orientation: Wrapper<ComplexF>,
        width: Float,
        height: Float,
        cornerRadius: Float
    ) {
        val rectangle = MutableRoundedRectangle(
            center = Vector2F.ZERO,
            orientation = ComplexF.ONE,
            width = 1f,
            height = 0.5f,
            cornerRadius = 0.1f
        )
        assertDoesNotThrow {
            rectangle.set(center.value, orientation.value, width, height, cornerRadius)
        }
    }

    @ParameterizedTest
    @MethodSource("interpolatedArgs")
    fun interpolatedReturnsCorrectValue(
        rectangle: RoundedRectangle, to: RoundedRectangle, by: Float, expected: RoundedRectangle
    ) = assertImmutabilityOf(rectangle) {
        assertImmutabilityOf(to) {
            assertApproximation(expected, rectangle.interpolated(to, by))
        }
    }

    @ParameterizedTest
    @MethodSource("interpolateArgs")
    fun interpolateMutatesRoundedRectangleCorrectly(
        rectangle: MutableRoundedRectangle,
        from: RoundedRectangle,
        to: RoundedRectangle,
        by: Float,
        expected: MutableRoundedRectangle
    ) = assertImmutabilityOf(from) {
        assertImmutabilityOf(to) {
            assertApproximation(expected, rectangle.apply { interpolate(from, to, by) })
        }
    }

    @ParameterizedTest
    @MethodSource("closestPointToArgs")
    fun closestPointToReturnsCorrectValue(
        rectangle: RoundedRectangle, point: Wrapper<Vector2F>, expected: Wrapper<Vector2F>
    ) = assertImmutabilityOf(rectangle) {
        assertApproximation(expected.value, rectangle.closestPointTo(point.value))
    }

    @ParameterizedTest
    @MethodSource("intersectsRayArgs")
    fun intersectsRayReturnsCorrectValue(
        rectangle: RoundedRectangle, ray: Ray, expected: Boolean
    ) = assertImmutabilityOf(rectangle) {
        assertImmutabilityOf(ray) {
            assertEquals(expected, rectangle.intersects(ray))
        }
    }

    @ParameterizedTest
    @MethodSource("containsVector2FArgs")
    fun containsReturnsCorrectValue(
        rectangle: RoundedRectangle, point: Wrapper<Vector2F>, expected: Boolean
    ) = assertImmutabilityOf(rectangle) {
        assertEquals(expected, rectangle.contains(point.value))
    }

    @ParameterizedTest
    @MethodSource("copyArgs")
    fun copyReturnsCorrectValue(
        rectangle: RoundedRectangle,
        center: Wrapper<Vector2F>,
        orientation: Wrapper<ComplexF>,
        width: Float,
        height: Float,
        cornerRadius: Float,
        expected: RoundedRectangle
    ) = assertEquals(
        expected, rectangle.copy(center.value, orientation.value, width, height, cornerRadius)
    )

    @ParameterizedTest
    @MethodSource("copyThrowsExceptionArgs")
    fun copyThrowsCorrectException(
        center: Wrapper<Vector2F>,
        orientation: Wrapper<ComplexF>,
        width: Float,
        height: Float,
        cornerRadius: Float,
        expectedExceptionClass: Class<Throwable>
    ) {
        val rectangle = MutableRoundedRectangle(
            center = Vector2F.ZERO,
            orientation = ComplexF.ONE,
            width = 1f,
            height = 0.5f,
            cornerRadius = 0.1f
        )
        assertThrows(expectedExceptionClass) {
            rectangle.copy(center.value, orientation.value, width, height, cornerRadius)
        }
    }

    @ParameterizedTest
    @MethodSource("copyDoesNotThrowExceptionArgs")
    fun copyDoesNotThrowException(
        center: Wrapper<Vector2F>,
        orientation: Wrapper<ComplexF>,
        width: Float,
        height: Float,
        cornerRadius: Float
    ) {
        val rectangle = MutableRoundedRectangle(
            center = Vector2F.ZERO,
            orientation = ComplexF.ONE,
            width = 1f,
            height = 0.5f,
            cornerRadius = 0.1f
        )
        assertDoesNotThrow {
            rectangle.copy(center.value, orientation.value, width, height, cornerRadius)
        }
    }

    @ParameterizedTest
    @MethodSource("equalsAnyArgs")
    fun equalsReturnsCorrectValue(
        rectangle: MutableRoundedRectangle, other: Any?, expected: Boolean
    ) = assertImmutabilityOf(rectangle) {
        assertEquals(expected, rectangle == other)
    }

    @ParameterizedTest
    @MethodSource("equalsMutableRoundedRectangleArgs")
    fun equalsReturnsCorrectValue(
        rectangle: MutableRoundedRectangle, other: MutableRoundedRectangle, expected: Boolean
    ) = assertImmutabilityOf(rectangle) {
        assertImmutabilityOf(other) {
            assertEquals(expected, rectangle.equals(other))
        }
    }

    @ParameterizedTest
    @MethodSource("hashCodeArgs")
    fun hashCodeReturnsCorrectValue(
        rectangle: MutableRoundedRectangle, other: MutableRoundedRectangle
    ) = assertImmutabilityOf(rectangle) {
        assertImmutabilityOf(other) {
            assertEquals(rectangle.hashCode(), other.hashCode())
        }
    }

    @ParameterizedTest
    @MethodSource("toStringArgs")
    fun toStringReturnsCorrectValue(rectangle: MutableRoundedRectangle, expected: String) =
        assertImmutabilityOf(rectangle) {
            assertEquals(expected, rectangle.toString())
        }

    @ParameterizedTest
    @MethodSource("componentsArgs")
    fun componentsReturnCorrectValues(
        rectangle: RoundedRectangle,
        expectedComponent1: Wrapper<Vector2F>,
        expectedComponent2: Wrapper<ComplexF>,
        expectedComponent3: Float,
        expectedComponent4: Float,
        expectedComponent5: Float
    ) = assertImmutabilityOf(rectangle) {
        val (
            actualComponent1: Vector2F,
            actualComponent2: ComplexF,
            actualComponent3: Float,
            actualComponent4: Float,
            actualComponent5: Float
        ) = rectangle

        assertEquals(expectedComponent1.value, actualComponent1)
        assertEquals(expectedComponent2.value, actualComponent2)
        assertEquals(expectedComponent3, actualComponent3)
        assertEquals(expectedComponent4, actualComponent4)
        assertEquals(expectedComponent5, actualComponent5)
    }

    companion object {
        @JvmStatic
        fun areApproximatelyEqual(
            a: RoundedRectangle, b: RoundedRectangle, tolerance: Float = 0.00001f
        ): Boolean = a.center.isApproximately(b.center, tolerance) and
                a.orientation.isApproximately(b.orientation, tolerance) and
                a.width.isApproximately(b.width, tolerance) and
                a.height.isApproximately(b.height, tolerance) and
                a.cornerRadius.isApproximately(b.cornerRadius, tolerance) and
                a.pointA.isApproximately(b.pointA, tolerance) and
                a.pointB.isApproximately(b.pointB, tolerance) and
                a.pointC.isApproximately(b.pointC, tolerance) and
                a.pointD.isApproximately(b.pointD, tolerance) and
                a.pointE.isApproximately(b.pointE, tolerance) and
                a.pointF.isApproximately(b.pointF, tolerance) and
                a.pointG.isApproximately(b.pointG, tolerance) and
                a.pointH.isApproximately(b.pointH, tolerance) and
                a.cornerCenterA.isApproximately(b.cornerCenterA, tolerance) and
                a.cornerCenterB.isApproximately(b.cornerCenterB, tolerance) and
                a.cornerCenterC.isApproximately(b.cornerCenterC, tolerance) and
                a.cornerCenterD.isApproximately(b.cornerCenterD, tolerance)

        @JvmStatic
        fun areEqual(a: RoundedRectangle, b: RoundedRectangle): Boolean =
            (a.center == b.center) and
                    (a.orientation == b.orientation) and
                    (a.width == b.width) and
                    (a.height == b.height) and
                    (a.cornerRadius == b.cornerRadius) and
                    (a.pointA == b.pointA) and
                    (a.pointB == b.pointB) and
                    (a.pointC == b.pointC) and
                    (a.pointD == b.pointD) and
                    (a.pointE == b.pointE) and
                    (a.pointF == b.pointF) and
                    (a.pointG == b.pointG) and
                    (a.pointH == b.pointH) and
                    (a.cornerCenterA == b.cornerCenterA) and
                    (a.cornerCenterB == b.cornerCenterB) and
                    (a.cornerCenterC == b.cornerCenterC) and
                    (a.cornerCenterD == b.cornerCenterD)

        @JvmStatic
        fun clone(rectangle: RoundedRectangle) = rectangle.copy()

        @JvmStatic
        fun List<Arguments>.mapRoundedRectanglesToDefaultRoundedRectangles() = map { args ->
            val argArray = args.get().map {
                if (it is RoundedRectangle) DefaultRoundedRectangle(
                    it.center, it.orientation, it.width, it.height, it.cornerRadius
                )
                else it
            }.toTypedArray()

            Arguments.of(*argArray)
        }

        @JvmStatic
        fun constructorArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2F.ZERO),
                Wrapper(ComplexF.ONE),
                1f,
                2f,
                0.25f
            ),
            Arguments.of(
                Wrapper(Vector2F.ZERO),
                Wrapper(ComplexF.ONE),
                1f,
                2f,
                0f
            ),
            Arguments.of(
                Wrapper(Vector2F.ZERO),
                Wrapper(ComplexF.ONE),
                1f,
                2f,
                0.5f
            ),
            Arguments.of(
                Wrapper(Vector2F.ZERO),
                Wrapper(ComplexF.ONE),
                0f,
                0f,
                0f
            ),
            Arguments.of(
                Wrapper(Vector2F(-3f, -4f)),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(-60f))),
                8f,
                4f,
                1f
            ),
            Arguments.of(
                Wrapper(Vector2F(6f, -4f)),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(45f))),
                3f,
                4f,
                1.5f
            ),
        )

        @JvmStatic
        fun constructorThrowsExceptionArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2F.ZERO),
                Wrapper(ComplexF.ONE),
                -1f,
                2f,
                0.25f,
                IllegalArgumentException::class.java
            ),
            Arguments.of(
                Wrapper(Vector2F.ZERO),
                Wrapper(ComplexF.ONE),
                1f,
                -2f,
                0.25f,
                IllegalArgumentException::class.java
            ),
            Arguments.of(
                Wrapper(Vector2F.ZERO),
                Wrapper(ComplexF.ONE),
                1f,
                2f,
                -0.25f,
                IllegalArgumentException::class.java
            ),
            Arguments.of(
                Wrapper(Vector2F.ZERO),
                Wrapper(ComplexF.ONE),
                -1f,
                -2f,
                -0.25f,
                IllegalArgumentException::class.java
            ),
            Arguments.of(
                Wrapper(Vector2F.ZERO),
                Wrapper(ComplexF.ONE),
                1f,
                2f,
                0.51f,
                IllegalArgumentException::class.java
            ),
            Arguments.of(
                Wrapper(Vector2F.ZERO),
                Wrapper(ComplexF.ONE),
                2f,
                1f,
                0.51f,
                IllegalArgumentException::class.java
            ),
        )

        @JvmStatic
        fun centerArgs(): List<Arguments> {
            val mutableRoundedRectangleArgs = listOf(
                Arguments.of(
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    Wrapper(Vector2F(-3f, -4f))
                ),
                Arguments.of(
                    MutableRoundedRectangle(
                        center = Vector2F(6f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        width = 3f,
                        height = 4f,
                        cornerRadius = 1.5f
                    ),
                    Wrapper(Vector2F(6f, -4f))
                ),
            )
            val defaultRoundedRectangleArgs = mutableRoundedRectangleArgs
                .mapRoundedRectanglesToDefaultRoundedRectangles()

            return listOf(
                mutableRoundedRectangleArgs,
                defaultRoundedRectangleArgs
            ).flatten()
        }

        @JvmStatic
        fun orientationArgs(): List<Arguments> {
            val mutableRoundedRectangleArgs = listOf(
                Arguments.of(
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(-60f)))
                ),
                Arguments.of(
                    MutableRoundedRectangle(
                        center = Vector2F(6f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        width = 3f,
                        height = 4f,
                        cornerRadius = 1.5f
                    ),
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(45f)))
                ),
            )
            val defaultRoundedRectangleArgs = mutableRoundedRectangleArgs
                .mapRoundedRectanglesToDefaultRoundedRectangles()

            return listOf(
                mutableRoundedRectangleArgs,
                defaultRoundedRectangleArgs
            ).flatten()
        }

        @JvmStatic
        fun widthArgs(): List<Arguments> {
            val mutableRoundedRectangleArgs = listOf(
                Arguments.of(
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    8f
                ),
                Arguments.of(
                    MutableRoundedRectangle(
                        center = Vector2F(6f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        width = 3f,
                        height = 4f,
                        cornerRadius = 1.5f
                    ),
                    3f
                ),
            )
            val defaultRoundedRectangleArgs = mutableRoundedRectangleArgs
                .mapRoundedRectanglesToDefaultRoundedRectangles()

            return listOf(
                mutableRoundedRectangleArgs,
                defaultRoundedRectangleArgs
            ).flatten()
        }

        @JvmStatic
        fun heightArgs(): List<Arguments> {
            val mutableRoundedRectangleArgs = listOf(
                Arguments.of(
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    4f
                ),
                Arguments.of(
                    MutableRoundedRectangle(
                        center = Vector2F(6f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        width = 3f,
                        height = 4f,
                        cornerRadius = 1.5f
                    ),
                    4f
                ),
            )
            val defaultRoundedRectangleArgs = mutableRoundedRectangleArgs
                .mapRoundedRectanglesToDefaultRoundedRectangles()

            return listOf(
                mutableRoundedRectangleArgs,
                defaultRoundedRectangleArgs
            ).flatten()
        }

        @JvmStatic
        fun cornerRadiusArgs(): List<Arguments> {
            val mutableRoundedRectangleArgs = listOf(
                Arguments.of(
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    1f
                ),
                Arguments.of(
                    MutableRoundedRectangle(
                        center = Vector2F(6f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        width = 3f,
                        height = 4f,
                        cornerRadius = 1.5f
                    ),
                    1.5f
                ),
            )
            val defaultRoundedRectangleArgs = mutableRoundedRectangleArgs
                .mapRoundedRectanglesToDefaultRoundedRectangles()

            return listOf(
                mutableRoundedRectangleArgs,
                defaultRoundedRectangleArgs
            ).flatten()
        }

        @JvmStatic
        fun pointsArgs(): List<Arguments> {
            val mutableRoundedRectangleArgs = listOf(
                Arguments.of(
                    MutableRoundedRectangle(
                        center = Vector2F.ZERO,
                        orientation = ComplexF.ONE,
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    Wrapper(Vector2F(3f, 2f)),
                    Wrapper(Vector2F(-3f, 2f)),
                    Wrapper(Vector2F(-4f, 1f)),
                    Wrapper(Vector2F(-4f, -1f)),
                    Wrapper(Vector2F(-3f, -2f)),
                    Wrapper(Vector2F(3f, -2f)),
                    Wrapper(Vector2F(4f, -1f)),
                    Wrapper(Vector2F(4f, 1f))
                ),
                Arguments.of(
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    Wrapper(Vector2F(0.2320509f, -5.5980763f)),
                    Wrapper(Vector2F(-2.767949f, -0.40192366f)),
                    Wrapper(Vector2F(-4.1339746f, -0.03589821f)),
                    Wrapper(Vector2F(-5.8660254f, -1.0358982f)),
                    Wrapper(Vector2F(-6.232051f, -2.4019237f)),
                    Wrapper(Vector2F(-3.232051f, -7.5980763f)),
                    Wrapper(Vector2F(-1.8660256f, -7.964102f)),
                    Wrapper(Vector2F(-0.13397455f, -6.964102f))
                ),
                Arguments.of(
                    MutableRoundedRectangle(
                        center = Vector2F(6f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        width = 3f,
                        height = 4f,
                        cornerRadius = 1.5f
                    ),
                    Wrapper(Vector2F(4.5857863f, -2.5857863f)),
                    Wrapper(Vector2F(4.5857863f, -2.5857863f)),
                    Wrapper(Vector2F(4.5857863f, -4.7071066f)),
                    Wrapper(Vector2F(5.2928934f, -5.4142137f)),
                    Wrapper(Vector2F(7.4142137f, -5.4142137f)),
                    Wrapper(Vector2F(7.4142137f, -5.4142137f)),
                    Wrapper(Vector2F(7.4142137f, -3.2928934f)),
                    Wrapper(Vector2F(6.7071066f, -2.5857863f))
                ),
            )
            val defaultRoundedRectangleArgs = mutableRoundedRectangleArgs
                .mapRoundedRectanglesToDefaultRoundedRectangles()

            return listOf(
                mutableRoundedRectangleArgs,
                defaultRoundedRectangleArgs
            ).flatten()
        }

        @JvmStatic
        fun cornerCentersArgs(): List<Arguments> {
            val mutableRoundedRectangleArgs = listOf(
                Arguments.of(
                    MutableRoundedRectangle(
                        center = Vector2F.ZERO,
                        orientation = ComplexF.ONE,
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    Wrapper(Vector2F(3f, 1f)),
                    Wrapper(Vector2F(-3f, 1f)),
                    Wrapper(Vector2F(-3f, -1f)),
                    Wrapper(Vector2F(3f, -1f))
                ),
                Arguments.of(
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    Wrapper(Vector2F(-0.63397455f, -6.0980763f)),
                    Wrapper(Vector2F(-3.6339746f, -0.90192366f)),
                    Wrapper(Vector2F(-5.3660254f, -1.9019237f)),
                    Wrapper(Vector2F(-2.3660254f, -7.0980763f))
                ),
                Arguments.of(
                    MutableRoundedRectangle(
                        center = Vector2F(6f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        width = 3f,
                        height = 4f,
                        cornerRadius = 1.5f
                    ),
                    Wrapper(Vector2F(5.6464467f, -3.6464467f)),
                    Wrapper(Vector2F(5.6464467f, -3.6464467f)),
                    Wrapper(Vector2F(6.3535533f, -4.3535533f)),
                    Wrapper(Vector2F(6.3535533f, -4.3535533f))
                ),
            )
            val defaultRoundedRectangleArgs = mutableRoundedRectangleArgs
                .mapRoundedRectanglesToDefaultRoundedRectangles()

            return listOf(
                mutableRoundedRectangleArgs,
                defaultRoundedRectangleArgs
            ).flatten()
        }

        @JvmStatic
        fun areaArgs(): List<Arguments> {
            val mutableRoundedRectangleArgs = listOf(
                Arguments.of(
                    MutableRoundedRectangle(
                        center = Vector2F.ZERO,
                        orientation = ComplexF.ONE,
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    31.14159f
                ),
                Arguments.of(
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    31.14159f
                ),
                Arguments.of(
                    MutableRoundedRectangle(
                        center = Vector2F(6f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        width = 3f,
                        height = 4f,
                        cornerRadius = 1.5f
                    ),
                    10.0685835f
                ),
            )
            val defaultRoundedRectangleArgs = mutableRoundedRectangleArgs
                .mapRoundedRectanglesToDefaultRoundedRectangles()

            return listOf(
                mutableRoundedRectangleArgs,
                defaultRoundedRectangleArgs
            ).flatten()
        }

        @JvmStatic
        fun perimeterArgs(): List<Arguments> {
            val mutableRoundedRectangleArgs = listOf(
                Arguments.of(
                    MutableRoundedRectangle(
                        center = Vector2F.ZERO,
                        orientation = ComplexF.ONE,
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    22.28319f
                ),
                Arguments.of(
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    22.28319f
                ),
                Arguments.of(
                    MutableRoundedRectangle(
                        center = Vector2F(6f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        width = 3f,
                        height = 4f,
                        cornerRadius = 1.5f
                    ),
                    11.424778f
                ),
            )
            val defaultRoundedRectangleArgs = mutableRoundedRectangleArgs
                .mapRoundedRectanglesToDefaultRoundedRectangles()

            return listOf(
                mutableRoundedRectangleArgs,
                defaultRoundedRectangleArgs
            ).flatten()
        }

        @JvmStatic
        fun positionArgs(): List<Arguments> = centerArgs()

        @JvmStatic
        fun calibrateArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableRoundedRectangle(
                    center = Vector2F(-3f, -4f),
                    orientation = ComplexF.fromPolar(
                        magnitude = 5f, AngleF.fromDegrees(-60f).radians
                    ),
                    width = 8f,
                    height = 4f,
                    cornerRadius = 1f
                ),
                MutableRoundedRectangle(
                    center = Vector2F(-3f, -4f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                    width = 8f,
                    height = 4f,
                    cornerRadius = 1f
                )
            ),
            Arguments.of(
                MutableRoundedRectangle(
                    center = Vector2F(-3f, -4f),
                    orientation = ComplexF.fromPolar(
                        magnitude = 0.5f, AngleF.fromDegrees(-60f).radians
                    ),
                    width = 8f,
                    height = 4f,
                    cornerRadius = 1f
                ),
                MutableRoundedRectangle(
                    center = Vector2F(-3f, -4f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                    width = 8f,
                    height = 4f,
                    cornerRadius = 1f
                )
            ),
            Arguments.of(
                MutableRoundedRectangle(
                    center = Vector2F(-3f, -4f),
                    orientation = ComplexF.ZERO,
                    width = 8f,
                    height = 4f,
                    cornerRadius = 1f
                ),
                MutableRoundedRectangle(
                    center = Vector2F(-3f, -4f),
                    orientation = ComplexF.ONE,
                    width = 8f,
                    height = 4f,
                    cornerRadius = 1f
                )
            ),
        )

        @JvmStatic
        fun setArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableRoundedRectangle(
                    center = Vector2F(-3f, -4f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                    width = 8f,
                    height = 4f,
                    cornerRadius = 1f
                ),
                Wrapper(Vector2F(-3f, -4f)),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(-60f))),
                8f,
                4f,
                1f,
                MutableRoundedRectangle(
                    center = Vector2F(-3f, -4f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                    width = 8f,
                    height = 4f,
                    cornerRadius = 1f
                )
            ),
            Arguments.of(
                MutableRoundedRectangle(
                    center = Vector2F(-3f, -4f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                    width = 8f,
                    height = 4f,
                    cornerRadius = 1f
                ),
                Wrapper(Vector2F(-3f, -4f)),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(45f))),
                8f,
                5f,
                1.5f,
                MutableRoundedRectangle(
                    center = Vector2F(-3f, -4f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    width = 8f,
                    height = 5f,
                    cornerRadius = 1.5f
                )
            ),
            Arguments.of(
                MutableRoundedRectangle(
                    center = Vector2F(-3f, -4f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                    width = 8f,
                    height = 4f,
                    cornerRadius = 1f
                ),
                Wrapper(Vector2F(6f, -4f)),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(45f))),
                3f,
                5f,
                1.5f,
                MutableRoundedRectangle(
                    center = Vector2F(6f, -4f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    width = 3f,
                    height = 5f,
                    cornerRadius = 1.5f
                )
            )
        )

        @JvmStatic
        fun setThrowsExceptionArgs(): List<Arguments> = constructorThrowsExceptionArgs()

        @JvmStatic
        fun setDoesNotThrowExceptionArgs(): List<Arguments> = constructorArgs()

        @JvmStatic
        fun interpolatedArgs(): List<Arguments> {
            val mutableRoundedRectangleArgs = interpolateArgs().map {
                Arguments.of(*it.get().drop(1).toTypedArray())
            }
            val defaultRoundedRectangleArgs = mutableRoundedRectangleArgs
                .mapRoundedRectanglesToDefaultRoundedRectangles()

            return listOf(
                mutableRoundedRectangleArgs,
                defaultRoundedRectangleArgs
            ).flatten()
        }

        @JvmStatic
        fun interpolateArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableRoundedRectangle(
                    center = Vector2F.ZERO,
                    orientation = ComplexF.ONE,
                    width = 1f,
                    height = 0.5f,
                    cornerRadius = 0.1f
                ),
                MutableRoundedRectangle(
                    center = Vector2F(-3f, -4f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                    width = 8f,
                    height = 4f,
                    cornerRadius = 1f
                ),
                MutableRoundedRectangle(
                    center = Vector2F(-3f, -4f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                    width = 8f,
                    height = 4f,
                    cornerRadius = 1f
                ),
                0.5f,
                MutableRoundedRectangle(
                    center = Vector2F(-3f, -4f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                    width = 8f,
                    height = 4f,
                    cornerRadius = 1f
                )
            ),
            Arguments.of(
                MutableRoundedRectangle(
                    center = Vector2F.ZERO,
                    orientation = ComplexF.ONE,
                    width = 1f,
                    height = 0.5f,
                    cornerRadius = 0.1f
                ),
                MutableRoundedRectangle(
                    center = Vector2F(-3f, -4f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                    width = 8f,
                    height = 4f,
                    cornerRadius = 1.5f
                ),
                MutableRoundedRectangle(
                    center = Vector2F(6f, -1f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    width = 3f,
                    height = 2f,
                    cornerRadius = 1f
                ),
                0f,
                MutableRoundedRectangle(
                    center = Vector2F(-3f, -4f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                    width = 8f,
                    height = 4f,
                    cornerRadius = 1.5f
                )
            ),
            Arguments.of(
                MutableRoundedRectangle(
                    center = Vector2F.ZERO,
                    orientation = ComplexF.ONE,
                    width = 1f,
                    height = 0.5f,
                    cornerRadius = 0.1f
                ),
                MutableRoundedRectangle(
                    center = Vector2F(-3f, -4f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                    width = 8f,
                    height = 4f,
                    cornerRadius = 1.5f
                ),
                MutableRoundedRectangle(
                    center = Vector2F(6f, -1f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    width = 3f,
                    height = 2f,
                    cornerRadius = 1f
                ),
                1f,
                MutableRoundedRectangle(
                    center = Vector2F(6f, -1f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    width = 3f,
                    height = 2f,
                    cornerRadius = 1f
                )
            ),
            Arguments.of(
                MutableRoundedRectangle(
                    center = Vector2F.ZERO,
                    orientation = ComplexF.ONE,
                    width = 1f,
                    height = 0.5f,
                    cornerRadius = 0.1f
                ),
                MutableRoundedRectangle(
                    center = Vector2F(-3f, -4f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                    width = 8f,
                    height = 4f,
                    cornerRadius = 1.5f
                ),
                MutableRoundedRectangle(
                    center = Vector2F(6f, -1f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    width = 3f,
                    height = 2f,
                    cornerRadius = 1f
                ),
                0.5f,
                MutableRoundedRectangle(
                    center = Vector2F(1.5f, -2.5f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-7.5f)),
                    width = 5.5f,
                    height = 3f,
                    cornerRadius = 1.25f
                )
            ),
        )

        @JvmStatic
        fun closestPointToArgs(): List<Arguments> {
            val rectangle = MutableRoundedRectangle(
                center = Vector2F(-3f, -4f),
                orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                width = 8f,
                height = 4f,
                cornerRadius = 1f
            )
            val mutableRoundedRectangleArgs = listOf(
                Arguments.of(
                    rectangle,
                    Wrapper(Vector2F(0.14544821f, -6.5480766f)),
                    Wrapper(Vector2F(0.14544821f, -6.5480766f))
                ),
                Arguments.of(
                    rectangle,
                    Wrapper(Vector2F(0.31865335f, -6.648077f)),
                    Wrapper(Vector2F(0.2320509f, -6.5980763f))
                ),
                Arguments.of(
                    rectangle,
                    Wrapper(Vector2F(0.26602554f, -6.0980763f)),
                    Wrapper(Vector2F(0.26602554f, -6.0980763f))
                ),
                Arguments.of(
                    rectangle,
                    Wrapper(Vector2F(0.46602535f, -6.0980763f)),
                    Wrapper(Vector2F(0.36602545f, -6.0980763f))
                ),
                Arguments.of(
                    rectangle,
                    Wrapper(Vector2F(-0.6045518f, -4.349038f)),
                    Wrapper(Vector2F(-0.6045518f, -4.349038f))
                ),
                Arguments.of(
                    rectangle,
                    Wrapper(Vector2F(-0.43134665f, -4.249038f)),
                    Wrapper(Vector2F(-0.5179491f, -4.2990384f))
                ),
                Arguments.of(
                    rectangle,
                    Wrapper(Vector2F(-2.1045516f, -1.7509618f)),
                    Wrapper(Vector2F(-2.1045516f, -1.7509618f))
                ),
                Arguments.of(
                    rectangle,
                    Wrapper(Vector2F(-1.9313467f, -1.6509619f)),
                    Wrapper(Vector2F(-2.017949f, -1.7009618f))
                ),
                Arguments.of(
                    rectangle,
                    Wrapper(Vector2F(-3.1839743f, -0.1225009f)),
                    Wrapper(Vector2F(-3.1839743f, -0.1225009f))
                ),
                Arguments.of(
                    rectangle,
                    Wrapper(Vector2F(-3.0839744f, 0.05070448f)),
                    Wrapper(Vector2F(-3.1339743f, -0.03589821f))
                ),
                Arguments.of(
                    rectangle,
                    Wrapper(Vector2F(-3.6339743f, -0.0019237995f)),
                    Wrapper(Vector2F(-3.6339743f, -0.0019237995f))
                ),
                Arguments.of(
                    rectangle,
                    Wrapper(Vector2F(-3.6339746f, 0.19807673f)),
                    Wrapper(Vector2F(-3.6339746f, 0.09807634f))
                ),
                Arguments.of(
                    rectangle,
                    Wrapper(Vector2F(-4.5169873f, -0.37250066f)),
                    Wrapper(Vector2F(-4.5169873f, -0.37250066f))
                ),
                Arguments.of(
                    rectangle,
                    Wrapper(Vector2F(-4.616987f, -0.19929576f)),
                    Wrapper(Vector2F(-4.566987f, -0.2858982f))
                ),
                Arguments.of(
                    rectangle,
                    Wrapper(Vector2F(-5.383013f, -0.87250066f)),
                    Wrapper(Vector2F(-5.383013f, -0.87250066f))
                ),
                Arguments.of(
                    rectangle,
                    Wrapper(Vector2F(-5.483012f, -0.69929576f)),
                    Wrapper(Vector2F(-5.4330125f, -0.7858982f))
                ),
                Arguments.of(
                    rectangle,
                    Wrapper(Vector2F(-6.145448f, -1.4519236f)),
                    Wrapper(Vector2F(-6.145448f, -1.4519236f))
                ),
                Arguments.of(
                    rectangle,
                    Wrapper(Vector2F(-6.318653f, -1.3519232f)),
                    Wrapper(Vector2F(-6.232051f, -1.4019237f))
                ),
                Arguments.of(
                    rectangle,
                    Wrapper(Vector2F(-6.2660255f, -1.9019237f)),
                    Wrapper(Vector2F(-6.2660255f, -1.9019237f))
                ),
                Arguments.of(
                    rectangle,
                    Wrapper(Vector2F(-6.4660254f, -1.9019237f)),
                    Wrapper(Vector2F(-6.3660254f, -1.9019237f))
                ),
                Arguments.of(
                    rectangle,
                    Wrapper(Vector2F(-5.395448f, -3.6509619f)),
                    Wrapper(Vector2F(-5.395448f, -3.6509619f))
                ),
                Arguments.of(
                    rectangle,
                    Wrapper(Vector2F(-5.568653f, -3.7509618f)),
                    Wrapper(Vector2F(-5.482051f, -3.7009618f))
                ),
                Arguments.of(
                    rectangle,
                    Wrapper(Vector2F(-3.8954484f, -6.249038f)),
                    Wrapper(Vector2F(-3.8954484f, -6.249038f))
                ),
                Arguments.of(
                    rectangle,
                    Wrapper(Vector2F(-4.068653f, -6.349038f)),
                    Wrapper(Vector2F(-3.982051f, -6.299038f))
                ),
                Arguments.of(
                    rectangle,
                    Wrapper(Vector2F(-2.8160257f, -7.877499f)),
                    Wrapper(Vector2F(-2.8160257f, -7.877499f))
                ),
                Arguments.of(
                    rectangle,
                    Wrapper(Vector2F(-2.9160256f, -8.050705f)),
                    Wrapper(Vector2F(-2.8660257f, -7.964102f))
                ),
                Arguments.of(
                    rectangle,
                    Wrapper(Vector2F(-2.366026f, -7.9980764f)),
                    Wrapper(Vector2F(-2.366026f, -7.9980764f))
                ),
                Arguments.of(
                    rectangle,
                    Wrapper(Vector2F(-2.3660257f, -8.198076f)),
                    Wrapper(Vector2F(-2.366026f, -8.098076f))
                ),
                Arguments.of(
                    rectangle,
                    Wrapper(Vector2F(-1.4830128f, -7.6274996f)),
                    Wrapper(Vector2F(-1.4830128f, -7.6274996f))
                ),
                Arguments.of(
                    rectangle,
                    Wrapper(Vector2F(-1.383013f, -7.800704f)),
                    Wrapper(Vector2F(-1.4330128f, -7.714102f))
                ),
                Arguments.of(
                    rectangle,
                    Wrapper(Vector2F(-0.6169872f, -7.1274996f)),
                    Wrapper(Vector2F(-0.6169872f, -7.1274996f))
                ),
                Arguments.of(
                    rectangle,
                    Wrapper(Vector2F(-0.51698756f, -7.300704f)),
                    Wrapper(Vector2F(-0.5669875f, -7.214102f))
                ),
                Arguments.of(
                    rectangle,
                    Wrapper(rectangle.center),
                    Wrapper(rectangle.center)
                ),
            )
            val defaultRoundedRectangleArgs = mutableRoundedRectangleArgs
                .mapRoundedRectanglesToDefaultRoundedRectangles()

            return listOf(
                mutableRoundedRectangleArgs,
                defaultRoundedRectangleArgs
            ).flatten()
        }

        @JvmStatic
        fun intersectsRayArgs(): List<Arguments> = RayTests.intersectsRoundedRectangleArgs().map {
            val args = it.get()
            Arguments.of(args[1], args[0], args[2])
        }

        @JvmStatic
        fun containsVector2FArgs(): List<Arguments> {
            val rectangle = MutableRoundedRectangle(
                center = Vector2F(-3f, -4f),
                orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                width = 8f,
                height = 4f,
                cornerRadius = 1f
            )
            val mutableRoundedRectangleArgs = listOf(
                Arguments.of(
                    rectangle, Wrapper(Vector2F(0.14544821f, -6.5480766f)), true
                ),
                Arguments.of(
                    rectangle, Wrapper(Vector2F(0.31865335f, -6.648077f)), false
                ),
                Arguments.of(
                    rectangle, Wrapper(Vector2F(0.26602554f, -6.0980763f)), true
                ),
                Arguments.of(
                    rectangle, Wrapper(Vector2F(0.46602535f, -6.0980763f)), false
                ),
                Arguments.of(
                    rectangle, Wrapper(Vector2F(-0.6045518f, -4.349038f)), true
                ),
                Arguments.of(
                    rectangle, Wrapper(Vector2F(-0.43134665f, -4.249038f)), false
                ),
                Arguments.of(
                    rectangle, Wrapper(Vector2F(-2.1045516f, -1.7509618f)), true
                ),
                Arguments.of(
                    rectangle, Wrapper(Vector2F(-1.9313467f, -1.6509619f)), false
                ),
                Arguments.of(
                    rectangle, Wrapper(Vector2F(-3.1839743f, -0.1225009f)), true
                ),
                Arguments.of(
                    rectangle, Wrapper(Vector2F(-3.0839744f, 0.05070448f)), false
                ),
                Arguments.of(
                    rectangle, Wrapper(Vector2F(-3.6339743f, -0.0019237995f)), true
                ),
                Arguments.of(
                    rectangle, Wrapper(Vector2F(-3.6339746f, 0.19807673f)), false
                ),
                Arguments.of(
                    rectangle, Wrapper(Vector2F(-4.5169873f, -0.37250066f)), true
                ),
                Arguments.of(
                    rectangle, Wrapper(Vector2F(-4.616987f, -0.19929576f)), false
                ),
                Arguments.of(
                    rectangle, Wrapper(Vector2F(-5.383013f, -0.87250066f)), true
                ),
                Arguments.of(
                    rectangle, Wrapper(Vector2F(-5.483012f, -0.69929576f)), false
                ),
                Arguments.of(
                    rectangle, Wrapper(Vector2F(-6.145448f, -1.4519236f)), true
                ),
                Arguments.of(
                    rectangle, Wrapper(Vector2F(-6.318653f, -1.3519232f)), false
                ),
                Arguments.of(
                    rectangle, Wrapper(Vector2F(-6.2660255f, -1.9019237f)), true
                ),
                Arguments.of(
                    rectangle, Wrapper(Vector2F(-6.4660254f, -1.9019237f)), false
                ),
                Arguments.of(
                    rectangle, Wrapper(Vector2F(-5.395448f, -3.6509619f)), true
                ),
                Arguments.of(
                    rectangle, Wrapper(Vector2F(-5.568653f, -3.7509618f)), false
                ),
                Arguments.of(
                    rectangle, Wrapper(Vector2F(-3.8954484f, -6.249038f)), true
                ),
                Arguments.of(
                    rectangle, Wrapper(Vector2F(-4.068653f, -6.349038f)), false
                ),
                Arguments.of(
                    rectangle, Wrapper(Vector2F(-2.8160257f, -7.877499f)), true
                ),
                Arguments.of(
                    rectangle, Wrapper(Vector2F(-2.9160256f, -8.050705f)), false
                ),
                Arguments.of(
                    rectangle, Wrapper(Vector2F(-2.366026f, -7.9980764f)), true
                ),
                Arguments.of(
                    rectangle, Wrapper(Vector2F(-2.3660257f, -8.198076f)), false
                ),
                Arguments.of(
                    rectangle, Wrapper(Vector2F(-1.4830128f, -7.6274996f)), true
                ),
                Arguments.of(
                    rectangle, Wrapper(Vector2F(-1.383013f, -7.800704f)), false
                ),
                Arguments.of(
                    rectangle, Wrapper(Vector2F(-0.6169872f, -7.1274996f)), true
                ),
                Arguments.of(
                    rectangle, Wrapper(Vector2F(-0.51698756f, -7.300704f)), false
                ),
                Arguments.of(
                    rectangle, Wrapper(rectangle.center), true
                ),
            )
            val defaultRoundedRectangleArgs = mutableRoundedRectangleArgs
                .mapRoundedRectanglesToDefaultRoundedRectangles()

            return listOf(
                mutableRoundedRectangleArgs,
                defaultRoundedRectangleArgs
            ).flatten()
        }

        @JvmStatic
        fun copyArgs(): List<Arguments> {
            val mutableRoundedRectangleArgs = setArgs()
            val defaultRoundedRectangleArgs = mutableRoundedRectangleArgs
                .mapRoundedRectanglesToDefaultRoundedRectangles()

            return listOf(
                mutableRoundedRectangleArgs,
                defaultRoundedRectangleArgs
            ).flatten()
        }

        @JvmStatic
        fun copyThrowsExceptionArgs(): List<Arguments> = constructorThrowsExceptionArgs()

        @JvmStatic
        fun copyDoesNotThrowExceptionArgs(): List<Arguments> = constructorArgs()

        @JvmStatic
        fun equalsAnyArgs(): List<Arguments> = equalsMutableRoundedRectangleArgs() + listOf(
            Arguments.of(
                MutableRoundedRectangle(
                    center = Vector2F(-3f, -4f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                    width = 8f,
                    height = 4f,
                    cornerRadius = 1f
                ),
                null,
                false
            ),
            Arguments.of(
                MutableRoundedRectangle(
                    center = Vector2F(-3f, -4f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                    width = 8f,
                    height = 4f,
                    cornerRadius = 1f
                ),
                DefaultRoundedRectangle(
                    center = Vector2F(-3f, -4f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                    width = 8f,
                    height = 4f,
                    cornerRadius = 1f
                ),
                true
            ),
            Arguments.of(
                MutableRoundedRectangle(
                    center = Vector2F(-3f, -4f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                    width = 8f,
                    height = 4f,
                    cornerRadius = 1f
                ),
                DefaultRoundedRectangle(
                    center = Vector2F(-3f, -4f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60.1f)),
                    width = 8f,
                    height = 4f,
                    cornerRadius = 1f
                ),
                false
            ),
        )

        @JvmStatic
        fun equalsMutableRoundedRectangleArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableRoundedRectangle(
                    center = Vector2F(-3f, -4f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                    width = 8f,
                    height = 4f,
                    cornerRadius = 1f
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
                MutableRoundedRectangle(
                    center = Vector2F(-3f, -4f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                    width = 8f,
                    height = 4f,
                    cornerRadius = 1f
                ),
                MutableRoundedRectangle(
                    center = Vector2F(-3f, -4f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60.1f)),
                    width = 8f,
                    height = 4f,
                    cornerRadius = 1f
                ),
                false
            ),
        )

        @JvmStatic
        fun hashCodeArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableRoundedRectangle(
                    center = Vector2F(-3f, -4f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                    width = 8f,
                    height = 4f,
                    cornerRadius = 1f
                ),
                MutableRoundedRectangle(
                    center = Vector2F(-3f, -4f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                    width = 8f,
                    height = 4f,
                    cornerRadius = 1f
                )
            ),
            Arguments.of(
                MutableRoundedRectangle(
                    center = Vector2F(6f, -4f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    width = 3f,
                    height = 5f,
                    cornerRadius = 1.5f
                ),
                MutableRoundedRectangle(
                    center = Vector2F(6f, -4f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    width = 3f,
                    height = 5f,
                    cornerRadius = 1.5f
                )
            ),
        )

        @JvmStatic
        fun toStringArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableRoundedRectangle(
                    center = Vector2F(-3f, -4f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                    width = 8f,
                    height = 4f,
                    cornerRadius = 1f
                ),
                "RoundedRectangle(" +
                        "center=${Vector2F(-3f, -4f)}, " +
                        "orientation=${ComplexF.fromAngle(AngleF.fromDegrees(-60f))}, " +
                        "width=${8f}, " +
                        "height=${4f}, " +
                        "cornerRadius=${1f})"
            ),
            Arguments.of(
                MutableRoundedRectangle(
                    center = Vector2F(6f, -4f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    width = 3f,
                    height = 5f,
                    cornerRadius = 1.5f
                ),
                "RoundedRectangle(" +
                        "center=${Vector2F(6f, -4f)}, " +
                        "orientation=${ComplexF.fromAngle(AngleF.fromDegrees(45f))}, " +
                        "width=${3f}, " +
                        "height=${5f}, " +
                        "cornerRadius=${1.5f})"
            ),
        )

        @JvmStatic
        fun componentsArgs(): List<Arguments> {
            val mutableRoundedRectangleArgs = listOf(
                Arguments.of(
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    Wrapper(Vector2F(-3f, -4f)),
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(-60f))),
                    8f,
                    4f,
                    1f
                ),
                Arguments.of(
                    MutableRoundedRectangle(
                        center = Vector2F(6f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        width = 3f,
                        height = 5f,
                        cornerRadius = 1.5f
                    ),
                    Wrapper(Vector2F(6f, -4f)),
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(45f))),
                    3f,
                    5f,
                    1.5f
                ),
            )
            val defaultRoundedRectangleArgs = mutableRoundedRectangleArgs
                .mapRoundedRectanglesToDefaultRoundedRectangles()

            return listOf(
                mutableRoundedRectangleArgs,
                defaultRoundedRectangleArgs
            ).flatten()
        }
    }
}