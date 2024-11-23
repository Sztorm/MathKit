package com.sztorm.lowallocmath.euclidean2d

import com.sztorm.lowallocmath.*
import kotlin.math.absoluteValue
import kotlin.math.sqrt
import kotlin.math.withSign

/**
 * Creates a new instance of [Triangle].
 *
 * @param pathRotorA the value is expected to be [normalized][ComplexF.normalized].
 * @param pathRotorAB the value is expected to be [normalized][ComplexF.normalized].
 * @param pathRotorAC the value is expected to be [normalized][ComplexF.normalized].
 * @throws IllegalArgumentException when [pointDistanceA] is less than zero.
 * @throws IllegalArgumentException when [pointDistanceB] is less than zero.
 * @throws IllegalArgumentException when [pointDistanceC] is less than zero.
 */
fun Triangle(
    centroid: Vector2F,
    pathRotorA: ComplexF,
    pointDistanceA: Float,
    pathRotorAB: ComplexF,
    pointDistanceB: Float,
    pathRotorAC: ComplexF,
    pointDistanceC: Float
): Triangle = MutableTriangle(
    centroid,
    pathRotorA,
    pointDistanceA,
    pathRotorAB,
    pointDistanceB,
    pathRotorAC,
    pointDistanceC
)

/** Creates a new instance of [Triangle]. **/
fun Triangle(pointA: Vector2F, pointB: Vector2F, pointC: Vector2F): Triangle =
    MutableTriangle(pointA, pointB, pointC)

/**
 * Represents a transformable triangle in a two-dimensional Euclidean space.
 *
 * Implementations that use default-implemented members of this interface must make sure that the
 * properties [centroid], [pathRotorA], [pointDistanceA], [pathRotorAB], [pointDistanceB],
 * [pathRotorAC], [pointDistanceC] and the [copy] method are independent of other members and the
 * computational complexity of these members is trivial.
 */
interface Triangle : TriangleShape, Transformable {
    /**
     * Returns the centroid of this triangle. Centroid is the intersection point of the triangle's
     * medians. Centroid is also known as the center of mass.
     */
    val centroid: Vector2F

    /**
     * Returns the rotation from [ComplexF.ONE] to the normalized position of [pointA] that is
     * relative to [centroid] of this triangle.
     */
    val pathRotorA: ComplexF

    /**
     * Returns the distance from [centroid] to [pointA] of this triangle. The distance is equal to
     * 2/3 of median length that starts at point _A_ and ends at midpoint of _BC_ side.
     */
    val pointDistanceA: Float

    /**
     * Returns the rotation from [pathRotorA] to the normalized position of [pointB] that is
     * relative to [centroid] of this triangle.
     */
    val pathRotorAB: ComplexF

    /**
     * Returns the distance from [centroid] to [pointB] of this triangle. The distance is equal to
     * 2/3 of median length that starts at point _B_ and ends at midpoint of _AC_ side.
     */
    val pointDistanceB: Float

    /**
     * Returns the rotation from [pathRotorA] to the normalized position of [pointC] that is
     * relative to [centroid] of this triangle.
     */
    val pathRotorAC: ComplexF

    /**
     * Returns the distance from [centroid] to [pointC] of this triangle. The distance is equal to
     * 2/3 of median length that starts at point _C_ and ends at midpoint of _AB_ side.
     */
    val pointDistanceC: Float

    /** Returns the point _A_ of this triangle. **/
    val pointA: Vector2F
        get() {
            val (cX: Float, cY: Float) = centroid
            val (prAR: Float, prAI: Float) = pathRotorA
            val pdA: Float = pointDistanceA

            return Vector2F(prAR * pdA + cX, prAI * pdA + cY)
        }

    /** Returns the point _B_ of this triangle. **/
    val pointB: Vector2F
        get() {
            val (cX: Float, cY: Float) = centroid
            val (prAR: Float, prAI: Float) = pathRotorA
            val (prABR: Float, prABI: Float) = pathRotorAB
            val pdB: Float = pointDistanceB

            return Vector2F(
                (prAR * prABR - prAI * prABI) * pdB + cX,
                (prAI * prABR + prAR * prABI) * pdB + cY
            )
        }

