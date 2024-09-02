package com.sztorm.lowallocmath.euclidean2d

import com.sztorm.lowallocmath.AngleF
import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F
import com.sztorm.lowallocmath.Vector2FIterator
import kotlin.math.abs
import kotlin.math.absoluteValue
import kotlin.math.sqrt

/** Creates a new instance of [Triangle]. **/
fun Triangle(
    centroid: Vector2F, originPointA: Vector2F, originPointB: Vector2F, originPointC: Vector2F
): Triangle = MutableTriangle(centroid, originPointA, originPointB, originPointC)

/** Creates a new instance of [Triangle]. **/
fun Triangle(pointA: Vector2F, pointB: Vector2F, pointC: Vector2F): Triangle =
    MutableTriangle(pointA, pointB, pointC)

/**
 * Represents a transformable triangle in a two-dimensional Euclidean space.
 *
 * Implementations that use default-implemented members of this interface must make sure that the
 * properties [originPointA], [originPointB], [originPointC], [centroid] and the [copy] method are
 * independent of other members and the computational complexity of these members is trivial.
 */
interface Triangle : TriangleShape, Transformable {
    /**
     * Returns the centroid of this triangle. Centroid is the intersection point of the triangle's
     * medians. Centroid is also known as the center of mass.
     */
    val centroid: Vector2F

    /** Returns the point _A_ in reference to the [centroid] of this triangle. **/
    val originPointA: Vector2F

    /** Returns the point _B_ in reference to the [centroid] of this triangle. **/
    val originPointB: Vector2F

    /** Returns the point _C_ in reference to the [centroid] of this triangle. **/
    val originPointC: Vector2F

    /** Returns the point _A_ of this triangle. **/
    val pointA: Vector2F
        get() = originPointA + centroid

    /** Returns the point _B_ of this triangle. **/
    val pointB: Vector2F
        get() = originPointB + centroid

    /** Returns the point _C_ of this triangle. **/
    val pointC: Vector2F
        get() = originPointC + centroid

    /**
     * Returns the incenter of this triangle. Incenter is the center of the triangle's inscribed
     * circle.
     */
    val incenter: Vector2F
        get() {
            val (cX: Float, cY: Float) = centroid
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

            return Vector2F(
                (bcSide * opAX + acSide * opBX + abSide * opCX) * factor + cX,
                (bcSide * opAY + acSide * opBY + abSide * opCY) * factor + cY
            )
        }

    /**
     * Returns the circumcenter of this triangle. Circumcenter is the center of the triangle's
     * circumscribed circle.
     */
    val circumcenter: Vector2F
        get() {
            val (cX: Float, cY: Float) = centroid
            val (opAX: Float, opAY: Float) = originPointA
            val (opBX: Float, opBY: Float) = originPointB
            val (opCX: Float, opCY: Float) = originPointC
            val opASquaredMagnitude: Float = opAX * opAX + opAY * opAY
            val opBSquaredMagnitude: Float = opBX * opBX + opBY * opBY
            val opCSquaredMagnitude: Float = opCX * opCX + opCY * opCY
            val aDet: Float =
                opAX * opBY + opAY * opCX + opBX * opCY - opBY * opCX - opAY * opBX - opAX * opCY
            val factor: Float = 0.5f / aDet
            val xDet: Float = opASquaredMagnitude * opBY + opAY * opCSquaredMagnitude +
                    opBSquaredMagnitude * opCY - opBY * opCSquaredMagnitude -
                    opAY * opBSquaredMagnitude - opASquaredMagnitude * opCY
            val yDet: Float = opAX * opBSquaredMagnitude + opASquaredMagnitude * opCX +
                    opBX * opCSquaredMagnitude - opBSquaredMagnitude * opCX -
                    opASquaredMagnitude * opBX - opAX * opCSquaredMagnitude

            return Vector2F(xDet * factor + cX, yDet * factor + cY)
        }

