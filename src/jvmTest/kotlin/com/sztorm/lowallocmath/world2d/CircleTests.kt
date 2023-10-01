package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.AngleF
import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F
import com.sztorm.lowallocmath.utils.Wrapper
import com.sztorm.lowallocmath.utils.assertApproximation
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

class CircleTests {
    @ParameterizedTest
    @MethodSource("areaArgs")
    fun areaReturnsCorrectValue(circle: CircleShape, expected: Float) =
        assertApproximation(expected, circle.area)

    @ParameterizedTest
    @MethodSource("perimeterArgs")
    fun perimeterReturnsCorrectValue(circle: CircleShape, expected: Float) =
        assertApproximation(expected, circle.perimeter)

    @ParameterizedTest
    @MethodSource("diameterArgs")
    fun diameterReturnsCorrectValue(circle: CircleShape, expected: Float) =
        assertApproximation(expected, circle.diameter)

    @ParameterizedTest
    @MethodSource("closestPointToArgs")
    fun closestPointToReturnsCorrectValue(
        circle: Circle, point: Wrapper<Vector2F>, expected: Wrapper<Vector2F>
    ) = assertApproximation(expected.value, circle.closestPointTo(point.value))

    @ParameterizedTest
    @MethodSource("intersectsAnnulusArgs")
    fun intersectsReturnsCorrectValue(circle: Circle, annulus: AnnulusShape, expected: Boolean) =
        assertEquals(expected, circle.intersects(annulus))

    @ParameterizedTest
    @MethodSource("intersectsCircleArgs")
    fun intersectsReturnsCorrectValue(
        circle: Circle, otherCircle: CircleShape, expected: Boolean
    ) = assertEquals(expected, circle.intersects(otherCircle))

    @ParameterizedTest
    @MethodSource("containsVector2FArgs")
    fun containsReturnsCorrectValue(circle: Circle, point: Wrapper<Vector2F>, expected: Boolean) =
        assertEquals(expected, circle.contains(point.value))

    @ParameterizedTest
    @MethodSource("containsAnnulusArgs")
    fun containsReturnsCorrectValue(circle: Circle, annulus: AnnulusShape, expected: Boolean) =
        assertEquals(expected, circle.contains(annulus))

    @ParameterizedTest
    @MethodSource("containsCircleArgs")
    fun containsReturnsCorrectValue(circle: Circle, otherCircle: CircleShape, expected: Boolean) =
        assertEquals(expected, circle.contains(otherCircle))

    @ParameterizedTest
    @MethodSource("copyArgs")
    fun copyReturnsCorrectValue(
        circle: Circle,
        center: Wrapper<Vector2F>,
        rotation: Wrapper<ComplexF>,
        radius: Float,
        expected: Circle
    ) = assertEquals(expected, circle.copy(center.value, rotation.value, radius))

    @ParameterizedTest
    @MethodSource("equalsArgs")
    fun equalsReturnsCorrectValue(
        circle: MutableCircle, other: Any?, expected: Boolean
    ) = assertEquals(expected, circle == other)

    @ParameterizedTest
    @MethodSource("equalsMutableCircleArgs")
    fun equalsReturnsCorrectValue(
        circle: MutableCircle, other: MutableCircle, expected: Boolean
    ) = assertEquals(expected, circle.equals(other))

    @ParameterizedTest
    @MethodSource("hashCodeArgs")
    fun hashCodeReturnsCorrectValue(circle: MutableCircle, other: MutableCircle) =
        assertEquals(circle.hashCode(), other.hashCode())

    @ParameterizedTest
    @MethodSource("toStringArgs")
    fun toStringReturnsCorrectValue(circle: MutableCircle, expected: String) =
        assertEquals(expected, circle.toString())

    @ParameterizedTest
    @MethodSource("componentsArgs")
    fun componentsReturnCorrectValues(
        circle: Circle,
        expectedComponent1: Wrapper<Vector2F>,
        expectedComponent2: Wrapper<ComplexF>,
        expectedComponent3: Float
    ) {
        val (
            actualComponent1: Vector2F,
            actualComponent2: ComplexF,
            actualComponent3: Float
        ) = circle

        assertEquals(expectedComponent1.value, actualComponent1)
        assertEquals(expectedComponent2.value, actualComponent2)
        assertEquals(expectedComponent3, actualComponent3)
    }

