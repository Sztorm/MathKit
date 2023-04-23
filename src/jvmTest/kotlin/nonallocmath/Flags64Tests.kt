package nonallocmath

import com.sztorm.nonallocmath.Flags64
import nonallocmath.utils.Wrapper
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class Flags64Tests {

    @ParameterizedTest
    @MethodSource("flags")
    fun basicPropertiesAreValid(flags: Wrapper<Flags64>) {
        assertEquals(64, flags.value.size)
        assertEquals(63, flags.value.lastIndex)
        assertEquals(false, flags.value.isEmpty())
    }

    @ParameterizedTest
    @MethodSource("containsAllArgs")
    fun containsAllReturnsCorrectValue(
        flags: Wrapper<Flags64>, elements: Collection<Boolean>, expected: Boolean
    ) = assertEquals(expected, flags.value.containsAll(elements))

    @ParameterizedTest
    @MethodSource("addingArgs")
    fun addingReturnsCorrectValue(
        a: Wrapper<Flags64>, b: Wrapper<Flags64>, expected: Wrapper<Flags64>
    ) = assertEquals(expected.value, a.value adding b.value)

    @ParameterizedTest
    @MethodSource("removingArgs")
    fun removingReturnsCorrectValue(
        a: Wrapper<Flags64>, b: Wrapper<Flags64>, expected: Wrapper<Flags64>
    ) = assertEquals(expected.value, a.value removing b.value)

    @ParameterizedTest
    @MethodSource("togglingArgs")
    fun togglingReturnsCorrectValue(
        a: Wrapper<Flags64>, b: Wrapper<Flags64>, expected: Wrapper<Flags64>
    ) = assertEquals(expected.value, a.value toggling b.value)

    @ParameterizedTest
    @MethodSource("settingArgs")
    fun settingReturnsCorrectValue(
        a: Wrapper<Flags64>, setFlags: Wrapper<Flags64>, toValue: Boolean, expected: Wrapper<Flags64>
    ) = assertEquals(expected.value, a.value.setting(setFlags.value, toValue))

    @ParameterizedTest
    @MethodSource("hasAllArgs")
    fun hasAllReturnsCorrectValue(a: Wrapper<Flags64>, b: Wrapper<Flags64>, expected: Boolean) =
        assertEquals(expected, a.value.hasAll(b.value))

    @ParameterizedTest
    @MethodSource("hasAnyArgs")
    fun hasAnyReturnsCorrectValue(a: Wrapper<Flags64>, b: Wrapper<Flags64>, expected: Boolean) =
        assertEquals(expected, a.value.hasAny(b.value))

    @ParameterizedTest
    @MethodSource("toStringArgs")
    fun toStringReturnsCorrectValue(flags: Wrapper<Flags64>, expected: String) =
        assertEquals(expected, flags.value.toString())

    @ParameterizedTest
    @MethodSource("containsArgs")
    fun containsReturnsCorrectValue(flags: Wrapper<Flags64>, element: Boolean, expected: Boolean) =
        assertEquals(expected, flags.value.contains(element))

    @ParameterizedTest
    @MethodSource("getArgs")
    fun getReturnsCorrectValue(flags: Wrapper<Flags64>, expected: Collection<Boolean>) {
        val actual = emptyList<Boolean>().toMutableList()

        for (i in 0..flags.value.lastIndex) {
            actual.add(flags.value[i])
        }
        assertContentEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("iteratorArgs")
    fun iteratorReturnsCorrectValue(flags: Wrapper<Flags64>, expected: Iterator<Boolean>) {
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
        val none = Flags64.NONE

        for (i in 0..none.lastIndex) {
            assertFalse(none[i])
        }
    }

    @Test
    fun allValueReturnsCorrectValue() {
        val all = Flags64.ALL

        for (i in 0..all.lastIndex) {
            assertTrue(all[i])
        }
    }

    companion object {
        @JvmStatic
        fun flags(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(
                    Flags64.fromULong(
                        0b11110001_01100110_10101001_11100111_11100000_00001101_00000100_01000001uL
                    )
                )
            ),
            Arguments.of(
                Wrapper(
                    Flags64.fromULong(
                        0b10100010_01010011_01110011_00111110_11110111_11111101_01111111_01111101uL
                    )
                )
            ),
            Arguments.of(
                Wrapper(
                    Flags64.fromULong(
                        0b00000000_00000000_00000000_00000000_00000000_00000000_00000000_00000000uL
                    )
                )
            ),
            Arguments.of(
                Wrapper(
                    Flags64.fromULong(
                        0b11111111_11111111_11111111_11111111_11111111_11111111_11111111_11111111uL
                    )
                )
            ),
        )

        @JvmStatic
        fun containsAllArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(
                    Flags64.fromULong(
                        0b11110001_01100110_10101001_11100111_11100000_00001101_00000100_01000001uL
                    )
                ),
                emptyList<Boolean>(),
                true
            ),
            Arguments.of(
                Wrapper(
                    Flags64.fromULong(
                        0b11110001_01100110_10101001_11100111_11100000_00001101_00000100_01000001uL
                    )
                ),
                listOf(true, false),
                true
            ),
            Arguments.of(
                Wrapper(
                    Flags64.fromULong(
                        0b11110001_01100110_10101001_11100111_11100000_00001101_00000100_01000001uL
                    )
                ),
                listOf(false, false),
                true
            ),
            Arguments.of(
                Wrapper(
                    Flags64.fromULong(
                        0b11110001_01100110_10101001_11100111_11100000_00001101_00000100_01000001uL
                    )
                ),
                listOf(true, true),
                true
            ),
            Arguments.of(
                Wrapper(
                    Flags64.fromULong(
                        0b00000000_00000000_00000000_00000000_00000000_00000000_00000000_00000000uL
                    )
                ),
                listOf(true, false),
                false
            ),
            Arguments.of(
                Wrapper(
                    Flags64.fromULong(
                        0b00000000_00000000_00000000_00000000_00000000_00000000_00000000_00000000uL
                    )
                ),
                listOf(false, false),
                true
            ),
            Arguments.of(
                Wrapper(
                    Flags64.fromULong(
                        0b00000000_00000000_00000000_00000000_00000000_00000000_00000000_00000000uL
                    )
                ),
                listOf(true, true),
                false
            ),
            Arguments.of(
                Wrapper(
                    Flags64.fromULong(
                        0b11111111_11111111_11111111_11111111_11111111_11111111_11111111_11111111uL
                    )
                ),
                listOf(true, false),
                false
            ),
            Arguments.of(
                Wrapper(
                    Flags64.fromULong(
                        0b11111111_11111111_11111111_11111111_11111111_11111111_11111111_11111111uL
                    )
                ),
                listOf(false, false),
                false
            ),
            Arguments.of(
                Wrapper(
                    Flags64.fromULong(
                        0b11111111_11111111_11111111_11111111_11111111_11111111_11111111_11111111uL
                    )
                ),
                listOf(true, true),
                true
            ),
        )

        @JvmStatic
        fun addingArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(
                    Flags64.fromULong(
                        0b00000000_00000000_00000000_00000000_00000000_00000000_00000000_00000000uL
                    )
                ),
                Wrapper(
                    Flags64.fromULong(
                        0b11110001_01100110_10101001_11100111_11100000_00001101_00000100_01000001uL
                    )
                ),
                Wrapper(
                    Flags64.fromULong(
                        0b11110001_01100110_10101001_11100111_11100000_00001101_00000100_01000001uL
                    )
                )
            ),
            Arguments.of(
                Wrapper(
                    Flags64.fromULong(
                        0b11110001_01100110_10101001_11100111_11100000_00001101_00000100_01000001uL
                    )
                ),
                Wrapper(
                    Flags64.fromULong(
                        0b00000000_00000000_00000000_00000000_00000000_00000000_00000000_00000000uL
                    )
                ),
                Wrapper(
                    Flags64.fromULong(
                        0b11110001_01100110_10101001_11100111_11100000_00001101_00000100_01000001uL
                    )
                )
            ),
            Arguments.of(
                Wrapper(
                    Flags64.fromULong(
                        0b11110001_01100110_10101001_11100111_11100000_00001101_00000100_01000001uL
                    )
                ),
                Wrapper(
                    Flags64.fromULong(
                        0b11110001_01100110_10101001_11100111_11100000_00001101_00000100_01000001uL
                    )
                ),
                Wrapper(
                    Flags64.fromULong(
                        0b11110001_01100110_10101001_11100111_11100000_00001101_00000100_01000001uL
                    )
                )
            ),
            Arguments.of(
                Wrapper(
                    Flags64.fromULong(
                        0b11110001_01100110_10101001_11100111_11100000_00001101_00000100_01000001uL
                    )
                ),
                Wrapper(
                    Flags64.fromULong(
                        0b11000100_01011100_10011101_01011011_11110111_11111101_01111011_01111100uL
                    )
                ),
                Wrapper(
                    Flags64.fromULong(
                        0b11110101_01111110_10111101_11111111_11110111_11111101_01111111_01111101uL
                    )
                )
            ),
        )

        @JvmStatic
        fun removingArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(
                    Flags64.fromULong(
                        0b00000000_00000000_00000000_00000000_00000000_00000000_00000000_00000000uL
                    )
                ),
                Wrapper(
                    Flags64.fromULong(
                        0b11110001_01100110_10101001_11100111_11100000_00001101_00000100_01000001uL
                    )
                ),
                Wrapper(
                    Flags64.fromULong(
                        0b00000000_00000000_00000000_00000000_00000000_00000000_00000000_00000000uL
                    )
                )
            ),
            Arguments.of(
                Wrapper(
                    Flags64.fromULong(
                        0b11110001_01100110_10101001_11100111_11100000_00001101_00000100_01000001uL
                    )
                ),
                Wrapper(
                    Flags64.fromULong(
                        0b00000000_00000000_00000000_00000000_00000000_00000000_00000000_00000000uL
                    )
                ),
                Wrapper(
                    Flags64.fromULong(
                        0b11110001_01100110_10101001_11100111_11100000_00001101_00000100_01000001uL
                    )
                )
            ),
            Arguments.of(
                Wrapper(
                    Flags64.fromULong(
                        0b11110001_01100110_10101001_11100111_11100000_00001101_00000100_01000001uL
                    )
                ),
                Wrapper(
                    Flags64.fromULong(
                        0b11110001_01100110_10101001_11100111_11100000_00001101_00000100_01000001uL
                    )
                ),
                Wrapper(
                    Flags64.fromULong(
                        0b00000000_00000000_00000000_00000000_00000000_00000000_00000000_00000000uL
                    )
                )
            ),
            Arguments.of(
                Wrapper(
                    Flags64.fromULong(
                        0b10100010_01010011_01110011_00111110_11110111_11111101_01111111_01111101uL
                    )
                ),
                Wrapper(
                    Flags64.fromULong(
                        0b11110001_01100110_10101001_11100111_11100000_00001101_00000100_01000001uL
                    )
                ),
                Wrapper(
                    Flags64.fromULong(
                        0b00000010_00010001_01010010_00011000_00010111_11110000_01111011_00111100uL
                    )
                )
            ),
        )

        @JvmStatic
        fun togglingArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(
                    Flags64.fromULong(
                        0b00000000_00000000_00000000_00000000_00000000_00000000_00000000_00000000uL
                    )
                ),
                Wrapper(
                    Flags64.fromULong(
                        0b11110001_01100110_10101001_11100111_11100000_00001101_00000100_01000001uL
                    )
                ),
                Wrapper(
                    Flags64.fromULong(
                        0b11110001_01100110_10101001_11100111_11100000_00001101_00000100_01000001uL
                    )
                )
            ),
            Arguments.of(
                Wrapper(
                    Flags64.fromULong(
                        0b11110001_01100110_10101001_11100111_11100000_00001101_00000100_01000001uL
                    )
                ),
                Wrapper(
                    Flags64.fromULong(
                        0b00000000_00000000_00000000_00000000_00000000_00000000_00000000_00000000uL
                    )
                ),
                Wrapper(
                    Flags64.fromULong(
                        0b11110001_01100110_10101001_11100111_11100000_00001101_00000100_01000001uL
                    )
                )
            ),
            Arguments.of(
                Wrapper(
                    Flags64.fromULong(
                        0b11110001_01100110_10101001_11100111_11100000_00001101_00000100_01000001uL
                    )
                ),
                Wrapper(
                    Flags64.fromULong(
                        0b11110001_01100110_10101001_11100111_11100000_00001101_00000100_01000001uL
                    )
                ),
                Wrapper(
                    Flags64.fromULong(
                        0b00000000_00000000_00000000_00000000_00000000_00000000_00000000_00000000uL
                    )
                )
            ),
            Arguments.of(
                Wrapper(
                    Flags64.fromULong(
                        0b10101110_01111001_01101100_01010101_10110111_10001101_01000111_00111001uL
                    )
                ),
                Wrapper(
                    Flags64.fromULong(
                        0b10111010_01010101_01010101_01011011_11101100_01101101_00101100_01110001uL
                    )
                ),
                Wrapper(
                    Flags64.fromULong(
                        0b00010100_00101100_00111001_00001110_01011011_11100000_01101011_01001000uL
                    )
                )
            ),
        )

        @JvmStatic
        fun settingArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(
                    Flags64.fromULong(
                        0b00000000_00000000_00000000_00000000_00000000_00000000_00000000_00000000uL
                    )
                ),
                Wrapper(
                    Flags64.fromULong(
                        0b11110001_01100110_10101001_11100111_11100000_00001101_00000100_01000001uL
                    )
                ),
                true,
                Wrapper(
                    Flags64.fromULong(
                        0b11110001_01100110_10101001_11100111_11100000_00001101_00000100_01000001uL
                    )
                )
            ),
            Arguments.of(
                Wrapper(
                    Flags64.fromULong(
                        0b11110001_01100110_10101001_11100111_11100000_00001101_00000100_01000001uL
                    )
                ),
                Wrapper(
                    Flags64.fromULong(
                        0b11110001_01100110_10101001_11100111_11100000_00001101_00000100_01000001uL
                    )
                ),
                false,
                Wrapper(
                    Flags64.fromULong(
                        0b00000000_00000000_00000000_00000000_00000000_00000000_00000000_00000000uL
                    )
                )
            ),
            Arguments.of(
                Wrapper(
                    Flags64.fromULong(
                        0b11110001_01100110_10101001_11100111_11100000_00001101_00000100_01000001uL
                    )
                ),
                Wrapper(
                    Flags64.fromULong(
                        0b00000000_00000000_00000000_00000000_00000000_00000000_00000000_00000000uL
                    )
                ),
                true,
                Wrapper(
                    Flags64.fromULong(
                        0b11110001_01100110_10101001_11100111_11100000_00001101_00000100_01000001uL
                    )
                )
            ),
            Arguments.of(
                Wrapper(
                    Flags64.fromULong(
                        0b11110001_01100110_10101001_11100111_11100000_00001101_00000100_01000001uL
                    )
                ),
                Wrapper(
                    Flags64.fromULong(
                        0b00000000_00000000_00000000_00000000_00000000_00000000_00000000_00000000uL
                    )
                ),
                false,
                Wrapper(
                    Flags64.fromULong(
                        0b11110001_01100110_10101001_11100111_11100000_00001101_00000100_01000001uL
                    )
                )
            ),
            Arguments.of(
                Wrapper(
                    Flags64.fromULong(
                        0b11011010_10101101_01010101_10101101_10110111_10001101_01000111_00111001uL
                    )
                ),
                Wrapper(
                    Flags64.fromULong(
                        0b01010101_11110100_00101011_01111101_11101100_01101101_00101100_01110001uL
                    )
                ),
                true,
                Wrapper(
                    Flags64.fromULong(
                        0b11011111_11111101_01111111_11111101_11111111_11101101_01101111_01111001uL
                    )
                )
            ),
            Arguments.of(
                Wrapper(
                    Flags64.fromULong(
                        0b11011010_10101101_01010101_10101101_10110111_10001101_01000111_00111001uL
                    )
                ),
                Wrapper(
                    Flags64.fromULong(
                        0b01010101_11110100_00101011_01111101_11101100_01101101_00101100_01110001uL
                    )
                ),
                false,
                Wrapper(
                    Flags64.fromULong(
                        0b10001010_00001001_01010100_10000000_00010011_10000000_01000011_00001000uL
                    )
                )
            )
        )

        @JvmStatic
        fun hasAllArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(
                    Flags64.fromULong(
                        0b11110001_01100110_10101001_11100111_11100000_00001101_00000100_01000001uL
                    )
                ),
                Wrapper(
                    Flags64.fromULong(
                        0b00000000_00000000_00000000_00000000_00000000_00000000_00000000_00000000uL
                    )
                ),
                true,
            ),
            Arguments.of(
                Wrapper(
                    Flags64.fromULong(
                        0b00000000_00000000_00000000_00000000_00000000_00000000_00000000_00000000uL
                    )
                ),
                Wrapper(
                    Flags64.fromULong(
                        0b11110001_01100110_10101001_11100111_11100000_00001101_00000100_01000001uL
                    )
                ),
                false,
            ),
            Arguments.of(
                Wrapper(
                    Flags64.fromULong(
                        0b11110001_01100110_10101001_11100111_11100000_00001101_00000100_01000001uL
                    )
                ),
                Wrapper(
                    Flags64.fromULong(
                        0b11110001_01100110_10101001_11100111_11100000_00001101_00000100_01000001uL
                    )
                ),
                true,
            ),
            Arguments.of(
                Wrapper(
                    Flags64.fromULong(
                        0b11110111_01111111_10101001_11101111_11111000_01101111_01110100_01000111uL
                    )
                ),
                Wrapper(
                    Flags64.fromULong(
                        0b11110001_01100110_10101001_11100111_11100000_00001101_00000100_01000001uL
                    )
                ),
                true,
            ),
            Arguments.of(
                Wrapper(
                    Flags64.fromULong(
                        0b01110111_01111111_10101001_11101111_11111000_01101111_01110100_01000111uL
                    )
                ),
                Wrapper(
                    Flags64.fromULong(
                        0b11110001_01100110_10101001_11100111_11100000_00001101_00000100_01000001uL
                    )
                ),
                false,
            )
        )

        @JvmStatic
        fun hasAnyArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(
                    Flags64.fromULong(
                        0b11110001_01100110_10101001_11100111_11100000_00001101_00000100_01000001uL
                    )
                ),
                Wrapper(
                    Flags64.fromULong(
                        0b00000000_00000000_00000000_00000000_00000000_00000000_00000000_00000000uL
                    )
                ),
                false,
            ),
            Arguments.of(
                Wrapper(
                    Flags64.fromULong(
                        0b00000000_00000000_00000000_00000000_00000000_00000000_00000000_00000000uL
                    )
                ),
                Wrapper(
                    Flags64.fromULong(
                        0b11110001_01100110_10101001_11100111_11100000_00001101_00000100_01000001uL
                    )
                ),
                false,
            ),
            Arguments.of(
                Wrapper(
                    Flags64.fromULong(
                        0b10000000_00000000_00000000_00000000_00000000_00000000_00000000_00000000uL
                    )
                ),
                Wrapper(
                    Flags64.fromULong(
                        0b11110001_01100110_10101001_11100111_11100000_00001101_00000100_01000001uL
                    )
                ),
                true,
            ),
            Arguments.of(
                Wrapper(
                    Flags64.fromULong(
                        0b11110111_01111111_10101001_11101111_11111000_01101111_01110100_01000111uL
                    )
                ),
                Wrapper(
                    Flags64.fromULong(
                        0b11110001_01100110_10101001_11100111_11100000_00001101_00000100_01000001uL
                    )
                ),
                true,
            ),
            Arguments.of(
                Wrapper(
                    Flags64.fromULong(
                        0b00001010_10000001_01010000_00011000_00011000_01100010_01110000_00000110uL
                    )
                ),
                Wrapper(
                    Flags64.fromULong(
                        0b11110001_01100110_10101001_11100111_11100000_00001101_00000100_01000001uL
                    )
                ),
                false,
            )
        )

        @JvmStatic
        fun toStringArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(
                    Flags64.fromULong(
                        0b11110001_01100110_10101001_11100111_11100000_00001101_00000100_01000001uL
                    )
                ),
                "Flags64(10000010 00100000 10110000 00000111 11100111 10010101 01100110 10001111)"
            ),
            Arguments.of(
                Wrapper(
                    Flags64.fromULong(
                        0b10100010_01010011_01110011_00111110_11110111_11111101_01111111_01111101uL
                    )
                ),
                "Flags64(10111110 11111110 10111111 11101111 01111100 11001110 11001010 01000101)"
            ),
            Arguments.of(
                Wrapper(
                    Flags64.fromULong(
                        0b00000000_00000000_00000000_00000000_00000000_00000000_00000000_00000000uL
                    )
                ),
                "Flags64(00000000 00000000 00000000 00000000 00000000 00000000 00000000 00000000)"
            ),
            Arguments.of(
                Wrapper(
                    Flags64.fromULong(
                        0b11111111_11111111_11111111_11111111_11111111_11111111_11111111_11111111uL
                    )
                ),
                "Flags64(11111111 11111111 11111111 11111111 11111111 11111111 11111111 11111111)"
            ),
        )

        @JvmStatic
        fun containsArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(
                    Flags64.fromULong(
                        0b11110001_01100110_10101001_11100111_11100000_00001101_00000100_01000001uL
                    )
                ),
                true,
                true
            ),
            Arguments.of(
                Wrapper(
                    Flags64.fromULong(
                        0b11110001_01100110_10101001_11100111_11100000_00001101_00000100_01000001uL
                    )
                ),
                false,
                true
            ),
            Arguments.of(
                Wrapper(
                    Flags64.fromULong(
                        0b00000000_00000000_00000000_00000000_00000000_00000000_00000000_00000000uL
                    )
                ),
                true,
                false
            ),
            Arguments.of(
                Wrapper(
                    Flags64.fromULong(
                        0b00000000_00000000_00000000_00000000_00000000_00000000_00000000_00000000uL
                    )
                ),
                false,
                true
            ),
            Arguments.of(
                Wrapper(
                    Flags64.fromULong(
                        0b11111111_11111111_11111111_11111111_11111111_11111111_11111111_11111111uL
                    )
                ),
                true,
                true
            ),
            Arguments.of(
                Wrapper(
                    Flags64.fromULong(
                        0b11111111_11111111_11111111_11111111_11111111_11111111_11111111_11111111uL
                    )
                ),
                false,
                false
            ),
        )

        @JvmStatic
        fun getArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(
                    Flags64.fromULong(
                        0b11110001_01100110_10101001_11100111_11100000_00001101_00000100_01000001uL
                    )
                ),
                listOf(
                    true, true, true, true, false, false, false, true,
                    false, true, true, false, false, true, true, false,
                    true, false, true, false, true, false, false, true,
                    true, true, true, false, false, true, true, true,
                    true, true, true, false, false, false, false, false,
                    false, false, false, false, true, true, false, true,
                    false, false, false, false, false, true, false, false,
                    false, true, false, false, false, false, false, true,
                ).asReversed()
            ),
            Arguments.of(
                Wrapper(
                    Flags64.fromULong(
                        0b10100010_01010011_01110011_00111110_11110111_11111101_01111111_01111101uL
                    )
                ),
                listOf(
                    true, false, true, false, false, false, true, false,
                    false, true, false, true, false, false, true, true,
                    false, true, true, true, false, false, true, true,
                    false, false, true, true, true, true, true, false,
                    true, true, true, true, false, true, true, true,
                    true, true, true, true, true, true, false, true,
                    false, true, true, true, true, true, true, true,
                    false, true, true, true, true, true, false, true,
                ).asReversed()
            ),
            Arguments.of(
                Wrapper(
                    Flags64.fromULong(
                        0b00000000_00000000_00000000_00000000_00000000_00000000_00000000_00000000uL
                    )
                ),
                listOf(
                    false, false, false, false, false, false, false, false,
                    false, false, false, false, false, false, false, false,
                    false, false, false, false, false, false, false, false,
                    false, false, false, false, false, false, false, false,
                    false, false, false, false, false, false, false, false,
                    false, false, false, false, false, false, false, false,
                    false, false, false, false, false, false, false, false,
                    false, false, false, false, false, false, false, false,
                )
            ),
            Arguments.of(
                Wrapper(
                    Flags64.fromULong(
                        0b11111111_11111111_11111111_11111111_11111111_11111111_11111111_11111111uL
                    )
                ),
                listOf(
                    true, true, true, true, true, true, true, true,
                    true, true, true, true, true, true, true, true,
                    true, true, true, true, true, true, true, true,
                    true, true, true, true, true, true, true, true,
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
                Wrapper(
                    Flags64.fromULong(
                        0b11110001_01100110_10101001_11100111_11100000_00001101_00000100_01000001uL
                    )
                ),
                listOf(
                    true, true, true, true, false, false, false, true,
                    false, true, true, false, false, true, true, false,
                    true, false, true, false, true, false, false, true,
                    true, true, true, false, false, true, true, true,
                    true, true, true, false, false, false, false, false,
                    false, false, false, false, true, true, false, true,
                    false, false, false, false, false, true, false, false,
                    false, true, false, false, false, false, false, true,
                ).asReversed().iterator()
            ),
            Arguments.of(
                Wrapper(
                    Flags64.fromULong(
                        0b10100010_01010011_01110011_00111110_11110111_11111101_01111111_01111101uL
                    )
                ),
                listOf(
                    true, false, true, false, false, false, true, false,
                    false, true, false, true, false, false, true, true,
                    false, true, true, true, false, false, true, true,
                    false, false, true, true, true, true, true, false,
                    true, true, true, true, false, true, true, true,
                    true, true, true, true, true, true, false, true,
                    false, true, true, true, true, true, true, true,
                    false, true, true, true, true, true, false, true,
                ).asReversed().iterator()
            ),
            Arguments.of(
                Wrapper(
                    Flags64.fromULong(
                        0b00000000_00000000_00000000_00000000_00000000_00000000_00000000_00000000uL
                    )
                ),
                listOf(
                    false, false, false, false, false, false, false, false,
                    false, false, false, false, false, false, false, false,
                    false, false, false, false, false, false, false, false,
                    false, false, false, false, false, false, false, false,
                    false, false, false, false, false, false, false, false,
                    false, false, false, false, false, false, false, false,
                    false, false, false, false, false, false, false, false,
                    false, false, false, false, false, false, false, false,
                ).iterator()
            ),
            Arguments.of(
                Wrapper(
                    Flags64.fromULong(
                        0b11111111_11111111_11111111_11111111_11111111_11111111_11111111_11111111uL
                    )
                ),
                listOf(
                    true, true, true, true, true, true, true, true,
                    true, true, true, true, true, true, true, true,
                    true, true, true, true, true, true, true, true,
                    true, true, true, true, true, true, true, true,
                    true, true, true, true, true, true, true, true,
                    true, true, true, true, true, true, true, true,
                    true, true, true, true, true, true, true, true,
                    true, true, true, true, true, true, true, true,
                ).iterator()
            ),
        )
    }
}