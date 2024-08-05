package com.sztorm.lowallocmath.euclidean2d

import com.sztorm.lowallocmath.AngleF
import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F
import com.sztorm.lowallocmath.Vector2FIterator
import kotlin.math.abs
import kotlin.math.absoluteValue
import kotlin.math.sqrt

/** Creates a new instance of [LineSegment]. **/
fun LineSegment(pointA: Vector2F, pointB: Vector2F): LineSegment =
    MutableLineSegment(pointA, pointB)

/**
 * Represents a transformable line segment in a two-dimensional Euclidean space.
 *
 * Implementations that use default-implemented members of this interface must make sure that the
 * properties [pointA], [pointB] and the [copy] method are independent of other members and the
 * computational complexity of these members is trivial.
 */
interface LineSegment : Transformable {
    /** Returns the point _A_ of this line segment. **/
    val pointA: Vector2F

    /** Returns the point _B_ of this line segment. **/
    val pointB: Vector2F

    /** Returns the center of this line segment. **/
    val center: Vector2F
        get() = (pointA + pointB) * 0.5f

    /** Returns the length of this line segment. **/
    val length: Float
        get() = pointA.distanceTo(pointB)

    /**
     * Returns the position of this object in reference to the origin of [Vector2F.ZERO].
     *
     * This property is equal to [center].
     */
    override val position: Vector2F
        get() = (pointA + pointB) * 0.5f

    /**
     * Returns the orientation of this object in reference to the origin of [ComplexF.ONE].
     *
     * This property is determined by the direction formed from [center] to [pointA].
     */
    override val orientation: ComplexF
        get() = (pointA - pointB).normalized.toComplexF()

    override fun movedBy(displacement: Vector2F): LineSegment = copy(
        pointA = pointA + displacement,
        pointB = pointB + displacement
    )

    override fun movedTo(position: Vector2F): LineSegment {
        val center: Vector2F = (pointA + pointB) * 0.5f
        val displacement: Vector2F = position - center

        return copy(
            pointA = pointA + displacement,
            pointB = pointB + displacement
        )
    }

    private fun rotatedByImpl(rotation: ComplexF): LineSegment {
        val (paX: Float, paY: Float) = pointA
        val (pbX: Float, pbY: Float) = pointB
        val (rotR: Float, rotI: Float) = rotation
        val cX: Float = (paX + pbX) * 0.5f
        val cY: Float = (paY + pbY) * 0.5f
        val pcaX: Float = paX - cX
        val pcaY: Float = paY - cY
        val pcbX: Float = pbX - cX
        val pcbY: Float = pbY - cY

        return copy(
            pointA = Vector2F(
                pcaX * rotR - pcaY * rotI + cX, pcaY * rotR + pcaX * rotI + cY
            ),
            pointB = Vector2F(
                pcbX * rotR - pcbY * rotI + cX, pcbY * rotR + pcbX * rotI + cY
            )
        )
    }

    override fun rotatedBy(rotation: AngleF): LineSegment =
        rotatedByImpl(ComplexF.fromAngle(rotation))

    override fun rotatedBy(rotation: ComplexF): LineSegment = rotatedByImpl(rotation)

    private fun rotatedToImpl(orientation: ComplexF): LineSegment {
        val (paX: Float, paY: Float) = pointA
        val (pbX: Float, pbY: Float) = pointB
        val (rotR: Float, rotI: Float) = orientation
        val cX: Float = (paX + pbX) * 0.5f
        val cY: Float = (paY + pbY) * 0.5f
        val pabY: Float = pbY - paY
        val pabX: Float = pbX - paX
        val halfLength = sqrt(pabX * pabX + pabY * pabY) * 0.5f

        return copy(
            pointA = Vector2F(cX + halfLength * rotR, cY + halfLength * rotI),
            pointB = Vector2F(cX - halfLength * rotR, cY - halfLength * rotI)
        )
    }

    override fun rotatedTo(orientation: AngleF): LineSegment =
        rotatedToImpl(ComplexF.fromAngle(orientation))

    override fun rotatedTo(orientation: ComplexF): LineSegment =
        rotatedToImpl(orientation)

