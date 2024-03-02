package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.AngleF
import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F
import com.sztorm.lowallocmath.isApproximately
import com.sztorm.lowallocmath.utils.Wrapper
import com.sztorm.lowallocmath.utils.assertApproximation
import com.sztorm.lowallocmath.world2d.utils.DefaultRegularTriangle
import com.sztorm.lowallocmath.world2d.utils.assertImmutabilityOf
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

class RegularTriangleTests {
    @ParameterizedTest
    @MethodSource("centerArgs")
    fun centerReturnsCorrectValue(triangle: RegularTriangle, expected: Wrapper<Vector2F>) =
        assertImmutabilityOf(triangle) {
            assertApproximation(expected.value, triangle.center)
        }

    @ParameterizedTest
    @MethodSource("orientationArgs")
    fun orientationReturnsCorrectValue(triangle: RegularTriangle, expected: Wrapper<ComplexF>) =
        assertImmutabilityOf(triangle) {
            assertApproximation(expected.value, triangle.orientation)
        }

    @ParameterizedTest
    @MethodSource("sideLengthArgs")
    fun sideLengthReturnsCorrectValue(triangle: RegularTriangle, expected: Float) =
        assertImmutabilityOf(triangle) {
            assertApproximation(expected, triangle.sideLength)
        }

    @ParameterizedTest
    @MethodSource("pointsArgs")
    fun pointsReturnCorrectValues(
        triangle: RegularTriangle,
        expectedPointA: Wrapper<Vector2F>,
        expectedPointB: Wrapper<Vector2F>,
        expectedPointC: Wrapper<Vector2F>,
    ) = assertImmutabilityOf(triangle) {
        assertApproximation(expectedPointA.value, triangle.pointA)
        assertApproximation(expectedPointB.value, triangle.pointB)
        assertApproximation(expectedPointC.value, triangle.pointC)
    }

    @ParameterizedTest
    @MethodSource("areaArgs")
    fun areaReturnsCorrectValue(triangle: RegularTriangle, expected: Float) =
        assertImmutabilityOf(triangle) {
            assertApproximation(expected, triangle.area)
        }

    @ParameterizedTest
    @MethodSource("perimeterArgs")
    fun perimeterReturnsCorrectValue(triangle: RegularTriangle, expected: Float) =
        assertImmutabilityOf(triangle) {
            assertApproximation(expected, triangle.perimeter)
        }

    @ParameterizedTest
    @MethodSource("sideLengthsArgs")
    fun sideLengthsReturnCorrectValues(triangle: RegularTriangle, expected: Float) =
        assertImmutabilityOf(triangle) {
            assertApproximation(expected, triangle.sideLengthAB)
            assertApproximation(expected, triangle.sideLengthBC)
            assertApproximation(expected, triangle.sideLengthCA)
        }

    @ParameterizedTest
    @MethodSource("sideCountArgs")
    fun sideCountReturnsCorrectValue(triangle: RegularTriangle, expected: Int) =
        assertImmutabilityOf(triangle) {
            assertEquals(expected, triangle.sideCount)
        }

    @ParameterizedTest
    @MethodSource("interiorAngleArgs")
    fun interiorAngleReturnsCorrectValue(triangle: RegularTriangle, expected: Wrapper<AngleF>) =
        assertImmutabilityOf(triangle) {
            assertApproximation(expected.value, triangle.interiorAngle)
        }

    @ParameterizedTest
    @MethodSource("exteriorAngleArgs")
    fun exteriorAngleReturnsCorrectValue(triangle: RegularTriangle, expected: Wrapper<AngleF>) =
        assertImmutabilityOf(triangle) {
            assertApproximation(expected.value, triangle.exteriorAngle)
        }

    @ParameterizedTest
    @MethodSource("inradiusArgs")
    fun inradiusReturnsCorrectValue(triangle: RegularTriangle, expected: Float) =
        assertImmutabilityOf(triangle) {
            assertApproximation(expected, triangle.inradius)
        }

    @ParameterizedTest
    @MethodSource("circumradiusArgs")
    fun circumradiusReturnsCorrectValue(triangle: RegularTriangle, expected: Float) =
        assertImmutabilityOf(triangle) {
            assertApproximation(expected, triangle.circumradius)
        }

    @ParameterizedTest
    @MethodSource("positionArgs")
    fun positionReturnsCorrectValue(triangle: RegularTriangle, expected: Wrapper<Vector2F>) =
        assertImmutabilityOf(triangle) {
            assertApproximation(expected.value, triangle.position)
        }

