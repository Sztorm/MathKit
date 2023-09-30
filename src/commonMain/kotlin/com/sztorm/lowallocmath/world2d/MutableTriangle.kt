package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.Vector2F
import com.sztorm.lowallocmath.Vector2FIterator
import kotlin.math.abs

class MutableTriangle(pointA: Vector2F, pointB: Vector2F, pointC: Vector2F) : Triangle {
    private var _pointA: Vector2F = pointA
    private var _pointB: Vector2F = pointB
    private var _pointC: Vector2F = pointC

    override val pointA: Vector2F
        get() = _pointA

    override val pointB: Vector2F
        get() = _pointB

    override val pointC: Vector2F
        get() = _pointC

    override val area: Float
        get() {
            val (aX: Float, aY: Float) = _pointA
            val (bX: Float, bY: Float) = _pointB
            val (cX: Float, cY: Float) = _pointC

            return 0.5f * abs((aX - cX) * (bY - cY) - (bX - cX) * (aY - cY))
        }

    override val perimeter: Float
        get() {
            val pointA: Vector2F = _pointA
            val pointB: Vector2F = _pointB
            val pointC: Vector2F = _pointC

            return pointA.distanceTo(pointB) +
                    pointB.distanceTo(pointC) +
                    pointC.distanceTo(pointA)
        }

    override val centroid: Vector2F
        get() = (_pointA + _pointB + _pointC) * 0.3333333f

    override val orthocenter: Vector2F
        get() {
            val (aX: Float, aY: Float) = _pointA
            val (bX: Float, bY: Float) = _pointB
            val (cX: Float, cY: Float) = _pointC
            val pASquaredMagnitude: Float = aX * aX + aY * aY
            val pBSquaredMagnitude: Float = bX * bX + bY * bY
            val pCSquaredMagnitude: Float = cX * cX + cY * cY
            val aDet: Float = aX * bY + aY * cX + bX * cY - bY * cX - aY * bX - aX * cY
            val factor: Float = 0.5f / aDet
            val xDet: Float = pASquaredMagnitude * bY + aY * pCSquaredMagnitude +
                    pBSquaredMagnitude * cY - bY * pCSquaredMagnitude -
                    aY * pBSquaredMagnitude - pASquaredMagnitude * cY
            val yDet: Float = aX * pBSquaredMagnitude + pASquaredMagnitude * cX +
                    bX * pCSquaredMagnitude - pBSquaredMagnitude * cX -
                    pASquaredMagnitude * bX - aX * pCSquaredMagnitude
            val circumcenterX: Float = xDet * factor
            val circumcenterY: Float = yDet * factor
            val centroidX = (aX + bX + cX) * 0.3333333f
            val centroidY = (aY + bY + cY) * 0.3333333f

            return Vector2F(
                circumcenterX + (centroidX - circumcenterX) * 3f,
                circumcenterY + (centroidY - circumcenterY) * 3f
            )
        }

    override val incenter: Vector2F
        get() {
            val pointA: Vector2F = _pointA
            val pointB: Vector2F = _pointB
            val pointC: Vector2F = _pointC
            val abSide: Float = pointA.distanceTo(pointB)
            val bcSide: Float = pointB.distanceTo(pointC)
            val acSide: Float = pointA.distanceTo(pointC)
            val factor: Float = 1f / (abSide + bcSide + acSide)

            return Vector2F(
                (bcSide * pointA.x + acSide * pointB.x + abSide * pointC.x) * factor,
                (bcSide * pointA.y + acSide * pointB.y + abSide * pointC.y) * factor
            )
        }

    override val circumcenter: Vector2F
        get() {
            val (aX: Float, aY: Float) = _pointA
            val (bX: Float, bY: Float) = _pointB
            val (cX: Float, cY: Float) = _pointC
            val pASquaredMagnitude: Float = aX * aX + aY * aY
            val pBSquaredMagnitude: Float = bX * bX + bY * bY
            val pCSquaredMagnitude: Float = cX * cX + cY * cY
            val aDet: Float = aX * bY + aY * cX + bX * cY - bY * cX - aY * bX - aX * cY
            val factor: Float = 0.5f / aDet
            val xDet: Float = pASquaredMagnitude * bY + aY * pCSquaredMagnitude +
                    pBSquaredMagnitude * cY - bY * pCSquaredMagnitude -
                    aY * pBSquaredMagnitude - pASquaredMagnitude * cY
            val yDet: Float = aX * pBSquaredMagnitude + pASquaredMagnitude * cX +
                    bX * pCSquaredMagnitude - pBSquaredMagnitude * cX -
                    pASquaredMagnitude * bX - aX * pCSquaredMagnitude
            return Vector2F(xDet * factor, yDet * factor)
        }

