package com.sztorm.lowallocmath.euclidean2d

import com.sztorm.lowallocmath.AngleF
import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F
import kotlin.math.absoluteValue
import kotlin.math.sqrt
import kotlin.math.withSign

fun Ray(origin: Vector2F, direction: Vector2F): Ray = MutableRay(origin, direction)

interface Ray : Transformable {
    val origin: Vector2F
    val direction: Vector2F

    override val position: Vector2F
        get() = origin

    override val orientation: ComplexF
        get() = direction.toComplexF()

    override fun movedBy(offset: Vector2F): Ray = copy(origin = origin + offset)

    override fun movedTo(position: Vector2F): Ray = copy(origin = position)

    private inline fun rotatedByImpl(rotation: ComplexF): Ray {
        val (dirX: Float, dirY: Float) = direction
        val (rotR: Float, rotI: Float) = rotation

        return copy(
            direction = Vector2F(dirX * rotR - dirY * rotI, dirY * rotR + dirX * rotI)
        )
    }

    override fun rotatedBy(rotation: AngleF): Ray = rotatedByImpl(ComplexF.fromAngle(rotation))

    override fun rotatedBy(rotation: ComplexF): Ray = rotatedByImpl(rotation)

    override fun rotatedTo(orientation: AngleF): Ray =
        copy(direction = ComplexF.fromAngle(orientation).toVector2F())

    override fun rotatedTo(orientation: ComplexF): Ray = copy(direction = orientation.toVector2F())

    private fun rotatedAroundPointByImpl(point: Vector2F, rotation: ComplexF): Ray {
        val (pX: Float, pY: Float) = point
        val (rotR: Float, rotI: Float) = rotation
        val (cX: Float, cY: Float) = origin
        val (startRotR: Float, startRotI: Float) = direction
        val cpDiffX: Float = cX - pX
        val cpDiffY: Float = cY - pY

        return copy(
            origin = Vector2F(
                cpDiffX * rotR - cpDiffY * rotI + pX, cpDiffY * rotR + cpDiffX * rotI + pY
            ),
            direction = Vector2F(
                startRotR * rotR - startRotI * rotI, startRotI * rotR + startRotR * rotI
            )
        )
    }

    override fun rotatedAroundPointBy(point: Vector2F, rotation: AngleF): Ray =
        rotatedAroundPointByImpl(point, ComplexF.fromAngle(rotation))

    override fun rotatedAroundPointBy(point: Vector2F, rotation: ComplexF): Ray =
        rotatedAroundPointByImpl(point, rotation)

    private fun rotatedAroundPointToImpl(point: Vector2F, orientation: ComplexF): Ray {
        val (pX: Float, pY: Float) = point
        val (rotR: Float, rotI: Float) = orientation
        val (cX: Float, cY: Float) = origin
        val cpDiffX: Float = cX - pX
        val cpDiffY: Float = cY - pY
        val centerToPointDist: Float = sqrt(cpDiffX * cpDiffX + cpDiffY * cpDiffY)

        if (centerToPointDist > 0.00001f) {
            val pointRotR: Float = cpDiffX / centerToPointDist
            val pointRotI: Float = cpDiffY / centerToPointDist
            val (startRotR: Float, startRotI: Float) = direction
            val r0: Float = pointRotR * startRotR + pointRotI * startRotI
            val i0: Float = pointRotR * startRotI - pointRotI * startRotR

            return copy(
                origin = Vector2F(
                    rotR * centerToPointDist + pX, rotI * centerToPointDist + pY
                ),
                direction = Vector2F(r0 * rotR - i0 * rotI, i0 * rotR + r0 * rotI)
            )
        } else {
            return copy(direction = orientation.toVector2F())
        }
    }

    override fun rotatedAroundPointTo(point: Vector2F, orientation: AngleF): Ray =
        rotatedAroundPointToImpl(point, ComplexF.fromAngle(orientation))

    override fun rotatedAroundPointTo(point: Vector2F, orientation: ComplexF): Ray =
        rotatedAroundPointToImpl(point, orientation)

    override fun scaledBy(factor: Float): Ray = copy(direction = direction * 1f.withSign(factor))

    override fun dilatedBy(point: Vector2F, factor: Float): Ray {
        val (cX: Float, cY: Float) = origin
        val (pX: Float, pY: Float) = point

        return copy(
            origin = Vector2F(pX + factor * (cX - pX), pY + factor * (cY - pY)),
            direction = direction * 1f.withSign(factor)
        )
    }

    private inline fun transformedByImpl(offset: Vector2F, rotation: ComplexF): Ray {
        val (dirX: Float, dirY: Float) = direction
        val (rotR: Float, rotI: Float) = rotation

        return copy(
            origin = origin + offset,
            direction = Vector2F(dirX * rotR - dirY * rotI, dirY * rotR + dirX * rotI)
        )
    }

    override fun transformedBy(offset: Vector2F, rotation: AngleF): Ray =
        transformedByImpl(offset, ComplexF.fromAngle(rotation))

