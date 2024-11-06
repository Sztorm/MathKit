package com.sztorm.lowallocmath.euclidean2d.utils

import com.sztorm.lowallocmath.euclidean2d.*
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.test.assertEquals
import kotlin.test.assertTrue

private fun messagePrefix(message: String?) = if (message == null) "" else "$message. "

/**
 * Asserts that the [expected] value is approximately equal to the [actual] value within an
 * [absoluteTolerance], with an optional [message].
 */
fun assertApproximation(
    expected: Annulus, actual: Annulus, absoluteTolerance: Float = 0.00001f, message: String? = null
) {
    val mainMessage: String =
        """
        Expected <Annulus(
            center=${expected.center},
            orientation=${expected.orientation},
            outerRadius=${expected.outerRadius},
            innerRadius=${expected.innerRadius}
        )> with absolute tolerance <$absoluteTolerance>,
        actual <Annulus(
            center=${actual.center},
            orientation=${actual.orientation},
            outerRadius=${actual.outerRadius},
            innerRadius=${actual.innerRadius}
        )>.
        """.trimIndent()

    assertTrue(
        AnnulusTests.areApproximatelyEqual(expected, actual, absoluteTolerance),
        messagePrefix(message) + mainMessage
    )
}

/**
 * Asserts that the [expected] value is approximately equal to the [actual] value within an
 * [absoluteTolerance], with an optional [message].
 */
fun assertApproximation(
    expected: Circle, actual: Circle, absoluteTolerance: Float = 0.00001f, message: String? = null
) {
    val mainMessage: String =
        """
        Expected <Circle(
            center=${expected.center},
            orientation=${expected.orientation},
            radius=${expected.radius}
        )> with absolute tolerance <$absoluteTolerance>,
        actual <Circle(
            center=${actual.center},
            orientation=${actual.orientation},
            radius=${actual.radius}
        )>.
        """.trimIndent()

    assertTrue(
        CircleTests.areApproximatelyEqual(expected, actual, absoluteTolerance),
        messagePrefix(message) + mainMessage
    )
}

/**
 * Asserts that the [expected] value is approximately equal to the [actual] value within an
 * [absoluteTolerance], with an optional [message].
 */
fun assertApproximation(
    expected: LineSegment,
    actual: LineSegment,
    absoluteTolerance: Float = 0.00001f,
    message: String? = null
) {
    val mainMessage: String =
        """
        Expected <LineSegment(
            center=${expected.center},
            orientation=${expected.orientation},
            length=${expected.length},
            pointA=${expected.pointA},
            pointB=${expected.pointB}
        )> with absolute tolerance <$absoluteTolerance>,
        actual <LineSegment(
            center=${actual.center},
            orientation=${actual.orientation},
            length=${actual.length},
            pointA=${actual.pointA},
            pointB=${actual.pointB}
        )>.
        """.trimIndent()

    assertTrue(
        LineSegmentTests.areApproximatelyEqual(expected, actual, absoluteTolerance),
        messagePrefix(message) + mainMessage
    )
}

/**
 * Asserts that the [expected] value is approximately equal to the [actual] value within an
 * [absoluteTolerance], with an optional [message].
 */
fun assertApproximation(
    expected: Ray, actual: Ray, absoluteTolerance: Float = 0.00001f, message: String? = null
) {
    val mainMessage: String =
        """
        Expected <Ray(
            origin=${expected.origin},
            direction=${expected.direction}
        )> with absolute tolerance <$absoluteTolerance>,
        actual <Ray(
            origin=${actual.origin},
            direction=${actual.direction}
        )>.
        """.trimIndent()

    assertTrue(
        RayTests.areApproximatelyEqual(expected, actual, absoluteTolerance),
        messagePrefix(message) + mainMessage
    )
}

/**
 * Asserts that the [expected] value is approximately equal to the [actual] value within an
 * [absoluteTolerance], with an optional [message].
 */
