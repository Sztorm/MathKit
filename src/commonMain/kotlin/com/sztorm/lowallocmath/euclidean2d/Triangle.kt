package com.sztorm.lowallocmath.euclidean2d

import com.sztorm.lowallocmath.AngleF
import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F
import com.sztorm.lowallocmath.Vector2FIterator
import kotlin.math.abs
import kotlin.math.absoluteValue
import kotlin.math.sqrt

/**
 * Creates a new instance of [Triangle].
 *
 * @param orientation the value is expected to be [normalized][ComplexF.normalized].
 */
fun Triangle(
    centroid: Vector2F,
    orientation: ComplexF,
    originPointA: Vector2F,
    originPointB: Vector2F,
    originPointC: Vector2F
): Triangle = MutableTriangle(
    centroid,
    orientation,
    originPointA,
    originPointB,
    originPointC
)

/** Creates a new instance of [Triangle]. **/
fun Triangle(pointA: Vector2F, pointB: Vector2F, pointC: Vector2F): Triangle =
    MutableTriangle(pointA, pointB, pointC)

/**
 * Represents a transformable triangle in a two-dimensional Euclidean space.
 *
 * Implementations that use default-implemented members of this interface must make sure that the
 * properties [centroid], [orientation], [originPointA], [originPointB], [originPointC] and the
 * [copy] method are independent of other members and the computational complexity of these members
 * is trivial.
 */
interface Triangle : TriangleShape, Transformable {
    /**
     * Returns the centroid of this triangle. Centroid is the intersection point of the triangle's
     * medians. Centroid is the triangle's center of mass.
     */
    val centroid: Vector2F

    /**
     *  Returns the point _A_ in reference to the triangle's [centroid] and [orientation] of
     *  [ComplexF.ONE].
     */
    val originPointA: Vector2F

    /**
     *  Returns the point _B_ in reference to the triangle's [centroid] and [orientation] of
     *  [ComplexF.ONE].
     */
    val originPointB: Vector2F

    /**
     *  Returns the point _C_ in reference to the triangle's [centroid] and [orientation] of
     *  [ComplexF.ONE].
     */
    val originPointC: Vector2F

    /** Returns the point _A_ of this triangle. **/
    val pointA: Vector2F
        get() {
            val (cX: Float, cY: Float) = centroid
            val (oR: Float, oI: Float) = orientation
            val (opAX: Float, opAY: Float) = originPointA

            return Vector2F((opAX * oR - opAY * oI) + cX, (opAY * oR + opAX * oI) + cY)
        }

    /** Returns the point _B_ of this triangle. **/
    val pointB: Vector2F
        get() {
            val (cX: Float, cY: Float) = centroid
            val (oR: Float, oI: Float) = orientation
            val (opBX: Float, opBY: Float) = originPointB

            return Vector2F((opBX * oR - opBY * oI) + cX, (opBY * oR + opBX * oI) + cY)
        }

    /** Returns the point _C_ of this triangle. **/
    val pointC: Vector2F
        get() {
            val (cX: Float, cY: Float) = centroid
            val (oR: Float, oI: Float) = orientation
            val (opCX: Float, opCY: Float) = originPointC

            return Vector2F((opCX * oR - opCY * oI) + cX, (opCY * oR + opCX * oI) + cY)
        }

    /**
     * Returns the incenter of this triangle. Incenter is the intersection point of the triangle's
     * angle bisectors. Incenter is the center of the triangle's inscribed circle.
     */
    val incenter: Vector2F
        get() {
            val (cX: Float, cY: Float) = centroid
            val (oR: Float, oI: Float) = orientation
            val (opAX: Float, opAY: Float) = originPointA
            val (opBX: Float, opBY: Float) = originPointB
            val (opCX: Float, opCY: Float) = originPointC
            val opABX: Float = opBX - opAX
            val opABY: Float = opBY - opAY
            val opBCX: Float = opCX - opBX
            val opBCY: Float = opCY - opBY
            val opACX: Float = opCX - opAX
            val opACY: Float = opCY - opAY
            val abSide: Float = sqrt(opABX * opABX + opABY * opABY)
            val bcSide: Float = sqrt(opBCX * opBCX + opBCY * opBCY)
            val acSide: Float = sqrt(opACX * opACX + opACY * opACY)
            val factor: Float = 1f / (abSide + bcSide + acSide)
            val icX: Float = (bcSide * opAX + acSide * opBX + abSide * opCX) * factor
            val icY: Float = (bcSide * opAY + acSide * opBY + abSide * opCY) * factor

            return Vector2F((icX * oR - icY * oI) + cX, (icY * oR + icX * oI) + cY)
        }

