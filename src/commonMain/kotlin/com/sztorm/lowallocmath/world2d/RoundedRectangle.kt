package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.AngleF
import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F
import com.sztorm.lowallocmath.Vector2FIterator
import kotlin.math.*

fun RoundedRectangle(
    center: Vector2F, orientation: ComplexF, width: Float, height: Float, cornerRadius: Float
): RoundedRectangle = MutableRoundedRectangle(center, orientation, width, height, cornerRadius)

interface RoundedRectangle : RoundedRectangleShape, Transformable {
    val center: Vector2F

    val pointA: Vector2F
        get() {
            val (cX: Float, cY: Float) = center
            val (rotR: Float, rotI: Float) = orientation
            val halfWidth: Float = width * 0.5f
            val halfHeight: Float = height * 0.5f
            val halfWidthMinusRadius: Float = halfWidth - cornerRadius
            val addendA: Float = rotR * halfWidthMinusRadius
            val addendB: Float = rotI * halfWidthMinusRadius
            val addendG: Float = rotR * halfHeight
            val addendH: Float = rotI * halfHeight
            val addendSumBG: Float = addendB + addendG
            val addendDiffAH: Float = addendA - addendH

            return Vector2F(cX + addendDiffAH, cY + addendSumBG)
        }

    val pointB: Vector2F
        get() {
            val (cX: Float, cY: Float) = center
            val (rotR: Float, rotI: Float) = orientation
            val halfWidth: Float = width * 0.5f
            val halfHeight: Float = height * 0.5f
            val halfWidthMinusRadius: Float = halfWidth - cornerRadius
            val addendA: Float = rotR * halfWidthMinusRadius
            val addendB: Float = rotI * halfWidthMinusRadius
            val addendG: Float = rotR * halfHeight
            val addendH: Float = rotI * halfHeight
            val addendSumAH: Float = addendA + addendH
            val addendDiffBG: Float = addendB - addendG

            return Vector2F(cX - addendSumAH, cY - addendDiffBG)
        }

    val pointC: Vector2F
        get() {
            val (cX: Float, cY: Float) = center
            val (rotR: Float, rotI: Float) = orientation
            val halfWidth: Float = width * 0.5f
            val halfHeight: Float = height * 0.5f
            val halfHeightMinusRadius: Float = halfHeight - cornerRadius
            val addendC: Float = rotR * halfHeightMinusRadius
            val addendD: Float = rotI * halfHeightMinusRadius
            val addendE: Float = rotR * halfWidth
            val addendF: Float = rotI * halfWidth
            val addendSumED: Float = addendE + addendD
            val addendDiffCF: Float = addendC - addendF

            return Vector2F(cX - addendSumED, cY + addendDiffCF)
        }

    val pointD: Vector2F
        get() {
            val (cX: Float, cY: Float) = center
            val (rotR: Float, rotI: Float) = orientation
            val halfWidth: Float = width * 0.5f
            val halfHeight: Float = height * 0.5f
            val halfHeightMinusRadius: Float = halfHeight - cornerRadius
            val addendC: Float = rotR * halfHeightMinusRadius
            val addendD: Float = rotI * halfHeightMinusRadius
            val addendE: Float = rotR * halfWidth
            val addendF: Float = rotI * halfWidth
            val addendSumCF: Float = addendC + addendF
            val addendDiffED: Float = addendE - addendD

            return Vector2F(cX - addendDiffED, cY - addendSumCF)
        }

    val pointE: Vector2F
        get() {
            val (cX: Float, cY: Float) = center
            val (rotR: Float, rotI: Float) = orientation
            val halfWidth: Float = width * 0.5f
            val halfHeight: Float = height * 0.5f
            val halfWidthMinusRadius: Float = halfWidth - cornerRadius
            val addendA: Float = rotR * halfWidthMinusRadius
            val addendB: Float = rotI * halfWidthMinusRadius
            val addendG: Float = rotR * halfHeight
            val addendH: Float = rotI * halfHeight
            val addendSumBG: Float = addendB + addendG
            val addendDiffAH: Float = addendA - addendH

            return Vector2F(cX - addendDiffAH, cY - addendSumBG)
        }