    /** Returns the point _C_ of this triangle. **/
    val pointC: Vector2F
        get() {
            val (cX: Float, cY: Float) = centroid
            val (prAR: Float, prAI: Float) = pathRotorA
            val (prACR: Float, prACI: Float) = pathRotorAC
            val pdC: Float = pointDistanceC

            return Vector2F(
                (prAR * prACR - prAI * prACI) * pdC + cX,
                (prAI * prACR + prAR * prACI) * pdC + cY
            )
        }

    /**
     * Returns the incenter of this triangle. Incenter is the center of the triangle's inscribed
     * circle.
     */
    val incenter: Vector2F
        get() {
            val (cX: Float, cY: Float) = centroid
            val (prAR: Float, prAI: Float) = pathRotorA
            val pdA: Float = pointDistanceA
            val (prABR: Float, prABI: Float) = pathRotorAB
            val pdB: Float = pointDistanceB
            val (prACR: Float, prACI: Float) = pathRotorAC
            val pdC: Float = pointDistanceC
            val pdASquared: Float = pdA * pdA
            val pdBSquared: Float = pdB * pdB
            val pdCSquared: Float = pdC * pdC
            val pathSpinorABR: Float = prABR * pdB
            val pathSpinorABI: Float = prABI * pdB
            val pathSpinorACR: Float = prACR * pdC
            val pathSpinorACI: Float = prACI * pdC
            val abSide: Float = sqrt(2f * (pdASquared + pdBSquared) - pdCSquared)
            val bcSide: Float = sqrt(2f * (pdBSquared + pdCSquared) - pdASquared)
            val acSide: Float = sqrt(2f * (pdASquared + pdCSquared) - pdBSquared)
            val factor: Float = 1f / (abSide + bcSide + acSide)
            val icX: Float =
                (bcSide * pdA + acSide * pathSpinorABR + abSide * pathSpinorACR) * factor
            val icY: Float = (acSide * pathSpinorABI + abSide * pathSpinorACI) * factor

            return Vector2F(icX * prAR - icY * prAI + cX, icX * prAI + icY * prAR + cY)
        }

    /**
     * Returns the circumcenter of this triangle. Circumcenter is the center of the triangle's
     * circumscribed circle.
     */
    val circumcenter: Vector2F
        get() {
            val (cX: Float, cY: Float) = centroid
            val (prAR: Float, prAI: Float) = pathRotorA
            val pdA: Float = pointDistanceA
            val (prABR: Float, prABI: Float) = pathRotorAB
            val pdB: Float = pointDistanceB
            val (prACR: Float, prACI: Float) = pathRotorAC
            val pdC: Float = pointDistanceC
            val pathSpinorABR: Float = prABR * pdB
            val pathSpinorABI: Float = prABI * pdB
            val pathSpinorACR: Float = prACR * pdC
            val pathSpinorACI: Float = prACI * pdC
            val addendA: Float = pathSpinorABR - pdA
            val addendB: Float = pdA - pathSpinorACR
            val factor: Float =
                (addendB * (pathSpinorABR - pathSpinorACR) -
                        pathSpinorACI * (pathSpinorABI - pathSpinorACI)) /
                        (pathSpinorACI * addendA + pathSpinorABI * addendB)
            val ccX: Float = 0.5f * (pdA + pathSpinorABR - pathSpinorABI * factor)
            val ccY: Float = 0.5f * (pathSpinorABI + addendA * factor)

            return Vector2F(ccX * prAR - ccY * prAI + cX, ccX * prAI + ccY * prAR + cY)
        }

