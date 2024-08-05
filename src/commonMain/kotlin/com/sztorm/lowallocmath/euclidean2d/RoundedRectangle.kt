package com.sztorm.lowallocmath.euclidean2d

import com.sztorm.lowallocmath.*
import kotlin.math.*

/**
 * Creates a new instance of [RoundedRectangle].
 *
 * @param orientation the value is expected to be [normalized][ComplexF.normalized].
 * @throws IllegalArgumentException when [width] is less than zero.
 * @throws IllegalArgumentException when [height] is less than zero.
 * @throws IllegalArgumentException when [cornerRadius] is less than zero.
 * @throws IllegalArgumentException when [cornerRadius] is greater than the half the length of
 * the shorter side of this rounded rectangle.
 */
fun RoundedRectangle(
    center: Vector2F, orientation: ComplexF, width: Float, height: Float, cornerRadius: Float
): RoundedRectangle = MutableRoundedRectangle(center, orientation, width, height, cornerRadius)

/**
 * Represents a transformable rounded rectangle in a two-dimensional Euclidean space.
 *
 * Implementations that use default-implemented members of this interface must make sure that the
 * properties [center], [orientation], [width], [height], [cornerRadius] and the [copy] method are
 * independent of other members and the computational complexity of these members is trivial.
 */
interface RoundedRectangle : RoundedRectangleShape, Transformable {
    /** Returns the center of this rounded rectangle. **/
    val center: Vector2F

    /** Returns the point _A_ of this rounded rectangle. **/
    val pointA: Vector2F
        get() {
            val (cX: Float, cY: Float) = center
            val (rotR: Float, rotI: Float) = orientation
            val halfWidth: Float = width * 0.5f
            val halfHeight: Float = height * 0.5f
            val halfWidthMinusRadius: Float = halfWidth - cornerRadius
            val addendA: Float = rotR * halfWidthMinusRadius
            val addendB: Float = rotI * halfWidthMinusRadius
            val addendG: Float = rotR * halfHeight
            val addendH: Float = rotI * halfHeight
            val addendSumBG: Float = addendB + addendG
            val addendDiffAH: Float = addendA - addendH

            return Vector2F(cX + addendDiffAH, cY + addendSumBG)
        }

    /** Returns the point _B_ of this rounded rectangle. **/
    val pointB: Vector2F
        get() {
            val (cX: Float, cY: Float) = center
            val (rotR: Float, rotI: Float) = orientation
            val halfWidth: Float = width * 0.5f
            val halfHeight: Float = height * 0.5f
            val halfWidthMinusRadius: Float = halfWidth - cornerRadius
            val addendA: Float = rotR * halfWidthMinusRadius
            val addendB: Float = rotI * halfWidthMinusRadius
            val addendG: Float = rotR * halfHeight
            val addendH: Float = rotI * halfHeight
            val addendSumAH: Float = addendA + addendH
            val addendDiffBG: Float = addendB - addendG

            return Vector2F(cX - addendSumAH, cY - addendDiffBG)
        }

    /** Returns the point _C_ of this rounded rectangle. **/
    val pointC: Vector2F
        get() {
            val (cX: Float, cY: Float) = center
            val (rotR: Float, rotI: Float) = orientation
            val halfWidth: Float = width * 0.5f
            val halfHeight: Float = height * 0.5f
            val halfHeightMinusRadius: Float = halfHeight - cornerRadius
            val addendC: Float = rotR * halfHeightMinusRadius
            val addendD: Float = rotI * halfHeightMinusRadius
            val addendE: Float = rotR * halfWidth
            val addendF: Float = rotI * halfWidth
            val addendSumED: Float = addendE + addendD
            val addendDiffCF: Float = addendC - addendF

            return Vector2F(cX - addendSumED, cY + addendDiffCF)
        }

    /** Returns the point _D_ of this rounded rectangle. **/
    val pointD: Vector2F
        get() {
            val (cX: Float, cY: Float) = center
            val (rotR: Float, rotI: Float) = orientation
            val halfWidth: Float = width * 0.5f
            val halfHeight: Float = height * 0.5f
            val halfHeightMinusRadius: Float = halfHeight - cornerRadius
            val addendC: Float = rotR * halfHeightMinusRadius
            val addendD: Float = rotI * halfHeightMinusRadius
            val addendE: Float = rotR * halfWidth
            val addendF: Float = rotI * halfWidth
            val addendSumCF: Float = addendC + addendF
            val addendDiffED: Float = addendE - addendD

            return Vector2F(cX - addendDiffED, cY - addendSumCF)
        }

