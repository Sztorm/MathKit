package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.AngleF
import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F
import com.sztorm.lowallocmath.isApproximately
import com.sztorm.lowallocmath.utils.Wrapper
import com.sztorm.lowallocmath.utils.assertApproximation
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

class RoundedRectangleTests {
    @ParameterizedTest
    @MethodSource("pointsArgs")
    fun pointsReturnCorrectValues(
        rectangle: RoundedRectangleShape,
        expectedPointA: Wrapper<Vector2F>,
        expectedPointB: Wrapper<Vector2F>,
        expectedPointC: Wrapper<Vector2F>,
        expectedPointD: Wrapper<Vector2F>,
        expectedPointE: Wrapper<Vector2F>,
        expectedPointF: Wrapper<Vector2F>,
        expectedPointG: Wrapper<Vector2F>,
        expectedPointH: Wrapper<Vector2F>,
    ) {
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
        rectangle: RoundedRectangleShape,
        expectedCornerCenterA: Wrapper<Vector2F>,
        expectedCornerCenterB: Wrapper<Vector2F>,
        expectedCornerCenterC: Wrapper<Vector2F>,
        expectedCornerCenterD: Wrapper<Vector2F>
    ) {
        assertApproximation(expectedCornerCenterA.value, rectangle.cornerCenterA)
        assertApproximation(expectedCornerCenterB.value, rectangle.cornerCenterB)
        assertApproximation(expectedCornerCenterC.value, rectangle.cornerCenterC)
        assertApproximation(expectedCornerCenterD.value, rectangle.cornerCenterD)
    }

    @ParameterizedTest
    @MethodSource("areaArgs")
    fun areaReturnsCorrectValue(rectangle: RoundedRectangleShape, expected: Float) =
        assertApproximation(expected, rectangle.area)

    @ParameterizedTest
    @MethodSource("perimeterArgs")
    fun perimeterReturnsCorrectValue(rectangle: RoundedRectangleShape, expected: Float) =
        assertApproximation(expected, rectangle.perimeter)

    @ParameterizedTest
    @MethodSource("closestPointToArgs")
    fun closestPointToReturnsCorrectValue(
        rectangle: RoundedRectangle, point: Wrapper<Vector2F>, expected: Wrapper<Vector2F>
    ) = assertApproximation(expected.value, rectangle.closestPointTo(point.value))

    @ParameterizedTest
    @MethodSource("containsVector2FArgs")
    fun containsReturnsCorrectValue(
        rectangle: RoundedRectangle, point: Wrapper<Vector2F>, expected: Boolean
    ) = assertEquals(expected, rectangle.contains(point.value))

    @ParameterizedTest
    @MethodSource("copyArgs")
    fun copyReturnsCorrectValue(
        rectangle: RoundedRectangle,
        center: Wrapper<Vector2F>,
        rotation: Wrapper<ComplexF>,
        width: Float,
        height: Float,
        cornerRadius: Float,
        expected: RoundedRectangle
    ) = assertEquals(
        expected, rectangle.copy(center.value, rotation.value, width, height, cornerRadius)
    )

    @ParameterizedTest
    @MethodSource("equalsArgs")
    fun equalsReturnsCorrectValue(
        rectangle: MutableRoundedRectangle, other: Any?, expected: Boolean
    ) = assertEquals(expected, rectangle == other)

    @ParameterizedTest
    @MethodSource("equalsMutableRoundedRectangleArgs")
    fun equalsReturnsCorrectValue(
        rectangle: MutableRoundedRectangle, other: MutableRoundedRectangle, expected: Boolean
    ) = assertEquals(expected, rectangle.equals(other))

    @ParameterizedTest
    @MethodSource("hashCodeArgs")
    fun hashCodeReturnsCorrectValue(
        rectangle: MutableRoundedRectangle, other: MutableRoundedRectangle
    ) = assertEquals(rectangle.hashCode(), other.hashCode())

    @ParameterizedTest
    @MethodSource("toStringArgs")
    fun toStringReturnsCorrectValue(rectangle: MutableRoundedRectangle, expected: String) =
        assertEquals(expected, rectangle.toString())

    @ParameterizedTest
    @MethodSource("componentsArgs")
    fun componentsReturnCorrectValues(
        rectangle: RoundedRectangle,
        expectedComponent1: Wrapper<Vector2F>,
        expectedComponent2: Wrapper<ComplexF>,
        expectedComponent3: Float,
        expectedComponent4: Float,
        expectedComponent5: Float
    ) {
        val (
            actualComponent1,
            actualComponent2,
            actualComponent3,
            actualComponent4,
            actualComponent5
        ) = rectangle

        assertEquals(expectedComponent1.value, actualComponent1)
        assertEquals(expectedComponent2.value, actualComponent2)
        assertEquals(expectedComponent3, actualComponent3)
        assertEquals(expectedComponent4, actualComponent4)
        assertEquals(expectedComponent5, actualComponent5)
    }