    companion object {
        @JvmStatic
        fun areaArgs(): List<Arguments> = listOf(
            Arguments.of(
                Circle(center = Vector2F.ZERO, rotation = ComplexF.ONE, radius = 4f), 50.2655f
            ),
            Arguments.of(
                Circle(
                    center = Vector2F(1f, 2f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                    radius = 4f
                ),
                50.2655f
            ),
            Arguments.of(
                Circle(center = Vector2F.ZERO, rotation = ComplexF.ONE, radius = 2.5f), 19.635f
            ),
        )

        @JvmStatic
        fun perimeterArgs(): List<Arguments> = listOf(
            Arguments.of(
                Circle(center = Vector2F.ZERO, rotation = ComplexF.ONE, radius = 4f), 25.1327f
            ),
            Arguments.of(
                Circle(
                    center = Vector2F(1f, 2f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                    radius = 4f
                ),
                25.1327f
            ),
            Arguments.of(
                Circle(center = Vector2F.ZERO, rotation = ComplexF.ONE, radius = 2.5f), 15.708f
            ),
        )

        @JvmStatic
        fun diameterArgs(): List<Arguments> = listOf(
            Arguments.of(
                Circle(center = Vector2F.ZERO, rotation = ComplexF.ONE, radius = 4f), 8f
            ),
            Arguments.of(
                Circle(
                    center = Vector2F(1f, 2f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                    radius = 4f
                ), 8f
            ),
            Arguments.of(
                Circle(center = Vector2F.ZERO, rotation = ComplexF.ONE, radius = 2.5f), 5f
            ),
        )

        @JvmStatic
        fun closestPointToArgs(): List<Arguments> = listOf(
            Arguments.of(
                Circle(center = Vector2F(2f, 0f), rotation = ComplexF.ONE, radius = 4f),
                Wrapper(Vector2F.ZERO),
                Wrapper(Vector2F.ZERO)
            ),
            Arguments.of(
                Circle(center = Vector2F(2f, 0f), rotation = ComplexF.ONE, radius = 4f),
                Wrapper(Vector2F(-2.1f, 0f)),
                Wrapper(Vector2F(-2f, 0f))
            ),
            Arguments.of(
                Circle(center = Vector2F(2f, 0f), rotation = ComplexF.ONE, radius = 4f),
                Wrapper(Vector2F(-2f, 4f)),
                Wrapper(Vector2F(-0.828429f, 2.828429f))
            ),
            Arguments.of(
                Circle(center = Vector2F(2f, 0f), rotation = ComplexF.ONE, radius = 4f),
                Wrapper(Vector2F(6f, -4f)),
                Wrapper(Vector2F(4.828429f, -2.828429f))
            ),
            Arguments.of(
                Circle(center = Vector2F(2f, 0f), rotation = ComplexF.ONE, radius = 4f),
                Wrapper(Vector2F(0f, 2f)),
                Wrapper(Vector2F(0f, 2f))
            ),
        )

        @JvmStatic
        fun intersectsAnnulusArgs(): List<Arguments> = listOf(
            Arguments.of(
                Circle(center = Vector2F(-2f, 2f), rotation = ComplexF.ONE, radius = 1.01f),
                Annulus(
                    center = Vector2F(-1f, 2f),
                    rotation = ComplexF.ONE,
                    outerRadius = 4f,
                    innerRadius = 2f
                ),
                true
            ),
            Arguments.of(
                Circle(center = Vector2F(-2f, 2f), rotation = ComplexF.ONE, radius = 0.99f),
                Annulus(
                    center = Vector2F(-1f, 2f),
                    rotation = ComplexF.ONE,
                    outerRadius = 4f,
                    innerRadius = 2f
                ),
                false
            ),
            Arguments.of(
                Circle(center = Vector2F(-6f, 2f), rotation = ComplexF.ONE, radius = 1.01f),
                Annulus(
                    center = Vector2F(-1f, 2f),
                    rotation = ComplexF.ONE,
                    outerRadius = 4f,
                    innerRadius = 2f
                ),
                true
            ),
            Arguments.of(
                Circle(center = Vector2F(-6f, 2f), rotation = ComplexF.ONE, radius = 0.99f),
                Annulus(
                    center = Vector2F(-1f, 2f),
                    rotation = ComplexF.ONE,
                    outerRadius = 4f,
                    innerRadius = 2f
                ),
                false
            ),
        )

        @JvmStatic
        fun intersectsCircleArgs(): List<Arguments> = listOf(
            Arguments.of(
                Circle(center = Vector2F(-4f, 4f), rotation = ComplexF.ONE, radius = 4f),
                Circle(center = Vector2F(2f, 0f), rotation = ComplexF.ONE, radius = 3f),
                false
            ),
            Arguments.of(
                Circle(center = Vector2F(-4f, 4f), rotation = ComplexF.ONE, radius = 4f),
                Circle(center = Vector2F(2f, 0f), rotation = ComplexF.ONE, radius = 4f),
                true
            ),
            Arguments.of(
                Circle(center = Vector2F(-4f, 4f), rotation = ComplexF.ONE, radius = 4f),
                Circle(center = Vector2F(3.99f, 4f), rotation = ComplexF.ONE, radius = 4f),
                true
            ),
        )

        @JvmStatic
        fun containsVector2FArgs(): List<Arguments> = listOf(
            Arguments.of(
                Circle(center = Vector2F.ZERO, rotation = ComplexF.ONE, radius = 4f),
                Wrapper(Vector2F.ZERO),
                true
            ),
            Arguments.of(
                Circle(center = Vector2F.ZERO, rotation = ComplexF.ONE, radius = 4f),
                Wrapper(Vector2F(3.99f, 0f)),
                true
            ),
            Arguments.of(
                Circle(center = Vector2F.ZERO, rotation = ComplexF.ONE, radius = 4f),
                Wrapper(Vector2F(4.01f, 0f)),
                false
            ),
        )

        @JvmStatic
        fun containsAnnulusArgs(): List<Arguments> = listOf(
            Arguments.of(
                Circle(center = Vector2F.ZERO, rotation = ComplexF.ONE, radius = 4f),
                Annulus(
                    center = Vector2F.ZERO,
                    rotation = ComplexF.ONE,
                    outerRadius = 3.99f,
                    innerRadius = 2f
                ),
                true
            ),
            Arguments.of(
                Circle(center = Vector2F.ZERO, rotation = ComplexF.ONE, radius = 4f),
                Annulus(
                    center = Vector2F.ZERO,
                    rotation = ComplexF.ONE,
                    outerRadius = 4.01f,
                    innerRadius = 2f
                ),
                false
            ),
            Arguments.of(
                Circle(center = Vector2F(2f, 0f), rotation = ComplexF.ONE, radius = 4f),
                Annulus(
                    center = Vector2F(4f, 0f),
                    rotation = ComplexF.ONE,
                    outerRadius = 1.99f,
                    innerRadius = 1f
                ),
                true
            ),
            Arguments.of(
                Circle(center = Vector2F(2f, 0f), rotation = ComplexF.ONE, radius = 4f),
                Annulus(
                    center = Vector2F(4f, 0f),
                    rotation = ComplexF.ONE,
                    outerRadius = 2.01f,
                    innerRadius = 1f
                ),
                false
            ),
        )

        @JvmStatic
        fun containsCircleArgs(): List<Arguments> = listOf(
            Arguments.of(
                Circle(center = Vector2F.ZERO, rotation = ComplexF.ONE, radius = 4f),
                Circle(center = Vector2F.ZERO, rotation = ComplexF.ONE, radius = 3.99f),
                true
            ),
            Arguments.of(
                Circle(center = Vector2F.ZERO, rotation = ComplexF.ONE, radius = 4f),
                Circle(center = Vector2F.ZERO, rotation = ComplexF.ONE, radius = 4.01f),
                false
            ),
            Arguments.of(
                Circle(center = Vector2F(2f, 0f), rotation = ComplexF.ONE, radius = 4f),
                Circle(center = Vector2F(4f, 0f), rotation = ComplexF.ONE, radius = 1.99f),
                true
            ),
            Arguments.of(
                Circle(center = Vector2F(2f, 0f), rotation = ComplexF.ONE, radius = 4f),
                Circle(center = Vector2F(4f, 0f), rotation = ComplexF.ONE, radius = 2.01f),
                false
            ),
        )

        @JvmStatic
        fun copyArgs(): List<Arguments> = listOf(
            Arguments.of(
                Circle(
                    center = Vector2F(2f, 0f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(-78f)),
                    radius = 4f
                ),
                Wrapper(Vector2F(2f, 0f)),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(-78f))),
                4f,
                Circle(
                    center = Vector2F(2f, 0f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(-78f)),
                    radius = 4f
                )
            ),
            Arguments.of(
                Circle(
                    center = Vector2F(2f, 0f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(-78f)),
                    radius = 4f
                ),
                Wrapper(Vector2F(2f, 0f)),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(-78f))),
                5f,
                Circle(
                    center = Vector2F(2f, 0f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(-78f)),
                    radius = 5f
                )
            ),
            Arguments.of(
                Circle(
                    center = Vector2F(2f, 0f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(244f)),
                    radius = 4f
                ),
                Wrapper(Vector2F(-1f, 7f)),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(244f))),
                5f,
                Circle(
                    center = Vector2F(-1f, 7f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(244f)),
                    radius = 5f
                )
            ),
        )

        @JvmStatic
        fun equalsArgs(): List<Arguments> = equalsMutableCircleArgs() + listOf(
            Arguments.of(
                MutableCircle(
                    center = Vector2F(2f, 0f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(-78f)),
                    radius = 4f
                ),
                null,
                false
            ),
        )

        @JvmStatic
        fun equalsMutableCircleArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableCircle(
                    center = Vector2F(2f, 0f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(-78f)),
                    radius = 4f
                ),
                MutableCircle(
                    center = Vector2F(2f, 0f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(-78f)),
                    radius = 4f
                ),
                true
            ),
            Arguments.of(
                MutableCircle(
                    center = Vector2F(2f, 0f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(-78f)),
                    radius = 4f
                ),
                MutableCircle(
                    center = Vector2F(2f, 0.1f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(-78f)),
                    radius = 4f
                ),
                false
            ),
        )

        @JvmStatic
        fun hashCodeArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableCircle(
                    center = Vector2F(2f, 0f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(-78f)),
                    radius = 4f
                ),
                MutableCircle(
                    center = Vector2F(2f, 0f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(-78f)),
                    radius = 4f
                )
            ),
            Arguments.of(
                MutableCircle(
                    center = Vector2F(-1f, 7f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(244f)),
                    radius = 5f
                ),
                MutableCircle(
                    center = Vector2F(-1f, 7f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(244f)),
                    radius = 5f
                )
            ),
        )

        @JvmStatic
        fun toStringArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableCircle(
                    center = Vector2F(2f, 0f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(-78f)),
                    radius = 4f
                ),
                "Circle(" +
                        "center=${Vector2F(2f, 0f)}, " +
                        "rotation=${ComplexF.fromAngle(AngleF.fromDegrees(-78f))}, " +
                        "radius=${4f})"
            ),
            Arguments.of(
                MutableCircle(
                    center = Vector2F(-1f, 7f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(244f)),
                    radius = 5f
                ),
                "Circle(" +
                        "center=${Vector2F(-1f, 7f)}, " +
                        "rotation=${ComplexF.fromAngle(AngleF.fromDegrees(244f))}, " +
                        "radius=${5f})"
            ),
        )

        @JvmStatic
        fun componentsArgs(): List<Arguments> = listOf(
            Arguments.of(
                Circle(
                    center = Vector2F(2f, 0f),
                    ComplexF.fromAngle(AngleF.fromDegrees(-78f)),
                    radius = 4f
                ),
                Wrapper(Vector2F(2f, 0f)),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(-78f))),
                4f
            ),
            Arguments.of(
                Circle(
                    center = Vector2F(-1f, 7f),
                    ComplexF.fromAngle(AngleF.fromDegrees(244f)),
                    radius = 5f
                ),
                Wrapper(Vector2F(-1f, 7f)),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(244f))),
                5f
            ),
        )
    }
}