package com.sztorm.lowallocmath.euclidean2d

import com.sztorm.lowallocmath.*
import kotlin.math.abs
import kotlin.math.absoluteValue
import kotlin.math.sqrt
import kotlin.math.withSign

/** Represents a mutable transformable line segment in a two-dimensional Euclidean space. **/
class MutableLineSegment : LineSegment, MutableTransformable {
    private var _center: Vector2F
    private var _orientation: ComplexF
    private var _length: Float
    private var _pointA: Vector2F
    private var _pointB: Vector2F

    /**
     * Creates a new instance of [MutableLineSegment].
     *
     * @param orientation the value is expected to be [normalized][ComplexF.normalized].
     * @throws IllegalArgumentException when [length] is less than zero.
     */
    constructor(center: Vector2F, orientation: ComplexF, length: Float) {
        throwWhenConstructorArgumentIsIllegal(length)
        val (cX: Float, cY: Float) = center
        val (oR: Float, oI: Float) = orientation
        val halfLength: Float = length * 0.5f
        val originPointAX: Float = halfLength * oR
        val originPointAY: Float = halfLength * oI
        _center = center
        _orientation = orientation
        _length = length
        _pointA = Vector2F(cX + originPointAX, cY + originPointAY)
        _pointB = Vector2F(cX - originPointAX, cY - originPointAY)
    }

    /** Creates a new instance of [MutableLineSegment]. **/
    constructor(pointA: Vector2F, pointB: Vector2F) {
        val (aX: Float, aY: Float) = pointA
        val (bX: Float, bY: Float) = pointB
        val vBAX: Float = aX - bX
        val vBAY: Float = aY - bY
        _center = Vector2F(0.5f * (aX + bX), 0.5f * (aY + bY))
        _orientation = ComplexF(vBAX, vBAY).normalizedOrElse(ComplexF(1f, 0f))
        _length = sqrt(vBAX * vBAX + vBAY * vBAY)
        _pointA = pointA
        _pointB = pointB
    }

    private constructor(
        center: Vector2F,
        orientation: ComplexF,
        length: Float,
        pointA: Vector2F,
        pointB: Vector2F
    ) {
        _center = center
        _orientation = orientation
        _length = length
        _pointA = pointA
        _pointB = pointB
    }

    override val center: Vector2F
        get() = _center

    override val orientation: ComplexF
        get() = _orientation

    override val length: Float
        get() = _length

    override val pointA: Vector2F
        get() = _pointA

    override val pointB: Vector2F
        get() = _pointB

    override val position: Vector2F
        get() = _center

    override fun movedBy(displacement: Vector2F) = MutableLineSegment(
        center = _center + displacement,
        _orientation,
        _length,
        pointA = _pointA + displacement,
        pointB = _pointB + displacement
    )

    override fun movedTo(position: Vector2F): MutableLineSegment {
        val displacement: Vector2F = position - _center

        return MutableLineSegment(
            center = position,
            _orientation,
            _length,
            pointA = _pointA + displacement,
            pointB = _pointB + displacement
        )
    }

    override fun moveBy(displacement: Vector2F) {
        _center += displacement
        _pointA += displacement
        _pointB += displacement
    }

    override fun moveTo(position: Vector2F) {
        val displacement: Vector2F = position - _center
        _center = position
        _pointA += displacement
        _pointB += displacement
    }

    private inline fun rotatedByImpl(rotation: ComplexF): MutableLineSegment {
        val center: Vector2F = _center
        val (cX: Float, cY: Float) = center
        val (oR: Float, oI: Float) = _orientation
        val length: Float = _length
        val halfLength: Float = length * 0.5f
        val (rotR: Float, rotI: Float) = rotation
        val orientationR: Float = (oR * rotR - oI * rotI)
        val orientationI: Float = (oI * rotR + oR * rotI)
        val originPointAX: Float = halfLength * orientationR
        val originPointAY: Float = halfLength * orientationI

        return MutableLineSegment(
            center,
            orientation = ComplexF(orientationR, orientationI),
            length,
            pointA = Vector2F(cX + originPointAX, cY + originPointAY),
            pointB = Vector2F(cX - originPointAX, cY - originPointAY)
        )
    }

