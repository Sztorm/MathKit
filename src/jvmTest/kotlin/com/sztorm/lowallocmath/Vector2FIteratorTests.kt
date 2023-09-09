package com.sztorm.lowallocmath

import com.sztorm.lowallocmath.utils.Wrapper
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
        fun collections(): List<Arguments> =
            Vector2FArrayTests.arrays().map {
                val array = (it.get()[0] as Wrapper<*>).value as Vector2FArray

                Arguments.of(
                    { array.iterator() }, array.indices
                )
            } + Vector2FListTests.lists().map {
                val list = (it.get()[0] as Wrapper<*>).value as Vector2FList

                Arguments.of(
                    { list.iterator() }, list.indices
                )
            } + Vector2FSubListTests.subLists().map {
                val subList = it.get()[0] as Vector2FSubList

                Arguments.of(
                    { subList.iterator() }, subList.indices
                )
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
            return arrayArgs + listArgs + subListArgs
        }

        @JvmStatic
        fun nextExceptionArgs(): List<Arguments> = collections()

        @JvmStatic
        fun nextVector2FArgs(): List<Arguments> = nextArgs()

        @JvmStatic
        fun nextVector2FExceptionArgs(): List<Arguments> = collections()

        @JvmStatic
        fun hasNextArgs(): List<Arguments> = collections()
    }
}