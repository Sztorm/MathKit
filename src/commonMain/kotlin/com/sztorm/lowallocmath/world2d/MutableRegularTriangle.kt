@file:Suppress("OVERRIDE_BY_INLINE", "PropertyName")

package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.*
import kotlin.math.PI
import kotlin.math.absoluteValue
import kotlin.math.sqrt
import kotlin.math.withSign

class MutableRegularTriangle : RegularTriangle, MutableTransformable {
    internal var _center: Vector2F
    internal var _orientation: ComplexF
    internal var _sideLength: Float
    internal var _pointA: Vector2F
    internal var _pointB: Vector2F
    internal var _pointC: Vector2F

    constructor(center: Vector2F, orientation: ComplexF, sideLength: Float) {
        val (cX: Float, cY: Float) = center
        val (rotR: Float, rotI: Float) = orientation
        val halfSideLength: Float = sideLength * 0.5f
        val inradius: Float = 0.28867513f * sideLength
        val circumradius: Float = inradius + inradius
        val addendX1: Float = rotI * inradius + cX
        val addendX2: Float = rotR * halfSideLength
        val addendY1: Float = rotI * halfSideLength
        val addendY2: Float = rotR * inradius - cY
        _center = center
        _orientation = orientation
        _sideLength = sideLength
        _pointA = Vector2F(cX - rotI * circumradius, cY + rotR * circumradius)
        _pointB = Vector2F(addendX1 - addendX2, -addendY1 - addendY2)
        _pointC = Vector2F(addendX1 + addendX2, addendY1 - addendY2)
    }

    private constructor(
        center: Vector2F,
        orientation: ComplexF,
        sideLength: Float,
        pointA: Vector2F,
        pointB: Vector2F,
        pointC: Vector2F
    ) {
        _center = center
        _orientation = orientation
        _sideLength = sideLength
        _pointA = pointA
        _pointB = pointB
        _pointC = pointC
    }

    internal constructor(regularPolygon: MutableRegularPolygon) {
        val points: Vector2FArray = regularPolygon._points
        _center = regularPolygon._center
        _orientation = regularPolygon._orientation
        _sideLength = regularPolygon._sideLength
        _pointA = points.elementAt(0)
        _pointB = points.elementAt(1)
        _pointC = points.elementAt(2)
    }

    override val center: Vector2F
        get() = _center

    override val orientation: ComplexF
        get() = _orientation

    override val sideLength: Float
        get() = _sideLength

    override val pointA: Vector2F
        get() = _pointA

    override val pointB: Vector2F
        get() = _pointB

    override val pointC: Vector2F
        get() = _pointC

    override val area: Float
        get() = 0.4330127f * _sideLength * _sideLength

    override val perimeter: Float
        get() = 3f * _sideLength

    override val sideLengthAB: Float
        get() = _sideLength

    override val sideLengthBC: Float
        get() = _sideLength

    override val sideLengthCA: Float
        get() = _sideLength

    override inline val sideCount: Int
        get() = 3

    override inline val interiorAngle: AngleF
        get() = AngleF((PI / 3.0).toFloat())

    override inline val exteriorAngle: AngleF
        get() = AngleF((2.0 / 3.0 * PI).toFloat())

    override val inradius: Float
        get() = 0.28867513f * _sideLength

    override val circumradius: Float
        get() = 0.5773503f * _sideLength

    override val position: Vector2F
        get() = _center

    override val centroid: Vector2F
        get() = _center

    override val orthocenter: Vector2F
        get() = _center

    override val incenter: Vector2F
        get() = _center

    override val circumcenter: Vector2F
        get() = _center

    override fun movedBy(offset: Vector2F) = MutableRegularTriangle(
        _center + offset,
        _orientation,
        _sideLength,
        _pointA + offset,
        _pointB + offset,
        _pointC + offset
    )

    override fun movedTo(position: Vector2F): MutableRegularTriangle {
        val offset: Vector2F = position - _center

        return MutableRegularTriangle(
            position,
            _orientation,
            _sideLength,
            _pointA + offset,
            _pointB + offset,
            _pointC + offset
        )
    }

    override fun moveBy(offset: Vector2F) {
        _center += offset
        _pointA += offset
        _pointB += offset
        _pointC += offset
    }

    override fun moveTo(position: Vector2F) {
        val offset: Vector2F = position - _center
        _center = position
        _pointA += offset
        _pointB += offset
        _pointC += offset
    }