    override fun rotatedBy(rotation: AngleF): MutableLineSegment =
        rotatedByImpl(ComplexF.fromAngle(rotation))

    override fun rotatedBy(rotation: ComplexF): MutableLineSegment = rotatedByImpl(rotation)

    private inline fun rotatedToImpl(orientation: ComplexF): MutableLineSegment {
        val center: Vector2F = _center
        val (cX: Float, cY: Float) = center
        val length: Float = _length
        val halfLength: Float = length * 0.5f
        val (oR: Float, oI: Float) = orientation
        val originPointAX: Float = halfLength * oR
        val originPointAY: Float = halfLength * oI

        return MutableLineSegment(
            center,
            orientation,
            length,
            pointA = Vector2F(cX + originPointAX, cY + originPointAY),
            pointB = Vector2F(cX - originPointAX, cY - originPointAY)
        )
    }

    override fun rotatedTo(orientation: AngleF): MutableLineSegment =
        rotatedToImpl(ComplexF.fromAngle(orientation))

    override fun rotatedTo(orientation: ComplexF): MutableLineSegment = rotatedToImpl(orientation)

    private inline fun rotatedAroundPointByImpl(
        point: Vector2F, rotation: ComplexF
    ): MutableLineSegment {
        val (cX: Float, cY: Float) = _center
        val (oR: Float, oI: Float) = _orientation
        val length: Float = _length
        val halfLength: Float = length * 0.5f
        val (pX: Float, pY: Float) = point
        val (rotR: Float, rotI: Float) = rotation
        val cpDiffX: Float = cX - pX
        val cpDiffY: Float = cY - pY
        val centerX: Float = cpDiffX * rotR - cpDiffY * rotI + pX
        val centerY: Float = cpDiffY * rotR + cpDiffX * rotI + pY
        val orientationR: Float = oR * rotR - oI * rotI
        val orientationI: Float = oI * rotR + oR * rotI
        val originPointAX: Float = halfLength * orientationR
        val originPointAY: Float = halfLength * orientationI

        return MutableLineSegment(
            center = Vector2F(centerX, centerY),
            orientation = ComplexF(orientationR, orientationI),
            length,
            pointA = Vector2F(centerX + originPointAX, centerY + originPointAY),
            pointB = Vector2F(centerX - originPointAX, centerY - originPointAY)
        )
    }

    override fun rotatedAroundPointBy(point: Vector2F, rotation: AngleF): MutableLineSegment =
        rotatedAroundPointByImpl(point, ComplexF.fromAngle(rotation))

    override fun rotatedAroundPointBy(point: Vector2F, rotation: ComplexF): MutableLineSegment =
        rotatedAroundPointByImpl(point, rotation)

