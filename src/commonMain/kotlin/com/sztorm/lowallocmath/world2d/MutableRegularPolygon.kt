@file:Suppress("PropertyName", "ReplaceManualRangeWithIndicesCalls")

package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.*
import kotlin.math.*

class MutableRegularPolygon : RegularPolygon, MutableTransformable {
    internal var _center: Vector2F
    internal var _rotation: ComplexF
    internal var _sideLength: Float
    internal var _points: Vector2FArray
    private var _circumradius: Float
    private var _inradius: Float

    constructor(center: Vector2F, rotation: ComplexF, sideLength: Float, sideCount: Int) {
        val points: Vector2FArray
        val halfSideLength: Float = 0.5f * sideLength

        if (sideCount < 3) {
            if (sideCount < 2) {
                throw IllegalArgumentException(
                    "Minimum required side count to create a polygon is 2."
                )
            }
            points = Vector2FArray(sideCount)
            points[0] = Vector2F(halfSideLength, 0f)
            points[1] = Vector2F(-halfSideLength, 0f)
            _inradius = 0f
            _circumradius = halfSideLength
        } else {
            points = Vector2FArray(sideCount)
            val isSideCountEven: Boolean = sideCount and 1 == 0
            val halfCount: Int = sideCount / 2
            val exteriorAngle: Float = (2.0 * PI).toFloat() / sideCount
            val halfExteriorAngle: Float = exteriorAngle * 0.5f
            val exteriorRotation = ComplexF(cos(exteriorAngle), sin(exteriorAngle))

            if (isSideCountEven) {
                val inradius: Float = halfSideLength / tan(halfExteriorAngle)
                points[0] = Vector2F(halfSideLength, inradius)
                points[1] = Vector2F(-halfSideLength, inradius)

                for (i in 2..halfCount) {
                    points[i] = (exteriorRotation * points[i - 1].toComplexF()).toVector2F()
                }
                for (i in halfCount + 1 until sideCount) {
                    val oppositePoint: Vector2F = points[sideCount - i + 1]
                    points[i] = Vector2F(-oppositePoint.x, oppositePoint.y)
                }
                _inradius = inradius
                _circumradius = halfSideLength / sin(halfExteriorAngle)
            } else {
                val circumradius: Float = halfSideLength / sin(halfExteriorAngle)
                points[0] = Vector2F(0f, circumradius)

                for (i in 1..halfCount) {
                    points[i] = (exteriorRotation * points[i - 1].toComplexF()).toVector2F()
                }
                for (i in (halfCount + 1) until sideCount) {
                    val oppositePoint: Vector2F = points[sideCount - i]
                    points[i] = Vector2F(-oppositePoint.x, oppositePoint.y)
                }
                _inradius = halfSideLength / tan(halfExteriorAngle)
                _circumradius = circumradius
            }
        }
        for (i in 0 until sideCount) {
            points[i] = center + (rotation * points[i].toComplexF()).toVector2F()
        }
        _center = center
        _rotation = rotation
        _sideLength = sideLength
        _points = points
    }

    private constructor(
        center: Vector2F,
        rotation: ComplexF,
        sideLength: Float,
        points: Vector2FArray,
        circumradius: Float,
        inradius: Float,
    ) {
        _center = center
        _rotation = rotation
        _sideLength = sideLength
        _points = points
        _circumradius = circumradius
        _inradius = inradius
    }

    constructor(regularTriangle: MutableRegularTriangle) {
        val points = Vector2FArray(3)
        points[0] = regularTriangle._pointA
        points[1] = regularTriangle._pointB
        points[2] = regularTriangle._pointC
        _center = regularTriangle._center
        _rotation = regularTriangle._rotation
        _sideLength = regularTriangle._sideLength
        _points = points
        _circumradius = regularTriangle.circumradius
        _inradius = regularTriangle.inradius
    }

    constructor(square: MutableSquare) {
        val points = Vector2FArray(4)
        points[0] = square._pointA
        points[1] = square._pointB
        points[2] = square._pointC
        points[3] = square._pointD
        _center = square._center
        _rotation = square._rotation
        _sideLength = square._sideLength
        _points = points
        _circumradius = square.circumradius
        _inradius = square.inradius
    }

    override val center: Vector2F
        get() = _center

