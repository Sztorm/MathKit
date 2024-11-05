package com.sztorm.lowallocmath.euclidean2d

import com.sztorm.lowallocmath.*
import kotlin.math.absoluteValue
import kotlin.math.sqrt
import kotlin.math.withSign

/** Represents a mutable transformable triangle in a two-dimensional Euclidean space. **/
class MutableTriangle : Triangle, MutableTransformable {
    private var _centroid: Vector2F
    private var _pathRotorA: ComplexF
    private var _pointDistanceA: Float
    private var _pathRotorAB: ComplexF
    private var _pointDistanceB: Float
    private var _pathRotorAC: ComplexF
    private var _pointDistanceC: Float
    private var _pointA: Vector2F
    private var _pointB: Vector2F
    private var _pointC: Vector2F

    /**
     * Creates a new instance of [MutableTriangle].
     *
     * @param pathRotorA the value is expected to be [normalized][ComplexF.normalized].
     * @param pathRotorAB the value is expected to be [normalized][ComplexF.normalized].
     * @param pathRotorAC the value is expected to be [normalized][ComplexF.normalized].
     * @throws IllegalArgumentException when [pointDistanceA] is less than zero.
     * @throws IllegalArgumentException when [pointDistanceB] is less than zero.
     * @throws IllegalArgumentException when [pointDistanceC] is less than zero.
     */
    constructor(
        centroid: Vector2F,
        pathRotorA: ComplexF,
        pointDistanceA: Float,
        pathRotorAB: ComplexF,
        pointDistanceB: Float,
        pathRotorAC: ComplexF,
        pointDistanceC: Float
    ) {
        throwWhenConstructorArgumentsAreIllegal(pointDistanceA, pointDistanceB, pointDistanceC)
        val (cX: Float, cY: Float) = centroid
        val (prAR: Float, prAI: Float) = pathRotorA
        val (prABR: Float, prABI: Float) = pathRotorAB
        val (prACR: Float, prACI: Float) = pathRotorAC
        _centroid = centroid
        _pathRotorA = pathRotorA
        _pointDistanceA = pointDistanceA
        _pathRotorAB = pathRotorAB
        _pointDistanceB = pointDistanceB
        _pathRotorAC = pathRotorAC
        _pointDistanceC = pointDistanceC
        _pointA = Vector2F(prAR * pointDistanceA + cX, prAI * pointDistanceA + cY)
        _pointB = Vector2F(
            (prAR * prABR - prAI * prABI) * pointDistanceB + cX,
            (prAI * prABR + prAR * prABI) * pointDistanceB + cY
        )
        _pointC = Vector2F(
            (prAR * prACR - prAI * prACI) * pointDistanceC + cX,
            (prAI * prACR + prAR * prACI) * pointDistanceC + cY
        )
    }

    /** Creates a new instance of [MutableTriangle]. **/
    constructor(pointA: Vector2F, pointB: Vector2F, pointC: Vector2F) {
        val (pAX: Float, pAY: Float) = pointA
        val (pBX: Float, pBY: Float) = pointB
        val (pCX: Float, pCY: Float) = pointC
        val centroidX: Float = (pAX + pBX + pCX) * 0.3333333f
        val centroidY: Float = (pAY + pBY + pCY) * 0.3333333f
        val pathSpinorAR: Float = pAX - centroidX
        val pathSpinorAI: Float = pAY - centroidY
        val pathSpinorBR: Float = pBX - centroidX
        val pathSpinorBI: Float = pBY - centroidY
        val pathSpinorCR: Float = pCX - centroidX
        val pathSpinorCI: Float = pCY - centroidY
        val pointDistanceA: Float =
            sqrt(pathSpinorAR * pathSpinorAR + pathSpinorAI * pathSpinorAI)
        val pointDistanceB: Float =
            sqrt(pathSpinorBR * pathSpinorBR + pathSpinorBI * pathSpinorBI)
        val pointDistanceC: Float =
            sqrt(pathSpinorCR * pathSpinorCR + pathSpinorCI * pathSpinorCI)
        val pathRotorA: ComplexF
        val pathRotorAB: ComplexF
        val pathRotorAC: ComplexF

        if (pointDistanceA > 0.00001f) {
            val pathRotorAR: Float = pathSpinorAR / pointDistanceA
            val pathRotorAI: Float = pathSpinorAI / pointDistanceA
            pathRotorA = ComplexF(pathRotorAR, pathRotorAI)
            pathRotorAB = if (pointDistanceB > 0.00001f) {
                val pointRotorBR: Float = pathSpinorBR / pointDistanceB
                val pointRotorBI: Float = pathSpinorBI / pointDistanceB

                ComplexF(
                    pointRotorBR * pathRotorAR + pointRotorBI * pathRotorAI,
                    pointRotorBI * pathRotorAR - pointRotorBR * pathRotorAI
                )
            } else ComplexF(pathRotorAR, -pathRotorAI)
            pathRotorAC = if (pointDistanceB > 0.00001f) {
                val pointRotorCR: Float = pathSpinorCR / pointDistanceC
                val pointRotorCI: Float = pathSpinorCI / pointDistanceC

                ComplexF(
                    pointRotorCR * pathRotorAR + pointRotorCI * pathRotorAI,
                    pointRotorCI * pathRotorAR - pointRotorCR * pathRotorAI
                )
            } else ComplexF(pathRotorAR, -pathRotorAI)
        } else {
            pathRotorA = ComplexF(1f, 0f)
            pathRotorAB = if (pointDistanceB > 0.00001f) {
                val pointRotorBR: Float = pathSpinorBR / pointDistanceB
                val pointRotorBI: Float = pathSpinorBI / pointDistanceB

                ComplexF(pointRotorBR, pointRotorBI)
            } else ComplexF(1f, 0f)
            pathRotorAC = if (pointDistanceB > 0.00001f) {
                val pointRotorCR: Float = pathSpinorCR / pointDistanceC
                val pointRotorCI: Float = pathSpinorCI / pointDistanceC

                ComplexF(pointRotorCR, pointRotorCI)
            } else ComplexF(1f, 0f)
        }
        _centroid = Vector2F(centroidX, centroidY)
        _pathRotorA = pathRotorA
        _pointDistanceA = pointDistanceA
        _pathRotorAB = pathRotorAB
        _pointDistanceB = pointDistanceB
        _pathRotorAC = pathRotorAC
        _pointDistanceC = pointDistanceC
        _pointA = pointA
        _pointB = pointB
        _pointC = pointC
    }

    private constructor(
        centroid: Vector2F,
        pathRotorA: ComplexF,
        pointDistanceA: Float,
        pathRotorAB: ComplexF,
        pointDistanceB: Float,
        pathRotorAC: ComplexF,
        pointDistanceC: Float,
        pointA: Vector2F,
        pointB: Vector2F,
        pointC: Vector2F,
    ) {
        _centroid = centroid
        _pathRotorA = pathRotorA
        _pointDistanceA = pointDistanceA
        _pathRotorAB = pathRotorAB
        _pointDistanceB = pointDistanceB
        _pathRotorAC = pathRotorAC
        _pointDistanceC = pointDistanceC
        _pointA = pointA
        _pointB = pointB
        _pointC = pointC
    }

    override val centroid: Vector2F
        get() = _centroid

    override val pathRotorA: ComplexF
        get() = _pathRotorA

    override val pointDistanceA: Float
        get() = _pointDistanceA

    override val pathRotorAB: ComplexF
        get() = _pathRotorAB

    override val pointDistanceB: Float
        get() = _pointDistanceB

    override val pathRotorAC: ComplexF
        get() = _pathRotorAC

    override val pointDistanceC: Float
        get() = _pointDistanceC

    override val pointA: Vector2F
        get() = _pointA

    override val pointB: Vector2F
        get() = _pointB

    override val pointC: Vector2F
        get() = _pointC

    override val area: Float
        get() {
            val pdA: Float = _pointDistanceA
            val pdB: Float = _pointDistanceB
            val pdC: Float = _pointDistanceC
            val s: Float = (pdA + pdB + pdC) * 0.5f

            return 3f * sqrt(s * (s - pdA) * (s - pdB) * (s - pdC))
        }

