@file:Suppress("PropertyName", "ReplaceManualRangeWithIndicesCalls")

package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.*
import kotlin.math.*

class MutableRegularPolygon : RegularPolygon, MutableTransformable {
    internal var _center: Vector2F
    internal var _orientation: ComplexF
    internal var _sideLength: Float
    internal var _points: Vector2FArray
    private var _inradius: Float
    private var _circumradius: Float

    constructor(center: Vector2F, orientation: ComplexF, sideLength: Float, sideCount: Int) {
        throwWhenSideLengthValueIsIllegal(sideLength)

        if (sideCount < 3) {
            throwWhenSideCountValueIsIllegal(sideCount)
            val (oR: Float, oI: Float) = orientation
            val halfSideLength: Float = 0.5f * sideLength
            val offset = Vector2F(oR * halfSideLength, oI * halfSideLength)
            val points = Vector2FArray(sideCount)
            points[0] = center + offset
            points[1] = center - offset
            _center = center
            _orientation = orientation
            _sideLength = sideLength
            _points = points
            _inradius = 0f
            _circumradius = halfSideLength
            return
        }
        val points = Vector2FArray(sideCount)
        val halfSideLength: Float = 0.5f * sideLength
        val isSideCountEven: Boolean = sideCount and 1 == 0
        val halfCount: Int = sideCount / 2
        val exteriorAngle: Float = (2.0 * PI).toFloat() / sideCount
        val halfExteriorAngle: Float = exteriorAngle * 0.5f
        val exteriorRotation = ComplexF(cos(exteriorAngle), sin(exteriorAngle))
        val inradius: Float
        val circumradius: Float

        if (isSideCountEven) {
            inradius = halfSideLength / tan(halfExteriorAngle)
            circumradius = halfSideLength / sin(halfExteriorAngle)
            points[0] = Vector2F(halfSideLength, inradius)
            points[1] = Vector2F(-halfSideLength, inradius)

            for (i in 2..halfCount) {
                points[i] = (exteriorRotation * points[i - 1].toComplexF()).toVector2F()
            }
            for (i in halfCount + 1 until sideCount) {
                val oppositePoint: Vector2F = points[sideCount - i + 1]
                points[i] = Vector2F(-oppositePoint.x, oppositePoint.y)
            }
        } else {
            inradius = halfSideLength / tan(halfExteriorAngle)
            circumradius = halfSideLength / sin(halfExteriorAngle)
            points[0] = Vector2F(0f, circumradius)

            for (i in 1..halfCount) {
                points[i] = (exteriorRotation * points[i - 1].toComplexF()).toVector2F()
            }
            for (i in (halfCount + 1) until sideCount) {
                val oppositePoint: Vector2F = points[sideCount - i]
                points[i] = Vector2F(-oppositePoint.x, oppositePoint.y)
            }
        }
        for (i in 0 until sideCount) {
            points[i] = center + (orientation * points[i].toComplexF()).toVector2F()
        }
        _center = center
        _orientation = orientation
        _sideLength = sideLength
        _points = points
        _inradius = inradius
        _circumradius = circumradius
    }

    private constructor(
        center: Vector2F,
        orientation: ComplexF,
        sideLength: Float,
        points: Vector2FArray,
        inradius: Float,
        circumradius: Float,
    ) {
        _center = center
        _orientation = orientation
        _sideLength = sideLength
        _points = points
        _inradius = inradius
        _circumradius = circumradius
    }

    constructor(regularTriangle: MutableRegularTriangle) {
        val points = Vector2FArray(3)
        points[0] = regularTriangle._pointA
        points[1] = regularTriangle._pointB
        points[2] = regularTriangle._pointC
        _center = regularTriangle._center
        _orientation = regularTriangle._orientation
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
        _orientation = square._orientation
        _sideLength = square._sideLength
        _points = points
        _circumradius = square.circumradius
        _inradius = square.inradius
    }

    override val center: Vector2F
        get() = _center

    override val orientation: ComplexF
        get() = _orientation

    override val sideLength: Float
        get() = _sideLength

    override val sideCount: Int
        get() = _points.size

    override val points: Vector2FList
        get() = _points.asList()

    override val inradius: Float
        get() = _inradius