    /**
     * Returns the orthocenter of this triangle. Orthocenter is the intersection point of the
     * triangle's altitudes.
     */
    val orthocenter: Vector2F
        get() {
            val (cX: Float, cY: Float) = centroid
            val (prAR: Float, prAI: Float) = pathRotorA
            val pdA: Float = pointDistanceA
            val (prABR: Float, prABI: Float) = pathRotorAB
            val pdB: Float = pointDistanceB
            val (prACR: Float, prACI: Float) = pathRotorAC
            val pdC: Float = pointDistanceC
            val pathSpinorABR: Float = prABR * pdB
            val pathSpinorABI: Float = prABI * pdB
            val pathSpinorACR: Float = prACR * pdC
            val pathSpinorACI: Float = prACI * pdC
            val addendA: Float = pathSpinorABR - pdA
            val addendB: Float = pdA - pathSpinorACR
            val factor: Float =
                (addendB * (pathSpinorABR - pathSpinorACR) -
                        pathSpinorACI * (pathSpinorABI - pathSpinorACI)) /
                        (pathSpinorACI * addendA + pathSpinorABI * addendB)
            val ccX: Float = pdA + pathSpinorABR - pathSpinorABI * factor
            val ccY: Float = pathSpinorABI + addendA * factor

            return Vector2F(
                cX - ccX * prAR + ccY * prAI,
                cY - ccX * prAI - ccY * prAR
            )
        }

    override val area: Float
        get() {
            val pdA: Float = pointDistanceA
            val pdB: Float = pointDistanceB
            val pdC: Float = pointDistanceC
            val s: Float = (pdA + pdB + pdC) * 0.5f

            return 3f * sqrt(s * (s - pdA) * (s - pdB) * (s - pdC))
        }

    override val perimeter: Float
        get() {
            val pdA: Float = pointDistanceA
            val pdB: Float = pointDistanceB
            val pdC: Float = pointDistanceC
            val pdASquared: Float = pdA * pdA
            val pdBSquared: Float = pdB * pdB
            val pdCSquared: Float = pdC * pdC
            val s: Float = (pdASquared + pdBSquared + pdCSquared) * 0.6666667f

            return 1.7320508f *
                    (sqrt(s - pdCSquared) + sqrt(s - pdASquared) + sqrt(s - pdBSquared))
        }

    override val sideLengthAB: Float
        get() {
            val pdA: Float = pointDistanceA
            val pdB: Float = pointDistanceB
            val pdC: Float = pointDistanceC

            return sqrt(2f * (pdA * pdA + pdB * pdB) - pdC * pdC)
        }

    override val sideLengthBC: Float
        get() {
            val pdA: Float = pointDistanceA
            val pdB: Float = pointDistanceB
            val pdC: Float = pointDistanceC

            return sqrt(2f * (pdB * pdB + pdC * pdC) - pdA * pdA)
        }

    override val sideLengthAC: Float
        get() {
            val pdA: Float = pointDistanceA
            val pdB: Float = pointDistanceB
            val pdC: Float = pointDistanceC

            return sqrt(2f * (pdA * pdA + pdC * pdC) - pdB * pdB)
        }

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
     * This property is equal to [pathRotorA].
     */
    override val orientation: ComplexF
        get() = pathRotorA

    override fun movedBy(displacement: Vector2F): Triangle =
        copy(centroid = centroid + displacement)

    override fun movedTo(position: Vector2F): Triangle = copy(centroid = position)

    private inline fun rotatedByImpl(rotation: ComplexF): Triangle {
        val (prAR: Float, prAI: Float) = pathRotorA
        val (rotR: Float, rotI: Float) = rotation
        val pathRotorAR: Float = prAR * rotR - prAI * rotI
        val pathRotorAI: Float = prAI * rotR + prAR * rotI

        return copy(pathRotorA = ComplexF(pathRotorAR, pathRotorAI).normalizedOrElse(ComplexF.ONE))
    }

    override fun rotatedBy(rotation: AngleF): Triangle =
        rotatedByImpl(ComplexF.fromAngle(rotation))

    override fun rotatedBy(rotation: ComplexF): Triangle = rotatedByImpl(rotation)