    override val perimeter: Float
        get() {
            val pdA: Float = _pointDistanceA
            val pdB: Float = _pointDistanceB
            val pdC: Float = _pointDistanceC
            val pdASquared: Float = pdA * pdA
            val pdBSquared: Float = pdB * pdB
            val pdCSquared: Float = pdC * pdC
            val s: Float = (pdASquared + pdBSquared + pdCSquared) * 0.6666667f

            return 1.7320508f *
                    (sqrt(s - pdCSquared) + sqrt(s - pdASquared) + sqrt(s - pdBSquared))
        }

    override val sideLengthAB: Float
        get() {
            val pdA: Float = _pointDistanceA
            val pdB: Float = _pointDistanceB
            val pdC: Float = _pointDistanceC

            return sqrt(2f * (pdA * pdA + pdB * pdB) - pdC * pdC)
        }

    override val sideLengthBC: Float
        get() {
            val pdA: Float = _pointDistanceA
            val pdB: Float = _pointDistanceB
            val pdC: Float = _pointDistanceC

            return sqrt(2f * (pdB * pdB + pdC * pdC) - pdA * pdA)
        }

    override val sideLengthAC: Float
        get() {
            val pdA: Float = _pointDistanceA
            val pdB: Float = _pointDistanceB
            val pdC: Float = _pointDistanceC

            return sqrt(2f * (pdA * pdA + pdC * pdC) - pdB * pdB)
        }

    override val position: Vector2F
        get() = _centroid

    override val orientation: ComplexF
        get() = _pathRotorA

