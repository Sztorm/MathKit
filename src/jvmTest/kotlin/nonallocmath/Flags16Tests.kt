package nonallocmath

import com.sztorm.nonallocmath.Flags16
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertContentEquals

class Flags16Tests {
    @Test
    fun getReturnsValidValue() {
        val flags = Flags16.fromUShort(0b11100000_00001101u)
        val expected: Sequence<Boolean> = sequenceOf(
            1, 0, 1, 1, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 1, 1, 1
        ).map { it != 0 }
        val actual: Sequence<Boolean> = generateSequence(flags) { it }
            .mapIndexed { i, f -> f[i] }
            .take(16)

        assertContentEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("addingArguments")
    fun addingReturnsValidValue(a: Short, b: Short, expected: Short) =
        assertTrue((Flags16(a) adding Flags16(b)) == Flags16(expected))

    @ParameterizedTest
    @MethodSource("removingArguments")
    fun removingReturnsValidValue(a: Short, b: Short, expected: Short) =
        assertTrue((Flags16(a) removing Flags16(b)) == Flags16(expected))

    @ParameterizedTest
    @MethodSource("togglingArguments")
    fun togglingReturnsValidValue(a: Short, b: Short, expected: Short) =
        assertTrue((Flags16(a) toggling Flags16(b)) == Flags16(expected))

    @ParameterizedTest
    @MethodSource("settingArguments")
    fun settingReturnsValidValue(a: Short, setFlags: Short, toValue: Boolean, expected: Short) =
        assertTrue((Flags16(a).setting(Flags16(setFlags), toValue)) == Flags16(expected))

    @ParameterizedTest
    @MethodSource("hasAllArguments")
    fun hasAllReturnsValidValue(a: Short, b: Short, expected: Boolean) =
        assertTrue(Flags16(a).hasAll(Flags16(b)) == expected)

    @ParameterizedTest
    @MethodSource("hasAnyArguments")
    fun hasAnyReturnsValidValue(a: Short, b: Short, expected: Boolean) =
        assertTrue(Flags16(a).hasAny(Flags16(b)) == expected)

    companion object {
        @JvmStatic
        fun addingArguments(): List<Arguments> = listOf(
            Arguments.of(
                0b00000000_00000000u.toShort(),
                0b11100000_00001101u.toShort(),
                0b11100000_00001101u.toShort()
            ),
            Arguments.of(
                0b11100000_00001101u.toShort(),
                0b00000000_00000000u.toShort(),
                0b11100000_00001101u.toShort()
            ),
            Arguments.of(
                0b11100000_00001101u.toShort(),
                0b11100000_00001101u.toShort(),
                0b11100000_00001101u.toShort()
            ),
            Arguments.of(
                0b11100000_00001101u.toShort(),
                0b11110111_10110101u.toShort(),
                0b11110111_10111101u.toShort()
            ),
        )

        @JvmStatic
        fun removingArguments(): List<Arguments> = listOf(
            Arguments.of(
                0b00000000_00000000u.toShort(),
                0b11100000_00001101u.toShort(),
                0b00000000_00000000u.toShort()
            ),
            Arguments.of(
                0b11100000_00001101u.toShort(),
                0b00000000_00000000u.toShort(),
                0b11100000_00001101u.toShort()
            ),
            Arguments.of(
                0b11100000_00001101u.toShort(),
                0b11100000_00001101u.toShort(),
                0b00000000_00000000u.toShort()
            ),
            Arguments.of(
                0b11110111_11111101u.toShort(),
                0b11100000_00001101u.toShort(),
                0b00010111_11110000u.toShort()
            ),
        )

        @JvmStatic
        fun togglingArguments(): List<Arguments> = listOf(
            Arguments.of(
                0b00000000_00000000u.toShort(),
                0b11100000_00001101u.toShort(),
                0b11100000_00001101u.toShort()
            ),
            Arguments.of(
                0b11100000_00001101u.toShort(),
                0b00000000_00000000u.toShort(),
                0b11100000_00001101u.toShort()
            ),
            Arguments.of(
                0b11100000_00001101u.toShort(),
                0b11100000_00001101u.toShort(),
                0b00000000_00000000u.toShort()
            ),
            Arguments.of(
                0b10110111_10001101u.toShort(),
                0b11101100_01101101u.toShort(),
                0b01011011_11100000u.toShort()
            ),
        )

        @JvmStatic
        fun settingArguments(): List<Arguments> = listOf(
            Arguments.of(
                0b00000000_00000000u.toShort(),
                0b11100000_00001101u.toShort(),
                true,
                0b11100000_00001101u.toShort()
            ),
            Arguments.of(
                0b11100000_00001101u.toShort(),
                0b11100000_00001101u.toShort(),
                false,
                0b00000000_00000000u.toShort()
            ),
            Arguments.of(
                0b11100000_00001101u.toShort(),
                0b00000000_00000000u.toShort(),
                true,
                0b11100000_00001101u.toShort()
            ),
            Arguments.of(
                0b11100000_00001101u.toShort(),
                0b00000000_00000000u.toShort(),
                false,
                0b11100000_00001101u.toShort()
            ),
            Arguments.of(
                0b10110111_10001101u.toShort(),
                0b11101100_01101101u.toShort(),
                true,
                0b11111111_11101101u.toShort()
            ),
            Arguments.of(
                0b10110111_10001101u.toShort(),
                0b11101100_01101101u.toShort(),
                false,
                0b00010011_10000000u.toShort()
            )
        )

        @JvmStatic
        fun hasAllArguments(): List<Arguments> = listOf(
            Arguments.of(
                0b11100000_00001101u.toShort(),
                0b00000000_00000000u.toShort(),
                true,
            ),
            Arguments.of(
                0b00000000_00000000u.toShort(),
                0b11100000_00001101u.toShort(),
                false,
            ),
            Arguments.of(
                0b11100000_00001101u.toShort(),
                0b11100000_00001101u.toShort(),
                true,
            ),
            Arguments.of(
                0b11111000_01101111u.toShort(),
                0b11100000_00001101u.toShort(),
                true,
            ),
            Arguments.of(
                0b11011000_01101111u.toShort(),
                0b11100000_00001101u.toShort(),
                false,
            )
        )

        @JvmStatic
        fun hasAnyArguments(): List<Arguments> = listOf(
            Arguments.of(
                0b11100000_00001101u.toShort(),
                0b00000000_00000000u.toShort(),
                false,
            ),
            Arguments.of(
                0b00000000_00000000u.toShort(),
                0b11100000_00001101u.toShort(),
                false,
            ),
            Arguments.of(
                0b00000000_00000001u.toShort(),
                0b11100000_00001101u.toShort(),
                true,
            ),
            Arguments.of(
                0b11111000_01101111u.toShort(),
                0b11100000_00001101u.toShort(),
                true,
            ),
            Arguments.of(
                0b00011000_01100010u.toShort(),
                0b11100000_00001101u.toShort(),
                false,
            )
        )
    }
}