    /**
     * Returns the circumcenter of this triangle. Circumcenter is the intersection point of the
     * perpendicular bisectors of the triangle's sides. Circumcenter is the center of the
     * triangle's circumscribed circle.
     */
    val circumcenter: Vector2F
        get() {
            val (cX: Float, cY: Float) = centroid
            val (oR: Float, oI: Float) = orientation
            val (opAX: Float, opAY: Float) = originPointA
            val (opBX: Float, opBY: Float) = originPointB
            val (opCX: Float, opCY: Float) = originPointC
            val opASquaredMagnitude: Float = opAX * opAX + opAY * opAY
            val opBSquaredMagnitude: Float = opBX * opBX + opBY * opBY
            val opCSquaredMagnitude: Float = opCX * opCX + opCY * opCY
            val bYcYDiff: Float = opBY - opCY
            val cXbXDiff: Float = opCX - opBX
            val factor: Float =
                0.5f / (opAX * bYcYDiff + opAY * cXbXDiff + opBX * opCY - opBY * opCX)
            val xDet: Float =
                opASquaredMagnitude * bYcYDiff +
                        opBSquaredMagnitude * (opCY - opAY) +
                        opCSquaredMagnitude * (opAY - opBY)
            val yDet: Float =
                opASquaredMagnitude * cXbXDiff +
                        opBSquaredMagnitude * (opAX - opCX) +
                        opCSquaredMagnitude * (opBX - opAX)
            val ccX: Float = xDet * factor
            val ccY: Float = yDet * factor

            return Vector2F((ccX * oR - ccY * oI) + cX, (ccY * oR + ccX * oI) + cY)
        }

    /**
     * Returns the orthocenter of this triangle. Orthocenter is the intersection point of the
     * triangle's altitudes.
     */
    val orthocenter: Vector2F
        get() {
            val (cX: Float, cY: Float) = centroid
            val (oR: Float, oI: Float) = orientation
            val (opAX: Float, opAY: Float) = originPointA
            val (opBX: Float, opBY: Float) = originPointB
            val (opCX: Float, opCY: Float) = originPointC
            val opASquaredMagnitude: Float = opAX * opAX + opAY * opAY
            val opBSquaredMagnitude: Float = opBX * opBX + opBY * opBY
            val opCSquaredMagnitude: Float = opCX * opCX + opCY * opCY
            val bYcYDiff: Float = opBY - opCY
            val cXbXDiff: Float = opCX - opBX
            val factor: Float =
                1f / (opAX * bYcYDiff + opAY * cXbXDiff + opBX * opCY - opBY * opCX)
            val xDet: Float =
                opASquaredMagnitude * bYcYDiff +
                        opBSquaredMagnitude * (opCY - opAY) +
                        opCSquaredMagnitude * (opAY - opBY)
            val yDet: Float =
                opASquaredMagnitude * cXbXDiff +
                        opBSquaredMagnitude * (opAX - opCX) +
                        opCSquaredMagnitude * (opBX - opAX)
            val ccX: Float = xDet * factor
            val ccY: Float = yDet * factor

            return Vector2F(cX - (ccX * oR - ccY * oI), cY - (ccY * oR + ccX * oI))
        }

    override val area: Float
        get() {
            val (opAX: Float, opAY: Float) = originPointA
            val (opBX: Float, opBY: Float) = originPointB
            val (opCX: Float, opCY: Float) = originPointC

            return 0.5f * abs((opAX - opCX) * (opBY - opCY) - (opBX - opCX) * (opAY - opCY))
        }

    override val perimeter: Float
        get() {
            val (opAX: Float, opAY: Float) = originPointA
            val (opBX: Float, opBY: Float) = originPointB
            val (opCX: Float, opCY: Float) = originPointC
            val abX: Float = opBX - opAX
            val abY: Float = opBY - opAY
            val bcX: Float = opCX - opBX
            val bcY: Float = opCY - opBY
            val acX: Float = opCX - opAX
            val acY: Float = opCY - opAY
            val lengthAB: Float = sqrt(abX * abX + abY * abY)
            val lengthBC: Float = sqrt(bcX * bcX + bcY * bcY)
            val lengthAC: Float = sqrt(acX * acX + acY * acY)

            return lengthAB + lengthBC + lengthAC
        }

