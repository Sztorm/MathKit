package com.sztorm.lowallocmath.euclidean2d

import com.sztorm.lowallocmath.AngleF
import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F
import com.sztorm.lowallocmath.Vector2FIterator
import kotlin.math.abs
import kotlin.math.sqrt

fun Triangle(pointA: Vector2F, pointB: Vector2F, pointC: Vector2F): Triangle =
    MutableTriangle(pointA, pointB, pointC)

interface Triangle : TriangleShape, Transformable {
    val pointA: Vector2F
    val pointB: Vector2F
    val pointC: Vector2F

    val centroid: Vector2F
        get() = (pointA + pointB + pointC) * 0.3333333f

    val orthocenter: Vector2F
        get() {
            val (aX: Float, aY: Float) = pointA
            val (bX: Float, bY: Float) = pointB
            val (cX: Float, cY: Float) = pointC
            val centroidX: Float = (aX + bX + cX) * 0.3333333f
            val centroidY: Float = (aY + bY + cY) * 0.3333333f
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

    val incenter: Vector2F
        get() {
            val pointA: Vector2F = pointA
            val pointB: Vector2F = pointB
            val pointC: Vector2F = pointC
            val abSide: Float = pointA.distanceTo(pointB)
            val bcSide: Float = pointB.distanceTo(pointC)
            val acSide: Float = pointA.distanceTo(pointC)
            val factor: Float = 1f / (abSide + bcSide + acSide)

            return Vector2F(
                (bcSide * pointA.x + acSide * pointB.x + abSide * pointC.x) * factor,
                (bcSide * pointA.y + acSide * pointB.y + abSide * pointC.y) * factor
            )
        }

    val circumcenter: Vector2F
        get() {
            val (aX: Float, aY: Float) = pointA
            val (bX: Float, bY: Float) = pointB
            val (cX: Float, cY: Float) = pointC
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

    override val area: Float
        get() {
            val (aX: Float, aY: Float) = pointA
            val (bX: Float, bY: Float) = pointB
            val (cX: Float, cY: Float) = pointC

            return 0.5f * abs((aX - cX) * (bY - cY) - (bX - cX) * (aY - cY))
        }

    override val perimeter: Float
        get() {
            val pointA: Vector2F = pointA
            val pointB: Vector2F = pointB
            val pointC: Vector2F = pointC

            return pointA.distanceTo(pointB) +
                    pointB.distanceTo(pointC) +
                    pointC.distanceTo(pointA)
        }

    override val sideLengthAB: Float
        get() = pointA.distanceTo(pointB)

    override val sideLengthBC: Float
        get() = pointB.distanceTo(pointC)

    override val sideLengthCA: Float
        get() = pointC.distanceTo(pointA)

    override val position: Vector2F
        get() = (pointA + pointB + pointC) * 0.3333333f

    override val orientation: ComplexF
        get() {
            val pointA: Vector2F = this.pointA
            val centroid: Vector2F = (pointA + pointB + pointC) * 0.3333333f

            return (pointA - centroid).normalized.toComplexF()
        }

    override fun movedBy(offset: Vector2F): Triangle = copy(
        pointA = pointA + offset,
        pointB = pointB + offset,
        pointC = pointC + offset
    )

    override fun movedTo(position: Vector2F): Triangle {
        val centroid: Vector2F = (pointA + pointB + pointC) * 0.3333333f
        val offset: Vector2F = position - centroid

        return copy(
            pointA = pointA + offset,
            pointB = pointB + offset,
            pointC = pointC + offset
        )
    }

    private fun rotatedByImpl(rotation: ComplexF): Triangle {
        val (aX: Float, aY: Float) = pointA
        val (bX: Float, bY: Float) = pointB
        val (cX: Float, cY: Float) = pointC
        val (rotR: Float, rotI: Float) = rotation
        val centroidX: Float = (aX + bX + cX) * 0.3333333f
        val centroidY: Float = (aY + bY + cY) * 0.3333333f
        val caX: Float = aX - centroidX
        val caY: Float = aY - centroidY
        val cbX: Float = bX - centroidX
        val cbY: Float = bY - centroidY
        val ccX: Float = cX - centroidX
        val ccY: Float = cY - centroidY

        return copy(
            pointA = Vector2F(
                caX * rotR - caY * rotI + centroidX, caY * rotR + caX * rotI + centroidY
            ),
            pointB = Vector2F(
                cbX * rotR - cbY * rotI + centroidX, cbY * rotR + cbX * rotI + centroidY
            ),
            pointC = Vector2F(
                ccX * rotR - ccY * rotI + centroidX, ccY * rotR + ccX * rotI + centroidY
            )
        )
    }

    override fun rotatedBy(rotation: AngleF): Triangle =
        rotatedByImpl(ComplexF.fromAngle(rotation))

    override fun rotatedBy(rotation: ComplexF): Triangle = rotatedByImpl(rotation)

    private fun rotatedToImpl(orientation: ComplexF): Triangle {
        val (aX: Float, aY: Float) = pointA
        val (bX: Float, bY: Float) = pointB
        val (cX: Float, cY: Float) = pointC
        val centroidX: Float = (aX + bX + cX) * 0.3333333f
        val centroidY: Float = (aY + bY + cY) * 0.3333333f
        val conjugatedStartRot = ComplexF(aX - centroidX, centroidY - aY).normalized
        val (rotR: Float, rotI: Float) = conjugatedStartRot * orientation
        val caX: Float = aX - centroidX
        val caY: Float = aY - centroidY
        val cbX: Float = bX - centroidX
        val cbY: Float = bY - centroidY
        val ccX: Float = cX - centroidX
        val ccY: Float = cY - centroidY

        return copy(
            pointA = Vector2F(
                caX * rotR - caY * rotI + centroidX, caY * rotR + caX * rotI + centroidY
            ),
            pointB = Vector2F(
                cbX * rotR - cbY * rotI + centroidX, cbY * rotR + cbX * rotI + centroidY
            ),
            pointC = Vector2F(
                ccX * rotR - ccY * rotI + centroidX, ccY * rotR + ccX * rotI + centroidY
            )
        )
    }

    override fun rotatedTo(orientation: AngleF): Triangle =
        rotatedToImpl(ComplexF.fromAngle(orientation))

    override fun rotatedTo(orientation: ComplexF): Triangle = rotatedToImpl(orientation)

    private fun rotatedAroundPointByImpl(point: Vector2F, rotation: ComplexF): Triangle {
        val (aX: Float, aY: Float) = pointA
        val (bX: Float, bY: Float) = pointB
        val (cX: Float, cY: Float) = pointC
        val centroidX: Float = (aX + bX + cX) * 0.3333333f
        val centroidY: Float = (aY + bY + cY) * 0.3333333f
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

        return copy(
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
            )
        )
    }

    override fun rotatedAroundPointBy(point: Vector2F, rotation: AngleF): Triangle =
        rotatedAroundPointByImpl(point, ComplexF.fromAngle(rotation))

    override fun rotatedAroundPointBy(point: Vector2F, rotation: ComplexF): Triangle =
        rotatedAroundPointByImpl(point, rotation)

    private fun rotatedAroundPointToImpl(point: Vector2F, orientation: ComplexF): Triangle {
        val (aX: Float, aY: Float) = pointA
        val (bX: Float, bY: Float) = pointB
        val (cX: Float, cY: Float) = pointC
        val centroidX: Float = (aX + bX + cX) * 0.3333333f
        val centroidY: Float = (aY + bY + cY) * 0.3333333f
        val (startRotR: Float, startRotI: Float) =
            ComplexF(aX - centroidX, aY - centroidY).normalized
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

            return copy(
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
                )
            )
        } else {
            val pRotR: Float = rotR * startRotR + rotI * startRotI
            val pRotI: Float = rotI * startRotR - rotR * startRotI

            return copy(
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
                )
            )
        }
    }

    override fun rotatedAroundPointTo(point: Vector2F, orientation: AngleF): Triangle =
        rotatedAroundPointToImpl(point, ComplexF.fromAngle(orientation))

    override fun rotatedAroundPointTo(point: Vector2F, orientation: ComplexF): Triangle =
        rotatedAroundPointToImpl(point, orientation)

    override fun scaledBy(factor: Float): Triangle {
        val (aX: Float, aY: Float) = pointA
        val (bX: Float, bY: Float) = pointB
        val (cX: Float, cY: Float) = pointC
        val centroidX: Float = (aX + bX + cX) * 0.3333333f
        val centroidY: Float = (aY + bY + cY) * 0.3333333f
        val f: Float = 1f - factor
        val addendX: Float = centroidX * f
        val addendY: Float = centroidY * f

        return copy(
            pointA = Vector2F(aX * factor + addendX, aY * factor + addendY),
            pointB = Vector2F(bX * factor + addendX, bY * factor + addendY),
            pointC = Vector2F(cX * factor + addendX, cY * factor + addendY)
        )
    }

    override fun dilatedBy(point: Vector2F, factor: Float): Triangle {
        val (aX: Float, aY: Float) = pointA
        val (bX: Float, bY: Float) = pointB
        val (cX: Float, cY: Float) = pointC
        val f: Float = 1f - factor
        val addendX: Float = point.x * f
        val addendY: Float = point.y * f

        return copy(
            pointA = Vector2F(aX * factor + addendX, aY * factor + addendY),
            pointB = Vector2F(bX * factor + addendX, bY * factor + addendY),
            pointC = Vector2F(cX * factor + addendX, cY * factor + addendY)
        )
    }

    private fun transformedByImpl(offset: Vector2F, rotation: ComplexF): Triangle {
        val (aX: Float, aY: Float) = pointA
        val (bX: Float, bY: Float) = pointB
        val (cX: Float, cY: Float) = pointC
        val centroidX: Float = (aX + bX + cX) * 0.3333333f
        val centroidY: Float = (aY + bY + cY) * 0.3333333f
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

        return copy(
            pointA = Vector2F(
                caX * rotR - caY * rotI + targetPosX, caY * rotR + caX * rotI + targetPosY
            ),
            pointB = Vector2F(
                cbX * rotR - cbY * rotI + targetPosX, cbY * rotR + cbX * rotI + targetPosY
            ),
            pointC = Vector2F(
                ccX * rotR - ccY * rotI + targetPosX, ccY * rotR + ccX * rotI + targetPosY
            )
        )
    }

    override fun transformedBy(offset: Vector2F, rotation: AngleF): Triangle =
        transformedByImpl(offset, ComplexF.fromAngle(rotation))

    override fun transformedBy(offset: Vector2F, rotation: ComplexF): Triangle =
        transformedByImpl(offset, rotation)

    private fun transformedByImpl(offset: Vector2F, rotation: ComplexF, factor: Float): Triangle {
        val (aX: Float, aY: Float) = pointA
        val (bX: Float, bY: Float) = pointB
        val (cX: Float, cY: Float) = pointC
        val centroidX: Float = (aX + bX + cX) * 0.3333333f
        val centroidY: Float = (aY + bY + cY) * 0.3333333f
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
        val addendX: Float = targetPosX * f
        val addendY: Float = targetPosY * f

        return copy(
            pointA = Vector2F(
                (caX * rotR - caY * rotI + targetPosX) * factor + addendX,
                (caY * rotR + caX * rotI + targetPosY) * factor + addendY
            ),
            pointB = Vector2F(
                (cbX * rotR - cbY * rotI + targetPosX) * factor + addendX,
                (cbY * rotR + cbX * rotI + targetPosY) * factor + addendY
            ),
            pointC = Vector2F(
                (ccX * rotR - ccY * rotI + targetPosX) * factor + addendX,
                (ccY * rotR + ccX * rotI + targetPosY) * factor + addendY
            )
        )
    }

    override fun transformedBy(offset: Vector2F, rotation: AngleF, factor: Float): Triangle =
        transformedByImpl(offset, ComplexF.fromAngle(rotation), factor)

    override fun transformedBy(offset: Vector2F, rotation: ComplexF, factor: Float): Triangle =
        transformedByImpl(offset, rotation, factor)

    private fun transformedToImpl(position: Vector2F, orientation: ComplexF): Triangle {
        val (aX: Float, aY: Float) = pointA
        val (bX: Float, bY: Float) = pointB
        val (cX: Float, cY: Float) = pointC
        val centroidX: Float = (aX + bX + cX) * 0.3333333f
        val centroidY: Float = (aY + bY + cY) * 0.3333333f
        val conjugatedStartRot = ComplexF(aX - centroidX, centroidY - aY).normalized
        val (rotR: Float, rotI: Float) = conjugatedStartRot * orientation
        val (pX: Float, pY: Float) = position
        val caX: Float = aX - centroidX
        val caY: Float = aY - centroidY
        val cbX: Float = bX - centroidX
        val cbY: Float = bY - centroidY
        val ccX: Float = cX - centroidX
        val ccY: Float = cY - centroidY

        return copy(
            pointA = Vector2F(caX * rotR - caY * rotI + pX, caY * rotR + caX * rotI + pY),
            pointB = Vector2F(cbX * rotR - cbY * rotI + pX, cbY * rotR + cbX * rotI + pY),
            pointC = Vector2F(ccX * rotR - ccY * rotI + pX, ccY * rotR + ccX * rotI + pY)
        )
    }

    override fun transformedTo(position: Vector2F, orientation: AngleF): Triangle =
        transformedToImpl(position, ComplexF.fromAngle(orientation))

    override fun transformedTo(position: Vector2F, orientation: ComplexF): Triangle =
        transformedToImpl(position, orientation)

    fun interpolated(to: Triangle, by: Float): Triangle = copy(
        pointA = Vector2F.lerp(pointA, to.pointA, by),
        pointB = Vector2F.lerp(pointB, to.pointB, by),
        pointC = Vector2F.lerp(pointC, to.pointC, by)
    )

    fun closestPointTo(point: Vector2F): Vector2F {
        val a: Vector2F = pointA
        val b: Vector2F = pointB
        val c: Vector2F = pointC
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

    operator fun contains(point: Vector2F): Boolean {
        val (aX: Float, aY: Float) = pointA
        val (bX: Float, bY: Float) = pointB
        val (cX: Float, cY: Float) = pointC
        val (pX: Float, pY: Float) = point
        val abp: Boolean = ((pX - aX) * (aY - bY) + (pY - aY) * (bX - aX)) >= 0f
        val bcp: Boolean = ((pX - bX) * (bY - cY) + (pY - bY) * (cX - bX)) >= 0f
        val acp: Boolean = ((pX - cX) * (cY - aY) + (pY - cY) * (aX - cX)) >= 0f

        return (abp == bcp) and (bcp == acp)
    }

    fun pointIterator(): Vector2FIterator = PointIterator(this, index = 0)

    fun copy(
        pointA: Vector2F = this.pointA,
        pointB: Vector2F = this.pointB,
        pointC: Vector2F = this.pointC
    ): Triangle

    operator fun component1(): Vector2F = pointA

    operator fun component2(): Vector2F = pointB

    operator fun component3(): Vector2F = pointC

    private class PointIterator(
        private val triangle: Triangle,
        private var index: Int
    ) : Vector2FIterator() {
        override fun hasNext(): Boolean = index < 3

        override fun nextVector2F(): Vector2F = when (index++) {
            0 -> triangle.pointA
            1 -> triangle.pointB
            2 -> triangle.pointC
            else -> throw NoSuchElementException("${index - 1}")
        }
    }
}