fun assertApproximation(
    expected: Rectangle,
    actual: Rectangle,
    absoluteTolerance: Float = 0.00001f,
    message: String? = null
) {
    val mainMessage: String =
        """
        Expected <Rectangle(
            center=${expected.center},
            orientation=${expected.orientation},
            width=${expected.width},
            height=${expected.height},
            pointA=${expected.pointA},
            pointB=${expected.pointB},
            pointC=${expected.pointC},
            pointD=${expected.pointD}
        )> with absolute tolerance <$absoluteTolerance>,
        actual <Rectangle(
            center=${actual.center},
            orientation=${actual.orientation},
            width=${actual.width},
            height=${actual.height},
            pointA=${actual.pointA},
            pointB=${actual.pointB},
            pointC=${actual.pointC},
            pointD=${actual.pointD}
        )>.
        """.trimIndent()

    assertTrue(
        RectangleTests.areApproximatelyEqual(expected, actual, absoluteTolerance),
        messagePrefix(message) + mainMessage
    )
}

/**
 * Asserts that the [expected] value is approximately equal to the [actual] value within an
 * [absoluteTolerance], with an optional [message].
 */
fun assertApproximation(
    expected: RegularPolygon,
    actual: RegularPolygon,
    absoluteTolerance: Float = 0.00001f,
    message: String? = null
) {
    val mainMessage: String =
        """
        Expected <RegularPolygon(
            center=${expected.center},
            orientation=${expected.orientation},
            sideLength=${expected.sideLength},
            points=${expected.points},
            circumradius=${expected.circumradius}
            inradius=${expected.inradius}
        )> with absolute tolerance <$absoluteTolerance>,
        actual <RegularPolygon(
            center=${actual.center},
            orientation=${actual.orientation},
            sideLength=${actual.sideLength},
            points=${actual.points},
            circumradius=${actual.circumradius}
            inradius=${actual.inradius}
        )>.
        """.trimIndent()

    assertTrue(
        RegularPolygonTests.areApproximatelyEqual(expected, actual, absoluteTolerance),
        messagePrefix(message) + mainMessage
    )
}

/**
 * Asserts that the [expected] value is approximately equal to the [actual] value within an
 * [absoluteTolerance], with an optional [message].
 */
fun assertApproximation(
    expected: RegularTriangle,
    actual: RegularTriangle,
    absoluteTolerance: Float = 0.00001f,
    message: String? = null
) {
    val mainMessage: String =
        """
        Expected <RegularTriangle(
            center=${expected.center},
            orientation=${expected.orientation},
            sideLength=${expected.sideLength},
            pointA=${expected.pointA},
            pointB=${expected.pointB},
            pointC=${expected.pointC}
        )> with absolute tolerance <$absoluteTolerance>,
        actual <RegularTriangle(
            center=${actual.center},
            orientation=${actual.orientation},
            sideLength=${actual.sideLength},
            pointA=${actual.pointA},
            pointB=${actual.pointB},
            pointC=${actual.pointC}
        )>.
        """.trimIndent()

    assertTrue(
        RegularTriangleTests.areApproximatelyEqual(expected, actual, absoluteTolerance),
        messagePrefix(message) + mainMessage
    )
}

/**
 * Asserts that the [expected] value is approximately equal to the [actual] value within an
 * [absoluteTolerance], with an optional [message].
 */
fun assertApproximation(
    expected: RoundedRectangle,
    actual: RoundedRectangle,
    absoluteTolerance: Float = 0.00001f,
    message: String? = null
) {
    val mainMessage: String =
        """
        Expected <RoundedRectangle(
            center=${expected.center},
            orientation=${expected.orientation},
            width=${expected.width},
            height=${expected.height},
            cornerRadius=${expected.cornerRadius},
            pointA=${expected.pointA},
            pointB=${expected.pointB},
            pointC=${expected.pointC},
            pointD=${expected.pointD},
            pointE=${expected.pointE},
            pointF=${expected.pointF},
            pointG=${expected.pointG},
            pointH=${expected.pointH},
            cornerCenterA=${expected.cornerCenterA},
            cornerCenterB=${expected.cornerCenterB},
            cornerCenterC=${expected.cornerCenterC},
            cornerCenterD=${expected.cornerCenterD}
        )> with absolute tolerance <$absoluteTolerance>,
        actual <RoundedRectangle(
            center=${actual.center},
            orientation=${actual.orientation},
            width=${actual.width},
            height=${actual.height},
            cornerRadius=${actual.cornerRadius},
            pointA=${actual.pointA},
            pointB=${actual.pointB},
            pointC=${actual.pointC},
            pointD=${actual.pointD},
            pointE=${actual.pointE},
            pointF=${actual.pointF},
            pointG=${actual.pointG},
            pointH=${actual.pointH},
            cornerCenterA=${actual.cornerCenterA},
            cornerCenterB=${actual.cornerCenterB},
            cornerCenterC=${actual.cornerCenterC},
            cornerCenterD=${actual.cornerCenterD}
        )>.
        """.trimIndent()

    assertTrue(
        RoundedRectangleTests.areApproximatelyEqual(expected, actual, absoluteTolerance),
        messagePrefix(message) + mainMessage
    )
}

