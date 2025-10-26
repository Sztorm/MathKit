@file:Suppress("NOTHING_TO_INLINE")

package com.sztorm.mathkit.euclidean2d

import com.sztorm.mathkit.AngleF
import com.sztorm.mathkit.ComplexF
import com.sztorm.mathkit.Vector2F
import kotlin.math.*

/**
 * Creates a new instance of [Ray].
 *
 * @param direction the value is expected to be [normalized][Vector2F.normalized].
 */
fun Ray(origin: Vector2F, direction: Vector2F): Ray = MutableRay(origin, direction)

/**
 * Represents a transformable ray in a two-dimensional Euclidean space.
 *
 * Implementations that use default-implemented members of this interface must make sure that the
 * properties [origin], [direction] and the [copy] method are independent of other members and the
 * computational complexity of these members is trivial.
 */
interface Ray : Transformable {
    /** Returns the origin of this ray. **/
    val origin: Vector2F

    /** Returns the direction of this ray. **/
    val direction: Vector2F

    /**
     * Returns the position of this object in reference to the origin of [Vector2F.ZERO].
     *
     * This property is equal to [origin].
     */
    override val position: Vector2F
        get() = origin

    /**
     * Returns the orientation of this object in reference to the origin of [ComplexF.ONE].
     *
     * This property is determined by [direction].
     */
    override val orientation: ComplexF
        get() = direction.toComplexF()

    override fun movedBy(displacement: Vector2F): Ray = copy(origin = origin + displacement)

    override fun movedTo(position: Vector2F): Ray = copy(origin = position)

    private inline fun rotatedByImpl(rotation: ComplexF): Ray {
        val (dirX: Float, dirY: Float) = direction
        val (rotR: Float, rotI: Float) = rotation

        return copy(
            direction = Vector2F(dirX * rotR - dirY * rotI, dirY * rotR + dirX * rotI)
                .normalizedOrElse(Vector2F(1f, 0f))
        )
    }

    override fun rotatedBy(rotation: AngleF): Ray = rotatedByImpl(ComplexF.fromAngle(rotation))

    override fun rotatedBy(rotation: ComplexF): Ray = rotatedByImpl(rotation)

    private inline fun rotatedToImpl(orientation: ComplexF): Ray =
        copy(direction = orientation.normalizedOrElse(ComplexF.ONE).toVector2F())

    override fun rotatedTo(orientation: AngleF): Ray =
        rotatedToImpl(ComplexF.fromAngle(orientation))

    override fun rotatedTo(orientation: ComplexF): Ray = rotatedToImpl(orientation)

