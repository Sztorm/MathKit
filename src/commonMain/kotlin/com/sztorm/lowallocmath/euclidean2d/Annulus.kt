@file:Suppress("ConvertTwoComparisonsToRangeCheck")

package com.sztorm.lowallocmath.euclidean2d

import com.sztorm.lowallocmath.AngleF
import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F
import com.sztorm.lowallocmath.lerp
import kotlin.math.PI
import kotlin.math.absoluteValue
import kotlin.math.sqrt
import kotlin.math.withSign

/** Creates a new instance of [Annulus]. **/
fun Annulus(
    center: Vector2F, orientation: ComplexF, outerRadius: Float, innerRadius: Float
): Annulus = MutableAnnulus(center, orientation, outerRadius, innerRadius)

/**
 * Represents a transformable annulus in a two-dimensional Euclidean space.
 *
 * Implementations that use default-implemented members of this interface must make sure that the
 * [center], [orientation], [outerRadius], and [innerRadius] properties are independent of other
 * properties and their computational complexity is trivial.
 */
interface Annulus : AnnulusShape, Transformable {
    /** Returns the center of this annulus. **/
    val center: Vector2F

    override val area: Float
        get() = PI.toFloat() * (outerRadius * outerRadius - innerRadius * innerRadius)

    override val perimeter: Float
        get() = (2.0 * PI).toFloat() * (outerRadius + innerRadius)

    override val width: Float
        get() = outerRadius - innerRadius

    /**
     * Returns the position of this object in reference to the origin of [Vector2F.ZERO].
     *
     * This property is equal to [center].
     */
    override val position: Vector2F
        get() = center

    override val outerDiameter: Float
        get() = outerRadius * 2f

    override val innerDiameter: Float
        get() = innerRadius * 2f

    override fun movedBy(displacement: Vector2F): Annulus = copy(center = center + displacement)

    override fun movedTo(position: Vector2F): Annulus = copy(center = position)

    override fun rotatedBy(rotation: AngleF): Annulus =
        copy(orientation = orientation * ComplexF.fromAngle(rotation))

    override fun rotatedBy(rotation: ComplexF): Annulus =
        copy(orientation = orientation * rotation)

    override fun rotatedTo(orientation: AngleF): Annulus =
        copy(orientation = ComplexF.fromAngle(orientation))

    override fun rotatedTo(orientation: ComplexF): Annulus = copy(orientation = orientation)

