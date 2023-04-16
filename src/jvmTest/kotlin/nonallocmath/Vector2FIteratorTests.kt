package nonallocmath

import com.sztorm.nonallocmath.Vector2F
import com.sztorm.nonallocmath.toVector2FArray
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class Vector2FIteratorTests {

    @ParameterizedTest
    @MethodSource("nextArgs")
    fun nextMutatesIteratorCorrectly(array: Array<Vector2F>, expected: List<Vector2F>) {
        val iterator = array.toVector2FArray().iterator()
        val actual = emptyList<Vector2F>().toMutableList()

        for (i in 0..array.lastIndex) {
            actual.add(iterator.next())
        }
        assertContentEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("nextVector2FArgs")
    fun nextVector2FMutatesIteratorCorrectly(array: Array<Vector2F>, expected: List<Vector2F>) {
        val iterator = array.toVector2FArray().iterator()
        val actual = emptyList<Vector2F>().toMutableList()

        for (i in 0..array.lastIndex) {
            actual.add(iterator.nextVector2F())
        }
        assertContentEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("hasNextArgs")
    fun hasNextReturnsCorrectValue(array: Array<Vector2F>, expected: List<Boolean>) {
        val iterator = array.toVector2FArray().iterator()
        val actual = emptyList<Boolean>().toMutableList()

        for (i in 0..array.lastIndex) {
            actual.add(iterator.hasNext())
            iterator.next()
        }
        assertEquals(false, iterator.hasNext())
        assertContentEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("arrays")
    fun nextThrowsWhenDoesNotHaveNextElement(array: Array<Vector2F>) {
        val iterator = array.toVector2FArray().iterator()

        for (i in 0..array.lastIndex) {
            iterator.next()
        }
        assertThrows<NoSuchElementException> { iterator.next() }
    }

    @ParameterizedTest
    @MethodSource("arrays")
    fun nextThrowsWhenDoesNotHaveNextVector2F(array: Array<Vector2F>) {
        val iterator = array.toVector2FArray().iterator()

        for (i in 0..array.lastIndex) {
            iterator.nextVector2F()
        }
        assertThrows<NoSuchElementException> { iterator.nextVector2F() }
    }

    companion object {
        @JvmStatic
        fun arrays(): List<Arguments> = Vector2FArrayTests.arrays()

        @JvmStatic
        fun nextArgs(): List<Arguments> = listOf(
            Arguments.of(
                emptyArray<Vector2F>(),
                emptyList<Vector2F>(),
            ),
            Arguments.of(
                Array(4) { Vector2F(it.toFloat(), 0f) },
                List(4) { Vector2F(it.toFloat(), 0f) },
            ),
        )

        @JvmStatic
        fun nextVector2FArgs(): List<Arguments> = nextArgs()

        @JvmStatic
        fun hasNextArgs(): List<Arguments> = listOf(
            Arguments.of(
                emptyArray<Vector2F>(),
                emptyList<Boolean>(),
            ),
            Arguments.of(
                Array(4) { Vector2F(it.toFloat(), 0f) },
                List(4) { true },
            ),
        )
    }
}