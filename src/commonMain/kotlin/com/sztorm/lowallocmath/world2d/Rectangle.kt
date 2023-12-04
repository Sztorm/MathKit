package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.AngleF
import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F
import com.sztorm.lowallocmath.Vector2FIterator
import kotlin.math.absoluteValue
import kotlin.math.sqrt
import kotlin.math.withSign

fun Rectangle(center: Vector2F, orientation: ComplexF, width: Float, height: Float): Rectangle =
    MutableRectangle(center, orientation, width, height)

interface Rectangle : RectangleShape, Transformable {
    val center: Vector2F

    val pointA: Vector2F
        get() {
            val (cX: Float, cY: Float) = center
            val (rotR: Float, rotI: Float) = orientation
            val halfWidth: Float = width * 0.5f
            val halfHeight: Float = height * 0.5f
            val addendX1: Float = rotR * halfWidth
            val addendX2: Float = rotI * halfHeight
            val addendY1: Float = rotR * halfHeight
            val addendY2: Float = rotI * halfWidth
            val addendDiffX1X2: Float = addendX1 - addendX2
            val addendSumY1Y2: Float = addendY1 + addendY2

            return Vector2F(cX + addendDiffX1X2, cY + addendSumY1Y2)
        }

    val pointB: Vector2F
        get() {
            val (cX: Float, cY: Float) = center
            val (rotR: Float, rotI: Float) = orientation
            val halfWidth: Float = width * 0.5f
            val halfHeight: Float = height * 0.5f
            val addendX1: Float = rotR * halfWidth
            val addendX2: Float = rotI * halfHeight
            val addendY1: Float = rotR * halfHeight
            val addendY2: Float = rotI * halfWidth
            val addendSumX1X2: Float = addendX1 + addendX2
            val addendDiffY1Y2: Float = addendY1 - addendY2

            return Vector2F(cX - addendSumX1X2, cY + addendDiffY1Y2)
        }

    val pointC: Vector2F
        get() {
            val (cX: Float, cY: Float) = center
            val (rotR: Float, rotI: Float) = orientation
            val halfWidth: Float = width * 0.5f
            val halfHeight: Float = height * 0.5f
            val addendX1: Float = rotR * halfWidth
            val addendX2: Float = rotI * halfHeight
            val addendY1: Float = rotR * halfHeight
            val addendY2: Float = rotI * halfWidth
            val addendDiffX1X2: Float = addendX1 - addendX2
            val addendSumY1Y2: Float = addendY1 + addendY2

            return Vector2F(cX - addendDiffX1X2, cY - addendSumY1Y2)
        }

    val pointD: Vector2F
        get() {
            val (cX: Float, cY: Float) = center
            val (rotR: Float, rotI: Float) = orientation
            val halfWidth: Float = width * 0.5f
            val halfHeight: Float = height * 0.5f
            val addendX1: Float = rotR * halfWidth
            val addendX2: Float = rotI * halfHeight
            val addendY1: Float = rotR * halfHeight
            val addendY2: Float = rotI * halfWidth
            val addendSumX1X2: Float = addendX1 + addendX2
            val addendDiffY1Y2: Float = addendY1 - addendY2

            return Vector2F(cX + addendSumX1X2, cY - addendDiffY1Y2)
        }

    override val area: Float
        get() = width * height

    override val perimeter: Float
        get() = 2f * (width + height)

    override val position: Vector2F
        get() = center

    override fun movedBy(offset: Vector2F): Rectangle = copy(center = center + offset)

    override fun movedTo(position: Vector2F): Rectangle = copy(center = position)

    override fun rotatedBy(rotation: AngleF): Rectangle =
        copy(orientation = orientation * ComplexF.fromAngle(rotation))

    override fun rotatedBy(rotation: ComplexF): Rectangle =
        copy(orientation = orientation * rotation)

    override fun rotatedTo(orientation: AngleF): Rectangle =
        copy(orientation = ComplexF.fromAngle(orientation))

    override fun rotatedTo(orientation: ComplexF): Rectangle =
        copy(orientation = orientation)

    private fun rotatedAroundPointByImpl(point: Vector2F, rotation: ComplexF): Rectangle {
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

    override fun rotatedAroundPointBy(point: Vector2F, rotation: AngleF): Rectangle =
        rotatedAroundPointByImpl(point, ComplexF.fromAngle(rotation))

    override fun rotatedAroundPointBy(point: Vector2F, rotation: ComplexF): Rectangle =
        rotatedAroundPointByImpl(point, rotation)

    private fun rotatedAroundPointToImpl(point: Vector2F, orientation: ComplexF): Rectangle {
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
        } else copy(orientation = orientation)
    }

