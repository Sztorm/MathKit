@file:Suppress("NOTHING_TO_INLINE", "OVERRIDE_BY_INLINE", "PropertyName")

package com.sztorm.mathkit.euclidean2d

import com.sztorm.mathkit.*
import kotlin.math.PI
import kotlin.math.absoluteValue
import kotlin.math.sqrt
import kotlin.math.withSign

/** Represents a mutable transformable regular triangle in a two-dimensional Euclidean space. **/
class MutableRegularTriangle : RegularTriangle, MutableTransformable {
    internal var _center: Vector2F
    internal var _orientation: ComplexF
    internal var _sideLength: Float
    internal var _pointA: Vector2F
    internal var _pointB: Vector2F
    internal var _pointC: Vector2F

    /**
     * Creates a new instance of [MutableRegularTriangle].
     *
     * @param orientation the value is expected to be [normalized][ComplexF.normalized].
     * @throws IllegalArgumentException when [sideLength] is less than zero.
     */
    constructor(center: Vector2F, orientation: ComplexF, sideLength: Float) {
        throwWhenConstructorArgumentIsIllegal(sideLength)
        val (cX: Float, cY: Float) = center
        val (oR: Float, oI: Float) = orientation
        val halfSideLength: Float = sideLength * 0.5f
        val inradius: Float = sideLength * 0.28867513f
        val circumradius: Float = inradius + inradius
        val addendX1: Float = oI * inradius + cX
        val addendX2: Float = oR * halfSideLength
        val addendY1: Float = oI * halfSideLength
        val addendY2: Float = oR * inradius - cY
        _center = center
        _orientation = orientation
        _sideLength = sideLength
        _pointA = Vector2F(cX - oI * circumradius, cY + oR * circumradius)
        _pointB = Vector2F(addendX1 - addendX2, -addendY1 - addendY2)
        _pointC = Vector2F(addendX1 + addendX2, addendY1 - addendY2)
    }

    private constructor(
        center: Vector2F,
        orientation: ComplexF,
        sideLength: Float,
        pointA: Vector2F,
        pointB: Vector2F,
        pointC: Vector2F
    ) {
        _center = center
        _orientation = orientation
        _sideLength = sideLength
        _pointA = pointA
        _pointB = pointB
        _pointC = pointC
    }

    internal constructor(regularPolygon: MutableRegularPolygon) {
        val points: Vector2FArray = regularPolygon._points
        _center = regularPolygon._center
        _orientation = regularPolygon._orientation
        _sideLength = regularPolygon._sideLength
        _pointA = points.elementAt(0)
        _pointB = points.elementAt(1)
        _pointC = points.elementAt(2)
    }

    override val center: Vector2F
        get() = _center

    override val orientation: ComplexF
        get() = _orientation

    override val sideLength: Float
        get() = _sideLength

    override val pointA: Vector2F
        get() = _pointA

    override val pointB: Vector2F
        get() = _pointB

    override val pointC: Vector2F
        get() = _pointC

    override val area: Float
        get() = 0.4330127f * _sideLength * _sideLength

    override val perimeter: Float
        get() = 3f * _sideLength

    override val sideLengthAB: Float
        get() = _sideLength

    override val sideLengthBC: Float
        get() = _sideLength

    override val sideLengthAC: Float
        get() = _sideLength

    override inline val sideCount: Int
        get() = 3

    override inline val interiorAngle: AngleF
        get() = AngleF((PI / 3.0).toFloat())

    override inline val exteriorAngle: AngleF
        get() = AngleF((2.0 / 3.0 * PI).toFloat())

    override val inradius: Float
        get() = 0.28867513f * _sideLength

    override val circumradius: Float
        get() = 0.5773503f * _sideLength

    override val position: Vector2F
        get() = _center

    override val incenter: Vector2F
        get() = _center

    override val centroid: Vector2F
        get() = _center

    override val circumcenter: Vector2F
        get() = _center

    override val orthocenter: Vector2F
        get() = _center

