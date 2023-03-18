package nonallocmath

import com.sztorm.nonallocmath.Vector2F
import com.sztorm.nonallocmath.Vector2F.Companion.times
import com.sztorm.nonallocmath.isApproximately
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.*
import org.junit.jupiter.params.provider.*
import kotlin.test.*

class Vector2FTests {

    @ParameterizedTest
    @MethodSource("vectors")
    fun vectorContentsAreValid(x: Float, y: Float) =
        assertTrue(equalsBitwise(Vector2F(x, y), x, y))

    @ParameterizedTest
    @MethodSource("vectors")
    fun basicVectorPropertiesAreValid(x: Float, y: Float) {
        val vec = Vector2F(x, y)
        val (x0, y0) = vec

        assertTrue(
            equalsBitwise(vec, x, y) &&
                    equalsBitwise(vec.xy, x, y) &&
                    equalsBitwise(vec.yx, y, x) &&
                    equalsBitwise(vec.xx, x, x) &&
                    equalsBitwise(vec.yy, y, y) &&
                    vec.x.toRawBits() == vec[0].toRawBits() &&
                    vec.y.toRawBits() == vec[1].toRawBits() &&
                    vec.x.toRawBits() == x0.toRawBits() &&
                    vec.y.toRawBits() == y0.toRawBits()
        )
    }

    @Test
    fun getThrowsWhenIndexIsOutOfBounds() {
        assertThrows<IndexOutOfBoundsException> { Vector2F(3f, 4f)[-1] }
        assertThrows<IndexOutOfBoundsException> { Vector2F(3f, 4f)[2] }
    }

    @Test
    fun squaredMagnitudeReturnsCorrectValue() =
        assertTrue(Vector2F(3f, 4f).squaredMagnitude.isApproximately(25f))

    @Test
    fun magnitudeReturnsCorrectValue() =
        assertTrue(Vector2F(3f, 4f).magnitude.isApproximately(5f))

    @Test
    fun normalizedReturnsCorrectValue() =
        assertTrue(Vector2F(3f, 4f).normalized.isApproximately(Vector2F(0.6f, 0.8f)))

    @Test
    fun squaredDistanceToReturnsCorrectValue() =
        assertTrue(
            Vector2F(3f, 4f)
                .squaredDistanceTo(Vector2F(0f, 4f))
                .isApproximately(9f)
        )

    @Test
    fun distanceToReturnsCorrectValue() =
        assertTrue(
            Vector2F(3f, 4f)
                .distanceTo(Vector2F(0f, 4f))
                .isApproximately(3f)
        )

    @Test
    fun dotReturnsCorrectValue() =
        assertTrue(
            Vector2F(3f, 4f)
                .dot(Vector2F(0f, 4f))
                .isApproximately(16f)
        )

    @Test
    fun coerceInReturnsCorrectValue() =
        assertTrue(
            Vector2F(3f, 4f)
                .coerceIn(min = Vector2F(0f, 5f), max = Vector2F(2f, 10f))
                .isApproximately(Vector2F(2f, 5f))
        )

    @Test
    fun lerpReturnsCorrectValue() =
        assertTrue(
            Vector2F.lerp(Vector2F(0f, 10f), Vector2F(10f, 20f), 0.5f)
                .isApproximately(Vector2F(5f, 15f))
        )

    @Test
    fun lerpVector2ReturnsCorrectValue() =
        assertTrue(
            Vector2F.lerp(
                a = Vector2F(0f, 10f),
                b = Vector2F(10f, 20f),
                t = Vector2F(0.5f, 0.1f)
            )
                .isApproximately(Vector2F(5f, 11f))
        )

    @Test
    fun inverseLerpInReturnsCorrectValue() =
        assertTrue(
            Vector2F.inverseLerp(
                a = Vector2F(0f, 10f),
                b = Vector2F(10f, 20f),
                t = Vector2F(5f, 11f)
            )
                .isApproximately(Vector2F(0.5f, 0.1f))
        )

    @Test
    fun minReturnsCorrectValue() =
        assertTrue(
            Vector2F.min(Vector2F(3f, 4f), Vector2F(2f, 10f))
                .isApproximately(Vector2F(2f, 4f))
        )