    /**
     * Returns the orthocenter of this triangle. Orthocenter is the intersection point of the
     * triangle's altitudes.
     */
    val orthocenter: Vector2F
        get() {
            val (centroidX: Float, centroidY: Float) = centroid
            val (opAX: Float, opAY: Float) = originPointA
            val (opBX: Float, opBY: Float) = originPointB
            val (opCX: Float, opCY: Float) = originPointC
            val opASquaredMagnitude: Float = opAX * opAX + opAY * opAY
            val opBSquaredMagnitude: Float = opBX * opBX + opBY * opBY
            val opCSquaredMagnitude: Float = opCX * opCX + opCY * opCY
            val aDet: Float =
                opAX * opBY + opAY * opCX + opBX * opCY - opBY * opCX - opAY * opBX - opAX * opCY
            val factor: Float = 0.5f / aDet
            val xDet: Float = opASquaredMagnitude * opBY + opAY * opCSquaredMagnitude +
                    opBSquaredMagnitude * opCY - opBY * opCSquaredMagnitude -
                    opAY * opBSquaredMagnitude - opASquaredMagnitude * opCY
            val yDet: Float = opAX * opBSquaredMagnitude + opASquaredMagnitude * opCX +
                    opBX * opCSquaredMagnitude - opBSquaredMagnitude * opCX -
                    opASquaredMagnitude * opBX - opAX * opCSquaredMagnitude
            val circumcenterX: Float = xDet * factor + centroidX
            val circumcenterY: Float = yDet * factor + centroidY

            return Vector2F(
                circumcenterX + (centroidX - circumcenterX) * 3f,
                circumcenterY + (centroidY - circumcenterY) * 3f
            )
        }

    override val area: Float
        get() {
            val (aX: Float, aY: Float) = originPointA
            val (bX: Float, bY: Float) = originPointB
            val (cX: Float, cY: Float) = originPointC

            return 0.5f * abs((aX - cX) * (bY - cY) - (bX - cX) * (aY - cY))
        }

    override val perimeter: Float
        get() {
            val opA: Vector2F = originPointA
            val opB: Vector2F = originPointB
            val opC: Vector2F = originPointC

            return opA.distanceTo(opB) + opB.distanceTo(opC) + opC.distanceTo(opA)
        }

    override val sideLengthAB: Float
        get() = originPointA.distanceTo(originPointB)

    override val sideLengthBC: Float
        get() = originPointB.distanceTo(originPointC)

    override val sideLengthCA: Float
        get() = originPointC.distanceTo(originPointA)

    /**
     * Returns the position of this object in reference to the origin of [Vector2F.ZERO].
     *
     * This property is equal to [centroid].
     */
    override val position: Vector2F
        get() = centroid

    /**
     * Returns the orientation of this object in reference to the origin of [ComplexF.ONE].
     *
     * This property is determined by the direction of [originPointA].
     */
    override val orientation: ComplexF
        get() = originPointA.toComplexF().normalizedOrElse(ComplexF(1f, 0f))

    override fun movedBy(displacement: Vector2F): Triangle =
        copy(centroid = centroid + displacement)

    override fun movedTo(position: Vector2F): Triangle =
        copy(centroid = position)

    private fun rotatedByImpl(rotation: ComplexF): Triangle {
        val (opAX: Float, opAY: Float) = originPointA
        val (opBX: Float, opBY: Float) = originPointB
        val (opCX: Float, opCY: Float) = originPointC
        val (rotR: Float, rotI: Float) = rotation

        return copy(
            originPointA = Vector2F(opAX * rotR - opAY * rotI, opAY * rotR + opAX * rotI),
            originPointB = Vector2F(opBX * rotR - opBY * rotI, opBY * rotR + opBX * rotI),
            originPointC = Vector2F(opCX * rotR - opCY * rotI, opCY * rotR + opCX * rotI)
        )
    }

    override fun rotatedBy(rotation: AngleF): Triangle =
        rotatedByImpl(ComplexF.fromAngle(rotation))

    override fun rotatedBy(rotation: ComplexF): Triangle = rotatedByImpl(rotation)

    private fun rotatedToImpl(orientation: ComplexF): Triangle {
        val (opAX: Float, opAY: Float) = originPointA
        val (opBX: Float, opBY: Float) = originPointB
        val (opCX: Float, opCY: Float) = originPointC
        val conjugatedOrientation = ComplexF(opAX, -opAY)
            .normalizedOrElse(ComplexF(1f, 0f))
        val (rotR: Float, rotI: Float) = conjugatedOrientation * orientation

        return copy(
            originPointA = Vector2F(opAX * rotR - opAY * rotI, opAY * rotR + opAX * rotI),
            originPointB = Vector2F(opBX * rotR - opBY * rotI, opBY * rotR + opBX * rotI),
            originPointC = Vector2F(opCX * rotR - opCY * rotI, opCY * rotR + opCX * rotI)
        )
    }

    override fun rotatedTo(orientation: AngleF): Triangle =
        rotatedToImpl(ComplexF.fromAngle(orientation))

    override fun rotatedTo(orientation: ComplexF): Triangle = rotatedToImpl(orientation)

