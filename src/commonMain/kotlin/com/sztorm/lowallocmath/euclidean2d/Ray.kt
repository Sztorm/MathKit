package com.sztorm.lowallocmath.euclidean2d

import com.sztorm.lowallocmath.AngleF
import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F
import kotlin.math.*

fun Ray(origin: Vector2F, direction: Vector2F): Ray = MutableRay(origin, direction)

interface Ray : Transformable {
    val origin: Vector2F
    val direction: Vector2F

    override val position: Vector2F
        get() = origin

    override val orientation: ComplexF
        get() = direction.toComplexF()

    override fun movedBy(offset: Vector2F): Ray = copy(origin = origin + offset)

    override fun movedTo(position: Vector2F): Ray = copy(origin = position)

    private inline fun rotatedByImpl(rotation: ComplexF): Ray {
        val (dirX: Float, dirY: Float) = direction
        val (rotR: Float, rotI: Float) = rotation

        return copy(
            direction = Vector2F(dirX * rotR - dirY * rotI, dirY * rotR + dirX * rotI)
        )
    }

    override fun rotatedBy(rotation: AngleF): Ray = rotatedByImpl(ComplexF.fromAngle(rotation))

    override fun rotatedBy(rotation: ComplexF): Ray = rotatedByImpl(rotation)

    override fun rotatedTo(orientation: AngleF): Ray =
        copy(direction = ComplexF.fromAngle(orientation).toVector2F())

    override fun rotatedTo(orientation: ComplexF): Ray = copy(direction = orientation.toVector2F())

    private fun rotatedAroundPointByImpl(point: Vector2F, rotation: ComplexF): Ray {
        val (pX: Float, pY: Float) = point
        val (rotR: Float, rotI: Float) = rotation
        val (cX: Float, cY: Float) = origin
        val (startRotR: Float, startRotI: Float) = direction
        val cpDiffX: Float = cX - pX
        val cpDiffY: Float = cY - pY

        return copy(
            origin = Vector2F(
                cpDiffX * rotR - cpDiffY * rotI + pX, cpDiffY * rotR + cpDiffX * rotI + pY
            ),
            direction = Vector2F(
                startRotR * rotR - startRotI * rotI, startRotI * rotR + startRotR * rotI
            )
        )
    }

    override fun rotatedAroundPointBy(point: Vector2F, rotation: AngleF): Ray =
        rotatedAroundPointByImpl(point, ComplexF.fromAngle(rotation))

    override fun rotatedAroundPointBy(point: Vector2F, rotation: ComplexF): Ray =
        rotatedAroundPointByImpl(point, rotation)

    private fun rotatedAroundPointToImpl(point: Vector2F, orientation: ComplexF): Ray {
        val (pX: Float, pY: Float) = point
        val (rotR: Float, rotI: Float) = orientation
        val (cX: Float, cY: Float) = origin
        val cpDiffX: Float = cX - pX
        val cpDiffY: Float = cY - pY
        val centerToPointDist: Float = sqrt(cpDiffX * cpDiffX + cpDiffY * cpDiffY)

        if (centerToPointDist > 0.00001f) {
            val pointRotR: Float = cpDiffX / centerToPointDist
            val pointRotI: Float = cpDiffY / centerToPointDist
            val (startRotR: Float, startRotI: Float) = direction
            val r0: Float = pointRotR * startRotR + pointRotI * startRotI
            val i0: Float = pointRotR * startRotI - pointRotI * startRotR

            return copy(
                origin = Vector2F(
                    rotR * centerToPointDist + pX, rotI * centerToPointDist + pY
                ),
                direction = Vector2F(r0 * rotR - i0 * rotI, i0 * rotR + r0 * rotI)
            )
        } else {
            return copy(direction = orientation.toVector2F())
        }
    }

    override fun rotatedAroundPointTo(point: Vector2F, orientation: AngleF): Ray =
        rotatedAroundPointToImpl(point, ComplexF.fromAngle(orientation))

