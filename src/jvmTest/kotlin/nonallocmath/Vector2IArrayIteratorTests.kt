package nonallocmath

import com.sztorm.nonallocmath.Vector2I
import com.sztorm.nonallocmath.Vector2IArray
import com.sztorm.nonallocmath.Vector2IIterator
import nonallocmath.utils.Wrapper
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class Vector2IArrayIteratorTests {

    @ParameterizedTest
    @MethodSource("nextArgs")
    fun nextMutatesIteratorCorrectly(array: Wrapper<Vector2IArray>, expected: List<Vector2I>) {
        val iterator: Vector2IIterator = array.value.iterator()
        val actual = emptyList<Vector2I>().toMutableList()

        for (i in 0..array.value.lastIndex) {
            actual.add(iterator.next())
        }
        assertContentEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("nextVector2IArgs")
    fun nextVector2IMutatesIteratorCorrectly(
        array: Wrapper<Vector2IArray>, expected: List<Vector2I>
    ) {
        val iterator: Vector2IIterator = array.value.iterator()
        val actual = emptyList<Vector2I>().toMutableList()

        for (i in 0..array.value.lastIndex) {
            actual.add(iterator.nextVector2I())
        }
        assertContentEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("hasNextArgs")
    fun hasNextReturnsCorrectValue(array: Wrapper<Vector2IArray>, expected: List<Boolean>) {
        val iterator: Vector2IIterator = array.value.iterator()
        val actual = emptyList<Boolean>().toMutableList()

        for (i in 0..array.value.lastIndex) {
            actual.add(iterator.hasNext())
            iterator.next()
        }
        assertEquals(false, iterator.hasNext())
        assertContentEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("arrays")
    fun nextThrowsWhenDoesNotHaveNextElement(array: Wrapper<Vector2IArray>) {
        val iterator: Vector2IIterator = array.value.iterator()

        for (i in 0..array.value.lastIndex) {
            iterator.next()
        }
        assertThrows<NoSuchElementException> { iterator.next() }
    }

    @ParameterizedTest
    @MethodSource("arrays")
    fun nextThrowsWhenDoesNotHaveNextVector2I(array: Wrapper<Vector2IArray>) {
        val iterator: Vector2IIterator = array.value.iterator()

        for (i in 0..array.value.lastIndex) {
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
                Wrapper(Vector2IArray(0)),
                emptyList<Vector2I>(),
            ),
            Arguments.of(
                Wrapper(Vector2IArray(4) { Vector2I(it, 0) }),
                List(4) { Vector2I(it, 0) },
            ),
        )

        @JvmStatic
        fun nextVector2IArgs(): List<Arguments> = nextArgs()

        @JvmStatic
        fun hasNextArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2IArray(0)),
                emptyList<Boolean>(),
            ),
            Arguments.of(
                Wrapper(Vector2IArray(4) { Vector2I(it, 0) }),
                List(4) { true },
            ),
        )
    }
}