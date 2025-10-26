@file:Suppress("NOTHING_TO_INLINE")

package com.sztorm.mathkit.euclidean2d

import com.sztorm.mathkit.*
import kotlin.math.absoluteValue
import kotlin.math.sqrt
import kotlin.math.withSign

/**
 * Creates a new instance of [LineSegment].
 *
 * @param orientation the value is expected to be [normalized][ComplexF.normalized].
 * @throws IllegalArgumentException when [length] is less than zero.
 */
fun LineSegment(center: Vector2F, orientation: ComplexF, length: Float): LineSegment =
    MutableLineSegment(center, orientation, length)

/** Creates a new instance of [LineSegment]. **/
fun LineSegment(pointA: Vector2F, pointB: Vector2F): LineSegment =
    MutableLineSegment(pointA, pointB)

/**
 * Represents a transformable line segment in a two-dimensional Euclidean space.
 *
 * Implementations that use default-implemented members of this interface must make sure that the
 * properties [center], [orientation], [length] and the [copy] method are independent of other
 * members and the computational complexity of these members is trivial.
 */
interface LineSegment : Transformable {
    /** Returns the center of this line segment. **/
    val center: Vector2F

    /** Returns the orientation of this object in reference to the origin of [ComplexF.ONE]. **/
    override val orientation: ComplexF

    /** Returns the length of this line segment. **/
    val length: Float

    /** Returns the point _A_ of this line segment. **/
    val pointA: Vector2F
        get() {
            val (cX: Float, cY: Float) = center
            val (oR: Float, oI: Float) = orientation
            val halfLength: Float = length * 0.5f

            return Vector2F(cX + halfLength * oR, cY + halfLength * oI)
        }

    /** Returns the point _B_ of this line segment. **/
    val pointB: Vector2F
        get() {
            val (cX: Float, cY: Float) = center
            val (oR: Float, oI: Float) = orientation
            val halfLength: Float = length * 0.5f

            return Vector2F(cX - halfLength * oR, cY - halfLength * oI)
        }

    /**
     * Returns the position of this object in reference to the origin of [Vector2F.ZERO].
     *
     * This property is equal to [center].
     */
    override val position: Vector2F
        get() = center

    override fun movedBy(displacement: Vector2F): LineSegment =
        copy(center = center + displacement)

    override fun movedTo(position: Vector2F): LineSegment = copy(center = position)

    private inline fun rotatedByImpl(rotation: ComplexF): LineSegment =
        copy(orientation = (orientation * rotation).normalizedOrElse(ComplexF.ONE))

    override fun rotatedBy(rotation: AngleF): LineSegment =
        rotatedByImpl(ComplexF.fromAngle(rotation))

    override fun rotatedBy(rotation: ComplexF): LineSegment = rotatedByImpl(rotation)

    private inline fun rotatedToImpl(orientation: ComplexF): LineSegment = copy(
        orientation = orientation.normalizedOrElse(ComplexF.ONE)
    )

    override fun rotatedTo(orientation: AngleF): LineSegment =
        rotatedToImpl(ComplexF.fromAngle(orientation))

    override fun rotatedTo(orientation: ComplexF): LineSegment = rotatedToImpl(orientation)

    private inline fun rotatedAroundPointByImpl(point: Vector2F, rotation: ComplexF): LineSegment {
        val (cX: Float, cY: Float) = center
        val (oR: Float, oI: Float) = orientation
        val (pX: Float, pY: Float) = point
        val (rotR: Float, rotI: Float) = rotation
        val cpDiffX: Float = cX - pX
        val cpDiffY: Float = cY - pY
        val centerX: Float = cpDiffX * rotR - cpDiffY * rotI + pX
        val centerY: Float = cpDiffY * rotR + cpDiffX * rotI + pY
        val orientationR: Float = oR * rotR - oI * rotI
        val orientationI: Float = oI * rotR + oR * rotI

        return copy(
            center = Vector2F(centerX, centerY),
            orientation = ComplexF(orientationR, orientationI).normalizedOrElse(ComplexF.ONE)
        )
    }

