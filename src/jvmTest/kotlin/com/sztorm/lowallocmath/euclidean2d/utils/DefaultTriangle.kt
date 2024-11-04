package com.sztorm.lowallocmath.euclidean2d.utils

import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F
import com.sztorm.lowallocmath.euclidean2d.Triangle

class DefaultTriangle(
    override val centroid: Vector2F,
    override val pathRotorA: ComplexF,
    override val pointDistanceA: Float,
    override val pathRotorAB: ComplexF,
    override val pointDistanceB: Float,
    override val pathRotorAC: ComplexF,
    override val pointDistanceC: Float
) : Triangle {
    override fun copy(
        centroid: Vector2F,
        pathRotorA: ComplexF,
        pointDistanceA: Float,
        pathRotorAB: ComplexF,
        pointDistanceB: Float,
        pathRotorAC: ComplexF,
        pointDistanceC: Float
    ) = DefaultTriangle(
        centroid,
        pathRotorA,
        pointDistanceA,
        pathRotorAB,
        pointDistanceB,
        pathRotorAC,
        pointDistanceC
    )

    override fun equals(other: Any?): Boolean = other is Triangle &&
            centroid == other.centroid &&
            pathRotorA == other.pathRotorA &&
            pointDistanceA == other.pointDistanceA &&
            pathRotorAB == other.pathRotorAB &&
            pointDistanceB == other.pointDistanceB &&
            pathRotorAC == other.pathRotorAC &&
            pointDistanceC == other.pointDistanceC

    override fun hashCode(): Int {
        var result: Int = centroid.hashCode()
        result = 31 * result + pathRotorA.hashCode()
        result = 31 * result + pointDistanceA.hashCode()
        result = 31 * result + pathRotorAB.hashCode()
        result = 31 * result + pointDistanceB.hashCode()
        result = 31 * result + pathRotorAC.hashCode()
        result = 31 * result + pointDistanceC.hashCode()

        return result
    }
}