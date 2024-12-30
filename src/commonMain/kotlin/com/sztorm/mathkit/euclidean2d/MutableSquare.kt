@file:Suppress("OVERRIDE_BY_INLINE", "PropertyName")

package com.sztorm.mathkit.euclidean2d

import com.sztorm.mathkit.*
import kotlin.math.*

/** Represents a mutable transformable square in a two-dimensional Euclidean space. **/
class MutableSquare : Square, MutableTransformable {
    internal var _center: Vector2F
    internal var _orientation: ComplexF
    internal var _sideLength: Float
    internal var _pointA: Vector2F
    internal var _pointB: Vector2F
    internal var _pointC: Vector2F
    internal var _pointD: Vector2F

    /**
     * Creates a new instance of [MutableSquare].
     *
     * @param orientation the value is expected to be [normalized][ComplexF.normalized].
     * @throws IllegalArgumentException when [sideLength] is less than zero.
     */
    constructor(center: Vector2F, orientation: ComplexF, sideLength: Float) {
        throwWhenConstructorArgumentIsIllegal(sideLength)
        val (cX: Float, cY: Float) = center
        val (oR: Float, oI: Float) = orientation
        val halfSideLength: Float = sideLength * 0.5f
        val addendA: Float = halfSideLength * (oR + oI)
        val addendB: Float = halfSideLength * (oR - oI)
        _center = center
        _orientation = orientation
        _sideLength = sideLength
        _pointA = Vector2F(cX + addendB, cY + addendA)
        _pointB = Vector2F(cX - addendA, cY + addendB)
        _pointC = Vector2F(cX - addendB, cY - addendA)
        _pointD = Vector2F(cX + addendA, cY - addendB)
    }

    private constructor(
        center: Vector2F,
        orientation: ComplexF,
        sideLength: Float,
        pointA: Vector2F,
        pointB: Vector2F,
        pointC: Vector2F,
        pointD: Vector2F
    ) {
        _center = center
        _orientation = orientation
        _sideLength = sideLength
        _pointA = pointA
        _pointB = pointB
        _pointC = pointC
        _pointD = pointD
    }

