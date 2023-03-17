package nonallocmath

import com.sztorm.nonallocmath.Flags8
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertContentEquals

class Flags8Tests {
    @Test
    fun getReturnsValidValue() {
        val flags = Flags8.fromUByte(0b11100001u)
        val expected: Sequence<Boolean> = sequenceOf(
            1, 0, 0, 0, 0, 1, 1, 1
        ).map { it != 0 }
        val actual: Sequence<Boolean> = generateSequence(flags) { it }
            .mapIndexed { i, f -> f[i] }
            .take(8)

        assertContentEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("addingArguments")
    fun addingReturnsValidValue(a: Byte, b: Byte, expected: Byte) =
        assertTrue((Flags8(a) adding Flags8(b)) == Flags8(expected))

    @ParameterizedTest
    @MethodSource("removingArguments")
    fun removingReturnsValidValue(a: Byte, b: Byte, expected: Byte) =
        assertTrue((Flags8(a) removing Flags8(b)) == Flags8(expected))

    @ParameterizedTest
    @MethodSource("togglingArguments")
    fun togglingReturnsValidValue(a: Byte, b: Byte, expected: Byte) =
        assertTrue((Flags8(a) toggling Flags8(b)) == Flags8(expected))

    @ParameterizedTest
    @MethodSource("settingArguments")
    fun settingReturnsValidValue(a: Byte, setFlags: Byte, toValue: Boolean, expected: Byte) =
        assertTrue((Flags8(a).setting(Flags8(setFlags), toValue)) == Flags8(expected))

    @ParameterizedTest
    @MethodSource("hasAllArguments")
    fun hasAllReturnsValidValue(a: Byte, b: Byte, expected: Boolean) =
        assertTrue(Flags8(a).hasAll(Flags8(b)) == expected)

    @ParameterizedTest
    @MethodSource("hasAnyArguments")
    fun hasAnyReturnsValidValue(a: Byte, b: Byte, expected: Boolean) =
        assertTrue(Flags8(a).hasAny(Flags8(b)) == expected)

    companion object {
        @JvmStatic
        fun addingArguments(): List<Arguments> = listOf(
            Arguments.of(
                0b00000000u.toByte(),
                0b11100001u.toByte(),
                0b11100001u.toByte()
            ),
            Arguments.of(
                0b11100001u.toByte(),
                0b00000000u.toByte(),
                0b11100001u.toByte()
            ),
            Arguments.of(
                0b11100001u.toByte(),
                0b11100001u.toByte(),
                0b11100001u.toByte()
            ),
            Arguments.of(
                0b10100001u.toByte(),
                0b10110111u.toByte(),
                0b10110111u.toByte()
            ),
        )

        @JvmStatic
        fun removingArguments(): List<Arguments> = listOf(
            Arguments.of(
                0b00000000u.toByte(),
                0b11100001u.toByte(),
                0b00000000u.toByte()
            ),
            Arguments.of(
                0b11100001u.toByte(),
                0b00000000u.toByte(),
                0b11100001u.toByte()
            ),
            Arguments.of(
                0b11100001u.toByte(),
                0b11100001u.toByte(),
                0b00000000u.toByte()
            ),
            Arguments.of(
                0b11110111u.toByte(),
                0b11100001u.toByte(),
                0b00010110u.toByte()
            ),
        )

        @JvmStatic
        fun togglingArguments(): List<Arguments> = listOf(
            Arguments.of(
                0b00000000u.toByte(),
                0b11100001u.toByte(),
                0b11100001u.toByte()
            ),
            Arguments.of(
                0b11100001u.toByte(),
                0b00000000u.toByte(),
                0b11100001u.toByte()
            ),
            Arguments.of(
                0b11100001u.toByte(),
                0b11100001u.toByte(),
                0b00000000u.toByte()
            ),
            Arguments.of(
                0b10110111u.toByte(),
                0b11101100u.toByte(),
                0b01011011u.toByte()
            ),
        )

        @JvmStatic
        fun settingArguments(): List<Arguments> = listOf(
            Arguments.of(
                0b00000000u.toByte(),
                0b11100001u.toByte(),
                true,
                0b11100001u.toByte()
            ),
            Arguments.of(
                0b11100001u.toByte(),
                0b11100001u.toByte(),
                false,
                0b00000000u.toByte()
            ),
            Arguments.of(
                0b11100001u.toByte(),
                0b00000000u.toByte(),
                true,
                0b11100001u.toByte()
            ),
            Arguments.of(
                0b11100001u.toByte(),
                0b00000000u.toByte(),
                false,
                0b11100001u.toByte()
            ),
            Arguments.of(
                0b10110101u.toByte(),
                0b11101100u.toByte(),
                true,
                0b11111101u.toByte()
            ),
            Arguments.of(
                0b10110101u.toByte(),
                0b11101100u.toByte(),
                false,
                0b00010001u.toByte()
            )
        )

        @JvmStatic
        fun hasAllArguments(): List<Arguments> = listOf(
            Arguments.of(
                0b11100001u.toByte(),
                0b00000000u.toByte(),
                true,
            ),
            Arguments.of(
                0b00000000u.toByte(),
                0b11100001u.toByte(),
                false,
            ),
            Arguments.of(
                0b11100001u.toByte(),
                0b11100001u.toByte(),
                true,
            ),
            Arguments.of(
                0b11111001u.toByte(),
                0b11100001u.toByte(),
                true,
            ),
            Arguments.of(
                0b11011001u.toByte(),
                0b11100001u.toByte(),
                false,
            )
        )

        @JvmStatic
        fun hasAnyArguments(): List<Arguments> = listOf(
            Arguments.of(
                0b11100001u.toByte(),
                0b00000000u.toByte(),
                false,
            ),
            Arguments.of(
                0b00000000u.toByte(),
                0b11100001u.toByte(),
                false,
            ),
            Arguments.of(
                0b00000001u.toByte(),
                0b11100001u.toByte(),
                true,
            ),
            Arguments.of(
                0b11111000u.toByte(),
                0b11100001u.toByte(),
                true,
            ),
            Arguments.of(
                0b00011000u.toByte(),
                0b11100001u.toByte(),
                false,
            )
        )
    }
}