    @Test
    fun maxReturnsCorrectValue() =
        assertTrue(
            Vector2F.max(Vector2F(3f, 4f), Vector2F(2f, 10f))
                .isApproximately(Vector2F(3f, 10f))
        )

    @ParameterizedTest
    @MethodSource("vectors")
    fun unaryPlusOperatorReturnsUnchangedVector(x: Float, y: Float) =
        assertTrue(equalsBitwise(+Vector2F(x, y), x, y))

    @ParameterizedTest
    @MethodSource("vectors")
    fun unaryMinusOperatorReturnsOppositeVector(x: Float, y: Float) =
        assertTrue(equalsBitwise(-Vector2F(x, y), -x, -y))

    @ParameterizedTest
    @MethodSource("vectorPairs")
    fun plusOperatorAddsVectors(x1: Float, y1: Float, x2: Float, y2: Float) =
        assertTrue(
            equalsBitwise(
                Vector2F(x1, y1) + Vector2F(x2, y2),
                x1 + x2, y1 + y2
            )
        )

    @ParameterizedTest
    @MethodSource("vectorPairs")
    fun minusOperatorSubtractsVectors(x1: Float, y1: Float, x2: Float, y2: Float) =
        assertTrue(
            equalsBitwise(
                Vector2F(x1, y1) - Vector2F(x2, y2),
                x1 - x2, y1 - y2
            )
        )

    @ParameterizedTest
    @MethodSource("vectorPairs")
    fun timesOperatorMultipliesVectorsComponentwise(x1: Float, y1: Float, x2: Float, y2: Float) =
        assertTrue(
            equalsBitwise(
                Vector2F(x1, y1) * Vector2F(x2, y2),
                x1 * x2, y1 * y2
            )
        )

    @ParameterizedTest
    @MethodSource("vectorAndScalarArgs")
    fun timesOperatorMultipliesVectorsByScalar(x: Float, y: Float, scalar: Float) =
        assertTrue(
            equalsBitwise(
                Vector2F(x, y) * scalar,
                x * scalar, y * scalar
            ) &&
                    equalsBitwise(
                        scalar * Vector2F(x, y),
                        x * scalar, y * scalar
                    )
        )

    @ParameterizedTest
    @MethodSource("vectorPairs")
    fun divOperatorDividesVectorsComponentwise(x1: Float, y1: Float, x2: Float, y2: Float) =
        assertTrue(
            equalsBitwise(
                Vector2F(x1, y1) / Vector2F(x2, y2),
                x1 / x2, y1 / y2
            )
        )

    @ParameterizedTest
    @MethodSource("vectorAndScalarArgs")
    fun divOperatorDividesVectorsByScalar(x: Float, y: Float, scalar: Float) =
        assertTrue(
            equalsBitwise(
                Vector2F(x, y) / scalar,
                x / scalar, y / scalar
            )
        )

    companion object {
        @JvmStatic
        fun vectors(): List<Arguments> = listOf(
            Arguments.of(2f, 4f),
            Arguments.of(2f, Float.NaN),
            Arguments.of(Float.NEGATIVE_INFINITY, -1f),
            Arguments.of(-1f, Float.NEGATIVE_INFINITY),
        )

        @JvmStatic
        fun vectorAndScalarArgs(): List<Arguments> = listOf(
            Arguments.of(2f, 4f, 3f),
            Arguments.of(2f, Float.NaN, Float.NaN),
            Arguments.of(Float.NEGATIVE_INFINITY, -1f, 5f),
            Arguments.of(-1f, Float.NEGATIVE_INFINITY, 0.1f),
        )

        @JvmStatic
        fun vectorPairs(): List<Arguments> = listOf(
            Arguments.of(2f, 4f, 3f, 1f),
            Arguments.of(2f, Float.NaN, Float.NaN, 5f),
            Arguments.of(Float.NEGATIVE_INFINITY, -1f, 5f, 6f),
            Arguments.of(-1f, Float.NEGATIVE_INFINITY, 0.1f, Float.POSITIVE_INFINITY),
        )
    }
}