    private inline fun rotatedAroundPointToImpl(
        point: Vector2F, orientation: ComplexF
    ): MutableLineSegment {
        val center: Vector2F = _center
        val (cX: Float, cY: Float) = center
        val length: Float = _length
        val halfLength: Float = length * 0.5f
        val (pX: Float, pY: Float) = point
        val (oR: Float, oI: Float) = orientation
        val cpDiffX: Float = cX - pX
        val cpDiffY: Float = cY - pY
        val centerToPointDist: Float = sqrt(cpDiffX * cpDiffX + cpDiffY * cpDiffY)

        if (centerToPointDist > 0.00001f) {
            val centerX: Float = oR * centerToPointDist + pX
            val centerY: Float = oI * centerToPointDist + pY
            val centerToPointDistReciprocal: Float = 1f / centerToPointDist
            val pointRotR: Float = cpDiffX * centerToPointDistReciprocal
            val pointRotI: Float = cpDiffY * centerToPointDistReciprocal
            val (startOR: Float, startOI: Float) = _orientation
            val pRotTimesStartOR: Float = pointRotR * startOR + pointRotI * startOI
            val pRotTimesStartOI: Float = pointRotR * startOI - pointRotI * startOR
            val orientationR: Float = pRotTimesStartOR * oR - pRotTimesStartOI * oI
            val orientationI: Float = pRotTimesStartOI * oR + pRotTimesStartOR * oI
            val originPointAX: Float = halfLength * orientationR
            val originPointAY: Float = halfLength * orientationI

            return MutableLineSegment(
                center = Vector2F(centerX, centerY),
                orientation = ComplexF(orientationR, orientationI),
                length,
                pointA = Vector2F(centerX + originPointAX, centerY + originPointAY),
                pointB = Vector2F(centerX - originPointAX, centerY - originPointAY)
            )
        }
        val originPointAX: Float = halfLength * oR
        val originPointAY: Float = halfLength * oI

        return MutableLineSegment(
            center,
            orientation,
            length,
            pointA = Vector2F(cX + originPointAX, cY + originPointAY),
            pointB = Vector2F(cX - originPointAX, cY - originPointAY)
        )
    }

    override fun rotatedAroundPointTo(point: Vector2F, orientation: AngleF): MutableLineSegment =
        rotatedAroundPointToImpl(point, ComplexF.fromAngle(orientation))

    override fun rotatedAroundPointTo(point: Vector2F, orientation: ComplexF): MutableLineSegment =
        rotatedAroundPointToImpl(point, orientation)

    private inline fun rotateByImpl(rotation: ComplexF) {
        val center: Vector2F = _center
        val (cX: Float, cY: Float) = center
        val (oR: Float, oI: Float) = _orientation
        val length: Float = _length
        val halfLength: Float = length * 0.5f
        val (rotR: Float, rotI: Float) = rotation
        val orientationR: Float = (oR * rotR - oI * rotI)
        val orientationI: Float = (oI * rotR + oR * rotI)
        val originPointAX: Float = halfLength * orientationR
        val originPointAY: Float = halfLength * orientationI
        _orientation = ComplexF(orientationR, orientationI)
        _pointA = Vector2F(cX + originPointAX, cY + originPointAY)
        _pointB = Vector2F(cX - originPointAX, cY - originPointAY)
    }

    override fun rotateBy(rotation: AngleF) = rotateByImpl(ComplexF.fromAngle(rotation))

    override fun rotateBy(rotation: ComplexF) = rotateByImpl(rotation)

    private inline fun rotateToImpl(orientation: ComplexF) {
        val center: Vector2F = _center
        val (cX: Float, cY: Float) = center
        val length: Float = _length
        val halfLength: Float = length * 0.5f
        val (oR: Float, oI: Float) = orientation
        val originPointAX: Float = halfLength * oR
        val originPointAY: Float = halfLength * oI
        _orientation = orientation
        _pointA = Vector2F(cX + originPointAX, cY + originPointAY)
        _pointB = Vector2F(cX - originPointAX, cY - originPointAY)
    }

    override fun rotateTo(orientation: AngleF) = rotateToImpl(ComplexF.fromAngle(orientation))

    override fun rotateTo(orientation: ComplexF) = rotateToImpl(orientation)

    private inline fun rotateAroundPointByImpl(point: Vector2F, rotation: ComplexF) {
        val (cX: Float, cY: Float) = _center
        val (oR: Float, oI: Float) = _orientation
        val length: Float = _length
        val halfLength: Float = length * 0.5f
        val (pX: Float, pY: Float) = point
        val (rotR: Float, rotI: Float) = rotation
        val cpDiffX: Float = cX - pX
        val cpDiffY: Float = cY - pY
        val centerX: Float = cpDiffX * rotR - cpDiffY * rotI + pX
        val centerY: Float = cpDiffY * rotR + cpDiffX * rotI + pY
        val orientationR: Float = oR * rotR - oI * rotI
        val orientationI: Float = oI * rotR + oR * rotI
        val originPointAX: Float = halfLength * orientationR
        val originPointAY: Float = halfLength * orientationI
        _center = Vector2F(centerX, centerY)
        _orientation = ComplexF(orientationR, orientationI)
        _pointA = Vector2F(centerX + originPointAX, centerY + originPointAY)
        _pointB = Vector2F(centerX - originPointAX, centerY - originPointAY)
    }

