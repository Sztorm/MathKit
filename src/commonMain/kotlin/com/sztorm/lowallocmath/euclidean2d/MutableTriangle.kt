package com.sztorm.lowallocmath.euclidean2d

import com.sztorm.lowallocmath.AngleF
import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F
import com.sztorm.lowallocmath.Vector2FIterator
import kotlin.math.abs
import kotlin.math.absoluteValue
import kotlin.math.sqrt
import kotlin.math.withSign

/** Represents a mutable transformable triangle in a two-dimensional Euclidean space. **/
class MutableTriangle : Triangle, MutableTransformable {
    private var _centroid: Vector2F
    private var _originPointA: Vector2F
    private var _originPointB: Vector2F
    private var _originPointC: Vector2F
    private var _orientation: ComplexF
    private var _pointA: Vector2F
    private var _pointB: Vector2F
    private var _pointC: Vector2F

    /** Creates a new instance of [MutableTriangle]. **/
    constructor(
        centroid: Vector2F, originPointA: Vector2F, originPointB: Vector2F, originPointC: Vector2F
    ) {
        val (opAX: Float, opAY: Float) = originPointA
        val (opBX: Float, opBY: Float) = originPointB
        val (opCX: Float, opCY: Float) = originPointC
        val (centroidX: Float, centroidY: Float) = centroid
        val orientation: ComplexF = originPointA
            .toComplexF()
            .normalizedOrElse(ComplexF(1f, 0f))
        val pointA = Vector2F(opAX + centroidX, opAY + centroidY)
        val pointB = Vector2F(opBX + centroidX, opBY + centroidY)
        val pointC = Vector2F(opCX + centroidX, opCY + centroidY)
        _centroid = centroid
        _originPointA = originPointA
        _originPointB = originPointB
        _originPointC = originPointC
        _orientation = orientation
        _pointA = pointA
        _pointB = pointB
        _pointC = pointC
    }

    /** Creates a new instance of [MutableTriangle]. **/
    constructor(pointA: Vector2F, pointB: Vector2F, pointC: Vector2F) {
        val (pAX: Float, pAY: Float) = pointA
        val (pBX: Float, pBY: Float) = pointB
        val (pCX: Float, pCY: Float) = pointC
        val centroidX: Float = (pAX + pBX + pCX) * 0.3333333f
        val centroidY: Float = (pAY + pBY + pCY) * 0.3333333f
        val originPointA = Vector2F(pAX - centroidX, pAY - centroidY)
        val originPointB = Vector2F(pBX - centroidX, pBY - centroidY)
        val originPointC = Vector2F(pCX - centroidX, pCY - centroidY)
        val orientation: ComplexF = originPointA
            .toComplexF()
            .normalizedOrElse(ComplexF(1f, 0f))
        _originPointA = originPointA
        _originPointB = originPointB
        _originPointC = originPointC
        _centroid = Vector2F(centroidX, centroidY)
        _orientation = orientation
        _pointA = pointA
        _pointB = pointB
        _pointC = pointC
    }

    private constructor(
        centroid: Vector2F,
        originPointA: Vector2F,
        originPointB: Vector2F,
        originPointC: Vector2F,
        orientation: ComplexF,
        pointA: Vector2F,
        pointB: Vector2F,
        pointC: Vector2F
    ) {
        _centroid = centroid
        _originPointA = originPointA
        _originPointB = originPointB
        _originPointC = originPointC
        _orientation = orientation
        _pointA = pointA
        _pointB = pointB
        _pointC = pointC
    }

    override val centroid: Vector2F
        get() = _centroid

    override val originPointA: Vector2F
        get() = _originPointA

    override val originPointB: Vector2F
        get() = _originPointB

    override val originPointC: Vector2F
        get() = _originPointC

    override val pointA: Vector2F
        get() = _pointA

    override val pointB: Vector2F
        get() = _pointB

    override val pointC: Vector2F
        get() = _pointC

    override val orientation: ComplexF
        get() = _orientation

    override val area: Float
        get() {
            val (aX: Float, aY: Float) = _pointA
            val (bX: Float, bY: Float) = _pointB
            val (cX: Float, cY: Float) = _pointC

            return 0.5f * abs((aX - cX) * (bY - cY) - (bX - cX) * (aY - cY))
        }

    override val perimeter: Float
        get() {
            val pointA: Vector2F = _pointA
            val pointB: Vector2F = _pointB
            val pointC: Vector2F = _pointC

            return pointA.distanceTo(pointB) +
                    pointB.distanceTo(pointC) +
                    pointC.distanceTo(pointA)
        }

    override val sideLengthAB: Float
        get() = _originPointA.distanceTo(_originPointB)

    override val sideLengthBC: Float
        get() = _originPointB.distanceTo(_originPointC)

    override val sideLengthCA: Float
        get() = _originPointC.distanceTo(_originPointA)

    override val position: Vector2F
        get() = _centroid

