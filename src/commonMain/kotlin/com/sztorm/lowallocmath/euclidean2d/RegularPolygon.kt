package com.sztorm.lowallocmath.euclidean2d

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
                val displacement = Vector2F(rotR * halfSideLength, rotI * halfSideLength)
                points[0] = center + displacement
                points[1] = center - displacement

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

    override fun movedBy(displacement: Vector2F): RegularPolygon =
        copy(center = center + displacement)

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

    override fun transformedBy(displacement: Vector2F, rotation: AngleF): RegularPolygon = copy(
        center = center + displacement,
        orientation = orientation * ComplexF.fromAngle(rotation)
    )

    override fun transformedBy(displacement: Vector2F, rotation: ComplexF): RegularPolygon = copy(
        center = center + displacement,
        orientation = orientation * rotation
    )

    override fun transformedBy(
        displacement: Vector2F, rotation: AngleF, scaleFactor: Float
    ): RegularPolygon = copy(
        center = center + displacement,
        orientation = orientation * ComplexF.fromAngle(rotation) * 1f.withSign(scaleFactor),
        sideLength = sideLength * scaleFactor.absoluteValue
    )

    override fun transformedBy(
        displacement: Vector2F, rotation: ComplexF, scaleFactor: Float
    ): RegularPolygon = copy(
        center = center + displacement,
        orientation = orientation * rotation * 1f.withSign(scaleFactor),
        sideLength = sideLength * scaleFactor.absoluteValue
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
        val halfSideLength: Float = sideLength * 0.5f
        val (cX: Float, cY: Float) = center
        val (oR: Float, oI: Float) = orientation
        val (pX: Float, pY: Float) = point
        val cpX: Float = pX - cX
        val cpY: Float = pY - cY

        if (sideCount == 2) {
            // x = (orientation.conjugate * cp).real
            val x: Float = oR * cpX + oI * cpY

            return when {
                x > halfSideLength -> Vector2F(
                    cX + oR * halfSideLength, cY + oI * halfSideLength
                )

                x < -halfSideLength -> Vector2F(
                    cX - oR * halfSideLength, cY - oI * halfSideLength
                )

                else -> Vector2F(cX + oR * x, cY + oI * x)
            }
        }
        val fullAngle = (2.0 * PI).toFloat()
        val exteriorAngle: Float = fullAngle / sideCount
        val halfExteriorAngle: Float = exteriorAngle * 0.5f
        val inradius: Float = halfSideLength / tan(halfExteriorAngle)
        // p1 = orientation.conjugate * ComplexF.fromAngle(AngleF.fromDegrees(-90f)) * cp
        val p1X: Float = oR * cpY - oI * cpX
        val p1Y: Float = -oR * cpX - oI * cpY
        val p1Angle: Float = atan2(p1Y, p1X) + 0.0001f +
                ((sideCount + 1) and 1) * halfExteriorAngle
        val p1AnglePositive: Float = p1Angle + fullAngle
        val index: Int = (p1AnglePositive / exteriorAngle).toInt()
        val angle: Float = (sideCount and 1) * -halfExteriorAngle - exteriorAngle * index
        val cosAngle: Float = cos(angle)
        val sinAngle: Float = sin(angle)
        // p2 = ComplexF.fromAngle(angle) * ComplexF.fromAngle(AngleF.fromDegrees(90f)) * p1
        val p2X: Float = -cosAngle * p1Y - sinAngle * p1X

        if (p2X < -halfSideLength) {
            val r: Float = cosAngle * oR + sinAngle * oI
            val i: Float = cosAngle * oI - sinAngle * oR

            // center + orientation * ComplexF(cosAngle, -sinAngle) *
            // ComplexF(-halfSideLength, inradius)
            return Vector2F(
                cX - r * halfSideLength - i * inradius, cY + r * inradius - i * halfSideLength
            )
        }
        if (p2X > halfSideLength) {
            val r: Float = cosAngle * oR + sinAngle * oI
            val i: Float = cosAngle * oI - sinAngle * oR

            // center + orientation * ComplexF(cosAngle, -sinAngle) *
            // ComplexF(halfSideLength, inradius)
            return Vector2F(
                cX + r * halfSideLength - i * inradius, cY + i * halfSideLength + r * inradius
            )
        }
        val p2Y: Float = cosAngle * p1X - sinAngle * p1Y

        if (p2Y > inradius) {
            val r: Float = cosAngle * oR + sinAngle * oI
            val i: Float = cosAngle * oI - sinAngle * oR

            // center + orientation * ComplexF(cosAngle, -sinAngle) * ComplexF(p2X, inradius)
            return Vector2F(cX + r * p2X - i * inradius, cY + i * p2X + r * inradius)
        }
        return point
    }

    fun intersects(ray: Ray): Boolean {
        val (polyCX: Float, polyCY: Float) = center
        val (polyOR: Float, polyOI: Float) = orientation
        val sideCount: Int = sideCount
        val sideLength: Float = sideLength
        val halfSideLength: Float = sideLength * 0.5f
        val (rayCX: Float, rayCY: Float) = ray.origin
        val (rayDirX: Float, rayDirY: Float) = ray.direction
        val rayCPolyCX: Float = polyCX - rayCX
        val rayCPolyCY: Float = polyCY - rayCY
        val oRayCX: Float = -rayCPolyCX * polyOR - rayCPolyCY * polyOI
        val oRayCY: Float = rayCPolyCX * polyOI - rayCPolyCY * polyOR
        val oRayDirX: Float = rayDirX * polyOR + rayDirY * polyOI
        val oRayDirY: Float = rayDirY * polyOR - rayDirX * polyOI

        if (sideCount == 2) {
            val aoX: Float = oRayCX - halfSideLength
            val dirCrossAB: Float = sideLength * oRayDirY
            val areParallel: Boolean = dirCrossAB.absoluteValue < 0.00001f

            if (areParallel) {
                val boX: Float = oRayCX + halfSideLength
                val factor: Float = oRayCY * oRayDirY
                val dirDotAO: Float = aoX * oRayDirX + factor
                val dirDotBO: Float = boX * oRayDirX + factor
                val dirCrossAO: Float = oRayDirX * oRayCY - oRayDirY * aoX

                return (dirCrossAO.absoluteValue < 0.00001f) and ((dirDotAO <= 0f) or (dirDotBO <= 0f))
            }
            val detABReciprocal: Float = 1f / dirCrossAB
            val t1: Float = -oRayCY * sideLength * detABReciprocal

            if (t1 >= 0f) {
                val t2: Float = (oRayCY * oRayDirX - aoX * oRayDirY) * detABReciprocal

                return (t2 >= 0f) and (t2 <= 1f)
            }
            return false
        }
        val fullAngle = (2.0 * PI).toFloat()
        val exteriorAngle: Float = fullAngle / sideCount
        val halfExteriorAngle: Float = exteriorAngle * 0.5f
        val inradius: Float = halfSideLength / tan(halfExteriorAngle)
        val minusORayCDotORayDir: Float = -oRayCX * oRayDirX - oRayCY * oRayDirY
        val closestPointOnORayX: Float
        val closestPointOnORayY: Float

        if (minusORayCDotORayDir <= 0f) {
            closestPointOnORayX = oRayCX
            closestPointOnORayY = oRayCY
        } else {
            closestPointOnORayX = oRayCX + oRayDirX * minusORayCDotORayDir
            closestPointOnORayY = oRayCY + oRayDirY * minusORayCDotORayDir
        }
        val closestPointOnORayLength: Float = sqrt(
            closestPointOnORayX * closestPointOnORayX + closestPointOnORayY * closestPointOnORayY
        )
        if (closestPointOnORayLength <= inradius) {
            return true
        }
        val sinHalfExteriorAngle: Float = sin(halfExteriorAngle)
        val circumradius: Float = halfSideLength / sinHalfExteriorAngle

        if (closestPointOnORayLength > circumradius) {
            return false
        }
        val rightAngle = (PI * 0.5).toFloat()
        val evenSidedFactor = ((sideCount + 1) and 1).toFloat()
        val evenSidedHalfExteriorAngle: Float = evenSidedFactor * halfExteriorAngle
        val cosP1: Float = cos(evenSidedHalfExteriorAngle)
        val sinP1: Float = evenSidedFactor * sinHalfExteriorAngle
        // p1 = ComplexF.ONE *
        //      ComplexF.fromAngle(AngleF.fromDegrees(-90f)) *
        //      ComplexF.fromAngle(AngleF.fromDegrees(evenSidedHalfExteriorAngle)) *
        //      ComplexF(closestPointOnORayX, closestPointOnORayY)
        val p1X: Float = closestPointOnORayY * cosP1 + closestPointOnORayX * sinP1
        val p1Y: Float = closestPointOnORayY * sinP1 - closestPointOnORayX * cosP1
        val p1Angle: Float = atan2(p1Y, p1X) + 0.001f
        val p1AnglePositive: Float = p1Angle + fullAngle
        val index = (p1AnglePositive / exteriorAngle).toInt()
        val angleA: Float = evenSidedHalfExteriorAngle - exteriorAngle * index - rightAngle
        val angleB: Float = angleA - exteriorAngle
        val cosA: Float = cos(angleA)
        val sinA: Float = sin(angleA)
        val cosB: Float = cos(angleB)
        val sinB: Float = sin(angleB)
        val aX: Float = cosA * circumradius
        val aY: Float = -sinA * circumradius
        val bX: Float = cosB * circumradius
        val bY: Float = -sinB * circumradius
        val aoX: Float = oRayCX - aX
        val aoY: Float = oRayCY - aY
        val abX: Float = bX - aX
        val abY: Float = bY - aY
        val dirCrossAB: Float = abY * oRayDirX - abX * oRayDirY
        val areParallel: Boolean = dirCrossAB.absoluteValue < 0.00001f

        if (areParallel) {
            return false
        }
        val detABReciprocal: Float = 1f / dirCrossAB
        val t1: Float = (aoY * abX - aoX * abY) * detABReciprocal

        if (t1 >= 0f) {
            val t2: Float = (aoY * oRayDirX - aoX * oRayDirY) * detABReciprocal

            return (t2 >= 0f) and (t2 <= 1f)
        }
        return false
    }

    operator fun contains(point: Vector2F): Boolean {
        val sideCount: Int = this.sideCount
        val halfSideLength: Float = sideLength * 0.5f
        val (cX: Float, cY: Float) = center
        val (oR: Float, oI: Float) = orientation
        val (pX: Float, pY: Float) = point
        val cpX: Float = pX - cX
        val cpY: Float = pY - cY

        if (sideCount == 2) {
            // x = (orientation.conjugate * cp).real
            val x: Float = oR * cpX + oI * cpY

            return if (x.absoluteValue > halfSideLength) false
            else {
                val closestPointX: Float = cX + oR * x
                val closestPointY: Float = cY + oI * x

                return closestPointX.isApproximately(pX) && closestPointY.isApproximately(pY)
            }
        }
        val fullAngle = (2.0 * PI).toFloat()
        val exteriorAngle: Float = fullAngle / sideCount
        val halfExteriorAngle: Float = exteriorAngle * 0.5f
        val inradius: Float = halfSideLength / tan(halfExteriorAngle)
        // p1 = orientation.conjugate * ComplexF.fromAngle(AngleF.fromDegrees(-90f)) * cp
        val p1X: Float = oR * cpY - oI * cpX
        val p1Y: Float = -oR * cpX - oI * cpY
        val p1Angle: Float = atan2(p1Y, p1X) + 0.0001f +
                ((sideCount + 1) and 1) * halfExteriorAngle
        val p1AnglePositive: Float = p1Angle + fullAngle
        val index: Int = (p1AnglePositive / exteriorAngle).toInt()
        val angle: Float = (sideCount and 1) * -halfExteriorAngle - exteriorAngle * index
        val cosAngle: Float = cos(angle)
        val sinAngle: Float = sin(angle)
        // p2 = ComplexF.fromAngle(angle) * ComplexF.fromAngle(AngleF.fromDegrees(90f)) * p1
        val p2I: Float = cosAngle * p1X - sinAngle * p1Y

        return p2I <= inradius
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