    internal constructor(regularPolygon: MutableRegularPolygon) {
        val points: Vector2FArray = regularPolygon._points
        _center = regularPolygon._center
        _orientation = regularPolygon._orientation
        _sideLength = regularPolygon._sideLength
        _pointA = points.elementAt(0)
        _pointB = points.elementAt(1)
        _pointC = points.elementAt(2)
        _pointD = points.elementAt(3)
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

    override val pointD: Vector2F
        get() = _pointD

    override val area: Float
        get() = _sideLength * _sideLength

    override val perimeter: Float
        get() = 4f * _sideLength

    override val width: Float
        get() = _sideLength

    override val height: Float
        get() = _sideLength

    override inline val sideCount: Int
        get() = 4

    override inline val interiorAngle: AngleF
        get() = AngleF((0.5 * PI).toFloat())

    override inline val exteriorAngle: AngleF
        get() = AngleF((0.5 * PI).toFloat())

    override val inradius: Float
        get() = 0.5f * _sideLength

    override val circumradius: Float
        get() = 0.7071068f * _sideLength

    override val position: Vector2F
        get() = _center

    override fun movedBy(displacement: Vector2F): MutableSquare = createInternal(
        center = _center + displacement,
        _orientation,
        _sideLength,
    )

    override fun movedTo(position: Vector2F): MutableSquare = createInternal(
        center = position,
        _orientation,
        _sideLength,
    )

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

    private inline fun rotatedByImpl(rotation: ComplexF): MutableSquare = createInternal(
        _center,
        orientation = (_orientation * rotation).normalizedOrElse(ComplexF.ONE),
        _sideLength
    )

    override fun rotatedBy(rotation: AngleF): MutableSquare =
        rotatedByImpl(ComplexF.fromAngle(rotation))

    override fun rotatedBy(rotation: ComplexF): MutableSquare = rotatedByImpl(rotation)

    private inline fun rotatedToImpl(orientation: ComplexF): MutableSquare = createInternal(
        _center,
        orientation = orientation.normalizedOrElse(ComplexF.ONE),
        _sideLength
    )

    override fun rotatedTo(orientation: AngleF): MutableSquare =
        rotatedToImpl(ComplexF.fromAngle(orientation))

    override fun rotatedTo(orientation: ComplexF): MutableSquare = rotatedToImpl(orientation)

    private inline fun rotatedAroundPointByImpl(
        point: Vector2F, rotation: ComplexF
    ): MutableSquare {
        val (cX: Float, cY: Float) = _center
        val (oR: Float, oI: Float) = _orientation
        val (pX: Float, pY: Float) = point
        val (rotR: Float, rotI: Float) = rotation
        val cpDiffX: Float = cX - pX
        val cpDiffY: Float = cY - pY
        val centerX: Float = cpDiffX * rotR - cpDiffY * rotI + pX
        val centerY: Float = cpDiffY * rotR + cpDiffX * rotI + pY
        val orientationR: Float = oR * rotR - oI * rotI
        val orientationI: Float = oI * rotR + oR * rotI

        return createInternal(
            center = Vector2F(centerX, centerY),
            orientation = ComplexF(orientationR, orientationI).normalizedOrElse(ComplexF.ONE),
            _sideLength,
        )
    }

    override fun rotatedAroundPointBy(point: Vector2F, rotation: AngleF): MutableSquare =
        rotatedAroundPointByImpl(point, ComplexF.fromAngle(rotation))

    override fun rotatedAroundPointBy(point: Vector2F, rotation: ComplexF): MutableSquare =
        rotatedAroundPointByImpl(point, rotation)

    private inline fun rotatedAroundPointToImpl(
        point: Vector2F, orientation: ComplexF
    ): MutableSquare {
        val center: Vector2F = _center
        val (cX: Float, cY: Float) = center
        val (pX: Float, pY: Float) = point
        val (oR: Float, oI: Float) = orientation
        val cpDiffX: Float = cX - pX
        val cpDiffY: Float = cY - pY
        val centerToPointDist: Float = sqrt(cpDiffX * cpDiffX + cpDiffY * cpDiffY)

        return if (centerToPointDist > 0.00001f) {
            val pointRotR: Float = cpDiffX / centerToPointDist
            val pointRotI: Float = cpDiffY / centerToPointDist
            val targetOrientation = (ComplexF(pointRotR, -pointRotI) * _orientation *
                    orientation).normalizedOrElse(ComplexF.ONE)
            val targetCenterX: Float = oR * centerToPointDist + pX
            val targetCenterY: Float = oI * centerToPointDist + pY

            createInternal(
                center = Vector2F(targetCenterX, targetCenterY),
                orientation = targetOrientation,
                _sideLength,
            )
        } else createInternal(
            center,
            orientation = orientation.normalizedOrElse(ComplexF.ONE),
            _sideLength,
        )
    }

    override fun rotatedAroundPointTo(point: Vector2F, orientation: AngleF): MutableSquare =
        rotatedAroundPointToImpl(point, ComplexF.fromAngle(orientation))

    override fun rotatedAroundPointTo(point: Vector2F, orientation: ComplexF): MutableSquare =
        rotatedAroundPointToImpl(point, orientation)

    private inline fun rotateToImpl(orientation: ComplexF) {
        val (cX: Float, cY: Float) = _center
        val (oR: Float, oI: Float) = orientation
        val halfSideLength: Float = _sideLength * 0.5f
        val addendA: Float = halfSideLength * (oR + oI)
        val addendB: Float = halfSideLength * (oR - oI)
        _orientation = orientation
        _pointA = Vector2F(cX + addendB, cY + addendA)
        _pointB = Vector2F(cX - addendA, cY + addendB)
        _pointC = Vector2F(cX - addendB, cY - addendA)
        _pointD = Vector2F(cX + addendA, cY - addendB)
    }

    override fun rotateBy(rotation: AngleF) =
        rotateToImpl(_orientation * ComplexF.fromAngle(rotation))

    override fun rotateBy(rotation: ComplexF) = rotateToImpl(_orientation * rotation)

    override fun rotateTo(orientation: AngleF) = rotateToImpl(ComplexF.fromAngle(orientation))

    override fun rotateTo(orientation: ComplexF) = rotateToImpl(orientation)

    private inline fun rotateAroundPointByImpl(point: Vector2F, rotation: ComplexF) {
        val (cX: Float, cY: Float) = _center
        val (oR: Float, oI: Float) = _orientation
        val (pX: Float, pY: Float) = point
        val (rotR: Float, rotI: Float) = rotation
        val halfSideLength: Float = _sideLength * 0.5f
        val cpDiffX: Float = cX - pX
        val cpDiffY: Float = cY - pY
        val centerX: Float = cpDiffX * rotR - cpDiffY * rotI + pX
        val centerY: Float = cpDiffY * rotR + cpDiffX * rotI + pY
        val orientationR: Float = oR * rotR - oI * rotI
        val orientationI: Float = oI * rotR + oR * rotI
        val addendA: Float = halfSideLength * (orientationR + orientationI)
        val addendB: Float = halfSideLength * (orientationR - orientationI)
        _center = Vector2F(centerX, centerY)
        _orientation = ComplexF(orientationR, orientationI)
        _pointA = Vector2F(centerX + addendB, centerY + addendA)
        _pointB = Vector2F(centerX - addendA, centerY + addendB)
        _pointC = Vector2F(centerX - addendB, centerY - addendA)
        _pointD = Vector2F(centerX + addendA, centerY - addendB)
    }

    override fun rotateAroundPointBy(point: Vector2F, rotation: AngleF) =
        rotateAroundPointByImpl(point, ComplexF.fromAngle(rotation))

    override fun rotateAroundPointBy(point: Vector2F, rotation: ComplexF) =
        rotateAroundPointByImpl(point, rotation)

    private inline fun rotateAroundPointToImpl(point: Vector2F, orientation: ComplexF) {
        val (cX: Float, cY: Float) = _center
        val halfSideLength: Float = _sideLength * 0.5f
        val (pX: Float, pY: Float) = point
        val (rotR: Float, rotI: Float) = orientation
        val cpDiffX: Float = cX - pX
        val cpDiffY: Float = cY - pY
        val centerToPointDist: Float = sqrt(cpDiffX * cpDiffX + cpDiffY * cpDiffY)

        if (centerToPointDist > 0.00001f) {
            val pointRotR: Float = cpDiffX / centerToPointDist
            val pointRotI: Float = cpDiffY / centerToPointDist
            val targetOrientation = ComplexF(pointRotR, -pointRotI) * _orientation * orientation
            val (targetOR: Float, targetOI: Float) = targetOrientation
            val targetCenterX: Float = rotR * centerToPointDist + pX
            val targetCenterY: Float = rotI * centerToPointDist + pY
            val addendA: Float = halfSideLength * (targetOR + targetOI)
            val addendB: Float = halfSideLength * (targetOR - targetOI)
            _center = Vector2F(targetCenterX, targetCenterY)
            _orientation = targetOrientation
            _pointA = Vector2F(targetCenterX + addendB, targetCenterY + addendA)
            _pointB = Vector2F(targetCenterX - addendA, targetCenterY + addendB)
            _pointC = Vector2F(targetCenterX - addendB, targetCenterY - addendA)
            _pointD = Vector2F(targetCenterX + addendA, targetCenterY - addendB)
        } else {
            val addendA: Float = halfSideLength * (rotR + rotI)
            val addendB: Float = halfSideLength * (rotR - rotI)
            _orientation = orientation
            _pointA = Vector2F(cX + addendB, cY + addendA)
            _pointB = Vector2F(cX - addendA, cY + addendB)
            _pointC = Vector2F(cX - addendB, cY - addendA)
            _pointD = Vector2F(cX + addendA, cY - addendB)
        }
    }

    override fun rotateAroundPointTo(point: Vector2F, orientation: AngleF) =
        rotateAroundPointToImpl(point, ComplexF.fromAngle(orientation))

    override fun rotateAroundPointTo(point: Vector2F, orientation: ComplexF) =
        rotateAroundPointToImpl(point, orientation)

    override fun scaledBy(factor: Float): MutableSquare = createInternal(
        _center,
        orientation = _orientation * 1f.withSign(factor),
        sideLength = _sideLength * factor.absoluteValue
    )

    override fun dilatedBy(point: Vector2F, factor: Float): MutableSquare {
        val (cX: Float, cY: Float) = _center
        val (pX: Float, pY: Float) = point
        val f: Float = 1f - factor
        val centerX: Float = cX * factor + pX * f
        val centerY: Float = cY * factor + pY * f
        val (orientationR: Float, orientationI: Float) = _orientation * 1f.withSign(factor)
        val sideLength: Float = _sideLength * factor.absoluteValue
        val halfSideLength: Float = sideLength * 0.5f
        val addendA: Float = halfSideLength * (orientationR + orientationI)
        val addendB: Float = halfSideLength * (orientationR - orientationI)

        return MutableSquare(
            center = Vector2F(centerX, centerY),
            orientation = ComplexF(orientationR, orientationI),
            sideLength,
            pointA = Vector2F(centerX + addendB, centerY + addendA),
            pointB = Vector2F(centerX - addendA, centerY + addendB),
            pointC = Vector2F(centerX - addendB, centerY - addendA),
            pointD = Vector2F(centerX + addendA, centerY - addendB)
        )
    }

    override fun scaleBy(factor: Float) {
        val (cX: Float, cY: Float) = _center
        val (orientationR: Float, orientationI: Float) = _orientation * 1f.withSign(factor)
        val sideLength: Float = _sideLength * factor.absoluteValue
        val halfSideLength: Float = sideLength * 0.5f
        val addendA: Float = halfSideLength * (orientationR + orientationI)
        val addendB: Float = halfSideLength * (orientationR - orientationI)
        _orientation = ComplexF(orientationR, orientationI)
        _sideLength = sideLength
        _pointA = Vector2F(cX + addendB, cY + addendA)
        _pointB = Vector2F(cX - addendA, cY + addendB)
        _pointC = Vector2F(cX - addendB, cY - addendA)
        _pointD = Vector2F(cX + addendA, cY - addendB)
    }

    override fun dilateBy(point: Vector2F, factor: Float) {
        val (cX: Float, cY: Float) = _center
        val (pX: Float, pY: Float) = point
        val f: Float = 1f - factor
        val centerX: Float = cX * factor + pX * f
        val centerY: Float = cY * factor + pY * f
        val (orientationR: Float, orientationI: Float) = _orientation * 1f.withSign(factor)
        val sideLength: Float = _sideLength * factor.absoluteValue
        val halfSideLength: Float = sideLength * 0.5f
        val addendA: Float = halfSideLength * (orientationR + orientationI)
        val addendB: Float = halfSideLength * (orientationR - orientationI)
        _center = Vector2F(centerX, centerY)
        _orientation = ComplexF(orientationR, orientationI)
        _sideLength = sideLength
        _pointA = Vector2F(centerX + addendB, centerY + addendA)
        _pointB = Vector2F(centerX - addendA, centerY + addendB)
        _pointC = Vector2F(centerX - addendB, centerY - addendA)
        _pointD = Vector2F(centerX + addendA, centerY - addendB)
    }

    private inline fun transformedByImpl(
        displacement: Vector2F, rotation: ComplexF
    ): MutableSquare = createInternal(
        center = _center + displacement,
        orientation = (_orientation * rotation).normalizedOrElse(ComplexF.ONE),
        _sideLength
    )

    override fun transformedBy(displacement: Vector2F, rotation: AngleF): MutableSquare =
        transformedByImpl(displacement, ComplexF.fromAngle(rotation))

    override fun transformedBy(displacement: Vector2F, rotation: ComplexF): MutableSquare =
        transformedByImpl(displacement, rotation)

    private inline fun transformedByImpl(
        displacement: Vector2F, rotation: ComplexF, scaleFactor: Float
    ): MutableSquare = createInternal(
        center = _center + displacement,
        orientation = (_orientation * rotation)
            .normalizedOrElse(ComplexF.ONE) * 1f.withSign(scaleFactor),
        sideLength = _sideLength * scaleFactor.absoluteValue
    )

    override fun transformedBy(
        displacement: Vector2F, rotation: AngleF, scaleFactor: Float
    ): MutableSquare = transformedByImpl(displacement, ComplexF.fromAngle(rotation), scaleFactor)

    override fun transformedBy(
        displacement: Vector2F, rotation: ComplexF, scaleFactor: Float
    ): MutableSquare = transformedByImpl(displacement, rotation, scaleFactor)

    private inline fun transformedToImpl(
        position: Vector2F, orientation: ComplexF
    ): MutableSquare = createInternal(
        center = position,
        orientation = orientation.normalizedOrElse(ComplexF.ONE),
        _sideLength
    )

    override fun transformedTo(position: Vector2F, orientation: AngleF): MutableSquare =
        transformedToImpl(position, ComplexF.fromAngle(orientation))

    override fun transformedTo(position: Vector2F, orientation: ComplexF): MutableSquare =
        transformedToImpl(position, orientation)

    private inline fun transformToImpl(position: Vector2F, orientation: ComplexF) {
        val (cX: Float, cY: Float) = position
        val (oR: Float, oI: Float) = orientation
        val halfSideLength: Float = _sideLength * 0.5f
        val addendA: Float = halfSideLength * (oR + oI)
        val addendB: Float = halfSideLength * (oR - oI)
        _center = position
        _orientation = orientation
        _pointA = Vector2F(cX + addendB, cY + addendA)
        _pointB = Vector2F(cX - addendA, cY + addendB)
        _pointC = Vector2F(cX - addendB, cY - addendA)
        _pointD = Vector2F(cX + addendA, cY - addendB)
    }

    override fun transformBy(displacement: Vector2F, rotation: AngleF) = transformToImpl(
        position = _center + displacement,
        orientation = _orientation * ComplexF.fromAngle(rotation)
    )

    override fun transformBy(displacement: Vector2F, rotation: ComplexF) = transformToImpl(
        position = _center + displacement,
        orientation = _orientation * rotation
    )

    private inline fun transformByImpl(
        displacement: Vector2F, rotation: ComplexF, scaleFactor: Float
    ) {
        val (cX: Float, cY: Float) = _center
        val (oR: Float, oI: Float) = _orientation
        val (dX: Float, dY: Float) = displacement
        val (rotR: Float, rotI: Float) = rotation
        val centerX: Float = cX + dX
        val centerY: Float = cY + dY
        val scaleFactorSign: Float = 1f.withSign(scaleFactor)
        val orientationR: Float = (oR * rotR - oI * rotI) * scaleFactorSign
        val orientationI: Float = (oI * rotR + oR * rotI) * scaleFactorSign
        val sideLength: Float = _sideLength * scaleFactor.absoluteValue
        val halfSideLength: Float = sideLength * 0.5f
        val addendA: Float = halfSideLength * (orientationR + orientationI)
        val addendB: Float = halfSideLength * (orientationR - orientationI)
        _center = Vector2F(centerX, centerY)
        _orientation = ComplexF(orientationR, orientationI)
        _sideLength = sideLength
        _pointA = Vector2F(centerX + addendB, centerY + addendA)
        _pointB = Vector2F(centerX - addendA, centerY + addendB)
        _pointC = Vector2F(centerX - addendB, centerY - addendA)
        _pointD = Vector2F(centerX + addendA, centerY - addendB)
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
        val halfSideLength: Float = _sideLength * 0.5f
        val addendA: Float = halfSideLength * (oR + oI)
        val addendB: Float = halfSideLength * (oR - oI)
        _orientation = newOrientation
        _pointA = Vector2F(cX + addendB, cY + addendA)
        _pointB = Vector2F(cX - addendA, cY + addendB)
        _pointC = Vector2F(cX - addendB, cY - addendA)
        _pointD = Vector2F(cX + addendA, cY - addendB)
    }

    private inline fun setInternal(center: Vector2F, orientation: ComplexF, sideLength: Float) {
        val (cX: Float, cY: Float) = center
        val (oR: Float, oI: Float) = orientation
        val halfSideLength: Float = sideLength * 0.5f
        val addendA: Float = halfSideLength * (oR + oI)
        val addendB: Float = halfSideLength * (oR - oI)
        _center = center
        _orientation = orientation
        _sideLength = sideLength
        _pointA = Vector2F(cX + addendB, cY + addendA)
        _pointB = Vector2F(cX - addendA, cY + addendB)
        _pointC = Vector2F(cX - addendB, cY - addendA)
        _pointD = Vector2F(cX + addendA, cY - addendB)
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

    override fun interpolated(to: Square, by: Float) = createInternal(
        center = Vector2F.lerp(_center, to.center, by),
        orientation = ComplexF.slerp(_orientation, to.orientation, by)
            .normalizedOrElse(ComplexF.ONE),
        sideLength = Float.lerp(_sideLength, to.sideLength, by)
    )

    /**
     * Sets this square with the result of interpolation [from] one square [to] another square [by]
     * a factor.
     *
     * @param from the square from which the interpolation starts.
     * @param to the square at which the interpolation ends.
     * @param by the interpolation factor which is expected to be in the range of `[0, 1]`.
     */
    fun interpolate(from: Square, to: Square, by: Float) = setInternal(
        center = Vector2F.lerp(from.center, to.center, by),
        orientation = ComplexF.slerp(from.orientation, to.orientation, by),
        sideLength = Float.lerp(from.sideLength, to.sideLength, by)
    )

    override fun closestPointTo(point: Vector2F): Vector2F {
        val (cX: Float, cY: Float) = _center
        val (rotR: Float, rotI: Float) = _orientation
        val halfSideLength: Float = _sideLength * 0.5f
        val (pX: Float, pY: Float) = point
        val cpDiffX: Float = pX - cX
        val cpDiffY: Float = pY - cY
        val p1X: Float = rotR * cpDiffX + rotI * cpDiffY
        val p1Y: Float = rotR * cpDiffY - rotI * cpDiffX
        val p2X: Float =
            if (p1X.absoluteValue > halfSideLength) halfSideLength.withSign(p1X) else p1X
        val p2Y: Float =
            if (p1Y.absoluteValue > halfSideLength) halfSideLength.withSign(p1Y) else p1Y

        return Vector2F(rotR * p2X - rotI * p2Y + cX, rotI * p2X + rotR * p2Y + cY)
    }

    override fun intersects(ray: Ray): Boolean {
        val (rectCX: Float, rectCY: Float) = _center
        val (rectOR: Float, rectOI: Float) = _orientation
        val halfSideLength: Float = _sideLength * 0.5f
        val aabbMinX: Float = rectCX - halfSideLength
        val aabbMinY: Float = rectCY - halfSideLength
        val aabbMaxX: Float = rectCX + halfSideLength
        val aabbMaxY: Float = rectCY + halfSideLength

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
        val halfSideLength: Float = _sideLength * 0.5f
        val (pX: Float, pY: Float) = point
        val cpDiffX: Float = pX - cX
        val cpDiffY: Float = pY - cY
        val p1X: Float = rotR * cpDiffX + rotI * cpDiffY
        val p1Y: Float = rotR * cpDiffY - rotI * cpDiffX

        return (p1X.absoluteValue <= halfSideLength) and (p1Y.absoluteValue <= halfSideLength)
    }

    override fun pointIterator(): Vector2FIterator = PointIterator(this, index = 0)

    /**
     * Returns a copy of this instance with specified properties changed.
     *
     * @param orientation the value is expected to be [normalized][ComplexF.normalized].
     * @throws IllegalArgumentException when [sideLength] is less than zero.
     */
    override fun copy(center: Vector2F, orientation: ComplexF, sideLength: Float) =
        MutableSquare(center, orientation, sideLength)

    override fun equals(other: Any?): Boolean = other is Square &&
            _center == other.center &&
            _orientation == other.orientation &&
            _sideLength == other.sideLength

    /** Indicates whether the other [MutableSquare] is equal to this one. **/
    fun equals(other: MutableSquare): Boolean =
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
        StringBuilder("Square(center=").append(_center)
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
        ): MutableSquare {
            val (cX: Float, cY: Float) = center
            val (oR: Float, oI: Float) = orientation
            val halfSideLength: Float = sideLength * 0.5f
            val addendA: Float = halfSideLength * (oR + oI)
            val addendB: Float = halfSideLength * (oR - oI)

            return MutableSquare(
                center,
                orientation,
                sideLength,
                pointA = Vector2F(cX + addendB, cY + addendA),
                pointB = Vector2F(cX - addendA, cY + addendB),
                pointC = Vector2F(cX - addendB, cY - addendA),
                pointD = Vector2F(cX + addendA, cY - addendB)
            )
        }
    }

    private class PointIterator(
        private val square: MutableSquare,
        private var index: Int
    ) : Vector2FIterator() {
        override fun hasNext(): Boolean = index < 4

        override fun nextVector2F(): Vector2F = when (index++) {
            0 -> square._pointA
            1 -> square._pointB
            2 -> square._pointC
            3 -> square._pointD
            else -> throw NoSuchElementException("${index - 1}")
        }
    }
}