    override val sideLengthAB: Float
        get() {
            val (opAX: Float, opAY: Float) = originPointA
            val (opBX: Float, opBY: Float) = originPointB
            val abX: Float = opBX - opAX
            val abY: Float = opBY - opAY

            return sqrt(abX * abX + abY * abY)
        }

    override val sideLengthBC: Float
        get() {
            val (opBX: Float, opBY: Float) = originPointB
            val (opCX: Float, opCY: Float) = originPointC
            val bcX: Float = opCX - opBX
            val bcY: Float = opCY - opBY

            return sqrt(bcX * bcX + bcY * bcY)
        }

    override val sideLengthAC: Float
        get() {
            val (opAX: Float, opAY: Float) = originPointA
            val (opCX: Float, opCY: Float) = originPointC
            val acX: Float = opCX - opAX
            val acY: Float = opCY - opAY

            return sqrt(acX * acX + acY * acY)
        }

    /**
     * Returns the position of this object in reference to the origin of [Vector2F.ZERO].
     *
     * This property is equal to [centroid].
     */
    override val position: Vector2F
        get() = centroid

    override fun movedBy(displacement: Vector2F): Triangle =
        copy(centroid = centroid + displacement)

    override fun movedTo(position: Vector2F): Triangle = copy(centroid = position)

    private inline fun rotatedByImpl(rotation: ComplexF): Triangle =
        copy(orientation = (orientation * rotation).normalizedOrElse(ComplexF.ONE))

    override fun rotatedBy(rotation: AngleF): Triangle =
        rotatedByImpl(ComplexF.fromAngle(rotation))

    override fun rotatedBy(rotation: ComplexF): Triangle = rotatedByImpl(rotation)

    private inline fun rotatedToImpl(orientation: ComplexF): Triangle =
        copy(orientation = orientation.normalizedOrElse(ComplexF.ONE))

    override fun rotatedTo(orientation: AngleF): Triangle =
        rotatedToImpl(ComplexF.fromAngle(orientation))

    override fun rotatedTo(orientation: ComplexF): Triangle = rotatedToImpl(orientation)

    private inline fun rotatedAroundPointByImpl(point: Vector2F, rotation: ComplexF): Triangle {
        val (cX: Float, cY: Float) = centroid
        val (oR: Float, oI: Float) = orientation
        val (pX: Float, pY: Float) = point
        val (rotR: Float, rotI: Float) = rotation
        val cpDiffX: Float = cX - pX
        val cpDiffY: Float = cY - pY
        val centroidX: Float = cpDiffX * rotR - cpDiffY * rotI + pX
        val centroidY: Float = cpDiffY * rotR + cpDiffX * rotI + pY
        val orientationR: Float = oR * rotR - oI * rotI
        val orientationI: Float = oI * rotR + oR * rotI

        return copy(
            centroid = Vector2F(centroidX, centroidY),
            orientation = ComplexF(orientationR, orientationI).normalizedOrElse(ComplexF.ONE)
        )
    }

    override fun rotatedAroundPointBy(point: Vector2F, rotation: AngleF): Triangle =
        rotatedAroundPointByImpl(point, ComplexF.fromAngle(rotation))

    override fun rotatedAroundPointBy(point: Vector2F, rotation: ComplexF): Triangle =
        rotatedAroundPointByImpl(point, rotation)

    private inline fun rotatedAroundPointToImpl(point: Vector2F, orientation: ComplexF): Triangle {
        val centroid: Vector2F = centroid
        val (startOR: Float, startOI: Float) = this.orientation
        val (cX: Float, cY: Float) = centroid
        val (pX: Float, pY: Float) = point
        val (oR: Float, oI: Float) = orientation
        val cpDiffX: Float = cX - pX
        val cpDiffY: Float = cY - pY
        val centroidToPointDist: Float = sqrt(cpDiffX * cpDiffX + cpDiffY * cpDiffY)

        return if (centroidToPointDist > 0.00001f) {
            val centroidToPointDistReciprocal: Float = 1f / centroidToPointDist
            val pointRotR: Float = cpDiffX * centroidToPointDistReciprocal
            val pointRotI: Float = cpDiffY * centroidToPointDistReciprocal
            val pRotTimesStartOR: Float = pointRotR * startOR + pointRotI * startOI
            val pRotTimesStartOI: Float = pointRotR * startOI - pointRotI * startOR
            val centroidX: Float = oR * centroidToPointDist + pX
            val centroidY: Float = oI * centroidToPointDist + pY
            val orientationR: Float = pRotTimesStartOR * oR - pRotTimesStartOI * oI
            val orientationI: Float = pRotTimesStartOI * oR + pRotTimesStartOR * oI

            copy(
                centroid = Vector2F(centroidX, centroidY),
                orientation = ComplexF(orientationR, orientationI).normalizedOrElse(ComplexF.ONE)
            )
        } else copy(
            centroid,
            orientation = orientation.normalizedOrElse(ComplexF.ONE)
        )
    }