    private inline fun rotatedToImpl(orientation: ComplexF): Triangle =
        copy(pathRotorA = orientation.normalizedOrElse(ComplexF.ONE))

    override fun rotatedTo(orientation: AngleF): Triangle =
        rotatedToImpl(ComplexF.fromAngle(orientation))

    override fun rotatedTo(orientation: ComplexF): Triangle = rotatedToImpl(orientation)

    private inline fun rotatedAroundPointByImpl(point: Vector2F, rotation: ComplexF): Triangle {
        val (cX: Float, cY: Float) = centroid
        val (prAR: Float, prAI: Float) = pathRotorA
        val (pX: Float, pY: Float) = point
        val (rotR: Float, rotI: Float) = rotation
        val cpDiffX: Float = cX - pX
        val cpDiffY: Float = cY - pY
        val centroidX: Float = cpDiffX * rotR - cpDiffY * rotI + pX
        val centroidY: Float = cpDiffY * rotR + cpDiffX * rotI + pY
        val pathRotorAR: Float = prAR * rotR - prAI * rotI
        val pathRotorAI: Float = prAI * rotR + prAR * rotI

        return copy(
            centroid = Vector2F(centroidX, centroidY),
            pathRotorA = ComplexF(pathRotorAR, pathRotorAI).normalizedOrElse(ComplexF.ONE)
        )
    }

    override fun rotatedAroundPointBy(point: Vector2F, rotation: AngleF): Triangle =
        rotatedAroundPointByImpl(point, ComplexF.fromAngle(rotation))

    override fun rotatedAroundPointBy(point: Vector2F, rotation: ComplexF): Triangle =
        rotatedAroundPointByImpl(point, rotation)

    private inline fun rotatedAroundPointToImpl(point: Vector2F, orientation: ComplexF): Triangle {
        val (cX: Float, cY: Float) = centroid
        val (prAR: Float, prAI: Float) = pathRotorA
        val (pX: Float, pY: Float) = point
        val (oR: Float, oI: Float) = orientation
        val cpDiffX: Float = cX - pX
        val cpDiffY: Float = cY - pY
        val centroidToPointDist: Float = sqrt(cpDiffX * cpDiffX + cpDiffY * cpDiffY)

        if (centroidToPointDist > 0.00001f) {
            val centroidToPointDistReciprocal: Float = 1f / centroidToPointDist
            val pointRotR: Float = cpDiffX * centroidToPointDistReciprocal
            val pointRotI: Float = cpDiffY * centroidToPointDistReciprocal
            val pRotTimesStartOR: Float = pointRotR * prAR + pointRotI * prAI
            val pRotTimesStartOI: Float = pointRotR * prAI - pointRotI * prAR
            val centroidX: Float = oR * centroidToPointDist + pX
            val centroidY: Float = oI * centroidToPointDist + pY
            val pathRotorAR: Float = pRotTimesStartOR * oR - pRotTimesStartOI * oI
            val pathRotorAI: Float = pRotTimesStartOI * oR + pRotTimesStartOR * oI

            return copy(
                centroid = Vector2F(centroidX, centroidY),
                pathRotorA = ComplexF(pathRotorAR, pathRotorAI).normalizedOrElse(ComplexF.ONE)
            )
        } else {
            return copy(pathRotorA = orientation.normalizedOrElse(ComplexF.ONE))
        }
    }

    override fun rotatedAroundPointTo(point: Vector2F, orientation: AngleF): Triangle =
        rotatedAroundPointToImpl(point, ComplexF.fromAngle(orientation))

    override fun rotatedAroundPointTo(point: Vector2F, orientation: ComplexF): Triangle =
        rotatedAroundPointToImpl(point, orientation)