    override val circumradius: Float
        get() = _circumradius

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
            _orientation,
            _sideLength,
            points,
            _inradius,
            _circumradius
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
            _orientation,
            _sideLength,
            points,
            _inradius,
            _circumradius
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

    override fun rotatedBy(rotation: AngleF): MutableRegularPolygon =
        rotatedBy(ComplexF.fromAngle(rotation))

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
            _orientation * rotation,
            _sideLength,
            points,
            _inradius,
            _circumradius
        )
    }

    override fun rotatedTo(orientation: AngleF): MutableRegularPolygon =
        rotatedTo(ComplexF.fromAngle(orientation))

    override fun rotatedTo(orientation: ComplexF): MutableRegularPolygon {
        val (cX: Float, cY: Float) = _center
        val (rotR: Float, rotI: Float) = orientation * _orientation.conjugate
        val points: Vector2FArray = _points.copyOf()

        for (i in 0 until points.size) {
            val pX: Float = points[i].x - cX
            val pY: Float = points[i].y - cY
            points[i] = Vector2F(pX * rotR - pY * rotI + cX, pY * rotR + pX * rotI + cY)
        }
        return MutableRegularPolygon(
            _center,
            orientation,
            _sideLength,
            points,
            _inradius,
            _circumradius
        )
    }

    override fun rotatedAroundPointBy(point: Vector2F, rotation: AngleF): MutableRegularPolygon =
        rotatedAroundPointBy(point, ComplexF.fromAngle(rotation))

    override fun rotatedAroundPointBy(point: Vector2F, rotation: ComplexF): MutableRegularPolygon {
        val (pX: Float, pY: Float) = point
        val (rotR: Float, rotI: Float) = rotation
        val (cX: Float, cY: Float) = _center
        val (startRotR: Float, startRotI: Float) = _orientation
        val points: Vector2FArray = _points.copyOf()
        val cpDiffX: Float = cX - pX
        val cpDiffY: Float = cY - pY
        val targetCenterX: Float = cpDiffX * rotR - cpDiffY * rotI + pX
        val targetCenterY: Float = cpDiffY * rotR + cpDiffX * rotI + pY
        val targetRotR: Float = startRotR * rotR - startRotI * rotI
        val targetRotI: Float = startRotI * rotR + startRotR * rotI

        for (i in 0 until points.size) {
            val piX: Float = points[i].x - cX
            val piY: Float = points[i].y - cY
            points[i] = Vector2F(
                piX * rotR - piY * rotI + targetCenterX,
                piY * rotR + piX * rotI + targetCenterY
            )
        }
        return MutableRegularPolygon(
            center = Vector2F(targetCenterX, targetCenterY),
            orientation = ComplexF(targetRotR, targetRotI),
            _sideLength,
            points,
            _inradius,
            _circumradius
        )
    }

    override fun rotatedAroundPointTo(
        point: Vector2F, orientation: AngleF
    ): MutableRegularPolygon =
        rotatedAroundPointTo(point, ComplexF.fromAngle(orientation))

    override fun rotatedAroundPointTo(
        point: Vector2F, orientation: ComplexF
    ): MutableRegularPolygon {
        val (pX: Float, pY: Float) = point
        val (rotR: Float, rotI: Float) = orientation
        val (startRotR: Float, startRotI: Float) = _orientation
        val (cX: Float, cY: Float) = _center
        val points: Vector2FArray = _points.copyOf()
        val cpDiffX: Float = cX - pX
        val cpDiffY: Float = cY - pY
        val centerToPointDist: Float = sqrt(cpDiffX * cpDiffX + cpDiffY * cpDiffY)

        if (centerToPointDist > 0.00001f) {
            val pointRotR: Float = cpDiffX / centerToPointDist
            val pointRotI: Float = cpDiffY / centerToPointDist
            val targetCenterX: Float = rotR * centerToPointDist + pX
            val targetCenterY: Float = rotI * centerToPointDist + pY
            val pRotR: Float = pointRotR * rotR + pointRotI * rotI
            val pRotI: Float = pointRotR * rotI - pointRotI * rotR

            for (i in 0 until points.size) {
                val piX: Float = points[i].x - cX
                val piY: Float = points[i].y - cY
                points[i] = Vector2F(
                    piX * pRotR - piY * pRotI + targetCenterX,
                    piY * pRotR + piX * pRotI + targetCenterY
                )
            }
            return MutableRegularPolygon(
                center = Vector2F(targetCenterX, targetCenterY),
                orientation = ComplexF(
                    pRotR * startRotR - pRotI * startRotI,
                    pRotI * startRotR + pRotR * startRotI
                ),
                _sideLength,
                points,
                _inradius,
                _circumradius
            )
        } else {
            val pRotR: Float = rotR * startRotR + rotI * startRotI
            val pRotI: Float = rotI * startRotR - rotR * startRotI

            for (i in 0 until points.size) {
                val piX: Float = points[i].x - cX
                val piY: Float = points[i].y - cY
                points[i] = Vector2F(
                    piX * pRotR - piY * pRotI + cX,
                    piY * pRotR + piX * pRotI + cY
                )
            }
            return MutableRegularPolygon(
                _center,
                orientation,
                _sideLength,
                points,
                _inradius,
                _circumradius
            )
        }
    }

    override fun rotateBy(rotation: AngleF) = rotateBy(ComplexF.fromAngle(rotation))

    override fun rotateBy(rotation: ComplexF) {
        val (cX: Float, cY: Float) = _center
        val (rotR: Float, rotI: Float) = rotation
        val points: Vector2FArray = _points

        for (i in 0 until points.size) {
            val pX: Float = points[i].x - cX
            val pY: Float = points[i].y - cY
            points[i] = Vector2F(pX * rotR - pY * rotI + cX, pY * rotR + pX * rotI + cY)
        }
        _orientation *= rotation
    }

    override fun rotateTo(orientation: AngleF) = rotateTo(ComplexF.fromAngle(orientation))

    override fun rotateTo(orientation: ComplexF) {
        val (cX: Float, cY: Float) = _center
        val (rotR: Float, rotI: Float) = orientation * _orientation.conjugate
        val points: Vector2FArray = _points

        for (i in 0 until points.size) {
            val pX: Float = points[i].x - cX
            val pY: Float = points[i].y - cY
            points[i] = Vector2F(pX * rotR - pY * rotI + cX, pY * rotR + pX * rotI + cY)
        }
        _orientation = orientation
    }

    override fun rotateAroundPointBy(point: Vector2F, rotation: AngleF) =
        rotateAroundPointBy(point, ComplexF.fromAngle(rotation))

    override fun rotateAroundPointBy(point: Vector2F, rotation: ComplexF) {
        val (pX: Float, pY: Float) = point
        val (rotR: Float, rotI: Float) = rotation
        val (cX: Float, cY: Float) = _center
        val (startRotR: Float, startRotI: Float) = _orientation
        val points: Vector2FArray = _points
        val cpDiffX: Float = cX - pX
        val cpDiffY: Float = cY - pY
        val targetCenterX: Float = cpDiffX * rotR - cpDiffY * rotI + pX
        val targetCenterY: Float = cpDiffY * rotR + cpDiffX * rotI + pY
        val targetRotR: Float = startRotR * rotR - startRotI * rotI
        val targetRotI: Float = startRotI * rotR + startRotR * rotI

        for (i in 0 until points.size) {
            val piX: Float = points[i].x - cX
            val piY: Float = points[i].y - cY
            points[i] = Vector2F(
                piX * rotR - piY * rotI + targetCenterX,
                piY * rotR + piX * rotI + targetCenterY
            )
        }
        _center = Vector2F(targetCenterX, targetCenterY)
        _orientation = ComplexF(targetRotR, targetRotI)
    }

    override fun rotateAroundPointTo(point: Vector2F, orientation: AngleF) =
        rotateAroundPointTo(point, ComplexF.fromAngle(orientation))

    override fun rotateAroundPointTo(point: Vector2F, orientation: ComplexF) {
        val (pX: Float, pY: Float) = point
        val (rotR: Float, rotI: Float) = orientation
        val (startRotR: Float, startRotI: Float) = _orientation
        val (cX: Float, cY: Float) = _center
        val points: Vector2FArray = _points
        val cpDiffX: Float = cX - pX
        val cpDiffY: Float = cY - pY
        val centerToPointDist: Float = sqrt(cpDiffX * cpDiffX + cpDiffY * cpDiffY)

        if (centerToPointDist > 0.00001f) {
            val pointRotR: Float = cpDiffX / centerToPointDist
            val pointRotI: Float = cpDiffY / centerToPointDist
            val targetCenterX: Float = rotR * centerToPointDist + pX
            val targetCenterY: Float = rotI * centerToPointDist + pY
            val pRotR: Float = pointRotR * rotR + pointRotI * rotI
            val pRotI: Float = pointRotR * rotI - pointRotI * rotR

            for (i in 0 until points.size) {
                val piX: Float = points[i].x - cX
                val piY: Float = points[i].y - cY
                points[i] = Vector2F(
                    piX * pRotR - piY * pRotI + targetCenterX,
                    piY * pRotR + piX * pRotI + targetCenterY
                )
            }
            _center = Vector2F(targetCenterX, targetCenterY)
            _orientation = ComplexF(
                pRotR * startRotR - pRotI * startRotI,
                pRotI * startRotR + pRotR * startRotI
            )
        } else {
            val pRotR: Float = rotR * startRotR + rotI * startRotI
            val pRotI: Float = rotI * startRotR - rotR * startRotI

            for (i in 0 until points.size) {
                val piX: Float = points[i].x - cX
                val piY: Float = points[i].y - cY
                points[i] = Vector2F(
                    piX * pRotR - piY * pRotI + cX,
                    piY * pRotR + piX * pRotI + cY
                )
            }
            _orientation = orientation
        }
    }

    override fun scaledBy(factor: Float): MutableRegularPolygon {
        val points: Vector2FArray = _points.copyOf()
        val (cX: Float, cY: Float) = _center
        val f: Float = 1f - factor
        val absFactor: Float = factor.absoluteValue
        val addendX: Float = cX * f
        val addendY: Float = cY * f

        for (i in 0 until points.size) {
            val (pX: Float, pY: Float) = points[i]

            points[i] = Vector2F(pX * factor + addendX, pY * factor + addendY)
        }
        return MutableRegularPolygon(
            center,
            _orientation * 1f.withSign(factor),
            _sideLength * absFactor,
            points,
            _inradius * absFactor,
            _circumradius * absFactor
        )
    }

    override fun dilatedBy(point: Vector2F, factor: Float): MutableRegularPolygon {
        val points: Vector2FArray = _points.copyOf()
        val (startCX: Float, startCY: Float) = _center
        val f: Float = 1f - factor
        val addendX: Float = point.x * f
        val addendY: Float = point.y * f
        val absFactor: Float = factor.absoluteValue

        for (i in 0 until points.size) {
            val (pX: Float, pY: Float) = points[i]

            points[i] = Vector2F(pX * factor + addendX, pY * factor + addendY)
        }
        return MutableRegularPolygon(
            center = Vector2F(startCX * factor + addendX, startCY * factor + addendY),
            orientation * 1f.withSign(factor),
            sideLength * absFactor,
            points,
            inradius * absFactor,
            circumradius * absFactor
        )
    }

    override fun scaleBy(factor: Float) {
        val points: Vector2FArray = _points
        val (cX: Float, cY: Float) = _center
        val f: Float = 1f - factor
        val addendX: Float = cX * f
        val addendY: Float = cY * f
        val absFactor: Float = factor.absoluteValue

        for (i in 0 until points.size) {
            val (pX: Float, pY: Float) = points[i]

            points[i] = Vector2F(pX * factor + addendX, pY * factor + addendY)
        }
        _orientation *= 1f.withSign(factor)
        _sideLength *= absFactor
        _circumradius *= absFactor
        _inradius *= absFactor
    }

    override fun dilateBy(point: Vector2F, factor: Float) {
        val points: Vector2FArray = _points
        val (startCX: Float, startCY: Float) = _center
        val f: Float = 1f - factor
        val addendX: Float = point.x * f
        val addendY: Float = point.y * f
        val absFactor: Float = factor.absoluteValue

        for (i in 0 until points.size) {
            val (pX: Float, pY: Float) = points[i]

            points[i] = Vector2F(pX * factor + addendX, pY * factor + addendY)
        }
        _center = Vector2F(startCX * factor + addendX, startCY * factor + addendY)
        _orientation *= 1f.withSign(factor)
        _sideLength *= absFactor
        _circumradius *= absFactor
        _inradius *= absFactor
    }

    override fun transformedBy(offset: Vector2F, rotation: AngleF): MutableRegularPolygon =
        transformedBy(offset, ComplexF.fromAngle(rotation))

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
            _orientation * rotation,
            _sideLength,
            points,
            _inradius,
            _circumradius
        )
    }

    override fun transformedBy(
        offset: Vector2F, rotation: AngleF, factor: Float
    ): MutableRegularPolygon = transformedBy(offset, ComplexF.fromAngle(rotation), factor)

    override fun transformedBy(
        offset: Vector2F, rotation: ComplexF, factor: Float
    ): MutableRegularPolygon {
        val points: Vector2FArray = _points.copyOf()
        val (pcX: Float, pcY: Float) = _center
        val cX: Float = pcX + offset.x
        val cY: Float = pcY + offset.y
        val (rotR: Float, rotI: Float) = rotation
        val f: Float = 1f - factor
        val absFactor: Float = factor.absoluteValue
        val addendX: Float = cX * f
        val addendY: Float = cY * f

        for (i in 0 until points.size) {
            val p1X: Float = points[i].x - pcX
            val p1Y: Float = points[i].y - pcY
            val p2X: Float = p1X * rotR - p1Y * rotI + cX
            val p2Y: Float = p1Y * rotR + p1X * rotI + cY
            points[i] = Vector2F(addendX + p2X * factor, addendY + p2Y * factor)
        }
        return MutableRegularPolygon(
            Vector2F(cX, cY),
            _orientation * rotation * 1f.withSign(factor),
            _sideLength * absFactor,
            points,
            _inradius * absFactor,
            _circumradius * absFactor
        )
    }

    override fun transformedTo(position: Vector2F, orientation: AngleF): MutableRegularPolygon =
        transformedTo(position, ComplexF.fromAngle(orientation))

    override fun transformedTo(position: Vector2F, orientation: ComplexF): MutableRegularPolygon {
        val points: Vector2FArray = _points.copyOf()
        val (pcX: Float, pcY: Float) = _center
        val (cX: Float, cY: Float) = position
        val (rotR: Float, rotI: Float) = orientation * _orientation.conjugate

        for (i in 0 until points.size) {
            val pX: Float = points[i].x - pcX
            val pY: Float = points[i].y - pcY
            points[i] = Vector2F(pX * rotR - pY * rotI + cX, pY * rotR + pX * rotI + cY)
        }
        return MutableRegularPolygon(
            position,
            orientation,
            _sideLength,
            points,
            _inradius,
            _circumradius
        )
    }

    override fun transformBy(offset: Vector2F, rotation: AngleF) =
        transformBy(offset, ComplexF.fromAngle(rotation))

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
        _orientation *= rotation
    }

    override fun transformBy(offset: Vector2F, rotation: AngleF, factor: Float) =
        transformBy(offset, ComplexF.fromAngle(rotation), factor)

    override fun transformBy(offset: Vector2F, rotation: ComplexF, factor: Float) {
        val points: Vector2FArray = _points
        val (pcX: Float, pcY: Float) = _center
        val cX: Float = pcX + offset.x
        val cY: Float = pcY + offset.y
        val (rotR: Float, rotI: Float) = rotation
        val f: Float = 1f - factor
        val absFactor: Float = factor.absoluteValue
        val addendX: Float = cX * f
        val addendY: Float = cY * f

        for (i in 0 until points.size) {
            val p1X: Float = points[i].x - pcX
            val p1Y: Float = points[i].y - pcY
            val p2X: Float = p1X * rotR - p1Y * rotI + cX
            val p2Y: Float = p1Y * rotR + p1X * rotI + cY
            points[i] = Vector2F(addendX + p2X * factor, addendY + p2Y * factor)
        }
        _center = Vector2F(cX, cY)
        _orientation *= rotation * 1f.withSign(factor)
        _sideLength *= absFactor
        _circumradius *= absFactor
        _inradius *= absFactor
    }

    override fun transformTo(position: Vector2F, orientation: AngleF) =
        transformTo(position, ComplexF.fromAngle(orientation))

    override fun transformTo(position: Vector2F, orientation: ComplexF) {
        val points: Vector2FArray = _points
        val (pcX: Float, pcY: Float) = _center
        val (cX: Float, cY: Float) = position
        val (rotR: Float, rotI: Float) = orientation * _orientation.conjugate

        for (i in 0 until points.size) {
            val pX: Float = points[i].x - pcX
            val pY: Float = points[i].y - pcY
            points[i] = Vector2F(pX * rotR - pY * rotI + cX, pY * rotR + pX * rotI + cY)
        }
        _center = position
        _orientation = orientation
    }

    private inline fun setInternal(
        center: Vector2F, orientation: ComplexF, sideLength: Float, sideCount: Int
    ) {
        if (sideCount < 3) {
            val (oR: Float, oI: Float) = orientation
            val halfSideLength: Float = 0.5f * sideLength
            val offset = Vector2F(oR * halfSideLength, oI * halfSideLength)
            val points: Vector2FArray =
                if (sideCount == points.size) _points
                else Vector2FArray(sideCount)
            points[0] = center + offset
            points[1] = center - offset
            _center = center
            _orientation = orientation
            _sideLength = sideLength
            _points = points
            _inradius = 0f
            _circumradius = halfSideLength
            return
        }
        val points: Vector2FArray =
            if (sideCount == points.size) _points
            else Vector2FArray(sideCount)
        val halfSideLength: Float = 0.5f * sideLength
        val isSideCountEven: Boolean = sideCount and 1 == 0
        val halfCount: Int = sideCount / 2
        val exteriorAngle: Float = (2.0 * PI).toFloat() / sideCount
        val halfExteriorAngle: Float = exteriorAngle * 0.5f
        val exteriorRotation = ComplexF(cos(exteriorAngle), sin(exteriorAngle))
        val inradius: Float
        val circumradius: Float

        if (isSideCountEven) {
            inradius = halfSideLength / tan(halfExteriorAngle)
            circumradius = halfSideLength / sin(halfExteriorAngle)
            points[0] = Vector2F(halfSideLength, inradius)
            points[1] = Vector2F(-halfSideLength, inradius)

            for (i in 2..halfCount) {
                points[i] = (exteriorRotation * points[i - 1].toComplexF()).toVector2F()
            }
            for (i in halfCount + 1 until sideCount) {
                val oppositePoint: Vector2F = points[sideCount - i + 1]
                points[i] = Vector2F(-oppositePoint.x, oppositePoint.y)
            }
        } else {
            inradius = halfSideLength / tan(halfExteriorAngle)
            circumradius = halfSideLength / sin(halfExteriorAngle)
            points[0] = Vector2F(0f, circumradius)

            for (i in 1..halfCount) {
                points[i] = (exteriorRotation * points[i - 1].toComplexF()).toVector2F()
            }
            for (i in (halfCount + 1) until sideCount) {
                val oppositePoint: Vector2F = points[sideCount - i]
                points[i] = Vector2F(-oppositePoint.x, oppositePoint.y)
            }
        }
        for (i in 0 until sideCount) {
            points[i] = center + (orientation * points[i].toComplexF()).toVector2F()
        }
        _center = center
        _orientation = orientation
        _sideLength = sideLength
        _points = points
        _inradius = inradius
        _circumradius = circumradius
    }

    fun set(
        center: Vector2F = this.center,
        orientation: ComplexF = this.orientation,
        sideLength: Float = this.sideLength,
        sideCount: Int = this.sideCount
    ) {
        throwWhenSideLengthValueIsIllegal(sideLength)
        throwWhenSideCountValueIsIllegal(sideCount)
        setInternal(center, orientation, sideLength, sideCount)
    }

    override fun closestPointTo(point: Vector2F): Vector2F {
        val sideCount: Int = this.sideCount
        val center: Vector2F = _center
        val orientation: ComplexF = _orientation
        val rotConj: ComplexF = orientation.conjugate
        val halfSideLength: Float = _sideLength * 0.5f

        if (sideCount == 2) {
            val x: Float = (rotConj * (point - center).toComplexF()).real

            return when {
                x > halfSideLength -> _points[0]
                x < -halfSideLength -> _points[1]
                else -> center + orientation.toVector2F() * x
            }
        }
        val rot90Deg = ComplexF(0f, 1f)
        val rotNeg90Deg = ComplexF(0f, -1f)
        val fullAngle = (2.0 * PI).toFloat()
        val exteriorAngle: Float = fullAngle / sideCount
        val inradius: Float = _inradius
        val p1: ComplexF = rotConj * rotNeg90Deg * (point - center).toComplexF()
        val p1Angle: Float = atan2(p1.imaginary, p1.real) + 0.001f
        val p1AnglePositive: Float =
            if (p1Angle < 0) p1Angle + fullAngle
            else p1Angle
        val index: Int = (p1AnglePositive / exteriorAngle).toInt()
        val rot1 = ComplexF.fromAngle(
            AngleF((sideCount and 1) * (-exteriorAngle * 0.5f) + -exteriorAngle * index)
        )
        val rot1Conj: ComplexF = rot1.conjugate
        val p2: ComplexF = rot1 * rot90Deg * p1
        val (p2X, p2Y) = p2

        return when {
            p2X < -halfSideLength ->
                center + (rot1Conj * orientation * ComplexF(-halfSideLength, inradius)).toVector2F()

            p2X > halfSideLength ->
                center + (rot1Conj * orientation * ComplexF(halfSideLength, inradius)).toVector2F()

            p2Y > inradius ->
                center + (rot1Conj * orientation * ComplexF(p2X, inradius)).toVector2F()

            else -> point
        }
    }

    override operator fun contains(point: Vector2F): Boolean {
        val sideCount: Int = this.sideCount
        val orientation: ComplexF = _orientation
        val rotConj: ComplexF = orientation.conjugate
        val halfSideLength: Float = _sideLength * 0.5f
        val center: Vector2F = _center

        if (sideCount == 2) {
            val x: Float = (rotConj * (point - center).toComplexF()).real

            return if ((x > halfSideLength) or (x < -halfSideLength)) false
            else {
                val closestPoint = center + orientation.toVector2F() * x

                return closestPoint.isApproximately(point)
            }
        }
        val rot90Deg = ComplexF(0f, 1f)
        val rotNeg90Deg = ComplexF(0f, -1f)
        val fullAngle = (2.0 * PI).toFloat()
        val exteriorAngle: Float = fullAngle / sideCount
        val inradius: Float = _inradius
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

    override fun copy(center: Vector2F, orientation: ComplexF, sideLength: Float, sideCount: Int) =
        MutableRegularPolygon(center, orientation, sideLength, sideCount)

    override fun equals(other: Any?): Boolean = other is RegularPolygon &&
            _center == other.center &&
            _orientation == other.orientation &&
            _sideLength == other.sideLength &&
            sideCount == other.sideCount

    fun equals(other: MutableRegularPolygon): Boolean =
        _center == other._center &&
                _orientation == other._orientation &&
                _sideLength == other._sideLength &&
                sideCount == other.sideCount

    override fun hashCode(): Int {
        val centerHash: Int = center.hashCode()
        val orientationHash: Int = orientation.hashCode()
        val sideLengthHash: Int = sideLength.hashCode()
        val sideCountHash: Int = sideCount.hashCode()

        return centerHash * 29791 + orientationHash * 961 + sideLengthHash * 31 + sideCountHash
    }

    override fun toString() =
        StringBuilder("RegularPolygon(center=").append(_center)
            .append(", orientation=").append(_orientation)
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

    override operator fun component2(): ComplexF = _orientation

    override operator fun component3(): Float = _sideLength

    override operator fun component4(): Int = sideCount

    companion object {
        private inline fun throwWhenSideCountValueIsIllegal(sideCount: Int) {
            if (sideCount < 2) {
                throw IllegalArgumentException("sideCount must be greater than or equal to two.")
            }
        }

        private inline fun throwWhenSideLengthValueIsIllegal(sideLength: Float) {
            if (sideLength < 0f) {
                throw IllegalArgumentException("sideLength must be greater than or equal to zero.")
            }
        }
    }
}