    /** Returns the point _E_ of this rounded rectangle. **/
    val pointE: Vector2F
        get() {
            val (cX: Float, cY: Float) = center
            val (rotR: Float, rotI: Float) = orientation
            val halfWidth: Float = width * 0.5f
            val halfHeight: Float = height * 0.5f
            val halfWidthMinusRadius: Float = halfWidth - cornerRadius
            val addendA: Float = rotR * halfWidthMinusRadius
            val addendB: Float = rotI * halfWidthMinusRadius
            val addendG: Float = rotR * halfHeight
            val addendH: Float = rotI * halfHeight
            val addendSumBG: Float = addendB + addendG
            val addendDiffAH: Float = addendA - addendH

            return Vector2F(cX - addendDiffAH, cY - addendSumBG)
        }

    /** Returns the point _F_ of this rounded rectangle. **/
    val pointF: Vector2F
        get() {
            val (cX: Float, cY: Float) = center
            val (rotR: Float, rotI: Float) = orientation
            val halfWidth: Float = width * 0.5f
            val halfHeight: Float = height * 0.5f
            val halfWidthMinusRadius: Float = halfWidth - cornerRadius
            val addendA: Float = rotR * halfWidthMinusRadius
            val addendB: Float = rotI * halfWidthMinusRadius
            val addendG: Float = rotR * halfHeight
            val addendH: Float = rotI * halfHeight
            val addendSumAH: Float = addendA + addendH
            val addendDiffBG: Float = addendB - addendG

            return Vector2F(cX + addendSumAH, cY + addendDiffBG)
        }

    /** Returns the point _G_ of this rounded rectangle. **/
    val pointG: Vector2F
        get() {
            val (cX: Float, cY: Float) = center
            val (rotR: Float, rotI: Float) = orientation
            val halfWidth: Float = width * 0.5f
            val halfHeight: Float = height * 0.5f
            val halfHeightMinusRadius: Float = halfHeight - cornerRadius
            val addendC: Float = rotR * halfHeightMinusRadius
            val addendD: Float = rotI * halfHeightMinusRadius
            val addendE: Float = rotR * halfWidth
            val addendF: Float = rotI * halfWidth
            val addendSumED: Float = addendE + addendD
            val addendDiffCF: Float = addendC - addendF

            return Vector2F(cX + addendSumED, cY - addendDiffCF)
        }

    /** Returns the point _H_ of this rounded rectangle. **/
    val pointH: Vector2F
        get() {
            val (cX: Float, cY: Float) = center
            val (rotR: Float, rotI: Float) = orientation
            val halfWidth: Float = width * 0.5f
            val halfHeight: Float = height * 0.5f
            val halfHeightMinusRadius: Float = halfHeight - cornerRadius
            val addendC: Float = rotR * halfHeightMinusRadius
            val addendD: Float = rotI * halfHeightMinusRadius
            val addendE: Float = rotR * halfWidth
            val addendF: Float = rotI * halfWidth
            val addendSumCF: Float = addendC + addendF
            val addendDiffED: Float = addendE - addendD

            return Vector2F(cX + addendDiffED, cY + addendSumCF)
        }

    /** Returns the corner center _A_ of this rounded rectangle. **/
    val cornerCenterA: Vector2F
        get() {
            val (cX: Float, cY: Float) = center
            val (rotR: Float, rotI: Float) = orientation
            val halfWidth: Float = width * 0.5f
            val halfHeight: Float = height * 0.5f
            val cornerRadius: Float = this.cornerRadius
            val halfWidthMinusRadius: Float = halfWidth - cornerRadius
            val halfHeightMinusRadius: Float = halfHeight - cornerRadius
            val addendA: Float = rotR * halfWidthMinusRadius
            val addendB: Float = rotI * halfWidthMinusRadius
            val addendC: Float = rotR * halfHeightMinusRadius
            val addendD: Float = rotI * halfHeightMinusRadius
            val addendSumBC: Float = addendB + addendC
            val addendDiffAD: Float = addendA - addendD

            return Vector2F(cX + addendDiffAD, cY + addendSumBC)
        }

