@file:Suppress("NOTHING_TO_INLINE")

package com.sztorm.mathkit.euclidean2d

import com.sztorm.mathkit.AngleF
import com.sztorm.mathkit.ComplexF
import com.sztorm.mathkit.Vector2F
import com.sztorm.mathkit.Vector2FIterator
import kotlin.math.abs
import kotlin.math.absoluteValue
import kotlin.math.sqrt

/** Represents a mutable transformable triangle in a two-dimensional Euclidean space. **/
class MutableTriangle : Triangle, MutableTransformable {
    private var _centroid: Vector2F
    private var _orientation: ComplexF
    private var _originPointA: Vector2F
    private var _originPointB: Vector2F
    private var _originPointC: Vector2F
    private var _pointA: Vector2F
    private var _pointB: Vector2F
    private var _pointC: Vector2F

    /**
     * Creates a new instance of [MutableTriangle].
     *
     * @param orientation the value is expected to be [normalized][ComplexF.normalized].
     */
    constructor(
        centroid: Vector2F,
        orientation: ComplexF,
        originPointA: Vector2F,
        originPointB: Vector2F,
        originPointC: Vector2F
    ) {
        val (cX: Float, cY: Float) = centroid
        val (oR: Float, oI: Float) = orientation
        val (opAX: Float, opAY: Float) = originPointA
        val (opBX: Float, opBY: Float) = originPointB
        val (opCX: Float, opCY: Float) = originPointC
        _centroid = centroid
        _orientation = orientation
        _originPointA = originPointA
        _originPointB = originPointB
        _originPointC = originPointC
        _pointA = Vector2F((opAX * oR - opAY * oI) + cX, (opAY * oR + opAX * oI) + cY)
        _pointB = Vector2F((opBX * oR - opBY * oI) + cX, (opBY * oR + opBX * oI) + cY)
        _pointC = Vector2F((opCX * oR - opCY * oI) + cX, (opCY * oR + opCX * oI) + cY)
    }

    /** Creates a new instance of [MutableTriangle]. **/
    constructor(pointA: Vector2F, pointB: Vector2F, pointC: Vector2F) {
        val (pAX: Float, pAY: Float) = pointA
        val (pBX: Float, pBY: Float) = pointB
        val (pCX: Float, pCY: Float) = pointC
        val centroidX: Float = (pAX + pBX + pCX) * 0.3333333f
        val centroidY: Float = (pAY + pBY + pCY) * 0.3333333f
        _centroid = Vector2F(centroidX, centroidY)
        _orientation = ComplexF.ONE
        _originPointA = Vector2F(pAX - centroidX, pAY - centroidY)
        _originPointB = Vector2F(pBX - centroidX, pBY - centroidY)
        _originPointC = Vector2F(pCX - centroidX, pCY - centroidY)
        _pointA = pointA
        _pointB = pointB
        _pointC = pointC
    }

    private constructor(
        centroid: Vector2F,
        orientation: ComplexF,
        originPointA: Vector2F,
        originPointB: Vector2F,
        originPointC: Vector2F,
        pointA: Vector2F,
        pointB: Vector2F,
        pointC: Vector2F,
    ) {
        _centroid = centroid
        _orientation = orientation
        _originPointA = originPointA
        _originPointB = originPointB
        _originPointC = originPointC
        _pointA = pointA
        _pointB = pointB
        _pointC = pointC
    }

    override val centroid: Vector2F
        get() = _centroid

    override val orientation: ComplexF
        get() = _orientation

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

    override val area: Float
        get() {
            val (opAX: Float, opAY: Float) = _originPointA
            val (opBX: Float, opBY: Float) = _originPointB
            val (opCX: Float, opCY: Float) = _originPointC

            return 0.5f * abs((opAX - opCX) * (opBY - opCY) - (opBX - opCX) * (opAY - opCY))
        }

    override val perimeter: Float
        get() {
            val (opAX: Float, opAY: Float) = _originPointA
            val (opBX: Float, opBY: Float) = _originPointB
            val (opCX: Float, opCY: Float) = _originPointC
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
            val (opAX: Float, opAY: Float) = _originPointA
            val (opBX: Float, opBY: Float) = _originPointB
            val abX: Float = opBX - opAX
            val abY: Float = opBY - opAY

            return sqrt(abX * abX + abY * abY)
        }

