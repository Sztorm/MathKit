package com.sztorm.lowallocmath.euclidean2d

import com.sztorm.lowallocmath.*
import kotlin.math.*

/**
 * Creates a new instance of [Rectangle].
 *
 * @param orientation the value is expected to be [normalized][ComplexF.normalized].
 * @throws IllegalArgumentException when [width] is less than zero.
 * @throws IllegalArgumentException when [height] is less than zero.
 */
fun Rectangle(center: Vector2F, orientation: ComplexF, width: Float, height: Float): Rectangle =
    MutableRectangle(center, orientation, width, height)

/**
 * Represents a transformable rectangle in a two-dimensional Euclidean space.
 *
 * Implementations that use default-implemented members of this interface must make sure that the
 * properties [center], [orientation], [width], [height] and the [copy] method are independent of
 * other members and the computational complexity of these members is trivial.
 */
interface Rectangle : RectangleShape, Transformable {
    /** Returns the center of this rectangle. **/
    val center: Vector2F

    /** Returns the point _A_ of this rectangle. **/
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

    /** Returns the point _B_ of this rectangle. **/
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

    /** Returns the point _C_ of this rectangle. **/
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

    /** Returns the point _D_ of this rectangle. **/
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

    /**
     * Returns the position of this object in reference to the origin of [Vector2F.ZERO].
     *
     * This property is equal to [center].
     */
    override val position: Vector2F
        get() = center

    override fun movedBy(displacement: Vector2F): Rectangle = copy(center = center + displacement)

    override fun movedTo(position: Vector2F): Rectangle = copy(center = position)

    private inline fun rotatedByImpl(rotation: ComplexF): Rectangle = copy(
        orientation = (orientation * rotation).normalizedOrElse(ComplexF.ONE)
    )

    override fun rotatedBy(rotation: AngleF): Rectangle =
        rotatedByImpl(ComplexF.fromAngle(rotation))

    override fun rotatedBy(rotation: ComplexF): Rectangle = rotatedByImpl(rotation)

    private inline fun rotatedToImpl(orientation: ComplexF): Rectangle =
        copy(orientation = orientation.normalizedOrElse(ComplexF.ONE))

    override fun rotatedTo(orientation: AngleF): Rectangle =
        rotatedToImpl(ComplexF.fromAngle(orientation))

    override fun rotatedTo(orientation: ComplexF): Rectangle = rotatedToImpl(orientation)