    /** Returns the corner center _B_ of this rounded rectangle. **/
    val cornerCenterB: Vector2F
        get() {
            val (cX: Float, cY: Float) = center
            val (rotR: Float, rotI: Float) = orientation
            val halfWidth: Float = width * 0.5f
            val halfHeight: Float = height * 0.5f
            val cornerRadius: Float = this.cornerRadius
            val halfWidthMinusRadius: Float = halfWidth - cornerRadius
            val halfHeightMinusRadius: Float = halfHeight - cornerRadius
            val addendA: Float = rotR * halfWidthMinusRadius
            val addendB: Float = rotI * halfWidthMinusRadius
            val addendC: Float = rotR * halfHeightMinusRadius
            val addendD: Float = rotI * halfHeightMinusRadius
            val addendSumAD: Float = addendA + addendD
            val addendDiffBC: Float = addendB - addendC

            return Vector2F(cX - addendSumAD, cY - addendDiffBC)
        }

    /** Returns the corner center _C_ of this rounded rectangle. **/
    val cornerCenterC: Vector2F
        get() {
            val (cX: Float, cY: Float) = center
            val (rotR: Float, rotI: Float) = orientation
            val halfWidth: Float = width * 0.5f
            val halfHeight: Float = height * 0.5f
            val cornerRadius: Float = this.cornerRadius
            val halfWidthMinusRadius: Float = halfWidth - cornerRadius
            val halfHeightMinusRadius: Float = halfHeight - cornerRadius
            val addendA: Float = rotR * halfWidthMinusRadius
            val addendB: Float = rotI * halfWidthMinusRadius
            val addendC: Float = rotR * halfHeightMinusRadius
            val addendD: Float = rotI * halfHeightMinusRadius
            val addendSumBC: Float = addendB + addendC
            val addendDiffAD: Float = addendA - addendD

            return Vector2F(cX - addendDiffAD, cY - addendSumBC)
        }

    /** Returns the corner center _D_ of this rounded rectangle. **/
    val cornerCenterD: Vector2F
        get() {
            val (cX: Float, cY: Float) = center
            val (rotR: Float, rotI: Float) = orientation
            val halfWidth: Float = width * 0.5f
            val halfHeight: Float = height * 0.5f
            val cornerRadius: Float = this.cornerRadius
            val halfWidthMinusRadius: Float = halfWidth - cornerRadius
            val halfHeightMinusRadius: Float = halfHeight - cornerRadius
            val addendA: Float = rotR * halfWidthMinusRadius
            val addendB: Float = rotI * halfWidthMinusRadius
            val addendC: Float = rotR * halfHeightMinusRadius
            val addendD: Float = rotI * halfHeightMinusRadius
            val addendSumAD: Float = addendA + addendD
            val addendDiffBC: Float = addendB - addendC

            return Vector2F(cX + addendSumAD, cY + addendDiffBC)
        }

    override val area: Float
        get() {
            val radius: Float = cornerRadius
            val squaredRadius: Float = radius * radius

            return PI.toFloat() * squaredRadius + width * height - 4f * squaredRadius
        }

    override val perimeter: Float
        get() {
            val radius: Float = cornerRadius

            return (2.0 * PI).toFloat() * radius + 2f * (width + height - 4f * radius)
        }

    /**
     * Returns the position of this object in reference to the origin of [Vector2F.ZERO].
     *
     * This property is equal to [center].
     */
    override val position: Vector2F
        get() = center

    override fun movedBy(displacement: Vector2F): RoundedRectangle =
        copy(center = center + displacement)

    override fun movedTo(position: Vector2F): RoundedRectangle = copy(center = position)

    override fun rotatedBy(rotation: AngleF): RoundedRectangle =
        copy(orientation = orientation * ComplexF.fromAngle(rotation))

    override fun rotatedBy(rotation: ComplexF): RoundedRectangle =
        copy(orientation = orientation * rotation)

    override fun rotatedTo(orientation: AngleF): RoundedRectangle =
        copy(orientation = ComplexF.fromAngle(orientation))

    override fun rotatedTo(orientation: ComplexF): RoundedRectangle =
        copy(orientation = orientation)

    private fun rotatedAroundPointByImpl(point: Vector2F, rotation: ComplexF): RoundedRectangle {
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

    override fun rotatedAroundPointBy(point: Vector2F, rotation: AngleF): RoundedRectangle =
        rotatedAroundPointByImpl(point, ComplexF.fromAngle(rotation))

    override fun rotatedAroundPointBy(point: Vector2F, rotation: ComplexF): RoundedRectangle =
        rotatedAroundPointByImpl(point, rotation)

    private fun rotatedAroundPointToImpl(
        point: Vector2F, orientation: ComplexF
    ): RoundedRectangle {
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
        } else {
            copy(orientation = orientation)
        }
    }

