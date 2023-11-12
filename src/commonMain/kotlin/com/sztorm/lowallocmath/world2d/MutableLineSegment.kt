package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.AngleF
import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F
import com.sztorm.lowallocmath.Vector2FIterator
import kotlin.math.abs
import kotlin.math.sqrt

class MutableLineSegment(pointA: Vector2F, pointB: Vector2F) : LineSegment, MutableTransformable {
    private var _pointA: Vector2F = pointA
    private var _pointB: Vector2F = pointB

    override val pointA: Vector2F
        get() = _pointA

    override val pointB: Vector2F
        get() = _pointB

    override val center: Vector2F
        get() = (_pointA + _pointB) * 0.5f

    override val length: Float
        get() = _pointA.distanceTo(_pointB)

    override val position: Vector2F
        get() = (_pointA + _pointB) * 0.5f

    override val orientation: ComplexF
        get() = (_pointA - _pointB).normalized.toComplexF()

    override fun movedBy(offset: Vector2F) =
        MutableLineSegment(_pointA + offset, _pointB + offset)

    override fun movedTo(position: Vector2F): MutableLineSegment {
        val center: Vector2F = (_pointA + _pointB) * 0.5f
        val offset: Vector2F = position - center

        return MutableLineSegment(_pointA + offset, _pointB + offset)
    }

    override fun moveBy(offset: Vector2F) {
        _pointA += offset
        _pointB += offset
    }

    override fun moveTo(position: Vector2F) {
        val center: Vector2F = (_pointA + _pointB) * 0.5f
        val offset: Vector2F = position - center
        _pointA += offset
        _pointB += offset
    }

    override fun rotatedBy(rotation: AngleF): MutableLineSegment =
        rotatedBy(ComplexF.fromAngle(rotation))

    override fun rotatedBy(rotation: ComplexF): MutableLineSegment {
        val (paX: Float, paY: Float) = _pointA
        val (pbX: Float, pbY: Float) = _pointB
        val (rotR: Float, rotI: Float) = rotation
        val cX: Float = (paX + pbX) * 0.5f
        val cY: Float = (paY + pbY) * 0.5f
        val pcaX: Float = paX - cX
        val pcaY: Float = paY - cY
        val pcbX: Float = pbX - cX
        val pcbY: Float = pbY - cY

        return MutableLineSegment(
            pointA = Vector2F(
                pcaX * rotR - pcaY * rotI + cX, pcaY * rotR + pcaX * rotI + cY
            ),
            pointB = Vector2F(
                pcbX * rotR - pcbY * rotI + cX, pcbY * rotR + pcbX * rotI + cY
            )
        )
    }

    override fun rotatedTo(orientation: AngleF): MutableLineSegment =
        rotatedTo(ComplexF.fromAngle(orientation))

    override fun rotatedTo(orientation: ComplexF): MutableLineSegment {
        val (paX: Float, paY: Float) = _pointA
        val (pbX: Float, pbY: Float) = _pointB
        val (rotR: Float, rotI: Float) = orientation
        val cX: Float = (paX + pbX) * 0.5f
        val cY: Float = (paY + pbY) * 0.5f
        val pabY: Float = pbY - paY
        val pabX: Float = pbX - paX
        val halfLength = sqrt(pabX * pabX + pabY * pabY) * 0.5f

        return MutableLineSegment(
            pointA = Vector2F(cX + halfLength * rotR, cY + halfLength * rotI),
            pointB = Vector2F(cX - halfLength * rotR, cY - halfLength * rotI)
        )
    }

    override fun rotatedAroundPointBy(point: Vector2F, rotation: AngleF): MutableLineSegment =
        rotatedAroundPointBy(point, ComplexF.fromAngle(rotation))

