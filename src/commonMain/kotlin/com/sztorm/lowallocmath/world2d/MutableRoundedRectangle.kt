package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F
import com.sztorm.lowallocmath.Vector2FIterator
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.sqrt
import kotlin.math.withSign

class MutableRoundedRectangle(
    center: Vector2F, rotation: ComplexF, width: Float, height: Float, cornerRadius: Float
) : RoundedRectangle {
    private var _center: Vector2F = center
    private var _rotation: ComplexF = rotation
    private var _width: Float = width
    private var _height: Float = height
    private var _cornerRadius: Float = cornerRadius
    private var _pointA: Vector2F
    private var _pointB: Vector2F
    private var _pointC: Vector2F
    private var _pointD: Vector2F
    private var _pointE: Vector2F
    private var _pointF: Vector2F
    private var _pointG: Vector2F
    private var _pointH: Vector2F
    private var _cornerCenterA: Vector2F
    private var _cornerCenterB: Vector2F
    private var _cornerCenterC: Vector2F
    private var _cornerCenterD: Vector2F

    init {
        val halfWidth: Float = width * 0.5f
        val halfHeight: Float = height * 0.5f
        val halfWidthMinusRadius: Float = halfWidth - cornerRadius
        val halfHeightMinusRadius: Float = halfHeight - cornerRadius
        val pA = ComplexF(halfWidthMinusRadius, halfHeight)
        val pB = ComplexF(-halfWidthMinusRadius, halfHeight)
        val pC = ComplexF(-halfWidth, halfHeightMinusRadius)
        val pD = ComplexF(-halfWidth, -halfHeightMinusRadius)
        val pE = ComplexF(-halfWidthMinusRadius, -halfHeight)
        val pF = ComplexF(halfWidthMinusRadius, -halfHeight)
        val pG = ComplexF(halfWidth, -halfHeightMinusRadius)
        val pH = ComplexF(halfWidth, halfHeightMinusRadius)
        val ccA = ComplexF(halfWidthMinusRadius, halfHeightMinusRadius)
        val ccB = ComplexF(-halfWidthMinusRadius, halfHeightMinusRadius)
        val ccC = ComplexF(-halfWidthMinusRadius, -halfHeightMinusRadius)
        val ccD = ComplexF(halfWidthMinusRadius, -halfHeightMinusRadius)
        _pointA = center + (rotation * pA).toVector2F()
        _pointB = center + (rotation * pB).toVector2F()
        _pointC = center + (rotation * pC).toVector2F()
        _pointD = center + (rotation * pD).toVector2F()
        _pointE = center + (rotation * pE).toVector2F()
        _pointF = center + (rotation * pF).toVector2F()
        _pointG = center + (rotation * pG).toVector2F()
        _pointH = center + (rotation * pH).toVector2F()
        _cornerCenterA = center + (rotation * ccA).toVector2F()
        _cornerCenterB = center + (rotation * ccB).toVector2F()
        _cornerCenterC = center + (rotation * ccC).toVector2F()
        _cornerCenterD = center + (rotation * ccD).toVector2F()
    }

    override val center: Vector2F
        get() = _center

    override val rotation: ComplexF
        get() = _rotation

    override val width: Float
        get() = _width

    override val height: Float
        get() = _height

    override val cornerRadius: Float
        get() = _cornerRadius

    override val pointA: Vector2F
        get() = _pointA

    override val pointB: Vector2F
        get() = _pointB

    override val pointC: Vector2F
        get() = _pointC

    override val pointD: Vector2F
        get() = _pointD

    override val pointE: Vector2F
        get() = _pointE

    override val pointF: Vector2F
        get() = _pointF

    override val pointG: Vector2F
        get() = _pointG

    override val pointH: Vector2F
        get() = _pointH

    override val cornerCenterA: Vector2F
        get() = _cornerCenterA

    override val cornerCenterB: Vector2F
        get() = _cornerCenterB

    override val cornerCenterC: Vector2F
        get() = _cornerCenterC

    override val cornerCenterD: Vector2F
        get() = _cornerCenterD

    override val area: Float
        get() {
            val radius: Float = _cornerRadius
            val squaredRadius: Float = radius * radius

            return PI.toFloat() * squaredRadius + _width * _height - 4f * squaredRadius
        }

    override val perimeter: Float
        get() {
            val radius: Float = _cornerRadius

            return (2.0 * PI).toFloat() * radius + 2f * (_width + _height - 4f * radius)
        }

    override fun closestPointTo(point: Vector2F): Vector2F {
        val rotation: ComplexF = _rotation
        val center: Vector2F = _center
        val cornerRadius: Float = _cornerRadius
        val halfWidth: Float = _width * 0.5f
        val halfHeight: Float = _height * 0.5f
        val halfWidthMinusRadius: Float = halfWidth - cornerRadius
        val halfHeightMinusRadius: Float = halfHeight - cornerRadius
        val p1 = ComplexF.conjugate(rotation) *
                ComplexF(point.x - center.x, point.y - center.y)
        val p1X: Float = p1.real
        val p1Y: Float = p1.imaginary
        val p1XAbs: Float = abs(p1X)
        val p1YAbs: Float = abs(p1Y)
        val cornerCenterX: Float = halfWidthMinusRadius.withSign(p1X)
        val cornerCenterY: Float = halfHeightMinusRadius.withSign(p1Y)
        val dx: Float = p1X - cornerCenterX
        val dy: Float = p1Y - cornerCenterY
        val distance: Float = sqrt(dx * dx + dy * dy)
        val isOutOfCorner: Boolean = (p1YAbs > halfHeightMinusRadius) and
                (p1XAbs > halfWidthMinusRadius) and
                (distance > cornerRadius)

        return when {
            isOutOfCorner -> {
                val t: Float = cornerRadius / distance

                center + (rotation * ComplexF(
                    (cornerCenterX + dx * t),
                    (cornerCenterY + dy * t)
                )).toVector2F()
            }

            p1XAbs > halfWidth -> center + (rotation * ComplexF(
                halfWidth.withSign(p1X),
                p1Y
            )).toVector2F()

            p1YAbs > halfHeight -> center + (rotation * ComplexF(
                p1X,
                halfHeight.withSign(p1Y)
            )).toVector2F()

            else -> point
        }
    }

    override operator fun contains(point: Vector2F): Boolean {
        val rotation: ComplexF = _rotation
        val center: Vector2F = _center
        val cornerRadius: Float = _cornerRadius
        val halfWidth: Float = _width * 0.5f
        val halfHeight: Float = _height * 0.5f
        val halfWidthMinusRadius: Float = halfWidth - cornerRadius
        val halfHeightMinusRadius: Float = halfHeight - cornerRadius
        val p1 = ComplexF.conjugate(rotation) *
                ComplexF(point.x - center.x, point.y - center.y)
        val p1X: Float = p1.real
        val p1Y: Float = p1.imaginary
        val p1XAbs: Float = abs(p1X)
        val p1YAbs: Float = abs(p1Y)
        val cornerCenterX: Float = halfWidthMinusRadius.withSign(p1X)
        val cornerCenterY: Float = halfHeightMinusRadius.withSign(p1Y)
        val dx: Float = p1X - cornerCenterX
        val dy: Float = p1Y - cornerCenterY
        val distance: Float = sqrt(dx * dx + dy * dy)

        return (p1YAbs <= halfHeightMinusRadius) or
                (p1XAbs <= halfWidthMinusRadius) or
                (distance <= cornerRadius) and
                (p1XAbs <= halfWidth) and
                (p1YAbs <= halfHeight)
    }

    override fun pointIterator(): Vector2FIterator = PointIterator(this, index = 0)

    override fun cornerCenterIterator(): Vector2FIterator =
        CornerCenterIterator(this, index = 0)

    override fun copy(
        center: Vector2F, rotation: ComplexF, width: Float, height: Float, cornerRadius: Float
    ) = MutableRoundedRectangle(center, rotation, width, height, cornerRadius)

    override fun equals(other: Any?): Boolean = other is RoundedRectangle &&
            _center == other.center &&
            _rotation == other.rotation &&
            _width == other.width &&
            _height == other.height &&
            _cornerRadius == other.cornerRadius

    fun equals(other: MutableRoundedRectangle): Boolean =
        _center == other._center &&
                _rotation == other._rotation &&
                _width == other._width &&
                _height == other._height &&
                _cornerRadius == other._cornerRadius

    override fun hashCode(): Int {
        val centerHash: Int = _center.hashCode()
        val rotationHash: Int = _rotation.hashCode()
        val widthHash: Int = _width.hashCode()
        val heightHash: Int = _height.hashCode()
        val cornerRadiusHash: Int = _cornerRadius.hashCode()

        return centerHash * 923521 +
                rotationHash * 29791 +
                widthHash * 961 +
                heightHash * 31 +
                cornerRadiusHash
    }

    override fun toString() =
        StringBuilder("RoundedRectangle(center=").append(_center)
            .append(", rotation=").append(_rotation)
            .append(", width=").append(_width)
            .append(", height=").append(_height)
            .append(", cornerRadius=").append(_cornerRadius).append(")")
            .toString()

    override operator fun component1(): Vector2F = _center

    override operator fun component2(): ComplexF = _rotation

    override operator fun component3(): Float = _width

    override operator fun component4(): Float = _height

    override operator fun component5(): Float = _cornerRadius

    private class CornerCenterIterator(
        private val rectangle: MutableRoundedRectangle,
        private var index: Int
    ) : Vector2FIterator() {
        override fun hasNext(): Boolean = index < 4

        override fun nextVector2F(): Vector2F = when (index++) {
            0 -> rectangle._cornerCenterA
            1 -> rectangle._cornerCenterB
            2 -> rectangle._cornerCenterC
            3 -> rectangle._cornerCenterD
            else -> throw NoSuchElementException("${index - 1}")
        }
    }

    private class PointIterator(
        private val rectangle: MutableRoundedRectangle,
        private var index: Int
    ) : Vector2FIterator() {
        override fun hasNext(): Boolean = index < 8

        override fun nextVector2F(): Vector2F = when (index++) {
            0 -> rectangle._pointA
            1 -> rectangle._pointB
            2 -> rectangle._pointC
            3 -> rectangle._pointD
            4 -> rectangle._pointE
            5 -> rectangle._pointF
            6 -> rectangle._pointG
            7 -> rectangle._pointH
            else -> throw NoSuchElementException("${index - 1}")
        }
    }
}