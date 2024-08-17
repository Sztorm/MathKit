package com.sztorm.lowallocmath.euclidean2d

import com.sztorm.lowallocmath.*
import kotlin.math.*

/** Represents a mutable transformable rectangle in a two-dimensional Euclidean space. **/
class MutableRectangle : Rectangle, MutableTransformable {
    private var _center: Vector2F
    private var _orientation: ComplexF
    private var _width: Float
    private var _height: Float
    private var _pointA: Vector2F
    private var _pointB: Vector2F
    private var _pointC: Vector2F
    private var _pointD: Vector2F

    /**
     * Creates a new instance of [MutableRectangle].
     *
     * @param orientation the value is expected to be [normalized][ComplexF.normalized].
     * @throws IllegalArgumentException when [width] is less than zero.
     * @throws IllegalArgumentException when [height] is less than zero.
     */
    constructor(center: Vector2F, orientation: ComplexF, width: Float, height: Float) {
        throwWhenConstructorArgumentsAreIllegal(width, height)
        val (cX: Float, cY: Float) = center
        val (oR: Float, oI: Float) = orientation
        val halfWidth: Float = width * 0.5f
        val halfHeight: Float = height * 0.5f
        val addendX1: Float = oR * halfWidth
        val addendX2: Float = oI * halfHeight
        val addendY1: Float = oR * halfHeight
        val addendY2: Float = oI * halfWidth
        val addendSumX1X2: Float = addendX1 + addendX2
        val addendDiffX1X2: Float = addendX1 - addendX2
        val addendSumY1Y2: Float = addendY1 + addendY2
        val addendDiffY1Y2: Float = addendY1 - addendY2
        _center = center
        _orientation = orientation
        _width = width
        _height = height
        _pointA = Vector2F(cX + addendDiffX1X2, cY + addendSumY1Y2)
        _pointB = Vector2F(cX - addendSumX1X2, cY + addendDiffY1Y2)
        _pointC = Vector2F(cX - addendDiffX1X2, cY - addendSumY1Y2)
        _pointD = Vector2F(cX + addendSumX1X2, cY - addendDiffY1Y2)
    }

    private constructor(
        center: Vector2F,
        orientation: ComplexF,
        width: Float,
        height: Float,
        pointA: Vector2F,
        pointB: Vector2F,
        pointC: Vector2F,
        pointD: Vector2F
    ) {
        _center = center
        _orientation = orientation
        _width = width
        _height = height
        _pointA = pointA
        _pointB = pointB
        _pointC = pointC
        _pointD = pointD
    }

    override val center: Vector2F
        get() = _center

    override val orientation: ComplexF
        get() = _orientation

    override val width: Float
        get() = _width

    override val height: Float
        get() = _height

    override val pointA: Vector2F
        get() = _pointA

    override val pointB: Vector2F
        get() = _pointB

    override val pointC: Vector2F
        get() = _pointC

    override val pointD: Vector2F
        get() = _pointD

    override val area: Float
        get() = _width * _height

    override val perimeter: Float
        get() = 2f * (_width + _height)

    override val position: Vector2F
        get() = _center

    override fun movedBy(displacement: Vector2F) = MutableRectangle(
        _center + displacement,
        _orientation,
        _width,
        _height,
        _pointA + displacement,
        _pointB + displacement,
        _pointC + displacement,
        _pointD + displacement,
    )

    override fun movedTo(position: Vector2F): MutableRectangle {
        val displacement: Vector2F = position - _center

        return MutableRectangle(
            position,
            _orientation,
            _width,
            _height,
            _pointA + displacement,
            _pointB + displacement,
            _pointC + displacement,
            _pointD + displacement,
        )
    }

    override fun moveBy(displacement: Vector2F) {
        _center += displacement
        _pointA += displacement
        _pointB += displacement
        _pointC += displacement
        _pointD += displacement
    }

    override fun moveTo(position: Vector2F) {
        val displacement: Vector2F = position - _center
        _center = position
        _pointA += displacement
        _pointB += displacement
        _pointC += displacement
        _pointD += displacement
    }

    override fun rotatedBy(rotation: AngleF) = createInternal(
        _center, _orientation * ComplexF.fromAngle(rotation), _width, _height
    )

    override fun rotatedBy(rotation: ComplexF) =
        createInternal(_center, _orientation * rotation, _width, _height)