    override fun rotateAroundPointBy(point: Vector2F, rotation: AngleF) =
        rotateAroundPointByImpl(point, ComplexF.fromAngle(rotation))

    override fun rotateAroundPointBy(point: Vector2F, rotation: ComplexF) =
        rotateAroundPointByImpl(point, rotation)

    private inline fun rotateAroundPointToImpl(point: Vector2F, orientation: ComplexF) {
        val center: Vector2F = _center
        val (cX: Float, cY: Float) = center
        val length: Float = _length
        val halfLength: Float = length * 0.5f
        val (pX: Float, pY: Float) = point
        val (oR: Float, oI: Float) = orientation
        val cpDiffX: Float = cX - pX
        val cpDiffY: Float = cY - pY
        val centerToPointDist: Float = sqrt(cpDiffX * cpDiffX + cpDiffY * cpDiffY)

        if (centerToPointDist > 0.00001f) {
            val centerX: Float = oR * centerToPointDist + pX
            val centerY: Float = oI * centerToPointDist + pY
            val centerToPointDistReciprocal: Float = 1f / centerToPointDist
            val pointRotR: Float = cpDiffX * centerToPointDistReciprocal
            val pointRotI: Float = cpDiffY * centerToPointDistReciprocal
            val (startOR: Float, startOI: Float) = _orientation
            val pRotTimesStartOR: Float = pointRotR * startOR + pointRotI * startOI
            val pRotTimesStartOI: Float = pointRotR * startOI - pointRotI * startOR
            val orientationR: Float = pRotTimesStartOR * oR - pRotTimesStartOI * oI
            val orientationI: Float = pRotTimesStartOI * oR + pRotTimesStartOR * oI
            val originPointAX: Float = halfLength * orientationR
            val originPointAY: Float = halfLength * orientationI
            _center = Vector2F(centerX, centerY)
            _orientation = ComplexF(orientationR, orientationI)
            _pointA = Vector2F(centerX + originPointAX, centerY + originPointAY)
            _pointB = Vector2F(centerX - originPointAX, centerY - originPointAY)
        } else {
            val originPointAX: Float = halfLength * oR
            val originPointAY: Float = halfLength * oI
            _orientation = orientation
            _pointA = Vector2F(cX + originPointAX, cY + originPointAY)
            _pointB = Vector2F(cX - originPointAX, cY - originPointAY)
        }
    }

    override fun rotateAroundPointTo(point: Vector2F, orientation: AngleF) =
        rotateAroundPointToImpl(point, ComplexF.fromAngle(orientation))

    override fun rotateAroundPointTo(point: Vector2F, orientation: ComplexF) =
        rotateAroundPointToImpl(point, orientation)

    override fun scaledBy(factor: Float): MutableLineSegment {
        val center: Vector2F = _center
        val (cX: Float, cY: Float) = center
        val (oR: Float, oI: Float) = _orientation
        val length: Float = _length * factor.absoluteValue
        val halfLength: Float = length * 0.5f
        val factorSign: Float = 1f.withSign(factor)
        val orientationR: Float = oR * factorSign
        val orientationI: Float = oI * factorSign
        val originPointAX: Float = halfLength * orientationR
        val originPointAY: Float = halfLength * orientationI

        return MutableLineSegment(
            center,
            orientation = ComplexF(orientationR, orientationI),
            length,
            pointA = Vector2F(cX + originPointAX, cY + originPointAY),
            pointB = Vector2F(cX - originPointAX, cY - originPointAY)
        )
    }

