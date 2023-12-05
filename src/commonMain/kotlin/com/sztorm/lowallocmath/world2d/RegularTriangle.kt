package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.AngleF
import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F
import com.sztorm.lowallocmath.Vector2FIterator
import kotlin.math.PI
import kotlin.math.absoluteValue
import kotlin.math.sqrt
import kotlin.math.withSign

fun RegularTriangle(center: Vector2F, orientation: ComplexF, sideLength: Float): RegularTriangle =
    MutableRegularTriangle(center, orientation, sideLength)

interface RegularTriangle : TriangleShape, RegularShape, Transformable {
    val center: Vector2F

    val pointA: Vector2F
        get() {
            val (cX: Float, cY: Float) = center
            val (rotR: Float, rotI: Float) = orientation
            val inradius: Float = 0.28867513f * sideLength
            val circumradius: Float = inradius + inradius

            return Vector2F(cX - rotI * circumradius, cY + rotR * circumradius)
        }

    val pointB: Vector2F
        get() {
            val (cX: Float, cY: Float) = center
            val (rotR: Float, rotI: Float) = orientation
            val sideLength: Float = this.sideLength
            val halfSideLength: Float = sideLength * 0.5f
            val inradius: Float = 0.28867513f * sideLength
            val addendX1: Float = rotI * inradius + cX
            val addendX2: Float = rotR * halfSideLength
            val addendY1: Float = rotI * halfSideLength
            val addendY2: Float = rotR * inradius - cY
            return Vector2F(addendX1 - addendX2, -addendY1 - addendY2)
        }

    val pointC: Vector2F
        get() {
            val (cX: Float, cY: Float) = center
            val (rotR: Float, rotI: Float) = orientation
            val sideLength: Float = this.sideLength
            val halfSideLength: Float = sideLength * 0.5f
            val inradius: Float = 0.28867513f * sideLength
            val addendX1: Float = rotI * inradius + cX
            val addendX2: Float = rotR * halfSideLength
            val addendY1: Float = rotI * halfSideLength
            val addendY2: Float = rotR * inradius - cY
            return Vector2F(addendX1 + addendX2, addendY1 - addendY2)
        }

    val centroid: Vector2F
        get() = center

    val orthocenter: Vector2F
        get() = center

    val incenter: Vector2F
        get() = center

    val circumcenter: Vector2F
        get() = center

    override val area: Float
        get() = 0.4330127f * sideLength * sideLength

    override val perimeter: Float
        get() = 3f * sideLength

    override val sideLengthAB: Float
        get() = sideLength

    override val sideLengthBC: Float
        get() = sideLength

    override val sideLengthCA: Float
        get() = sideLength

    override val sideCount: Int
        get() = 3

    override val interiorAngle: AngleF
        get() = AngleF((PI / 3.0).toFloat())

    override val exteriorAngle: AngleF
        get() = AngleF((2.0 / 3.0 * PI).toFloat())

    override val inradius: Float
        get() = 0.28867513f * sideLength

    override val circumradius: Float
        get() = 0.5773503f * sideLength

    override val position: Vector2F
        get() = center

    override fun movedBy(offset: Vector2F): RegularTriangle = copy(center = center + offset)

    override fun movedTo(position: Vector2F): RegularTriangle = copy(center = position)

    override fun rotatedBy(rotation: AngleF): RegularTriangle =
        copy(orientation = orientation * ComplexF.fromAngle(rotation))

    override fun rotatedBy(rotation: ComplexF): RegularTriangle =
        copy(orientation = orientation * rotation)

    override fun rotatedTo(orientation: AngleF): RegularTriangle =
        copy(orientation = ComplexF.fromAngle(orientation))

    override fun rotatedTo(orientation: ComplexF): RegularTriangle =
        copy(orientation = orientation)

    private fun rotatedAroundPointByImpl(point: Vector2F, rotation: ComplexF): RegularTriangle {
        val (pX: Float, pY: Float) = point
        val (rotR: Float, rotI: Float) = rotation
        val (cX: Float, cY: Float) = center
        val (startRotR: Float, startRotI: Float) = orientation
        val cpDiffX: Float = cX - pX
        val cpDiffY: Float = cY - pY
        val targetCenterX: Float = cpDiffX * rotR - cpDiffY * rotI + pX
        val targetCenterY: Float = cpDiffY * rotR + cpDiffX * rotI + pY
        val targetRotR: Float = startRotR * rotR - startRotI * rotI
        val targetRotI: Float = startRotI * rotR + startRotR * rotI

        return copy(
            center = Vector2F(targetCenterX, targetCenterY),
            orientation = ComplexF(targetRotR, targetRotI)
        )
    }

    override fun rotatedAroundPointBy(point: Vector2F, rotation: AngleF): RegularTriangle =
        rotatedAroundPointByImpl(point, ComplexF.fromAngle(rotation))

    override fun rotatedAroundPointBy(point: Vector2F, rotation: ComplexF): RegularTriangle =
        rotatedAroundPointByImpl(point, rotation)