    private inline fun rotatedAroundPointByImpl(point: Vector2F, rotation: ComplexF): Rectangle {
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
            orientation = ComplexF(targetRotR, targetRotI).normalizedOrElse(ComplexF.ONE)
        )
    }

    override fun rotatedAroundPointBy(point: Vector2F, rotation: AngleF): Rectangle =
        rotatedAroundPointByImpl(point, ComplexF.fromAngle(rotation))

    override fun rotatedAroundPointBy(point: Vector2F, rotation: ComplexF): Rectangle =
        rotatedAroundPointByImpl(point, rotation)

    private inline fun rotatedAroundPointToImpl(point: Vector2F, orientation: ComplexF): Rectangle {
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
                orientation = targetRot.normalizedOrElse(ComplexF.ONE)
            )
        } else copy(orientation = orientation.normalizedOrElse(ComplexF.ONE))
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
        val (cX: Float, cY: Float) = center
        val (pX: Float, pY: Float) = point
        val centerX: Float = cX * factor + pX * f
        val centerY: Float = cY * factor + pY * f
        val absFactor: Float = factor.absoluteValue

        return copy(
            center = Vector2F(centerX, centerY),
            orientation = orientation * 1f.withSign(factor),
            width = width * absFactor,
            height = height * absFactor
        )
    }

    private inline fun transformedByImpl(
        displacement: Vector2F, rotation: ComplexF
    ): Rectangle = copy(
        center = center + displacement,
        orientation = (orientation * rotation).normalizedOrElse(ComplexF.ONE)
    )

    override fun transformedBy(displacement: Vector2F, rotation: AngleF): Rectangle =
        transformedByImpl(displacement, ComplexF.fromAngle(rotation))

    override fun transformedBy(displacement: Vector2F, rotation: ComplexF): Rectangle =
        transformedByImpl(displacement, rotation)

    private inline fun transformedByImpl(
        displacement: Vector2F, rotation: ComplexF, scaleFactor: Float
    ): Rectangle {
        val absScaleFactor: Float = scaleFactor.absoluteValue

        return copy(
            center = center + displacement,
            orientation = (orientation * rotation)
                .normalizedOrElse(ComplexF.ONE) * 1f.withSign(scaleFactor),
            width = width * absScaleFactor,
            height = height * absScaleFactor
        )
    }

    override fun transformedBy(
        displacement: Vector2F, rotation: AngleF, scaleFactor: Float
    ): Rectangle = transformedByImpl(displacement, ComplexF.fromAngle(rotation), scaleFactor)

    override fun transformedBy(
        displacement: Vector2F, rotation: ComplexF, scaleFactor: Float
    ): Rectangle = transformedByImpl(displacement, rotation, scaleFactor)

    private inline fun transformedToImpl(
        position: Vector2F, orientation: ComplexF
    ): Rectangle = copy(
        center = position,
        orientation = orientation.normalizedOrElse(ComplexF.ONE)
    )

    override fun transformedTo(position: Vector2F, orientation: AngleF): Rectangle =
        transformedToImpl(position, ComplexF.fromAngle(orientation))

    override fun transformedTo(position: Vector2F, orientation: ComplexF): Rectangle =
        transformedToImpl(position, orientation)

    /**
     * Returns a copy of this rectangle interpolated [to] other rectangle [by] a factor.
     *
     * @param to the rectangle to which this rectangle is interpolated.
     * @param by the interpolation factor which is expected to be in the range of `[0, 1]`.
     */
    fun interpolated(to: Rectangle, by: Float): Rectangle = copy(
        center = Vector2F.lerp(center, to.center, by),
        orientation = ComplexF.slerp(orientation, to.orientation, by)
            .normalizedOrElse(ComplexF.ONE),
        width = Float.lerp(width, to.width, by),
        height = Float.lerp(height, to.height, by)
    )

    /** Returns the closest point on this rectangle to the given [point]. **/
    fun closestPointTo(point: Vector2F): Vector2F {
        val (cX: Float, cY: Float) = center
        val (rotR: Float, rotI: Float) = orientation
        val halfWidth: Float = width * 0.5f
        val halfHeight: Float = height * 0.5f
        val (pX: Float, pY: Float) = point
        val pcDiffX: Float = pX - cX
        val pcDiffY: Float = pY - cY
        val p1X: Float = rotR * pcDiffX + rotI * pcDiffY
        val p1Y: Float = rotR * pcDiffY - rotI * pcDiffX
        val p2X: Float = if (p1X.absoluteValue > halfWidth) halfWidth.withSign(p1X) else p1X
        val p2Y: Float = if (p1Y.absoluteValue > halfHeight) halfHeight.withSign(p1Y) else p1Y

        return Vector2F(rotR * p2X - rotI * p2Y + cX, rotI * p2X + rotR * p2Y + cY)
    }

    /** Returns `true` if this rectangle intersects the given [ray]. **/
    fun intersects(ray: Ray): Boolean {
        val (rectCX: Float, rectCY: Float) = center
        val (rectOR: Float, rectOI: Float) = orientation
        val halfWidth: Float = width * 0.5f
        val halfHeight: Float = height * 0.5f
        val aabbMinX: Float = rectCX - halfWidth
        val aabbMinY: Float = rectCY - halfHeight
        val aabbMaxX: Float = rectCX + halfWidth
        val aabbMaxY: Float = rectCY + halfHeight

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

    /** Returns `true` if this rectangle contains the given [point]. **/
    operator fun contains(point: Vector2F): Boolean {
        val (cX: Float, cY: Float) = center
        val (rotR: Float, rotI: Float) = orientation
        val halfWidth: Float = width * 0.5f
        val halfHeight: Float = height * 0.5f
        val (pX: Float, pY: Float) = point
        val pcDiffX: Float = pX - cX
        val pcDiffY: Float = pY - cY
        val p1X: Float = rotR * pcDiffX + rotI * pcDiffY
        val p1Y: Float = rotR * pcDiffY - rotI * pcDiffX

        return (p1X.absoluteValue <= halfWidth) and (p1Y.absoluteValue <= halfHeight)
    }

    /** Creates an iterator over the points of this rectangle. **/
    fun pointIterator(): Vector2FIterator = PointIterator(this, index = 0)

    /**
     * Returns a copy of this instance with specified properties changed.
     *
     * @param orientation the value is expected to be [normalized][ComplexF.normalized].
     */
    fun copy(
        center: Vector2F = this.center,
        orientation: ComplexF = this.orientation,
        width: Float = this.width,
        height: Float = this.height
    ): Rectangle

    /** Returns the [center] of this rectangle. **/
    operator fun component1(): Vector2F = center

    /** Returns the [orientation] of this rectangle. **/
    operator fun component2(): ComplexF = orientation

    /** Returns the [width] of this rectangle. **/
    operator fun component3(): Float = width

    /** Returns the [height] of this rectangle. **/
    operator fun component4(): Float = height

    private class PointIterator(rectangle: Rectangle, index: Int) : Vector2FIterator() {
        private val _pointA: Vector2F
        private val _pointB: Vector2F
        private val _pointC: Vector2F
        private val _pointD: Vector2F
        private var _index: Int

        init {
            val (cX: Float, cY: Float) = rectangle.center
            val (oR: Float, oI: Float) = rectangle.orientation
            val halfWidth: Float = rectangle.width * 0.5f
            val halfHeight: Float = rectangle.height * 0.5f
            val addendX1: Float = oR * halfWidth
            val addendX2: Float = oI * halfHeight
            val addendY1: Float = oR * halfHeight
            val addendY2: Float = oI * halfWidth
            val addendSumX1X2: Float = addendX1 + addendX2
            val addendDiffX1X2: Float = addendX1 - addendX2
            val addendSumY1Y2: Float = addendY1 + addendY2
            val addendDiffY1Y2: Float = addendY1 - addendY2
            _pointA = Vector2F(cX + addendDiffX1X2, cY + addendSumY1Y2)
            _pointB = Vector2F(cX - addendSumX1X2, cY + addendDiffY1Y2)
            _pointC = Vector2F(cX - addendDiffX1X2, cY - addendSumY1Y2)
            _pointD = Vector2F(cX + addendSumX1X2, cY - addendDiffY1Y2)
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