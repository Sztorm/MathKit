package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.AngleF
import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F
import com.sztorm.lowallocmath.Vector2FIterator
import kotlin.math.abs

class MutableTriangle : Triangle, MutableTransformable {
    private var _pointA: Vector2F
    private var _pointB: Vector2F
    private var _pointC: Vector2F
    private var _centroid: Vector2F
    private var _rotation: ComplexF

    constructor(pointA: Vector2F, pointB: Vector2F, pointC: Vector2F) {
        _pointA = pointA
        _pointB = pointB
        _pointC = pointC
        _centroid = (_pointA + _pointB + _pointC) * 0.3333333f
        _rotation = (_pointA - centroid).normalized.toComplexF()
    }

    private constructor(
        pointA: Vector2F,
        pointB: Vector2F,
        pointC: Vector2F,
        centroid: Vector2F,
        rotation: ComplexF
    ) {
        _pointA = pointA
        _pointB = pointB
        _pointC = pointC
        _centroid = centroid
        _rotation = rotation
    }

    override val pointA: Vector2F
        get() = _pointA

    override val pointB: Vector2F
        get() = _pointB

    override val pointC: Vector2F
        get() = _pointC

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

    override val centroid: Vector2F
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

    override val position: Vector2F
        get() = _centroid

    override val rotation: ComplexF
        get() = _rotation

    override fun movedBy(offset: Vector2F) = MutableTriangle(
        _pointA + offset,
        _pointB + offset,
        _pointC + offset,
        _centroid + offset,
        _rotation
    )

