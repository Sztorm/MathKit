package nonallocmath

import com.sztorm.nonallocmath.Flags8
import nonallocmath.utils.Wrapper
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class Flags8IteratorTests {

    @ParameterizedTest
    @MethodSource("nextArgs")
    fun nextMutatesIteratorCorrectly(flags: Wrapper<Flags8>, expected: List<Boolean>) {
        val iterator: BooleanIterator = flags.value.iterator()
        val actual = emptyList<Boolean>().toMutableList()

        for (i in 0..flags.value.lastIndex) {
            actual.add(iterator.next())
        }
        assertContentEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("nextBooleanArgs")
    fun nextBooleanMutatesIteratorCorrectly(flags: Wrapper<Flags8>, expected: List<Boolean>) {
        val iterator: BooleanIterator = flags.value.iterator()
        val actual = emptyList<Boolean>().toMutableList()

        for (i in 0..flags.value.lastIndex) {
            actual.add(iterator.nextBoolean())
        }
        assertContentEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("hasNextArgs")
    fun hasNextReturnsCorrectValue(flags: Wrapper<Flags8>, expected: List<Boolean>) {
        val iterator: BooleanIterator = flags.value.iterator()
        val actual = emptyList<Boolean>().toMutableList()

        for (i in 0..flags.value.lastIndex) {
            actual.add(iterator.hasNext())
            iterator.next()
        }
        assertEquals(false, iterator.hasNext())
        assertContentEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("flags")
    fun nextThrowsWhenDoesNotHaveNextElement(flags: Wrapper<Flags8>) {
        val iterator: BooleanIterator = flags.value.iterator()

        for (i in 0..flags.value.lastIndex) {
            iterator.next()
        }
        assertThrows<NoSuchElementException> { iterator.next() }
    }

    @ParameterizedTest
    @MethodSource("flags")
    fun nextThrowsWhenDoesNotHaveNextBoolean(flags: Wrapper<Flags8>) {
        val iterator: BooleanIterator = flags.value.iterator()

        for (i in 0..flags.value.lastIndex) {
            iterator.nextBoolean()
        }
        assertThrows<NoSuchElementException> { iterator.nextBoolean() }
    }

    companion object {
        @JvmStatic
        fun flags(): List<Arguments> = Flags8Tests.flags()

        @JvmStatic
        fun nextArgs(): List<Arguments> = Flags8Tests.getArgs()

        @JvmStatic
        fun nextBooleanArgs(): List<Arguments> = nextArgs()

        @JvmStatic
        fun hasNextArgs(): List<Arguments> = listOf(
            Arguments.of(Wrapper(Flags8.fromUByte(0b11100001u)), List(Flags8.SIZE_BITS) { true }),
            Arguments.of(Wrapper(Flags8.fromUByte(0b10110111u)), List(Flags8.SIZE_BITS) { true }),
        )
    }
}