    private inline fun rotatedAroundPointByImpl(point: Vector2F, rotation: ComplexF): Ray {
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
            ).normalizedOrElse(Vector2F(1f, 0f))
        )
    }

    override fun rotatedAroundPointBy(point: Vector2F, rotation: AngleF): Ray =
        rotatedAroundPointByImpl(point, ComplexF.fromAngle(rotation))

    override fun rotatedAroundPointBy(point: Vector2F, rotation: ComplexF): Ray =
        rotatedAroundPointByImpl(point, rotation)

    private inline fun rotatedAroundPointToImpl(point: Vector2F, orientation: ComplexF): Ray {
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
                    .normalizedOrElse(Vector2F(1f, 0f))
            )
        } else {
            return copy(direction = orientation.normalizedOrElse(ComplexF.ONE).toVector2F())
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

    private inline fun transformedByImpl(displacement: Vector2F, rotation: ComplexF): Ray {
        val (dirX: Float, dirY: Float) = direction
        val (rotR: Float, rotI: Float) = rotation

        return copy(
            origin = origin + displacement,
            direction = Vector2F(dirX * rotR - dirY * rotI, dirY * rotR + dirX * rotI)
                .normalizedOrElse(Vector2F(1f, 0f))
        )
    }

    override fun transformedBy(displacement: Vector2F, rotation: AngleF): Ray =
        transformedByImpl(displacement, ComplexF.fromAngle(rotation))

    override fun transformedBy(displacement: Vector2F, rotation: ComplexF): Ray =
        transformedByImpl(displacement, rotation)

    private inline fun transformedByImpl(
        displacement: Vector2F, rotation: ComplexF, scaleFactor: Float
    ): Ray {
        val (dirX: Float, dirY: Float) = direction
        val scaleFactorSign: Float = 1f.withSign(scaleFactor)
        val rotR: Float = rotation.real * scaleFactorSign
        val rotI: Float = rotation.imaginary * scaleFactorSign

        return copy(
            origin = origin + displacement,
            direction = Vector2F(dirX * rotR - dirY * rotI, dirY * rotR + dirX * rotI)
                .normalizedOrElse(Vector2F(1f, 0f))
        )
    }

    override fun transformedBy(displacement: Vector2F, rotation: AngleF, scaleFactor: Float): Ray =
        transformedByImpl(displacement, ComplexF.fromAngle(rotation), scaleFactor)

    override fun transformedBy(
        displacement: Vector2F, rotation: ComplexF, scaleFactor: Float
    ): Ray = transformedByImpl(displacement, rotation, scaleFactor)

    private inline fun transformedToImpl(position: Vector2F, orientation: ComplexF): Ray = copy(
        origin = position,
        direction = orientation.normalizedOrElse(ComplexF.ONE).toVector2F()
    )

    override fun transformedTo(position: Vector2F, orientation: AngleF): Ray =
        transformedToImpl(position, ComplexF.fromAngle(orientation))

    override fun transformedTo(position: Vector2F, orientation: ComplexF): Ray =
        transformedToImpl(position, orientation)

    /**
     * Returns a copy of this ray interpolated [to] other ray [by] a factor.
     *
     * @param to the ray to which this ray is interpolated.
     * @param by the interpolation factor which is expected to be in the range of `[0, 1]`.
     */
    fun interpolated(to: Ray, by: Float): Ray = copy(
        origin = Vector2F.lerp(origin, to.origin, by),
        direction = ComplexF
            .slerp(direction.toComplexF(), to.direction.toComplexF(), by)
            .normalizedOrElse(ComplexF.ONE)
            .toVector2F()
    )

    /** Returns the closest point on this ray to the given [point]. **/
    fun closestPointTo(point: Vector2F): Vector2F {
        val origin: Vector2F = this.origin
        val direction: Vector2F = this.direction
        val op: Vector2F = point - origin
        val t: Float = op dot direction

        return if (t <= 0f) origin
        else origin + direction * t
    }

    /** Returns `true` if this ray intersects the given [annulus]. **/
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

    /** Returns `true` if this ray intersects the given [circle]. **/
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

    /** Returns `true` if this ray intersects the given [lineSegment]. **/
    fun intersects(lineSegment: LineSegment): Boolean {
        val (oX: Float, oY: Float) = origin
        val (dirX: Float, dirY: Float) = direction
        val (cX: Float, cY: Float) = lineSegment.center
        val (oR: Float, oI: Float) = lineSegment.orientation
        val halfLength: Float = lineSegment.length * 0.5f
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

    /** Returns `true` if this ray intersects the given [ray]. **/
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
            val dirADotDirB: Float = dirAX * dirBX + dirAY * dirBY
            val areSameDirs: Boolean = dirADotDirB >= 0f

            return if (areSameDirs) (dirAX * dy - dirAY * dx).absoluteValue < 0.00001f
            else (dirAX * dx + dirAY * dy) >= 0f

        }
        val nomX: Float = dy * dirBX - dx * dirBY
        val nomY: Float = dy * dirAX - dx * dirAY

        return (nomX * dirBCrossDirA >= 0f) and (nomY * dirBCrossDirA >= 0f)
    }

    /** Returns `true` if this ray intersects the given [rectangle]. **/
    fun intersects(rectangle: Rectangle): Boolean {
        val (rectCX: Float, rectCY: Float) = rectangle.center
        val (rectOR: Float, rectOI: Float) = rectangle.orientation
        val halfWidth: Float = rectangle.width * 0.5f
        val halfHeight: Float = rectangle.height * 0.5f
        val aabbMinX: Float = rectCX - halfWidth
        val aabbMinY: Float = rectCY - halfHeight
        val aabbMaxX: Float = rectCX + halfWidth
        val aabbMaxY: Float = rectCY + halfHeight

        val (rayCX: Float, rayCY: Float) = origin
        val (rayDirX: Float, rayDirY: Float) = direction
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

    /** Returns `true` if this ray intersects the given [polygon]. **/
    fun intersects(polygon: RegularPolygon): Boolean {
        val (polyCX: Float, polyCY: Float) = polygon.center
        val (polyOR: Float, polyOI: Float) = polygon.orientation
        val sideCount: Int = polygon.sideCount
        val sideLength: Float = polygon.sideLength
        val halfSideLength: Float = sideLength * 0.5f
        val (rayCX: Float, rayCY: Float) = origin
        val (rayDirX: Float, rayDirY: Float) = direction
        val rayCPolyCX: Float = polyCX - rayCX
        val rayCPolyCY: Float = polyCY - rayCY
        val oRayCX: Float = -rayCPolyCX * polyOR - rayCPolyCY * polyOI
        val oRayCY: Float = rayCPolyCX * polyOI - rayCPolyCY * polyOR
        val oRayDirX: Float = rayDirX * polyOR + rayDirY * polyOI
        val oRayDirY: Float = rayDirY * polyOR - rayDirX * polyOI

        if (sideCount == 2) {
            val aoX: Float = oRayCX - halfSideLength
            val dirCrossAB: Float = sideLength * oRayDirY
            val areParallel: Boolean = dirCrossAB.absoluteValue < 0.00001f

            if (areParallel) {
                val boX: Float = oRayCX + halfSideLength
                val factor: Float = oRayCY * oRayDirY
                val dirDotAO: Float = aoX * oRayDirX + factor
                val dirDotBO: Float = boX * oRayDirX + factor
                val dirCrossAO: Float = oRayDirX * oRayCY - oRayDirY * aoX

                return (dirCrossAO.absoluteValue < 0.00001f) and ((dirDotAO <= 0f) or (dirDotBO <= 0f))
            }
            val detABReciprocal: Float = 1f / dirCrossAB
            val t1: Float = -oRayCY * sideLength * detABReciprocal

            if (t1 >= 0f) {
                val t2: Float = (oRayCY * oRayDirX - aoX * oRayDirY) * detABReciprocal

                return (t2 >= 0f) and (t2 <= 1f)
            }
            return false
        }
        val fullAngle = (2.0 * PI).toFloat()
        val exteriorAngle: Float = fullAngle / sideCount
        val halfExteriorAngle: Float = exteriorAngle * 0.5f
        val inradius: Float = halfSideLength / tan(halfExteriorAngle)
        val minusORayCDotORayDir: Float = -oRayCX * oRayDirX - oRayCY * oRayDirY
        val closestPointOnORayX: Float
        val closestPointOnORayY: Float

        if (minusORayCDotORayDir <= 0f) {
            closestPointOnORayX = oRayCX
            closestPointOnORayY = oRayCY
        } else {
            closestPointOnORayX = oRayCX + oRayDirX * minusORayCDotORayDir
            closestPointOnORayY = oRayCY + oRayDirY * minusORayCDotORayDir
        }
        val closestPointOnORayLength: Float = sqrt(
            closestPointOnORayX * closestPointOnORayX + closestPointOnORayY * closestPointOnORayY
        )
        if (closestPointOnORayLength <= inradius) {
            return true
        }
        val sinHalfExteriorAngle: Float = sin(halfExteriorAngle)
        val circumradius: Float = halfSideLength / sinHalfExteriorAngle

        if (closestPointOnORayLength > circumradius) {
            return false
        }
        val rightAngle = (PI * 0.5).toFloat()
        val evenSidedFactor = ((sideCount + 1) and 1).toFloat()
        val evenSidedHalfExteriorAngle: Float = evenSidedFactor * halfExteriorAngle
        val cosP1: Float = cos(evenSidedHalfExteriorAngle)
        val sinP1: Float = evenSidedFactor * sinHalfExteriorAngle
        // p1 = ComplexF.ONE *
        //      ComplexF.fromAngle(AngleF.fromDegrees(-90f)) *
        //      ComplexF.fromAngle(AngleF.fromDegrees(evenSidedHalfExteriorAngle)) *
        //      ComplexF(closestPointOnORayX, closestPointOnORayY)
        val p1X: Float = closestPointOnORayY * cosP1 + closestPointOnORayX * sinP1
        val p1Y: Float = closestPointOnORayY * sinP1 - closestPointOnORayX * cosP1
        val p1Angle: Float = atan2(p1Y, p1X) + 0.001f
        val p1AnglePositive: Float = p1Angle + fullAngle
        val index = (p1AnglePositive / exteriorAngle).toInt()
        val angleA: Float = evenSidedHalfExteriorAngle - exteriorAngle * index - rightAngle
        val angleB: Float = angleA - exteriorAngle
        val cosA: Float = cos(angleA)
        val sinA: Float = sin(angleA)
        val cosB: Float = cos(angleB)
        val sinB: Float = sin(angleB)
        val aX: Float = cosA * circumradius
        val aY: Float = -sinA * circumradius
        val bX: Float = cosB * circumradius
        val bY: Float = -sinB * circumradius
        val aoX: Float = oRayCX - aX
        val aoY: Float = oRayCY - aY
        val abX: Float = bX - aX
        val abY: Float = bY - aY
        val dirCrossAB: Float = abY * oRayDirX - abX * oRayDirY
        val areParallel: Boolean = dirCrossAB.absoluteValue < 0.00001f

        if (areParallel) {
            return false
        }
        val detABReciprocal: Float = 1f / dirCrossAB
        val t1: Float = (aoY * abX - aoX * abY) * detABReciprocal

        if (t1 >= 0f) {
            val t2: Float = (aoY * oRayDirX - aoX * oRayDirY) * detABReciprocal

            return (t2 >= 0f) and (t2 <= 1f)
        }
        return false
    }

    /** Returns `true` if this ray intersects the given [triangle]. **/
    fun intersects(triangle: RegularTriangle): Boolean {
        val (oX: Float, oY: Float) = origin
        val (dirX: Float, dirY: Float) = direction
        val (triangleCX: Float, triangleCY: Float) = triangle.center
        val (triangleOR: Float, triangleOI: Float) = triangle.orientation
        val sideLength: Float = triangle.sideLength
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

    /** Returns `true` if this ray intersects the given [rectangle]. **/
    fun intersects(rectangle: RoundedRectangle): Boolean {
        val (rectCX: Float, rectCY: Float) = rectangle.center
        val (rectOR: Float, rectOI: Float) = rectangle.orientation
        val halfWidth: Float = rectangle.width * 0.5f
        val halfHeight: Float = rectangle.height * 0.5f
        val cornerRadius: Float = rectangle.cornerRadius
        val halfWidthMinusRadius: Float = halfWidth - cornerRadius
        val halfHeightMinusRadius: Float = halfHeight - cornerRadius
        val aabb1MinX: Float = rectCX - halfWidth
        val aabb1MinY: Float = rectCY - halfHeightMinusRadius
        val aabb1MaxX: Float = rectCX + halfWidth
        val aabb1MaxY: Float = rectCY + halfHeightMinusRadius
        val aabb2MinX: Float = rectCX - halfWidthMinusRadius
        val aabb2MinY: Float = rectCY - halfHeight
        val aabb2MaxX: Float = rectCX + halfWidthMinusRadius
        val aabb2MaxY: Float = rectCY + halfHeight

        val (rayCX: Float, rayCY: Float) = origin
        val (rayDirX: Float, rayDirY: Float) = direction
        val centersDiffX: Float = rayCX - rectCX
        val centersDiffY: Float = rayCY - rectCY
        val orientedRayCX: Float = centersDiffX * rectOR + centersDiffY * rectOI + rectCX
        val orientedRayCY: Float = centersDiffY * rectOR - centersDiffX * rectOI + rectCY
        val orientedRayDirX: Float = rayDirX * rectOR + rayDirY * rectOI
        val orientedRayDirY: Float = rayDirY * rectOR - rayDirX * rectOI

        val ccArCDiffY: Float = aabb1MaxY - orientedRayCY
        val ccCrCDiffY: Float = aabb1MinY - orientedRayCY
        val dirReciprocalX: Float = 1f / orientedRayDirX
        val dirReciprocalY: Float = 1f / orientedRayDirY
        val tx11: Float = (aabb1MinX - orientedRayCX) * dirReciprocalX
        val tx12: Float = (aabb1MaxX - orientedRayCX) * dirReciprocalX
        val ty11: Float = ccCrCDiffY * dirReciprocalY
        val ty12: Float = ccArCDiffY * dirReciprocalY
        val tMax1: Float = max(min(tx11, tx12), min(ty11, ty12))
        val tMin1: Float = min(max(tx11, tx12), max(ty11, ty12))
        val intersectsAabb1: Boolean = (tMin1 >= 0f) and (tMax1 <= tMin1)

        if (intersectsAabb1) {
            return true
        }

        val ccArCDiffX: Float = aabb2MaxX - orientedRayCX
        val ccBrCDiffX: Float = aabb2MinX - orientedRayCX
        val tx21: Float = ccBrCDiffX * dirReciprocalX
        val tx22: Float = ccArCDiffX * dirReciprocalX
        val ty21: Float = (aabb2MinY - orientedRayCY) * dirReciprocalY
        val ty22: Float = (aabb2MaxY - orientedRayCY) * dirReciprocalY
        val tMax2: Float = max(min(tx21, tx22), min(ty21, ty22))
        val tMin2: Float = min(max(tx21, tx22), max(ty21, ty22))
        val intersectsAabb2: Boolean = (tMin2 >= 0f) and (tMax2 <= tMin2)

        if (intersectsAabb2) {
            return true
        }

        val tA1: Float = ccArCDiffX * orientedRayDirX
        val tA2: Float = ccArCDiffY * orientedRayDirY
        val tA: Float = tA1 + tA2
        val minusCcArCDiffX: Float = -ccArCDiffX
        val minusCcArCDiffY: Float = -ccArCDiffY
        val intersectsCircleA: Boolean = if (tA <= 0f) {
            sqrt(
                minusCcArCDiffX * minusCcArCDiffX + minusCcArCDiffY * minusCcArCDiffY
            ) <= cornerRadius
        } else {
            val cpCcADiffX: Float = minusCcArCDiffX + orientedRayDirX * tA
            val cpCcADiffY: Float = minusCcArCDiffY + orientedRayDirY * tA

            sqrt(cpCcADiffX * cpCcADiffX + cpCcADiffY * cpCcADiffY) <= cornerRadius
        }
        if (intersectsCircleA) {
            return true
        }

        val tB1: Float = ccBrCDiffX * orientedRayDirX
        val tB: Float = tB1 + tA2
        val minusCcBrCDiffX: Float = -ccBrCDiffX
        val intersectsCircleB: Boolean = if (tB <= 0f) {
            sqrt(
                minusCcBrCDiffX * minusCcBrCDiffX + minusCcArCDiffY * minusCcArCDiffY
            ) <= cornerRadius
        } else {
            val cpCcBDiffX: Float = minusCcBrCDiffX + orientedRayDirX * tB
            val cpCcBDiffY: Float = minusCcArCDiffY + orientedRayDirY * tB

            sqrt(cpCcBDiffX * cpCcBDiffX + cpCcBDiffY * cpCcBDiffY) <= cornerRadius
        }
        if (intersectsCircleB) {
            return true
        }

        val tC2: Float = ccCrCDiffY * orientedRayDirY
        val tC: Float = tB1 + tC2
        val minusCcCrCDiffY: Float = -ccCrCDiffY
        val intersectsCircleC: Boolean = if (tC <= 0f) {
            sqrt(
                minusCcBrCDiffX * minusCcBrCDiffX + minusCcCrCDiffY * minusCcCrCDiffY
            ) <= cornerRadius
        } else {
            val cpCcCDiffX: Float = minusCcBrCDiffX + orientedRayDirX * tC
            val cpCcCDiffY: Float = minusCcCrCDiffY + orientedRayDirY * tC

            sqrt(cpCcCDiffX * cpCcCDiffX + cpCcCDiffY * cpCcCDiffY) <= cornerRadius
        }
        if (intersectsCircleC) {
            return true
        }

        val tD: Float = tA1 + tC2
        val intersectsCircleD: Boolean = if (tD <= 0f) {
            sqrt(
                minusCcArCDiffX * minusCcArCDiffX + minusCcCrCDiffY * minusCcCrCDiffY
            ) <= cornerRadius
        } else {
            val cpCcDDiffX: Float = minusCcArCDiffX + orientedRayDirX * tD
            val cpCcDDiffY: Float = minusCcCrCDiffY + orientedRayDirY * tD

            sqrt(cpCcDDiffX * cpCcDDiffX + cpCcDDiffY * cpCcDDiffY) <= cornerRadius
        }
        return intersectsCircleD
    }

    /** Returns `true` if this ray intersects the given [square]. **/
    fun intersects(square: Square): Boolean {
        val (rectCX: Float, rectCY: Float) = square.center
        val (rectOR: Float, rectOI: Float) = square.orientation
        val halfSideLength: Float = square.sideLength * 0.5f
        val aabbMinX: Float = rectCX - halfSideLength
        val aabbMinY: Float = rectCY - halfSideLength
        val aabbMaxX: Float = rectCX + halfSideLength
        val aabbMaxY: Float = rectCY + halfSideLength

        val (rayCX: Float, rayCY: Float) = origin
        val (rayDirX: Float, rayDirY: Float) = direction
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

    /** Returns `true` if this ray intersects the given [triangle]. **/
    fun intersects(triangle: Triangle): Boolean {
        val (oR: Float, oI: Float) = triangle.orientation
        val (opAX: Float, opAY: Float) = triangle.originPointA
        val (opBX: Float, opBY: Float) = triangle.originPointB
        val (opCX: Float, opCY: Float) = triangle.originPointC
        val (oX: Float, oY: Float) = origin - triangle.centroid
        val (dirX: Float, dirY: Float) = direction
        val aX: Float = (opAX * oR - opAY * oI)
        val aY: Float = (opAY * oR + opAX * oI)
        val bX: Float = (opBX * oR - opBY * oI)
        val bY: Float = (opBY * oR + opBX * oI)
        val cX: Float = (opCX * oR - opCY * oI)
        val cY: Float = (opCY * oR + opCX * oI)
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

    /** Returns `true` if this ray approximately contains the given [point]. **/
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

    /**
     * Returns a copy of this instance with specified properties changed.
     *
     * @param direction the value is expected to be [normalized][Vector2F.normalized].
     */
    fun copy(origin: Vector2F = this.origin, direction: Vector2F = this.direction): Ray

    /** Returns the [origin] of this ray. **/
    operator fun component1(): Vector2F = origin

    /** Returns the [direction] of this ray. **/
    operator fun component2(): Vector2F = direction
}