    val pointF: Vector2F
        get() {
            val (cX: Float, cY: Float) = center
            val (rotR: Float, rotI: Float) = orientation
            val halfWidth: Float = width * 0.5f
            val halfHeight: Float = height * 0.5f
            val halfWidthMinusRadius: Float = halfWidth - cornerRadius
            val addendA: Float = rotR * halfWidthMinusRadius
            val addendB: Float = rotI * halfWidthMinusRadius
            val addendG: Float = rotR * halfHeight
            val addendH: Float = rotI * halfHeight
            val addendSumAH: Float = addendA + addendH
            val addendDiffBG: Float = addendB - addendG

            return Vector2F(cX + addendSumAH, cY + addendDiffBG)
        }

    val pointG: Vector2F
        get() {
            val (cX: Float, cY: Float) = center
            val (rotR: Float, rotI: Float) = orientation
            val halfWidth: Float = width * 0.5f
            val halfHeight: Float = height * 0.5f
            val halfHeightMinusRadius: Float = halfHeight - cornerRadius
            val addendC: Float = rotR * halfHeightMinusRadius
            val addendD: Float = rotI * halfHeightMinusRadius
            val addendE: Float = rotR * halfWidth
            val addendF: Float = rotI * halfWidth
            val addendSumED: Float = addendE + addendD
            val addendDiffCF: Float = addendC - addendF

            return Vector2F(cX + addendSumED, cY - addendDiffCF)
        }

    val pointH: Vector2F
        get() {
            val (cX: Float, cY: Float) = center
            val (rotR: Float, rotI: Float) = orientation
            val halfWidth: Float = width * 0.5f
            val halfHeight: Float = height * 0.5f
            val halfHeightMinusRadius: Float = halfHeight - cornerRadius
            val addendC: Float = rotR * halfHeightMinusRadius
            val addendD: Float = rotI * halfHeightMinusRadius
            val addendE: Float = rotR * halfWidth
            val addendF: Float = rotI * halfWidth
            val addendSumCF: Float = addendC + addendF
            val addendDiffED: Float = addendE - addendD

            return Vector2F(cX + addendDiffED, cY + addendSumCF)
        }

    val cornerCenterA: Vector2F
        get() {
            val (cX: Float, cY: Float) = center
            val (rotR: Float, rotI: Float) = orientation
            val halfWidth: Float = width * 0.5f
            val halfHeight: Float = height * 0.5f
            val cornerRadius: Float = this.cornerRadius
            val halfWidthMinusRadius: Float = halfWidth - cornerRadius
            val halfHeightMinusRadius: Float = halfHeight - cornerRadius
            val addendA: Float = rotR * halfWidthMinusRadius
            val addendB: Float = rotI * halfWidthMinusRadius
            val addendC: Float = rotR * halfHeightMinusRadius
            val addendD: Float = rotI * halfHeightMinusRadius
            val addendSumBC: Float = addendB + addendC
            val addendDiffAD: Float = addendA - addendD

            return Vector2F(cX + addendDiffAD, cY + addendSumBC)
        }

    val cornerCenterB: Vector2F
        get() {
            val (cX: Float, cY: Float) = center
            val (rotR: Float, rotI: Float) = orientation
            val halfWidth: Float = width * 0.5f
            val halfHeight: Float = height * 0.5f
            val cornerRadius: Float = this.cornerRadius
            val halfWidthMinusRadius: Float = halfWidth - cornerRadius
            val halfHeightMinusRadius: Float = halfHeight - cornerRadius
            val addendA: Float = rotR * halfWidthMinusRadius
            val addendB: Float = rotI * halfWidthMinusRadius
            val addendC: Float = rotR * halfHeightMinusRadius
            val addendD: Float = rotI * halfHeightMinusRadius
            val addendSumAD: Float = addendA + addendD
            val addendDiffBC: Float = addendB - addendC

            return Vector2F(cX - addendSumAD, cY - addendDiffBC)
        }

