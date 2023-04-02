package nonallocmath

import com.sztorm.nonallocmath.Vector2F
import com.sztorm.nonallocmath.Vector2FArray
import com.sztorm.nonallocmath.toVector2FArray
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.Test
import kotlin.test.assertEquals

class Vector2FArrayTests {
    @Test
    fun constructorThrowsWhenSizeIsNegative() {
        assertThrows<NegativeArraySizeException> { Vector2FArray(-1) }
    }

    @Test
    fun getThrowsWhenIndexIsOutOfBounds() {
        val array = Vector2FArray(4)

        assertThrows<IndexOutOfBoundsException> { array[-1] }
        assertThrows<IndexOutOfBoundsException> { array[4] }
    }

    @ParameterizedTest
    @MethodSource("isEmptyArgs")
    fun isEmptyReturnsCorrectValue(array: Array<Vector2F>, expected: Boolean) =
        assertEquals(expected, array.toVector2FArray().isEmpty())

    companion object {
        @JvmStatic
        fun arrays(): List<Arguments> = listOf(
            Arguments.of(Array(4) { Vector2F(it.toFloat(), -it.toFloat()) }),
        )

        @JvmStatic
        fun isEmptyArgs(): List<Arguments> = listOf(
            Arguments.of(Array(4) { Vector2F.ZERO }, false),
            Arguments.of(Array(0) { Vector2F.ZERO }, true),
        )
    }
}