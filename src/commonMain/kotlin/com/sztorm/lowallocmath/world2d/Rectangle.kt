package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.AngleF
import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F
import com.sztorm.lowallocmath.Vector2FIterator

fun Rectangle(center: Vector2F, rotation: ComplexF, width: Float, height: Float): Rectangle =
    MutableRectangle(center, rotation, width, height)

interface Rectangle : RectangleShape, Transformable {
    val center: Vector2F

    override val position: Vector2F
        get() = center

    override fun movedBy(offset: Vector2F): Rectangle

    override fun movedTo(position: Vector2F): Rectangle

    override fun rotatedBy(angle: AngleF): Rectangle = rotatedBy(ComplexF.fromAngle(angle))

    override fun rotatedBy(rotation: ComplexF): Rectangle

    override fun rotatedTo(angle: AngleF): Rectangle = rotatedTo(ComplexF.fromAngle(angle))

    override fun rotatedTo(rotation: ComplexF): Rectangle

    override fun rotatedAroundPointBy(point: Vector2F, angle: AngleF): Rectangle =
        rotatedAroundPointBy(point, ComplexF.fromAngle(angle))

    override fun rotatedAroundPointBy(point: Vector2F, rotation: ComplexF): Rectangle

    override fun rotatedAroundPointTo(point: Vector2F, angle: AngleF): Rectangle =
        rotatedAroundPointTo(point, ComplexF.fromAngle(angle))

    override fun rotatedAroundPointTo(point: Vector2F, rotation: ComplexF): Rectangle

    override fun scaledBy(factor: Float): Rectangle

    override fun transformedBy(offset: Vector2F, angle: AngleF): Rectangle =
        transformedBy(offset, ComplexF.fromAngle(angle))

    override fun transformedBy(offset: Vector2F, rotation: ComplexF): Rectangle

    override fun transformedBy(offset: Vector2F, angle: AngleF, factor: Float): Rectangle =
        transformedBy(offset, ComplexF.fromAngle(angle), factor)

    override fun transformedBy(offset: Vector2F, rotation: ComplexF, factor: Float): Rectangle

    override fun transformedTo(position: Vector2F, angle: AngleF): Rectangle =
        transformedTo(position, ComplexF.fromAngle(angle))

    override fun transformedTo(position: Vector2F, rotation: ComplexF): Rectangle

    fun closestPointTo(point: Vector2F): Vector2F

    operator fun contains(point: Vector2F): Boolean

    fun pointIterator(): Vector2FIterator

    fun copy(
        center: Vector2F = this.center,
        rotation: ComplexF = this.rotation,
        width: Float = this.width,
        height: Float = this.height
    ): Rectangle

    operator fun component1(): Vector2F

    operator fun component2(): ComplexF

    operator fun component3(): Float

    operator fun component4(): Float
}