    @ParameterizedTest
    @MethodSource("centroidArgs")
    fun centroidReturnsCorrectValue(triangle: RegularTriangle, expected: Wrapper<Vector2F>) =
        assertImmutabilityOf(triangle) {
            assertApproximation(expected.value, triangle.centroid)
        }

    @ParameterizedTest
    @MethodSource("orthocenterArgs")
    fun orthocenterReturnsCorrectValue(triangle: RegularTriangle, expected: Wrapper<Vector2F>) =
        assertImmutabilityOf(triangle) {
            assertApproximation(expected.value, triangle.orthocenter)
        }

    @ParameterizedTest
    @MethodSource("incenterArgs")
    fun incenterReturnsCorrectValue(triangle: RegularTriangle, expected: Wrapper<Vector2F>) =
        assertImmutabilityOf(triangle) {
            assertApproximation(expected.value, triangle.incenter)
        }

    @ParameterizedTest
    @MethodSource("circumcenterArgs")
    fun circumcenterReturnsCorrectValue(triangle: RegularTriangle, expected: Wrapper<Vector2F>) =
        assertImmutabilityOf(triangle) {
            assertApproximation(expected.value, triangle.circumcenter)
        }

    @ParameterizedTest
    @MethodSource("setArgs")
    fun setMutatesRegularTriangleCorrectly(
        triangle: MutableRegularTriangle,
        center: Wrapper<Vector2F>,
        orientation: Wrapper<ComplexF>,
        sideLength: Float,
        expected: MutableRegularTriangle
    ) = assertEquals(expected, triangle.apply { set(center.value, orientation.value, sideLength) })

    @ParameterizedTest
    @MethodSource("interpolatedArgs")
    fun interpolatedReturnsCorrectValue(
        triangle: RegularTriangle, to: RegularTriangle, by: Float, expected: RegularTriangle
    ) = assertImmutabilityOf(triangle) {
        assertImmutabilityOf(to) {
            assertApproximation(expected, triangle.interpolated(to, by))
        }
    }

    @ParameterizedTest
    @MethodSource("interpolateArgs")
    fun interpolateMutatesRegularTriangleCorrectly(
        triangle: MutableRegularTriangle,
        from: RegularTriangle,
        to: RegularTriangle,
        by: Float,
        expected: MutableRegularTriangle
    ) = assertImmutabilityOf(from) {
        assertImmutabilityOf(to) {
            assertApproximation(expected, triangle.apply { interpolate(from, to, by) })
        }
    }

    @ParameterizedTest
    @MethodSource("closestPointToArgs")
    fun closestPointToReturnsCorrectValue(
        triangle: RegularTriangle, point: Wrapper<Vector2F>, expected: Wrapper<Vector2F>
    ) = assertImmutabilityOf(triangle) {
        assertApproximation(expected.value, triangle.closestPointTo(point.value))
    }

    @ParameterizedTest
    @MethodSource("containsVector2FArgs")
    fun containsReturnsCorrectValue(
        triangle: RegularTriangle, point: Wrapper<Vector2F>, expected: Boolean
    ) = assertImmutabilityOf(triangle) {
        assertEquals(expected, triangle.contains(point.value))
    }

    @ParameterizedTest
    @MethodSource("copyArgs")
    fun copyReturnsCorrectValue(
        triangle: RegularTriangle,
        center: Wrapper<Vector2F>,
        orientation: Wrapper<ComplexF>,
        sideLength: Float,
        expected: RegularTriangle
    ) = assertEquals(expected, triangle.copy(center.value, orientation.value, sideLength))

    @ParameterizedTest
    @MethodSource("equalsAnyArgs")
    fun equalsReturnsCorrectValue(
        triangle: MutableRegularTriangle, other: Any?, expected: Boolean
    ) = assertImmutabilityOf(triangle) {
        assertEquals(expected, triangle == other)
    }

    @ParameterizedTest
    @MethodSource("equalsMutableRegularTriangleArgs")
    fun equalsReturnsCorrectValue(
        triangle: MutableRegularTriangle, other: MutableRegularTriangle, expected: Boolean
    ) = assertImmutabilityOf(triangle) {
        assertImmutabilityOf(other) {
            assertEquals(expected, triangle.equals(other))
        }
    }

    @ParameterizedTest
    @MethodSource("hashCodeArgs")
    fun hashCodeReturnsCorrectValue(
        triangle: MutableRegularTriangle, other: MutableRegularTriangle
    ) = assertImmutabilityOf(triangle) {
        assertImmutabilityOf(other) {
            assertEquals(triangle.hashCode(), other.hashCode())
        }
    }

    @ParameterizedTest
    @MethodSource("toStringArgs")
    fun toStringReturnsCorrectValue(triangle: MutableRegularTriangle, expected: String) =
        assertImmutabilityOf(triangle) {
            assertEquals(expected, triangle.toString())
        }