    private fun rotatedAroundPointByImpl(point: Vector2F, rotation: ComplexF): Triangle {
        val (cX: Float, cY: Float) = centroid
        val (opAX: Float, opAY: Float) = originPointA
        val (opBX: Float, opBY: Float) = originPointB
        val (opCX: Float, opCY: Float) = originPointC
        val (pX: Float, pY: Float) = point
        val (rotR: Float, rotI: Float) = rotation
        val cpDiffX: Float = cX - pX
        val cpDiffY: Float = cY - pY

        return copy(
            centroid = Vector2F(
                cpDiffX * rotR - cpDiffY * rotI + pX,
                cpDiffY * rotR + cpDiffX * rotI + pY
            ),
            originPointA = Vector2F(opAX * rotR - opAY * rotI, opAY * rotR + opAX * rotI),
            originPointB = Vector2F(opBX * rotR - opBY * rotI, opBY * rotR + opBX * rotI),
            originPointC = Vector2F(opCX * rotR - opCY * rotI, opCY * rotR + opCX * rotI)
        )
    }

    override fun rotatedAroundPointBy(point: Vector2F, rotation: AngleF): Triangle =
        rotatedAroundPointByImpl(point, ComplexF.fromAngle(rotation))

    override fun rotatedAroundPointBy(point: Vector2F, rotation: ComplexF): Triangle =
        rotatedAroundPointByImpl(point, rotation)

    private fun rotatedAroundPointToImpl(point: Vector2F, orientation: ComplexF): Triangle {
        val (cX: Float, cY: Float) = centroid
        val opA: Vector2F = originPointA
        val (opAX: Float, opAY: Float) = opA
        val (opBX: Float, opBY: Float) = originPointB
        val (opCX: Float, opCY: Float) = originPointC
        val (startOR: Float, startOI: Float) = opA.normalizedOrElse(Vector2F(1f, 0f))
        val (pX: Float, pY: Float) = point
        val (oR: Float, oI: Float) = orientation
        val cpDiffX: Float = cX - pX
        val cpDiffY: Float = cY - pY
        val centroidToPointDist: Float = sqrt(cpDiffX * cpDiffX + cpDiffY * cpDiffY)

        if (centroidToPointDist > 0.00001f) {
            val centroidToPointDistReciprocal: Float = 1f / centroidToPointDist
            val pointRotR: Float = cpDiffX * centroidToPointDistReciprocal
            val pointRotI: Float = cpDiffY * centroidToPointDistReciprocal
            val pRotR: Float = pointRotR * oR + pointRotI * oI
            val pRotI: Float = pointRotR * oI - pointRotI * oR
            val centroidX: Float = oR * centroidToPointDist + pX
            val centroidY: Float = oI * centroidToPointDist + pY

            return copy(
                centroid = Vector2F(centroidX, centroidY),
                originPointA = Vector2F(
                    opAX * pRotR - opAY * pRotI, opAY * pRotR + opAX * pRotI
                ),
                originPointB = Vector2F(
                    opBX * pRotR - opBY * pRotI, opBY * pRotR + opBX * pRotI
                ),
                originPointC = Vector2F(
                    opCX * pRotR - opCY * pRotI, opCY * pRotR + opCX * pRotI
                )
            )
        } else {
            val pRotR: Float = oR * startOR + oI * startOI
            val pRotI: Float = oI * startOR - oR * startOI

            return copy(
                originPointA = Vector2F(
                    opAX * pRotR - opAY * pRotI, opAY * pRotR + opAX * pRotI
                ),
                originPointB = Vector2F(
                    opBX * pRotR - opBY * pRotI, opBY * pRotR + opBX * pRotI
                ),
                originPointC = Vector2F(
                    opCX * pRotR - opCY * pRotI, opCY * pRotR + opCX * pRotI
                )
            )
        }
    }

    override fun rotatedAroundPointTo(point: Vector2F, orientation: AngleF): Triangle =
        rotatedAroundPointToImpl(point, ComplexF.fromAngle(orientation))

    override fun rotatedAroundPointTo(point: Vector2F, orientation: ComplexF): Triangle =
        rotatedAroundPointToImpl(point, orientation)

    override fun scaledBy(factor: Float): Triangle = copy(
        originPointA = originPointA * factor,
        originPointB = originPointB * factor,
        originPointC = originPointC * factor,
    )

