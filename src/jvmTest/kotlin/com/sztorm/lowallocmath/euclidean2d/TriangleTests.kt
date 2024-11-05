package com.sztorm.lowallocmath.euclidean2d

import com.sztorm.lowallocmath.AngleF
import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F
import com.sztorm.lowallocmath.euclidean2d.utils.DefaultTriangle
import com.sztorm.lowallocmath.euclidean2d.utils.assertApproximation
import com.sztorm.lowallocmath.euclidean2d.utils.assertEquals
import com.sztorm.lowallocmath.euclidean2d.utils.assertImmutabilityOf
import com.sztorm.lowallocmath.isApproximately
import com.sztorm.lowallocmath.utils.Wrapper
import com.sztorm.lowallocmath.utils.assertApproximation
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

class TriangleTests {
    @ParameterizedTest
    @MethodSource("constructorVector2FComplexFFloatComplexFFloatComplexFFloatArgs")
    fun constructorCreatesCorrectTriangle(
        centroid: Wrapper<Vector2F>,
        pathRotorA: Wrapper<ComplexF>,
        pointDistanceA: Float,
        pathRotorAB: Wrapper<ComplexF>,
        pointDistanceB: Float,
        pathRotorAC: Wrapper<ComplexF>,
        pointDistanceC: Float
    ) {
        val mutableTriangle = MutableTriangle(
            centroid.value,
            pathRotorA.value,
            pointDistanceA,
            pathRotorAB.value,
            pointDistanceB,
            pathRotorAC.value,
            pointDistanceC
        )
        val triangle = Triangle(
            centroid.value,
            pathRotorA.value,
            pointDistanceA,
            pathRotorAB.value,
            pointDistanceB,
            pathRotorAC.value,
            pointDistanceC
        )
        assertEquals(centroid.value, mutableTriangle.centroid)
        assertEquals(pathRotorA.value, mutableTriangle.pathRotorA)
        assertEquals(pointDistanceA, mutableTriangle.pointDistanceA)
        assertEquals(pathRotorAB.value, mutableTriangle.pathRotorAB)
        assertEquals(pointDistanceB, mutableTriangle.pointDistanceB)
        assertEquals(pathRotorAC.value, mutableTriangle.pathRotorAC)
        assertEquals(pointDistanceC, mutableTriangle.pointDistanceC)

        assertEquals(centroid.value, triangle.centroid)
        assertEquals(pathRotorA.value, triangle.pathRotorA)
        assertEquals(pointDistanceA, triangle.pointDistanceA)
        assertEquals(pathRotorAB.value, triangle.pathRotorAB)
        assertEquals(pointDistanceB, triangle.pointDistanceB)
        assertEquals(pathRotorAC.value, triangle.pathRotorAC)
        assertEquals(pointDistanceC, triangle.pointDistanceC)

        assertApproximation(triangle.pointA, mutableTriangle.pointA)
        assertApproximation(triangle.pointB, mutableTriangle.pointB)
        assertApproximation(triangle.pointC, mutableTriangle.pointC)
    }

    @ParameterizedTest
    @MethodSource("constructorVector2FComplexFFloatComplexFFloatComplexFFloatThrowsExceptionArgs")
    fun constructorThrowsCorrectException(
        centroid: Wrapper<Vector2F>,
        pathRotorA: Wrapper<ComplexF>,
        pointDistanceA: Float,
        pathRotorAB: Wrapper<ComplexF>,
        pointDistanceB: Float,
        pathRotorAC: Wrapper<ComplexF>,
        pointDistanceC: Float,
        expectedExceptionClass: Class<Throwable>
    ) {
        assertThrows(expectedExceptionClass) {
            MutableTriangle(
                centroid.value,
                pathRotorA.value,
                pointDistanceA,
                pathRotorAB.value,
                pointDistanceB,
                pathRotorAC.value,
                pointDistanceC
            )
        }
        assertThrows(expectedExceptionClass) {
            Triangle(
                centroid.value,
                pathRotorA.value,
                pointDistanceA,
                pathRotorAB.value,
                pointDistanceB,
                pathRotorAC.value,
                pointDistanceC
            )
        }
    }

    @ParameterizedTest
    @MethodSource("constructorVector2Fx3Args")
    fun constructorCreatesCorrectTriangle(
        pointA: Wrapper<Vector2F>, pointB: Wrapper<Vector2F>, pointC: Wrapper<Vector2F>
    ) {
        val mutableTriangle = MutableTriangle(pointA.value, pointB.value, pointC.value)
        val triangle = Triangle(pointA.value, pointB.value, pointC.value)

        assertApproximation(pointA.value, mutableTriangle.pointA)
        assertApproximation(pointB.value, mutableTriangle.pointB)
        assertApproximation(pointC.value, mutableTriangle.pointC)

        assertApproximation(pointA.value, triangle.pointA)
        assertApproximation(pointB.value, triangle.pointB)
        assertApproximation(pointC.value, triangle.pointC)

        assertApproximation(triangle.centroid, mutableTriangle.centroid)
        assertApproximation(triangle.pathRotorA, mutableTriangle.pathRotorA)
        assertApproximation(triangle.pointDistanceA, mutableTriangle.pointDistanceA)
        assertApproximation(triangle.pathRotorAB, mutableTriangle.pathRotorAB)
        assertApproximation(triangle.pointDistanceB, mutableTriangle.pointDistanceB)
        assertApproximation(triangle.pathRotorAC, mutableTriangle.pathRotorAC)
        assertApproximation(triangle.pointDistanceC, mutableTriangle.pointDistanceC)
    }

    @ParameterizedTest
    @MethodSource("centroidArgs")
    fun centroidReturnsCorrectValue(triangle: Triangle, expected: Wrapper<Vector2F>) =
        assertImmutabilityOf(triangle) {
            assertApproximation(expected.value, triangle.centroid)
        }

    @ParameterizedTest
    @MethodSource("pointsArgs")
    fun pointsReturnCorrectValues(
        triangle: Triangle,
        expectedPointA: Wrapper<Vector2F>,
        expectedPointB: Wrapper<Vector2F>,
        expectedPointC: Wrapper<Vector2F>
    ) = assertImmutabilityOf(triangle) {
        assertApproximation(expectedPointA.value, triangle.pointA)
        assertApproximation(expectedPointB.value, triangle.pointB)
        assertApproximation(expectedPointC.value, triangle.pointC)
    }

    @ParameterizedTest
    @MethodSource("pathRotorArgs")
    fun pathRotorsReturnCorrectValues(
        triangle: Triangle,
        expectedPathRotorA: Wrapper<ComplexF>,
        expectedPathRotorAB: Wrapper<ComplexF>,
        expectedPathRotorAC: Wrapper<ComplexF>
    ) = assertImmutabilityOf(triangle) {
        assertApproximation(expectedPathRotorA.value, triangle.pathRotorA)
        assertApproximation(expectedPathRotorAB.value, triangle.pathRotorAB)
        assertApproximation(expectedPathRotorAC.value, triangle.pathRotorAC)
    }

    @ParameterizedTest
    @MethodSource("pointDistanceArgs")
    fun pointDistancesReturnCorrectValues(
        triangle: Triangle,
        expectedPointDistanceA: Float,
        expectedPointDistanceB: Float,
        expectedPointDistanceC: Float
    ) = assertImmutabilityOf(triangle) {
        assertApproximation(expectedPointDistanceA, triangle.pointDistanceA)
        assertApproximation(expectedPointDistanceB, triangle.pointDistanceB)
        assertApproximation(expectedPointDistanceC, triangle.pointDistanceC)
    }

    @ParameterizedTest
    @MethodSource("areaArgs")
    fun areaReturnsCorrectValue(triangle: Triangle, expected: Float) =
        assertImmutabilityOf(triangle) {
            assertApproximation(expected, triangle.area)
        }

    @ParameterizedTest
    @MethodSource("perimeterArgs")
    fun perimeterReturnsCorrectValue(triangle: Triangle, expected: Float) =
        assertImmutabilityOf(triangle) {
            assertApproximation(expected, triangle.perimeter)
        }

    @ParameterizedTest
    @MethodSource("sideLengthsArgs")
    fun sideLengthsReturnsCorrectValues(
        triangle: Triangle,
        expectedSideLengthAB: Float,
        expectedSideLengthBC: Float,
        expectedSideLengthCA: Float,
    ) = assertImmutabilityOf(triangle) {
        assertApproximation(expectedSideLengthAB, triangle.sideLengthAB)
        assertApproximation(expectedSideLengthBC, triangle.sideLengthBC)
        assertApproximation(expectedSideLengthCA, triangle.sideLengthAC)
    }

    @ParameterizedTest
    @MethodSource("positionArgs")
    fun positionReturnsCorrectValue(triangle: Triangle, expected: Wrapper<Vector2F>) =
        assertImmutabilityOf(triangle) {
            assertApproximation(expected.value, triangle.position)
        }

    @ParameterizedTest
    @MethodSource("orientationArgs")
    fun orientationReturnsCorrectValue(triangle: Triangle, expected: Wrapper<ComplexF>) =
        assertImmutabilityOf(triangle) {
            assertApproximation(expected.value, triangle.orientation)
        }

    @ParameterizedTest
    @MethodSource("incenterArgs")
    fun incenterReturnsCorrectValue(triangle: Triangle, expected: Wrapper<Vector2F>) =
        assertImmutabilityOf(triangle) {
            assertApproximation(expected.value, triangle.incenter)
        }

    @ParameterizedTest
    @MethodSource("circumcenterArgs")
    fun circumcenterReturnsCorrectValue(triangle: Triangle, expected: Wrapper<Vector2F>) =
        assertImmutabilityOf(triangle) {
            assertApproximation(expected.value, triangle.circumcenter)
        }

    @ParameterizedTest
    @MethodSource("orthocenterArgs")
    fun orthocenterReturnsCorrectValue(triangle: Triangle, expected: Wrapper<Vector2F>) =
        assertImmutabilityOf(triangle) {
            assertApproximation(expected.value, triangle.orthocenter)
        }

    @ParameterizedTest
    @MethodSource("calibrateArgs")
    fun calibrateMutatesTriangleCorrectly(triangle: MutableTriangle, expected: MutableTriangle) =
        assertApproximation(expected, triangle.apply { calibrate() })

