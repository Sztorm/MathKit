package com.sztorm.lowallocmath.euclidean2d

import com.sztorm.lowallocmath.*
import kotlin.math.PI
import kotlin.math.absoluteValue
import kotlin.math.sqrt
import kotlin.math.withSign

fun Square(center: Vector2F, orientation: ComplexF, sideLength: Float): Square =
    MutableSquare(center, orientation, sideLength)

interface Square : RectangleShape, RegularShape, Transformable {
    val center: Vector2F

    val pointA: Vector2F
        get() {
            val (cX: Float, cY: Float) = center
            val (rotR: Float, rotI: Float) = orientation
            val halfSideLength: Float = sideLength * 0.5f
            val addendA: Float = halfSideLength * (rotR + rotI)
            val addendB: Float = halfSideLength * (rotR - rotI)

            return Vector2F(cX + addendB, cY + addendA)
        }

    val pointB: Vector2F
        get() {
            val (cX: Float, cY: Float) = center
            val (rotR: Float, rotI: Float) = orientation
            val halfSideLength: Float = sideLength * 0.5f
            val addendA: Float = halfSideLength * (rotR + rotI)
            val addendB: Float = halfSideLength * (rotR - rotI)

            return Vector2F(cX - addendA, cY + addendB)
        }

    val pointC: Vector2F
        get() {
            val (cX: Float, cY: Float) = center
            val (rotR: Float, rotI: Float) = orientation
            val halfSideLength: Float = sideLength * 0.5f
            val addendA: Float = halfSideLength * (rotR + rotI)
            val addendB: Float = halfSideLength * (rotR - rotI)

            return Vector2F(cX - addendB, cY - addendA)
        }

    val pointD: Vector2F
        get() {
            val (cX: Float, cY: Float) = center
            val (rotR: Float, rotI: Float) = orientation
            val halfSideLength: Float = sideLength * 0.5f
            val addendA: Float = halfSideLength * (rotR + rotI)
            val addendB: Float = halfSideLength * (rotR - rotI)

            return Vector2F(cX + addendA, cY - addendB)
        }

    override val area: Float
        get() = sideLength * sideLength

    override val perimeter: Float
        get() = 4f * sideLength

    override val width: Float
        get() = sideLength

    override val height: Float
        get() = sideLength

    override val sideCount: Int
        get() = 4

    override val interiorAngle: AngleF
        get() = AngleF((0.5 * PI).toFloat())

    override val exteriorAngle: AngleF
        get() = AngleF((0.5 * PI).toFloat())

    override val inradius: Float
        get() = 0.5f * sideLength

    override val circumradius: Float
        get() = 0.7071068f * sideLength

    override val position: Vector2F
        get() = center

    override fun movedBy(offset: Vector2F): Square = copy(center = center + offset)

    override fun movedTo(position: Vector2F): Square = copy(center = position)

    override fun rotatedBy(rotation: AngleF): Square =
        copy(orientation = orientation * ComplexF.fromAngle(rotation))

    override fun rotatedBy(rotation: ComplexF): Square =
        copy(orientation = orientation * rotation)

    override fun rotatedTo(orientation: AngleF): Square =
        copy(orientation = ComplexF.fromAngle(orientation))

    override fun rotatedTo(orientation: ComplexF): Square = copy(orientation = orientation)