    override fun dilatedBy(point: Vector2F, factor: Float): MutableLineSegment {
        val (cX: Float, cY: Float) = _center
        val (oR: Float, oI: Float) = _orientation
        val length: Float = _length * factor.absoluteValue
        val halfLength: Float = length * 0.5f
        val (pX: Float, pY: Float) = point
        val f: Float = 1f - factor
        val factorSign: Float = 1f.withSign(factor)
        val centerX: Float = cX * factor + pX * f
        val centerY: Float = cY * factor + pY * f
        val orientationR: Float = oR * factorSign
        val orientationI: Float = oI * factorSign
        val originPointAX: Float = halfLength * orientationR
        val originPointAY: Float = halfLength * orientationI

        return MutableLineSegment(
            center = Vector2F(centerX, centerY),
            orientation = ComplexF(orientationR, orientationI),
            length,
            pointA = Vector2F(centerX + originPointAX, centerY + originPointAY),
            pointB = Vector2F(centerX - originPointAX, centerY - originPointAY)
        )
    }

    override fun scaleBy(factor: Float) {
        val center: Vector2F = _center
        val (cX: Float, cY: Float) = center
        val (oR: Float, oI: Float) = _orientation
        val length: Float = _length * factor.absoluteValue
        val halfLength: Float = length * 0.5f
        val factorSign: Float = 1f.withSign(factor)
        val orientationR: Float = oR * factorSign
        val orientationI: Float = oI * factorSign
        val originPointAX: Float = halfLength * orientationR
        val originPointAY: Float = halfLength * orientationI
        _orientation = ComplexF(orientationR, orientationI)
        _length = length
        _pointA = Vector2F(cX + originPointAX, cY + originPointAY)
        _pointB = Vector2F(cX - originPointAX, cY - originPointAY)
    }

    override fun dilateBy(point: Vector2F, factor: Float) {
        val (cX: Float, cY: Float) = _center
        val (oR: Float, oI: Float) = _orientation
        val length: Float = _length * factor.absoluteValue
        val halfLength: Float = length * 0.5f
        val (pX: Float, pY: Float) = point
        val f: Float = 1f - factor
        val factorSign: Float = 1f.withSign(factor)
        val centerX: Float = cX * factor + pX * f
        val centerY: Float = cY * factor + pY * f
        val orientationR: Float = oR * factorSign
        val orientationI: Float = oI * factorSign
        val originPointAX: Float = halfLength * orientationR
        val originPointAY: Float = halfLength * orientationI
        _center = Vector2F(centerX, centerY)
        _orientation = ComplexF(orientationR, orientationI)
        _length = length
        _pointA = Vector2F(centerX + originPointAX, centerY + originPointAY)
        _pointB = Vector2F(centerX - originPointAX, centerY - originPointAY)
    }

    private inline fun transformedByImpl(
        displacement: Vector2F, rotation: ComplexF
    ): MutableLineSegment {
        val (cX: Float, cY: Float) = _center
        val (oR: Float, oI: Float) = _orientation
        val length: Float = _length
        val halfLength: Float = length * 0.5f
        val (dX: Float, dY: Float) = displacement
        val (rotR: Float, rotI: Float) = rotation
        val orientationR: Float = (oR * rotR - oI * rotI)
        val orientationI: Float = (oI * rotR + oR * rotI)
        val originPointAX: Float = halfLength * orientationR
        val originPointAY: Float = halfLength * orientationI
        val centerX: Float = cX + dX
        val centerY: Float = cY + dY

        return MutableLineSegment(
            center = Vector2F(centerX, centerY),
            orientation = ComplexF(orientationR, orientationI),
            length,
            pointA = Vector2F(centerX + originPointAX, centerY + originPointAY),
            pointB = Vector2F(centerX - originPointAX, centerY - originPointAY)
        )
    }

    override fun transformedBy(displacement: Vector2F, rotation: AngleF): MutableLineSegment =
        transformedByImpl(displacement, ComplexF.fromAngle(rotation))

    override fun transformedBy(displacement: Vector2F, rotation: ComplexF): MutableLineSegment =
        transformedByImpl(displacement, rotation)