    private fun rotatedAroundPointByImpl(point: Vector2F, rotation: ComplexF): LineSegment {
        val (paX: Float, paY: Float) = pointA
        val (pbX: Float, pbY: Float) = pointB
        val (pX: Float, pY: Float) = point
        val (rotR: Float, rotI: Float) = rotation
        val cX: Float = (paX + pbX) * 0.5f
        val cY: Float = (paY + pbY) * 0.5f
        val cpDiffX: Float = cX - pX
        val cpDiffY: Float = cY - pY
        val targetCenterX: Float = cpDiffX * rotR - cpDiffY * rotI + pX
        val targetCenterY: Float = cpDiffY * rotR + cpDiffX * rotI + pY
        val pcaX: Float = paX - cX
        val pcaY: Float = paY - cY
        val pcbX: Float = pbX - cX
        val pcbY: Float = pbY - cY

        return copy(
            pointA = Vector2F(
                pcaX * rotR - pcaY * rotI + targetCenterX,
                pcaY * rotR + pcaX * rotI + targetCenterY
            ),
            pointB = Vector2F(
                pcbX * rotR - pcbY * rotI + targetCenterX,
                pcbY * rotR + pcbX * rotI + targetCenterY
            )
        )
    }

    override fun rotatedAroundPointBy(point: Vector2F, rotation: AngleF): LineSegment =
        rotatedAroundPointByImpl(point, ComplexF.fromAngle(rotation))

    override fun rotatedAroundPointBy(point: Vector2F, rotation: ComplexF): LineSegment =
        rotatedAroundPointByImpl(point, rotation)

    private fun rotatedAroundPointToImpl(point: Vector2F, orientation: ComplexF): LineSegment {
        val (paX: Float, paY: Float) = pointA
        val (pbX: Float, pbY: Float) = pointB
        val (pX: Float, pY: Float) = point
        val (rotR: Float, rotI: Float) = orientation
        val cX: Float = (paX + pbX) * 0.5f
        val cY: Float = (paY + pbY) * 0.5f
        val cpDiffX: Float = cX - pX
        val cpDiffY: Float = cY - pY
        val centerToPointDist: Float = sqrt(cpDiffX * cpDiffX + cpDiffY * cpDiffY)

        if (centerToPointDist > 0.00001f) {
            val pointRotR: Float = cpDiffX / centerToPointDist
            val pointRotI: Float = cpDiffY / centerToPointDist
            val pRotR: Float = pointRotR * rotR + pointRotI * rotI
            val pRotI: Float = pointRotR * rotI - pointRotI * rotR
            val targetCenterX: Float = rotR * centerToPointDist + pX
            val targetCenterY: Float = rotI * centerToPointDist + pY
            val pcaX: Float = paX - cX
            val pcaY: Float = paY - cY
            val pcbX: Float = pbX - cX
            val pcbY: Float = pbY - cY

            return copy(
                pointA = Vector2F(
                    pcaX * pRotR - pcaY * pRotI + targetCenterX,
                    pcaY * pRotR + pcaX * pRotI + targetCenterY
                ),
                pointB = Vector2F(
                    pcbX * pRotR - pcbY * pRotI + targetCenterX,
                    pcbY * pRotR + pcbX * pRotI + targetCenterY
                )
            )
        } else {
            val pabX: Float = pbX - paX
            val pabY: Float = pbY - paY
            val halfLength: Float = sqrt(pabX * pabX + pabY * pabY) * 0.5f

            return copy(
                pointA = Vector2F(cX + halfLength * rotR, cY + halfLength * rotI),
                pointB = Vector2F(cX - halfLength * rotR, cY - halfLength * rotI)
            )
        }
    }

    override fun rotatedAroundPointTo(point: Vector2F, orientation: AngleF): LineSegment =
        rotatedAroundPointToImpl(point, ComplexF.fromAngle(orientation))

    override fun rotatedAroundPointTo(point: Vector2F, orientation: ComplexF): LineSegment =
        rotatedAroundPointToImpl(point, orientation)

