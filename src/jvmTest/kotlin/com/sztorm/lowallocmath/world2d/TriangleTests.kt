package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.Vector2F
import com.sztorm.lowallocmath.utils.Wrapper
import com.sztorm.lowallocmath.utils.assertApproximation
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

class TriangleTests {
    @ParameterizedTest
    @MethodSource("pointsArgs")
    fun pointsReturnCorrectValues(
        triangle: TriangleShape,
        expectedPointA: Wrapper<Vector2F>,
        expectedPointB: Wrapper<Vector2F>,
        expectedPointC: Wrapper<Vector2F>
    ) {
        assertApproximation(expectedPointA.value, triangle.pointA)
        assertApproximation(expectedPointB.value, triangle.pointB)
        assertApproximation(expectedPointC.value, triangle.pointC)
    }

    @ParameterizedTest
    @MethodSource("areaArgs")
    fun areaReturnsCorrectValue(triangle: TriangleShape, expected: Float) =
        assertApproximation(expected, triangle.area)

    @ParameterizedTest
    @MethodSource("perimeterArgs")
    fun perimeterReturnsCorrectValue(triangle: TriangleShape, expected: Float) =
        assertApproximation(expected, triangle.perimeter)

    @ParameterizedTest
    @MethodSource("centroidArgs")
    fun centroidReturnsCorrectValue(triangle: TriangleShape, expected: Wrapper<Vector2F>) =
        assertApproximation(expected.value, triangle.centroid)

    @ParameterizedTest
    @MethodSource("orthocenterArgs")
    fun orthocenterReturnsCorrectValue(triangle: TriangleShape, expected: Wrapper<Vector2F>) =
        assertApproximation(expected.value, triangle.orthocenter)

    @ParameterizedTest
    @MethodSource("incenterArgs")
    fun incenterReturnsCorrectValue(triangle: TriangleShape, expected: Wrapper<Vector2F>) =
        assertApproximation(expected.value, triangle.incenter)

    @ParameterizedTest
    @MethodSource("circumcenterArgs")
    fun circumcenterReturnsCorrectValue(triangle: TriangleShape, expected: Wrapper<Vector2F>) =
        assertApproximation(expected.value, triangle.circumcenter)

    @ParameterizedTest
    @MethodSource("closestPointToArgs")
    fun closestPointToReturnsCorrectValue(
        triangle: Triangle, point: Wrapper<Vector2F>, expected: Wrapper<Vector2F>
    ) = assertApproximation(expected.value, triangle.closestPointTo(point.value))

    @ParameterizedTest
    @MethodSource("containsVector2FArgs")
    fun containsReturnsCorrectValue(
        triangle: Triangle, point: Wrapper<Vector2F>, expected: Boolean
    ) = assertEquals(expected, triangle.contains(point.value))

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
    @MethodSource("equalsArgs")
    fun equalsReturnsCorrectValue(triangle: MutableTriangle, other: Any?, expected: Boolean) =
        assertEquals(expected, triangle == other)

    @ParameterizedTest
    @MethodSource("equalsMutableTriangleArgs")
    fun equalsReturnsCorrectValue(
        triangle: MutableTriangle, other: MutableTriangle, expected: Boolean
    ) = assertEquals(expected, triangle.equals(other))

    @ParameterizedTest
    @MethodSource("hashCodeArgs")
    fun hashCodeReturnsCorrectValue(triangle: MutableTriangle, other: MutableTriangle) =
        assertEquals(triangle.hashCode(), other.hashCode())

    @ParameterizedTest
    @MethodSource("toStringArgs")
    fun toStringReturnsCorrectValue(triangle: MutableTriangle, expected: String) =
        assertEquals(expected, triangle.toString())

    @ParameterizedTest
    @MethodSource("componentsArgs")
    fun componentsReturnCorrectValues(
        triangle: Triangle,
        expectedComponent1: Wrapper<Vector2F>,
        expectedComponent2: Wrapper<Vector2F>,
        expectedComponent3: Wrapper<Vector2F>
    ) {
        val (actualComponent1, actualComponent2, actualComponent3) = triangle

        assertEquals(expectedComponent1.value, actualComponent1)
        assertEquals(expectedComponent2.value, actualComponent2)
        assertEquals(expectedComponent3.value, actualComponent3)
    }