    override fun rotatedBy(rotation: AngleF) =
        MutableRegularTriangle(_center, _orientation * ComplexF.fromAngle(rotation), _sideLength)

    override fun rotatedBy(rotation: ComplexF) =
        MutableRegularTriangle(_center, _orientation * rotation, _sideLength)

    override fun rotatedTo(orientation: AngleF) =
        MutableRegularTriangle(_center, ComplexF.fromAngle(orientation), _sideLength)

    override fun rotatedTo(orientation: ComplexF) =
        MutableRegularTriangle(_center, orientation, _sideLength)

    override fun rotatedAroundPointBy(point: Vector2F, rotation: AngleF): MutableRegularTriangle =
        rotatedAroundPointBy(point, ComplexF.fromAngle(rotation))

    override fun rotatedAroundPointBy(
        point: Vector2F, rotation: ComplexF
    ): MutableRegularTriangle {
        val (pX: Float, pY: Float) = point
        val (rotR: Float, rotI: Float) = rotation
        val (cX: Float, cY: Float) = _center
        val (startRotR: Float, startRotI: Float) = _orientation
        val cpDiffX: Float = cX - pX
        val cpDiffY: Float = cY - pY
        val targetCenterX: Float = cpDiffX * rotR - cpDiffY * rotI + pX
        val targetCenterY: Float = cpDiffY * rotR + cpDiffX * rotI + pY
        val targetRotR: Float = startRotR * rotR - startRotI * rotI
        val targetRotI: Float = startRotI * rotR + startRotR * rotI
        val sideLength: Float = _sideLength
        val halfSideLength: Float = sideLength * 0.5f
        val inradius: Float = 0.28867513f * sideLength
        val circumradius: Float = inradius + inradius
        val addendX1: Float = targetRotI * inradius + targetCenterX
        val addendX2: Float = targetRotR * halfSideLength
        val addendY1: Float = targetRotI * halfSideLength
        val addendY2: Float = targetRotR * inradius - targetCenterY

        return MutableRegularTriangle(
            center = Vector2F(targetCenterX, targetCenterY),
            orientation = ComplexF(targetRotR, targetRotI),
            _sideLength,
            pointA = Vector2F(
                targetCenterX - targetRotI * circumradius,
                targetCenterY + targetRotR * circumradius
            ),
            pointB = Vector2F(addendX1 - addendX2, -addendY1 - addendY2),
            pointC = Vector2F(addendX1 + addendX2, addendY1 - addendY2)
        )
    }

    override fun rotatedAroundPointTo(
        point: Vector2F, orientation: AngleF
    ): MutableRegularTriangle = rotatedAroundPointTo(point, ComplexF.fromAngle(orientation))

    override fun rotatedAroundPointTo(
        point: Vector2F, orientation: ComplexF
    ): MutableRegularTriangle {
        val (pX: Float, pY: Float) = point
        val (rotR: Float, rotI: Float) = orientation
        val (cX: Float, cY: Float) = _center
        val sideLength: Float = _sideLength
        val halfSideLength: Float = sideLength * 0.5f
        val inradius: Float = 0.28867513f * sideLength
        val circumradius: Float = inradius + inradius
        val cpDiffX: Float = cX - pX
        val cpDiffY: Float = cY - pY
        val centerToPointDist: Float = sqrt(cpDiffX * cpDiffX + cpDiffY * cpDiffY)

        if (centerToPointDist > 0.00001f) {
            val pointRotR: Float = cpDiffX / centerToPointDist
            val pointRotI: Float = cpDiffY / centerToPointDist
            val targetRot = ComplexF(pointRotR, -pointRotI) * _orientation * orientation
            val (targetRotR: Float, targetRotI: Float) = targetRot
            val targetCenterX: Float = rotR * centerToPointDist + pX
            val targetCenterY: Float = rotI * centerToPointDist + pY
            val addendX1: Float = targetRotI * inradius + targetCenterX
            val addendX2: Float = targetRotR * halfSideLength
            val addendY1: Float = targetRotI * halfSideLength
            val addendY2: Float = targetRotR * inradius - targetCenterY

            return MutableRegularTriangle(
                center = Vector2F(targetCenterX, targetCenterY),
                targetRot,
                _sideLength,
                pointA = Vector2F(
                    targetCenterX - targetRotI * circumradius,
                    targetCenterY + targetRotR * circumradius
                ),
                pointB = Vector2F(addendX1 - addendX2, -addendY1 - addendY2),
                pointC = Vector2F(addendX1 + addendX2, addendY1 - addendY2)
            )
        } else {
            val addendX1: Float = rotI * inradius + cX
            val addendX2: Float = rotR * halfSideLength
            val addendY1: Float = rotI * halfSideLength
            val addendY2: Float = rotR * inradius - cY

            return MutableRegularTriangle(
                _center,
                orientation,
                _sideLength,
                pointA = Vector2F(cX - rotI * circumradius, cY + rotR * circumradius),
                pointB = Vector2F(addendX1 - addendX2, -addendY1 - addendY2),
                pointC = Vector2F(addendX1 + addendX2, addendY1 - addendY2)
            )
        }
    }

