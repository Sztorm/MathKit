package nonallocmath

import com.sztorm.nonallocmath.Flags16
import nonallocmath.utils.Wrapper
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class Flags16Tests {

    @Test
    fun basicPropertiesAreValid() {
        val flags = Flags16.fromUShort(0b11100000_00001101u)

        assertEquals(16, flags.size)
        assertEquals(15, flags.lastIndex)
        assertEquals(false, flags.isEmpty())
    }

    @ParameterizedTest
    @MethodSource("containsAllArgs")
    fun containsAllReturnsCorrectValue(
        flags: Wrapper<Flags16>, elements: Collection<Boolean>, expected: Boolean
    ) = assertEquals(expected, flags.value.containsAll(elements))

    @ParameterizedTest
    @MethodSource("addingArgs")
    fun addingReturnsCorrectValue(
        a: Wrapper<Flags16>, b: Wrapper<Flags16>, expected: Wrapper<Flags16>
    ) = assertEquals(expected.value, a.value adding b.value)

    @ParameterizedTest
    @MethodSource("removingArgs")
    fun removingReturnsCorrectValue(
        a: Wrapper<Flags16>, b: Wrapper<Flags16>, expected: Wrapper<Flags16>
    ) = assertEquals(expected.value, a.value removing b.value)

    @ParameterizedTest
    @MethodSource("togglingArgs")
    fun togglingReturnsCorrectValue(
        a: Wrapper<Flags16>, b: Wrapper<Flags16>, expected: Wrapper<Flags16>
    ) = assertEquals(expected.value, a.value toggling b.value)

    @ParameterizedTest
    @MethodSource("settingArgs")
    fun settingReturnsCorrectValue(
        a: Wrapper<Flags16>, setFlags: Wrapper<Flags16>, toValue: Boolean, expected: Wrapper<Flags16>
    ) = assertEquals(expected.value, a.value.setting(setFlags.value, toValue))

    @ParameterizedTest
    @MethodSource("hasAllArgs")
    fun hasAllReturnsCorrectValue(a: Wrapper<Flags16>, b: Wrapper<Flags16>, expected: Boolean) =
        assertEquals(expected, a.value.hasAll(b.value))

    @ParameterizedTest
    @MethodSource("hasAnyArgs")
    fun hasAnyReturnsCorrectValue(a: Wrapper<Flags16>, b: Wrapper<Flags16>, expected: Boolean) =
        assertEquals(expected, a.value.hasAny(b.value))

    @ParameterizedTest
    @MethodSource("containsArgs")
    fun containsReturnsCorrectValue(flags: Wrapper<Flags16>, element: Boolean, expected: Boolean) =
        assertEquals(expected, flags.value.contains(element))

    @ParameterizedTest
    @MethodSource("getArgs")
    fun getReturnsCorrectValue(flags: Wrapper<Flags16>, expected: Collection<Boolean>) {
        val actual = emptyList<Boolean>().toMutableList()

        for (i in 0..flags.value.lastIndex) {
            actual.add(flags.value[i])
        }
        assertContentEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("iteratorArgs")
    fun iteratorReturnsCorrectValue(flags: Wrapper<Flags16>, expected: Iterator<Boolean>) {
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
        val none = Flags16.NONE

        for (i in 0..15) {
            assertFalse(none[i])
        }
    }

    @Test
    fun allValueReturnsCorrectValue() {
        val all = Flags16.ALL

        for (i in 0..15) {
            assertTrue(all[i])
        }
    }

    companion object {
        @JvmStatic
        fun containsAllArgs(): List<Arguments> {
            return listOf(
                Arguments.of(
                    Wrapper(Flags16.fromUShort(0b11100000_00001101u)),
                    emptyList<Boolean>(),
                    true
                ),
                Arguments.of(
                    Wrapper(Flags16.fromUShort(0b11100000_00001101u)),
                    listOf(true, false),
                    true
                ),
                Arguments.of(
                    Wrapper(Flags16.fromUShort(0b11100000_00001101u)),
                    listOf(false, false),
                    true
                ),
                Arguments.of(
                    Wrapper(Flags16.fromUShort(0b11100000_00001101u)),
                    listOf(true, true),
                    true
                ),
                Arguments.of(
                    Wrapper(Flags16.fromUShort(0b00000000_00000000u)),
                    listOf(true, false),
                    false
                ),
                Arguments.of(
                    Wrapper(Flags16.fromUShort(0b00000000_00000000u)),
                    listOf(false, false),
                    true
                ),
                Arguments.of(
                    Wrapper(Flags16.fromUShort(0b00000000_00000000u)),
                    listOf(true, true),
                    false
                ),
                Arguments.of(
                    Wrapper(Flags16.fromUShort(0b11111111_11111111u)),
                    listOf(true, false),
                    false
                ),
                Arguments.of(
                    Wrapper(Flags16.fromUShort(0b11111111_11111111u)),
                    listOf(false, false),
                    false
                ),
                Arguments.of(
                    Wrapper(Flags16.fromUShort(0b11111111_11111111u)),
                    listOf(true, true),
                    true
                ),
            )
        }

        @JvmStatic
        fun addingArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Flags16.fromUShort(0b00000000_00000000u)),
                Wrapper(Flags16.fromUShort(0b11100000_00001101u)),
                Wrapper(Flags16.fromUShort(0b11100000_00001101u))
            ),
            Arguments.of(
                Wrapper(Flags16.fromUShort(0b11100000_00001101u)),
                Wrapper(Flags16.fromUShort(0b00000000_00000000u)),
                Wrapper(Flags16.fromUShort(0b11100000_00001101u))
            ),
            Arguments.of(
                Wrapper(Flags16.fromUShort(0b11100000_00001101u)),
                Wrapper(Flags16.fromUShort(0b11100000_00001101u)),
                Wrapper(Flags16.fromUShort(0b11100000_00001101u))
            ),
            Arguments.of(
                Wrapper(Flags16.fromUShort(0b11100000_00001101u)),
                Wrapper(Flags16.fromUShort(0b11110111_10110101u)),
                Wrapper(Flags16.fromUShort(0b11110111_10111101u))
            ),
        )

        @JvmStatic
        fun removingArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Flags16.fromUShort(0b00000000_00000000u)),
                Wrapper(Flags16.fromUShort(0b11100000_00001101u)),
                Wrapper(Flags16.fromUShort(0b00000000_00000000u))
            ),
            Arguments.of(
                Wrapper(Flags16.fromUShort(0b11100000_00001101u)),
                Wrapper(Flags16.fromUShort(0b00000000_00000000u)),
                Wrapper(Flags16.fromUShort(0b11100000_00001101u))
            ),
            Arguments.of(
                Wrapper(Flags16.fromUShort(0b11100000_00001101u)),
                Wrapper(Flags16.fromUShort(0b11100000_00001101u)),
                Wrapper(Flags16.fromUShort(0b00000000_00000000u))
            ),
            Arguments.of(
                Wrapper(Flags16.fromUShort(0b11110111_11111101u)),
                Wrapper(Flags16.fromUShort(0b11100000_00001101u)),
                Wrapper(Flags16.fromUShort(0b00010111_11110000u))
            ),
        )

        @JvmStatic
        fun togglingArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Flags16.fromUShort(0b00000000_00000000u)),
                Wrapper(Flags16.fromUShort(0b11100000_00001101u)),
                Wrapper(Flags16.fromUShort(0b11100000_00001101u))
            ),
            Arguments.of(
                Wrapper(Flags16.fromUShort(0b11100000_00001101u)),
                Wrapper(Flags16.fromUShort(0b00000000_00000000u)),
                Wrapper(Flags16.fromUShort(0b11100000_00001101u))
            ),
            Arguments.of(
                Wrapper(Flags16.fromUShort(0b11100000_00001101u)),
                Wrapper(Flags16.fromUShort(0b11100000_00001101u)),
                Wrapper(Flags16.fromUShort(0b00000000_00000000u))
            ),
            Arguments.of(
                Wrapper(Flags16.fromUShort(0b10110111_10001101u)),
                Wrapper(Flags16.fromUShort(0b11101100_01101101u)),
                Wrapper(Flags16.fromUShort(0b01011011_11100000u))
            ),
        )

        @JvmStatic
        fun settingArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Flags16.fromUShort(0b00000000_00000000u)),
                Wrapper(Flags16.fromUShort(0b11100000_00001101u)),
                true,
                Wrapper(Flags16.fromUShort(0b11100000_00001101u))
            ),
            Arguments.of(
                Wrapper(Flags16.fromUShort(0b11100000_00001101u)),
                Wrapper(Flags16.fromUShort(0b11100000_00001101u)),
                false,
                Wrapper(Flags16.fromUShort(0b00000000_00000000u))
            ),
            Arguments.of(
                Wrapper(Flags16.fromUShort(0b11100000_00001101u)),
                Wrapper(Flags16.fromUShort(0b00000000_00000000u)),
                true,
                Wrapper(Flags16.fromUShort(0b11100000_00001101u))
            ),
            Arguments.of(
                Wrapper(Flags16.fromUShort(0b11100000_00001101u)),
                Wrapper(Flags16.fromUShort(0b00000000_00000000u)),
                false,
                Wrapper(Flags16.fromUShort(0b11100000_00001101u))
            ),
            Arguments.of(
                Wrapper(Flags16.fromUShort(0b10110111_10001101u)),
                Wrapper(Flags16.fromUShort(0b11101100_01101101u)),
                true,
                Wrapper(Flags16.fromUShort(0b11111111_11101101u))
            ),
            Arguments.of(
                Wrapper(Flags16.fromUShort(0b10110111_10001101u)),
                Wrapper(Flags16.fromUShort(0b11101100_01101101u)),
                false,
                Wrapper(Flags16.fromUShort(0b00010011_10000000u))
            )
        )

        @JvmStatic
        fun hasAllArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Flags16.fromUShort(0b11100000_00001101u)),
                Wrapper(Flags16.fromUShort(0b00000000_00000000u)),
                true,
            ),
            Arguments.of(
                Wrapper(Flags16.fromUShort(0b00000000_00000000u)),
                Wrapper(Flags16.fromUShort(0b11100000_00001101u)),
                false,
            ),
            Arguments.of(
                Wrapper(Flags16.fromUShort(0b11100000_00001101u)),
                Wrapper(Flags16.fromUShort(0b11100000_00001101u)),
                true,
            ),
            Arguments.of(
                Wrapper(Flags16.fromUShort(0b11111000_01101111u)),
                Wrapper(Flags16.fromUShort(0b11100000_00001101u)),
                true,
            ),
            Arguments.of(
                Wrapper(Flags16.fromUShort(0b11011000_01101111u)),
                Wrapper(Flags16.fromUShort(0b11100000_00001101u)),
                false,
            )
        )

        @JvmStatic
        fun hasAnyArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Flags16.fromUShort(0b11100000_00001101u)),
                Wrapper(Flags16.fromUShort(0b00000000_00000000u)),
                false,
            ),
            Arguments.of(
                Wrapper(Flags16.fromUShort(0b00000000_00000000u)),
                Wrapper(Flags16.fromUShort(0b11100000_00001101u)),
                false,
            ),
            Arguments.of(
                Wrapper(Flags16.fromUShort(0b00000000_00000001u)),
                Wrapper(Flags16.fromUShort(0b11100000_00001101u)),
                true,
            ),
            Arguments.of(
                Wrapper(Flags16.fromUShort(0b11111000_01101111u)),
                Wrapper(Flags16.fromUShort(0b11100000_00001101u)),
                true,
            ),
            Arguments.of(
                Wrapper(Flags16.fromUShort(0b00011000_01100010u)),
                Wrapper(Flags16.fromUShort(0b11100000_00001101u)),
                false,
            )
        )

        @JvmStatic
        fun containsArgs(): List<Arguments> = listOf(
            Arguments.of(Wrapper(Flags16.fromUShort(0b11100000_00001101u)), true, true),
            Arguments.of(Wrapper(Flags16.fromUShort(0b11100000_00001101u)), false, true),
            Arguments.of(Wrapper(Flags16.fromUShort(0b00000000_00000000u)), true, false),
            Arguments.of(Wrapper(Flags16.fromUShort(0b00000000_00000000u)), false, true),
            Arguments.of(Wrapper(Flags16.fromUShort(0b11111111_11111111u)), true, true),
            Arguments.of(Wrapper(Flags16.fromUShort(0b11111111_11111111u)), false, false),
        )

        @JvmStatic
        fun getArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Flags16.fromUShort(0b11100000_00001101u)),
                listOf(
                    true, true, true, false, false, false, false, false,
                    false, false, false, false, true, true, false, true,
                ).asReversed()
            ),
            Arguments.of(
                Wrapper(Flags16.fromUShort(0b11110111_11111101u)),
                listOf(
                    true, true, true, true, false, true, true, true,
                    true, true, true, true, true, true, false, true,
                ).asReversed()
            ),
            Arguments.of(
                Wrapper(Flags16.fromUShort(0b00000000_00000000u)),
                listOf(
                    false, false, false, false, false, false, false, false,
                    false, false, false, false, false, false, false, false,
                )
            ),
            Arguments.of(
                Wrapper(Flags16.fromUShort(0b11111111_11111111u)),
                listOf(
                    true, true, true, true, true, true, true, true,
                    true, true, true, true, true, true, true, true,
                )
            ),
        )

        @JvmStatic
        fun iteratorArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Flags16.fromUShort(0b11100000_00001101u)),
                listOf(
                    true, true, true, false, false, false, false, false,
                    false, false, false, false, true, true, false, true,
                ).asReversed().iterator()
            ),
            Arguments.of(
                Wrapper(Flags16.fromUShort(0b11110111_11111101u)),
                listOf(
                    true, true, true, true, false, true, true, true,
                    true, true, true, true, true, true, false, true,
                ).asReversed().iterator()
            ),
            Arguments.of(
                Wrapper(Flags16.fromUShort(0b00000000_00000000u)),
                listOf(
                    false, false, false, false, false, false, false, false,
                    false, false, false, false, false, false, false, false,
                ).iterator()
            ),
            Arguments.of(
                Wrapper(Flags16.fromUShort(0b11111111_11111111u)),
                listOf(
                    true, true, true, true, true, true, true, true,
                    true, true, true, true, true, true, true, true,
                ).iterator()
            ),
        )
    }
}