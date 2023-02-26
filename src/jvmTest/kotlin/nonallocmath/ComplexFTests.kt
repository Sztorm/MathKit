package nonallocmath

import com.sztorm.nonallocmath.ComplexF
import org.junit.jupiter.params.*
import org.junit.jupiter.params.provider.*
import kotlin.test.*

class ComplexFTests {

    @ParameterizedTest
    @MethodSource("complices")
    fun complexContentsAreValid(real: Float, imaginary: Float) {
        val complex = ComplexF(real, imaginary)

        // Comparing anything with NaN always returns false. To compare exact contents we need to
        // compare bits.
        assertTrue(
            complex.real.toRawBits() == real.toRawBits() &&
                    complex.imaginary.toRawBits() == imaginary.toRawBits()
        )
    }

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