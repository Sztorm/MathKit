package com.sztorm.lowallocmath.euclidean2d

import com.sztorm.lowallocmath.*
import kotlin.math.*

/**
 * Creates a new instance of [Square].
 *
 * @param orientation the value is expected to be [normalized][ComplexF.normalized].
 * @throws IllegalArgumentException when [sideLength] is less than zero.
 */
fun Square(center: Vector2F, orientation: ComplexF, sideLength: Float): Square =
    MutableSquare(center, orientation, sideLength)

/**
 * Represents a transformable square in a two-dimensional Euclidean space.
 *
 * Implementations that use default-implemented members of this interface must make sure that the
 * properties [center], [orientation], [sideLength] and the [copy] method are independent of other
 * members and the computational complexity of these members is trivial.
 */
interface Square : RectangleShape, RegularShape, Transformable {
    /** Returns the center of this square. **/
    val center: Vector2F

    /** Returns the point _A_ of this square. **/
    val pointA: Vector2F
        get() {
            val (cX: Float, cY: Float) = center
            val (rotR: Float, rotI: Float) = orientation
            val halfSideLength: Float = sideLength * 0.5f
            val addendA: Float = halfSideLength * (rotR + rotI)
            val addendB: Float = halfSideLength * (rotR - rotI)

            return Vector2F(cX + addendB, cY + addendA)
        }

    /** Returns the point _B_ of this square. **/
    val pointB: Vector2F
        get() {
            val (cX: Float, cY: Float) = center
            val (rotR: Float, rotI: Float) = orientation
            val halfSideLength: Float = sideLength * 0.5f
            val addendA: Float = halfSideLength * (rotR + rotI)
            val addendB: Float = halfSideLength * (rotR - rotI)

            return Vector2F(cX - addendA, cY + addendB)
        }

    /** Returns the point _C_ of this square. **/
    val pointC: Vector2F
        get() {
            val (cX: Float, cY: Float) = center
            val (rotR: Float, rotI: Float) = orientation
            val halfSideLength: Float = sideLength * 0.5f
            val addendA: Float = halfSideLength * (rotR + rotI)
            val addendB: Float = halfSideLength * (rotR - rotI)

            return Vector2F(cX - addendB, cY - addendA)
        }

    /** Returns the point _D_ of this square. **/
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

    /**
     * Returns the width of this square.
     *
     * This property is equal to [sideLength].
     */
    override val width: Float
        get() = sideLength

    /**
     * Returns the height of this square.
     *
     * This property is equal to [sideLength].
     */
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

    /**
     * Returns the position of this object in reference to the origin of [Vector2F.ZERO].
     *
     * This property is equal to [center].
     */
    override val position: Vector2F
        get() = center

    override fun movedBy(displacement: Vector2F): Square = copy(center = center + displacement)

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

    override fun transformedBy(displacement: Vector2F, rotation: AngleF): Square = copy(
        center = center + displacement,
        orientation = orientation * ComplexF.fromAngle(rotation)
    )

    override fun transformedBy(displacement: Vector2F, rotation: ComplexF): Square = copy(
        center = center + displacement,
        orientation = orientation * rotation
    )

    override fun transformedBy(
        displacement: Vector2F, rotation: AngleF, scaleFactor: Float
    ): Square = copy(
        center = center + displacement,
        orientation = orientation * ComplexF.fromAngle(rotation) * 1f.withSign(scaleFactor),
        sideLength = sideLength * scaleFactor.absoluteValue
    )

    override fun transformedBy(
        displacement: Vector2F, rotation: ComplexF, scaleFactor: Float
    ): Square = copy(
        center = center + displacement,
        orientation = orientation * rotation * 1f.withSign(scaleFactor),
        sideLength = sideLength * scaleFactor.absoluteValue
    )

    override fun transformedTo(position: Vector2F, orientation: AngleF): Square = copy(
        center = position,
        orientation = ComplexF.fromAngle(orientation)
    )

    override fun transformedTo(position: Vector2F, orientation: ComplexF): Square = copy(
        center = position,
        orientation = orientation
    )

    /**
     * Returns a copy of this square interpolated [to] other square [by] a factor.
     *
     * @param to the square to which this square is interpolated.
     * @param by the interpolation factor which is expected to be in the range of `[0, 1]`.
     */
    fun interpolated(to: Square, by: Float): Square = copy(
        center = Vector2F.lerp(center, to.center, by),
        orientation = ComplexF.slerp(orientation, to.orientation, by),
        sideLength = Float.lerp(sideLength, to.sideLength, by)
    )

    /** Returns the closest point on this square to the given [point]. **/
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

    /** Returns `true` if this square intersects the given [ray]. **/
    fun intersects(ray: Ray): Boolean {
        val (rectCX: Float, rectCY: Float) = center
        val (rectOR: Float, rectOI: Float) = orientation
        val halfSideLength: Float = sideLength * 0.5f
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

    /** Returns `true` if this square contains the given [point]. **/
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

    /** Creates an iterator over the points of this square. **/
    fun pointIterator(): Vector2FIterator = PointIterator(this, index = 0)

    /**
     * Returns a copy of this instance with specified properties changed.
     *
     * @param orientation the value is expected to be [normalized][ComplexF.normalized].
     */
    fun copy(
        center: Vector2F = this.center,
        orientation: ComplexF = this.orientation,
        sideLength: Float = this.sideLength,
    ): Square

    /** Returns the [center] of this square. **/
    operator fun component1(): Vector2F = center

    /** Returns the [orientation] of this square. **/
    operator fun component2(): ComplexF = orientation

    /** Returns the [sideLength] of this square. **/
    operator fun component3(): Float = sideLength

    private class PointIterator(square: Square, index: Int) : Vector2FIterator() {
        private val _pointA: Vector2F
        private val _pointB: Vector2F
        private val _pointC: Vector2F
        private val _pointD: Vector2F
        private var _index: Int

        init {
            val (cX: Float, cY: Float) = square.center
            val (oR: Float, oI: Float) = square.orientation
            val halfSideLength: Float = square.sideLength * 0.5f
            val addendA: Float = halfSideLength * (oR + oI)
            val addendB: Float = halfSideLength * (oR - oI)
            _pointA = Vector2F(cX + addendB, cY + addendA)
            _pointB = Vector2F(cX - addendA, cY + addendB)
            _pointC = Vector2F(cX - addendB, cY - addendA)
            _pointD = Vector2F(cX + addendA, cY - addendB)
            _index = index
        }

        override fun hasNext(): Boolean = _index < 4

        override fun nextVector2F(): Vector2F = when (_index++) {
            0 -> _pointA
            1 -> _pointB
            2 -> _pointC
            3 -> _pointD
            else -> throw NoSuchElementException("${_index - 1}")
        }
    }
}