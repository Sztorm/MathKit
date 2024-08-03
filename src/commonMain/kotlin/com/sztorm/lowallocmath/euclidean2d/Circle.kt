package com.sztorm.lowallocmath.euclidean2d

import com.sztorm.lowallocmath.AngleF
import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F
import com.sztorm.lowallocmath.lerp
import kotlin.math.PI
import kotlin.math.absoluteValue
import kotlin.math.sqrt
import kotlin.math.withSign

/**
 * Creates a new instance of [Circle].
 *
 * @param orientation the value is expected to be [normalized][ComplexF.normalized].
 * @throws IllegalArgumentException when [radius] is less than zero.
 */
fun Circle(center: Vector2F, orientation: ComplexF, radius: Float): Circle =
    MutableCircle(center, orientation, radius)

/**
 * Represents a transformable circle in a two-dimensional Euclidean space.
 *
 * Implementations that use default-implemented members of this interface must make sure that the
 * properties [center], [orientation], [radius] and the [copy] method are independent of other
 * properties and the computational complexity of these members is trivial.
 */
interface Circle : CircleShape, Transformable {
    /** Returns the center of this circle. **/
    val center: Vector2F

    override val area: Float
        get() = PI.toFloat() * radius * radius

    override val perimeter: Float
        get() = 2f * PI.toFloat() * radius

    override val diameter: Float
        get() = 2f * radius

    /**
     * Returns the position of this object in reference to the origin of [Vector2F.ZERO].
     *
     * This property is equal to [center].
     */
    override val position: Vector2F
        get() = center

    override fun movedBy(displacement: Vector2F): Circle = copy(center = center + displacement)

    override fun movedTo(position: Vector2F): Circle = copy(center = position)

    override fun rotatedBy(rotation: AngleF): Circle =
        copy(orientation = orientation * ComplexF.fromAngle(rotation))

    override fun rotatedBy(rotation: ComplexF): Circle = copy(orientation = orientation * rotation)

    override fun rotatedTo(orientation: AngleF): Circle =
        copy(orientation = ComplexF.fromAngle(orientation))

    override fun rotatedTo(orientation: ComplexF): Circle = copy(orientation = orientation)

    private fun rotatedAroundPointByImpl(point: Vector2F, rotation: ComplexF): Circle {
        val (pX: Float, pY: Float) = point
        val (rotR: Float, rotI: Float) = rotation
        val (cX: Float, cY: Float) = center
        val (startRotR: Float, startRotI: Float) = orientation
        val cpDiffX: Float = cX - pX
        val cpDiffY: Float = cY - pY

        return copy(
            center = Vector2F(
                cpDiffX * rotR - cpDiffY * rotI + pX,
                cpDiffY * rotR + cpDiffX * rotI + pY
            ),
            orientation = ComplexF(
                startRotR * rotR - startRotI * rotI,
                startRotI * rotR + startRotR * rotI
            )
        )
    }

    override fun rotatedAroundPointBy(point: Vector2F, rotation: AngleF): Circle =
        rotatedAroundPointByImpl(point, ComplexF.fromAngle(rotation))

    override fun rotatedAroundPointBy(point: Vector2F, rotation: ComplexF): Circle =
        rotatedAroundPointByImpl(point, rotation)

    private fun rotatedAroundPointToImpl(point: Vector2F, orientation: ComplexF): Circle {
        val (pX: Float, pY: Float) = point
        val (rotR: Float, rotI: Float) = orientation
        val (cX: Float, cY: Float) = center
        val cpDiffX: Float = cX - pX
        val cpDiffY: Float = cY - pY
        val centerToPointDist: Float = sqrt(cpDiffX * cpDiffX + cpDiffY * cpDiffY)

        return if (centerToPointDist > 0.00001f) {
            val startRotR: Float = cpDiffX / centerToPointDist
            val startRotI: Float = cpDiffY / centerToPointDist

            copy(
                center = Vector2F(
                    rotR * centerToPointDist + pX, rotI * centerToPointDist + pY
                ),
                orientation = ComplexF(startRotR, -startRotI) * this.orientation * orientation
            )
        } else {
            copy(orientation = orientation)
        }
    }

    override fun rotatedAroundPointTo(point: Vector2F, orientation: AngleF): Circle =
        rotatedAroundPointToImpl(point, ComplexF.fromAngle(orientation))

    override fun rotatedAroundPointTo(point: Vector2F, orientation: ComplexF): Circle =
        rotatedAroundPointToImpl(point, orientation)

    override fun scaledBy(factor: Float): Circle = copy(
        orientation = orientation * 1f.withSign(factor),
        radius = radius * factor.absoluteValue
    )