    @ParameterizedTest
    @MethodSource("setArgs")
    fun setMutatesTriangleCorrectly(
        triangle: MutableTriangle,
        centroid: Wrapper<Vector2F>,
        pathRotorA: Wrapper<ComplexF>,
        pointDistanceA: Float,
        pathRotorAB: Wrapper<ComplexF>,
        pointDistanceB: Float,
        pathRotorAC: Wrapper<ComplexF>,
        pointDistanceC: Float,
        expected: MutableTriangle
    ) = assertEquals(
        expected,
        triangle.apply {
            set(
                centroid.value,
                pathRotorA.value,
                pointDistanceA,
                pathRotorAB.value,
                pointDistanceB,
                pathRotorAC.value,
                pointDistanceC
            )
        }
    )

    @ParameterizedTest
    @MethodSource("setThrowsExceptionArgs")
    fun setThrowsCorrectException(
        centroid: Wrapper<Vector2F>,
        pathRotorA: Wrapper<ComplexF>,
        pointDistanceA: Float,
        pathRotorAB: Wrapper<ComplexF>,
        pointDistanceB: Float,
        pathRotorAC: Wrapper<ComplexF>,
        pointDistanceC: Float,
        expectedExceptionClass: Class<Throwable>
    ) {
        val triangle = MutableTriangle(
            centroid = Vector2F.ZERO,
            pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(0f)),
            pointDistanceA = 1f,
            pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(180f)),
            pointDistanceB = 1f,
            pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
            pointDistanceC = 1f
        )
        assertThrows(expectedExceptionClass) {
            triangle.set(
                centroid.value,
                pathRotorA.value,
                pointDistanceA,
                pathRotorAB.value,
                pointDistanceB,
                pathRotorAC.value,
                pointDistanceC
            )
        }
    }

    @ParameterizedTest
    @MethodSource("setDoesNotThrowExceptionArgs")
    fun setDoesNotThrowException(
        centroid: Wrapper<Vector2F>,
        pathRotorA: Wrapper<ComplexF>,
        pointDistanceA: Float,
        pathRotorAB: Wrapper<ComplexF>,
        pointDistanceB: Float,
        pathRotorAC: Wrapper<ComplexF>,
        pointDistanceC: Float
    ) {
        val triangle = MutableTriangle(
            centroid = Vector2F.ZERO,
            pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(0f)),
            pointDistanceA = 1f,
            pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(180f)),
            pointDistanceB = 1f,
            pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
            pointDistanceC = 1f
        )
        assertDoesNotThrow {
            triangle.set(
                centroid.value,
                pathRotorA.value,
                pointDistanceA,
                pathRotorAB.value,
                pointDistanceB,
                pathRotorAC.value,
                pointDistanceC
            )
        }
    }

    @ParameterizedTest
    @MethodSource("interpolatedArgs")
    fun interpolatedReturnsCorrectValue(
        triangle: Triangle, to: Triangle, by: Float, expected: Triangle
    ) = assertImmutabilityOf(triangle) {
        assertImmutabilityOf(to) {
            assertApproximation(expected, triangle.interpolated(to, by))
        }
    }

    @ParameterizedTest
    @MethodSource("interpolateArgs")
    fun interpolateMutatesTriangleCorrectly(
        triangle: MutableTriangle,
        from: Triangle,
        to: Triangle,
        by: Float,
        expected: MutableTriangle
    ) = assertImmutabilityOf(from) {
        assertImmutabilityOf(to) {
            assertApproximation(expected, triangle.apply { interpolate(from, to, by) })
        }
    }

    @ParameterizedTest
    @MethodSource("closestPointToArgs")
    fun closestPointToReturnsCorrectValue(
        triangle: Triangle, point: Wrapper<Vector2F>, expected: Wrapper<Vector2F>
    ) = assertImmutabilityOf(triangle) {
        assertApproximation(expected.value, triangle.closestPointTo(point.value))
    }

    @ParameterizedTest
    @MethodSource("intersectsRayArgs")
    fun intersectsRayReturnsCorrectValue(triangle: Triangle, ray: Ray, expected: Boolean) =
        assertImmutabilityOf(triangle) {
            assertImmutabilityOf(ray) {
                assertEquals(expected, triangle.intersects(ray))
            }
        }

    @ParameterizedTest
    @MethodSource("containsVector2FArgs")
    fun containsReturnsCorrectValue(
        triangle: Triangle, point: Wrapper<Vector2F>, expected: Boolean
    ) = assertImmutabilityOf(triangle) {
        assertEquals(expected, triangle.contains(point.value))
    }

    @ParameterizedTest
    @MethodSource("copyArgs")
    fun copyReturnsCorrectValue(
        triangle: Triangle,
        centroid: Wrapper<Vector2F>,
        pathRotorA: Wrapper<ComplexF>,
        pointDistanceA: Float,
        pathRotorAB: Wrapper<ComplexF>,
        pointDistanceB: Float,
        pathRotorAC: Wrapper<ComplexF>,
        pointDistanceC: Float,
        expected: Triangle
    ) = assertEquals(
        expected,
        triangle.copy(
            centroid.value,
            pathRotorA.value,
            pointDistanceA,
            pathRotorAB.value,
            pointDistanceB,
            pathRotorAC.value,
            pointDistanceC
        )
    )

    @ParameterizedTest
    @MethodSource("copyThrowsExceptionArgs")
    fun copyThrowsCorrectException(
        centroid: Wrapper<Vector2F>,
        pathRotorA: Wrapper<ComplexF>,
        pointDistanceA: Float,
        pathRotorAB: Wrapper<ComplexF>,
        pointDistanceB: Float,
        pathRotorAC: Wrapper<ComplexF>,
        pointDistanceC: Float,
        expectedExceptionClass: Class<Throwable>
    ) {
        val triangle = MutableTriangle(
            centroid = Vector2F.ZERO,
            pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(0f)),
            pointDistanceA = 1f,
            pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(180f)),
            pointDistanceB = 1f,
            pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
            pointDistanceC = 1f
        )
        assertThrows(expectedExceptionClass) {
            triangle.copy(
                centroid.value,
                pathRotorA.value,
                pointDistanceA,
                pathRotorAB.value,
                pointDistanceB,
                pathRotorAC.value,
                pointDistanceC
            )
        }
    }

    @ParameterizedTest
    @MethodSource("copyDoesNotThrowExceptionArgs")
    fun copyDoesNotThrowException(
        centroid: Wrapper<Vector2F>,
        pathRotorA: Wrapper<ComplexF>,
        pointDistanceA: Float,
        pathRotorAB: Wrapper<ComplexF>,
        pointDistanceB: Float,
        pathRotorAC: Wrapper<ComplexF>,
        pointDistanceC: Float
    ) {
        val triangle = MutableTriangle(
            centroid = Vector2F.ZERO,
            pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(0f)),
            pointDistanceA = 1f,
            pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(180f)),
            pointDistanceB = 1f,
            pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
            pointDistanceC = 1f
        )
        assertDoesNotThrow {
            triangle.copy(
                centroid.value,
                pathRotorA.value,
                pointDistanceA,
                pathRotorAB.value,
                pointDistanceB,
                pathRotorAC.value,
                pointDistanceC
            )
        }
    }

    @ParameterizedTest
    @MethodSource("equalsAnyArgs")
    fun equalsReturnsCorrectValue(triangle: MutableTriangle, other: Any?, expected: Boolean) =
        assertImmutabilityOf(triangle) {
            assertEquals(expected, triangle == other)
        }

    @ParameterizedTest
    @MethodSource("equalsMutableTriangleArgs")
    fun equalsReturnsCorrectValue(
        triangle: MutableTriangle, other: MutableTriangle, expected: Boolean
    ) = assertImmutabilityOf(triangle) {
        assertImmutabilityOf(other) {
            assertEquals(expected, triangle.equals(other))
        }
    }

    @ParameterizedTest
    @MethodSource("hashCodeArgs")
    fun hashCodeReturnsCorrectValue(triangle: MutableTriangle, other: MutableTriangle) =
        assertImmutabilityOf(triangle) {
            assertImmutabilityOf(other) {
                assertEquals(triangle.hashCode(), other.hashCode())
            }
        }

    @ParameterizedTest
    @MethodSource("toStringArgs")
    fun toStringReturnsCorrectValue(triangle: MutableTriangle, expected: String) =
        assertImmutabilityOf(triangle) {
            assertEquals(expected, triangle.toString())
        }

    @ParameterizedTest
    @MethodSource("componentsArgs")
    fun componentsReturnCorrectValues(
        triangle: Triangle,
        expectedComponent1: Wrapper<Vector2F>,
        expectedComponent2: Wrapper<ComplexF>,
        expectedComponent3: Float,
        expectedComponent4: Wrapper<ComplexF>,
        expectedComponent5: Float,
        expectedComponent6: Wrapper<ComplexF>,
        expectedComponent7: Float
    ) = assertImmutabilityOf(triangle) {
        val (
            actualComponent1: Vector2F,
            actualComponent2: ComplexF,
            actualComponent3: Float,
            actualComponent4: ComplexF,
            actualComponent5: Float,
            actualComponent6: ComplexF,
            actualComponent7: Float
        ) = triangle

        assertEquals(expectedComponent1.value, actualComponent1)
        assertEquals(expectedComponent2.value, actualComponent2)
        assertEquals(expectedComponent3, actualComponent3)
        assertEquals(expectedComponent4.value, actualComponent4)
        assertEquals(expectedComponent5, actualComponent5)
        assertEquals(expectedComponent6.value, actualComponent6)
        assertEquals(expectedComponent7, actualComponent7)
    }

    companion object {
        @JvmStatic
        fun areApproximatelyEqual(a: Triangle, b: Triangle, tolerance: Float = 0.00001f): Boolean =
            a.centroid.isApproximately(b.centroid, tolerance) and
                    a.pathRotorA.isApproximately(b.pathRotorA, tolerance) and
                    a.pointDistanceA.isApproximately(b.pointDistanceA, tolerance) and
                    a.pathRotorAB.isApproximately(b.pathRotorAB, tolerance) and
                    a.pointDistanceB.isApproximately(b.pointDistanceB, tolerance) and
                    a.pathRotorAC.isApproximately(b.pathRotorAC, tolerance) and
                    a.pointDistanceC.isApproximately(b.pointDistanceC, tolerance) and
                    a.pointA.isApproximately(b.pointA, tolerance) and
                    a.pointB.isApproximately(b.pointB, tolerance) and
                    a.pointC.isApproximately(b.pointC, tolerance)

        @JvmStatic
        fun areEqual(a: Triangle, b: Triangle): Boolean =
            (a.centroid == b.centroid) and
                    (a.pathRotorA == b.pathRotorA) and
                    (a.pointDistanceA == b.pointDistanceA) and
                    (a.pathRotorAB == b.pathRotorAB) and
                    (a.pointDistanceB == b.pointDistanceB) and
                    (a.pathRotorAC == b.pathRotorAC) and
                    (a.pointDistanceC == b.pointDistanceC) and
                    (a.pointA == b.pointA) and
                    (a.pointB == b.pointB) and
                    (a.pointC == b.pointC)

        @JvmStatic
        fun clone(triangle: Triangle) = triangle.copy()

        @JvmStatic
        fun List<Arguments>.mapTrianglesToDefaultTriangles() = map { args ->
            val argArray = args.get().map {
                if (it is Triangle) DefaultTriangle(
                    it.centroid,
                    it.pathRotorA,
                    it.pointDistanceA,
                    it.pathRotorAB,
                    it.pointDistanceB,
                    it.pathRotorAC,
                    it.pointDistanceC
                ) else it
            }.toTypedArray()

            Arguments.of(*argArray)
        }

        @JvmStatic
        fun constructorVector2FComplexFFloatComplexFFloatComplexFFloatArgs(
        ): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2F(-0.3333333f, 3f)),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(-164.74489f))),
                3.8005848f,
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(141.54628f))),
                2.538591f,
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(-138.94519f))),
                2.4037008f
            ),
            Arguments.of(
                Wrapper(Vector2F(-1.3333333f, -2.6666667f)),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(100.30485f))),
                3.72678f,
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(91.00509f))),
                1.6996733f,
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f))),
                4.068852f
            ),
            Arguments.of(
                Wrapper(Vector2F(8f, -4.8453f)),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(90f))),
                2.3094f,
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(-120f))),
                2.3094f,
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(120f))),
                2.3094f
            ),
        )

        @JvmStatic
        fun constructorVector2FComplexFFloatComplexFFloatComplexFFloatThrowsExceptionArgs(
        ): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2F.ZERO),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(0f))),
                -1f,
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(180f))),
                1f,
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(90f))),
                1f,
                IllegalArgumentException::class.java
            ),
            Arguments.of(
                Wrapper(Vector2F.ZERO),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(0f))),
                1f,
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(180f))),
                -1f,
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(90f))),
                1f,
                IllegalArgumentException::class.java
            ),
            Arguments.of(
                Wrapper(Vector2F.ZERO),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(0f))),
                1f,
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(180f))),
                1f,
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(90f))),
                -1f,
                IllegalArgumentException::class.java
            ),
            Arguments.of(
                Wrapper(Vector2F.ZERO),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(0f))),
                -1f,
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(180f))),
                -0.5f,
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(90f))),
                1f,
                IllegalArgumentException::class.java
            ),
            Arguments.of(
                Wrapper(Vector2F.ZERO),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(0f))),
                -0.5f,
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(180f))),
                1f,
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(90f))),
                -1f,
                IllegalArgumentException::class.java
            ),
            Arguments.of(
                Wrapper(Vector2F.ZERO),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(0f))),
                1f,
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(180f))),
                -1f,
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(90f))),
                -0.5f,
                IllegalArgumentException::class.java
            ),
            Arguments.of(
                Wrapper(Vector2F.ZERO),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(0f))),
                -0.1f,
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(180f))),
                -1f,
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(90f))),
                -0.5f,
                IllegalArgumentException::class.java
            ),
        )

        @JvmStatic
        fun constructorVector2Fx3Args(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2F(-4f, 2f)),
                Wrapper(Vector2F(2f, 2f)),
                Wrapper(Vector2F(1f, 5f))
            ),
            Arguments.of(
                Wrapper(Vector2F(-2f, 1f)),
                Wrapper(Vector2F(-3f, -3f)),
                Wrapper(Vector2F(1f, -6f))
            ),
            Arguments.of(
                Wrapper(Vector2F(8f, -2.535898f)),
                Wrapper(Vector2F(10f, -6f)),
                Wrapper(Vector2F(6f, -6f))
            ),
        )

        @JvmStatic
        fun centroidArgs(): List<Arguments> {
            val mutableTriangleArgs = listOf(
                Arguments.of(
                    MutableTriangle(
                        centroid = Vector2F(-0.3333333f, 3f),
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(-164.74489f)),
                        pointDistanceA = 3.8005848f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(141.54628f)),
                        pointDistanceB = 2.538591f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-138.94519f)),
                        pointDistanceC = 2.4037008f
                    ),
                    Wrapper(Vector2F(-0.3333333f, 3f))
                ),
                Arguments.of(
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(100.30485f)),
                        pointDistanceA = 3.72678f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(91.00509f)),
                        pointDistanceB = 1.6996733f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f)),
                        pointDistanceC = 4.068852f
                    ),
                    Wrapper(Vector2F(-1.3333333f, -2.6666667f))
                ),
                Arguments.of(
                    MutableTriangle(
                        centroid = Vector2F(8f, -4.8453f),
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                        pointDistanceA = 2.3094f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(-120f)),
                        pointDistanceB = 2.3094f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        pointDistanceC = 2.3094f
                    ),
                    Wrapper(Vector2F(8f, -4.8453f))
                ),
            )
            val defaultTriangleArgs = mutableTriangleArgs.mapTrianglesToDefaultTriangles()

            return listOf(
                mutableTriangleArgs,
                defaultTriangleArgs
            ).flatten()
        }

        @JvmStatic
        fun pathRotorArgs(): List<Arguments> {
            val mutableTriangleArgs = listOf(
                Arguments.of(
                    MutableTriangle(
                        centroid = Vector2F(-0.3333333f, 3f),
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(-164.74489f)),
                        pointDistanceA = 3.8005848f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(141.54628f)),
                        pointDistanceB = 2.538591f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-138.94519f)),
                        pointDistanceC = 2.4037008f
                    ),
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(-164.74489f))),
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(141.54628f))),
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(-138.94519f)))
                ),
                Arguments.of(
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(100.30485f)),
                        pointDistanceA = 3.72678f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(91.00509f)),
                        pointDistanceB = 1.6996733f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f)),
                        pointDistanceC = 4.068852f
                    ),
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(100.30485f))),
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(91.00509f))),
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f)))
                ),
                Arguments.of(
                    MutableTriangle(
                        centroid = Vector2F(8f, -4.8453f),
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                        pointDistanceA = 2.3094f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(-120f)),
                        pointDistanceB = 2.3094f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        pointDistanceC = 2.3094f
                    ),
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(90f))),
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(-120f))),
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(120f)))
                ),
            )
            val defaultTriangleArgs = mutableTriangleArgs.mapTrianglesToDefaultTriangles()

            return listOf(
                mutableTriangleArgs,
                defaultTriangleArgs
            ).flatten()
        }

        @JvmStatic
        fun pointDistanceArgs(): List<Arguments> {
            val mutableTriangleArgs = listOf(
                Arguments.of(
                    MutableTriangle(
                        centroid = Vector2F(-0.3333333f, 3f),
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(-164.74489f)),
                        pointDistanceA = 3.8005848f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(141.54628f)),
                        pointDistanceB = 2.538591f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-138.94519f)),
                        pointDistanceC = 2.4037008f
                    ),
                    3.8005848f,
                    2.538591f,
                    2.4037008f
                ),
                Arguments.of(
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(100.30485f)),
                        pointDistanceA = 3.72678f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(91.00509f)),
                        pointDistanceB = 1.6996733f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f)),
                        pointDistanceC = 4.068852f
                    ),
                    3.72678f,
                    1.6996733f,
                    4.068852f
                ),
                Arguments.of(
                    MutableTriangle(
                        centroid = Vector2F(8f, -4.8453f),
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                        pointDistanceA = 2.3094f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(-120f)),
                        pointDistanceB = 2.3094f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        pointDistanceC = 2.3094f
                    ),
                    2.3094f,
                    2.3094f,
                    2.3094f
                ),
            )
            val defaultTriangleArgs = mutableTriangleArgs.mapTrianglesToDefaultTriangles()

            return listOf(
                mutableTriangleArgs,
                defaultTriangleArgs
            ).flatten()
        }

        @JvmStatic
        fun pointsArgs(): List<Arguments> {
            val mutableTriangleArgs = listOf(
                Arguments.of(
                    MutableTriangle(
                        centroid = Vector2F(-0.3333333f, 3f),
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(-164.74489f)),
                        pointDistanceA = 3.8005848f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(141.54628f)),
                        pointDistanceB = 2.538591f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-138.94519f)),
                        pointDistanceC = 2.4037008f
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    Wrapper(Vector2F(2f, 2f)),
                    Wrapper(Vector2F(1f, 5f))
                ),
                Arguments.of(
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(100.30485f)),
                        pointDistanceA = 3.72678f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(91.00509f)),
                        pointDistanceB = 1.6996733f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f)),
                        pointDistanceC = 4.068852f
                    ),
                    Wrapper(Vector2F(-2f, 1f)),
                    Wrapper(Vector2F(-3f, -3f)),
                    Wrapper(Vector2F(1f, -6f))
                ),
                Arguments.of(
                    MutableTriangle(
                        centroid = Vector2F(8f, -4.8453f),
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                        pointDistanceA = 2.3094f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(-120f)),
                        pointDistanceB = 2.3094f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        pointDistanceC = 2.3094f
                    ),
                    Wrapper(Vector2F(8f, -2.535898f)),
                    Wrapper(Vector2F(10f, -6f)),
                    Wrapper(Vector2F(6f, -6f))
                ),
            )
            val defaultTriangleArgs = mutableTriangleArgs.mapTrianglesToDefaultTriangles()

            return listOf(
                mutableTriangleArgs,
                defaultTriangleArgs
            ).flatten()
        }

        @JvmStatic
        fun areaArgs(): List<Arguments> {
            val mutableTriangleArgs = listOf(
                Arguments.of(
                    MutableTriangle(
                        centroid = Vector2F(-0.3333333f, 3f),
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(-164.74489f)),
                        pointDistanceA = 3.8005848f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(141.54628f)),
                        pointDistanceB = 2.538591f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-138.94519f)),
                        pointDistanceC = 2.4037008f
                    ),
                    9f
                ),
                Arguments.of(
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(100.30485f)),
                        pointDistanceA = 3.72678f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(91.00509f)),
                        pointDistanceB = 1.6996733f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f)),
                        pointDistanceC = 4.068852f
                    ),
                    9.5f
                ),
                Arguments.of(
                    MutableTriangle(
                        centroid = Vector2F(8f, -4.8453f),
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                        pointDistanceA = 2.3094f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(-120f)),
                        pointDistanceB = 2.3094f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        pointDistanceC = 2.3094f
                    ),
                    6.928203f
                ),
            )
            val defaultTriangleArgs = mutableTriangleArgs.mapTrianglesToDefaultTriangles()

            return listOf(
                mutableTriangleArgs,
                defaultTriangleArgs
            ).flatten()
        }

        @JvmStatic
        fun perimeterArgs(): List<Arguments> {
            val mutableTriangleArgs = listOf(
                Arguments.of(
                    MutableTriangle(
                        centroid = Vector2F(-0.3333333f, 3f),
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(-164.74489f)),
                        pointDistanceA = 3.8005848f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(141.54628f)),
                        pointDistanceB = 2.538591f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-138.94519f)),
                        pointDistanceC = 2.4037008f
                    ),
                    14.99323f
                ),
                Arguments.of(
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(100.30485f)),
                        pointDistanceA = 3.72678f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(91.00509f)),
                        pointDistanceB = 1.6996733f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f)),
                        pointDistanceC = 4.068852f
                    ),
                    16.73888f
                ),
                Arguments.of(
                    MutableTriangle(
                        centroid = Vector2F(8f, -4.8453f),
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                        pointDistanceA = 2.3094f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(-120f)),
                        pointDistanceB = 2.3094f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        pointDistanceC = 2.3094f
                    ),
                    12f
                ),
            )
            val defaultTriangleArgs = mutableTriangleArgs.mapTrianglesToDefaultTriangles()

            return listOf(
                mutableTriangleArgs,
                defaultTriangleArgs
            ).flatten()
        }

        @JvmStatic
        fun sideLengthsArgs(): List<Arguments> {
            val mutableTriangleArgs = listOf(
                Arguments.of(
                    MutableTriangle(
                        centroid = Vector2F(-0.3333333f, 3f),
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(-164.74489f)),
                        pointDistanceA = 3.8005848f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(141.54628f)),
                        pointDistanceB = 2.538591f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-138.94519f)),
                        pointDistanceC = 2.4037008f
                    ),
                    6f,
                    3.1622777f,
                    5.8309517f
                ),
                Arguments.of(
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(100.30485f)),
                        pointDistanceA = 3.72678f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(91.00509f)),
                        pointDistanceB = 1.6996733f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f)),
                        pointDistanceC = 4.068852f
                    ),
                    4.1231055f,
                    5.0f,
                    7.615773f
                ),
                Arguments.of(
                    MutableTriangle(
                        centroid = Vector2F(8f, -4.8453f),
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                        pointDistanceA = 2.3094f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(-120f)),
                        pointDistanceB = 2.3094f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        pointDistanceC = 2.3094f
                    ),
                    4f,
                    4f,
                    4f
                ),
            )
            val defaultTriangleArgs = mutableTriangleArgs.mapTrianglesToDefaultTriangles()

            return listOf(
                mutableTriangleArgs,
                defaultTriangleArgs
            ).flatten()
        }

        @JvmStatic
        fun positionArgs(): List<Arguments> = centroidArgs()

        @JvmStatic
        fun orientationArgs(): List<Arguments> {
            val mutableTriangleArgs = listOf(
                Arguments.of(
                    MutableTriangle(
                        centroid = Vector2F(-0.3333333f, 3f),
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(-164.74489f)),
                        pointDistanceA = 3.8005848f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(141.54628f)),
                        pointDistanceB = 2.538591f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-138.94519f)),
                        pointDistanceC = 2.4037008f
                    ),
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(-164.74489f)))
                ),
                Arguments.of(
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(100.30485f)),
                        pointDistanceA = 3.72678f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(91.00509f)),
                        pointDistanceB = 1.6996733f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f)),
                        pointDistanceC = 4.068852f
                    ),
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(100.30485f)))
                ),
                Arguments.of(
                    MutableTriangle(
                        centroid = Vector2F(8f, -4.8453f),
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                        pointDistanceA = 2.3094f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(-120f)),
                        pointDistanceB = 2.3094f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        pointDistanceC = 2.3094f
                    ),
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(90f)))
                ),
            )
            val defaultTriangleArgs = mutableTriangleArgs.mapTrianglesToDefaultTriangles()

            return listOf(
                mutableTriangleArgs,
                defaultTriangleArgs
            ).flatten()
        }

        @JvmStatic
        fun incenterArgs(): List<Arguments> {
            val mutableTriangleArgs = listOf(
                Arguments.of(
                    MutableTriangle(
                        centroid = Vector2F(-0.3333333f, 3f),
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(-164.74489f)),
                        pointDistanceA = 3.8005848f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(141.54628f)),
                        pointDistanceB = 2.538591f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-138.94519f)),
                        pointDistanceC = 2.4037008f
                    ),
                    Wrapper(Vector2F(0.3343371f, 3.200542f))
                ),
                Arguments.of(
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(100.30485f)),
                        pointDistanceA = 3.72678f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(91.00509f)),
                        pointDistanceB = 1.6996733f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f)),
                        pointDistanceC = 4.068852f
                    ),
                    Wrapper(Vector2F(-1.7160178f, -2.544134f))
                ),
                Arguments.of(
                    MutableTriangle(
                        centroid = Vector2F(8f, -4.8453f),
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                        pointDistanceA = 2.3094f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(-120f)),
                        pointDistanceB = 2.3094f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        pointDistanceC = 2.3094f
                    ),
                    Wrapper(Vector2F(8f, -4.8453f))
                ),
            )
            val defaultTriangleArgs = mutableTriangleArgs.mapTrianglesToDefaultTriangles()

            return listOf(
                mutableTriangleArgs,
                defaultTriangleArgs
            ).flatten()
        }

        @JvmStatic
        fun circumcenterArgs(): List<Arguments> {
            val mutableTriangleArgs = listOf(
                Arguments.of(
                    MutableTriangle(
                        centroid = Vector2F(-0.3333333f, 3f),
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(-164.74489f)),
                        pointDistanceA = 3.8005848f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(141.54628f)),
                        pointDistanceB = 2.538591f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-138.94519f)),
                        pointDistanceC = 2.4037008f
                    ),
                    Wrapper(Vector2F(-1f, 2.6666667f))
                ),
                Arguments.of(
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(100.30485f)),
                        pointDistanceA = 3.72678f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(91.00509f)),
                        pointDistanceB = 1.6996733f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f)),
                        pointDistanceC = 4.068852f
                    ),
                    Wrapper(Vector2F(0.9736842f, -1.868421f))
                ),
                Arguments.of(
                    MutableTriangle(
                        centroid = Vector2F(8f, -4.8453f),
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                        pointDistanceA = 2.3094f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(-120f)),
                        pointDistanceB = 2.3094f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        pointDistanceC = 2.3094f
                    ),
                    Wrapper(Vector2F(8f, -4.8453f))
                ),
            )
            val defaultTriangleArgs = mutableTriangleArgs.mapTrianglesToDefaultTriangles()

            return listOf(
                mutableTriangleArgs,
                defaultTriangleArgs
            ).flatten()
        }

        @JvmStatic
        fun orthocenterArgs(): List<Arguments> {
            val mutableTriangleArgs = listOf(
                Arguments.of(
                    MutableTriangle(
                        centroid = Vector2F(-0.3333333f, 3f),
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(-164.74489f)),
                        pointDistanceA = 3.8005848f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(141.54628f)),
                        pointDistanceB = 2.538591f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-138.94519f)),
                        pointDistanceC = 2.4037008f
                    ),
                    Wrapper(Vector2F(1f, 3.6666667f))
                ),
                Arguments.of(
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(100.30485f)),
                        pointDistanceA = 3.72678f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(91.00509f)),
                        pointDistanceB = 1.6996733f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f)),
                        pointDistanceC = 4.068852f
                    ),
                    Wrapper(Vector2F(-5.947368f, -4.263158f))
                ),
                Arguments.of(
                    MutableTriangle(
                        centroid = Vector2F(8f, -4.8453f),
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                        pointDistanceA = 2.3094f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(-120f)),
                        pointDistanceB = 2.3094f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        pointDistanceC = 2.3094f
                    ),
                    Wrapper(Vector2F(8f, -4.8453f))
                ),
            )
            val defaultTriangleArgs = mutableTriangleArgs.mapTrianglesToDefaultTriangles()

            return listOf(
                mutableTriangleArgs,
                defaultTriangleArgs
            ).flatten()
        }

        @JvmStatic
        fun calibrateArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableTriangle(
                    centroid = Vector2F(-0.3333333f, 3f),
                    pathRotorA = ComplexF.fromPolar(
                        magnitude = 7.1f, AngleF.fromDegrees(-164.74489f).radians
                    ),
                    pointDistanceA = 3.8005848f,
                    pathRotorAB = ComplexF.fromPolar(
                        magnitude = 1.6f, AngleF.fromDegrees(141.54628f).radians
                    ),
                    pointDistanceB = 2.538591f,
                    pathRotorAC = ComplexF.fromPolar(
                        magnitude = 3.8f, AngleF.fromDegrees(-138.94519f).radians
                    ),
                    pointDistanceC = 2.4037008f
                ),
                MutableTriangle(
                    centroid = Vector2F(-0.3333333f, 3f),
                    pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(-164.74489f)),
                    pointDistanceA = 3.8005848f,
                    pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(141.54628f)),
                    pointDistanceB = 2.538591f,
                    pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-138.94519f)),
                    pointDistanceC = 2.4037008f
                )
            ),
            Arguments.of(
                MutableTriangle(
                    centroid = Vector2F(-0.3333333f, 3f),
                    pathRotorA = ComplexF.fromPolar(
                        magnitude = 0.71f, AngleF.fromDegrees(-164.74489f).radians
                    ),
                    pointDistanceA = 3.8005848f,
                    pathRotorAB = ComplexF.fromPolar(
                        magnitude = 0.16f, AngleF.fromDegrees(141.54628f).radians
                    ),
                    pointDistanceB = 2.538591f,
                    pathRotorAC = ComplexF.fromPolar(
                        magnitude = 0.38f, AngleF.fromDegrees(-138.94519f).radians
                    ),
                    pointDistanceC = 2.4037008f
                ),
                MutableTriangle(
                    centroid = Vector2F(-0.3333333f, 3f),
                    pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(-164.74489f)),
                    pointDistanceA = 3.8005848f,
                    pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(141.54628f)),
                    pointDistanceB = 2.538591f,
                    pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-138.94519f)),
                    pointDistanceC = 2.4037008f
                )
            ),
            Arguments.of(
                MutableTriangle(
                    centroid = Vector2F(-0.3333333f, 3f),
                    pathRotorA = ComplexF.ZERO,
                    pointDistanceA = 3.8005848f,
                    pathRotorAB = ComplexF.ZERO,
                    pointDistanceB = 2.538591f,
                    pathRotorAC = ComplexF.ZERO,
                    pointDistanceC = 2.4037008f
                ),
                MutableTriangle(
                    centroid = Vector2F(-0.3333333f, 3f),
                    pathRotorA = ComplexF.ONE,
                    pointDistanceA = 3.8005848f,
                    pathRotorAB = ComplexF.ONE,
                    pointDistanceB = 2.538591f,
                    pathRotorAC = ComplexF.ONE,
                    pointDistanceC = 2.4037008f
                )
            ),
        )

        @JvmStatic
        fun setArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableTriangle(
                    centroid = Vector2F(-0.3333333f, 3f),
                    pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(-164.74489f)),
                    pointDistanceA = 3.8005848f,
                    pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(141.54628f)),
                    pointDistanceB = 2.538591f,
                    pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-138.94519f)),
                    pointDistanceC = 2.4037008f
                ),
                Wrapper(Vector2F(-0.3333333f, 3f)),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(-164.74489f))),
                3.8005848f,
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(141.54628f))),
                2.538591f,
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(-138.94519f))),
                2.4037008f,
                MutableTriangle(
                    centroid = Vector2F(-0.3333333f, 3f),
                    pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(-164.74489f)),
                    pointDistanceA = 3.8005848f,
                    pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(141.54628f)),
                    pointDistanceB = 2.538591f,
                    pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-138.94519f)),
                    pointDistanceC = 2.4037008f
                )
            ),
            Arguments.of(
                MutableTriangle(
                    centroid = Vector2F(-0.3333333f, 3f),
                    pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(-164.74489f)),
                    pointDistanceA = 3.8005848f,
                    pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(141.54628f)),
                    pointDistanceB = 2.538591f,
                    pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-138.94519f)),
                    pointDistanceC = 2.4037008f
                ),
                Wrapper(Vector2F(-1.3333333f, -2.6666667f)),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(-164.74489f))),
                3.8005848f,
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(141.54628f))),
                2.538591f,
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(109.73689f))),
                4.068852f,
                MutableTriangle(
                    centroid = Vector2F(-1.3333333f, -2.6666667f),
                    pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(-164.74489f)),
                    pointDistanceA = 3.8005848f,
                    pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(141.54628f)),
                    pointDistanceB = 2.538591f,
                    pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(109.73689f)),
                    pointDistanceC = 4.068852f
                )
            ),
            Arguments.of(
                MutableTriangle(
                    centroid = Vector2F(-0.3333333f, 3f),
                    pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(-164.74489f)),
                    pointDistanceA = 3.8005848f,
                    pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(141.54628f)),
                    pointDistanceB = 2.538591f,
                    pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-138.94519f)),
                    pointDistanceC = 2.4037008f
                ),
                Wrapper(Vector2F(-1.3333333f, -2.6666667f)),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(100.30485f))),
                3.72678f,
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(91.00509f))),
                1.6996733f,
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f))),
                4.068852f,
                MutableTriangle(
                    centroid = Vector2F(-1.3333333f, -2.6666667f),
                    pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(100.30485f)),
                    pointDistanceA = 3.72678f,
                    pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(91.00509f)),
                    pointDistanceB = 1.6996733f,
                    pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f)),
                    pointDistanceC = 4.068852f
                )
            ),
        )

        @JvmStatic
        fun setThrowsExceptionArgs(): List<Arguments> =
            constructorVector2FComplexFFloatComplexFFloatComplexFFloatThrowsExceptionArgs()

        @JvmStatic
        fun setDoesNotThrowExceptionArgs(): List<Arguments> =
            constructorVector2FComplexFFloatComplexFFloatComplexFFloatArgs()

        @JvmStatic
        fun interpolatedArgs(): List<Arguments> {
            val mutableTriangleArgs = interpolateArgs().map {
                Arguments.of(*it.get().drop(1).toTypedArray())
            }
            val defaultTriangleArgs = mutableTriangleArgs.mapTrianglesToDefaultTriangles()

            return listOf(
                mutableTriangleArgs,
                defaultTriangleArgs
            ).flatten()
        }

        @JvmStatic
        fun interpolateArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableTriangle(
                    centroid = Vector2F.ZERO,
                    pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(0f)),
                    pointDistanceA = 1f,
                    pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(180f)),
                    pointDistanceB = 1f,
                    pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                    pointDistanceC = 1f
                ),
                MutableTriangle(
                    centroid = Vector2F(-0.3333333f, 3f),
                    pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(-164.74489f)),
                    pointDistanceA = 3.8005848f,
                    pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(141.54628f)),
                    pointDistanceB = 2.538591f,
                    pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-138.94519f)),
                    pointDistanceC = 2.4037008f
                ),
                MutableTriangle(
                    centroid = Vector2F(-0.3333333f, 3f),
                    pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(-164.74489f)),
                    pointDistanceA = 3.8005848f,
                    pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(141.54628f)),
                    pointDistanceB = 2.538591f,
                    pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-138.94519f)),
                    pointDistanceC = 2.4037008f
                ),
                0.5f,
                MutableTriangle(
                    centroid = Vector2F(-0.3333333f, 3f),
                    pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(-164.74489f)),
                    pointDistanceA = 3.8005848f,
                    pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(141.54628f)),
                    pointDistanceB = 2.538591f,
                    pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-138.94519f)),
                    pointDistanceC = 2.4037008f
                )
            ),
            Arguments.of(
                MutableTriangle(
                    centroid = Vector2F.ZERO,
                    pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(0f)),
                    pointDistanceA = 1f,
                    pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(180f)),
                    pointDistanceB = 1f,
                    pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                    pointDistanceC = 1f
                ),
                MutableTriangle(
                    centroid = Vector2F(-0.3333333f, 3f),
                    pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(-164.74489f)),
                    pointDistanceA = 3.8005848f,
                    pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(141.54628f)),
                    pointDistanceB = 2.538591f,
                    pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-138.94519f)),
                    pointDistanceC = 2.4037008f
                ),
                MutableTriangle(
                    centroid = Vector2F(-1.3333333f, -2.6666667f),
                    pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(100.30485f)),
                    pointDistanceA = 3.72678f,
                    pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(91.00509f)),
                    pointDistanceB = 1.6996733f,
                    pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f)),
                    pointDistanceC = 4.068852f
                ),
                0f,
                MutableTriangle(
                    centroid = Vector2F(-0.3333333f, 3f),
                    pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(-164.74489f)),
                    pointDistanceA = 3.8005848f,
                    pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(141.54628f)),
                    pointDistanceB = 2.538591f,
                    pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-138.94519f)),
                    pointDistanceC = 2.4037008f
                )
            ),
            Arguments.of(
                MutableTriangle(
                    centroid = Vector2F.ZERO,
                    pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(0f)),
                    pointDistanceA = 1f,
                    pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(180f)),
                    pointDistanceB = 1f,
                    pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                    pointDistanceC = 1f
                ),
                MutableTriangle(
                    centroid = Vector2F(-0.3333333f, 3f),
                    pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(-164.74489f)),
                    pointDistanceA = 3.8005848f,
                    pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(141.54628f)),
                    pointDistanceB = 2.538591f,
                    pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-138.94519f)),
                    pointDistanceC = 2.4037008f
                ),
                MutableTriangle(
                    centroid = Vector2F(-1.3333333f, -2.6666667f),
                    pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(100.30485f)),
                    pointDistanceA = 3.72678f,
                    pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(91.00509f)),
                    pointDistanceB = 1.6996733f,
                    pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f)),
                    pointDistanceC = 4.068852f
                ),
                0.25f,
                MutableTriangle(
                    centroid = Vector2F(-0.5833333f, 1.5833333f),
                    pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(171.51755f)),
                    pointDistanceA = 3.7821336f,
                    pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(132.96468f)),
                    pointDistanceB = 2.1986036f,
                    pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-144.83484f)),
                    pointDistanceC = 2.79351f
                )
            ),
            Arguments.of(
                MutableTriangle(
                    centroid = Vector2F.ZERO,
                    pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(0f)),
                    pointDistanceA = 1f,
                    pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(180f)),
                    pointDistanceB = 1f,
                    pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                    pointDistanceC = 1f
                ),
                MutableTriangle(
                    centroid = Vector2F(-0.3333333f, 3f),
                    pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(-164.74489f)),
                    pointDistanceA = 3.8005848f,
                    pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(141.54628f)),
                    pointDistanceB = 2.538591f,
                    pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-138.94519f)),
                    pointDistanceC = 2.4037008f
                ),
                MutableTriangle(
                    centroid = Vector2F(-1.3333333f, -2.6666667f),
                    pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(100.30485f)),
                    pointDistanceA = 3.72678f,
                    pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(91.00509f)),
                    pointDistanceB = 1.6996733f,
                    pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f)),
                    pointDistanceC = 4.068852f
                ),
                0.5f,
                MutableTriangle(
                    centroid = Vector2F(-0.8333333f, 0.16666667f),
                    pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(147.77998f)),
                    pointDistanceA = 3.7636826f,
                    pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(121.61397f)),
                    pointDistanceB = 1.9246825f,
                    pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-149.24788f)),
                    pointDistanceC = 3.2055113f
                )
            ),
            Arguments.of(
                MutableTriangle(
                    centroid = Vector2F.ZERO,
                    pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(0f)),
                    pointDistanceA = 1f,
                    pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(180f)),
                    pointDistanceB = 1f,
                    pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                    pointDistanceC = 1f
                ),
                MutableTriangle(
                    centroid = Vector2F(-0.3333333f, 3f),
                    pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(-164.74489f)),
                    pointDistanceA = 3.8005848f,
                    pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(141.54628f)),
                    pointDistanceB = 2.538591f,
                    pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-138.94519f)),
                    pointDistanceC = 2.4037008f
                ),
                MutableTriangle(
                    centroid = Vector2F(-1.3333333f, -2.6666667f),
                    pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(100.30485f)),
                    pointDistanceA = 3.72678f,
                    pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(91.00509f)),
                    pointDistanceB = 1.6996733f,
                    pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f)),
                    pointDistanceC = 4.068852f
                ),
                0.75f,
                MutableTriangle(
                    centroid = Vector2F(-1.0833333f, -1.25f),
                    pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(124.04243f)),
                    pointDistanceA = 3.7452312f,
                    pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(107.28284f)),
                    pointDistanceB = 1.7481649f,
                    pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-152.6406f)),
                    pointDistanceC = 3.6321602f
                )
            ),
            Arguments.of(
                MutableTriangle(
                    centroid = Vector2F.ZERO,
                    pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(0f)),
                    pointDistanceA = 1f,
                    pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(180f)),
                    pointDistanceB = 1f,
                    pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                    pointDistanceC = 1f
                ),
                MutableTriangle(
                    centroid = Vector2F(-0.3333333f, 3f),
                    pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(-164.74489f)),
                    pointDistanceA = 3.8005848f,
                    pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(141.54628f)),
                    pointDistanceB = 2.538591f,
                    pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-138.94519f)),
                    pointDistanceC = 2.4037008f
                ),
                MutableTriangle(
                    centroid = Vector2F(-1.3333333f, -2.6666667f),
                    pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(100.30485f)),
                    pointDistanceA = 3.72678f,
                    pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(91.00509f)),
                    pointDistanceB = 1.6996733f,
                    pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f)),
                    pointDistanceC = 4.068852f
                ),
                1f,
                MutableTriangle(
                    centroid = Vector2F(-1.3333333f, -2.6666667f),
                    pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(100.30485f)),
                    pointDistanceA = 3.72678f,
                    pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(91.00509f)),
                    pointDistanceB = 1.6996733f,
                    pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f)),
                    pointDistanceC = 4.068852f
                )
            ),
        )

        @JvmStatic
        fun closestPointToArgs(): List<Arguments> {
            val triangleA = MutableTriangle(
                centroid = Vector2F(-0.3333333f, 3f),
                pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(-164.74489f)),
                pointDistanceA = 3.8005848f,
                pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(141.54628f)),
                pointDistanceB = 2.538591f,
                pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-138.94519f)),
                pointDistanceC = 2.4037008f
            )
            val triangleAArgs = listOf(
                Arguments.of(
                    triangleA,
                    Wrapper(Vector2F(-3.903629f, 2.0266933f)),
                    Wrapper(Vector2F(-3.903629f, 2.0266933f))
                ),
                Arguments.of(
                    triangleA,
                    Wrapper(Vector2F(-4f, 2f)),
                    Wrapper(Vector2F(-4f, 2f))
                ),
                Arguments.of(
                    triangleA,
                    Wrapper(Vector2F(-4.0963717f, 1.9733067f)),
                    Wrapper(Vector2F(-4f, 2f))
                ),
                Arguments.of(
                    triangleA,
                    Wrapper(Vector2F(1.9188758f, 2.058471f)),
                    Wrapper(Vector2F(1.9188758f, 2.058471f))
                ),
                Arguments.of(
                    triangleA,
                    Wrapper(Vector2F(2f, 2f)),
                    Wrapper(Vector2F(2f, 2f))
                ),
                Arguments.of(
                    triangleA,
                    Wrapper(Vector2F(2.081124f, 1.941529f)),
                    Wrapper(Vector2F(2f, 2f))
                ),
                Arguments.of(
                    triangleA,
                    Wrapper(Vector2F(0.9653053f, 4.9062114f)),
                    Wrapper(Vector2F(0.9653053f, 4.9062114f))
                ),
                Arguments.of(
                    triangleA,
                    Wrapper(Vector2F(1f, 5f)),
                    Wrapper(Vector2F(1f, 5f))
                ),
                Arguments.of(
                    triangleA,
                    Wrapper(Vector2F(1.0346946f, 5.093788f)),
                    Wrapper(Vector2F(1f, 5f))
                ),
                Arguments.of(
                    triangleA,
                    Wrapper(Vector2F(-1f, 2.1f)),
                    Wrapper(Vector2F(-1f, 2.1f))
                ),
                Arguments.of(
                    triangleA,
                    Wrapper(Vector2F(-1f, 2f)),
                    Wrapper(Vector2F(-1f, 2f))
                ),
                Arguments.of(
                    triangleA,
                    Wrapper(Vector2F(-1f, 1.9f)),
                    Wrapper(Vector2F(-1f, 2f))
                ),
                Arguments.of(
                    triangleA,
                    Wrapper(Vector2F(1.4051317f, 3.468377f)),
                    Wrapper(Vector2F(1.4051317f, 3.468377f))
                ),
                Arguments.of(
                    triangleA,
                    Wrapper(Vector2F(1.5f, 3.5f)),
                    Wrapper(Vector2F(1.5f, 3.5f))
                ),
                Arguments.of(
                    triangleA,
                    Wrapper(Vector2F(1.5948683f, 3.531623f)),
                    Wrapper(Vector2F(1.5f, 3.5f))
                ),
                Arguments.of(
                    triangleA,
                    Wrapper(Vector2F(-1.4485505f, 3.4142506f)),
                    Wrapper(Vector2F(-1.4485505f, 3.4142506f))
                ),
                Arguments.of(
                    triangleA,
                    Wrapper(Vector2F(-1.5f, 3.5f)),
                    Wrapper(Vector2F(-1.5f, 3.5f))
                ),
                Arguments.of(
                    triangleA,
                    Wrapper(Vector2F(-1.5514495f, 3.5857494f)),
                    Wrapper(Vector2F(-1.5f, 3.5f))
                ),
                Arguments.of(
                    triangleA, Wrapper(triangleA.centroid), Wrapper(triangleA.centroid)
                ),
                Arguments.of(
                    triangleA, Wrapper(triangleA.orthocenter), Wrapper(triangleA.orthocenter)
                ),
                Arguments.of(
                    triangleA, Wrapper(triangleA.incenter), Wrapper(triangleA.incenter)
                ),
                Arguments.of(
                    triangleA, Wrapper(triangleA.circumcenter), Wrapper(triangleA.circumcenter)
                ),
            )
            val triangleB = MutableTriangle(
                centroid = Vector2F(-1.3333333f, -2.6666667f),
                pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(100.30485f)),
                pointDistanceA = 3.72678f,
                pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(91.00509f)),
                pointDistanceB = 1.6996733f,
                pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f)),
                pointDistanceC = 4.068852f
            )
            val triangleBArgs = listOf(
                Arguments.of(
                    triangleB,
                    Wrapper(Vector2F(-1.9920129f, 0.9003196f)),
                    Wrapper(Vector2F(-1.9920129f, 0.9003196f))
                ),
                Arguments.of(
                    triangleB,
                    Wrapper(Vector2F(-2f, 1f)),
                    Wrapper(Vector2F(-2f, 1f))
                ),
                Arguments.of(
                    triangleB,
                    Wrapper(Vector2F(-2.007987f, 1.0996807f)),
                    Wrapper(Vector2F(-2f, 1f))
                ),
                Arguments.of(
                    triangleB,
                    Wrapper(Vector2F(-2.9057631f, -2.966542f)),
                    Wrapper(Vector2F(-2.9057631f, -2.966542f))
                ),
                Arguments.of(
                    triangleB,
                    Wrapper(Vector2F(-3f, -3f)),
                    Wrapper(Vector2F(-3f, -3f))
                ),
                Arguments.of(
                    triangleB,
                    Wrapper(Vector2F(-3.0942369f, -3.033458f)),
                    Wrapper(Vector2F(-3f, -3f))
                ),
                Arguments.of(
                    triangleB,
                    Wrapper(Vector2F(0.9382081f, -5.921376f)),
                    Wrapper(Vector2F(0.9382081f, -5.921376f))
                ),
                Arguments.of(
                    triangleB,
                    Wrapper(Vector2F(1f, -6f)),
                    Wrapper(Vector2F(1f, -6f))
                ),
                Arguments.of(
                    triangleB,
                    Wrapper(Vector2F(1.0617917f, -6.078624f)),
                    Wrapper(Vector2F(1f, -6f))
                ),
                Arguments.of(
                    triangleB,
                    Wrapper(Vector2F(-2.4029858f, -1.0242536f)),
                    Wrapper(Vector2F(-2.4029858f, -1.0242536f))
                ),
                Arguments.of(
                    triangleB,
                    Wrapper(Vector2F(-2.5f, -1f)),
                    Wrapper(Vector2F(-2.5f, -1f))
                ),
                Arguments.of(
                    triangleB,
                    Wrapper(Vector2F(-2.5970142f, -0.97574645f)),
                    Wrapper(Vector2F(-2.5f, -1f))
                ),
                Arguments.of(
                    triangleB,
                    Wrapper(Vector2F(-0.94f, -4.42f)),
                    Wrapper(Vector2F(-0.94f, -4.42f))
                ),
                Arguments.of(
                    triangleB,
                    Wrapper(Vector2F(-1f, -4.5f)),
                    Wrapper(Vector2F(-1f, -4.5f))
                ),
                Arguments.of(
                    triangleB,
                    Wrapper(Vector2F(-1.06f, -4.58f)),
                    Wrapper(Vector2F(-1f, -4.5f))
                ),
                Arguments.of(
                    triangleB,
                    Wrapper(Vector2F(-0.59191453f, -2.539392f)),
                    Wrapper(Vector2F(-0.59191453f, -2.539392f))
                ),
                Arguments.of(
                    triangleB,
                    Wrapper(Vector2F(-0.5f, -2.5f)),
                    Wrapper(Vector2F(-0.5f, -2.5f))
                ),
                Arguments.of(
                    triangleB,
                    Wrapper(Vector2F(-0.4080855f, -2.460608f)),
                    Wrapper(Vector2F(-0.5f, -2.5f))
                ),
                Arguments.of(
                    triangleB, Wrapper(triangleB.centroid), Wrapper(triangleB.centroid)
                ),
                Arguments.of(
                    triangleB,
                    Wrapper(triangleB.orthocenter),
                    Wrapper(Vector2F(-3f, -3f))
                ),
                Arguments.of(
                    triangleB, Wrapper(triangleB.incenter), Wrapper(triangleB.incenter)
                ),
                Arguments.of(
                    triangleB,
                    Wrapper(triangleB.circumcenter),
                    Wrapper(Vector2F(-0.5f, -2.5f))
                ),
            )
            val triangleC = MutableTriangle(
                centroid = Vector2F(8f, -4.8453f),
                pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                pointDistanceA = 2.3094f,
                pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(-120f)),
                pointDistanceB = 2.3094f,
                pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                pointDistanceC = 2.3094f
            )
            val triangleCArgs = listOf(
                Arguments.of(
                    triangleC,
                    Wrapper(Vector2F(8f, -2.6358979f)),
                    Wrapper(Vector2F(8f, -2.6358979f))
                ),
                Arguments.of(
                    triangleC,
                    Wrapper(Vector2F(8f, -2.535898f)),
                    Wrapper(Vector2F(8f, -2.535898f))
                ),
                Arguments.of(
                    triangleC,
                    Wrapper(Vector2F(8f, -2.435898f)),
                    Wrapper(Vector2F(8f, -2.535898f))
                ),
                Arguments.of(
                    triangleC,
                    Wrapper(Vector2F(9.913398f, -5.95f)),
                    Wrapper(Vector2F(9.913398f, -5.95f))
                ),
                Arguments.of(
                    triangleC,
                    Wrapper(Vector2F(10f, -6f)),
                    Wrapper(Vector2F(10f, -6f))
                ),
                Arguments.of(
                    triangleC,
                    Wrapper(Vector2F(10.086602f, -6.05f)),
                    Wrapper(Vector2F(10f, -6f))
                ),
                Arguments.of(
                    triangleC,
                    Wrapper(Vector2F(6.086602f, -5.95f)),
                    Wrapper(Vector2F(6.086602f, -5.95f))
                ),
                Arguments.of(
                    triangleC,
                    Wrapper(Vector2F(6f, -6f)),
                    Wrapper(Vector2F(6f, -6f))
                ),
                Arguments.of(
                    triangleC,
                    Wrapper(Vector2F(5.913398f, -6.05f)),
                    Wrapper(Vector2F(6f, -6f))
                ),
                Arguments.of(
                    triangleC,
                    Wrapper(Vector2F(8.913398f, -4.3179493f)),
                    Wrapper(Vector2F(8.913398f, -4.3179493f))
                ),
                Arguments.of(
                    triangleC,
                    Wrapper(Vector2F(9f, -4.267949f)),
                    Wrapper(Vector2F(9f, -4.267949f))
                ),
                Arguments.of(
                    triangleC,
                    Wrapper(Vector2F(9.086602f, -4.217949f)),
                    Wrapper(Vector2F(9f, -4.267949f))
                ),
                Arguments.of(
                    triangleC,
                    Wrapper(Vector2F(8f, -5.9f)),
                    Wrapper(Vector2F(8f, -5.9f))
                ),
                Arguments.of(
                    triangleC,
                    Wrapper(Vector2F(8f, -6f)),
                    Wrapper(Vector2F(8f, -6f))
                ),
                Arguments.of(
                    triangleC,
                    Wrapper(Vector2F(8f, -6.1f)),
                    Wrapper(Vector2F(8f, -6f))
                ),
                Arguments.of(
                    triangleC,
                    Wrapper(Vector2F(7.0866027f, -4.3179493f)),
                    Wrapper(Vector2F(7.0866027f, -4.3179493f))
                ),
                Arguments.of(
                    triangleC,
                    Wrapper(Vector2F(7f, -4.267949f)),
                    Wrapper(Vector2F(7f, -4.267949f))
                ),
                Arguments.of(
                    triangleC,
                    Wrapper(Vector2F(6.9133973f, -4.217949f)),
                    Wrapper(Vector2F(7f, -4.267949f))
                ),
                Arguments.of(
                    triangleC, Wrapper(triangleC.centroid), Wrapper(triangleC.centroid)
                ),
                Arguments.of(
                    triangleC, Wrapper(triangleC.orthocenter), Wrapper(triangleC.orthocenter)
                ),
                Arguments.of(
                    triangleC, Wrapper(triangleC.incenter), Wrapper(triangleC.incenter)
                ),
                Arguments.of(
                    triangleC, Wrapper(triangleC.circumcenter), Wrapper(triangleC.circumcenter)
                ),
            )
            val mutableTriangleArgs = listOf(
                triangleAArgs,
                triangleBArgs,
                triangleCArgs
            ).flatten()
            val defaultTriangleArgs = mutableTriangleArgs.mapTrianglesToDefaultTriangles()

            return listOf(
                mutableTriangleArgs,
                defaultTriangleArgs
            ).flatten()
        }

        @JvmStatic
        fun intersectsRayArgs(): List<Arguments> = RayTests.intersectsTriangleArgs().map {
            val args = it.get()
            Arguments.of(args[1], args[0], args[2])
        }

        @JvmStatic
        fun containsVector2FArgs(): List<Arguments> {
            val triangleA = MutableTriangle(
                centroid = Vector2F(-0.3333333f, 3f),
                pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(-164.74489f)),
                pointDistanceA = 3.8005848f,
                pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(141.54628f)),
                pointDistanceB = 2.538591f,
                pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-138.94519f)),
                pointDistanceC = 2.4037008f
            )
            val triangleAArgs = listOf(
                Arguments.of(triangleA, Wrapper(Vector2F(-3.903629f, 2.0266933f)), true),
                Arguments.of(triangleA, Wrapper(Vector2F(-4.0963717f, 1.9733067f)), false),
                Arguments.of(triangleA, Wrapper(Vector2F(1.9188758f, 2.058471f)), true),
                Arguments.of(triangleA, Wrapper(Vector2F(2.081124f, 1.941529f)), false),
                Arguments.of(triangleA, Wrapper(Vector2F(0.9653053f, 4.9062114f)), true),
                Arguments.of(triangleA, Wrapper(Vector2F(1.0346946f, 5.093788f)), false),
                Arguments.of(triangleA, Wrapper(Vector2F(-1.0f, 2.1f)), true),
                Arguments.of(triangleA, Wrapper(Vector2F(-1.0f, 1.9f)), false),
                Arguments.of(triangleA, Wrapper(Vector2F(1.4051317f, 3.468377f)), true),
                Arguments.of(triangleA, Wrapper(Vector2F(1.5948683f, 3.531623f)), false),
                Arguments.of(triangleA, Wrapper(Vector2F(-1.4485505f, 3.4142506f)), true),
                Arguments.of(triangleA, Wrapper(Vector2F(-1.5514495f, 3.5857494f)), false),
                Arguments.of(triangleA, Wrapper(triangleA.centroid), true),
                Arguments.of(triangleA, Wrapper(triangleA.orthocenter), true),
                Arguments.of(triangleA, Wrapper(triangleA.incenter), true),
                Arguments.of(triangleA, Wrapper(triangleA.circumcenter), true),
            )
            val triangleB = MutableTriangle(
                centroid = Vector2F(-1.3333333f, -2.6666667f),
                pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(100.30485f)),
                pointDistanceA = 3.72678f,
                pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(91.00509f)),
                pointDistanceB = 1.6996733f,
                pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f)),
                pointDistanceC = 4.068852f
            )
            val triangleBArgs = listOf(
                Arguments.of(triangleB, Wrapper(Vector2F(-1.9920129f, 0.9003196f)), true),
                Arguments.of(triangleB, Wrapper(Vector2F(-2.007987f, 1.0996807f)), false),
                Arguments.of(triangleB, Wrapper(Vector2F(-2.9057631f, -2.966542f)), true),
                Arguments.of(triangleB, Wrapper(Vector2F(-3.0942369f, -3.033458f)), false),
                Arguments.of(triangleB, Wrapper(Vector2F(0.9382081f, -5.921376f)), true),
                Arguments.of(triangleB, Wrapper(Vector2F(1.0617917f, -6.078624f)), false),
                Arguments.of(triangleB, Wrapper(Vector2F(-2.4029858f, -1.0242536f)), true),
                Arguments.of(triangleB, Wrapper(Vector2F(-2.5970142f, -0.97574645f)), false),
                Arguments.of(triangleB, Wrapper(Vector2F(-0.94f, -4.42f)), true),
                Arguments.of(triangleB, Wrapper(Vector2F(-1.06f, -4.58f)), false),
                Arguments.of(triangleB, Wrapper(Vector2F(-0.59191453f, -2.539392f)), true),
                Arguments.of(triangleB, Wrapper(Vector2F(-0.4080855f, -2.460608f)), false),
                Arguments.of(triangleB, Wrapper(triangleB.centroid), true),
                Arguments.of(triangleB, Wrapper(triangleB.orthocenter), false),
                Arguments.of(triangleB, Wrapper(triangleB.incenter), true),
                Arguments.of(triangleB, Wrapper(triangleB.circumcenter), false),
            )
            val triangleC = MutableTriangle(
                centroid = Vector2F(8f, -4.8453f),
                pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                pointDistanceA = 2.3094f,
                pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(-120f)),
                pointDistanceB = 2.3094f,
                pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                pointDistanceC = 2.3094f
            )
            val triangleCArgs = listOf(
                Arguments.of(triangleC, Wrapper(Vector2F(8.0f, -2.6358979f)), true),
                Arguments.of(triangleC, Wrapper(Vector2F(8.0f, -2.435898f)), false),
                Arguments.of(triangleC, Wrapper(Vector2F(9.913398f, -5.95f)), true),
                Arguments.of(triangleC, Wrapper(Vector2F(10.086602f, -6.05f)), false),
                Arguments.of(triangleC, Wrapper(Vector2F(6.086602f, -5.95f)), true),
                Arguments.of(triangleC, Wrapper(Vector2F(5.913398f, -6.05f)), false),
                Arguments.of(triangleC, Wrapper(Vector2F(8.913398f, -4.3179493f)), true),
                Arguments.of(triangleC, Wrapper(Vector2F(9.086602f, -4.217949f)), false),
                Arguments.of(triangleC, Wrapper(Vector2F(8.0f, -5.9f)), true),
                Arguments.of(triangleC, Wrapper(Vector2F(8.0f, -6.1f)), false),
                Arguments.of(triangleC, Wrapper(Vector2F(7.0866027f, -4.3179493f)), true),
                Arguments.of(triangleC, Wrapper(Vector2F(6.9133973f, -4.217949f)), false),
                Arguments.of(triangleC, Wrapper(triangleC.centroid), true),
                Arguments.of(triangleC, Wrapper(triangleC.orthocenter), true),
                Arguments.of(triangleC, Wrapper(triangleC.incenter), true),
                Arguments.of(triangleC, Wrapper(triangleC.circumcenter), true),
            )
            val mutableTriangleArgs = listOf(
                triangleAArgs,
                triangleBArgs,
                triangleCArgs
            ).flatten()
            val defaultTriangleArgs = mutableTriangleArgs.mapTrianglesToDefaultTriangles()

            return listOf(
                mutableTriangleArgs,
                defaultTriangleArgs
            ).flatten()
        }

        @JvmStatic
        fun copyArgs(): List<Arguments> {
            val mutableTriangleArgs = setArgs()
            val defaultTriangleArgs = mutableTriangleArgs.mapTrianglesToDefaultTriangles()

            return listOf(
                mutableTriangleArgs,
                defaultTriangleArgs
            ).flatten()
        }

        @JvmStatic
        fun copyThrowsExceptionArgs(): List<Arguments> =
            constructorVector2FComplexFFloatComplexFFloatComplexFFloatThrowsExceptionArgs()

        @JvmStatic
        fun copyDoesNotThrowExceptionArgs(): List<Arguments> =
            constructorVector2FComplexFFloatComplexFFloatComplexFFloatArgs()

        @JvmStatic
        fun equalsAnyArgs(): List<Arguments> = equalsMutableTriangleArgs() + listOf(
            Arguments.of(
                MutableTriangle(
                    centroid = Vector2F(-0.3333333f, 3f),
                    pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(-164.74489f)),
                    pointDistanceA = 3.8005848f,
                    pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(141.54628f)),
                    pointDistanceB = 2.538591f,
                    pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-138.94519f)),
                    pointDistanceC = 2.4037008f
                ),
                null,
                false
            ),
            Arguments.of(
                MutableTriangle(
                    centroid = Vector2F(-0.3333333f, 3f),
                    pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(-164.74489f)),
                    pointDistanceA = 3.8005848f,
                    pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(141.54628f)),
                    pointDistanceB = 2.538591f,
                    pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-138.94519f)),
                    pointDistanceC = 2.4037008f
                ),
                DefaultTriangle(
                    centroid = Vector2F(-0.3333333f, 3f),
                    pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(-164.74489f)),
                    pointDistanceA = 3.8005848f,
                    pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(141.54628f)),
                    pointDistanceB = 2.538591f,
                    pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-138.94519f)),
                    pointDistanceC = 2.4037008f
                ),
                true
            ),
            Arguments.of(
                MutableTriangle(
                    centroid = Vector2F(-0.3333333f, 3f),
                    pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(-164.74489f)),
                    pointDistanceA = 3.8005848f,
                    pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(141.54628f)),
                    pointDistanceB = 2.538591f,
                    pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-138.94519f)),
                    pointDistanceC = 2.4037008f
                ),
                DefaultTriangle(
                    centroid = Vector2F(-0.3333333f, 3f),
                    pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(-164.74489f)),
                    pointDistanceA = 3.8005848f,
                    pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(141.54628f)),
                    pointDistanceB = 2.538591f,
                    pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-139f)),
                    pointDistanceC = 2.4037008f
                ),
                false
            ),
        )

        @JvmStatic
        fun equalsMutableTriangleArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableTriangle(
                    centroid = Vector2F(-0.3333333f, 3f),
                    pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(-164.74489f)),
                    pointDistanceA = 3.8005848f,
                    pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(141.54628f)),
                    pointDistanceB = 2.538591f,
                    pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-138.94519f)),
                    pointDistanceC = 2.4037008f
                ),
                MutableTriangle(
                    centroid = Vector2F(-0.3333333f, 3f),
                    pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(-164.74489f)),
                    pointDistanceA = 3.8005848f,
                    pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(141.54628f)),
                    pointDistanceB = 2.538591f,
                    pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-138.94519f)),
                    pointDistanceC = 2.4037008f
                ),
                true
            ),
            Arguments.of(
                MutableTriangle(
                    centroid = Vector2F(-0.3333333f, 3f),
                    pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(-164.74489f)),
                    pointDistanceA = 3.8005848f,
                    pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(141.54628f)),
                    pointDistanceB = 2.538591f,
                    pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-138.94519f)),
                    pointDistanceC = 2.4037008f
                ),
                MutableTriangle(
                    centroid = Vector2F(-0.3333333f, 3f),
                    pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(-164.74489f)),
                    pointDistanceA = 3.8005848f,
                    pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(141.54628f)),
                    pointDistanceB = 2.538591f,
                    pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-139f)),
                    pointDistanceC = 2.4037008f
                ),
                false
            ),
        )

        @JvmStatic
        fun hashCodeArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableTriangle(
                    centroid = Vector2F(-0.3333333f, 3f),
                    pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(-164.74489f)),
                    pointDistanceA = 3.8005848f,
                    pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(141.54628f)),
                    pointDistanceB = 2.538591f,
                    pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-138.94519f)),
                    pointDistanceC = 2.4037008f
                ),
                MutableTriangle(
                    centroid = Vector2F(-0.3333333f, 3f),
                    pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(-164.74489f)),
                    pointDistanceA = 3.8005848f,
                    pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(141.54628f)),
                    pointDistanceB = 2.538591f,
                    pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-138.94519f)),
                    pointDistanceC = 2.4037008f
                )
            ),
            Arguments.of(
                MutableTriangle(
                    centroid = Vector2F(-1.3333333f, -2.6666667f),
                    pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(100.30485f)),
                    pointDistanceA = 3.72678f,
                    pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(91.00509f)),
                    pointDistanceB = 1.6996733f,
                    pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f)),
                    pointDistanceC = 4.068852f
                ),
                MutableTriangle(
                    centroid = Vector2F(-1.3333333f, -2.6666667f),
                    pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(100.30485f)),
                    pointDistanceA = 3.72678f,
                    pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(91.00509f)),
                    pointDistanceB = 1.6996733f,
                    pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f)),
                    pointDistanceC = 4.068852f
                )
            ),
        )

        @JvmStatic
        fun toStringArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableTriangle(
                    centroid = Vector2F(-0.3333333f, 3f),
                    pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(-164.74489f)),
                    pointDistanceA = 3.8005848f,
                    pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(141.54628f)),
                    pointDistanceB = 2.538591f,
                    pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-138.94519f)),
                    pointDistanceC = 2.4037008f
                ),
                "Triangle(" +
                        "centroid=${Vector2F(-0.3333333f, 3f)}, " +
                        "pathRotorA=${ComplexF.fromAngle(AngleF.fromDegrees(-164.74489f))}, " +
                        "pointDistanceA=${3.8005848f}, " +
                        "pathRotorAB=${ComplexF.fromAngle(AngleF.fromDegrees(141.54628f))}, " +
                        "pointDistanceB=${2.538591f}, " +
                        "pathRotorAC=${ComplexF.fromAngle(AngleF.fromDegrees(-138.94519f))}, " +
                        "pointDistanceC=${2.4037008f})"
            ),
            Arguments.of(
                MutableTriangle(
                    centroid = Vector2F(-1.3333333f, -2.6666667f),
                    pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(100.30485f)),
                    pointDistanceA = 3.72678f,
                    pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(91.00509f)),
                    pointDistanceB = 1.6996733f,
                    pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f)),
                    pointDistanceC = 4.068852f
                ),
                "Triangle(" +
                        "centroid=${Vector2F(-1.3333333f, -2.6666667f)}, " +
                        "pathRotorA=${ComplexF.fromAngle(AngleF.fromDegrees(100.30485f))}, " +
                        "pointDistanceA=${3.72678f}, " +
                        "pathRotorAB=${ComplexF.fromAngle(AngleF.fromDegrees(91.00509f))}, " +
                        "pointDistanceB=${1.6996733f}, " +
                        "pathRotorAC=${ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f))}, " +
                        "pointDistanceC=${4.068852f})"
            ),
        )

        @JvmStatic
        fun componentsArgs(): List<Arguments> {
            val mutableTriangleArgs = listOf(
                Arguments.of(
                    MutableTriangle(
                        centroid = Vector2F(-0.3333333f, 3f),
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(-164.74489f)),
                        pointDistanceA = 3.8005848f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(141.54628f)),
                        pointDistanceB = 2.538591f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-138.94519f)),
                        pointDistanceC = 2.4037008f
                    ),
                    Wrapper(Vector2F(-0.3333333f, 3f)),
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(-164.74489f))),
                    3.8005848f,
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(141.54628f))),
                    2.538591f,
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(-138.94519f))),
                    2.4037008f
                ),
                Arguments.of(
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(100.30485f)),
                        pointDistanceA = 3.72678f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(91.00509f)),
                        pointDistanceB = 1.6996733f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f)),
                        pointDistanceC = 4.068852f
                    ),
                    Wrapper(Vector2F(-1.3333333f, -2.6666667f)),
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(100.30485f))),
                    3.72678f,
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(91.00509f))),
                    1.6996733f,
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f))),
                    4.068852f
                ),
                Arguments.of(
                    MutableTriangle(
                        centroid = Vector2F(8f, -4.8453f),
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                        pointDistanceA = 2.3094f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(-120f)),
                        pointDistanceB = 2.3094f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        pointDistanceC = 2.3094f
                    ),
                    Wrapper(Vector2F(8f, -4.8453f)),
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(90f))),
                    2.3094f,
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(-120f))),
                    2.3094f,
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(120f))),
                    2.3094f
                ),
            )
            val defaultTriangleArgs = mutableTriangleArgs.mapTrianglesToDefaultTriangles()

            return listOf(
                mutableTriangleArgs,
                defaultTriangleArgs
            ).flatten()
        }
    }
}