    private inline fun transformedByImpl(
        displacement: Vector2F, rotation: ComplexF, scaleFactor: Float
    ): MutableLineSegment {
        val (cX: Float, cY: Float) = _center
        val (oR: Float, oI: Float) = _orientation
        val length: Float = _length * scaleFactor.absoluteValue
        val halfLength: Float = length * 0.5f
        val factorSign: Float = 1f.withSign(scaleFactor)
        val (dX: Float, dY: Float) = displacement
        val (rotR: Float, rotI: Float) = rotation
        val orientationR: Float = (oR * rotR - oI * rotI) * factorSign
        val orientationI: Float = (oI * rotR + oR * rotI) * factorSign
        val originPointAX: Float = halfLength * orientationR
        val originPointAY: Float = halfLength * orientationI
        val centerX: Float = cX + dX
        val centerY: Float = cY + dY

        return MutableLineSegment(
            center = Vector2F(centerX, centerY),
            orientation = ComplexF(orientationR, orientationI),
            length,
            pointA = Vector2F(centerX + originPointAX, centerY + originPointAY),
            pointB = Vector2F(centerX - originPointAX, centerY - originPointAY)
        )
    }

    override fun transformedBy(
        displacement: Vector2F, rotation: AngleF, scaleFactor: Float
    ): MutableLineSegment = transformedByImpl(
        displacement, ComplexF.fromAngle(rotation), scaleFactor
    )

    override fun transformedBy(
        displacement: Vector2F, rotation: ComplexF, scaleFactor: Float
    ): MutableLineSegment = transformedByImpl(displacement, rotation, scaleFactor)

    private inline fun transformedToImpl(
        position: Vector2F, orientation: ComplexF
    ): MutableLineSegment {
        val length: Float = _length
        val halfLength: Float = length * 0.5f
        val (pX: Float, pY: Float) = position
        val (oR: Float, oI: Float) = orientation
        val originPointAX: Float = halfLength * oR
        val originPointAY: Float = halfLength * oI

        return MutableLineSegment(
            center = position,
            orientation,
            length,
            pointA = Vector2F(pX + originPointAX, pY + originPointAY),
            pointB = Vector2F(pX - originPointAX, pY - originPointAY)
        )
    }

    override fun transformedTo(position: Vector2F, orientation: AngleF): MutableLineSegment =
        transformedToImpl(position, ComplexF.fromAngle(orientation))

    override fun transformedTo(position: Vector2F, orientation: ComplexF): MutableLineSegment =
        transformedToImpl(position, orientation)

    private inline fun transformByImpl(
        displacement: Vector2F, rotation: ComplexF
    ) {
        val (cX: Float, cY: Float) = _center
        val (oR: Float, oI: Float) = _orientation
        val length: Float = _length
        val halfLength: Float = length * 0.5f
        val (dX: Float, dY: Float) = displacement
        val (rotR: Float, rotI: Float) = rotation
        val orientationR: Float = (oR * rotR - oI * rotI)
        val orientationI: Float = (oI * rotR + oR * rotI)
        val originPointAX: Float = halfLength * orientationR
        val originPointAY: Float = halfLength * orientationI
        val centerX: Float = cX + dX
        val centerY: Float = cY + dY
        _center = Vector2F(centerX, centerY)
        _orientation = ComplexF(orientationR, orientationI)
        _pointA = Vector2F(centerX + originPointAX, centerY + originPointAY)
        _pointB = Vector2F(centerX - originPointAX, centerY - originPointAY)

    }

    override fun transformBy(displacement: Vector2F, rotation: AngleF) =
        transformByImpl(displacement, ComplexF.fromAngle(rotation))

    override fun transformBy(displacement: Vector2F, rotation: ComplexF) =
        transformByImpl(displacement, rotation)

