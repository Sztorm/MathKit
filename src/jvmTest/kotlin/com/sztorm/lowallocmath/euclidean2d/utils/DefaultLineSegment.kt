package com.sztorm.lowallocmath.euclidean2d.utils

import com.sztorm.lowallocmath.Vector2F
import com.sztorm.lowallocmath.euclidean2d.LineSegment

class DefaultLineSegment(
    override val pointA: Vector2F,
    override val pointB: Vector2F
) : LineSegment {
    override fun copy(pointA: Vector2F, pointB: Vector2F) = DefaultLineSegment(pointA, pointB)

    override fun equals(other: Any?): Boolean = other is LineSegment &&
            pointA == other.pointA &&
            pointB == other.pointB

    override fun hashCode(): Int {
        var result = pointA.hashCode()
        result = 31 * result + pointB.hashCode()

        return result
    }
}