    override fun scaledBy(factor: Float): LineSegment {
        val (paX: Float, paY: Float) = pointA
        val (pbX: Float, pbY: Float) = pointB
        val cX: Float = (paX + pbX) * 0.5f
        val cY: Float = (paY + pbY) * 0.5f
        val f: Float = 1f - factor
        val addendX: Float = cX * f
        val addendY: Float = cY * f

        return copy(
            pointA = Vector2F(paX * factor + addendX, paY * factor + addendY),
            pointB = Vector2F(pbX * factor + addendX, pbY * factor + addendY)
        )
    }

    override fun dilatedBy(point: Vector2F, factor: Float): LineSegment {
        val (paX: Float, paY: Float) = pointA
        val (pbX: Float, pbY: Float) = pointB
        val (pX: Float, pY: Float) = point
        val f: Float = 1f - factor
        val addendX: Float = pX * f
        val addendY: Float = pY * f

        return copy(
            pointA = Vector2F(paX * factor + addendX, paY * factor + addendY),
            pointB = Vector2F(pbX * factor + addendX, pbY * factor + addendY)
        )
    }

    private fun transformedByImpl(displacement: Vector2F, rotation: ComplexF): LineSegment {
        val (paX: Float, paY: Float) = pointA
        val (pbX: Float, pbY: Float) = pointB
        val (dX: Float, dY: Float) = displacement
        val (rotR: Float, rotI: Float) = rotation
        val cX: Float = (paX + pbX) * 0.5f
        val cY: Float = (paY + pbY) * 0.5f
        val pcaX: Float = paX - cX
        val pcaY: Float = paY - cY
        val pcbX: Float = pbX - cX
        val pcbY: Float = pbY - cY
        val targetPosX: Float = cX + dX
        val targetPosY: Float = cY + dY

        return copy(
            pointA = Vector2F(
                pcaX * rotR - pcaY * rotI + targetPosX,
                pcaY * rotR + pcaX * rotI + targetPosY
            ),
            pointB = Vector2F(
                pcbX * rotR - pcbY * rotI + targetPosX,
                pcbY * rotR + pcbX * rotI + targetPosY
            )
        )
    }

    override fun transformedBy(displacement: Vector2F, rotation: AngleF): LineSegment =
        transformedByImpl(displacement, ComplexF.fromAngle(rotation))

    override fun transformedBy(displacement: Vector2F, rotation: ComplexF): LineSegment =
        transformedByImpl(displacement, rotation)

    private fun transformedByImpl(
        displacement: Vector2F, rotation: ComplexF, scaleFactor: Float
    ): LineSegment {
        val (paX: Float, paY: Float) = pointA
        val (pbX: Float, pbY: Float) = pointB
        val (dX: Float, dY: Float) = displacement
        val (rotR: Float, rotI: Float) = rotation
        val cX: Float = (paX + pbX) * 0.5f
        val cY: Float = (paY + pbY) * 0.5f
        val pcaX: Float = paX - cX
        val pcaY: Float = paY - cY
        val pcbX: Float = pbX - cX
        val pcbY: Float = pbY - cY
        val targetPosX: Float = cX + dX
        val targetPosY: Float = cY + dY
        val f: Float = 1f - scaleFactor
        val addendX: Float = targetPosX * f
        val addendY: Float = targetPosY * f

        return copy(
            pointA = Vector2F(
                (pcaX * rotR - pcaY * rotI + targetPosX) * scaleFactor + addendX,
                (pcaY * rotR + pcaX * rotI + targetPosY) * scaleFactor + addendY
            ),
            pointB = Vector2F(
                (pcbX * rotR - pcbY * rotI + targetPosX) * scaleFactor + addendX,
                (pcbY * rotR + pcbX * rotI + targetPosY) * scaleFactor + addendY
            )
        )
    }

    override fun transformedBy(
        displacement: Vector2F, rotation: AngleF, scaleFactor: Float
    ): LineSegment = transformedByImpl(displacement, ComplexF.fromAngle(rotation), scaleFactor)

    override fun transformedBy(
        displacement: Vector2F, rotation: ComplexF, scaleFactor: Float
    ): LineSegment = transformedByImpl(displacement, rotation, scaleFactor)

