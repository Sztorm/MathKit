package com.sztorm.lowallocmath.euclidean2d

import com.sztorm.lowallocmath.*
import kotlin.math.PI
import kotlin.math.absoluteValue
import kotlin.math.sqrt
import kotlin.math.withSign

/**
 * Creates a new instance of [RegularTriangle].
 *
 * @param orientation the value is expected to be [normalized][ComplexF.normalized].
 * @throws IllegalArgumentException when [sideLength] is less than zero.
 */
fun RegularTriangle(center: Vector2F, orientation: ComplexF, sideLength: Float): RegularTriangle =
    MutableRegularTriangle(center, orientation, sideLength)

/**
 * Represents a transformable regular triangle in a two-dimensional Euclidean space.
 *
 * Implementations that use default-implemented members of this interface must make sure that the
 * properties [center], [orientation], [sideLength] and the [copy] method are independent of other
 * members and the computational complexity of these members is trivial.
 */
interface RegularTriangle : TriangleShape, RegularShape, Transformable {
    /** Returns the center of this regular triangle. **/
    val center: Vector2F

    /** Returns the point _A_ of this regular triangle. **/
    val pointA: Vector2F
        get() {
            val (cX: Float, cY: Float) = center
            val (rotR: Float, rotI: Float) = orientation
            val inradius: Float = 0.28867513f * sideLength
            val circumradius: Float = inradius + inradius

            return Vector2F(cX - rotI * circumradius, cY + rotR * circumradius)
        }

    /** Returns the point _B_ of this regular triangle. **/
    val pointB: Vector2F
        get() {
            val (cX: Float, cY: Float) = center
            val (rotR: Float, rotI: Float) = orientation
            val sideLength: Float = this.sideLength
            val halfSideLength: Float = sideLength * 0.5f
            val inradius: Float = 0.28867513f * sideLength
            val addendX1: Float = rotI * inradius + cX
            val addendX2: Float = rotR * halfSideLength
            val addendY1: Float = rotI * halfSideLength
            val addendY2: Float = rotR * inradius - cY
            return Vector2F(addendX1 - addendX2, -addendY1 - addendY2)
        }

    /** Returns the point _C_ of this regular triangle. **/
    val pointC: Vector2F
        get() {
            val (cX: Float, cY: Float) = center
            val (rotR: Float, rotI: Float) = orientation
            val sideLength: Float = this.sideLength
            val halfSideLength: Float = sideLength * 0.5f
            val inradius: Float = 0.28867513f * sideLength
            val addendX1: Float = rotI * inradius + cX
            val addendX2: Float = rotR * halfSideLength
            val addendY1: Float = rotI * halfSideLength
            val addendY2: Float = rotR * inradius - cY
            return Vector2F(addendX1 + addendX2, addendY1 - addendY2)
        }

    /**
     * Returns the incenter of this regular triangle. Incenter is the center of the triangle's
     * inscribed circle.
     *
     * This property is equal to [center].
     */
    val incenter: Vector2F
        get() = center

    /**
     * Returns the centroid of this regular triangle. Centroid is the intersection point of the
     * triangle's medians. Centroid is also known as the center of mass.
     *
     * This property is equal to [center].
     */
    val centroid: Vector2F
        get() = center

    /**
     * Returns the circumcenter of this regular triangle. Circumcenter is the center of the
     * triangle's circumscribed circle.
     *
     * This property is equal to [center].
     */
    val circumcenter: Vector2F
        get() = center

    /**
     * Returns the orthocenter of this regular triangle. Orthocenter is the intersection point of
     * the triangle's altitudes.
     *
     * This property is equal to [center].
     */
    val orthocenter: Vector2F
        get() = center

    override val area: Float
        get() = 0.4330127f * sideLength * sideLength

    override val perimeter: Float
        get() = 3f * sideLength

    override val sideLengthAB: Float
        get() = sideLength

    override val sideLengthBC: Float
        get() = sideLength

    override val sideLengthAC: Float
        get() = sideLength

    override val sideCount: Int
        get() = 3

    override val interiorAngle: AngleF
        get() = AngleF((PI / 3.0).toFloat())

    override val exteriorAngle: AngleF
        get() = AngleF((2.0 / 3.0 * PI).toFloat())

    override val inradius: Float
        get() = 0.28867513f * sideLength

    override val circumradius: Float
        get() = 0.5773503f * sideLength

    /**
     * Returns the position of this object in reference to the origin of [Vector2F.ZERO].
     *
     * This property is equal to [center].
     */
    override val position: Vector2F
        get() = center