    val cornerCenterC: Vector2F
        get() {
            val (cX: Float, cY: Float) = center
            val (rotR: Float, rotI: Float) = orientation
            val halfWidth: Float = width * 0.5f
            val halfHeight: Float = height * 0.5f
            val cornerRadius: Float = this.cornerRadius
            val halfWidthMinusRadius: Float = halfWidth - cornerRadius
            val halfHeightMinusRadius: Float = halfHeight - cornerRadius
            val addendA: Float = rotR * halfWidthMinusRadius
            val addendB: Float = rotI * halfWidthMinusRadius
            val addendC: Float = rotR * halfHeightMinusRadius
            val addendD: Float = rotI * halfHeightMinusRadius
            val addendSumBC: Float = addendB + addendC
            val addendDiffAD: Float = addendA - addendD

            return Vector2F(cX - addendDiffAD, cY - addendSumBC)
        }

    val cornerCenterD: Vector2F
        get() {
            val (cX: Float, cY: Float) = center
            val (rotR: Float, rotI: Float) = orientation
            val halfWidth: Float = width * 0.5f
            val halfHeight: Float = height * 0.5f
            val cornerRadius: Float = this.cornerRadius
            val halfWidthMinusRadius: Float = halfWidth - cornerRadius
            val halfHeightMinusRadius: Float = halfHeight - cornerRadius
            val addendA: Float = rotR * halfWidthMinusRadius
            val addendB: Float = rotI * halfWidthMinusRadius
            val addendC: Float = rotR * halfHeightMinusRadius
            val addendD: Float = rotI * halfHeightMinusRadius
            val addendSumAD: Float = addendA + addendD
            val addendDiffBC: Float = addendB - addendC

            return Vector2F(cX + addendSumAD, cY + addendDiffBC)
        }

    override val area: Float
        get() {
            val radius: Float = cornerRadius
            val squaredRadius: Float = radius * radius

            return PI.toFloat() * squaredRadius + width * height - 4f * squaredRadius
        }

    override val perimeter: Float
        get() {
            val radius: Float = cornerRadius

            return (2.0 * PI).toFloat() * radius + 2f * (width + height - 4f * radius)
        }

    override val position: Vector2F
        get() = center

    override fun movedBy(offset: Vector2F): RoundedRectangle = copy(center = center + offset)

    override fun movedTo(position: Vector2F): RoundedRectangle = copy(center = position)

    override fun rotatedBy(rotation: AngleF): RoundedRectangle =
        copy(orientation = orientation * ComplexF.fromAngle(rotation))

    override fun rotatedBy(rotation: ComplexF): RoundedRectangle =
        copy(orientation = orientation * rotation)

    override fun rotatedTo(orientation: AngleF): RoundedRectangle =
        copy(orientation = ComplexF.fromAngle(orientation))

    override fun rotatedTo(orientation: ComplexF): RoundedRectangle =
        copy(orientation = orientation)

    private fun rotatedAroundPointByImpl(point: Vector2F, rotation: ComplexF): RoundedRectangle {
        val (pX: Float, pY: Float) = point
        val (rotR: Float, rotI: Float) = rotation
        val (cX: Float, cY: Float) = center
        val (startRotR: Float, startRotI: Float) = orientation
        val cpDiffX: Float = cX - pX
        val cpDiffY: Float = cY - pY
        val targetCenterX: Float = cpDiffX * rotR - cpDiffY * rotI + pX
        val targetCenterY: Float = cpDiffY * rotR + cpDiffX * rotI + pY
        val targetRotR: Float = startRotR * rotR - startRotI * rotI
        val targetRotI: Float = startRotI * rotR + startRotR * rotI

        return copy(
            center = Vector2F(targetCenterX, targetCenterY),
            orientation = ComplexF(targetRotR, targetRotI)
        )
    }

