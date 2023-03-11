package nonallocmath

import com.sztorm.nonallocmath.Flags32
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertContentEquals

class Flags32Tests {
    @Test
    fun getReturnsValidValue() {
        val flags = Flags32.fromUInt(0b11100000_00001101_00000100_01000001u)
        val expected: Sequence<Boolean> = sequenceOf(
            1, 0, 0, 0, 0, 0, 1, 0,
            0, 0, 1, 0, 0, 0, 0, 0,
            1, 0, 1, 1, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 1, 1, 1
        ).map { it != 0 }
        val actual: Sequence<Boolean> = generateSequence(flags) { it }
            .mapIndexed { i, f -> f[i] }
            .take(32)

        assertContentEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("addingArguments")
    fun addingReturnsValidValue(a: Int, b: Int, expected: Int) =
        assertTrue((Flags32(a) adding Flags32(b)) == Flags32(expected))

    @ParameterizedTest
    @MethodSource("removingArguments")
    fun removingReturnsValidValue(a: Int, b: Int, expected: Int) =
        assertTrue((Flags32(a) removing Flags32(b)) == Flags32(expected))

    @ParameterizedTest
    @MethodSource("togglingArguments")
    fun togglingReturnsValidValue(a: Int, b: Int, expected: Int) =
        assertTrue((Flags32(a) toggling Flags32(b)) == Flags32(expected))

    @ParameterizedTest
    @MethodSource("settingArguments")
    fun settingReturnsValidValue(a: Int, setFlags: Int, toValue: Boolean, expected: Int) =
        assertTrue((Flags32(a).setting(Flags32(setFlags), toValue)) == Flags32(expected))

    @ParameterizedTest
    @MethodSource("hasAllArguments")
    fun hasAllReturnsValidValue(a: Int, b: Int, expected: Boolean) =
        assertTrue(Flags32(a).hasAll(Flags32(b)) == expected)

    @ParameterizedTest
    @MethodSource("hasAnyArguments")
    fun hasAnyReturnsValidValue(a: Int, b: Int, expected: Boolean) =
        assertTrue(Flags32(a).hasAny(Flags32(b)) == expected)

    companion object {
        @JvmStatic
        fun addingArguments(): List<Arguments> = listOf(
            Arguments.of(
                0b00000000_00000000_00000000_00000000u.toInt(),
                0b11100000_00001101_00000100_01000001u.toInt(),
                0b11100000_00001101_00000100_01000001u.toInt()
            ),
            Arguments.of(
                0b11100000_00001101_00000100_01000001u.toInt(),
                0b00000000_00000000_00000000_00000000u.toInt(),
                0b11100000_00001101_00000100_01000001u.toInt()
            ),
            Arguments.of(
                0b11100000_00001101_00000100_01000001u.toInt(),
                0b11100000_00001101_00000100_01000001u.toInt(),
                0b11100000_00001101_00000100_01000001u.toInt()
            ),
            Arguments.of(
                0b11100000_00001101_00000100_01000001u.toInt(),
                0b11110111_11111101_01111011_01111100u.toInt(),
                0b11110111_11111101_01111111_01111101u.toInt()
            ),
        )

        @JvmStatic
        fun removingArguments(): List<Arguments> = listOf(
            Arguments.of(
                0b00000000_00000000_00000000_00000000u.toInt(),
                0b11100000_00001101_00000100_01000001u.toInt(),
                0b00000000_00000000_00000000_00000000u.toInt()
            ),
            Arguments.of(
                0b11100000_00001101_00000100_01000001u.toInt(),
                0b00000000_00000000_00000000_00000000u.toInt(),
                0b11100000_00001101_00000100_01000001u.toInt()
            ),
            Arguments.of(
                0b11100000_00001101_00000100_01000001u.toInt(),
                0b11100000_00001101_00000100_01000001u.toInt(),
                0b00000000_00000000_00000000_00000000u.toInt()
            ),
            Arguments.of(
                0b11110111_11111101_01111111_01111101u.toInt(),
                0b11100000_00001101_00000100_01000001u.toInt(),
                0b00010111_11110000_01111011_00111100u.toInt()
            ),
        )

        @JvmStatic
        fun togglingArguments(): List<Arguments> = listOf(
            Arguments.of(
                0b00000000_00000000_00000000_00000000u.toInt(),
                0b11100000_00001101_00000100_01000001u.toInt(),
                0b11100000_00001101_00000100_01000001u.toInt()
            ),
            Arguments.of(
                0b11100000_00001101_00000100_01000001u.toInt(),
                0b00000000_00000000_00000000_00000000u.toInt(),
                0b11100000_00001101_00000100_01000001u.toInt()
            ),
            Arguments.of(
                0b11100000_00001101_00000100_01000001u.toInt(),
                0b11100000_00001101_00000100_01000001u.toInt(),
                0b00000000_00000000_00000000_00000000u.toInt()
            ),
            Arguments.of(
                0b10110111_10001101_01000111_00111001u.toInt(),
                0b11101100_01101101_00101100_01110001u.toInt(),
                0b01011011_11100000_01101011_01001000u.toInt()
            ),
        )

        @JvmStatic
        fun settingArguments(): List<Arguments> = listOf(
            Arguments.of(
                0b00000000_00000000_00000000_00000000u.toInt(),
                0b11100000_00001101_00000100_01000001u.toInt(),
                true,
                0b11100000_00001101_00000100_01000001u.toInt()
            ),
            Arguments.of(
                0b11100000_00001101_00000100_01000001u.toInt(),
                0b11100000_00001101_00000100_01000001u.toInt(),
                false,
                0b00000000_00000000_00000000_00000000u.toInt()
            ),
            Arguments.of(
                0b11100000_00001101_00000100_01000001u.toInt(),
                0b00000000_00000000_00000000_00000000u.toInt(),
                true,
                0b11100000_00001101_00000100_01000001u.toInt()
            ),
            Arguments.of(
                0b11100000_00001101_00000100_01000001u.toInt(),
                0b00000000_00000000_00000000_00000000u.toInt(),
                false,
                0b11100000_00001101_00000100_01000001u.toInt()
            ),
            Arguments.of(
                0b10110111_10001101_01000111_00111001u.toInt(),
                0b11101100_01101101_00101100_01110001u.toInt(),
                true,
                0b11111111_11101101_01101111_01111001u.toInt()
            ),
            Arguments.of(
                0b10110111_10001101_01000111_00111001u.toInt(),
                0b11101100_01101101_00101100_01110001u.toInt(),
                false,
                0b00010011_10000000_01000011_00001000u.toInt()
            )
        )

        @JvmStatic
        fun hasAllArguments(): List<Arguments> = listOf(
            Arguments.of(
                0b11100000_00001101_00000100_01000001u.toInt(),
                0b00000000_00000000_00000000_00000000u.toInt(),
                true,
            ),
            Arguments.of(
                0b00000000_00000000_00000000_00000000u.toInt(),
                0b11100000_00001101_00000100_01000001u.toInt(),
                false,
            ),
            Arguments.of(
                0b11100000_00001101_00000100_01000001u.toInt(),
                0b11100000_00001101_00000100_01000001u.toInt(),
                true,
            ),
            Arguments.of(
                0b11111000_01101111_01110100_01000111u.toInt(),
                0b11100000_00001101_00000100_01000001u.toInt(),
                true,
            ),
            Arguments.of(
                0b11011000_01101111_01110100_01000111u.toInt(),
                0b11100000_00001101_00000100_01000001u.toInt(),
                false,
            )
        )

        @JvmStatic
        fun hasAnyArguments(): List<Arguments> = listOf(
            Arguments.of(
                0b11100000_00001101_00000100_01000001u.toInt(),
                0b00000000_00000000_00000000_00000000u.toInt(),
                false,
            ),
            Arguments.of(
                0b00000000_00000000_00000000_00000000u.toInt(),
                0b11100000_00001101_00000100_01000001u.toInt(),
                false,
            ),
            Arguments.of(
                0b00000000_00000000_00000000_00000001u.toInt(),
                0b11100000_00001101_00000100_01000001u.toInt(),
                true,
            ),
            Arguments.of(
                0b11111000_01101111_01110100_01000111u.toInt(),
                0b11100000_00001101_00000100_01000001u.toInt(),
                true,
            ),
            Arguments.of(
                0b00011000_01100010_01110000_00000110u.toInt(),
                0b11100000_00001101_00000100_01000001u.toInt(),
                false,
            )
        )
    }
}