    override fun movedBy(displacement: Vector2F): MutableRegularTriangle = createInternal(
        center = _center + displacement,
        _orientation,
        _sideLength,
    )

    override fun movedTo(position: Vector2F): MutableRegularTriangle = createInternal(
        center = position,
        _orientation,
        _sideLength
    )

    override fun moveBy(displacement: Vector2F) {
        _center += displacement
        _pointA += displacement
        _pointB += displacement
        _pointC += displacement
    }

    override fun moveTo(position: Vector2F) {
        val displacement: Vector2F = position - _center
        _center = position
        _pointA += displacement
        _pointB += displacement
        _pointC += displacement
    }

    private inline fun rotatedByImpl(rotation: ComplexF): MutableRegularTriangle = createInternal(
        _center,
        orientation = (_orientation * rotation).normalizedOrElse(ComplexF.ONE),
        _sideLength
    )

    override fun rotatedBy(rotation: AngleF): MutableRegularTriangle =
        rotatedByImpl(ComplexF.fromAngle(rotation))

    override fun rotatedBy(rotation: ComplexF): MutableRegularTriangle = rotatedByImpl(rotation)

    private inline fun rotatedToImpl(
        orientation: ComplexF
    ): MutableRegularTriangle = createInternal(
        _center,
        orientation = orientation.normalizedOrElse(ComplexF.ONE),
        _sideLength
    )

    override fun rotatedTo(orientation: AngleF): MutableRegularTriangle =
        rotatedToImpl(ComplexF.fromAngle(orientation))

    override fun rotatedTo(orientation: ComplexF): MutableRegularTriangle =
        rotatedToImpl(orientation)

    private inline fun rotatedAroundPointByImpl(
        point: Vector2F, rotation: ComplexF
    ): MutableRegularTriangle {
        val (pX: Float, pY: Float) = point
        val (rotR: Float, rotI: Float) = rotation
        val (cX: Float, cY: Float) = _center
        val (startRotR: Float, startRotI: Float) = _orientation
        val cpDiffX: Float = cX - pX
        val cpDiffY: Float = cY - pY
        val targetCenterX: Float = cpDiffX * rotR - cpDiffY * rotI + pX
        val targetCenterY: Float = cpDiffY * rotR + cpDiffX * rotI + pY
        val targetRotR: Float = startRotR * rotR - startRotI * rotI
        val targetRotI: Float = startRotI * rotR + startRotR * rotI

        return createInternal(
            center = Vector2F(targetCenterX, targetCenterY),
            orientation = ComplexF(targetRotR, targetRotI).normalizedOrElse(ComplexF.ONE),
            _sideLength,
        )
    }

    override fun rotatedAroundPointBy(point: Vector2F, rotation: AngleF): MutableRegularTriangle =
        rotatedAroundPointByImpl(point, ComplexF.fromAngle(rotation))

    override fun rotatedAroundPointBy(
        point: Vector2F, rotation: ComplexF
    ): MutableRegularTriangle = rotatedAroundPointByImpl(point, rotation)

    private inline fun rotatedAroundPointToImpl(
        point: Vector2F, orientation: ComplexF
    ): MutableRegularTriangle {
        val (pX: Float, pY: Float) = point
        val (rotR: Float, rotI: Float) = orientation
        val center: Vector2F = _center
        val (cX: Float, cY: Float) = center
        val cpDiffX: Float = cX - pX
        val cpDiffY: Float = cY - pY
        val centerToPointDist: Float = sqrt(cpDiffX * cpDiffX + cpDiffY * cpDiffY)

        return if (centerToPointDist > 0.00001f) {
            val pointRotR: Float = cpDiffX / centerToPointDist
            val pointRotI: Float = cpDiffY / centerToPointDist
            val targetOrientation = (ComplexF(pointRotR, -pointRotI) * _orientation *
                orientation).normalizedOrElse(ComplexF.ONE)
            val targetCenterX: Float = rotR * centerToPointDist + pX
            val targetCenterY: Float = rotI * centerToPointDist + pY

            createInternal(
                center = Vector2F(targetCenterX, targetCenterY),
                orientation = targetOrientation,
                _sideLength,
            )
        } else createInternal(
            center,
            orientation = orientation.normalizedOrElse(ComplexF.ONE),
            _sideLength
        )
    }

