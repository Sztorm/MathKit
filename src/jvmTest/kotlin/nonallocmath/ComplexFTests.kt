package nonallocmath

import com.sztorm.nonallocmath.ComplexF
import org.junit.jupiter.params.*
import org.junit.jupiter.params.provider.*
import kotlin.test.*

class ComplexFTests {

    @ParameterizedTest
    @MethodSource("complices")
    fun complexContentsAreValid(real: Float, imaginary: Float) =
        assertTrue(equalsBitwise(ComplexF(real, imaginary), real, imaginary))

    companion object {
        @JvmStatic
        fun complices(): List<Arguments> = listOf(
            Arguments.of(2F, 4F),
            Arguments.of(2F, Float.NaN),
            Arguments.of(Float.NEGATIVE_INFINITY, -1F),
            Arguments.of(-1F, Float.NEGATIVE_INFINITY),
        )
    }
}