    private fun rotatedAroundPointByImpl(point: Vector2F, rotation: ComplexF): Annulus {
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

    override fun rotatedAroundPointBy(point: Vector2F, rotation: AngleF): Annulus =
        rotatedAroundPointByImpl(point, ComplexF.fromAngle(rotation))

    override fun rotatedAroundPointBy(point: Vector2F, rotation: ComplexF): Annulus =
        rotatedAroundPointByImpl(point, rotation)

    private fun rotatedAroundPointToImpl(point: Vector2F, orientation: ComplexF): Annulus {
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
                orientation = ComplexF(startRotR, -startRotI) * this.orientation * orientation,
            )
        } else {
            copy(orientation = orientation)
        }
    }

    override fun rotatedAroundPointTo(point: Vector2F, orientation: AngleF): Annulus =
        rotatedAroundPointToImpl(point, ComplexF.fromAngle(orientation))

    override fun rotatedAroundPointTo(point: Vector2F, orientation: ComplexF): Annulus =
        rotatedAroundPointToImpl(point, orientation)

    override fun scaledBy(factor: Float): Annulus {
        val absFactor: Float = factor.absoluteValue

        return copy(
            orientation = orientation * 1f.withSign(factor),
            outerRadius = outerRadius * absFactor,
            innerRadius = innerRadius * absFactor
        )
    }

    override fun dilatedBy(point: Vector2F, factor: Float): Annulus {
        val (cX: Float, cY: Float) = center
        val (pX: Float, pY: Float) = point
        val absFactor: Float = factor.absoluteValue

        return copy(
            center = Vector2F(pX + factor * (cX - pX), pY + factor * (cY - pY)),
            orientation = orientation * 1f.withSign(factor),
            outerRadius = outerRadius * absFactor,
            innerRadius = innerRadius * absFactor
        )
    }

    override fun transformedBy(displacement: Vector2F, rotation: AngleF): Annulus = copy(
        center = center + displacement,
        orientation = orientation * ComplexF.fromAngle(rotation)
    )

    override fun transformedBy(displacement: Vector2F, rotation: ComplexF): Annulus = copy(
        center = center + displacement,
        orientation = orientation * rotation
    )

    override fun transformedBy(
        displacement: Vector2F, rotation: AngleF, scaleFactor: Float
    ): Annulus {
        val absScaleFactor: Float = scaleFactor.absoluteValue

        return copy(
            center = center + displacement,
            orientation = orientation * ComplexF.fromAngle(rotation) * 1f.withSign(scaleFactor),
            outerRadius = outerRadius * absScaleFactor,
            innerRadius = innerRadius * absScaleFactor
        )
    }

    override fun transformedBy(
        displacement: Vector2F, rotation: ComplexF, scaleFactor: Float
    ): Annulus {
        val absScaleFactor: Float = scaleFactor.absoluteValue

        return copy(
            center = center + displacement,
            orientation = orientation * rotation * 1f.withSign(scaleFactor),
            outerRadius = outerRadius * absScaleFactor,
            innerRadius = innerRadius * absScaleFactor
        )
    }

    override fun transformedTo(position: Vector2F, orientation: AngleF): Annulus = copy(
        center = position,
        orientation = ComplexF.fromAngle(orientation)
    )

    override fun transformedTo(position: Vector2F, orientation: ComplexF): Annulus = copy(
        center = position,
        orientation = orientation
    )

    /**
     * Returns a copy of this annulus interpolated [to] other annulus [by] a factor.
     *
     * @param to The annulus to which this annulus is interpolated.
     * @param by The interpolation factor which is expected to be in the range of `[0, 1]`.
     */
    fun interpolated(to: Annulus, by: Float): Annulus = copy(
        center = Vector2F.lerp(center, to.center, by),
        orientation = ComplexF.slerp(orientation, to.orientation, by),
        outerRadius = Float.lerp(outerRadius, to.outerRadius, by),
        innerRadius = Float.lerp(innerRadius, to.innerRadius, by)
    )

    /** Returns the closest point to the given [point] on this annulus. **/
    fun closestPointTo(point: Vector2F): Vector2F {
        val outerRadius: Float = outerRadius
        val innerRadius: Float = innerRadius
        val (cx: Float, cy: Float) = center
        val dx: Float = cx - point.x
        val dy: Float = cy - point.y
        val distance: Float = sqrt(dx * dx + dy * dy)

        return when {
            distance < innerRadius -> {
                val t: Float = innerRadius / distance

                Vector2F(cx - dx * t, cy - dy * t)
            }

            distance > outerRadius -> {
                val t: Float = outerRadius / distance

                Vector2F(cx - dx * t, cy - dy * t)
            }

            else -> point
        }
    }

    /** Returns `true` if this annulus intersects the given [annulus]. **/
    fun intersects(annulus: Annulus): Boolean {
        val distance: Float = center.distanceTo(annulus.center)
        val otherAnnulusOuterRadius: Float = annulus.outerRadius
        val otherAnnulusInnerRadius: Float = annulus.innerRadius
        val innerRadius: Float = innerRadius

        return (innerRadius <= (otherAnnulusOuterRadius + distance)) &&
                (outerRadius >= (distance - otherAnnulusOuterRadius)) &&
                (otherAnnulusInnerRadius <= (outerRadius + distance))
    }

    /** Returns `true` if this annulus intersects the given [circle]. **/
    fun intersects(circle: Circle): Boolean {
        val distance: Float = center.distanceTo(circle.center)
        val circleRadius: Float = circle.radius

        return (distance >= (innerRadius - circleRadius)) &&
                (distance <= (outerRadius + circleRadius))
    }

    /** Returns `true` if this annulus intersects the given [ray]. **/
    fun intersects(ray: Ray): Boolean {
        val rayOrigin: Vector2F = ray.origin
        val rayDirection: Vector2F = ray.direction
        val annulusCenter: Vector2F = center
        val annulusOuterRadius: Float = outerRadius
        val diff: Vector2F = annulusCenter - rayOrigin
        val t: Float = diff dot rayDirection
        val closestPointOnRay: Vector2F =
            if (t <= 0f) rayOrigin
            else rayOrigin + rayDirection * t

        return closestPointOnRay.distanceTo(annulusCenter) <= annulusOuterRadius
    }

    /** Returns `true` if this annulus contains the given [point]. **/
    operator fun contains(point: Vector2F): Boolean {
        val distance: Float = center.distanceTo(point)

        return distance >= innerRadius && distance <= outerRadius
    }

    /** Returns `true` if this annulus contains the whole given [annulus]. **/
    operator fun contains(annulus: Annulus): Boolean {
        val distance: Float = center.distanceTo(annulus.center)
        val otherAnnulusOuterRadius: Float = annulus.outerRadius
        val otherAnnulusInnerRadius: Float = annulus.innerRadius
        val innerRadius: Float = innerRadius

        return (outerRadius >= (distance + otherAnnulusOuterRadius)) &&
                ((innerRadius <= (distance - otherAnnulusOuterRadius)) ||
                        (innerRadius <= (distance + otherAnnulusInnerRadius)))
    }

    /** Returns `true` if this annulus contains the whole given [circle]. **/
    operator fun contains(circle: Circle): Boolean {
        val distance: Float = center.distanceTo(circle.center)
        val circleRadius: Float = circle.radius

        return (outerRadius >= (distance + circleRadius)) &&
                (innerRadius <= (distance - circleRadius))
    }

    /** Returns a copy of this instance with specified properties changed. **/
    fun copy(
        center: Vector2F = this.center,
        orientation: ComplexF = this.orientation,
        outerRadius: Float = this.outerRadius,
        innerRadius: Float = this.innerRadius
    ): Annulus

    /** Returns the [center] of this annulus. **/
    operator fun component1(): Vector2F = center

    /** Returns the [orientation] of this annulus. **/
    operator fun component2(): ComplexF = orientation

    /** Returns the [outerRadius] of this annulus. **/
    operator fun component3(): Float = outerRadius

    /** Returns the [innerRadius] of this annulus. **/
    operator fun component4(): Float = innerRadius
}