    override fun rotatedAroundPointBy(point: Vector2F, rotation: ComplexF): MutableLineSegment {
        val (paX: Float, paY: Float) = _pointA
        val (pbX: Float, pbY: Float) = _pointB
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

        return MutableLineSegment(
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

    override fun rotatedAroundPointTo(point: Vector2F, orientation: AngleF): MutableLineSegment =
        rotatedAroundPointTo(point, ComplexF.fromAngle(orientation))

    override fun rotatedAroundPointTo(point: Vector2F, orientation: ComplexF): MutableLineSegment {
        val (paX: Float, paY: Float) = _pointA
        val (pbX: Float, pbY: Float) = _pointB
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

            return MutableLineSegment(
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

            return MutableLineSegment(
                pointA = Vector2F(cX + halfLength * rotR, cY + halfLength * rotI),
                pointB = Vector2F(cX - halfLength * rotR, cY - halfLength * rotI)
            )
        }
    }

    override fun rotateBy(rotation: AngleF) = rotateBy(ComplexF.fromAngle(rotation))

    override fun rotateBy(rotation: ComplexF) {
        val (paX: Float, paY: Float) = _pointA
        val (pbX: Float, pbY: Float) = _pointB
        val (rotR: Float, rotI: Float) = rotation
        val cX: Float = (paX + pbX) * 0.5f
        val cY: Float = (paY + pbY) * 0.5f
        val pcaX: Float = paX - cX
        val pcaY: Float = paY - cY
        val pcbX: Float = pbX - cX
        val pcbY: Float = pbY - cY
        _pointA = Vector2F(pcaX * rotR - pcaY * rotI + cX, pcaY * rotR + pcaX * rotI + cY)
        _pointB = Vector2F(pcbX * rotR - pcbY * rotI + cX, pcbY * rotR + pcbX * rotI + cY)
    }

    override fun rotateTo(orientation: AngleF) = rotateTo(ComplexF.fromAngle(orientation))

    override fun rotateTo(orientation: ComplexF) {
        val (paX: Float, paY: Float) = _pointA
        val (pbX: Float, pbY: Float) = _pointB
        val (rotR: Float, rotI: Float) = orientation
        val cX: Float = (paX + pbX) * 0.5f
        val cY: Float = (paY + pbY) * 0.5f
        val pabX: Float = pbX - paX
        val pabY: Float = pbY - paY
        val halfLength: Float = sqrt(pabX * pabX + pabY * pabY) * 0.5f

        _pointA = Vector2F(cX + halfLength * rotR, cY + halfLength * rotI)
        _pointB = Vector2F(cX - halfLength * rotR, cY - halfLength * rotI)
    }

    override fun rotateAroundPointBy(point: Vector2F, rotation: AngleF) =
        rotateAroundPointBy(point, ComplexF.fromAngle(rotation))

    override fun rotateAroundPointBy(point: Vector2F, rotation: ComplexF) {
        val (paX: Float, paY: Float) = _pointA
        val (pbX: Float, pbY: Float) = _pointB
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
        _pointA = Vector2F(
            pcaX * rotR - pcaY * rotI + targetCenterX,
            pcaY * rotR + pcaX * rotI + targetCenterY
        )
        _pointB = Vector2F(
            pcbX * rotR - pcbY * rotI + targetCenterX,
            pcbY * rotR + pcbX * rotI + targetCenterY
        )
    }

    override fun rotateAroundPointTo(point: Vector2F, orientation: AngleF) =
        rotateAroundPointTo(point, ComplexF.fromAngle(orientation))

    override fun rotateAroundPointTo(point: Vector2F, orientation: ComplexF) {
        val (paX: Float, paY: Float) = _pointA
        val (pbX: Float, pbY: Float) = _pointB
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

            _pointA = Vector2F(
                pcaX * pRotR - pcaY * pRotI + targetCenterX,
                pcaY * pRotR + pcaX * pRotI + targetCenterY
            )
            _pointB = Vector2F(
                pcbX * pRotR - pcbY * pRotI + targetCenterX,
                pcbY * pRotR + pcbX * pRotI + targetCenterY
            )
        } else {
            val pabX: Float = pbX - paX
            val pabY: Float = pbY - paY
            val halfLength: Float = sqrt(pabX * pabX + pabY * pabY) * 0.5f

            _pointA = Vector2F(cX + halfLength * rotR, cY + halfLength * rotI)
            _pointB = Vector2F(cX - halfLength * rotR, cY - halfLength * rotI)
        }
    }

    override fun scaledBy(factor: Float): MutableLineSegment {
        val (paX: Float, paY: Float) = _pointA
        val (pbX: Float, pbY: Float) = _pointB
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

    override fun dilatedBy(point: Vector2F, factor: Float): MutableLineSegment {
        val (paX: Float, paY: Float) = _pointA
        val (pbX: Float, pbY: Float) = _pointB
        val (pX: Float, pY: Float) = point
        val f: Float = 1f - factor
        val addendX: Float = pX * f
        val addendY: Float = pY * f

        return MutableLineSegment(
            pointA = Vector2F(paX * factor + addendX, paY * factor + addendY),
            pointB = Vector2F(pbX * factor + addendX, pbY * factor + addendY)
        )
    }

    override fun scaleBy(factor: Float) {
        val (paX: Float, paY: Float) = _pointA
        val (pbX: Float, pbY: Float) = _pointB
        val cX: Float = (paX + pbX) * 0.5f
        val cY: Float = (paY + pbY) * 0.5f
        val f: Float = 1f - factor
        val addendX: Float = cX * f
        val addendY: Float = cY * f
        _pointA = Vector2F(paX * factor + addendX, paY * factor + addendY)
        _pointB = Vector2F(pbX * factor + addendX, pbY * factor + addendY)
    }

    override fun dilateBy(point: Vector2F, factor: Float) {
        val (paX: Float, paY: Float) = _pointA
        val (pbX: Float, pbY: Float) = _pointB
        val (pX: Float, pY: Float) = point
        val f: Float = 1f - factor
        val addendX: Float = pX * f
        val addendY: Float = pY * f
        _pointA = Vector2F(paX * factor + addendX, paY * factor + addendY)
        _pointB = Vector2F(pbX * factor + addendX, pbY * factor + addendY)
    }

    override fun transformedBy(offset: Vector2F, rotation: AngleF): MutableLineSegment =
        transformedBy(offset, ComplexF.fromAngle(rotation))

    override fun transformedBy(offset: Vector2F, rotation: ComplexF): MutableLineSegment {
        val (paX: Float, paY: Float) = _pointA
        val (pbX: Float, pbY: Float) = _pointB
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

    override fun transformedBy(
        offset: Vector2F, rotation: AngleF, factor: Float
    ): MutableLineSegment = transformedBy(offset, ComplexF.fromAngle(rotation), factor)

    override fun transformedBy(
        offset: Vector2F, rotation: ComplexF, factor: Float
    ): MutableLineSegment {
        val (paX: Float, paY: Float) = _pointA
        val (pbX: Float, pbY: Float) = _pointB
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

    override fun transformedTo(position: Vector2F, orientation: AngleF): MutableLineSegment =
        transformedTo(position, ComplexF.fromAngle(orientation))

    override fun transformedTo(position: Vector2F, orientation: ComplexF): MutableLineSegment {
        val (paX: Float, paY: Float) = _pointA
        val (pbX: Float, pbY: Float) = _pointB
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

    override fun transformBy(offset: Vector2F, rotation: AngleF) =
        transformBy(offset, ComplexF.fromAngle(rotation))

    override fun transformBy(offset: Vector2F, rotation: ComplexF) {
        val (paX: Float, paY: Float) = _pointA
        val (pbX: Float, pbY: Float) = _pointB
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
        _pointA = Vector2F(
            pcaX * rotR - pcaY * rotI + targetPosX,
            pcaY * rotR + pcaX * rotI + targetPosY
        )
        _pointB = Vector2F(
            pcbX * rotR - pcbY * rotI + targetPosX,
            pcbY * rotR + pcbX * rotI + targetPosY
        )
    }

    override fun transformBy(offset: Vector2F, rotation: AngleF, factor: Float) =
        transformBy(offset, ComplexF.fromAngle(rotation), factor)

    override fun transformBy(offset: Vector2F, rotation: ComplexF, factor: Float) {
        val (paX: Float, paY: Float) = _pointA
        val (pbX: Float, pbY: Float) = _pointB
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
        _pointA = Vector2F(
            (pcaX * rotR - pcaY * rotI + targetPosX) * factor + addendX,
            (pcaY * rotR + pcaX * rotI + targetPosY) * factor + addendY
        )
        _pointB = Vector2F(
            (pcbX * rotR - pcbY * rotI + targetPosX) * factor + addendX,
            (pcbY * rotR + pcbX * rotI + targetPosY) * factor + addendY
        )
    }

    override fun transformTo(position: Vector2F, orientation: AngleF) =
        transformTo(position, ComplexF.fromAngle(orientation))

    override fun transformTo(position: Vector2F, orientation: ComplexF) {
        val (paX: Float, paY: Float) = _pointA
        val (pbX: Float, pbY: Float) = _pointB
        val (pX: Float, pY: Float) = position
        val (rotR: Float, rotI: Float) = orientation
        val pabX: Float = pbX - paX
        val pabY: Float = pbY - paY
        val halfLength: Float = sqrt(pabX * pabX + pabY * pabY) * 0.5f

        _pointA = Vector2F(pX + halfLength * rotR, pY + halfLength * rotI)
        _pointB = Vector2F(pX - halfLength * rotR, pY - halfLength * rotI)
    }

    override fun closestPointTo(point: Vector2F): Vector2F {
        val ab: Vector2F = _pointB - _pointA
        val epsilon = 0.00001f

        if ((abs(ab.x) <= epsilon) and (abs(ab.y) <= epsilon)) {
            return _pointA
        }
        val ap: Vector2F = point - _pointA
        val t: Float = (ab dot ap) / (ab dot ab)
        val tClamped: Float = when {
            t < 0f -> 0f
            t > 1f -> 1f
            else -> t
        }
        return _pointA + ab * tClamped
    }

    override operator fun contains(point: Vector2F): Boolean {
        val ab: Vector2F = _pointB - _pointA
        val epsilon = 0.00001f

        if ((abs(ab.x) <= epsilon) and (abs(ab.y) <= epsilon)) {
            return _pointA.isApproximately(point)
        }
        val ap: Vector2F = point - _pointA
        val t: Float = (ab dot ap) / (ab dot ab)

        return if ((t < 0f) or (t > 1f)) false
        else {
            val closestPoint: Vector2F = _pointA + ab * t

            return closestPoint.isApproximately(point)
        }
    }

    override fun pointIterator(): Vector2FIterator = PointIterator(this, index = 0)

    override fun copy(pointA: Vector2F, pointB: Vector2F) = MutableLineSegment(pointA, pointB)

    override fun equals(other: Any?): Boolean = other is LineSegment &&
            _pointA == other.pointA &&
            _pointB == other.pointB

    fun equals(other: MutableLineSegment): Boolean =
        _pointA == other._pointA && _pointB == other._pointB

    override fun hashCode(): Int {
        val pointAHash: Int = _pointA.hashCode()
        val pointBHash: Int = _pointB.hashCode()

        return pointAHash * 31 + pointBHash
    }

    override fun toString() =
        StringBuilder("LineSegment(pointA=").append(_pointA)
            .append(", pointB=").append(_pointB).append(")")
            .toString()

    override operator fun component1(): Vector2F = _pointA

    override operator fun component2(): Vector2F = _pointB

    private class PointIterator(
        private val lineSegment: MutableLineSegment,
        private var index: Int
    ) : Vector2FIterator() {
        override fun hasNext(): Boolean = index < 2

        override fun nextVector2F(): Vector2F = when (index++) {
            0 -> lineSegment._pointA
            1 -> lineSegment._pointB
            else -> throw NoSuchElementException("${index - 1}")
        }
    }
}