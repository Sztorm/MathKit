package nonallocmath

import com.sztorm.nonallocmath.Flags8
import nonallocmath.utils.Wrapper
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class Flags8Tests {

    @Test
    fun basicPropertiesAreValid() {
        val flags = Flags8.fromUByte(0b11100001u)

        assertEquals(8, flags.size)
        assertEquals(7, flags.lastIndex)
        assertEquals(false, flags.isEmpty())
    }

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

    companion object {
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
    }
}