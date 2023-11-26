package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.AngleF
import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F
import com.sztorm.lowallocmath.Vector2FIterator

fun Triangle(pointA: Vector2F, pointB: Vector2F, pointC: Vector2F): Triangle =
    MutableTriangle(pointA, pointB, pointC)

interface Triangle : TriangleShape, Transformable {
    val pointA: Vector2F
    val pointB: Vector2F
    val pointC: Vector2F
    val centroid: Vector2F
    val orthocenter: Vector2F
    val incenter: Vector2F
    val circumcenter: Vector2F

    override fun movedBy(offset: Vector2F): Triangle

    override fun movedTo(position: Vector2F): Triangle

    override fun rotatedBy(rotation: AngleF): Triangle = rotatedBy(ComplexF.fromAngle(rotation))

    override fun rotatedBy(rotation: ComplexF): Triangle

    override fun rotatedTo(orientation: AngleF): Triangle =
        rotatedTo(ComplexF.fromAngle(orientation))

    override fun rotatedTo(orientation: ComplexF): Triangle

    override fun rotatedAroundPointBy(point: Vector2F, rotation: AngleF): Triangle =
        rotatedAroundPointBy(point, ComplexF.fromAngle(rotation))

    override fun rotatedAroundPointBy(point: Vector2F, rotation: ComplexF): Triangle

    override fun rotatedAroundPointTo(point: Vector2F, orientation: AngleF): Triangle =
        rotatedAroundPointTo(point, ComplexF.fromAngle(orientation))

    override fun rotatedAroundPointTo(point: Vector2F, orientation: ComplexF): Triangle

    override fun scaledBy(factor: Float): Triangle

    override fun dilatedBy(point: Vector2F, factor: Float): Triangle

    override fun transformedBy(offset: Vector2F, rotation: AngleF): Triangle =
        transformedBy(offset, ComplexF.fromAngle(rotation))

    override fun transformedBy(offset: Vector2F, rotation: ComplexF): Triangle

    override fun transformedBy(offset: Vector2F, rotation: AngleF, factor: Float): Triangle =
        transformedBy(offset, ComplexF.fromAngle(rotation), factor)

    override fun transformedBy(offset: Vector2F, rotation: ComplexF, factor: Float): Triangle

    override fun transformedTo(position: Vector2F, orientation: AngleF): Triangle =
        transformedTo(position, ComplexF.fromAngle(orientation))

    override fun transformedTo(position: Vector2F, orientation: ComplexF): Triangle

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