    override fun rotateBy(rotation: AngleF) =
        rotateTo(_orientation * ComplexF.fromAngle(rotation))

    override fun rotateBy(rotation: ComplexF) = rotateTo(_orientation * rotation)

    override fun rotateTo(orientation: AngleF) = rotateTo(ComplexF.fromAngle(orientation))

    override fun rotateTo(orientation: ComplexF) {
        val (cX: Float, cY: Float) = _center
        val (rotR: Float, rotI: Float) = orientation
        val sideLength: Float = _sideLength
        val halfSideLength: Float = sideLength * 0.5f
        val inradius: Float = 0.28867513f * sideLength
        val circumradius: Float = inradius + inradius
        val addendX1: Float = rotI * inradius + cX
        val addendX2: Float = rotR * halfSideLength
        val addendY1: Float = rotI * halfSideLength
        val addendY2: Float = rotR * inradius - cY
        _orientation = orientation
        _pointA = Vector2F(cX - rotI * circumradius, cY + rotR * circumradius)
        _pointB = Vector2F(addendX1 - addendX2, -addendY1 - addendY2)
        _pointC = Vector2F(addendX1 + addendX2, addendY1 - addendY2)
    }

    override fun rotateAroundPointBy(point: Vector2F, rotation: AngleF) =
        rotateAroundPointBy(point, ComplexF.fromAngle(rotation))

    override fun rotateAroundPointBy(point: Vector2F, rotation: ComplexF) {
        val (pX: Float, pY: Float) = point
        val (rotR: Float, rotI: Float) = rotation
        val (cX: Float, cY: Float) = _center
        val (startRotR: Float, startRotI: Float) = _orientation
        val cpDiffX: Float = cX - pX
        val cpDiffY: Float = cY - pY
        val targetCenterX: Float = cpDiffX * rotR - cpDiffY * rotI + pX
        val targetCenterY: Float = cpDiffY * rotR + cpDiffX * rotI + pY
        val targetRotR: Float = startRotR * rotR - startRotI * rotI
        val targetRotI: Float = startRotI * rotR + startRotR * rotI
        val sideLength: Float = _sideLength
        val halfSideLength: Float = sideLength * 0.5f
        val inradius: Float = 0.28867513f * sideLength
        val circumradius: Float = inradius + inradius
        val addendX1: Float = targetRotI * inradius + targetCenterX
        val addendX2: Float = targetRotR * halfSideLength
        val addendY1: Float = targetRotI * halfSideLength
        val addendY2: Float = targetRotR * inradius - targetCenterY
        _center = Vector2F(targetCenterX, targetCenterY)
        _orientation = ComplexF(targetRotR, targetRotI)
        _pointA = Vector2F(
            targetCenterX - targetRotI * circumradius,
            targetCenterY + targetRotR * circumradius
        )
        _pointB = Vector2F(addendX1 - addendX2, -addendY1 - addendY2)
        _pointC = Vector2F(addendX1 + addendX2, addendY1 - addendY2)
    }

    override fun rotateAroundPointTo(point: Vector2F, orientation: AngleF) =
        rotateAroundPointTo(point, ComplexF.fromAngle(orientation))

