package com.sztorm.lowallocmath.world2d.utils

import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F
import com.sztorm.lowallocmath.world2d.Circle

class DefaultCircle(
    override val center: Vector2F,
    override val orientation: ComplexF,
    override val radius: Float
) : Circle {
    override fun copy(center: Vector2F, orientation: ComplexF, radius: Float) =
        DefaultCircle(center, orientation, radius)

    override fun equals(other: Any?): Boolean = other is Circle &&
            center == other.center &&
            orientation == other.orientation &&
            radius == other.radius

    override fun hashCode(): Int {
        var result = center.hashCode()
        result = 31 * result + orientation.hashCode()
        result = 31 * result + radius.hashCode()

        return result
    }
}