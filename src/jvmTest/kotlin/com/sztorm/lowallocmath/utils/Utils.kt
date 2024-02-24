package com.sztorm.lowallocmath.utils

import com.sztorm.lowallocmath.AngleF
import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F
import com.sztorm.lowallocmath.isApproximately
import com.sztorm.lowallocmath.world2d.*
import kotlin.math.sign
import kotlin.test.assertTrue

private fun Float.hasSymbolIdenticalTo(other: Float) =
    (isNaN() and other.isNaN()) || (isInfinite() && other.isInfinite() && sign == other.sign)

fun assertApproximation(expected: Float, actual: Float, tolerance: Float = 0.00001f) = assertTrue(
    expected.hasSymbolIdenticalTo(actual) || expected.isApproximately(actual, tolerance),
    "Expected <$expected> with absolute tolerance <$tolerance>, actual <$actual>."
)

fun assertApproximation(expected: Vector2F, actual: Vector2F, tolerance: Float = 0.00001f) {
    val (eX: Float, eY: Float) = expected
    val (aX: Float, aY: Float) = actual

    assertTrue(
        (eX.hasSymbolIdenticalTo(aX) || eX.isApproximately(aX, tolerance)) &&
                (eY.hasSymbolIdenticalTo(aY) || eY.isApproximately(aY, tolerance)),
        "Expected <$expected> with absolute tolerance <$tolerance>, actual <$actual>."
    )
}

fun assertApproximation(expected: ComplexF, actual: ComplexF, tolerance: Float = 0.00001f) {
    val (eR: Float, eI: Float) = expected
    val (aR: Float, aI: Float) = actual

    assertTrue(
        (eR.hasSymbolIdenticalTo(aR) || eR.isApproximately(aR, tolerance)) &&
                (eI.hasSymbolIdenticalTo(aI) || eI.isApproximately(aI, tolerance)),
        "Expected <$expected> with absolute tolerance <$tolerance>, actual <$actual>."
    )
}

fun assertApproximation(
    expected: AngleF, actual: AngleF, tolerance: AngleF = AngleF(0.00001f)
) = assertTrue(
    expected.radians.hasSymbolIdenticalTo(actual.radians) ||
            expected.isApproximately(actual, tolerance),
    "Expected <$expected> with absolute tolerance <$tolerance>, actual <$actual>."
)

fun assertApproximation(expected: Annulus, actual: Annulus, tolerance: Float = 0.00001f) =
    assertTrue(
        AnnulusTests.areApproximatelyEqual(expected, actual, tolerance),
        """
        Expected <Annulus(
            center=${expected.center},
            orientation=${expected.orientation},
            outerRadius=${expected.outerRadius},
            innerRadius=${expected.innerRadius}
        )> with absolute tolerance <$tolerance>,
        actual <Annulus(
            center=${actual.center},
            orientation=${actual.orientation},
            outerRadius=${actual.outerRadius},
            innerRadius=${actual.innerRadius}
        )>.
        """.trimIndent()
    )

fun assertApproximation(expected: Circle, actual: Circle, tolerance: Float = 0.00001f) =
    assertTrue(
        CircleTests.areApproximatelyEqual(expected, actual, tolerance),
        """
        Expected <Circle(
            center=${expected.center},
            orientation=${expected.orientation},
            radius=${expected.radius}
        )> with absolute tolerance <$tolerance>,
        actual <Circle(
            center=${actual.center},
            orientation=${actual.orientation},
            radius=${actual.radius}
        )>.
        """.trimIndent()
    )

fun assertApproximation(expected: LineSegment, actual: LineSegment, tolerance: Float = 0.00001f) =
    assertTrue(
        LineSegmentTests.areApproximatelyEqual(expected, actual, tolerance),
        """
        Expected <LineSegment(
            pointA=${expected.pointA},
            pointB=${expected.pointB}
        )> with absolute tolerance <$tolerance>,
        actual <LineSegment(
            pointA=${actual.pointA},
            pointB=${actual.pointB}
        )>.
        """.trimIndent()
    )