    override val rotation: ComplexF
        get() = _rotation

    override val sideLength: Float
        get() = _sideLength

    override val sideCount: Int
        get() = _points.size

    override val circumradius: Float
        get() = _circumradius

    override val inradius: Float
        get() = _inradius

    override val points: Vector2FList
        get() = _points.asList()

    override val area: Float
        get() = 0.5f * sideCount.toFloat() * _sideLength * _inradius

    override val perimeter: Float
        get() = sideCount * _sideLength

    override val interiorAngle: AngleF
        get() = AngleF(PI.toFloat() - (2.0 * PI).toFloat() / sideCount.toFloat())

    override val exteriorAngle: AngleF
        get() = AngleF((2.0 * PI).toFloat() / sideCount)

    override val position: Vector2F
        get() = _center

    override fun movedBy(offset: Vector2F): MutableRegularPolygon {
        val points: Vector2FArray = _points.copyOf()

        for (i in 0 until points.size) {
            points[i] += offset
        }
        return MutableRegularPolygon(
            _center + offset,
            _rotation,
            _sideLength,
            points,
            _circumradius,
            _inradius
        )
    }

    override fun movedTo(position: Vector2F): MutableRegularPolygon {
        val points: Vector2FArray = _points.copyOf()
        val offset: Vector2F = position - _center

        for (i in 0 until points.size) {
            points[i] += offset
        }
        return MutableRegularPolygon(
            position,
            _rotation,
            _sideLength,
            points,
            _circumradius,
            _inradius
        )
    }

    override fun moveBy(offset: Vector2F) {
        val points: Vector2FArray = _points
        _center += offset

        for (i in 0 until points.size) {
            points[i] += offset
        }
    }

    override fun moveTo(position: Vector2F) {
        val points: Vector2FArray = _points
        val offset: Vector2F = position - _center
        _center = position

        for (i in 0 until points.size) {
            points[i] += offset
        }
    }

    override fun rotatedBy(angle: AngleF): MutableRegularPolygon =
        rotatedBy(ComplexF.fromAngle(angle))

    override fun rotatedBy(rotation: ComplexF): MutableRegularPolygon {
        val (cX: Float, cY: Float) = _center
        val (rotR: Float, rotI: Float) = rotation
        val points: Vector2FArray = _points.copyOf()

        for (i in 0 until points.size) {
            val pX: Float = points[i].x - cX
            val pY: Float = points[i].y - cY
            points[i] = Vector2F(pX * rotR - pY * rotI + cX, pY * rotR + pX * rotI + cY)
        }
        return MutableRegularPolygon(
            _center,
            _rotation * rotation,
            _sideLength,
            points,
            _circumradius,
            _inradius
        )
    }

    override fun rotatedTo(angle: AngleF): MutableRegularPolygon =
        rotatedTo(ComplexF.fromAngle(angle))

    override fun rotatedTo(rotation: ComplexF): MutableRegularPolygon {
        val (cX: Float, cY: Float) = _center
        val (rotR: Float, rotI: Float) = rotation * ComplexF.conjugate(_rotation)
        val points: Vector2FArray = _points.copyOf()

        for (i in 0 until points.size) {
            val pX: Float = points[i].x - cX
            val pY: Float = points[i].y - cY
            points[i] = Vector2F(pX * rotR - pY * rotI + cX, pY * rotR + pX * rotI + cY)
        }
        return MutableRegularPolygon(
            _center,
            rotation,
            _sideLength,
            points,
            _circumradius,
            _inradius
        )
    }

    override fun rotateBy(angle: AngleF) = rotateBy(ComplexF.fromAngle(angle))

    override fun rotateBy(rotation: ComplexF) {
        val (cX: Float, cY: Float) = _center
        val (rotR: Float, rotI: Float) = rotation
        val points: Vector2FArray = _points

        for (i in 0 until points.size) {
            val pX: Float = points[i].x - cX
            val pY: Float = points[i].y - cY
            points[i] = Vector2F(pX * rotR - pY * rotI + cX, pY * rotR + pX * rotI + cY)
        }
        _rotation *= rotation
    }

    override fun rotateTo(angle: AngleF) = rotateTo(ComplexF.fromAngle(angle))

