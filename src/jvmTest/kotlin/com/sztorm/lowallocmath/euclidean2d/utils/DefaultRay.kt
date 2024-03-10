package com.sztorm.lowallocmath.euclidean2d.utils

import com.sztorm.lowallocmath.Vector2F
import com.sztorm.lowallocmath.euclidean2d.Ray

class DefaultRay(
    override val origin: Vector2F,
    override val direction: Vector2F
) : Ray {
    override fun copy(origin: Vector2F, direction: Vector2F) = DefaultRay(origin, direction)

    override fun equals(other: Any?): Boolean = other is Ray &&
            origin == other.origin &&
            direction == other.direction

    override fun hashCode(): Int {
        var result = origin.hashCode()
        result = 31 * result + direction.hashCode()

        return result
    }
}