    override fun rotatedAroundPointBy(point: Vector2F, rotation: AngleF): LineSegment =
        rotatedAroundPointByImpl(point, ComplexF.fromAngle(rotation))

    override fun rotatedAroundPointBy(point: Vector2F, rotation: ComplexF): LineSegment =
        rotatedAroundPointByImpl(point, rotation)

    private inline fun rotatedAroundPointToImpl(
        point: Vector2F, orientation: ComplexF
    ): LineSegment {
        val (cX: Float, cY: Float) = center
        val (pX: Float, pY: Float) = point
        val (oR: Float, oI: Float) = orientation
        val cpDiffX: Float = cX - pX
        val cpDiffY: Float = cY - pY
        val centerToPointDist: Float = sqrt(cpDiffX * cpDiffX + cpDiffY * cpDiffY)

        if (centerToPointDist > 0.00001f) {
            val centerX: Float = oR * centerToPointDist + pX
            val centerY: Float = oI * centerToPointDist + pY
            val centerToPointDistReciprocal: Float = 1f / centerToPointDist
            val pointRotR: Float = cpDiffX * centerToPointDistReciprocal
            val pointRotI: Float = cpDiffY * centerToPointDistReciprocal
            val (startOR: Float, startOI: Float) = this.orientation
            val pRotTimesStartOR: Float = pointRotR * startOR + pointRotI * startOI
            val pRotTimesStartOI: Float = pointRotR * startOI - pointRotI * startOR
            val orientationR: Float = pRotTimesStartOR * oR - pRotTimesStartOI * oI
            val orientationI: Float = pRotTimesStartOI * oR + pRotTimesStartOR * oI

            return copy(
                center = Vector2F(centerX, centerY),
                orientation = ComplexF(orientationR, orientationI).normalizedOrElse(ComplexF.ONE)
            )
        }
        return copy(orientation = orientation.normalizedOrElse(ComplexF.ONE))
    }

    override fun rotatedAroundPointTo(point: Vector2F, orientation: AngleF): LineSegment =
        rotatedAroundPointToImpl(point, ComplexF.fromAngle(orientation))

    override fun rotatedAroundPointTo(point: Vector2F, orientation: ComplexF): LineSegment =
        rotatedAroundPointToImpl(point, orientation)

    override fun scaledBy(factor: Float): LineSegment = copy(
        orientation = orientation * 1f.withSign(factor),
        length = this.length * factor.absoluteValue
    )

    override fun dilatedBy(point: Vector2F, factor: Float): LineSegment {
        val (cX: Float, cY: Float) = center
        val (oR: Float, oI: Float) = orientation
        val (pX: Float, pY: Float) = point
        val f: Float = 1f - factor
        val factorSign: Float = 1f.withSign(factor)
        val centerX: Float = cX * factor + pX * f
        val centerY: Float = cY * factor + pY * f
        val orientationR: Float = oR * factorSign
        val orientationI: Float = oI * factorSign
        val length: Float = this.length * factor.absoluteValue

        return copy(
            center = Vector2F(centerX, centerY),
            orientation = ComplexF(orientationR, orientationI),
            length
        )
    }

    private inline fun transformedByImpl(displacement: Vector2F, rotation: ComplexF): LineSegment {
        val (cX: Float, cY: Float) = center
        val (oR: Float, oI: Float) = orientation
        val (dX: Float, dY: Float) = displacement
        val (rotR: Float, rotI: Float) = rotation
        val orientationR: Float = (oR * rotR - oI * rotI)
        val orientationI: Float = (oI * rotR + oR * rotI)
        val centerX: Float = cX + dX
        val centerY: Float = cY + dY

        return copy(
            center = Vector2F(centerX, centerY),
            orientation = ComplexF(orientationR, orientationI).normalizedOrElse(ComplexF.ONE)
        )
    }

    override fun transformedBy(displacement: Vector2F, rotation: AngleF): LineSegment =
        transformedByImpl(displacement, ComplexF.fromAngle(rotation))