    override fun rotateTo(rotation: ComplexF) {
        val (cX: Float, cY: Float) = _center
        val (rotR: Float, rotI: Float) = rotation * ComplexF.conjugate(_rotation)
        val points: Vector2FArray = _points

        for (i in 0 until points.size) {
            val pX: Float = points[i].x - cX
            val pY: Float = points[i].y - cY
            points[i] = Vector2F(pX * rotR - pY * rotI + cX, pY * rotR + pX * rotI + cY)
        }
        _rotation = rotation
    }

    override fun scaledBy(factor: Float): MutableRegularPolygon {
        val points: Vector2FArray = _points.copyOf()
        val center: Vector2F = _center
        val addendX: Float = center.x * (1f - factor)
        val addendY: Float = center.y * (1f - factor)

        for (i in 0 until points.size) {
            val (pX: Float, pY: Float) = points[i]

            points[i] = Vector2F(addendX + pX * factor, addendY + pY * factor)
        }
        return MutableRegularPolygon(
            center,
            _rotation,
            _sideLength * factor,
            points,
            _circumradius * factor,
            _inradius * factor
        )
    }

    override fun scaleBy(factor: Float) {
        val points: Vector2FArray = _points
        val center: Vector2F = _center
        val addendX: Float = center.x * (1f - factor)
        val addendY: Float = center.y * (1f - factor)

        for (i in 0 until points.size) {
            val (pX: Float, pY: Float) = points[i]

            points[i] = Vector2F(addendX + pX * factor, addendY + pY * factor)
        }
        _sideLength *= factor
        _circumradius *= factor
        _inradius *= factor
    }

    override fun transformedBy(offset: Vector2F, angle: AngleF): MutableRegularPolygon =
        transformedBy(offset, ComplexF.fromAngle(angle))

    override fun transformedBy(offset: Vector2F, rotation: ComplexF): MutableRegularPolygon {
        val points: Vector2FArray = _points.copyOf()
        val (pcX: Float, pcY: Float) = _center
        val cX: Float = pcX + offset.x
        val cY: Float = pcY + offset.y
        val (rotR: Float, rotI: Float) = rotation

        for (i in 0 until points.size) {
            val pX: Float = points[i].x - pcX
            val pY: Float = points[i].y - pcY
            points[i] = Vector2F(pX * rotR - pY * rotI + cX, pY * rotR + pX * rotI + cY)
        }
        return MutableRegularPolygon(
            Vector2F(cX, cY),
            _rotation * rotation,
            _sideLength,
            points,
            _circumradius,
            _inradius
        )
    }

    override fun transformedBy(
        offset: Vector2F, angle: AngleF, factor: Float
    ): MutableRegularPolygon = transformedBy(offset, ComplexF.fromAngle(angle), factor)

    override fun transformedBy(
        offset: Vector2F, rotation: ComplexF, factor: Float
    ): MutableRegularPolygon {
        val points: Vector2FArray = _points.copyOf()
        val (pcX: Float, pcY: Float) = _center
        val cX: Float = pcX + offset.x
        val cY: Float = pcY + offset.y
        val (rotR: Float, rotI: Float) = rotation
        val addendX: Float = cX * (1f - factor)
        val addendY: Float = cY * (1f - factor)

        for (i in 0 until points.size) {
            val p1X: Float = points[i].x - pcX
            val p1Y: Float = points[i].y - pcY
            val p2X: Float = p1X * rotR - p1Y * rotI + cX
            val p2Y: Float = p1Y * rotR + p1X * rotI + cY
            points[i] = Vector2F(addendX + p2X * factor, addendY + p2Y * factor)
        }
        return MutableRegularPolygon(
            Vector2F(cX, cY),
            _rotation * rotation,
            _sideLength * factor,
            points,
            _circumradius * factor,
            _inradius * factor
        )
    }

    override fun transformedTo(position: Vector2F, angle: AngleF): MutableRegularPolygon =
        transformedTo(position, ComplexF.fromAngle(angle))

