@file:Suppress("NOTHING_TO_INLINE")

package com.sztorm.mathkit.euclidean2d

import com.sztorm.mathkit.*
import kotlin.math.*

/** Represents a mutable transformable rounded rectangle in a two-dimensional Euclidean space. **/
class MutableRoundedRectangle : RoundedRectangle, MutableTransformable {
    private var _center: Vector2F
    private var _orientation: ComplexF
    private var _width: Float
    private var _height: Float
    private var _cornerRadius: Float
    private var _pointA: Vector2F
    private var _pointB: Vector2F
    private var _pointC: Vector2F
    private var _pointD: Vector2F
    private var _pointE: Vector2F
    private var _pointF: Vector2F
    private var _pointG: Vector2F
    private var _pointH: Vector2F
    private var _cornerCenterA: Vector2F
    private var _cornerCenterB: Vector2F
    private var _cornerCenterC: Vector2F
    private var _cornerCenterD: Vector2F

    /**
     * Creates a new instance of [MutableRoundedRectangle].
     *
     * @param orientation the value is expected to be [normalized][ComplexF.normalized].
     * @throws IllegalArgumentException when [width] is less than zero.
     * @throws IllegalArgumentException when [height] is less than zero.
     * @throws IllegalArgumentException when [cornerRadius] is less than zero.
     * @throws IllegalArgumentException when [cornerRadius] is greater than the half the length of
     * the shorter side of this rounded rectangle.
     */
    constructor(
        center: Vector2F, orientation: ComplexF, width: Float, height: Float, cornerRadius: Float
    ) {
        throwWhenConstructorArgumentsAreIllegal(width, height, cornerRadius)
        val (cX: Float, cY: Float) = center
        val (oR: Float, oI: Float) = orientation
        val halfWidth: Float = width * 0.5f
        val halfHeight: Float = height * 0.5f
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
        val addendSumBC: Float = addendB + addendC
        val addendSumAD: Float = addendA + addendD
        val addendDiffAH: Float = addendA - addendH
        val addendDiffBG: Float = addendB - addendG
        val addendDiffCF: Float = addendC - addendF
        val addendDiffED: Float = addendE - addendD
        val addendDiffBC: Float = addendB - addendC
        val addendDiffAD: Float = addendA - addendD
        _center = center
        _orientation = orientation
        _width = width
        _height = height
        _cornerRadius = cornerRadius
        _pointA = Vector2F(cX + addendDiffAH, cY + addendSumBG)
        _pointB = Vector2F(cX - addendSumAH, cY - addendDiffBG)
        _pointC = Vector2F(cX - addendSumED, cY + addendDiffCF)
        _pointD = Vector2F(cX - addendDiffED, cY - addendSumCF)
        _pointE = Vector2F(cX - addendDiffAH, cY - addendSumBG)
        _pointF = Vector2F(cX + addendSumAH, cY + addendDiffBG)
        _pointG = Vector2F(cX + addendSumED, cY - addendDiffCF)
        _pointH = Vector2F(cX + addendDiffED, cY + addendSumCF)
        _cornerCenterA = Vector2F(cX + addendDiffAD, cY + addendSumBC)
        _cornerCenterB = Vector2F(cX - addendSumAD, cY - addendDiffBC)
        _cornerCenterC = Vector2F(cX - addendDiffAD, cY - addendSumBC)
        _cornerCenterD = Vector2F(cX + addendSumAD, cY + addendDiffBC)
    }

    private constructor(
        center: Vector2F,
        orientation: ComplexF,
        width: Float,
        height: Float,
        cornerRadius: Float,
        pointA: Vector2F,
        pointB: Vector2F,
        pointC: Vector2F,
        pointD: Vector2F,
        pointE: Vector2F,
        pointF: Vector2F,
        pointG: Vector2F,
        pointH: Vector2F,
        cornerCenterA: Vector2F,
        cornerCenterB: Vector2F,
        cornerCenterC: Vector2F,
        cornerCenterD: Vector2F
    ) {
        _center = center
        _orientation = orientation
        _width = width
        _height = height
        _cornerRadius = cornerRadius
        _pointA = pointA
        _pointB = pointB
        _pointC = pointC
        _pointD = pointD
        _pointE = pointE
        _pointF = pointF
        _pointG = pointG
        _pointH = pointH
        _cornerCenterA = cornerCenterA
        _cornerCenterB = cornerCenterB
        _cornerCenterC = cornerCenterC
        _cornerCenterD = cornerCenterD
    }

    override val center: Vector2F
        get() = _center

    override val orientation: ComplexF
        get() = _orientation

    override val width: Float
        get() = _width

    override val height: Float
        get() = _height

    override val cornerRadius: Float
        get() = _cornerRadius

    override val pointA: Vector2F
        get() = _pointA

    override val pointB: Vector2F
        get() = _pointB

    override val pointC: Vector2F
        get() = _pointC

    override val pointD: Vector2F
        get() = _pointD

    override val pointE: Vector2F
        get() = _pointE

    override val pointF: Vector2F
        get() = _pointF

    override val pointG: Vector2F
        get() = _pointG

    override val pointH: Vector2F
        get() = _pointH

    override val cornerCenterA: Vector2F
        get() = _cornerCenterA

    override val cornerCenterB: Vector2F
        get() = _cornerCenterB

    override val cornerCenterC: Vector2F
        get() = _cornerCenterC

    override val cornerCenterD: Vector2F
        get() = _cornerCenterD

    override val area: Float
        get() {
            val radius: Float = _cornerRadius
            val squaredRadius: Float = radius * radius

            return PI.toFloat() * squaredRadius + _width * _height - 4f * squaredRadius
        }

    override val perimeter: Float
        get() {
            val radius: Float = _cornerRadius

            return (2.0 * PI).toFloat() * radius + 2f * (_width + _height - 4f * radius)
        }

    override val position: Vector2F
        get() = _center

    override fun movedBy(displacement: Vector2F): MutableRoundedRectangle = createInternal(
        center = _center + displacement,
        _orientation,
        _width,
        _height,
        _cornerRadius
    )

    override fun movedTo(position: Vector2F): MutableRoundedRectangle = createInternal(
        center = position,
        _orientation,
        _width,
        _height,
        _cornerRadius
    )

    override fun moveBy(displacement: Vector2F) {
        _center += displacement
        _pointA += displacement
        _pointB += displacement
        _pointC += displacement
        _pointD += displacement
        _pointE += displacement
        _pointF += displacement
        _pointG += displacement
        _pointH += displacement
        _cornerCenterA += displacement
        _cornerCenterB += displacement
        _cornerCenterC += displacement
        _cornerCenterD += displacement
    }

