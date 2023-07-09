@file:Suppress(
    "MemberVisibilityCanBePrivate",
    "OVERRIDE_BY_INLINE",
    "ReplaceRangeToWithUntil",
    "unused",
)

package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.AngleF
import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F
import com.sztorm.lowallocmath.Vector2FArray
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.tan

class RegularPolygon(
    val center: Vector2F,
    val rotation: ComplexF,
    override inline val sideLength: Float,
    sideCount: Int,
) : RegularShape {
    private val _points: Vector2FArray

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
        } else {
            points = Vector2FArray(sideCount)
            val isSideCountEven: Boolean = sideCount and 1 == 0
            val halfCount: Int = sideCount / 2
            val exteriorAngle: Float = (2.0 * PI).toFloat() / sideCount
            val exteriorRotation = ComplexF(cos(exteriorAngle), sin(exteriorAngle))

            if (isSideCountEven) {
                val inradius: Float = halfSideLength / tan(exteriorAngle * 0.5f)
                points[0] = Vector2F(halfSideLength, inradius)
                points[1] = Vector2F(-halfSideLength, inradius)

                for (i in 2..halfCount) {
                    points[i] = (exteriorRotation * points[i - 1].toComplexF()).toVector2F()
                }
                for (i in halfCount + 1..sideCount - 1) {
                    val oppositePoint: Vector2F = points[sideCount - i + 1]
                    points[i] = Vector2F(-oppositePoint.x, oppositePoint.y)
                }
            } else {
                val circumradius: Float = halfSideLength / sin(exteriorAngle * 0.5f)
                points[0] = Vector2F(0f, circumradius)

                for (i in 1..halfCount) {
                    points[i] = (exteriorRotation * points[i - 1].toComplexF()).toVector2F()
                }
                for (i in (halfCount + 1)..sideCount - 1) {
                    val oppositePoint: Vector2F = points[sideCount - i]
                    points[i] = Vector2F(-oppositePoint.x, oppositePoint.y)
                }
            }
        }
        for (i in 0..sideCount - 1) {
            points[i] = center + (rotation * points[i].toComplexF()).toVector2F()
        }
        _points = points
    }

    val points: List<Vector2F>
        get() = _points.asList()

    override val sideCount: Int
        get() = _points.size

    override inline val area: Float
        get() {
            val sideCount: Float = this.sideCount.toFloat()
            val sideLength: Float = this.sideLength

            return 0.25f * sideCount * sideLength * sideLength / tan(PI.toFloat() / sideCount)
        }

    override inline val perimeter: Float
        get() = sideCount * sideLength

    override inline val interiorAngle: AngleF
        get() = AngleF(PI.toFloat() - (2.0 * PI).toFloat() / sideCount.toFloat())

    override inline val exteriorAngle: AngleF
        get() = AngleF((2.0 * PI).toFloat() / sideCount)

    override inline val inradius: Float
        get() = (sideLength * 0.5f) / tan(PI.toFloat() / sideCount)

    override inline val circumradius: Float
        get() = (sideLength * 0.5f) / sin(PI.toFloat() / sideCount)

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