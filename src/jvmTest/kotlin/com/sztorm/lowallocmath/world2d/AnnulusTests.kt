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

class AnnulusTests {
    @ParameterizedTest
    @MethodSource("annularRadiusArgs")
    fun annularRadiusReturnsCorrectValue(annulus: AnnulusShape, expected: Float) =
        assertApproximation(expected, annulus.annularRadius)

    @ParameterizedTest
    @MethodSource("areaArgs")
    fun areaReturnsCorrectValue(annulus: AnnulusShape, expected: Float) =
        assertApproximation(expected, annulus.area)

    @ParameterizedTest
    @MethodSource("perimeterArgs")
    fun perimeterReturnsCorrectValue(annulus: AnnulusShape, expected: Float) =
        assertApproximation(expected, annulus.perimeter)

    @ParameterizedTest
    @MethodSource("closestPointToArgs")
    fun closestPointToReturnsCorrectValue(
        annulus: Annulus, point: Wrapper<Vector2F>, expected: Wrapper<Vector2F>
    ) = assertApproximation(expected.value, annulus.closestPointTo(point.value))

    @ParameterizedTest
    @MethodSource("intersectsCircleArgs")
    fun intersectsReturnsCorrectValue(
        annulus: Annulus, circle: CircleShape, expected: Boolean
    ) = assertEquals(expected, annulus.intersects(circle))

    @ParameterizedTest
    @MethodSource("intersectsAnnulusArgs")
    fun intersectsReturnsCorrectValue(
        annulus: Annulus, otherAnnulus: AnnulusShape, expected: Boolean
    ) = assertEquals(expected, annulus.intersects(otherAnnulus))

    @ParameterizedTest
    @MethodSource("containsVector2FArgs")
    fun containsReturnsCorrectValue(
        annulus: Annulus, point: Wrapper<Vector2F>, expected: Boolean
    ) = assertEquals(expected, annulus.contains(point.value))

    @ParameterizedTest
    @MethodSource("containsCircleArgs")
    fun containsReturnsCorrectValue(
        annulus: Annulus, circle: CircleShape, expected: Boolean
    ) = assertEquals(expected, annulus.contains(circle))

    @ParameterizedTest
    @MethodSource("containsAnnulusArgs")
    fun containsReturnsCorrectValue(
        annulus: Annulus, otherAnnulus: AnnulusShape, expected: Boolean
    ) = assertEquals(expected, annulus.contains(otherAnnulus))

    @ParameterizedTest
    @MethodSource("copyArgs")
    fun copyReturnsCorrectValue(
        annulus: Annulus,
        center: Wrapper<Vector2F>,
        orientation: Wrapper<ComplexF>,
        outerRadius: Float,
        innerRadius: Float,
        expected: Annulus
    ) = assertEquals(
        expected, annulus.copy(center.value, orientation.value, outerRadius, innerRadius)
    )

    @ParameterizedTest
    @MethodSource("equalsArgs")
    fun equalsReturnsCorrectValue(
        annulus: MutableAnnulus, other: Any?, expected: Boolean
    ) = assertEquals(expected, annulus == other)

    @ParameterizedTest
    @MethodSource("equalsMutableAnnulusArgs")
    fun equalsReturnsCorrectValue(
        annulus: MutableAnnulus, other: MutableAnnulus, expected: Boolean
    ) = assertEquals(expected, annulus.equals(other))

    @ParameterizedTest
    @MethodSource("hashCodeArgs")
    fun hashCodeReturnsCorrectValue(annulus: MutableAnnulus, other: MutableAnnulus) =
        assertEquals(annulus.hashCode(), other.hashCode())

    @ParameterizedTest
    @MethodSource("toStringArgs")
    fun toStringReturnsCorrectValue(annulus: MutableAnnulus, expected: String) =
        assertEquals(expected, annulus.toString())

    @ParameterizedTest
    @MethodSource("componentsArgs")
    fun componentsReturnCorrectValues(
        annulus: Annulus,
        expectedComponent1: Wrapper<Vector2F>,
        expectedComponent2: Wrapper<ComplexF>,
        expectedComponent3: Float,
        expectedComponent4: Float
    ) {
        val (
            actualComponent1: Vector2F,
            actualComponent2: ComplexF,
            actualComponent3: Float,
            actualComponent4: Float
        ) = annulus

        assertEquals(expectedComponent1.value, actualComponent1)
        assertEquals(expectedComponent2.value, actualComponent2)
        assertEquals(expectedComponent3, actualComponent3)
        assertEquals(expectedComponent4, actualComponent4)
    }