/**
 * Asserts that the [expected] value is approximately equal to the [actual] value within an
 * [absoluteTolerance], with an optional [message].
 */
fun assertApproximation(
    expected: Square, actual: Square, absoluteTolerance: Float = 0.00001f, message: String? = null
) {
    val mainMessage: String =
        """
        Expected <Square(
            center=${expected.center},
            orientation=${expected.orientation},
            sideLength=${expected.sideLength},
            pointA=${expected.pointA},
            pointB=${expected.pointB},
            pointC=${expected.pointC},
            pointD=${expected.pointD}
        )> with absolute tolerance <$absoluteTolerance>,
        actual <Square(
            center=${actual.center},
            orientation=${actual.orientation},
            sideLength=${actual.sideLength},
            pointA=${actual.pointA},
            pointB=${actual.pointB},
            pointC=${actual.pointC},
            pointD=${actual.pointD}
        )>.
        """.trimIndent()

    assertTrue(
        SquareTests.areApproximatelyEqual(expected, actual, absoluteTolerance),
        messagePrefix(message) + mainMessage
    )
}

/**
 * Asserts that the [expected] value is approximately equal to the [actual] value within an
 * [absoluteTolerance], with an optional [message].
 */
fun assertApproximation(
    expected: Triangle,
    actual: Triangle,
    absoluteTolerance: Float = 0.00001f,
    message: String? = null
) {
    val mainMessage: String =
        """
        Expected <Triangle(
            centroid=${expected.centroid},
            pathRotorA=${expected.pathRotorA},
            pointDistanceA=${expected.pointDistanceA},
            pathRotorAB=${expected.pathRotorAB},
            pointDistanceB=${expected.pointDistanceB},
            pathRotorAC=${expected.pathRotorAC},
            pointDistanceC=${expected.pointDistanceC},
            pointA=${expected.pointA},
            pointB=${expected.pointB},
            pointC=${expected.pointC}
        )> with absolute tolerance <$absoluteTolerance>,
        actual <Triangle(
            centroid=${actual.centroid},
            pathRotorA=${actual.pathRotorA},
            pointDistanceA=${actual.pointDistanceA},
            pathRotorAB=${actual.pathRotorAB},
            pointDistanceB=${actual.pointDistanceB},
            pathRotorAC=${actual.pathRotorAC},
            pointDistanceC=${actual.pointDistanceC},
            pointA=${actual.pointA},
            pointB=${actual.pointB},
            pointC=${actual.pointC}
        )>.
        """.trimIndent()

    assertTrue(
        TriangleTests.areApproximatelyEqual(expected, actual, absoluteTolerance),
        messagePrefix(message) + mainMessage
    )
}

/**
 * Asserts that the [expected] value is equal to the [actual] value, with an optional [message].
 */
fun assertEquals(expected: Annulus, actual: Annulus, message: String? = null) {
    val mainMessage: String =
        """
        Expected <Annulus(
            center=${expected.center},
            orientation=${expected.orientation},
            outerRadius=${expected.outerRadius},
            innerRadius=${expected.innerRadius}
        )>,
        actual <Annulus(
            center=${actual.center},
            orientation=${actual.orientation},
            outerRadius=${actual.outerRadius},
            innerRadius=${actual.innerRadius}
        )>.
        """.trimIndent()

    assertTrue(
        AnnulusTests.areEqual(expected, actual),
        messagePrefix(message) + mainMessage
    )
}

/**
 * Asserts that the [expected] value is equal to the [actual] value, with an optional [message].
 */