    override fun rotatedAroundPointTo(
        point: Vector2F, orientation: AngleF
    ): MutableRegularTriangle = rotatedAroundPointToImpl(point, ComplexF.fromAngle(orientation))

    override fun rotatedAroundPointTo(
        point: Vector2F, orientation: ComplexF
    ): MutableRegularTriangle = rotatedAroundPointToImpl(point, orientation)

    private inline fun rotateToImpl(orientation: ComplexF) {
        val (cX: Float, cY: Float) = _center
        val (rotR: Float, rotI: Float) = orientation
        val sideLength: Float = _sideLength
        val halfSideLength: Float = sideLength * 0.5f
        val inradius: Float = 0.28867513f * sideLength
        val circumradius: Float = inradius + inradius
        val addendX1: Float = rotI * inradius + cX
        val addendX2: Float = rotR * halfSideLength
        val addendY1: Float = rotI * halfSideLength
        val addendY2: Float = rotR * inradius - cY
        _orientation = orientation
        _pointA = Vector2F(cX - rotI * circumradius, cY + rotR * circumradius)
        _pointB = Vector2F(addendX1 - addendX2, -addendY1 - addendY2)
        _pointC = Vector2F(addendX1 + addendX2, addendY1 - addendY2)
    }

    override fun rotateBy(rotation: AngleF) =
        rotateToImpl(_orientation * ComplexF.fromAngle(rotation))

    override fun rotateBy(rotation: ComplexF) = rotateToImpl(_orientation * rotation)

    override fun rotateTo(orientation: AngleF) = rotateToImpl(ComplexF.fromAngle(orientation))

    override fun rotateTo(orientation: ComplexF) = rotateToImpl(orientation)

    private inline fun rotateAroundPointByImpl(point: Vector2F, rotation: ComplexF) {
        val (pX: Float, pY: Float) = point
        val (rotR: Float, rotI: Float) = rotation
        val (cX: Float, cY: Float) = _center
        val (startRotR: Float, startRotI: Float) = _orientation
        val cpDiffX: Float = cX - pX
        val cpDiffY: Float = cY - pY
        val targetCenterX: Float = cpDiffX * rotR - cpDiffY * rotI + pX
        val targetCenterY: Float = cpDiffY * rotR + cpDiffX * rotI + pY
        val targetRotR: Float = startRotR * rotR - startRotI * rotI
        val targetRotI: Float = startRotI * rotR + startRotR * rotI
        val sideLength: Float = _sideLength
        val halfSideLength: Float = sideLength * 0.5f
        val inradius: Float = 0.28867513f * sideLength
        val circumradius: Float = inradius + inradius
        val addendX1: Float = targetRotI * inradius + targetCenterX
        val addendX2: Float = targetRotR * halfSideLength
        val addendY1: Float = targetRotI * halfSideLength
        val addendY2: Float = targetRotR * inradius - targetCenterY
        _center = Vector2F(targetCenterX, targetCenterY)
        _orientation = ComplexF(targetRotR, targetRotI)
        _pointA = Vector2F(
            targetCenterX - targetRotI * circumradius,
            targetCenterY + targetRotR * circumradius
        )
        _pointB = Vector2F(addendX1 - addendX2, -addendY1 - addendY2)
        _pointC = Vector2F(addendX1 + addendX2, addendY1 - addendY2)
    }

    override fun rotateAroundPointBy(point: Vector2F, rotation: AngleF) =
        rotateAroundPointByImpl(point, ComplexF.fromAngle(rotation))

    override fun rotateAroundPointBy(point: Vector2F, rotation: ComplexF) =
        rotateAroundPointByImpl(point, rotation)