    override fun rotatedAroundPointTo(point: Vector2F, orientation: ComplexF): Ray =
        rotatedAroundPointToImpl(point, orientation)

    override fun scaledBy(factor: Float): Ray = copy(direction = direction * 1f.withSign(factor))

    override fun dilatedBy(point: Vector2F, factor: Float): Ray {
        val (cX: Float, cY: Float) = origin
        val (pX: Float, pY: Float) = point

        return copy(
            origin = Vector2F(pX + factor * (cX - pX), pY + factor * (cY - pY)),
            direction = direction * 1f.withSign(factor)
        )
    }

    private inline fun transformedByImpl(offset: Vector2F, rotation: ComplexF): Ray {
        val (dirX: Float, dirY: Float) = direction
        val (rotR: Float, rotI: Float) = rotation

        return copy(
            origin = origin + offset,
            direction = Vector2F(dirX * rotR - dirY * rotI, dirY * rotR + dirX * rotI)
        )
    }

    override fun transformedBy(offset: Vector2F, rotation: AngleF): Ray =
        transformedByImpl(offset, ComplexF.fromAngle(rotation))

    override fun transformedBy(offset: Vector2F, rotation: ComplexF): Ray =
        transformedByImpl(offset, rotation)

    private inline fun transformedByImpl(
        offset: Vector2F, rotation: ComplexF, factor: Float
    ): Ray {
        val (dirX: Float, dirY: Float) = direction
        val factorSign: Float = 1f.withSign(factor)
        val rotR: Float = rotation.real * factorSign
        val rotI: Float = rotation.imaginary * factorSign

        return copy(
            origin = origin + offset,
            direction = Vector2F(dirX * rotR - dirY * rotI, dirY * rotR + dirX * rotI)
        )
    }

    override fun transformedBy(offset: Vector2F, rotation: AngleF, factor: Float): Ray =
        transformedByImpl(offset, ComplexF.fromAngle(rotation), factor)

    override fun transformedBy(offset: Vector2F, rotation: ComplexF, factor: Float): Ray =
        transformedByImpl(offset, rotation, factor)

    override fun transformedTo(position: Vector2F, orientation: AngleF): Ray = copy(
        origin = position,
        direction = ComplexF.fromAngle(orientation).toVector2F()
    )

    override fun transformedTo(position: Vector2F, orientation: ComplexF): Ray = copy(
        origin = position,
        direction = orientation.toVector2F()
    )

    fun interpolated(to: Ray, by: Float): Ray = copy(
        origin = Vector2F.lerp(origin, to.origin, by),
        direction = ComplexF
            .slerp(direction.toComplexF(), to.direction.toComplexF(), by)
            .toVector2F()
    )

    fun closestPointTo(point: Vector2F): Vector2F {
        val origin: Vector2F = this.origin
        val direction: Vector2F = this.direction
        val op: Vector2F = point - origin
        val t: Float = op dot direction

        return if (t <= 0f) origin
        else origin + direction * t
    }

    fun intersects(annulus: Annulus): Boolean {
        val rayOrigin: Vector2F = origin
        val rayDirection: Vector2F = direction
        val annulusCenter: Vector2F = annulus.center
        val annulusOuterRadius: Float = annulus.outerRadius
        val diff: Vector2F = annulusCenter - rayOrigin
        val t: Float = diff dot rayDirection
        val closestPointOnRay: Vector2F =
            if (t <= 0f) rayOrigin
            else rayOrigin + rayDirection * t

        return closestPointOnRay.distanceTo(annulusCenter) <= annulusOuterRadius
    }

    fun intersects(circle: Circle): Boolean {
        val rayOrigin: Vector2F = origin
        val rayDirection: Vector2F = direction
        val circleCenter: Vector2F = circle.center
        val circleRadius: Float = circle.radius
        val diff: Vector2F = circleCenter - rayOrigin
        val t: Float = diff dot rayDirection
        val closestPointOnRay: Vector2F =
            if (t <= 0f) rayOrigin
            else rayOrigin + rayDirection * t

        return closestPointOnRay.distanceTo(circleCenter) <= circleRadius
    }