    override fun rotateAroundPointTo(point: Vector2F, orientation: ComplexF) {
        val (pX: Float, pY: Float) = point
        val (rotR: Float, rotI: Float) = orientation
        val (cX: Float, cY: Float) = _center
        val sideLength: Float = _sideLength
        val halfSideLength: Float = sideLength * 0.5f
        val inradius: Float = 0.28867513f * sideLength
        val circumradius: Float = inradius + inradius
        val cpDiffX: Float = cX - pX
        val cpDiffY: Float = cY - pY
        val centerToPointDist: Float = sqrt(cpDiffX * cpDiffX + cpDiffY * cpDiffY)

        if (centerToPointDist > 0.00001f) {
            val pointRotR: Float = cpDiffX / centerToPointDist
            val pointRotI: Float = cpDiffY / centerToPointDist
            val targetRot = ComplexF(pointRotR, -pointRotI) * _orientation * orientation
            val (targetRotR: Float, targetRotI: Float) = targetRot
            val targetCenterX: Float = rotR * centerToPointDist + pX
            val targetCenterY: Float = rotI * centerToPointDist + pY
            val addendX1: Float = targetRotI * inradius + targetCenterX
            val addendX2: Float = targetRotR * halfSideLength
            val addendY1: Float = targetRotI * halfSideLength
            val addendY2: Float = targetRotR * inradius - targetCenterY
            _center = Vector2F(targetCenterX, targetCenterY)
            _orientation = targetRot
            _pointA = Vector2F(
                targetCenterX - targetRotI * circumradius,
                targetCenterY + targetRotR * circumradius
            )
            _pointB = Vector2F(addendX1 - addendX2, -addendY1 - addendY2)
            _pointC = Vector2F(addendX1 + addendX2, addendY1 - addendY2)
        } else {
            val addendX1: Float = rotI * inradius + cX
            val addendX2: Float = rotR * halfSideLength
            val addendY1: Float = rotI * halfSideLength
            val addendY2: Float = rotR * inradius - cY
            _orientation = orientation
            _pointA = Vector2F(cX - rotI * circumradius, cY + rotR * circumradius)
            _pointB = Vector2F(addendX1 - addendX2, -addendY1 - addendY2)
            _pointC = Vector2F(addendX1 + addendX2, addendY1 - addendY2)
        }
    }

    override fun scaledBy(factor: Float) = MutableRegularTriangle(
        _center,
        orientation = _orientation * 1f.withSign(factor),
        sideLength = _sideLength * factor.absoluteValue
    )

    override fun dilatedBy(point: Vector2F, factor: Float): MutableRegularTriangle {
        val f: Float = 1f - factor
        val cX: Float = _center.x * factor + point.x * f
        val cY: Float = _center.y * factor + point.y * f
        val (rotR: Float, rotI: Float) = _orientation * 1f.withSign(factor)
        val sideLength: Float = _sideLength * factor.absoluteValue
        val halfSideLength: Float = sideLength * 0.5f
        val inradius: Float = 0.28867513f * sideLength
        val circumradius: Float = inradius + inradius
        val addendX1: Float = rotI * inradius + cX
        val addendX2: Float = rotR * halfSideLength
        val addendY1: Float = rotI * halfSideLength
        val addendY2: Float = rotR * inradius - cY

        return MutableRegularTriangle(
            center = Vector2F(cX, cY),
            orientation = ComplexF(rotR, rotI),
            sideLength,
            pointA = Vector2F(cX - rotI * circumradius, cY + rotR * circumradius),
            pointB = Vector2F(addendX1 - addendX2, -addendY1 - addendY2),
            pointC = Vector2F(addendX1 + addendX2, addendY1 - addendY2)
        )
    }

    override fun scaleBy(factor: Float) {
        val (cX: Float, cY: Float) = _center
        val (rotR: Float, rotI: Float) = _orientation * 1f.withSign(factor)
        val sideLength: Float = _sideLength * factor.absoluteValue
        val halfSideLength: Float = sideLength * 0.5f
        val inradius: Float = 0.28867513f * sideLength
        val circumradius: Float = inradius + inradius
        val addendX1: Float = rotI * inradius + cX
        val addendX2: Float = rotR * halfSideLength
        val addendY1: Float = rotI * halfSideLength
        val addendY2: Float = rotR * inradius - cY
        _orientation = ComplexF(rotR, rotI)
        _sideLength = sideLength
        _pointA = Vector2F(cX - rotI * circumradius, cY + rotR * circumradius)
        _pointB = Vector2F(addendX1 - addendX2, -addendY1 - addendY2)
        _pointC = Vector2F(addendX1 + addendX2, addendY1 - addendY2)
    }