    override fun movedTo(position: Vector2F): MutableTriangle {
        val offset: Vector2F = position - _centroid

        return MutableTriangle(
            _pointA + offset,
            _pointB + offset,
            _pointC + offset,
            position,
            _rotation
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

    override fun rotatedBy(angle: AngleF): MutableTriangle = rotatedBy(ComplexF.fromAngle(angle))

    override fun rotatedBy(rotation: ComplexF): MutableTriangle {
        val (cX: Float, cY: Float) = _centroid
        val (startRotR: Float, startRotI: Float) = _rotation
        val (rotR: Float, rotI: Float) = rotation
        val pcaX: Float = _pointA.x - cX
        val pcaY: Float = _pointA.y - cY
        val pcbX: Float = _pointB.x - cX
        val pcbY: Float = _pointB.y - cY
        val pccX: Float = _pointC.x - cX
        val pccY: Float = _pointC.y - cY

        return MutableTriangle(
            Vector2F(pcaX * rotR - pcaY * rotI + cX, pcaY * rotR + pcaX * rotI + cY),
            Vector2F(pcbX * rotR - pcbY * rotI + cX, pcbY * rotR + pcbX * rotI + cY),
            Vector2F(pccX * rotR - pccY * rotI + cX, pccY * rotR + pccX * rotI + cY),
            _centroid,
            ComplexF(
                startRotR * rotR - startRotI * rotI,
                startRotI * rotR + startRotR * rotI
            )
        )
    }

    override fun rotatedTo(angle: AngleF): MutableTriangle = rotatedTo(ComplexF.fromAngle(angle))

    override fun rotatedTo(rotation: ComplexF): MutableTriangle {
        val (cX: Float, cY: Float) = _centroid
        val (rotR: Float, rotI: Float) = ComplexF.conjugate(_rotation) * rotation
        val pcaX: Float = _pointA.x - cX
        val pcaY: Float = _pointA.y - cY
        val pcbX: Float = _pointB.x - cX
        val pcbY: Float = _pointB.y - cY
        val pccX: Float = _pointC.x - cX
        val pccY: Float = _pointC.y - cY

        return MutableTriangle(
            Vector2F(pcaX * rotR - pcaY * rotI + cX, pcaY * rotR + pcaX * rotI + cY),
            Vector2F(pcbX * rotR - pcbY * rotI + cX, pcbY * rotR + pcbX * rotI + cY),
            Vector2F(pccX * rotR - pccY * rotI + cX, pccY * rotR + pccX * rotI + cY),
            _centroid,
            rotation
        )
    }

    override fun rotatedAroundPointBy(point: Vector2F, angle: AngleF): MutableTriangle =
        rotatedAroundPointBy(point, ComplexF.fromAngle(angle))

    override fun rotatedAroundPointBy(point: Vector2F, rotation: ComplexF): MutableTriangle =
        TODO()

    override fun rotatedAroundPointTo(point: Vector2F, angle: AngleF): MutableTriangle =
        rotatedAroundPointTo(point, ComplexF.fromAngle(angle))

    override fun rotatedAroundPointTo(point: Vector2F, rotation: ComplexF): MutableTriangle =
        TODO()

    override fun rotateBy(angle: AngleF) = rotateBy(ComplexF.fromAngle(angle))

    override fun rotateBy(rotation: ComplexF) {
        val (cX: Float, cY: Float) = _centroid
        val (startRotR: Float, startRotI: Float) = _rotation
        val (rotR: Float, rotI: Float) = rotation
        val pcaX: Float = _pointA.x - cX
        val pcaY: Float = _pointA.y - cY
        val pcbX: Float = _pointB.x - cX
        val pcbY: Float = _pointB.y - cY
        val pccX: Float = _pointC.x - cX
        val pccY: Float = _pointC.y - cY
        _pointA = Vector2F(pcaX * rotR - pcaY * rotI + cX, pcaY * rotR + pcaX * rotI + cY)
        _pointB = Vector2F(pcbX * rotR - pcbY * rotI + cX, pcbY * rotR + pcbX * rotI + cY)
        _pointC = Vector2F(pccX * rotR - pccY * rotI + cX, pccY * rotR + pccX * rotI + cY)
        _rotation = ComplexF(
            startRotR * rotR - startRotI * rotI, startRotI * rotR + startRotR * rotI
        )
    }

    override fun rotateTo(angle: AngleF) = rotateTo(ComplexF.fromAngle(angle))

    override fun rotateTo(rotation: ComplexF) {
        val (cX: Float, cY: Float) = _centroid
        val (rotR: Float, rotI: Float) = ComplexF.conjugate(_rotation) * rotation
        val pcaX: Float = _pointA.x - cX
        val pcaY: Float = _pointA.y - cY
        val pcbX: Float = _pointB.x - cX
        val pcbY: Float = _pointB.y - cY
        val pccX: Float = _pointC.x - cX
        val pccY: Float = _pointC.y - cY
        _pointA = Vector2F(pcaX * rotR - pcaY * rotI + cX, pcaY * rotR + pcaX * rotI + cY)
        _pointB = Vector2F(pcbX * rotR - pcbY * rotI + cX, pcbY * rotR + pcbX * rotI + cY)
        _pointC = Vector2F(pccX * rotR - pccY * rotI + cX, pccY * rotR + pccX * rotI + cY)
        _rotation = rotation
    }

    override fun rotateAroundPointBy(point: Vector2F, angle: AngleF) =
        rotateAroundPointBy(point, ComplexF.fromAngle(angle))

    override fun rotateAroundPointBy(point: Vector2F, rotation: ComplexF) =
        TODO()

    override fun rotateAroundPointTo(point: Vector2F, angle: AngleF) =
        rotateAroundPointTo(point, ComplexF.fromAngle(angle))

    override fun rotateAroundPointTo(point: Vector2F, rotation: ComplexF) =
        TODO()

    override fun scaledBy(factor: Float): MutableTriangle {
        val f: Float = 1f - factor
        val addendX: Float = _centroid.x * f
        val addendY: Float = _centroid.y * f

        return MutableTriangle(
            Vector2F(_pointA.x * factor + addendX, _pointA.y * factor + addendY),
            Vector2F(_pointB.x * factor + addendX, _pointB.y * factor + addendY),
            Vector2F(_pointC.x * factor + addendX, _pointC.y * factor + addendY),
            _centroid,
            _rotation
        )
    }

    override fun scaleBy(factor: Float) {
        val f: Float = 1f - factor
        val addendX: Float = _centroid.x * f
        val addendY: Float = _centroid.y * f
        _pointA = Vector2F(_pointA.x * factor + addendX, _pointA.y * factor + addendY)
        _pointB = Vector2F(_pointB.x * factor + addendX, _pointB.y * factor + addendY)
        _pointC = Vector2F(_pointC.x * factor + addendX, _pointC.y * factor + addendY)
    }

    override fun transformedBy(offset: Vector2F, angle: AngleF): MutableTriangle =
        transformedBy(offset, ComplexF.fromAngle(angle))

    override fun transformedBy(offset: Vector2F, rotation: ComplexF): MutableTriangle {
        val (oX: Float, oY: Float) = offset
        val (cX: Float, cY: Float) = _centroid
        val (startRotR: Float, startRotI: Float) = _rotation
        val (rotR: Float, rotI: Float) = rotation
        val pcaX: Float = _pointA.x - cX
        val pcaY: Float = _pointA.y - cY
        val pcbX: Float = _pointB.x - cX
        val pcbY: Float = _pointB.y - cY
        val pccX: Float = _pointC.x - cX
        val pccY: Float = _pointC.y - cY
        val newPosX: Float = cX + oX
        val newPosY: Float = cY + oY

        return MutableTriangle(
            Vector2F(
                pcaX * rotR - pcaY * rotI + newPosX, pcaY * rotR + pcaX * rotI + newPosY
            ),
            Vector2F(
                pcbX * rotR - pcbY * rotI + newPosX, pcbY * rotR + pcbX * rotI + newPosY
            ),
            Vector2F(
                pccX * rotR - pccY * rotI + newPosX, pccY * rotR + pccX * rotI + newPosY
            ),
            Vector2F(newPosX, newPosY),
            ComplexF(
                startRotR * rotR - startRotI * rotI,
                startRotI * rotR + startRotR * rotI
            )
        )
    }

    override fun transformedBy(offset: Vector2F, angle: AngleF, factor: Float): MutableTriangle =
        transformedBy(offset, ComplexF.fromAngle(angle), factor)

    override fun transformedBy(
        offset: Vector2F, rotation: ComplexF, factor: Float
    ): MutableTriangle {
        val (oX: Float, oY: Float) = offset
        val (cX: Float, cY: Float) = _centroid
        val (startRotR: Float, startRotI: Float) = _rotation
        val (rotR: Float, rotI: Float) = rotation
        val pcaX: Float = _pointA.x - cX
        val pcaY: Float = _pointA.y - cY
        val pcbX: Float = _pointB.x - cX
        val pcbY: Float = _pointB.y - cY
        val pccX: Float = _pointC.x - cX
        val pccY: Float = _pointC.y - cY
        val newPosX: Float = cX + oX
        val newPosY: Float = cY + oY
        val f: Float = 1f - factor
        val addendX: Float = newPosX * f
        val addendY: Float = newPosY * f

        return MutableTriangle(
            Vector2F(
                (pcaX * rotR - pcaY * rotI + newPosX) * factor + addendX,
                (pcaY * rotR + pcaX * rotI + newPosY) * factor + addendY
            ),
            Vector2F(
                (pcbX * rotR - pcbY * rotI + newPosX) * factor + addendX,
                (pcbY * rotR + pcbX * rotI + newPosY) * factor + addendY
            ),
            Vector2F(
                (pccX * rotR - pccY * rotI + newPosX) * factor + addendX,
                (pccY * rotR + pccX * rotI + newPosY) * factor + addendY
            ),
            Vector2F(newPosX, newPosY),
            ComplexF(
                startRotR * rotR - startRotI * rotI,
                startRotI * rotR + startRotR * rotI
            )
        )
    }

    override fun transformedTo(position: Vector2F, angle: AngleF): MutableTriangle =
        transformedTo(position, ComplexF.fromAngle(angle))

    override fun transformedTo(position: Vector2F, rotation: ComplexF): MutableTriangle {
        val (pX: Float, pY: Float) = position
        val (cX: Float, cY: Float) = _centroid
        val (rotR: Float, rotI: Float) = ComplexF.conjugate(_rotation) * rotation
        val pcaX: Float = _pointA.x - cX
        val pcaY: Float = _pointA.y - cY
        val pcbX: Float = _pointB.x - cX
        val pcbY: Float = _pointB.y - cY
        val pccX: Float = _pointC.x - cX
        val pccY: Float = _pointC.y - cY

        return MutableTriangle(
            Vector2F(pcaX * rotR - pcaY * rotI + pX, pcaY * rotR + pcaX * rotI + pY),
            Vector2F(pcbX * rotR - pcbY * rotI + pX, pcbY * rotR + pcbX * rotI + pY),
            Vector2F(pccX * rotR - pccY * rotI + pX, pccY * rotR + pccX * rotI + pY),
            position,
            rotation
        )
    }

    override fun transformBy(offset: Vector2F, angle: AngleF) =
        transformBy(offset, ComplexF.fromAngle(angle))

    override fun transformBy(offset: Vector2F, rotation: ComplexF) {
        val (oX: Float, oY: Float) = offset
        val (cX: Float, cY: Float) = _centroid
        val (startRotR: Float, startRotI: Float) = _rotation
        val (rotR: Float, rotI: Float) = rotation
        val pcaX: Float = _pointA.x - cX
        val pcaY: Float = _pointA.y - cY
        val pcbX: Float = _pointB.x - cX
        val pcbY: Float = _pointB.y - cY
        val pccX: Float = _pointC.x - cX
        val pccY: Float = _pointC.y - cY
        val newPosX: Float = cX + oX
        val newPosY: Float = cY + oY
        _pointA = Vector2F(
            pcaX * rotR - pcaY * rotI + newPosX, pcaY * rotR + pcaX * rotI + newPosY
        )
        _pointB = Vector2F(
            pcbX * rotR - pcbY * rotI + newPosX, pcbY * rotR + pcbX * rotI + newPosY
        )
        _pointC = Vector2F(
            pccX * rotR - pccY * rotI + newPosX, pccY * rotR + pccX * rotI + newPosY
        )
        _centroid = Vector2F(newPosX, newPosY)
        _rotation = ComplexF(
            startRotR * rotR - startRotI * rotI, startRotI * rotR + startRotR * rotI
        )
    }

    override fun transformBy(offset: Vector2F, angle: AngleF, factor: Float) =
        transformBy(offset, ComplexF.fromAngle(angle), factor)

    override fun transformBy(offset: Vector2F, rotation: ComplexF, factor: Float) {
        val (oX: Float, oY: Float) = offset
        val (cX: Float, cY: Float) = _centroid
        val (startRotR: Float, startRotI: Float) = _rotation
        val (rotR: Float, rotI: Float) = rotation
        val pcaX: Float = _pointA.x - cX
        val pcaY: Float = _pointA.y - cY
        val pcbX: Float = _pointB.x - cX
        val pcbY: Float = _pointB.y - cY
        val pccX: Float = _pointC.x - cX
        val pccY: Float = _pointC.y - cY
        val newPosX: Float = cX + oX
        val newPosY: Float = cY + oY
        val f: Float = 1f - factor
        val addendX: Float = newPosX * f
        val addendY: Float = newPosY * f
        _pointA = Vector2F(
            (pcaX * rotR - pcaY * rotI + newPosX) * factor + addendX,
            (pcaY * rotR + pcaX * rotI + newPosY) * factor + addendY
        )
        _pointB = Vector2F(
            (pcbX * rotR - pcbY * rotI + newPosX) * factor + addendX,
            (pcbY * rotR + pcbX * rotI + newPosY) * factor + addendY
        )
        _pointC = Vector2F(
            (pccX * rotR - pccY * rotI + newPosX) * factor + addendX,
            (pccY * rotR + pccX * rotI + newPosY) * factor + addendY
        )
        _centroid = Vector2F(newPosX, newPosY)
        _rotation = ComplexF(
            startRotR * rotR - startRotI * rotI, startRotI * rotR + startRotR * rotI
        )
    }

    override fun transformTo(position: Vector2F, angle: AngleF) =
        transformTo(position, ComplexF.fromAngle(angle))

    override fun transformTo(position: Vector2F, rotation: ComplexF) {
        val (pX: Float, pY: Float) = position
        val (cX: Float, cY: Float) = _centroid
        val (rotR: Float, rotI: Float) = ComplexF.conjugate(_rotation) * rotation
        val pcaX: Float = _pointA.x - cX
        val pcaY: Float = _pointA.y - cY
        val pcbX: Float = _pointB.x - cX
        val pcbY: Float = _pointB.y - cY
        val pccX: Float = _pointC.x - cX
        val pccY: Float = _pointC.y - cY
        _pointA = Vector2F(pcaX * rotR - pcaY * rotI + pX, pcaY * rotR + pcaX * rotI + pY)
        _pointB = Vector2F(pcbX * rotR - pcbY * rotI + pX, pcbY * rotR + pcbX * rotI + pY)
        _pointC = Vector2F(pccX * rotR - pccY * rotI + pX, pccY * rotR + pccX * rotI + pY)
        _centroid = position
        _rotation = rotation
    }

    override fun pointIterator(): Vector2FIterator = PointIterator(this, index = 0)

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
        val (pX: Float, pY: Float) = point
        val (aX: Float, aY: Float) = _pointA
        val (bX: Float, bY: Float) = _pointB
        val (cX: Float, cY: Float) = _pointC
        val abp: Boolean = ((pX - aX) * (aY - bY) + (pY - aY) * (bX - aX)) >= 0f
        val bcp: Boolean = ((pX - bX) * (bY - cY) + (pY - bY) * (cX - bX)) >= 0f
        val acp: Boolean = ((pX - cX) * (cY - aY) + (pY - cY) * (aX - cX)) >= 0f

        return (abp == bcp) and (bcp == acp)
    }

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