    override fun rotatedTo(orientation: AngleF) =
        createInternal(_center, ComplexF.fromAngle(orientation), _width, _height)

    override fun rotatedTo(orientation: ComplexF) =
        createInternal(_center, orientation, _width, _height)

    override fun rotatedAroundPointBy(point: Vector2F, rotation: AngleF): MutableRectangle =
        rotatedAroundPointBy(point, ComplexF.fromAngle(rotation))

    override fun rotatedAroundPointBy(point: Vector2F, rotation: ComplexF): MutableRectangle {
        val (pX: Float, pY: Float) = point
        val (rotR: Float, rotI: Float) = rotation
        val (cX: Float, cY: Float) = _center
        val (startRotR: Float, startRotI: Float) = _orientation
        val halfWidth: Float = _width * 0.5f
        val halfHeight: Float = _height * 0.5f
        val cpDiffX: Float = cX - pX
        val cpDiffY: Float = cY - pY
        val targetCenterX: Float = cpDiffX * rotR - cpDiffY * rotI + pX
        val targetCenterY: Float = cpDiffY * rotR + cpDiffX * rotI + pY
        val targetRotR: Float = startRotR * rotR - startRotI * rotI
        val targetRotI: Float = startRotI * rotR + startRotR * rotI
        val addendX1: Float = targetRotR * halfWidth
        val addendX1A: Float = targetCenterX + addendX1
        val addendX1B: Float = targetCenterX - addendX1
        val addendX2: Float = targetRotI * halfHeight
        val addendY1: Float = targetRotR * halfHeight
        val addendY1A: Float = targetCenterY + addendY1
        val addendY1B: Float = targetCenterY - addendY1
        val addendY2: Float = targetRotI * halfWidth

        return MutableRectangle(
            center = Vector2F(targetCenterX, targetCenterY),
            orientation = ComplexF(targetRotR, targetRotI),
            _width,
            _height,
            pointA = Vector2F(addendX1A - addendX2, addendY1A + addendY2),
            pointB = Vector2F(addendX1B - addendX2, addendY1A - addendY2),
            pointC = Vector2F(addendX1B + addendX2, addendY1B - addendY2),
            pointD = Vector2F(addendX1A + addendX2, addendY1B + addendY2)
        )
    }

    override fun rotatedAroundPointTo(point: Vector2F, orientation: AngleF): MutableRectangle =
        rotatedAroundPointTo(point, ComplexF.fromAngle(orientation))

    override fun rotatedAroundPointTo(point: Vector2F, orientation: ComplexF): MutableRectangle {
        val (pX: Float, pY: Float) = point
        val (rotR: Float, rotI: Float) = orientation
        val (cX: Float, cY: Float) = _center
        val halfWidth: Float = _width * 0.5f
        val halfHeight: Float = _height * 0.5f
        val cpDiffX: Float = cX - pX
        val cpDiffY: Float = cY - pY
        val centerToPointDist: Float = sqrt(cpDiffX * cpDiffX + cpDiffY * cpDiffY)

        if (centerToPointDist > 0.00001f) {
            val pointRotR: Float = cpDiffX / centerToPointDist
            val pointRotI: Float = cpDiffY / centerToPointDist
            val targetRot = ComplexF(pointRotR, -pointRotI) * _orientation * orientation
            val (targetRotR: Float, targetRotI: Float) = targetRot
            val targetCenterX: Float = rotR * centerToPointDist + pX
            val targetCenterY: Float = rotI * centerToPointDist + pY
            val addendX1: Float = targetRotR * halfWidth
            val addendX1A: Float = targetCenterX + addendX1
            val addendX1B: Float = targetCenterX - addendX1
            val addendX2: Float = targetRotI * halfHeight
            val addendY1: Float = targetRotR * halfHeight
            val addendY1A: Float = targetCenterY + addendY1
            val addendY1B: Float = targetCenterY - addendY1
            val addendY2: Float = targetRotI * halfWidth

            return MutableRectangle(
                center = Vector2F(targetCenterX, targetCenterY),
                orientation = targetRot,
                _width,
                _height,
                pointA = Vector2F(addendX1A - addendX2, addendY1A + addendY2),
                pointB = Vector2F(addendX1B - addendX2, addendY1A - addendY2),
                pointC = Vector2F(addendX1B + addendX2, addendY1B - addendY2),
                pointD = Vector2F(addendX1A + addendX2, addendY1B + addendY2),
            )
        } else {
            val addendX1: Float = rotR * halfWidth
            val addendX1A: Float = cX + addendX1
            val addendX1B: Float = cX - addendX1
            val addendX2: Float = rotI * halfHeight
            val addendY1: Float = rotR * halfHeight
            val addendY1A: Float = cY + addendY1
            val addendY1B: Float = cY - addendY1
            val addendY2: Float = rotI * halfWidth

            return MutableRectangle(
                _center,
                orientation,
                _width,
                _height,
                pointA = Vector2F(addendX1A - addendX2, addendY1A + addendY2),
                pointB = Vector2F(addendX1B - addendX2, addendY1A - addendY2),
                pointC = Vector2F(addendX1B + addendX2, addendY1B - addendY2),
                pointD = Vector2F(addendX1A + addendX2, addendY1B + addendY2),
            )
        }
    }

