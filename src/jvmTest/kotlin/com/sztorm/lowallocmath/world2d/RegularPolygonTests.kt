package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.*
import com.sztorm.lowallocmath.utils.Wrapper
import com.sztorm.lowallocmath.utils.assertApproximation
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

class RegularPolygonTests {
    @Test
    fun constructorThrowsWhenSideCountIsLessThanTwo() {
        assertThrows<IllegalArgumentException> {
            RegularPolygon(
                center = Vector2F.ZERO, rotation = ComplexF.ONE, sideLength = 2f, sideCount = 1
            )
        }
        assertThrows<IllegalArgumentException> {
            RegularPolygon(
                center = Vector2F.ZERO, rotation = ComplexF.ONE, sideLength = 2f, sideCount = 0
            )
        }
        assertThrows<IllegalArgumentException> {
            RegularPolygon(
                center = Vector2F.ZERO, rotation = ComplexF.ONE, sideLength = 2f, sideCount = -1
            )
        }
    }

    @ParameterizedTest
    @MethodSource("pointsArgs")
    fun pointsReturnCorrectValues(
        polygon: RegularPolygon, expectedPoints: Wrapper<Vector2FArray>
    ) {
        assertEquals(expectedPoints.value.size, polygon.points.size)

        for (i in expectedPoints.value.indices) {
            assertApproximation(expectedPoints.value[i], polygon.points[i])
        }
    }

    @ParameterizedTest
    @MethodSource("sideLengthArgs")
    fun sideLengthReturnsCorrectValue(polygon: RegularPolygon, expected: Float) =
        assertApproximation(expected, polygon.sideLength)

    @ParameterizedTest
    @MethodSource("sideCountArgs")
    fun sideCountReturnsCorrectValue(polygon: RegularPolygon, expected: Int) =
        assertEquals(expected, polygon.sideCount)

    @ParameterizedTest
    @MethodSource("areaArgs")
    fun areaReturnsCorrectValue(polygon: RegularPolygon, expected: Float) =
        assertApproximation(expected, polygon.area)

    @ParameterizedTest
    @MethodSource("perimeterArgs")
    fun perimeterReturnsCorrectValue(polygon: RegularPolygon, expected: Float) =
        assertApproximation(expected, polygon.perimeter)

    @ParameterizedTest
    @MethodSource("interiorAngleArgs")
    fun interiorAngleReturnsCorrectValue(polygon: RegularPolygon, expected: Wrapper<AngleF>) =
        assertApproximation(expected.value, polygon.interiorAngle)

    @ParameterizedTest
    @MethodSource("exteriorAngleArgs")
    fun exteriorAngleReturnsCorrectValue(polygon: RegularPolygon, expected: Wrapper<AngleF>) =
        assertApproximation(expected.value, polygon.exteriorAngle)

    @ParameterizedTest
    @MethodSource("inradiusArgs")
    fun inradiusReturnsCorrectValue(polygon: RegularPolygon, expected: Float) =
        assertApproximation(expected, polygon.inradius)

    @ParameterizedTest
    @MethodSource("circumradiusArgs")
    fun circumradiusReturnsCorrectValue(polygon: RegularPolygon, expected: Float) =
        assertApproximation(expected, polygon.circumradius)

    @ParameterizedTest
    @MethodSource("closestPointToArgs")
    fun closestPointToReturnsCorrectValue(
        polygon: RegularPolygon, point: Wrapper<Vector2F>, expected: Wrapper<Vector2F>
    ) = assertApproximation(expected.value, polygon.closestPointTo(point.value))

    @ParameterizedTest
    @MethodSource("containsVector2FArgs")
    fun containsReturnsCorrectValue(
        polygon: RegularPolygon, point: Wrapper<Vector2F>, expected: Boolean
    ) = assertEquals(expected, polygon.contains(point.value))

    @ParameterizedTest
    @MethodSource("copyArgs")
    fun copyReturnsCorrectValue(
        polygon: RegularPolygon,
        center: Wrapper<Vector2F>,
        rotation: Wrapper<ComplexF>,
        sideLength: Float,
        sideCount: Int,
        expected: RegularPolygon
    ) = assertEquals(expected, polygon.copy(center.value, rotation.value, sideLength, sideCount))

    @ParameterizedTest
    @MethodSource("equalsArgs")
    fun equalsReturnsCorrectValue(
        polygon: RegularPolygon, other: Any?, expected: Boolean
    ) = assertEquals(expected, polygon == other)

    @ParameterizedTest
    @MethodSource("equalsRegularPolygonArgs")
    fun equalsReturnsCorrectValue(
        polygon: RegularPolygon, other: RegularPolygon, expected: Boolean
    ) = assertEquals(expected, polygon == other)

    @ParameterizedTest
    @MethodSource("hashCodeArgs")
    fun hashCodeReturnsCorrectValue(polygon: RegularPolygon, other: RegularPolygon) =
        assertEquals(polygon.hashCode(), other.hashCode())

    @ParameterizedTest
    @MethodSource("toStringArgs")
    fun toStringReturnsCorrectValue(polygon: RegularPolygon, expected: String) =
        assertEquals(expected, polygon.toString())

    @ParameterizedTest
    @MethodSource("componentsArgs")
    fun componentsReturnCorrectValues(
        polygon: RegularPolygon,
        expectedComponent1: Wrapper<Vector2F>,
        expectedComponent2: Wrapper<ComplexF>,
        expectedComponent3: Float,
        expectedComponent4: Int,
    ) {
        val (actualComponent1, actualComponent2, actualComponent3, actualComponent4) = polygon

        assertEquals(expectedComponent1.value, actualComponent1)
        assertEquals(expectedComponent2.value, actualComponent2)
        assertEquals(expectedComponent3, actualComponent3)
        assertEquals(expectedComponent4, actualComponent4)
    }