    private inline fun rotateAroundPointToImpl(point: Vector2F, orientation: ComplexF) {
        val (pX: Float, pY: Float) = point
        val (rotR: Float, rotI: Float) = orientation
        val (cX: Float, cY: Float) = _center
        val sideLength: Float = _sideLength
        val halfSideLength: Float = sideLength * 0.5f
        val inradius: Float = 0.28867513f * sideLength
        val circumradius: Float = inradius + inradius
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
            val addendX1: Float = targetRotI * inradius + targetCenterX
            val addendX2: Float = targetRotR * halfSideLength
            val addendY1: Float = targetRotI * halfSideLength
            val addendY2: Float = targetRotR * inradius - targetCenterY
            _center = Vector2F(targetCenterX, targetCenterY)
            _orientation = targetRot
            _pointA = Vector2F(
                targetCenterX - targetRotI * circumradius,
                targetCenterY + targetRotR * circumradius
            )
            _pointB = Vector2F(addendX1 - addendX2, -addendY1 - addendY2)
            _pointC = Vector2F(addendX1 + addendX2, addendY1 - addendY2)
        } else {
            val addendX1: Float = rotI * inradius + cX
            val addendX2: Float = rotR * halfSideLength
            val addendY1: Float = rotI * halfSideLength
            val addendY2: Float = rotR * inradius - cY
            _orientation = orientation
            _pointA = Vector2F(cX - rotI * circumradius, cY + rotR * circumradius)
            _pointB = Vector2F(addendX1 - addendX2, -addendY1 - addendY2)
            _pointC = Vector2F(addendX1 + addendX2, addendY1 - addendY2)
        }
    }

    override fun rotateAroundPointTo(point: Vector2F, orientation: AngleF) =
        rotateAroundPointToImpl(point, ComplexF.fromAngle(orientation))

    override fun rotateAroundPointTo(point: Vector2F, orientation: ComplexF) =
        rotateAroundPointToImpl(point, orientation)

    override fun scaledBy(factor: Float): MutableRegularTriangle = createInternal(
        _center,
        orientation = _orientation * 1f.withSign(factor),
        sideLength = _sideLength * factor.absoluteValue
    )

    override fun dilatedBy(point: Vector2F, factor: Float): MutableRegularTriangle {
        val (cX: Float, cY: Float) = _center
        val (pX: Float, pY: Float) = point
        val f: Float = 1f - factor
        val centerX: Float = cX * factor + pX * f
        val centerY: Float = cY * factor + pY * f
        val (rotR: Float, rotI: Float) = _orientation * 1f.withSign(factor)
        val sideLength: Float = _sideLength * factor.absoluteValue
        val halfSideLength: Float = sideLength * 0.5f
        val inradius: Float = 0.28867513f * sideLength
        val circumradius: Float = inradius + inradius
        val addendX1: Float = rotI * inradius + centerX
        val addendX2: Float = rotR * halfSideLength
        val addendY1: Float = rotI * halfSideLength
        val addendY2: Float = rotR * inradius - centerY

        return MutableRegularTriangle(
            center = Vector2F(centerX, centerY),
            orientation = ComplexF(rotR, rotI),
            sideLength,
            pointA = Vector2F(centerX - rotI * circumradius, centerY + rotR * circumradius),
            pointB = Vector2F(addendX1 - addendX2, -addendY1 - addendY2),
            pointC = Vector2F(addendX1 + addendX2, addendY1 - addendY2)
        )
    }

    override fun scaleBy(factor: Float) {
        val (cX: Float, cY: Float) = _center
        val (rotR: Float, rotI: Float) = _orientation * 1f.withSign(factor)
        val sideLength: Float = _sideLength * factor.absoluteValue
        val halfSideLength: Float = sideLength * 0.5f
        val inradius: Float = 0.28867513f * sideLength
        val circumradius: Float = inradius + inradius
        val addendX1: Float = rotI * inradius + cX
        val addendX2: Float = rotR * halfSideLength
        val addendY1: Float = rotI * halfSideLength
        val addendY2: Float = rotR * inradius - cY
        _orientation = ComplexF(rotR, rotI)
        _sideLength = sideLength
        _pointA = Vector2F(cX - rotI * circumradius, cY + rotR * circumradius)
        _pointB = Vector2F(addendX1 - addendX2, -addendY1 - addendY2)
        _pointC = Vector2F(addendX1 + addendX2, addendY1 - addendY2)
    }

    override fun dilateBy(point: Vector2F, factor: Float) {
        val (cX: Float, cY: Float) = _center
        val (pX: Float, pY: Float) = point
        val f: Float = 1f - factor
        val centerX: Float = cX * factor + pX * f
        val centerY: Float = cY * factor + pY * f
        val (rotR: Float, rotI: Float) = _orientation * 1f.withSign(factor)
        val sideLength: Float = _sideLength * factor.absoluteValue
        val halfSideLength: Float = sideLength * 0.5f
        val inradius: Float = 0.28867513f * sideLength
        val circumradius: Float = inradius + inradius
        val addendX1: Float = rotI * inradius + centerX
        val addendX2: Float = rotR * halfSideLength
        val addendY1: Float = rotI * halfSideLength
        val addendY2: Float = rotR * inradius - centerY
        _center = Vector2F(centerX, centerY)
        _orientation = ComplexF(rotR, rotI)
        _sideLength = sideLength
        _pointA = Vector2F(centerX - rotI * circumradius, centerY + rotR * circumradius)
        _pointB = Vector2F(addendX1 - addendX2, -addendY1 - addendY2)
        _pointC = Vector2F(addendX1 + addendX2, addendY1 - addendY2)
    }

    private inline fun transformedByImpl(
        displacement: Vector2F, rotation: ComplexF
    ): MutableRegularTriangle = createInternal(
        center = _center + displacement,
        orientation = (_orientation * rotation).normalizedOrElse(ComplexF.ONE),
        _sideLength
    )

    override fun transformedBy(displacement: Vector2F, rotation: AngleF): MutableRegularTriangle =
        transformedByImpl(displacement, ComplexF.fromAngle(rotation))

    override fun transformedBy(
        displacement: Vector2F, rotation: ComplexF
    ): MutableRegularTriangle = transformedByImpl(displacement, rotation)

    private inline fun transformedByImpl(
        displacement: Vector2F, rotation: ComplexF, scaleFactor: Float
    ): MutableRegularTriangle = createInternal(
        center = _center + displacement,
        orientation = (_orientation * rotation)
            .normalizedOrElse(ComplexF.ONE) * 1f.withSign(scaleFactor),
        sideLength = _sideLength * scaleFactor.absoluteValue
    )

    override fun transformedBy(
        displacement: Vector2F, rotation: AngleF, scaleFactor: Float
    ): MutableRegularTriangle = transformedByImpl(
        displacement, ComplexF.fromAngle(rotation), scaleFactor
    )

    override fun transformedBy(
        displacement: Vector2F, rotation: ComplexF, scaleFactor: Float
    ): MutableRegularTriangle = transformedByImpl(displacement, rotation, scaleFactor)

    private inline fun transformedToImpl(
        position: Vector2F, orientation: ComplexF
    ): MutableRegularTriangle = createInternal(
        center = position,
        orientation = orientation.normalizedOrElse(ComplexF.ONE),
        _sideLength
    )

    override fun transformedTo(position: Vector2F, orientation: AngleF): MutableRegularTriangle =
        transformedToImpl(position, ComplexF.fromAngle(orientation))

    override fun transformedTo(position: Vector2F, orientation: ComplexF): MutableRegularTriangle =
        transformedToImpl(position, orientation)

    private inline fun transformToImpl(position: Vector2F, orientation: ComplexF) {
        val (cX: Float, cY: Float) = position
        val (oR: Float, oI: Float) = orientation
        val sideLength: Float = _sideLength
        val halfSideLength: Float = sideLength * 0.5f
        val inradius: Float = 0.28867513f * sideLength
        val circumradius: Float = inradius + inradius
        val addendX1: Float = oI * inradius + cX
        val addendX2: Float = oR * halfSideLength
        val addendY1: Float = oI * halfSideLength
        val addendY2: Float = oR * inradius - cY
        _center = position
        _orientation = orientation
        _pointA = Vector2F(cX - oI * circumradius, cY + oR * circumradius)
        _pointB = Vector2F(addendX1 - addendX2, -addendY1 - addendY2)
        _pointC = Vector2F(addendX1 + addendX2, addendY1 - addendY2)
    }

    override fun transformBy(displacement: Vector2F, rotation: AngleF) = transformToImpl(
        _center + displacement, _orientation * ComplexF.fromAngle(rotation)
    )

    override fun transformBy(displacement: Vector2F, rotation: ComplexF) =
        transformToImpl(_center + displacement, _orientation * rotation)

    private inline fun transformByImpl(
        displacement: Vector2F, rotation: ComplexF, scaleFactor: Float
    ) {
        val (cX: Float, cY: Float) = _center
        val (oR: Float, oI: Float) = _orientation
        val sideLength: Float = _sideLength * scaleFactor.absoluteValue
        val (dX: Float, dY: Float) = displacement
        val centerX: Float = cX + dX
        val centerY: Float = cY + dY
        val (rotR: Float, rotI: Float) = rotation
        val scaleFactorSign: Float = 1f.withSign(scaleFactor)
        val orientationR: Float = (oR * rotR - oI * rotI) * scaleFactorSign
        val orientationI: Float = (oI * rotR + oR * rotI) * scaleFactorSign
        val halfSideLength: Float = sideLength * 0.5f
        val inradius: Float = 0.28867513f * sideLength
        val circumradius: Float = inradius + inradius
        val addendX1: Float = orientationI * inradius + centerX
        val addendX2: Float = orientationR * halfSideLength
        val addendY1: Float = orientationI * halfSideLength
        val addendY2: Float = orientationR * inradius - centerY
        _center = Vector2F(centerX, centerY)
        _orientation = ComplexF(orientationR, orientationI)
        _sideLength = sideLength
        _pointA = Vector2F(
            centerX - orientationI * circumradius,
            centerY + orientationR * circumradius
        )
        _pointB = Vector2F(addendX1 - addendX2, -addendY1 - addendY2)
        _pointC = Vector2F(addendX1 + addendX2, addendY1 - addendY2)
    }

    override fun transformBy(displacement: Vector2F, rotation: AngleF, scaleFactor: Float) =
        transformByImpl(displacement, ComplexF.fromAngle(rotation), scaleFactor)

    override fun transformBy(displacement: Vector2F, rotation: ComplexF, scaleFactor: Float) =
        transformByImpl(displacement, rotation, scaleFactor)

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
        val newOrientation: ComplexF = _orientation.normalizedOrElse(ComplexF.ONE)
        val (cX: Float, cY: Float) = _center
        val (oR: Float, oI: Float) = newOrientation
        val sideLength: Float = _sideLength
        val halfSideLength: Float = sideLength * 0.5f
        val inradius: Float = 0.28867513f * sideLength
        val circumradius: Float = inradius + inradius
        val addendX1: Float = oI * inradius + cX
        val addendX2: Float = oR * halfSideLength
        val addendY1: Float = oI * halfSideLength
        val addendY2: Float = oR * inradius - cY
        _orientation = newOrientation
        _pointA = Vector2F(cX - oI * circumradius, cY + oR * circumradius)
        _pointB = Vector2F(addendX1 - addendX2, -addendY1 - addendY2)
        _pointC = Vector2F(addendX1 + addendX2, addendY1 - addendY2)
    }

    private inline fun setInternal(center: Vector2F, orientation: ComplexF, sideLength: Float) {
        val (cX: Float, cY: Float) = center
        val (oR: Float, oI: Float) = orientation
        val halfSideLength: Float = sideLength * 0.5f
        val inradius: Float = 0.28867513f * sideLength
        val circumradius: Float = inradius + inradius
        val addendX1: Float = oI * inradius + cX
        val addendX2: Float = oR * halfSideLength
        val addendY1: Float = oI * halfSideLength
        val addendY2: Float = oR * inradius - cY
        _center = center
        _orientation = orientation
        _sideLength = sideLength
        _pointA = Vector2F(cX - oI * circumradius, cY + oR * circumradius)
        _pointB = Vector2F(addendX1 - addendX2, -addendY1 - addendY2)
        _pointC = Vector2F(addendX1 + addendX2, addendY1 - addendY2)
    }

    /**
     * Sets the specified properties of this instance.
     *
     * @param orientation the value is expected to be [normalized][ComplexF.normalized].
     * @throws IllegalArgumentException when [sideLength] is less than zero.
     */
    fun set(
        center: Vector2F = this.center,
        orientation: ComplexF = this.orientation,
        sideLength: Float = this.sideLength
    ) {
        throwWhenConstructorArgumentIsIllegal(sideLength)
        setInternal(center, orientation, sideLength)
    }

    override fun interpolated(
        to: RegularTriangle, by: Float
    ): MutableRegularTriangle = createInternal(
        center = Vector2F.lerp(_center, to.center, by),
        orientation = ComplexF.slerp(_orientation, to.orientation, by)
            .normalizedOrElse(ComplexF.ONE),
        sideLength = Float.lerp(_sideLength, to.sideLength, by)
    )

    /**
     * Sets this regular triangle with the result of interpolation [from] one regular triangle [to]
     * another regular triangle [by] a factor.
     *
     * @param from the regular triangle from which the interpolation starts.
     * @param to the regular triangle at which the interpolation ends.
     * @param by the interpolation factor which is expected to be in the range of `[0, 1]`.
     */
    fun interpolate(from: RegularTriangle, to: RegularTriangle, by: Float) = setInternal(
        center = Vector2F.lerp(from.center, to.center, by),
        orientation = ComplexF.slerp(from.orientation, to.orientation, by),
        sideLength = Float.lerp(from.sideLength, to.sideLength, by)
    )

    override fun closestPointTo(point: Vector2F): Vector2F {
        val sideLength: Float = _sideLength
        val halfSideLength: Float = 0.5f * sideLength
        val inradius: Float = 0.28867513f * sideLength
        val orientation: ComplexF = _orientation
        val center: Vector2F = _center
        val p1: ComplexF = orientation.conjugate *
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
                val vertexPoint = Vector2F(halfSideLength.withSign(p1X), -inradius)

                return vertexPoint * orientation + center
            }
            val edgePoint = Vector2F(p1X, -inradius)

            return edgePoint * orientation + center
        }
        val add120DegRotation = ComplexF(-0.5f, -(0.8660254f.withSign(p1X)))
        val p2: ComplexF = add120DegRotation * p1
        val p2X: Float = p2.real
        val p2Y: Float = p2.imaginary

        if ((p2Y >= -inradius)) {
            return point
        }
        if (p2X.absoluteValue >= halfSideLength) {
            val vertexPoint = Vector2F(halfSideLength.withSign(p2X), -inradius)

            return vertexPoint * (add120DegRotation.conjugate * orientation) + center
        }
        val edgePoint = Vector2F(p2X, -inradius)

        return edgePoint * (add120DegRotation.conjugate * orientation) + center
    }

    override fun intersects(ray: Ray): Boolean {
        val (oX: Float, oY: Float) = ray.origin
        val (dirX: Float, dirY: Float) = ray.direction
        val (aX: Float, aY: Float) = _pointA
        val (bX: Float, bY: Float) = _pointB
        val (cX: Float, cY: Float) = _pointC
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

    override operator fun contains(point: Vector2F): Boolean {
        val (cX: Float, cY: Float) = _center
        val (rotR: Float, rotI: Float) = _orientation
        val sideLength: Float = _sideLength
        val minusInradius: Float = -0.28867513f * sideLength // sqrt(3) / 6 * a
        val (pX: Float, pY: Float) = point
        val cpDiffX: Float = pX - cX
        val cpDiffY: Float = pY - cY
        val p1X: Float = rotR * cpDiffX + rotI * cpDiffY
        val p1Y: Float = rotR * cpDiffY - rotI * cpDiffX
        //yAB = sqrt(3) * x + (sqrt(3) / 3) * a
        //yAC = -sqrt(3) * x + (sqrt(3) / 3) * a
        val ax: Float = 1.7320508f * p1X
        val b: Float = 0.5773503f * sideLength
        val yAB: Float = ax + b
        val yAC: Float = -ax + b

        return (p1Y >= minusInradius) and (p1Y <= yAB) and (p1Y <= yAC)
    }

    override fun pointIterator(): Vector2FIterator = PointIterator(this, index = 0)

    /**
     * Returns a copy of this instance with specified properties changed.
     *
     * @param orientation the value is expected to be [normalized][ComplexF.normalized].
     * @throws IllegalArgumentException when [sideLength] is less than zero.
     */
    override fun copy(center: Vector2F, orientation: ComplexF, sideLength: Float) =
        MutableRegularTriangle(center, orientation, sideLength)

    override fun equals(other: Any?): Boolean = other is RegularTriangle &&
        _center == other.center &&
        _orientation == other.orientation &&
        _sideLength == other.sideLength

    /** Indicates whether the other [MutableRegularTriangle] is equal to this one. **/
    fun equals(other: MutableRegularTriangle): Boolean =
        _center == other._center &&
            _orientation == other._orientation &&
            _sideLength == other._sideLength

    override fun hashCode(): Int {
        val centerHash: Int = _center.hashCode()
        val orientationHash: Int = _orientation.hashCode()
        val sideLengthHash: Int = _sideLength.hashCode()

        return centerHash * 961 + orientationHash * 31 + sideLengthHash
    }

    override fun toString() =
        StringBuilder("RegularTriangle(center=").append(_center)
            .append(", orientation=").append(_orientation)
            .append(", sideLength=").append(_sideLength).append(")")
            .toString()

    override operator fun component1(): Vector2F = _center

    override operator fun component2(): ComplexF = _orientation

    override operator fun component3(): Float = _sideLength

    companion object {
        private inline fun throwWhenConstructorArgumentIsIllegal(sideLength: Float) {
            if (sideLength < 0f) {
                throw IllegalArgumentException("sideLength must be greater than or equal to zero.")
            }
        }

        private inline fun createInternal(
            center: Vector2F, orientation: ComplexF, sideLength: Float
        ): MutableRegularTriangle {
            val (cX: Float, cY: Float) = center
            val (oR: Float, oI: Float) = orientation
            val halfSideLength: Float = sideLength * 0.5f
            val inradius: Float = sideLength * 0.28867513f
            val circumradius: Float = inradius + inradius
            val addendX1: Float = oI * inradius + cX
            val addendX2: Float = oR * halfSideLength
            val addendY1: Float = oI * halfSideLength
            val addendY2: Float = oR * inradius - cY

            return MutableRegularTriangle(
                center,
                orientation,
                sideLength,
                pointA = Vector2F(cX - oI * circumradius, cY + oR * circumradius),
                pointB = Vector2F(addendX1 - addendX2, -addendY1 - addendY2),
                pointC = Vector2F(addendX1 + addendX2, addendY1 - addendY2)
            )
        }
    }

    private class PointIterator(
        private val triangle: MutableRegularTriangle,
        private var index: Int
    ) : Vector2FIterator() {
        override fun hasNext(): Boolean = index < 3

        override fun nextVector2F(): Vector2F = when (index++) {
            0 -> triangle._pointA
            1 -> triangle._pointB
            2 -> triangle._pointC
            else -> throw NoSuchElementException("${index - 1}")
        }
    }
}