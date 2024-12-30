package com.sztorm.mathkit

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class Vector2FListIteratorTests {
    @ParameterizedTest
    @MethodSource("nextArgs")
    fun nextMutatesIteratorCorrectly(
        collection: Collection<Vector2F>,
        index: Int,
        iteratorCreator: (Collection<Vector2F>, index: Int) -> Vector2FListIterator,
        expected: List<Vector2F>
    ) {
        val iterator: Vector2FListIterator = iteratorCreator(collection, index)

        for (v in expected) {
            assertEquals(v, iterator.next())
        }
    }

    @ParameterizedTest
    @MethodSource("nextExceptionArgs")
    fun nextThrowsWhenDoesNotHaveNextElement(
        collection: Collection<Vector2F>,
        index: Int,
        iteratorCreator: (Collection<Vector2F>, index: Int) -> Vector2FListIterator
    ) {
        val iterator: Vector2FIterator = iteratorCreator(collection, index)

        for (i in index until collection.size) {
            iterator.next()
        }
        assertThrows<NoSuchElementException> { iterator.next() }
    }

    @ParameterizedTest
    @MethodSource("nextVector2FArgs")
    fun nextVector2FMutatesIteratorCorrectly(
        collection: Collection<Vector2F>,
        index: Int,
        iteratorCreator: (Collection<Vector2F>, index: Int) -> Vector2FListIterator,
        expected: List<Vector2F>
    ) {
        val iterator: Vector2FListIterator = iteratorCreator(collection, index)

        for (v in expected) {
            assertEquals(v, iterator.nextVector2F())
        }
    }

    @ParameterizedTest
    @MethodSource("nextVector2FExceptionArgs")
    fun nextVector2FThrowsWhenDoesNotHaveNextElement(
        collection: Collection<Vector2F>,
        index: Int,
        iteratorCreator: (Collection<Vector2F>, index: Int) -> Vector2FListIterator
    ) {
        val iterator: Vector2FListIterator = iteratorCreator(collection, index)

        for (i in index until collection.size) {
            iterator.nextVector2F()
        }
        assertThrows<NoSuchElementException> { iterator.nextVector2F() }
    }

    @ParameterizedTest
    @MethodSource("previousArgs")
    fun previousMutatesIteratorCorrectly(
        collection: Collection<Vector2F>,
        index: Int,
        iteratorCreator: (Collection<Vector2F>, index: Int) -> Vector2FListIterator,
        expected: List<Vector2F>
    ) {
        val iterator: Vector2FListIterator = iteratorCreator(collection, index)

        for (v in expected) {
            assertEquals(v, iterator.previous())
        }
    }

    @ParameterizedTest
    @MethodSource("previousExceptionArgs")
    fun previousThrowsWhenDoesNotHavePreviousElement(
        collection: Collection<Vector2F>,
        index: Int,
        iteratorCreator: (Collection<Vector2F>, index: Int) -> Vector2FListIterator
    ) {
        val iterator: Vector2FListIterator = iteratorCreator(collection, index)

        for (i in index - 1 downTo 0) {
            iterator.previous()
        }
        assertThrows<NoSuchElementException> { iterator.previous() }
    }

    @ParameterizedTest
    @MethodSource("previousVector2FArgs")
    fun previousVector2FMutatesIteratorCorrectly(
        collection: Collection<Vector2F>,
        index: Int,
        iteratorCreator: (Collection<Vector2F>, index: Int) -> Vector2FListIterator,
        expected: List<Vector2F>
    ) {
        val iterator: Vector2FListIterator = iteratorCreator(collection, index)

        for (v in expected) {
            assertEquals(v, iterator.previousVector2F())
        }
    }

    @ParameterizedTest
    @MethodSource("previousVector2FExceptionArgs")
    fun previousVector2FThrowsWhenDoesNotHavePreviousElement(
        collection: Collection<Vector2F>,
        index: Int,
        iteratorCreator: (Collection<Vector2F>, index: Int) -> Vector2FListIterator
    ) {
        val iterator: Vector2FListIterator = iteratorCreator(collection, index)

        for (i in index - 1 downTo 0) {
            iterator.previousVector2F()
        }
        assertThrows<NoSuchElementException> { iterator.previousVector2F() }
    }

    @ParameterizedTest
    @MethodSource("nextIndexArgs")
    fun nextIndexReturnsCorrectValue(
        collection: Collection<Vector2F>,
        index: Int,
        iteratorCreator: (Collection<Vector2F>, index: Int) -> Vector2FListIterator
    ) {
        val iterator: Vector2FListIterator = iteratorCreator(collection, index)

        for (i in index until collection.size) {
            assertEquals(i, iterator.nextIndex())
            iterator.next()
        }
        assertEquals(collection.size, iterator.nextIndex())
    }

    @ParameterizedTest
    @MethodSource("previousIndexArgs")
    fun previousIndexReturnsCorrectValue(
        collection: Collection<Vector2F>,
        index: Int,
        iteratorCreator: (Collection<Vector2F>, index: Int) -> Vector2FListIterator
    ) {
        val iterator: Vector2FListIterator = iteratorCreator(collection, index)

        for (i in index - 1 downTo 0) {
            assertEquals(i, iterator.previousIndex())
            iterator.previous()
        }
        assertEquals(-1, iterator.previousIndex())
    }

    @ParameterizedTest
    @MethodSource("hasNextArgs")
    fun hasNextReturnsCorrectValue(
        collection: Collection<Vector2F>,
        index: Int,
        iteratorCreator: (Collection<Vector2F>, index: Int) -> Vector2FListIterator
    ) {
        val iterator: Vector2FListIterator = iteratorCreator(collection, index)

        for (i in index until collection.size) {
            assertTrue(iterator.hasNext())
            iterator.next()
        }
        assertFalse(iterator.hasNext())
    }

    @ParameterizedTest
    @MethodSource("hasPreviousArgs")
    fun hasPreviousReturnsCorrectValue(
        collection: Collection<Vector2F>,
        index: Int,
        iteratorCreator: (Collection<Vector2F>, index: Int) -> Vector2FListIterator
    ) {
        val iterator: Vector2FListIterator = iteratorCreator(collection, index)

        for (i in index - 1 downTo 0) {
            assertTrue(iterator.hasPrevious())
            iterator.previous()
        }
        assertFalse(iterator.hasPrevious())
    }

    companion object {
        @JvmStatic
        fun nextArgs(): List<Arguments> {
            val listArgs = listOf(
                Arguments.of(
                    Vector2FArray(0).asList(),
                    0,
                    { list: Vector2FList, _: Int -> list.listIterator() },
                    emptyList<Vector2F>()
                ),
                Arguments.of(
                    Vector2FArray(0).asList(),
                    0,
                    { list: Vector2FList, index: Int -> list.listIterator(index) },
                    emptyList<Vector2F>()
                ),
                Arguments.of(
                    Vector2FArray(4) { Vector2F(it.toFloat(), 0f) }.asList(),
                    0,
                    { list: Vector2FList, _: Int -> list.listIterator() },
                    List(4) { Vector2F(it.toFloat(), 0f) }
                ),
                Arguments.of(
                    Vector2FArray(4) { Vector2F(it.toFloat(), 0f) }.asList(),
                    1,
                    { list: Vector2FList, index: Int -> list.listIterator(index) },
                    List(4) { Vector2F(it.toFloat(), 0f) }.drop(1)
                ),
            )
            val subListArgs = listOf(
                Arguments.of(
                    Vector2FArray(0).asList().subList(0, 0),
                    0,
                    { list: Vector2FSubList, _: Int -> list.listIterator() },
                    emptyList<Vector2F>()
                ),
                Arguments.of(
                    Vector2FArray(0).asList().subList(0, 0),
                    0,
                    { list: Vector2FSubList, index: Int -> list.listIterator(index) },
                    emptyList<Vector2F>()
                ),
                Arguments.of(
                    Vector2FArray(4) { Vector2F(it.toFloat(), 0f) }.asList().subList(0, 4),
                    0,
                    { list: Vector2FSubList, _: Int -> list.listIterator() },
                    List(4) { Vector2F(it.toFloat(), 0f) }
                ),
                Arguments.of(
                    Vector2FArray(4) { Vector2F(it.toFloat(), 0f) }.asList().subList(0, 4),
                    1,
                    { list: Vector2FSubList, index: Int -> list.listIterator(index) },
                    List(4) { Vector2F(it.toFloat(), 0f) }.drop(1)
                ),
                Arguments.of(
                    Vector2FArray(4) { Vector2F(it.toFloat(), 0f) }.asList().subList(1, 4),
                    0,
                    { list: Vector2FSubList, _: Int -> list.listIterator() },
                    List(4) { Vector2F(it.toFloat(), 0f) }.subList(1, 4)
                ),
                Arguments.of(
                    Vector2FArray(4) { Vector2F(it.toFloat(), 0f) }.asList().subList(1, 4),
                    1,
                    { list: Vector2FSubList, index: Int -> list.listIterator(index) },
                    List(4) { Vector2F(it.toFloat(), 0f) }.subList(1, 4).drop(1)
                ),
                Arguments.of(
                    Vector2FArray(4) { Vector2F(it.toFloat(), 0f) }.asList().subList(0, 2),
                    0,
                    { list: Vector2FSubList, _: Int -> list.listIterator() },
                    List(4) { Vector2F(it.toFloat(), 0f) }.subList(0, 2)
                ),
                Arguments.of(
                    Vector2FArray(4) { Vector2F(it.toFloat(), 0f) }.asList().subList(0, 2),
                    1,
                    { list: Vector2FSubList, index: Int -> list.listIterator(index) },
                    List(4) { Vector2F(it.toFloat(), 0f) }.subList(0, 2).drop(1)
                ),
            )
            return listArgs + subListArgs
        }

        @JvmStatic
        fun nextExceptionArgs(): List<Arguments> {
            val listArgs = listOf(
                Arguments.of(
                    Vector2FArray(0).asList(),
                    0,
                    { list: Vector2FList, _: Int -> list.listIterator() }
                ),
                Arguments.of(
                    Vector2FArray(0).asList(),
                    0,
                    { list: Vector2FList, index: Int -> list.listIterator(index) }
                ),
                Arguments.of(
                    Vector2FArray(4) { Vector2F(it.toFloat(), 0f) }.asList(),
                    0,
                    { list: Vector2FList, _: Int -> list.listIterator() }
                ),
                Arguments.of(
                    Vector2FArray(4) { Vector2F(it.toFloat(), 0f) }.asList(),
                    1,
                    { list: Vector2FList, index: Int -> list.listIterator(index) }
                ),
            )
            val subListArgs = listOf(
                Arguments.of(
                    Vector2FArray(0).asList().subList(0, 0),
                    0,
                    { list: Vector2FSubList, _: Int -> list.listIterator() }
                ),
                Arguments.of(
                    Vector2FArray(0).asList().subList(0, 0),
                    0,
                    { list: Vector2FSubList, index: Int -> list.listIterator(index) }
                ),
                Arguments.of(
                    Vector2FArray(4) { Vector2F(it.toFloat(), 0f) }.asList().subList(0, 4),
                    0,
                    { list: Vector2FSubList, _: Int -> list.listIterator() }
                ),
                Arguments.of(
                    Vector2FArray(4) { Vector2F(it.toFloat(), 0f) }.asList().subList(0, 4),
                    1,
                    { list: Vector2FSubList, index: Int -> list.listIterator(index) }
                ),
                Arguments.of(
                    Vector2FArray(4) { Vector2F(it.toFloat(), 0f) }.asList().subList(1, 4),
                    0,
                    { list: Vector2FSubList, _: Int -> list.listIterator() }
                ),
                Arguments.of(
                    Vector2FArray(4) { Vector2F(it.toFloat(), 0f) }.asList().subList(1, 4),
                    1,
                    { list: Vector2FSubList, index: Int -> list.listIterator(index) }
                ),
                Arguments.of(
                    Vector2FArray(4) { Vector2F(it.toFloat(), 0f) }.asList().subList(0, 2),
                    0,
                    { list: Vector2FSubList, _: Int -> list.listIterator() }
                ),
                Arguments.of(
                    Vector2FArray(4) { Vector2F(it.toFloat(), 0f) }.asList().subList(0, 2),
                    1,
                    { list: Vector2FSubList, index: Int -> list.listIterator(index) }
                ),
            )
            return listArgs + subListArgs
        }

        @JvmStatic
        fun nextVector2FArgs(): List<Arguments> = nextArgs()

        @JvmStatic
        fun nextVector2FExceptionArgs(): List<Arguments> = nextExceptionArgs()

        @JvmStatic
        fun previousArgs(): List<Arguments> {
            val listArgs = listOf(
                Arguments.of(
                    Vector2FArray(0).asList(),
                    0,
                    { list: Vector2FList, _: Int -> list.listIterator() },
                    emptyList<Vector2F>()
                ),
                Arguments.of(
                    Vector2FArray(0).asList(),
                    0,
                    { list: Vector2FList, index: Int -> list.listIterator(index) },
                    emptyList<Vector2F>()
                ),
                Arguments.of(
                    Vector2FArray(4) { Vector2F(it.toFloat(), 0f) }.asList(),
                    4,
                    { list: Vector2FList, index: Int -> list.listIterator(index) },
                    List(4) { Vector2F(it.toFloat(), 0f) }.reversed()
                ),
                Arguments.of(
                    Vector2FArray(4) { Vector2F(it.toFloat(), 0f) }.asList(),
                    3,
                    { list: Vector2FList, index: Int -> list.listIterator(index) },
                    List(4) { Vector2F(it.toFloat(), 0f) }.dropLast(1).reversed()
                ),
                Arguments.of(
                    Vector2FArray(4) { Vector2F(it.toFloat(), 0f) }.asList(),
                    0,
                    { list: Vector2FList, _: Int -> list.listIterator() },
                    emptyList<Vector2F>()
                ),
            )
            val subListArgs = listOf(
                Arguments.of(
                    Vector2FArray(0).asList().subList(0, 0),
                    0,
                    { list: Vector2FSubList, _: Int -> list.listIterator() },
                    emptyList<Vector2F>()
                ),
                Arguments.of(
                    Vector2FArray(0).asList().subList(0, 0),
                    0,
                    { list: Vector2FSubList, index: Int -> list.listIterator(index) },
                    emptyList<Vector2F>()
                ),
                Arguments.of(
                    Vector2FArray(4) { Vector2F(it.toFloat(), 0f) }.asList().subList(0, 4),
                    4,
                    { list: Vector2FSubList, index: Int -> list.listIterator(index) },
                    List(4) { Vector2F(it.toFloat(), 0f) }.reversed()
                ),
                Arguments.of(
                    Vector2FArray(4) { Vector2F(it.toFloat(), 0f) }.asList().subList(0, 4),
                    3,
                    { list: Vector2FSubList, index: Int -> list.listIterator(index) },
                    List(4) { Vector2F(it.toFloat(), 0f) }.dropLast(1).reversed()
                ),
                Arguments.of(
                    Vector2FArray(4) { Vector2F(it.toFloat(), 0f) }.asList().subList(0, 4),
                    0,
                    { list: Vector2FSubList, _: Int -> list.listIterator() },
                    emptyList<Vector2F>()
                ),
                Arguments.of(
                    Vector2FArray(4) { Vector2F(it.toFloat(), 0f) }.asList().subList(1, 4),
                    3,
                    { list: Vector2FSubList, index: Int -> list.listIterator(index) },
                    List(4) { Vector2F(it.toFloat(), 0f) }.subList(1, 4).reversed()
                ),
                Arguments.of(
                    Vector2FArray(4) { Vector2F(it.toFloat(), 0f) }.asList().subList(1, 4),
                    2,
                    { list: Vector2FSubList, index: Int -> list.listIterator(index) },
                    List(4) { Vector2F(it.toFloat(), 0f) }.subList(1, 4)
                        .dropLast(1).reversed()
                ),
                Arguments.of(
                    Vector2FArray(4) { Vector2F(it.toFloat(), 0f) }.asList().subList(1, 4),
                    0,
                    { list: Vector2FSubList, _: Int -> list.listIterator() },
                    emptyList<Vector2F>()
                ),
                Arguments.of(
                    Vector2FArray(4) { Vector2F(it.toFloat(), 0f) }.asList().subList(0, 2),
                    2,
                    { list: Vector2FSubList, index: Int -> list.listIterator(index) },
                    List(4) { Vector2F(it.toFloat(), 0f) }.subList(0, 2).reversed()
                ),
                Arguments.of(
                    Vector2FArray(4) { Vector2F(it.toFloat(), 0f) }.asList().subList(0, 2),
                    1,
                    { list: Vector2FSubList, index: Int -> list.listIterator(index) },
                    List(4) { Vector2F(it.toFloat(), 0f) }.subList(0, 2)
                        .dropLast(1).reversed()
                ),
                Arguments.of(
                    Vector2FArray(4) { Vector2F(it.toFloat(), 0f) }.asList().subList(0, 2),
                    0,
                    { list: Vector2FSubList, _: Int -> list.listIterator() },
                    emptyList<Vector2F>()
                ),
            )
            return listArgs + subListArgs
        }

        @JvmStatic
        fun previousExceptionArgs(): List<Arguments> {
            val listArgs = listOf(
                Arguments.of(
                    Vector2FArray(0).asList(),
                    0,
                    { list: Vector2FList, _: Int -> list.listIterator() }
                ),
                Arguments.of(
                    Vector2FArray(0).asList(),
                    0,
                    { list: Vector2FList, index: Int -> list.listIterator(index) }
                ),
                Arguments.of(
                    Vector2FArray(4) { Vector2F(it.toFloat(), 0f) }.asList(),
                    4,
                    { list: Vector2FList, index: Int -> list.listIterator(index) }
                ),
                Arguments.of(
                    Vector2FArray(4) { Vector2F(it.toFloat(), 0f) }.asList(),
                    3,
                    { list: Vector2FList, index: Int -> list.listIterator(index) }
                ),
                Arguments.of(
                    Vector2FArray(4) { Vector2F(it.toFloat(), 0f) }.asList(),
                    0,
                    { list: Vector2FList, _: Int -> list.listIterator() }
                ),
            )
            val subListArgs = listOf(
                Arguments.of(
                    Vector2FArray(0).asList().subList(0, 0),
                    0,
                    { list: Vector2FSubList, _: Int -> list.listIterator() }
                ),
                Arguments.of(
                    Vector2FArray(0).asList().subList(0, 0),
                    0,
                    { list: Vector2FSubList, index: Int -> list.listIterator(index) }
                ),
                Arguments.of(
                    Vector2FArray(4) { Vector2F(it.toFloat(), 0f) }.asList().subList(0, 4),
                    4,
                    { list: Vector2FSubList, index: Int -> list.listIterator(index) }
                ),
                Arguments.of(
                    Vector2FArray(4) { Vector2F(it.toFloat(), 0f) }.asList().subList(0, 4),
                    3,
                    { list: Vector2FSubList, index: Int -> list.listIterator(index) }
                ),
                Arguments.of(
                    Vector2FArray(4) { Vector2F(it.toFloat(), 0f) }.asList().subList(0, 4),
                    0,
                    { list: Vector2FSubList, _: Int -> list.listIterator() }
                ),
                Arguments.of(
                    Vector2FArray(4) { Vector2F(it.toFloat(), 0f) }.asList().subList(1, 4),
                    3,
                    { list: Vector2FSubList, index: Int -> list.listIterator(index) }
                ),
                Arguments.of(
                    Vector2FArray(4) { Vector2F(it.toFloat(), 0f) }.asList().subList(1, 4),
                    2,
                    { list: Vector2FSubList, index: Int -> list.listIterator(index) }
                ),
                Arguments.of(
                    Vector2FArray(4) { Vector2F(it.toFloat(), 0f) }.asList().subList(1, 4),
                    0,
                    { list: Vector2FSubList, _: Int -> list.listIterator() }
                ),
                Arguments.of(
                    Vector2FArray(4) { Vector2F(it.toFloat(), 0f) }.asList().subList(0, 2),
                    2,
                    { list: Vector2FSubList, index: Int -> list.listIterator(index) }
                ),
                Arguments.of(
                    Vector2FArray(4) { Vector2F(it.toFloat(), 0f) }.asList().subList(0, 2),
                    1,
                    { list: Vector2FSubList, index: Int -> list.listIterator(index) }
                ),
                Arguments.of(
                    Vector2FArray(4) { Vector2F(it.toFloat(), 0f) }.asList().subList(0, 2),
                    0,
                    { list: Vector2FSubList, _: Int -> list.listIterator() }
                ),
            )
            return listArgs + subListArgs
        }

        @JvmStatic
        fun previousVector2FExceptionArgs(): List<Arguments> = previousVector2FArgs()

        @JvmStatic
        fun previousVector2FArgs(): List<Arguments> = previousArgs()

        @JvmStatic
        fun nextIndexArgs(): List<Arguments> = nextExceptionArgs()

        @JvmStatic
        fun previousIndexArgs(): List<Arguments> = previousExceptionArgs()

        @JvmStatic
        fun hasNextArgs(): List<Arguments> = nextExceptionArgs()

        @JvmStatic
        fun hasPreviousArgs(): List<Arguments> = previousExceptionArgs()
    }
}