package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.*
import kotlin.math.*

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

    override fun movedBy(offset: Vector2F) = MutableRoundedRectangle(
        _center + offset,
        _orientation,
        _width,
        _height,
        _cornerRadius,
        _pointA + offset,
        _pointB + offset,
        _pointC + offset,
        _pointD + offset,
        _pointE + offset,
        _pointF + offset,
        _pointG + offset,
        _pointH + offset,
        _cornerCenterA + offset,
        _cornerCenterB + offset,
        _cornerCenterC + offset,
        _cornerCenterD + offset,
    )

    override fun movedTo(position: Vector2F): MutableRoundedRectangle {
        val offset: Vector2F = position - _center

        return MutableRoundedRectangle(
            position,
            _orientation,
            _width,
            _height,
            _cornerRadius,
            _pointA + offset,
            _pointB + offset,
            _pointC + offset,
            _pointD + offset,
            _pointE + offset,
            _pointF + offset,
            _pointG + offset,
            _pointH + offset,
            _cornerCenterA + offset,
            _cornerCenterB + offset,
            _cornerCenterC + offset,
            _cornerCenterD + offset,
        )
    }

    override fun moveBy(offset: Vector2F) {
        _center += offset
        _pointA += offset
        _pointB += offset
        _pointC += offset
        _pointD += offset
        _pointE += offset
        _pointF += offset
        _pointG += offset
        _pointH += offset
        _cornerCenterA += offset
        _cornerCenterB += offset
        _cornerCenterC += offset
        _cornerCenterD += offset
    }

    override fun moveTo(position: Vector2F) {
        val offset: Vector2F = position - _center
        _center = position
        _pointA += offset
        _pointB += offset
        _pointC += offset
        _pointD += offset
        _pointE += offset
        _pointF += offset
        _pointG += offset
        _pointH += offset
        _cornerCenterA += offset
        _cornerCenterB += offset
        _cornerCenterC += offset
        _cornerCenterD += offset
    }

    override fun rotatedBy(rotation: AngleF) = createInternal(
        _center, _orientation * ComplexF.fromAngle(rotation), _width, _height, _cornerRadius
    )

    override fun rotatedBy(rotation: ComplexF) =
        createInternal(_center, _orientation * rotation, _width, _height, _cornerRadius)

    override fun rotatedTo(orientation: AngleF) =
        createInternal(_center, ComplexF.fromAngle(orientation), _width, _height, _cornerRadius)

    override fun rotatedTo(orientation: ComplexF) =
        createInternal(_center, orientation, _width, _height, _cornerRadius)

    override fun rotatedAroundPointBy(point: Vector2F, rotation: AngleF): MutableRoundedRectangle =
        rotatedAroundPointBy(point, ComplexF.fromAngle(rotation))

    override fun rotatedAroundPointBy(
        point: Vector2F, rotation: ComplexF
    ): MutableRoundedRectangle {
        val (pX: Float, pY: Float) = point
        val (rotR: Float, rotI: Float) = rotation
        val (cX: Float, cY: Float) = _center
        val (startRotR: Float, startRotI: Float) = _orientation
        val halfWidth: Float = _width * 0.5f
        val halfHeight: Float = _height * 0.5f
        val cornerRadius: Float = _cornerRadius
        val halfWidthMinusRadius: Float = halfWidth - cornerRadius
        val halfHeightMinusRadius: Float = halfHeight - cornerRadius
        val cpDiffX: Float = cX - pX
        val cpDiffY: Float = cY - pY
        val targetCenterX: Float = cpDiffX * rotR - cpDiffY * rotI + pX
        val targetCenterY: Float = cpDiffY * rotR + cpDiffX * rotI + pY
        val targetRotR: Float = startRotR * rotR - startRotI * rotI
        val targetRotI: Float = startRotI * rotR + startRotR * rotI
        val addendA: Float = targetRotR * halfWidthMinusRadius
        val addendB: Float = targetRotI * halfWidthMinusRadius
        val addendC: Float = targetRotR * halfHeightMinusRadius
        val addendD: Float = targetRotI * halfHeightMinusRadius
        val addendE: Float = targetRotR * halfWidth
        val addendF: Float = targetRotI * halfWidth
        val addendG: Float = targetRotR * halfHeight
        val addendH: Float = targetRotI * halfHeight
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
            center = Vector2F(targetCenterX, targetCenterY),
            orientation = ComplexF(targetRotR, targetRotI),
            _width,
            _height,
            _cornerRadius,
            pointA = Vector2F(targetCenterX + addendDiffAH, targetCenterY + addendSumBG),
            pointB = Vector2F(targetCenterX - addendSumAH, targetCenterY - addendDiffBG),
            pointC = Vector2F(targetCenterX - addendSumED, targetCenterY + addendDiffCF),
            pointD = Vector2F(targetCenterX - addendDiffED, targetCenterY - addendSumCF),
            pointE = Vector2F(targetCenterX - addendDiffAH, targetCenterY - addendSumBG),
            pointF = Vector2F(targetCenterX + addendSumAH, targetCenterY + addendDiffBG),
            pointG = Vector2F(targetCenterX + addendSumED, targetCenterY - addendDiffCF),
            pointH = Vector2F(targetCenterX + addendDiffED, targetCenterY + addendSumCF),
            cornerCenterA = Vector2F(
                targetCenterX + addendDiffAD, targetCenterY + addendSumBC
            ),
            cornerCenterB = Vector2F(
                targetCenterX - addendSumAD, targetCenterY - addendDiffBC
            ),
            cornerCenterC = Vector2F(
                targetCenterX - addendDiffAD, targetCenterY - addendSumBC
            ),
            cornerCenterD = Vector2F(
                targetCenterX + addendSumAD, targetCenterY + addendDiffBC
            ),
        )
    }

    override fun rotatedAroundPointTo(
        point: Vector2F, orientation: AngleF
    ): MutableRoundedRectangle = rotatedAroundPointTo(point, ComplexF.fromAngle(orientation))

    override fun rotatedAroundPointTo(
        point: Vector2F, orientation: ComplexF
    ): MutableRoundedRectangle {
        val (pX: Float, pY: Float) = point
        val (rotR: Float, rotI: Float) = orientation
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
            val targetRot = ComplexF(pointRotR, -pointRotI) * _orientation * orientation
            val (targetRotR: Float, targetRotI: Float) = targetRot
            val targetCenterX: Float = rotR * centerToPointDist + pX
            val targetCenterY: Float = rotI * centerToPointDist + pY
            val addendA: Float = targetRotR * halfWidthMinusRadius
            val addendB: Float = targetRotI * halfWidthMinusRadius
            val addendC: Float = targetRotR * halfHeightMinusRadius
            val addendD: Float = targetRotI * halfHeightMinusRadius
            val addendE: Float = targetRotR * halfWidth
            val addendF: Float = targetRotI * halfWidth
            val addendG: Float = targetRotR * halfHeight
            val addendH: Float = targetRotI * halfHeight
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
                center = Vector2F(targetCenterX, targetCenterY),
                orientation = targetRot,
                _width,
                _height,
                _cornerRadius,
                pointA = Vector2F(targetCenterX + addendDiffAH, targetCenterY + addendSumBG),
                pointB = Vector2F(targetCenterX - addendSumAH, targetCenterY - addendDiffBG),
                pointC = Vector2F(targetCenterX - addendSumED, targetCenterY + addendDiffCF),
                pointD = Vector2F(targetCenterX - addendDiffED, targetCenterY - addendSumCF),
                pointE = Vector2F(targetCenterX - addendDiffAH, targetCenterY - addendSumBG),
                pointF = Vector2F(targetCenterX + addendSumAH, targetCenterY + addendDiffBG),
                pointG = Vector2F(targetCenterX + addendSumED, targetCenterY - addendDiffCF),
                pointH = Vector2F(targetCenterX + addendDiffED, targetCenterY + addendSumCF),
                cornerCenterA = Vector2F(
                    targetCenterX + addendDiffAD, targetCenterY + addendSumBC
                ),
                cornerCenterB = Vector2F(
                    targetCenterX - addendSumAD, targetCenterY - addendDiffBC
                ),
                cornerCenterC = Vector2F(
                    targetCenterX - addendDiffAD, targetCenterY - addendSumBC
                ),
                cornerCenterD = Vector2F(
                    targetCenterX + addendSumAD, targetCenterY + addendDiffBC
                ),
            )
        } else {
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

            return MutableRoundedRectangle(
                _center,
                orientation,
                _width,
                _height,
                _cornerRadius,
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
                cornerCenterD = Vector2F(cX + addendSumAD, cY + addendDiffBC),
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

    override fun rotateAroundPointBy(point: Vector2F, rotation: AngleF) =
        rotateAroundPointBy(point, ComplexF.fromAngle(rotation))

    override fun rotateAroundPointBy(point: Vector2F, rotation: ComplexF) {
        val (pX: Float, pY: Float) = point
        val (rotR: Float, rotI: Float) = rotation
        val (cX: Float, cY: Float) = _center
        val (startRotR: Float, startRotI: Float) = _orientation
        val halfWidth: Float = _width * 0.5f
        val halfHeight: Float = _height * 0.5f
        val cornerRadius: Float = _cornerRadius
        val halfWidthMinusRadius: Float = halfWidth - cornerRadius
        val halfHeightMinusRadius: Float = halfHeight - cornerRadius
        val cpDiffX: Float = cX - pX
        val cpDiffY: Float = cY - pY
        val targetCenterX: Float = cpDiffX * rotR - cpDiffY * rotI + pX
        val targetCenterY: Float = cpDiffY * rotR + cpDiffX * rotI + pY
        val targetRotR: Float = startRotR * rotR - startRotI * rotI
        val targetRotI: Float = startRotI * rotR + startRotR * rotI
        val addendA: Float = targetRotR * halfWidthMinusRadius
        val addendB: Float = targetRotI * halfWidthMinusRadius
        val addendC: Float = targetRotR * halfHeightMinusRadius
        val addendD: Float = targetRotI * halfHeightMinusRadius
        val addendE: Float = targetRotR * halfWidth
        val addendF: Float = targetRotI * halfWidth
        val addendG: Float = targetRotR * halfHeight
        val addendH: Float = targetRotI * halfHeight
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
        _orientation = ComplexF(targetRotR, targetRotI)
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

    override fun rotateAroundPointTo(point: Vector2F, orientation: AngleF) =
        rotateAroundPointTo(point, ComplexF.fromAngle(orientation))

    override fun rotateAroundPointTo(point: Vector2F, orientation: ComplexF) {
        val (pX: Float, pY: Float) = point
        val (rotR: Float, rotI: Float) = orientation
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
            val targetRot = ComplexF(pointRotR, -pointRotI) * _orientation * orientation
            val (targetRotR: Float, targetRotI: Float) = targetRot
            val targetCenterX: Float = rotR * centerToPointDist + pX
            val targetCenterY: Float = rotI * centerToPointDist + pY
            val addendA: Float = targetRotR * halfWidthMinusRadius
            val addendB: Float = targetRotI * halfWidthMinusRadius
            val addendC: Float = targetRotR * halfHeightMinusRadius
            val addendD: Float = targetRotI * halfHeightMinusRadius
            val addendE: Float = targetRotR * halfWidth
            val addendF: Float = targetRotI * halfWidth
            val addendG: Float = targetRotR * halfHeight
            val addendH: Float = targetRotI * halfHeight
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
            _orientation = targetRot
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
    }

    override fun scaledBy(factor: Float): MutableRoundedRectangle {
        val absFactor: Float = factor.absoluteValue

        return createInternal(
            _center,
            _orientation * 1f.withSign(factor),
            _width * absFactor,
            _height * absFactor,
            _cornerRadius * absFactor
        )
    }

    override fun dilatedBy(point: Vector2F, factor: Float): MutableRoundedRectangle {
        val f: Float = 1f - factor
        val cX: Float = _center.x * factor + point.x * f
        val cY: Float = _center.y * factor + point.y * f
        val (rotR: Float, rotI: Float) = _orientation * 1f.withSign(factor)
        val absFactor: Float = factor.absoluteValue
        val width: Float = _width * absFactor
        val height: Float = _height * absFactor
        val halfWidth: Float = width * 0.5f
        val halfHeight: Float = height * 0.5f
        val cornerRadius: Float = _cornerRadius * absFactor
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

        return MutableRoundedRectangle(
            center = Vector2F(cX, cY),
            orientation = ComplexF(rotR, rotI),
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

    override fun scaleBy(factor: Float) {
        val (cX: Float, cY: Float) = _center
        val (rotR: Float, rotI: Float) = _orientation * 1f.withSign(factor)
        val absFactor: Float = factor.absoluteValue
        val width: Float = _width * absFactor
        val height: Float = _height * absFactor
        val halfWidth: Float = width * 0.5f
        val halfHeight: Float = height * 0.5f
        val cornerRadius: Float = _cornerRadius * absFactor
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
        _orientation = ComplexF(rotR, rotI)
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
        val f: Float = 1f - factor
        val cX: Float = _center.x * factor + point.x * f
        val cY: Float = _center.y * factor + point.y * f
        val (rotR: Float, rotI: Float) = _orientation * 1f.withSign(factor)
        val absFactor: Float = factor.absoluteValue
        val width: Float = _width * absFactor
        val height: Float = _height * absFactor
        val halfWidth: Float = width * 0.5f
        val halfHeight: Float = height * 0.5f
        val cornerRadius: Float = _cornerRadius * absFactor
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
        _center = Vector2F(cX, cY)
        _orientation = ComplexF(rotR, rotI)
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

    override fun transformedBy(offset: Vector2F, rotation: AngleF) = createInternal(
        _center + offset,
        _orientation * ComplexF.fromAngle(rotation),
        _width,
        _height,
        _cornerRadius
    )

    override fun transformedBy(offset: Vector2F, rotation: ComplexF) = createInternal(
        _center + offset,
        _orientation * rotation,
        _width,
        _height,
        _cornerRadius
    )

    override fun transformedBy(
        offset: Vector2F, rotation: AngleF, factor: Float
    ): MutableRoundedRectangle {
        val absFactor: Float = factor.absoluteValue

        return createInternal(
            _center + offset,
            _orientation * ComplexF.fromAngle(rotation) * 1f.withSign(factor),
            _width * absFactor,
            _height * absFactor,
            _cornerRadius * absFactor
        )
    }

    override fun transformedBy(
        offset: Vector2F, rotation: ComplexF, factor: Float
    ): MutableRoundedRectangle {
        val absFactor: Float = factor.absoluteValue

        return createInternal(
            _center + offset,
            _orientation * rotation * 1f.withSign(factor),
            _width * absFactor,
            _height * absFactor,
            _cornerRadius * absFactor
        )
    }

    override fun transformedTo(position: Vector2F, orientation: AngleF) = createInternal(
        position, ComplexF.fromAngle(orientation), _width, _height, _cornerRadius
    )

    override fun transformedTo(position: Vector2F, orientation: ComplexF) =
        createInternal(position, orientation, _width, _height, _cornerRadius)

    override fun transformBy(offset: Vector2F, rotation: AngleF) =
        transformTo(_center + offset, _orientation * ComplexF.fromAngle(rotation))

    override fun transformBy(offset: Vector2F, rotation: ComplexF) =
        transformTo(_center + offset, _orientation * rotation)

    override fun transformBy(offset: Vector2F, rotation: AngleF, factor: Float) =
        transformBy(offset, ComplexF.fromAngle(rotation), factor)

    override fun transformBy(offset: Vector2F, rotation: ComplexF, factor: Float) {
        val cX: Float = _center.x + offset.x
        val cY: Float = _center.y + offset.y
        val r0 = _orientation.real
        val i0 = _orientation.imaginary
        val r1 = rotation.real
        val i1 = rotation.imaginary
        val absFactor: Float = factor.absoluteValue
        val factorSign: Float = 1f.withSign(factor)
        val rotR: Float = (r0 * r1 - i0 * i1) * factorSign
        val rotI: Float = (i0 * r1 + r0 * i1) * factorSign
        val width: Float = _width * absFactor
        val height: Float = _height * absFactor
        val halfWidth: Float = width * 0.5f
        val halfHeight: Float = height * 0.5f
        val cornerRadius: Float = _cornerRadius * absFactor
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
        _center = Vector2F(cX, cY)
        _orientation = ComplexF(rotR, rotI)
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

    override fun transformTo(position: Vector2F, orientation: AngleF) =
        transformTo(position, ComplexF.fromAngle(orientation))

    override fun transformTo(position: Vector2F, orientation: ComplexF) {
        val (cX: Float, cY: Float) = position
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

    override fun interpolated(to: RoundedRectangle, by: Float) = createInternal(
        center = Vector2F.lerp(_center, to.center, by),
        orientation = ComplexF.slerp(_orientation, to.orientation, by),
        width = Float.lerp(_width, to.width, by),
        height = Float.lerp(_height, to.height, by),
        cornerRadius = Float.lerp(_cornerRadius, to.cornerRadius, by)
    )

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

                center + (rotation * ComplexF(
                    (cornerCenterX + dx * t),
                    (cornerCenterY + dy * t)
                )).toVector2F()
            }

            p1XAbs > halfWidth -> center + (rotation * ComplexF(
                halfWidth.withSign(p1X),
                p1Y
            )).toVector2F()

            p1YAbs > halfHeight -> center + (rotation * ComplexF(
                p1X,
                halfHeight.withSign(p1Y)
            )).toVector2F()

            else -> point
        }
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

    override fun copy(
        center: Vector2F, orientation: ComplexF, width: Float, height: Float, cornerRadius: Float
    ) = MutableRoundedRectangle(center, orientation, width, height, cornerRadius)

    override fun equals(other: Any?): Boolean = other is RoundedRectangle &&
            _center == other.center &&
            _orientation == other.orientation &&
            _width == other.width &&
            _height == other.height &&
            _cornerRadius == other.cornerRadius

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
                    "cornerRadius must be less than half of width and less than half of height."
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