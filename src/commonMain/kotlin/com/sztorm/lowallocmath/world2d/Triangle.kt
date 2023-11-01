package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.AngleF
import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F
import com.sztorm.lowallocmath.Vector2FIterator

fun Triangle(pointA: Vector2F, pointB: Vector2F, pointC: Vector2F): Triangle =
    MutableTriangle(pointA, pointB, pointC)

interface Triangle : TriangleShape, Transformable {
    override fun movedBy(offset: Vector2F): Triangle

    override fun movedTo(position: Vector2F): Triangle

    override fun rotatedBy(angle: AngleF): Triangle = rotatedBy(ComplexF.fromAngle(angle))

    override fun rotatedBy(rotation: ComplexF): Triangle

    override fun rotatedTo(angle: AngleF): Triangle = rotatedTo(ComplexF.fromAngle(angle))

    override fun rotatedTo(rotation: ComplexF): Triangle

    override fun rotatedAroundPointBy(point: Vector2F, angle: AngleF): Triangle =
        rotatedAroundPointBy(point, ComplexF.fromAngle(angle))

    override fun rotatedAroundPointBy(point: Vector2F, rotation: ComplexF): Triangle

    override fun rotatedAroundPointTo(point: Vector2F, angle: AngleF): Triangle =
        rotatedAroundPointTo(point, ComplexF.fromAngle(angle))

    override fun rotatedAroundPointTo(point: Vector2F, rotation: ComplexF): Triangle

    override fun scaledBy(factor: Float): Triangle

    override fun transformedBy(offset: Vector2F, angle: AngleF): Triangle =
        transformedBy(offset, ComplexF.fromAngle(angle))

    override fun transformedBy(offset: Vector2F, rotation: ComplexF): Triangle

    override fun transformedBy(offset: Vector2F, angle: AngleF, factor: Float): Triangle =
        transformedBy(offset, ComplexF.fromAngle(angle), factor)

    override fun transformedBy(offset: Vector2F, rotation: ComplexF, factor: Float): Triangle

    override fun transformedTo(position: Vector2F, angle: AngleF): Triangle =
        transformedTo(position, ComplexF.fromAngle(angle))

    override fun transformedTo(position: Vector2F, rotation: ComplexF): Triangle

    fun closestPointTo(point: Vector2F): Vector2F

    operator fun contains(point: Vector2F): Boolean

    fun pointIterator(): Vector2FIterator

    fun copy(
        pointA: Vector2F = this.pointA,
        pointB: Vector2F = this.pointB,
        pointC: Vector2F = this.pointC
    ): Triangle

    operator fun component1(): Vector2F

    operator fun component2(): Vector2F

    operator fun component3(): Vector2F
}