    override fun transformedTo(position: Vector2F, rotation: ComplexF): MutableRegularPolygon {
        val points: Vector2FArray = _points.copyOf()
        val (pcX: Float, pcY: Float) = _center
        val (cX: Float, cY: Float) = position
        val (rotR: Float, rotI: Float) = rotation * ComplexF.conjugate(_rotation)

        for (i in 0 until points.size) {
            val pX: Float = points[i].x - pcX
            val pY: Float = points[i].y - pcY
            points[i] = Vector2F(pX * rotR - pY * rotI + cX, pY * rotR + pX * rotI + cY)
        }
        return MutableRegularPolygon(
            position,
            rotation,
            _sideLength,
            points,
            _circumradius,
            _inradius
        )
    }

    override fun transformBy(offset: Vector2F, angle: AngleF) =
        transformBy(offset, ComplexF.fromAngle(angle))

    override fun transformBy(offset: Vector2F, rotation: ComplexF) {
        val points: Vector2FArray = _points
        val (pcX: Float, pcY: Float) = _center
        val cX: Float = pcX + offset.x
        val cY: Float = pcY + offset.y
        val (rotR: Float, rotI: Float) = rotation

        for (i in 0 until points.size) {
            val pX: Float = points[i].x - pcX
            val pY: Float = points[i].y - pcY
            points[i] = Vector2F(pX * rotR - pY * rotI + cX, pY * rotR + pX * rotI + cY)
        }
        _center = Vector2F(cX, cY)
        _rotation *= rotation
    }

    override fun transformBy(offset: Vector2F, angle: AngleF, factor: Float) =
        transformBy(offset, ComplexF.fromAngle(angle), factor)

    override fun transformBy(offset: Vector2F, rotation: ComplexF, factor: Float) {
        val points: Vector2FArray = _points
        val (pcX: Float, pcY: Float) = _center
        val cX: Float = pcX + offset.x
        val cY: Float = pcY + offset.y
        val (rotR: Float, rotI: Float) = rotation
        val addendX: Float = cX * (1f - factor)
        val addendY: Float = cY * (1f - factor)

        for (i in 0 until points.size) {
            val p1X: Float = points[i].x - pcX
            val p1Y: Float = points[i].y - pcY
            val p2X: Float = p1X * rotR - p1Y * rotI + cX
            val p2Y: Float = p1Y * rotR + p1X * rotI + cY
            points[i] = Vector2F(addendX + p2X * factor, addendY + p2Y * factor)
        }
        _center = Vector2F(cX, cY)
        _rotation *= rotation
        _sideLength *= factor
        _circumradius *= factor
        _inradius *= factor
    }

    override fun transformTo(position: Vector2F, angle: AngleF) =
        transformTo(position, ComplexF.fromAngle(angle))

    override fun transformTo(position: Vector2F, rotation: ComplexF) {
        val points: Vector2FArray = _points
        val (pcX: Float, pcY: Float) = _center
        val (cX: Float, cY: Float) = position
        val (rotR: Float, rotI: Float) = rotation * ComplexF.conjugate(_rotation)

        for (i in 0 until points.size) {
            val pX: Float = points[i].x - pcX
            val pY: Float = points[i].y - pcY
            points[i] = Vector2F(pX * rotR - pY * rotI + cX, pY * rotR + pX * rotI + cY)
        }
        _center = position
        _rotation = rotation
    }

    override fun closestPointTo(point: Vector2F): Vector2F {
        val sideCount: Int = this.sideCount
        val center: Vector2F = _center
        val rotation: ComplexF = _rotation
        val rotConj = ComplexF.conjugate(rotation)
        val halfSideLength: Float = _sideLength * 0.5f

        if (sideCount == 2) {
            val (x, _) = rotConj * (point - center).toComplexF()

            return when {
                x > halfSideLength -> _points[0]
                x < -halfSideLength -> _points[1]
                else -> center + rotation.toVector2F() * x
            }
        }
        val rot90Deg = ComplexF(0f, 1f)
        val rotNeg90Deg = ComplexF(0f, -1f)
        val inradius: Float = _inradius
        val fullAngle = (2.0 * PI).toFloat()
        val exteriorAngle: Float = fullAngle / sideCount
        val p1: ComplexF = rotConj * rotNeg90Deg * (point - center).toComplexF()
        val p1Angle: Float = atan2(p1.imaginary, p1.real) + 0.001f
        val p1AnglePositive: Float =
            if (p1Angle < 0) p1Angle + fullAngle
            else p1Angle
        val index: Int = (p1AnglePositive / exteriorAngle).toInt()
        val rot1 = ComplexF.fromAngle(
            AngleF((sideCount and 1) * (-exteriorAngle * 0.5f) + -exteriorAngle * index)
        )
        val rot1Conj = ComplexF.conjugate(rot1)
        val p2: ComplexF = rot1 * rot90Deg * p1
        val (p2X, p2Y) = p2

        return when {
            p2X < -halfSideLength ->
                center + (rot1Conj * rotation * ComplexF(-halfSideLength, inradius)).toVector2F()

            p2X > halfSideLength ->
                center + (rot1Conj * rotation * ComplexF(halfSideLength, inradius)).toVector2F()

            p2Y > inradius ->
                center + (rot1Conj * rotation * ComplexF(p2X, inradius)).toVector2F()

            else -> point
        }
    }