    override fun movedBy(displacement: Vector2F): RegularTriangle =
        copy(center = center + displacement)

    override fun movedTo(position: Vector2F): RegularTriangle = copy(center = position)

    private inline fun rotatedByImpl(rotation: ComplexF): RegularTriangle =
        copy(orientation = (orientation * rotation).normalizedOrElse(ComplexF.ONE))

    override fun rotatedBy(rotation: AngleF): RegularTriangle =
        rotatedByImpl(ComplexF.fromAngle(rotation))

    override fun rotatedBy(rotation: ComplexF): RegularTriangle = rotatedByImpl(rotation)

    private inline fun rotatedToImpl(orientation: ComplexF): RegularTriangle =
        copy(orientation = orientation.normalizedOrElse(ComplexF.ONE))

    override fun rotatedTo(orientation: AngleF): RegularTriangle =
        rotatedToImpl(ComplexF.fromAngle(orientation))

    override fun rotatedTo(orientation: ComplexF): RegularTriangle = rotatedToImpl(orientation)

    private inline fun rotatedAroundPointByImpl(
        point: Vector2F, rotation: ComplexF
    ): RegularTriangle {
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

    override fun rotatedAroundPointBy(point: Vector2F, rotation: AngleF): RegularTriangle =
        rotatedAroundPointByImpl(point, ComplexF.fromAngle(rotation))

    override fun rotatedAroundPointBy(point: Vector2F, rotation: ComplexF): RegularTriangle =
        rotatedAroundPointByImpl(point, rotation)

    private inline fun rotatedAroundPointToImpl(
        point: Vector2F, orientation: ComplexF
    ): RegularTriangle {
        val (pX: Float, pY: Float) = point
        val (rotR: Float, rotI: Float) = orientation
        val (cX: Float, cY: Float) = center
        val cpDiffX: Float = cX - pX
        val cpDiffY: Float = cY - pY
        val centerToPointDist: Float = sqrt(cpDiffX * cpDiffX + cpDiffY * cpDiffY)

        return if (centerToPointDist > 0.00001f) {
            val pointRotR: Float = cpDiffX / centerToPointDist
            val pointRotI: Float = cpDiffY / centerToPointDist
            val targetOrientation = (ComplexF(pointRotR, -pointRotI) * this.orientation *
                    orientation).normalizedOrElse(ComplexF.ONE)
            val targetCenterX: Float = rotR * centerToPointDist + pX
            val targetCenterY: Float = rotI * centerToPointDist + pY

            copy(
                center = Vector2F(targetCenterX, targetCenterY),
                orientation = targetOrientation
            )
        } else copy(orientation = orientation.normalizedOrElse(ComplexF.ONE))

    }

    override fun rotatedAroundPointTo(point: Vector2F, orientation: AngleF): RegularTriangle =
        rotatedAroundPointToImpl(point, ComplexF.fromAngle(orientation))

    override fun rotatedAroundPointTo(point: Vector2F, orientation: ComplexF): RegularTriangle =
        rotatedAroundPointToImpl(point, orientation)

    override fun scaledBy(factor: Float): RegularTriangle = copy(
        orientation = orientation * 1f.withSign(factor),
        sideLength = sideLength * factor.absoluteValue
    )

    override fun dilatedBy(point: Vector2F, factor: Float): RegularTriangle {
        val (cX: Float, cY: Float) = center
        val (pX: Float, pY: Float) = point
        val f: Float = 1f - factor
        val centerX: Float = cX * factor + pX * f
        val centerY: Float = cY * factor + pY * f
        val (rotR: Float, rotI: Float) = orientation * 1f.withSign(factor)
        val sideLength: Float = sideLength * factor.absoluteValue

        return copy(
            center = Vector2F(centerX, centerY),
            orientation = ComplexF(rotR, rotI),
            sideLength = sideLength,
        )
    }

    private inline fun transformedByImpl(
        displacement: Vector2F, rotation: ComplexF
    ): RegularTriangle = copy(
        center = center + displacement,
        orientation = (orientation * rotation).normalizedOrElse(ComplexF.ONE)
    )

    override fun transformedBy(displacement: Vector2F, rotation: AngleF): RegularTriangle =
        transformedByImpl(displacement, ComplexF.fromAngle(rotation))

    override fun transformedBy(displacement: Vector2F, rotation: ComplexF): RegularTriangle =
        transformedByImpl(displacement, rotation)

    private inline fun transformedByImpl(
        displacement: Vector2F, rotation: ComplexF, scaleFactor: Float
    ): RegularTriangle = copy(
        center = center + displacement,
        orientation = (orientation * rotation)
            .normalizedOrElse(ComplexF.ONE) * 1f.withSign(scaleFactor),
        sideLength = sideLength * scaleFactor.absoluteValue
    )

    override fun transformedBy(
        displacement: Vector2F, rotation: AngleF, scaleFactor: Float
    ): RegularTriangle = transformedByImpl(displacement, ComplexF.fromAngle(rotation), scaleFactor)

    override fun transformedBy(
        displacement: Vector2F, rotation: ComplexF, scaleFactor: Float
    ): RegularTriangle = transformedByImpl(displacement, rotation, scaleFactor)

    private inline fun transformedToImpl(
        position: Vector2F, orientation: ComplexF
    ): RegularTriangle = copy(
        center = position,
        orientation = orientation.normalizedOrElse(ComplexF.ONE)
    )

    override fun transformedTo(position: Vector2F, orientation: AngleF): RegularTriangle =
        transformedToImpl(position, ComplexF.fromAngle(orientation))

    override fun transformedTo(position: Vector2F, orientation: ComplexF): RegularTriangle =
        transformedToImpl(position, orientation)

    /**
     * Returns a copy of this regular triangle interpolated [to] other regular triangle [by] a
     * factor.
     *
     * @param to the regular triangle to which this regular triangle is interpolated.
     * @param by the interpolation factor which is expected to be in the range of `[0, 1]`.
     */
    fun interpolated(to: RegularTriangle, by: Float): RegularTriangle = copy(
        center = Vector2F.lerp(center, to.center, by),
        orientation = ComplexF.slerp(orientation, to.orientation, by)
            .normalizedOrElse(ComplexF.ONE),
        sideLength = Float.lerp(sideLength, to.sideLength, by)
    )

    /** Returns the closest point on this regular triangle to the given [point]. **/
    fun closestPointTo(point: Vector2F): Vector2F {
        val sideLength: Float = this.sideLength
        val halfSideLength: Float = 0.5f * sideLength
        val inradius: Float = 0.28867513f * sideLength
        val orientation: ComplexF = orientation
        val center: Vector2F = center
        val p1: ComplexF = orientation.conjugate *
                ComplexF(point.x - center.x, point.y - center.y)
        val p1X: Float = p1.real
        val p1Y: Float = p1.imaginary
        val yGB: Float = 0.5773503f * p1X // (sqrt(3) / 3) * x
        val yGC: Float = -yGB // (-sqrt(3) / 3) * x

        if ((p1Y <= yGB) and (p1Y <= yGC)) {
            if ((p1Y >= -inradius)) {
                return point
            }
            if (p1X.absoluteValue >= halfSideLength) {
                val vertexPoint = Vector2F(halfSideLength.withSign(p1X), -inradius)

                return vertexPoint * orientation + center
            }
            val edgePoint = Vector2F(p1X, -inradius)

            return edgePoint * orientation + center
        }
        val add120DegRotation = ComplexF(-0.5f, -(0.8660254f.withSign(p1X)))
        val p2: ComplexF = add120DegRotation * p1
        val p2X: Float = p2.real
        val p2Y: Float = p2.imaginary

        if ((p2Y >= -inradius)) {
            return point
        }
        if (p2X.absoluteValue >= halfSideLength) {
            val vertexPoint = Vector2F(halfSideLength.withSign(p2X), -inradius)

            return vertexPoint * (add120DegRotation.conjugate * orientation) + center
        }
        val edgePoint = Vector2F(p2X, -inradius)

        return edgePoint * (add120DegRotation.conjugate * orientation) + center
    }

    /** Returns `true` if this regular triangle intersects the given [ray]. **/
    fun intersects(ray: Ray): Boolean {
        val (oX: Float, oY: Float) = ray.origin
        val (dirX: Float, dirY: Float) = ray.direction
        val (triangleCX: Float, triangleCY: Float) = center
        val (triangleOR: Float, triangleOI: Float) = orientation
        val sideLength: Float = sideLength
        val halfSideLength: Float = sideLength * 0.5f
        val inradius: Float = sideLength * 0.28867513f
        val circumradius: Float = inradius + inradius
        val addendX1: Float = triangleOI * inradius + triangleCX
        val addendX2: Float = triangleOR * halfSideLength
        val addendY1: Float = triangleOI * halfSideLength
        val addendY2: Float = triangleOR * inradius - triangleCY
        val aX: Float = triangleCX - triangleOI * circumradius
        val aY: Float = triangleCY + triangleOR * circumradius
        val bX: Float = addendX1 - addendX2
        val bY: Float = -addendY1 - addendY2
        val cX: Float = addendX1 + addendX2
        val cY: Float = addendY1 - addendY2
        val aoX: Float = oX - aX
        val aoY: Float = oY - aY
        val abX: Float = bX - aX
        val abY: Float = bY - aY
        val detAB: Float = abY * dirX - abX * dirY

        if (detAB.absoluteValue >= 0.00001f) {
            val detABReciprocal: Float = 1f / detAB
            val t1: Float = (aoY * abX - aoX * abY) * detABReciprocal

            if (t1 >= 0f) {
                val t2: Float = (aoY * dirX - aoX * dirY) * detABReciprocal

                if ((t2 >= 0f) and (t2 <= 1f)) {
                    return true
                }
            }
        }
        val boX: Float = oX - bX
        val boY: Float = oY - bY
        val bcX: Float = cX - bX
        val bcY: Float = cY - bY
        val detBC: Float = bcY * dirX - bcX * dirY

        if (detBC.absoluteValue >= 0.00001f) {
            val detBCReciprocal: Float = 1f / detBC
            val t1: Float = (boY * bcX - boX * bcY) * detBCReciprocal

            if (t1 >= 0f) {
                val t2: Float = (boY * dirX - boX * dirY) * detBCReciprocal

                if ((t2 >= 0f) and (t2 <= 1f)) {
                    return true
                }
            }
        }
        val coX: Float = oX - cX
        val coY: Float = oY - cY
        val acX: Float = cX - aX
        val acY: Float = cY - aY
        val abo: Boolean = (aoY * abX - aoX * abY) >= 0f
        val bco: Boolean = (boY * bcX - boX * bcY) >= 0f
        val aco: Boolean = (coX * acY - coY * acX) >= 0f

        return (abo == bco) and (bco == aco)
    }

    /** Returns `true` if this regular triangle contains the given [point]. **/
    operator fun contains(point: Vector2F): Boolean {
        val (cX: Float, cY: Float) = center
        val (rotR: Float, rotI: Float) = orientation
        val sideLength: Float = sideLength
        val minusInradius: Float = -0.28867513f * sideLength // sqrt(3) / 6 * a
        val (pX: Float, pY: Float) = point
        val cpDiffX: Float = pX - cX
        val cpDiffY: Float = pY - cY
        val p1X: Float = rotR * cpDiffX + rotI * cpDiffY
        val p1Y: Float = rotR * cpDiffY - rotI * cpDiffX
        //yAB = sqrt(3) * x + (sqrt(3) / 3) * a
        //yAC = -sqrt(3) * x + (sqrt(3) / 3) * a
        val ax: Float = 1.7320508f * p1X
        val b: Float = 0.5773503f * sideLength
        val yAB: Float = ax + b
        val yAC: Float = -ax + b

        return (p1Y >= minusInradius) and (p1Y <= yAB) and (p1Y <= yAC)
    }

    /** Creates an iterator over the points of this regular triangle. **/
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
    ): RegularTriangle

