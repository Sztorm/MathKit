package com.sztorm.lowallocmath.world2d.utils

import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F
import com.sztorm.lowallocmath.world2d.Square

class DefaultSquare(
    override val center: Vector2F,
    override val orientation: ComplexF,
    override val sideLength: Float
) : Square {
    override fun copy(center: Vector2F, orientation: ComplexF, sideLength: Float) =
        DefaultSquare(center, orientation, sideLength)

    override fun equals(other: Any?): Boolean = other is Square &&
            center == other.center &&
            orientation == other.orientation &&
            sideLength == other.sideLength

    override fun hashCode(): Int {
        var result = center.hashCode()
        result = 31 * result + orientation.hashCode()
        result = 31 * result + sideLength.hashCode()

        return result
    }
}