package com.sztorm.mathkit.euclidean2d.utils

import com.sztorm.mathkit.ComplexF
import com.sztorm.mathkit.Vector2F
import com.sztorm.mathkit.euclidean2d.Annulus

class DefaultAnnulus(
    override val center: Vector2F,
    override val orientation: ComplexF,
    override val outerRadius: Float,
    override val innerRadius: Float
) : Annulus {
    override fun copy(
        center: Vector2F, orientation: ComplexF, outerRadius: Float, innerRadius: Float
    ) = DefaultAnnulus(center, orientation, outerRadius, innerRadius)

    override fun equals(other: Any?): Boolean = other is Annulus &&
            center == other.center &&
            orientation == other.orientation &&
            outerRadius == other.outerRadius &&
            innerRadius == other.innerRadius

    override fun hashCode(): Int {
        var result = center.hashCode()
        result = 31 * result + orientation.hashCode()
        result = 31 * result + outerRadius.hashCode()
        result = 31 * result + innerRadius.hashCode()

        return result
    }
}