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
        fun equalsArgs(): List<Arguments> = listOf(
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