    fun intersects(lineSegment: LineSegment): Boolean {
        val (oX: Float, oY: Float) = origin
        val (dirX: Float, dirY: Float) = direction
        val (aX: Float, aY: Float) = lineSegment.pointA
        val (bX: Float, bY: Float) = lineSegment.pointB
        val aoX: Float = oX - aX
        val aoY: Float = oY - aY
        val abX: Float = bX - aX
        val abY: Float = bY - aY
        val dirCrossAB: Float = abY * dirX - abX * dirY
        val areParallel: Boolean = dirCrossAB.absoluteValue < 0.00001f

        if (areParallel) {
            val boX: Float = oX - bX
            val boY: Float = oY - bY
            val dirDotAO: Float = aoX * dirX + aoY * dirY
            val dirDotBO: Float = boX * dirX + boY * dirY
            val dirCrossAO = dirX * aoY - dirY * aoX

            return (dirCrossAO.absoluteValue < 0.00001f) and ((dirDotAO <= 0f) or (dirDotBO <= 0f))
        }
        val detABReciprocal: Float = 1f / dirCrossAB
        val t1: Float = (aoY * abX - aoX * abY) * detABReciprocal

        if (t1 >= 0f) {
            val t2: Float = (aoY * dirX - aoX * dirY) * detABReciprocal

            return (t2 >= 0f) and (t2 <= 1f)
        }
        return false
    }

    fun intersects(ray: Ray): Boolean {
        val (origAX: Float, origAY: Float) = origin
        val (dirAX: Float, dirAY: Float) = direction
        val (origBX: Float, origBY: Float) = ray.origin
        val (dirBX: Float, dirBY: Float) = ray.direction
        val dx: Float = origBX - origAX
        val dy: Float = origBY - origAY
        val dirBCrossDirA: Float = dirBX * dirAY - dirBY * dirAX
        val areDirsTheSameOrOpposite: Boolean = dirBCrossDirA.absoluteValue < 0.00001f

        if (areDirsTheSameOrOpposite) {
            val dirADotDirB: Float = dirAX * dirBX + dirAY * dirBY
            val areSameDirs: Boolean = dirADotDirB >= 0f

            return if (areSameDirs) (dirAX * dy - dirAY * dx).absoluteValue < 0.00001f
            else (dirAX * dx + dirAY * dy) >= 0f

        }
        val nomX: Float = dy * dirBX - dx * dirBY
        val nomY: Float = dy * dirAX - dx * dirAY

        return (nomX * dirBCrossDirA >= 0f) and (nomY * dirBCrossDirA >= 0f)
    }

    fun intersects(rectangle: Rectangle): Boolean {
        val (rectCX: Float, rectCY: Float) = rectangle.center
        val (rectOR: Float, rectOI: Float) = rectangle.orientation
        val halfWidth: Float = rectangle.width * 0.5f
        val halfHeight: Float = rectangle.height * 0.5f
        val aabbMinX: Float = rectCX - halfWidth
        val aabbMinY: Float = rectCY - halfHeight
        val aabbMaxX: Float = rectCX + halfWidth
        val aabbMaxY: Float = rectCY + halfHeight

        val (rayCX: Float, rayCY: Float) = origin
        val (rayDirX: Float, rayDirY: Float) = direction
        val cpDiffX: Float = rayCX - rectCX
        val cpDiffY: Float = rayCY - rectCY
        val orientedOriginX: Float = cpDiffX * rectOR + cpDiffY * rectOI + rectCX
        val orientedOriginY: Float = cpDiffY * rectOR - cpDiffX * rectOI + rectCY
        val orientedDirX: Float = rayDirX * rectOR + rayDirY * rectOI
        val orientedDirY: Float = rayDirY * rectOR - rayDirX * rectOI

        val dirReciprocalX: Float = 1f / orientedDirX
        val dirReciprocalY: Float = 1f / orientedDirY
        val tx1: Float = (aabbMinX - orientedOriginX) * dirReciprocalX
        val tx2: Float = (aabbMaxX - orientedOriginX) * dirReciprocalX
        val ty1: Float = (aabbMinY - orientedOriginY) * dirReciprocalY
        val ty2: Float = (aabbMaxY - orientedOriginY) * dirReciprocalY
        val tMax: Float = max(min(tx1, tx2), min(ty1, ty2))
        val tMin: Float = min(max(tx1, tx2), max(ty1, ty2))

        return (tMin >= 0f) and (tMax <= tMin)
    }

