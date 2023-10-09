package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.AngleF
import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F
import com.sztorm.lowallocmath.Vector2FIterator

fun RoundedRectangle(
    center: Vector2F, rotation: ComplexF, width: Float, height: Float, cornerRadius: Float
): RoundedRectangle = MutableRoundedRectangle(center, rotation, width, height, cornerRadius)

interface RoundedRectangle : RoundedRectangleShape, Transformable {
    val center: Vector2F

    override val position: Vector2F
        get() = center

    override fun movedBy(offset: Vector2F): RoundedRectangle

    override fun movedTo(position: Vector2F): RoundedRectangle

    override fun rotatedBy(angle: AngleF): RoundedRectangle = rotatedBy(ComplexF.fromAngle(angle))

    override fun rotatedBy(rotation: ComplexF): RoundedRectangle

    override fun rotatedTo(angle: AngleF): RoundedRectangle = rotatedTo(ComplexF.fromAngle(angle))

    override fun rotatedTo(rotation: ComplexF): RoundedRectangle

    override fun scaledBy(factor: Float): RoundedRectangle

    override fun transformedBy(offset: Vector2F, angle: AngleF): RoundedRectangle =
        transformedBy(offset, ComplexF.fromAngle(angle))

    override fun transformedBy(offset: Vector2F, rotation: ComplexF): RoundedRectangle

    override fun transformedBy(offset: Vector2F, angle: AngleF, factor: Float): RoundedRectangle =
        transformedBy(offset, ComplexF.fromAngle(angle), factor)

    override fun transformedBy(
        offset: Vector2F,
        rotation: ComplexF,
        factor: Float
    ): RoundedRectangle

    override fun transformedTo(position: Vector2F, angle: AngleF): RoundedRectangle =
        transformedTo(position, ComplexF.fromAngle(angle))

    override fun transformedTo(position: Vector2F, rotation: ComplexF): RoundedRectangle

    fun closestPointTo(point: Vector2F): Vector2F

    operator fun contains(point: Vector2F): Boolean

    fun pointIterator(): Vector2FIterator

    fun cornerCenterIterator(): Vector2FIterator

    fun copy(
        center: Vector2F = this.center,
        rotation: ComplexF = this.rotation,
        width: Float = this.width,
        height: Float = this.height,
        cornerRadius: Float = this.cornerRadius
    ): RoundedRectangle

    operator fun component1(): Vector2F

    operator fun component2(): ComplexF

    operator fun component3(): Float

    operator fun component4(): Float

    operator fun component5(): Float
}