    override fun rotatedAroundPointTo(point: Vector2F, orientation: AngleF): Rectangle =
        rotatedAroundPointToImpl(point, ComplexF.fromAngle(orientation))

    override fun rotatedAroundPointTo(point: Vector2F, orientation: ComplexF): Rectangle =
        rotatedAroundPointToImpl(point, orientation)

    override fun scaledBy(factor: Float): Rectangle {
        val absFactor: Float = factor.absoluteValue

        return copy(
            orientation = orientation * 1f.withSign(factor),
            width = width * absFactor,
            height = height * absFactor
        )
    }

    override fun dilatedBy(point: Vector2F, factor: Float): Rectangle {
        val f: Float = 1f - factor
        val cX: Float = center.x * factor + point.x * f
        val cY: Float = center.y * factor + point.y * f
        val (rotR: Float, rotI: Float) = orientation * 1f.withSign(factor)
        val absFactor: Float = factor.absoluteValue

        return copy(
            center = Vector2F(cX, cY),
            orientation = ComplexF(rotR, rotI),
            width = width * absFactor,
            height = height * absFactor
        )
    }

    override fun transformedBy(offset: Vector2F, rotation: AngleF): Rectangle = copy(
        center = center + offset,
        orientation = orientation * ComplexF.fromAngle(rotation)
    )

    override fun transformedBy(offset: Vector2F, rotation: ComplexF): Rectangle = copy(
        center = center + offset,
        orientation = orientation * rotation
    )

    private inline fun transformedByImpl(
        offset: Vector2F, rotation: ComplexF, factor: Float
    ): Rectangle {
        val absFactor: Float = factor.absoluteValue

        return copy(
            center = center + offset,
            orientation = orientation * rotation * 1f.withSign(factor),
            width = width * absFactor,
            height = height * absFactor
        )
    }

    override fun transformedBy(offset: Vector2F, rotation: AngleF, factor: Float): Rectangle =
        transformedByImpl(offset, ComplexF.fromAngle(rotation), factor)

    override fun transformedBy(offset: Vector2F, rotation: ComplexF, factor: Float): Rectangle =
        transformedByImpl(offset, rotation, factor)

    override fun transformedTo(position: Vector2F, orientation: AngleF): Rectangle = copy(
        center = position,
        orientation = ComplexF.fromAngle(orientation)
    )

    override fun transformedTo(position: Vector2F, orientation: ComplexF): Rectangle = copy(
        center = position,
        orientation = orientation
    )

    fun closestPointTo(point: Vector2F): Vector2F {
        val center: Vector2F = this.center
        val orientation: ComplexF = this.orientation
        val halfWidth: Float = width * 0.5f
        val halfHeight: Float = height * 0.5f
        val p1 = ComplexF.conjugate(orientation) *
                ComplexF(point.x - center.x, point.y - center.y)
        val (p1X: Float, p1Y: Float) = p1
        val p2 = ComplexF(
            if (p1X.absoluteValue > halfWidth) halfWidth.withSign(p1X) else p1X,
            if (p1Y.absoluteValue > halfHeight) halfHeight.withSign(p1Y) else p1Y
        )
        return center + (orientation * p2).toVector2F()
    }

    operator fun contains(point: Vector2F): Boolean {
        val center: Vector2F = this.center
        val halfWidth: Float = width * 0.5f
        val halfHeight: Float = height * 0.5f
        val p1 = ComplexF.conjugate(orientation) *
                ComplexF(point.x - center.x, point.y - center.y)

        return (p1.real.absoluteValue <= halfWidth) and (p1.imaginary.absoluteValue <= halfHeight)
    }

    fun pointIterator(): Vector2FIterator = PointIterator(this, index = 0)

    fun copy(
        center: Vector2F = this.center,
        orientation: ComplexF = this.orientation,
        width: Float = this.width,
        height: Float = this.height
    ): Rectangle

    operator fun component1(): Vector2F = center

    operator fun component2(): ComplexF = orientation

    operator fun component3(): Float = width

    operator fun component4(): Float = height

    private class PointIterator(
        private val rectangle: Rectangle,
        private var index: Int
    ) : Vector2FIterator() {
        override fun hasNext(): Boolean = index < 4

        override fun nextVector2F(): Vector2F = when (index++) {
            0 -> rectangle.pointA
            1 -> rectangle.pointB
            2 -> rectangle.pointC
            3 -> rectangle.pointD
            else -> throw NoSuchElementException("${index - 1}")
        }
    }
}