    override fun rotatedAroundPointTo(point: Vector2F, orientation: AngleF): RoundedRectangle =
        rotatedAroundPointToImpl(point, ComplexF.fromAngle(orientation))

    override fun rotatedAroundPointTo(point: Vector2F, orientation: ComplexF): RoundedRectangle =
        rotatedAroundPointToImpl(point, orientation)

    override fun scaledBy(factor: Float): RoundedRectangle {
        val absFactor: Float = factor.absoluteValue

        return copy(
            orientation = orientation * 1f.withSign(factor),
            width = width * absFactor,
            height = height * absFactor,
            cornerRadius = cornerRadius * absFactor
        )
    }

    override fun dilatedBy(point: Vector2F, factor: Float): RoundedRectangle {
        val f: Float = 1f - factor
        val cX: Float = center.x * factor + point.x * f
        val cY: Float = center.y * factor + point.y * f
        val (rotR: Float, rotI: Float) = orientation * 1f.withSign(factor)
        val absFactor: Float = factor.absoluteValue
        val width: Float = width * absFactor
        val height: Float = height * absFactor
        val cornerRadius: Float = cornerRadius * absFactor

        return copy(
            center = Vector2F(cX, cY),
            orientation = ComplexF(rotR, rotI),
            width = width,
            height = height,
            cornerRadius = cornerRadius,
        )
    }

    override fun transformedBy(displacement: Vector2F, rotation: AngleF): RoundedRectangle = copy(
        center = center + displacement,
        orientation = orientation * ComplexF.fromAngle(rotation)
    )

    override fun transformedBy(displacement: Vector2F, rotation: ComplexF): RoundedRectangle =
        copy(
            center = center + displacement,
            orientation = orientation * rotation
        )

    private inline fun transformedByImpl(
        displacement: Vector2F, rotation: ComplexF, scaleFactor: Float
    ): RoundedRectangle {
        val absScaleFactor: Float = scaleFactor.absoluteValue

        return copy(
            center = center + displacement,
            orientation = orientation * rotation * 1f.withSign(scaleFactor),
            width = width * absScaleFactor,
            height = height * absScaleFactor,
            cornerRadius = cornerRadius * absScaleFactor
        )
    }

    override fun transformedBy(
        displacement: Vector2F, rotation: AngleF, scaleFactor: Float
    ): RoundedRectangle = transformedByImpl(
        displacement, ComplexF.fromAngle(rotation), scaleFactor
    )

    override fun transformedBy(
        displacement: Vector2F, rotation: ComplexF, scaleFactor: Float
    ): RoundedRectangle = transformedByImpl(displacement, rotation, scaleFactor)

    override fun transformedTo(position: Vector2F, orientation: AngleF): RoundedRectangle = copy(
        center = position,
        orientation = ComplexF.fromAngle(orientation)
    )

    override fun transformedTo(position: Vector2F, orientation: ComplexF): RoundedRectangle = copy(
        center = position,
        orientation = orientation
    )

    /**
     * Returns a copy of this rounded rectangle interpolated [to] other rounded rectangle [by] a
     * factor.
     *
     * @param to the rounded rectangle to which this rounded rectangle is interpolated.
     * @param by the interpolation factor which is expected to be in the range of `[0, 1]`.
     */
    fun interpolated(to: RoundedRectangle, by: Float): RoundedRectangle = copy(
        center = Vector2F.lerp(center, to.center, by),
        orientation = ComplexF.slerp(orientation, to.orientation, by),
        width = Float.lerp(width, to.width, by),
        height = Float.lerp(height, to.height, by),
        cornerRadius = Float.lerp(cornerRadius, to.cornerRadius, by)
    )