    override fun moveTo(position: Vector2F) {
        val displacement: Vector2F = position - _center
        _center = position
        _pointA += displacement
        _pointB += displacement
        _pointC += displacement
        _pointD += displacement
        _pointE += displacement
        _pointF += displacement
        _pointG += displacement
        _pointH += displacement
        _cornerCenterA += displacement
        _cornerCenterB += displacement
        _cornerCenterC += displacement
        _cornerCenterD += displacement
    }

    private inline fun rotatedByImpl(rotation: ComplexF): MutableRoundedRectangle = createInternal(
        _center,
        orientation = (_orientation * rotation).normalizedOrElse(ComplexF.ONE),
        _width,
        _height,
        _cornerRadius
    )

    override fun rotatedBy(rotation: AngleF): MutableRoundedRectangle =
        rotatedByImpl(ComplexF.fromAngle(rotation))

    override fun rotatedBy(rotation: ComplexF): MutableRoundedRectangle = rotatedByImpl(rotation)

    private inline fun rotatedToImpl(
        orientation: ComplexF
    ): MutableRoundedRectangle = createInternal(
        _center,
        orientation = orientation.normalizedOrElse(ComplexF.ONE),
        _width,
        _height,
        _cornerRadius
    )

    override fun rotatedTo(orientation: AngleF): MutableRoundedRectangle =
        rotatedToImpl(ComplexF.fromAngle(orientation))

    override fun rotatedTo(orientation: ComplexF): MutableRoundedRectangle =
        rotatedToImpl(orientation)

    private inline fun rotatedAroundPointByImpl(
        point: Vector2F, rotation: ComplexF
    ): MutableRoundedRectangle {
        val (pX: Float, pY: Float) = point
        val (rotR: Float, rotI: Float) = rotation
        val (cX: Float, cY: Float) = _center
        val (oR: Float, oI: Float) = _orientation
        val cpDiffX: Float = cX - pX
        val cpDiffY: Float = cY - pY
        val centerX: Float = cpDiffX * rotR - cpDiffY * rotI + pX
        val centerY: Float = cpDiffY * rotR + cpDiffX * rotI + pY
        val orientationR: Float = oR * rotR - oI * rotI
        val orientationI: Float = oI * rotR + oR * rotI

        return createInternal(
            center = Vector2F(centerX, centerY),
            orientation = ComplexF(orientationR, orientationI).normalizedOrElse(ComplexF.ONE),
            _width,
            _height,
            _cornerRadius
        )
    }

    override fun rotatedAroundPointBy(point: Vector2F, rotation: AngleF): MutableRoundedRectangle =
        rotatedAroundPointByImpl(point, ComplexF.fromAngle(rotation))

    override fun rotatedAroundPointBy(
        point: Vector2F, rotation: ComplexF
    ): MutableRoundedRectangle = rotatedAroundPointByImpl(point, rotation)