    override fun rotatedAroundPointBy(point: Vector2F, rotation: AngleF): RoundedRectangle =
        rotatedAroundPointByImpl(point, ComplexF.fromAngle(rotation))

    override fun rotatedAroundPointBy(point: Vector2F, rotation: ComplexF): RoundedRectangle =
        rotatedAroundPointByImpl(point, rotation)

    private fun rotatedAroundPointToImpl(
        point: Vector2F, orientation: ComplexF
    ): RoundedRectangle {
        val (pX: Float, pY: Float) = point
        val (rotR: Float, rotI: Float) = orientation
        val (cX: Float, cY: Float) = center
        val cpDiffX: Float = cX - pX
        val cpDiffY: Float = cY - pY
        val centerToPointDist: Float = sqrt(cpDiffX * cpDiffX + cpDiffY * cpDiffY)

        return if (centerToPointDist > 0.00001f) {
            val pointRotR: Float = cpDiffX / centerToPointDist
            val pointRotI: Float = cpDiffY / centerToPointDist
            val targetRot = ComplexF(pointRotR, -pointRotI) * this.orientation * orientation
            val targetCenterX: Float = rotR * centerToPointDist + pX
            val targetCenterY: Float = rotI * centerToPointDist + pY

            copy(
                center = Vector2F(targetCenterX, targetCenterY),
                orientation = targetRot
            )
        } else {
            copy(orientation = orientation)
        }
    }

    override fun rotatedAroundPointTo(point: Vector2F, orientation: AngleF): RoundedRectangle =
        rotatedAroundPointToImpl(point, ComplexF.fromAngle(orientation))

    override fun rotatedAroundPointTo(point: Vector2F, orientation: ComplexF): RoundedRectangle =
        rotatedAroundPointToImpl(point, orientation)

    override fun scaledBy(factor: Float): RoundedRectangle {
        val absFactor: Float = factor.absoluteValue

        return copy(
            orientation = orientation * 1f.withSign(factor),
            width = width * absFactor,
            height = height * absFactor,
            cornerRadius = cornerRadius * absFactor
        )
    }

    override fun dilatedBy(point: Vector2F, factor: Float): RoundedRectangle {
        val f: Float = 1f - factor
        val cX: Float = center.x * factor + point.x * f
        val cY: Float = center.y * factor + point.y * f
        val (rotR: Float, rotI: Float) = orientation * 1f.withSign(factor)
        val absFactor: Float = factor.absoluteValue
        val width: Float = width * absFactor
        val height: Float = height * absFactor
        val cornerRadius: Float = cornerRadius * absFactor

        return copy(
            center = Vector2F(cX, cY),
            orientation = ComplexF(rotR, rotI),
            width = width,
            height = height,
            cornerRadius = cornerRadius,
        )
    }

    override fun transformedBy(offset: Vector2F, rotation: AngleF): RoundedRectangle = copy(
        center = center + offset,
        orientation = orientation * ComplexF.fromAngle(rotation)
    )

    override fun transformedBy(offset: Vector2F, rotation: ComplexF): RoundedRectangle = copy(
        center = center + offset,
        orientation = orientation * rotation
    )

    private inline fun transformedByImpl(
        offset: Vector2F, rotation: ComplexF, factor: Float
    ): RoundedRectangle {
        val absFactor: Float = factor.absoluteValue

        return copy(
            center = center + offset,
            orientation = orientation * rotation * 1f.withSign(factor),
            width = width * absFactor,
            height = height * absFactor,
            cornerRadius = cornerRadius * absFactor
        )
    }

    override fun transformedBy(
        offset: Vector2F, rotation: AngleF, factor: Float
    ): RoundedRectangle = transformedByImpl(offset, ComplexF.fromAngle(rotation), factor)