    override val incenter: Vector2F
        get() {
            val (cX: Float, cY: Float) = _centroid
            val (prAR: Float, prAI: Float) = _pathRotorA
            val pdA: Float = _pointDistanceA
            val (prABR: Float, prABI: Float) = _pathRotorAB
            val pdB: Float = _pointDistanceB
            val (prACR: Float, prACI: Float) = _pathRotorAC
            val pdC: Float = _pointDistanceC
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

    override val circumcenter: Vector2F
        get() {
            val (cX: Float, cY: Float) = _centroid
            val (prAR: Float, prAI: Float) = _pathRotorA
            val pdA: Float = _pointDistanceA
            val (prABR: Float, prABI: Float) = _pathRotorAB
            val pdB: Float = _pointDistanceB
            val (prACR: Float, prACI: Float) = _pathRotorAC
            val pdC: Float = _pointDistanceC
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

    override val orthocenter: Vector2F
        get() {
            val (cX: Float, cY: Float) = _centroid
            val (prAR: Float, prAI: Float) = _pathRotorA
            val pdA: Float = _pointDistanceA
            val (prABR: Float, prABI: Float) = _pathRotorAB
            val pdB: Float = _pointDistanceB
            val (prACR: Float, prACI: Float) = _pathRotorAC
            val pdC: Float = _pointDistanceC
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

    override fun movedBy(displacement: Vector2F) = MutableTriangle(
        centroid = _centroid + displacement,
        _pathRotorA,
        _pointDistanceA,
        _pathRotorAB,
        _pointDistanceB,
        _pathRotorAC,
        _pointDistanceC,
        pointA = _pointA + displacement,
        pointB = _pointB + displacement,
        pointC = _pointC + displacement
    )

    override fun movedTo(position: Vector2F): MutableTriangle {
        val displacement: Vector2F = position - _centroid

        return MutableTriangle(
            centroid = position,
            _pathRotorA,
            _pointDistanceA,
            _pathRotorAB,
            _pointDistanceB,
            _pathRotorAC,
            _pointDistanceC,
            pointA = _pointA + displacement,
            pointB = _pointB + displacement,
            pointC = _pointC + displacement
        )
    }

    override fun moveBy(displacement: Vector2F) {
        _centroid += displacement
        _pointA += displacement
        _pointB += displacement
        _pointC += displacement
    }

    override fun moveTo(position: Vector2F) {
        val displacement: Vector2F = position - _centroid
        _centroid = position
        _pointA += displacement
        _pointB += displacement
        _pointC += displacement
    }

    private inline fun rotatedByImpl(rotation: ComplexF): MutableTriangle {
        val centroid: Vector2F = _centroid
        val (prAR: Float, prAI: Float) = _pathRotorA
        val pdA: Float = _pointDistanceA
        val pathRotorAB: ComplexF = _pathRotorAB
        val pdB: Float = _pointDistanceB
        val pathRotorAC: ComplexF = _pathRotorAC
        val pdC: Float = _pointDistanceC
        val (cX: Float, cY: Float) = centroid
        val (prABR: Float, prABI: Float) = pathRotorAB
        val (prACR: Float, prACI: Float) = pathRotorAC
        val (rotR: Float, rotI: Float) = rotation
        val pathRotorAR: Float = prAR * rotR - prAI * rotI
        val pathRotorAI: Float = prAI * rotR + prAR * rotI
        val pointRotorBR: Float = pathRotorAR * prABR - pathRotorAI * prABI
        val pointRotorBI: Float = pathRotorAI * prABR + pathRotorAR * prABI
        val pointRotorCR: Float = pathRotorAR * prACR - pathRotorAI * prACI
        val pointRotorCI: Float = pathRotorAI * prACR + pathRotorAR * prACI

        return MutableTriangle(
            centroid,
            pathRotorA = ComplexF(pathRotorAR, pathRotorAI),
            pdA,
            pathRotorAB,
            pdB,
            pathRotorAC,
            pdC,
            pointA = Vector2F(pathRotorAR * pdA + cX, pathRotorAI * pdA + cY),
            pointB = Vector2F(pointRotorBR * pdB + cX, pointRotorBI * pdB + cY),
            pointC = Vector2F(pointRotorCR * pdC + cX, pointRotorCI * pdC + cY)
        )
    }

    override fun rotatedBy(rotation: AngleF): MutableTriangle =
        rotatedByImpl(ComplexF.fromAngle(rotation))

    override fun rotatedBy(rotation: ComplexF): MutableTriangle = rotatedByImpl(rotation)

    private inline fun rotatedToImpl(orientation: ComplexF): MutableTriangle {
        val centroid: Vector2F = _centroid
        val pdA: Float = _pointDistanceA
        val pathRotorAB: ComplexF = _pathRotorAB
        val pdB: Float = _pointDistanceB
        val pathRotorAC: ComplexF = _pathRotorAC
        val pdC: Float = _pointDistanceC
        val (cX: Float, cY: Float) = centroid
        val (prABR: Float, prABI: Float) = pathRotorAB
        val (prACR: Float, prACI: Float) = pathRotorAC
        val (oR: Float, oI: Float) = orientation
        val pointRotorBR: Float = oR * prABR - oI * prABI
        val pointRotorBI: Float = oI * prABR + oR * prABI
        val pointRotorCR: Float = oR * prACR - oI * prACI
        val pointRotorCI: Float = oI * prACR + oR * prACI

        return MutableTriangle(
            centroid,
            pathRotorA = orientation,
            pdA,
            pathRotorAB,
            pdB,
            pathRotorAC,
            pdC,
            pointA = Vector2F(oR * pdA + cX, oI * pdA + cY),
            pointB = Vector2F(pointRotorBR * pdB + cX, pointRotorBI * pdB + cY),
            pointC = Vector2F(pointRotorCR * pdC + cX, pointRotorCI * pdC + cY)
        )
    }

    override fun rotatedTo(orientation: AngleF): MutableTriangle =
        rotatedToImpl(ComplexF.fromAngle(orientation))

    override fun rotatedTo(orientation: ComplexF): MutableTriangle = rotatedToImpl(orientation)

    private inline fun rotatedAroundPointByImpl(
        point: Vector2F, rotation: ComplexF
    ): MutableTriangle {
        val centroid: Vector2F = _centroid
        val (prAR: Float, prAI: Float) = _pathRotorA
        val pdA: Float = _pointDistanceA
        val pathRotorAB: ComplexF = _pathRotorAB
        val pdB: Float = _pointDistanceB
        val pathRotorAC: ComplexF = _pathRotorAC
        val pdC: Float = _pointDistanceC
        val (cX: Float, cY: Float) = centroid
        val (prABR: Float, prABI: Float) = pathRotorAB
        val (prACR: Float, prACI: Float) = pathRotorAC
        val (pX: Float, pY: Float) = point
        val (rotR: Float, rotI: Float) = rotation
        val cpDiffX: Float = cX - pX
        val cpDiffY: Float = cY - pY
        val centroidX: Float = cpDiffX * rotR - cpDiffY * rotI + pX
        val centroidY: Float = cpDiffY * rotR + cpDiffX * rotI + pY
        val pathRotorAR: Float = prAR * rotR - prAI * rotI
        val pathRotorAI: Float = prAI * rotR + prAR * rotI
        val pointRotorBR: Float = pathRotorAR * prABR - pathRotorAI * prABI
        val pointRotorBI: Float = pathRotorAI * prABR + pathRotorAR * prABI
        val pointRotorCR: Float = pathRotorAR * prACR - pathRotorAI * prACI
        val pointRotorCI: Float = pathRotorAI * prACR + pathRotorAR * prACI

        return MutableTriangle(
            centroid = Vector2F(centroidX, centroidY),
            pathRotorA = ComplexF(pathRotorAR, pathRotorAI),
            pdA,
            pathRotorAB,
            pdB,
            pathRotorAC,
            pdC,
            pointA = Vector2F(pathRotorAR * pdA + centroidX, pathRotorAI * pdA + centroidY),
            pointB = Vector2F(pointRotorBR * pdB + centroidX, pointRotorBI * pdB + centroidY),
            pointC = Vector2F(pointRotorCR * pdC + centroidX, pointRotorCI * pdC + centroidY)
        )
    }

    override fun rotatedAroundPointBy(point: Vector2F, rotation: AngleF): MutableTriangle =
        rotatedAroundPointByImpl(point, ComplexF.fromAngle(rotation))

    override fun rotatedAroundPointBy(point: Vector2F, rotation: ComplexF): MutableTriangle =
        rotatedAroundPointByImpl(point, rotation)

    private inline fun rotatedAroundPointToImpl(
        point: Vector2F, orientation: ComplexF
    ): MutableTriangle {
        val centroid: Vector2F = _centroid
        val (prAR: Float, prAI: Float) = _pathRotorA
        val pdA: Float = _pointDistanceA
        val pathRotorAB: ComplexF = _pathRotorAB
        val pdB: Float = _pointDistanceB
        val pathRotorAC: ComplexF = _pathRotorAC
        val pdC: Float = _pointDistanceC
        val (cX: Float, cY: Float) = centroid
        val (prABR: Float, prABI: Float) = pathRotorAB
        val (prACR: Float, prACI: Float) = pathRotorAC
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
            val pointRotorBR: Float = pathRotorAR * prABR - pathRotorAI * prABI
            val pointRotorBI: Float = pathRotorAI * prABR + pathRotorAR * prABI
            val pointRotorCR: Float = pathRotorAR * prACR - pathRotorAI * prACI
            val pointRotorCI: Float = pathRotorAI * prACR + pathRotorAR * prACI

            return MutableTriangle(
                centroid = Vector2F(centroidX, centroidY),
                pathRotorA = ComplexF(pathRotorAR, pathRotorAI),
                pdA,
                pathRotorAB,
                pdB,
                pathRotorAC,
                pdC,
                pointA = Vector2F(
                    pathRotorAR * pdA + centroidX, pathRotorAI * pdA + centroidY
                ),
                pointB = Vector2F(
                    pointRotorBR * pdB + centroidX, pointRotorBI * pdB + centroidY
                ),
                pointC = Vector2F(
                    pointRotorCR * pdC + centroidX, pointRotorCI * pdC + centroidY
                )
            )
        } else {
            val pointRotorBR: Float = oR * prABR - oI * prABI
            val pointRotorBI: Float = oI * prABR + oR * prABI
            val pointRotorCR: Float = oR * prACR - oI * prACI
            val pointRotorCI: Float = oI * prACR + oR * prACI

            return MutableTriangle(
                centroid,
                pathRotorA = orientation,
                pdA,
                pathRotorAB,
                pdB,
                pathRotorAC,
                pdC,
                pointA = Vector2F(oR * pdA + cX, oI * pdA + cY),
                pointB = Vector2F(pointRotorBR * pdB + cX, pointRotorBI * pdB + cY),
                pointC = Vector2F(pointRotorCR * pdC + cX, pointRotorCI * pdC + cY)
            )
        }
    }

    override fun rotatedAroundPointTo(point: Vector2F, orientation: AngleF): MutableTriangle =
        rotatedAroundPointToImpl(point, ComplexF.fromAngle(orientation))

    override fun rotatedAroundPointTo(point: Vector2F, orientation: ComplexF): MutableTriangle =
        rotatedAroundPointToImpl(point, orientation)

    private inline fun rotateByImpl(rotation: ComplexF) {
        val centroid: Vector2F = _centroid
        val (prAR: Float, prAI: Float) = _pathRotorA
        val pdA: Float = _pointDistanceA
        val pathRotorAB: ComplexF = _pathRotorAB
        val pdB: Float = _pointDistanceB
        val pathRotorAC: ComplexF = _pathRotorAC
        val pdC: Float = _pointDistanceC
        val (cX: Float, cY: Float) = centroid
        val (prABR: Float, prABI: Float) = pathRotorAB
        val (prACR: Float, prACI: Float) = pathRotorAC
        val (rotR: Float, rotI: Float) = rotation
        val pathRotorAR: Float = prAR * rotR - prAI * rotI
        val pathRotorAI: Float = prAI * rotR + prAR * rotI
        val pointRotorBR: Float = pathRotorAR * prABR - pathRotorAI * prABI
        val pointRotorBI: Float = pathRotorAI * prABR + pathRotorAR * prABI
        val pointRotorCR: Float = pathRotorAR * prACR - pathRotorAI * prACI
        val pointRotorCI: Float = pathRotorAI * prACR + pathRotorAR * prACI
        _pathRotorA = ComplexF(pathRotorAR, pathRotorAI)
        _pointA = Vector2F(pathRotorAR * pdA + cX, pathRotorAI * pdA + cY)
        _pointB = Vector2F(pointRotorBR * pdB + cX, pointRotorBI * pdB + cY)
        _pointC = Vector2F(pointRotorCR * pdC + cX, pointRotorCI * pdC + cY)
    }

    override fun rotateBy(rotation: AngleF) = rotateByImpl(ComplexF.fromAngle(rotation))

    override fun rotateBy(rotation: ComplexF) = rotateByImpl(rotation)

    private inline fun rotateToImpl(orientation: ComplexF) {
        val centroid: Vector2F = _centroid
        val pdA: Float = _pointDistanceA
        val pathRotorAB: ComplexF = _pathRotorAB
        val pdB: Float = _pointDistanceB
        val pathRotorAC: ComplexF = _pathRotorAC
        val pdC: Float = _pointDistanceC
        val (cX: Float, cY: Float) = centroid
        val (prABR: Float, prABI: Float) = pathRotorAB
        val (prACR: Float, prACI: Float) = pathRotorAC
        val (oR: Float, oI: Float) = orientation
        val pointRotorBR: Float = oR * prABR - oI * prABI
        val pointRotorBI: Float = oI * prABR + oR * prABI
        val pointRotorCR: Float = oR * prACR - oI * prACI
        val pointRotorCI: Float = oI * prACR + oR * prACI
        _pathRotorA = orientation
        _pointA = Vector2F(oR * pdA + cX, oI * pdA + cY)
        _pointB = Vector2F(pointRotorBR * pdB + cX, pointRotorBI * pdB + cY)
        _pointC = Vector2F(pointRotorCR * pdC + cX, pointRotorCI * pdC + cY)
    }

    override fun rotateTo(orientation: AngleF) = rotateToImpl(ComplexF.fromAngle(orientation))

    override fun rotateTo(orientation: ComplexF) = rotateToImpl(orientation)

    private inline fun rotateAroundPointByImpl(point: Vector2F, rotation: ComplexF) {
        val centroid: Vector2F = _centroid
        val (prAR: Float, prAI: Float) = _pathRotorA
        val pdA: Float = _pointDistanceA
        val pathRotorAB: ComplexF = _pathRotorAB
        val pdB: Float = _pointDistanceB
        val pathRotorAC: ComplexF = _pathRotorAC
        val pdC: Float = _pointDistanceC
        val (cX: Float, cY: Float) = centroid
        val (prABR: Float, prABI: Float) = pathRotorAB
        val (prACR: Float, prACI: Float) = pathRotorAC
        val (pX: Float, pY: Float) = point
        val (rotR: Float, rotI: Float) = rotation
        val cpDiffX: Float = cX - pX
        val cpDiffY: Float = cY - pY
        val centroidX: Float = cpDiffX * rotR - cpDiffY * rotI + pX
        val centroidY: Float = cpDiffY * rotR + cpDiffX * rotI + pY
        val pathRotorAR: Float = prAR * rotR - prAI * rotI
        val pathRotorAI: Float = prAI * rotR + prAR * rotI
        val pointRotorBR: Float = pathRotorAR * prABR - pathRotorAI * prABI
        val pointRotorBI: Float = pathRotorAI * prABR + pathRotorAR * prABI
        val pointRotorCR: Float = pathRotorAR * prACR - pathRotorAI * prACI
        val pointRotorCI: Float = pathRotorAI * prACR + pathRotorAR * prACI
        _centroid = Vector2F(centroidX, centroidY)
        _pathRotorA = ComplexF(pathRotorAR, pathRotorAI)
        _pointA = Vector2F(pathRotorAR * pdA + centroidX, pathRotorAI * pdA + centroidY)
        _pointB = Vector2F(pointRotorBR * pdB + centroidX, pointRotorBI * pdB + centroidY)
        _pointC = Vector2F(pointRotorCR * pdC + centroidX, pointRotorCI * pdC + centroidY)

    }

    override fun rotateAroundPointBy(point: Vector2F, rotation: AngleF) =
        rotateAroundPointByImpl(point, ComplexF.fromAngle(rotation))

    override fun rotateAroundPointBy(point: Vector2F, rotation: ComplexF) =
        rotateAroundPointByImpl(point, rotation)

    private inline fun rotateAroundPointToImpl(point: Vector2F, orientation: ComplexF) {
        val centroid: Vector2F = _centroid
        val (prAR: Float, prAI: Float) = _pathRotorA
        val pdA: Float = _pointDistanceA
        val pathRotorAB: ComplexF = _pathRotorAB
        val pdB: Float = _pointDistanceB
        val pathRotorAC: ComplexF = _pathRotorAC
        val pdC: Float = _pointDistanceC
        val (cX: Float, cY: Float) = centroid
        val (prABR: Float, prABI: Float) = pathRotorAB
        val (prACR: Float, prACI: Float) = pathRotorAC
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
            val pointRotorBR: Float = pathRotorAR * prABR - pathRotorAI * prABI
            val pointRotorBI: Float = pathRotorAI * prABR + pathRotorAR * prABI
            val pointRotorCR: Float = pathRotorAR * prACR - pathRotorAI * prACI
            val pointRotorCI: Float = pathRotorAI * prACR + pathRotorAR * prACI
            _centroid = Vector2F(centroidX, centroidY)
            _pathRotorA = ComplexF(pathRotorAR, pathRotorAI)
            _pointA = Vector2F(
                pathRotorAR * pdA + centroidX, pathRotorAI * pdA + centroidY
            )
            _pointB = Vector2F(
                pointRotorBR * pdB + centroidX, pointRotorBI * pdB + centroidY
            )
            _pointC = Vector2F(
                pointRotorCR * pdC + centroidX, pointRotorCI * pdC + centroidY
            )
        } else {
            val pointRotorBR: Float = oR * prABR - oI * prABI
            val pointRotorBI: Float = oI * prABR + oR * prABI
            val pointRotorCR: Float = oR * prACR - oI * prACI
            val pointRotorCI: Float = oI * prACR + oR * prACI
            _pathRotorA = orientation
            _pointA = Vector2F(oR * pdA + cX, oI * pdA + cY)
            _pointB = Vector2F(pointRotorBR * pdB + cX, pointRotorBI * pdB + cY)
            _pointC = Vector2F(pointRotorCR * pdC + cX, pointRotorCI * pdC + cY)
        }
    }

    override fun rotateAroundPointTo(point: Vector2F, orientation: AngleF) =
        rotateAroundPointToImpl(point, ComplexF.fromAngle(orientation))

    override fun rotateAroundPointTo(point: Vector2F, orientation: ComplexF) =
        rotateAroundPointToImpl(point, orientation)

    override fun scaledBy(factor: Float): MutableTriangle {
        val centroid: Vector2F = _centroid
        val (prAR: Float, prAI: Float) = _pathRotorA
        val pathRotorAB: ComplexF = _pathRotorAB
        val pathRotorAC: ComplexF = _pathRotorAC
        val factorSign: Float = 1f.withSign(factor)
        val (cX: Float, cY: Float) = centroid
        val (prABR: Float, prABI: Float) = pathRotorAB
        val (prACR: Float, prACI: Float) = pathRotorAC
        val pathRotorAR: Float = prAR * factorSign
        val pathRotorAI: Float = prAI * factorSign
        val pointRotorBR: Float = pathRotorAR * prABR - pathRotorAI * prABI
        val pointRotorBI: Float = pathRotorAI * prABR + pathRotorAR * prABI
        val pointRotorCR: Float = pathRotorAR * prACR - pathRotorAI * prACI
        val pointRotorCI: Float = pathRotorAI * prACR + pathRotorAR * prACI
        val absFactor: Float = factor.absoluteValue
        val pointDistanceA: Float = _pointDistanceA * absFactor
        val pointDistanceB: Float = _pointDistanceB * absFactor
        val pointDistanceC: Float = _pointDistanceC * absFactor

        return MutableTriangle(
            centroid,
            pathRotorA = ComplexF(pathRotorAR, pathRotorAI),
            pointDistanceA,
            pathRotorAB,
            pointDistanceB,
            pathRotorAC,
            pointDistanceC,
            pointA = Vector2F(
                pathRotorAR * pointDistanceA + cX, pathRotorAI * pointDistanceA + cY
            ),
            pointB = Vector2F(
                pointRotorBR * pointDistanceB + cX, pointRotorBI * pointDistanceB + cY
            ),
            pointC = Vector2F(
                pointRotorCR * pointDistanceC + cX, pointRotorCI * pointDistanceC + cY
            )
        )
    }

    override fun dilatedBy(point: Vector2F, factor: Float): MutableTriangle {
        val centroid: Vector2F = _centroid
        val (prAR: Float, prAI: Float) = _pathRotorA
        val pathRotorAB: ComplexF = _pathRotorAB
        val pathRotorAC: ComplexF = _pathRotorAC
        val (pX: Float, pY: Float) = point
        val (cX: Float, cY: Float) = centroid
        val f: Float = 1f - factor
        val centroidX: Float = cX * factor + pX * f
        val centroidY: Float = cY * factor + pY * f
        val (prABR: Float, prABI: Float) = pathRotorAB
        val (prACR: Float, prACI: Float) = pathRotorAC
        val factorSign: Float = 1f.withSign(factor)
        val pathRotorAR: Float = prAR * factorSign
        val pathRotorAI: Float = prAI * factorSign
        val pointRotorBR: Float = pathRotorAR * prABR - pathRotorAI * prABI
        val pointRotorBI: Float = pathRotorAI * prABR + pathRotorAR * prABI
        val pointRotorCR: Float = pathRotorAR * prACR - pathRotorAI * prACI
        val pointRotorCI: Float = pathRotorAI * prACR + pathRotorAR * prACI
        val absFactor: Float = factor.absoluteValue
        val pointDistanceA: Float = _pointDistanceA * absFactor
        val pointDistanceB: Float = _pointDistanceB * absFactor
        val pointDistanceC: Float = _pointDistanceC * absFactor

        return MutableTriangle(
            centroid = Vector2F(centroidX, centroidY),
            pathRotorA = ComplexF(pathRotorAR, pathRotorAI),
            pointDistanceA,
            pathRotorAB,
            pointDistanceB,
            pathRotorAC,
            pointDistanceC,
            pointA = Vector2F(
                pathRotorAR * pointDistanceA + centroidX,
                pathRotorAI * pointDistanceA + centroidY
            ),
            pointB = Vector2F(
                pointRotorBR * pointDistanceB + centroidX,
                pointRotorBI * pointDistanceB + centroidY
            ),
            pointC = Vector2F(
                pointRotorCR * pointDistanceC + centroidX,
                pointRotorCI * pointDistanceC + centroidY
            )
        )
    }

    override fun scaleBy(factor: Float) {
        val centroid: Vector2F = _centroid
        val (prAR: Float, prAI: Float) = _pathRotorA
        val pathRotorAB: ComplexF = _pathRotorAB
        val pathRotorAC: ComplexF = _pathRotorAC
        val factorSign: Float = 1f.withSign(factor)
        val (cX: Float, cY: Float) = centroid
        val (prABR: Float, prABI: Float) = pathRotorAB
        val (prACR: Float, prACI: Float) = pathRotorAC
        val pathRotorAR: Float = prAR * factorSign
        val pathRotorAI: Float = prAI * factorSign
        val pointRotorBR: Float = pathRotorAR * prABR - pathRotorAI * prABI
        val pointRotorBI: Float = pathRotorAI * prABR + pathRotorAR * prABI
        val pointRotorCR: Float = pathRotorAR * prACR - pathRotorAI * prACI
        val pointRotorCI: Float = pathRotorAI * prACR + pathRotorAR * prACI
        val absFactor: Float = factor.absoluteValue
        val pointDistanceA: Float = _pointDistanceA * absFactor
        val pointDistanceB: Float = _pointDistanceB * absFactor
        val pointDistanceC: Float = _pointDistanceC * absFactor
        _pathRotorA = ComplexF(pathRotorAR, pathRotorAI)
        _pointDistanceA = pointDistanceA
        _pointDistanceB = pointDistanceB
        _pointDistanceC = pointDistanceC
        _pointA = Vector2F(
            pathRotorAR * pointDistanceA + cX, pathRotorAI * pointDistanceA + cY
        )
        _pointB = Vector2F(
            pointRotorBR * pointDistanceB + cX, pointRotorBI * pointDistanceB + cY
        )
        _pointC = Vector2F(
            pointRotorCR * pointDistanceC + cX, pointRotorCI * pointDistanceC + cY
        )
    }

    override fun dilateBy(point: Vector2F, factor: Float) {
        val centroid: Vector2F = _centroid
        val (prAR: Float, prAI: Float) = _pathRotorA
        val pathRotorAB: ComplexF = _pathRotorAB
        val pathRotorAC: ComplexF = _pathRotorAC
        val (pX: Float, pY: Float) = point
        val (cX: Float, cY: Float) = centroid
        val f: Float = 1f - factor
        val centroidX: Float = cX * factor + pX * f
        val centroidY: Float = cY * factor + pY * f
        val (prABR: Float, prABI: Float) = pathRotorAB
        val (prACR: Float, prACI: Float) = pathRotorAC
        val factorSign: Float = 1f.withSign(factor)
        val pathRotorAR: Float = prAR * factorSign
        val pathRotorAI: Float = prAI * factorSign
        val pointRotorBR: Float = pathRotorAR * prABR - pathRotorAI * prABI
        val pointRotorBI: Float = pathRotorAI * prABR + pathRotorAR * prABI
        val pointRotorCR: Float = pathRotorAR * prACR - pathRotorAI * prACI
        val pointRotorCI: Float = pathRotorAI * prACR + pathRotorAR * prACI
        val absFactor: Float = factor.absoluteValue
        val pointDistanceA: Float = _pointDistanceA * absFactor
        val pointDistanceB: Float = _pointDistanceB * absFactor
        val pointDistanceC: Float = _pointDistanceC * absFactor
        _centroid = Vector2F(centroidX, centroidY)
        _pathRotorA = ComplexF(pathRotorAR, pathRotorAI)
        _pointDistanceA = pointDistanceA
        _pointDistanceB = pointDistanceB
        _pointDistanceC = pointDistanceC
        _pointA = Vector2F(
            pathRotorAR * pointDistanceA + centroidX,
            pathRotorAI * pointDistanceA + centroidY
        )
        _pointB = Vector2F(
            pointRotorBR * pointDistanceB + centroidX,
            pointRotorBI * pointDistanceB + centroidY
        )
        _pointC = Vector2F(
            pointRotorCR * pointDistanceC + centroidX,
            pointRotorCI * pointDistanceC + centroidY
        )
    }

    private inline fun transformedByImpl(
        displacement: Vector2F, rotation: ComplexF
    ): MutableTriangle {
        val centroid: Vector2F = _centroid
        val (prAR: Float, prAI: Float) = _pathRotorA
        val pdA: Float = _pointDistanceA
        val pathRotorAB: ComplexF = _pathRotorAB
        val pdB: Float = _pointDistanceB
        val pathRotorAC: ComplexF = _pathRotorAC
        val pdC: Float = _pointDistanceC
        val (cX: Float, cY: Float) = centroid
        val (prABR: Float, prABI: Float) = pathRotorAB
        val (prACR: Float, prACI: Float) = pathRotorAC
        val (displacementX: Float, displacementY: Float) = displacement
        val (rotR: Float, rotI: Float) = rotation
        val centroidX: Float = cX + displacementX
        val centroidY: Float = cY + displacementY
        val pathRotorAR: Float = prAR * rotR - prAI * rotI
        val pathRotorAI: Float = prAI * rotR + prAR * rotI
        val pointRotorBR: Float = pathRotorAR * prABR - pathRotorAI * prABI
        val pointRotorBI: Float = pathRotorAI * prABR + pathRotorAR * prABI
        val pointRotorCR: Float = pathRotorAR * prACR - pathRotorAI * prACI
        val pointRotorCI: Float = pathRotorAI * prACR + pathRotorAR * prACI

        return MutableTriangle(
            centroid = Vector2F(centroidX, centroidY),
            pathRotorA = ComplexF(pathRotorAR, pathRotorAI),
            pdA,
            pathRotorAB,
            pdB,
            pathRotorAC,
            pdC,
            pointA = Vector2F(pathRotorAR * pdA + centroidX, pathRotorAI * pdA + centroidY),
            pointB = Vector2F(pointRotorBR * pdB + centroidX, pointRotorBI * pdB + centroidY),
            pointC = Vector2F(pointRotorCR * pdC + centroidX, pointRotorCI * pdC + centroidY)
        )
    }

    override fun transformedBy(displacement: Vector2F, rotation: AngleF): MutableTriangle =
        transformedByImpl(displacement, ComplexF.fromAngle(rotation))

    override fun transformedBy(displacement: Vector2F, rotation: ComplexF): MutableTriangle =
        transformedByImpl(displacement, rotation)

    private inline fun transformedByImpl(
        displacement: Vector2F, rotation: ComplexF, scaleFactor: Float
    ): MutableTriangle {
        val centroid: Vector2F = _centroid
        val (prAR: Float, prAI: Float) = _pathRotorA
        val pathRotorAB: ComplexF = _pathRotorAB
        val pathRotorAC: ComplexF = _pathRotorAC
        val (cX: Float, cY: Float) = centroid
        val (prABR: Float, prABI: Float) = pathRotorAB
        val (prACR: Float, prACI: Float) = pathRotorAC
        val (displacementX: Float, displacementY: Float) = displacement
        val (rotR: Float, rotI: Float) = rotation
        val centroidX: Float = cX + displacementX
        val centroidY: Float = cY + displacementY
        val factorSign: Float = 1f.withSign(scaleFactor)
        val pathRotorAR: Float = (prAR * rotR - prAI * rotI) * factorSign
        val pathRotorAI: Float = (prAI * rotR + prAR * rotI) * factorSign
        val pointRotorBR: Float = pathRotorAR * prABR - pathRotorAI * prABI
        val pointRotorBI: Float = pathRotorAI * prABR + pathRotorAR * prABI
        val pointRotorCR: Float = pathRotorAR * prACR - pathRotorAI * prACI
        val pointRotorCI: Float = pathRotorAI * prACR + pathRotorAR * prACI
        val absFactor: Float = scaleFactor.absoluteValue
        val pointDistanceA: Float = _pointDistanceA * absFactor
        val pointDistanceB: Float = _pointDistanceB * absFactor
        val pointDistanceC: Float = _pointDistanceC * absFactor

        return MutableTriangle(
            centroid = Vector2F(centroidX, centroidY),
            pathRotorA = ComplexF(pathRotorAR, pathRotorAI),
            pointDistanceA,
            pathRotorAB,
            pointDistanceB,
            pathRotorAC,
            pointDistanceC,
            pointA = Vector2F(
                pathRotorAR * pointDistanceA + centroidX,
                pathRotorAI * pointDistanceA + centroidY
            ),
            pointB = Vector2F(
                pointRotorBR * pointDistanceB + centroidX,
                pointRotorBI * pointDistanceB + centroidY
            ),
            pointC = Vector2F(
                pointRotorCR * pointDistanceC + centroidX,
                pointRotorCI * pointDistanceC + centroidY
            )
        )
    }

    override fun transformedBy(
        displacement: Vector2F, rotation: AngleF, scaleFactor: Float
    ): MutableTriangle = transformedByImpl(displacement, ComplexF.fromAngle(rotation), scaleFactor)

    override fun transformedBy(
        displacement: Vector2F, rotation: ComplexF, scaleFactor: Float
    ): MutableTriangle = transformedByImpl(displacement, rotation, scaleFactor)

    private inline fun transformedToImpl(
        position: Vector2F, orientation: ComplexF
    ): MutableTriangle {
        val pdA: Float = _pointDistanceA
        val pathRotorAB: ComplexF = _pathRotorAB
        val pdB: Float = _pointDistanceB
        val pathRotorAC: ComplexF = _pathRotorAC
        val pdC: Float = _pointDistanceC
        val (prABR: Float, prABI: Float) = pathRotorAB
        val (prACR: Float, prACI: Float) = pathRotorAC
        val (pX: Float, pY: Float) = position
        val (oR: Float, oI: Float) = orientation
        val pointRotorBR: Float = oR * prABR - oI * prABI
        val pointRotorBI: Float = oI * prABR + oR * prABI
        val pointRotorCR: Float = oR * prACR - oI * prACI
        val pointRotorCI: Float = oI * prACR + oR * prACI

        return MutableTriangle(
            centroid = position,
            pathRotorA = orientation,
            pdA,
            pathRotorAB,
            pdB,
            pathRotorAC,
            pdC,
            pointA = Vector2F(oR * pdA + pX, oI * pdA + pY),
            pointB = Vector2F(pointRotorBR * pdB + pX, pointRotorBI * pdB + pY),
            pointC = Vector2F(pointRotorCR * pdC + pX, pointRotorCI * pdC + pY)
        )
    }

    override fun transformedTo(position: Vector2F, orientation: AngleF): MutableTriangle =
        transformedToImpl(position, ComplexF.fromAngle(orientation))

    override fun transformedTo(position: Vector2F, orientation: ComplexF): MutableTriangle =
        transformedToImpl(position, orientation)

    private inline fun transformByImpl(displacement: Vector2F, rotation: ComplexF) {
        val centroid: Vector2F = _centroid
        val (prAR: Float, prAI: Float) = _pathRotorA
        val pdA: Float = _pointDistanceA
        val pathRotorAB: ComplexF = _pathRotorAB
        val pdB: Float = _pointDistanceB
        val pathRotorAC: ComplexF = _pathRotorAC
        val pdC: Float = _pointDistanceC
        val (cX: Float, cY: Float) = centroid
        val (prABR: Float, prABI: Float) = pathRotorAB
        val (prACR: Float, prACI: Float) = pathRotorAC
        val (displacementX: Float, displacementY: Float) = displacement
        val (rotR: Float, rotI: Float) = rotation
        val centroidX: Float = cX + displacementX
        val centroidY: Float = cY + displacementY
        val pathRotorAR: Float = prAR * rotR - prAI * rotI
        val pathRotorAI: Float = prAI * rotR + prAR * rotI
        val pointRotorBR: Float = pathRotorAR * prABR - pathRotorAI * prABI
        val pointRotorBI: Float = pathRotorAI * prABR + pathRotorAR * prABI
        val pointRotorCR: Float = pathRotorAR * prACR - pathRotorAI * prACI
        val pointRotorCI: Float = pathRotorAI * prACR + pathRotorAR * prACI
        _centroid = Vector2F(centroidX, centroidY)
        _pathRotorA = ComplexF(pathRotorAR, pathRotorAI)
        _pointA = Vector2F(pathRotorAR * pdA + centroidX, pathRotorAI * pdA + centroidY)
        _pointB = Vector2F(pointRotorBR * pdB + centroidX, pointRotorBI * pdB + centroidY)
        _pointC = Vector2F(pointRotorCR * pdC + centroidX, pointRotorCI * pdC + centroidY)
    }

    override fun transformBy(displacement: Vector2F, rotation: AngleF) =
        transformByImpl(displacement, ComplexF.fromAngle(rotation))

    override fun transformBy(displacement: Vector2F, rotation: ComplexF) =
        transformByImpl(displacement, rotation)

    private inline fun transformByImpl(
        displacement: Vector2F, rotation: ComplexF, scaleFactor: Float
    ) {
        val centroid: Vector2F = _centroid
        val (prAR: Float, prAI: Float) = _pathRotorA
        val pathRotorAB: ComplexF = _pathRotorAB
        val pathRotorAC: ComplexF = _pathRotorAC
        val (cX: Float, cY: Float) = centroid
        val (prABR: Float, prABI: Float) = pathRotorAB
        val (prACR: Float, prACI: Float) = pathRotorAC
        val (displacementX: Float, displacementY: Float) = displacement
        val (rotR: Float, rotI: Float) = rotation
        val centroidX: Float = cX + displacementX
        val centroidY: Float = cY + displacementY
        val factorSign: Float = 1f.withSign(scaleFactor)
        val pathRotorAR: Float = (prAR * rotR - prAI * rotI) * factorSign
        val pathRotorAI: Float = (prAI * rotR + prAR * rotI) * factorSign
        val pointRotorBR: Float = pathRotorAR * prABR - pathRotorAI * prABI
        val pointRotorBI: Float = pathRotorAI * prABR + pathRotorAR * prABI
        val pointRotorCR: Float = pathRotorAR * prACR - pathRotorAI * prACI
        val pointRotorCI: Float = pathRotorAI * prACR + pathRotorAR * prACI
        val absFactor: Float = scaleFactor.absoluteValue
        val pointDistanceA: Float = _pointDistanceA * absFactor
        val pointDistanceB: Float = _pointDistanceB * absFactor
        val pointDistanceC: Float = _pointDistanceC * absFactor
        _centroid = Vector2F(centroidX, centroidY)
        _pathRotorA = ComplexF(pathRotorAR, pathRotorAI)
        _pointDistanceA = pointDistanceA
        _pointDistanceB = pointDistanceB
        _pointDistanceC = pointDistanceC
        _pointA = Vector2F(
            pathRotorAR * pointDistanceA + centroidX,
            pathRotorAI * pointDistanceA + centroidY
        )
        _pointB = Vector2F(
            pointRotorBR * pointDistanceB + centroidX,
            pointRotorBI * pointDistanceB + centroidY
        )
        _pointC = Vector2F(
            pointRotorCR * pointDistanceC + centroidX,
            pointRotorCI * pointDistanceC + centroidY
        )
    }

    override fun transformBy(displacement: Vector2F, rotation: AngleF, scaleFactor: Float) =
        transformByImpl(displacement, ComplexF.fromAngle(rotation), scaleFactor)

    override fun transformBy(displacement: Vector2F, rotation: ComplexF, scaleFactor: Float) =
        transformByImpl(displacement, rotation, scaleFactor)

    private inline fun transformToImpl(position: Vector2F, orientation: ComplexF) {
        val pdA: Float = _pointDistanceA
        val pathRotorAB: ComplexF = _pathRotorAB
        val pdB: Float = _pointDistanceB
        val pathRotorAC: ComplexF = _pathRotorAC
        val pdC: Float = _pointDistanceC
        val (prABR: Float, prABI: Float) = pathRotorAB
        val (prACR: Float, prACI: Float) = pathRotorAC
        val (pX: Float, pY: Float) = position
        val (oR: Float, oI: Float) = orientation
        val pointRotorBR: Float = oR * prABR - oI * prABI
        val pointRotorBI: Float = oI * prABR + oR * prABI
        val pointRotorCR: Float = oR * prACR - oI * prACI
        val pointRotorCI: Float = oI * prACR + oR * prACI
        _centroid = position
        _pathRotorA = orientation
        _pointA = Vector2F(oR * pdA + pX, oI * pdA + pY)
        _pointB = Vector2F(pointRotorBR * pdB + pX, pointRotorBI * pdB + pY)
        _pointC = Vector2F(pointRotorCR * pdC + pX, pointRotorCI * pdC + pY)
    }

    override fun transformTo(position: Vector2F, orientation: AngleF) =
        transformToImpl(position, ComplexF.fromAngle(orientation))

    override fun transformTo(position: Vector2F, orientation: ComplexF) =
        transformToImpl(position, orientation)

    /**
     * Calibrates the properties of this instance. If the [pathRotorA], [pathRotorAB], or
     * [pathRotorAC] cannot be normalized, it will take the value of [ONE][ComplexF.ONE].
     *
     * Transformations and operations involving floating point numbers may introduce various
     * inaccuracies that can be countered by this method.
     */
    fun calibrate() {
        val pathRotorA: ComplexF = _pathRotorA.normalizedOrElse(ComplexF(1f, 0f))
        val pathRotorAB: ComplexF = _pathRotorAB.normalizedOrElse(ComplexF(1f, 0f))
        val pathRotorAC: ComplexF = _pathRotorAC.normalizedOrElse(ComplexF(1f, 0f))
        val (cX: Float, cY: Float) = _centroid
        val pdA: Float = _pointDistanceA
        val pdB: Float = _pointDistanceB
        val pdC: Float = _pointDistanceC
        val (prAR: Float, prAI: Float) = pathRotorA
        val (prABR: Float, prABI: Float) = pathRotorAB
        val (prACR: Float, prACI: Float) = pathRotorAC
        _pathRotorA = pathRotorA
        _pathRotorAB = pathRotorAB
        _pathRotorAC = pathRotorAC
        _pointA = Vector2F(prAR * pdA + cX, prAI * pdA + cY)
        _pointB = Vector2F(
            (prAR * prABR - prAI * prABI) * pdB + cX,
            (prAI * prABR + prAR * prABI) * pdB + cY
        )
        _pointC = Vector2F(
            (prAR * prACR - prAI * prACI) * pdC + cX,
            (prAI * prACR + prAR * prACI) * pdC + cY
        )
    }

    private inline fun setInternal(
        centroid: Vector2F,
        pathRotorA: ComplexF,
        pointDistanceA: Float,
        pathRotorAB: ComplexF,
        pointDistanceB: Float,
        pathRotorAC: ComplexF,
        pointDistanceC: Float
    ) {
        val (cX: Float, cY: Float) = centroid
        val (prAR: Float, prAI: Float) = pathRotorA
        val (prABR: Float, prABI: Float) = pathRotorAB
        val (prACR: Float, prACI: Float) = pathRotorAC
        _centroid = centroid
        _pathRotorA = pathRotorA
        _pointDistanceA = pointDistanceA
        _pathRotorAB = pathRotorAB
        _pointDistanceB = pointDistanceB
        _pathRotorAC = pathRotorAC
        _pointDistanceC = pointDistanceC
        _pointA = Vector2F(prAR * pointDistanceA + cX, prAI * pointDistanceA + cY)
        _pointB = Vector2F(
            (prAR * prABR - prAI * prABI) * pointDistanceB + cX,
            (prAI * prABR + prAR * prABI) * pointDistanceB + cY
        )
        _pointC = Vector2F(
            (prAR * prACR - prAI * prACI) * pointDistanceC + cX,
            (prAI * prACR + prAR * prACI) * pointDistanceC + cY
        )
    }

    /**
     * Sets the specified properties of this instance.
     *
     * @param pathRotorA the value is expected to be [normalized][ComplexF.normalized].
     * @param pathRotorAB the value is expected to be [normalized][ComplexF.normalized].
     * @param pathRotorAC the value is expected to be [normalized][ComplexF.normalized].
     * @throws IllegalArgumentException when [pointDistanceA] is less than zero.
     * @throws IllegalArgumentException when [pointDistanceB] is less than zero.
     * @throws IllegalArgumentException when [pointDistanceC] is less than zero.
     */
    fun set(
        centroid: Vector2F = this.centroid,
        pathRotorA: ComplexF = this.pathRotorA,
        pointDistanceA: Float = this.pointDistanceA,
        pathRotorAB: ComplexF = this.pathRotorAB,
        pointDistanceB: Float = this.pointDistanceB,
        pathRotorAC: ComplexF = this.pathRotorAC,
        pointDistanceC: Float = this.pointDistanceC
    ) {
        throwWhenConstructorArgumentsAreIllegal(pointDistanceA, pointDistanceB, pointDistanceC)
        setInternal(
            centroid,
            pathRotorA,
            pointDistanceA,
            pathRotorAB,
            pointDistanceB,
            pathRotorAC,
            pointDistanceC
        )
    }

    override fun interpolated(to: Triangle, by: Float): MutableTriangle {
        val centroid = Vector2F.lerp(_centroid, to.centroid, by)
        val pathRotorA = ComplexF.slerp(_pathRotorA, to.pathRotorA, by)
        val pointDistanceA = Float.lerp(_pointDistanceA, to.pointDistanceA, by)
        val (fPrABR: Float, fPrABI: Float) = _pathRotorAB
        val (fPrACR: Float, fPrACI: Float) = _pathRotorAC
        val (tPrABR: Float, tPrABI: Float) = to.pathRotorAB
        val (tPrACR: Float, tPrACI: Float) = to.pathRotorAC
        val oneMinusBy: Float = 1f - by
        val pointSpinorBFactorA: Float = _pointDistanceB * oneMinusBy
        val pointSpinorBFactorB: Float = to.pointDistanceB * by
        val pointSpinorBR: Float = fPrABR * pointSpinorBFactorA +
                tPrABR * pointSpinorBFactorB
        val pointSpinorBI: Float = fPrABI * pointSpinorBFactorA +
                tPrABI * pointSpinorBFactorB
        val pointSpinorCFactorA: Float = _pointDistanceC * oneMinusBy
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
            ) else ComplexF(1f, 0f)
        val pointDistanceC: Float =
            sqrt(pointSpinorCR * pointSpinorCR + pointSpinorCI * pointSpinorCI)
        val pathRotorAC: ComplexF =
            if (pointDistanceC > 0.00001f) ComplexF(
                pointSpinorCR / pointDistanceC, pointSpinorCI / pointDistanceC
            ) else ComplexF(1f, 0f)
        val (cX: Float, cY: Float) = centroid
        val (prAR: Float, prAI: Float) = pathRotorA
        val (prABR: Float, prABI: Float) = pathRotorAB
        val (prACR: Float, prACI: Float) = pathRotorAC
        val pointA = Vector2F(prAR * pointDistanceA + cX, prAI * pointDistanceA + cY)
        val pointB = Vector2F(
            (prAR * prABR - prAI * prABI) * pointDistanceB + cX,
            (prAI * prABR + prAR * prABI) * pointDistanceB + cY
        )
        val pointC = Vector2F(
            (prAR * prACR - prAI * prACI) * pointDistanceC + cX,
            (prAI * prACR + prAR * prACI) * pointDistanceC + cY
        )

        return MutableTriangle(
            centroid,
            pathRotorA,
            pointDistanceA,
            pathRotorAB,
            pointDistanceB,
            pathRotorAC,
            pointDistanceC,
            pointA,
            pointB,
            pointC
        )
    }

