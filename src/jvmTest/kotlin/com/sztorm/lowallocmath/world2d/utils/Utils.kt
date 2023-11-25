package com.sztorm.lowallocmath.world2d.utils

import com.sztorm.lowallocmath.world2d.*
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.test.assertEquals

@OptIn(ExperimentalContracts::class)
inline fun assertImmutabilityOf(annulus: Annulus, block: () -> Unit) {
    contract { callsInPlace(block, InvocationKind.EXACTLY_ONCE) }
    val copy: Annulus = annulus.copy()

    block()
    assertEquals(copy, annulus, "Annulus must not be mutated.")
}

@OptIn(ExperimentalContracts::class)
inline fun assertImmutabilityOf(circle: Circle, block: () -> Unit) {
    contract { callsInPlace(block, InvocationKind.EXACTLY_ONCE) }
    val copy: Circle = circle.copy()

    block()
    assertEquals(copy, circle, "Circle must not be mutated.")
}

@OptIn(ExperimentalContracts::class)
inline fun assertImmutabilityOf(lineSegment: LineSegment, block: () -> Unit) {
    contract { callsInPlace(block, InvocationKind.EXACTLY_ONCE) }
    val copy: LineSegment = lineSegment.copy()

    block()
    assertEquals(copy, lineSegment, "LineSegment must not be mutated.")
}

@OptIn(ExperimentalContracts::class)
inline fun assertImmutabilityOf(ray: Ray, block: () -> Unit) {
    contract { callsInPlace(block, InvocationKind.EXACTLY_ONCE) }
    val copy: Ray = ray.copy()

    block()
    assertEquals(copy, ray, "Ray must not be mutated.")
}

@OptIn(ExperimentalContracts::class)
inline fun assertImmutabilityOf(rectangle: Rectangle, block: () -> Unit) {
    contract { callsInPlace(block, InvocationKind.EXACTLY_ONCE) }
    val copy: Rectangle = rectangle.copy()

    block()
    assertEquals(copy, rectangle, "Rectangle must not be mutated.")
}

@OptIn(ExperimentalContracts::class)
inline fun assertImmutabilityOf(regularPolygon: RegularPolygon, block: () -> Unit) {
    contract { callsInPlace(block, InvocationKind.EXACTLY_ONCE) }
    val copy: RegularPolygon = regularPolygon.copy()

    block()
    assertEquals(copy, regularPolygon, "RegularPolygon must not be mutated.")
}

@OptIn(ExperimentalContracts::class)
inline fun assertImmutabilityOf(regularTriangle: RegularTriangle, block: () -> Unit) {
    contract { callsInPlace(block, InvocationKind.EXACTLY_ONCE) }
    val copy: RegularTriangle = regularTriangle.copy()

    block()
    assertEquals(copy, regularTriangle, "RegularTriangle must not be mutated.")
}

@OptIn(ExperimentalContracts::class)
inline fun assertImmutabilityOf(rectangle: RoundedRectangle, block: () -> Unit) {
    contract { callsInPlace(block, InvocationKind.EXACTLY_ONCE) }
    val copy: RoundedRectangle = rectangle.copy()

    block()
    assertEquals(copy, rectangle, "RoundedRectangle must not be mutated.")
}

@OptIn(ExperimentalContracts::class)
inline fun assertImmutabilityOf(square: Square, block: () -> Unit) {
    contract { callsInPlace(block, InvocationKind.EXACTLY_ONCE) }
    val copy: Square = square.copy()

    block()
    assertEquals(copy, square, "Square must not be mutated.")
}

@OptIn(ExperimentalContracts::class)
inline fun assertImmutabilityOf(triangle: Triangle, block: () -> Unit) {
    contract { callsInPlace(block, InvocationKind.EXACTLY_ONCE) }
    val copy: Triangle = triangle.copy()

    block()
    assertEquals(copy, triangle, "Triangle must not be mutated.")
}