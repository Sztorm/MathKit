package nonallocmath

import com.sztorm.nonallocmath.Vector2I
import com.sztorm.nonallocmath.Vector2I.Companion.times
import com.sztorm.nonallocmath.isApproximately
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.*
import org.junit.jupiter.params.provider.*
import kotlin.test.*

class Vector2ITests {

    @ParameterizedTest
    @MethodSource("vectors")
    fun vectorContentsAreValid(x: Int, y: Int) = assertTrue(equals(Vector2I(x, y), x, y))

    @ParameterizedTest
    @MethodSource("vectors")
    fun basicVectorPropertiesAreValid(x: Int, y: Int) {
        val vec = Vector2I(x, y)
        val (x0, y0) = vec

        assertTrue(
            equals(vec, x, y) &&
                    equals(vec.xy, x, y) &&
                    equals(vec.yx, y, x) &&
                    equals(vec.xx, x, x) &&
                    equals(vec.yy, y, y) &&
                    vec.x == vec[0] &&
                    vec.y == vec[1] &&
                    vec.x == x0 &&
                    vec.y == y0
        )
    }

    @Test
    fun getThrowsWhenIndexIsOutOfBounds() {
        assertThrows<IndexOutOfBoundsException> { Vector2I(3, 4)[-1] }
        assertThrows<IndexOutOfBoundsException> { Vector2I(3, 4)[2] }
    }

    @Test
    fun squaredMagnitudeReturnsCorrectValue() =
        assertTrue(Vector2I(3, 4).squaredMagnitude.isApproximately(25f))

    @Test
    fun magnitudeReturnsCorrectValue() =
        assertTrue(Vector2I(3, 4).magnitude.isApproximately(5f))

    @Test
    fun dotReturnsCorrectValue() =
        assertTrue(Vector2I(3, 4).dot(Vector2I(0, 4)) == 16)

    @Test
    fun coerceInReturnsCorrectValue() =
        assertEquals(
            expected = Vector2I(2, 5),
            actual = Vector2I(3, 4)
                .coerceIn(min = Vector2I(0, 5), max = Vector2I(2, 10))
        )

    @Test
    fun minReturnsCorrectValue() =
        assertEquals(
            expected = Vector2I(2, 4),
            actual = Vector2I.min(Vector2I(3, 4), Vector2I(2, 10))
        )

    @Test
    fun maxReturnsCorrectValue() =
        assertEquals(
            expected = Vector2I(3, 10),
            actual = Vector2I.max(Vector2I(3, 4), Vector2I(2, 10))
        )

    @ParameterizedTest
    @MethodSource("vectors")
    fun unaryPlusOperatorReturnsUnchangedVector(x: Int, y: Int) =
        assertTrue(equals(+Vector2I(x, y), x, y))

    @ParameterizedTest
    @MethodSource("vectors")
    fun unaryMinusOperatorReturnsOppositeVector(x: Int, y: Int) =
        assertTrue(equals(-Vector2I(x, y), -x, -y))

    @ParameterizedTest
    @MethodSource("vectorPairs")
    fun plusOperatorAddsVectors(x1: Int, y1: Int, x2: Int, y2: Int) =
        assertTrue(
            equals(
                Vector2I(x1, y1) + Vector2I(x2, y2),
                x1 + x2, y1 + y2
            )
        )

    @ParameterizedTest
    @MethodSource("vectorPairs")
    fun minusOperatorSubtractsVectors(x1: Int, y1: Int, x2: Int, y2: Int) =
        assertTrue(
            equals(
                Vector2I(x1, y1) - Vector2I(x2, y2),
                x1 - x2, y1 - y2
            )
        )

    @ParameterizedTest
    @MethodSource("vectorPairs")
    fun timesOperatorMultipliesVectorsComponentwise(x1: Int, y1: Int, x2: Int, y2: Int) =
        assertTrue(
            equals(
                Vector2I(x1, y1) * Vector2I(x2, y2),
                x1 * x2, y1 * y2
            )
        )

    @ParameterizedTest
    @MethodSource("vectorAndScalarArgs")
    fun timesOperatorMultipliesVectorsByScalar(x: Int, y: Int, scalar: Int) =
        assertTrue(
            equals(
                Vector2I(x, y) * scalar,
                x * scalar, y * scalar
            ) &&
            equals(
                scalar * Vector2I(x, y),
                x * scalar, y * scalar
            )
        )

    @ParameterizedTest
    @MethodSource("vectorPairs")
    fun divOperatorDividesVectorsComponentwise(x1: Int, y1: Int, x2: Int, y2: Int) =
        assertTrue(
            equals(
                Vector2I(x1, y1) / Vector2I(x2, y2),
                x1 / x2, y1 / y2
            )
        )

    @ParameterizedTest
    @MethodSource("vectorAndScalarArgs")
    fun divOperatorDividesVectorsByScalar(x: Int, y: Int, scalar: Int) =
        assertTrue(
            equals(
                Vector2I(x, y) / scalar,
                x / scalar, y / scalar
            )
        )

    companion object {
        @JvmStatic
        fun equals(vec: Vector2I, x: Int, y: Int) = vec.x == x && vec.y == y

        @JvmStatic
        fun vectors(): List<Arguments> = listOf(
            Arguments.of(2, 4),
            Arguments.of(Int.MAX_VALUE, Int.MIN_VALUE),
            Arguments.of(Int.MIN_VALUE, Int.MAX_VALUE),
        )

        @JvmStatic
        fun vectorAndScalarArgs(): List<Arguments> = listOf(
            Arguments.of(2, 4, 3),
            Arguments.of(-2, 4, 1),
            Arguments.of(Int.MIN_VALUE, 0, -2),
        )

        @JvmStatic
        fun vectorPairs(): List<Arguments> = listOf(
            Arguments.of(2, 4, 6, 8),
            Arguments.of(-1, 4, 2, -3),
            Arguments.of(Int.MIN_VALUE, Int.MAX_VALUE, Int.MAX_VALUE, Int.MIN_VALUE),
        )
    }
}