    override fun pointIterator(): Vector2FIterator = PointIterator(this, index = 0)

    override fun closestPointTo(point: Vector2F): Vector2F {
        val a: Vector2F = _pointA
        val b: Vector2F = _pointB
        val c: Vector2F = _pointC
        val ab: Vector2F = b - a
        val ac: Vector2F = c - a
        val ap: Vector2F = point - a
        val d1: Float = ab dot ap
        val d2: Float = ac dot ap

        if (d1 <= 0f && d2 <= 0f) {
            return a
        }
        val bp: Vector2F = point - b
        val d3: Float = ab dot bp
        val d4: Float = ac dot bp

        if (d3 >= 0f && d4 <= d3) {
            return b
        }
        val cp: Vector2F = point - c
        val d5: Float = ab dot cp
        val d6: Float = ac dot cp

        if (d6 >= 0f && d5 <= d6) {
            return c
        }
        val vc: Float = d1 * d4 - d3 * d2

        if (vc <= 0f && d1 >= 0f && d3 <= 0f) {
            val v: Float = d1 / (d1 - d3)
            return a + ab * v
        }
        val vb: Float = d5 * d2 - d1 * d6

        if (vb <= 0f && d2 >= 0f && d6 <= 0f) {
            val v: Float = d2 / (d2 - d6)
            return a + ac * v
        }
        val va: Float = d3 * d6 - d5 * d4

        if (va <= 0f && (d4 - d3) >= 0f && (d5 - d6) >= 0f) {
            val v: Float = (d4 - d3) / ((d4 - d3) + (d5 - d6))
            return b + (c - b) * v
        }
        val denominator: Float = 1f / (va + vb + vc)
        val v: Float = vb * denominator
        val w: Float = vc * denominator

        return a + ab * v + ac * w
    }

    override operator fun contains(point: Vector2F): Boolean {
        val (pX: Float, pY: Float) = point
        val (aX: Float, aY: Float) = _pointA
        val (bX: Float, bY: Float) = _pointB
        val (cX: Float, cY: Float) = _pointC
        val abp: Boolean = ((pX - aX) * (aY - bY) + (pY - aY) * (bX - aX)) >= 0f
        val bcp: Boolean = ((pX - bX) * (bY - cY) + (pY - bY) * (cX - bX)) >= 0f
        val acp: Boolean = ((pX - cX) * (cY - aY) + (pY - cY) * (aX - cX)) >= 0f

        return (abp == bcp) and (bcp == acp)
    }

    override fun copy(pointA: Vector2F, pointB: Vector2F, pointC: Vector2F) =
        MutableTriangle(pointA, pointB, pointC)

    override fun equals(other: Any?): Boolean = other is Triangle &&
            _pointA == other.pointA &&
            _pointB == other.pointB &&
            _pointC == other.pointC

    fun equals(other: MutableTriangle): Boolean =
        _pointA == other._pointA &&
                _pointB == other._pointB &&
                _pointC == other._pointC

    override fun hashCode(): Int {
        val pointAHash: Int = _pointA.hashCode()
        val pointBHash: Int = _pointB.hashCode()
        val pointCHash: Int = _pointC.hashCode()

        return pointAHash * 961 + pointBHash * 31 + pointCHash
    }

    override fun toString() =
        StringBuilder("Triangle(pointA=").append(_pointA)
            .append(", pointB=").append(_pointB)
            .append(", pointC=").append(_pointC).append(")")
            .toString()

    override operator fun component1(): Vector2F = _pointA

    override operator fun component2(): Vector2F = _pointB

    override operator fun component3(): Vector2F = _pointC

    private class PointIterator(
        private val triangle: MutableTriangle,
        private var index: Int
    ) : Vector2FIterator() {
        override fun hasNext(): Boolean = index < 3

        override fun nextVector2F(): Vector2F = when (index++) {
            0 -> triangle._pointA
            1 -> triangle._pointB
            2 -> triangle._pointC
            else -> throw NoSuchElementException("${index - 1}")
        }
    }
}