    override fun rotatedAroundPointTo(point: Vector2F, orientation: AngleF): Triangle =
        rotatedAroundPointToImpl(point, ComplexF.fromAngle(orientation))

    override fun rotatedAroundPointTo(point: Vector2F, orientation: ComplexF): Triangle =
        rotatedAroundPointToImpl(point, orientation)

    override fun scaledBy(factor: Float): Triangle {
        val (opAX: Float, opAY: Float) = originPointA
        val (opBX: Float, opBY: Float) = originPointB
        val (opCX: Float, opCY: Float) = originPointC
        val originPointAX: Float = opAX * factor
        val originPointAY: Float = opAY * factor
        val originPointBX: Float = opBX * factor
        val originPointBY: Float = opBY * factor
        val originPointCX: Float = opCX * factor
        val originPointCY: Float = opCY * factor

        return copy(
            originPointA = Vector2F(originPointAX, originPointAY),
            originPointB = Vector2F(originPointBX, originPointBY),
            originPointC = Vector2F(originPointCX, originPointCY),
        )
    }

    override fun dilatedBy(point: Vector2F, factor: Float): Triangle {
        val (cX: Float, cY: Float) = centroid
        val (opAX: Float, opAY: Float) = originPointA
        val (opBX: Float, opBY: Float) = originPointB
        val (opCX: Float, opCY: Float) = originPointC
        val (pX: Float, pY: Float) = point
        val f: Float = 1f - factor
        val centroidX: Float = cX * factor + pX * f
        val centroidY: Float = cY * factor + pY * f
        val originPointAX: Float = opAX * factor
        val originPointAY: Float = opAY * factor
        val originPointBX: Float = opBX * factor
        val originPointBY: Float = opBY * factor
        val originPointCX: Float = opCX * factor
        val originPointCY: Float = opCY * factor

        return copy(
            centroid = Vector2F(centroidX, centroidY),
            originPointA = Vector2F(originPointAX, originPointAY),
            originPointB = Vector2F(originPointBX, originPointBY),
            originPointC = Vector2F(originPointCX, originPointCY),
        )
    }

    private inline fun transformedByImpl(
        displacement: Vector2F, rotation: ComplexF
    ): Triangle = copy(
        centroid = centroid + displacement,
        orientation = (orientation * rotation).normalizedOrElse(ComplexF.ONE),
    )

    override fun transformedBy(displacement: Vector2F, rotation: AngleF): Triangle =
        transformedByImpl(displacement, ComplexF.fromAngle(rotation))

    override fun transformedBy(displacement: Vector2F, rotation: ComplexF): Triangle =
        transformedByImpl(displacement, rotation)

    private inline fun transformedByImpl(
        displacement: Vector2F, rotation: ComplexF, scaleFactor: Float
    ): Triangle = copy(
        centroid = centroid + displacement,
        orientation = (orientation * rotation).normalizedOrElse(ComplexF.ONE),
        originPointA = originPointA * scaleFactor,
        originPointB = originPointB * scaleFactor,
        originPointC = originPointC * scaleFactor
    )

    override fun transformedBy(
        displacement: Vector2F, rotation: AngleF, scaleFactor: Float
    ): Triangle = transformedByImpl(displacement, ComplexF.fromAngle(rotation), scaleFactor)

    override fun transformedBy(
        displacement: Vector2F, rotation: ComplexF, scaleFactor: Float
    ): Triangle = transformedByImpl(displacement, rotation, scaleFactor)

