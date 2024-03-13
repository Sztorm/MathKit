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

class Flags8Tests {
    @ParameterizedTest
    @MethodSource("uByteValueArgs")
    fun uByteValueReturnsCorrectValue(flags: Wrapper<Flags8>, expected: Wrapper<UByte>) =
        assertEquals(expected.value, flags.value.uByteValue)

    @ParameterizedTest
    @MethodSource("sizeArgs")
    fun sizeReturnsCorrectValue(flags: Wrapper<Flags8>) =
        assertEquals(8, flags.value.size)

    @ParameterizedTest
    @MethodSource("lastIndexArgs")
    fun lastIndexReturnsCorrectValue(flags: Wrapper<Flags8>) =
        assertEquals(7, flags.value.lastIndex)

    @ParameterizedTest
    @MethodSource("isEmptyArgs")
    fun isEmptyReturnsCorrectValue(flags: Wrapper<Flags8>) =
        assertEquals(false, flags.value.isEmpty())

    @ParameterizedTest
    @MethodSource("containsAllArgs")
    fun containsAllReturnsCorrectValue(
        flags: Wrapper<Flags8>, elements: Collection<Boolean>, expected: Boolean
    ) = assertEquals(expected, flags.value.containsAll(elements))

    @ParameterizedTest
    @MethodSource("addingArgs")
    fun addingReturnsCorrectValue(
        a: Wrapper<Flags8>, b: Wrapper<Flags8>, expected: Wrapper<Flags8>
    ) = assertEquals(expected.value, a.value adding b.value)

    @ParameterizedTest
    @MethodSource("removingArgs")
    fun removingReturnsCorrectValue(
        a: Wrapper<Flags8>, b: Wrapper<Flags8>, expected: Wrapper<Flags8>
    ) = assertEquals(expected.value, a.value removing b.value)

    @ParameterizedTest
    @MethodSource("togglingArgs")
    fun togglingReturnsCorrectValue(
        a: Wrapper<Flags8>, b: Wrapper<Flags8>, expected: Wrapper<Flags8>
    ) = assertEquals(expected.value, a.value toggling b.value)

    @ParameterizedTest
    @MethodSource("settingArgs")
    fun settingReturnsCorrectValue(
        a: Wrapper<Flags8>, setFlags: Wrapper<Flags8>, toValue: Boolean, expected: Wrapper<Flags8>
    ) = assertEquals(expected.value, a.value.setting(setFlags.value, toValue))

    @ParameterizedTest
    @MethodSource("hasAllArgs")
    fun hasAllReturnsCorrectValue(a: Wrapper<Flags8>, b: Wrapper<Flags8>, expected: Boolean) =
        assertEquals(expected, a.value.hasAll(b.value))

    @ParameterizedTest
    @MethodSource("hasAnyArgs")
    fun hasAnyReturnsCorrectValue(a: Wrapper<Flags8>, b: Wrapper<Flags8>, expected: Boolean) =
        assertEquals(expected, a.value.hasAny(b.value))

    @ParameterizedTest
    @MethodSource("toStringArgs")
    fun toStringReturnsCorrectValue(flags: Wrapper<Flags8>, expected: String) =
        assertEquals(expected, flags.value.toString())

    @ParameterizedTest
    @MethodSource("containsArgs")
    fun containsReturnsCorrectValue(flags: Wrapper<Flags8>, element: Boolean, expected: Boolean) =
        assertEquals(expected, flags.value.contains(element))

