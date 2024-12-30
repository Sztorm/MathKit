package com.sztorm.mathkit

import com.sztorm.mathkit.utils.Wrapper
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class Flags64IteratorTests {
    @ParameterizedTest
    @MethodSource("nextArgs")
    fun nextMutatesIteratorCorrectly(flags: Wrapper<Flags64>, expected: List<Boolean>) {
        val iterator: BooleanIterator = flags.value.iterator()
        val actual = emptyList<Boolean>().toMutableList()

        for (i in 0..flags.value.lastIndex) {
            actual.add(iterator.next())
        }
        assertContentEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("nextBooleanArgs")
    fun nextBooleanMutatesIteratorCorrectly(flags: Wrapper<Flags64>, expected: List<Boolean>) {
        val iterator: BooleanIterator = flags.value.iterator()
        val actual = emptyList<Boolean>().toMutableList()

        for (i in 0..flags.value.lastIndex) {
            actual.add(iterator.nextBoolean())
        }
        assertContentEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("hasNextArgs")
    fun hasNextReturnsCorrectValue(flags: Wrapper<Flags64>, expected: List<Boolean>) {
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
    fun nextThrowsWhenDoesNotHaveNextElement(flags: Wrapper<Flags64>) {
        val iterator: BooleanIterator = flags.value.iterator()

        for (i in 0..flags.value.lastIndex) {
            iterator.next()
        }
        assertThrows<NoSuchElementException> { iterator.next() }
    }

    @ParameterizedTest
    @MethodSource("flags")
    fun nextThrowsWhenDoesNotHaveNextBoolean(flags: Wrapper<Flags64>) {
        val iterator: BooleanIterator = flags.value.iterator()

        for (i in 0..flags.value.lastIndex) {
            iterator.nextBoolean()
        }
        assertThrows<NoSuchElementException> { iterator.nextBoolean() }
    }

    companion object {
        @JvmStatic
        fun flags(): List<Arguments> = Flags64Tests.flags()

        @JvmStatic
        fun nextArgs(): List<Arguments> = Flags64Tests.getArgs()

        @JvmStatic
        fun nextBooleanArgs(): List<Arguments> = nextArgs()

        @JvmStatic
        fun hasNextArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(
                    Flags64.fromULong(
                        0b11110001_01100110_10101001_11100111_11100000_00001101_00000100_01000001uL
                    )
                ),
                List(Flags64.SIZE_BITS) { true }
            ),
            Arguments.of(
                Wrapper(
                    Flags64.fromULong(
                        0b10100010_01010011_01110011_00111110_11110111_11111101_01111111_01111101uL
                    )
                ),
                List(Flags64.SIZE_BITS) { true }
            ),
        )
    }
}