package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.*
import kotlin.math.*

fun RegularPolygon(
    center: Vector2F, orientation: ComplexF, sideLength: Float, sideCount: Int
): RegularPolygon = MutableRegularPolygon(center, orientation, sideLength, sideCount)

interface RegularPolygon : RegularShape, Transformable {
    val center: Vector2F

    val points: Vector2FList
        get() {
            val center: Vector2F = this.center
            val orientation: ComplexF = this.orientation
            val sideCount: Int = this.sideCount
            val halfSideLength: Float = 0.5f * sideLength
            val points = Vector2FArray(sideCount)

            if (sideCount == 2) {
                val (rotR: Float, rotI: Float) = orientation
                val offset = Vector2F(rotR * halfSideLength, rotI * halfSideLength)
                points[0] = center + offset
                points[1] = center - offset

                return points.asList()
            }
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
                for (i in halfCount + 1 until sideCount) {
                    val oppositePoint: Vector2F = points[sideCount - i + 1]
                    points[i] = Vector2F(-oppositePoint.x, oppositePoint.y)
                }
            } else {
                val circumradius: Float = halfSideLength / sin(halfExteriorAngle)
                points[0] = Vector2F(0f, circumradius)

                for (i in 1..halfCount) {
                    points[i] = (exteriorRotation * points[i - 1].toComplexF()).toVector2F()
                }
                for (i in (halfCount + 1) until sideCount) {
                    val oppositePoint: Vector2F = points[sideCount - i]
                    points[i] = Vector2F(-oppositePoint.x, oppositePoint.y)
                }
            }
            for (i in 0 until sideCount) {
                points[i] = center + (orientation * points[i].toComplexF()).toVector2F()
            }
            return points.asList()
        }

    override val area: Float
        get() {
            val sideCount: Float = this.sideCount.toFloat()

            if (sideCount == 2f) {
                return 0f
            }
            val sideLength: Float = this.sideLength
            val halfExteriorAngle: Float = PI.toFloat() / sideCount

            return 0.25f * sideCount * sideLength * (sideLength / tan(halfExteriorAngle))
        }

    override val perimeter: Float
        get() = sideCount * sideLength

    override val interiorAngle: AngleF
        get() = AngleF(PI.toFloat() - (2.0 * PI).toFloat() / sideCount.toFloat())

    override val exteriorAngle: AngleF
        get() = AngleF((2.0 * PI).toFloat() / sideCount)

    override val inradius: Float
        get() {
            val sideCount: Int = this.sideCount

            if (sideCount == 2) {
                return 0f
            }
            val halfExteriorAngle: Float = PI.toFloat() / sideCount
            val halfSideLength: Float = 0.5f * sideLength

            return halfSideLength / tan(halfExteriorAngle)
        }

    override val circumradius: Float
        get() {
            val halfExteriorAngle: Float = PI.toFloat() / sideCount
            val halfSideLength: Float = 0.5f * sideLength

            return halfSideLength / sin(halfExteriorAngle)
        }

    override val position: Vector2F
        get() = center

    override fun movedBy(offset: Vector2F): RegularPolygon = copy(center = center + offset)

    override fun movedTo(position: Vector2F): RegularPolygon = copy(center = position)

    override fun rotatedBy(rotation: AngleF): RegularPolygon =
        copy(orientation = orientation * ComplexF.fromAngle(rotation))

    override fun rotatedBy(rotation: ComplexF): RegularPolygon =
        copy(orientation = orientation * rotation)

    override fun rotatedTo(orientation: AngleF): RegularPolygon =
        copy(orientation = ComplexF.fromAngle(orientation))

    override fun rotatedTo(orientation: ComplexF): RegularPolygon = copy(orientation = orientation)

    private fun rotatedAroundPointByImpl(point: Vector2F, rotation: ComplexF): RegularPolygon {
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

    override fun rotatedAroundPointBy(point: Vector2F, rotation: AngleF): RegularPolygon =
        rotatedAroundPointByImpl(point, ComplexF.fromAngle(rotation))

    override fun rotatedAroundPointBy(point: Vector2F, rotation: ComplexF): RegularPolygon =
        rotatedAroundPointByImpl(point, rotation)

    private fun rotatedAroundPointToImpl(point: Vector2F, orientation: ComplexF): RegularPolygon {
        val (pX: Float, pY: Float) = point
        val (rotR: Float, rotI: Float) = orientation
        val (startRotR: Float, startRotI: Float) = this.orientation
        val (cX: Float, cY: Float) = center
        val cpDiffX: Float = cX - pX
        val cpDiffY: Float = cY - pY
        val centerToPointDist: Float = sqrt(cpDiffX * cpDiffX + cpDiffY * cpDiffY)

        return if (centerToPointDist > 0.00001f) {
            val pointRotR: Float = cpDiffX / centerToPointDist
            val pointRotI: Float = cpDiffY / centerToPointDist
            val targetCenterX: Float = rotR * centerToPointDist + pX
            val targetCenterY: Float = rotI * centerToPointDist + pY
            val pRotR: Float = pointRotR * rotR + pointRotI * rotI
            val pRotI: Float = pointRotR * rotI - pointRotI * rotR

            copy(
                center = Vector2F(targetCenterX, targetCenterY),
                orientation = ComplexF(
                    pRotR * startRotR - pRotI * startRotI,
                    pRotI * startRotR + pRotR * startRotI
                )
            )
        } else copy(orientation = orientation)
    }

    override fun rotatedAroundPointTo(point: Vector2F, orientation: AngleF): RegularPolygon =
        rotatedAroundPointToImpl(point, ComplexF.fromAngle(orientation))

    override fun rotatedAroundPointTo(point: Vector2F, orientation: ComplexF): RegularPolygon =
        rotatedAroundPointToImpl(point, orientation)

    override fun scaledBy(factor: Float): RegularPolygon = copy(
        orientation = orientation * 1f.withSign(factor),
        sideLength = sideLength * factor.absoluteValue
    )

    override fun dilatedBy(point: Vector2F, factor: Float): RegularPolygon {
        val (startCX: Float, startCY: Float) = center
        val f: Float = 1f - factor
        val addendX: Float = point.x * f
        val addendY: Float = point.y * f

        return copy(
            center = Vector2F(startCX * factor + addendX, startCY * factor + addendY),
            orientation = orientation * 1f.withSign(factor),
            sideLength = sideLength * factor.absoluteValue
        )
    }

    override fun transformedBy(offset: Vector2F, rotation: AngleF): RegularPolygon = copy(
        center = center + offset,
        orientation = orientation * ComplexF.fromAngle(rotation)
    )

    override fun transformedBy(offset: Vector2F, rotation: ComplexF): RegularPolygon = copy(
        center = center + offset,
        orientation = orientation * rotation
    )

    override fun transformedBy(
        offset: Vector2F, rotation: AngleF, factor: Float
    ): RegularPolygon = copy(
        center = center + offset,
        orientation = orientation * ComplexF.fromAngle(rotation) * 1f.withSign(factor),
        sideLength = sideLength * factor.absoluteValue
    )

    override fun transformedBy(
        offset: Vector2F, rotation: ComplexF, factor: Float
    ): RegularPolygon = copy(
        center = center + offset,
        orientation = orientation * rotation * 1f.withSign(factor),
        sideLength = sideLength * factor.absoluteValue
    )

    override fun transformedTo(position: Vector2F, orientation: AngleF): RegularPolygon = copy(
        center = position,
        orientation = ComplexF.fromAngle(orientation)
    )

    override fun transformedTo(position: Vector2F, orientation: ComplexF): RegularPolygon = copy(
        center = position,
        orientation = orientation
    )

    fun closestPointTo(point: Vector2F): Vector2F {
        val sideCount: Int = this.sideCount
        val center: Vector2F = this.center
        val orientation: ComplexF = this.orientation
        val (rotR: Float, rotI: Float) = orientation
        val rotConj = ComplexF(rotR, -rotI)
        val halfSideLength: Float = sideLength * 0.5f

        if (sideCount == 2) {
            val x: Float = (rotConj * (point - center).toComplexF()).real

            return when {
                x > halfSideLength -> center + Vector2F(
                    rotR * halfSideLength, rotI * halfSideLength
                )

                x < -halfSideLength -> center - Vector2F(
                    rotR * halfSideLength, rotI * halfSideLength
                )

                else -> center + orientation.toVector2F() * x
            }
        }
        val rot90Deg = ComplexF(0f, 1f)
        val rotNeg90Deg = ComplexF(0f, -1f)
        val fullAngle = (2.0 * PI).toFloat()
        val exteriorAngle: Float = fullAngle / sideCount
        val halfExteriorAngle: Float = exteriorAngle * 0.5f
        val inradius: Float = halfSideLength / tan(halfExteriorAngle)
        val p1: ComplexF = rotConj * rotNeg90Deg * (point - center).toComplexF()
        val p1Angle: Float = atan2(p1.imaginary, p1.real) + 0.001f
        val p1AnglePositive: Float =
            if (p1Angle < 0) p1Angle + fullAngle
            else p1Angle
        val index: Int = (p1AnglePositive / exteriorAngle).toInt()
        val rot1 = ComplexF.fromAngle(
            AngleF((sideCount and 1) * (-halfExteriorAngle) + -exteriorAngle * index)
        )
        val rot1Conj = ComplexF.conjugate(rot1)
        val p2: ComplexF = rot1 * rot90Deg * p1
        val (p2X, p2Y) = p2

        return when {
            p2X < -halfSideLength ->
                center + (rot1Conj * orientation * ComplexF(-halfSideLength, inradius)).toVector2F()

            p2X > halfSideLength ->
                center + (rot1Conj * orientation * ComplexF(halfSideLength, inradius)).toVector2F()

            p2Y > inradius ->
                center + (rot1Conj * orientation * ComplexF(p2X, inradius)).toVector2F()

            else -> point
        }
    }

    operator fun contains(point: Vector2F): Boolean {
        val sideCount: Int = this.sideCount
        val orientation: ComplexF = this.orientation
        val rotConj = ComplexF.conjugate(orientation)
        val halfSideLength: Float = sideLength * 0.5f
        val center: Vector2F = center

        if (sideCount == 2) {
            val x: Float = (rotConj * (point - center).toComplexF()).real

            return if ((x > halfSideLength) or (x < -halfSideLength)) false
            else {
                val closestPoint = center + orientation.toVector2F() * x

                return closestPoint.isApproximately(point)
            }
        }
        val rot90Deg = ComplexF(0f, 1f)
        val rotNeg90Deg = ComplexF(0f, -1f)
        val fullAngle = (2.0 * PI).toFloat()
        val exteriorAngle: Float = fullAngle / sideCount
        val halfExteriorAngle: Float = exteriorAngle * 0.5f
        val inradius: Float = halfSideLength / tan(halfExteriorAngle)
        val p1: ComplexF = rotConj * rotNeg90Deg * (point - center).toComplexF()
        val p1Angle: Float = atan2(p1.imaginary, p1.real) + 0.001f
        val p1AnglePositive: Float =
            if (p1Angle < 0) p1Angle + fullAngle
            else p1Angle
        val index: Int = (p1AnglePositive / exteriorAngle).toInt()
        val rot1 = ComplexF.fromAngle(
            AngleF((sideCount and 1) * (-halfExteriorAngle) + -exteriorAngle * index)
        )
        val p2: ComplexF = rot1 * rot90Deg * p1
        val (p2X, p2Y) = p2

        return (p2X >= -halfSideLength) and (p2X <= halfSideLength) and (p2Y <= inradius)
    }

    fun pointIterator(): Vector2FIterator = points.iterator()

    fun copy(
        center: Vector2F = this.center,
        orientation: ComplexF = this.orientation,
        sideLength: Float = this.sideLength,
        sideCount: Int = this.sideCount
    ): RegularPolygon

    fun toRegularTriangleOrNull(): RegularTriangle? =
        if (sideCount == 3) RegularTriangle(center, orientation, sideLength)
        else null

    fun toSquareOrNull(): Square? =
        if (sideCount == 4) Square(center, orientation, sideLength)
        else null

    operator fun component1(): Vector2F = center

    operator fun component2(): ComplexF = orientation

    operator fun component3(): Float = sideLength

    operator fun component4(): Int = sideCount
}