    override fun scaledBy(factor: Float): Triangle {
        val (prAR: Float, prAI: Float) = pathRotorA
        val factorSign: Float = 1f.withSign(factor)
        val pathRotorAR: Float = prAR * factorSign
        val pathRotorAI: Float = prAI * factorSign
        val absFactor: Float = factor.absoluteValue
        val pointDistanceA: Float = pointDistanceA * absFactor
        val pointDistanceB: Float = pointDistanceB * absFactor
        val pointDistanceC: Float = pointDistanceC * absFactor

        return copy(
            pathRotorA = ComplexF(pathRotorAR, pathRotorAI),
            pointDistanceA = pointDistanceA,
            pointDistanceB = pointDistanceB,
            pointDistanceC = pointDistanceC
        )
    }

    override fun dilatedBy(point: Vector2F, factor: Float): Triangle {
        val (cX: Float, cY: Float) = centroid
        val (prAR: Float, prAI: Float) = pathRotorA
        val (pX: Float, pY: Float) = point
        val f: Float = 1f - factor
        val centroidX: Float = cX * factor + pX * f
        val centroidY: Float = cY * factor + pY * f
        val factorSign: Float = 1f.withSign(factor)
        val pathRotorAR: Float = prAR * factorSign
        val pathRotorAI: Float = prAI * factorSign
        val absFactor: Float = factor.absoluteValue
        val pointDistanceA: Float = pointDistanceA * absFactor
        val pointDistanceB: Float = pointDistanceB * absFactor
        val pointDistanceC: Float = pointDistanceC * absFactor

        return copy(
            centroid = Vector2F(centroidX, centroidY),
            pathRotorA = ComplexF(pathRotorAR, pathRotorAI),
            pointDistanceA = pointDistanceA,
            pointDistanceB = pointDistanceB,
            pointDistanceC = pointDistanceC
        )
    }

    private inline fun transformedByImpl(displacement: Vector2F, rotation: ComplexF): Triangle {
        val (cX: Float, cY: Float) = centroid
        val (prAR: Float, prAI: Float) = pathRotorA
        val (displacementX: Float, displacementY: Float) = displacement
        val (rotR: Float, rotI: Float) = rotation
        val centroidX: Float = cX + displacementX
        val centroidY: Float = cY + displacementY
        val pathRotorAR: Float = prAR * rotR - prAI * rotI
        val pathRotorAI: Float = prAI * rotR + prAR * rotI

        return copy(
            centroid = Vector2F(centroidX, centroidY),
            pathRotorA = ComplexF(pathRotorAR, pathRotorAI).normalizedOrElse(ComplexF.ONE)
        )
    }

    override fun transformedBy(displacement: Vector2F, rotation: AngleF): Triangle =
        transformedByImpl(displacement, ComplexF.fromAngle(rotation))

    override fun transformedBy(displacement: Vector2F, rotation: ComplexF): Triangle =
        transformedByImpl(displacement, rotation)