    override fun rotateBy(rotation: AngleF) =
        rotateTo(_orientation * ComplexF.fromAngle(rotation))

    override fun rotateBy(rotation: ComplexF) = rotateTo(_orientation * rotation)

    override fun rotateTo(orientation: AngleF) = rotateTo(ComplexF.fromAngle(orientation))

    override fun rotateTo(orientation: ComplexF) {
        val (cX: Float, cY: Float) = _center
        val (rotR: Float, rotI: Float) = orientation
        val halfWidth: Float = _width * 0.5f
        val halfHeight: Float = _height * 0.5f
        val addendX1: Float = rotR * halfWidth
        val addendX1A: Float = cX + addendX1
        val addendX1B: Float = cX - addendX1
        val addendX2: Float = rotI * halfHeight
        val addendY1: Float = rotR * halfHeight
        val addendY1A: Float = cY + addendY1
        val addendY1B: Float = cY - addendY1
        val addendY2: Float = rotI * halfWidth
        _orientation = orientation
        _pointA = Vector2F(addendX1A - addendX2, addendY1A + addendY2)
        _pointB = Vector2F(addendX1B - addendX2, addendY1A - addendY2)
        _pointC = Vector2F(addendX1B + addendX2, addendY1B - addendY2)
        _pointD = Vector2F(addendX1A + addendX2, addendY1B + addendY2)
    }

    override fun rotateAroundPointBy(point: Vector2F, rotation: AngleF) =
        rotateAroundPointBy(point, ComplexF.fromAngle(rotation))

    override fun rotateAroundPointBy(point: Vector2F, rotation: ComplexF) {
        val (pX: Float, pY: Float) = point
        val (rotR: Float, rotI: Float) = rotation
        val (cX: Float, cY: Float) = _center
        val (startRotR: Float, startRotI: Float) = _orientation
        val halfWidth: Float = _width * 0.5f
        val halfHeight: Float = _height * 0.5f
        val cpDiffX: Float = cX - pX
        val cpDiffY: Float = cY - pY
        val targetCenterX: Float = cpDiffX * rotR - cpDiffY * rotI + pX
        val targetCenterY: Float = cpDiffY * rotR + cpDiffX * rotI + pY
        val targetRotR: Float = startRotR * rotR - startRotI * rotI
        val targetRotI: Float = startRotI * rotR + startRotR * rotI
        val addendX1: Float = targetRotR * halfWidth
        val addendX1A: Float = targetCenterX + addendX1
        val addendX1B: Float = targetCenterX - addendX1
        val addendX2: Float = targetRotI * halfHeight
        val addendY1: Float = targetRotR * halfHeight
        val addendY1A: Float = targetCenterY + addendY1
        val addendY1B: Float = targetCenterY - addendY1
        val addendY2: Float = targetRotI * halfWidth
        _center = Vector2F(targetCenterX, targetCenterY)
        _orientation = ComplexF(targetRotR, targetRotI)
        _pointA = Vector2F(addendX1A - addendX2, addendY1A + addendY2)
        _pointB = Vector2F(addendX1B - addendX2, addendY1A - addendY2)
        _pointC = Vector2F(addendX1B + addendX2, addendY1B - addendY2)
        _pointD = Vector2F(addendX1A + addendX2, addendY1B + addendY2)
    }

    override fun rotateAroundPointTo(point: Vector2F, orientation: AngleF) =
        rotateAroundPointTo(point, ComplexF.fromAngle(orientation))

