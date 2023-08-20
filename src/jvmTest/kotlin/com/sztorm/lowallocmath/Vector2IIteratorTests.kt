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
        collection: Collection<Vector2I>,
        iteratorCreator: (Collection<Vector2I>) -> Vector2IIterator,
        expected: List<Vector2I>
    ) {
        val iterator: Vector2IIterator = iteratorCreator(collection)

        for (v in expected) {
            assertEquals(v, iterator.next())
        }
    }

    @ParameterizedTest
    @MethodSource("nextExceptionArgs")
    fun nextThrowsWhenDoesNotHaveNextElement(
        collection: Collection<Vector2I>,
        iteratorCreator: (Collection<Vector2I>) -> Vector2IIterator
    ) {
        val iterator: Vector2IIterator = iteratorCreator(collection)

        for (i in collection.indices) {
            iterator.next()
        }
        assertThrows<NoSuchElementException> { iterator.next() }
    }

    @ParameterizedTest
    @MethodSource("nextVector2IArgs")
    fun nextVector2IMutatesIteratorCorrectly(
        collection: Collection<Vector2I>,
        iteratorCreator: (Collection<Vector2I>) -> Vector2IIterator,
        expected: List<Vector2I>
    ) {
        val iterator: Vector2IIterator = iteratorCreator(collection)

        for (v in expected) {
            assertEquals(v, iterator.nextVector2I())
        }
    }

    @ParameterizedTest
    @MethodSource("nextVector2IExceptionArgs")
    fun nextVector2IThrowsWhenDoesNotHaveNextElement(
        collection: Collection<Vector2I>,
        iteratorCreator: (Collection<Vector2I>) -> Vector2IIterator
    ) {
        val iterator: Vector2IIterator = iteratorCreator(collection)

        for (i in collection.indices) {
            iterator.nextVector2I()
        }
        assertThrows<NoSuchElementException> { iterator.nextVector2I() }
    }

    @ParameterizedTest
    @MethodSource("hasNextArgs")
    fun hasNextReturnsCorrectValue(
        collection: Collection<Vector2I>,
        iteratorCreator: (Collection<Vector2I>) -> Vector2IIterator
    ) {
        val iterator: Vector2IIterator = iteratorCreator(collection)

        for (i in collection.indices) {
            assertTrue(iterator.hasNext())
            iterator.next()
        }
        assertFalse(iterator.hasNext())
    }

    companion object {
        @JvmStatic
        fun collections(): List<Arguments> =
            Vector2IArrayTests.arrays().map {
                Arguments.of(
                    (it.get()[0] as Wrapper<*>).value as Vector2IArray,
                    { array: Vector2IArray -> array.iterator() }
                )
            } + Vector2IListTests.lists().map {
                Arguments.of(
                    (it.get()[0] as Wrapper<*>).value as Vector2IList,
                    { list: Vector2IList -> list.iterator() }
                )
            } + Vector2ISubListTests.subLists().map {
                Arguments.of(
                    it.get()[0] as Vector2ISubList,
                    { list: Vector2ISubList -> list.iterator() }
                )
            }

        @JvmStatic
        fun nextArgs(): List<Arguments> {
            val arrayArgs = listOf(
                Arguments.of(
                    Vector2IArray(0),
                    { array: Vector2IArray -> array.iterator() },
                    emptyList<Vector2I>(),
                ),
                Arguments.of(
                    Vector2IArray(4) { Vector2I(it, 0) },
                    { array: Vector2IArray -> array.iterator() },
                    List(4) { Vector2I(it, 0) },
                ),
            )
            val listArgs = listOf(
                Arguments.of(
                    Vector2IArray(0).asList(),
                    { list: Vector2IList -> list.iterator() },
                    emptyList<Vector2I>(),
                ),
                Arguments.of(
                    Vector2IArray(4) { Vector2I(it, 0) }.asList(),
                    { list: Vector2IList -> list.iterator() },
                    List(4) { Vector2I(it, 0) },
                ),
            )
            val subListArgs = listOf(
                Arguments.of(
                    Vector2IArray(0).asList().subList(0, 0),
                    { list: Vector2ISubList -> list.iterator() },
                    emptyList<Vector2I>(),
                ),
                Arguments.of(
                    Vector2IArray(4) { Vector2I(it, 0) }.asList().subList(0, 4),
                    { list: Vector2ISubList -> list.iterator() },
                    List(4) { Vector2I(it, 0) },
                ),
                Arguments.of(
                    Vector2IArray(4) { Vector2I(it, 0) }.asList().subList(1, 4),
                    { list: Vector2ISubList -> list.iterator() },
                    List(4) { Vector2I(it, 0) }.subList(1, 4),
                ),
                Arguments.of(
                    Vector2IArray(4) { Vector2I(it, 0) }.asList().subList(0, 2),
                    { list: Vector2ISubList -> list.iterator() },
                    List(4) { Vector2I(it, 0) }.subList(0, 2),
                ),
            )
            return arrayArgs + listArgs + subListArgs
        }

        @JvmStatic
        fun nextExceptionArgs(): List<Arguments> = collections()

        @JvmStatic
        fun nextVector2IArgs(): List<Arguments> = nextArgs()

        @JvmStatic
        fun nextVector2IExceptionArgs(): List<Arguments> = collections()

        @JvmStatic
        fun hasNextArgs(): List<Arguments> = collections()
    }
}