    fun intersects(rectangle: RoundedRectangle): Boolean {
        val (rectCX: Float, rectCY: Float) = rectangle.center
        val (rectOR: Float, rectOI: Float) = rectangle.orientation
        val halfWidth: Float = rectangle.width * 0.5f
        val halfHeight: Float = rectangle.height * 0.5f
        val cornerRadius: Float = rectangle.cornerRadius
        val halfWidthMinusRadius: Float = halfWidth - cornerRadius
        val halfHeightMinusRadius: Float = halfHeight - cornerRadius
        val aabb1MinX: Float = rectCX - halfWidth
        val aabb1MinY: Float = rectCY - halfHeightMinusRadius
        val aabb1MaxX: Float = rectCX + halfWidth
        val aabb1MaxY: Float = rectCY + halfHeightMinusRadius
        val aabb2MinX: Float = rectCX - halfWidthMinusRadius
        val aabb2MinY: Float = rectCY - halfHeight
        val aabb2MaxX: Float = rectCX + halfWidthMinusRadius
        val aabb2MaxY: Float = rectCY + halfHeight

        val (rayCX: Float, rayCY: Float) = origin
        val (rayDirX: Float, rayDirY: Float) = direction
        val centersDiffX: Float = rayCX - rectCX
        val centersDiffY: Float = rayCY - rectCY
        val orientedRayCX: Float = centersDiffX * rectOR + centersDiffY * rectOI + rectCX
        val orientedRayCY: Float = centersDiffY * rectOR - centersDiffX * rectOI + rectCY
        val orientedRayDirX: Float = rayDirX * rectOR + rayDirY * rectOI
        val orientedRayDirY: Float = rayDirY * rectOR - rayDirX * rectOI

        val ccArCDiffY: Float = aabb1MaxY - orientedRayCY
        val ccCrCDiffY: Float = aabb1MinY - orientedRayCY
        val dirReciprocalX: Float = 1f / orientedRayDirX
        val dirReciprocalY: Float = 1f / orientedRayDirY
        val tx11: Float = (aabb1MinX - orientedRayCX) * dirReciprocalX
        val tx12: Float = (aabb1MaxX - orientedRayCX) * dirReciprocalX
        val ty11: Float = ccCrCDiffY * dirReciprocalY
        val ty12: Float = ccArCDiffY * dirReciprocalY
        val tMax1: Float = max(min(tx11, tx12), min(ty11, ty12))
        val tMin1: Float = min(max(tx11, tx12), max(ty11, ty12))
        val intersectsAabb1: Boolean = (tMin1 >= 0f) and (tMax1 <= tMin1)

        if (intersectsAabb1) {
            return true
        }

        val ccArCDiffX: Float = aabb2MaxX - orientedRayCX
        val ccBrCDiffX: Float = aabb2MinX - orientedRayCX
        val tx21: Float = ccBrCDiffX * dirReciprocalX
        val tx22: Float = ccArCDiffX * dirReciprocalX
        val ty21: Float = (aabb2MinY - orientedRayCY) * dirReciprocalY
        val ty22: Float = (aabb2MaxY - orientedRayCY) * dirReciprocalY
        val tMax2: Float = max(min(tx21, tx22), min(ty21, ty22))
        val tMin2: Float = min(max(tx21, tx22), max(ty21, ty22))
        val intersectsAabb2: Boolean = (tMin2 >= 0f) and (tMax2 <= tMin2)

        if (intersectsAabb2) {
            return true
        }

        val tA1: Float = ccArCDiffX * orientedRayDirX
        val tA2: Float = ccArCDiffY * orientedRayDirY
        val tA: Float = tA1 + tA2
        val minusCcArCDiffX: Float = -ccArCDiffX
        val minusCcArCDiffY: Float = -ccArCDiffY
        val intersectsCircleA: Boolean = if (tA <= 0f) {
            sqrt(
                minusCcArCDiffX * minusCcArCDiffX + minusCcArCDiffY * minusCcArCDiffY
            ) <= cornerRadius
        } else {
            val cpCcADiffX: Float = minusCcArCDiffX + orientedRayDirX * tA
            val cpCcADiffY: Float = minusCcArCDiffY + orientedRayDirY * tA

            sqrt(cpCcADiffX * cpCcADiffX + cpCcADiffY * cpCcADiffY) <= cornerRadius
        }
        if (intersectsCircleA) {
            return true
        }

        val tB1: Float = ccBrCDiffX * orientedRayDirX
        val tB: Float = tB1 + tA2
        val minusCcBrCDiffX: Float = -ccBrCDiffX
        val intersectsCircleB: Boolean = if (tB <= 0f) {
            sqrt(
                minusCcBrCDiffX * minusCcBrCDiffX + minusCcArCDiffY * minusCcArCDiffY
            ) <= cornerRadius
        } else {
            val cpCcBDiffX: Float = minusCcBrCDiffX + orientedRayDirX * tB
            val cpCcBDiffY: Float = minusCcArCDiffY + orientedRayDirY * tB

            sqrt(cpCcBDiffX * cpCcBDiffX + cpCcBDiffY * cpCcBDiffY) <= cornerRadius
        }
        if (intersectsCircleB) {
            return true
        }

        val tC2: Float = ccCrCDiffY * orientedRayDirY
        val tC: Float = tB1 + tC2
        val minusCcCrCDiffY: Float = -ccCrCDiffY
        val intersectsCircleC: Boolean = if (tC <= 0f) {
            sqrt(
                minusCcBrCDiffX * minusCcBrCDiffX + minusCcCrCDiffY * minusCcCrCDiffY
            ) <= cornerRadius
        } else {
            val cpCcCDiffX: Float = minusCcBrCDiffX + orientedRayDirX * tC
            val cpCcCDiffY: Float = minusCcCrCDiffY + orientedRayDirY * tC

            sqrt(cpCcCDiffX * cpCcCDiffX + cpCcCDiffY * cpCcCDiffY) <= cornerRadius
        }
        if (intersectsCircleC) {
            return true
        }

        val tD: Float = tA1 + tC2
        val intersectsCircleD: Boolean = if (tD <= 0f) {
            sqrt(
                minusCcArCDiffX * minusCcArCDiffX + minusCcCrCDiffY * minusCcCrCDiffY
            ) <= cornerRadius
        } else {
            val cpCcDDiffX: Float = minusCcArCDiffX + orientedRayDirX * tD
            val cpCcDDiffY: Float = minusCcCrCDiffY + orientedRayDirY * tD

            sqrt(cpCcDDiffX * cpCcDDiffX + cpCcDDiffY * cpCcDDiffY) <= cornerRadius
        }
        return intersectsCircleD
    }

