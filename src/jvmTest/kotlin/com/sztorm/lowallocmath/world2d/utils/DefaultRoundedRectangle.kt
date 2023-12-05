package com.sztorm.lowallocmath.world2d.utils

import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F
import com.sztorm.lowallocmath.world2d.RoundedRectangle

class DefaultRoundedRectangle(
    override val center: Vector2F,
    override val orientation: ComplexF,
    override val width: Float,
    override val height: Float,
    override val cornerRadius: Float
) : RoundedRectangle {
    override fun copy(
        center: Vector2F, orientation: ComplexF, width: Float, height: Float, cornerRadius: Float
    ) = DefaultRoundedRectangle(center, orientation, width, height, cornerRadius)

    override fun equals(other: Any?): Boolean = other is RoundedRectangle &&
            center == other.center &&
            orientation == other.orientation &&
            width == other.width &&
            height == other.height &&
            cornerRadius == other.cornerRadius

    override fun hashCode(): Int {
        var result = center.hashCode()
        result = 31 * result + orientation.hashCode()
        result = 31 * result + width.hashCode()
        result = 31 * result + height.hashCode()
        result = 31 * result + cornerRadius.hashCode()

        return result
    }
}