    private fun transformedToImpl(position: Vector2F, orientation: ComplexF): LineSegment {
        val (paX: Float, paY: Float) = pointA
        val (pbX: Float, pbY: Float) = pointB
        val (pX: Float, pY: Float) = position
        val (rotR: Float, rotI: Float) = orientation
        val pabX: Float = pbX - paX
        val pabY: Float = pbY - paY
        val halfLength: Float = sqrt(pabX * pabX + pabY * pabY) * 0.5f

        return copy(
            pointA = Vector2F(pX + halfLength * rotR, pY + halfLength * rotI),
            pointB = Vector2F(pX - halfLength * rotR, pY - halfLength * rotI)
        )
    }

    override fun transformedTo(position: Vector2F, orientation: AngleF): LineSegment =
        transformedToImpl(position, ComplexF.fromAngle(orientation))

    override fun transformedTo(position: Vector2F, orientation: ComplexF): LineSegment =
        transformedToImpl(position, orientation)

    /**
     * Returns a copy of this line segment interpolated [to] other line segment [by] a factor.
     *
     * @param to the line segment to which this line segment is interpolated.
     * @param by the interpolation factor which is expected to be in the range of `[0, 1]`.
     */
    fun interpolated(to: LineSegment, by: Float): LineSegment = copy(
        pointA = Vector2F.lerp(pointA, to.pointA, by),
        pointB = Vector2F.lerp(pointB, to.pointB, by)
    )

    /** Returns the closest point on this line segment to the given [point]. **/
    fun closestPointTo(point: Vector2F): Vector2F {
        val pointA: Vector2F = this.pointA
        val ab: Vector2F = pointB - pointA
        val epsilon = 0.00001f

        if ((abs(ab.x) <= epsilon) and (abs(ab.y) <= epsilon)) {
            return pointA
        }
        val ap: Vector2F = point - pointA
        val t: Float = (ab dot ap) / (ab dot ab)
        val tClamped: Float = when {
            t < 0f -> 0f
            t > 1f -> 1f
            else -> t
        }
        return pointA + ab * tClamped
    }

    /** Returns `true` if this line segment intersects the given [ray]. **/
    fun intersects(ray: Ray): Boolean {
        val (oX: Float, oY: Float) = ray.origin
        val (dirX: Float, dirY: Float) = ray.direction
        val (aX: Float, aY: Float) = pointA
        val (bX: Float, bY: Float) = pointB
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

    /** Returns `true` if this line segment approximately contains the given [point]. **/
    operator fun contains(point: Vector2F): Boolean {
        val pointA: Vector2F = this.pointA
        val ab: Vector2F = pointB - pointA
        val epsilon = 0.00001f

        if ((abs(ab.x) <= epsilon) and (abs(ab.y) <= epsilon)) {
            return pointA.isApproximately(point)
        }
        val ap: Vector2F = point - pointA
        val t: Float = (ab dot ap) / (ab dot ab)

        return if ((t < 0f) or (t > 1f)) false
        else {
            val closestPoint: Vector2F = pointA + ab * t

            return closestPoint.isApproximately(point)
        }
    }

    /** Creates an iterator over the points of this line segment. **/
    fun pointIterator(): Vector2FIterator = PointIterator(this, index = 0)

    /** Returns a copy of this instance with specified properties changed. **/
    fun copy(pointA: Vector2F = this.pointA, pointB: Vector2F = this.pointB): LineSegment

    /** Returns the [pointA] of this line segment. **/
    operator fun component1(): Vector2F = pointA

    /** Returns the [pointB] of this line segment. **/
    operator fun component2(): Vector2F = pointB

    private class PointIterator(
        private val lineSegment: LineSegment,
        private var index: Int
    ) : Vector2FIterator() {
        override fun hasNext(): Boolean = index < 2

        override fun nextVector2F(): Vector2F = when (index++) {
            0 -> lineSegment.pointA
            1 -> lineSegment.pointB
            else -> throw NoSuchElementException("${index - 1}")
        }
    }
}