    private fun rotatedAroundPointByImpl(point: Vector2F, rotation: ComplexF): Square {
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

    override fun rotatedAroundPointBy(point: Vector2F, rotation: AngleF): Square =
        rotatedAroundPointByImpl(point, ComplexF.fromAngle(rotation))

    override fun rotatedAroundPointBy(point: Vector2F, rotation: ComplexF): Square =
        rotatedAroundPointByImpl(point, rotation)

    private fun rotatedAroundPointToImpl(point: Vector2F, orientation: ComplexF): Square {
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

    override fun rotatedAroundPointTo(point: Vector2F, orientation: AngleF): Square =
        rotatedAroundPointToImpl(point, ComplexF.fromAngle(orientation))

    override fun rotatedAroundPointTo(point: Vector2F, orientation: ComplexF): Square =
        rotatedAroundPointToImpl(point, orientation)

    override fun scaledBy(factor: Float): Square = copy(
        orientation = orientation * 1f.withSign(factor),
        sideLength = sideLength * factor.absoluteValue
    )

    override fun dilatedBy(point: Vector2F, factor: Float): Square {
        val f: Float = 1f - factor
        val cX: Float = center.x * factor + point.x * f
        val cY: Float = center.y * factor + point.y * f
        val sideLength: Float = sideLength * factor.absoluteValue

        return copy(
            center = Vector2F(cX, cY),
            orientation = orientation * 1f.withSign(factor),
            sideLength = sideLength
        )
    }

    override fun transformedBy(offset: Vector2F, rotation: AngleF): Square = copy(
        center = center + offset,
        orientation = orientation * ComplexF.fromAngle(rotation)
    )

    override fun transformedBy(offset: Vector2F, rotation: ComplexF): Square = copy(
        center = center + offset,
        orientation = orientation * rotation
    )

    override fun transformedBy(offset: Vector2F, rotation: AngleF, factor: Float): Square = copy(
        center = center + offset,
        orientation = orientation * ComplexF.fromAngle(rotation) * 1f.withSign(factor),
        sideLength = sideLength * factor.absoluteValue
    )

    override fun transformedBy(offset: Vector2F, rotation: ComplexF, factor: Float): Square = copy(
        center = center + offset,
        orientation = orientation * rotation * 1f.withSign(factor),
        sideLength = sideLength * factor.absoluteValue
    )

    override fun transformedTo(position: Vector2F, orientation: AngleF): Square = copy(
        center = position,
        orientation = ComplexF.fromAngle(orientation)
    )

    override fun transformedTo(position: Vector2F, orientation: ComplexF): Square = copy(
        center = position,
        orientation = orientation
    )

    fun interpolated(to: Square, by: Float): Square = copy(
        center = Vector2F.lerp(center, to.center, by),
        orientation = ComplexF.slerp(orientation, to.orientation, by),
        sideLength = Float.lerp(sideLength, to.sideLength, by)
    )

    fun closestPointTo(point: Vector2F): Vector2F {
        val (cX: Float, cY: Float) = center
        val (rotR: Float, rotI: Float) = orientation
        val halfSideLength: Float = sideLength * 0.5f
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

    operator fun contains(point: Vector2F): Boolean {
        val (cX: Float, cY: Float) = center
        val (rotR: Float, rotI: Float) = orientation
        val halfSideLength: Float = sideLength * 0.5f
        val (pX: Float, pY: Float) = point
        val cpDiffX: Float = pX - cX
        val cpDiffY: Float = pY - cY
        val p1X: Float = rotR * cpDiffX + rotI * cpDiffY
        val p1Y: Float = rotR * cpDiffY - rotI * cpDiffX

        return (p1X.absoluteValue <= halfSideLength) and (p1Y.absoluteValue <= halfSideLength)
    }

    fun pointIterator(): Vector2FIterator = PointIterator(this, index = 0)

    fun copy(
        center: Vector2F = this.center,
        orientation: ComplexF = this.orientation,
        sideLength: Float = this.sideLength,
    ): Square

    operator fun component1(): Vector2F = center

    operator fun component2(): ComplexF = orientation

    operator fun component3(): Float = sideLength

    private class PointIterator(
        private val square: Square,
        private var index: Int
    ) : Vector2FIterator() {
        override fun hasNext(): Boolean = index < 4

        override fun nextVector2F(): Vector2F = when (index++) {
            0 -> square.pointA
            1 -> square.pointB
            2 -> square.pointC
            3 -> square.pointD
            else -> throw NoSuchElementException("${index - 1}")
        }
    }
}