    override fun rotateAroundPointTo(point: Vector2F, orientation: ComplexF) {
        val (pX: Float, pY: Float) = point
        val (rotR: Float, rotI: Float) = orientation
        val (cX: Float, cY: Float) = _center
        val halfWidth: Float = _width * 0.5f
        val halfHeight: Float = _height * 0.5f
        val cpDiffX: Float = cX - pX
        val cpDiffY: Float = cY - pY
        val centerToPointDist: Float = sqrt(cpDiffX * cpDiffX + cpDiffY * cpDiffY)

        if (centerToPointDist > 0.00001f) {
            val pointRotR: Float = cpDiffX / centerToPointDist
            val pointRotI: Float = cpDiffY / centerToPointDist
            val targetRot = ComplexF(pointRotR, -pointRotI) * _orientation * orientation
            val (targetRotR: Float, targetRotI: Float) = targetRot
            val targetCenterX: Float = rotR * centerToPointDist + pX
            val targetCenterY: Float = rotI * centerToPointDist + pY
            val addendX1: Float = targetRotR * halfWidth
            val addendX1A: Float = targetCenterX + addendX1
            val addendX1B: Float = targetCenterX - addendX1
            val addendX2: Float = targetRotI * halfHeight
            val addendY1: Float = targetRotR * halfHeight
            val addendY1A: Float = targetCenterY + addendY1
            val addendY1B: Float = targetCenterY - addendY1
            val addendY2: Float = targetRotI * halfWidth
            _center = Vector2F(targetCenterX, targetCenterY)
            _orientation = targetRot
            _pointA = Vector2F(addendX1A - addendX2, addendY1A + addendY2)
            _pointB = Vector2F(addendX1B - addendX2, addendY1A - addendY2)
            _pointC = Vector2F(addendX1B + addendX2, addendY1B - addendY2)
            _pointD = Vector2F(addendX1A + addendX2, addendY1B + addendY2)
        } else {
            val addendX1: Float = rotR * halfWidth
            val addendX1A: Float = cX + addendX1
            val addendX1B: Float = cX - addendX1
            val addendX2: Float = rotI * halfHeight
            val addendY1: Float = rotR * halfHeight
            val addendY1A: Float = cY + addendY1
            val addendY1B: Float = cY - addendY1
            val addendY2: Float = rotI * halfWidth
            _orientation = orientation
            _pointA = Vector2F(addendX1A - addendX2, addendY1A + addendY2)
            _pointB = Vector2F(addendX1B - addendX2, addendY1A - addendY2)
            _pointC = Vector2F(addendX1B + addendX2, addendY1B - addendY2)
            _pointD = Vector2F(addendX1A + addendX2, addendY1B + addendY2)
        }
    }

    override fun scaledBy(factor: Float): MutableRectangle {
        val absFactor: Float = factor.absoluteValue

        return createInternal(
            _center,
            _orientation * 1f.withSign(factor),
            _width * absFactor,
            _height * absFactor
        )
    }

    override fun dilatedBy(point: Vector2F, factor: Float): MutableRectangle {
        val f: Float = 1f - factor
        val cX: Float = _center.x * factor + point.x * f
        val cY: Float = _center.y * factor + point.y * f
        val (rotR: Float, rotI: Float) = _orientation * 1f.withSign(factor)
        val absFactor: Float = factor.absoluteValue
        val width: Float = _width * absFactor
        val height: Float = _height * absFactor
        val halfWidth: Float = width * 0.5f
        val halfHeight: Float = height * 0.5f
        val addendX1: Float = rotR * halfWidth
        val addendX1A: Float = cX + addendX1
        val addendX1B: Float = cX - addendX1
        val addendX2: Float = rotI * halfHeight
        val addendY1: Float = rotR * halfHeight
        val addendY1A: Float = cY + addendY1
        val addendY1B: Float = cY - addendY1
        val addendY2: Float = rotI * halfWidth

        return MutableRectangle(
            center = Vector2F(cX, cY),
            orientation = ComplexF(rotR, rotI),
            width,
            height,
            pointA = Vector2F(addendX1A - addendX2, addendY1A + addendY2),
            pointB = Vector2F(addendX1B - addendX2, addendY1A - addendY2),
            pointC = Vector2F(addendX1B + addendX2, addendY1B - addendY2),
            pointD = Vector2F(addendX1A + addendX2, addendY1B + addendY2)
        )
    }