    /** Returns the closest point on this rounded rectangle to the given [point]. **/
    fun closestPointTo(point: Vector2F): Vector2F {
        val center: Vector2F = center
        val orientation: ComplexF = orientation
        val cornerRadius: Float = cornerRadius
        val halfWidth: Float = width * 0.5f
        val halfHeight: Float = height * 0.5f
        val halfWidthMinusRadius: Float = halfWidth - cornerRadius
        val halfHeightMinusRadius: Float = halfHeight - cornerRadius
        val p1: ComplexF = orientation.conjugate *
                ComplexF(point.x - center.x, point.y - center.y)
        val (p1X: Float, p1Y: Float) = p1
        val p1XAbs: Float = p1X.absoluteValue
        val p1YAbs: Float = p1Y.absoluteValue
        val cornerCenterX: Float = halfWidthMinusRadius.withSign(p1X)
        val cornerCenterY: Float = halfHeightMinusRadius.withSign(p1Y)
        val dx: Float = p1X - cornerCenterX
        val dy: Float = p1Y - cornerCenterY
        val distance: Float = sqrt(dx * dx + dy * dy)
        val isOutOfCorner: Boolean = (p1YAbs > halfHeightMinusRadius) and
                (p1XAbs > halfWidthMinusRadius) and
                (distance > cornerRadius)

        return when {
            isOutOfCorner -> {
                val t: Float = cornerRadius / distance

                center + Vector2F(
                    cornerCenterX + dx * t, cornerCenterY + dy * t
                ) * orientation
            }

            p1XAbs > halfWidth -> center + Vector2F(halfWidth.withSign(p1X), p1Y) * orientation

            p1YAbs > halfHeight -> center + Vector2F(p1X, halfHeight.withSign(p1Y)) * orientation

            else -> point
        }
    }