    override fun dilatedBy(point: Vector2F, factor: Float): Triangle {
        val (cX: Float, cY: Float) = centroid
        val (opAX: Float, opAY: Float) = originPointA
        val (opBX: Float, opBY: Float) = originPointB
        val (opCX: Float, opCY: Float) = originPointC
        val (pX: Float, pY: Float) = point
        val f: Float = 1f - factor

        return copy(
            centroid = Vector2F(cX * factor + pX * f, cY * factor + pY * f),
            originPointA = Vector2F(opAX * factor, opAY * factor),
            originPointB = Vector2F(opBX * factor, opBY * factor),
            originPointC = Vector2F(opCX * factor, opCY * factor),
        )
    }

    private fun transformedByImpl(displacement: Vector2F, rotation: ComplexF): Triangle {
        val (dX: Float, dY: Float) = displacement
        val (cX: Float, cY: Float) = centroid
        val (opAX: Float, opAY: Float) = originPointA
        val (opBX: Float, opBY: Float) = originPointB
        val (opCX: Float, opCY: Float) = originPointC
        val (rotR: Float, rotI: Float) = rotation

        return copy(
            centroid = Vector2F(cX + dX, cY + dY),
            originPointA = Vector2F(opAX * rotR - opAY * rotI, opAY * rotR + opAX * rotI),
            originPointB = Vector2F(opBX * rotR - opBY * rotI, opBY * rotR + opBX * rotI),
            originPointC = Vector2F(opCX * rotR - opCY * rotI, opCY * rotR + opCX * rotI)
        )
    }

    override fun transformedBy(displacement: Vector2F, rotation: AngleF): Triangle =
        transformedByImpl(displacement, ComplexF.fromAngle(rotation))

    override fun transformedBy(displacement: Vector2F, rotation: ComplexF): Triangle =
        transformedByImpl(displacement, rotation)

    private fun transformedByImpl(
        displacement: Vector2F, rotation: ComplexF, scaleFactor: Float
    ): Triangle {
        val (dX: Float, dY: Float) = displacement
        val (cX: Float, cY: Float) = centroid
        val (opAX: Float, opAY: Float) = originPointA
        val (opBX: Float, opBY: Float) = originPointB
        val (opCX: Float, opCY: Float) = originPointC
        val (rotR: Float, rotI: Float) = rotation

        return copy(
            centroid = Vector2F(cX + dX, cY + dY),
            originPointA = Vector2F(
                (opAX * rotR - opAY * rotI) * scaleFactor,
                (opAY * rotR + opAX * rotI) * scaleFactor
            ),
            originPointB = Vector2F(
                (opBX * rotR - opBY * rotI) * scaleFactor,
                (opBY * rotR + opBX * rotI) * scaleFactor
            ),
            originPointC = Vector2F(
                (opCX * rotR - opCY * rotI) * scaleFactor,
                (opCY * rotR + opCX * rotI) * scaleFactor
            )
        )
    }

    override fun transformedBy(
        displacement: Vector2F, rotation: AngleF, scaleFactor: Float
    ): Triangle = transformedByImpl(displacement, ComplexF.fromAngle(rotation), scaleFactor)

    override fun transformedBy(
        displacement: Vector2F, rotation: ComplexF, scaleFactor: Float
    ): Triangle = transformedByImpl(displacement, rotation, scaleFactor)

    private fun transformedToImpl(position: Vector2F, orientation: ComplexF): Triangle {
        val (opAX: Float, opAY: Float) = originPointA
        val (opBX: Float, opBY: Float) = originPointB
        val (opCX: Float, opCY: Float) = originPointC
        val conjugatedOrientation = ComplexF(opAX, -opAY)
            .normalizedOrElse(ComplexF(1f, 0f))
        val (rotR: Float, rotI: Float) = conjugatedOrientation * orientation

        return copy(
            centroid = position,
            originPointA = Vector2F(opAX * rotR - opAY * rotI, opAY * rotR + opAX * rotI),
            originPointB = Vector2F(opBX * rotR - opBY * rotI, opBY * rotR + opBX * rotI),
            originPointC = Vector2F(opCX * rotR - opCY * rotI, opCY * rotR + opCX * rotI)
        )
    }

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
        val fromOrientation = fOpA.toComplexF().normalizedOrElse(ComplexF(1f, 0f))
        val toOrientation = tOpA.toComplexF().normalizedOrElse(ComplexF(1f, 0f))
        val rotation = ComplexF.slerp(fromOrientation, toOrientation, by)
        val fromRotation: ComplexF = fromOrientation.conjugate * rotation
        val toRotation: ComplexF = toOrientation.conjugate * rotation
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