    /**
     * Sets this triangle with the result of interpolation [from] one triangle [to] another
     * triangle [by] a factor.
     *
     * @param from the triangle from which the interpolation starts.
     * @param to the triangle at which the interpolation ends.
     * @param by the interpolation factor which is expected to be in the range of `[0, 1]`.
     */
    fun interpolate(from: Triangle, to: Triangle, by: Float) {
        val centroid = Vector2F.lerp(from.centroid, to.centroid, by)
        val pathRotorA = ComplexF.slerp(from.pathRotorA, to.pathRotorA, by)
        val pointDistanceA = Float.lerp(from.pointDistanceA, to.pointDistanceA, by)
        val (fPrABR: Float, fPrABI: Float) = from.pathRotorAB
        val (fPrACR: Float, fPrACI: Float) = from.pathRotorAC
        val (tPrABR: Float, tPrABI: Float) = to.pathRotorAB
        val (tPrACR: Float, tPrACI: Float) = to.pathRotorAC
        val oneMinusBy: Float = 1f - by
        val pointSpinorBFactorA: Float = from.pointDistanceB * oneMinusBy
        val pointSpinorBFactorB: Float = to.pointDistanceB * by
        val pointSpinorBR: Float = fPrABR * pointSpinorBFactorA +
                tPrABR * pointSpinorBFactorB
        val pointSpinorBI: Float = fPrABI * pointSpinorBFactorA +
                tPrABI * pointSpinorBFactorB
        val pointSpinorCFactorA: Float = from.pointDistanceC * oneMinusBy
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
            ) else ComplexF(1f, 0f)
        val pointDistanceC: Float =
            sqrt(pointSpinorCR * pointSpinorCR + pointSpinorCI * pointSpinorCI)
        val pathRotorAC: ComplexF =
            if (pointDistanceC > 0.00001f) ComplexF(
                pointSpinorCR / pointDistanceC, pointSpinorCI / pointDistanceC
            ) else ComplexF(1f, 0f)
        val (cX: Float, cY: Float) = centroid
        val (prAR: Float, prAI: Float) = pathRotorA
        val (prABR: Float, prABI: Float) = pathRotorAB
        val (prACR: Float, prACI: Float) = pathRotorAC
        val pointA = Vector2F(prAR * pointDistanceA + cX, prAI * pointDistanceA + cY)
        val pointB = Vector2F(
            (prAR * prABR - prAI * prABI) * pointDistanceB + cX,
            (prAI * prABR + prAR * prABI) * pointDistanceB + cY
        )
        val pointC = Vector2F(
            (prAR * prACR - prAI * prACI) * pointDistanceC + cX,
            (prAI * prACR + prAR * prACI) * pointDistanceC + cY
        )
        _centroid = centroid
        _pathRotorA = pathRotorA
        _pointDistanceA = pointDistanceA
        _pathRotorAB = pathRotorAB
        _pointDistanceB = pointDistanceB
        _pathRotorAC = pathRotorAC
        _pointDistanceC = pointDistanceC
        _pointA = pointA
        _pointB = pointB
        _pointC = pointC
    }

    override fun closestPointTo(point: Vector2F): Vector2F {
        val a: Vector2F = _pointA
        val b: Vector2F = _pointB
        val c: Vector2F = _pointC
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

    override fun intersects(ray: Ray): Boolean {
        val (aX: Float, aY: Float) = _pointA
        val (bX: Float, bY: Float) = _pointB
        val (cX: Float, cY: Float) = _pointC
        val (oX: Float, oY: Float) = ray.origin
        val (dirX: Float, dirY: Float) = ray.direction
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

    override operator fun contains(point: Vector2F): Boolean {
        val (aX: Float, aY: Float) = _pointA
        val (bX: Float, bY: Float) = _pointB
        val (cX: Float, cY: Float) = _pointC
        val (pX: Float, pY: Float) = point
        val abp: Boolean = ((pX - aX) * (aY - bY) + (pY - aY) * (bX - aX)) >= 0f
        val bcp: Boolean = ((pX - bX) * (bY - cY) + (pY - bY) * (cX - bX)) >= 0f
        val acp: Boolean = ((pX - cX) * (cY - aY) + (pY - cY) * (aX - cX)) >= 0f

        return (abp == bcp) and (bcp == acp)
    }

    override fun pointIterator(): Vector2FIterator = PointIterator(this, index = 0)

    /**
     * Returns a copy of this instance with specified properties changed.
     *
     * @param pathRotorA the value is expected to be [normalized][ComplexF.normalized].
     * @param pathRotorAB the value is expected to be [normalized][ComplexF.normalized].
     * @param pathRotorAC the value is expected to be [normalized][ComplexF.normalized].
     * @throws IllegalArgumentException when [pointDistanceA] is less than zero.
     * @throws IllegalArgumentException when [pointDistanceB] is less than zero.
     * @throws IllegalArgumentException when [pointDistanceC] is less than zero.
     */
    override fun copy(
        centroid: Vector2F,
        pathRotorA: ComplexF,
        pointDistanceA: Float,
        pathRotorAB: ComplexF,
        pointDistanceB: Float,
        pathRotorAC: ComplexF,
        pointDistanceC: Float
    ) = MutableTriangle(
        centroid,
        pathRotorA,
        pointDistanceA,
        pathRotorAB,
        pointDistanceB,
        pathRotorAC,
        pointDistanceC
    )

    override fun equals(other: Any?): Boolean = other is Triangle &&
            _centroid == other.centroid &&
            _pathRotorA == other.pathRotorA &&
            _pointDistanceA == other.pointDistanceA &&
            _pathRotorAB == other.pathRotorAB &&
            _pointDistanceB == other.pointDistanceB &&
            _pathRotorAC == other.pathRotorAC &&
            _pointDistanceC == other.pointDistanceC

    /** Indicates whether the other [MutableTriangle] is equal to this one. **/
    fun equals(other: MutableTriangle): Boolean =
        _centroid == other._centroid &&
                _pathRotorA == other._pathRotorA &&
                _pointDistanceA == other._pointDistanceA &&
                _pathRotorAB == other._pathRotorAB &&
                _pointDistanceB == other._pointDistanceB &&
                _pathRotorAC == other._pathRotorAC &&
                _pointDistanceC == other._pointDistanceC

    override fun hashCode(): Int {
        var result: Int = _centroid.hashCode()
        result = 31 * result + _pathRotorA.hashCode()
        result = 31 * result + _pointDistanceA.hashCode()
        result = 31 * result + _pathRotorAB.hashCode()
        result = 31 * result + _pointDistanceB.hashCode()
        result = 31 * result + _pathRotorAC.hashCode()
        result = 31 * result + _pointDistanceC.hashCode()

        return result
    }

    override fun toString() =
        StringBuilder("Triangle(centroid=").append(_centroid)
            .append(", pathRotorA=").append(_pathRotorA)
            .append(", pointDistanceA=").append(_pointDistanceA)
            .append(", pathRotorAB=").append(_pathRotorAB)
            .append(", pointDistanceB=").append(_pointDistanceB)
            .append(", pathRotorAC=").append(_pathRotorAC)
            .append(", pointDistanceC=").append(_pointDistanceC).append(")")
            .toString()

    override operator fun component1(): Vector2F = _centroid

    override operator fun component2(): ComplexF = _pathRotorA

    override operator fun component3(): Float = _pointDistanceA

    override operator fun component4(): ComplexF = _pathRotorAB

    override operator fun component5(): Float = _pointDistanceB

    override operator fun component6(): ComplexF = _pathRotorAC

    override operator fun component7(): Float = _pointDistanceC

    companion object {
        private inline fun throwWhenConstructorArgumentsAreIllegal(
            pointDistanceA: Float, pointDistanceB: Float, pointDistanceC: Float
        ) {
            if (pointDistanceA < 0f) {
                throw IllegalArgumentException(
                    "pointDistanceA must be greater than or equal to zero."
                )
            }
            if (pointDistanceB < 0f) {
                throw IllegalArgumentException(
                    "pointDistanceB must be greater than or equal to zero."
                )
            }
            if (pointDistanceC < 0f) {
                throw IllegalArgumentException(
                    "pointDistanceC must be greater than or equal to zero."
                )
            }
        }
    }

    private class PointIterator(
        private val triangle: MutableTriangle,
        private var index: Int
    ) : Vector2FIterator() {
        override fun hasNext(): Boolean = index < 3

        override fun nextVector2F(): Vector2F = when (index++) {
            0 -> triangle._pointA
            1 -> triangle._pointB
            2 -> triangle._pointC
            else -> throw NoSuchElementException("${index - 1}")
        }
    }
}