    private inline fun rotatedAroundPointToImpl(
        point: Vector2F, orientation: ComplexF
    ): MutableRoundedRectangle {
        val (pX: Float, pY: Float) = point
        val (oR: Float, oI: Float) = orientation
        val (cX: Float, cY: Float) = _center
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
                _width,
                _height,
                _cornerRadius
            )
        } else createInternal(
            _center,
            orientation = orientation.normalizedOrElse(ComplexF.ONE),
            _width,
            _height,
            _cornerRadius
        )
    }

    override fun rotatedAroundPointTo(
        point: Vector2F, orientation: AngleF
    ): MutableRoundedRectangle = rotatedAroundPointToImpl(point, ComplexF.fromAngle(orientation))

    override fun rotatedAroundPointTo(
        point: Vector2F, orientation: ComplexF
    ): MutableRoundedRectangle = rotatedAroundPointToImpl(point, orientation)

    private inline fun rotateToImpl(orientation: ComplexF) {
        val (cX: Float, cY: Float) = _center
        val (rotR: Float, rotI: Float) = orientation
        val halfWidth: Float = _width * 0.5f
        val halfHeight: Float = _height * 0.5f
        val cornerRadius: Float = _cornerRadius
        val halfWidthMinusRadius: Float = halfWidth - cornerRadius
        val halfHeightMinusRadius: Float = halfHeight - cornerRadius
        val addendA: Float = rotR * halfWidthMinusRadius
        val addendB: Float = rotI * halfWidthMinusRadius
        val addendC: Float = rotR * halfHeightMinusRadius
        val addendD: Float = rotI * halfHeightMinusRadius
        val addendE: Float = rotR * halfWidth
        val addendF: Float = rotI * halfWidth
        val addendG: Float = rotR * halfHeight
        val addendH: Float = rotI * halfHeight
        val addendSumAH: Float = addendA + addendH
        val addendSumBG: Float = addendB + addendG
        val addendSumCF: Float = addendC + addendF
        val addendSumED: Float = addendE + addendD
        val addendSumBC: Float = addendB + addendC
        val addendSumAD: Float = addendA + addendD
        val addendDiffAH: Float = addendA - addendH
        val addendDiffBG: Float = addendB - addendG
        val addendDiffCF: Float = addendC - addendF
        val addendDiffED: Float = addendE - addendD
        val addendDiffBC: Float = addendB - addendC
        val addendDiffAD: Float = addendA - addendD
        _orientation = orientation
        _pointA = Vector2F(cX + addendDiffAH, cY + addendSumBG)
        _pointB = Vector2F(cX - addendSumAH, cY - addendDiffBG)
        _pointC = Vector2F(cX - addendSumED, cY + addendDiffCF)
        _pointD = Vector2F(cX - addendDiffED, cY - addendSumCF)
        _pointE = Vector2F(cX - addendDiffAH, cY - addendSumBG)
        _pointF = Vector2F(cX + addendSumAH, cY + addendDiffBG)
        _pointG = Vector2F(cX + addendSumED, cY - addendDiffCF)
        _pointH = Vector2F(cX + addendDiffED, cY + addendSumCF)
        _cornerCenterA = Vector2F(cX + addendDiffAD, cY + addendSumBC)
        _cornerCenterB = Vector2F(cX - addendSumAD, cY - addendDiffBC)
        _cornerCenterC = Vector2F(cX - addendDiffAD, cY - addendSumBC)
        _cornerCenterD = Vector2F(cX + addendSumAD, cY + addendDiffBC)
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
        val (oR: Float, oI: Float) = _orientation
        val halfWidth: Float = _width * 0.5f
        val halfHeight: Float = _height * 0.5f
        val cornerRadius: Float = _cornerRadius
        val halfWidthMinusRadius: Float = halfWidth - cornerRadius
        val halfHeightMinusRadius: Float = halfHeight - cornerRadius
        val cpDiffX: Float = cX - pX
        val cpDiffY: Float = cY - pY
        val targetCenterX: Float = cpDiffX * rotR - cpDiffY * rotI + pX
        val targetCenterY: Float = cpDiffY * rotR + cpDiffX * rotI + pY
        val targetOR: Float = oR * rotR - oI * rotI
        val targetOI: Float = oI * rotR + oR * rotI
        val addendA: Float = targetOR * halfWidthMinusRadius
        val addendB: Float = targetOI * halfWidthMinusRadius
        val addendC: Float = targetOR * halfHeightMinusRadius
        val addendD: Float = targetOI * halfHeightMinusRadius
        val addendE: Float = targetOR * halfWidth
        val addendF: Float = targetOI * halfWidth
        val addendG: Float = targetOR * halfHeight
        val addendH: Float = targetOI * halfHeight
        val addendSumAH: Float = addendA + addendH
        val addendSumBG: Float = addendB + addendG
        val addendSumCF: Float = addendC + addendF
        val addendSumED: Float = addendE + addendD
        val addendSumBC: Float = addendB + addendC
        val addendSumAD: Float = addendA + addendD
        val addendDiffAH: Float = addendA - addendH
        val addendDiffBG: Float = addendB - addendG
        val addendDiffCF: Float = addendC - addendF
        val addendDiffED: Float = addendE - addendD
        val addendDiffBC: Float = addendB - addendC
        val addendDiffAD: Float = addendA - addendD
        _center = Vector2F(targetCenterX, targetCenterY)
        _orientation = ComplexF(targetOR, targetOI)
        _pointA = Vector2F(targetCenterX + addendDiffAH, targetCenterY + addendSumBG)
        _pointB = Vector2F(targetCenterX - addendSumAH, targetCenterY - addendDiffBG)
        _pointC = Vector2F(targetCenterX - addendSumED, targetCenterY + addendDiffCF)
        _pointD = Vector2F(targetCenterX - addendDiffED, targetCenterY - addendSumCF)
        _pointE = Vector2F(targetCenterX - addendDiffAH, targetCenterY - addendSumBG)
        _pointF = Vector2F(targetCenterX + addendSumAH, targetCenterY + addendDiffBG)
        _pointG = Vector2F(targetCenterX + addendSumED, targetCenterY - addendDiffCF)
        _pointH = Vector2F(targetCenterX + addendDiffED, targetCenterY + addendSumCF)
        _cornerCenterA = Vector2F(targetCenterX + addendDiffAD, targetCenterY + addendSumBC)
        _cornerCenterB = Vector2F(targetCenterX - addendSumAD, targetCenterY - addendDiffBC)
        _cornerCenterC = Vector2F(targetCenterX - addendDiffAD, targetCenterY - addendSumBC)
        _cornerCenterD = Vector2F(targetCenterX + addendSumAD, targetCenterY + addendDiffBC)
    }

    override fun rotateAroundPointBy(point: Vector2F, rotation: AngleF) =
        rotateAroundPointByImpl(point, ComplexF.fromAngle(rotation))

    override fun rotateAroundPointBy(point: Vector2F, rotation: ComplexF) =
        rotateAroundPointByImpl(point, rotation)

    private inline fun rotateAroundPointToImpl(point: Vector2F, orientation: ComplexF) {
        val (pX: Float, pY: Float) = point
        val (oR: Float, oI: Float) = orientation
        val (cX: Float, cY: Float) = _center
        val halfWidth: Float = _width * 0.5f
        val halfHeight: Float = _height * 0.5f
        val cornerRadius: Float = _cornerRadius
        val halfWidthMinusRadius: Float = halfWidth - cornerRadius
        val halfHeightMinusRadius: Float = halfHeight - cornerRadius
        val cpDiffX: Float = cX - pX
        val cpDiffY: Float = cY - pY
        val centerToPointDist: Float = sqrt(cpDiffX * cpDiffX + cpDiffY * cpDiffY)

        if (centerToPointDist > 0.00001f) {
            val pointRotR: Float = cpDiffX / centerToPointDist
            val pointRotI: Float = cpDiffY / centerToPointDist
            val targetOrientation = ComplexF(pointRotR, -pointRotI) * _orientation * orientation
            val (targetOR: Float, targetOI: Float) = targetOrientation
            val targetCenterX: Float = oR * centerToPointDist + pX
            val targetCenterY: Float = oI * centerToPointDist + pY
            val addendA: Float = targetOR * halfWidthMinusRadius
            val addendB: Float = targetOI * halfWidthMinusRadius
            val addendC: Float = targetOR * halfHeightMinusRadius
            val addendD: Float = targetOI * halfHeightMinusRadius
            val addendE: Float = targetOR * halfWidth
            val addendF: Float = targetOI * halfWidth
            val addendG: Float = targetOR * halfHeight
            val addendH: Float = targetOI * halfHeight
            val addendSumAH: Float = addendA + addendH
            val addendSumBG: Float = addendB + addendG
            val addendSumCF: Float = addendC + addendF
            val addendSumED: Float = addendE + addendD
            val addendSumBC: Float = addendB + addendC
            val addendSumAD: Float = addendA + addendD
            val addendDiffAH: Float = addendA - addendH
            val addendDiffBG: Float = addendB - addendG
            val addendDiffCF: Float = addendC - addendF
            val addendDiffED: Float = addendE - addendD
            val addendDiffBC: Float = addendB - addendC
            val addendDiffAD: Float = addendA - addendD
            _center = Vector2F(targetCenterX, targetCenterY)
            _orientation = targetOrientation
            _pointA = Vector2F(targetCenterX + addendDiffAH, targetCenterY + addendSumBG)
            _pointB = Vector2F(targetCenterX - addendSumAH, targetCenterY - addendDiffBG)
            _pointC = Vector2F(targetCenterX - addendSumED, targetCenterY + addendDiffCF)
            _pointD = Vector2F(targetCenterX - addendDiffED, targetCenterY - addendSumCF)
            _pointE = Vector2F(targetCenterX - addendDiffAH, targetCenterY - addendSumBG)
            _pointF = Vector2F(targetCenterX + addendSumAH, targetCenterY + addendDiffBG)
            _pointG = Vector2F(targetCenterX + addendSumED, targetCenterY - addendDiffCF)
            _pointH = Vector2F(targetCenterX + addendDiffED, targetCenterY + addendSumCF)
            _cornerCenterA = Vector2F(
                targetCenterX + addendDiffAD, targetCenterY + addendSumBC
            )
            _cornerCenterB = Vector2F(
                targetCenterX - addendSumAD, targetCenterY - addendDiffBC
            )
            _cornerCenterC = Vector2F(
                targetCenterX - addendDiffAD, targetCenterY - addendSumBC
            )
            _cornerCenterD = Vector2F(
                targetCenterX + addendSumAD, targetCenterY + addendDiffBC
            )
        } else {
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
            val addendSumBC: Float = addendB + addendC
            val addendSumAD: Float = addendA + addendD
            val addendDiffAH: Float = addendA - addendH
            val addendDiffBG: Float = addendB - addendG
            val addendDiffCF: Float = addendC - addendF
            val addendDiffED: Float = addendE - addendD
            val addendDiffBC: Float = addendB - addendC
            val addendDiffAD: Float = addendA - addendD
            _orientation = orientation
            _pointA = Vector2F(cX + addendDiffAH, cY + addendSumBG)
            _pointB = Vector2F(cX - addendSumAH, cY - addendDiffBG)
            _pointC = Vector2F(cX - addendSumED, cY + addendDiffCF)
            _pointD = Vector2F(cX - addendDiffED, cY - addendSumCF)
            _pointE = Vector2F(cX - addendDiffAH, cY - addendSumBG)
            _pointF = Vector2F(cX + addendSumAH, cY + addendDiffBG)
            _pointG = Vector2F(cX + addendSumED, cY - addendDiffCF)
            _pointH = Vector2F(cX + addendDiffED, cY + addendSumCF)
            _cornerCenterA = Vector2F(cX + addendDiffAD, cY + addendSumBC)
            _cornerCenterB = Vector2F(cX - addendSumAD, cY - addendDiffBC)
            _cornerCenterC = Vector2F(cX - addendDiffAD, cY - addendSumBC)
            _cornerCenterD = Vector2F(cX + addendSumAD, cY + addendDiffBC)
        }
    }

    override fun rotateAroundPointTo(point: Vector2F, orientation: AngleF) =
        rotateAroundPointToImpl(point, ComplexF.fromAngle(orientation))

    override fun rotateAroundPointTo(point: Vector2F, orientation: ComplexF) =
        rotateAroundPointToImpl(point, orientation)

    override fun scaledBy(factor: Float): MutableRoundedRectangle {
        val absFactor: Float = factor.absoluteValue

        return createInternal(
            _center,
            orientation = _orientation * 1f.withSign(factor),
            width = _width * absFactor,
            height = _height * absFactor,
            cornerRadius = _cornerRadius * absFactor
        )
    }

    override fun dilatedBy(point: Vector2F, factor: Float): MutableRoundedRectangle {
        val (cX: Float, cY: Float) = _center
        val (pX: Float, pY: Float) = point
        val f: Float = 1f - factor
        val centerX: Float = cX * factor + pX * f
        val centerY: Float = cY * factor + pY * f
        val (orientationR: Float, orientationI: Float) = _orientation * 1f.withSign(factor)
        val absFactor: Float = factor.absoluteValue
        val width: Float = _width * absFactor
        val height: Float = _height * absFactor
        val halfWidth: Float = width * 0.5f
        val halfHeight: Float = height * 0.5f
        val cornerRadius: Float = _cornerRadius * absFactor
        val halfWidthMinusRadius: Float = halfWidth - cornerRadius
        val halfHeightMinusRadius: Float = halfHeight - cornerRadius
        val addendA: Float = orientationR * halfWidthMinusRadius
        val addendB: Float = orientationI * halfWidthMinusRadius
        val addendC: Float = orientationR * halfHeightMinusRadius
        val addendD: Float = orientationI * halfHeightMinusRadius
        val addendE: Float = orientationR * halfWidth
        val addendF: Float = orientationI * halfWidth
        val addendG: Float = orientationR * halfHeight
        val addendH: Float = orientationI * halfHeight
        val addendSumAH: Float = addendA + addendH
        val addendSumBG: Float = addendB + addendG
        val addendSumCF: Float = addendC + addendF
        val addendSumED: Float = addendE + addendD
        val addendSumBC: Float = addendB + addendC
        val addendSumAD: Float = addendA + addendD
        val addendDiffAH: Float = addendA - addendH
        val addendDiffBG: Float = addendB - addendG
        val addendDiffCF: Float = addendC - addendF
        val addendDiffED: Float = addendE - addendD
        val addendDiffBC: Float = addendB - addendC
        val addendDiffAD: Float = addendA - addendD

        return MutableRoundedRectangle(
            center = Vector2F(centerX, centerY),
            orientation = ComplexF(orientationR, orientationI),
            width,
            height,
            cornerRadius,
            pointA = Vector2F(centerX + addendDiffAH, centerY + addendSumBG),
            pointB = Vector2F(centerX - addendSumAH, centerY - addendDiffBG),
            pointC = Vector2F(centerX - addendSumED, centerY + addendDiffCF),
            pointD = Vector2F(centerX - addendDiffED, centerY - addendSumCF),
            pointE = Vector2F(centerX - addendDiffAH, centerY - addendSumBG),
            pointF = Vector2F(centerX + addendSumAH, centerY + addendDiffBG),
            pointG = Vector2F(centerX + addendSumED, centerY - addendDiffCF),
            pointH = Vector2F(centerX + addendDiffED, centerY + addendSumCF),
            cornerCenterA = Vector2F(centerX + addendDiffAD, centerY + addendSumBC),
            cornerCenterB = Vector2F(centerX - addendSumAD, centerY - addendDiffBC),
            cornerCenterC = Vector2F(centerX - addendDiffAD, centerY - addendSumBC),
            cornerCenterD = Vector2F(centerX + addendSumAD, centerY + addendDiffBC)
        )
    }

    override fun scaleBy(factor: Float) {
        val (cX: Float, cY: Float) = _center
        val (orientationR: Float, orientationI: Float) = _orientation * 1f.withSign(factor)
        val absFactor: Float = factor.absoluteValue
        val width: Float = _width * absFactor
        val height: Float = _height * absFactor
        val halfWidth: Float = width * 0.5f
        val halfHeight: Float = height * 0.5f
        val cornerRadius: Float = _cornerRadius * absFactor
        val halfWidthMinusRadius: Float = halfWidth - cornerRadius
        val halfHeightMinusRadius: Float = halfHeight - cornerRadius
        val addendA: Float = orientationR * halfWidthMinusRadius
        val addendB: Float = orientationI * halfWidthMinusRadius
        val addendC: Float = orientationR * halfHeightMinusRadius
        val addendD: Float = orientationI * halfHeightMinusRadius
        val addendE: Float = orientationR * halfWidth
        val addendF: Float = orientationI * halfWidth
        val addendG: Float = orientationR * halfHeight
        val addendH: Float = orientationI * halfHeight
        val addendSumAH: Float = addendA + addendH
        val addendSumBG: Float = addendB + addendG
        val addendSumCF: Float = addendC + addendF
        val addendSumED: Float = addendE + addendD
        val addendSumBC: Float = addendB + addendC
        val addendSumAD: Float = addendA + addendD
        val addendDiffAH: Float = addendA - addendH
        val addendDiffBG: Float = addendB - addendG
        val addendDiffCF: Float = addendC - addendF
        val addendDiffED: Float = addendE - addendD
        val addendDiffBC: Float = addendB - addendC
        val addendDiffAD: Float = addendA - addendD
        _orientation = ComplexF(orientationR, orientationI)
        _width = width
        _height = height
        _cornerRadius = cornerRadius
        _pointA = Vector2F(cX + addendDiffAH, cY + addendSumBG)
        _pointB = Vector2F(cX - addendSumAH, cY - addendDiffBG)
        _pointC = Vector2F(cX - addendSumED, cY + addendDiffCF)
        _pointD = Vector2F(cX - addendDiffED, cY - addendSumCF)
        _pointE = Vector2F(cX - addendDiffAH, cY - addendSumBG)
        _pointF = Vector2F(cX + addendSumAH, cY + addendDiffBG)
        _pointG = Vector2F(cX + addendSumED, cY - addendDiffCF)
        _pointH = Vector2F(cX + addendDiffED, cY + addendSumCF)
        _cornerCenterA = Vector2F(cX + addendDiffAD, cY + addendSumBC)
        _cornerCenterB = Vector2F(cX - addendSumAD, cY - addendDiffBC)
        _cornerCenterC = Vector2F(cX - addendDiffAD, cY - addendSumBC)
        _cornerCenterD = Vector2F(cX + addendSumAD, cY + addendDiffBC)
    }

    override fun dilateBy(point: Vector2F, factor: Float) {
        val (cX: Float, cY: Float) = _center
        val (pX: Float, pY: Float) = point
        val f: Float = 1f - factor
        val centerX: Float = cX * factor + pX * f
        val centerY: Float = cY * factor + pY * f
        val (orientationR: Float, orientationI: Float) = _orientation * 1f.withSign(factor)
        val absFactor: Float = factor.absoluteValue
        val width: Float = _width * absFactor
        val height: Float = _height * absFactor
        val halfWidth: Float = width * 0.5f
        val halfHeight: Float = height * 0.5f
        val cornerRadius: Float = _cornerRadius * absFactor
        val halfWidthMinusRadius: Float = halfWidth - cornerRadius
        val halfHeightMinusRadius: Float = halfHeight - cornerRadius
        val addendA: Float = orientationR * halfWidthMinusRadius
        val addendB: Float = orientationI * halfWidthMinusRadius
        val addendC: Float = orientationR * halfHeightMinusRadius
        val addendD: Float = orientationI * halfHeightMinusRadius
        val addendE: Float = orientationR * halfWidth
        val addendF: Float = orientationI * halfWidth
        val addendG: Float = orientationR * halfHeight
        val addendH: Float = orientationI * halfHeight
        val addendSumAH: Float = addendA + addendH
        val addendSumBG: Float = addendB + addendG
        val addendSumCF: Float = addendC + addendF
        val addendSumED: Float = addendE + addendD
        val addendSumBC: Float = addendB + addendC
        val addendSumAD: Float = addendA + addendD
        val addendDiffAH: Float = addendA - addendH
        val addendDiffBG: Float = addendB - addendG
        val addendDiffCF: Float = addendC - addendF
        val addendDiffED: Float = addendE - addendD
        val addendDiffBC: Float = addendB - addendC
        val addendDiffAD: Float = addendA - addendD
        _center = Vector2F(centerX, centerY)
        _orientation = ComplexF(orientationR, orientationI)
        _width = width
        _height = height
        _cornerRadius = cornerRadius
        _pointA = Vector2F(centerX + addendDiffAH, centerY + addendSumBG)
        _pointB = Vector2F(centerX - addendSumAH, centerY - addendDiffBG)
        _pointC = Vector2F(centerX - addendSumED, centerY + addendDiffCF)
        _pointD = Vector2F(centerX - addendDiffED, centerY - addendSumCF)
        _pointE = Vector2F(centerX - addendDiffAH, centerY - addendSumBG)
        _pointF = Vector2F(centerX + addendSumAH, centerY + addendDiffBG)
        _pointG = Vector2F(centerX + addendSumED, centerY - addendDiffCF)
        _pointH = Vector2F(centerX + addendDiffED, centerY + addendSumCF)
        _cornerCenterA = Vector2F(centerX + addendDiffAD, centerY + addendSumBC)
        _cornerCenterB = Vector2F(centerX - addendSumAD, centerY - addendDiffBC)
        _cornerCenterC = Vector2F(centerX - addendDiffAD, centerY - addendSumBC)
        _cornerCenterD = Vector2F(centerX + addendSumAD, centerY + addendDiffBC)
    }

    private inline fun transformedByImpl(
        displacement: Vector2F, rotation: ComplexF
    ): MutableRoundedRectangle = createInternal(
        center = _center + displacement,
        orientation = (_orientation * rotation).normalizedOrElse(ComplexF.ONE),
        _width,
        _height,
        _cornerRadius
    )

    override fun transformedBy(
        displacement: Vector2F, rotation: AngleF
    ): MutableRoundedRectangle = transformedByImpl(displacement, ComplexF.fromAngle(rotation))

    override fun transformedBy(
        displacement: Vector2F, rotation: ComplexF
    ): MutableRoundedRectangle = transformedByImpl(displacement, rotation)

    private inline fun transformedByImpl(
        displacement: Vector2F, rotation: ComplexF, scaleFactor: Float
    ): MutableRoundedRectangle {
        val absScaleFactor: Float = scaleFactor.absoluteValue

        return createInternal(
            center = _center + displacement,
            orientation = (_orientation * rotation)
                .normalizedOrElse(ComplexF.ONE) * 1f.withSign(scaleFactor),
            width = _width * absScaleFactor,
            height = _height * absScaleFactor,
            cornerRadius = _cornerRadius * absScaleFactor
        )
    }

    override fun transformedBy(
        displacement: Vector2F, rotation: AngleF, scaleFactor: Float
    ): MutableRoundedRectangle = transformedByImpl(
        displacement, ComplexF.fromAngle(rotation), scaleFactor
    )

    override fun transformedBy(
        displacement: Vector2F, rotation: ComplexF, scaleFactor: Float
    ): MutableRoundedRectangle = transformedByImpl(displacement, rotation, scaleFactor)

    private inline fun transformedToImpl(
        position: Vector2F, orientation: ComplexF
    ): MutableRoundedRectangle = createInternal(
        center = position,
        orientation = orientation.normalizedOrElse(ComplexF.ONE),
        _width,
        _height,
        _cornerRadius
    )

    override fun transformedTo(
        position: Vector2F, orientation: AngleF
    ): MutableRoundedRectangle = transformedToImpl(position, ComplexF.fromAngle(orientation))

    override fun transformedTo(
        position: Vector2F, orientation: ComplexF
    ): MutableRoundedRectangle = transformedToImpl(position, orientation)

    private inline fun transformToImpl(position: Vector2F, orientation: ComplexF) {
        val (cX: Float, cY: Float) = position
        val (oR: Float, oI: Float) = orientation
        val halfWidth: Float = _width * 0.5f
        val halfHeight: Float = _height * 0.5f
        val cornerRadius: Float = _cornerRadius
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
        val addendSumBC: Float = addendB + addendC
        val addendSumAD: Float = addendA + addendD
        val addendDiffAH: Float = addendA - addendH
        val addendDiffBG: Float = addendB - addendG
        val addendDiffCF: Float = addendC - addendF
        val addendDiffED: Float = addendE - addendD
        val addendDiffBC: Float = addendB - addendC
        val addendDiffAD: Float = addendA - addendD
        _center = position
        _orientation = orientation
        _pointA = Vector2F(cX + addendDiffAH, cY + addendSumBG)
        _pointB = Vector2F(cX - addendSumAH, cY - addendDiffBG)
        _pointC = Vector2F(cX - addendSumED, cY + addendDiffCF)
        _pointD = Vector2F(cX - addendDiffED, cY - addendSumCF)
        _pointE = Vector2F(cX - addendDiffAH, cY - addendSumBG)
        _pointF = Vector2F(cX + addendSumAH, cY + addendDiffBG)
        _pointG = Vector2F(cX + addendSumED, cY - addendDiffCF)
        _pointH = Vector2F(cX + addendDiffED, cY + addendSumCF)
        _cornerCenterA = Vector2F(cX + addendDiffAD, cY + addendSumBC)
        _cornerCenterB = Vector2F(cX - addendSumAD, cY - addendDiffBC)
        _cornerCenterC = Vector2F(cX - addendDiffAD, cY - addendSumBC)
        _cornerCenterD = Vector2F(cX + addendSumAD, cY + addendDiffBC)
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
        val centerX: Float = cX + dX
        val centerY: Float = cY + dY
        val (rotR: Float, rotI: Float) = rotation
        val absScaleFactor: Float = scaleFactor.absoluteValue
        val scaleFactorSign: Float = 1f.withSign(scaleFactor)
        val orientationR: Float = (oR * rotR - oI * rotI) * scaleFactorSign
        val orientationI: Float = (oI * rotR + oR * rotI) * scaleFactorSign
        val width: Float = _width * absScaleFactor
        val height: Float = _height * absScaleFactor
        val halfWidth: Float = width * 0.5f
        val halfHeight: Float = height * 0.5f
        val cornerRadius: Float = _cornerRadius * absScaleFactor
        val halfWidthMinusRadius: Float = halfWidth - cornerRadius
        val halfHeightMinusRadius: Float = halfHeight - cornerRadius
        val addendA: Float = orientationR * halfWidthMinusRadius
        val addendB: Float = orientationI * halfWidthMinusRadius
        val addendC: Float = orientationR * halfHeightMinusRadius
        val addendD: Float = orientationI * halfHeightMinusRadius
        val addendE: Float = orientationR * halfWidth
        val addendF: Float = orientationI * halfWidth
        val addendG: Float = orientationR * halfHeight
        val addendH: Float = orientationI * halfHeight
        val addendSumAH: Float = addendA + addendH
        val addendSumBG: Float = addendB + addendG
        val addendSumCF: Float = addendC + addendF
        val addendSumED: Float = addendE + addendD
        val addendSumBC: Float = addendB + addendC
        val addendSumAD: Float = addendA + addendD
        val addendDiffAH: Float = addendA - addendH
        val addendDiffBG: Float = addendB - addendG
        val addendDiffCF: Float = addendC - addendF
        val addendDiffED: Float = addendE - addendD
        val addendDiffBC: Float = addendB - addendC
        val addendDiffAD: Float = addendA - addendD
        _center = Vector2F(centerX, centerY)
        _orientation = ComplexF(orientationR, orientationI)
        _width = width
        _height = height
        _cornerRadius = cornerRadius
        _pointA = Vector2F(centerX + addendDiffAH, centerY + addendSumBG)
        _pointB = Vector2F(centerX - addendSumAH, centerY - addendDiffBG)
        _pointC = Vector2F(centerX - addendSumED, centerY + addendDiffCF)
        _pointD = Vector2F(centerX - addendDiffED, centerY - addendSumCF)
        _pointE = Vector2F(centerX - addendDiffAH, centerY - addendSumBG)
        _pointF = Vector2F(centerX + addendSumAH, centerY + addendDiffBG)
        _pointG = Vector2F(centerX + addendSumED, centerY - addendDiffCF)
        _pointH = Vector2F(centerX + addendDiffED, centerY + addendSumCF)
        _cornerCenterA = Vector2F(centerX + addendDiffAD, centerY + addendSumBC)
        _cornerCenterB = Vector2F(centerX - addendSumAD, centerY - addendDiffBC)
        _cornerCenterC = Vector2F(centerX - addendDiffAD, centerY - addendSumBC)
        _cornerCenterD = Vector2F(centerX + addendSumAD, centerY + addendDiffBC)
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
        val halfWidth: Float = _width * 0.5f
        val halfHeight: Float = _height * 0.5f
        val cornerRadius: Float = _cornerRadius
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
        val addendSumBC: Float = addendB + addendC
        val addendSumAD: Float = addendA + addendD
        val addendDiffAH: Float = addendA - addendH
        val addendDiffBG: Float = addendB - addendG
        val addendDiffCF: Float = addendC - addendF
        val addendDiffED: Float = addendE - addendD
        val addendDiffBC: Float = addendB - addendC
        val addendDiffAD: Float = addendA - addendD
        _orientation = newOrientation
        _pointA = Vector2F(cX + addendDiffAH, cY + addendSumBG)
        _pointB = Vector2F(cX - addendSumAH, cY - addendDiffBG)
        _pointC = Vector2F(cX - addendSumED, cY + addendDiffCF)
        _pointD = Vector2F(cX - addendDiffED, cY - addendSumCF)
        _pointE = Vector2F(cX - addendDiffAH, cY - addendSumBG)
        _pointF = Vector2F(cX + addendSumAH, cY + addendDiffBG)
        _pointG = Vector2F(cX + addendSumED, cY - addendDiffCF)
        _pointH = Vector2F(cX + addendDiffED, cY + addendSumCF)
        _cornerCenterA = Vector2F(cX + addendDiffAD, cY + addendSumBC)
        _cornerCenterB = Vector2F(cX - addendSumAD, cY - addendDiffBC)
        _cornerCenterC = Vector2F(cX - addendDiffAD, cY - addendSumBC)
        _cornerCenterD = Vector2F(cX + addendSumAD, cY + addendDiffBC)
    }

    private inline fun setInternal(
        center: Vector2F, orientation: ComplexF, width: Float, height: Float, cornerRadius: Float
    ) {
        val (cX: Float, cY: Float) = center
        val (oR: Float, oI: Float) = orientation
        val halfWidth: Float = width * 0.5f
        val halfHeight: Float = height * 0.5f
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
        val addendSumBC: Float = addendB + addendC
        val addendSumAD: Float = addendA + addendD
        val addendDiffAH: Float = addendA - addendH
        val addendDiffBG: Float = addendB - addendG
        val addendDiffCF: Float = addendC - addendF
        val addendDiffED: Float = addendE - addendD
        val addendDiffBC: Float = addendB - addendC
        val addendDiffAD: Float = addendA - addendD
        _center = center
        _orientation = orientation
        _width = width
        _height = height
        _cornerRadius = cornerRadius
        _pointA = Vector2F(cX + addendDiffAH, cY + addendSumBG)
        _pointB = Vector2F(cX - addendSumAH, cY - addendDiffBG)
        _pointC = Vector2F(cX - addendSumED, cY + addendDiffCF)
        _pointD = Vector2F(cX - addendDiffED, cY - addendSumCF)
        _pointE = Vector2F(cX - addendDiffAH, cY - addendSumBG)
        _pointF = Vector2F(cX + addendSumAH, cY + addendDiffBG)
        _pointG = Vector2F(cX + addendSumED, cY - addendDiffCF)
        _pointH = Vector2F(cX + addendDiffED, cY + addendSumCF)
        _cornerCenterA = Vector2F(cX + addendDiffAD, cY + addendSumBC)
        _cornerCenterB = Vector2F(cX - addendSumAD, cY - addendDiffBC)
        _cornerCenterC = Vector2F(cX - addendDiffAD, cY - addendSumBC)
        _cornerCenterD = Vector2F(cX + addendSumAD, cY + addendDiffBC)
    }

    /**
     * Sets the specified properties of this instance.
     *
     * @param orientation the value is expected to be [normalized][ComplexF.normalized].
     * @throws IllegalArgumentException when [width] is less than zero.
     * @throws IllegalArgumentException when [height] is less than zero.
     * @throws IllegalArgumentException when [cornerRadius] is less than zero.
     * @throws IllegalArgumentException when [cornerRadius] is greater than the half the length of
     * the shorter side of this rounded rectangle.
     */
    fun set(
        center: Vector2F = this.center,
        orientation: ComplexF = this.orientation,
        width: Float = this.width,
        height: Float = this.height,
        cornerRadius: Float = this.cornerRadius
    ) {
        throwWhenConstructorArgumentsAreIllegal(width, height, cornerRadius)
        setInternal(center, orientation, width, height, cornerRadius)
    }

    override fun interpolated(
        to: RoundedRectangle, by: Float
    ): MutableRoundedRectangle = createInternal(
        center = Vector2F.lerp(_center, to.center, by),
        orientation = ComplexF.slerp(_orientation, to.orientation, by)
            .normalizedOrElse(ComplexF.ONE),
        width = Float.lerp(_width, to.width, by),
        height = Float.lerp(_height, to.height, by),
        cornerRadius = Float.lerp(_cornerRadius, to.cornerRadius, by)
    )

    /**
     * Sets this rounded rectangle with the result of interpolation [from] one rounded rectangle
     * [to] another rounded rectangle [by] a factor.
     *
     * @param from the rounded rectangle from which the interpolation starts.
     * @param to the rounded rectangle at which the interpolation ends.
     * @param by the interpolation factor which is expected to be in the range of `[0, 1]`.
     */
    fun interpolate(from: RoundedRectangle, to: RoundedRectangle, by: Float) = setInternal(
        center = Vector2F.lerp(from.center, to.center, by),
        orientation = ComplexF.slerp(from.orientation, to.orientation, by),
        width = Float.lerp(from.width, to.width, by),
        height = Float.lerp(from.height, to.height, by),
        cornerRadius = Float.lerp(from.cornerRadius, to.cornerRadius, by)
    )

    override fun closestPointTo(point: Vector2F): Vector2F {
        val center: Vector2F = _center
        val rotation: ComplexF = _orientation
        val cornerRadius: Float = _cornerRadius
        val halfWidth: Float = _width * 0.5f
        val halfHeight: Float = _height * 0.5f
        val halfWidthMinusRadius: Float = halfWidth - cornerRadius
        val halfHeightMinusRadius: Float = halfHeight - cornerRadius
        val p1: ComplexF = rotation.conjugate *
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

                center + Vector2F(cornerCenterX + dx * t, cornerCenterY + dy * t) * rotation
            }

            p1XAbs > halfWidth -> center + Vector2F(halfWidth.withSign(p1X), p1Y) * rotation

            p1YAbs > halfHeight -> center + Vector2F(p1X, halfHeight.withSign(p1Y)) * rotation

            else -> point
        }
    }

    override fun intersects(ray: Ray): Boolean {
        val (rectCX: Float, rectCY: Float) = _center
        val (rectOR: Float, rectOI: Float) = _orientation
        val halfWidth: Float = _width * 0.5f
        val halfHeight: Float = _height * 0.5f
        val cornerRadius: Float = _cornerRadius
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

    override operator fun contains(point: Vector2F): Boolean {
        val (cX: Float, cY: Float) = _center
        val (rotR: Float, rotI: Float) = _orientation
        val cornerRadius: Float = _cornerRadius
        val halfWidth: Float = _width * 0.5f
        val halfHeight: Float = _height * 0.5f
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

    override fun pointIterator(): Vector2FIterator = PointIterator(this, index = 0)

    override fun cornerCenterIterator(): Vector2FIterator =
        CornerCenterIterator(this, index = 0)

    /**
     * Returns a copy of this instance with specified properties changed.
     *
     * @param orientation the value is expected to be [normalized][ComplexF.normalized].
     * @throws IllegalArgumentException when [width] is less than zero.
     * @throws IllegalArgumentException when [height] is less than zero.
     * @throws IllegalArgumentException when [cornerRadius] is less than zero.
     * @throws IllegalArgumentException when [cornerRadius] is greater than the half the length of
     * the shorter side of this rounded rectangle.
     */
    override fun copy(
        center: Vector2F, orientation: ComplexF, width: Float, height: Float, cornerRadius: Float
    ) = MutableRoundedRectangle(center, orientation, width, height, cornerRadius)

    override fun equals(other: Any?): Boolean = other is RoundedRectangle &&
        _center == other.center &&
        _orientation == other.orientation &&
        _width == other.width &&
        _height == other.height &&
        _cornerRadius == other.cornerRadius

    /** Indicates whether the other [MutableRoundedRectangle] is equal to this one. **/
    fun equals(other: MutableRoundedRectangle): Boolean =
        _center == other._center &&
            _orientation == other._orientation &&
            _width == other._width &&
            _height == other._height &&
            _cornerRadius == other._cornerRadius

    override fun hashCode(): Int {
        val centerHash: Int = _center.hashCode()
        val orientationHash: Int = _orientation.hashCode()
        val widthHash: Int = _width.hashCode()
        val heightHash: Int = _height.hashCode()
        val cornerRadiusHash: Int = _cornerRadius.hashCode()

        return centerHash * 923521 +
            orientationHash * 29791 +
            widthHash * 961 +
            heightHash * 31 +
            cornerRadiusHash
    }

    override fun toString() =
        StringBuilder("RoundedRectangle(center=").append(_center)
            .append(", orientation=").append(_orientation)
            .append(", width=").append(_width)
            .append(", height=").append(_height)
            .append(", cornerRadius=").append(_cornerRadius).append(")")
            .toString()

    override operator fun component1(): Vector2F = _center

    override operator fun component2(): ComplexF = _orientation

    override operator fun component3(): Float = _width

    override operator fun component4(): Float = _height

    override operator fun component5(): Float = _cornerRadius

    companion object {
        private inline fun throwWhenConstructorArgumentsAreIllegal(
            width: Float, height: Float, cornerRadius: Float
        ) {
            if (width < 0f) {
                throw IllegalArgumentException("width must be greater than or equal to zero.")
            }
            if (height < 0f) {
                throw IllegalArgumentException("height must be greater than or equal to zero.")
            }
            if (cornerRadius < 0f) {
                throw IllegalArgumentException(
                    "cornerRadius must be greater than or equal to zero."
                )
            }
            val halfShortestSize: Float = min(width, height) * 0.5f

            if (cornerRadius > halfShortestSize) {
                throw IllegalArgumentException(
                    "cornerRadius must be less than or equal to the half the length of the shorter side of this rounded rectangle."
                )
            }
        }

        private inline fun createInternal(
            center: Vector2F,
            orientation: ComplexF,
            width: Float,
            height: Float,
            cornerRadius: Float
        ): MutableRoundedRectangle {
            val (cX: Float, cY: Float) = center
            val (oR: Float, oI: Float) = orientation
            val halfWidth: Float = width * 0.5f
            val halfHeight: Float = height * 0.5f
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
            val addendSumBC: Float = addendB + addendC
            val addendSumAD: Float = addendA + addendD
            val addendDiffAH: Float = addendA - addendH
            val addendDiffBG: Float = addendB - addendG
            val addendDiffCF: Float = addendC - addendF
            val addendDiffED: Float = addendE - addendD
            val addendDiffBC: Float = addendB - addendC
            val addendDiffAD: Float = addendA - addendD

            return MutableRoundedRectangle(
                center,
                orientation,
                width,
                height,
                cornerRadius,
                pointA = Vector2F(cX + addendDiffAH, cY + addendSumBG),
                pointB = Vector2F(cX - addendSumAH, cY - addendDiffBG),
                pointC = Vector2F(cX - addendSumED, cY + addendDiffCF),
                pointD = Vector2F(cX - addendDiffED, cY - addendSumCF),
                pointE = Vector2F(cX - addendDiffAH, cY - addendSumBG),
                pointF = Vector2F(cX + addendSumAH, cY + addendDiffBG),
                pointG = Vector2F(cX + addendSumED, cY - addendDiffCF),
                pointH = Vector2F(cX + addendDiffED, cY + addendSumCF),
                cornerCenterA = Vector2F(cX + addendDiffAD, cY + addendSumBC),
                cornerCenterB = Vector2F(cX - addendSumAD, cY - addendDiffBC),
                cornerCenterC = Vector2F(cX - addendDiffAD, cY - addendSumBC),
                cornerCenterD = Vector2F(cX + addendSumAD, cY + addendDiffBC)
            )
        }
    }

    private class CornerCenterIterator(
        private val rectangle: MutableRoundedRectangle,
        private var index: Int
    ) : Vector2FIterator() {
        override fun hasNext(): Boolean = index < 4

        override fun nextVector2F(): Vector2F = when (index++) {
            0 -> rectangle._cornerCenterA
            1 -> rectangle._cornerCenterB
            2 -> rectangle._cornerCenterC
            3 -> rectangle._cornerCenterD
            else -> throw NoSuchElementException("${index - 1}")
        }
    }

    private class PointIterator(
        private val rectangle: MutableRoundedRectangle,
        private var index: Int
    ) : Vector2FIterator() {
        override fun hasNext(): Boolean = index < 8

        override fun nextVector2F(): Vector2F = when (index++) {
            0 -> rectangle._pointA
            1 -> rectangle._pointB
            2 -> rectangle._pointC
            3 -> rectangle._pointD
            4 -> rectangle._pointE
            5 -> rectangle._pointF
            6 -> rectangle._pointG
            7 -> rectangle._pointH
            else -> throw NoSuchElementException("${index - 1}")
        }
    }
}