    private inline fun transformByImpl(
        displacement: Vector2F, rotation: ComplexF, scaleFactor: Float
    ) {
        val (cX: Float, cY: Float) = _center
        val (oR: Float, oI: Float) = _orientation
        val length: Float = _length * scaleFactor.absoluteValue
        val halfLength: Float = length * 0.5f
        val factorSign: Float = 1f.withSign(scaleFactor)
        val (dX: Float, dY: Float) = displacement
        val (rotR: Float, rotI: Float) = rotation
        val orientationR: Float = (oR * rotR - oI * rotI) * factorSign
        val orientationI: Float = (oI * rotR + oR * rotI) * factorSign
        val originPointAX: Float = halfLength * orientationR
        val originPointAY: Float = halfLength * orientationI
        val centerX: Float = cX + dX
        val centerY: Float = cY + dY
        _center = Vector2F(centerX, centerY)
        _orientation = ComplexF(orientationR, orientationI)
        _length = length
        _pointA = Vector2F(centerX + originPointAX, centerY + originPointAY)
        _pointB = Vector2F(centerX - originPointAX, centerY - originPointAY)

    }

    override fun transformBy(displacement: Vector2F, rotation: AngleF, scaleFactor: Float) =
        transformByImpl(displacement, ComplexF.fromAngle(rotation), scaleFactor)

    override fun transformBy(displacement: Vector2F, rotation: ComplexF, scaleFactor: Float) =
        transformByImpl(displacement, rotation, scaleFactor)

    private inline fun transformToImpl(
        position: Vector2F, orientation: ComplexF
    ) {
        val length: Float = _length
        val halfLength: Float = length * 0.5f
        val (pX: Float, pY: Float) = position
        val (oR: Float, oI: Float) = orientation
        val originPointAX: Float = halfLength * oR
        val originPointAY: Float = halfLength * oI
        _center = position
        _orientation = orientation
        _pointA = Vector2F(pX + originPointAX, pY + originPointAY)
        _pointB = Vector2F(pX - originPointAX, pY - originPointAY)
    }

    override fun transformTo(position: Vector2F, orientation: AngleF) =
        transformToImpl(position, ComplexF.fromAngle(orientation))

    override fun transformTo(position: Vector2F, orientation: ComplexF) =
        transformToImpl(position, orientation)

    /**
     * Calibrates the properties of this instance. If the [orientation] cannot be normalized, it
     * will take the value of [ONE][ComplexF.ONE].
     *
     * Transformations and operations involving floating point numbers may introduce various
     * inaccuracies that can be countered by this method.
     */
    fun calibrate() {
        val newOrientation: ComplexF = _orientation.normalizedOrElse(ComplexF(1f, 0f))
        val (cX: Float, cY: Float) = _center
        val (oR: Float, oI: Float) = newOrientation
        val halfLength: Float = _length * 0.5f
        val originPointAX: Float = halfLength * oR
        val originPointAY: Float = halfLength * oI
        _orientation = newOrientation
        _pointA = Vector2F(cX + originPointAX, cY + originPointAY)
        _pointB = Vector2F(cX - originPointAX, cY - originPointAY)
    }

    private inline fun setInternal(center: Vector2F, orientation: ComplexF, length: Float) {
        val (cX: Float, cY: Float) = center
        val (oR: Float, oI: Float) = orientation
        val halfLength: Float = length * 0.5f
        val originPointAX: Float = halfLength * oR
        val originPointAY: Float = halfLength * oI
        _center = center
        _orientation = orientation
        _length = length
        _pointA = Vector2F(cX + originPointAX, cY + originPointAY)
        _pointB = Vector2F(cX - originPointAX, cY - originPointAY)
    }

    /**
     * Sets the specified properties of this instance.
     *
     * @param orientation the value is expected to be [normalized][ComplexF.normalized].
     * @throws IllegalArgumentException when [length] is less than zero.
     */
    fun set(
        center: Vector2F = this.center,
        orientation: ComplexF = this.orientation,
        length: Float = this.length
    ) {
        throwWhenConstructorArgumentIsIllegal(length)
        setInternal(center, orientation, length)
    }

