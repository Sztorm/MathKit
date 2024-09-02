package com.sztorm.lowallocmath.euclidean2d.utils

import com.sztorm.lowallocmath.Vector2F
import com.sztorm.lowallocmath.euclidean2d.Triangle

class DefaultTriangle(
    override val centroid: Vector2F,
    override val originPointA: Vector2F,
    override val originPointB: Vector2F,
    override val originPointC: Vector2F
) : Triangle {
    override fun copy(
        centroid: Vector2F, originPointA: Vector2F, originPointB: Vector2F, originPointC: Vector2F
    ) = DefaultTriangle(centroid, originPointA, originPointB, originPointC)

    override fun equals(other: Any?): Boolean = other is Triangle &&
            centroid == other.centroid &&
            originPointA == other.originPointA &&
            originPointB == other.originPointB &&
            originPointC == other.originPointC

    override fun hashCode(): Int {
        val centroidHash: Int = centroid.hashCode()
        val originPointAHash: Int = originPointA.hashCode()
        val originPointBHash: Int = originPointB.hashCode()
        val originPointCHash: Int = originPointC.hashCode()

        return centroidHash * 29791 +
                originPointAHash * 961 +
                originPointBHash * 31 +
                originPointCHash
    }
}