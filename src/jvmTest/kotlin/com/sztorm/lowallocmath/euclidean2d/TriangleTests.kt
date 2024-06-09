package com.sztorm.lowallocmath.euclidean2d

import com.sztorm.lowallocmath.AngleF
import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F
import com.sztorm.lowallocmath.euclidean2d.utils.DefaultTriangle
import com.sztorm.lowallocmath.euclidean2d.utils.assertImmutabilityOf
import com.sztorm.lowallocmath.utils.Wrapper
import com.sztorm.lowallocmath.utils.assertApproximation
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

class TriangleTests {
    @ParameterizedTest
    @MethodSource("constructorArgs")
    fun constructorCreatesCorrectTriangle(
        pointA: Wrapper<Vector2F>, pointB: Wrapper<Vector2F>, pointC: Wrapper<Vector2F>
    ) {
        val mutableTriangle = MutableTriangle(pointA.value, pointB.value, pointC.value)
        val triangle = Triangle(pointA.value, pointB.value, pointC.value)

        assertEquals(pointA.value, mutableTriangle.pointA)
        assertEquals(pointB.value, mutableTriangle.pointB)
        assertEquals(pointC.value, mutableTriangle.pointC)

        assertEquals(pointA.value, triangle.pointA)
        assertEquals(pointB.value, triangle.pointB)
        assertEquals(pointC.value, triangle.pointC)
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
    @MethodSource("centroidArgs")
    fun centroidReturnsCorrectValue(triangle: Triangle, expected: Wrapper<Vector2F>) =
        assertImmutabilityOf(triangle) {
            assertApproximation(expected.value, triangle.centroid)
        }

    @ParameterizedTest
    @MethodSource("orientationArgs")
    fun orientationReturnsCorrectValue(triangle: Triangle, expected: Wrapper<ComplexF>) =
        assertImmutabilityOf(triangle) {
            assertApproximation(expected.value, triangle.orientation)
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
        assertApproximation(expectedSideLengthCA, triangle.sideLengthCA)
    }

    @ParameterizedTest
    @MethodSource("positionArgs")
    fun positionReturnsCorrectValue(triangle: Triangle, expected: Wrapper<Vector2F>) =
        assertImmutabilityOf(triangle) {
            assertApproximation(expected.value, triangle.position)
        }

    @ParameterizedTest
    @MethodSource("orthocenterArgs")
    fun orthocenterReturnsCorrectValue(triangle: Triangle, expected: Wrapper<Vector2F>) =
        assertImmutabilityOf(triangle) {
            assertApproximation(expected.value, triangle.orthocenter)
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
    @MethodSource("setArgs")
    fun setMutatesTriangleCorrectly(
        triangle: MutableTriangle,
        pointA: Wrapper<Vector2F>,
        pointB: Wrapper<Vector2F>,
        pointC: Wrapper<Vector2F>,
        expected: MutableTriangle
    ) = assertEquals(expected, triangle.apply { set(pointA.value, pointB.value, pointC.value) })

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
        pointA: Wrapper<Vector2F>,
        pointB: Wrapper<Vector2F>,
        pointC: Wrapper<Vector2F>,
        expected: Triangle
    ) = assertEquals(expected, triangle.copy(pointA.value, pointB.value, pointC.value))

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
        expectedComponent2: Wrapper<Vector2F>,
        expectedComponent3: Wrapper<Vector2F>
    ) = assertImmutabilityOf(triangle) {
        val (
            actualComponent1: Vector2F,
            actualComponent2: Vector2F,
            actualComponent3: Vector2F
        ) = triangle

        assertEquals(expectedComponent1.value, actualComponent1)
        assertEquals(expectedComponent2.value, actualComponent2)
        assertEquals(expectedComponent3.value, actualComponent3)
    }