    companion object {
        @JvmStatic
        fun areApproximatelyEqual(a: Annulus, b: Annulus): Boolean =
            a.center.isApproximately(b.center) and
                    a.orientation.isApproximately(b.orientation) and
                    a.outerRadius.isApproximately(b.outerRadius) and
                    a.innerRadius.isApproximately(b.innerRadius)

        @JvmStatic
        fun clone(annulus: Annulus) = annulus.copy()

        @JvmStatic
        fun annularRadiusArgs(): List<Arguments> = listOf(
            Arguments.of(
                Annulus(
                    center = Vector2F.ZERO,
                    orientation = ComplexF.ONE,
                    outerRadius = 4f,
                    innerRadius = 2f
                ),
                2f
            ),
            Arguments.of(
                Annulus(
                    center = Vector2F(-1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                    outerRadius = 4f,
                    innerRadius = 2f
                ),
                2f
            ),
            Arguments.of(
                Annulus(
                    center = Vector2F.ZERO,
                    orientation = ComplexF.ONE,
                    outerRadius = 2.5f,
                    innerRadius = 1.5f
                ),
                1f
            ),
        )

        @JvmStatic
        fun areaArgs(): List<Arguments> = listOf(
            Arguments.of(
                Annulus(
                    center = Vector2F.ZERO,
                    orientation = ComplexF.ONE,
                    outerRadius = 4f,
                    innerRadius = 2f
                ),
                37.6991f
            ),
            Arguments.of(
                Annulus(
                    center = Vector2F(-1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                    outerRadius = 4f,
                    innerRadius = 2f
                ),
                37.6991f
            ),
            Arguments.of(
                Annulus(
                    center = Vector2F.ZERO,
                    orientation = ComplexF.ONE,
                    outerRadius = 2.5f,
                    innerRadius = 1.5f
                ),
                12.5664f
            ),
        )

        @JvmStatic
        fun perimeterArgs(): List<Arguments> = listOf(
            Arguments.of(
                Annulus(
                    center = Vector2F.ZERO,
                    orientation = ComplexF.ONE,
                    outerRadius = 4f,
                    innerRadius = 2f
                ),
                37.6991f
            ),
            Arguments.of(
                Annulus(
                    center = Vector2F(-1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                    outerRadius = 4f,
                    innerRadius = 2f
                ),
                37.6991f
            ),
            Arguments.of(
                Annulus(
                    center = Vector2F.ZERO,
                    orientation = ComplexF.ONE,
                    outerRadius = 2.5f,
                    innerRadius = 1.5f
                ),
                25.1327f
            ),
        )

        @JvmStatic
        fun closestPointToArgs(): List<Arguments> = listOf(
            Arguments.of(
                Annulus(
                    center = Vector2F(-1f, 2f),
                    orientation = ComplexF.ONE,
                    outerRadius = 4f,
                    innerRadius = 2f
                ),
                Wrapper(Vector2F(-2.9f, 2f)),
                Wrapper(Vector2F(-3f, 2f))
            ),
            Arguments.of(
                Annulus(
                    center = Vector2F(-1f, 2f),
                    orientation = ComplexF.ONE,
                    outerRadius = 4f,
                    innerRadius = 2f
                ),
                Wrapper(Vector2F(-3.1f, 2f)),
                Wrapper(Vector2F(-3.1f, 2f))
            ),
            Arguments.of(
                Annulus(
                    center = Vector2F(-1f, 2f),
                    orientation = ComplexF.ONE,
                    outerRadius = 4f,
                    innerRadius = 2f
                ),
                Wrapper(Vector2F(-4.9f, 2f)),
                Wrapper(Vector2F(-4.9f, 2f))
            ),
            Arguments.of(
                Annulus(
                    center = Vector2F(-1f, 2f),
                    orientation = ComplexF.ONE,
                    outerRadius = 4f,
                    innerRadius = 2f
                ),
                Wrapper(Vector2F(-5.1f, 2f)),
                Wrapper(Vector2F(-5f, 2f))
            ),
            Arguments.of(
                Annulus(
                    center = Vector2F(-1f, 2f),
                    orientation = ComplexF.ONE,
                    outerRadius = 4f,
                    innerRadius = 2f
                ),
                Wrapper(Vector2F(-5f, 6f)),
                Wrapper(Vector2F(-3.828429f, 4.828429f))
            ),
        )

        @JvmStatic
        fun intersectsCircleArgs(): List<Arguments> = listOf(
            Arguments.of(
                Annulus(
                    center = Vector2F(-1f, 2f),
                    outerRadius = 4f,
                    orientation = ComplexF.ONE,
                    innerRadius = 2f
                ),
                Circle(center = Vector2F(-2f, 2f), orientation = ComplexF.ONE, radius = 1.01f),
                true
            ),
            Arguments.of(
                Annulus(
                    center = Vector2F(-1f, 2f),
                    outerRadius = 4f,
                    orientation = ComplexF.ONE,
                    innerRadius = 2f
                ),
                Circle(center = Vector2F(-2f, 2f), orientation = ComplexF.ONE, radius = 0.99f),
                false
            ),
            Arguments.of(
                Annulus(
                    center = Vector2F(-1f, 2f),
                    outerRadius = 4f,
                    orientation = ComplexF.ONE,
                    innerRadius = 2f
                ),
                Circle(center = Vector2F(-6f, 2f), orientation = ComplexF.ONE, radius = 1.01f),
                true
            ),
            Arguments.of(
                Annulus(
                    center = Vector2F(-1f, 2f),
                    outerRadius = 4f,
                    orientation = ComplexF.ONE,
                    innerRadius = 2f
                ),
                Circle(center = Vector2F(-6f, 2f), orientation = ComplexF.ONE, radius = 0.99f),
                false
            ),
        )

        @JvmStatic
        fun intersectsAnnulusArgs(): List<Arguments> = listOf(
            Arguments.of(
                Annulus(
                    center = Vector2F(-1f, 2f),
                    outerRadius = 4f,
                    orientation = ComplexF.ONE,
                    innerRadius = 2f
                ),
                Annulus(
                    center = Vector2F(-2f, 2f),
                    outerRadius = 1.01f,
                    orientation = ComplexF.ONE,
                    innerRadius = 0.5f
                ),
                true
            ),
            Arguments.of(
                Annulus(
                    center = Vector2F(-1f, 2f),
                    outerRadius = 4f,
                    orientation = ComplexF.ONE,
                    innerRadius = 2f
                ),
                Annulus(
                    center = Vector2F(-2f, 2f),
                    outerRadius = 0.99f,
                    orientation = ComplexF.ONE,
                    innerRadius = 0.5f
                ),
                false
            ),
            Arguments.of(
                Annulus(
                    center = Vector2F(-1f, 2f),
                    outerRadius = 4f,
                    orientation = ComplexF.ONE,
                    innerRadius = 2f
                ),
                Annulus(
                    center = Vector2F(-6f, 2f),
                    outerRadius = 1.01f,
                    orientation = ComplexF.ONE,
                    innerRadius = 0.5f
                ),
                true
            ),
            Arguments.of(
                Annulus(
                    center = Vector2F(-1f, 2f),
                    outerRadius = 4f,
                    orientation = ComplexF.ONE,
                    innerRadius = 2f
                ),
                Annulus(
                    center = Vector2F(-6f, 2f),
                    outerRadius = 0.99f,
                    orientation = ComplexF.ONE,
                    innerRadius = 0.5f
                ),
                false
            ),
            Arguments.of(
                Annulus(
                    center = Vector2F(-1f, 2f),
                    outerRadius = 4f,
                    orientation = ComplexF.ONE,
                    innerRadius = 2f
                ),
                Annulus(
                    center = Vector2F(0f, 2f),
                    outerRadius = 7f,
                    orientation = ComplexF.ONE,
                    innerRadius = 4.9f
                ),
                true
            ),
            Arguments.of(
                Annulus(
                    center = Vector2F(-1f, 2f),
                    outerRadius = 4f,
                    orientation = ComplexF.ONE,
                    innerRadius = 2f
                ),
                Annulus(
                    center = Vector2F(0f, 2f),
                    outerRadius = 7f,
                    orientation = ComplexF.ONE,
                    innerRadius = 5.1f
                ),
                false
            ),
        )

        @JvmStatic
        fun containsVector2FArgs(): List<Arguments> = listOf(
            Arguments.of(
                Annulus(
                    center = Vector2F(-1f, 2f),
                    outerRadius = 4f,
                    orientation = ComplexF.ONE,
                    innerRadius = 2f
                ),
                Wrapper(Vector2F(-3.01f, 2f)),
                true
            ),
            Arguments.of(
                Annulus(
                    center = Vector2F(-1f, 2f),
                    outerRadius = 4f,
                    orientation = ComplexF.ONE,
                    innerRadius = 2f
                ),
                Wrapper(Vector2F(-2.99f, 2f)),
                false
            ),
            Arguments.of(
                Annulus(
                    center = Vector2F.ZERO,
                    outerRadius = 2.5f,
                    orientation = ComplexF.ONE,
                    innerRadius = 1.5f
                ),
                Wrapper(Vector2F(0f, 2.49f)),
                true
            ),
            Arguments.of(
                Annulus(
                    center = Vector2F.ZERO,
                    outerRadius = 2.5f,
                    orientation = ComplexF.ONE,
                    innerRadius = 1.5f
                ),
                Wrapper(Vector2F(0f, 2.51f)),
                false
            ),
        )

        @JvmStatic
        fun containsCircleArgs(): List<Arguments> = listOf(
            Arguments.of(
                Annulus(
                    center = Vector2F(-1f, 2f),
                    outerRadius = 4f,
                    orientation = ComplexF.ONE,
                    innerRadius = 2f
                ),
                Circle(center = Vector2F(-1f, -1f), orientation = ComplexF.ONE, radius = 0.99f),
                true
            ),
            Arguments.of(
                Annulus(
                    center = Vector2F(-1f, 2f),
                    outerRadius = 4f,
                    orientation = ComplexF.ONE,
                    innerRadius = 2f
                ),
                Circle(center = Vector2F(-1f, -1f), orientation = ComplexF.ONE, radius = 1.01f),
                false
            ),
        )

        @JvmStatic
        fun containsAnnulusArgs(): List<Arguments> = listOf(
            Arguments.of(
                Annulus(
                    center = Vector2F(-1f, 2f),
                    outerRadius = 4f,
                    orientation = ComplexF.ONE,
                    innerRadius = 2f
                ),
                Annulus(
                    center = Vector2F(-1f, -1f),
                    outerRadius = 0.99f,
                    orientation = ComplexF.ONE,
                    innerRadius = 0.5f
                ),
                true
            ),
            Arguments.of(
                Annulus(
                    center = Vector2F(-1f, 2f),
                    outerRadius = 4f,
                    orientation = ComplexF.ONE,
                    innerRadius = 2f
                ),
                Annulus(
                    center = Vector2F(-1f, -1f),
                    outerRadius = 1.01f,
                    orientation = ComplexF.ONE,
                    innerRadius = 0.5f
                ),
                false
            ),
            Arguments.of(
                Annulus(
                    center = Vector2F(-1f, 2f),
                    outerRadius = 4f,
                    orientation = ComplexF.ONE,
                    innerRadius = 2f
                ),
                Annulus(
                    center = Vector2F(-1f, 2f),
                    outerRadius = 3.99f,
                    orientation = ComplexF.ONE,
                    innerRadius = 2.01f
                ),
                true
            ),
            Arguments.of(
                Annulus(
                    center = Vector2F(-1f, 2f),
                    outerRadius = 4f,
                    orientation = ComplexF.ONE,
                    innerRadius = 2f
                ),
                Annulus(
                    center = Vector2F(-1f, 2f),
                    outerRadius = 4.01f,
                    orientation = ComplexF.ONE,
                    innerRadius = 2.01f
                ),
                false
            ),
            Arguments.of(
                Annulus(
                    center = Vector2F(-1f, 2f),
                    outerRadius = 4f,
                    orientation = ComplexF.ONE,
                    innerRadius = 2f
                ),
                Annulus(
                    center = Vector2F(-1f, 2f),
                    outerRadius = 3.99f,
                    orientation = ComplexF.ONE,
                    innerRadius = 1.99f
                ),
                false
            ),
        )

        @JvmStatic
        fun copyArgs(): List<Arguments> = listOf(
            Arguments.of(
                Annulus(
                    center = Vector2F(-1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                    outerRadius = 4f,
                    innerRadius = 2f
                ),
                Wrapper(Vector2F(-1f, 2f)),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(-45f))),
                4f,
                2f,
                Annulus(
                    center = Vector2F(-1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                    outerRadius = 4f,
                    innerRadius = 2f
                )
            ),
            Arguments.of(
                Annulus(
                    center = Vector2F(-1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                    outerRadius = 4f,
                    innerRadius = 2f
                ),
                Wrapper(Vector2F(6f, 3f)),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(-45f))),
                4f,
                1f,
                Annulus(
                    center = Vector2F(6f, 3f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                    outerRadius = 4f,
                    innerRadius = 1f
                )
            ),
            Arguments.of(
                Annulus(
                    center = Vector2F(-1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                    outerRadius = 4f,
                    innerRadius = 2f
                ),
                Wrapper(Vector2F(6f, 3f)),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(330f))),
                8f,
                1f,
                Annulus(
                    center = Vector2F(6f, 3f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(330f)),
                    outerRadius = 8f,
                    innerRadius = 1f
                )
            ),
        )

        @JvmStatic
        fun equalsArgs(): List<Arguments> = equalsMutableAnnulusArgs() + listOf(
            Arguments.of(
                MutableAnnulus(
                    center = Vector2F(-1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                    outerRadius = 4f,
                    innerRadius = 2f
                ),
                null,
                false
            ),
        )

        @JvmStatic
        fun equalsMutableAnnulusArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableAnnulus(
                    center = Vector2F(-1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                    outerRadius = 4f,
                    innerRadius = 2f
                ),
                MutableAnnulus(
                    center = Vector2F(-1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                    outerRadius = 4f,
                    innerRadius = 2f
                ),
                true
            ),
            Arguments.of(
                MutableAnnulus(
                    center = Vector2F(-1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                    outerRadius = 4f,
                    innerRadius = 2f
                ),
                MutableAnnulus(
                    center = Vector2F(-1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                    outerRadius = 3.9f,
                    innerRadius = 2f
                ),
                false
            ),
        )

        @JvmStatic
        fun hashCodeArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableAnnulus(
                    center = Vector2F(-1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                    outerRadius = 4f,
                    innerRadius = 2f
                ),
                MutableAnnulus(
                    center = Vector2F(-1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                    outerRadius = 4f,
                    innerRadius = 2f
                )
            ),
            Arguments.of(
                MutableAnnulus(
                    center = Vector2F(6f, 3f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(330f)),
                    outerRadius = 8f,
                    innerRadius = 1f
                ),
                MutableAnnulus(
                    center = Vector2F(6f, 3f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(330f)),
                    outerRadius = 8f,
                    innerRadius = 1f
                )
            )
        )

        @JvmStatic
        fun toStringArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableAnnulus(
                    center = Vector2F(-1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                    outerRadius = 4f,
                    innerRadius = 2f
                ),
                "Annulus(" +
                        "center=${Vector2F(-1f, 2f)}, " +
                        "orientation=${ComplexF.fromAngle(AngleF.fromDegrees(-45f))}, " +
                        "outerRadius=${4f}, " +
                        "innerRadius=${2f})"
            ),
            Arguments.of(
                MutableAnnulus(
                    center = Vector2F(6f, 3f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(330f)),
                    outerRadius = 8f,
                    innerRadius = 1f
                ),
                "Annulus(" +
                        "center=${Vector2F(6f, 3f)}, " +
                        "orientation=${ComplexF.fromAngle(AngleF.fromDegrees(330f))}, " +
                        "outerRadius=${8f}, " +
                        "innerRadius=${1f})"
            )
        )

        @JvmStatic
        fun componentsArgs(): List<Arguments> = listOf(
            Arguments.of(
                Annulus(
                    center = Vector2F(-1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                    outerRadius = 4f,
                    innerRadius = 2f
                ),
                Wrapper(Vector2F(-1f, 2f)),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(-45f))),
                4f,
                2f
            ),
            Arguments.of(
                Annulus(
                    center = Vector2F(6f, 3f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(330f)),
                    outerRadius = 8f,
                    innerRadius = 1f
                ),
                Wrapper(Vector2F(6f, 3f)),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(330f))),
                8f,
                1f
            ),
        )
    }
}