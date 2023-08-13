package com.sztorm.lowallocmath

import com.sztorm.lowallocmath.utils.Wrapper
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.*
import org.junit.jupiter.params.provider.*
import kotlin.test.*

class Color32Tests {
    @ParameterizedTest
    @MethodSource("colors")
    fun basicPropertiesAreValid(color: Wrapper<Color32>) {
        val unwrappedColor: Color32 = color.value
        val (r, g, b, a) = unwrappedColor

        assertEquals(unwrappedColor.r, r)
        assertEquals(unwrappedColor.g, g)
        assertEquals(unwrappedColor.b, b)
        assertEquals(unwrappedColor.a, a)
        assertEquals(unwrappedColor.r, unwrappedColor[0])
        assertEquals(unwrappedColor.g, unwrappedColor[1])
        assertEquals(unwrappedColor.b, unwrappedColor[2])
        assertEquals(unwrappedColor.a, unwrappedColor[3])
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
    @MethodSource("getArgs")
    fun getReturnsCorrectValue(color: Wrapper<Color32>, expected: Collection<UByte>) {
        val actual = emptyList<UByte>().toMutableList()

        for (i in 0..3) {
            actual.add(color.value[i])
        }
        assertContentEquals(expected, actual)
    }

    @Test
    fun getThrowsWhenIndexIsOutOfBounds() {
        assertThrows<IndexOutOfBoundsException> { Color32(1u, 255u, 0u, 4u)[-1] }
        assertThrows<IndexOutOfBoundsException> { Color32(1u, 255u, 0u, 4u)[4] }
    }

    @ParameterizedTest
    @MethodSource("lerpArgs")
    @Suppress("SpellCheckingInspection")
    fun lerpReturnsCorrectValue(
        a: Wrapper<Color32>, b: Wrapper<Color32>, t: Float, expected: Wrapper<Color32>
    ) = assertEquals(expected.value, Color32.lerp(a.value, b.value, t))

    companion object {
        @JvmStatic
        fun colors(): List<Arguments> = listOf(
            Arguments.of(Wrapper(Color32(255u, 255u, 255u, 255u))),
            Arguments.of(Wrapper(Color32(0u, 255u, 0u, 255u))),
            Arguments.of(Wrapper(Color32(255u, 0u, 255u, 0u))),
            Arguments.of(Wrapper(Color32(127u, 255u, 255u, 127u))),
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
                Wrapper(Color32(127u, 255u, 58u, 244u)),
            ),
            Arguments.of(
                Wrapper(Color32(127u, 255u, 58u, 244u)),
                Wrapper(127u.toUByte()),
                Wrapper(3u.toUByte()),
                Wrapper(58u.toUByte()),
                Wrapper(199u.toUByte()),
                Wrapper(Color32(127u, 3u, 58u, 199u)),
            ),
            Arguments.of(
                Wrapper(Color32(127u, 255u, 58u, 244u)),
                Wrapper(1u.toUByte()),
                Wrapper(2u.toUByte()),
                Wrapper(3u.toUByte()),
                Wrapper(4u.toUByte()),
                Wrapper(Color32(1u, 2u, 3u, 4u)),
            ),
        )

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
                Wrapper(Color32(127u, 255u, 255u, 127u)),
                listOf<UByte>(127u, 255u, 255u, 127u)
            ),
        )

        @Suppress("SpellCheckingInspection")
        @JvmStatic
        fun lerpArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Color32(0u, 3u, 0u, 0u)),
                Wrapper(Color32(255u, 9u, 0u, 48u)),
                0.5f,
                Wrapper(Color32(127u, 6u, 0u, 24u)),
            ),
            Arguments.of(
                Wrapper(Color32(180u, 90u, 45u, 255u)),
                Wrapper(Color32(90u, 140u, 5u, 255u)),
                0.333333f,
                Wrapper(Color32(150u, 106u, 31u, 255u)),
            ),
        )
    }
}