        return copy(centroid, originPointA, originPointB, originPointC)
    }

    /** Returns the closest point on this triangle to the given [point]. **/
    fun closestPointTo(point: Vector2F): Vector2F {
        val (centroidX: Float, centroidY: Float) = centroid
        val (opAX: Float, opAY: Float) = originPointA
        val (opBX: Float, opBY: Float) = originPointB
        val (opCX: Float, opCY: Float) = originPointC
        val a = Vector2F(opAX + centroidX, opAY + centroidY)
        val b = Vector2F(opBX + centroidX, opBY + centroidY)
        val c = Vector2F(opCX + centroidX, opCY + centroidY)
        val ab: Vector2F = b - a
        val ac: Vector2F = c - a
        val ap: Vector2F = point - a
        val d1: Float = ab dot ap
        val d2: Float = ac dot ap

        if (d1 <= 0f && d2 <= 0f) {
            return a
        }
        val bp: Vector2F = point - b
        val d3: Float = ab dot bp
        val d4: Float = ac dot bp

        if (d3 >= 0f && d4 <= d3) {
            return b
        }
        val cp: Vector2F = point - c
        val d5: Float = ab dot cp
        val d6: Float = ac dot cp

        if (d6 >= 0f && d5 <= d6) {
            return c
        }
        val vc: Float = d1 * d4 - d3 * d2

        if (vc <= 0f && d1 >= 0f && d3 <= 0f) {
            val v: Float = d1 / (d1 - d3)
            return a + ab * v
        }
        val vb: Float = d5 * d2 - d1 * d6

        if (vb <= 0f && d2 >= 0f && d6 <= 0f) {
            val v: Float = d2 / (d2 - d6)
            return a + ac * v
        }
        val va: Float = d3 * d6 - d5 * d4

        if (va <= 0f && (d4 - d3) >= 0f && (d5 - d6) >= 0f) {
            val v: Float = (d4 - d3) / ((d4 - d3) + (d5 - d6))
            return b + (c - b) * v
        }
        val denominator: Float = 1f / (va + vb + vc)
        val v: Float = vb * denominator
        val w: Float = vc * denominator

        return a + ab * v + ac * w
    }

    /** Returns `true` if this triangle intersects the given [ray]. **/
    fun intersects(ray: Ray): Boolean {
        val (centroidX: Float, centroidY: Float) = centroid
        val (opAX: Float, opAY: Float) = originPointA
        val (opBX: Float, opBY: Float) = originPointB
        val (opCX: Float, opCY: Float) = originPointC
        val (oX: Float, oY: Float) = ray.origin
        val (dirX: Float, dirY: Float) = ray.direction
        val aX: Float = opAX + centroidX
        val aY: Float = opAY + centroidY
        val bX: Float = opBX + centroidX
        val bY: Float = opBY + centroidY
        val cX: Float = opCX + centroidX
        val cY: Float = opCY + centroidY
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
        val (opX: Float, opY: Float) = point - centroid
        val abp: Boolean = ((opX - opAX) * (opAY - opBY) + (opY - opAY) * (opBX - opAX)) >= 0f
        val bcp: Boolean = ((opX - opBX) * (opBY - opCY) + (opY - opBY) * (opCX - opBX)) >= 0f
        val acp: Boolean = ((opX - opCX) * (opCY - opAY) + (opY - opCY) * (opAX - opCX)) >= 0f

        return (abp == bcp) and (bcp == acp)
    }

    /** Creates an iterator over the origin points of this triangle. **/
    fun originPointIterator(): Vector2FIterator = OriginPointIterator(this, index = 0)

    /** Creates an iterator over the points of this triangle. **/
    fun pointIterator(): Vector2FIterator = PointIterator(this, index = 0)

    /** Returns a copy of this instance with specified properties changed. **/
    fun copy(
        centroid: Vector2F = this.centroid,
        originPointA: Vector2F = this.originPointA,
        originPointB: Vector2F = this.originPointB,
        originPointC: Vector2F = this.originPointC
    ): Triangle

    /** Returns the [centroid] of this triangle. **/
    operator fun component1(): Vector2F = centroid

    /** Returns the [originPointA] of this triangle. **/
    operator fun component2(): Vector2F = originPointA

    /** Returns the [originPointB] of this triangle. **/
    operator fun component3(): Vector2F = originPointB

    /** Returns the [originPointC] of this triangle. **/
    operator fun component4(): Vector2F = originPointC

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
            val centroid: Vector2F = triangle.centroid
            _pointA = triangle.originPointA + centroid
            _pointB = triangle.originPointB + centroid
            _pointC = triangle.originPointC + centroid
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