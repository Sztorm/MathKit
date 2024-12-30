package com.sztorm.mathkit.euclidean2d.utils

import com.sztorm.mathkit.ComplexF
import com.sztorm.mathkit.Vector2F
import com.sztorm.mathkit.euclidean2d.LineSegment

class DefaultLineSegment(
    override val center: Vector2F,
    override val orientation: ComplexF,
    override val length: Float
) : LineSegment {
    override fun copy(center: Vector2F, orientation: ComplexF, length: Float) =
        DefaultLineSegment(center, orientation, length)

    override fun equals(other: Any?): Boolean = other is LineSegment &&
            center == other.center && orientation == other.orientation && length == other.length

    override fun hashCode(): Int {
        var result = center.hashCode()
        result = 31 * result + orientation.hashCode()
        result = 31 * result + length.hashCode()

        return result
    }
}