    override fun transformedBy(displacement: Vector2F, rotation: ComplexF): LineSegment =
        transformedByImpl(displacement, rotation)

    private inline fun transformedByImpl(
        displacement: Vector2F, rotation: ComplexF, scaleFactor: Float
    ): LineSegment {
        val (cX: Float, cY: Float) = center
        val (oR: Float, oI: Float) = orientation
        val length: Float = length * scaleFactor.absoluteValue
        val factorSign: Float = 1f.withSign(scaleFactor)
        val (dX: Float, dY: Float) = displacement
        val (rotR: Float, rotI: Float) = rotation
        val orientationR: Float = (oR * rotR - oI * rotI) * factorSign
        val orientationI: Float = (oI * rotR + oR * rotI) * factorSign
        val centerX: Float = cX + dX
        val centerY: Float = cY + dY

        return copy(
            center = Vector2F(centerX, centerY),
            orientation = ComplexF(orientationR, orientationI).normalizedOrElse(ComplexF.ONE),
            length,
        )
    }

    override fun transformedBy(
        displacement: Vector2F, rotation: AngleF, scaleFactor: Float
    ): LineSegment = transformedByImpl(displacement, ComplexF.fromAngle(rotation), scaleFactor)

    override fun transformedBy(
        displacement: Vector2F, rotation: ComplexF, scaleFactor: Float
    ): LineSegment = transformedByImpl(displacement, rotation, scaleFactor)

    private inline fun transformedToImpl(
        position: Vector2F, orientation: ComplexF
    ): LineSegment = copy(
        center = position,
        orientation = orientation.normalizedOrElse(ComplexF.ONE)
    )

    override fun transformedTo(position: Vector2F, orientation: AngleF): LineSegment =
        transformedToImpl(position, ComplexF.fromAngle(orientation))

    override fun transformedTo(position: Vector2F, orientation: ComplexF): LineSegment =
        transformedToImpl(position, orientation)

    /**
     * Returns a copy of this line segment interpolated [to] other line segment [by] a factor.
     *
     * @param to the line segment to which this line segment is interpolated.
     * @param by the interpolation factor which is expected to be in the range of `[0, 1]`.
     */
    fun interpolated(to: LineSegment, by: Float): LineSegment = copy(
        center = Vector2F.lerp(center, to.center, by),
        orientation = ComplexF.slerp(orientation, to.orientation, by)
            .normalizedOrElse(ComplexF.ONE),
        length = Float.lerp(length, to.length, by)
    )

    /** Returns the closest point on this line segment to the given [point]. **/
    fun closestPointTo(point: Vector2F): Vector2F {
        val (cX: Float, cY: Float) = center
        val (oR: Float, oI: Float) = orientation
        val halfLength: Float = length * 0.5f
        val originPointAX: Float = halfLength * oR
        val originPointAY: Float = halfLength * oI
        val pointA = Vector2F(cX + originPointAX, cY + originPointAY)
        val abX: Float = originPointAX * -2f
        val abY: Float = originPointAY * -2f
        val ab = Vector2F(abX, abY)
        val epsilon = 0.00001f

        if ((abX.absoluteValue <= epsilon) and (abY.absoluteValue <= epsilon)) {
            return pointA
        }
        val ap: Vector2F = point - pointA
        val t: Float = (ab dot ap) / (ab dot ab)
        val tClamped: Float = when {
            t < 0f -> 0f
            t > 1f -> 1f
            else -> t
        }
        return pointA + ab * tClamped
    }