fun assertEquals(expected: Circle, actual: Circle, message: String? = null) {
    val mainMessage: String =
        """
        Expected <Circle(
            center=${expected.center},
            orientation=${expected.orientation},
            radius=${expected.radius}
        )>,
        actual <Circle(
            center=${actual.center},
            orientation=${actual.orientation},
            radius=${actual.radius}
        )>.
        """.trimIndent()

    assertTrue(
        CircleTests.areEqual(expected, actual),
        messagePrefix(message) + mainMessage
    )
}

/**
 * Asserts that the [expected] value is equal to the [actual] value, with an optional [message].
 */
fun assertEquals(expected: LineSegment, actual: LineSegment, message: String? = null) {
    val mainMessage: String =
        """
        Expected <LineSegment(
            center=${expected.center},
            orientation=${expected.orientation},
            length=${expected.length},
            pointA=${expected.pointA},
            pointB=${expected.pointB}
        )>,
        actual <LineSegment(
            center=${actual.center},
            orientation=${actual.orientation},
            length=${actual.length},
            pointA=${actual.pointA},
            pointB=${actual.pointB}
        )>.
        """.trimIndent()

    assertTrue(
        LineSegmentTests.areEqual(expected, actual),
        messagePrefix(message) + mainMessage
    )
}

/**
 * Asserts that the [expected] value is equal to the [actual] value, with an optional [message].
 */
fun assertEquals(expected: Ray, actual: Ray, message: String? = null) {
    val mainMessage: String =
        """
        Expected <Ray(
            origin=${expected.origin},
            direction=${expected.direction}
        )>,
        actual <Ray(
            origin=${actual.origin},
            direction=${actual.direction}
        )>.
        """.trimIndent()

    assertTrue(
        RayTests.areEqual(expected, actual),
        messagePrefix(message) + mainMessage
    )
}

/**
 * Asserts that the [expected] value is equal to the [actual] value, with an optional [message].
 */
fun assertEquals(expected: Rectangle, actual: Rectangle, message: String? = null) {
    val mainMessage: String =
        """
        Expected <Rectangle(
            center=${expected.center},
            orientation=${expected.orientation},
            width=${expected.width},
            height=${expected.height},
            pointA=${expected.pointA},
            pointB=${expected.pointB},
            pointC=${expected.pointC},
            pointD=${expected.pointD}
        )>,
        actual <Rectangle(
            center=${actual.center},
            orientation=${actual.orientation},
            width=${actual.width},
            height=${actual.height},
            pointA=${actual.pointA},
            pointB=${actual.pointB},
            pointC=${actual.pointC},
            pointD=${actual.pointD}
        )>.
        """.trimIndent()

    assertTrue(
        RectangleTests.areEqual(expected, actual),
        messagePrefix(message) + mainMessage
    )
}

/**
 * Asserts that the [expected] value is equal to the [actual] value, with an optional [message].
 */
fun assertEquals(
    expected: RegularPolygon, actual: RegularPolygon, message: String? = null
) {
    val mainMessage: String =
        """
        Expected <RegularPolygon(
            center=${expected.center},
            orientation=${expected.orientation},
            sideLength=${expected.sideLength},
            points=${expected.points},
            circumradius=${expected.circumradius}
            inradius=${expected.inradius}
        )>,
        actual <RegularPolygon(
            center=${actual.center},
            orientation=${actual.orientation},
            sideLength=${actual.sideLength},
            points=${actual.points},
            circumradius=${actual.circumradius}
            inradius=${actual.inradius}
        )>.
        """.trimIndent()

    assertTrue(
        RegularPolygonTests.areEqual(expected, actual),
        messagePrefix(message) + mainMessage
    )
}

/**
 * Asserts that the [expected] value is equal to the [actual] value, with an optional [message].
 */
fun assertEquals(
    expected: RegularTriangle, actual: RegularTriangle, message: String? = null
) {
    val mainMessage: String =
        """
        Expected <RegularTriangle(
            center=${expected.center},
            orientation=${expected.orientation},
            sideLength=${expected.sideLength},
            pointA=${expected.pointA},
            pointB=${expected.pointB},
            pointC=${expected.pointC}
        )>,
        actual <RegularTriangle(
            center=${actual.center},
            orientation=${actual.orientation},
            sideLength=${actual.sideLength},
            pointA=${actual.pointA},
            pointB=${actual.pointB},
            pointC=${actual.pointC}
        )>.
        """.trimIndent()

    assertTrue(
        RegularTriangleTests.areEqual(expected, actual),
        messagePrefix(message) + mainMessage
    )
}

