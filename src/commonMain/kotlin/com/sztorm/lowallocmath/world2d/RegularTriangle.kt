@file:Suppress(
    "MemberVisibilityCanBePrivate",
    "OVERRIDE_BY_INLINE",
    "unused"
)

package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.AngleF
import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F
import kotlin.math.PI
import kotlin.math.absoluteValue
import kotlin.math.withSign

data class RegularTriangle(
    val center: Vector2F,
    val rotation: ComplexF,
    override inline val sideLength: Float
) : TriangleShape, RegularShape {
    override val pointA: Vector2F
    override val pointB: Vector2F
    override val pointC: Vector2F

    init {
        val halfSideLength: Float = sideLength * 0.5f
        val inradius: Float = this.inradius
        val circumradius: Float = inradius + inradius
        val center: Vector2F = this.center
        val rotationR: Float = rotation.real
        val rotationI: Float = rotation.imaginary
        val pB = ComplexF(-halfSideLength, -inradius)
        val pC = ComplexF(halfSideLength, -inradius)
        pointA = center + Vector2F(-rotationI * circumradius, rotationR * circumradius)
        pointB = center + (rotation * pB).toVector2F()
        pointC = center + (rotation * pC).toVector2F()
    }

    override inline val area: Float
        get() = 0.4330127f * sideLength * sideLength

    override inline val perimeter: Float
        get() = 3f * sideLength

    override inline val interiorAngle: AngleF
        get() = AngleF((PI / 3.0).toFloat())

    override inline val inradius: Float
        get() = 0.28867513f * sideLength

    override inline val circumradius: Float
        get() = 0.5773503f * sideLength

    fun closestPointTo(point: Vector2F): Vector2F {
        val halfSideLength: Float = sideLength * 0.5f
        val inradius: Float = this.inradius
        val rotation: ComplexF = this.rotation
        val center: Vector2F = this.center
        val p1 = ComplexF.conjugate(rotation) *
                ComplexF(point.x - center.x, point.y - center.y)
        val p1X: Float = p1.real
        val p1Y: Float = p1.imaginary
        val yGB: Float = 0.5773503f * p1X // (sqrt(3) / 3) * x
        val yGC: Float = -yGB // (-sqrt(3) / 3) * x

        if ((p1Y <= yGB) and (p1Y <= yGC)) {
            if ((p1Y >= -inradius)) {
                return point
            }
            if (p1X.absoluteValue >= halfSideLength) {
                val vertexPoint = ComplexF(halfSideLength.withSign(p1X), -inradius)

                return (rotation * vertexPoint).toVector2F() + center
            }
            val edgePoint = ComplexF(p1X, -inradius)

            return (rotation * edgePoint).toVector2F() + center
        }
        val add120DegRotation = ComplexF(-0.5f, -(0.8660254f.withSign(p1X)))
        val p2: ComplexF = add120DegRotation * p1
        val p2X: Float = p2.real
        val p2Y: Float = p2.imaginary

        if ((p2Y >= -inradius)) {
            return point
        }
        if (p2X.absoluteValue >= halfSideLength) {
            val vertexPoint = ComplexF(halfSideLength.withSign(p2X), -inradius)

            return (ComplexF.conjugate(add120DegRotation) * rotation * vertexPoint)
                .toVector2F() + center
        }
        val edgePoint = ComplexF(p2X, -inradius)

        return (ComplexF.conjugate(add120DegRotation) * rotation * edgePoint)
            .toVector2F() + center
    }

    operator fun contains(point: Vector2F): Boolean {
        val sideLength: Float = this.sideLength
        val minusInradius: Float = -0.28867513f * sideLength // sqrt(3) / 6 * a
        val p1 = ComplexF.conjugate(rotation) *
                ComplexF(point.x - center.x, point.y - center.y)
        val p1X: Float = p1.real
        val p1Y: Float = p1.imaginary
        //yAB = sqrt(3) * x + (sqrt(3) / 3) * a
        //yAC = -sqrt(3) * x + (sqrt(3) / 3) * a
        val ax: Float = 1.7320508f * p1X
        val b: Float = 0.5773503f * sideLength
        val yAB: Float = ax + b
        val yAC: Float = -ax + b

        return (p1Y >= minusInradius) and (p1Y <= yAB) and (p1Y <= yAC)
    }
}