    /** Returns `true` if this rounded rectangle intersects the given [ray]. **/
    fun intersects(ray: Ray): Boolean {
        val (rectCX: Float, rectCY: Float) = center
        val (rectOR: Float, rectOI: Float) = orientation
        val halfWidth: Float = width * 0.5f
        val halfHeight: Float = height * 0.5f
        val cornerRadius: Float = cornerRadius
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

        val (rayCX: Float, rayCY: Float) = ray.origin
        val (rayDirX: Float, rayDirY: Float) = ray.direction
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

    /** Returns `true` if this rounded rectangle contains the given [point]. **/
    operator fun contains(point: Vector2F): Boolean {
        val (cX: Float, cY: Float) = center
        val (rotR: Float, rotI: Float) = orientation
        val cornerRadius: Float = cornerRadius
        val halfWidth: Float = width * 0.5f
        val halfHeight: Float = height * 0.5f
        val halfWidthMinusRadius: Float = halfWidth - cornerRadius
        val halfHeightMinusRadius: Float = halfHeight - cornerRadius
        val (pX: Float, pY: Float) = point
        val cpDiffX: Float = pX - cX
        val cpDiffY: Float = pY - cY
        val p1X: Float = rotR * cpDiffX + rotI * cpDiffY
        val p1Y: Float = rotR * cpDiffY - rotI * cpDiffX
        val p1XAbs: Float = p1X.absoluteValue
        val p1YAbs: Float = p1Y.absoluteValue
        val cornerCenterX: Float = halfWidthMinusRadius.withSign(p1X)
        val cornerCenterY: Float = halfHeightMinusRadius.withSign(p1Y)
        val dx: Float = p1X - cornerCenterX
        val dy: Float = p1Y - cornerCenterY
        val distance: Float = sqrt(dx * dx + dy * dy)

        return (p1YAbs <= halfHeightMinusRadius) or
                (p1XAbs <= halfWidthMinusRadius) or
                (distance <= cornerRadius) and
                (p1XAbs <= halfWidth) and
                (p1YAbs <= halfHeight)
    }

    /** Creates an iterator over the points of this rounded rectangle. **/
    fun pointIterator(): Vector2FIterator = PointIterator(this, index = 0)

    /** Creates an iterator over the corner centers of this rounded rectangle. **/
    fun cornerCenterIterator(): Vector2FIterator = CornerCenterIterator(this, index = 0)

    /**
     * Returns a copy of this instance with specified properties changed.
     *
     * @param orientation the value is expected to be [normalized][ComplexF.normalized].
     */
    fun copy(
        center: Vector2F = this.center,
        orientation: ComplexF = this.orientation,
        width: Float = this.width,
        height: Float = this.height,
        cornerRadius: Float = this.cornerRadius
    ): RoundedRectangle

    /** Returns the [center] of this rounded rectangle. **/
    operator fun component1(): Vector2F = center

    /** Returns the [orientation] of this rounded rectangle. **/
    operator fun component2(): ComplexF = orientation

    /** Returns the [width] of this rounded rectangle. **/
    operator fun component3(): Float = width

    /** Returns the [height] of this rounded rectangle. **/
    operator fun component4(): Float = height

    /** Returns the [cornerRadius] of this rounded rectangle. **/
    operator fun component5(): Float = cornerRadius

    private class CornerCenterIterator(
        rectangle: RoundedRectangle, index: Int
    ) : Vector2FIterator() {
        private val _cornerCenterA: Vector2F
        private val _cornerCenterB: Vector2F
        private val _cornerCenterC: Vector2F
        private val _cornerCenterD: Vector2F
        private var _index: Int

        init {
            val (cX: Float, cY: Float) = rectangle.center
            val (oR: Float, oI: Float) = rectangle.orientation
            val halfWidth: Float = rectangle.width * 0.5f
            val halfHeight: Float = rectangle.height * 0.5f
            val cornerRadius: Float = rectangle.cornerRadius
            val halfWidthMinusRadius: Float = halfWidth - cornerRadius
            val halfHeightMinusRadius: Float = halfHeight - cornerRadius
            val addendA: Float = oR * halfWidthMinusRadius
            val addendB: Float = oI * halfWidthMinusRadius
            val addendC: Float = oR * halfHeightMinusRadius
            val addendD: Float = oI * halfHeightMinusRadius
            val addendSumBC: Float = addendB + addendC
            val addendSumAD: Float = addendA + addendD
            val addendDiffBC: Float = addendB - addendC
            val addendDiffAD: Float = addendA - addendD
            _cornerCenterA = Vector2F(cX + addendDiffAD, cY + addendSumBC)
            _cornerCenterB = Vector2F(cX - addendSumAD, cY - addendDiffBC)
            _cornerCenterC = Vector2F(cX - addendDiffAD, cY - addendSumBC)
            _cornerCenterD = Vector2F(cX + addendSumAD, cY + addendDiffBC)
            _index = index
        }

        override fun hasNext(): Boolean = _index < 4

        override fun nextVector2F(): Vector2F = when (_index++) {
            0 -> _cornerCenterA
            1 -> _cornerCenterB
            2 -> _cornerCenterC
            3 -> _cornerCenterD
            else -> throw NoSuchElementException("${_index - 1}")
        }
    }

    private class PointIterator(rectangle: RoundedRectangle, index: Int) : Vector2FIterator() {
        private val _pointA: Vector2F
        private val _pointB: Vector2F
        private val _pointC: Vector2F
        private val _pointD: Vector2F
        private val _pointE: Vector2F
        private val _pointF: Vector2F
        private val _pointG: Vector2F
        private val _pointH: Vector2F
        private var _index: Int

        init {
            val (cX: Float, cY: Float) = rectangle.center
            val (oR: Float, oI: Float) = rectangle.orientation
            val halfWidth: Float = rectangle.width * 0.5f
            val halfHeight: Float = rectangle.height * 0.5f
            val cornerRadius: Float = rectangle.cornerRadius
            val halfWidthMinusRadius: Float = halfWidth - cornerRadius
            val halfHeightMinusRadius: Float = halfHeight - cornerRadius
            val addendA: Float = oR * halfWidthMinusRadius
            val addendB: Float = oI * halfWidthMinusRadius
            val addendC: Float = oR * halfHeightMinusRadius
            val addendD: Float = oI * halfHeightMinusRadius
            val addendE: Float = oR * halfWidth
            val addendF: Float = oI * halfWidth
            val addendG: Float = oR * halfHeight
            val addendH: Float = oI * halfHeight
            val addendSumAH: Float = addendA + addendH
            val addendSumBG: Float = addendB + addendG
            val addendSumCF: Float = addendC + addendF
            val addendSumED: Float = addendE + addendD
            val addendDiffAH: Float = addendA - addendH
            val addendDiffBG: Float = addendB - addendG
            val addendDiffCF: Float = addendC - addendF
            val addendDiffED: Float = addendE - addendD
            _pointA = Vector2F(cX + addendDiffAH, cY + addendSumBG)
            _pointB = Vector2F(cX - addendSumAH, cY - addendDiffBG)
            _pointC = Vector2F(cX - addendSumED, cY + addendDiffCF)
            _pointD = Vector2F(cX - addendDiffED, cY - addendSumCF)
            _pointE = Vector2F(cX - addendDiffAH, cY - addendSumBG)
            _pointF = Vector2F(cX + addendSumAH, cY + addendDiffBG)
            _pointG = Vector2F(cX + addendSumED, cY - addendDiffCF)
            _pointH = Vector2F(cX + addendDiffED, cY + addendSumCF)
            _index = index
        }

        override fun hasNext(): Boolean = _index < 8

        override fun nextVector2F(): Vector2F = when (_index++) {
            0 -> _pointA
            1 -> _pointB
            2 -> _pointC
            3 -> _pointD
            4 -> _pointE
            5 -> _pointF
            6 -> _pointG
            7 -> _pointH
            else -> throw NoSuchElementException("${_index - 1}")
        }
    }
}