    private inline fun transformedByImpl(
        displacement: Vector2F, rotation: ComplexF, scaleFactor: Float
    ): Triangle {
        val (cX: Float, cY: Float) = centroid
        val (prAR: Float, prAI: Float) = pathRotorA
        val (displacementX: Float, displacementY: Float) = displacement
        val (rotR: Float, rotI: Float) = rotation
        val centroidX: Float = cX + displacementX
        val centroidY: Float = cY + displacementY
        val factorSign: Float = 1f.withSign(scaleFactor)
        val pathRotorAR: Float = (prAR * rotR - prAI * rotI) * factorSign
        val pathRotorAI: Float = (prAI * rotR + prAR * rotI) * factorSign
        val absFactor: Float = scaleFactor.absoluteValue
        val pointDistanceA: Float = pointDistanceA * absFactor
        val pointDistanceB: Float = pointDistanceB * absFactor
        val pointDistanceC: Float = pointDistanceC * absFactor

        return copy(
            centroid = Vector2F(centroidX, centroidY),
            pathRotorA = ComplexF(pathRotorAR, pathRotorAI).normalizedOrElse(ComplexF.ONE),
            pointDistanceA = pointDistanceA,
            pointDistanceB = pointDistanceB,
            pointDistanceC = pointDistanceC,
        )
    }

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
        pathRotorA = orientation.normalizedOrElse(ComplexF.ONE)
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
        val centroid = Vector2F.lerp(this.centroid, to.centroid, by)
        val pathRotorA = ComplexF.slerp(this.pathRotorA, to.pathRotorA, by)
            .normalizedOrElse(ComplexF.ONE)
        val pointDistanceA = Float.lerp(this.pointDistanceA, to.pointDistanceA, by)
        val (fPrABR: Float, fPrABI: Float) = this.pathRotorAB
        val (fPrACR: Float, fPrACI: Float) = this.pathRotorAC
        val (tPrABR: Float, tPrABI: Float) = to.pathRotorAB
        val (tPrACR: Float, tPrACI: Float) = to.pathRotorAC
        val oneMinusBy: Float = 1f - by
        val pointSpinorBFactorA: Float = this.pointDistanceB * oneMinusBy
        val pointSpinorBFactorB: Float = to.pointDistanceB * by
        val pointSpinorBR: Float = fPrABR * pointSpinorBFactorA +
                tPrABR * pointSpinorBFactorB
        val pointSpinorBI: Float = fPrABI * pointSpinorBFactorA +
                tPrABI * pointSpinorBFactorB
        val pointSpinorCFactorA: Float = this.pointDistanceC * oneMinusBy
        val pointSpinorCFactorB: Float = to.pointDistanceC * by
        val pointSpinorCR: Float = fPrACR * pointSpinorCFactorA +
                tPrACR * pointSpinorCFactorB
        val pointSpinorCI: Float = fPrACI * pointSpinorCFactorA +
                tPrACI * pointSpinorCFactorB
        val pointDistanceB: Float =
            sqrt(pointSpinorBR * pointSpinorBR + pointSpinorBI * pointSpinorBI)
        val pathRotorAB: ComplexF =
            if (pointDistanceB > 0.00001f) ComplexF(
                pointSpinorBR / pointDistanceB, pointSpinorBI / pointDistanceB
            ) else ComplexF.ONE
        val pointDistanceC: Float =
            sqrt(pointSpinorCR * pointSpinorCR + pointSpinorCI * pointSpinorCI)
        val pathRotorAC: ComplexF =
            if (pointDistanceC > 0.00001f) ComplexF(
                pointSpinorCR / pointDistanceC, pointSpinorCI / pointDistanceC
            ) else ComplexF.ONE