    @ParameterizedTest
    @MethodSource("getArgs")
    fun getReturnsCorrectValue(flags: Wrapper<Flags8>, expected: Collection<Boolean>) {
        val actual = emptyList<Boolean>().toMutableList()

        for (i in 0..flags.value.lastIndex) {
            actual.add(flags.value[i])
        }
        assertContentEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("iteratorArgs")
    fun iteratorReturnsCorrectValue(flags: Wrapper<Flags8>, expected: Iterator<Boolean>) {
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
    fun sizeBitsReturnsCorrectValue() = assertEquals(Flags8.SIZE_BITS, 8)

    @Test
    fun sizeBytesReturnsCorrectValue() = assertEquals(Flags8.SIZE_BYTES, 1)

    @Test
    fun noneValueReturnsCorrectValue() {
        val none = Flags8.NONE

        for (i in 0..none.lastIndex) {
            assertFalse(none[i])
        }
    }

    @Test
    fun allValueReturnsCorrectValue() {
        val all = Flags8.ALL

        for (i in 0..all.lastIndex) {
            assertTrue(all[i])
        }
    }

    @ParameterizedTest
    @MethodSource("fromUByteArgs")
    fun fromUByteReturnsCorrectValue(uByteValue: Wrapper<UByte>, expected: Wrapper<Flags8>) =
        assertEquals(expected.value, Flags8.fromUByte(uByteValue.value))

    @ParameterizedTest
    @MethodSource("fromByteArgs")
    fun fromByteReturnsCorrectValue(byteValue: Byte, expected: Wrapper<Flags8>) =
        assertEquals(expected.value, Flags8.fromByte(byteValue))

    companion object {
        @JvmStatic
        fun flags(): List<Arguments> = listOf(
            Arguments.of(Wrapper(Flags8.fromUByte(0b11100001u))),
            Arguments.of(Wrapper(Flags8.fromUByte(0b10110111u))),
            Arguments.of(Wrapper(Flags8.fromUByte(0b00000000u))),
            Arguments.of(Wrapper(Flags8.fromUByte(0b11111111u))),
        )

        @JvmStatic
        fun uByteValueArgs(): List<Arguments> = fromByteArgs().map {
            val args: Array<Any> = it.get()
            val byteValue = args[0] as Byte

            Arguments.of(Wrapper(Flags8(byteValue)), Wrapper(byteValue.toUByte()))
        }

        @JvmStatic
        fun sizeArgs(): List<Arguments> = flags()

        @JvmStatic
        fun lastIndexArgs(): List<Arguments> = flags()

        @JvmStatic
        fun isEmptyArgs(): List<Arguments> = flags()

        @JvmStatic
        fun containsAllArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Flags8.fromUByte(0b11100001u)), emptyList<Boolean>(), true
            ),
            Arguments.of(
                Wrapper(Flags8.fromUByte(0b11100001u)), listOf(true, false), true
            ),
            Arguments.of(
                Wrapper(Flags8.fromUByte(0b11100001u)), listOf(false, false), true
            ),
            Arguments.of(
                Wrapper(Flags8.fromUByte(0b11100001u)), listOf(true, true), true
            ),
            Arguments.of(
                Wrapper(Flags8.fromUByte(0b00000000u)), listOf(true, false), false
            ),
            Arguments.of(
                Wrapper(Flags8.fromUByte(0b00000000u)), listOf(false, false), true
            ),
            Arguments.of(
                Wrapper(Flags8.fromUByte(0b00000000u)), listOf(true, true), false
            ),
            Arguments.of(
                Wrapper(Flags8.fromUByte(0b11111111u)), listOf(true, false), false
            ),
            Arguments.of(
                Wrapper(Flags8.fromUByte(0b11111111u)), listOf(false, false), false
            ),
            Arguments.of(
                Wrapper(Flags8.fromUByte(0b11111111u)), listOf(true, true), true
            ),
        )

