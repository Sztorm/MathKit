package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.AngleF
import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F
import com.sztorm.lowallocmath.Vector2FIterator
import kotlin.math.abs
import kotlin.math.sqrt
import kotlin.math.withSign

class MutableTriangle : Triangle, MutableTransformable {
    private var _pointA: Vector2F
    private var _pointB: Vector2F
    private var _pointC: Vector2F
    private var _centroid: Vector2F
    private var _orientation: ComplexF

    constructor(pointA: Vector2F, pointB: Vector2F, pointC: Vector2F) {
        _pointA = pointA
        _pointB = pointB
        _pointC = pointC
        _centroid = (_pointA + _pointB + _pointC) * 0.3333333f
        _orientation = (_pointA - centroid).normalized.toComplexF()
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

    override val centroid: Vector2F
        get() = _centroid

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

    override fun movedBy(offset: Vector2F) = MutableTriangle(
        _pointA + offset,
        _pointB + offset,
        _pointC + offset,
        _centroid + offset,
        _orientation
    )

    override fun movedTo(position: Vector2F): MutableTriangle {
        val offset: Vector2F = position - _centroid

        return MutableTriangle(
            _pointA + offset,
            _pointB + offset,
            _pointC + offset,
            position,
            _orientation
        )
    }

    override fun moveBy(offset: Vector2F) {
        _pointA += offset
        _pointB += offset
        _pointC += offset
        _centroid += offset
    }

    override fun moveTo(position: Vector2F) {
        val offset: Vector2F = position - _centroid
        _pointA += offset
        _pointB += offset
        _pointC += offset
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
        val (rotR: Float, rotI: Float) = ComplexF.conjugate(_orientation) * orientation
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
        val (rotR: Float, rotI: Float) = ComplexF.conjugate(_orientation) * orientation
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

    override fun transformedBy(offset: Vector2F, rotation: AngleF): MutableTriangle =
        transformedBy(offset, ComplexF.fromAngle(rotation))

    override fun transformedBy(offset: Vector2F, rotation: ComplexF): MutableTriangle {
        val (aX: Float, aY: Float) = _pointA
        val (bX: Float, bY: Float) = _pointB
        val (cX: Float, cY: Float) = _pointC
        val (centroidX: Float, centroidY: Float) = _centroid
        val (startRotR: Float, startRotI: Float) = _orientation
        val (oX: Float, oY: Float) = offset
        val (rotR: Float, rotI: Float) = rotation
        val caX: Float = aX - centroidX
        val caY: Float = aY - centroidY
        val cbX: Float = bX - centroidX
        val cbY: Float = bY - centroidY
        val ccX: Float = cX - centroidX
        val ccY: Float = cY - centroidY
        val targetPosX: Float = centroidX + oX
        val targetPosY: Float = centroidY + oY

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
        offset: Vector2F, rotation: AngleF, factor: Float
    ): MutableTriangle = transformedBy(offset, ComplexF.fromAngle(rotation), factor)

    override fun transformedBy(
        offset: Vector2F, rotation: ComplexF, factor: Float
    ): MutableTriangle {
        val (aX: Float, aY: Float) = _pointA
        val (bX: Float, bY: Float) = _pointB
        val (cX: Float, cY: Float) = _pointC
        val (centroidX: Float, centroidY: Float) = _centroid
        val (startRotR: Float, startRotI: Float) = _orientation
        val (oX: Float, oY: Float) = offset
        val (rotR: Float, rotI: Float) = rotation
        val caX: Float = aX - centroidX
        val caY: Float = aY - centroidY
        val cbX: Float = bX - centroidX
        val cbY: Float = bY - centroidY
        val ccX: Float = cX - centroidX
        val ccY: Float = cY - centroidY
        val targetPosX: Float = centroidX + oX
        val targetPosY: Float = centroidY + oY
        val f: Float = 1f - factor
        val factorSign: Float = 1f.withSign(factor)
        val addendX: Float = targetPosX * f
        val addendY: Float = targetPosY * f

        return MutableTriangle(
            Vector2F(
                (caX * rotR - caY * rotI + targetPosX) * factor + addendX,
                (caY * rotR + caX * rotI + targetPosY) * factor + addendY
            ),
            Vector2F(
                (cbX * rotR - cbY * rotI + targetPosX) * factor + addendX,
                (cbY * rotR + cbX * rotI + targetPosY) * factor + addendY
            ),
            Vector2F(
                (ccX * rotR - ccY * rotI + targetPosX) * factor + addendX,
                (ccY * rotR + ccX * rotI + targetPosY) * factor + addendY
            ),
            Vector2F(targetPosX, targetPosY),
            ComplexF(
                (startRotR * rotR - startRotI * rotI) * factorSign,
                (startRotI * rotR + startRotR * rotI) * factorSign
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
        val (rotR: Float, rotI: Float) = ComplexF.conjugate(_orientation) * orientation
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

    override fun transformBy(offset: Vector2F, rotation: AngleF) =
        transformBy(offset, ComplexF.fromAngle(rotation))

    override fun transformBy(offset: Vector2F, rotation: ComplexF) {
        val (aX: Float, aY: Float) = _pointA
        val (bX: Float, bY: Float) = _pointB
        val (cX: Float, cY: Float) = _pointC
        val (centroidX: Float, centroidY: Float) = _centroid
        val (startRotR: Float, startRotI: Float) = _orientation
        val (oX: Float, oY: Float) = offset
        val (rotR: Float, rotI: Float) = rotation
        val caX: Float = aX - centroidX
        val caY: Float = aY - centroidY
        val cbX: Float = bX - centroidX
        val cbY: Float = bY - centroidY
        val ccX: Float = cX - centroidX
        val ccY: Float = cY - centroidY
        val targetPosX: Float = centroidX + oX
        val targetPosY: Float = centroidY + oY
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

    override fun transformBy(offset: Vector2F, rotation: AngleF, factor: Float) =
        transformBy(offset, ComplexF.fromAngle(rotation), factor)

    override fun transformBy(offset: Vector2F, rotation: ComplexF, factor: Float) {
        val (aX: Float, aY: Float) = _pointA
        val (bX: Float, bY: Float) = _pointB
        val (cX: Float, cY: Float) = _pointC
        val (centroidX: Float, centroidY: Float) = _centroid
        val (startRotR: Float, startRotI: Float) = _orientation
        val (oX: Float, oY: Float) = offset
        val (rotR: Float, rotI: Float) = rotation
        val caX: Float = aX - centroidX
        val caY: Float = aY - centroidY
        val cbX: Float = bX - centroidX
        val cbY: Float = bY - centroidY
        val ccX: Float = cX - centroidX
        val ccY: Float = cY - centroidY
        val targetPosX: Float = centroidX + oX
        val targetPosY: Float = centroidY + oY
        val f: Float = 1f - factor
        val factorSign: Float = 1f.withSign(factor)
        val addendX: Float = targetPosX * f
        val addendY: Float = targetPosY * f
        _pointA = Vector2F(
            (caX * rotR - caY * rotI + targetPosX) * factor + addendX,
            (caY * rotR + caX * rotI + targetPosY) * factor + addendY
        )
        _pointB = Vector2F(
            (cbX * rotR - cbY * rotI + targetPosX) * factor + addendX,
            (cbY * rotR + cbX * rotI + targetPosY) * factor + addendY
        )
        _pointC = Vector2F(
            (ccX * rotR - ccY * rotI + targetPosX) * factor + addendX,
            (ccY * rotR + ccX * rotI + targetPosY) * factor + addendY
        )
        _centroid = Vector2F(targetPosX, targetPosY)
        _orientation = ComplexF(
            (startRotR * rotR - startRotI * rotI) * factorSign,
            (startRotI * rotR + startRotR * rotI) * factorSign
        )
    }

    override fun transformTo(position: Vector2F, orientation: AngleF) =
        transformTo(position, ComplexF.fromAngle(orientation))

    override fun transformTo(position: Vector2F, orientation: ComplexF) {
        val (aX: Float, aY: Float) = _pointA
        val (bX: Float, bY: Float) = _pointB
        val (cX: Float, cY: Float) = _pointC
        val (centroidX: Float, centroidY: Float) = _centroid
        val (rotR: Float, rotI: Float) = ComplexF.conjugate(_orientation) * orientation
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