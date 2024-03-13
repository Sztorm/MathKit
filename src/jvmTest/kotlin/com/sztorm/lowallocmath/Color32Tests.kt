package com.sztorm.lowallocmath

import com.sztorm.lowallocmath.utils.Wrapper
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class Color32Tests {
    @ParameterizedTest
    @MethodSource("colorComponentsArgs")
    fun colorComponentsReturnCorrectValues(
        color: Wrapper<Color32>,
        expectedR: Wrapper<UByte>,
        expectedG: Wrapper<UByte>,
        expectedB: Wrapper<UByte>,
        expectedA: Wrapper<UByte>
    ) {
        assertEquals(expectedR.value, color.value.r)
        assertEquals(expectedG.value, color.value.g)
        assertEquals(expectedB.value, color.value.b)
        assertEquals(expectedA.value, color.value.a)
    }

    @ParameterizedTest
    @MethodSource("toStringArgs")
    fun toStringReturnsCorrectValue(color: Wrapper<Color32>, expected: String) =
        assertEquals(expected, color.value.toString())

    @ParameterizedTest
    @MethodSource("copyArgs")
    fun copyReturnsCorrectValue(
        color: Wrapper<Color32>,
        r: Wrapper<UByte>,
        g: Wrapper<UByte>,
        b: Wrapper<UByte>,
        a: Wrapper<UByte>,
        expected: Wrapper<Color32>
    ) = assertEquals(expected.value, color.value.copy(r.value, g.value, b.value, a.value))

    @ParameterizedTest
    @MethodSource("componentsArgs")
    fun componentsReturnCorrectValues(
        color: Wrapper<Color32>,
        expectedComponent1: Wrapper<UByte>,
        expectedComponent2: Wrapper<UByte>,
        expectedComponent3: Wrapper<UByte>,
        expectedComponent4: Wrapper<UByte>,
    ) {
        val (
            actualComponent1: UByte,
            actualComponent2: UByte,
            actualComponent3: UByte,
            actualComponent4: UByte
        ) = color.value

        assertEquals(expectedComponent1.value, actualComponent1)
        assertEquals(expectedComponent2.value, actualComponent2)
        assertEquals(expectedComponent3.value, actualComponent3)
        assertEquals(expectedComponent4.value, actualComponent4)
    }

    @ParameterizedTest
    @MethodSource("getArgs")
    fun getReturnsCorrectValue(color: Wrapper<Color32>, expected: Collection<UByte>) {
        val actual: List<UByte> = listOf(
            color.value[0], color.value[1], color.value[2], color.value[3]
        )
        assertContentEquals(expected, actual)
    }

    @Test
    fun getThrowsWhenIndexIsOutOfBounds() {
        assertThrows<IndexOutOfBoundsException> { Color32(1u, 255u, 0u, 4u)[-1] }
        assertThrows<IndexOutOfBoundsException> { Color32(1u, 255u, 0u, 4u)[4] }
    }

    @Test
    fun sizeBitsReturnsCorrectValue() = assertEquals(Color32.SIZE_BITS, 32)

    @Test
    fun sizeBytesReturnsCorrectValue() = assertEquals(Color32.SIZE_BYTES, 4)

    @Test
    fun blackReturnsCorrectValue() =
        assertEquals(Color32.BLACK, Color32(0u, 0u, 0u, 255u))

    @Test
    fun whiteReturnsCorrectValue() =
        assertEquals(Color32.WHITE, Color32(255u, 255u, 255u, 255u))

    @Test
    fun redReturnsCorrectValue() =
        assertEquals(Color32.RED, Color32(255u, 0u, 0u, 255u))

    @Test
    fun greenReturnsCorrectValue() =
        assertEquals(Color32.GREEN, Color32(0u, 255u, 0u, 255u))

    @Test
    fun blueReturnsCorrectValue() =
        assertEquals(Color32.BLUE, Color32(0u, 0u, 255u, 255u))

    @Test
    fun yellowReturnsCorrectValue() =
        assertEquals(Color32.YELLOW, Color32(255u, 255u, 0u, 255u))

    @Test
    fun magentaReturnsCorrectValue() =
        assertEquals(Color32.MAGENTA, Color32(255u, 0u, 255u, 255u))

    @Test
    fun cyanReturnsCorrectValue() =
        assertEquals(Color32.CYAN, Color32(0u, 255u, 255u, 255u))

    @ParameterizedTest
    @MethodSource("lerpArgs")
    @Suppress("SpellCheckingInspection")
    fun lerpReturnsCorrectValue(
        a: Wrapper<Color32>, b: Wrapper<Color32>, t: Float, expected: Wrapper<Color32>
    ) = assertEquals(expected.value, Color32.lerp(a.value, b.value, t))

    companion object {
        @JvmStatic
        fun colorComponentsArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Color32(127u, 255u, 58u, 244u)),
                Wrapper(127u.toUByte()),
                Wrapper(255u.toUByte()),
                Wrapper(58u.toUByte()),
                Wrapper(244u.toUByte())
            ),
            Arguments.of(
                Wrapper(Color32(254u, 0u, 253u, 2u)),
                Wrapper(254u.toUByte()),
                Wrapper(0u.toUByte()),
                Wrapper(253u.toUByte()),
                Wrapper(2u.toUByte())
            ),
            Arguments.of(
                Wrapper(Color32(1u, 2u, 3u, 4u)),
                Wrapper(1u.toUByte()),
                Wrapper(2u.toUByte()),
                Wrapper(3u.toUByte()),
                Wrapper(4u.toUByte())
            ),
        )

        @JvmStatic
        fun toStringArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Color32(255u, 255u, 255u, 255u)),
                "Color32(r=255, g=255, b=255, a=255)"
            ),
            Arguments.of(
                Wrapper(Color32(0u, 255u, 0u, 255u)),
                "Color32(r=0, g=255, b=0, a=255)"
            ),
            Arguments.of(
                Wrapper(Color32(255u, 0u, 255u, 0u)),
                "Color32(r=255, g=0, b=255, a=0)"
            ),
            Arguments.of(
                Wrapper(Color32(127u, 255u, 255u, 127u)),
                "Color32(r=127, g=255, b=255, a=127)"
            ),
        )

        @JvmStatic
        fun copyArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Color32(127u, 255u, 58u, 244u)),
                Wrapper(127u.toUByte()),
                Wrapper(255u.toUByte()),
                Wrapper(58u.toUByte()),
                Wrapper(244u.toUByte()),
                Wrapper(Color32(127u, 255u, 58u, 244u))
            ),
            Arguments.of(
                Wrapper(Color32(127u, 255u, 58u, 244u)),
                Wrapper(127u.toUByte()),
                Wrapper(3u.toUByte()),
                Wrapper(58u.toUByte()),
                Wrapper(199u.toUByte()),
                Wrapper(Color32(127u, 3u, 58u, 199u))
            ),
            Arguments.of(
                Wrapper(Color32(127u, 255u, 58u, 244u)),
                Wrapper(1u.toUByte()),
                Wrapper(2u.toUByte()),
                Wrapper(3u.toUByte()),
                Wrapper(4u.toUByte()),
                Wrapper(Color32(1u, 2u, 3u, 4u))
            ),
        )

        @JvmStatic
        fun componentsArgs(): List<Arguments> = colorComponentsArgs()

        @JvmStatic
        fun getArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Color32(255u, 255u, 255u, 255u)),
                listOf<UByte>(255u, 255u, 255u, 255u)
            ),
            Arguments.of(
                Wrapper(Color32(0u, 255u, 0u, 255u)),
                listOf<UByte>(0u, 255u, 0u, 255u)
            ),
            Arguments.of(
                Wrapper(Color32(255u, 0u, 255u, 0u)),
                listOf<UByte>(255u, 0u, 255u, 0u)
            ),
            Arguments.of(
                Wrapper(Color32(1u, 2u, 3u, 4u)),
                listOf<UByte>(1u, 2u, 3u, 4u)
            ),
        )

        @Suppress("SpellCheckingInspection")
        @JvmStatic
        fun lerpArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Color32(0u, 3u, 0u, 0u)),
                Wrapper(Color32(255u, 9u, 0u, 48u)),
                0.5f,
                Wrapper(Color32(127u, 6u, 0u, 24u))
            ),
            Arguments.of(
                Wrapper(Color32(180u, 90u, 45u, 255u)),
                Wrapper(Color32(90u, 140u, 5u, 255u)),
                0.333333f,
                Wrapper(Color32(150u, 106u, 31u, 255u))
            ),
        )
    }
}