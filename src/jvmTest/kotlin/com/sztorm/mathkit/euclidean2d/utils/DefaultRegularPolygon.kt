package com.sztorm.mathkit.euclidean2d.utils

import com.sztorm.mathkit.ComplexF
import com.sztorm.mathkit.Vector2F
import com.sztorm.mathkit.euclidean2d.RegularPolygon

class DefaultRegularPolygon(
    override val center: Vector2F,
    override val orientation: ComplexF,
    override val sideLength: Float,
    override val sideCount: Int
) : RegularPolygon {
    override fun copy(center: Vector2F, orientation: ComplexF, sideLength: Float, sideCount: Int) =
        DefaultRegularPolygon(center, orientation, sideLength, sideCount)

    override fun equals(other: Any?): Boolean = other is RegularPolygon &&
            center == other.center &&
            orientation == other.orientation &&
            sideLength == other.sideLength &&
            sideCount == other.sideCount

    override fun hashCode(): Int {
        var result = center.hashCode()
        result = 31 * result + orientation.hashCode()
        result = 31 * result + sideLength.hashCode()
        result = 31 * result + sideCount

        return result
    }
}