    override fun dilateBy(point: Vector2F, factor: Float) {
        val f: Float = 1f - factor
        val cX: Float = _center.x * factor + point.x * f
        val cY: Float = _center.y * factor + point.y * f
        val (rotR: Float, rotI: Float) = _orientation * 1f.withSign(factor)
        val sideLength: Float = _sideLength * factor.absoluteValue
        val halfSideLength: Float = sideLength * 0.5f
        val inradius: Float = 0.28867513f * sideLength
        val circumradius: Float = inradius + inradius
        val addendX1: Float = rotI * inradius + cX
        val addendX2: Float = rotR * halfSideLength
        val addendY1: Float = rotI * halfSideLength
        val addendY2: Float = rotR * inradius - cY
        _center = Vector2F(cX, cY)
        _orientation = ComplexF(rotR, rotI)
        _sideLength = sideLength
        _pointA = Vector2F(cX - rotI * circumradius, cY + rotR * circumradius)
        _pointB = Vector2F(addendX1 - addendX2, -addendY1 - addendY2)
        _pointC = Vector2F(addendX1 + addendX2, addendY1 - addendY2)
    }

    override fun transformedBy(offset: Vector2F, rotation: AngleF) = MutableRegularTriangle(
        _center + offset, _orientation * ComplexF.fromAngle(rotation), _sideLength
    )

    override fun transformedBy(offset: Vector2F, rotation: ComplexF) =
        MutableRegularTriangle(_center + offset, _orientation * rotation, _sideLength)

    override fun transformedBy(offset: Vector2F, rotation: AngleF, factor: Float) =
        MutableRegularTriangle(
            _center + offset,
            _orientation * ComplexF.fromAngle(rotation) * 1f.withSign(factor),
            _sideLength * factor.absoluteValue
        )

    override fun transformedBy(offset: Vector2F, rotation: ComplexF, factor: Float) =
        MutableRegularTriangle(
            _center + offset,
            _orientation * rotation * 1f.withSign(factor),
            _sideLength * factor.absoluteValue
        )

    override fun transformedTo(position: Vector2F, orientation: AngleF) =
        MutableRegularTriangle(position, ComplexF.fromAngle(orientation), _sideLength)

    override fun transformedTo(position: Vector2F, orientation: ComplexF) =
        MutableRegularTriangle(position, orientation, _sideLength)

    override fun transformBy(offset: Vector2F, rotation: AngleF) =
        transformTo(_center + offset, _orientation * ComplexF.fromAngle(rotation))

    override fun transformBy(offset: Vector2F, rotation: ComplexF) =
        transformTo(_center + offset, _orientation * rotation)

    override fun transformBy(offset: Vector2F, rotation: AngleF, factor: Float) =
        transformBy(offset, ComplexF.fromAngle(rotation), factor)

    override fun transformBy(offset: Vector2F, rotation: ComplexF, factor: Float) {
        val cX: Float = _center.x + offset.x
        val cY: Float = _center.y + offset.y
        val r0 = _orientation.real
        val i0 = _orientation.imaginary
        val r1 = rotation.real
        val i1 = rotation.imaginary
        val factorSign: Float = 1f.withSign(factor)
        val rotR: Float = (r0 * r1 - i0 * i1) * factorSign
        val rotI: Float = (i0 * r1 + r0 * i1) * factorSign
        val sideLength: Float = _sideLength * factor.absoluteValue
        val halfSideLength: Float = sideLength * 0.5f
        val inradius: Float = 0.28867513f * sideLength
        val circumradius: Float = inradius + inradius
        val addendX1: Float = rotI * inradius + cX
        val addendX2: Float = rotR * halfSideLength
        val addendY1: Float = rotI * halfSideLength
        val addendY2: Float = rotR * inradius - cY
        _center = Vector2F(cX, cY)
        _orientation = ComplexF(rotR, rotI)
        _sideLength = sideLength
        _pointA = Vector2F(cX - rotI * circumradius, cY + rotR * circumradius)
        _pointB = Vector2F(addendX1 - addendX2, -addendY1 - addendY2)
        _pointC = Vector2F(addendX1 + addendX2, addendY1 - addendY2)
    }

    override fun transformTo(position: Vector2F, orientation: AngleF) =
        transformTo(position, ComplexF.fromAngle(orientation))

