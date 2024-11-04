package com.sztorm.lowallocmath.utils

import com.sztorm.lowallocmath.AngleF
import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F
import com.sztorm.lowallocmath.euclidean2d.*
import com.sztorm.lowallocmath.isApproximately
import kotlin.math.sign
import kotlin.test.assertTrue

private fun Float.hasSymbolIdenticalTo(other: Float) =
    (isNaN() and other.isNaN()) || (isInfinite() && other.isInfinite() && sign == other.sign)

private fun messagePrefix(message: String?) = if (message == null) "" else "$message. "

/**
 * Asserts that the [expected] value is approximately equal to the [actual] value within an
 * [absoluteTolerance], with an optional [message].
 */
fun assertApproximation(
    expected: Float,
    actual: Float,
    absoluteTolerance: Float = 0.00001f,
    message: String? = null
) {
    val mainMessage =
        "Expected <$expected> with absolute tolerance <$absoluteTolerance>, actual <$actual>."

    assertTrue(
        expected.hasSymbolIdenticalTo(actual) ||
                expected.isApproximately(actual, absoluteTolerance),
        messagePrefix(message) + mainMessage
    )
}

/**
 * Asserts that the [expected] value is approximately equal to the [actual] value within an
 * [absoluteTolerance], with an optional [message].
 */
fun assertApproximation(
    expected: Vector2F,
    actual: Vector2F,
    absoluteTolerance: Float = 0.00001f,
    message: String? = null
) {
    val (eX: Float, eY: Float) = expected
    val (aX: Float, aY: Float) = actual
    val mainMessage =
        "Expected <$expected> with absolute tolerance <$absoluteTolerance>, actual <$actual>."

    assertTrue(
        (eX.hasSymbolIdenticalTo(aX) || eX.isApproximately(aX, absoluteTolerance)) &&
                (eY.hasSymbolIdenticalTo(aY) || eY.isApproximately(aY, absoluteTolerance)),
        messagePrefix(message) + mainMessage
    )
}

/**
 * Asserts that the [expected] value is approximately equal to the [actual] value within an
 * [absoluteTolerance], with an optional [message].
 */
fun assertApproximation(
    expected: ComplexF,
    actual: ComplexF,
    absoluteTolerance: Float = 0.00001f,
    message: String? = null
) {
    val (eR: Float, eI: Float) = expected
    val (aR: Float, aI: Float) = actual
    val mainMessage =
        "Expected <$expected> with absolute tolerance <$absoluteTolerance>, actual <$actual>."

    assertTrue(
        (eR.hasSymbolIdenticalTo(aR) || eR.isApproximately(aR, absoluteTolerance)) &&
                (eI.hasSymbolIdenticalTo(aI) || eI.isApproximately(aI, absoluteTolerance)),
        messagePrefix(message) + mainMessage
    )
}

/**
 * Asserts that the [expected] value is approximately equal to the [actual] value within an
 * [absoluteTolerance], with an optional [message].
 */
fun assertApproximation(
    expected: AngleF,
    actual: AngleF,
    absoluteTolerance: AngleF = AngleF(0.00001f),
    message: String? = null
) {
    val mainMessage =
        "Expected <$expected> with absolute tolerance <$absoluteTolerance>, actual <$actual>."

    assertTrue(
        expected.radians.hasSymbolIdenticalTo(actual.radians) ||
                expected.isApproximately(actual, absoluteTolerance),
        messagePrefix(message) + mainMessage
    )
}

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

/**
 * Asserts that the [expected] value is equal to the [actual] value using [equalityComparator],
 * with an optional [message].
 */
inline fun <reified T> assertEquals(
    expected: T, actual: T, equalityComparator: (T, T) -> Boolean, message: String? = null
) {
    val prefix: String = if (message == null) "" else "$message. "

    assertTrue(
        equalityComparator(expected, actual),
        prefix + "Expected <$expected>, actual <$actual>."
    )
}