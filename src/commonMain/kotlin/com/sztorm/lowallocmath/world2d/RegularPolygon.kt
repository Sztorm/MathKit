@file:Suppress(
    "MemberVisibilityCanBePrivate",
    "OVERRIDE_BY_INLINE",
    "ReplaceRangeToWithUntil",
    "unused",
)

package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.*
import kotlin.math.*

class RegularPolygon(
    val center: Vector2F,
    val rotation: ComplexF,
    override inline val sideLength: Float,
    sideCount: Int,
) : RegularShape {
    private val _points: Vector2FArray
    override val circumradius: Float
    override val inradius: Float

    init {
        val center: Vector2F = this.center
        val rotation: ComplexF = this.rotation
        val points: Vector2FArray
        val halfSideLength: Float = 0.5f * sideLength

        if (sideCount < 3) {
            if (sideCount < 2) {
                throw IllegalArgumentException(
                    "Minimum required side count to create a polygon is 2."
                )
            }
            points = Vector2FArray(sideCount)
            points[0] = Vector2F(halfSideLength, 0f)
            points[1] = Vector2F(-halfSideLength, 0f)
            this.inradius = 0f
            this.circumradius = halfSideLength
        } else {
            points = Vector2FArray(sideCount)
            val isSideCountEven: Boolean = sideCount and 1 == 0
            val halfCount: Int = sideCount / 2
            val exteriorAngle: Float = (2.0 * PI).toFloat() / sideCount
            val halfExteriorAngle: Float = exteriorAngle * 0.5f
            val exteriorRotation = ComplexF(cos(exteriorAngle), sin(exteriorAngle))

            if (isSideCountEven) {
                val inradius: Float = halfSideLength / tan(halfExteriorAngle)
                points[0] = Vector2F(halfSideLength, inradius)
                points[1] = Vector2F(-halfSideLength, inradius)

                for (i in 2..halfCount) {
                    points[i] = (exteriorRotation * points[i - 1].toComplexF()).toVector2F()
                }
                for (i in halfCount + 1..sideCount - 1) {
                    val oppositePoint: Vector2F = points[sideCount - i + 1]
                    points[i] = Vector2F(-oppositePoint.x, oppositePoint.y)
                }
                this.inradius = inradius
                this.circumradius = halfSideLength / sin(halfExteriorAngle)
            } else {
                val circumradius: Float = halfSideLength / sin(halfExteriorAngle)
                points[0] = Vector2F(0f, circumradius)

                for (i in 1..halfCount) {
                    points[i] = (exteriorRotation * points[i - 1].toComplexF()).toVector2F()
                }
                for (i in (halfCount + 1)..sideCount - 1) {
                    val oppositePoint: Vector2F = points[sideCount - i]
                    points[i] = Vector2F(-oppositePoint.x, oppositePoint.y)
                }
                this.inradius = halfSideLength / tan(halfExteriorAngle)
                this.circumradius = circumradius
            }
        }
        for (i in 0..sideCount - 1) {
            points[i] = center + (rotation * points[i].toComplexF()).toVector2F()
        }
        _points = points
    }

    val points: Vector2FList
        get() = _points.asList()

    override val sideCount: Int
        get() = _points.size

    override inline val area: Float
        get() = 0.5f * sideCount.toFloat() * sideLength * inradius

    override inline val perimeter: Float
        get() = sideCount * sideLength

    override inline val interiorAngle: AngleF
        get() = AngleF(PI.toFloat() - (2.0 * PI).toFloat() / sideCount.toFloat())

    override inline val exteriorAngle: AngleF
        get() = AngleF((2.0 * PI).toFloat() / sideCount)

    fun closestPointTo(point: Vector2F): Vector2F {
        val sideCount: Int = this.sideCount
        val rotConj = ComplexF.conjugate(rotation)
        val halfSideLength: Float = this.sideLength * 0.5f
        val center: Vector2F = this.center

        if (sideCount == 2) {
            val (x, _) = rotConj * (point - center).toComplexF()

            return when {
                x > halfSideLength -> _points[0]
                x < -halfSideLength -> _points[1]
                else -> center + rotation.toVector2F() * x
            }
        }
        val rot90Deg = ComplexF(0f, 1f)
        val rotNeg90Deg = ComplexF(0f, -1f)
        val inradius: Float = this.inradius
        val rotation: ComplexF = this.rotation
        val fullAngle = (2.0 * PI).toFloat()
        val exteriorAngle: Float = fullAngle / sideCount
        val p1: ComplexF = rotConj * rotNeg90Deg * (point - center).toComplexF()
        val p1Angle: Float = atan2(p1.imaginary, p1.real) + 0.001f
        val p1AnglePositive: Float =
            if (p1Angle < 0) p1Angle + fullAngle
            else p1Angle
        val index: Int = (p1AnglePositive / exteriorAngle).toInt()
        val rot1 = ComplexF.fromAngle(
            AngleF((sideCount and 1) * (-exteriorAngle * 0.5f) + -exteriorAngle * index)
        )
        val rot1Conj = ComplexF.conjugate(rot1)
        val p2: ComplexF = rot1 * rot90Deg * p1
        val (p2X, p2Y) = p2

        return when {
            p2X < -halfSideLength ->
                center + (rot1Conj * rotation * ComplexF(-halfSideLength, inradius)).toVector2F()

            p2X > halfSideLength ->
                center + (rot1Conj * rotation * ComplexF(halfSideLength, inradius)).toVector2F()

            p2Y > inradius ->
                center + (rot1Conj * rotation * ComplexF(p2X, inradius)).toVector2F()

            else -> point
        }
    }

    operator fun contains(point: Vector2F): Boolean {
        val sideCount: Int = this.sideCount
        val rotConj = ComplexF.conjugate(rotation)
        val halfSideLength: Float = this.sideLength * 0.5f
        val center: Vector2F = this.center

        if (sideCount == 2) {
            val (x, _) = rotConj * (point - center).toComplexF()

            return if ((x > halfSideLength) or (x < -halfSideLength)) false
            else {
                val closestPoint = center + rotation.toVector2F() * x

                return closestPoint.isApproximately(point)
            }
        }
        val rot90Deg = ComplexF(0f, 1f)
        val rotNeg90Deg = ComplexF(0f, -1f)
        val inradius: Float = this.inradius
        val fullAngle = (2.0 * PI).toFloat()
        val exteriorAngle: Float = fullAngle / sideCount
        val p1: ComplexF = rotConj * rotNeg90Deg * (point - center).toComplexF()
        val p1Angle: Float = atan2(p1.imaginary, p1.real) + 0.001f
        val p1AnglePositive: Float =
            if (p1Angle < 0) p1Angle + fullAngle
            else p1Angle
        val index: Int = (p1AnglePositive / exteriorAngle).toInt()
        val rot1 = ComplexF.fromAngle(
            AngleF((sideCount and 1) * (-exteriorAngle * 0.5f) + -exteriorAngle * index)
        )
        val p2: ComplexF = rot1 * rot90Deg * p1
        val (p2X, p2Y) = p2

        return (p2X >= -halfSideLength) and (p2X <= halfSideLength) and (p2Y <= inradius)
    }

    inline fun copy(
        center: Vector2F = this.center,
        rotation: ComplexF = this.rotation,
        sideLength: Float = this.sideLength,
        sideCount: Int = this.sideCount
    ) = RegularPolygon(center, rotation, sideLength, sideCount)

    override fun equals(other: Any?): Boolean = other is RegularPolygon &&
            center == other.center &&
            rotation == other.rotation &&
            sideLength == other.sideLength &&
            sideCount == other.sideCount

    override fun hashCode(): Int {
        val centerHash: Int = center.hashCode()
        val rotationHash: Int = rotation.hashCode()
        val sideLengthHash: Int = sideLength.hashCode()
        val sideCountHash: Int = sideCount.hashCode()

        return centerHash * 29791 + rotationHash * 961 + sideLengthHash * 31 + sideCountHash
    }

    override fun toString() =
        StringBuilder("RegularPolygon(center=").append(center)
            .append(", rotation=").append(rotation)
            .append(", sideLength=").append(sideLength)
            .append(", sideCount=").append(sideCount).append(")")
            .toString()

    operator fun component1(): Vector2F = center

    operator fun component2(): ComplexF = rotation

    operator fun component3(): Float = sideLength

    operator fun component4(): Int = sideCount
}