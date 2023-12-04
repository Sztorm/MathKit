package com.sztorm.lowallocmath.world2d.utils

import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F
import com.sztorm.lowallocmath.world2d.Rectangle

class DefaultRectangle(
    override val center: Vector2F,
    override val orientation: ComplexF,
    override val width: Float,
    override val height: Float
) : Rectangle {
    override fun copy(center: Vector2F, orientation: ComplexF, width: Float, height: Float) =
        DefaultRectangle(center, orientation, width, height)

    override fun equals(other: Any?): Boolean = other is Rectangle &&
            center == other.center &&
            orientation == other.orientation &&
            width == other.width &&
            height == other.height

    override fun hashCode(): Int {
        var result = center.hashCode()
        result = 31 * result + orientation.hashCode()
        result = 31 * result + width.hashCode()
        result = 31 * result + height.hashCode()

        return result
    }
}