    override val incenter: Vector2F
        get() {
            val (cX: Float, cY: Float) = _centroid
            val (opAX: Float, opAY: Float) = _originPointA
            val (opBX: Float, opBY: Float) = _originPointB
            val (opCX: Float, opCY: Float) = _originPointC
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

    override val circumcenter: Vector2F
        get() {
            val (cX: Float, cY: Float) = _centroid
            val (opAX: Float, opAY: Float) = _originPointA
            val (opBX: Float, opBY: Float) = _originPointB
            val (opCX: Float, opCY: Float) = _originPointC
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

    override val orthocenter: Vector2F
        get() {
            val (centroidX: Float, centroidY: Float) = _centroid
            val (opAX: Float, opAY: Float) = _originPointA
            val (opBX: Float, opBY: Float) = _originPointB
            val (opCX: Float, opCY: Float) = _originPointC
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

    override fun movedBy(displacement: Vector2F) = MutableTriangle(
        centroid = _centroid + displacement,
        _originPointA,
        _originPointB,
        _originPointC,
        _orientation,
        pointA = _pointA + displacement,
        pointB = _pointB + displacement,
        pointC = _pointC + displacement
    )

    override fun movedTo(position: Vector2F): MutableTriangle {
        val displacement: Vector2F = position - _centroid

        return MutableTriangle(
            centroid = position,
            _originPointA,
            _originPointB,
            _originPointC,
            _orientation,
            pointA = _pointA + displacement,
            pointB = _pointB + displacement,
            pointC = _pointC + displacement
        )
    }

    override fun moveBy(displacement: Vector2F) {
        _pointA += displacement
        _pointB += displacement
        _pointC += displacement
        _centroid += displacement
    }

    override fun moveTo(position: Vector2F) {
        val displacement: Vector2F = position - _centroid
        _pointA += displacement
        _pointB += displacement
        _pointC += displacement
        _centroid = position
    }

    private inline fun rotatedByImpl(rotation: ComplexF): MutableTriangle {
        val centroid: Vector2F = _centroid
        val (cX: Float, cY: Float) = centroid
        val (opAX: Float, opAY: Float) = _originPointA
        val (opBX: Float, opBY: Float) = _originPointB
        val (opCX: Float, opCY: Float) = _originPointC
        val (oR: Float, oI: Float) = _orientation
        val (rotR: Float, rotI: Float) = rotation
        val originPointAX: Float = opAX * rotR - opAY * rotI
        val originPointAY: Float = opAY * rotR + opAX * rotI
        val originPointBX: Float = opBX * rotR - opBY * rotI
        val originPointBY: Float = opBY * rotR + opBX * rotI
        val originPointCX: Float = opCX * rotR - opCY * rotI
        val originPointCY: Float = opCY * rotR + opCX * rotI

        return MutableTriangle(
            centroid,
            originPointA = Vector2F(originPointAX, originPointAY),
            originPointB = Vector2F(originPointBX, originPointBY),
            originPointC = Vector2F(originPointCX, originPointCY),
            orientation = ComplexF(oR * rotR - oI * rotI, oI * rotR + oR * rotI),
            pointA = Vector2F(originPointAX + cX, originPointAY + cY),
            pointB = Vector2F(originPointBX + cX, originPointBY + cY),
            pointC = Vector2F(originPointCX + cX, originPointCY + cY)
        )
    }

    override fun rotatedBy(rotation: AngleF): MutableTriangle =
        rotatedByImpl(ComplexF.fromAngle(rotation))

    override fun rotatedBy(rotation: ComplexF): MutableTriangle = rotatedByImpl(rotation)

    private inline fun rotatedToImpl(orientation: ComplexF): MutableTriangle {
        val centroid: Vector2F = _centroid
        val (cX: Float, cY: Float) = centroid
        val (opAX: Float, opAY: Float) = _originPointA
        val (opBX: Float, opBY: Float) = _originPointB
        val (opCX: Float, opCY: Float) = _originPointC
        val (rotR: Float, rotI: Float) = _orientation.conjugate * orientation
        val originPointAX: Float = opAX * rotR - opAY * rotI
        val originPointAY: Float = opAY * rotR + opAX * rotI
        val originPointBX: Float = opBX * rotR - opBY * rotI
        val originPointBY: Float = opBY * rotR + opBX * rotI
        val originPointCX: Float = opCX * rotR - opCY * rotI
        val originPointCY: Float = opCY * rotR + opCX * rotI

        return MutableTriangle(
            centroid,
            originPointA = Vector2F(originPointAX, originPointAY),
            originPointB = Vector2F(originPointBX, originPointBY),
            originPointC = Vector2F(originPointCX, originPointCY),
            orientation,
            pointA = Vector2F(originPointAX + cX, originPointAY + cY),
            pointB = Vector2F(originPointBX + cX, originPointBY + cY),
            pointC = Vector2F(originPointCX + cX, originPointCY + cY)
        )
    }

    override fun rotatedTo(orientation: AngleF): MutableTriangle =
        rotatedToImpl(ComplexF.fromAngle(orientation))

    override fun rotatedTo(orientation: ComplexF): MutableTriangle = rotatedToImpl(orientation)

    private inline fun rotatedAroundPointByImpl(
        point: Vector2F, rotation: ComplexF
    ): MutableTriangle {
        val (cX: Float, cY: Float) = _centroid
        val (opAX: Float, opAY: Float) = _originPointA
        val (opBX: Float, opBY: Float) = _originPointB
        val (opCX: Float, opCY: Float) = _originPointC
        val (oR: Float, oI: Float) = _orientation
        val (pX: Float, pY: Float) = point
        val (rotR: Float, rotI: Float) = rotation
        val cpDiffX: Float = cX - pX
        val cpDiffY: Float = cY - pY
        val centroidX: Float = cpDiffX * rotR - cpDiffY * rotI + pX
        val centroidY: Float = cpDiffY * rotR + cpDiffX * rotI + pY
        val originPointAX: Float = opAX * rotR - opAY * rotI
        val originPointAY: Float = opAY * rotR + opAX * rotI
        val originPointBX: Float = opBX * rotR - opBY * rotI
        val originPointBY: Float = opBY * rotR + opBX * rotI
        val originPointCX: Float = opCX * rotR - opCY * rotI
        val originPointCY: Float = opCY * rotR + opCX * rotI

        return MutableTriangle(
            centroid = Vector2F(centroidX, centroidY),
            originPointA = Vector2F(originPointAX, originPointAY),
            originPointB = Vector2F(originPointBX, originPointBY),
            originPointC = Vector2F(originPointCX, originPointCY),
            orientation = ComplexF(oR * rotR - oI * rotI, oI * rotR + oR * rotI),
            pointA = Vector2F(originPointAX + centroidX, originPointAY + centroidY),
            pointB = Vector2F(originPointBX + centroidX, originPointBY + centroidY),
            pointC = Vector2F(originPointCX + centroidX, originPointCY + centroidY)
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
        val (cX: Float, cY: Float) = centroid
        val (opAX: Float, opAY: Float) = _originPointA
        val (opBX: Float, opBY: Float) = _originPointB
        val (opCX: Float, opCY: Float) = _originPointC
        val (startOR: Float, startOI: Float) = _orientation
        val (pX: Float, pY: Float) = point
        val (oR: Float, oI: Float) = orientation
        val cpDiffX: Float = cX - pX
        val cpDiffY: Float = cY - pY
        val centroidToPointDist: Float = sqrt(cpDiffX * cpDiffX + cpDiffY * cpDiffY)

        if (centroidToPointDist > 0.00001f) {
            val centroidToPointDistReciprocal: Float = 1f / centroidToPointDist
            val pointRotR: Float = cpDiffX * centroidToPointDistReciprocal
            val pointRotI: Float = cpDiffY * centroidToPointDistReciprocal
            val pRotTimesStartOR: Float = pointRotR * startOR + pointRotI * startOI
            val pRotTimesStartOI: Float = pointRotR * startOI - pointRotI * startOR
            val pRotR: Float = pointRotR * oR + pointRotI * oI
            val pRotI: Float = pointRotR * oI - pointRotI * oR
            val originPointAX: Float = opAX * pRotR - opAY * pRotI
            val originPointAY: Float = opAY * pRotR + opAX * pRotI
            val originPointBX: Float = opBX * pRotR - opBY * pRotI
            val originPointBY: Float = opBY * pRotR + opBX * pRotI
            val originPointCX: Float = opCX * pRotR - opCY * pRotI
            val originPointCY: Float = opCY * pRotR + opCX * pRotI
            val centroidX: Float = oR * centroidToPointDist + pX
            val centroidY: Float = oI * centroidToPointDist + pY

            return MutableTriangle(
                centroid = Vector2F(centroidX, centroidY),
                originPointA = Vector2F(originPointAX, originPointAY),
                originPointB = Vector2F(originPointBX, originPointBY),
                originPointC = Vector2F(originPointCX, originPointCY),
                orientation = ComplexF(
                    pRotTimesStartOR * oR - pRotTimesStartOI * oI,
                    pRotTimesStartOI * oR + pRotTimesStartOR * oI
                ),
                pointA = Vector2F(originPointAX + centroidX, originPointAY + centroidY),
                pointB = Vector2F(originPointBX + centroidX, originPointBY + centroidY),
                pointC = Vector2F(originPointCX + centroidX, originPointCY + centroidY)
            )
        } else {
            val pRotR: Float = oR * startOR + oI * startOI
            val pRotI: Float = oI * startOR - oR * startOI
            val originPointAX: Float = opAX * pRotR - opAY * pRotI
            val originPointAY: Float = opAY * pRotR + opAX * pRotI
            val originPointBX: Float = opBX * pRotR - opBY * pRotI
            val originPointBY: Float = opBY * pRotR + opBX * pRotI
            val originPointCX: Float = opCX * pRotR - opCY * pRotI
            val originPointCY: Float = opCY * pRotR + opCX * pRotI

            return MutableTriangle(
                centroid,
                originPointA = Vector2F(originPointAX, originPointAY),
                originPointB = Vector2F(originPointBX, originPointBY),
                originPointC = Vector2F(originPointCX, originPointCY),
                orientation,
                pointA = Vector2F(originPointAX + cX, originPointAY + cY),
                pointB = Vector2F(originPointBX + cX, originPointBY + cY),
                pointC = Vector2F(originPointCX + cX, originPointCY + cY)
            )
        }
    }

    override fun rotatedAroundPointTo(point: Vector2F, orientation: AngleF): MutableTriangle =
        rotatedAroundPointToImpl(point, ComplexF.fromAngle(orientation))

    override fun rotatedAroundPointTo(point: Vector2F, orientation: ComplexF): MutableTriangle =
        rotatedAroundPointToImpl(point, orientation)

    private inline fun rotateByImpl(rotation: ComplexF) {
        val (cX: Float, cY: Float) = _centroid
        val (opAX: Float, opAY: Float) = _originPointA
        val (opBX: Float, opBY: Float) = _originPointB
        val (opCX: Float, opCY: Float) = _originPointC
        val (oR: Float, oI: Float) = _orientation
        val (rotR: Float, rotI: Float) = rotation
        val originPointAX: Float = opAX * rotR - opAY * rotI
        val originPointAY: Float = opAY * rotR + opAX * rotI
        val originPointBX: Float = opBX * rotR - opBY * rotI
        val originPointBY: Float = opBY * rotR + opBX * rotI
        val originPointCX: Float = opCX * rotR - opCY * rotI
        val originPointCY: Float = opCY * rotR + opCX * rotI
        _originPointA = Vector2F(originPointAX, originPointAY)
        _originPointB = Vector2F(originPointBX, originPointBY)
        _originPointC = Vector2F(originPointCX, originPointCY)
        _orientation = ComplexF(oR * rotR - oI * rotI, oI * rotR + oR * rotI)
        _pointA = Vector2F(originPointAX + cX, originPointAY + cY)
        _pointB = Vector2F(originPointBX + cX, originPointBY + cY)
        _pointC = Vector2F(originPointCX + cX, originPointCY + cY)
    }

    override fun rotateBy(rotation: AngleF) = rotateByImpl(ComplexF.fromAngle(rotation))

    override fun rotateBy(rotation: ComplexF) = rotateByImpl(rotation)

    private inline fun rotateToImpl(orientation: ComplexF) {
        val (cX: Float, cY: Float) = _centroid
        val (opAX: Float, opAY: Float) = _originPointA
        val (opBX: Float, opBY: Float) = _originPointB
        val (opCX: Float, opCY: Float) = _originPointC
        val (rotR: Float, rotI: Float) = _orientation.conjugate * orientation
        val originPointAX: Float = opAX * rotR - opAY * rotI
        val originPointAY: Float = opAY * rotR + opAX * rotI
        val originPointBX: Float = opBX * rotR - opBY * rotI
        val originPointBY: Float = opBY * rotR + opBX * rotI
        val originPointCX: Float = opCX * rotR - opCY * rotI
        val originPointCY: Float = opCY * rotR + opCX * rotI
        _originPointA = Vector2F(originPointAX, originPointAY)
        _originPointB = Vector2F(originPointBX, originPointBY)
        _originPointC = Vector2F(originPointCX, originPointCY)
        _orientation = orientation
        _pointA = Vector2F(originPointAX + cX, originPointAY + cY)
        _pointB = Vector2F(originPointBX + cX, originPointBY + cY)
        _pointC = Vector2F(originPointCX + cX, originPointCY + cY)
    }

    override fun rotateTo(orientation: AngleF) = rotateToImpl(ComplexF.fromAngle(orientation))

    override fun rotateTo(orientation: ComplexF) = rotateToImpl(orientation)

    private inline fun rotateAroundPointByImpl(point: Vector2F, rotation: ComplexF) {
        val (cX: Float, cY: Float) = _centroid
        val (opAX: Float, opAY: Float) = _originPointA
        val (opBX: Float, opBY: Float) = _originPointB
        val (opCX: Float, opCY: Float) = _originPointC
        val (oR: Float, oI: Float) = _orientation
        val (pX: Float, pY: Float) = point
        val (rotR: Float, rotI: Float) = rotation
        val cpDiffX: Float = cX - pX
        val cpDiffY: Float = cY - pY
        val centroidX: Float = cpDiffX * rotR - cpDiffY * rotI + pX
        val centroidY: Float = cpDiffY * rotR + cpDiffX * rotI + pY
        val originPointAX: Float = opAX * rotR - opAY * rotI
        val originPointAY: Float = opAY * rotR + opAX * rotI
        val originPointBX: Float = opBX * rotR - opBY * rotI
        val originPointBY: Float = opBY * rotR + opBX * rotI
        val originPointCX: Float = opCX * rotR - opCY * rotI
        val originPointCY: Float = opCY * rotR + opCX * rotI
        _centroid = Vector2F(centroidX, centroidY)
        _originPointA = Vector2F(originPointAX, originPointAY)
        _originPointB = Vector2F(originPointBX, originPointBY)
        _originPointC = Vector2F(originPointCX, originPointCY)
        _orientation = ComplexF(oR * rotR - oI * rotI, oI * rotR + oR * rotI)
        _pointA = Vector2F(originPointAX + centroidX, originPointAY + centroidY)
        _pointB = Vector2F(originPointBX + centroidX, originPointBY + centroidY)
        _pointC = Vector2F(originPointCX + centroidX, originPointCY + centroidY)
    }

    override fun rotateAroundPointBy(point: Vector2F, rotation: AngleF) =
        rotateAroundPointByImpl(point, ComplexF.fromAngle(rotation))

    override fun rotateAroundPointBy(point: Vector2F, rotation: ComplexF) =
        rotateAroundPointByImpl(point, rotation)

    private inline fun rotateAroundPointToImpl(point: Vector2F, orientation: ComplexF) {
        val centroid: Vector2F = _centroid
        val (cX: Float, cY: Float) = centroid
        val (opAX: Float, opAY: Float) = _originPointA
        val (opBX: Float, opBY: Float) = _originPointB
        val (opCX: Float, opCY: Float) = _originPointC
        val (startOR: Float, startOI: Float) = _orientation
        val (pX: Float, pY: Float) = point
        val (oR: Float, oI: Float) = orientation
        val cpDiffX: Float = cX - pX
        val cpDiffY: Float = cY - pY
        val centroidToPointDist: Float = sqrt(cpDiffX * cpDiffX + cpDiffY * cpDiffY)

        if (centroidToPointDist > 0.00001f) {
            val centroidToPointDistReciprocal: Float = 1f / centroidToPointDist
            val pointRotR: Float = cpDiffX * centroidToPointDistReciprocal
            val pointRotI: Float = cpDiffY * centroidToPointDistReciprocal
            val pRotTimesStartOR: Float = pointRotR * startOR + pointRotI * startOI
            val pRotTimesStartOI: Float = pointRotR * startOI - pointRotI * startOR
            val pRotR: Float = pointRotR * oR + pointRotI * oI
            val pRotI: Float = pointRotR * oI - pointRotI * oR
            val originPointAX: Float = opAX * pRotR - opAY * pRotI
            val originPointAY: Float = opAY * pRotR + opAX * pRotI
            val originPointBX: Float = opBX * pRotR - opBY * pRotI
            val originPointBY: Float = opBY * pRotR + opBX * pRotI
            val originPointCX: Float = opCX * pRotR - opCY * pRotI
            val originPointCY: Float = opCY * pRotR + opCX * pRotI
            val centroidX: Float = oR * centroidToPointDist + pX
            val centroidY: Float = oI * centroidToPointDist + pY
            _centroid = Vector2F(centroidX, centroidY)
            _originPointA = Vector2F(originPointAX, originPointAY)
            _originPointB = Vector2F(originPointBX, originPointBY)
            _originPointC = Vector2F(originPointCX, originPointCY)
            _orientation = ComplexF(
                pRotTimesStartOR * oR - pRotTimesStartOI * oI,
                pRotTimesStartOI * oR + pRotTimesStartOR * oI
            )
            _pointA = Vector2F(originPointAX + centroidX, originPointAY + centroidY)
            _pointB = Vector2F(originPointBX + centroidX, originPointBY + centroidY)
            _pointC = Vector2F(originPointCX + centroidX, originPointCY + centroidY)

        } else {
            val pRotR: Float = oR * startOR + oI * startOI
            val pRotI: Float = oI * startOR - oR * startOI
            val originPointAX: Float = opAX * pRotR - opAY * pRotI
            val originPointAY: Float = opAY * pRotR + opAX * pRotI
            val originPointBX: Float = opBX * pRotR - opBY * pRotI
            val originPointBY: Float = opBY * pRotR + opBX * pRotI
            val originPointCX: Float = opCX * pRotR - opCY * pRotI
            val originPointCY: Float = opCY * pRotR + opCX * pRotI
            _originPointA = Vector2F(originPointAX, originPointAY)
            _originPointB = Vector2F(originPointBX, originPointBY)
            _originPointC = Vector2F(originPointCX, originPointCY)
            _orientation = orientation
            _pointA = Vector2F(originPointAX + cX, originPointAY + cY)
            _pointB = Vector2F(originPointBX + cX, originPointBY + cY)
            _pointC = Vector2F(originPointCX + cX, originPointCY + cY)
        }
    }

    override fun rotateAroundPointTo(point: Vector2F, orientation: AngleF) =
        rotateAroundPointToImpl(point, ComplexF.fromAngle(orientation))

    override fun rotateAroundPointTo(point: Vector2F, orientation: ComplexF) =
        rotateAroundPointToImpl(point, orientation)

    override fun scaledBy(factor: Float): MutableTriangle {
        val centroid: Vector2F = _centroid
        val (cX: Float, cY: Float) = centroid
        val (opAX: Float, opAY: Float) = _originPointA
        val (opBX: Float, opBY: Float) = _originPointB
        val (opCX: Float, opCY: Float) = _originPointC
        val originPointAX: Float = opAX * factor
        val originPointAY: Float = opAY * factor
        val originPointBX: Float = opBX * factor
        val originPointBY: Float = opBY * factor
        val originPointCX: Float = opCX * factor
        val originPointCY: Float = opCY * factor

        return MutableTriangle(
            centroid,
            originPointA = Vector2F(originPointAX, originPointAY),
            originPointB = Vector2F(originPointBX, originPointBY),
            originPointC = Vector2F(originPointCX, originPointCY),
            orientation = _orientation * 1f.withSign(factor),
            pointA = Vector2F(originPointAX + cX, originPointAY + cY),
            pointB = Vector2F(originPointBX + cX, originPointBY + cY),
            pointC = Vector2F(originPointCX + cX, originPointCY + cY)
        )
    }

    override fun dilatedBy(point: Vector2F, factor: Float): MutableTriangle {
        val (cX: Float, cY: Float) = _centroid
        val (opAX: Float, opAY: Float) = _originPointA
        val (opBX: Float, opBY: Float) = _originPointB
        val (opCX: Float, opCY: Float) = _originPointC
        val (pX: Float, pY: Float) = point
        val f: Float = 1f - factor
        val originPointAX: Float = opAX * factor
        val originPointAY: Float = opAY * factor
        val originPointBX: Float = opBX * factor
        val originPointBY: Float = opBY * factor
        val originPointCX: Float = opCX * factor
        val originPointCY: Float = opCY * factor
        val centroidX: Float = cX * factor + pX * f
        val centroidY: Float = cY * factor + pY * f

        return MutableTriangle(
            centroid = Vector2F(centroidX, centroidY),
            originPointA = Vector2F(originPointAX, originPointAY),
            originPointB = Vector2F(originPointBX, originPointBY),
            originPointC = Vector2F(originPointCX, originPointCY),
            orientation = _orientation * 1f.withSign(factor),
            pointA = Vector2F(originPointAX + centroidX, originPointAY + centroidY),
            pointB = Vector2F(originPointBX + centroidX, originPointBY + centroidY),
            pointC = Vector2F(originPointCX + centroidX, originPointCY + centroidY)
        )
    }

    override fun scaleBy(factor: Float) {
        val (cX: Float, cY: Float) = _centroid
        val (opAX: Float, opAY: Float) = _originPointA
        val (opBX: Float, opBY: Float) = _originPointB
        val (opCX: Float, opCY: Float) = _originPointC
        val originPointAX: Float = opAX * factor
        val originPointAY: Float = opAY * factor
        val originPointBX: Float = opBX * factor
        val originPointBY: Float = opBY * factor
        val originPointCX: Float = opCX * factor
        val originPointCY: Float = opCY * factor
        _originPointA = Vector2F(originPointAX, originPointAY)
        _originPointB = Vector2F(originPointBX, originPointBY)
        _originPointC = Vector2F(originPointCX, originPointCY)
        _orientation *= 1f.withSign(factor)
        _pointA = Vector2F(originPointAX + cX, originPointAY + cY)
        _pointB = Vector2F(originPointBX + cX, originPointBY + cY)
        _pointC = Vector2F(originPointCX + cX, originPointCY + cY)
    }

    override fun dilateBy(point: Vector2F, factor: Float) {
        val (cX: Float, cY: Float) = _centroid
        val (opAX: Float, opAY: Float) = _originPointA
        val (opBX: Float, opBY: Float) = _originPointB
        val (opCX: Float, opCY: Float) = _originPointC
        val (pX: Float, pY: Float) = point
        val f: Float = 1f - factor
        val originPointAX: Float = opAX * factor
        val originPointAY: Float = opAY * factor
        val originPointBX: Float = opBX * factor
        val originPointBY: Float = opBY * factor
        val originPointCX: Float = opCX * factor
        val originPointCY: Float = opCY * factor
        val centroidX: Float = cX * factor + pX * f
        val centroidY: Float = cY * factor + pY * f
        _centroid = Vector2F(centroidX, centroidY)
        _originPointA = Vector2F(originPointAX, originPointAY)
        _originPointB = Vector2F(originPointBX, originPointBY)
        _originPointC = Vector2F(originPointCX, originPointCY)
        _orientation *= 1f.withSign(factor)
        _pointA = Vector2F(originPointAX + centroidX, originPointAY + centroidY)
        _pointB = Vector2F(originPointBX + centroidX, originPointBY + centroidY)
        _pointC = Vector2F(originPointCX + centroidX, originPointCY + centroidY)
    }

    private inline fun transformedByImpl(
        displacement: Vector2F, rotation: ComplexF
    ): MutableTriangle {
        val (dX: Float, dY: Float) = displacement
        val (cX: Float, cY: Float) = _centroid
        val (opAX: Float, opAY: Float) = _originPointA
        val (opBX: Float, opBY: Float) = _originPointB
        val (opCX: Float, opCY: Float) = _originPointC
        val (oR: Float, oI: Float) = _orientation
        val (rotR: Float, rotI: Float) = rotation
        val centroidX: Float = cX + dX
        val centroidY: Float = cY + dY
        val originPointAX: Float = opAX * rotR - opAY * rotI
        val originPointAY: Float = opAY * rotR + opAX * rotI
        val originPointBX: Float = opBX * rotR - opBY * rotI
        val originPointBY: Float = opBY * rotR + opBX * rotI
        val originPointCX: Float = opCX * rotR - opCY * rotI
        val originPointCY: Float = opCY * rotR + opCX * rotI

        return MutableTriangle(
            centroid = Vector2F(centroidX, centroidY),
            originPointA = Vector2F(originPointAX, originPointAY),
            originPointB = Vector2F(originPointBX, originPointBY),
            originPointC = Vector2F(originPointCX, originPointCY),
            orientation = ComplexF(oR * rotR - oI * rotI, oI * rotR + oR * rotI),
            pointA = Vector2F(originPointAX + centroidX, originPointAY + centroidY),
            pointB = Vector2F(originPointBX + centroidX, originPointBY + centroidY),
            pointC = Vector2F(originPointCX + centroidX, originPointCY + centroidY)
        )
    }

    override fun transformedBy(displacement: Vector2F, rotation: AngleF): MutableTriangle =
        transformedByImpl(displacement, ComplexF.fromAngle(rotation))

    override fun transformedBy(displacement: Vector2F, rotation: ComplexF): MutableTriangle =
        transformedByImpl(displacement, rotation)

    private inline fun transformedByImpl(
        displacement: Vector2F, rotation: ComplexF, scaleFactor: Float
    ): MutableTriangle {
        val (dX: Float, dY: Float) = displacement
        val (cX: Float, cY: Float) = _centroid
        val (opAX: Float, opAY: Float) = _originPointA
        val (opBX: Float, opBY: Float) = _originPointB
        val (opCX: Float, opCY: Float) = _originPointC
        val (oR: Float, oI: Float) = _orientation
        val (rotR: Float, rotI: Float) = rotation
        val centroidX: Float = cX + dX
        val centroidY: Float = cY + dY
        val originPointAX: Float = (opAX * rotR - opAY * rotI) * scaleFactor
        val originPointAY: Float = (opAY * rotR + opAX * rotI) * scaleFactor
        val originPointBX: Float = (opBX * rotR - opBY * rotI) * scaleFactor
        val originPointBY: Float = (opBY * rotR + opBX * rotI) * scaleFactor
        val originPointCX: Float = (opCX * rotR - opCY * rotI) * scaleFactor
        val originPointCY: Float = (opCY * rotR + opCX * rotI) * scaleFactor
        val scaleFactorSign: Float = 1f.withSign(scaleFactor)

        return MutableTriangle(
            centroid = Vector2F(centroidX, centroidY),
            originPointA = Vector2F(originPointAX, originPointAY),
            originPointB = Vector2F(originPointBX, originPointBY),
            originPointC = Vector2F(originPointCX, originPointCY),
            orientation = ComplexF(
                (oR * rotR - oI * rotI) * scaleFactorSign,
                (oI * rotR + oR * rotI) * scaleFactorSign
            ),
            pointA = Vector2F(originPointAX + centroidX, originPointAY + centroidY),
            pointB = Vector2F(originPointBX + centroidX, originPointBY + centroidY),
            pointC = Vector2F(originPointCX + centroidX, originPointCY + centroidY)
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
        val (pX: Float, pY: Float) = position
        val (opAX: Float, opAY: Float) = _originPointA
        val (opBX: Float, opBY: Float) = _originPointB
        val (opCX: Float, opCY: Float) = _originPointC
        val (rotR: Float, rotI: Float) = _orientation.conjugate * orientation
        val originPointAX: Float = opAX * rotR - opAY * rotI
        val originPointAY: Float = opAY * rotR + opAX * rotI
        val originPointBX: Float = opBX * rotR - opBY * rotI
        val originPointBY: Float = opBY * rotR + opBX * rotI
        val originPointCX: Float = opCX * rotR - opCY * rotI
        val originPointCY: Float = opCY * rotR + opCX * rotI

        return MutableTriangle(
            position,
            originPointA = Vector2F(originPointAX, originPointAY),
            originPointB = Vector2F(originPointBX, originPointBY),
            originPointC = Vector2F(originPointCX, originPointCY),
            orientation,
            pointA = Vector2F(originPointAX + pX, originPointAY + pY),
            pointB = Vector2F(originPointBX + pX, originPointBY + pY),
            pointC = Vector2F(originPointCX + pX, originPointCY + pY)
        )
    }

    override fun transformedTo(position: Vector2F, orientation: AngleF): MutableTriangle =
        transformedToImpl(position, ComplexF.fromAngle(orientation))

    override fun transformedTo(position: Vector2F, orientation: ComplexF): MutableTriangle =
        transformedToImpl(position, orientation)

    private inline fun transformByImpl(displacement: Vector2F, rotation: ComplexF) {
        val (dX: Float, dY: Float) = displacement
        val (cX: Float, cY: Float) = _centroid
        val (opAX: Float, opAY: Float) = _originPointA
        val (opBX: Float, opBY: Float) = _originPointB
        val (opCX: Float, opCY: Float) = _originPointC
        val (oR: Float, oI: Float) = _orientation
        val (rotR: Float, rotI: Float) = rotation
        val centroidX: Float = cX + dX
        val centroidY: Float = cY + dY
        val originPointAX: Float = opAX * rotR - opAY * rotI
        val originPointAY: Float = opAY * rotR + opAX * rotI
        val originPointBX: Float = opBX * rotR - opBY * rotI
        val originPointBY: Float = opBY * rotR + opBX * rotI
        val originPointCX: Float = opCX * rotR - opCY * rotI
        val originPointCY: Float = opCY * rotR + opCX * rotI
        _centroid = Vector2F(centroidX, centroidY)
        _originPointA = Vector2F(originPointAX, originPointAY)
        _originPointB = Vector2F(originPointBX, originPointBY)
        _originPointC = Vector2F(originPointCX, originPointCY)
        _orientation = ComplexF(oR * rotR - oI * rotI, oI * rotR + oR * rotI)
        _pointA = Vector2F(originPointAX + centroidX, originPointAY + centroidY)
        _pointB = Vector2F(originPointBX + centroidX, originPointBY + centroidY)
        _pointC = Vector2F(originPointCX + centroidX, originPointCY + centroidY)
    }

    override fun transformBy(displacement: Vector2F, rotation: AngleF) =
        transformByImpl(displacement, ComplexF.fromAngle(rotation))

    override fun transformBy(displacement: Vector2F, rotation: ComplexF) =
        transformByImpl(displacement, rotation)

    private inline fun transformByImpl(
        displacement: Vector2F, rotation: ComplexF, scaleFactor: Float
    ) {
        val (dX: Float, dY: Float) = displacement
        val (cX: Float, cY: Float) = _centroid
        val (opAX: Float, opAY: Float) = _originPointA
        val (opBX: Float, opBY: Float) = _originPointB
        val (opCX: Float, opCY: Float) = _originPointC
        val (oR: Float, oI: Float) = _orientation
        val (rotR: Float, rotI: Float) = rotation
        val centroidX: Float = cX + dX
        val centroidY: Float = cY + dY
        val originPointAX: Float = (opAX * rotR - opAY * rotI) * scaleFactor
        val originPointAY: Float = (opAY * rotR + opAX * rotI) * scaleFactor
        val originPointBX: Float = (opBX * rotR - opBY * rotI) * scaleFactor
        val originPointBY: Float = (opBY * rotR + opBX * rotI) * scaleFactor
        val originPointCX: Float = (opCX * rotR - opCY * rotI) * scaleFactor
        val originPointCY: Float = (opCY * rotR + opCX * rotI) * scaleFactor
        val scaleFactorSign: Float = 1f.withSign(scaleFactor)
        _centroid = Vector2F(centroidX, centroidY)
        _originPointA = Vector2F(originPointAX, originPointAY)
        _originPointB = Vector2F(originPointBX, originPointBY)
        _originPointC = Vector2F(originPointCX, originPointCY)
        _orientation = ComplexF(
            (oR * rotR - oI * rotI) * scaleFactorSign,
            (oI * rotR + oR * rotI) * scaleFactorSign
        )
        _pointA = Vector2F(originPointAX + centroidX, originPointAY + centroidY)
        _pointB = Vector2F(originPointBX + centroidX, originPointBY + centroidY)
        _pointC = Vector2F(originPointCX + centroidX, originPointCY + centroidY)
    }

    override fun transformBy(displacement: Vector2F, rotation: AngleF, scaleFactor: Float) =
        transformByImpl(displacement, ComplexF.fromAngle(rotation), scaleFactor)

    override fun transformBy(displacement: Vector2F, rotation: ComplexF, scaleFactor: Float) =
        transformByImpl(displacement, rotation, scaleFactor)

    private inline fun transformToImpl(position: Vector2F, orientation: ComplexF) {
        val (pX: Float, pY: Float) = position
        val (opAX: Float, opAY: Float) = _originPointA
        val (opBX: Float, opBY: Float) = _originPointB
        val (opCX: Float, opCY: Float) = _originPointC
        val (rotR: Float, rotI: Float) = _orientation.conjugate * orientation
        val originPointAX: Float = opAX * rotR - opAY * rotI
        val originPointAY: Float = opAY * rotR + opAX * rotI
        val originPointBX: Float = opBX * rotR - opBY * rotI
        val originPointBY: Float = opBY * rotR + opBX * rotI
        val originPointCX: Float = opCX * rotR - opCY * rotI
        val originPointCY: Float = opCY * rotR + opCX * rotI
        _centroid = position
        _originPointA = Vector2F(originPointAX, originPointAY)
        _originPointB = Vector2F(originPointBX, originPointBY)
        _originPointC = Vector2F(originPointCX, originPointCY)
        _orientation = orientation
        _pointA = Vector2F(originPointAX + pX, originPointAY + pY)
        _pointB = Vector2F(originPointBX + pX, originPointBY + pY)
        _pointC = Vector2F(originPointCX + pX, originPointCY + pY)

    }

    override fun transformTo(position: Vector2F, orientation: AngleF) =
        transformToImpl(position, ComplexF.fromAngle(orientation))

    override fun transformTo(position: Vector2F, orientation: ComplexF) =
        transformToImpl(position, orientation)

    private inline fun setInternal(
        centroid: Vector2F, originPointA: Vector2F, originPointB: Vector2F, originPointC: Vector2F
    ) {
        val (opAX: Float, opAY: Float) = originPointA
        val (opBX: Float, opBY: Float) = originPointB
        val (opCX: Float, opCY: Float) = originPointC
        val (centroidX: Float, centroidY: Float) = centroid
        val orientation: ComplexF = originPointA
            .toComplexF()
            .normalizedOrElse(ComplexF(1f, 0f))
        val pointA = Vector2F(opAX + centroidX, opAY + centroidY)
        val pointB = Vector2F(opBX + centroidX, opBY + centroidY)
        val pointC = Vector2F(opCX + centroidX, opCY + centroidY)
        _centroid = centroid
        _originPointA = originPointA
        _originPointB = originPointB
        _originPointC = originPointC
        _orientation = orientation
        _pointA = pointA
        _pointB = pointB
        _pointC = pointC
    }

    /** Sets the specified properties of this instance. **/
    fun set(
        centroid: Vector2F = this.centroid,
        originPointA: Vector2F = this.originPointA,
        originPointB: Vector2F = this.originPointB,
        originPointC: Vector2F = this.originPointC
    ) = setInternal(centroid, originPointA, originPointB, originPointC)

    override fun interpolated(to: Triangle, by: Float): MutableTriangle {
        val fOpA: Vector2F = _originPointA
        val fOpB: Vector2F = _originPointB
        val fOpC: Vector2F = _originPointC
        val tOpA: Vector2F = to.originPointA
        val tOpB: Vector2F = to.originPointB
        val tOpC: Vector2F = to.originPointC
        val fromOrientation: ComplexF = _orientation
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
        val centroid = Vector2F.lerp(_centroid, to.centroid, by)

        return MutableTriangle(centroid, originPointA, originPointB, originPointC)
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
        val fOpA: Vector2F = from.originPointA
        val fOpB: Vector2F = from.originPointB
        val fOpC: Vector2F = from.originPointC
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
        val centroid = Vector2F.lerp(from.centroid, to.centroid, by)

        setInternal(centroid, originPointA, originPointB, originPointC)
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

    override fun originPointIterator(): Vector2FIterator =
        OriginPointIterator(this, index = 0)

    override fun pointIterator(): Vector2FIterator = PointIterator(this, index = 0)

    override fun copy(
        centroid: Vector2F, originPointA: Vector2F, originPointB: Vector2F, originPointC: Vector2F
    ) = MutableTriangle(centroid, originPointA, originPointB, originPointC)

    override fun equals(other: Any?): Boolean = other is Triangle &&
            _centroid == other.centroid &&
            _originPointA == other.originPointA &&
            _originPointB == other.originPointB &&
            _originPointC == other.originPointC

    /** Indicates whether the other [MutableTriangle] is equal to this one. **/
    fun equals(other: MutableTriangle): Boolean =
        _centroid == other._centroid &&
                _originPointA == other._originPointA &&
                _originPointB == other._originPointB &&
                _originPointC == other._originPointC

    override fun hashCode(): Int {
        val centroidHash: Int = _centroid.hashCode()
        val originPointAHash: Int = _originPointA.hashCode()
        val originPointBHash: Int = _originPointB.hashCode()
        val originPointCHash: Int = _originPointC.hashCode()

        return centroidHash * 29791 +
                originPointAHash * 961 +
                originPointBHash * 31 +
                originPointCHash
    }

    override fun toString() =
        StringBuilder("Triangle(centroid=").append(_centroid)
            .append(", originPointA=").append(_originPointA)
            .append(", originPointB=").append(_originPointB)
            .append(", originPointC=").append(_originPointC).append(")")
            .toString()

    override operator fun component1(): Vector2F = _centroid

    override operator fun component2(): Vector2F = _originPointA

    override operator fun component3(): Vector2F = _originPointB

    override operator fun component4(): Vector2F = _originPointC

    private class OriginPointIterator(
        private val triangle: MutableTriangle,
        private var index: Int
    ) : Vector2FIterator() {
        override fun hasNext(): Boolean = index < 3

        override fun nextVector2F(): Vector2F = when (index++) {
            0 -> triangle._originPointA
            1 -> triangle._originPointB
            2 -> triangle._originPointC
            else -> throw NoSuchElementException("${index - 1}")
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