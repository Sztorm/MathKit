package nonallocmath

import com.sztorm.nonallocmath.Flags64
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertContentEquals

class Flags64Tests {
    @Test
    fun getReturnsValidValue() {
        val flags = Flags64.fromULong(0b11110001_01100110_10101001_11100111_11100000_00001101_00000100_01000001uL)
        val expected: Sequence<Boolean> = sequenceOf(
            1, 0, 0, 0, 0, 0, 1, 0,
            0, 0, 1, 0, 0, 0, 0, 0,
            1, 0, 1, 1, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 1, 1, 1,
            1, 1, 1, 0, 0, 1, 1, 1,
            1, 0, 0, 1, 0, 1, 0, 1,
            0, 1, 1, 0, 0, 1, 1, 0,
            1, 0, 0, 0, 1, 1, 1, 1
        ).map { it != 0 }
        val actual: Sequence<Boolean> = generateSequence(flags) { it }
            .mapIndexed { i, f -> f[i] }
            .take(64)

        assertContentEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("addingArguments")
    fun addingReturnsValidValue(a: Long, b: Long, expected: Long) =
        assertTrue((Flags64(a) adding Flags64(b)) == Flags64(expected))

    @ParameterizedTest
    @MethodSource("removingArguments")
    fun removingReturnsValidValue(a: Long, b: Long, expected: Long) =
        assertTrue((Flags64(a) removing Flags64(b)) == Flags64(expected))

    @ParameterizedTest
    @MethodSource("togglingArguments")
    fun togglingReturnsValidValue(a: Long, b: Long, expected: Long) =
        assertTrue((Flags64(a) toggling Flags64(b)) == Flags64(expected))

    @ParameterizedTest
    @MethodSource("settingArguments")
    fun settingReturnsValidValue(a: Long, setFlags: Long, toValue: Boolean, expected: Long) =
        assertTrue((Flags64(a).setting(Flags64(setFlags), toValue)) == Flags64(expected))

    @ParameterizedTest
    @MethodSource("hasAllArguments")
    fun hasAllReturnsValidValue(a: Long, b: Long, expected: Boolean) =
        assertTrue(Flags64(a).hasAll(Flags64(b)) == expected)

    @ParameterizedTest
    @MethodSource("hasAnyArguments")
    fun hasAnyReturnsValidValue(a: Long, b: Long, expected: Boolean) =
        assertTrue(Flags64(a).hasAny(Flags64(b)) == expected)

    companion object {
        @JvmStatic
        fun addingArguments(): List<Arguments> = listOf(
            Arguments.of(
                0b00000000_00000000_00000000_00000000_00000000_00000000_00000000_00000000uL.toLong(),
                0b11110001_01100110_10101001_11100111_11100000_00001101_00000100_01000001uL.toLong(),
                0b11110001_01100110_10101001_11100111_11100000_00001101_00000100_01000001uL.toLong()
            ),
            Arguments.of(
                0b11110001_01100110_10101001_11100111_11100000_00001101_00000100_01000001uL.toLong(),
                0b00000000_00000000_00000000_00000000_00000000_00000000_00000000_00000000uL.toLong(),
                0b11110001_01100110_10101001_11100111_11100000_00001101_00000100_01000001uL.toLong()
            ),
            Arguments.of(
                0b11110001_01100110_10101001_11100111_11100000_00001101_00000100_01000001uL.toLong(),
                0b11110001_01100110_10101001_11100111_11100000_00001101_00000100_01000001uL.toLong(),
                0b11110001_01100110_10101001_11100111_11100000_00001101_00000100_01000001uL.toLong()
            ),
            Arguments.of(
                0b11110001_01100110_10101001_11100111_11100000_00001101_00000100_01000001uL.toLong(),
                0b11000100_01011100_10011101_01011011_11110111_11111101_01111011_01111100uL.toLong(),
                0b11110101_01111110_10111101_11111111_11110111_11111101_01111111_01111101uL.toLong()
            ),
        )

        @JvmStatic
        fun removingArguments(): List<Arguments> = listOf(
            Arguments.of(
                0b00000000_00000000_00000000_00000000_00000000_00000000_00000000_00000000uL.toLong(),
                0b11110001_01100110_10101001_11100111_11100000_00001101_00000100_01000001uL.toLong(),
                0b00000000_00000000_00000000_00000000_00000000_00000000_00000000_00000000uL.toLong()
            ),
            Arguments.of(
                0b11110001_01100110_10101001_11100111_11100000_00001101_00000100_01000001uL.toLong(),
                0b00000000_00000000_00000000_00000000_00000000_00000000_00000000_00000000uL.toLong(),
                0b11110001_01100110_10101001_11100111_11100000_00001101_00000100_01000001uL.toLong()
            ),
            Arguments.of(
                0b11110001_01100110_10101001_11100111_11100000_00001101_00000100_01000001uL.toLong(),
                0b11110001_01100110_10101001_11100111_11100000_00001101_00000100_01000001uL.toLong(),
                0b00000000_00000000_00000000_00000000_00000000_00000000_00000000_00000000uL.toLong()
            ),
            Arguments.of(
                0b10100010_01010011_01110011_00111110_11110111_11111101_01111111_01111101uL.toLong(),
                0b11110001_01100110_10101001_11100111_11100000_00001101_00000100_01000001uL.toLong(),
                0b00000010_00010001_01010010_00011000_00010111_11110000_01111011_00111100uL.toLong()
            ),
        )

        @JvmStatic
        fun togglingArguments(): List<Arguments> = listOf(
            Arguments.of(
                0b00000000_00000000_00000000_00000000_00000000_00000000_00000000_00000000uL.toLong(),
                0b11110001_01100110_10101001_11100111_11100000_00001101_00000100_01000001uL.toLong(),
                0b11110001_01100110_10101001_11100111_11100000_00001101_00000100_01000001uL.toLong()
            ),
            Arguments.of(
                0b11110001_01100110_10101001_11100111_11100000_00001101_00000100_01000001uL.toLong(),
                0b00000000_00000000_00000000_00000000_00000000_00000000_00000000_00000000uL.toLong(),
                0b11110001_01100110_10101001_11100111_11100000_00001101_00000100_01000001uL.toLong()
            ),
            Arguments.of(
                0b11110001_01100110_10101001_11100111_11100000_00001101_00000100_01000001uL.toLong(),
                0b11110001_01100110_10101001_11100111_11100000_00001101_00000100_01000001uL.toLong(),
                0b00000000_00000000_00000000_00000000_00000000_00000000_00000000_00000000uL.toLong()
            ),
            Arguments.of(
                0b10101110_01111001_01101100_01010101_10110111_10001101_01000111_00111001uL.toLong(),
                0b10111010_01010101_01010101_01011011_11101100_01101101_00101100_01110001uL.toLong(),
                0b00010100_00101100_00111001_00001110_01011011_11100000_01101011_01001000uL.toLong()
            ),
        )

        @JvmStatic
        fun settingArguments(): List<Arguments> = listOf(
            Arguments.of(
                0b00000000_00000000_00000000_00000000_00000000_00000000_00000000_00000000uL.toLong(),
                0b11110001_01100110_10101001_11100111_11100000_00001101_00000100_01000001uL.toLong(),
                true,
                0b11110001_01100110_10101001_11100111_11100000_00001101_00000100_01000001uL.toLong()
            ),
            Arguments.of(
                0b11110001_01100110_10101001_11100111_11100000_00001101_00000100_01000001uL.toLong(),
                0b11110001_01100110_10101001_11100111_11100000_00001101_00000100_01000001uL.toLong(),
                false,
                0b00000000_00000000_00000000_00000000_00000000_00000000_00000000_00000000uL.toLong()
            ),
            Arguments.of(
                0b11110001_01100110_10101001_11100111_11100000_00001101_00000100_01000001uL.toLong(),
                0b00000000_00000000_00000000_00000000_00000000_00000000_00000000_00000000uL.toLong(),
                true,
                0b11110001_01100110_10101001_11100111_11100000_00001101_00000100_01000001uL.toLong()
            ),
            Arguments.of(
                0b11110001_01100110_10101001_11100111_11100000_00001101_00000100_01000001uL.toLong(),
                0b00000000_00000000_00000000_00000000_00000000_00000000_00000000_00000000uL.toLong(),
                false,
                0b11110001_01100110_10101001_11100111_11100000_00001101_00000100_01000001uL.toLong()
            ),
            Arguments.of(
                0b11011010_10101101_01010101_10101101_10110111_10001101_01000111_00111001uL.toLong(),
                0b01010101_11110100_00101011_01111101_11101100_01101101_00101100_01110001uL.toLong(),
                true,
                0b11011111_11111101_01111111_11111101_11111111_11101101_01101111_01111001uL.toLong()
            ),
            Arguments.of(
                0b11011010_10101101_01010101_10101101_10110111_10001101_01000111_00111001uL.toLong(),
                0b01010101_11110100_00101011_01111101_11101100_01101101_00101100_01110001uL.toLong(),
                false,
                0b10001010_00001001_01010100_10000000_00010011_10000000_01000011_00001000uL.toLong()
            )
        )

        @JvmStatic
        fun hasAllArguments(): List<Arguments> = listOf(
            Arguments.of(
                0b11110001_01100110_10101001_11100111_11100000_00001101_00000100_01000001uL.toLong(),
                0b00000000_00000000_00000000_00000000_00000000_00000000_00000000_00000000uL.toLong(),
                true,
            ),
            Arguments.of(
                0b00000000_00000000_00000000_00000000_00000000_00000000_00000000_00000000uL.toLong(),
                0b11110001_01100110_10101001_11100111_11100000_00001101_00000100_01000001uL.toLong(),
                false,
            ),
            Arguments.of(
                0b11110001_01100110_10101001_11100111_11100000_00001101_00000100_01000001uL.toLong(),
                0b11110001_01100110_10101001_11100111_11100000_00001101_00000100_01000001uL.toLong(),
                true,
            ),
            Arguments.of(
                0b11110111_01111111_10101001_11101111_11111000_01101111_01110100_01000111uL.toLong(),
                0b11110001_01100110_10101001_11100111_11100000_00001101_00000100_01000001uL.toLong(),
                true,
            ),
            Arguments.of(
                0b01110111_01111111_10101001_11101111_11111000_01101111_01110100_01000111uL.toLong(),
                0b11110001_01100110_10101001_11100111_11100000_00001101_00000100_01000001uL.toLong(),
                false,
            )
        )

        @JvmStatic
        fun hasAnyArguments(): List<Arguments> = listOf(
            Arguments.of(
                0b11110001_01100110_10101001_11100111_11100000_00001101_00000100_01000001uL.toLong(),
                0b00000000_00000000_00000000_00000000_00000000_00000000_00000000_00000000uL.toLong(),
                false,
            ),
            Arguments.of(
                0b00000000_00000000_00000000_00000000_00000000_00000000_00000000_00000000uL.toLong(),
                0b11110001_01100110_10101001_11100111_11100000_00001101_00000100_01000001uL.toLong(),
                false,
            ),
            Arguments.of(
                0b10000000_00000000_00000000_00000000_00000000_00000000_00000000_00000000uL.toLong(),
                0b11110001_01100110_10101001_11100111_11100000_00001101_00000100_01000001uL.toLong(),
                true,
            ),
            Arguments.of(
                0b11110111_01111111_10101001_11101111_11111000_01101111_01110100_01000111uL.toLong(),
                0b11110001_01100110_10101001_11100111_11100000_00001101_00000100_01000001uL.toLong(),
                true,
            ),
            Arguments.of(
                0b00001010_10000001_01010000_00011000_00011000_01100010_01110000_00000110uL.toLong(),
                0b11110001_01100110_10101001_11100111_11100000_00001101_00000100_01000001uL.toLong(),
                false,
            )
        )
    }
}