fun assertApproximation(expected: Ray, actual: Ray, tolerance: Float = 0.00001f) = assertTrue(
    RayTests.areApproximatelyEqual(expected, actual, tolerance),
    """
    Expected <Ray(
        origin=${expected.origin},
        direction=${expected.direction}
    )> with absolute tolerance <$tolerance>,
    actual <Ray(
        origin=${actual.origin},
        direction=${actual.direction}
    )>.
    """.trimIndent()
)

fun assertApproximation(expected: Rectangle, actual: Rectangle, tolerance: Float = 0.00001f) =
    assertTrue(
        RectangleTests.areApproximatelyEqual(expected, actual, tolerance),
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
        )> with absolute tolerance <$tolerance>,
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
    )

fun assertApproximation(
    expected: RegularPolygon, actual: RegularPolygon, tolerance: Float = 0.00001f
) = assertTrue(
    RegularPolygonTests.areApproximatelyEqual(expected, actual, tolerance),
    """
    Expected <RegularPolygon(
        center=${expected.center},
        orientation=${expected.orientation},
        sideLength=${expected.sideLength},
        points=${expected.points},
        circumradius=${expected.circumradius}
        inradius=${expected.inradius}
    )> with absolute tolerance <$tolerance>,
    actual <RegularPolygon(
        center=${actual.center},
        orientation=${actual.orientation},
        sideLength=${actual.sideLength},
        points=${actual.points},
        circumradius=${actual.circumradius}
        inradius=${actual.inradius}
    )>.
    """.trimIndent()
)

fun assertApproximation(
    expected: RegularTriangle, actual: RegularTriangle, tolerance: Float = 0.00001f
) = assertTrue(
    RegularTriangleTests.areApproximatelyEqual(expected, actual, tolerance),
    """
    Expected <RegularTriangle(
        center=${expected.center},
        orientation=${expected.orientation},
        sideLength=${expected.sideLength},
        pointA=${expected.pointA},
        pointB=${expected.pointB},
        pointC=${expected.pointC}
    )> with absolute tolerance <$tolerance>,
    actual <RegularTriangle(
        center=${actual.center},
        orientation=${actual.orientation},
        sideLength=${actual.sideLength},
        pointA=${actual.pointA},
        pointB=${actual.pointB},
        pointC=${actual.pointC}
    )>.
    """.trimIndent()
)

fun assertApproximation(
    expected: RoundedRectangle, actual: RoundedRectangle, tolerance: Float = 0.00001f
) = assertTrue(
    RoundedRectangleTests.areApproximatelyEqual(expected, actual, tolerance),
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
    )> with absolute tolerance <$tolerance>,
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
)

fun assertApproximation(expected: Square, actual: Square, tolerance: Float = 0.00001f) =
    assertTrue(
        SquareTests.areApproximatelyEqual(expected, actual, tolerance),
        """
        Expected <Square(
            center=${expected.center},
            orientation=${expected.orientation},
            sideLength=${expected.sideLength},
            pointA=${expected.pointA},
            pointB=${expected.pointB},
            pointC=${expected.pointC},
            pointD=${expected.pointD}
        )> with absolute tolerance <$tolerance>,
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
    )

fun assertApproximation(expected: Triangle, actual: Triangle, tolerance: Float = 0.00001f) =
    assertTrue(
        TriangleTests.areApproximatelyEqual(expected, actual, tolerance),
        """
        Expected <Triangle(
            pointA=${expected.pointA},
            pointB=${expected.pointB},
            pointC=${expected.pointC},
            centroid=${expected.centroid},
            orientation=${expected.orientation}
        )> with absolute tolerance <$tolerance>,
        actual <Triangle(
            pointA=${actual.pointA},
            pointB=${actual.pointB},
            pointC=${actual.pointC},
            centroid=${actual.centroid},
            orientation=${actual.orientation}
        )>.
        """.trimIndent()
    )

inline fun <reified T> assertEquals(
    expected: T, actual: T, equalityComparator: (T, T) -> Boolean, message: String? = null
) {
    val prefix: String = if (message == null) "" else "$message. "

    assertTrue(
        equalityComparator(expected, actual),
        prefix + "Expected <$expected>, actual <$actual>."
    )
}