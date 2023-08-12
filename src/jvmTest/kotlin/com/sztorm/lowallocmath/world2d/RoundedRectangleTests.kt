package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.AngleF
import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F
import com.sztorm.lowallocmath.utils.Wrapper
import com.sztorm.lowallocmath.utils.assertApproximation
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class RoundedRectangleTests {
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
        expectedCornerCenterA: Wrapper<Vector2F>,
        expectedCornerCenterB: Wrapper<Vector2F>,
        expectedCornerCenterC: Wrapper<Vector2F>,
        expectedCornerCenterD: Wrapper<Vector2F>
    ) {
        assertApproximation(expectedPointA.value, rectangle.pointA)
        assertApproximation(expectedPointB.value, rectangle.pointB)
        assertApproximation(expectedPointC.value, rectangle.pointC)
        assertApproximation(expectedPointD.value, rectangle.pointD)
        assertApproximation(expectedPointE.value, rectangle.pointE)
        assertApproximation(expectedPointF.value, rectangle.pointF)
        assertApproximation(expectedPointG.value, rectangle.pointG)
        assertApproximation(expectedPointH.value, rectangle.pointH)
        assertApproximation(expectedCornerCenterA.value, rectangle.cornerCenterA)
        assertApproximation(expectedCornerCenterB.value, rectangle.cornerCenterB)
        assertApproximation(expectedCornerCenterC.value, rectangle.cornerCenterC)
        assertApproximation(expectedCornerCenterD.value, rectangle.cornerCenterD)
    }

    @ParameterizedTest
    @MethodSource("areaArgs")
    fun areaReturnsCorrectValue(rectangle: RoundedRectangle, expected: Float) =
        assertApproximation(expected, rectangle.area)

    @ParameterizedTest
    @MethodSource("perimeterArgs")
    fun perimeterReturnsCorrectValue(rectangle: RoundedRectangle, expected: Float) =
        assertApproximation(expected, rectangle.perimeter)

    companion object {
        @JvmStatic
        fun pointsArgs(): List<Arguments> = listOf(
            Arguments.of(
                RoundedRectangle(
                    center = Vector2F.ZERO,
                    rotation = ComplexF.ONE,
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
                Wrapper(Vector2F(4f, 1f)),
                Wrapper(Vector2F(3f, 1f)),
                Wrapper(Vector2F(-3f, 1f)),
                Wrapper(Vector2F(-3f, -1f)),
                Wrapper(Vector2F(3f, -1f))
            ),
            Arguments.of(
                RoundedRectangle(
                    center = Vector2F(-3f, -4f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
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
                Wrapper(Vector2F(-0.13397455f, -6.964102f)),
                Wrapper(Vector2F(-0.63397455f, -6.0980763f)),
                Wrapper(Vector2F(-3.6339746f, -0.90192366f)),
                Wrapper(Vector2F(-5.3660254f, -1.9019237f)),
                Wrapper(Vector2F(-2.3660254f, -7.0980763f))
            ),
            Arguments.of(
                RoundedRectangle(
                    center = Vector2F(6f, -4f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
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
                Wrapper(Vector2F(6.7071066f, -2.5857863f)),
                Wrapper(Vector2F(5.6464467f, -3.6464467f)),
                Wrapper(Vector2F(5.6464467f, -3.6464467f)),
                Wrapper(Vector2F(6.3535533f, -4.3535533f)),
                Wrapper(Vector2F(6.3535533f, -4.3535533f))
            ),
        )

        @JvmStatic
        fun areaArgs(): List<Arguments> = listOf(
            Arguments.of(
                RoundedRectangle(
                    center = Vector2F.ZERO,
                    rotation = ComplexF.ONE,
                    width = 8f,
                    height = 4f,
                    cornerRadius = 1f
                ),
                31.14159f
            ),
            Arguments.of(
                RoundedRectangle(
                    center = Vector2F(-3f, -4f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                    width = 8f,
                    height = 4f,
                    cornerRadius = 1f
                ),
                31.14159f
            ),
            Arguments.of(
                RoundedRectangle(
                    center = Vector2F(6f, -4f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    width = 3f,
                    height = 4f,
                    cornerRadius = 1.5f
                ),
                10.0685835f
            ),
        )

        @JvmStatic
        fun perimeterArgs(): List<Arguments> = listOf(
            Arguments.of(
                RoundedRectangle(
                    center = Vector2F.ZERO,
                    rotation = ComplexF.ONE,
                    width = 8f,
                    height = 4f,
                    cornerRadius = 1f
                ),
                22.28319f
            ),
            Arguments.of(
                RoundedRectangle(
                    center = Vector2F(-3f, -4f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                    width = 8f,
                    height = 4f,
                    cornerRadius = 1f
                ),
                22.28319f
            ),
            Arguments.of(
                RoundedRectangle(
                    center = Vector2F(6f, -4f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    width = 3f,
                    height = 4f,
                    cornerRadius = 1.5f
                ),
                11.424778f
            ),
        )
    }
}