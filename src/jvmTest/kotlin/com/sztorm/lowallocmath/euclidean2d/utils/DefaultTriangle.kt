package com.sztorm.lowallocmath.euclidean2d.utils

import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F
import com.sztorm.lowallocmath.euclidean2d.Triangle

class DefaultTriangle(
    override val centroid: Vector2F,
    override val orientation: ComplexF,
    override val originPointA: Vector2F,
    override val originPointB: Vector2F,
    override val originPointC: Vector2F
) : Triangle {
    override fun copy(
        centroid: Vector2F,
        orientation: ComplexF,
        originPointA: Vector2F,
        originPointB: Vector2F,
        originPointC: Vector2F
    ) = DefaultTriangle(centroid, orientation, originPointA, originPointB, originPointC)

    override fun equals(other: Any?): Boolean = other is Triangle &&
            centroid == other.centroid &&
            orientation == other.orientation &&
            originPointA == other.originPointA &&
            originPointB == other.originPointB &&
            originPointC == other.originPointC

    override fun hashCode(): Int {
        var result: Int = centroid.hashCode()
        result = 31 * result + orientation.hashCode()
        result = 31 * result + originPointA.hashCode()
        result = 31 * result + originPointB.hashCode()
        result = 31 * result + originPointC.hashCode()

        return result
    }
}