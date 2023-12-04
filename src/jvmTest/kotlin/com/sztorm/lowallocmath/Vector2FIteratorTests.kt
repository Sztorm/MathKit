package com.sztorm.lowallocmath

import com.sztorm.lowallocmath.utils.Wrapper
import com.sztorm.lowallocmath.world2d.*
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class Vector2FIteratorTests {
    @ParameterizedTest
    @MethodSource("nextArgs")
    fun nextMutatesIteratorCorrectly(
        iteratorCreator: () -> Vector2FIterator, expected: List<Vector2F>
    ) {
        val iterator: Vector2FIterator = iteratorCreator()

        for (v in expected) {
            assertEquals(v, iterator.next())
        }
    }

    @ParameterizedTest
    @MethodSource("nextExceptionArgs")
    fun nextThrowsWhenDoesNotHaveNextElement(
        iteratorCreator: () -> Vector2FIterator, expectedIndices: IntRange
    ) {
        val iterator: Vector2FIterator = iteratorCreator()

        for (i in expectedIndices) {
            iterator.next()
        }
        assertThrows<NoSuchElementException> { iterator.next() }
    }

    @ParameterizedTest
    @MethodSource("nextVector2FArgs")
    fun nextVector2FMutatesIteratorCorrectly(
        iteratorCreator: () -> Vector2FIterator, expected: List<Vector2F>
    ) {
        val iterator: Vector2FIterator = iteratorCreator()

        for (v in expected) {
            assertEquals(v, iterator.nextVector2F())
        }
    }

    @ParameterizedTest
    @MethodSource("nextVector2FExceptionArgs")
    fun nextVector2FThrowsWhenDoesNotHaveNextElement(
        iteratorCreator: () -> Vector2FIterator, expectedIndices: IntRange
    ) {
        val iterator: Vector2FIterator = iteratorCreator()

        for (i in expectedIndices) {
            iterator.nextVector2F()
        }
        assertThrows<NoSuchElementException> { iterator.nextVector2F() }
    }

    @ParameterizedTest
    @MethodSource("hasNextArgs")
    fun hasNextReturnsCorrectValue(
        iteratorCreator: () -> Vector2FIterator, expectedIndices: IntRange
    ) {
        val iterator: Vector2FIterator = iteratorCreator()

        for (i in expectedIndices) {
            assertTrue(iterator.hasNext())
            iterator.next()
        }
        assertFalse(iterator.hasNext())
    }

    companion object {
        @JvmStatic
        fun iteratorAndIndexArgs(): List<Arguments> {
            val arrayArgs = Vector2FArrayTests.arrays().map {
                val array = (it.get()[0] as Wrapper<*>).value as Vector2FArray

                Arguments.of({ array.iterator() }, array.indices)
            }
            val listArgs = Vector2FListTests.lists().map {
                val list = (it.get()[0] as Wrapper<*>).value as Vector2FList

                Arguments.of({ list.iterator() }, list.indices)
            }
            val subListArgs = Vector2FSubListTests.subLists().map {
                val subList = it.get()[0] as Vector2FSubList

                Arguments.of({ subList.iterator() }, subList.indices)
            }
            val lineSegmentArgs = LineSegmentTests.pointsArgs().map {
                val lineSegment = it.get()[0] as LineSegment

                Arguments.of({ lineSegment.pointIterator() }, 0..1)
            }
            val triangleArgs = TriangleTests.pointsArgs().map {
                val triangle = it.get()[0] as Triangle

                Arguments.of({ triangle.pointIterator() }, 0..2)
            }
            val regularTriangleArgs = RegularTriangleTests.pointsArgs().map {
                val triangle = it.get()[0] as RegularTriangle

                Arguments.of({ triangle.pointIterator() }, 0..2)
            }
            val rectangleArgs = RectangleTests.pointsArgs().map {
                val rectangle = it.get()[0] as Rectangle

                Arguments.of({ rectangle.pointIterator() }, 0..3)
            }
            val squareArgs = SquareTests.pointsArgs().map {
                val square = it.get()[0] as Square

                Arguments.of({ square.pointIterator() }, 0..3)
            }
            val roundedRectangleArgs = RoundedRectangleTests.pointsArgs().map {
                val rectangle = it.get()[0] as RoundedRectangle

                Arguments.of({ rectangle.pointIterator() }, 0..7)
            } + RoundedRectangleTests.cornerCentersArgs().map {
                val rectangle = it.get()[0] as RoundedRectangle

                Arguments.of({ rectangle.cornerCenterIterator() }, 0..3)
            }
            val regularPolygonArgs = RegularPolygonTests.pointsArgs().map {
                val polygon = it.get()[0] as RegularPolygon

                Arguments.of({ polygon.pointIterator() }, 0..polygon.points.lastIndex)
            }
            return listOf(
                arrayArgs,
                listArgs,
                subListArgs,
                lineSegmentArgs,
                triangleArgs,
                regularTriangleArgs,
                rectangleArgs,
                squareArgs,
                roundedRectangleArgs,
                regularPolygonArgs
            ).flatten()
        }

        @JvmStatic
        fun nextArgs(): List<Arguments> {
            val arrayArgs = listOf(
                Arguments.of(
                    { Vector2FArray(0).iterator() },
                    emptyList<Vector2F>(),
                ),
                Arguments.of(
                    { Vector2FArray(4) { Vector2F(it.toFloat(), 0f) }.iterator() },
                    List(4) { Vector2F(it.toFloat(), 0f) },
                ),
            )
            val listArgs = listOf(
                Arguments.of(
                    { Vector2FArray(0).asList().iterator() },
                    emptyList<Vector2F>(),
                ),
                Arguments.of(
                    {
                        Vector2FArray(4) { Vector2F(it.toFloat(), 0f) }
                            .asList().iterator()
                    },
                    List(4) { Vector2F(it.toFloat(), 0f) },
                ),
            )
            val subListArgs = listOf(
                Arguments.of(
                    { Vector2FArray(0).asList().subList(0, 0).iterator() },
                    emptyList<Vector2F>(),
                ),
                Arguments.of(
                    {
                        Vector2FArray(4) { Vector2F(it.toFloat(), 0f) }
                            .asList().subList(0, 4).iterator()
                    },
                    List(4) { Vector2F(it.toFloat(), 0f) },
                ),
                Arguments.of(
                    {
                        Vector2FArray(4) { Vector2F(it.toFloat(), 0f) }
                            .asList().subList(1, 4).iterator()
                    },
                    List(4) { Vector2F(it.toFloat(), 0f) }.subList(1, 4),
                ),
                Arguments.of(
                    {
                        Vector2FArray(4) { Vector2F(it.toFloat(), 0f) }
                            .asList().subList(0, 2).iterator()
                    },
                    List(4) { Vector2F(it.toFloat(), 0f) }.subList(0, 2),
                ),
            )
            val lineSegmentArgs = LineSegmentTests.pointsArgs().map {
                val lineSegment = it.get()[0] as LineSegment
                val points = listOf(lineSegment.pointA, lineSegment.pointB)

                Arguments.of({ lineSegment.pointIterator() }, points)
            }
            val triangleArgs = TriangleTests.pointsArgs().map {
                val triangle = it.get()[0] as Triangle
                val points = listOf(triangle.pointA, triangle.pointB, triangle.pointC)

                Arguments.of({ triangle.pointIterator() }, points)
            }
            val regularTriangleArgs = RegularTriangleTests.pointsArgs().map {
                val triangle = it.get()[0] as RegularTriangle
                val points = listOf(triangle.pointA, triangle.pointB, triangle.pointC)

                Arguments.of({ triangle.pointIterator() }, points)
            }
            val rectangleArgs = RectangleTests.pointsArgs().map {
                val rectangle = it.get()[0] as Rectangle
                val points = listOf(
                    rectangle.pointA, rectangle.pointB, rectangle.pointC, rectangle.pointD
                )
                Arguments.of({ rectangle.pointIterator() }, points)
            }
            val squareArgs = SquareTests.pointsArgs().map {
                val square = it.get()[0] as Square
                val points = listOf(square.pointA, square.pointB, square.pointC, square.pointD)

                Arguments.of({ square.pointIterator() }, points)
            }
            val roundedRectangleArgs = RoundedRectangleTests.pointsArgs().map {
                val rectangle = it.get()[0] as RoundedRectangle
                val points = listOf(
                    rectangle.pointA,
                    rectangle.pointB,
                    rectangle.pointC,
                    rectangle.pointD,
                    rectangle.pointE,
                    rectangle.pointF,
                    rectangle.pointG,
                    rectangle.pointH,
                )
                Arguments.of({ rectangle.pointIterator() }, points)
            } + RoundedRectangleTests.cornerCentersArgs().map {
                val rectangle = it.get()[0] as RoundedRectangle
                val cornerCenters = listOf(
                    rectangle.cornerCenterA,
                    rectangle.cornerCenterB,
                    rectangle.cornerCenterC,
                    rectangle.cornerCenterD
                )
                Arguments.of({ rectangle.cornerCenterIterator() }, cornerCenters)
            }
            val regularPolygonArgs = RegularPolygonTests.pointsArgs().map {
                val polygon = it.get()[0] as RegularPolygon

                Arguments.of({ polygon.pointIterator() }, polygon.points)
            }
            return listOf(
                arrayArgs,
                listArgs,
                subListArgs,
                lineSegmentArgs,
                triangleArgs,
                regularTriangleArgs,
                rectangleArgs,
                squareArgs,
                roundedRectangleArgs,
                regularPolygonArgs
            ).flatten()
        }

        @JvmStatic
        fun nextExceptionArgs(): List<Arguments> = iteratorAndIndexArgs()

        @JvmStatic
        fun nextVector2FArgs(): List<Arguments> = nextArgs()

        @JvmStatic
        fun nextVector2FExceptionArgs(): List<Arguments> = iteratorAndIndexArgs()

        @JvmStatic
        fun hasNextArgs(): List<Arguments> = iteratorAndIndexArgs()
    }
}