        @JvmStatic
        fun addingArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Flags8.fromUByte(0b00000000u)),
                Wrapper(Flags8.fromUByte(0b11100001u)),
                Wrapper(Flags8.fromUByte(0b11100001u))
            ),
            Arguments.of(
                Wrapper(Flags8.fromUByte(0b11100001u)),
                Wrapper(Flags8.fromUByte(0b00000000u)),
                Wrapper(Flags8.fromUByte(0b11100001u))
            ),
            Arguments.of(
                Wrapper(Flags8.fromUByte(0b11100001u)),
                Wrapper(Flags8.fromUByte(0b11100001u)),
                Wrapper(Flags8.fromUByte(0b11100001u))
            ),
            Arguments.of(
                Wrapper(Flags8.fromUByte(0b10100001u)),
                Wrapper(Flags8.fromUByte(0b10110111u)),
                Wrapper(Flags8.fromUByte(0b10110111u))
            ),
        )

        @JvmStatic
        fun removingArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Flags8.fromUByte(0b00000000u)),
                Wrapper(Flags8.fromUByte(0b11100001u)),
                Wrapper(Flags8.fromUByte(0b00000000u))
            ),
            Arguments.of(
                Wrapper(Flags8.fromUByte(0b11100001u)),
                Wrapper(Flags8.fromUByte(0b00000000u)),
                Wrapper(Flags8.fromUByte(0b11100001u))
            ),
            Arguments.of(
                Wrapper(Flags8.fromUByte(0b11100001u)),
                Wrapper(Flags8.fromUByte(0b11100001u)),
                Wrapper(Flags8.fromUByte(0b00000000u))
            ),
            Arguments.of(
                Wrapper(Flags8.fromUByte(0b11110111u)),
                Wrapper(Flags8.fromUByte(0b11100001u)),
                Wrapper(Flags8.fromUByte(0b00010110u))
            ),
        )

        @JvmStatic
        fun togglingArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Flags8.fromUByte(0b00000000u)),
                Wrapper(Flags8.fromUByte(0b11100001u)),
                Wrapper(Flags8.fromUByte(0b11100001u))
            ),
            Arguments.of(
                Wrapper(Flags8.fromUByte(0b11100001u)),
                Wrapper(Flags8.fromUByte(0b00000000u)),
                Wrapper(Flags8.fromUByte(0b11100001u))
            ),
            Arguments.of(
                Wrapper(Flags8.fromUByte(0b11100001u)),
                Wrapper(Flags8.fromUByte(0b11100001u)),
                Wrapper(Flags8.fromUByte(0b00000000u))
            ),
            Arguments.of(
                Wrapper(Flags8.fromUByte(0b10110111u)),
                Wrapper(Flags8.fromUByte(0b11101100u)),
                Wrapper(Flags8.fromUByte(0b01011011u))
            ),
        )

        @JvmStatic
        fun settingArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Flags8.fromUByte(0b00000000u)),
                Wrapper(Flags8.fromUByte(0b11100001u)),
                true,
                Wrapper(Flags8.fromUByte(0b11100001u))
            ),
            Arguments.of(
                Wrapper(Flags8.fromUByte(0b11100001u)),
                Wrapper(Flags8.fromUByte(0b11100001u)),
                false,
                Wrapper(Flags8.fromUByte(0b00000000u))
            ),
            Arguments.of(
                Wrapper(Flags8.fromUByte(0b11100001u)),
                Wrapper(Flags8.fromUByte(0b00000000u)),
                true,
                Wrapper(Flags8.fromUByte(0b11100001u))
            ),
            Arguments.of(
                Wrapper(Flags8.fromUByte(0b11100001u)),
                Wrapper(Flags8.fromUByte(0b00000000u)),
                false,
                Wrapper(Flags8.fromUByte(0b11100001u))
            ),
            Arguments.of(
                Wrapper(Flags8.fromUByte(0b10110101u)),
                Wrapper(Flags8.fromUByte(0b11101100u)),
                true,
                Wrapper(Flags8.fromUByte(0b11111101u))
            ),
            Arguments.of(
                Wrapper(Flags8.fromUByte(0b10110101u)),
                Wrapper(Flags8.fromUByte(0b11101100u)),
                false,
                Wrapper(Flags8.fromUByte(0b00010001u))
            )
        )

        @JvmStatic
        fun hasAllArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Flags8.fromUByte(0b11100001u)),
                Wrapper(Flags8.fromUByte(0b00000000u)),
                true,
            ),
            Arguments.of(
                Wrapper(Flags8.fromUByte(0b00000000u)),
                Wrapper(Flags8.fromUByte(0b11100001u)),
                false,
            ),
            Arguments.of(
                Wrapper(Flags8.fromUByte(0b11100001u)),
                Wrapper(Flags8.fromUByte(0b11100001u)),
                true,
            ),
            Arguments.of(
                Wrapper(Flags8.fromUByte(0b11111001u)),
                Wrapper(Flags8.fromUByte(0b11100001u)),
                true,
            ),
            Arguments.of(
                Wrapper(Flags8.fromUByte(0b11011001u)),
                Wrapper(Flags8.fromUByte(0b11100001u)),
                false,
            )
        )

        @JvmStatic
        fun hasAnyArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Flags8.fromUByte(0b11100001u)),
                Wrapper(Flags8.fromUByte(0b00000000u)),
                false,
            ),
            Arguments.of(
                Wrapper(Flags8.fromUByte(0b00000000u)),
                Wrapper(Flags8.fromUByte(0b11100001u)),
                false,
            ),
            Arguments.of(
                Wrapper(Flags8.fromUByte(0b00000001u)),
                Wrapper(Flags8.fromUByte(0b11100001u)),
                true,
            ),
            Arguments.of(
                Wrapper(Flags8.fromUByte(0b11111000u)),
                Wrapper(Flags8.fromUByte(0b11100001u)),
                true,
            ),
            Arguments.of(
                Wrapper(Flags8.fromUByte(0b00011000u)),
                Wrapper(Flags8.fromUByte(0b11100001u)),
                false,
            )
        )

        @JvmStatic
        fun toStringArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Flags8.fromUByte(0b11100001u)),
                "Flags8(10000111)"
            ),
            Arguments.of(
                Wrapper(Flags8.fromUByte(0b10110111u)),
                "Flags8(11101101)"
            ),
            Arguments.of(
                Wrapper(Flags8.fromUByte(0b00000000u)),
                "Flags8(00000000)"
            ),
            Arguments.of(
                Wrapper(Flags8.fromUByte(0b11111111u)),
                "Flags8(11111111)"
            ),
        )

        @JvmStatic
        fun containsArgs(): List<Arguments> = listOf(
            Arguments.of(Wrapper(Flags8.fromUByte(0b11100001u)), true, true),
            Arguments.of(Wrapper(Flags8.fromUByte(0b11100001u)), false, true),
            Arguments.of(Wrapper(Flags8.fromUByte(0b00000000u)), true, false),
            Arguments.of(Wrapper(Flags8.fromUByte(0b00000000u)), false, true),
            Arguments.of(Wrapper(Flags8.fromUByte(0b11111111u)), true, true),
            Arguments.of(Wrapper(Flags8.fromUByte(0b11111111u)), false, false),
        )

        @JvmStatic
        fun getArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Flags8.fromUByte(0b11100001u)),
                listOf(true, true, true, false, false, false, false, true).asReversed()
            ),
            Arguments.of(
                Wrapper(Flags8.fromUByte(0b10110111u)),
                listOf(true, false, true, true, false, true, true, true).asReversed()
            ),
            Arguments.of(
                Wrapper(Flags8.fromUByte(0b00000000u)),
                listOf(false, false, false, false, false, false, false, false)
            ),
            Arguments.of(
                Wrapper(Flags8.fromUByte(0b11111111u)),
                listOf(true, true, true, true, true, true, true, true)
            ),
        )

        @JvmStatic
        fun iteratorArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Flags8.fromUByte(0b11100001u)),
                listOf(true, true, true, false, false, false, false, true).asReversed().iterator()
            ),
            Arguments.of(
                Wrapper(Flags8.fromUByte(0b10110111u)),
                listOf(true, false, true, true, false, true, true, true).asReversed().iterator()
            ),
            Arguments.of(
                Wrapper(Flags8.fromUByte(0b00000000u)),
                listOf(false, false, false, false, false, false, false, false).iterator()
            ),
            Arguments.of(
                Wrapper(Flags8.fromUByte(0b11111111u)),
                listOf(true, true, true, true, true, true, true, true).iterator()
            ),
        )

        @JvmStatic
        fun fromUByteArgs(): List<Arguments> = fromByteArgs().map {
            val args: Array<Any> = it.get()
            val byteValue = args[0] as Byte

            Arguments.of(Wrapper(byteValue.toUByte()), Wrapper(Flags8(byteValue)))
        }

        @JvmStatic
        fun fromByteArgs(): List<Arguments> = listOf(
            Arguments.of(
                0b11100001u.toByte(),
                Wrapper(Flags8(0b11100001u.toByte()))
            ),
            Arguments.of(
                0b10110111u.toByte(),
                Wrapper(Flags8(0b10110111u.toByte()))
            ),
            Arguments.of(
                0b00000000u.toByte(),
                Wrapper(Flags8(0b00000000u.toByte()))
            ),
            Arguments.of(
                0b11111111u.toByte(),
                Wrapper(Flags8(0b11111111u.toByte()))
            ),
        )
    }
}