    override val sideLengthBC: Float
        get() {
            val (opBX: Float, opBY: Float) = _originPointB
            val (opCX: Float, opCY: Float) = _originPointC
            val bcX: Float = opCX - opBX
            val bcY: Float = opCY - opBY

            return sqrt(bcX * bcX + bcY * bcY)
        }

    override val sideLengthAC: Float
        get() {
            val (opAX: Float, opAY: Float) = _originPointA
            val (opCX: Float, opCY: Float) = _originPointC
            val acX: Float = opCX - opAX
            val acY: Float = opCY - opAY

            return sqrt(acX * acX + acY * acY)
        }

    override val position: Vector2F
        get() = _centroid

    override val incenter: Vector2F
        get() {
            val (cX: Float, cY: Float) = _centroid
            val (oR: Float, oI: Float) = _orientation
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
            val icX: Float = (bcSide * opAX + acSide * opBX + abSide * opCX) * factor
            val icY: Float = (bcSide * opAY + acSide * opBY + abSide * opCY) * factor

            return Vector2F((icX * oR - icY * oI) + cX, (icY * oR + icX * oI) + cY)
        }

    override val circumcenter: Vector2F
        get() {
            val (cX: Float, cY: Float) = _centroid
            val (oR: Float, oI: Float) = _orientation
            val (opAX: Float, opAY: Float) = _originPointA
            val (opBX: Float, opBY: Float) = _originPointB
            val (opCX: Float, opCY: Float) = _originPointC
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

    override val orthocenter: Vector2F
        get() {
            val (cX: Float, cY: Float) = _centroid
            val (oR: Float, oI: Float) = _orientation
            val (opAX: Float, opAY: Float) = _originPointA
            val (opBX: Float, opBY: Float) = _originPointB
            val (opCX: Float, opCY: Float) = _originPointC
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

    override fun movedBy(displacement: Vector2F) = MutableTriangle(
        centroid = _centroid + displacement,
        _orientation,
        _originPointA,
        _originPointB,
        _originPointC
    )

    override fun movedTo(position: Vector2F) = MutableTriangle(
        centroid = position,
        _orientation,
        _originPointA,
        _originPointB,
        _originPointC
    )

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

    private inline fun rotatedByImpl(rotation: ComplexF) = MutableTriangle(
        _centroid,
        orientation = (_orientation * rotation).normalizedOrElse(ComplexF.ONE),
        _originPointA,
        _originPointB,
        _originPointC
    )

    override fun rotatedBy(rotation: AngleF): MutableTriangle =
        rotatedByImpl(ComplexF.fromAngle(rotation))

    override fun rotatedBy(rotation: ComplexF): MutableTriangle = rotatedByImpl(rotation)

    private inline fun rotatedToImpl(orientation: ComplexF) = MutableTriangle(
        _centroid,
        orientation = orientation.normalizedOrElse(ComplexF.ONE),
        _originPointA,
        _originPointB,
        _originPointC
    )

    override fun rotatedTo(orientation: AngleF): MutableTriangle =
        rotatedToImpl(ComplexF.fromAngle(orientation))

    override fun rotatedTo(orientation: ComplexF): MutableTriangle = rotatedToImpl(orientation)

    private inline fun rotatedAroundPointByImpl(
        point: Vector2F, rotation: ComplexF
    ): MutableTriangle {
        val (cX: Float, cY: Float) = _centroid
        val (oR: Float, oI: Float) = _orientation
        val (pX: Float, pY: Float) = point
        val (rotR: Float, rotI: Float) = rotation
        val cpDiffX: Float = cX - pX
        val cpDiffY: Float = cY - pY
        val centroidX: Float = cpDiffX * rotR - cpDiffY * rotI + pX
        val centroidY: Float = cpDiffY * rotR + cpDiffX * rotI + pY
        val orientationR: Float = oR * rotR - oI * rotI
        val orientationI: Float = oI * rotR + oR * rotI

        return MutableTriangle(
            centroid = Vector2F(centroidX, centroidY),
            orientation = ComplexF(orientationR, orientationI).normalizedOrElse(ComplexF.ONE),
            _originPointA,
            _originPointB,
            _originPointC
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
        val (startOR: Float, startOI: Float) = _orientation
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

            MutableTriangle(
                centroid = Vector2F(centroidX, centroidY),
                orientation = ComplexF(orientationR, orientationI).normalizedOrElse(ComplexF.ONE),
                _originPointA,
                _originPointB,
                _originPointC
            )
        } else MutableTriangle(
            centroid,
            orientation = orientation.normalizedOrElse(ComplexF.ONE),
            _originPointA,
            _originPointB,
            _originPointC
        )
    }

    override fun rotatedAroundPointTo(point: Vector2F, orientation: AngleF): MutableTriangle =
        rotatedAroundPointToImpl(point, ComplexF.fromAngle(orientation))

    override fun rotatedAroundPointTo(point: Vector2F, orientation: ComplexF): MutableTriangle =
        rotatedAroundPointToImpl(point, orientation)

    private inline fun rotateByImpl(rotation: ComplexF) {
        val (cX: Float, cY: Float) = _centroid
        val (oR: Float, oI: Float) = _orientation
        val (opAX: Float, opAY: Float) = _originPointA
        val (opBX: Float, opBY: Float) = _originPointB
        val (opCX: Float, opCY: Float) = _originPointC
        val (rotR: Float, rotI: Float) = rotation
        val orientationR: Float = oR * rotR - oI * rotI
        val orientationI: Float = oI * rotR + oR * rotI
        _orientation = ComplexF(orientationR, orientationI)
        _pointA = Vector2F(
            (opAX * orientationR - opAY * orientationI) + cX,
            (opAY * orientationR + opAX * orientationI) + cY
        )
        _pointB = Vector2F(
            (opBX * orientationR - opBY * orientationI) + cX,
            (opBY * orientationR + opBX * orientationI) + cY
        )
        _pointC = Vector2F(
            (opCX * orientationR - opCY * orientationI) + cX,
            (opCY * orientationR + opCX * orientationI) + cY
        )
    }

    override fun rotateBy(rotation: AngleF) = rotateByImpl(ComplexF.fromAngle(rotation))

    override fun rotateBy(rotation: ComplexF) = rotateByImpl(rotation)

    private inline fun rotateToImpl(orientation: ComplexF) {
        val (cX: Float, cY: Float) = _centroid
        val (opAX: Float, opAY: Float) = _originPointA
        val (opBX: Float, opBY: Float) = _originPointB
        val (opCX: Float, opCY: Float) = _originPointC
        val (oR: Float, oI: Float) = orientation
        _orientation = orientation
        _pointA = Vector2F((opAX * oR - opAY * oI) + cX, (opAY * oR + opAX * oI) + cY)
        _pointB = Vector2F((opBX * oR - opBY * oI) + cX, (opBY * oR + opBX * oI) + cY)
        _pointC = Vector2F((opCX * oR - opCY * oI) + cX, (opCY * oR + opCX * oI) + cY)
    }

    override fun rotateTo(orientation: AngleF) = rotateToImpl(ComplexF.fromAngle(orientation))

    override fun rotateTo(orientation: ComplexF) = rotateToImpl(orientation)

    private inline fun rotateAroundPointByImpl(point: Vector2F, rotation: ComplexF) {
        val (cX: Float, cY: Float) = _centroid
        val (oR: Float, oI: Float) = _orientation
        val (opAX: Float, opAY: Float) = _originPointA
        val (opBX: Float, opBY: Float) = _originPointB
        val (opCX: Float, opCY: Float) = _originPointC
        val (pX: Float, pY: Float) = point
        val (rotR: Float, rotI: Float) = rotation
        val cpDiffX: Float = cX - pX
        val cpDiffY: Float = cY - pY
        val centroidX: Float = cpDiffX * rotR - cpDiffY * rotI + pX
        val centroidY: Float = cpDiffY * rotR + cpDiffX * rotI + pY
        val orientationR: Float = oR * rotR - oI * rotI
        val orientationI: Float = oI * rotR + oR * rotI
        _centroid = Vector2F(centroidX, centroidY)
        _orientation = ComplexF(orientationR, orientationI)
        _pointA = Vector2F(
            (opAX * orientationR - opAY * orientationI) + centroidX,
            (opAY * orientationR + opAX * orientationI) + centroidY
        )
        _pointB = Vector2F(
            (opBX * orientationR - opBY * orientationI) + centroidX,
            (opBY * orientationR + opBX * orientationI) + centroidY
        )
        _pointC = Vector2F(
            (opCX * orientationR - opCY * orientationI) + centroidX,
            (opCY * orientationR + opCX * orientationI) + centroidY
        )
    }

    override fun rotateAroundPointBy(point: Vector2F, rotation: AngleF) =
        rotateAroundPointByImpl(point, ComplexF.fromAngle(rotation))

    override fun rotateAroundPointBy(point: Vector2F, rotation: ComplexF) =
        rotateAroundPointByImpl(point, rotation)

    private inline fun rotateAroundPointToImpl(point: Vector2F, orientation: ComplexF) {
        val centroid: Vector2F = _centroid
        val (startOR: Float, startOI: Float) = _orientation
        val (opAX: Float, opAY: Float) = _originPointA
        val (opBX: Float, opBY: Float) = _originPointB
        val (opCX: Float, opCY: Float) = _originPointC
        val (cX: Float, cY: Float) = centroid
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
            val centroidX: Float = oR * centroidToPointDist + pX
            val centroidY: Float = oI * centroidToPointDist + pY
            val orientationR: Float = pRotTimesStartOR * oR - pRotTimesStartOI * oI
            val orientationI: Float = pRotTimesStartOI * oR + pRotTimesStartOR * oI
            _centroid = Vector2F(centroidX, centroidY)
            _orientation = ComplexF(orientationR, orientationI)
            _pointA = Vector2F(
                (opAX * orientationR - opAY * orientationI) + centroidX,
                (opAY * orientationR + opAX * orientationI) + centroidY
            )
            _pointB = Vector2F(
                (opBX * orientationR - opBY * orientationI) + centroidX,
                (opBY * orientationR + opBX * orientationI) + centroidY
            )
            _pointC = Vector2F(
                (opCX * orientationR - opCY * orientationI) + centroidX,
                (opCY * orientationR + opCX * orientationI) + centroidY
            )
        } else {
            _orientation = orientation
            _pointA = Vector2F((opAX * oR - opAY * oI) + cX, (opAY * oR + opAX * oI) + cY)
            _pointB = Vector2F((opBX * oR - opBY * oI) + cX, (opBY * oR + opBX * oI) + cY)
            _pointC = Vector2F((opCX * oR - opCY * oI) + cX, (opCY * oR + opCX * oI) + cY)
        }
    }

    override fun rotateAroundPointTo(point: Vector2F, orientation: AngleF) =
        rotateAroundPointToImpl(point, ComplexF.fromAngle(orientation))

    override fun rotateAroundPointTo(point: Vector2F, orientation: ComplexF) =
        rotateAroundPointToImpl(point, orientation)

    override fun scaledBy(factor: Float): MutableTriangle {
        val centroid: Vector2F = _centroid
        val orientation: ComplexF = _orientation
        val (opAX: Float, opAY: Float) = _originPointA
        val (opBX: Float, opBY: Float) = _originPointB
        val (opCX: Float, opCY: Float) = _originPointC
        val (cX: Float, cY: Float) = centroid
        val (oR: Float, oI: Float) = orientation
        val originPointAX: Float = opAX * factor
        val originPointAY: Float = opAY * factor
        val originPointBX: Float = opBX * factor
        val originPointBY: Float = opBY * factor
        val originPointCX: Float = opCX * factor
        val originPointCY: Float = opCY * factor

        return MutableTriangle(
            centroid,
            orientation,
            originPointA = Vector2F(originPointAX, originPointAY),
            originPointB = Vector2F(originPointBX, originPointBY),
            originPointC = Vector2F(originPointCX, originPointCY),
            pointA = Vector2F(
                originPointAX * oR - originPointAY * oI + cX,
                originPointAY * oR + originPointAX * oI + cY
            ),
            pointB = Vector2F(
                originPointBX * oR - originPointBY * oI + cX,
                originPointBY * oR + originPointBX * oI + cY
            ),
            pointC = Vector2F(
                originPointCX * oR - originPointCY * oI + cX,
                originPointCY * oR + originPointCX * oI + cY
            )
        )
    }

    override fun dilatedBy(point: Vector2F, factor: Float): MutableTriangle {
        val (cX: Float, cY: Float) = _centroid
        val orientation: ComplexF = _orientation
        val (opAX: Float, opAY: Float) = _originPointA
        val (opBX: Float, opBY: Float) = _originPointB
        val (opCX: Float, opCY: Float) = _originPointC
        val (oR: Float, oI: Float) = orientation
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

        return MutableTriangle(
            centroid = Vector2F(centroidX, centroidY),
            orientation,
            originPointA = Vector2F(originPointAX, originPointAY),
            originPointB = Vector2F(originPointBX, originPointBY),
            originPointC = Vector2F(originPointCX, originPointCY),
            pointA = Vector2F(
                originPointAX * oR - originPointAY * oI + centroidX,
                originPointAY * oR + originPointAX * oI + centroidY
            ),
            pointB = Vector2F(
                originPointBX * oR - originPointBY * oI + centroidX,
                originPointBY * oR + originPointBX * oI + centroidY
            ),
            pointC = Vector2F(
                originPointCX * oR - originPointCY * oI + centroidX,
                originPointCY * oR + originPointCX * oI + centroidY
            )
        )
    }

    override fun scaleBy(factor: Float) {
        val (cX: Float, cY: Float) = _centroid
        val (oR: Float, oI: Float) = _orientation
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
        _pointA = Vector2F(
            (originPointAX * oR - originPointAY * oI) + cX,
            (originPointAY * oR + originPointAX * oI) + cY
        )
        _pointB = Vector2F(
            (originPointBX * oR - originPointBY * oI) + cX,
            (originPointBY * oR + originPointBX * oI) + cY
        )
        _pointC = Vector2F(
            (originPointCX * oR - originPointCY * oI) + cX,
            (originPointCY * oR + originPointCX * oI) + cY
        )
    }

    override fun dilateBy(point: Vector2F, factor: Float) {
        val (cX: Float, cY: Float) = _centroid
        val (oR: Float, oI: Float) = _orientation
        val (opAX: Float, opAY: Float) = _originPointA
        val (opBX: Float, opBY: Float) = _originPointB
        val (opCX: Float, opCY: Float) = _originPointC
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
        _centroid = Vector2F(centroidX, centroidY)
        _originPointA = Vector2F(originPointAX, originPointAY)
        _originPointB = Vector2F(originPointBX, originPointBY)
        _originPointC = Vector2F(originPointCX, originPointCY)
        _pointA = Vector2F(
            originPointAX * oR - originPointAY * oI + centroidX,
            originPointAY * oR + originPointAX * oI + centroidY
        )
        _pointB = Vector2F(
            originPointBX * oR - originPointBY * oI + centroidX,
            originPointBY * oR + originPointBX * oI + centroidY
        )
        _pointC = Vector2F(
            originPointCX * oR - originPointCY * oI + centroidX,
            originPointCY * oR + originPointCX * oI + centroidY
        )
    }

    private inline fun transformedByImpl(
        displacement: Vector2F, rotation: ComplexF
    ) = MutableTriangle(
        centroid = _centroid + displacement,
        orientation = (_orientation * rotation).normalizedOrElse(ComplexF.ONE),
        _originPointA,
        _originPointB,
        _originPointC
    )

    override fun transformedBy(displacement: Vector2F, rotation: AngleF): MutableTriangle =
        transformedByImpl(displacement, ComplexF.fromAngle(rotation))

    override fun transformedBy(displacement: Vector2F, rotation: ComplexF): MutableTriangle =
        transformedByImpl(displacement, rotation)

    private inline fun transformedByImpl(
        displacement: Vector2F, rotation: ComplexF, scaleFactor: Float
    ) = MutableTriangle(
        centroid = _centroid + displacement,
        orientation = (_orientation * rotation).normalizedOrElse(ComplexF.ONE),
        originPointA = _originPointA * scaleFactor,
        originPointB = _originPointB * scaleFactor,
        originPointC = _originPointC * scaleFactor
    )

    override fun transformedBy(
        displacement: Vector2F, rotation: AngleF, scaleFactor: Float
    ): MutableTriangle = transformedByImpl(displacement, ComplexF.fromAngle(rotation), scaleFactor)

    override fun transformedBy(
        displacement: Vector2F, rotation: ComplexF, scaleFactor: Float
    ): MutableTriangle = transformedByImpl(displacement, rotation, scaleFactor)

    private inline fun transformedToImpl(
        position: Vector2F, orientation: ComplexF
    ) = MutableTriangle(
        centroid = position,
        orientation = orientation.normalizedOrElse(ComplexF.ONE),
        _originPointA,
        _originPointB,
        _originPointC
    )

    override fun transformedTo(position: Vector2F, orientation: AngleF): MutableTriangle =
        transformedToImpl(position, ComplexF.fromAngle(orientation))

    override fun transformedTo(position: Vector2F, orientation: ComplexF): MutableTriangle =
        transformedToImpl(position, orientation)

    private inline fun transformByImpl(displacement: Vector2F, rotation: ComplexF) {
        val (cX: Float, cY: Float) = _centroid
        val (oR: Float, oI: Float) = _orientation
        val (opAX: Float, opAY: Float) = _originPointA
        val (opBX: Float, opBY: Float) = _originPointB
        val (opCX: Float, opCY: Float) = _originPointC
        val (dX: Float, dY: Float) = displacement
        val (rotR: Float, rotI: Float) = rotation
        val centroidX: Float = cX + dX
        val centroidY: Float = cY + dY
        val orientationR: Float = oR * rotR - oI * rotI
        val orientationI: Float = oI * rotR + oR * rotI
        _centroid = Vector2F(centroidX, centroidY)
        _orientation = ComplexF(orientationR, orientationI)
        _pointA = Vector2F(
            (opAX * orientationR - opAY * orientationI) + centroidX,
            (opAY * orientationR + opAX * orientationI) + centroidY
        )
        _pointB = Vector2F(
            (opBX * orientationR - opBY * orientationI) + centroidX,
            (opBY * orientationR + opBX * orientationI) + centroidY
        )
        _pointC = Vector2F(
            (opCX * orientationR - opCY * orientationI) + centroidX,
            (opCY * orientationR + opCX * orientationI) + centroidY
        )
    }

    override fun transformBy(displacement: Vector2F, rotation: AngleF) =
        transformByImpl(displacement, ComplexF.fromAngle(rotation))

    override fun transformBy(displacement: Vector2F, rotation: ComplexF) =
        transformByImpl(displacement, rotation)

    private inline fun transformByImpl(
        displacement: Vector2F, rotation: ComplexF, scaleFactor: Float
    ) {
        val (cX: Float, cY: Float) = _centroid
        val (oR: Float, oI: Float) = _orientation
        val (opAX: Float, opAY: Float) = _originPointA
        val (opBX: Float, opBY: Float) = _originPointB
        val (opCX: Float, opCY: Float) = _originPointC
        val originPointAX: Float = opAX * scaleFactor
        val originPointAY: Float = opAY * scaleFactor
        val originPointBX: Float = opBX * scaleFactor
        val originPointBY: Float = opBY * scaleFactor
        val originPointCX: Float = opCX * scaleFactor
        val originPointCY: Float = opCY * scaleFactor
        val (dX: Float, dY: Float) = displacement
        val (rotR: Float, rotI: Float) = rotation
        val centroidX: Float = cX + dX
        val centroidY: Float = cY + dY
        val orientationR: Float = oR * rotR - oI * rotI
        val orientationI: Float = oI * rotR + oR * rotI
        _centroid = Vector2F(centroidX, centroidY)
        _orientation = ComplexF(orientationR, orientationI)
        _originPointA = Vector2F(originPointAX, originPointAY)
        _originPointB = Vector2F(originPointBX, originPointBY)
        _originPointC = Vector2F(originPointCX, originPointCY)
        _pointA = Vector2F(
            originPointAX * orientationR - originPointAY * orientationI + centroidX,
            originPointAY * orientationR + originPointAX * orientationI + centroidY
        )
        _pointB = Vector2F(
            originPointBX * orientationR - originPointBY * orientationI + centroidX,
            originPointBY * orientationR + originPointBX * orientationI + centroidY
        )
        _pointC = Vector2F(
            originPointCX * orientationR - originPointCY * orientationI + centroidX,
            originPointCY * orientationR + originPointCX * orientationI + centroidY
        )
    }

    override fun transformBy(displacement: Vector2F, rotation: AngleF, scaleFactor: Float) =
        transformByImpl(displacement, ComplexF.fromAngle(rotation), scaleFactor)

    override fun transformBy(displacement: Vector2F, rotation: ComplexF, scaleFactor: Float) =
        transformByImpl(displacement, rotation, scaleFactor)

    private inline fun transformToImpl(position: Vector2F, orientation: ComplexF) {
        val (opAX: Float, opAY: Float) = _originPointA
        val (opBX: Float, opBY: Float) = _originPointB
        val (opCX: Float, opCY: Float) = _originPointC
        val (cX: Float, cY: Float) = position
        val (oR: Float, oI: Float) = orientation
        _centroid = position
        _orientation = orientation
        _pointA = Vector2F((opAX * oR - opAY * oI) + cX, (opAY * oR + opAX * oI) + cY)
        _pointB = Vector2F((opBX * oR - opBY * oI) + cX, (opBY * oR + opBX * oI) + cY)
        _pointC = Vector2F((opCX * oR - opCY * oI) + cX, (opCY * oR + opCX * oI) + cY)
    }

    override fun transformTo(position: Vector2F, orientation: AngleF) =
        transformToImpl(position, ComplexF.fromAngle(orientation))

    override fun transformTo(position: Vector2F, orientation: ComplexF) =
        transformToImpl(position, orientation)

    /**
     * Calibrates the properties of this instance. If the [orientation] cannot be normalized, it
     * will take the value of [ONE][ComplexF.ONE].
     *
     * Transformations and operations involving floating point numbers may introduce various
     * inaccuracies that can be countered by this method.
     */
    fun calibrate() {
        val orientation: ComplexF = _orientation.normalizedOrElse(ComplexF.ONE)
        val (cX: Float, cY: Float) = _centroid
        val (oR: Float, oI: Float) = orientation
        val (opAX: Float, opAY: Float) = _originPointA
        val (opBX: Float, opBY: Float) = _originPointB
        val (opCX: Float, opCY: Float) = _originPointC
        _orientation = orientation
        _pointA = Vector2F(
            (opAX * oR - opAY * oI) + cX, (opAY * oR + opAX * oI) + cY
        )
        _pointB = Vector2F(
            (opBX * oR - opBY * oI) + cX, (opBY * oR + opBX * oI) + cY
        )
        _pointC = Vector2F(
            (opCX * oR - opCY * oI) + cX, (opCY * oR + opCX * oI) + cY
        )
    }

    private inline fun setInternal(
        centroid: Vector2F,
        orientation: ComplexF,
        originPointA: Vector2F,
        originPointB: Vector2F,
        originPointC: Vector2F
    ) {
        val (cX: Float, cY: Float) = centroid
        val (oR: Float, oI: Float) = orientation
        val (opAX: Float, opAY: Float) = originPointA
        val (opBX: Float, opBY: Float) = originPointB
        val (opCX: Float, opCY: Float) = originPointC
        _centroid = centroid
        _orientation = orientation
        _originPointA = originPointA
        _originPointB = originPointB
        _originPointC = originPointC
        _pointA = Vector2F(
            (opAX * oR - opAY * oI) + cX, (opAY * oR + opAX * oI) + cY
        )
        _pointB = Vector2F(
            (opBX * oR - opBY * oI) + cX, (opBY * oR + opBX * oI) + cY
        )
        _pointC = Vector2F(
            (opCX * oR - opCY * oI) + cX, (opCY * oR + opCX * oI) + cY
        )
    }

    /**
     * Sets the specified properties of this instance.
     *
     * @param orientation the value is expected to be [normalized][ComplexF.normalized].
     */
    fun set(
        centroid: Vector2F = this.centroid,
        orientation: ComplexF = this.orientation,
        originPointA: Vector2F = this.originPointA,
        originPointB: Vector2F = this.originPointB,
        originPointC: Vector2F = this.originPointC
    ) = setInternal(
        centroid,
        orientation,
        originPointA,
        originPointB,
        originPointC
    )

    override fun interpolated(to: Triangle, by: Float): MutableTriangle {
        val fOpA: Vector2F = _originPointA
        val fOpB: Vector2F = _originPointB
        val fOpC: Vector2F = _originPointC
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
        val centroid = Vector2F.lerp(_centroid, to.centroid, by)
        val orientation = ComplexF
            .slerp(fOpAOrientation * _orientation, tOpAOrientation * to.orientation, by)
            .normalizedOrElse(ComplexF.ONE)

        return MutableTriangle(centroid, orientation, originPointA, originPointB, originPointC)
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
        val centroid = Vector2F.lerp(from.centroid, to.centroid, by)
        val orientation = ComplexF
            .slerp(fOpAOrientation * from.orientation, tOpAOrientation * to.orientation, by)
        val (cX: Float, cY: Float) = centroid
        val (oR: Float, oI: Float) = orientation
        val (opAX: Float, opAY: Float) = originPointA
        val (opBX: Float, opBY: Float) = originPointB
        val (opCX: Float, opCY: Float) = originPointC
        _centroid = centroid
        _orientation = orientation
        _originPointA = originPointA
        _originPointB = originPointB
        _originPointC = originPointC
        _pointA = Vector2F((opAX * oR - opAY * oI) + cX, (opAY * oR + opAX * oI) + cY)
        _pointB = Vector2F((opBX * oR - opBY * oI) + cX, (opBY * oR + opBX * oI) + cY)
        _pointC = Vector2F((opCX * oR - opCY * oI) + cX, (opCY * oR + opCX * oI) + cY)
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

    /**
     * Returns a copy of this instance with specified properties changed.
     *
     * @param orientation the value is expected to be [normalized][ComplexF.normalized].
     */
    override fun copy(
        centroid: Vector2F,
        orientation: ComplexF,
        originPointA: Vector2F,
        originPointB: Vector2F,
        originPointC: Vector2F
    ) = MutableTriangle(
        centroid,
        orientation,
        originPointA,
        originPointB,
        originPointC
    )

    override fun equals(other: Any?): Boolean = other is Triangle &&
        _centroid == other.centroid &&
        _orientation == other.orientation &&
        _originPointA == other.originPointA &&
        _originPointB == other.originPointB &&
        _originPointC == other.originPointC

    /** Indicates whether the other [MutableTriangle] is equal to this one. **/
    fun equals(other: MutableTriangle): Boolean =
        _centroid == other._centroid &&
            _orientation == other._orientation &&
            _originPointA == other._originPointA &&
            _originPointB == other._originPointB &&
            _originPointC == other._originPointC

    override fun hashCode(): Int {
        var result: Int = _centroid.hashCode()
        result = 31 * result + _orientation.hashCode()
        result = 31 * result + _originPointA.hashCode()
        result = 31 * result + _originPointB.hashCode()
        result = 31 * result + _originPointC.hashCode()

        return result
    }

    override fun toString() =
        StringBuilder("Triangle(centroid=").append(_centroid)
            .append(", orientation=").append(_orientation)
            .append(", originPointA=").append(_originPointA)
            .append(", originPointB=").append(_originPointB)
            .append(", originPointC=").append(_originPointC).append(")")
            .toString()

    override operator fun component1(): Vector2F = _centroid

    override operator fun component2(): ComplexF = _orientation

    override operator fun component3(): Vector2F = _originPointA

    override operator fun component4(): Vector2F = _originPointB

    override operator fun component5(): Vector2F = _originPointC

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