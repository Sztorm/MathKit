package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.AngleF
import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F
import com.sztorm.lowallocmath.Vector2FIterator
import kotlin.math.abs
import kotlin.math.sqrt

fun LineSegment(pointA: Vector2F, pointB: Vector2F): LineSegment =
    MutableLineSegment(pointA, pointB)

interface LineSegment : Transformable {
    val pointA: Vector2F
    val pointB: Vector2F

    val center: Vector2F
        get() = (pointA + pointB) * 0.5f

    val length: Float
        get() = pointA.distanceTo(pointB)

    override val position: Vector2F
        get() = (pointA + pointB) * 0.5f

    override val orientation: ComplexF
        get() = (pointA - pointB).normalized.toComplexF()

    override fun movedBy(offset: Vector2F): LineSegment = copy(
        pointA = pointA + offset,
        pointB = pointB + offset
    )

    override fun movedTo(position: Vector2F): LineSegment {
        val center: Vector2F = (pointA + pointB) * 0.5f
        val offset: Vector2F = position - center

        return copy(
            pointA = pointA + offset,
            pointB = pointB + offset
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

        return MutableLineSegment(
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

        return MutableLineSegment(
            pointA = Vector2F(paX * factor + addendX, paY * factor + addendY),
            pointB = Vector2F(pbX * factor + addendX, pbY * factor + addendY)
        )
    }

    private fun transformedByImpl(offset: Vector2F, rotation: ComplexF): LineSegment {
        val (paX: Float, paY: Float) = pointA
        val (pbX: Float, pbY: Float) = pointB
        val (oX: Float, oY: Float) = offset
        val (rotR: Float, rotI: Float) = rotation
        val cX: Float = (paX + pbX) * 0.5f
        val cY: Float = (paY + pbY) * 0.5f
        val pcaX: Float = paX - cX
        val pcaY: Float = paY - cY
        val pcbX: Float = pbX - cX
        val pcbY: Float = pbY - cY
        val targetPosX: Float = cX + oX
        val targetPosY: Float = cY + oY

        return MutableLineSegment(
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

    override fun transformedBy(offset: Vector2F, rotation: AngleF): LineSegment =
        transformedByImpl(offset, ComplexF.fromAngle(rotation))

    override fun transformedBy(offset: Vector2F, rotation: ComplexF): LineSegment =
        transformedByImpl(offset, rotation)

    private fun transformedByImpl(
        offset: Vector2F, rotation: ComplexF, factor: Float
    ): LineSegment {
        val (paX: Float, paY: Float) = pointA
        val (pbX: Float, pbY: Float) = pointB
        val (oX: Float, oY: Float) = offset
        val (rotR: Float, rotI: Float) = rotation
        val cX: Float = (paX + pbX) * 0.5f
        val cY: Float = (paY + pbY) * 0.5f
        val pcaX: Float = paX - cX
        val pcaY: Float = paY - cY
        val pcbX: Float = pbX - cX
        val pcbY: Float = pbY - cY
        val targetPosX: Float = cX + oX
        val targetPosY: Float = cY + oY
        val f: Float = 1f - factor
        val addendX: Float = targetPosX * f
        val addendY: Float = targetPosY * f

        return MutableLineSegment(
            pointA = Vector2F(
                (pcaX * rotR - pcaY * rotI + targetPosX) * factor + addendX,
                (pcaY * rotR + pcaX * rotI + targetPosY) * factor + addendY
            ),
            pointB = Vector2F(
                (pcbX * rotR - pcbY * rotI + targetPosX) * factor + addendX,
                (pcbY * rotR + pcbX * rotI + targetPosY) * factor + addendY
            )
        )
    }

    override fun transformedBy(offset: Vector2F, rotation: AngleF, factor: Float): LineSegment =
        transformedByImpl(offset, ComplexF.fromAngle(rotation), factor)

    override fun transformedBy(offset: Vector2F, rotation: ComplexF, factor: Float): LineSegment =
        transformedByImpl(offset, rotation, factor)

    private fun transformedToImpl(position: Vector2F, orientation: ComplexF): LineSegment {
        val (paX: Float, paY: Float) = pointA
        val (pbX: Float, pbY: Float) = pointB
        val (pX: Float, pY: Float) = position
        val (rotR: Float, rotI: Float) = orientation
        val pabX: Float = pbX - paX
        val pabY: Float = pbY - paY
        val halfLength: Float = sqrt(pabX * pabX + pabY * pabY) * 0.5f

        return MutableLineSegment(
            pointA = Vector2F(pX + halfLength * rotR, pY + halfLength * rotI),
            pointB = Vector2F(pX - halfLength * rotR, pY - halfLength * rotI)
        )
    }

    override fun transformedTo(position: Vector2F, orientation: AngleF): LineSegment =
        transformedToImpl(position, ComplexF.fromAngle(orientation))

    override fun transformedTo(position: Vector2F, orientation: ComplexF): LineSegment =
        transformedToImpl(position, orientation)

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

    fun pointIterator(): Vector2FIterator = PointIterator(this, index = 0)

    fun copy(pointA: Vector2F = this.pointA, pointB: Vector2F = this.pointB): LineSegment

    operator fun component1(): Vector2F = pointA

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