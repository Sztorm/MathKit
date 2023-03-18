package nonallocmath

import com.sztorm.nonallocmath.ComplexF
import com.sztorm.nonallocmath.Vector2F

/**
 * Compares vector with two floats bitwise. Useful when comparing NaNs.
 */
fun equalsBitwise(vec: Vector2F, x: Float, y: Float) =
    vec.x.toRawBits() == x.toRawBits() &&
    vec.y.toRawBits() == y.toRawBits()

/**
 * Compares complex with real and imaginary parts bitwise. Useful when comparing NaNs.
 */
fun equalsBitwise(complex: ComplexF, real: Float, imaginary: Float) =
    complex.real.toRawBits() == real.toRawBits() &&
    complex.imaginary.toRawBits() == imaginary.toRawBits()