    /** Returns `true` if this line segment intersects the given [ray]. **/
    fun intersects(ray: Ray): Boolean {
        val (oX: Float, oY: Float) = ray.origin
        val (dirX: Float, dirY: Float) = ray.direction
        val (cX: Float, cY: Float) = center
        val (oR: Float, oI: Float) = orientation
        val halfLength: Float = length * 0.5f
        val originPointAX: Float = halfLength * oR
        val originPointAY: Float = halfLength * oI
        val aX: Float = cX + originPointAX
        val aY: Float = cY + originPointAY
        val bX: Float = cX - originPointAX
        val bY: Float = cY - originPointAY
        val aoX: Float = oX - aX
        val aoY: Float = oY - aY
        val abX: Float = bX - aX
        val abY: Float = bY - aY
        val dirCrossAB: Float = abY * dirX - abX * dirY
        val areParallel: Boolean = dirCrossAB.absoluteValue < 0.00001f

        if (areParallel) {
            val boX: Float = oX - bX
            val boY: Float = oY - bY
            val dirDotAO: Float = aoX * dirX + aoY * dirY
            val dirDotBO: Float = boX * dirX + boY * dirY
            val dirCrossAO = dirX * aoY - dirY * aoX

            return (dirCrossAO.absoluteValue < 0.00001f) and ((dirDotAO <= 0f) or (dirDotBO <= 0f))
        }
        val detABReciprocal: Float = 1f / dirCrossAB
        val t1: Float = (aoY * abX - aoX * abY) * detABReciprocal

        if (t1 >= 0f) {
            val t2: Float = (aoY * dirX - aoX * dirY) * detABReciprocal

            return (t2 >= 0f) and (t2 <= 1f)
        }
        return false
    }

    /** Returns `true` if this line segment approximately contains the given [point]. **/
    operator fun contains(point: Vector2F): Boolean {
        val (cX: Float, cY: Float) = center
        val (oR: Float, oI: Float) = orientation
        val halfLength: Float = length * 0.5f
        val originPointAX: Float = halfLength * oR
        val originPointAY: Float = halfLength * oI
        val pointA = Vector2F(cX + originPointAX, cY + originPointAY)
        val abX: Float = originPointAX * -2f
        val abY: Float = originPointAY * -2f
        val ab = Vector2F(abX, abY)
        val epsilon = 0.00001f

        if ((abX.absoluteValue <= epsilon) and (abY.absoluteValue <= epsilon)) {
            return pointA.isApproximately(point)
        }
        val ap: Vector2F = point - pointA
        val t: Float = (ab dot ap) / (ab dot ab)

        return if ((t < 0f) or (t > 1f)) false
        else {
            val closestPoint: Vector2F = pointA + ab * t

            return closestPoint.isApproximately(point)
        }
    }

    /** Creates an iterator over the points of this line segment. **/
    fun pointIterator(): Vector2FIterator = PointIterator(this, index = 0)

    /**
     * Returns a copy of this instance with specified properties changed.
     *
     * @param orientation the value is expected to be [normalized][ComplexF.normalized].
     */
    fun copy(
        center: Vector2F = this.center,
        orientation: ComplexF = this.orientation,
        length: Float = this.length
    ): LineSegment

    /** Returns the [center] of this line segment. **/
    operator fun component1(): Vector2F = center

    /** Returns the [orientation] of this line segment. **/
    operator fun component2(): ComplexF = orientation

    /** Returns the [length] of this line segment. **/
    operator fun component3(): Float = length

    private class PointIterator(lineSegment: LineSegment, index: Int) : Vector2FIterator() {
        private val _pointA: Vector2F
        private val _pointB: Vector2F
        private var _index: Int

        init {
            val (cX: Float, cY: Float) = lineSegment.center
            val (oR: Float, oI: Float) = lineSegment.orientation
            val halfLength: Float = lineSegment.length * 0.5f
            val originPointAX: Float = halfLength * oR
            val originPointAY: Float = halfLength * oI
            _pointA = Vector2F(cX + originPointAX, cY + originPointAY)
            _pointB = Vector2F(cX - originPointAX, cY - originPointAY)
            _index = index
        }

        override fun hasNext(): Boolean = _index < 2

        override fun nextVector2F(): Vector2F = when (_index++) {
            0 -> _pointA
            1 -> _pointB
            else -> throw NoSuchElementException("${_index - 1}")
        }
    }
}