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
    private var _pointA: Vector2F
    private var _pointB: Vector2F
    private var _pointC: Vector2F
    private var _centroid: Vector2F
    private var _orientation: ComplexF

    /** Creates a new instance of [MutableTriangle]. **/
    constructor(pointA: Vector2F, pointB: Vector2F, pointC: Vector2F) {
        _pointA = pointA
        _pointB = pointB
        _pointC = pointC
        _centroid = (pointA + pointB + pointC) * 0.3333333f
        _orientation = (pointA - _centroid).normalized.toComplexF()
    }

    private constructor(
        pointA: Vector2F,
        pointB: Vector2F,
        pointC: Vector2F,
        centroid: Vector2F,
        orientation: ComplexF
    ) {
        _pointA = pointA
        _pointB = pointB
        _pointC = pointC
        _centroid = centroid
        _orientation = orientation
    }

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
        get() = _pointA.distanceTo(_pointB)

    override val sideLengthBC: Float
        get() = _pointB.distanceTo(_pointC)

    override val sideLengthCA: Float
        get() = _pointC.distanceTo(_pointA)

    override val position: Vector2F
        get() = _centroid

    override val incenter: Vector2F
        get() {
            val pointA: Vector2F = _pointA
            val pointB: Vector2F = _pointB
            val pointC: Vector2F = _pointC
            val abSide: Float = pointA.distanceTo(pointB)
            val bcSide: Float = pointB.distanceTo(pointC)
            val acSide: Float = pointA.distanceTo(pointC)
            val factor: Float = 1f / (abSide + bcSide + acSide)

            return Vector2F(
                (bcSide * pointA.x + acSide * pointB.x + abSide * pointC.x) * factor,
                (bcSide * pointA.y + acSide * pointB.y + abSide * pointC.y) * factor
            )
        }

    override val centroid: Vector2F
        get() = _centroid

    override val circumcenter: Vector2F
        get() {
            val (aX: Float, aY: Float) = _pointA
            val (bX: Float, bY: Float) = _pointB
            val (cX: Float, cY: Float) = _pointC
            val pASquaredMagnitude: Float = aX * aX + aY * aY
            val pBSquaredMagnitude: Float = bX * bX + bY * bY
            val pCSquaredMagnitude: Float = cX * cX + cY * cY
            val aDet: Float = aX * bY + aY * cX + bX * cY - bY * cX - aY * bX - aX * cY
            val factor: Float = 0.5f / aDet
            val xDet: Float = pASquaredMagnitude * bY + aY * pCSquaredMagnitude +
                    pBSquaredMagnitude * cY - bY * pCSquaredMagnitude -
                    aY * pBSquaredMagnitude - pASquaredMagnitude * cY
            val yDet: Float = aX * pBSquaredMagnitude + pASquaredMagnitude * cX +
                    bX * pCSquaredMagnitude - pBSquaredMagnitude * cX -
                    pASquaredMagnitude * bX - aX * pCSquaredMagnitude

            return Vector2F(xDet * factor, yDet * factor)
        }

    override val orthocenter: Vector2F
        get() {
            val (aX: Float, aY: Float) = _pointA
            val (bX: Float, bY: Float) = _pointB
            val (cX: Float, cY: Float) = _pointC
            val (centroidX: Float, centroidY: Float) = _centroid
            val pASquaredMagnitude: Float = aX * aX + aY * aY
            val pBSquaredMagnitude: Float = bX * bX + bY * bY
            val pCSquaredMagnitude: Float = cX * cX + cY * cY
            val aDet: Float = aX * bY + aY * cX + bX * cY - bY * cX - aY * bX - aX * cY
            val factor: Float = 0.5f / aDet
            val xDet: Float = pASquaredMagnitude * bY + aY * pCSquaredMagnitude +
                    pBSquaredMagnitude * cY - bY * pCSquaredMagnitude -
                    aY * pBSquaredMagnitude - pASquaredMagnitude * cY
            val yDet: Float = aX * pBSquaredMagnitude + pASquaredMagnitude * cX +
                    bX * pCSquaredMagnitude - pBSquaredMagnitude * cX -
                    pASquaredMagnitude * bX - aX * pCSquaredMagnitude
            val circumcenterX: Float = xDet * factor
            val circumcenterY: Float = yDet * factor

            return Vector2F(
                circumcenterX + (centroidX - circumcenterX) * 3f,
                circumcenterY + (centroidY - circumcenterY) * 3f
            )
        }

    override fun movedBy(displacement: Vector2F) = MutableTriangle(
        _pointA + displacement,
        _pointB + displacement,
        _pointC + displacement,
        _centroid + displacement,
        _orientation
    )

    override fun movedTo(position: Vector2F): MutableTriangle {
        val displacement: Vector2F = position - _centroid

        return MutableTriangle(
            _pointA + displacement,
            _pointB + displacement,
            _pointC + displacement,
            position,
            _orientation
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

    override fun rotatedBy(rotation: AngleF): MutableTriangle =
        rotatedBy(ComplexF.fromAngle(rotation))

    override fun rotatedBy(rotation: ComplexF): MutableTriangle {
        val (aX: Float, aY: Float) = _pointA
        val (bX: Float, bY: Float) = _pointB
        val (cX: Float, cY: Float) = _pointC
        val (centroidX: Float, centroidY: Float) = _centroid
        val (startRotR: Float, startRotI: Float) = _orientation
        val (rotR: Float, rotI: Float) = rotation
        val caX: Float = aX - centroidX
        val caY: Float = aY - centroidY
        val cbX: Float = bX - centroidX
        val cbY: Float = bY - centroidY
        val ccX: Float = cX - centroidX
        val ccY: Float = cY - centroidY

        return MutableTriangle(
            Vector2F(
                caX * rotR - caY * rotI + centroidX, caY * rotR + caX * rotI + centroidY
            ),
            Vector2F(
                cbX * rotR - cbY * rotI + centroidX, cbY * rotR + cbX * rotI + centroidY
            ),
            Vector2F(
                ccX * rotR - ccY * rotI + centroidX, ccY * rotR + ccX * rotI + centroidY
            ),
            _centroid,
            ComplexF(
                startRotR * rotR - startRotI * rotI,
                startRotI * rotR + startRotR * rotI
            )
        )
    }

    override fun rotatedTo(orientation: AngleF): MutableTriangle =
        rotatedTo(ComplexF.fromAngle(orientation))

    override fun rotatedTo(orientation: ComplexF): MutableTriangle {
        val (aX: Float, aY: Float) = _pointA
        val (bX: Float, bY: Float) = _pointB
        val (cX: Float, cY: Float) = _pointC
        val (centroidX: Float, centroidY: Float) = _centroid
        val (rotR: Float, rotI: Float) = _orientation.conjugate * orientation
        val caX: Float = aX - centroidX
        val caY: Float = aY - centroidY
        val cbX: Float = bX - centroidX
        val cbY: Float = bY - centroidY
        val ccX: Float = cX - centroidX
        val ccY: Float = cY - centroidY

        return MutableTriangle(
            Vector2F(
                caX * rotR - caY * rotI + centroidX, caY * rotR + caX * rotI + centroidY
            ),
            Vector2F(
                cbX * rotR - cbY * rotI + centroidX, cbY * rotR + cbX * rotI + centroidY
            ),
            Vector2F(
                ccX * rotR - ccY * rotI + centroidX, ccY * rotR + ccX * rotI + centroidY
            ),
            _centroid,
            orientation
        )
    }

    override fun rotatedAroundPointBy(point: Vector2F, rotation: AngleF): MutableTriangle =
        rotatedAroundPointBy(point, ComplexF.fromAngle(rotation))

    override fun rotatedAroundPointBy(point: Vector2F, rotation: ComplexF): MutableTriangle {
        val (aX: Float, aY: Float) = _pointA
        val (bX: Float, bY: Float) = _pointB
        val (cX: Float, cY: Float) = _pointC
        val (centroidX: Float, centroidY: Float) = _centroid
        val (startRotR: Float, startRotI: Float) = _orientation
        val (pX: Float, pY: Float) = point
        val (rotR: Float, rotI: Float) = rotation
        val cpDiffX: Float = centroidX - pX
        val cpDiffY: Float = centroidY - pY
        val targetCenterX: Float = cpDiffX * rotR - cpDiffY * rotI + pX
        val targetCenterY: Float = cpDiffY * rotR + cpDiffX * rotI + pY
        val caX: Float = aX - centroidX
        val caY: Float = aY - centroidY
        val cbX: Float = bX - centroidX
        val cbY: Float = bY - centroidY
        val ccX: Float = cX - centroidX
        val ccY: Float = cY - centroidY

        return MutableTriangle(
            pointA = Vector2F(
                caX * rotR - caY * rotI + targetCenterX,
                caY * rotR + caX * rotI + targetCenterY
            ),
            pointB = Vector2F(
                cbX * rotR - cbY * rotI + targetCenterX,
                cbY * rotR + cbX * rotI + targetCenterY
            ),
            pointC = Vector2F(
                ccX * rotR - ccY * rotI + targetCenterX,
                ccY * rotR + ccX * rotI + targetCenterY
            ),
            centroid = Vector2F(targetCenterX, targetCenterY),
            orientation = ComplexF(
                startRotR * rotR - startRotI * rotI,
                startRotI * rotR + startRotR * rotI
            )
        )
    }

    override fun rotatedAroundPointTo(point: Vector2F, orientation: AngleF): MutableTriangle =
        rotatedAroundPointTo(point, ComplexF.fromAngle(orientation))

    override fun rotatedAroundPointTo(point: Vector2F, orientation: ComplexF): MutableTriangle {
        val (aX: Float, aY: Float) = _pointA
        val (bX: Float, bY: Float) = _pointB
        val (cX: Float, cY: Float) = _pointC
        val (centroidX: Float, centroidY: Float) = _centroid
        val (startRotR: Float, startRotI: Float) = _orientation
        val caX: Float = aX - centroidX
        val caY: Float = aY - centroidY
        val cbX: Float = bX - centroidX
        val cbY: Float = bY - centroidY
        val ccX: Float = cX - centroidX
        val ccY: Float = cY - centroidY
        val (pX: Float, pY: Float) = point
        val (rotR: Float, rotI: Float) = orientation
        val cpDiffX: Float = centroidX - pX
        val cpDiffY: Float = centroidY - pY
        val centerToPointDist: Float = sqrt(cpDiffX * cpDiffX + cpDiffY * cpDiffY)

        if (centerToPointDist > 0.00001f) {
            val pointRotR: Float = cpDiffX / centerToPointDist
            val pointRotI: Float = cpDiffY / centerToPointDist
            val pRotR: Float = pointRotR * rotR + pointRotI * rotI
            val pRotI: Float = pointRotR * rotI - pointRotI * rotR
            val targetCenterX: Float = rotR * centerToPointDist + pX
            val targetCenterY: Float = rotI * centerToPointDist + pY

            return MutableTriangle(
                pointA = Vector2F(
                    caX * pRotR - caY * pRotI + targetCenterX,
                    caY * pRotR + caX * pRotI + targetCenterY
                ),
                pointB = Vector2F(
                    cbX * pRotR - cbY * pRotI + targetCenterX,
                    cbY * pRotR + cbX * pRotI + targetCenterY
                ),
                pointC = Vector2F(
                    ccX * pRotR - ccY * pRotI + targetCenterX,
                    ccY * pRotR + ccX * pRotI + targetCenterY
                ),
                centroid = Vector2F(targetCenterX, targetCenterY),
                orientation = ComplexF(
                    pRotR * startRotR - pRotI * startRotI,
                    pRotI * startRotR + pRotR * startRotI
                )
            )
        } else {
            val pRotR: Float = rotR * startRotR + rotI * startRotI
            val pRotI: Float = rotI * startRotR - rotR * startRotI

            return MutableTriangle(
                pointA = Vector2F(
                    caX * pRotR - caY * pRotI + centroidX,
                    caY * pRotR + caX * pRotI + centroidY
                ),
                pointB = Vector2F(
                    cbX * pRotR - cbY * pRotI + centroidX,
                    cbY * pRotR + cbX * pRotI + centroidY
                ),
                pointC = Vector2F(
                    ccX * pRotR - ccY * pRotI + centroidX,
                    ccY * pRotR + ccX * pRotI + centroidY
                ),
                _centroid,
                orientation
            )
        }
    }

    override fun rotateBy(rotation: AngleF) = rotateBy(ComplexF.fromAngle(rotation))

    override fun rotateBy(rotation: ComplexF) {
        val (aX: Float, aY: Float) = _pointA
        val (bX: Float, bY: Float) = _pointB
        val (cX: Float, cY: Float) = _pointC
        val (centroidX: Float, centroidY: Float) = _centroid
        val (startRotR: Float, startRotI: Float) = _orientation
        val (rotR: Float, rotI: Float) = rotation
        val caX: Float = aX - centroidX
        val caY: Float = aY - centroidY
        val cbX: Float = bX - centroidX
        val cbY: Float = bY - centroidY
        val ccX: Float = cX - centroidX
        val ccY: Float = cY - centroidY
        _pointA = Vector2F(
            caX * rotR - caY * rotI + centroidX, caY * rotR + caX * rotI + centroidY
        )
        _pointB = Vector2F(
            cbX * rotR - cbY * rotI + centroidX, cbY * rotR + cbX * rotI + centroidY
        )
        _pointC = Vector2F(
            ccX * rotR - ccY * rotI + centroidX, ccY * rotR + ccX * rotI + centroidY
        )
        _orientation = ComplexF(
            startRotR * rotR - startRotI * rotI, startRotI * rotR + startRotR * rotI
        )
    }

    override fun rotateTo(orientation: AngleF) = rotateTo(ComplexF.fromAngle(orientation))

    override fun rotateTo(orientation: ComplexF) {
        val (aX: Float, aY: Float) = _pointA
        val (bX: Float, bY: Float) = _pointB
        val (cX: Float, cY: Float) = _pointC
        val (centroidX: Float, centroidY: Float) = _centroid
        val (rotR: Float, rotI: Float) = _orientation.conjugate * orientation
        val caX: Float = aX - centroidX
        val caY: Float = aY - centroidY
        val cbX: Float = bX - centroidX
        val cbY: Float = bY - centroidY
        val ccX: Float = cX - centroidX
        val ccY: Float = cY - centroidY
        _pointA = Vector2F(
            caX * rotR - caY * rotI + centroidX, caY * rotR + caX * rotI + centroidY
        )
        _pointB = Vector2F(
            cbX * rotR - cbY * rotI + centroidX, cbY * rotR + cbX * rotI + centroidY
        )
        _pointC = Vector2F(
            ccX * rotR - ccY * rotI + centroidX, ccY * rotR + ccX * rotI + centroidY
        )
        _orientation = orientation
    }

    override fun rotateAroundPointBy(point: Vector2F, rotation: AngleF) =
        rotateAroundPointBy(point, ComplexF.fromAngle(rotation))

    override fun rotateAroundPointBy(point: Vector2F, rotation: ComplexF) {
        val (aX: Float, aY: Float) = _pointA
        val (bX: Float, bY: Float) = _pointB
        val (cX: Float, cY: Float) = _pointC
        val (centroidX: Float, centroidY: Float) = _centroid
        val (startRotR: Float, startRotI: Float) = _orientation
        val (pX: Float, pY: Float) = point
        val (rotR: Float, rotI: Float) = rotation
        val cpDiffX: Float = centroidX - pX
        val cpDiffY: Float = centroidY - pY
        val targetCenterX: Float = cpDiffX * rotR - cpDiffY * rotI + pX
        val targetCenterY: Float = cpDiffY * rotR + cpDiffX * rotI + pY
        val caX: Float = aX - centroidX
        val caY: Float = aY - centroidY
        val cbX: Float = bX - centroidX
        val cbY: Float = bY - centroidY
        val ccX: Float = cX - centroidX
        val ccY: Float = cY - centroidY
        _pointA = Vector2F(
            caX * rotR - caY * rotI + targetCenterX,
            caY * rotR + caX * rotI + targetCenterY
        )
        _pointB = Vector2F(
            cbX * rotR - cbY * rotI + targetCenterX,
            cbY * rotR + cbX * rotI + targetCenterY
        )
        _pointC = Vector2F(
            ccX * rotR - ccY * rotI + targetCenterX,
            ccY * rotR + ccX * rotI + targetCenterY
        )
        _centroid = Vector2F(targetCenterX, targetCenterY)
        _orientation = ComplexF(
            startRotR * rotR - startRotI * rotI,
            startRotI * rotR + startRotR * rotI
        )
    }

    override fun rotateAroundPointTo(point: Vector2F, orientation: AngleF) =
        rotateAroundPointTo(point, ComplexF.fromAngle(orientation))

    override fun rotateAroundPointTo(point: Vector2F, orientation: ComplexF) {
        val (aX: Float, aY: Float) = _pointA
        val (bX: Float, bY: Float) = _pointB
        val (cX: Float, cY: Float) = _pointC
        val (centroidX: Float, centroidY: Float) = _centroid
        val (startRotR: Float, startRotI: Float) = _orientation
        val caX: Float = aX - centroidX
        val caY: Float = aY - centroidY
        val cbX: Float = bX - centroidX
        val cbY: Float = bY - centroidY
        val ccX: Float = cX - centroidX
        val ccY: Float = cY - centroidY
        val (pX: Float, pY: Float) = point
        val (rotR: Float, rotI: Float) = orientation
        val cpDiffX: Float = centroidX - pX
        val cpDiffY: Float = centroidY - pY
        val centerToPointDist: Float = sqrt(cpDiffX * cpDiffX + cpDiffY * cpDiffY)

        if (centerToPointDist > 0.00001f) {
            val pointRotR: Float = cpDiffX / centerToPointDist
            val pointRotI: Float = cpDiffY / centerToPointDist
            val pRotR: Float = pointRotR * rotR + pointRotI * rotI
            val pRotI: Float = pointRotR * rotI - pointRotI * rotR
            val targetCenterX: Float = rotR * centerToPointDist + pX
            val targetCenterY: Float = rotI * centerToPointDist + pY
            _pointA = Vector2F(
                caX * pRotR - caY * pRotI + targetCenterX,
                caY * pRotR + caX * pRotI + targetCenterY
            )
            _pointB = Vector2F(
                cbX * pRotR - cbY * pRotI + targetCenterX,
                cbY * pRotR + cbX * pRotI + targetCenterY
            )
            _pointC = Vector2F(
                ccX * pRotR - ccY * pRotI + targetCenterX,
                ccY * pRotR + ccX * pRotI + targetCenterY
            )
            _centroid = Vector2F(targetCenterX, targetCenterY)
            _orientation = ComplexF(
                pRotR * startRotR - pRotI * startRotI,
                pRotI * startRotR + pRotR * startRotI
            )
        } else {
            val pRotR: Float = rotR * startRotR + rotI * startRotI
            val pRotI: Float = rotI * startRotR - rotR * startRotI
            _pointA = Vector2F(
                caX * pRotR - caY * pRotI + centroidX,
                caY * pRotR + caX * pRotI + centroidY
            )
            _pointB = Vector2F(
                cbX * pRotR - cbY * pRotI + centroidX,
                cbY * pRotR + cbX * pRotI + centroidY
            )
            _pointC = Vector2F(
                ccX * pRotR - ccY * pRotI + centroidX,
                ccY * pRotR + ccX * pRotI + centroidY
            )
            _orientation = orientation
        }
    }

    override fun scaledBy(factor: Float): MutableTriangle {
        val (aX: Float, aY: Float) = _pointA
        val (bX: Float, bY: Float) = _pointB
        val (cX: Float, cY: Float) = _pointC
        val (centroidX: Float, centroidY: Float) = _centroid
        val f: Float = 1f - factor
        val addendX: Float = centroidX * f
        val addendY: Float = centroidY * f

        return MutableTriangle(
            pointA = Vector2F(aX * factor + addendX, aY * factor + addendY),
            pointB = Vector2F(bX * factor + addendX, bY * factor + addendY),
            pointC = Vector2F(cX * factor + addendX, cY * factor + addendY),
            _centroid,
            _orientation * 1f.withSign(factor)
        )
    }

    override fun dilatedBy(point: Vector2F, factor: Float): MutableTriangle {
        val (aX: Float, aY: Float) = _pointA
        val (bX: Float, bY: Float) = _pointB
        val (cX: Float, cY: Float) = _pointC
        val (centroidX: Float, centroidY: Float) = _centroid
        val f: Float = 1f - factor
        val addendX: Float = point.x * f
        val addendY: Float = point.y * f

        return MutableTriangle(
            pointA = Vector2F(aX * factor + addendX, aY * factor + addendY),
            pointB = Vector2F(bX * factor + addendX, bY * factor + addendY),
            pointC = Vector2F(cX * factor + addendX, cY * factor + addendY),
            centroid = Vector2F(
                centroidX * factor + addendX, centroidY * factor + addendY
            ),
            _orientation * 1f.withSign(factor)
        )
    }

    override fun scaleBy(factor: Float) {
        val (aX: Float, aY: Float) = _pointA
        val (bX: Float, bY: Float) = _pointB
        val (cX: Float, cY: Float) = _pointC
        val (centroidX: Float, centroidY: Float) = _centroid
        val f: Float = 1f - factor
        val addendX: Float = centroidX * f
        val addendY: Float = centroidY * f
        _pointA = Vector2F(aX * factor + addendX, aY * factor + addendY)
        _pointB = Vector2F(bX * factor + addendX, bY * factor + addendY)
        _pointC = Vector2F(cX * factor + addendX, cY * factor + addendY)
        _orientation *= 1f.withSign(factor)
    }

    override fun dilateBy(point: Vector2F, factor: Float) {
        val (aX: Float, aY: Float) = _pointA
        val (bX: Float, bY: Float) = _pointB
        val (cX: Float, cY: Float) = _pointC
        val (centroidX: Float, centroidY: Float) = _centroid
        val f: Float = 1f - factor
        val addendX: Float = point.x * f
        val addendY: Float = point.y * f
        _pointA = Vector2F(aX * factor + addendX, aY * factor + addendY)
        _pointB = Vector2F(bX * factor + addendX, bY * factor + addendY)
        _pointC = Vector2F(cX * factor + addendX, cY * factor + addendY)
        _centroid = Vector2F(centroidX * factor + addendX, centroidY * factor + addendY)
        _orientation *= 1f.withSign(factor)
    }

    override fun transformedBy(displacement: Vector2F, rotation: AngleF): MutableTriangle =
        transformedBy(displacement, ComplexF.fromAngle(rotation))

    override fun transformedBy(displacement: Vector2F, rotation: ComplexF): MutableTriangle {
        val (aX: Float, aY: Float) = _pointA
        val (bX: Float, bY: Float) = _pointB
        val (cX: Float, cY: Float) = _pointC
        val (centroidX: Float, centroidY: Float) = _centroid
        val (startRotR: Float, startRotI: Float) = _orientation
        val (displacementX: Float, displacementY: Float) = displacement
        val (rotR: Float, rotI: Float) = rotation
        val caX: Float = aX - centroidX
        val caY: Float = aY - centroidY
        val cbX: Float = bX - centroidX
        val cbY: Float = bY - centroidY
        val ccX: Float = cX - centroidX
        val ccY: Float = cY - centroidY
        val targetPosX: Float = centroidX + displacementX
        val targetPosY: Float = centroidY + displacementY

        return MutableTriangle(
            Vector2F(
                caX * rotR - caY * rotI + targetPosX,
                caY * rotR + caX * rotI + targetPosY
            ),
            Vector2F(
                cbX * rotR - cbY * rotI + targetPosX,
                cbY * rotR + cbX * rotI + targetPosY
            ),
            Vector2F(
                ccX * rotR - ccY * rotI + targetPosX,
                ccY * rotR + ccX * rotI + targetPosY
            ),
            Vector2F(targetPosX, targetPosY),
            ComplexF(
                startRotR * rotR - startRotI * rotI,
                startRotI * rotR + startRotR * rotI
            )
        )
    }

    override fun transformedBy(
        displacement: Vector2F, rotation: AngleF, scaleFactor: Float
    ): MutableTriangle = transformedBy(displacement, ComplexF.fromAngle(rotation), scaleFactor)

    override fun transformedBy(
        displacement: Vector2F, rotation: ComplexF, scaleFactor: Float
    ): MutableTriangle {
        val (aX: Float, aY: Float) = _pointA
        val (bX: Float, bY: Float) = _pointB
        val (cX: Float, cY: Float) = _pointC
        val (centroidX: Float, centroidY: Float) = _centroid
        val (startRotR: Float, startRotI: Float) = _orientation
        val (displacementX: Float, displacementY: Float) = displacement
        val (rotR: Float, rotI: Float) = rotation
        val caX: Float = aX - centroidX
        val caY: Float = aY - centroidY
        val cbX: Float = bX - centroidX
        val cbY: Float = bY - centroidY
        val ccX: Float = cX - centroidX
        val ccY: Float = cY - centroidY
        val targetPosX: Float = centroidX + displacementX
        val targetPosY: Float = centroidY + displacementY
        val f: Float = 1f - scaleFactor
        val scaleFactorSign: Float = 1f.withSign(scaleFactor)
        val addendX: Float = targetPosX * f
        val addendY: Float = targetPosY * f

        return MutableTriangle(
            Vector2F(
                (caX * rotR - caY * rotI + targetPosX) * scaleFactor + addendX,
                (caY * rotR + caX * rotI + targetPosY) * scaleFactor + addendY
            ),
            Vector2F(
                (cbX * rotR - cbY * rotI + targetPosX) * scaleFactor + addendX,
                (cbY * rotR + cbX * rotI + targetPosY) * scaleFactor + addendY
            ),
            Vector2F(
                (ccX * rotR - ccY * rotI + targetPosX) * scaleFactor + addendX,
                (ccY * rotR + ccX * rotI + targetPosY) * scaleFactor + addendY
            ),
            Vector2F(targetPosX, targetPosY),
            ComplexF(
                (startRotR * rotR - startRotI * rotI) * scaleFactorSign,
                (startRotI * rotR + startRotR * rotI) * scaleFactorSign
            )
        )
    }

    override fun transformedTo(position: Vector2F, orientation: AngleF): MutableTriangle =
        transformedTo(position, ComplexF.fromAngle(orientation))

    override fun transformedTo(position: Vector2F, orientation: ComplexF): MutableTriangle {
        val (aX: Float, aY: Float) = _pointA
        val (bX: Float, bY: Float) = _pointB
        val (cX: Float, cY: Float) = _pointC
        val (centroidX: Float, centroidY: Float) = _centroid
        val (rotR: Float, rotI: Float) = _orientation.conjugate * orientation
        val (pX: Float, pY: Float) = position
        val caX: Float = aX - centroidX
        val caY: Float = aY - centroidY
        val cbX: Float = bX - centroidX
        val cbY: Float = bY - centroidY
        val ccX: Float = cX - centroidX
        val ccY: Float = cY - centroidY

        return MutableTriangle(
            Vector2F(caX * rotR - caY * rotI + pX, caY * rotR + caX * rotI + pY),
            Vector2F(cbX * rotR - cbY * rotI + pX, cbY * rotR + cbX * rotI + pY),
            Vector2F(ccX * rotR - ccY * rotI + pX, ccY * rotR + ccX * rotI + pY),
            position,
            orientation
        )
    }

    override fun transformBy(displacement: Vector2F, rotation: AngleF) =
        transformBy(displacement, ComplexF.fromAngle(rotation))

    override fun transformBy(displacement: Vector2F, rotation: ComplexF) {
        val (aX: Float, aY: Float) = _pointA
        val (bX: Float, bY: Float) = _pointB
        val (cX: Float, cY: Float) = _pointC
        val (centroidX: Float, centroidY: Float) = _centroid
        val (startRotR: Float, startRotI: Float) = _orientation
        val (displacementX: Float, displacementY: Float) = displacement
        val (rotR: Float, rotI: Float) = rotation
        val caX: Float = aX - centroidX
        val caY: Float = aY - centroidY
        val cbX: Float = bX - centroidX
        val cbY: Float = bY - centroidY
        val ccX: Float = cX - centroidX
        val ccY: Float = cY - centroidY
        val targetPosX: Float = centroidX + displacementX
        val targetPosY: Float = centroidY + displacementY
        _pointA = Vector2F(
            caX * rotR - caY * rotI + targetPosX, caY * rotR + caX * rotI + targetPosY
        )
        _pointB = Vector2F(
            cbX * rotR - cbY * rotI + targetPosX, cbY * rotR + cbX * rotI + targetPosY
        )
        _pointC = Vector2F(
            ccX * rotR - ccY * rotI + targetPosX, ccY * rotR + ccX * rotI + targetPosY
        )
        _centroid = Vector2F(targetPosX, targetPosY)
        _orientation = ComplexF(
            startRotR * rotR - startRotI * rotI, startRotI * rotR + startRotR * rotI
        )
    }

    override fun transformBy(displacement: Vector2F, rotation: AngleF, scaleFactor: Float) =
        transformBy(displacement, ComplexF.fromAngle(rotation), scaleFactor)

    override fun transformBy(displacement: Vector2F, rotation: ComplexF, scaleFactor: Float) {
        val (aX: Float, aY: Float) = _pointA
        val (bX: Float, bY: Float) = _pointB
        val (cX: Float, cY: Float) = _pointC
        val (centroidX: Float, centroidY: Float) = _centroid
        val (startRotR: Float, startRotI: Float) = _orientation
        val (displacementX: Float, displacementY: Float) = displacement
        val (rotR: Float, rotI: Float) = rotation
        val caX: Float = aX - centroidX
        val caY: Float = aY - centroidY
        val cbX: Float = bX - centroidX
        val cbY: Float = bY - centroidY
        val ccX: Float = cX - centroidX
        val ccY: Float = cY - centroidY
        val targetPosX: Float = centroidX + displacementX
        val targetPosY: Float = centroidY + displacementY
        val f: Float = 1f - scaleFactor
        val scaleFactorSign: Float = 1f.withSign(scaleFactor)
        val addendX: Float = targetPosX * f
        val addendY: Float = targetPosY * f
        _pointA = Vector2F(
            (caX * rotR - caY * rotI + targetPosX) * scaleFactor + addendX,
            (caY * rotR + caX * rotI + targetPosY) * scaleFactor + addendY
        )
        _pointB = Vector2F(
            (cbX * rotR - cbY * rotI + targetPosX) * scaleFactor + addendX,
            (cbY * rotR + cbX * rotI + targetPosY) * scaleFactor + addendY
        )
        _pointC = Vector2F(
            (ccX * rotR - ccY * rotI + targetPosX) * scaleFactor + addendX,
            (ccY * rotR + ccX * rotI + targetPosY) * scaleFactor + addendY
        )
        _centroid = Vector2F(targetPosX, targetPosY)
        _orientation = ComplexF(
            (startRotR * rotR - startRotI * rotI) * scaleFactorSign,
            (startRotI * rotR + startRotR * rotI) * scaleFactorSign
        )
    }

    override fun transformTo(position: Vector2F, orientation: AngleF) =
        transformTo(position, ComplexF.fromAngle(orientation))

    override fun transformTo(position: Vector2F, orientation: ComplexF) {
        val (aX: Float, aY: Float) = _pointA
        val (bX: Float, bY: Float) = _pointB
        val (cX: Float, cY: Float) = _pointC
        val (centroidX: Float, centroidY: Float) = _centroid
        val (rotR: Float, rotI: Float) = _orientation.conjugate * orientation
        val (pX: Float, pY: Float) = position
        val caX: Float = aX - centroidX
        val caY: Float = aY - centroidY
        val cbX: Float = bX - centroidX
        val cbY: Float = bY - centroidY
        val ccX: Float = cX - centroidX
        val ccY: Float = cY - centroidY
        _pointA = Vector2F(caX * rotR - caY * rotI + pX, caY * rotR + caX * rotI + pY)
        _pointB = Vector2F(cbX * rotR - cbY * rotI + pX, cbY * rotR + cbX * rotI + pY)
        _pointC = Vector2F(ccX * rotR - ccY * rotI + pX, ccY * rotR + ccX * rotI + pY)
        _centroid = position
        _orientation = orientation
    }

    private inline fun setInternal(pointA: Vector2F, pointB: Vector2F, pointC: Vector2F) {
        _pointA = pointA
        _pointB = pointB
        _pointC = pointC
        _centroid = (pointA + pointB + pointC) * 0.3333333f
        _orientation = (pointA - _centroid).normalized.toComplexF()
    }

    /** Sets the specified properties of this instance. **/
    fun set(
        pointA: Vector2F = this.pointA,
        pointB: Vector2F = this.pointB,
        pointC: Vector2F = this.pointC
    ) = setInternal(pointA, pointB, pointC)

    override fun interpolated(to: Triangle, by: Float) = MutableTriangle(
        pointA = Vector2F.lerp(_pointA, to.pointA, by),
        pointB = Vector2F.lerp(_pointB, to.pointB, by),
        pointC = Vector2F.lerp(_pointC, to.pointC, by)
    )

    /**
     * Sets this triangle with the result of interpolation [from] one triangle [to] another
     * triangle [by] a factor.
     *
     * @param from the triangle from which the interpolation starts.
     * @param to the triangle at which the interpolation ends.
     * @param by the interpolation factor which is expected to be in the range of `[0, 1]`.
     */
    fun interpolate(from: Triangle, to: Triangle, by: Float) = setInternal(
        pointA = Vector2F.lerp(from.pointA, to.pointA, by),
        pointB = Vector2F.lerp(from.pointB, to.pointB, by),
        pointC = Vector2F.lerp(from.pointC, to.pointC, by)
    )

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

    override fun copy(pointA: Vector2F, pointB: Vector2F, pointC: Vector2F) =
        MutableTriangle(pointA, pointB, pointC)

    override fun equals(other: Any?): Boolean = other is Triangle &&
            _pointA == other.pointA &&
            _pointB == other.pointB &&
            _pointC == other.pointC

    /** Indicates whether the other [MutableTriangle] is equal to this one. **/
    fun equals(other: MutableTriangle): Boolean =
        _pointA == other._pointA &&
                _pointB == other._pointB &&
                _pointC == other._pointC

    override fun hashCode(): Int {
        val pointAHash: Int = _pointA.hashCode()
        val pointBHash: Int = _pointB.hashCode()
        val pointCHash: Int = _pointC.hashCode()

        return pointAHash * 961 + pointBHash * 31 + pointCHash
    }

    override fun toString() =
        StringBuilder("Triangle(pointA=").append(_pointA)
            .append(", pointB=").append(_pointB)
            .append(", pointC=").append(_pointC).append(")")
            .toString()

    override operator fun component1(): Vector2F = _pointA

    override operator fun component2(): Vector2F = _pointB

    override operator fun component3(): Vector2F = _pointC

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