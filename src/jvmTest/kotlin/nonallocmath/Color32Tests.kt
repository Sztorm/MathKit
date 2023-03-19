package nonallocmath

import com.sztorm.nonallocmath.Color32
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.*
import org.junit.jupiter.params.provider.*
import kotlin.test.*

class Color32Tests {
    @ParameterizedTest
    @MethodSource("colors")
    fun colorContentsAreValid(r: Byte, g: Byte, b: Byte, a: Byte) =
        colorContentsAreValid(r.toUByte(), g.toUByte(), b.toUByte(), a.toUByte())

    private fun colorContentsAreValid(r: UByte, g: UByte, b: UByte, a: UByte) =
        assertTrue(equals(Color32(r, g, b, a), r, g, b, a))

    @ParameterizedTest
    @MethodSource("colors")
    fun basicColorPropertiesAreValid(r: UByte, g: UByte, b: UByte, a: UByte) {
        val color = Color32(r, g, b, a)
        val (r0, g0, b0, a0) = color

        assertTrue(
            equals(color, r, g, b, a) &&
                    color.r == color[0] &&
                    color.g == color[1] &&
                    color.b == color[2] &&
                    color.a == color[3] &&
                    color.r == r0 &&
                    color.g == g0 &&
                    color.b == b0 &&
                    color.a == a0
        )
    }

    @Test
    fun getThrowsWhenIndexIsOutOfBounds() {
        assertThrows<IndexOutOfBoundsException> { Color32(1u, 255u, 0u, 4u)[-1] }
        assertThrows<IndexOutOfBoundsException> { Color32(1u, 255u, 0u, 4u)[4] }
    }

    @Test
    fun lerpReturnsCorrectValue() =
        assertEquals(
            expected = Color32(127u, 6u, 0u, 24u),
            actual = Color32.lerp(
                a = Color32(0u, 3u, 0u, 0u),
                b = Color32(255u, 9u, 0u, 48u),
                t = 0.5f
            )
        )

    companion object {
        @JvmStatic
        fun equals(color: Color32, r: UByte, g: UByte, b: UByte, a: UByte) =
            color.r == r && color.g == g && color.b == b && color.a == a

        @JvmStatic
        fun colors(): List<Arguments> = listOf(
            Arguments.of(255.toByte(), 255.toByte(), 255.toByte(), 255.toByte()),
            Arguments.of(0.toByte(), 255.toByte(), 0.toByte(), 255.toByte()),
            Arguments.of(255.toByte(), 0.toByte(), 255.toByte(), 0.toByte()),
            Arguments.of(127.toByte(), 255.toByte(), 255.toByte(), 127.toByte()),
        )
    }
}