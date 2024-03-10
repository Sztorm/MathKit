package com.sztorm.lowallocmath.euclidean2d.utils

import com.sztorm.lowallocmath.Vector2F
import com.sztorm.lowallocmath.euclidean2d.Triangle

class DefaultTriangle(
    override val pointA: Vector2F,
    override val pointB: Vector2F,
    override val pointC: Vector2F
) : Triangle {
    override fun copy(pointA: Vector2F, pointB: Vector2F, pointC: Vector2F) =
        DefaultTriangle(pointA, pointB, pointC)

    override fun equals(other: Any?): Boolean = other is Triangle &&
            pointA == other.pointA &&
            pointB == other.pointB &&
            pointC == other.pointC

    override fun hashCode(): Int {
        var result = pointA.hashCode()
        result = 31 * result + pointB.hashCode()
        result = 31 * result + pointC.hashCode()

        return result
    }
}