    override fun scaleBy(factor: Float) {
        val (cX: Float, cY: Float) = _center
        val (rotR: Float, rotI: Float) = _orientation * 1f.withSign(factor)
        val absFactor: Float = factor.absoluteValue
        val width: Float = _width * absFactor
        val height: Float = _height * absFactor
        val halfWidth: Float = width * 0.5f
        val halfHeight: Float = height * 0.5f
        val addendX1: Float = rotR * halfWidth
        val addendX1A: Float = cX + addendX1
        val addendX1B: Float = cX - addendX1
        val addendX2: Float = rotI * halfHeight
        val addendY1: Float = rotR * halfHeight
        val addendY1A: Float = cY + addendY1
        val addendY1B: Float = cY - addendY1
        val addendY2: Float = rotI * halfWidth
        _orientation = ComplexF(rotR, rotI)
        _width = width
        _height = height
        _pointA = Vector2F(addendX1A - addendX2, addendY1A + addendY2)
        _pointB = Vector2F(addendX1B - addendX2, addendY1A - addendY2)
        _pointC = Vector2F(addendX1B + addendX2, addendY1B - addendY2)
        _pointD = Vector2F(addendX1A + addendX2, addendY1B + addendY2)
    }

    override fun dilateBy(point: Vector2F, factor: Float) {
        val f: Float = 1f - factor
        val cX: Float = _center.x * factor + point.x * f
        val cY: Float = _center.y * factor + point.y * f
        val (rotR: Float, rotI: Float) = _orientation * 1f.withSign(factor)
        val absFactor: Float = factor.absoluteValue
        val width: Float = _width * absFactor
        val height: Float = _height * absFactor
        val halfWidth: Float = width * 0.5f
        val halfHeight: Float = height * 0.5f
        val addendX1: Float = rotR * halfWidth
        val addendX1A: Float = cX + addendX1
        val addendX1B: Float = cX - addendX1
        val addendX2: Float = rotI * halfHeight
        val addendY1: Float = rotR * halfHeight
        val addendY1A: Float = cY + addendY1
        val addendY1B: Float = cY - addendY1
        val addendY2: Float = rotI * halfWidth
        _center = Vector2F(cX, cY)
        _orientation = ComplexF(rotR, rotI)
        _width = width
        _height = height
        _pointA = Vector2F(addendX1A - addendX2, addendY1A + addendY2)
        _pointB = Vector2F(addendX1B - addendX2, addendY1A - addendY2)
        _pointC = Vector2F(addendX1B + addendX2, addendY1B - addendY2)
        _pointD = Vector2F(addendX1A + addendX2, addendY1B + addendY2)
    }

    override fun transformedBy(displacement: Vector2F, rotation: AngleF) = createInternal(
        _center + displacement,
        _orientation * ComplexF.fromAngle(rotation),
        _width,
        _height
    )

    override fun transformedBy(displacement: Vector2F, rotation: ComplexF) = createInternal(
        _center + displacement, _orientation * rotation, _width, _height
    )

    override fun transformedBy(
        displacement: Vector2F, rotation: AngleF, scaleFactor: Float
    ): MutableRectangle {
        val absScaleFactor: Float = scaleFactor.absoluteValue

        return createInternal(
            _center + displacement,
            _orientation * ComplexF.fromAngle(rotation) * 1f.withSign(scaleFactor),
            _width * absScaleFactor,
            _height * absScaleFactor
        )
    }

    override fun transformedBy(
        displacement: Vector2F, rotation: ComplexF, scaleFactor: Float
    ): MutableRectangle {
        val absScaleFactor: Float = scaleFactor.absoluteValue

        return createInternal(
            _center + displacement,
            _orientation * rotation * 1f.withSign(scaleFactor),
            _width * absScaleFactor,
            _height * absScaleFactor
        )
    }

    override fun transformedTo(position: Vector2F, orientation: AngleF) =
        createInternal(position, ComplexF.fromAngle(orientation), _width, _height)

    override fun transformedTo(position: Vector2F, orientation: ComplexF) =
        createInternal(position, orientation, _width, _height)

    override fun transformBy(displacement: Vector2F, rotation: AngleF) =
        transformTo(_center + displacement, _orientation * ComplexF.fromAngle(rotation))