/**
 * Asserts that the [expected] value is equal to the [actual] value, with an optional [message].
 */
fun assertEquals(
    expected: RoundedRectangle, actual: RoundedRectangle, message: String? = null
) {
    val mainMessage: String =
        """
        Expected <RoundedRectangle(
            center=${expected.center},
            orientation=${expected.orientation},
            width=${expected.width},
            height=${expected.height},
            cornerRadius=${expected.cornerRadius},
            pointA=${expected.pointA},
            pointB=${expected.pointB},
            pointC=${expected.pointC},
            pointD=${expected.pointD},
            pointE=${expected.pointE},
            pointF=${expected.pointF},
            pointG=${expected.pointG},
            pointH=${expected.pointH},
            cornerCenterA=${expected.cornerCenterA},
            cornerCenterB=${expected.cornerCenterB},
            cornerCenterC=${expected.cornerCenterC},
            cornerCenterD=${expected.cornerCenterD}
        )>,
        actual <RoundedRectangle(
            center=${actual.center},
            orientation=${actual.orientation},
            width=${actual.width},
            height=${actual.height},
            cornerRadius=${actual.cornerRadius},
            pointA=${actual.pointA},
            pointB=${actual.pointB},
            pointC=${actual.pointC},
            pointD=${actual.pointD},
            pointE=${actual.pointE},
            pointF=${actual.pointF},
            pointG=${actual.pointG},
            pointH=${actual.pointH},
            cornerCenterA=${actual.cornerCenterA},
            cornerCenterB=${actual.cornerCenterB},
            cornerCenterC=${actual.cornerCenterC},
            cornerCenterD=${actual.cornerCenterD}
        )>.
        """.trimIndent()

    assertTrue(
        RoundedRectangleTests.areEqual(expected, actual),
        messagePrefix(message) + mainMessage
    )
}

/**
 * Asserts that the [expected] value is equal to the [actual] value, with an optional [message].
 */
fun assertEquals(expected: Square, actual: Square, message: String? = null) {
    val mainMessage: String =
        """
        Expected <Square(
            center=${expected.center},
            orientation=${expected.orientation},
            sideLength=${expected.sideLength},
            pointA=${expected.pointA},
            pointB=${expected.pointB},
            pointC=${expected.pointC},
            pointD=${expected.pointD}
        )>,
        actual <Square(
            center=${actual.center},
            orientation=${actual.orientation},
            sideLength=${actual.sideLength},
            pointA=${actual.pointA},
            pointB=${actual.pointB},
            pointC=${actual.pointC},
            pointD=${actual.pointD}
        )>.
        """.trimIndent()

    assertTrue(
        SquareTests.areEqual(expected, actual),
        messagePrefix(message) + mainMessage
    )
}

/**
 * Asserts that the [expected] value is equal to the [actual] value, with an optional [message].
 */
fun assertEquals(expected: Triangle, actual: Triangle, message: String? = null) {
    val mainMessage: String =
        """
        Expected <Triangle(
            centroid=${expected.centroid},
            pathRotorA=${expected.pathRotorA},
            pointDistanceA=${expected.pointDistanceA},
            pathRotorAB=${expected.pathRotorAB},
            pointDistanceB=${expected.pointDistanceB},
            pathRotorAC=${expected.pathRotorAC},
            pointDistanceC=${expected.pointDistanceC},
            pointA=${expected.pointA},
            pointB=${expected.pointB},
            pointC=${expected.pointC}
        )>,
        actual <Triangle(
            centroid=${actual.centroid},
            pathRotorA=${actual.pathRotorA},
            pointDistanceA=${actual.pointDistanceA},
            pathRotorAB=${actual.pathRotorAB},
            pointDistanceB=${actual.pointDistanceB},
            pathRotorAC=${actual.pathRotorAC},
            pointDistanceC=${actual.pointDistanceC},
            pointA=${actual.pointA},
            pointB=${actual.pointB},
            pointC=${actual.pointC}
        )>.
        """.trimIndent()

    assertTrue(
        TriangleTests.areEqual(expected, actual),
        messagePrefix(message) + mainMessage
    )
}