    @ParameterizedTest
    @MethodSource("componentsArgs")
    fun componentsReturnCorrectValues(
        triangle: RegularTriangle,
        expectedComponent1: Wrapper<Vector2F>,
        expectedComponent2: Wrapper<ComplexF>,
        expectedComponent3: Float
    ) = assertImmutabilityOf(triangle) {
        val (
            actualComponent1: Vector2F,
            actualComponent2: ComplexF,
            actualComponent3: Float
        ) = triangle

        assertEquals(expectedComponent1.value, actualComponent1)
        assertEquals(expectedComponent2.value, actualComponent2)
        assertEquals(expectedComponent3, actualComponent3)
    }

    companion object {
        @JvmStatic
        fun areApproximatelyEqual(
            a: RegularTriangle, b: RegularTriangle, tolerance: Float = 0.00001f
        ): Boolean = a.center.isApproximately(b.center, tolerance) and
                a.orientation.isApproximately(b.orientation, tolerance) and
                a.sideLength.isApproximately(b.sideLength, tolerance) and
                a.pointA.isApproximately(b.pointA, tolerance) and
                a.pointB.isApproximately(b.pointB, tolerance) and
                a.pointC.isApproximately(b.pointC, tolerance)

        @JvmStatic
        fun clone(triangle: RegularTriangle) = triangle.copy()

        @JvmStatic
        fun List<Arguments>.mapRegularTrianglesToDefaultRegularTriangles() = map { args ->
            val argArray = args.get().map {
                if (it is RegularTriangle) DefaultRegularTriangle(
                    it.center, it.orientation, it.sideLength
                )
                else it
            }.toTypedArray()

            Arguments.of(*argArray)
        }

        @JvmStatic
        fun centerArgs(): List<Arguments> {
            val mutableRegularTriangleArgs = listOf(
                Arguments.of(
                    MutableRegularTriangle(
                        center = Vector2F.ZERO, orientation = ComplexF.ONE, sideLength = 5.773503f
                    ),
                    Wrapper(Vector2F.ZERO)
                ),
                Arguments.of(
                    MutableRegularTriangle(
                        center = Vector2F(5f, 7f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-40f)),
                        sideLength = 3f
                    ),
                    Wrapper(Vector2F(5f, 7f))
                ),
                Arguments.of(
                    MutableRegularTriangle(
                        center = Vector2F(3.1547005f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-90f)),
                        sideLength = 4f
                    ),
                    Wrapper(Vector2F(3.1547005f, -4f))
                ),
            )
            val defaultRegularTriangleArgs = mutableRegularTriangleArgs
                .mapRegularTrianglesToDefaultRegularTriangles()

