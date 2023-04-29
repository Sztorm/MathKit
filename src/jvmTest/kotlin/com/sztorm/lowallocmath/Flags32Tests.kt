package com.sztorm.lowallocmath

import com.sztorm.lowallocmath.utils.Wrapper
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class Flags32Tests {

    @ParameterizedTest
    @MethodSource("flags")
    fun basicPropertiesAreValid(flags: Wrapper<Flags32>) {
        assertEquals(32, flags.value.size)
        assertEquals(31, flags.value.lastIndex)
        assertEquals(false, flags.value.isEmpty())
    }

    @ParameterizedTest
    @MethodSource("containsAllArgs")
    fun containsAllReturnsCorrectValue(
        flags: Wrapper<Flags32>, elements: Collection<Boolean>, expected: Boolean
    ) = assertEquals(expected, flags.value.containsAll(elements))

    @ParameterizedTest
    @MethodSource("addingArgs")
    fun addingReturnsCorrectValue(
        a: Wrapper<Flags32>, b: Wrapper<Flags32>, expected: Wrapper<Flags32>
    ) = assertEquals(expected.value, a.value adding b.value)

    @ParameterizedTest
    @MethodSource("removingArgs")
    fun removingReturnsCorrectValue(
        a: Wrapper<Flags32>, b: Wrapper<Flags32>, expected: Wrapper<Flags32>
    ) = assertEquals(expected.value, a.value removing b.value)

    @ParameterizedTest
    @MethodSource("togglingArgs")
    fun togglingReturnsCorrectValue(
        a: Wrapper<Flags32>, b: Wrapper<Flags32>, expected: Wrapper<Flags32>
    ) = assertEquals(expected.value, a.value toggling b.value)

    @ParameterizedTest
    @MethodSource("settingArgs")
    fun settingReturnsCorrectValue(
        a: Wrapper<Flags32>, setFlags: Wrapper<Flags32>, toValue: Boolean, expected: Wrapper<Flags32>
    ) = assertEquals(expected.value, a.value.setting(setFlags.value, toValue))

    @ParameterizedTest
    @MethodSource("hasAllArgs")
    fun hasAllReturnsCorrectValue(a: Wrapper<Flags32>, b: Wrapper<Flags32>, expected: Boolean) =
        assertEquals(expected, a.value.hasAll(b.value))

    @ParameterizedTest
    @MethodSource("hasAnyArgs")
    fun hasAnyReturnsCorrectValue(a: Wrapper<Flags32>, b: Wrapper<Flags32>, expected: Boolean) =
        assertEquals(expected, a.value.hasAny(b.value))

    @ParameterizedTest
    @MethodSource("toStringArgs")
    fun toStringReturnsCorrectValue(flags: Wrapper<Flags32>, expected: String) =
        assertEquals(expected, flags.value.toString())

    @ParameterizedTest
    @MethodSource("containsArgs")
    fun containsReturnsCorrectValue(flags: Wrapper<Flags32>, element: Boolean, expected: Boolean) =
        assertEquals(expected, flags.value.contains(element))

    @ParameterizedTest
    @MethodSource("getArgs")
    fun getReturnsCorrectValue(flags: Wrapper<Flags32>, expected: Collection<Boolean>) {
        val actual = emptyList<Boolean>().toMutableList()

        for (i in 0..flags.value.lastIndex) {
            actual.add(flags.value[i])
        }
        assertContentEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("iteratorArgs")
    fun iteratorReturnsCorrectValue(flags: Wrapper<Flags32>, expected: Iterator<Boolean>) {
        val actual = flags.value.iterator()

        for (i in 0..flags.value.lastIndex) {
            assertEquals(expected.hasNext(), actual.hasNext())

            if (expected.hasNext() && actual.hasNext()) {
                assertEquals(expected.next(), actual.next())
            }
        }
        assertEquals(expected.hasNext(), actual.hasNext())
    }

    @Test
    fun noneValueReturnsCorrectValue() {
        val none = Flags32.NONE

        for (i in 0..none.lastIndex) {
            assertFalse(none[i])
        }
    }

    @Test
    fun allValueReturnsCorrectValue() {
        val all = Flags32.ALL

        for (i in 0..all.lastIndex) {
            assertTrue(all[i])
        }
    }

    companion object {
        @JvmStatic
        fun flags(): List<Arguments> = listOf(
            Arguments.of(Wrapper(Flags32.fromUInt(0b11100000_00001101_00000100_01000001u))),
            Arguments.of(Wrapper(Flags32.fromUInt(0b11110111_11111101_01111111_01111101u))),
            Arguments.of(Wrapper(Flags32.fromUInt(0b00000000_00000000_00000000_00000000u))),
            Arguments.of(Wrapper(Flags32.fromUInt(0b11111111_11111111_11111111_11111111u))),
        )

        @JvmStatic
        fun containsAllArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Flags32.fromUInt(0b11100000_00001101_00000100_01000001u)),
                emptyList<Boolean>(),
                true
            ),
            Arguments.of(
                Wrapper(Flags32.fromUInt(0b11100000_00001101_00000100_01000001u)),
                listOf(true, false),
                true
            ),
            Arguments.of(
                Wrapper(Flags32.fromUInt(0b11100000_00001101_00000100_01000001u)),
                listOf(false, false),
                true
            ),
            Arguments.of(
                Wrapper(Flags32.fromUInt(0b11100000_00001101_00000100_01000001u)),
                listOf(true, true),
                true
            ),
            Arguments.of(
                Wrapper(Flags32.fromUInt(0b00000000_00000000_00000000_00000000u)),
                listOf(true, false),
                false
            ),
            Arguments.of(
                Wrapper(Flags32.fromUInt(0b00000000_00000000_00000000_00000000u)),
                listOf(false, false),
                true
            ),
            Arguments.of(
                Wrapper(Flags32.fromUInt(0b00000000_00000000_00000000_00000000u)),
                listOf(true, true),
                false
            ),
            Arguments.of(
                Wrapper(Flags32.fromUInt(0b11111111_11111111_11111111_11111111u)),
                listOf(true, false),
                false
            ),
            Arguments.of(
                Wrapper(Flags32.fromUInt(0b11111111_11111111_11111111_11111111u)),
                listOf(false, false),
                false
            ),
            Arguments.of(
                Wrapper(Flags32.fromUInt(0b11111111_11111111_11111111_11111111u)),
                listOf(true, true),
                true
            ),
        )

        @JvmStatic
        fun addingArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Flags32.fromUInt(0b00000000_00000000_00000000_00000000u)),
                Wrapper(Flags32.fromUInt(0b11100000_00001101_00000100_01000001u)),
                Wrapper(Flags32.fromUInt(0b11100000_00001101_00000100_01000001u))
            ),
            Arguments.of(
                Wrapper(Flags32.fromUInt(0b11100000_00001101_00000100_01000001u)),
                Wrapper(Flags32.fromUInt(0b00000000_00000000_00000000_00000000u)),
                Wrapper(Flags32.fromUInt(0b11100000_00001101_00000100_01000001u))
            ),
            Arguments.of(
                Wrapper(Flags32.fromUInt(0b11100000_00001101_00000100_01000001u)),
                Wrapper(Flags32.fromUInt(0b11100000_00001101_00000100_01000001u)),
                Wrapper(Flags32.fromUInt(0b11100000_00001101_00000100_01000001u))
            ),
            Arguments.of(
                Wrapper(Flags32.fromUInt(0b11100000_00001101_00000100_01000001u)),
                Wrapper(Flags32.fromUInt(0b11110111_11111101_01111011_01111100u)),
                Wrapper(Flags32.fromUInt(0b11110111_11111101_01111111_01111101u))
            ),
        )

        @JvmStatic
        fun removingArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Flags32.fromUInt(0b00000000_00000000_00000000_00000000u)),
                Wrapper(Flags32.fromUInt(0b11100000_00001101_00000100_01000001u)),
                Wrapper(Flags32.fromUInt(0b00000000_00000000_00000000_00000000u))
            ),
            Arguments.of(
                Wrapper(Flags32.fromUInt(0b11100000_00001101_00000100_01000001u)),
                Wrapper(Flags32.fromUInt(0b00000000_00000000_00000000_00000000u)),
                Wrapper(Flags32.fromUInt(0b11100000_00001101_00000100_01000001u))
            ),
            Arguments.of(
                Wrapper(Flags32.fromUInt(0b11100000_00001101_00000100_01000001u)),
                Wrapper(Flags32.fromUInt(0b11100000_00001101_00000100_01000001u)),
                Wrapper(Flags32.fromUInt(0b00000000_00000000_00000000_00000000u))
            ),
            Arguments.of(
                Wrapper(Flags32.fromUInt(0b11110111_11111101_01111111_01111101u)),
                Wrapper(Flags32.fromUInt(0b11100000_00001101_00000100_01000001u)),
                Wrapper(Flags32.fromUInt(0b00010111_11110000_01111011_00111100u))
            ),
        )

        @JvmStatic
        fun togglingArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Flags32.fromUInt(0b00000000_00000000_00000000_00000000u)),
                Wrapper(Flags32.fromUInt(0b11100000_00001101_00000100_01000001u)),
                Wrapper(Flags32.fromUInt(0b11100000_00001101_00000100_01000001u))
            ),
            Arguments.of(
                Wrapper(Flags32.fromUInt(0b11100000_00001101_00000100_01000001u)),
                Wrapper(Flags32.fromUInt(0b00000000_00000000_00000000_00000000u)),
                Wrapper(Flags32.fromUInt(0b11100000_00001101_00000100_01000001u))
            ),
            Arguments.of(
                Wrapper(Flags32.fromUInt(0b11100000_00001101_00000100_01000001u)),
                Wrapper(Flags32.fromUInt(0b11100000_00001101_00000100_01000001u)),
                Wrapper(Flags32.fromUInt(0b00000000_00000000_00000000_00000000u))
            ),
            Arguments.of(
                Wrapper(Flags32.fromUInt(0b10110111_10001101_01000111_00111001u)),
                Wrapper(Flags32.fromUInt(0b11101100_01101101_00101100_01110001u)),
                Wrapper(Flags32.fromUInt(0b01011011_11100000_01101011_01001000u))
            ),
        )

        @JvmStatic
        fun settingArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Flags32.fromUInt(0b00000000_00000000_00000000_00000000u)),
                Wrapper(Flags32.fromUInt(0b11100000_00001101_00000100_01000001u)),
                true,
                Wrapper(Flags32.fromUInt(0b11100000_00001101_00000100_01000001u))
            ),
            Arguments.of(
                Wrapper(Flags32.fromUInt(0b11100000_00001101_00000100_01000001u)),
                Wrapper(Flags32.fromUInt(0b11100000_00001101_00000100_01000001u)),
                false,
                Wrapper(Flags32.fromUInt(0b00000000_00000000_00000000_00000000u))
            ),
            Arguments.of(
                Wrapper(Flags32.fromUInt(0b11100000_00001101_00000100_01000001u)),
                Wrapper(Flags32.fromUInt(0b00000000_00000000_00000000_00000000u)),
                true,
                Wrapper(Flags32.fromUInt(0b11100000_00001101_00000100_01000001u))
            ),
            Arguments.of(
                Wrapper(Flags32.fromUInt(0b11100000_00001101_00000100_01000001u)),
                Wrapper(Flags32.fromUInt(0b00000000_00000000_00000000_00000000u)),
                false,
                Wrapper(Flags32.fromUInt(0b11100000_00001101_00000100_01000001u))
            ),
            Arguments.of(
                Wrapper(Flags32.fromUInt(0b10110111_10001101_01000111_00111001u)),
                Wrapper(Flags32.fromUInt(0b11101100_01101101_00101100_01110001u)),
                true,
                Wrapper(Flags32.fromUInt(0b11111111_11101101_01101111_01111001u))
            ),
            Arguments.of(
                Wrapper(Flags32.fromUInt(0b10110111_10001101_01000111_00111001u)),
                Wrapper(Flags32.fromUInt(0b11101100_01101101_00101100_01110001u)),
                false,
                Wrapper(Flags32.fromUInt(0b00010011_10000000_01000011_00001000u))
            )
        )

        @JvmStatic
        fun hasAllArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Flags32.fromUInt(0b11100000_00001101_00000100_01000001u)),
                Wrapper(Flags32.fromUInt(0b00000000_00000000_00000000_00000000u)),
                true,
            ),
            Arguments.of(
                Wrapper(Flags32.fromUInt(0b00000000_00000000_00000000_00000000u)),
                Wrapper(Flags32.fromUInt(0b11100000_00001101_00000100_01000001u)),
                false,
            ),
            Arguments.of(
                Wrapper(Flags32.fromUInt(0b11100000_00001101_00000100_01000001u)),
                Wrapper(Flags32.fromUInt(0b11100000_00001101_00000100_01000001u)),
                true,
            ),
            Arguments.of(
                Wrapper(Flags32.fromUInt(0b11111000_01101111_01110100_01000111u)),
                Wrapper(Flags32.fromUInt(0b11100000_00001101_00000100_01000001u)),
                true,
            ),
            Arguments.of(
                Wrapper(Flags32.fromUInt(0b11011000_01101111_01110100_01000111u)),
                Wrapper(Flags32.fromUInt(0b11100000_00001101_00000100_01000001u)),
                false,
            )
        )

        @JvmStatic
        fun hasAnyArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Flags32.fromUInt(0b11100000_00001101_00000100_01000001u)),
                Wrapper(Flags32.fromUInt(0b00000000_00000000_00000000_00000000u)),
                false,
            ),
            Arguments.of(
                Wrapper(Flags32.fromUInt(0b00000000_00000000_00000000_00000000u)),
                Wrapper(Flags32.fromUInt(0b11100000_00001101_00000100_01000001u)),
                false,
            ),
            Arguments.of(
                Wrapper(Flags32.fromUInt(0b00000000_00000000_00000000_00000001u)),
                Wrapper(Flags32.fromUInt(0b11100000_00001101_00000100_01000001u)),
                true,
            ),
            Arguments.of(
                Wrapper(Flags32.fromUInt(0b11111000_01101111_01110100_01000111u)),
                Wrapper(Flags32.fromUInt(0b11100000_00001101_00000100_01000001u)),
                true,
            ),
            Arguments.of(
                Wrapper(Flags32.fromUInt(0b00011000_01100010_01110000_00000110u)),
                Wrapper(Flags32.fromUInt(0b11100000_00001101_00000100_01000001u)),
                false,
            )
        )

        @JvmStatic
        fun toStringArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Flags32.fromUInt(0b11100000_00001101_00000100_01000001u)),
                "Flags32(10000010 00100000 10110000 00000111)"
            ),
            Arguments.of(
                Wrapper(Flags32.fromUInt(0b11110111_11111101_01111111_01111101u)),
                "Flags32(10111110 11111110 10111111 11101111)"
            ),
            Arguments.of(
                Wrapper(Flags32.fromUInt(0b00000000_00000000_00000000_00000000u)),
                "Flags32(00000000 00000000 00000000 00000000)"
            ),
            Arguments.of(
                Wrapper(Flags32.fromUInt(0b11111111_11111111_11111111_11111111u)),
                "Flags32(11111111 11111111 11111111 11111111)"
            ),
        )

        @JvmStatic
        fun containsArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Flags32.fromUInt(0b11100000_00001101_00000100_01000001u)), true, true
            ),
            Arguments.of(
                Wrapper(Flags32.fromUInt(0b11100000_00001101_00000100_01000001u)), false, true
            ),
            Arguments.of(
                Wrapper(Flags32.fromUInt(0b00000000_00000000_00000000_00000000u)), true, false
            ),
            Arguments.of(
                Wrapper(Flags32.fromUInt(0b00000000_00000000_00000000_00000000u)), false, true
            ),
            Arguments.of(
                Wrapper(Flags32.fromUInt(0b11111111_11111111_11111111_11111111u)), true, true
            ),
            Arguments.of(
                Wrapper(Flags32.fromUInt(0b11111111_11111111_11111111_11111111u)), false, false
            ),
        )

        @JvmStatic
        fun getArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Flags32.fromUInt(0b11100000_00001101_00000100_01000001u)),
                listOf(
                    true, true, true, false, false, false, false, false,
                    false, false, false, false, true, true, false, true,
                    false, false, false, false, false, true, false, false,
                    false, true, false, false, false, false, false, true,
                ).asReversed()
            ),
            Arguments.of(
                Wrapper(Flags32.fromUInt(0b11110111_11111101_01111111_01111101u)),
                listOf(
                    true, true, true, true, false, true, true, true,
                    true, true, true, true, true, true, false, true,
                    false, true, true, true, true, true, true, true,
                    false, true, true, true, true, true, false, true,
                ).asReversed()
            ),
            Arguments.of(
                Wrapper(Flags32.fromUInt(0b00000000_00000000_00000000_00000000u)),
                listOf(
                    false, false, false, false, false, false, false, false,
                    false, false, false, false, false, false, false, false,
                    false, false, false, false, false, false, false, false,
                    false, false, false, false, false, false, false, false,
                )
            ),
            Arguments.of(
                Wrapper(Flags32.fromUInt(0b11111111_11111111_11111111_11111111u)),
                listOf(
                    true, true, true, true, true, true, true, true,
                    true, true, true, true, true, true, true, true,
                    true, true, true, true, true, true, true, true,
                    true, true, true, true, true, true, true, true,
                )
            ),
        )

        @JvmStatic
        fun iteratorArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Flags32.fromUInt(0b11100000_00001101_00000100_01000001u)),
                listOf(
                    true, true, true, false, false, false, false, false,
                    false, false, false, false, true, true, false, true,
                    false, false, false, false, false, true, false, false,
                    false, true, false, false, false, false, false, true,
                ).asReversed().iterator()
            ),
            Arguments.of(
                Wrapper(Flags32.fromUInt(0b11110111_11111101_01111111_01111101u)),
                listOf(
                    true, true, true, true, false, true, true, true,
                    true, true, true, true, true, true, false, true,
                    false, true, true, true, true, true, true, true,
                    false, true, true, true, true, true, false, true,
                ).asReversed().iterator()
            ),
            Arguments.of(
                Wrapper(Flags32.fromUInt(0b00000000_00000000_00000000_00000000u)),
                listOf(
                    false, false, false, false, false, false, false, false,
                    false, false, false, false, false, false, false, false,
                    false, false, false, false, false, false, false, false,
                    false, false, false, false, false, false, false, false,
                ).iterator()
            ),
            Arguments.of(
                Wrapper(Flags32.fromUInt(0b11111111_11111111_11111111_11111111u)),
                listOf(
                    true, true, true, true, true, true, true, true,
                    true, true, true, true, true, true, true, true,
                    true, true, true, true, true, true, true, true,
                    true, true, true, true, true, true, true, true,
                ).iterator()
            ),
        )
    }
}