    companion object {
        @JvmStatic
        fun areApproximatelyEqual(a: Triangle, b: Triangle): Boolean =
            a.pointA.isApproximately(b.pointA) and
                    a.pointB.isApproximately(b.pointB) and
                    a.pointC.isApproximately(b.pointC) and
                    a.centroid.isApproximately(b.centroid) and
                    a.rotation.isApproximately(b.rotation)

        @JvmStatic
        fun pointsArgs(): List<Arguments> = listOf(
            Arguments.of(
                Triangle(Vector2F(-4f, 2f), Vector2F(2f, 2f), Vector2F(1f, 5f)),
                Wrapper(Vector2F(-4f, 2f)),
                Wrapper(Vector2F(2f, 2f)),
                Wrapper(Vector2F(1f, 5f))
            ),
            Arguments.of(
                Triangle(
                    Vector2F(-2f, 1f), Vector2F(-3f, -3f), Vector2F(1f, -6f)
                ),
                Wrapper(Vector2F(-2f, 1f)),
                Wrapper(Vector2F(-3f, -3f)),
                Wrapper(Vector2F(1f, -6f))
            ),
            Arguments.of(
                Triangle(
                    Vector2F(8f, -2.535898f),
                    Vector2F(10f, -6f),
                    Vector2F(6f, -6f)
                ),
                Wrapper(Vector2F(8f, -2.535898f)),
                Wrapper(Vector2F(10f, -6f)),
                Wrapper(Vector2F(6f, -6f))
            ),
        )

        @JvmStatic
        fun areaArgs(): List<Arguments> = listOf(
            Arguments.of(
                Triangle(Vector2F(-4f, 2f), Vector2F(2f, 2f), Vector2F(1f, 5f)),
                9f
            ),
            Arguments.of(
                Triangle(
                    Vector2F(-2f, 1f), Vector2F(-3f, -3f), Vector2F(1f, -6f)
                ),
                9.5f
            ),
            Arguments.of(
                Triangle(
                    Vector2F(8f, -2.535898f),
                    Vector2F(10f, -6f),
                    Vector2F(6f, -6f)
                ),
                6.928203f
            ),
        )

        @JvmStatic
        fun perimeterArgs(): List<Arguments> = listOf(
            Arguments.of(
                Triangle(Vector2F(-4f, 2f), Vector2F(2f, 2f), Vector2F(1f, 5f)),
                14.99323f
            ),
            Arguments.of(
                Triangle(
                    Vector2F(-2f, 1f), Vector2F(-3f, -3f), Vector2F(1f, -6f)
                ),
                16.73888f
            ),
            Arguments.of(
                Triangle(
                    Vector2F(8f, -2.535898f),
                    Vector2F(10f, -6f),
                    Vector2F(6f, -6f)
                ),
                12f
            ),
        )

        @JvmStatic
        fun centroidArgs(): List<Arguments> = listOf(
            Arguments.of(
                Triangle(Vector2F(-4f, 2f), Vector2F(2f, 2f), Vector2F(1f, 5f)),
                Wrapper(Vector2F(-0.3333333f, 3f))
            ),
            Arguments.of(
                Triangle(
                    Vector2F(-2f, 1f), Vector2F(-3f, -3f), Vector2F(1f, -6f)
                ),
                Wrapper(Vector2F(-1.3333333f, -2.6666667f))
            ),
            Arguments.of(
                Triangle(
                    Vector2F(8f, -2.535898f),
                    Vector2F(10f, -6f),
                    Vector2F(6f, -6f)
                ),
                Wrapper(Vector2F(8f, -4.8453f))
            ),
        )

        @JvmStatic
        fun orthocenterArgs(): List<Arguments> = listOf(
            Arguments.of(
                Triangle(Vector2F(-4f, 2f), Vector2F(2f, 2f), Vector2F(1f, 5f)),
                Wrapper(Vector2F(1f, 3.6666667f))
            ),
            Arguments.of(
                Triangle(
                    Vector2F(-2f, 1f), Vector2F(-3f, -3f), Vector2F(1f, -6f)
                ),
                Wrapper(Vector2F(-5.947368f, -4.263158f))
            ),
            Arguments.of(
                Triangle(
                    Vector2F(8f, -2.535898f),
                    Vector2F(10f, -6f),
                    Vector2F(6f, -6f)
                ),
                Wrapper(Vector2F(8f, -4.8453f))
            ),
        )

        @JvmStatic
        fun incenterArgs(): List<Arguments> = listOf(
            Arguments.of(
                Triangle(Vector2F(-4f, 2f), Vector2F(2f, 2f), Vector2F(1f, 5f)),
                Wrapper(Vector2F(0.3343371f, 3.200542f))
            ),
            Arguments.of(
                Triangle(
                    Vector2F(-2f, 1f), Vector2F(-3f, -3f), Vector2F(1f, -6f)
                ),
                Wrapper(Vector2F(-1.7160178f, -2.544134f))
            ),
            Arguments.of(
                Triangle(
                    Vector2F(8f, -2.535898f),
                    Vector2F(10f, -6f),
                    Vector2F(6f, -6f)
                ),
                Wrapper(Vector2F(8f, -4.8453f))
            ),
        )

        @JvmStatic
        fun circumcenterArgs(): List<Arguments> = listOf(
            Arguments.of(
                Triangle(Vector2F(-4f, 2f), Vector2F(2f, 2f), Vector2F(1f, 5f)),
                Wrapper(Vector2F(-1f, 2.6666667f))
            ),
            Arguments.of(
                Triangle(
                    Vector2F(-2f, 1f), Vector2F(-3f, -3f), Vector2F(1f, -6f)
                ),
                Wrapper(Vector2F(0.9736842f, -1.868421f))
            ),
            Arguments.of(
                Triangle(
                    Vector2F(8f, -2.535898f),
                    Vector2F(10f, -6f),
                    Vector2F(6f, -6f)
                ),
                Wrapper(Vector2F(8f, -4.8453f))
            ),
        )

        @JvmStatic
        fun closestPointToArgs(): List<Arguments> {
            val triangleA = Triangle(
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
            val triangleB = Triangle(
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
            val triangleC = Triangle(
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
            return triangleAArgs + triangleBArgs + triangleCArgs
        }

        @JvmStatic
        fun containsVector2FArgs(): List<Arguments> {
            val triangleA = Triangle(
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
            val triangleB = Triangle(
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
            val triangleC = Triangle(
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
            return triangleAArgs + triangleBArgs + triangleCArgs
        }

        @JvmStatic
        fun copyArgs(): List<Arguments> = listOf(
            Arguments.of(
                Triangle(Vector2F(-4f, 2f), Vector2F(2f, 2f), Vector2F(1f, 5f)),
                Wrapper(Vector2F(-4f, 2f)),
                Wrapper(Vector2F(2f, 2f)),
                Wrapper(Vector2F(1f, 5f)),
                Triangle(Vector2F(-4f, 2f), Vector2F(2f, 2f), Vector2F(1f, 5f))
            ),
            Arguments.of(
                Triangle(Vector2F(-4f, 2f), Vector2F(2f, 2f), Vector2F(1f, 5f)),
                Wrapper(Vector2F(-4f, 2f)),
                Wrapper(Vector2F(-3f, -3f)),
                Wrapper(Vector2F(1f, -6f)),
                Triangle(
                    Vector2F(-4f, 2f), Vector2F(-3f, -3f), Vector2F(1f, -6f)
                )
            ),
            Arguments.of(
                Triangle(Vector2F(-4f, 2f), Vector2F(2f, 2f), Vector2F(1f, 5f)),
                Wrapper(Vector2F(-2f, 1f)),
                Wrapper(Vector2F(-3f, -3f)),
                Wrapper(Vector2F(1f, -6f)),
                Triangle(
                    Vector2F(-2f, 1f), Vector2F(-3f, -3f), Vector2F(1f, -6f)
                )
            ),
        )

        @JvmStatic
        fun equalsArgs(): List<Arguments> = equalsMutableTriangleArgs() + listOf(
            Arguments.of(
                MutableTriangle(
                    Vector2F(-4f, 2f), Vector2F(2f, 2f), Vector2F(1f, 5f)
                ),
                null,
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