    override fun transformBy(displacement: Vector2F, rotation: ComplexF) =
        transformTo(_center + displacement, _orientation * rotation)

    override fun transformBy(displacement: Vector2F, rotation: AngleF, scaleFactor: Float) =
        transformBy(displacement, ComplexF.fromAngle(rotation), scaleFactor)

    override fun transformBy(displacement: Vector2F, rotation: ComplexF, scaleFactor: Float) {
        val cX: Float = _center.x + displacement.x
        val cY: Float = _center.y + displacement.y
        val r0 = _orientation.real
        val i0 = _orientation.imaginary
        val r1 = rotation.real
        val i1 = rotation.imaginary
        val scaleFactorSign: Float = 1f.withSign(scaleFactor)
        val absScaleFactor: Float = scaleFactor.absoluteValue
        val rotR: Float = (r0 * r1 - i0 * i1) * scaleFactorSign
        val rotI: Float = (i0 * r1 + r0 * i1) * scaleFactorSign
        val width = _width * absScaleFactor
        val height = _height * absScaleFactor
        val halfWidth: Float = width * 0.5f
        val halfHeight: Float = height * 0.5f
        val addendX1: Float = rotR * halfWidth
        val addendX1A: Float = cX + addendX1
        val addendX1B: Float = cX - addendX1
        val addendX2: Float = rotI * halfHeight
        val addendY1: Float = rotR * halfHeight
        val addendY1A: Float = cY + addendY1
        val addendY1B: Float = cY - addendY1
        val addendY2: Float = rotI * halfWidth
        _center = Vector2F(cX, cY)
        _orientation = ComplexF(rotR, rotI)
        _width = width
        _height = height
        _pointA = Vector2F(addendX1A - addendX2, addendY1A + addendY2)
        _pointB = Vector2F(addendX1B - addendX2, addendY1A - addendY2)
        _pointC = Vector2F(addendX1B + addendX2, addendY1B - addendY2)
        _pointD = Vector2F(addendX1A + addendX2, addendY1B + addendY2)
    }

    override fun transformTo(position: Vector2F, orientation: AngleF) =
        transformTo(position, ComplexF.fromAngle(orientation))