    private inline fun transformedToImpl(
        position: Vector2F, orientation: ComplexF
    ): Triangle = copy(
        centroid = position,
        orientation = orientation.normalizedOrElse(ComplexF.ONE),
    )

    override fun transformedTo(position: Vector2F, orientation: AngleF): Triangle =
        transformedToImpl(position, ComplexF.fromAngle(orientation))

    override fun transformedTo(position: Vector2F, orientation: ComplexF): Triangle =
        transformedToImpl(position, orientation)

    /**
     * Returns a copy of this triangle interpolated [to] other triangle [by] a factor.
     *
     * @param to the triangle to which this triangle is interpolated.
     * @param by the interpolation factor which is expected to be in the range of `[0, 1]`.
     */
    fun interpolated(to: Triangle, by: Float): Triangle {
        val fOpA: Vector2F = originPointA
        val fOpB: Vector2F = originPointB
        val fOpC: Vector2F = originPointC
        val tOpA: Vector2F = to.originPointA
        val tOpB: Vector2F = to.originPointB
        val tOpC: Vector2F = to.originPointC
        val fOpAOrientation: ComplexF = fOpA.toComplexF()
            .normalizedOrElse(ComplexF.ONE)
        val tOpAOrientation: ComplexF = tOpA.toComplexF()
            .normalizedOrElse(ComplexF.ONE)
        val fromRotation: ComplexF = fOpAOrientation.conjugate
        val toRotation: ComplexF = tOpAOrientation.conjugate
        val fromOpA: Vector2F = fOpA * fromRotation
        val fromOpB: Vector2F = fOpB * fromRotation
        val fromOpC: Vector2F = fOpC * fromRotation
        val toOpA: Vector2F = tOpA * toRotation
        val toOpB: Vector2F = tOpB * toRotation
        val toOpC: Vector2F = tOpC * toRotation
        val originPointA = Vector2F.lerp(fromOpA, toOpA, by)
        val originPointB = Vector2F.lerp(fromOpB, toOpB, by)
        val originPointC = Vector2F.lerp(fromOpC, toOpC, by)
        val centroid = Vector2F.lerp(centroid, to.centroid, by)
        val orientation = ComplexF
            .slerp(fOpAOrientation * orientation, tOpAOrientation * to.orientation, by)
            .normalizedOrElse(ComplexF.ONE)

        return copy(centroid, orientation, originPointA, originPointB, originPointC)
    }

    /** Returns the closest point on this triangle to the given [point]. **/
    fun closestPointTo(point: Vector2F): Vector2F {
        val centroid: Vector2F = centroid
        val (oR: Float, oI: Float) = orientation
        val (opAX: Float, opAY: Float) = originPointA
        val (opBX: Float, opBY: Float) = originPointB
        val (opCX: Float, opCY: Float) = originPointC
        val aX: Float = (opAX * oR - opAY * oI)
        val aY: Float = (opAY * oR + opAX * oI)
        val bX: Float = (opBX * oR - opBY * oI)
        val bY: Float = (opBY * oR + opBX * oI)
        val cX: Float = (opCX * oR - opCY * oI)
        val cY: Float = (opCY * oR + opCX * oI)
        val a = Vector2F(aX, aY)
        val b = Vector2F(bX, bY)
        val c = Vector2F(cX, cY)
        val p = point - centroid
        val ab: Vector2F = b - a
        val ac: Vector2F = c - a
        val ap: Vector2F = p - a
        val d1: Float = ab dot ap
        val d2: Float = ac dot ap

        if (d1 <= 0f && d2 <= 0f) {
            return a + centroid
        }
        val bp: Vector2F = p - b
        val d3: Float = ab dot bp
        val d4: Float = ac dot bp

        if (d3 >= 0f && d4 <= d3) {
            return b + centroid
        }
        val cp: Vector2F = p - c
        val d5: Float = ab dot cp
        val d6: Float = ac dot cp

        if (d6 >= 0f && d5 <= d6) {
            return c + centroid
        }
        val vc: Float = d1 * d4 - d3 * d2

        if (vc <= 0f && d1 >= 0f && d3 <= 0f) {
            val v: Float = d1 / (d1 - d3)
            return a + ab * v + centroid
        }
        val vb: Float = d5 * d2 - d1 * d6

        if (vb <= 0f && d2 >= 0f && d6 <= 0f) {
            val v: Float = d2 / (d2 - d6)
            return a + ac * v + centroid
        }
        val va: Float = d3 * d6 - d5 * d4

        if (va <= 0f && (d4 - d3) >= 0f && (d5 - d6) >= 0f) {
            val v: Float = (d4 - d3) / ((d4 - d3) + (d5 - d6))
            return b + (c - b) * v + centroid
        }
        val denominator: Float = 1f / (va + vb + vc)
        val v: Float = vb * denominator
        val w: Float = vc * denominator

        return a + ab * v + ac * w + centroid
    }

