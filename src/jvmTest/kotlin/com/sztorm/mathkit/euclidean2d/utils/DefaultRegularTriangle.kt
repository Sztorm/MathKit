package com.sztorm.mathkit.euclidean2d.utils

import com.sztorm.mathkit.ComplexF
import com.sztorm.mathkit.Vector2F
import com.sztorm.mathkit.euclidean2d.RegularTriangle

class DefaultRegularTriangle(
    override val center: Vector2F,
    override val orientation: ComplexF,
    override val sideLength: Float
) : RegularTriangle {
    override fun copy(center: Vector2F, orientation: ComplexF, sideLength: Float) =
        DefaultRegularTriangle(center, orientation, sideLength)

    override fun equals(other: Any?): Boolean = other is RegularTriangle &&
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