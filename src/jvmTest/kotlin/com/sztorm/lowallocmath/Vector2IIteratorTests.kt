package com.sztorm.lowallocmath

import com.sztorm.lowallocmath.utils.Wrapper
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class Vector2IIteratorTests {
    @ParameterizedTest
    @MethodSource("nextArgs")
    fun nextMutatesIteratorCorrectly(
        iteratorCreator: () -> Vector2IIterator, expected: List<Vector2I>
    ) {
        val iterator: Vector2IIterator = iteratorCreator()

        for (v in expected) {
            assertEquals(v, iterator.next())
        }
    }

    @ParameterizedTest
    @MethodSource("nextExceptionArgs")
    fun nextThrowsWhenDoesNotHaveNextElement(
        iteratorCreator: () -> Vector2IIterator, expectedIndices: IntRange
    ) {
        val iterator: Vector2IIterator = iteratorCreator()

        for (i in expectedIndices) {
            iterator.next()
        }
        assertThrows<NoSuchElementException> { iterator.next() }
    }

    @ParameterizedTest
    @MethodSource("nextVector2IArgs")
    fun nextVector2IMutatesIteratorCorrectly(
        iteratorCreator: () -> Vector2IIterator, expected: List<Vector2I>
    ) {
        val iterator: Vector2IIterator = iteratorCreator()

        for (v in expected) {
            assertEquals(v, iterator.nextVector2I())
        }
    }

    @ParameterizedTest
    @MethodSource("nextVector2IExceptionArgs")
    fun nextVector2IThrowsWhenDoesNotHaveNextElement(
        iteratorCreator: () -> Vector2IIterator, expectedIndices: IntRange
    ) {
        val iterator: Vector2IIterator = iteratorCreator()

        for (i in expectedIndices) {
            iterator.nextVector2I()
        }
        assertThrows<NoSuchElementException> { iterator.nextVector2I() }
    }

    @ParameterizedTest
    @MethodSource("hasNextArgs")
    fun hasNextReturnsCorrectValue(
        iteratorCreator: () -> Vector2IIterator, expectedIndices: IntRange
    ) {
        val iterator: Vector2IIterator = iteratorCreator()

        for (i in expectedIndices) {
            assertTrue(iterator.hasNext())
            iterator.next()
        }
        assertFalse(iterator.hasNext())
    }

    companion object {
        @JvmStatic
        fun iteratorAndIndexArgs(): List<Arguments> {
            val arrayArgs = Vector2IArrayTests.arrays().map {
                val array = (it.get()[0] as Wrapper<*>).value as Vector2IArray

                Arguments.of(
                    { array.iterator() }, array.indices
                )
            }
            val listArgs = Vector2IListTests.lists().map {
                val list = (it.get()[0] as Wrapper<*>).value as Vector2IList

                Arguments.of(
                    { list.iterator() }, list.indices
                )
            }
            val subListArgs = Vector2ISubListTests.subLists().map {
                val subList = it.get()[0] as Vector2ISubList

                Arguments.of(
                    { subList.iterator() }, subList.indices
                )
            }
            return arrayArgs + listArgs + subListArgs
        }

        @JvmStatic
        fun nextArgs(): List<Arguments> {
            val arrayArgs = listOf(
                Arguments.of(
                    { Vector2IArray(0).iterator() },
                    emptyList<Vector2I>(),
                ),
                Arguments.of(
                    { Vector2IArray(4) { Vector2I(it, 0) }.iterator() },
                    List(4) { Vector2I(it, 0) },
                ),
            )
            val listArgs = listOf(
                Arguments.of(
                    { Vector2IArray(0).asList().iterator() },
                    emptyList<Vector2I>(),
                ),
                Arguments.of(
                    {
                        Vector2IArray(4) { Vector2I(it, 0) }
                            .asList().iterator()
                    },
                    List(4) { Vector2I(it, 0) },
                ),
            )
            val subListArgs = listOf(
                Arguments.of(
                    { Vector2IArray(0).asList().subList(0, 0).iterator() },
                    emptyList<Vector2I>(),
                ),
                Arguments.of(
                    {
                        Vector2IArray(4) { Vector2I(it, 0) }
                            .asList().subList(0, 4).iterator()
                    },
                    List(4) { Vector2I(it, 0) },
                ),
                Arguments.of(
                    {
                        Vector2IArray(4) { Vector2I(it, 0) }
                            .asList().subList(1, 4).iterator()
                    },
                    List(4) { Vector2I(it, 0) }.subList(1, 4),
                ),
                Arguments.of(
                    {
                        Vector2IArray(4) { Vector2I(it, 0) }
                            .asList().subList(0, 2).iterator()
                    },
                    List(4) { Vector2I(it, 0) }.subList(0, 2),
                ),
            )
            return arrayArgs + listArgs + subListArgs
        }

        @JvmStatic
        fun nextExceptionArgs(): List<Arguments> = iteratorAndIndexArgs()

        @JvmStatic
        fun nextVector2IArgs(): List<Arguments> = nextArgs()

        @JvmStatic
        fun nextVector2IExceptionArgs(): List<Arguments> = iteratorAndIndexArgs()

        @JvmStatic
        fun hasNextArgs(): List<Arguments> = iteratorAndIndexArgs()
    }
}