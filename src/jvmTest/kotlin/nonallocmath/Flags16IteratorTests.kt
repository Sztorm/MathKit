package nonallocmath

import com.sztorm.nonallocmath.Flags16
import nonallocmath.utils.Wrapper
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class Flags16IteratorTests {

    @ParameterizedTest
    @MethodSource("nextArgs")
    fun nextMutatesIteratorCorrectly(flags: Wrapper<Flags16>, expected: List<Boolean>) {
        val iterator: BooleanIterator = flags.value.iterator()
        val actual = emptyList<Boolean>().toMutableList()

        for (i in 0..flags.value.lastIndex) {
            actual.add(iterator.next())
        }
        assertContentEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("nextBooleanArgs")
    fun nextBooleanMutatesIteratorCorrectly(flags: Wrapper<Flags16>, expected: List<Boolean>) {
        val iterator: BooleanIterator = flags.value.iterator()
        val actual = emptyList<Boolean>().toMutableList()

        for (i in 0..flags.value.lastIndex) {
            actual.add(iterator.nextBoolean())
        }
        assertContentEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("hasNextArgs")
    fun hasNextReturnsCorrectValue(flags: Wrapper<Flags16>, expected: List<Boolean>) {
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
    fun nextThrowsWhenDoesNotHaveNextElement(flags: Wrapper<Flags16>) {
        val iterator: BooleanIterator = flags.value.iterator()

        for (i in 0..flags.value.lastIndex) {
            iterator.next()
        }
        assertThrows<NoSuchElementException> { iterator.next() }
    }

    @ParameterizedTest
    @MethodSource("flags")
    fun nextThrowsWhenDoesNotHaveNextBoolean(flags: Wrapper<Flags16>) {
        val iterator: BooleanIterator = flags.value.iterator()

        for (i in 0..flags.value.lastIndex) {
            iterator.nextBoolean()
        }
        assertThrows<NoSuchElementException> { iterator.nextBoolean() }
    }

    companion object {
        @JvmStatic
        fun flags(): List<Arguments> = Flags16Tests.flags()

        @JvmStatic
        fun nextArgs(): List<Arguments> = Flags16Tests.getArgs()

        @JvmStatic
        fun nextBooleanArgs(): List<Arguments> = nextArgs()

        @JvmStatic
        fun hasNextArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Flags16.fromUShort(0b11100000_00001101u)),
                List(Flags16.SIZE_BITS) { true }
            ),
            Arguments.of(
                Wrapper(Flags16.fromUShort(0b11110111_11111101u)),
                List(Flags16.SIZE_BITS) { true }
            ),
        )
    }
}