    /** Returns `true` if this triangle intersects the given [ray]. **/
    fun intersects(ray: Ray): Boolean {
        val (oR: Float, oI: Float) = orientation
        val (opAX: Float, opAY: Float) = originPointA
        val (opBX: Float, opBY: Float) = originPointB
        val (opCX: Float, opCY: Float) = originPointC
        val (oX: Float, oY: Float) = ray.origin - centroid
        val (dirX: Float, dirY: Float) = ray.direction
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

    /** Returns `true` if this triangle contains the given [point]. **/
    operator fun contains(point: Vector2F): Boolean {
        val (opAX: Float, opAY: Float) = originPointA
        val (opBX: Float, opBY: Float) = originPointB
        val (opCX: Float, opCY: Float) = originPointC
        val (opX: Float, opY: Float) = (point - centroid) * orientation.conjugate
        val abp: Boolean = ((opX - opAX) * (opAY - opBY) + (opY - opAY) * (opBX - opAX)) >= 0f
        val bcp: Boolean = ((opX - opBX) * (opBY - opCY) + (opY - opBY) * (opCX - opBX)) >= 0f
        val acp: Boolean = ((opX - opCX) * (opCY - opAY) + (opY - opCY) * (opAX - opCX)) >= 0f

        return (abp == bcp) and (bcp == acp)
    }

    /** Creates an iterator over the origin points of this triangle. **/
    fun originPointIterator(): Vector2FIterator = OriginPointIterator(this, index = 0)

    /** Creates an iterator over the points of this triangle. **/
    fun pointIterator(): Vector2FIterator = PointIterator(this, index = 0)

    /**
     * Returns a copy of this instance with specified properties changed.
     *
     * @param orientation the value is expected to be [normalized][ComplexF.normalized].
     */
    fun copy(
        centroid: Vector2F = this.centroid,
        orientation: ComplexF = this.orientation,
        originPointA: Vector2F = this.originPointA,
        originPointB: Vector2F = this.originPointB,
        originPointC: Vector2F = this.originPointC
    ): Triangle

    /** Returns the [centroid] of this triangle. **/
    operator fun component1(): Vector2F = centroid

    /** Returns the [orientation] of this triangle. **/
    operator fun component2(): ComplexF = orientation

    /** Returns the [originPointA] of this triangle. **/
    operator fun component3(): Vector2F = originPointA

    /** Returns the [originPointB] of this triangle. **/
    operator fun component4(): Vector2F = originPointB

    /** Returns the [originPointC] of this triangle. **/
    operator fun component5(): Vector2F = originPointC

    private class OriginPointIterator(
        private val triangle: Triangle,
        private var index: Int
    ) : Vector2FIterator() {
        override fun hasNext(): Boolean = index < 3

        override fun nextVector2F(): Vector2F = when (index++) {
            0 -> triangle.originPointA
            1 -> triangle.originPointB
            2 -> triangle.originPointC
            else -> throw NoSuchElementException("${index - 1}")
        }
    }

    private class PointIterator(triangle: Triangle, index: Int) : Vector2FIterator() {
        private val _pointA: Vector2F
        private val _pointB: Vector2F
        private val _pointC: Vector2F
        private var _index: Int

        init {
            val (cX: Float, cY: Float) = triangle.centroid
            val (oR: Float, oI: Float) = triangle.orientation
            val (opAX: Float, opAY: Float) = triangle.originPointA
            val (opBX: Float, opBY: Float) = triangle.originPointB
            val (opCX: Float, opCY: Float) = triangle.originPointC
            _pointA = Vector2F((opAX * oR - opAY * oI) + cX, (opAY * oR + opAX * oI) + cY)
            _pointB = Vector2F((opBX * oR - opBY * oI) + cX, (opBY * oR + opBX * oI) + cY)
            _pointC = Vector2F((opCX * oR - opCY * oI) + cX, (opCY * oR + opCX * oI) + cY)
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