    override operator fun contains(point: Vector2F): Boolean {
        val sideCount: Int = this.sideCount
        val rotConj = ComplexF.conjugate(_rotation)
        val halfSideLength: Float = _sideLength * 0.5f
        val center: Vector2F = _center

        if (sideCount == 2) {
            val (x, _) = rotConj * (point - center).toComplexF()

            return if ((x > halfSideLength) or (x < -halfSideLength)) false
            else {
                val closestPoint = center + rotation.toVector2F() * x

                return closestPoint.isApproximately(point)
            }
        }
        val rot90Deg = ComplexF(0f, 1f)
        val rotNeg90Deg = ComplexF(0f, -1f)
        val inradius: Float = _inradius
        val fullAngle = (2.0 * PI).toFloat()
        val exteriorAngle: Float = fullAngle / sideCount
        val p1: ComplexF = rotConj * rotNeg90Deg * (point - center).toComplexF()
        val p1Angle: Float = atan2(p1.imaginary, p1.real) + 0.001f
        val p1AnglePositive: Float =
            if (p1Angle < 0) p1Angle + fullAngle
            else p1Angle
        val index: Int = (p1AnglePositive / exteriorAngle).toInt()
        val rot1 = ComplexF.fromAngle(
            AngleF((sideCount and 1) * (-exteriorAngle * 0.5f) + -exteriorAngle * index)
        )
        val p2: ComplexF = rot1 * rot90Deg * p1
        val (p2X, p2Y) = p2

        return (p2X >= -halfSideLength) and (p2X <= halfSideLength) and (p2Y <= inradius)
    }

    override fun pointIterator(): Vector2FIterator = _points.iterator()

    override fun copy(center: Vector2F, rotation: ComplexF, sideLength: Float, sideCount: Int) =
        MutableRegularPolygon(center, rotation, sideLength, sideCount)

    override fun equals(other: Any?): Boolean = other is RegularPolygon &&
            _center == other.center &&
            _rotation == other.rotation &&
            _sideLength == other.sideLength &&
            sideCount == other.sideCount

    fun equals(other: MutableRegularPolygon): Boolean =
        _center == other._center &&
                _rotation == other._rotation &&
                _sideLength == other._sideLength &&
                sideCount == other.sideCount

    override fun hashCode(): Int {
        val centerHash: Int = center.hashCode()
        val rotationHash: Int = rotation.hashCode()
        val sideLengthHash: Int = sideLength.hashCode()
        val sideCountHash: Int = sideCount.hashCode()

        return centerHash * 29791 + rotationHash * 961 + sideLengthHash * 31 + sideCountHash
    }

    override fun toString() =
        StringBuilder("RegularPolygon(center=").append(_center)
            .append(", rotation=").append(_rotation)
            .append(", sideLength=").append(_sideLength)
            .append(", sideCount=").append(sideCount).append(")")
            .toString()

    fun toMutableRegularTriangleOrNull(): MutableRegularTriangle? =
        if (_points.size == 3) MutableRegularTriangle(this)
        else null

    override fun toRegularTriangleOrNull(): RegularTriangle? = toMutableRegularTriangleOrNull()

    fun toMutableSquareOrNull(): MutableSquare? =
        if (_points.size == 4) MutableSquare(this)
        else null

    override fun toSquareOrNull(): Square? = toMutableSquareOrNull()

    override operator fun component1(): Vector2F = _center

    override operator fun component2(): ComplexF = _rotation

    override operator fun component3(): Float = _sideLength

    override operator fun component4(): Int = sideCount
}