    companion object {
        @JvmStatic
        fun areApproximatelyEqual(a: RoundedRectangle, b: RoundedRectangle): Boolean =
            a.center.isApproximately(b.center) and
                    a.rotation.isApproximately(b.rotation) and
                    a.width.isApproximately(b.width) and
                    a.height.isApproximately(b.height) and
                    a.cornerRadius.isApproximately(b.cornerRadius) and
                    a.pointA.isApproximately(b.pointA) and
                    a.pointB.isApproximately(b.pointB) and
                    a.pointC.isApproximately(b.pointC) and
                    a.pointD.isApproximately(b.pointD) and
                    a.pointE.isApproximately(b.pointE) and
                    a.pointF.isApproximately(b.pointF) and
                    a.pointG.isApproximately(b.pointG) and
                    a.pointH.isApproximately(b.pointH) and
                    a.cornerCenterA.isApproximately(b.cornerCenterA) and
                    a.cornerCenterB.isApproximately(b.cornerCenterB) and
                    a.cornerCenterC.isApproximately(b.cornerCenterC) and
                    a.cornerCenterD.isApproximately(b.cornerCenterD)

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
                Wrapper(Vector2F(4f, 1f))
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
                Wrapper(Vector2F(-0.13397455f, -6.964102f))
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
                Wrapper(Vector2F(6.7071066f, -2.5857863f))
            ),
        )

        @JvmStatic
        fun cornerCentersArgs(): List<Arguments> = listOf(
            Arguments.of(
                RoundedRectangle(
                    center = Vector2F.ZERO,
                    rotation = ComplexF.ONE,
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
                RoundedRectangle(
                    center = Vector2F(-3f, -4f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
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
                RoundedRectangle(
                    center = Vector2F(6f, -4f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
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

        @JvmStatic
        fun closestPointToArgs(): List<Arguments> {
            val rectangle = RoundedRectangle(
                center = Vector2F(-3f, -4f),
                rotation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                width = 8f,
                height = 4f,
                cornerRadius = 1f
            )
            return listOf(
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
        }

        @JvmStatic
        fun containsVector2FArgs(): List<Arguments> {
            val rectangle = RoundedRectangle(
                center = Vector2F(-3f, -4f),
                rotation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                width = 8f,
                height = 4f,
                cornerRadius = 1f
            )
            return listOf(
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
        }

        @JvmStatic
        fun copyArgs(): List<Arguments> = listOf(
            Arguments.of(
                RoundedRectangle(
                    center = Vector2F(-3f, -4f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                    width = 8f,
                    height = 4f,
                    cornerRadius = 1f
                ),
                Wrapper(Vector2F(-3f, -4f)),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(-60f))),
                8f,
                4f,
                1f,
                RoundedRectangle(
                    center = Vector2F(-3f, -4f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                    width = 8f,
                    height = 4f,
                    cornerRadius = 1f
                )
            ),
            Arguments.of(
                RoundedRectangle(
                    center = Vector2F(-3f, -4f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                    width = 8f,
                    height = 4f,
                    cornerRadius = 1f
                ),
                Wrapper(Vector2F(-3f, -4f)),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(45f))),
                8f,
                5f,
                1.5f,
                RoundedRectangle(
                    center = Vector2F(-3f, -4f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    width = 8f,
                    height = 5f,
                    cornerRadius = 1.5f
                )
            ),
            Arguments.of(
                RoundedRectangle(
                    center = Vector2F(-3f, -4f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                    width = 8f,
                    height = 4f,
                    cornerRadius = 1f
                ),
                Wrapper(Vector2F(6f, -4f)),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(45f))),
                3f,
                5f,
                1.5f,
                RoundedRectangle(
                    center = Vector2F(6f, -4f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    width = 3f,
                    height = 5f,
                    cornerRadius = 1.5f
                )
            )
        )

        @JvmStatic
        fun equalsArgs(): List<Arguments> = equalsMutableRoundedRectangleArgs() + listOf(
            Arguments.of(
                MutableRoundedRectangle(
                    center = Vector2F(-3f, -4f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                    width = 8f,
                    height = 4f,
                    cornerRadius = 1f
                ),
                null,
                false
            ),
        )

        @JvmStatic
        fun equalsMutableRoundedRectangleArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableRoundedRectangle(
                    center = Vector2F(-3f, -4f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                    width = 8f,
                    height = 4f,
                    cornerRadius = 1f
                ),
                MutableRoundedRectangle(
                    center = Vector2F(-3f, -4f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                    width = 8f,
                    height = 4f,
                    cornerRadius = 1f
                ),
                true
            ),
            Arguments.of(
                MutableRoundedRectangle(
                    center = Vector2F(-3f, -4f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                    width = 8f,
                    height = 4f,
                    cornerRadius = 1f
                ),
                MutableRoundedRectangle(
                    center = Vector2F(-3f, -4f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(-60.1f)),
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
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                    width = 8f,
                    height = 4f,
                    cornerRadius = 1f
                ),
                MutableRoundedRectangle(
                    center = Vector2F(-3f, -4f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                    width = 8f,
                    height = 4f,
                    cornerRadius = 1f
                )
            ),
            Arguments.of(
                MutableRoundedRectangle(
                    center = Vector2F(6f, -4f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    width = 3f,
                    height = 5f,
                    cornerRadius = 1.5f
                ),
                MutableRoundedRectangle(
                    center = Vector2F(6f, -4f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
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
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                    width = 8f,
                    height = 4f,
                    cornerRadius = 1f
                ),
                "RoundedRectangle(" +
                        "center=${Vector2F(-3f, -4f)}, " +
                        "rotation=${ComplexF.fromAngle(AngleF.fromDegrees(-60f))}, " +
                        "width=${8f}, " +
                        "height=${4f}, " +
                        "cornerRadius=${1f})"
            ),
            Arguments.of(
                MutableRoundedRectangle(
                    center = Vector2F(6f, -4f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    width = 3f,
                    height = 5f,
                    cornerRadius = 1.5f
                ),
                "RoundedRectangle(" +
                        "center=${Vector2F(6f, -4f)}, " +
                        "rotation=${ComplexF.fromAngle(AngleF.fromDegrees(45f))}, " +
                        "width=${3f}, " +
                        "height=${5f}, " +
                        "cornerRadius=${1.5f})"
            ),
        )

        @JvmStatic
        fun componentsArgs(): List<Arguments> = listOf(
            Arguments.of(
                RoundedRectangle(
                    center = Vector2F(-3f, -4f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
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
                RoundedRectangle(
                    center = Vector2F(6f, -4f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
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
    }
}