    companion object {
        @JvmStatic
        fun areApproximatelyEqual(a: Triangle, b: Triangle, tolerance: Float = 0.00001f): Boolean =
            a.pointA.isApproximately(b.pointA, tolerance) and
                    a.pointB.isApproximately(b.pointB, tolerance) and
                    a.pointC.isApproximately(b.pointC, tolerance) and
                    a.centroid.isApproximately(b.centroid, tolerance) and
                    a.orientation.isApproximately(b.orientation, tolerance)

        @JvmStatic
        fun clone(triangle: Triangle) = triangle.copy()

        @JvmStatic
        fun List<Arguments>.mapTrianglesToDefaultTriangles() = map { args ->
            val argArray = args.get().map {
                if (it is Triangle) DefaultTriangle(it.pointA, it.pointB, it.pointC)
                else it
            }.toTypedArray()

            Arguments.of(*argArray)
        }

        @JvmStatic
        fun constructorArgs(): List<Arguments> = listOf(
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
        fun pointsArgs(): List<Arguments> {
            val mutableTriangleArgs = listOf(
                Arguments.of(
                    MutableTriangle(
                        Vector2F(-4f, 2f), Vector2F(2f, 2f), Vector2F(1f, 5f)
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    Wrapper(Vector2F(2f, 2f)),
                    Wrapper(Vector2F(1f, 5f))
                ),
                Arguments.of(
                    MutableTriangle(
                        Vector2F(-2f, 1f), Vector2F(-3f, -3f), Vector2F(1f, -6f)
                    ),
                    Wrapper(Vector2F(-2f, 1f)),
                    Wrapper(Vector2F(-3f, -3f)),
                    Wrapper(Vector2F(1f, -6f))
                ),
                Arguments.of(
                    MutableTriangle(
                        Vector2F(8f, -2.535898f),
                        Vector2F(10f, -6f),
                        Vector2F(6f, -6f)
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
        fun centroidArgs(): List<Arguments> {
            val mutableTriangleArgs = listOf(
                Arguments.of(
                    MutableTriangle(
                        Vector2F(-4f, 2f), Vector2F(2f, 2f), Vector2F(1f, 5f)
                    ),
                    Wrapper(Vector2F(-0.3333333f, 3f))
                ),
                Arguments.of(
                    MutableTriangle(
                        Vector2F(-2f, 1f), Vector2F(-3f, -3f), Vector2F(1f, -6f)
                    ),
                    Wrapper(Vector2F(-1.3333333f, -2.6666667f))
                ),
                Arguments.of(
                    MutableTriangle(
                        Vector2F(8f, -2.535898f),
                        Vector2F(10f, -6f),
                        Vector2F(6f, -6f)
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
        fun orientationArgs(): List<Arguments> {
            val mutableTriangleArgs = listOf(
                Arguments.of(
                    MutableTriangle(
                        Vector2F(-4f, 2f), Vector2F(2f, 2f), Vector2F(1f, 5f)
                    ),
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(-164.74489f)))
                ),
                Arguments.of(
                    MutableTriangle(
                        Vector2F(-2f, 1f), Vector2F(-3f, -3f), Vector2F(1f, -6f)
                    ),
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(100.30485f)))
                ),
                Arguments.of(
                    MutableTriangle(
                        Vector2F(8f, -2.535898f),
                        Vector2F(10f, -6f),
                        Vector2F(6f, -6f)
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
        fun areaArgs(): List<Arguments> {
            val mutableTriangleArgs = listOf(
                Arguments.of(
                    MutableTriangle(
                        Vector2F(-4f, 2f), Vector2F(2f, 2f), Vector2F(1f, 5f)
                    ),
                    9f
                ),
                Arguments.of(
                    MutableTriangle(
                        Vector2F(-2f, 1f), Vector2F(-3f, -3f), Vector2F(1f, -6f)
                    ),
                    9.5f
                ),
                Arguments.of(
                    MutableTriangle(
                        Vector2F(8f, -2.535898f),
                        Vector2F(10f, -6f),
                        Vector2F(6f, -6f)
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
                        Vector2F(-4f, 2f), Vector2F(2f, 2f), Vector2F(1f, 5f)
                    ),
                    14.99323f
                ),
                Arguments.of(
                    MutableTriangle(
                        Vector2F(-2f, 1f), Vector2F(-3f, -3f), Vector2F(1f, -6f)
                    ),
                    16.73888f
                ),
                Arguments.of(
                    MutableTriangle(
                        Vector2F(8f, -2.535898f),
                        Vector2F(10f, -6f),
                        Vector2F(6f, -6f)
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
                        Vector2F(-4f, 2f), Vector2F(2f, 2f), Vector2F(1f, 5f)
                    ),
                    6f,
                    3.1622777f,
                    5.8309517f
                ),
                Arguments.of(
                    MutableTriangle(
                        Vector2F(-2f, 1f), Vector2F(-3f, -3f), Vector2F(1f, -6f)
                    ),
                    4.1231055f,
                    5.0f,
                    7.615773f
                ),
                Arguments.of(
                    MutableTriangle(
                        Vector2F(8f, -2.535898f),
                        Vector2F(10f, -6f),
                        Vector2F(6f, -6f)
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
        fun orthocenterArgs(): List<Arguments> {
            val mutableTriangleArgs = listOf(
                Arguments.of(
                    MutableTriangle(
                        Vector2F(-4f, 2f), Vector2F(2f, 2f), Vector2F(1f, 5f)
                    ),
                    Wrapper(Vector2F(1f, 3.6666667f))
                ),
                Arguments.of(
                    MutableTriangle(
                        Vector2F(-2f, 1f), Vector2F(-3f, -3f), Vector2F(1f, -6f)
                    ),
                    Wrapper(Vector2F(-5.947368f, -4.263158f))
                ),
                Arguments.of(
                    MutableTriangle(
                        Vector2F(8f, -2.535898f),
                        Vector2F(10f, -6f),
                        Vector2F(6f, -6f)
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
        fun incenterArgs(): List<Arguments> {
            val mutableTriangleArgs = listOf(
                Arguments.of(
                    MutableTriangle(
                        Vector2F(-4f, 2f), Vector2F(2f, 2f), Vector2F(1f, 5f)
                    ),
                    Wrapper(Vector2F(0.3343371f, 3.200542f))
                ),
                Arguments.of(
                    MutableTriangle(
                        Vector2F(-2f, 1f), Vector2F(-3f, -3f), Vector2F(1f, -6f)
                    ),
                    Wrapper(Vector2F(-1.7160178f, -2.544134f))
                ),
                Arguments.of(
                    MutableTriangle(
                        Vector2F(8f, -2.535898f),
                        Vector2F(10f, -6f),
                        Vector2F(6f, -6f)
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
                        Vector2F(-4f, 2f), Vector2F(2f, 2f), Vector2F(1f, 5f)
                    ),
                    Wrapper(Vector2F(-1f, 2.6666667f))
                ),
                Arguments.of(
                    MutableTriangle(
                        Vector2F(-2f, 1f), Vector2F(-3f, -3f), Vector2F(1f, -6f)
                    ),
                    Wrapper(Vector2F(0.9736842f, -1.868421f))
                ),
                Arguments.of(
                    MutableTriangle(
                        Vector2F(8f, -2.535898f),
                        Vector2F(10f, -6f),
                        Vector2F(6f, -6f)
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
        fun setArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableTriangle(
                    Vector2F(-4f, 2f), Vector2F(2f, 2f), Vector2F(1f, 5f)
                ),
                Wrapper(Vector2F(-4f, 2f)),
                Wrapper(Vector2F(2f, 2f)),
                Wrapper(Vector2F(1f, 5f)),
                MutableTriangle(
                    Vector2F(-4f, 2f), Vector2F(2f, 2f), Vector2F(1f, 5f)
                )
            ),
            Arguments.of(
                MutableTriangle(
                    Vector2F(-4f, 2f), Vector2F(2f, 2f), Vector2F(1f, 5f)
                ),
                Wrapper(Vector2F(-4f, 2f)),
                Wrapper(Vector2F(-3f, -3f)),
                Wrapper(Vector2F(1f, -6f)),
                MutableTriangle(
                    Vector2F(-4f, 2f), Vector2F(-3f, -3f), Vector2F(1f, -6f)
                )
            ),
            Arguments.of(
                MutableTriangle(
                    Vector2F(-4f, 2f), Vector2F(2f, 2f), Vector2F(1f, 5f)
                ),
                Wrapper(Vector2F(-2f, 1f)),
                Wrapper(Vector2F(-3f, -3f)),
                Wrapper(Vector2F(1f, -6f)),
                MutableTriangle(
                    Vector2F(-2f, 1f), Vector2F(-3f, -3f), Vector2F(1f, -6f)
                )
            ),
        )

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
                    pointA = Vector2F.ZERO, pointB = Vector2F.ONE, pointC = Vector2F(1f, -1f)
                ),
                MutableTriangle(
                    Vector2F(-4f, 2f), Vector2F(2f, 2f), Vector2F(1f, 5f)
                ),
                MutableTriangle(
                    Vector2F(-4f, 2f), Vector2F(2f, 2f), Vector2F(1f, 5f)
                ),
                0.5f,
                MutableTriangle(
                    Vector2F(-4f, 2f), Vector2F(2f, 2f), Vector2F(1f, 5f)
                )
            ),
            Arguments.of(
                MutableTriangle(
                    pointA = Vector2F.ZERO, pointB = Vector2F.ONE, pointC = Vector2F(1f, -1f)
                ),
                MutableTriangle(
                    Vector2F(-4f, 2f), Vector2F(2f, 2f), Vector2F(1f, 5f)
                ),
                MutableTriangle(
                    Vector2F(-2f, 1f), Vector2F(-3f, -3f), Vector2F(1f, -6f)
                ),
                0f,
                MutableTriangle(
                    Vector2F(-4f, 2f), Vector2F(2f, 2f), Vector2F(1f, 5f)
                )
            ),
            Arguments.of(
                MutableTriangle(
                    pointA = Vector2F.ZERO, pointB = Vector2F.ONE, pointC = Vector2F(1f, -1f)
                ),
                MutableTriangle(
                    Vector2F(-4f, 2f), Vector2F(2f, 2f), Vector2F(1f, 5f)
                ),
                MutableTriangle(
                    Vector2F(-2f, 1f), Vector2F(-3f, -3f), Vector2F(1f, -6f)
                ),
                1f,
                MutableTriangle(
                    Vector2F(-2f, 1f), Vector2F(-3f, -3f), Vector2F(1f, -6f)
                )
            ),
            Arguments.of(
                MutableTriangle(
                    pointA = Vector2F.ZERO, pointB = Vector2F.ONE, pointC = Vector2F(1f, -1f)
                ),
                MutableTriangle(
                    Vector2F(-4f, 2f), Vector2F(2f, 2f), Vector2F(1f, 5f)
                ),
                MutableTriangle(
                    Vector2F(-2f, 1f), Vector2F(-3f, -3f), Vector2F(1f, -6f)
                ),
                0.5f,
                MutableTriangle(
                    Vector2F(-3f, 1.5f),
                    Vector2F(-0.5f, -0.5f),
                    Vector2F(1f, -0.5f)
                )
            ),
        )

        @JvmStatic
        fun closestPointToArgs(): List<Arguments> {
            val triangleA = MutableTriangle(
                Vector2F(-4f, 2f), Vector2F(2f, 2f), Vector2F(1f, 5f)
            )
            val triangleAArgs = listOf(
                Arguments.of(
                    triangleA,
                    Wrapper(Vector2F(-3.903629f, 2.0266933f)),
                    Wrapper(Vector2F(-3.903629f, 2.0266933f))
                ),
                Arguments.of(
                    triangleA,
                    Wrapper(Vector2F(-4.0f, 2.0f)),
                    Wrapper(Vector2F(-4.0f, 2.0f))
                ),
                Arguments.of(
                    triangleA,
                    Wrapper(Vector2F(-4.0963717f, 1.9733067f)),
                    Wrapper(Vector2F(-4.0f, 2.0f))
                ),
                Arguments.of(
                    triangleA,
                    Wrapper(Vector2F(1.9188758f, 2.058471f)),
                    Wrapper(Vector2F(1.9188758f, 2.058471f))
                ),
                Arguments.of(
                    triangleA,
                    Wrapper(Vector2F(2.0f, 2.0f)),
                    Wrapper(Vector2F(2.0f, 2.0f))
                ),
                Arguments.of(
                    triangleA,
                    Wrapper(Vector2F(2.081124f, 1.941529f)),
                    Wrapper(Vector2F(2.0f, 2.0f))
                ),
                Arguments.of(
                    triangleA,
                    Wrapper(Vector2F(0.9653053f, 4.9062114f)),
                    Wrapper(Vector2F(0.9653053f, 4.9062114f))
                ),
                Arguments.of(
                    triangleA,
                    Wrapper(Vector2F(1.0f, 5.0f)),
                    Wrapper(Vector2F(1.0f, 5.0f))
                ),
                Arguments.of(
                    triangleA,
                    Wrapper(Vector2F(1.0346946f, 5.093788f)),
                    Wrapper(Vector2F(1.0f, 5.0f))
                ),
                Arguments.of(
                    triangleA,
                    Wrapper(Vector2F(-1.0f, 2.1f)),
                    Wrapper(Vector2F(-1.0f, 2.1f))
                ),
                Arguments.of(
                    triangleA,
                    Wrapper(Vector2F(-1.0f, 2.0f)),
                    Wrapper(Vector2F(-1.0f, 2.0f))
                ),
                Arguments.of(
                    triangleA,
                    Wrapper(Vector2F(-1.0f, 1.9f)),
                    Wrapper(Vector2F(-1.0f, 2.0f))
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
                Vector2F(-2f, 1f), Vector2F(-3f, -3f), Vector2F(1f, -6f)
            )
            val triangleBArgs = listOf(
                Arguments.of(
                    triangleB,
                    Wrapper(Vector2F(-1.9920129f, 0.9003196f)),
                    Wrapper(Vector2F(-1.9920129f, 0.9003196f))
                ),
                Arguments.of(
                    triangleB,
                    Wrapper(Vector2F(-2.0f, 1.0f)),
                    Wrapper(Vector2F(-2.0f, 1.0f))
                ),
                Arguments.of(
                    triangleB,
                    Wrapper(Vector2F(-2.007987f, 1.0996807f)),
                    Wrapper(Vector2F(-2.0f, 1.0f))
                ),
                Arguments.of(
                    triangleB,
                    Wrapper(Vector2F(-2.9057631f, -2.966542f)),
                    Wrapper(Vector2F(-2.9057631f, -2.966542f))
                ),
                Arguments.of(
                    triangleB,
                    Wrapper(Vector2F(-3.0f, -3.0f)),
                    Wrapper(Vector2F(-3.0f, -3.0f))
                ),
                Arguments.of(
                    triangleB,
                    Wrapper(Vector2F(-3.0942369f, -3.033458f)),
                    Wrapper(Vector2F(-3.0f, -3.0f))
                ),
                Arguments.of(
                    triangleB,
                    Wrapper(Vector2F(0.9382081f, -5.921376f)),
                    Wrapper(Vector2F(0.9382081f, -5.921376f))
                ),
                Arguments.of(
                    triangleB,
                    Wrapper(Vector2F(1.0f, -6.0f)),
                    Wrapper(Vector2F(1.0f, -6.0f))
                ),
                Arguments.of(
                    triangleB,
                    Wrapper(Vector2F(1.0617917f, -6.078624f)),
                    Wrapper(Vector2F(1.0f, -6.0f))
                ),
                Arguments.of(
                    triangleB,
                    Wrapper(Vector2F(-2.4029858f, -1.0242536f)),
                    Wrapper(Vector2F(-2.4029858f, -1.0242536f))
                ),
                Arguments.of(
                    triangleB,
                    Wrapper(Vector2F(-2.5f, -1.0f)),
                    Wrapper(Vector2F(-2.5f, -1.0f))
                ),
                Arguments.of(
                    triangleB,
                    Wrapper(Vector2F(-2.5970142f, -0.97574645f)),
                    Wrapper(Vector2F(-2.5f, -1.0f))
                ),
                Arguments.of(
                    triangleB,
                    Wrapper(Vector2F(-0.94f, -4.42f)),
                    Wrapper(Vector2F(-0.94f, -4.42f))
                ),
                Arguments.of(
                    triangleB,
                    Wrapper(Vector2F(-1.0f, -4.5f)),
                    Wrapper(Vector2F(-1.0f, -4.5f))
                ),
                Arguments.of(
                    triangleB,
                    Wrapper(Vector2F(-1.06f, -4.58f)),
                    Wrapper(Vector2F(-1.0f, -4.5f))
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
                Vector2F(8f, -2.535898f),
                Vector2F(10f, -6f),
                Vector2F(6f, -6f)
            )
            val triangleCArgs = listOf(
                Arguments.of(
                    triangleC,
                    Wrapper(Vector2F(8.0f, -2.6358979f)),
                    Wrapper(Vector2F(8.0f, -2.6358979f))
                ),
                Arguments.of(
                    triangleC,
                    Wrapper(Vector2F(8.0f, -2.535898f)),
                    Wrapper(Vector2F(8.0f, -2.535898f))
                ),
                Arguments.of(
                    triangleC,
                    Wrapper(Vector2F(8.0f, -2.435898f)),
                    Wrapper(Vector2F(8.0f, -2.535898f))
                ),
                Arguments.of(
                    triangleC,
                    Wrapper(Vector2F(9.913398f, -5.95f)),
                    Wrapper(Vector2F(9.913398f, -5.95f))
                ),
                Arguments.of(
                    triangleC,
                    Wrapper(Vector2F(10.0f, -6.0f)),
                    Wrapper(Vector2F(10.0f, -6.0f))
                ),
                Arguments.of(
                    triangleC,
                    Wrapper(Vector2F(10.086602f, -6.05f)),
                    Wrapper(Vector2F(10.0f, -6.0f))
                ),
                Arguments.of(
                    triangleC,
                    Wrapper(Vector2F(6.086602f, -5.95f)),
                    Wrapper(Vector2F(6.086602f, -5.95f))
                ),
                Arguments.of(
                    triangleC,
                    Wrapper(Vector2F(6.0f, -6.0f)),
                    Wrapper(Vector2F(6.0f, -6.0f))
                ),
                Arguments.of(
                    triangleC,
                    Wrapper(Vector2F(5.913398f, -6.05f)),
                    Wrapper(Vector2F(6.0f, -6.0f))
                ),
                Arguments.of(
                    triangleC,
                    Wrapper(Vector2F(8.913398f, -4.3179493f)),
                    Wrapper(Vector2F(8.913398f, -4.3179493f))
                ),
                Arguments.of(
                    triangleC,
                    Wrapper(Vector2F(9.0f, -4.267949f)),
                    Wrapper(Vector2F(9.0f, -4.267949f))
                ),
                Arguments.of(
                    triangleC,
                    Wrapper(Vector2F(9.086602f, -4.217949f)),
                    Wrapper(Vector2F(9.0f, -4.267949f))
                ),
                Arguments.of(
                    triangleC,
                    Wrapper(Vector2F(8.0f, -5.9f)),
                    Wrapper(Vector2F(8.0f, -5.9f))
                ),
                Arguments.of(
                    triangleC,
                    Wrapper(Vector2F(8.0f, -6.0f)),
                    Wrapper(Vector2F(8.0f, -6.0f))
                ),
                Arguments.of(
                    triangleC,
                    Wrapper(Vector2F(8.0f, -6.1f)),
                    Wrapper(Vector2F(8.0f, -6.0f))
                ),
                Arguments.of(
                    triangleC,
                    Wrapper(Vector2F(7.0866027f, -4.3179493f)),
                    Wrapper(Vector2F(7.0866027f, -4.3179493f))
                ),
                Arguments.of(
                    triangleC,
                    Wrapper(Vector2F(7.0f, -4.267949f)),
                    Wrapper(Vector2F(7.0f, -4.267949f))
                ),
                Arguments.of(
                    triangleC,
                    Wrapper(Vector2F(6.9133973f, -4.217949f)),
                    Wrapper(Vector2F(7.0f, -4.267949f))
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
                Vector2F(-4f, 2f), Vector2F(2f, 2f), Vector2F(1f, 5f)
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
                Vector2F(-2f, 1f), Vector2F(-3f, -3f), Vector2F(1f, -6f)
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
                Vector2F(8f, -2.535898f),
                Vector2F(10f, -6f),
                Vector2F(6f, -6f)
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
        fun equalsAnyArgs(): List<Arguments> = equalsMutableTriangleArgs() + listOf(
            Arguments.of(
                MutableTriangle(
                    Vector2F(-4f, 2f), Vector2F(2f, 2f), Vector2F(1f, 5f)
                ),
                null,
                false
            ),
            Arguments.of(
                MutableTriangle(
                    Vector2F(-4f, 2f), Vector2F(2f, 2f), Vector2F(1f, 5f)
                ),
                DefaultTriangle(
                    Vector2F(-4f, 2f), Vector2F(2f, 2f), Vector2F(1f, 5f)
                ),
                true
            ),
            Arguments.of(
                MutableTriangle(
                    Vector2F(-4f, 2f), Vector2F(2f, 2f), Vector2F(1f, 5f)
                ),
                DefaultTriangle(
                    Vector2F(-4f, 2f), Vector2F(2f, 2f), Vector2F(1.1f, 5f)
                ),
                false
            ),
        )

        @JvmStatic
        fun equalsMutableTriangleArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableTriangle(
                    Vector2F(-4f, 2f), Vector2F(2f, 2f), Vector2F(1f, 5f)
                ),
                MutableTriangle(
                    Vector2F(-4f, 2f), Vector2F(2f, 2f), Vector2F(1f, 5f)
                ),
                true
            ),
            Arguments.of(
                MutableTriangle(
                    Vector2F(-4f, 2f), Vector2F(2f, 2f), Vector2F(1f, 5f)
                ),
                MutableTriangle(
                    Vector2F(-4f, 2f), Vector2F(2f, 2f), Vector2F(1.1f, 5f)
                ),
                false
            ),
        )

        @JvmStatic
        fun hashCodeArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableTriangle(
                    Vector2F(-4f, 2f), Vector2F(2f, 2f), Vector2F(1f, 5f)
                ),
                MutableTriangle(
                    Vector2F(-4f, 2f), Vector2F(2f, 2f), Vector2F(1f, 5f)
                ),
            ),
            Arguments.of(
                MutableTriangle(
                    Vector2F(-2f, 1f), Vector2F(-3f, -3f), Vector2F(1f, -6f)
                ),
                MutableTriangle(
                    Vector2F(-2f, 1f), Vector2F(-3f, -3f), Vector2F(1f, -6f)
                ),
            ),
        )

        @JvmStatic
        fun toStringArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableTriangle(
                    Vector2F(-4f, 2f), Vector2F(2f, 2f), Vector2F(1f, 5f)
                ),
                "Triangle(" +
                        "pointA=${Vector2F(-4f, 2f)}, " +
                        "pointB=${Vector2F(2f, 2f)}, " +
                        "pointC=${Vector2F(1f, 5f)})"
            ),
            Arguments.of(
                MutableTriangle(
                    Vector2F(-2f, 1f), Vector2F(-3f, -3f), Vector2F(1f, -6f)
                ),
                "Triangle(" +
                        "pointA=${Vector2F(-2f, 1f)}, " +
                        "pointB=${Vector2F(-3f, -3f)}, " +
                        "pointC=${Vector2F(1f, -6f)})"
            ),
        )

        @JvmStatic
        fun componentsArgs(): List<Arguments> = pointsArgs()
    }
}