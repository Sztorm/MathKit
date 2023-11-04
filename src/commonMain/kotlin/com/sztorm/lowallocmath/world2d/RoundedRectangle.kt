package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.AngleF
import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F
import com.sztorm.lowallocmath.Vector2FIterator

fun RoundedRectangle(
    center: Vector2F, orientation: ComplexF, width: Float, height: Float, cornerRadius: Float
): RoundedRectangle = MutableRoundedRectangle(center, orientation, width, height, cornerRadius)

interface RoundedRectangle : RoundedRectangleShape, Transformable {
    val center: Vector2F

    override val position: Vector2F
        get() = center

    override fun movedBy(offset: Vector2F): RoundedRectangle

    override fun movedTo(position: Vector2F): RoundedRectangle

    override fun rotatedBy(rotation: AngleF): RoundedRectangle =
        rotatedBy(ComplexF.fromAngle(rotation))

    override fun rotatedBy(rotation: ComplexF): RoundedRectangle

    override fun rotatedTo(orientation: AngleF): RoundedRectangle =
        rotatedTo(ComplexF.fromAngle(orientation))

    override fun rotatedTo(orientation: ComplexF): RoundedRectangle

    override fun rotatedAroundPointBy(point: Vector2F, rotation: AngleF): RoundedRectangle =
        rotatedAroundPointBy(point, ComplexF.fromAngle(rotation))

    override fun rotatedAroundPointBy(point: Vector2F, rotation: ComplexF): RoundedRectangle

    override fun rotatedAroundPointTo(point: Vector2F, orientation: AngleF): RoundedRectangle =
        rotatedAroundPointTo(point, ComplexF.fromAngle(orientation))

    override fun rotatedAroundPointTo(point: Vector2F, orientation: ComplexF): RoundedRectangle

    override fun scaledBy(factor: Float): RoundedRectangle

    override fun dilatedBy(point: Vector2F, factor: Float): RoundedRectangle

    override fun transformedBy(offset: Vector2F, rotation: AngleF): RoundedRectangle =
        transformedBy(offset, ComplexF.fromAngle(rotation))

    override fun transformedBy(offset: Vector2F, rotation: ComplexF): RoundedRectangle

    override fun transformedBy(
        offset: Vector2F, rotation: AngleF, factor: Float
    ): RoundedRectangle = transformedBy(offset, ComplexF.fromAngle(rotation), factor)

    override fun transformedBy(
        offset: Vector2F, rotation: ComplexF, factor: Float
    ): RoundedRectangle

    override fun transformedTo(position: Vector2F, orientation: AngleF): RoundedRectangle =
        transformedTo(position, ComplexF.fromAngle(orientation))

    override fun transformedTo(position: Vector2F, orientation: ComplexF): RoundedRectangle

    fun closestPointTo(point: Vector2F): Vector2F

    operator fun contains(point: Vector2F): Boolean

    fun pointIterator(): Vector2FIterator

    fun cornerCenterIterator(): Vector2FIterator

    fun copy(
        center: Vector2F = this.center,
        orientation: ComplexF = this.orientation,
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