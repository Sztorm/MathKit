@file:Suppress("OVERRIDE_BY_INLINE")

package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.AngleF
import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F
import kotlin.math.PI
import kotlin.math.absoluteValue
import kotlin.math.withSign

class MutableSquare(center: Vector2F, rotation: ComplexF, sideLength: Float) : Square {
    private var _center: Vector2F = center
    private var _rotation: ComplexF = rotation
    private var _sideLength: Float = sideLength
    private var _pointA: Vector2F
    private var _pointB: Vector2F
    private var _pointC: Vector2F
    private var _pointD: Vector2F

    init {
        val halfSideLength: Float = sideLength * 0.5f
        val pA = ComplexF(halfSideLength, halfSideLength)
        val pB = ComplexF(-halfSideLength, halfSideLength)
        val pC = ComplexF(-halfSideLength, -halfSideLength)
        val pD = ComplexF(halfSideLength, -halfSideLength)
        _pointA = center + (rotation * pA).toVector2F()
        _pointB = center + (rotation * pB).toVector2F()
        _pointC = center + (rotation * pC).toVector2F()
        _pointD = center + (rotation * pD).toVector2F()
    }

    override val center: Vector2F
        get() = _center

    override val rotation: ComplexF
        get() = _rotation

    override val sideLength: Float
        get() = _sideLength

    override val pointA: Vector2F
        get() = _pointA

    override val pointB: Vector2F
        get() = _pointB

    override val pointC: Vector2F
        get() = _pointC

    override val pointD: Vector2F
        get() = _pointD

    override val width: Float
        get() = _sideLength

    override val height: Float
        get() = _sideLength

    override val area: Float
        get() = _sideLength * _sideLength

    override val perimeter: Float
        get() = 4f * _sideLength

    override inline val sideCount: Int
        get() = 4

    override inline val interiorAngle: AngleF
        get() = AngleF((0.5 * PI).toFloat())

    override inline val exteriorAngle: AngleF
        get() = AngleF((0.5 * PI).toFloat())

    override val inradius: Float
        get() = 0.5f * _sideLength

    override val circumradius: Float
        get() = 0.7071068f * _sideLength

    override fun closestPointTo(point: Vector2F): Vector2F {
        val halfSideLength: Float = _sideLength * 0.5f
        val center: Vector2F = _center
        val rotation: ComplexF = _rotation
        val p1 = ComplexF.conjugate(rotation) *
                ComplexF(point.x - center.x, point.y - center.y)
        val p1X: Float = p1.real
        val p1Y: Float = p1.imaginary
        val p2 = ComplexF(
            if (p1X.absoluteValue > halfSideLength) halfSideLength.withSign(p1X) else p1X,
            if (p1Y.absoluteValue > halfSideLength) halfSideLength.withSign(p1Y) else p1Y
        )
        return center + (rotation * p2).toVector2F()
    }

    override operator fun contains(point: Vector2F): Boolean {
        val halfSideLength: Float = _sideLength * 0.5f
        val center: Vector2F = _center
        val p1 = ComplexF.conjugate(_rotation) *
                ComplexF(point.x - center.x, point.y - center.y)

        return (p1.real.absoluteValue <= halfSideLength) and
                (p1.imaginary.absoluteValue <= halfSideLength)
    }

    override fun copy(center: Vector2F, rotation: ComplexF, sideLength: Float) =
        MutableSquare(center, rotation, sideLength)

    override fun equals(other: Any?): Boolean = other is Square &&
            _center == other.center &&
            _rotation == other.rotation &&
            _sideLength == other.sideLength

    fun equals(other: MutableSquare): Boolean =
        _center == other._center &&
                _rotation == other._rotation &&
                _sideLength == other._sideLength

    override fun hashCode(): Int {
        val centerHash: Int = _center.hashCode()
        val rotationHash: Int = _rotation.hashCode()
        val sideLengthHash: Int = _sideLength.hashCode()

        return centerHash * 961 + rotationHash * 31 + sideLengthHash
    }

    override fun toString() =
        StringBuilder("Square(center=").append(_center)
            .append(", rotation=").append(_rotation)
            .append(", sideLength=").append(_sideLength).append(")")
            .toString()

    override operator fun component1(): Vector2F = _center

    override operator fun component2(): ComplexF = _rotation

    override operator fun component3(): Float = _sideLength
}