    private fun rotatedAroundPointToImpl(point: Vector2F, orientation: ComplexF): RegularTriangle {
        val (pX: Float, pY: Float) = point
        val (rotR: Float, rotI: Float) = orientation
        val (cX: Float, cY: Float) = center
        val cpDiffX: Float = cX - pX
        val cpDiffY: Float = cY - pY
        val centerToPointDist: Float = sqrt(cpDiffX * cpDiffX + cpDiffY * cpDiffY)

        return if (centerToPointDist > 0.00001f) {
            val pointRotR: Float = cpDiffX / centerToPointDist
            val pointRotI: Float = cpDiffY / centerToPointDist
            val targetRot = ComplexF(pointRotR, -pointRotI) * this.orientation * orientation
            val targetCenterX: Float = rotR * centerToPointDist + pX
            val targetCenterY: Float = rotI * centerToPointDist + pY

            copy(
                center = Vector2F(targetCenterX, targetCenterY),
                orientation = targetRot
            )
        } else copy(orientation = orientation)

    }

    override fun rotatedAroundPointTo(point: Vector2F, orientation: AngleF): RegularTriangle =
        rotatedAroundPointToImpl(point, ComplexF.fromAngle(orientation))

    override fun rotatedAroundPointTo(point: Vector2F, orientation: ComplexF): RegularTriangle =
        rotatedAroundPointToImpl(point, orientation)

    override fun scaledBy(factor: Float): RegularTriangle = copy(
        orientation = orientation * 1f.withSign(factor),
        sideLength = sideLength * factor.absoluteValue
    )

    override fun dilatedBy(point: Vector2F, factor: Float): RegularTriangle {
        val f: Float = 1f - factor
        val cX: Float = center.x * factor + point.x * f
        val cY: Float = center.y * factor + point.y * f
        val (rotR: Float, rotI: Float) = orientation * 1f.withSign(factor)
        val sideLength: Float = sideLength * factor.absoluteValue

        return copy(
            center = Vector2F(cX, cY),
            orientation = ComplexF(rotR, rotI),
            sideLength = sideLength,
        )
    }

    override fun transformedBy(offset: Vector2F, rotation: AngleF): RegularTriangle = copy(
        center = center + offset,
        orientation = orientation * ComplexF.fromAngle(rotation)
    )

    override fun transformedBy(offset: Vector2F, rotation: ComplexF): RegularTriangle = copy(
        center = center + offset,
        orientation = orientation * rotation
    )

    override fun transformedBy(
        offset: Vector2F, rotation: AngleF, factor: Float
    ): RegularTriangle = copy(
        center = center + offset,
        orientation = orientation * ComplexF.fromAngle(rotation) * 1f.withSign(factor),
        sideLength = sideLength * factor.absoluteValue
    )

    override fun transformedBy(
        offset: Vector2F, rotation: ComplexF, factor: Float
    ): RegularTriangle = copy(
        center = center + offset,
        orientation = orientation * rotation * 1f.withSign(factor),
        sideLength = sideLength * factor.absoluteValue
    )

    override fun transformedTo(position: Vector2F, orientation: AngleF): RegularTriangle = copy(
        center = position,
        orientation = ComplexF.fromAngle(orientation)
    )

    override fun transformedTo(position: Vector2F, orientation: ComplexF): RegularTriangle = copy(
        center = position,
        orientation = orientation
    )

    fun closestPointTo(point: Vector2F): Vector2F {
        val sideLength: Float = this.sideLength
        val halfSideLength: Float = 0.5f * sideLength
        val inradius: Float = 0.28867513f * sideLength
        val orientation: ComplexF = orientation
        val center: Vector2F = center
        val p1 = ComplexF.conjugate(orientation) *
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

                return (orientation * vertexPoint).toVector2F() + center
            }
            val edgePoint = ComplexF(p1X, -inradius)

            return (orientation * edgePoint).toVector2F() + center
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

            return (ComplexF.conjugate(add120DegRotation) * orientation * vertexPoint)
                .toVector2F() + center
        }
        val edgePoint = ComplexF(p2X, -inradius)

        return (ComplexF.conjugate(add120DegRotation) * orientation * edgePoint)
            .toVector2F() + center
    }

    operator fun contains(point: Vector2F): Boolean {
        val sideLength: Float = sideLength
        val minusInradius: Float = -0.28867513f * sideLength // sqrt(3) / 6 * a
        val p1 = ComplexF.conjugate(orientation) *
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

    fun pointIterator(): Vector2FIterator = PointIterator(this, index = 0)

    fun copy(
        center: Vector2F = this.center,
        orientation: ComplexF = this.orientation,
        sideLength: Float = this.sideLength,
    ): RegularTriangle

    operator fun component1(): Vector2F = center

    operator fun component2(): ComplexF = orientation

    operator fun component3(): Float = sideLength

    private class PointIterator(
        private val triangle: RegularTriangle,
        private var index: Int
    ) : Vector2FIterator() {
        override fun hasNext(): Boolean = index < 3

        override fun nextVector2F(): Vector2F = when (index++) {
            0 -> triangle.pointA
            1 -> triangle.pointB
            2 -> triangle.pointC
            else -> throw NoSuchElementException("${index - 1}")
        }
    }
}