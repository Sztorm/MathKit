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
        collection: Collection<Vector2F>,
        iteratorCreator: (Collection<Vector2F>) -> Vector2FIterator,
        expected: List<Vector2F>
    ) {
        val iterator: Vector2FIterator = iteratorCreator(collection)

        for (v in expected) {
            assertEquals(v, iterator.next())
        }
    }

    @ParameterizedTest
    @MethodSource("nextExceptionArgs")
    fun nextThrowsWhenDoesNotHaveNextElement(
        collection: Collection<Vector2F>,
        iteratorCreator: (Collection<Vector2F>) -> Vector2FIterator
    ) {
        val iterator: Vector2FIterator = iteratorCreator(collection)

        for (i in collection.indices) {
            iterator.next()
        }
        assertThrows<NoSuchElementException> { iterator.next() }
    }

    @ParameterizedTest
    @MethodSource("nextVector2FArgs")
    fun nextVector2FMutatesIteratorCorrectly(
        collection: Collection<Vector2F>,
        iteratorCreator: (Collection<Vector2F>) -> Vector2FIterator,
        expected: List<Vector2F>
    ) {
        val iterator: Vector2FIterator = iteratorCreator(collection)

        for (v in expected) {
            assertEquals(v, iterator.nextVector2F())
        }
    }

    @ParameterizedTest
    @MethodSource("nextVector2FExceptionArgs")
    fun nextVector2FThrowsWhenDoesNotHaveNextElement(
        collection: Collection<Vector2F>,
        iteratorCreator: (Collection<Vector2F>) -> Vector2FIterator
    ) {
        val iterator: Vector2FIterator = iteratorCreator(collection)

        for (i in collection.indices) {
            iterator.nextVector2F()
        }
        assertThrows<NoSuchElementException> { iterator.nextVector2F() }
    }

    @ParameterizedTest
    @MethodSource("hasNextArgs")
    fun hasNextReturnsCorrectValue(
        collection: Collection<Vector2F>,
        iteratorCreator: (Collection<Vector2F>) -> Vector2FIterator
    ) {
        val iterator: Vector2FIterator = iteratorCreator(collection)

        for (i in collection.indices) {
            assertTrue(iterator.hasNext())
            iterator.next()
        }
        assertFalse(iterator.hasNext())
    }

    companion object {
        @JvmStatic
        fun collections(): List<Arguments> =
            Vector2FArrayTests.arrays().map {
                Arguments.of(
                    (it.get()[0] as Wrapper<*>).value as Vector2FArray,
                    { array: Vector2FArray -> array.iterator() }
                )
            } + Vector2FListTests.lists().map {
                Arguments.of(
                    (it.get()[0] as Wrapper<*>).value as Vector2FList,
                    { list: Vector2FList -> list.iterator() }
                )
            } + Vector2FSubListTests.subLists().map {
                Arguments.of(
                    it.get()[0] as Vector2FSubList,
                    { list: Vector2FSubList -> list.iterator() }
                )
            }

        @JvmStatic
        fun nextArgs(): List<Arguments> {
            val arrayArgs = listOf(
                Arguments.of(
                    Vector2FArray(0),
                    { array: Vector2FArray -> array.iterator() },
                    emptyList<Vector2F>(),
                ),
                Arguments.of(
                    Vector2FArray(4) { Vector2F(it.toFloat(), 0f) },
                    { array: Vector2FArray -> array.iterator() },
                    List(4) { Vector2F(it.toFloat(), 0f) },
                ),
            )
            val listArgs = listOf(
                Arguments.of(
                    Vector2FArray(0).asList(),
                    { list: Vector2FList -> list.iterator() },
                    emptyList<Vector2F>(),
                ),
                Arguments.of(
                    Vector2FArray(4) { Vector2F(it.toFloat(), 0f) }.asList(),
                    { list: Vector2FList -> list.iterator() },
                    List(4) { Vector2F(it.toFloat(), 0f) },
                ),
            )
            val subListArgs = listOf(
                Arguments.of(
                    Vector2FArray(0).asList().subList(0, 0),
                    { list: Vector2FSubList -> list.iterator() },
                    emptyList<Vector2F>(),
                ),
                Arguments.of(
                    Vector2FArray(4) { Vector2F(it.toFloat(), 0f) }.asList().subList(0, 4),
                    { list: Vector2FSubList -> list.iterator() },
                    List(4) { Vector2F(it.toFloat(), 0f) },
                ),
                Arguments.of(
                    Vector2FArray(4) { Vector2F(it.toFloat(), 0f) }.asList().subList(1, 4),
                    { list: Vector2FSubList -> list.iterator() },
                    List(4) { Vector2F(it.toFloat(), 0f) }.subList(1, 4),
                ),
                Arguments.of(
                    Vector2FArray(4) { Vector2F(it.toFloat(), 0f) }.asList().subList(0, 2),
                    { list: Vector2FSubList -> list.iterator() },
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