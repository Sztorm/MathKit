package nonallocmath

import com.sztorm.nonallocmath.Vector2I
import com.sztorm.nonallocmath.toVector2IArray
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class Vector2IIteratorTests {

    @ParameterizedTest
    @MethodSource("nextArgs")
    fun nextMutatesIteratorCorrectly(array: Array<Vector2I>, expected: List<Vector2I>) {
        val iterator = array.toVector2IArray().iterator()
        val actual = emptyList<Vector2I>().toMutableList()

        for (i in 0..array.lastIndex) {
            actual.add(iterator.next())
        }
        assertContentEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("nextVector2IArgs")
    fun nextVector2IMutatesIteratorCorrectly(array: Array<Vector2I>, expected: List<Vector2I>) {
        val iterator = array.toVector2IArray().iterator()
        val actual = emptyList<Vector2I>().toMutableList()

        for (i in 0..array.lastIndex) {
            actual.add(iterator.nextVector2I())
        }
        assertContentEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("hasNextArgs")
    fun hasNextReturnsCorrectValue(array: Array<Vector2I>, expected: List<Boolean>) {
        val iterator = array.toVector2IArray().iterator()
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
    fun nextThrowsWhenDoesNotHaveNextElement(array: Array<Vector2I>) {
        val iterator = array.toVector2IArray().iterator()

        for (i in 0..array.lastIndex) {
            iterator.next()
        }
        assertThrows<NoSuchElementException> { iterator.next() }
    }

    @ParameterizedTest
    @MethodSource("arrays")
    fun nextThrowsWhenDoesNotHaveNextVector2I(array: Array<Vector2I>) {
        val iterator = array.toVector2IArray().iterator()

        for (i in 0..array.lastIndex) {
            iterator.nextVector2I()
        }
        assertThrows<NoSuchElementException> { iterator.nextVector2I() }
    }

    companion object {
        @JvmStatic
        fun arrays(): List<Arguments> = Vector2IArrayTests.arrays()

        @JvmStatic
        fun nextArgs(): List<Arguments> = listOf(
            Arguments.of(
                emptyArray<Vector2I>(),
                emptyList<Vector2I>(),
            ),
            Arguments.of(
                Array(4) { Vector2I(it, 0) },
                List(4) { Vector2I(it, 0) },
            ),
        )

        @JvmStatic
        fun nextVector2IArgs(): List<Arguments> = nextArgs()

        @JvmStatic
        fun hasNextArgs(): List<Arguments> = listOf(
            Arguments.of(
                emptyArray<Vector2I>(),
                emptyList<Boolean>(),
            ),
            Arguments.of(
                Array(4) { Vector2I(it, 0) },
                List(4) { true },
            ),
        )
    }
}