    override fun transformTo(position: Vector2F, orientation: ComplexF) {
        val (cX: Float, cY: Float) = position
        val (rotR: Float, rotI: Float) = orientation
        val sideLength: Float = _sideLength
        val halfSideLength: Float = sideLength * 0.5f
        val inradius: Float = 0.28867513f * sideLength
        val circumradius: Float = inradius + inradius
        val addendX1: Float = rotI * inradius + cX
        val addendX2: Float = rotR * halfSideLength
        val addendY1: Float = rotI * halfSideLength
        val addendY2: Float = rotR * inradius - cY
        _center = position
        _orientation = orientation
        _pointA = Vector2F(cX - rotI * circumradius, cY + rotR * circumradius)
        _pointB = Vector2F(addendX1 - addendX2, -addendY1 - addendY2)
        _pointC = Vector2F(addendX1 + addendX2, addendY1 - addendY2)
    }

    override fun closestPointTo(point: Vector2F): Vector2F {
        val halfSideLength: Float = _sideLength * 0.5f
        val inradius: Float = this.inradius
        val rotation: ComplexF = _orientation
        val center: Vector2F = _center
        val p1 = ComplexF.conjugate(rotation) *
                ComplexF(point.x - center.x, point.y - center.y)
        val p1X: Float = p1.real
        val p1Y: Float = p1.imaginary
        val yGB: Float = 0.5773503f * p1X // (sqrt(3) / 3) * x
        val yGC: Float = -yGB // (-sqrt(3) / 3) * x

        if ((p1Y <= yGB) and (p1Y <= yGC)) {
            if ((p1Y >= -inradius)) {
                return point
            }
            if (p1X.absoluteValue >= halfSideLength) {
                val vertexPoint = ComplexF(halfSideLength.withSign(p1X), -inradius)

                return (rotation * vertexPoint).toVector2F() + center
            }
            val edgePoint = ComplexF(p1X, -inradius)

            return (rotation * edgePoint).toVector2F() + center
        }
        val add120DegRotation = ComplexF(-0.5f, -(0.8660254f.withSign(p1X)))
        val p2: ComplexF = add120DegRotation * p1
        val p2X: Float = p2.real
        val p2Y: Float = p2.imaginary

        if ((p2Y >= -inradius)) {
            return point
        }
        if (p2X.absoluteValue >= halfSideLength) {
            val vertexPoint = ComplexF(halfSideLength.withSign(p2X), -inradius)

            return (ComplexF.conjugate(add120DegRotation) * rotation * vertexPoint)
                .toVector2F() + center
        }
        val edgePoint = ComplexF(p2X, -inradius)

        return (ComplexF.conjugate(add120DegRotation) * rotation * edgePoint)
            .toVector2F() + center
    }

    override operator fun contains(point: Vector2F): Boolean {
        val sideLength: Float = _sideLength
        val minusInradius: Float = -0.28867513f * sideLength // sqrt(3) / 6 * a
        val p1 = ComplexF.conjugate(_orientation) *
                ComplexF(point.x - _center.x, point.y - _center.y)
        val p1X: Float = p1.real
        val p1Y: Float = p1.imaginary
        //yAB = sqrt(3) * x + (sqrt(3) / 3) * a
        //yAC = -sqrt(3) * x + (sqrt(3) / 3) * a
        val ax: Float = 1.7320508f * p1X
        val b: Float = 0.5773503f * sideLength
        val yAB: Float = ax + b
        val yAC: Float = -ax + b

        return (p1Y >= minusInradius) and (p1Y <= yAB) and (p1Y <= yAC)
    }

    override fun pointIterator(): Vector2FIterator = PointIterator(this, index = 0)

    override fun copy(center: Vector2F, orientation: ComplexF, sideLength: Float) =
        MutableRegularTriangle(center, orientation, sideLength)

    override fun equals(other: Any?): Boolean = other is RegularTriangle &&
            _center == other.center &&
            _orientation == other.orientation &&
            _sideLength == other.sideLength

    fun equals(other: MutableRegularTriangle): Boolean =
        _center == other._center &&
                _orientation == other._orientation &&
                _sideLength == other._sideLength

    override fun hashCode(): Int {
        val centerHash: Int = _center.hashCode()
        val orientationHash: Int = _orientation.hashCode()
        val sideLengthHash: Int = _sideLength.hashCode()

        return centerHash * 961 + orientationHash * 31 + sideLengthHash
    }

    override fun toString() =
        StringBuilder("RegularTriangle(center=").append(_center)
            .append(", orientation=").append(_orientation)
            .append(", sideLength=").append(_sideLength).append(")")
            .toString()

    override operator fun component1(): Vector2F = _center

    override operator fun component2(): ComplexF = _orientation

    override operator fun component3(): Float = _sideLength

    private class PointIterator(
        private val triangle: MutableRegularTriangle,
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