    override fun transformedBy(
        offset: Vector2F, rotation: ComplexF, factor: Float
    ): RoundedRectangle = transformedByImpl(offset, rotation, factor)

    override fun transformedTo(position: Vector2F, orientation: AngleF): RoundedRectangle = copy(
        center = position,
        orientation = ComplexF.fromAngle(orientation)
    )

    override fun transformedTo(position: Vector2F, orientation: ComplexF): RoundedRectangle = copy(
        center = position,
        orientation = orientation
    )

    fun closestPointTo(point: Vector2F): Vector2F {
        val orientation: ComplexF = orientation
        val center: Vector2F = center
        val cornerRadius: Float = cornerRadius
        val halfWidth: Float = width * 0.5f
        val halfHeight: Float = height * 0.5f
        val halfWidthMinusRadius: Float = halfWidth - cornerRadius
        val halfHeightMinusRadius: Float = halfHeight - cornerRadius
        val p1 = ComplexF.conjugate(orientation) *
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

                center + (orientation * ComplexF(
                    (cornerCenterX + dx * t),
                    (cornerCenterY + dy * t)
                )).toVector2F()
            }

            p1XAbs > halfWidth -> center + (orientation * ComplexF(
                halfWidth.withSign(p1X),
                p1Y
            )).toVector2F()

            p1YAbs > halfHeight -> center + (orientation * ComplexF(
                p1X,
                halfHeight.withSign(p1Y)
            )).toVector2F()

            else -> point
        }
    }

    operator fun contains(point: Vector2F): Boolean {
        val orientation: ComplexF = orientation
        val center: Vector2F = center
        val cornerRadius: Float = cornerRadius
        val halfWidth: Float = width * 0.5f
        val halfHeight: Float = height * 0.5f
        val halfWidthMinusRadius: Float = halfWidth - cornerRadius
        val halfHeightMinusRadius: Float = halfHeight - cornerRadius
        val p1 = ComplexF.conjugate(orientation) *
                ComplexF(point.x - center.x, point.y - center.y)
        val (p1X: Float, p1Y: Float) = p1
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

    fun pointIterator(): Vector2FIterator = PointIterator(this, index = 0)

    fun cornerCenterIterator(): Vector2FIterator = CornerCenterIterator(this, index = 0)

    fun copy(
        center: Vector2F = this.center,
        orientation: ComplexF = this.orientation,
        width: Float = this.width,
        height: Float = this.height,
        cornerRadius: Float = this.cornerRadius
    ): RoundedRectangle

    operator fun component1(): Vector2F = center

    operator fun component2(): ComplexF = orientation

    operator fun component3(): Float = width

    operator fun component4(): Float = height

    operator fun component5(): Float = cornerRadius

    private class CornerCenterIterator(
        private val rectangle: RoundedRectangle,
        private var index: Int
    ) : Vector2FIterator() {
        override fun hasNext(): Boolean = index < 4

        override fun nextVector2F(): Vector2F = when (index++) {
            0 -> rectangle.cornerCenterA
            1 -> rectangle.cornerCenterB
            2 -> rectangle.cornerCenterC
            3 -> rectangle.cornerCenterD
            else -> throw NoSuchElementException("${index - 1}")
        }
    }

    private class PointIterator(
        private val rectangle: RoundedRectangle,
        private var index: Int
    ) : Vector2FIterator() {
        override fun hasNext(): Boolean = index < 8

        override fun nextVector2F(): Vector2F = when (index++) {
            0 -> rectangle.pointA
            1 -> rectangle.pointB
            2 -> rectangle.pointC
            3 -> rectangle.pointD
            4 -> rectangle.pointE
            5 -> rectangle.pointF
            6 -> rectangle.pointG
            7 -> rectangle.pointH
            else -> throw NoSuchElementException("${index - 1}")
        }
    }
}