            return listOf(
                mutableRegularTriangleArgs,
                defaultRegularTriangleArgs
            ).flatten()
        }

        @JvmStatic
        fun orientationArgs(): List<Arguments> {
            val mutableRegularTriangleArgs = listOf(
                Arguments.of(
                    MutableRegularTriangle(
                        center = Vector2F.ZERO, orientation = ComplexF.ONE, sideLength = 5.773503f
                    ),
                    Wrapper(ComplexF.ONE)
                ),
                Arguments.of(
                    MutableRegularTriangle(
                        center = Vector2F(5f, 7f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-40f)),
                        sideLength = 3f
                    ),
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(-40f)))
                ),
                Arguments.of(
                    MutableRegularTriangle(
                        center = Vector2F(3.1547005f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-90f)),
                        sideLength = 4f
                    ),
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(-90f)))
                ),
            )
            val defaultRegularTriangleArgs = mutableRegularTriangleArgs
                .mapRegularTrianglesToDefaultRegularTriangles()

            return listOf(
                mutableRegularTriangleArgs,
                defaultRegularTriangleArgs
            ).flatten()
        }

        @JvmStatic
        fun sideLengthArgs(): List<Arguments> {
            val mutableRegularTriangleArgs = listOf(
                Arguments.of(
                    MutableRegularTriangle(
                        center = Vector2F.ZERO, orientation = ComplexF.ONE, sideLength = 5.773503f
                    ),
                    5.773503f
                ),
                Arguments.of(
                    MutableRegularTriangle(
                        center = Vector2F(-4f, 2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                        sideLength = 5.773503f
                    ),
                    5.773503f
                ),
                Arguments.of(
                    MutableRegularTriangle(
                        center = Vector2F(3.1547005f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-90f)),
                        sideLength = 4f
                    ),
                    4f
                ),
            )
            val defaultRegularTriangleArgs = mutableRegularTriangleArgs
                .mapRegularTrianglesToDefaultRegularTriangles()

            return listOf(
                mutableRegularTriangleArgs,
                defaultRegularTriangleArgs
            ).flatten()
        }

        @JvmStatic
        fun pointsArgs(): List<Arguments> {
            val mutableRegularTriangleArgs = listOf(
                Arguments.of(
                    MutableRegularTriangle(
                        center = Vector2F.ZERO, orientation = ComplexF.ONE, sideLength = 5.773503f
                    ),
                    Wrapper(Vector2F(0f, 3.3333333f)),
                    Wrapper(Vector2F(-2.886751f, -1.6666667f)),
                    Wrapper(Vector2F(2.886751f, -1.6666667f))
                ),
                Arguments.of(
                    MutableRegularTriangle(
                        center = Vector2F(3.1547005f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-90f)),
                        sideLength = 4f
                    ),
                    Wrapper(Vector2F(5.464102f, -4f)),
                    Wrapper(Vector2F(2f, -2f)),
                    Wrapper(Vector2F(2f, -6f))
                ),
            )
            val defaultRegularTriangleArgs = mutableRegularTriangleArgs
                .mapRegularTrianglesToDefaultRegularTriangles()

            return listOf(
                mutableRegularTriangleArgs,
                defaultRegularTriangleArgs
            ).flatten()
        }

        @JvmStatic
        fun areaArgs(): List<Arguments> {
            val mutableRegularTriangleArgs = listOf(
                Arguments.of(
                    MutableRegularTriangle(
                        center = Vector2F.ZERO, orientation = ComplexF.ONE, sideLength = 5.773503f
                    ),
                    14.433758f
                ),
                Arguments.of(
                    MutableRegularTriangle(
                        center = Vector2F(-4f, 2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                        sideLength = 5.773503f
                    ),
                    14.433758f
                ),
                Arguments.of(
                    MutableRegularTriangle(
                        center = Vector2F(3.1547005f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-90f)),
                        sideLength = 4f
                    ),
                    6.928203f
                ),
            )
            val defaultRegularTriangleArgs = mutableRegularTriangleArgs
                .mapRegularTrianglesToDefaultRegularTriangles()

            return listOf(
                mutableRegularTriangleArgs,
                defaultRegularTriangleArgs
            ).flatten()
        }

        @JvmStatic
        fun perimeterArgs(): List<Arguments> {
            val mutableRegularTriangleArgs = listOf(
                Arguments.of(
                    MutableRegularTriangle(
                        center = Vector2F.ZERO, orientation = ComplexF.ONE, sideLength = 5.773503f
                    ),
                    17.320509f
                ),
                Arguments.of(
                    MutableRegularTriangle(
                        center = Vector2F(-4f, 2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                        sideLength = 5.773503f
                    ),
                    17.320509f
                ),
                Arguments.of(
                    MutableRegularTriangle(
                        center = Vector2F(3.1547005f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-90f)),
                        sideLength = 4f
                    ),
                    12f
                ),
            )
            val defaultRegularTriangleArgs = mutableRegularTriangleArgs
                .mapRegularTrianglesToDefaultRegularTriangles()

            return listOf(
                mutableRegularTriangleArgs,
                defaultRegularTriangleArgs
            ).flatten()
        }

        @JvmStatic
        fun sideLengthsArgs(): List<Arguments> = sideLengthArgs()

        @JvmStatic
        fun sideCountArgs(): List<Arguments> {
            val mutableRegularTriangleArgs = listOf(
                Arguments.of(
                    MutableRegularTriangle(
                        center = Vector2F.ZERO, orientation = ComplexF.ONE, sideLength = 5.773503f
                    ),
                    3
                ),
                Arguments.of(
                    MutableRegularTriangle(
                        center = Vector2F(-4f, 2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                        sideLength = 5.773503f
                    ),
                    3
                ),
                Arguments.of(
                    MutableRegularTriangle(
                        center = Vector2F(3.1547005f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-90f)),
                        sideLength = 4f
                    ),
                    3
                ),
            )
            val defaultRegularTriangleArgs = mutableRegularTriangleArgs
                .mapRegularTrianglesToDefaultRegularTriangles()

            return listOf(
                mutableRegularTriangleArgs,
                defaultRegularTriangleArgs
            ).flatten()
        }

        @JvmStatic
        fun interiorAngleArgs(): List<Arguments> {
            val mutableRegularTriangleArgs = listOf(
                Arguments.of(
                    MutableRegularTriangle(
                        center = Vector2F.ZERO, orientation = ComplexF.ONE, sideLength = 5.773503f
                    ),
                    Wrapper(AngleF.fromDegrees(60f))
                ),
                Arguments.of(
                    MutableRegularTriangle(
                        center = Vector2F(-4f, 2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                        sideLength = 5.773503f
                    ),
                    Wrapper(AngleF.fromDegrees(60f))
                ),
                Arguments.of(
                    MutableRegularTriangle(
                        center = Vector2F(3.1547005f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-90f)),
                        sideLength = 4f
                    ),
                    Wrapper(AngleF.fromDegrees(60f))
                ),
            )
            val defaultRegularTriangleArgs = mutableRegularTriangleArgs
                .mapRegularTrianglesToDefaultRegularTriangles()

            return listOf(
                mutableRegularTriangleArgs,
                defaultRegularTriangleArgs
            ).flatten()
        }

        @JvmStatic
        fun exteriorAngleArgs(): List<Arguments> {
            val mutableRegularTriangleArgs = listOf(
                Arguments.of(
                    MutableRegularTriangle(
                        center = Vector2F.ZERO, orientation = ComplexF.ONE, sideLength = 5.773503f
                    ),
                    Wrapper(AngleF.fromDegrees(120f))
                ),
                Arguments.of(
                    MutableRegularTriangle(
                        center = Vector2F(-4f, 2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                        sideLength = 5.773503f
                    ),
                    Wrapper(AngleF.fromDegrees(120f))
                ),
                Arguments.of(
                    MutableRegularTriangle(
                        center = Vector2F(3.1547005f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-90f)),
                        sideLength = 4f
                    ),
                    Wrapper(AngleF.fromDegrees(120f))
                ),
            )
            val defaultRegularTriangleArgs = mutableRegularTriangleArgs
                .mapRegularTrianglesToDefaultRegularTriangles()

            return listOf(
                mutableRegularTriangleArgs,
                defaultRegularTriangleArgs
            ).flatten()
        }

        @JvmStatic
        fun inradiusArgs(): List<Arguments> {
            val mutableRegularTriangleArgs = listOf(
                Arguments.of(
                    MutableRegularTriangle(
                        center = Vector2F.ZERO, orientation = ComplexF.ONE, sideLength = 5.773503f
                    ),
                    1.6666667f
                ),
                Arguments.of(
                    MutableRegularTriangle(
                        center = Vector2F(-4f, 2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                        sideLength = 5.773503f
                    ),
                    1.6666667f
                ),
                Arguments.of(
                    MutableRegularTriangle(
                        center = Vector2F(3.1547005f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-90f)),
                        sideLength = 4f
                    ),
                    1.1547005f
                ),
            )
            val defaultRegularTriangleArgs = mutableRegularTriangleArgs
                .mapRegularTrianglesToDefaultRegularTriangles()

            return listOf(
                mutableRegularTriangleArgs,
                defaultRegularTriangleArgs
            ).flatten()
        }

        @JvmStatic
        fun circumradiusArgs(): List<Arguments> {
            val mutableRegularTriangleArgs = listOf(
                Arguments.of(
                    MutableRegularTriangle(
                        center = Vector2F.ZERO, orientation = ComplexF.ONE, sideLength = 5.773503f
                    ),
                    3.3333333f
                ),
                Arguments.of(
                    MutableRegularTriangle(
                        center = Vector2F(-4f, 2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                        sideLength = 5.773503f
                    ),
                    3.3333333f
                ),
                Arguments.of(
                    MutableRegularTriangle(
                        center = Vector2F(3.1547005f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-90f)),
                        sideLength = 4f
                    ),
                    2.309401f
                ),
            )
            val defaultRegularTriangleArgs = mutableRegularTriangleArgs
                .mapRegularTrianglesToDefaultRegularTriangles()

            return listOf(
                mutableRegularTriangleArgs,
                defaultRegularTriangleArgs
            ).flatten()
        }

        @JvmStatic
        fun positionArgs(): List<Arguments> = centerArgs()

        @JvmStatic
        fun centroidArgs(): List<Arguments> = centerArgs()

        @JvmStatic
        fun orthocenterArgs(): List<Arguments> = centerArgs()

        @JvmStatic
        fun incenterArgs(): List<Arguments> = centerArgs()

        @JvmStatic
        fun circumcenterArgs(): List<Arguments> = centerArgs()

        @JvmStatic
        fun setArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableRegularTriangle(
                    center = Vector2F(-4f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                    sideLength = 5.773503f
                ),
                Wrapper(Vector2F(-4f, 2f)),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(30f))),
                5.773503f,
                MutableRegularTriangle(
                    center = Vector2F(-4f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                    sideLength = 5.773503f
                )
            ),
            Arguments.of(
                MutableRegularTriangle(
                    center = Vector2F(-4f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                    sideLength = 5.773503f
                ),
                Wrapper(Vector2F(-4f, 2f)),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(-40f))),
                5.773503f,
                MutableRegularTriangle(
                    center = Vector2F(-4f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-40f)),
                    sideLength = 5.773503f
                )
            ),
            Arguments.of(
                MutableRegularTriangle(
                    center = Vector2F(-4f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                    sideLength = 5.773503f
                ),
                Wrapper(Vector2F(5f, 7f)),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(-40f))),
                3f,
                MutableRegularTriangle(
                    center = Vector2F(5f, 7f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-40f)),
                    sideLength = 3f
                )
            ),
        )

        @JvmStatic
        fun interpolatedArgs(): List<Arguments> {
            val mutableRegularTriangleArgs = interpolateArgs().map {
                Arguments.of(*it.get().drop(1).toTypedArray())
            }
            val defaultRegularTriangleArgs = mutableRegularTriangleArgs
                .mapRegularTrianglesToDefaultRegularTriangles()

            return listOf(
                mutableRegularTriangleArgs,
                defaultRegularTriangleArgs
            ).flatten()
        }

        @JvmStatic
        fun interpolateArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableRegularTriangle(
                    center = Vector2F.ZERO, orientation = ComplexF.ONE, sideLength = 1f
                ),
                MutableRegularTriangle(
                    center = Vector2F(-4f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                    sideLength = 5.773503f
                ),
                MutableRegularTriangle(
                    center = Vector2F(-4f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                    sideLength = 5.773503f
                ),
                0.5f,
                MutableRegularTriangle(
                    center = Vector2F(-4f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                    sideLength = 5.773503f
                )
            ),
            Arguments.of(
                MutableRegularTriangle(
                    center = Vector2F.ZERO, orientation = ComplexF.ONE, sideLength = 1f
                ),
                MutableRegularTriangle(
                    center = Vector2F(-4f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                    sideLength = 5.773503f
                ),
                MutableRegularTriangle(
                    center = Vector2F(3.1547005f, -4f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-90f)),
                    sideLength = 4f
                ),
                0f,
                MutableRegularTriangle(
                    center = Vector2F(-4f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                    sideLength = 5.773503f
                )
            ),
            Arguments.of(
                MutableRegularTriangle(
                    center = Vector2F.ZERO, orientation = ComplexF.ONE, sideLength = 1f
                ),
                MutableRegularTriangle(
                    center = Vector2F(-4f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                    sideLength = 5.773503f
                ),
                MutableRegularTriangle(
                    center = Vector2F(3.1547005f, -4f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-90f)),
                    sideLength = 4f
                ),
                1f,
                MutableRegularTriangle(
                    center = Vector2F(3.1547005f, -4f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-90f)),
                    sideLength = 4f
                )
            ),
            Arguments.of(
                MutableRegularTriangle(
                    center = Vector2F.ZERO, orientation = ComplexF.ONE, sideLength = 1f
                ),
                MutableRegularTriangle(
                    center = Vector2F(-4f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                    sideLength = 5.773503f
                ),
                MutableRegularTriangle(
                    center = Vector2F(3.1547005f, -4f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-90f)),
                    sideLength = 4f
                ),
                0.5f,
                MutableRegularTriangle(
                    center = Vector2F(-0.4226498f, -1f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-30f)),
                    sideLength = 4.886752f
                )
            ),
        )

        @JvmStatic
        fun closestPointToArgs(): List<Arguments> {
            val pointA = Vector2F(5.464102f, -4f)
            val pointB = Vector2F(2f, -2f)
            val pointC = Vector2F(2f, -6f)
            val center: Vector2F = (pointA + pointB + pointC) / 3f
            val triangle = MutableRegularTriangle(
                center,
                orientation = ComplexF.fromAngle(AngleF.fromDegrees(-90f)) *
                        ComplexF(pointA.x - center.x, pointA.y - center.y).normalized,
                sideLength = pointA.distanceTo(pointB)
            )
            val mutableRegularTriangleArgs = listOf(
                Arguments.of(
                    triangle,
                    Wrapper(Vector2F(6f, -3.25f)),
                    Wrapper(Vector2F(5.464102f, -4f))
                ),
                Arguments.of(
                    triangle,
                    Wrapper(Vector2F(5.5f, -3f)),
                    Wrapper(Vector2F(5.058013f, -3.7655447f))
                ),
                Arguments.of(
                    triangle,
                    Wrapper(Vector2F(3.5f, -2f)),
                    Wrapper(Vector2F(3.125f, -2.649519f))
                ),
                Arguments.of(
                    triangle,
                    Wrapper(Vector2F(2.1f, -1.5f)),
                    Wrapper(Vector2F(2f, -2f))
                ),
                Arguments.of(
                    triangle,
                    Wrapper(Vector2F(1.5f, -1.75f)),
                    Wrapper(Vector2F(2f, -2f))
                ),
                Arguments.of(
                    triangle,
                    Wrapper(Vector2F(1f, -2.75f)),
                    Wrapper(Vector2F(2f, -2.75f))
                ),
                Arguments.of(
                    triangle,
                    Wrapper(Vector2F(1f, -4.5f)),
                    Wrapper(Vector2F(2f, -4.5f))
                ),
                Arguments.of(
                    triangle,
                    Wrapper(Vector2F(1f, -6.25f)),
                    Wrapper(Vector2F(2f, -6f))
                ),
                Arguments.of(
                    triangle,
                    Wrapper(Vector2F(2f, -7f)),
                    Wrapper(Vector2F(2f, -6f))
                ),
                Arguments.of(
                    triangle,
                    Wrapper(Vector2F(3.5f, -6.5f)),
                    Wrapper(Vector2F(2.9084935f, -5.475481f))
                ),
                Arguments.of(
                    triangle,
                    Wrapper(Vector2F(5f, -5.75f)),
                    Wrapper(Vector2F(4.3582535f, -4.638462f))
                ),
                Arguments.of(
                    triangle,
                    Wrapper(Vector2F(6f, -4.5f)),
                    Wrapper(Vector2F(5.464102f, -4f))
                ),
                Arguments.of(
                    triangle,
                    Wrapper(Vector2F(5.364102f, -4f)),
                    Wrapper(Vector2F(5.364102f, -4f))
                ),
                Arguments.of(
                    triangle,
                    Wrapper(Vector2F(3.682051f, -3.0866027f)),
                    Wrapper(Vector2F(3.682051f, -3.0866027f))
                ),
                Arguments.of(
                    triangle,
                    Wrapper(Vector2F(2.05f, -2.0866024f)),
                    Wrapper(Vector2F(2.05f, -2.0866024f))
                ),
                Arguments.of(
                    triangle,
                    Wrapper(Vector2F(2.1f, -4f)),
                    Wrapper(Vector2F(2.1f, -4f))
                ),
                Arguments.of(
                    triangle,
                    Wrapper(Vector2F(2.05f, -5.913398f)),
                    Wrapper(Vector2F(2.05f, -5.913398f))
                ),
                Arguments.of(
                    triangle,
                    Wrapper(Vector2F(3.682051f, -4.9133973f)),
                    Wrapper(Vector2F(3.682051f, -4.9133973f))
                ),
                Arguments.of(
                    triangle,
                    Wrapper(center),
                    Wrapper(center)
                ),
            )
            val defaultRegularTriangleArgs = mutableRegularTriangleArgs
                .mapRegularTrianglesToDefaultRegularTriangles()

            return listOf(
                mutableRegularTriangleArgs,
                defaultRegularTriangleArgs
            ).flatten()
        }

        @JvmStatic
        fun containsVector2FArgs(): List<Arguments> {
            val pointA = Vector2F(5.464102f, -4f)
            val pointB = Vector2F(2f, -2f)
            val pointC = Vector2F(2f, -6f)
            val center: Vector2F = (pointA + pointB + pointC) / 3f
            val triangle = MutableRegularTriangle(
                center,
                orientation = ComplexF.fromAngle(AngleF.fromDegrees(-90f)) *
                        ComplexF(pointA.x - center.x, pointA.y - center.y).normalized,
                sideLength = pointA.distanceTo(pointB)
            )
            val mutableRegularTriangleArgs = listOf(
                Arguments.of(triangle, Wrapper(Vector2F(5.364102f, -4f)), true),
                Arguments.of(triangle, Wrapper(Vector2F(5.564102f, -4f)), false),
                Arguments.of(triangle, Wrapper(Vector2F(3.682051f, -3.0866027f)), true),
                Arguments.of(triangle, Wrapper(Vector2F(3.7820508f, -2.9133973f)), false),
                Arguments.of(triangle, Wrapper(Vector2F(2.05f, -2.0866024f)), true),
                Arguments.of(triangle, Wrapper(Vector2F(1.95f, -1.9133976f)), false),
                Arguments.of(triangle, Wrapper(Vector2F(2.1f, -4f)), true),
                Arguments.of(triangle, Wrapper(Vector2F(1.9f, -4f)), false),
                Arguments.of(triangle, Wrapper(Vector2F(2.05f, -5.913398f)), true),
                Arguments.of(triangle, Wrapper(Vector2F(1.95f, -6.086602f)), false),
                Arguments.of(triangle, Wrapper(Vector2F(3.682051f, -4.9133973f)), true),
                Arguments.of(triangle, Wrapper(Vector2F(3.7820508f, -5.0866027f)), false),
                Arguments.of(triangle, Wrapper(center), true),
            )
            val defaultRegularTriangleArgs = mutableRegularTriangleArgs
                .mapRegularTrianglesToDefaultRegularTriangles()

            return listOf(
                mutableRegularTriangleArgs,
                defaultRegularTriangleArgs
            ).flatten()
        }

        @JvmStatic
        fun copyArgs(): List<Arguments> {
            val mutableRegularTriangleArgs = setArgs()
            val defaultRegularTriangleArgs = mutableRegularTriangleArgs
                .mapRegularTrianglesToDefaultRegularTriangles()

            return listOf(
                mutableRegularTriangleArgs,
                defaultRegularTriangleArgs
            ).flatten()
        }

        @JvmStatic
        fun equalsAnyArgs(): List<Arguments> = equalsMutableRegularTriangleArgs() + listOf(
            Arguments.of(
                MutableRegularTriangle(
                    center = Vector2F(-4f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                    sideLength = 5.773503f
                ),
                null,
                false
            ),
            Arguments.of(
                MutableRegularTriangle(
                    center = Vector2F(-4f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                    sideLength = 5.773503f
                ),
                DefaultRegularTriangle(
                    center = Vector2F(-4f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                    sideLength = 5.773503f
                ),
                true
            ),
            Arguments.of(
                MutableRegularTriangle(
                    center = Vector2F(-4f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                    sideLength = 5.773503f
                ),
                DefaultRegularTriangle(
                    center = Vector2F(-4.1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                    sideLength = 5.773503f
                ),
                false
            ),
        )

        @JvmStatic
        fun equalsMutableRegularTriangleArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableRegularTriangle(
                    center = Vector2F(-4f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                    sideLength = 5.773503f
                ),
                MutableRegularTriangle(
                    center = Vector2F(-4f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                    sideLength = 5.773503f
                ),
                true
            ),
            Arguments.of(
                MutableRegularTriangle(
                    center = Vector2F(-4f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                    sideLength = 5.773503f
                ),
                MutableRegularTriangle(
                    center = Vector2F(-4.1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                    sideLength = 5.773503f
                ),
                false
            ),
        )

        @JvmStatic
        fun hashCodeArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableRegularTriangle(
                    center = Vector2F(-4f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                    sideLength = 5.773503f
                ),
                MutableRegularTriangle(
                    center = Vector2F(-4f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                    sideLength = 5.773503f
                )
            ),
            Arguments.of(
                MutableRegularTriangle(
                    center = Vector2F(5f, 7f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-40f)),
                    sideLength = 3f
                ),
                MutableRegularTriangle(
                    center = Vector2F(5f, 7f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-40f)),
                    sideLength = 3f
                )
            ),
        )

        @JvmStatic
        fun toStringArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableRegularTriangle(
                    center = Vector2F(-4f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                    sideLength = 5.773503f
                ),
                "RegularTriangle(" +
                        "center=${Vector2F(-4f, 2f)}, " +
                        "orientation=${ComplexF.fromAngle(AngleF.fromDegrees(30f))}, " +
                        "sideLength=${5.773503f})"
            ),
            Arguments.of(
                MutableRegularTriangle(
                    center = Vector2F(5f, 7f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-40f)),
                    sideLength = 3f
                ),
                "RegularTriangle(" +
                        "center=${Vector2F(5f, 7f)}, " +
                        "orientation=${ComplexF.fromAngle(AngleF.fromDegrees(-40f))}, " +
                        "sideLength=${3f})"
            ),
        )

        @JvmStatic
        fun componentsArgs(): List<Arguments> {
            val mutableRegularTriangleArgs = listOf(
                Arguments.of(
                    MutableRegularTriangle(
                        center = Vector2F(-4f, 2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                        sideLength = 5.773503f
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(30f))),
                    5.773503f
                ),
                Arguments.of(
                    MutableRegularTriangle(
                        center = Vector2F(5f, 7f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-40f)),
                        sideLength = 3f
                    ),
                    Wrapper(Vector2F(5f, 7f)),
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(-40f))),
                    3f
                ),
            )
            val defaultRegularTriangleArgs = mutableRegularTriangleArgs
                .mapRegularTrianglesToDefaultRegularTriangles()

            return listOf(
                mutableRegularTriangleArgs,
                defaultRegularTriangleArgs
            ).flatten()
        }
    }
}