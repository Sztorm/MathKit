package nonallocmath

import com.sztorm.nonallocmath.Color32
import org.junit.jupiter.params.*
import org.junit.jupiter.params.provider.*
import kotlin.test.*

class Color32Tests {
    @ParameterizedTest
    @MethodSource("colors")
    fun colorContentsAreValid(r: Byte, g: Byte, b: Byte, a: Byte) =
        colorContentsAreValid(r.toUByte(), g.toUByte(), b.toUByte(), a.toUByte())

    private fun colorContentsAreValid(r: UByte, g: UByte, b: UByte, a: UByte) {
        val color = Color32(r, g, b, a)

        assertTrue(actual =
            color.r == r &&
            color.g == g &&
            color.b == b &&
            color.a == a)
    }

    companion object {
        @JvmStatic
        fun colors(): List<Arguments> = listOf(
            Arguments.of(255.toByte(), 255.toByte(), 255.toByte(), 255.toByte()),
            Arguments.of(0.toByte(), 255.toByte(), 0.toByte(), 255.toByte()),
            Arguments.of(255.toByte(), 0.toByte(), 255.toByte(), 0.toByte()),
            Arguments.of(127.toByte(), 255.toByte(), 255.toByte(), 127.toByte()),
        )
    }
}