    /** Returns the [center] of this regular triangle. **/
    operator fun component1(): Vector2F = center

    /** Returns the [orientation] of this regular triangle. **/
    operator fun component2(): ComplexF = orientation

    /** Returns the [sideLength] of this regular triangle. **/
    operator fun component3(): Float = sideLength

    private class PointIterator(triangle: RegularTriangle, index: Int) : Vector2FIterator() {
        private val _pointA: Vector2F
        private val _pointB: Vector2F
        private val _pointC: Vector2F
        private var _index: Int

        init {
            val (cX: Float, cY: Float) = triangle.center
            val (oR: Float, oI: Float) = triangle.orientation
            val sideLength: Float = triangle.sideLength
            val halfSideLength: Float = sideLength * 0.5f
            val inradius: Float = sideLength * 0.28867513f
            val circumradius: Float = inradius + inradius
            val addendX1: Float = oI * inradius + cX
            val addendX2: Float = oR * halfSideLength
            val addendY1: Float = oI * halfSideLength
            val addendY2: Float = oR * inradius - cY
            _pointA = Vector2F(cX - oI * circumradius, cY + oR * circumradius)
            _pointB = Vector2F(addendX1 - addendX2, -addendY1 - addendY2)
            _pointC = Vector2F(addendX1 + addendX2, addendY1 - addendY2)
            _index = index
        }

        override fun hasNext(): Boolean = _index < 3

        override fun nextVector2F(): Vector2F = when (_index++) {
            0 -> _pointA
            1 -> _pointB
            2 -> _pointC
            else -> throw NoSuchElementException("${_index - 1}")
        }
    }
}