    companion object {
        @JvmStatic
        fun pointsArgs(): List<Arguments> = listOf(
            Arguments.of(
                RegularPolygon(
                    Vector2F(14f, 1f),
                    ComplexF.fromAngle(AngleF.fromDegrees(-72f)),
                    sideLength = 2f,
                    sideCount = 10
                ),
                Wrapper(
                    arrayOf(
                        Vector2F(17.236069f, 1f),
                        Vector2F(16.618034f, 2.902113f),
                        Vector2F(15.0f, 4.0776834f),
                        Vector2F(13.0f, 4.0776834f),
                        Vector2F(11.381966f, 2.902113f),
                        Vector2F(10.763932f, 1f),
                        Vector2F(11.381966f, -0.90211296f),
                        Vector2F(13.0f, -2.0776834f),
                        Vector2F(15.0f, -2.0776834f),
                        Vector2F(16.618034f, -0.9021131f),
                    ).toVector2FArray()
                )
            ),
            Arguments.of(
                RegularPolygon(
                    Vector2F(0f, 8f),
                    ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                    sideLength = 3f,
                    sideCount = 7
                ),
                Wrapper(
                    arrayOf(
                        Vector2F(-2.9939773f, 6.2714257f),
                        Vector2F(-0.5152612f, 4.5814657f),
                        Vector2F(2.3514576f, 5.4657316f),
                        Vector2F(3.4474807f, 8.258353f),
                        Vector2F(1.9474803f, 10.856429f),
                        Vector2F(-1.0190125f, 11.303556f),
                        Vector2F(-3.218168f, 9.263038f),
                    ).toVector2FArray()
                )
            ),
            Arguments.of(
                RegularPolygon(
                    Vector2F(-8f, 2f),
                    ComplexF.fromAngle(AngleF.fromDegrees(-30f)),
                    sideLength = 3.5f,
                    sideCount = 6
                ),
                Wrapper(
                    arrayOf(
                        Vector2F(-4.968911f, 3.75f),
                        Vector2F(-8f, 5.5f),
                        Vector2F(-11.031089f, 3.75f),
                        Vector2F(-11.031089f, 0.25f),
                        Vector2F(-8f, -1.5f),
                        Vector2F(-4.968911f, 0.25f),
                    ).toVector2FArray()
                )
            ),
            Arguments.of(
                RegularPolygon(
                    Vector2F(-7.5f, -8f),
                    ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    sideLength = 4f,
                    sideCount = 5
                ),
                Wrapper(
                    arrayOf(
                        Vector2F(-9.906004f, -5.5939965f),
                        Vector2F(-10.531742f, -9.544749f),
                        Vector2F(-6.9677157f, -11.360712f),
                        Vector2F(-4.1392884f, -8.532285f),
                        Vector2F(-5.9552507f, -4.9682584f),
                    ).toVector2FArray()
                )
            ),
            Arguments.of(
                RegularPolygon(
                    Vector2F(2f, -7.5f),
                    ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                    sideLength = 4f,
                    sideCount = 4
                ),
                Wrapper(
                    arrayOf(
                        Vector2F(4.8284273f, -7.5f),
                        Vector2F(2f, -4.6715727f),
                        Vector2F(-0.82842684f, -7.5f),
                        Vector2F(2f, -10.328426f),
                    ).toVector2FArray()
                )
            ),
            Arguments.of(
                RegularPolygon(
                    Vector2F(10f, -6.5f),
                    ComplexF.fromAngle(AngleF.fromDegrees(135f)),
                    sideLength = 4f,
                    sideCount = 3
                ),
                Wrapper(
                    arrayOf(
                        Vector2F(8.367007f, -8.132993f),
                        Vector2F(12.23071f, -7.097717f),
                        Vector2F(9.402283f, -4.26929f),
                    ).toVector2FArray()
                )
            ),
            Arguments.of(
                RegularPolygon(
                    Vector2F(10f, 6.5f),
                    ComplexF.fromAngle(AngleF.fromDegrees(195f)),
                    sideLength = 4f,
                    sideCount = 2
                ),
                Wrapper(
                    arrayOf(
                        Vector2F(8.068149f, 5.982362f),
                        Vector2F(11.931851f, 7.017638f),
                    ).toVector2FArray()
                )
            ),
        )

        @JvmStatic
        fun sideLengthArgs(): List<Arguments> = listOf(
            Arguments.of(
                RegularPolygon(
                    Vector2F(14f, 1f),
                    ComplexF.fromAngle(AngleF.fromDegrees(-72f)),
                    sideLength = 2f,
                    sideCount = 10
                ),
                2f
            ),
            Arguments.of(
                RegularPolygon(
                    Vector2F(0f, 8f),
                    ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                    sideLength = 3f,
                    sideCount = 7
                ),
                3f
            ),
            Arguments.of(
                RegularPolygon(
                    Vector2F(-8f, 2f),
                    ComplexF.fromAngle(AngleF.fromDegrees(-30f)),
                    sideLength = 3.5f,
                    sideCount = 6
                ),
                3.5f
            ),
            Arguments.of(
                RegularPolygon(
                    Vector2F(-7.5f, -8f),
                    ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    sideLength = 4f,
                    sideCount = 5
                ),
                4f
            ),
            Arguments.of(
                RegularPolygon(
                    Vector2F(2f, -7.5f),
                    ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                    sideLength = 4f,
                    sideCount = 4
                ),
                4f
            ),
            Arguments.of(
                RegularPolygon(
                    Vector2F(10f, -6.5f),
                    ComplexF.fromAngle(AngleF.fromDegrees(135f)),
                    sideLength = 4f,
                    sideCount = 3
                ),
                4f
            ),
            Arguments.of(
                RegularPolygon(
                    Vector2F(10f, 6.5f),
                    ComplexF.fromAngle(AngleF.fromDegrees(195f)),
                    sideLength = 4f,
                    sideCount = 2
                ),
                4f
            ),
        )

        @JvmStatic
        fun sideCountArgs(): List<Arguments> = listOf(
            Arguments.of(
                RegularPolygon(
                    Vector2F(14f, 1f),
                    ComplexF.fromAngle(AngleF.fromDegrees(-72f)),
                    sideLength = 2f,
                    sideCount = 10
                ),
                10
            ),
            Arguments.of(
                RegularPolygon(
                    Vector2F(0f, 8f),
                    ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                    sideLength = 3f,
                    sideCount = 7
                ),
                7
            ),
            Arguments.of(
                RegularPolygon(
                    Vector2F(-8f, 2f),
                    ComplexF.fromAngle(AngleF.fromDegrees(-30f)),
                    sideLength = 3.5f,
                    sideCount = 6
                ),
                6
            ),
            Arguments.of(
                RegularPolygon(
                    Vector2F(-7.5f, -8f),
                    ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    sideLength = 4f,
                    sideCount = 5
                ),
                5
            ),
            Arguments.of(
                RegularPolygon(
                    Vector2F(2f, -7.5f),
                    ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                    sideLength = 4f,
                    sideCount = 4
                ),
                4
            ),
            Arguments.of(
                RegularPolygon(
                    Vector2F(10f, -6.5f),
                    ComplexF.fromAngle(AngleF.fromDegrees(135f)),
                    sideLength = 4f,
                    sideCount = 3
                ),
                3
            ),
            Arguments.of(
                RegularPolygon(
                    Vector2F(10f, 6.5f),
                    ComplexF.fromAngle(AngleF.fromDegrees(195f)),
                    sideLength = 4f,
                    sideCount = 2
                ),
                2
            ),
        )

        @JvmStatic
        fun areaArgs(): List<Arguments> = listOf(
            Arguments.of(
                RegularPolygon(
                    Vector2F(14f, 1f),
                    ComplexF.fromAngle(AngleF.fromDegrees(-72f)),
                    sideLength = 2f,
                    sideCount = 10
                ),
                30.77684f
            ),
            Arguments.of(
                RegularPolygon(
                    Vector2F(0f, 8f),
                    ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                    sideLength = 3f,
                    sideCount = 7
                ),
                32.70521f
            ),
            Arguments.of(
                RegularPolygon(
                    Vector2F(-8f, 2f),
                    ComplexF.fromAngle(AngleF.fromDegrees(-30f)),
                    sideLength = 3.5f,
                    sideCount = 6
                ),
                31.82643f
            ),
            Arguments.of(
                RegularPolygon(
                    Vector2F(-7.5f, -8f),
                    ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    sideLength = 4f,
                    sideCount = 5
                ),
                27.52764f
            ),
            Arguments.of(
                RegularPolygon(
                    Vector2F(2f, -7.5f),
                    ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                    sideLength = 4f,
                    sideCount = 4
                ),
                16f
            ),
            Arguments.of(
                RegularPolygon(
                    Vector2F(10f, -6.5f),
                    ComplexF.fromAngle(AngleF.fromDegrees(135f)),
                    sideLength = 4f,
                    sideCount = 3
                ),
                6.928203f
            ),
            Arguments.of(
                RegularPolygon(
                    Vector2F(10f, 6.5f),
                    ComplexF.fromAngle(AngleF.fromDegrees(195f)),
                    sideLength = 4f,
                    sideCount = 2
                ),
                0f
            ),
        )

        @JvmStatic
        fun perimeterArgs(): List<Arguments> = listOf(
            Arguments.of(
                RegularPolygon(
                    Vector2F(14f, 1f),
                    ComplexF.fromAngle(AngleF.fromDegrees(-72f)),
                    sideLength = 2f,
                    sideCount = 10
                ),
                20f
            ),
            Arguments.of(
                RegularPolygon(
                    Vector2F(0f, 8f),
                    ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                    sideLength = 3f,
                    sideCount = 7
                ),
                21f
            ),
            Arguments.of(
                RegularPolygon(
                    Vector2F(-8f, 2f),
                    ComplexF.fromAngle(AngleF.fromDegrees(-30f)),
                    sideLength = 3.5f,
                    sideCount = 6
                ),
                21f
            ),
            Arguments.of(
                RegularPolygon(
                    Vector2F(-7.5f, -8f),
                    ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    sideLength = 4f,
                    sideCount = 5
                ),
                20f
            ),
            Arguments.of(
                RegularPolygon(
                    Vector2F(2f, -7.5f),
                    ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                    sideLength = 4f,
                    sideCount = 4
                ),
                16f
            ),
            Arguments.of(
                RegularPolygon(
                    Vector2F(10f, -6.5f),
                    ComplexF.fromAngle(AngleF.fromDegrees(135f)),
                    sideLength = 4f,
                    sideCount = 3
                ),
                12f
            ),
            Arguments.of(
                RegularPolygon(
                    Vector2F(10f, 6.5f),
                    ComplexF.fromAngle(AngleF.fromDegrees(195f)),
                    sideLength = 4f,
                    sideCount = 2
                ),
                8f
            ),
        )

        @JvmStatic
        fun interiorAngleArgs(): List<Arguments> = listOf(
            Arguments.of(
                RegularPolygon(
                    Vector2F(14f, 1f),
                    ComplexF.fromAngle(AngleF.fromDegrees(-72f)),
                    sideLength = 2f,
                    sideCount = 10
                ),
                Wrapper(AngleF.fromDegrees(144f))
            ),
            Arguments.of(
                RegularPolygon(
                    Vector2F(0f, 8f),
                    ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                    sideLength = 3f,
                    sideCount = 7
                ),
                Wrapper(AngleF.fromDegrees(128.5714f))
            ),
            Arguments.of(
                RegularPolygon(
                    Vector2F(-8f, 2f),
                    ComplexF.fromAngle(AngleF.fromDegrees(-30f)),
                    sideLength = 3.5f,
                    sideCount = 6
                ),
                Wrapper(AngleF.fromDegrees(120f))
            ),
            Arguments.of(
                RegularPolygon(
                    Vector2F(-7.5f, -8f),
                    ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    sideLength = 4f,
                    sideCount = 5
                ),
                Wrapper(AngleF.fromDegrees(108f))
            ),
            Arguments.of(
                RegularPolygon(
                    Vector2F(2f, -7.5f),
                    ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                    sideLength = 4f,
                    sideCount = 4
                ),
                Wrapper(AngleF.fromDegrees(90f))
            ),
            Arguments.of(
                RegularPolygon(
                    Vector2F(10f, -6.5f),
                    ComplexF.fromAngle(AngleF.fromDegrees(135f)),
                    sideLength = 4f,
                    sideCount = 3
                ),
                Wrapper(AngleF.fromDegrees(60f))
            ),
            Arguments.of(
                RegularPolygon(
                    Vector2F(10f, 6.5f),
                    ComplexF.fromAngle(AngleF.fromDegrees(195f)),
                    sideLength = 4f,
                    sideCount = 2
                ),
                Wrapper(AngleF.fromDegrees(0f))
            ),
        )

        @JvmStatic
        fun exteriorAngleArgs(): List<Arguments> = listOf(
            Arguments.of(
                RegularPolygon(
                    Vector2F(14f, 1f),
                    ComplexF.fromAngle(AngleF.fromDegrees(-72f)),
                    sideLength = 2f,
                    sideCount = 10
                ),
                Wrapper(AngleF.fromDegrees(36f))
            ),
            Arguments.of(
                RegularPolygon(
                    Vector2F(0f, 8f),
                    ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                    sideLength = 3f,
                    sideCount = 7
                ),
                Wrapper(AngleF.fromDegrees(51.42857f))
            ),
            Arguments.of(
                RegularPolygon(
                    Vector2F(-8f, 2f),
                    ComplexF.fromAngle(AngleF.fromDegrees(-30f)),
                    sideLength = 3.5f,
                    sideCount = 6
                ),
                Wrapper(AngleF.fromDegrees(60f))
            ),
            Arguments.of(
                RegularPolygon(
                    Vector2F(-7.5f, -8f),
                    ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    sideLength = 4f,
                    sideCount = 5
                ),
                Wrapper(AngleF.fromDegrees(72f))
            ),
            Arguments.of(
                RegularPolygon(
                    Vector2F(2f, -7.5f),
                    ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                    sideLength = 4f,
                    sideCount = 4
                ),
                Wrapper(AngleF.fromDegrees(90f))
            ),
            Arguments.of(
                RegularPolygon(
                    Vector2F(10f, -6.5f),
                    ComplexF.fromAngle(AngleF.fromDegrees(135f)),
                    sideLength = 4f,
                    sideCount = 3
                ),
                Wrapper(AngleF.fromDegrees(120f))
            ),
            Arguments.of(
                RegularPolygon(
                    Vector2F(10f, 6.5f),
                    ComplexF.fromAngle(AngleF.fromDegrees(195f)),
                    sideLength = 4f,
                    sideCount = 2
                ),
                Wrapper(AngleF.fromDegrees(180f))
            ),
        )

        @JvmStatic
        fun inradiusArgs(): List<Arguments> = listOf(
            Arguments.of(
                RegularPolygon(
                    Vector2F(14f, 1f),
                    ComplexF.fromAngle(AngleF.fromDegrees(-72f)),
                    sideLength = 2f,
                    sideCount = 10
                ),
                3.077684f
            ),
            Arguments.of(
                RegularPolygon(
                    Vector2F(0f, 8f),
                    ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                    sideLength = 3f,
                    sideCount = 7
                ),
                3.114782f
            ),
            Arguments.of(
                RegularPolygon(
                    Vector2F(-8f, 2f),
                    ComplexF.fromAngle(AngleF.fromDegrees(-30f)),
                    sideLength = 3.5f,
                    sideCount = 6
                ),
                3.031089f
            ),
            Arguments.of(
                RegularPolygon(
                    Vector2F(-7.5f, -8f),
                    ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    sideLength = 4f,
                    sideCount = 5
                ),
                2.752764f
            ),
            Arguments.of(
                RegularPolygon(
                    Vector2F(2f, -7.5f),
                    ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                    sideLength = 4f,
                    sideCount = 4
                ),
                2f
            ),
            Arguments.of(
                RegularPolygon(
                    Vector2F(10f, -6.5f),
                    ComplexF.fromAngle(AngleF.fromDegrees(135f)),
                    sideLength = 4f,
                    sideCount = 3
                ),
                1.1547005f
            ),
            Arguments.of(
                RegularPolygon(
                    Vector2F(10f, 6.5f),
                    ComplexF.fromAngle(AngleF.fromDegrees(195f)),
                    sideLength = 4f,
                    sideCount = 2
                ),
                0f
            ),
        )

        @JvmStatic
        fun circumradiusArgs(): List<Arguments> = listOf(
            Arguments.of(
                RegularPolygon(
                    Vector2F(14f, 1f),
                    ComplexF.fromAngle(AngleF.fromDegrees(-72f)),
                    sideLength = 2f,
                    sideCount = 10
                ),
                3.236068f
            ),
            Arguments.of(
                RegularPolygon(
                    Vector2F(0f, 8f),
                    ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                    sideLength = 3f,
                    sideCount = 7
                ),
                3.457147f
            ),
            Arguments.of(
                RegularPolygon(
                    Vector2F(-8f, 2f),
                    ComplexF.fromAngle(AngleF.fromDegrees(-30f)),
                    sideLength = 3.5f,
                    sideCount = 6
                ),
                3.5f
            ),
            Arguments.of(
                RegularPolygon(
                    Vector2F(-7.5f, -8f),
                    ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    sideLength = 4f,
                    sideCount = 5
                ),
                3.402603f
            ),
            Arguments.of(
                RegularPolygon(
                    Vector2F(2f, -7.5f),
                    ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                    sideLength = 4f,
                    sideCount = 4
                ),
                2.828427f
            ),
            Arguments.of(
                RegularPolygon(
                    Vector2F(10f, -6.5f),
                    ComplexF.fromAngle(AngleF.fromDegrees(135f)),
                    sideLength = 4f,
                    sideCount = 3
                ),
                2.309401f
            ),
            Arguments.of(
                RegularPolygon(
                    Vector2F(10f, 6.5f),
                    ComplexF.fromAngle(AngleF.fromDegrees(195f)),
                    sideLength = 4f,
                    sideCount = 2
                ),
                2f
            ),
        )

        @JvmStatic
        fun closestPointToArgs(): List<Arguments> {
            val decagon = RegularPolygon(
                Vector2F(14f, 1f),
                ComplexF.fromAngle(AngleF.fromDegrees(-72f)),
                sideLength = 2f,
                sideCount = 10
            )
            val heptagon = RegularPolygon(
                Vector2F(0f, 8f),
                ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                sideLength = 3f,
                sideCount = 7
            )
            val digon = RegularPolygon(
                Vector2F(10f, 6.5f),
                ComplexF.fromAngle(AngleF.fromDegrees(195f)),
                sideLength = 4f,
                sideCount = 2
            )
            val decagonArgs = listOf(
                Arguments.of(
                    decagon,
                    Wrapper(Vector2F(16.912462f, 1f)),
                    Wrapper(Vector2F(16.912462f, 1f))
                ),
                Arguments.of(
                    decagon,
                    Wrapper(Vector2F(17.559675f, 1f)),
                    Wrapper(Vector2F(17.236069f, 1f))
                ),
                Arguments.of(
                    decagon,
                    Wrapper(Vector2F(16.634346f, 1.8559508f)),
                    Wrapper(Vector2F(16.634346f, 1.8559508f))
                ),
                Arguments.of(
                    decagon,
                    Wrapper(Vector2F(17.219757f, 2.0461621f)),
                    Wrapper(Vector2F(16.927052f, 1.9510565f))
                ),
                Arguments.of(
                    decagon,
                    Wrapper(Vector2F(16.356232f, 2.7119017f)),
                    Wrapper(Vector2F(16.356232f, 2.7119017f))
                ),
                Arguments.of(
                    decagon,
                    Wrapper(Vector2F(16.879837f, 3.0923243f)),
                    Wrapper(Vector2F(16.618034f, 2.902113f))
                ),
                Arguments.of(
                    decagon,
                    Wrapper(Vector2F(15.628116f, 3.2409084f)),
                    Wrapper(Vector2F(15.628116f, 3.2409084f))
                ),
                Arguments.of(
                    decagon,
                    Wrapper(Vector2F(15.989919f, 3.738888f)),
                    Wrapper(Vector2F(15.809017f, 3.4898982f))
                ),
                Arguments.of(
                    decagon,
                    Wrapper(Vector2F(14.9f, 3.769915f)),
                    Wrapper(Vector2F(14.9f, 3.769915f))
                ),
                Arguments.of(
                    decagon,
                    Wrapper(Vector2F(15.1f, 4.385452f)),
                    Wrapper(Vector2F(15.0f, 4.0776834f))
                ),
                Arguments.of(
                    decagon,
                    Wrapper(Vector2F(14.0f, 3.769915f)),
                    Wrapper(Vector2F(14.0f, 3.769915f))
                ),
                Arguments.of(
                    decagon,
                    Wrapper(Vector2F(14.0f, 4.385452f)),
                    Wrapper(Vector2F(14.0f, 4.0776834f))
                ),
                Arguments.of(
                    decagon,
                    Wrapper(Vector2F(13.1f, 3.769915f)),
                    Wrapper(Vector2F(13.1f, 3.769915f))
                ),
                Arguments.of(
                    decagon,
                    Wrapper(Vector2F(12.9f, 4.385452f)),
                    Wrapper(Vector2F(13.0f, 4.0776834f))
                ),
                Arguments.of(
                    decagon,
                    Wrapper(Vector2F(12.371884f, 3.2409084f)),
                    Wrapper(Vector2F(12.371884f, 3.2409084f))
                ),
                Arguments.of(
                    decagon,
                    Wrapper(Vector2F(12.010081f, 3.738888f)),
                    Wrapper(Vector2F(12.190983f, 3.4898982f))
                ),
                Arguments.of(
                    decagon,
                    Wrapper(Vector2F(11.64377f, 2.7119017f)),
                    Wrapper(Vector2F(11.64377f, 2.7119017f))
                ),
                Arguments.of(
                    decagon,
                    Wrapper(Vector2F(11.120163f, 3.0923243f)),
                    Wrapper(Vector2F(11.381967f, 2.902113f))
                ),
                Arguments.of(
                    decagon,
                    Wrapper(Vector2F(11.365654f, 1.8559508f)),
                    Wrapper(Vector2F(11.365654f, 1.8559508f))
                ),
                Arguments.of(
                    decagon,
                    Wrapper(Vector2F(10.780245f, 2.0461621f)),
                    Wrapper(Vector2F(11.072949f, 1.9510565f))
                ),
                Arguments.of(
                    decagon,
                    Wrapper(Vector2F(11.08754f, 1f)),
                    Wrapper(Vector2F(11.08754f, 1f))
                ),
                Arguments.of(
                    decagon,
                    Wrapper(Vector2F(10.440325f, 1f)),
                    Wrapper(Vector2F(10.763932f, 1f))
                ),
                Arguments.of(
                    decagon,
                    Wrapper(Vector2F(11.365654f, 0.14404923f)),
                    Wrapper(Vector2F(11.365654f, 0.14404923f))
                ),
                Arguments.of(
                    decagon,
                    Wrapper(Vector2F(10.780243f, -0.04616213f)),
                    Wrapper(Vector2F(11.072948f, 0.04894358f))
                ),
                Arguments.of(
                    decagon,
                    Wrapper(Vector2F(11.643769f, -0.71190166f)),
                    Wrapper(Vector2F(11.643769f, -0.71190166f))
                ),
                Arguments.of(
                    decagon,
                    Wrapper(Vector2F(11.120162f, -1.0923243f)),
                    Wrapper(Vector2F(11.381966f, -0.90211296f))
                ),
                Arguments.of(
                    decagon,
                    Wrapper(Vector2F(12.371884f, -1.2409084f)),
                    Wrapper(Vector2F(12.371884f, -1.2409084f))
                ),
                Arguments.of(
                    decagon,
                    Wrapper(Vector2F(12.010081f, -1.738888f)),
                    Wrapper(Vector2F(12.190983f, -1.4898982f))
                ),
                Arguments.of(
                    decagon,
                    Wrapper(Vector2F(13.1f, -1.7699151f)),
                    Wrapper(Vector2F(13.1f, -1.7699151f))
                ),
                Arguments.of(
                    decagon,
                    Wrapper(Vector2F(12.9f, -2.3854518f)),
                    Wrapper(Vector2F(13.0f, -2.0776834f))
                ),
                Arguments.of(
                    decagon,
                    Wrapper(Vector2F(14.0f, -1.7699151f)),
                    Wrapper(Vector2F(14.0f, -1.7699151f))
                ),
                Arguments.of(
                    decagon,
                    Wrapper(Vector2F(14.0f, -2.3854518f)),
                    Wrapper(Vector2F(14.0f, -2.0776834f))
                ),
                Arguments.of(
                    decagon,
                    Wrapper(Vector2F(14.9f, -1.7699151f)),
                    Wrapper(Vector2F(14.9f, -1.7699151f))
                ),
                Arguments.of(
                    decagon,
                    Wrapper(Vector2F(15.1f, -2.3854518f)),
                    Wrapper(Vector2F(15.0f, -2.0776834f))
                ),
                Arguments.of(
                    decagon,
                    Wrapper(Vector2F(15.628116f, -1.2409084f)),
                    Wrapper(Vector2F(15.628116f, -1.2409084f))
                ),
                Arguments.of(
                    decagon,
                    Wrapper(Vector2F(15.989919f, -1.738888f)),
                    Wrapper(Vector2F(15.809017f, -1.4898982f))
                ),
                Arguments.of(
                    decagon,
                    Wrapper(Vector2F(16.356232f, -0.71190166f)),
                    Wrapper(Vector2F(16.356232f, -0.71190166f))
                ),
                Arguments.of(
                    decagon,
                    Wrapper(Vector2F(16.879837f, -1.0923243f)),
                    Wrapper(Vector2F(16.618034f, -0.90211296f))
                ),
                Arguments.of(
                    decagon,
                    Wrapper(Vector2F(16.634346f, 0.14404911f)),
                    Wrapper(Vector2F(16.634346f, 0.14404911f))
                ),
                Arguments.of(
                    decagon,
                    Wrapper(Vector2F(17.219757f, -0.046162248f)),
                    Wrapper(Vector2F(16.927052f, 0.04894346f))
                ),
                Arguments.of(
                    decagon,
                    Wrapper(Vector2F(14f, 1f)),
                    Wrapper(Vector2F(14f, 1f)),
                ),
            )
            val heptagonArgs = listOf(
                Arguments.of(
                    heptagon,
                    Wrapper(Vector2F(-2.6945794f, 6.4442835f)),
                    Wrapper(Vector2F(-2.6945794f, 6.4442835f))
                ),
                Arguments.of(
                    heptagon,
                    Wrapper(Vector2F(-3.2933748f, 6.098569f)),
                    Wrapper(Vector2F(-2.993977f, 6.271426f))
                ),
                Arguments.of(
                    heptagon,
                    Wrapper(Vector2F(-1.579157f, 5.6838017f)),
                    Wrapper(Vector2F(-1.579157f, 5.6838017f))
                ),
                Arguments.of(
                    heptagon,
                    Wrapper(Vector2F(-1.9300808f, 5.1690903f)),
                    Wrapper(Vector2F(-1.7546189f, 5.426446f))
                ),
                Arguments.of(
                    heptagon,
                    Wrapper(Vector2F(-0.46373463f, 4.92332f)),
                    Wrapper(Vector2F(-0.46373463f, 4.92332f))
                ),
                Arguments.of(
                    heptagon,
                    Wrapper(Vector2F(-0.56678677f, 4.2396126f)),
                    Wrapper(Vector2F(-0.5152607f, 4.581466f))
                ),
                Arguments.of(
                    heptagon,
                    Wrapper(Vector2F(0.8262886f, 5.3212395f)),
                    Wrapper(Vector2F(0.8262886f, 5.3212395f))
                ),
                Arguments.of(
                    heptagon,
                    Wrapper(Vector2F(1.0099083f, 4.72596f)),
                    Wrapper(Vector2F(0.91809845f, 5.0235996f))
                ),
                Arguments.of(
                    heptagon,
                    Wrapper(Vector2F(2.1163118f, 5.719159f)),
                    Wrapper(Vector2F(2.1163118f, 5.719159f))
                ),
                Arguments.of(
                    heptagon,
                    Wrapper(Vector2F(2.5866034f, 5.212306f)),
                    Wrapper(Vector2F(2.3514576f, 5.4657326f))
                ),
                Arguments.of(
                    heptagon,
                    Wrapper(Vector2F(2.6095219f, 6.9758387f)),
                    Wrapper(Vector2F(2.6095219f, 6.9758387f))
                ),
                Arguments.of(
                    heptagon,
                    Wrapper(Vector2F(3.189416f, 6.748247f)),
                    Wrapper(Vector2F(2.899469f, 6.862043f))
                ),
                Arguments.of(
                    heptagon,
                    Wrapper(Vector2F(3.1027322f, 8.232518f)),
                    Wrapper(Vector2F(3.1027322f, 8.232518f))
                ),
                Arguments.of(
                    heptagon,
                    Wrapper(Vector2F(3.7922282f, 8.284188f)),
                    Wrapper(Vector2F(3.4474802f, 8.258353f))
                ),
                Arguments.of(
                    heptagon,
                    Wrapper(Vector2F(2.4277322f, 9.401652f)),
                    Wrapper(Vector2F(2.4277322f, 9.401652f))
                ),
                Arguments.of(
                    heptagon,
                    Wrapper(Vector2F(2.9672287f, 9.71313f)),
                    Wrapper(Vector2F(2.6974804f, 9.557391f))
                ),
                Arguments.of(
                    heptagon,
                    Wrapper(Vector2F(1.7527325f, 10.570786f)),
                    Wrapper(Vector2F(1.7527325f, 10.570786f))
                ),
                Arguments.of(
                    heptagon,
                    Wrapper(Vector2F(2.1422288f, 11.142072f)),
                    Wrapper(Vector2F(1.9474807f, 10.856429f))
                ),
                Arguments.of(
                    heptagon,
                    Wrapper(Vector2F(0.41781092f, 10.771993f)),
                    Wrapper(Vector2F(0.41781092f, 10.771993f))
                ),
                Arguments.of(
                    heptagon,
                    Wrapper(Vector2F(0.5106578f, 11.387992f)),
                    Wrapper(Vector2F(0.46423435f, 11.079992f))
                ),
                Arguments.of(
                    heptagon,
                    Wrapper(Vector2F(-0.91711074f, 10.9732f)),
                    Wrapper(Vector2F(-0.91711074f, 10.9732f))
                ),
                Arguments.of(
                    heptagon,
                    Wrapper(Vector2F(-1.1209131f, 11.633911f)),
                    Wrapper(Vector2F(-1.019012f, 11.3035555f))
                ),
                Arguments.of(
                    heptagon,
                    Wrapper(Vector2F(-1.9067309f, 10.054967f)),
                    Wrapper(Vector2F(-1.9067309f, 10.054967f))
                ),
                Arguments.of(
                    heptagon,
                    Wrapper(Vector2F(-2.3304489f, 10.511626f)),
                    Wrapper(Vector2F(-2.1185899f, 10.283297f))
                ),
                Arguments.of(
                    heptagon,
                    Wrapper(Vector2F(-2.8963506f, 9.136734f)),
                    Wrapper(Vector2F(-2.8963506f, 9.136734f))
                ),
                Arguments.of(
                    heptagon,
                    Wrapper(Vector2F(-3.5399845f, 9.389341f)),
                    Wrapper(Vector2F(-3.2181675f, 9.263038f))
                ),
                Arguments.of(
                    heptagon,
                    Wrapper(Vector2F(-2.795465f, 7.7905087f)),
                    Wrapper(Vector2F(-2.795465f, 7.7905087f))
                ),
                Arguments.of(
                    heptagon,
                    Wrapper(Vector2F(-3.4166799f, 7.743955f)),
                    Wrapper(Vector2F(-3.1060724f, 7.767232f))
                ),
                Arguments.of(
                    heptagon,
                    Wrapper(Vector2F(0f, 8f)),
                    Wrapper(Vector2F(0f, 8f))
                ),
            )
            val digonArgs = listOf(
                Arguments.of(
                    digon,
                    Wrapper(Vector2F(8.261333f, 6.034126f)),
                    Wrapper(Vector2F(8.261333f, 6.034126f))
                ),
                Arguments.of(
                    digon,
                    Wrapper(Vector2F(7.8749638f, 5.930598f)),
                    Wrapper(Vector2F(8.068149f, 5.982362f))
                ),
                Arguments.of(
                    digon,
                    Wrapper(Vector2F(11.738667f, 6.965874f)),
                    Wrapper(Vector2F(11.738667f, 6.965874f))
                ),
                Arguments.of(
                    digon,
                    Wrapper(Vector2F(12.125036f, 7.069402f)),
                    Wrapper(Vector2F(11.931851f, 7.017638f))
                ),
                Arguments.of(
                    digon,
                    Wrapper(Vector2F(9.059956f, 6.1445885f)),
                    Wrapper(Vector2F(9.034074f, 6.241181f))
                ),
                Arguments.of(
                    digon,
                    Wrapper(Vector2F(9.008192f, 6.3377733f)),
                    Wrapper(Vector2F(9.034074f, 6.241181f))
                ),
                Arguments.of(
                    digon,
                    Wrapper(Vector2F(10.940044f, 6.8554115f)),
                    Wrapper(Vector2F(10.965926f, 6.758819f))
                ),
                Arguments.of(
                    digon,
                    Wrapper(Vector2F(10.991808f, 6.6622267f)),
                    Wrapper(Vector2F(10.965926f, 6.758819f))
                ),
                Arguments.of(
                    digon,
                    Wrapper(Vector2F(10f, 6.5f)),
                    Wrapper(Vector2F(10f, 6.5f))
                ),
            )
            return decagonArgs + heptagonArgs + digonArgs
        }

        @JvmStatic
        fun containsVector2FArgs(): List<Arguments> {
            val decagon = RegularPolygon(
                Vector2F(14f, 1f),
                ComplexF.fromAngle(AngleF.fromDegrees(-72f)),
                sideLength = 2f,
                sideCount = 10
            )
            val heptagon = RegularPolygon(
                Vector2F(0f, 8f),
                ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                sideLength = 3f,
                sideCount = 7
            )
            val digon = RegularPolygon(
                Vector2F(10f, 6.5f),
                ComplexF.fromAngle(AngleF.fromDegrees(195f)),
                sideLength = 4f,
                sideCount = 2
            )
            val decagonArgs = listOf(
                Arguments.of(
                    decagon, Wrapper(Vector2F(16.912462f, 1f)), true
                ),
                Arguments.of(
                    decagon, Wrapper(Vector2F(17.559675f, 1f)), false
                ),
                Arguments.of(
                    decagon, Wrapper(Vector2F(16.634346f, 1.8559508f)), true
                ),
                Arguments.of(
                    decagon, Wrapper(Vector2F(17.219757f, 2.0461621f)), false
                ),
                Arguments.of(
                    decagon, Wrapper(Vector2F(16.356232f, 2.7119017f)), true
                ),
                Arguments.of(
                    decagon, Wrapper(Vector2F(16.879837f, 3.0923243f)), false
                ),
                Arguments.of(
                    decagon, Wrapper(Vector2F(15.628116f, 3.2409084f)), true
                ),
                Arguments.of(
                    decagon, Wrapper(Vector2F(15.989919f, 3.738888f)), false
                ),
                Arguments.of(
                    decagon, Wrapper(Vector2F(14.9f, 3.769915f)), true
                ),
                Arguments.of(
                    decagon, Wrapper(Vector2F(15.1f, 4.385452f)), false
                ),
                Arguments.of(
                    decagon, Wrapper(Vector2F(14.0f, 3.769915f)), true
                ),
                Arguments.of(
                    decagon, Wrapper(Vector2F(14.0f, 4.385452f)), false
                ),
                Arguments.of(
                    decagon, Wrapper(Vector2F(13.1f, 3.769915f)), true
                ),
                Arguments.of(
                    decagon, Wrapper(Vector2F(12.9f, 4.385452f)), false
                ),
                Arguments.of(
                    decagon, Wrapper(Vector2F(12.371884f, 3.2409084f)), true
                ),
                Arguments.of(
                    decagon, Wrapper(Vector2F(12.010081f, 3.738888f)), false
                ),
                Arguments.of(
                    decagon, Wrapper(Vector2F(11.64377f, 2.7119017f)), true
                ),
                Arguments.of(
                    decagon, Wrapper(Vector2F(11.120163f, 3.0923243f)), false
                ),
                Arguments.of(
                    decagon, Wrapper(Vector2F(11.365654f, 1.8559508f)), true
                ),
                Arguments.of(
                    decagon, Wrapper(Vector2F(10.780245f, 2.0461621f)), false
                ),
                Arguments.of(
                    decagon, Wrapper(Vector2F(11.08754f, 1f)), true
                ),
                Arguments.of(
                    decagon, Wrapper(Vector2F(10.440325f, 1f)), false
                ),
                Arguments.of(
                    decagon, Wrapper(Vector2F(11.365654f, 0.14404923f)), true
                ),
                Arguments.of(
                    decagon, Wrapper(Vector2F(10.780243f, -0.04616213f)), false
                ),
                Arguments.of(
                    decagon, Wrapper(Vector2F(11.643769f, -0.71190166f)), true
                ),
                Arguments.of(
                    decagon, Wrapper(Vector2F(11.120162f, -1.0923243f)), false
                ),
                Arguments.of(
                    decagon, Wrapper(Vector2F(12.371884f, -1.2409084f)), true
                ),
                Arguments.of(
                    decagon, Wrapper(Vector2F(12.010081f, -1.738888f)), false
                ),
                Arguments.of(
                    decagon, Wrapper(Vector2F(13.1f, -1.7699151f)), true
                ),
                Arguments.of(
                    decagon, Wrapper(Vector2F(12.9f, -2.3854518f)), false
                ),
                Arguments.of(
                    decagon, Wrapper(Vector2F(14.0f, -1.7699151f)), true
                ),
                Arguments.of(
                    decagon, Wrapper(Vector2F(14.0f, -2.3854518f)), false
                ),
                Arguments.of(
                    decagon, Wrapper(Vector2F(14.9f, -1.7699151f)), true
                ),
                Arguments.of(
                    decagon, Wrapper(Vector2F(15.1f, -2.3854518f)), false
                ),
                Arguments.of(
                    decagon, Wrapper(Vector2F(15.628116f, -1.2409084f)), true
                ),
                Arguments.of(
                    decagon, Wrapper(Vector2F(15.989919f, -1.738888f)), false
                ),
                Arguments.of(
                    decagon, Wrapper(Vector2F(16.356232f, -0.71190166f)), true
                ),
                Arguments.of(
                    decagon, Wrapper(Vector2F(16.879837f, -1.0923243f)), false
                ),
                Arguments.of(
                    decagon, Wrapper(Vector2F(16.634346f, 0.14404911f)), true
                ),
                Arguments.of(
                    decagon, Wrapper(Vector2F(17.219757f, -0.046162248f)), false
                ),
                Arguments.of(
                    decagon, Wrapper(Vector2F(14f, 1f)), true
                ),
            )
            val heptagonArgs = listOf(
                Arguments.of(
                    heptagon, Wrapper(Vector2F(-2.6945794f, 6.4442835f)), true
                ),
                Arguments.of(
                    heptagon, Wrapper(Vector2F(-3.2933748f, 6.098569f)), false
                ),
                Arguments.of(
                    heptagon, Wrapper(Vector2F(-1.579157f, 5.6838017f)), true
                ),
                Arguments.of(
                    heptagon, Wrapper(Vector2F(-1.9300808f, 5.1690903f)), false
                ),
                Arguments.of(
                    heptagon, Wrapper(Vector2F(-0.46373463f, 4.92332f)), true
                ),
                Arguments.of(
                    heptagon, Wrapper(Vector2F(-0.56678677f, 4.2396126f)), false
                ),
                Arguments.of(
                    heptagon, Wrapper(Vector2F(0.8262886f, 5.3212395f)), true
                ),
                Arguments.of(
                    heptagon, Wrapper(Vector2F(1.0099083f, 4.72596f)), false
                ),
                Arguments.of(
                    heptagon, Wrapper(Vector2F(2.1163118f, 5.719159f)), true
                ),
                Arguments.of(
                    heptagon, Wrapper(Vector2F(2.5866034f, 5.212306f)), false
                ),
                Arguments.of(
                    heptagon, Wrapper(Vector2F(2.6095219f, 6.9758387f)), true
                ),
                Arguments.of(
                    heptagon, Wrapper(Vector2F(3.189416f, 6.748247f)), false
                ),
                Arguments.of(
                    heptagon, Wrapper(Vector2F(3.1027322f, 8.232518f)), true
                ),
                Arguments.of(
                    heptagon, Wrapper(Vector2F(3.7922282f, 8.284188f)), false
                ),
                Arguments.of(
                    heptagon, Wrapper(Vector2F(2.4277322f, 9.401652f)), true
                ),
                Arguments.of(
                    heptagon, Wrapper(Vector2F(2.9672287f, 9.71313f)), false
                ),
                Arguments.of(
                    heptagon, Wrapper(Vector2F(1.7527325f, 10.570786f)), true
                ),
                Arguments.of(
                    heptagon, Wrapper(Vector2F(2.1422288f, 11.142072f)), false
                ),
                Arguments.of(
                    heptagon, Wrapper(Vector2F(0.41781092f, 10.771993f)), true
                ),
                Arguments.of(
                    heptagon, Wrapper(Vector2F(0.5106578f, 11.387992f)), false
                ),
                Arguments.of(
                    heptagon, Wrapper(Vector2F(-0.91711074f, 10.9732f)), true
                ),
                Arguments.of(
                    heptagon, Wrapper(Vector2F(-1.1209131f, 11.633911f)), false
                ),
                Arguments.of(
                    heptagon, Wrapper(Vector2F(-1.9067309f, 10.054967f)), true
                ),
                Arguments.of(
                    heptagon, Wrapper(Vector2F(-2.3304489f, 10.511626f)), false
                ),
                Arguments.of(
                    heptagon, Wrapper(Vector2F(-2.8963506f, 9.136734f)), true
                ),
                Arguments.of(
                    heptagon, Wrapper(Vector2F(-3.5399845f, 9.389341f)), false
                ),
                Arguments.of(
                    heptagon, Wrapper(Vector2F(-2.795465f, 7.7905087f)), true
                ),
                Arguments.of(
                    heptagon, Wrapper(Vector2F(-3.4166799f, 7.743955f)), false
                ),
                Arguments.of(
                    heptagon, Wrapper(Vector2F(0f, 8f)), true
                ),
            )
            val digonArgs = listOf(
                Arguments.of(
                    digon, Wrapper(Vector2F(8.261333f, 6.034126f)), true
                ),
                Arguments.of(
                    digon, Wrapper(Vector2F(7.8749638f, 5.930598f)), false
                ),
                Arguments.of(
                    digon, Wrapper(Vector2F(11.738667f, 6.965874f)), true
                ),
                Arguments.of(
                    digon, Wrapper(Vector2F(12.125036f, 7.069402f)), false
                ),
                Arguments.of(
                    digon, Wrapper(Vector2F(9.059956f, 6.1445885f)), false
                ),
                Arguments.of(
                    digon, Wrapper(Vector2F(9.008192f, 6.3377733f)), false
                ),
                Arguments.of(
                    digon, Wrapper(Vector2F(9.034074f, 6.241181f)), true
                ),
                Arguments.of(
                    digon, Wrapper(Vector2F(10.940044f, 6.8554115f)), false
                ),
                Arguments.of(
                    digon, Wrapper(Vector2F(10.991808f, 6.6622267f)), false
                ),
                Arguments.of(
                    digon, Wrapper(Vector2F(10.965926f, 6.758819f)), true
                ),
                Arguments.of(
                    digon, Wrapper(Vector2F(10f, 6.5f)), true
                ),
            )
            return decagonArgs + heptagonArgs + digonArgs
        }

        @JvmStatic
        fun copyArgs(): List<Arguments> = listOf(
            Arguments.of(
                RegularPolygon(
                    Vector2F(-7.5f, -8f),
                    ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    sideLength = 4f,
                    sideCount = 5
                ),
                Wrapper(Vector2F(-7.5f, -8f)),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(45f))),
                4f,
                5,
                RegularPolygon(
                    Vector2F(-7.5f, -8f),
                    ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    sideLength = 4f,
                    sideCount = 5
                ),
            ),
            Arguments.of(
                RegularPolygon(
                    Vector2F(-7.5f, -8f),
                    ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    sideLength = 4f,
                    sideCount = 5
                ),
                Wrapper(Vector2F(2f, -4f)),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(45f))),
                4f,
                3,
                RegularPolygon(
                    Vector2F(2f, -4f),
                    ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    sideLength = 4f,
                    sideCount = 3
                ),
            ),
            Arguments.of(
                RegularPolygon(
                    Vector2F(-7.5f, -8f),
                    ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    sideLength = 4f,
                    sideCount = 5
                ),
                Wrapper(Vector2F(2f, -4f)),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(0f))),
                2f,
                3,
                RegularPolygon(
                    Vector2F(2f, -4f),
                    ComplexF.fromAngle(AngleF.fromDegrees(0f)),
                    sideLength = 2f,
                    sideCount = 3
                ),
            ),
        )

        @JvmStatic
        fun equalsArgs(): List<Arguments> = equalsRegularPolygonArgs() + listOf(
            Arguments.of(
                RegularPolygon(
                    Vector2F(0f, 8f),
                    ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                    sideLength = 3f,
                    sideCount = 7
                ),
                null,
                false
            ),
        )

        @JvmStatic
        fun equalsRegularPolygonArgs(): List<Arguments> = listOf(
            Arguments.of(
                RegularPolygon(
                    Vector2F(0f, 8f),
                    ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                    sideLength = 3f,
                    sideCount = 7
                ),
                RegularPolygon(
                    Vector2F(0f, 8f),
                    ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                    sideLength = 3f,
                    sideCount = 7
                ),
                true
            ),
            Arguments.of(
                RegularPolygon(
                    Vector2F(0f, 8f),
                    ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                    sideLength = 3f,
                    sideCount = 7
                ),
                RegularPolygon(
                    Vector2F(0f, 8f),
                    ComplexF.fromAngle(AngleF.fromDegrees(120.1f)),
                    sideLength = 3f,
                    sideCount = 7
                ),
                false
            ),
            Arguments.of(
                RegularPolygon(
                    Vector2F(2f, -7.5f),
                    ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                    sideLength = 4f,
                    sideCount = 4
                ),
                RegularPolygon(
                    Vector2F(2f, -7.5f),
                    ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                    sideLength = 4f,
                    sideCount = 4
                ),
                true
            ),
            Arguments.of(
                RegularPolygon(
                    Vector2F(2f, -7.5f),
                    ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                    sideLength = 4f,
                    sideCount = 4
                ),
                RegularPolygon(
                    Vector2F(2f, -7.5f),
                    ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                    sideLength = 4f,
                    sideCount = 3
                ),
                false
            ),
        )

        @JvmStatic
        fun hashCodeArgs(): List<Arguments> = listOf(
            Arguments.of(
                RegularPolygon(
                    Vector2F(0f, 8f),
                    ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                    sideLength = 3f,
                    sideCount = 7
                ),
                RegularPolygon(
                    Vector2F(0f, 8f),
                    ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                    sideLength = 3f,
                    sideCount = 7
                ),
            ),
            Arguments.of(
                RegularPolygon(
                    Vector2F(2f, -7.5f),
                    ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                    sideLength = 4f,
                    sideCount = 4
                ),
                RegularPolygon(
                    Vector2F(2f, -7.5f),
                    ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                    sideLength = 4f,
                    sideCount = 4
                ),
            ),
        )

        @JvmStatic
        fun toStringArgs(): List<Arguments> = listOf(
            Arguments.of(
                RegularPolygon(
                    Vector2F(0f, 8f),
                    ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                    sideLength = 3f,
                    sideCount = 7
                ),
                "RegularPolygon(" +
                        "center=${Vector2F(0f, 8f)}, " +
                        "rotation=${ComplexF.fromAngle(AngleF.fromDegrees(120f))}, " +
                        "sideLength=${3f}, " +
                        "sideCount=${7})"
            ),
            Arguments.of(
                RegularPolygon(
                    Vector2F(2f, -7.5f),
                    ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                    sideLength = 4f,
                    sideCount = 4
                ),
                "RegularPolygon(" +
                        "center=${Vector2F(2f, -7.5f)}, " +
                        "rotation=${ComplexF.fromAngle(AngleF.fromDegrees(-45f))}, " +
                        "sideLength=${4f}, " +
                        "sideCount=${4})"
            ),
        )

        @JvmStatic
        fun componentsArgs(): List<Arguments> = listOf(
            Arguments.of(
                RegularPolygon(
                    Vector2F(0f, 8f),
                    ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                    sideLength = 3f,
                    sideCount = 7
                ),
                Wrapper(Vector2F(0f, 8f)),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(120f))),
                3f,
                7
            ),
            Arguments.of(
                RegularPolygon(
                    Vector2F(2f, -7.5f),
                    ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                    sideLength = 4f,
                    sideCount = 4
                ),
                Wrapper(Vector2F(2f, -7.5f)),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(-45f))),
                4f,
                4
            ),
        )
    }
}