    override fun transformTo(position: Vector2F, orientation: ComplexF) {
        val (cX: Float, cY: Float) = position
        val (rotR: Float, rotI: Float) = orientation
        val halfWidth: Float = _width * 0.5f
        val halfHeight: Float = _height * 0.5f
        val addendX1: Float = rotR * halfWidth
        val addendX1A: Float = cX + addendX1
        val addendX1B: Float = cX - addendX1
        val addendX2: Float = rotI * halfHeight
        val addendY1: Float = rotR * halfHeight
        val addendY1A: Float = cY + addendY1
        val addendY1B: Float = cY - addendY1
        val addendY2: Float = rotI * halfWidth
        _center = position
        _orientation = orientation
        _pointA = Vector2F(addendX1A - addendX2, addendY1A + addendY2)
        _pointB = Vector2F(addendX1B - addendX2, addendY1A - addendY2)
        _pointC = Vector2F(addendX1B + addendX2, addendY1B - addendY2)
        _pointD = Vector2F(addendX1A + addendX2, addendY1B + addendY2)
    }

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
        val halfWidth: Float = _width * 0.5f
        val halfHeight: Float = _height * 0.5f
        val addendX1: Float = oR * halfWidth
        val addendX2: Float = oI * halfHeight
        val addendY1: Float = oR * halfHeight
        val addendY2: Float = oI * halfWidth
        val addendSumX1X2: Float = addendX1 + addendX2
        val addendDiffX1X2: Float = addendX1 - addendX2
        val addendSumY1Y2: Float = addendY1 + addendY2
        val addendDiffY1Y2: Float = addendY1 - addendY2
        _orientation = newOrientation
        _pointA = Vector2F(cX + addendDiffX1X2, cY + addendSumY1Y2)
        _pointB = Vector2F(cX - addendSumX1X2, cY + addendDiffY1Y2)
        _pointC = Vector2F(cX - addendDiffX1X2, cY - addendSumY1Y2)
        _pointD = Vector2F(cX + addendSumX1X2, cY - addendDiffY1Y2)
    }

    private inline fun setInternal(
        center: Vector2F, orientation: ComplexF, width: Float, height: Float
    ) {
        val (cX: Float, cY: Float) = center
        val (oR: Float, oI: Float) = orientation
        val halfWidth: Float = width * 0.5f
        val halfHeight: Float = height * 0.5f
        val addendX1: Float = oR * halfWidth
        val addendX2: Float = oI * halfHeight
        val addendY1: Float = oR * halfHeight
        val addendY2: Float = oI * halfWidth
        val addendSumX1X2: Float = addendX1 + addendX2
        val addendDiffX1X2: Float = addendX1 - addendX2
        val addendSumY1Y2: Float = addendY1 + addendY2
        val addendDiffY1Y2: Float = addendY1 - addendY2
        _center = center
        _orientation = orientation
        _width = width
        _height = height
        _pointA = Vector2F(cX + addendDiffX1X2, cY + addendSumY1Y2)
        _pointB = Vector2F(cX - addendSumX1X2, cY + addendDiffY1Y2)
        _pointC = Vector2F(cX - addendDiffX1X2, cY - addendSumY1Y2)
        _pointD = Vector2F(cX + addendSumX1X2, cY - addendDiffY1Y2)
    }

    /**
     * Sets the specified properties of this instance.
     *
     * @param orientation the value is expected to be [normalized][ComplexF.normalized].
     * @throws IllegalArgumentException when [width] is less than zero.
     * @throws IllegalArgumentException when [height] is less than zero.
     */
    fun set(
        center: Vector2F = this.center,
        orientation: ComplexF = this.orientation,
        width: Float = this.width,
        height: Float = this.height
    ) {
        throwWhenConstructorArgumentsAreIllegal(width, height)
        setInternal(center, orientation, width, height)
    }

    override fun interpolated(to: Rectangle, by: Float) = createInternal(
        center = Vector2F.lerp(_center, to.center, by),
        orientation = ComplexF.slerp(_orientation, to.orientation, by),
        width = Float.lerp(_width, to.width, by),
        height = Float.lerp(_height, to.height, by)
    )

    /**
     * Sets this rectangle with the result of interpolation [from] one rectangle [to] another
     * rectangle [by] a factor.
     *
     * @param from the rectangle from which the interpolation starts.
     * @param to the rectangle at which the interpolation ends.
     * @param by the interpolation factor which is expected to be in the range of `[0, 1]`.
     */
    fun interpolate(from: Rectangle, to: Rectangle, by: Float) = setInternal(
        center = Vector2F.lerp(from.center, to.center, by),
        orientation = ComplexF.slerp(from.orientation, to.orientation, by),
        width = Float.lerp(from.width, to.width, by),
        height = Float.lerp(from.height, to.height, by)
    )

    override fun closestPointTo(point: Vector2F): Vector2F {
        val (cX: Float, cY: Float) = _center
        val (rotR: Float, rotI: Float) = _orientation
        val halfWidth: Float = _width * 0.5f
        val halfHeight: Float = _height * 0.5f
        val (pX: Float, pY: Float) = point
        val pcDiffX: Float = pX - cX
        val pcDiffY: Float = pY - cY
        val p1X: Float = rotR * pcDiffX + rotI * pcDiffY
        val p1Y: Float = rotR * pcDiffY - rotI * pcDiffX
        val p2X: Float = if (p1X.absoluteValue > halfWidth) halfWidth.withSign(p1X) else p1X
        val p2Y: Float = if (p1Y.absoluteValue > halfHeight) halfHeight.withSign(p1Y) else p1Y

        return Vector2F(rotR * p2X - rotI * p2Y + cX, rotI * p2X + rotR * p2Y + cY)
    }

    override fun intersects(ray: Ray): Boolean {
        val (rectCX: Float, rectCY: Float) = _center
        val (rectOR: Float, rectOI: Float) = _orientation
        val halfWidth: Float = _width * 0.5f
        val halfHeight: Float = _height * 0.5f
        val aabbMinX: Float = rectCX - halfWidth
        val aabbMinY: Float = rectCY - halfHeight
        val aabbMaxX: Float = rectCX + halfWidth
        val aabbMaxY: Float = rectCY + halfHeight

        val (rayCX: Float, rayCY: Float) = ray.origin
        val (rayDirX: Float, rayDirY: Float) = ray.direction
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

    override operator fun contains(point: Vector2F): Boolean {
        val (cX: Float, cY: Float) = _center
        val (rotR: Float, rotI: Float) = _orientation
        val halfWidth: Float = _width * 0.5f
        val halfHeight: Float = _height * 0.5f
        val (pX: Float, pY: Float) = point
        val pcDiffX: Float = pX - cX
        val pcDiffY: Float = pY - cY
        val p1X: Float = rotR * pcDiffX + rotI * pcDiffY
        val p1Y: Float = rotR * pcDiffY - rotI * pcDiffX

        return (p1X.absoluteValue <= halfWidth) and (p1Y.absoluteValue <= halfHeight)
    }

    override fun pointIterator(): Vector2FIterator = PointIterator(this, index = 0)

    /**
     * Returns a copy of this instance with specified properties changed.
     *
     * @param orientation the value is expected to be [normalized][ComplexF.normalized].
     * @throws IllegalArgumentException when [width] is less than zero.
     * @throws IllegalArgumentException when [height] is less than zero.
     */
    override fun copy(center: Vector2F, orientation: ComplexF, width: Float, height: Float) =
        MutableRectangle(center, orientation, width, height)

    override fun equals(other: Any?): Boolean = other is Rectangle &&
            _center == other.center &&
            _orientation == other.orientation &&
            _width == other.width &&
            _height == other.height

    /** Indicates whether the other [MutableRectangle] is equal to this one. **/
    fun equals(other: MutableRectangle): Boolean =
        _center == other._center &&
                _orientation == other._orientation &&
                _width == other._width &&
                _height == other._height

    override fun hashCode(): Int {
        val centerHash: Int = _center.hashCode()
        val orientationHash: Int = _orientation.hashCode()
        val widthHash: Int = _width.hashCode()
        val heightHash: Int = _height.hashCode()

        return centerHash * 29791 + orientationHash * 961 + widthHash * 31 + heightHash
    }

    override fun toString() =
        StringBuilder("Rectangle(center=").append(_center)
            .append(", orientation=").append(_orientation)
            .append(", width=").append(_width)
            .append(", height=").append(_height).append(")")
            .toString()

    override operator fun component1(): Vector2F = _center

    override operator fun component2(): ComplexF = _orientation

    override operator fun component3(): Float = _width

    override operator fun component4(): Float = _height

    companion object {
        private inline fun throwWhenConstructorArgumentsAreIllegal(width: Float, height: Float) {
            if (width < 0f) {
                throw IllegalArgumentException("width must be greater than or equal to zero.")
            }
            if (height < 0f) {
                throw IllegalArgumentException("height must be greater than or equal to zero.")
            }
        }

        private inline fun createInternal(
            center: Vector2F, orientation: ComplexF, width: Float, height: Float
        ): MutableRectangle {
            val (cX: Float, cY: Float) = center
            val (oR: Float, oI: Float) = orientation
            val halfWidth: Float = width * 0.5f
            val halfHeight: Float = height * 0.5f
            val addendX1: Float = oR * halfWidth
            val addendX2: Float = oI * halfHeight
            val addendY1: Float = oR * halfHeight
            val addendY2: Float = oI * halfWidth
            val addendSumX1X2: Float = addendX1 + addendX2
            val addendDiffX1X2: Float = addendX1 - addendX2
            val addendSumY1Y2: Float = addendY1 + addendY2
            val addendDiffY1Y2: Float = addendY1 - addendY2

            return MutableRectangle(
                center,
                orientation,
                width,
                height,
                pointA = Vector2F(cX + addendDiffX1X2, cY + addendSumY1Y2),
                pointB = Vector2F(cX - addendSumX1X2, cY + addendDiffY1Y2),
                pointC = Vector2F(cX - addendDiffX1X2, cY - addendSumY1Y2),
                pointD = Vector2F(cX + addendSumX1X2, cY - addendDiffY1Y2)
            )
        }
    }

    private class PointIterator(
        private val rectangle: MutableRectangle,
        private var index: Int
    ) : Vector2FIterator() {
        override fun hasNext(): Boolean = index < 4

        override fun nextVector2F(): Vector2F = when (index++) {
            0 -> rectangle._pointA
            1 -> rectangle._pointB
            2 -> rectangle._pointC
            3 -> rectangle._pointD
            else -> throw NoSuchElementException("${index - 1}")
        }
    }
}