    override fun transformedBy(offset: Vector2F, rotation: ComplexF): Ray =
        transformedByImpl(offset, rotation)

    private inline fun transformedByImpl(
        offset: Vector2F, rotation: ComplexF, factor: Float
    ): Ray {
        val (dirX: Float, dirY: Float) = direction
        val factorSign: Float = 1f.withSign(factor)
        val rotR: Float = rotation.real * factorSign
        val rotI: Float = rotation.imaginary * factorSign

        return copy(
            origin = origin + offset,
            direction = Vector2F(dirX * rotR - dirY * rotI, dirY * rotR + dirX * rotI)
        )
    }

    override fun transformedBy(offset: Vector2F, rotation: AngleF, factor: Float): Ray =
        transformedByImpl(offset, ComplexF.fromAngle(rotation), factor)

    override fun transformedBy(offset: Vector2F, rotation: ComplexF, factor: Float): Ray =
        transformedByImpl(offset, rotation, factor)

    override fun transformedTo(position: Vector2F, orientation: AngleF): Ray = copy(
        origin = position,
        direction = ComplexF.fromAngle(orientation).toVector2F()
    )

    override fun transformedTo(position: Vector2F, orientation: ComplexF): Ray = copy(
        origin = position,
        direction = orientation.toVector2F()
    )

    fun interpolated(to: Ray, by: Float): Ray = copy(
        origin = Vector2F.lerp(origin, to.origin, by),
        direction = ComplexF
            .slerp(direction.toComplexF(), to.direction.toComplexF(), by)
            .toVector2F()
    )

    fun closestPointTo(point: Vector2F): Vector2F {
        val origin: Vector2F = this.origin
        val direction: Vector2F = this.direction
        val op: Vector2F = point - origin
        val t: Float = op dot direction

        return if (t <= 0f) origin
        else origin + direction * t
    }

    fun intersects(annulus: Annulus): Boolean {
        val rayOrigin: Vector2F = origin
        val rayDirection: Vector2F = direction
        val annulusCenter: Vector2F = annulus.center
        val annulusOuterRadius: Float = annulus.outerRadius
        val diff: Vector2F = annulusCenter - rayOrigin
        val t: Float = diff dot rayDirection
        val closestPointOnRay: Vector2F =
            if (t <= 0f) rayOrigin
            else rayOrigin + rayDirection * t

        return closestPointOnRay.distanceTo(annulusCenter) <= annulusOuterRadius
    }

    fun intersects(circle: Circle): Boolean {
        val rayOrigin: Vector2F = origin
        val rayDirection: Vector2F = direction
        val circleCenter: Vector2F = circle.center
        val circleRadius: Float = circle.radius
        val diff: Vector2F = circleCenter - rayOrigin
        val t: Float = diff dot rayDirection
        val closestPointOnRay: Vector2F =
            if (t <= 0f) rayOrigin
            else rayOrigin + rayDirection * t

        return closestPointOnRay.distanceTo(circleCenter) <= circleRadius
    }

    fun intersects(ray: Ray): Boolean {
        val (origAX: Float, origAY: Float) = origin
        val (dirAX: Float, dirAY: Float) = direction
        val (origBX: Float, origBY: Float) = ray.origin
        val (dirBX: Float, dirBY: Float) = ray.direction
        val dx: Float = origBX - origAX
        val dy: Float = origBY - origAY
        val dirBCrossDirA: Float = dirBX * dirAY - dirBY * dirAX
        val areDirsTheSameOrOpposite: Boolean = dirBCrossDirA.absoluteValue < 0.00001f

        if (areDirsTheSameOrOpposite) {
            val length: Float = sqrt(dx * dx + dy * dy)

            if (length < 0.00001f) {
                return true
            }
            val dirBDotDirA: Float = dirBX * dirAX + dirBY * dirAY
            val oneOverLength: Float = 1f / length
            val dxn: Float = dx * oneOverLength
            val dyn: Float = dy * oneOverLength
            val areDirsOpposite: Boolean = dirBDotDirA < 0
            val det: Float =
                if (areDirsOpposite) dirAX * dxn + dirAY * dyn - 1f
                else dirAX * dyn - dirAY * dxn

            return det.absoluteValue < 0.000001f
        }
        val nomX: Float = (dy * dirBX - dx * dirBY)
        val nomY: Float = (dy * dirAX - dx * dirAY)

        return (nomX * dirBCrossDirA >= 0f) and (nomY * dirBCrossDirA >= 0f)
    }

    operator fun contains(point: Vector2F): Boolean {
        val origin: Vector2F = this.origin
        val direction: Vector2F = this.direction
        val op: Vector2F = point - origin
        val t: Float = op dot direction
        val closestPoint: Vector2F =
            if (t <= 0f) origin
            else origin + direction * t

        return closestPoint.isApproximately(point)
    }

    fun copy(origin: Vector2F = this.origin, direction: Vector2F = this.direction): Ray

    operator fun component1(): Vector2F = origin

    operator fun component2(): Vector2F = direction
}