    override fun dilatedBy(point: Vector2F, factor: Float): Circle {
        val (cX: Float, cY: Float) = center
        val (pX: Float, pY: Float) = point

        return copy(
            center = Vector2F(pX + factor * (cX - pX), pY + factor * (cY - pY)),
            orientation = orientation * 1f.withSign(factor),
            radius = radius * factor.absoluteValue
        )
    }

    override fun transformedBy(displacement: Vector2F, rotation: AngleF): Circle = copy(
        center = center + displacement,
        orientation = orientation * ComplexF.fromAngle(rotation)
    )

    override fun transformedBy(displacement: Vector2F, rotation: ComplexF): Circle = copy(
        center = center + displacement,
        orientation = orientation * rotation
    )

    override fun transformedBy(
        displacement: Vector2F, rotation: AngleF, scaleFactor: Float
    ): Circle = copy(
        center = center + displacement,
        orientation = orientation * ComplexF.fromAngle(rotation) * 1f.withSign(scaleFactor),
        radius = radius * scaleFactor.absoluteValue
    )

    override fun transformedBy(
        displacement: Vector2F, rotation: ComplexF, scaleFactor: Float
    ): Circle = copy(
        center = center + displacement,
        orientation = orientation * rotation * 1f.withSign(scaleFactor),
        radius = radius * scaleFactor.absoluteValue
    )

    override fun transformedTo(position: Vector2F, orientation: AngleF): Circle = copy(
        center = position,
        orientation = ComplexF.fromAngle(orientation)
    )

    override fun transformedTo(position: Vector2F, orientation: ComplexF): Circle = copy(
        center = position,
        orientation = orientation
    )

    /**
     * Returns a copy of this circle interpolated [to] other circle [by] a factor.
     *
     * @param to the circle to which this circle is interpolated.
     * @param by the interpolation factor which is expected to be in the range of `[0, 1]`.
     */
    fun interpolated(to: Circle, by: Float): Circle = copy(
        center = Vector2F.lerp(center, to.center, by),
        orientation = ComplexF.slerp(orientation, to.orientation, by),
        radius = Float.lerp(radius, to.radius, by)
    )

    /** Returns the closest point on this circle to the given [point]. **/
    fun closestPointTo(point: Vector2F): Vector2F {
        val radius: Float = radius
        val (cx: Float, cy: Float) = center
        val dx: Float = point.x - cx
        val dy: Float = point.y - cy
        val distance: Float = sqrt(dx * dx + dy * dy)
        val t: Float = radius / distance

        return if (distance > radius) Vector2F(cx + dx * t, cy + dy * t)
        else point
    }

    /** Returns `true` if this circle intersects the given [annulus]. **/
    fun intersects(annulus: Annulus): Boolean {
        val distance: Float = center.distanceTo(annulus.center)
        val radius: Float = radius

        return (distance >= (annulus.innerRadius - radius)) and
                (distance <= (annulus.outerRadius + radius))
    }

    /** Returns `true` if this circle intersects the given [circle]. **/
    fun intersects(circle: Circle): Boolean =
        center.distanceTo(circle.center) <= radius + circle.radius

    /** Returns `true` if this circle intersects the given [ray]. **/
    fun intersects(ray: Ray): Boolean {
        val rayOrigin: Vector2F = ray.origin
        val rayDirection: Vector2F = ray.direction
        val circleCenter: Vector2F = center
        val circleRadius: Float = radius
        val diff: Vector2F = circleCenter - rayOrigin
        val t: Float = diff dot rayDirection
        val closestPointOnRay: Vector2F =
            if (t <= 0f) rayOrigin
            else rayOrigin + rayDirection * t

        return closestPointOnRay.distanceTo(circleCenter) <= circleRadius
    }

    /** Returns `true` if this circle contains the given [point]. **/
    operator fun contains(point: Vector2F): Boolean = center.distanceTo(point) <= radius

    /** Returns `true` if this circle contains the whole given [annulus]. **/
    operator fun contains(annulus: Annulus): Boolean =
        center.distanceTo(annulus.center) <= radius - annulus.outerRadius

    /** Returns `true` if this circle contains the whole given [circle]. **/
    operator fun contains(circle: Circle): Boolean =
        center.distanceTo(circle.center) <= radius - circle.radius

    /**
     * Returns a copy of this instance with specified properties changed.
     *
     * @param orientation the value is expected to be [normalized][ComplexF.normalized].
     */
    fun copy(
        center: Vector2F = this.center,
        orientation: ComplexF = this.orientation,
        radius: Float = this.radius
    ): Circle

    /** Returns the [center] of this circle. **/
    operator fun component1(): Vector2F = center

    /** Returns the [orientation] of this circle. **/
    operator fun component2(): ComplexF = orientation

    /** Returns the [radius] of this circle. **/
    operator fun component3(): Float = radius
}