        return copy(
            centroid,
            pathRotorA,
            pointDistanceA,
            pathRotorAB,
            pointDistanceB,
            pathRotorAC,
            pointDistanceC
        )
    }

    /** Returns the closest point on this triangle to the given [point]. **/
    fun closestPointTo(point: Vector2F): Vector2F {
        val centroid: Vector2F = centroid
        val (prAR: Float, prAI: Float) = pathRotorA
        val pdA: Float = pointDistanceA
        val (prABR: Float, prABI: Float) = pathRotorAB
        val pdB: Float = pointDistanceB
        val (prACR: Float, prACI: Float) = pathRotorAC
        val pdC: Float = pointDistanceC
        val a = Vector2F(prAR * pdA, prAI * pdA)
        val b = Vector2F(
            (prAR * prABR - prAI * prABI) * pdB, (prAI * prABR + prAR * prABI) * pdB
        )
        val c = Vector2F(
            (prAR * prACR - prAI * prACI) * pdC, (prAI * prACR + prAR * prACI) * pdC
        )
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
        val (prAR: Float, prAI: Float) = pathRotorA
        val pdA: Float = pointDistanceA
        val (prABR: Float, prABI: Float) = pathRotorAB
        val pdB: Float = pointDistanceB
        val (prACR: Float, prACI: Float) = pathRotorAC
        val pdC: Float = pointDistanceC
        val (oX: Float, oY: Float) = ray.origin - centroid
        val (dirX: Float, dirY: Float) = ray.direction
        val aX: Float = prAR * pdA
        val aY: Float = prAI * pdA
        val bX: Float = (prAR * prABR - prAI * prABI) * pdB
        val bY: Float = (prAI * prABR + prAR * prABI) * pdB
        val cX: Float = (prAR * prACR - prAI * prACI) * pdC
        val cY: Float = (prAI * prACR + prAR * prACI) * pdC
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
        val (opX: Float, opY: Float) = (point - centroid) * pathRotorA.conjugate
        val opAX: Float = pointDistanceA
        val (opBX: Float, opBY: Float) = pathRotorAB * pointDistanceB
        val (opCX: Float, opCY: Float) = pathRotorAC * pointDistanceC
        val abp: Boolean = ((opX - opAX) * -opBY + opY * (opBX - opAX)) >= 0f
        val bcp: Boolean = ((opX - opBX) * (opBY - opCY) + (opY - opBY) * (opCX - opBX)) >= 0f
        val acp: Boolean = ((opX - opCX) * opCY + (opY - opCY) * (opAX - opCX)) >= 0f

        return (abp == bcp) and (bcp == acp)
    }

    /** Creates an iterator over the points of this triangle. **/
    fun pointIterator(): Vector2FIterator = PointIterator(this, index = 0)

    /**
     * Returns a copy of this instance with specified properties changed.
     *
     * @param pathRotorA the value is expected to be [normalized][ComplexF.normalized].
     * @param pathRotorAB the value is expected to be [normalized][ComplexF.normalized].
     * @param pathRotorAC the value is expected to be [normalized][ComplexF.normalized].
     */
    fun copy(
        centroid: Vector2F = this.centroid,
        pathRotorA: ComplexF = this.pathRotorA,
        pointDistanceA: Float = this.pointDistanceA,
        pathRotorAB: ComplexF = this.pathRotorAB,
        pointDistanceB: Float = this.pointDistanceB,
        pathRotorAC: ComplexF = this.pathRotorAC,
        pointDistanceC: Float = this.pointDistanceC
    ): Triangle

    /** Returns the [centroid] of this triangle. **/
    operator fun component1(): Vector2F = centroid

    /** Returns the [pathRotorA] of this triangle. **/
    operator fun component2(): ComplexF = pathRotorA

    /** Returns the [pointDistanceA] of this triangle. **/
    operator fun component3(): Float = pointDistanceA

    /** Returns the [pathRotorAB] of this triangle. **/
    operator fun component4(): ComplexF = pathRotorAB

    /** Returns the [pointDistanceB] of this triangle. **/
    operator fun component5(): Float = pointDistanceB

    /** Returns the [pathRotorAC] of this triangle. **/
    operator fun component6(): ComplexF = pathRotorAC

    /** Returns the [pointDistanceC] of this triangle. **/
    operator fun component7(): Float = pointDistanceC

    private class PointIterator(triangle: Triangle, index: Int) : Vector2FIterator() {
        private val _pointA: Vector2F
        private val _pointB: Vector2F
        private val _pointC: Vector2F
        private var _index: Int

        init {
            val (cX: Float, cY: Float) = triangle.centroid
            val (prAR: Float, prAI: Float) = triangle.pathRotorA
            val pdA: Float = triangle.pointDistanceA
            val (prABR: Float, prABI: Float) = triangle.pathRotorAB
            val pdB: Float = triangle.pointDistanceB
            val (prACR: Float, prACI: Float) = triangle.pathRotorAC
            val pdC: Float = triangle.pointDistanceC
            _pointA = Vector2F(prAR * pdA + cX, prAI * pdA + cY)
            _pointB = Vector2F(
                (prAR * prABR - prAI * prABI) * pdB + cX,
                (prAI * prABR + prAR * prABI) * pdB + cY
            )
            _pointC = Vector2F(
                (prAR * prACR - prAI * prACI) * pdC + cX,
                (prAI * prACR + prAR * prACI) * pdC + cY
            )
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