    fun intersects(square: Square): Boolean {
        val (rectCX: Float, rectCY: Float) = square.center
        val (rectOR: Float, rectOI: Float) = square.orientation
        val halfSideLength: Float = square.sideLength * 0.5f
        val aabbMinX: Float = rectCX - halfSideLength
        val aabbMinY: Float = rectCY - halfSideLength
        val aabbMaxX: Float = rectCX + halfSideLength
        val aabbMaxY: Float = rectCY + halfSideLength

        val (rayCX: Float, rayCY: Float) = origin
        val (rayDirX: Float, rayDirY: Float) = direction
        val cpDiffX: Float = rayCX - rectCX
        val cpDiffY: Float = rayCY - rectCY
        val orientedOriginX: Float = cpDiffX * rectOR + cpDiffY * rectOI + rectCX
        val orientedOriginY: Float = cpDiffY * rectOR - cpDiffX * rectOI + rectCY
        val orientedDirX: Float = rayDirX * rectOR + rayDirY * rectOI
        val orientedDirY: Float = rayDirY * rectOR - rayDirX * rectOI

        val dirReciprocalX: Float = 1f / orientedDirX
        val dirReciprocalY: Float = 1f / orientedDirY
        val tx1: Float = (aabbMinX - orientedOriginX) * dirReciprocalX
        val tx2: Float = (aabbMaxX - orientedOriginX) * dirReciprocalX
        val ty1: Float = (aabbMinY - orientedOriginY) * dirReciprocalY
        val ty2: Float = (aabbMaxY - orientedOriginY) * dirReciprocalY
        val tMax: Float = max(min(tx1, tx2), min(ty1, ty2))
        val tMin: Float = min(max(tx1, tx2), max(ty1, ty2))

        return (tMin >= 0f) and (tMax <= tMin)
    }

