package com.sztorm.mathkit

import com.sztorm.mathkit.utils.Wrapper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class ColorRGBA32Tests {
    @ParameterizedTest
    @MethodSource("colorComponentsArgs")
    fun colorComponentsReturnCorrectValues(
        color: Wrapper<ColorRGBA32>,
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
    fun toStringReturnsCorrectValue(color: Wrapper<ColorRGBA32>, expected: String) =
        assertEquals(expected, color.value.toString())

    @ParameterizedTest
    @MethodSource("copyArgs")
    fun copyReturnsCorrectValue(
        color: Wrapper<ColorRGBA32>,
        r: Wrapper<UByte>,
        g: Wrapper<UByte>,
        b: Wrapper<UByte>,
        a: Wrapper<UByte>,
        expected: Wrapper<ColorRGBA32>
    ) = assertEquals(expected.value, color.value.copy(r.value, g.value, b.value, a.value))

    @ParameterizedTest
    @MethodSource("componentsArgs")
    fun componentsReturnCorrectValues(
        color: Wrapper<ColorRGBA32>,
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
    fun getReturnsCorrectValue(color: Wrapper<ColorRGBA32>, expected: Collection<UByte>) {
        val actual: List<UByte> = listOf(
            color.value[0], color.value[1], color.value[2], color.value[3]
        )
        assertContentEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("getThrowsExceptionArgs")
    fun getThrowsCorrectException(
        color: Wrapper<ColorRGBA32>, index: Int, expectedExceptionClass: Class<Throwable>
    ) {
        Assertions.assertThrows(expectedExceptionClass) { color.value[index] }
    }

    @Test
    fun sizeBitsReturnsCorrectValue() = assertEquals(ColorRGBA32.SIZE_BITS, 32)

    @Test
    fun sizeBytesReturnsCorrectValue() = assertEquals(ColorRGBA32.SIZE_BYTES, 4)

    @Test
    fun blackReturnsCorrectValue() =
        assertEquals(ColorRGBA32.BLACK, ColorRGBA32(0u, 0u, 0u, 255u))

    @Test
    fun whiteReturnsCorrectValue() =
        assertEquals(ColorRGBA32.WHITE, ColorRGBA32(255u, 255u, 255u, 255u))

    @Test
    fun redReturnsCorrectValue() =
        assertEquals(ColorRGBA32.RED, ColorRGBA32(255u, 0u, 0u, 255u))

    @Test
    fun greenReturnsCorrectValue() =
        assertEquals(ColorRGBA32.GREEN, ColorRGBA32(0u, 255u, 0u, 255u))

    @Test
    fun blueReturnsCorrectValue() =
        assertEquals(ColorRGBA32.BLUE, ColorRGBA32(0u, 0u, 255u, 255u))

    @Test
    fun yellowReturnsCorrectValue() =
        assertEquals(ColorRGBA32.YELLOW, ColorRGBA32(255u, 255u, 0u, 255u))

    @Test
    fun magentaReturnsCorrectValue() =
        assertEquals(ColorRGBA32.MAGENTA, ColorRGBA32(255u, 0u, 255u, 255u))

    @Test
    fun cyanReturnsCorrectValue() =
        assertEquals(ColorRGBA32.CYAN, ColorRGBA32(0u, 255u, 255u, 255u))

    @ParameterizedTest
    @MethodSource("lerpArgs")
    @Suppress("RedundantSuppression", "SpellCheckingInspection")
    fun lerpReturnsCorrectValue(
        a: Wrapper<ColorRGBA32>, b: Wrapper<ColorRGBA32>, t: Float, expected: Wrapper<ColorRGBA32>
    ) {
        assertEquals(expected.value, lerp(a.value, b.value, t))
        assertEquals(expected.value, ColorRGBA32.lerp(a.value, b.value, t))
    }

    companion object {
        @JvmStatic
        fun colorComponentsArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(ColorRGBA32(127u, 255u, 58u, 244u)),
                Wrapper(127u.toUByte()),
                Wrapper(255u.toUByte()),
                Wrapper(58u.toUByte()),
                Wrapper(244u.toUByte())
            ),
            Arguments.of(
                Wrapper(ColorRGBA32(254u, 0u, 253u, 2u)),
                Wrapper(254u.toUByte()),
                Wrapper(0u.toUByte()),
                Wrapper(253u.toUByte()),
                Wrapper(2u.toUByte())
            ),
            Arguments.of(
                Wrapper(ColorRGBA32(1u, 2u, 3u, 4u)),
                Wrapper(1u.toUByte()),
                Wrapper(2u.toUByte()),
                Wrapper(3u.toUByte()),
                Wrapper(4u.toUByte())
            ),
        )

        @JvmStatic
        fun toStringArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(ColorRGBA32(255u, 255u, 255u, 255u)),
                "Color32(r=255, g=255, b=255, a=255)"
            ),
            Arguments.of(
                Wrapper(ColorRGBA32(0u, 255u, 0u, 255u)),
                "Color32(r=0, g=255, b=0, a=255)"
            ),
            Arguments.of(
                Wrapper(ColorRGBA32(255u, 0u, 255u, 0u)),
                "Color32(r=255, g=0, b=255, a=0)"
            ),
            Arguments.of(
                Wrapper(ColorRGBA32(127u, 255u, 255u, 127u)),
                "Color32(r=127, g=255, b=255, a=127)"
            ),
        )

        @JvmStatic
        fun copyArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(ColorRGBA32(127u, 255u, 58u, 244u)),
                Wrapper(127u.toUByte()),
                Wrapper(255u.toUByte()),
                Wrapper(58u.toUByte()),
                Wrapper(244u.toUByte()),
                Wrapper(ColorRGBA32(127u, 255u, 58u, 244u))
            ),
            Arguments.of(
                Wrapper(ColorRGBA32(127u, 255u, 58u, 244u)),
                Wrapper(127u.toUByte()),
                Wrapper(3u.toUByte()),
                Wrapper(58u.toUByte()),
                Wrapper(199u.toUByte()),
                Wrapper(ColorRGBA32(127u, 3u, 58u, 199u))
            ),
            Arguments.of(
                Wrapper(ColorRGBA32(127u, 255u, 58u, 244u)),
                Wrapper(1u.toUByte()),
                Wrapper(2u.toUByte()),
                Wrapper(3u.toUByte()),
                Wrapper(4u.toUByte()),
                Wrapper(ColorRGBA32(1u, 2u, 3u, 4u))
            ),
        )

        @JvmStatic
        fun componentsArgs(): List<Arguments> = colorComponentsArgs()

        @JvmStatic
        fun getArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(ColorRGBA32(255u, 255u, 255u, 255u)),
                listOf<UByte>(255u, 255u, 255u, 255u)
            ),
            Arguments.of(
                Wrapper(ColorRGBA32(0u, 255u, 0u, 255u)),
                listOf<UByte>(0u, 255u, 0u, 255u)
            ),
            Arguments.of(
                Wrapper(ColorRGBA32(255u, 0u, 255u, 0u)),
                listOf<UByte>(255u, 0u, 255u, 0u)
            ),
            Arguments.of(
                Wrapper(ColorRGBA32(1u, 2u, 3u, 4u)),
                listOf<UByte>(1u, 2u, 3u, 4u)
            ),
        )

        @JvmStatic
        fun getThrowsExceptionArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(ColorRGBA32(1u, 255u, 0u, 4u)),
                -1,
                IndexOutOfBoundsException::class.java
            ),
            Arguments.of(
                Wrapper(ColorRGBA32(1u, 255u, 0u, 4u)),
                4,
                IndexOutOfBoundsException::class.java
            ),
            Arguments.of(
                Wrapper(ColorRGBA32(1u, 255u, 0u, 4u)),
                5,
                IndexOutOfBoundsException::class.java
            ),
        )

        @Suppress("RedundantSuppression", "SpellCheckingInspection")
        @JvmStatic
        fun lerpArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(ColorRGBA32(0u, 3u, 0u, 0u)),
                Wrapper(ColorRGBA32(255u, 9u, 0u, 48u)),
                0.5f,
                Wrapper(ColorRGBA32(127u, 6u, 0u, 24u))
            ),
            Arguments.of(
                Wrapper(ColorRGBA32(180u, 90u, 45u, 255u)),
                Wrapper(ColorRGBA32(90u, 140u, 5u, 255u)),
                0.333333f,
                Wrapper(ColorRGBA32(150u, 106u, 31u, 255u))
            ),
        )
    }
}