    override fun interpolated(to: LineSegment, by: Float) = createInternal(
        center = Vector2F.lerp(_center, to.center, by),
        orientation = ComplexF.slerp(_orientation, to.orientation, by),
        length = Float.lerp(_length, to.length, by)
    )

    /**
     * Sets this line segment with the result of interpolation [from] one line segment [to] another
     * line segment [by] a factor.
     *
     * @param from the line segment from which the interpolation starts.
     * @param to the line segment at which the interpolation ends.
     * @param by the interpolation factor which is expected to be in the range of `[0, 1]`.
     */
    fun interpolate(from: LineSegment, to: LineSegment, by: Float) {
        val center = Vector2F.lerp(from.center, to.center, by)
        val orientation = ComplexF.slerp(from.orientation, to.orientation, by)
        val length = Float.lerp(from.length, to.length, by)
        val (cX: Float, cY: Float) = center
        val (oR: Float, oI: Float) = orientation
        val halfLength: Float = length * 0.5f
        val originPointAX: Float = halfLength * oR
        val originPointAY: Float = halfLength * oI
        _center = center
        _orientation = orientation
        _length = length
        _pointA = Vector2F(cX + originPointAX, cY + originPointAY)
        _pointB = Vector2F(cX - originPointAX, cY - originPointAY)
    }

    override fun closestPointTo(point: Vector2F): Vector2F {
        val pointA: Vector2F = _pointA
        val ab: Vector2F = _pointB - pointA
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

    override fun intersects(ray: Ray): Boolean {
        val (oX: Float, oY: Float) = ray.origin
        val (dirX: Float, dirY: Float) = ray.direction
        val (aX: Float, aY: Float) = _pointA
        val (bX: Float, bY: Float) = _pointB
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

    override operator fun contains(point: Vector2F): Boolean {
        val pointA: Vector2F = _pointA
        val ab: Vector2F = _pointB - pointA
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

    override fun pointIterator(): Vector2FIterator = PointIterator(this, index = 0)

    /**
     * Returns a copy of this instance with specified properties changed.
     *
     * @param orientation the value is expected to be [normalized][ComplexF.normalized].
     * @throws IllegalArgumentException when [length] is less than zero.
     */
    override fun copy(center: Vector2F, orientation: ComplexF, length: Float) =
        MutableLineSegment(center, orientation, length)

    override fun equals(other: Any?): Boolean = other is LineSegment &&
            _center == other.center && _orientation == other.orientation && _length == other.length

    /** Indicates whether the other [MutableLineSegment] is equal to this one. **/
    fun equals(other: MutableLineSegment): Boolean =
        _center == other._center && _orientation == other._orientation && _length == other._length

    override fun hashCode(): Int {
        val centerHash: Int = _center.hashCode()
        val orientationHash: Int = _orientation.hashCode()
        val lengthHash: Int = _length.hashCode()

        return centerHash * 961 + orientationHash * 31 + lengthHash
    }

    override fun toString() =
        StringBuilder("LineSegment(center=").append(_center)
            .append(", orientation=").append(_orientation)
            .append(", length=").append(_length).append(")")
            .toString()

    override operator fun component1(): Vector2F = _center

    override operator fun component2(): ComplexF = _orientation

    override operator fun component3(): Float = _length

    companion object {
        private inline fun throwWhenConstructorArgumentIsIllegal(length: Float) {
            if (length < 0f) {
                throw IllegalArgumentException("length must be greater than or equal to zero.")
            }
        }

        private inline fun createInternal(
            center: Vector2F, orientation: ComplexF, length: Float
        ): MutableLineSegment {
            val (cX: Float, cY: Float) = center
            val (oR: Float, oI: Float) = orientation
            val halfLength: Float = length * 0.5f
            val originPointAX: Float = halfLength * oR
            val originPointAY: Float = halfLength * oI

            return MutableLineSegment(
                center,
                orientation,
                length,
                pointA = Vector2F(cX + originPointAX, cY + originPointAY),
                pointB = Vector2F(cX - originPointAX, cY - originPointAY)
            )
        }
    }

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