    fun intersects(triangle: Triangle): Boolean {
        val (aX: Float, aY: Float) = triangle.pointA
        val (bX: Float, bY: Float) = triangle.pointB
        val (cX: Float, cY: Float) = triangle.pointC
        val (oX: Float, oY: Float) = origin
        val (dirX: Float, dirY: Float) = direction
        val aoX: Float = oX - aX
        val aoY: Float = oY - aY
        val abX: Float = bX - aX
        val abY: Float = bY - aY
        val detAB: Float = abY * dirX - abX * dirY

        if (detAB.absoluteValue >= 0.00001f) {
            val detABReciprocal: Float = 1f / detAB
            val t1: Float = (aoY * abX - aoX * abY) * detABReciprocal

            if (t1 >= 0f) {
                val t2: Float = (aoY * dirX - aoX * dirY) * detABReciprocal

                if ((t2 >= 0f) and (t2 <= 1f)) {
                    return true
                }
            }
        }
        val boX: Float = oX - bX
        val boY: Float = oY - bY
        val bcX: Float = cX - bX
        val bcY: Float = cY - bY
        val detBC: Float = bcY * dirX - bcX * dirY

        if (detBC.absoluteValue >= 0.00001f) {
            val detBCReciprocal: Float = 1f / detBC
            val t1: Float = (boY * bcX - boX * bcY) * detBCReciprocal

            if (t1 >= 0f) {
                val t2: Float = (boY * dirX - boX * dirY) * detBCReciprocal

                if ((t2 >= 0f) and (t2 <= 1f)) {
                    return true
                }
            }
        }
        val coX: Float = oX - cX
        val coY: Float = oY - cY
        val acX: Float = cX - aX
        val acY: Float = cY - aY
        val abo: Boolean = (aoY * abX - aoX * abY) >= 0f
        val bco: Boolean = (boY * bcX - boX * bcY) >= 0f
        val aco: Boolean = (coX * acY - coY * acX) >= 0f

        return (abo == bco) and (bco == aco)
    }

    operator fun contains(point: Vector2F): Boolean {
        val origin: Vector2F = this.origin
        val direction: Vector2F = this.direction
        val op: Vector2F = point - origin
        val t: Float = op dot direction
        val closestPoint: Vector2F =
            if (t <= 0f) origin
            else origin + direction * t

        return closestPoint.isApproximately(point)
    }

    fun copy(origin: Vector2F = this.origin, direction: Vector2F = this.direction): Ray

    operator fun component1(): Vector2F = origin

    operator fun component2(): Vector2F = direction
}