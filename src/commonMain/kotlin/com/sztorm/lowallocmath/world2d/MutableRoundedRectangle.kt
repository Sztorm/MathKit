package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.AngleF
import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F
import com.sztorm.lowallocmath.Vector2FIterator
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.sqrt
import kotlin.math.withSign

class MutableRoundedRectangle : RoundedRectangle, MutableTransformable {
    private var _center: Vector2F
    private var _rotation: ComplexF
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
        center: Vector2F, rotation: ComplexF, width: Float, height: Float, cornerRadius: Float
    ) {
        val (cX: Float, cY: Float) = center
        val (rotR: Float, rotI: Float) = rotation
        val halfWidth: Float = width * 0.5f
        val halfHeight: Float = height * 0.5f
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
        _center = center
        _rotation = rotation
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
        rotation: ComplexF,
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
        _rotation = rotation
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

    override val rotation: ComplexF
        get() = _rotation

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
        _rotation,
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
            _rotation,
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

    override fun rotatedBy(angle: AngleF) = MutableRoundedRectangle(
        _center, _rotation * ComplexF.fromAngle(angle), _width, _height, _cornerRadius
    )

    override fun rotatedBy(rotation: ComplexF) = MutableRoundedRectangle(
        _center, _rotation * rotation, _width, _height, _cornerRadius
    )

    override fun rotatedTo(angle: AngleF) =
        MutableRoundedRectangle(_center, ComplexF.fromAngle(angle), _width, _height, _cornerRadius)

    override fun rotatedTo(rotation: ComplexF) =
        MutableRoundedRectangle(_center, rotation, _width, _height, _cornerRadius)

    override fun rotatedAroundPointBy(point: Vector2F, angle: AngleF): MutableRoundedRectangle =
        rotatedAroundPointBy(point, ComplexF.fromAngle(angle))

    override fun rotatedAroundPointBy(
        point: Vector2F, rotation: ComplexF
    ): MutableRoundedRectangle = TODO()

    override fun rotatedAroundPointTo(point: Vector2F, angle: AngleF): MutableRoundedRectangle =
        rotatedAroundPointTo(point, ComplexF.fromAngle(angle))

    override fun rotatedAroundPointTo(
        point: Vector2F, rotation: ComplexF
    ): MutableRoundedRectangle = TODO()

    override fun rotateBy(angle: AngleF) = rotateTo(_rotation * ComplexF.fromAngle(angle))

    override fun rotateBy(rotation: ComplexF) = rotateTo(_rotation * rotation)

    override fun rotateTo(angle: AngleF) = rotateTo(ComplexF.fromAngle(angle))

    override fun rotateTo(rotation: ComplexF) {
        val (cX: Float, cY: Float) = _center
        val (rotR: Float, rotI: Float) = rotation
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
        _rotation = rotation
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

    override fun rotateAroundPointBy(point: Vector2F, angle: AngleF) =
        rotateAroundPointBy(point, ComplexF.fromAngle(angle))

    override fun rotateAroundPointBy(point: Vector2F, rotation: ComplexF) =
        TODO()

    override fun rotateAroundPointTo(point: Vector2F, angle: AngleF) =
        rotateAroundPointTo(point, ComplexF.fromAngle(angle))

    override fun rotateAroundPointTo(point: Vector2F, rotation: ComplexF) =
        TODO()

    override fun scaledBy(factor: Float) = MutableRoundedRectangle(
        _center,
        _rotation,
        _width * factor,
        _height * factor,
        _cornerRadius * factor
    )

    override fun scaleBy(factor: Float) {
        val (cX: Float, cY: Float) = _center
        val (rotR: Float, rotI: Float) = _rotation
        val width: Float = _width * factor
        val height: Float = _height * factor
        val halfWidth: Float = width * 0.5f
        val halfHeight: Float = height * 0.5f
        val cornerRadius: Float = _cornerRadius * factor
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

    override fun transformedBy(offset: Vector2F, angle: AngleF) = MutableRoundedRectangle(
        _center + offset,
        _rotation * ComplexF.fromAngle(angle),
        _width,
        _height,
        _cornerRadius
    )

    override fun transformedBy(offset: Vector2F, rotation: ComplexF) = MutableRoundedRectangle(
        _center + offset,
        _rotation * rotation,
        _width,
        _height,
        _cornerRadius
    )

    override fun transformedBy(offset: Vector2F, angle: AngleF, factor: Float) =
        MutableRoundedRectangle(
            _center + offset,
            _rotation * ComplexF.fromAngle(angle),
            _width * factor,
            _height * factor,
            _cornerRadius * factor
        )

    override fun transformedBy(offset: Vector2F, rotation: ComplexF, factor: Float) =
        MutableRoundedRectangle(
            _center + offset,
            _rotation * rotation,
            _width * factor,
            _height * factor,
            _cornerRadius * factor
        )

    override fun transformedTo(position: Vector2F, angle: AngleF) = MutableRoundedRectangle(
        position, ComplexF.fromAngle(angle), _width, _height, _cornerRadius
    )

    override fun transformedTo(position: Vector2F, rotation: ComplexF) =
        MutableRoundedRectangle(position, rotation, _width, _height, _cornerRadius)

    override fun transformBy(offset: Vector2F, angle: AngleF) =
        transformTo(_center + offset, _rotation * ComplexF.fromAngle(angle))

    override fun transformBy(offset: Vector2F, rotation: ComplexF) =
        transformTo(_center + offset, _rotation * rotation)

    override fun transformBy(offset: Vector2F, angle: AngleF, factor: Float) =
        transformBy(offset, ComplexF.fromAngle(angle), factor)

    override fun transformBy(offset: Vector2F, rotation: ComplexF, factor: Float) {
        val cX: Float = _center.x + offset.x
        val cY: Float = _center.y + offset.y
        val r0 = _rotation.real
        val i0 = _rotation.imaginary
        val r1 = rotation.real
        val i1 = rotation.imaginary
        val rotR: Float = r0 * r1 - i0 * i1
        val rotI: Float = i0 * r1 + r0 * i1
        val width: Float = _width * factor
        val height: Float = _height * factor
        val halfWidth: Float = width * 0.5f
        val halfHeight: Float = height * 0.5f
        val cornerRadius: Float = _cornerRadius * factor
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
        _rotation = ComplexF(rotR, rotI)
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

    override fun transformTo(position: Vector2F, angle: AngleF) =
        transformTo(position, ComplexF.fromAngle(angle))

    override fun transformTo(position: Vector2F, rotation: ComplexF) {
        val (cX: Float, cY: Float) = position
        val (rotR: Float, rotI: Float) = rotation
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
        _rotation = rotation
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

    override fun closestPointTo(point: Vector2F): Vector2F {
        val rotation: ComplexF = _rotation
        val center: Vector2F = _center
        val cornerRadius: Float = _cornerRadius
        val halfWidth: Float = _width * 0.5f
        val halfHeight: Float = _height * 0.5f
        val halfWidthMinusRadius: Float = halfWidth - cornerRadius
        val halfHeightMinusRadius: Float = halfHeight - cornerRadius
        val p1 = ComplexF.conjugate(rotation) *
                ComplexF(point.x - center.x, point.y - center.y)
        val p1X: Float = p1.real
        val p1Y: Float = p1.imaginary
        val p1XAbs: Float = abs(p1X)
        val p1YAbs: Float = abs(p1Y)
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
        val rotation: ComplexF = _rotation
        val center: Vector2F = _center
        val cornerRadius: Float = _cornerRadius
        val halfWidth: Float = _width * 0.5f
        val halfHeight: Float = _height * 0.5f
        val halfWidthMinusRadius: Float = halfWidth - cornerRadius
        val halfHeightMinusRadius: Float = halfHeight - cornerRadius
        val p1 = ComplexF.conjugate(rotation) *
                ComplexF(point.x - center.x, point.y - center.y)
        val p1X: Float = p1.real
        val p1Y: Float = p1.imaginary
        val p1XAbs: Float = abs(p1X)
        val p1YAbs: Float = abs(p1Y)
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
        center: Vector2F, rotation: ComplexF, width: Float, height: Float, cornerRadius: Float
    ) = MutableRoundedRectangle(center, rotation, width, height, cornerRadius)

    override fun equals(other: Any?): Boolean = other is RoundedRectangle &&
            _center == other.center &&
            _rotation == other.rotation &&
            _width == other.width &&
            _height == other.height &&
            _cornerRadius == other.cornerRadius

    fun equals(other: MutableRoundedRectangle): Boolean =
        _center == other._center &&
                _rotation == other._rotation &&
                _width == other._width &&
                _height == other._height &&
                _cornerRadius == other._cornerRadius

    override fun hashCode(): Int {
        val centerHash: Int = _center.hashCode()
        val rotationHash: Int = _rotation.hashCode()
        val widthHash: Int = _width.hashCode()
        val heightHash: Int = _height.hashCode()
        val cornerRadiusHash: Int = _cornerRadius.hashCode()

        return centerHash * 923521 +
                rotationHash * 29791 +
                widthHash * 961 +
                heightHash * 31 +
                cornerRadiusHash
    }

    override fun toString() =
        StringBuilder("RoundedRectangle(center=").append(_center)
            .append(", rotation=").append(_rotation)
            .append(", width=").append(_width)
            .append(", height=").append(_height)
            .append(", cornerRadius=").append(_cornerRadius).append(")")
            .toString()

    override operator fun component1(): Vector2F = _center

    override operator fun component2(): ComplexF = _rotation

    override operator fun component3(): Float = _width

    override operator fun component4(): Float = _height

    override operator fun component5(): Float = _cornerRadius

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