/** Asserts that the [annulus] will not be mutated in the following [block]. */
@OptIn(ExperimentalContracts::class)
inline fun assertImmutabilityOf(annulus: Annulus, block: () -> Unit) {
    contract { callsInPlace(block, InvocationKind.EXACTLY_ONCE) }
    val copy: Annulus = annulus.copy()

    block()
    assertEquals(copy, annulus, "Annulus must not be mutated.")
}

/** Asserts that the [circle] will not be mutated in the following [block]. */
@OptIn(ExperimentalContracts::class)
inline fun assertImmutabilityOf(circle: Circle, block: () -> Unit) {
    contract { callsInPlace(block, InvocationKind.EXACTLY_ONCE) }
    val copy: Circle = circle.copy()

    block()
    assertEquals(copy, circle, "Circle must not be mutated.")
}

/** Asserts that the [lineSegment] will not be mutated in the following [block]. */
@OptIn(ExperimentalContracts::class)
inline fun assertImmutabilityOf(lineSegment: LineSegment, block: () -> Unit) {
    contract { callsInPlace(block, InvocationKind.EXACTLY_ONCE) }
    val copy: LineSegment = lineSegment.copy()

    block()
    assertEquals(copy, lineSegment, "LineSegment must not be mutated.")
}

/** Asserts that the [ray] will not be mutated in the following [block]. */
@OptIn(ExperimentalContracts::class)
inline fun assertImmutabilityOf(ray: Ray, block: () -> Unit) {
    contract { callsInPlace(block, InvocationKind.EXACTLY_ONCE) }
    val copy: Ray = ray.copy()

    block()
    assertEquals(copy, ray, "Ray must not be mutated.")
}

/** Asserts that the [rectangle] will not be mutated in the following [block]. */
@OptIn(ExperimentalContracts::class)
inline fun assertImmutabilityOf(rectangle: Rectangle, block: () -> Unit) {
    contract { callsInPlace(block, InvocationKind.EXACTLY_ONCE) }
    val copy: Rectangle = rectangle.copy()

    block()
    assertEquals(copy, rectangle, "Rectangle must not be mutated.")
}

/** Asserts that the [regularPolygon] will not be mutated in the following [block]. */
@OptIn(ExperimentalContracts::class)
inline fun assertImmutabilityOf(regularPolygon: RegularPolygon, block: () -> Unit) {
    contract { callsInPlace(block, InvocationKind.EXACTLY_ONCE) }
    val copy: RegularPolygon = regularPolygon.copy()

    block()
    assertEquals(copy, regularPolygon, "RegularPolygon must not be mutated.")
}

/** Asserts that the [regularTriangle] will not be mutated in the following [block]. */
@OptIn(ExperimentalContracts::class)
inline fun assertImmutabilityOf(regularTriangle: RegularTriangle, block: () -> Unit) {
    contract { callsInPlace(block, InvocationKind.EXACTLY_ONCE) }
    val copy: RegularTriangle = regularTriangle.copy()

    block()
    assertEquals(copy, regularTriangle, "RegularTriangle must not be mutated.")
}

/** Asserts that the [rectangle] will not be mutated in the following [block]. */
@OptIn(ExperimentalContracts::class)
inline fun assertImmutabilityOf(rectangle: RoundedRectangle, block: () -> Unit) {
    contract { callsInPlace(block, InvocationKind.EXACTLY_ONCE) }
    val copy: RoundedRectangle = rectangle.copy()

    block()
    assertEquals(copy, rectangle, "RoundedRectangle must not be mutated.")
}

/** Asserts that the [square] will not be mutated in the following [block]. */
@OptIn(ExperimentalContracts::class)
inline fun assertImmutabilityOf(square: Square, block: () -> Unit) {
    contract { callsInPlace(block, InvocationKind.EXACTLY_ONCE) }
    val copy: Square = square.copy()

    block()
    assertEquals(copy, square, "Square must not be mutated.")
}

/** Asserts that the [triangle] will not be mutated in the following [block]. */
@OptIn(ExperimentalContracts::class)
inline fun assertImmutabilityOf(triangle: Triangle, block: () -> Unit) {
    contract { callsInPlace(block, InvocationKind.EXACTLY_ONCE) }
    val copy: Triangle = triangle.copy()

    block()
    assertEquals(copy, triangle, "Triangle must not be mutated.")
}