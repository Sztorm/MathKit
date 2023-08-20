package com.sztorm.lowallocmath

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class Vector2IListIteratorTests {
    @ParameterizedTest
    @MethodSource("nextArgs")
    fun nextMutatesIteratorCorrectly(
        collection: Collection<Vector2I>,
        index: Int,
        iteratorCreator: (Collection<Vector2I>, index: Int) -> Vector2IListIterator,
        expected: List<Vector2I>
    ) {
        val iterator: Vector2IListIterator = iteratorCreator(collection, index)

        for (v in expected) {
            assertEquals(v, iterator.next())
        }
    }

    @ParameterizedTest
    @MethodSource("nextExceptionArgs")
    fun nextThrowsWhenDoesNotHaveNextElement(
        collection: Collection<Vector2I>,
        index: Int,
        iteratorCreator: (Collection<Vector2I>, index: Int) -> Vector2IListIterator
    ) {
        val iterator: Vector2IIterator = iteratorCreator(collection, index)

        for (i in index until collection.size) {
            iterator.next()
        }
        assertThrows<NoSuchElementException> { iterator.next() }
    }

    @ParameterizedTest
    @MethodSource("nextVector2IArgs")
    fun nextVector2IMutatesIteratorCorrectly(
        collection: Collection<Vector2I>,
        index: Int,
        iteratorCreator: (Collection<Vector2I>, index: Int) -> Vector2IListIterator,
        expected: List<Vector2I>
    ) {
        val iterator: Vector2IListIterator = iteratorCreator(collection, index)

        for (v in expected) {
            assertEquals(v, iterator.nextVector2I())
        }
    }

    @ParameterizedTest
    @MethodSource("nextVector2IExceptionArgs")
    fun nextVector2IThrowsWhenDoesNotHaveNextElement(
        collection: Collection<Vector2I>,
        index: Int,
        iteratorCreator: (Collection<Vector2I>, index: Int) -> Vector2IListIterator
    ) {
        val iterator: Vector2IListIterator = iteratorCreator(collection, index)

        for (i in index until collection.size) {
            iterator.nextVector2I()
        }
        assertThrows<NoSuchElementException> { iterator.nextVector2I() }
    }

    @ParameterizedTest
    @MethodSource("previousArgs")
    fun previousMutatesIteratorCorrectly(
        collection: Collection<Vector2I>,
        index: Int,
        iteratorCreator: (Collection<Vector2I>, index: Int) -> Vector2IListIterator,
        expected: List<Vector2I>
    ) {
        val iterator: Vector2IListIterator = iteratorCreator(collection, index)

        for (v in expected) {
            assertEquals(v, iterator.previous())
        }
    }

    @ParameterizedTest
    @MethodSource("previousExceptionArgs")
    fun previousThrowsWhenDoesNotHavePreviousElement(
        collection: Collection<Vector2I>,
        index: Int,
        iteratorCreator: (Collection<Vector2I>, index: Int) -> Vector2IListIterator
    ) {
        val iterator: Vector2IListIterator = iteratorCreator(collection, index)

        for (i in index - 1 downTo 0) {
            iterator.previous()
        }
        assertThrows<NoSuchElementException> { iterator.previous() }
    }

    @ParameterizedTest
    @MethodSource("previousVector2IArgs")
    fun previousVector2IMutatesIteratorCorrectly(
        collection: Collection<Vector2I>,
        index: Int,
        iteratorCreator: (Collection<Vector2I>, index: Int) -> Vector2IListIterator,
        expected: List<Vector2I>
    ) {
        val iterator: Vector2IListIterator = iteratorCreator(collection, index)

        for (v in expected) {
            assertEquals(v, iterator.previousVector2I())
        }
    }

    @ParameterizedTest
    @MethodSource("previousVector2IExceptionArgs")
    fun previousVector2IThrowsWhenDoesNotHavePreviousElement(
        collection: Collection<Vector2I>,
        index: Int,
        iteratorCreator: (Collection<Vector2I>, index: Int) -> Vector2IListIterator
    ) {
        val iterator: Vector2IListIterator = iteratorCreator(collection, index)

        for (i in index - 1 downTo 0) {
            iterator.previousVector2I()
        }
        assertThrows<NoSuchElementException> { iterator.previousVector2I() }
    }

    @ParameterizedTest
    @MethodSource("nextIndexArgs")
    fun nextIndexReturnsCorrectValue(
        collection: Collection<Vector2I>,
        index: Int,
        iteratorCreator: (Collection<Vector2I>, index: Int) -> Vector2IListIterator
    ) {
        val iterator: Vector2IListIterator = iteratorCreator(collection, index)

        for (i in index until collection.size) {
            assertEquals(i, iterator.nextIndex())
            iterator.next()
        }
        assertEquals(collection.size, iterator.nextIndex())
    }

    @ParameterizedTest
    @MethodSource("previousIndexArgs")
    fun previousIndexReturnsCorrectValue(
        collection: Collection<Vector2I>,
        index: Int,
        iteratorCreator: (Collection<Vector2I>, index: Int) -> Vector2IListIterator
    ) {
        val iterator: Vector2IListIterator = iteratorCreator(collection, index)

        for (i in index - 1 downTo 0) {
            assertEquals(i, iterator.previousIndex())
            iterator.previous()
        }
        assertEquals(-1, iterator.previousIndex())
    }

    @ParameterizedTest
    @MethodSource("hasNextArgs")
    fun hasNextReturnsCorrectValue(
        collection: Collection<Vector2I>,
        index: Int,
        iteratorCreator: (Collection<Vector2I>, index: Int) -> Vector2IListIterator
    ) {
        val iterator: Vector2IListIterator = iteratorCreator(collection, index)

        for (i in index until collection.size) {
            assertTrue(iterator.hasNext())
            iterator.next()
        }
        assertFalse(iterator.hasNext())
    }

    @ParameterizedTest
    @MethodSource("hasPreviousArgs")
    fun hasPreviousReturnsCorrectValue(
        collection: Collection<Vector2I>,
        index: Int,
        iteratorCreator: (Collection<Vector2I>, index: Int) -> Vector2IListIterator
    ) {
        val iterator: Vector2IListIterator = iteratorCreator(collection, index)

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
                    Vector2IArray(0).asList(),
                    0,
                    { list: Vector2IList, _: Int -> list.listIterator() },
                    emptyList<Vector2I>()
                ),
                Arguments.of(
                    Vector2IArray(0).asList(),
                    0,
                    { list: Vector2IList, index: Int -> list.listIterator(index) },
                    emptyList<Vector2I>()
                ),
                Arguments.of(
                    Vector2IArray(4) { Vector2I(it, 0) }.asList(),
                    0,
                    { list: Vector2IList, _: Int -> list.listIterator() },
                    List(4) { Vector2I(it, 0) }
                ),
                Arguments.of(
                    Vector2IArray(4) { Vector2I(it, 0) }.asList(),
                    1,
                    { list: Vector2IList, index: Int -> list.listIterator(index) },
                    List(4) { Vector2I(it, 0) }.drop(1)
                ),
            )
            val subListArgs = listOf(
                Arguments.of(
                    Vector2IArray(0).asList().subList(0, 0),
                    0,
                    { list: Vector2ISubList, _: Int -> list.listIterator() },
                    emptyList<Vector2I>()
                ),
                Arguments.of(
                    Vector2IArray(0).asList().subList(0, 0),
                    0,
                    { list: Vector2ISubList, index: Int -> list.listIterator(index) },
                    emptyList<Vector2I>()
                ),
                Arguments.of(
                    Vector2IArray(4) { Vector2I(it, 0) }.asList().subList(0, 4),
                    0,
                    { list: Vector2ISubList, _: Int -> list.listIterator() },
                    List(4) { Vector2I(it, 0) }
                ),
                Arguments.of(
                    Vector2IArray(4) { Vector2I(it, 0) }.asList().subList(0, 4),
                    1,
                    { list: Vector2ISubList, index: Int -> list.listIterator(index) },
                    List(4) { Vector2I(it, 0) }.drop(1)
                ),
                Arguments.of(
                    Vector2IArray(4) { Vector2I(it, 0) }.asList().subList(1, 4),
                    0,
                    { list: Vector2ISubList, _: Int -> list.listIterator() },
                    List(4) { Vector2I(it, 0) }.subList(1, 4)
                ),
                Arguments.of(
                    Vector2IArray(4) { Vector2I(it, 0) }.asList().subList(1, 4),
                    1,
                    { list: Vector2ISubList, index: Int -> list.listIterator(index) },
                    List(4) { Vector2I(it, 0) }.subList(1, 4).drop(1)
                ),
                Arguments.of(
                    Vector2IArray(4) { Vector2I(it, 0) }.asList().subList(0, 2),
                    0,
                    { list: Vector2ISubList, _: Int -> list.listIterator() },
                    List(4) { Vector2I(it, 0) }.subList(0, 2)
                ),
                Arguments.of(
                    Vector2IArray(4) { Vector2I(it, 0) }.asList().subList(0, 2),
                    1,
                    { list: Vector2ISubList, index: Int -> list.listIterator(index) },
                    List(4) { Vector2I(it, 0) }.subList(0, 2).drop(1)
                ),
            )
            return listArgs + subListArgs
        }

        @JvmStatic
        fun nextExceptionArgs(): List<Arguments> {
            val listArgs = listOf(
                Arguments.of(
                    Vector2IArray(0).asList(),
                    0,
                    { list: Vector2IList, _: Int -> list.listIterator() }
                ),
                Arguments.of(
                    Vector2IArray(0).asList(),
                    0,
                    { list: Vector2IList, index: Int -> list.listIterator(index) }
                ),
                Arguments.of(
                    Vector2IArray(4) { Vector2I(it, 0) }.asList(),
                    0,
                    { list: Vector2IList, _: Int -> list.listIterator() }
                ),
                Arguments.of(
                    Vector2IArray(4) { Vector2I(it, 0) }.asList(),
                    1,
                    { list: Vector2IList, index: Int -> list.listIterator(index) }
                ),
            )
            val subListArgs = listOf(
                Arguments.of(
                    Vector2IArray(0).asList().subList(0, 0),
                    0,
                    { list: Vector2ISubList, _: Int -> list.listIterator() }
                ),
                Arguments.of(
                    Vector2IArray(0).asList().subList(0, 0),
                    0,
                    { list: Vector2ISubList, index: Int -> list.listIterator(index) }
                ),
                Arguments.of(
                    Vector2IArray(4) { Vector2I(it, 0) }.asList().subList(0, 4),
                    0,
                    { list: Vector2ISubList, _: Int -> list.listIterator() }
                ),
                Arguments.of(
                    Vector2IArray(4) { Vector2I(it, 0) }.asList().subList(0, 4),
                    1,
                    { list: Vector2ISubList, index: Int -> list.listIterator(index) }
                ),
                Arguments.of(
                    Vector2IArray(4) { Vector2I(it, 0) }.asList().subList(1, 4),
                    0,
                    { list: Vector2ISubList, _: Int -> list.listIterator() }
                ),
                Arguments.of(
                    Vector2IArray(4) { Vector2I(it, 0) }.asList().subList(1, 4),
                    1,
                    { list: Vector2ISubList, index: Int -> list.listIterator(index) }
                ),
                Arguments.of(
                    Vector2IArray(4) { Vector2I(it, 0) }.asList().subList(0, 2),
                    0,
                    { list: Vector2ISubList, _: Int -> list.listIterator() }
                ),
                Arguments.of(
                    Vector2IArray(4) { Vector2I(it, 0) }.asList().subList(0, 2),
                    1,
                    { list: Vector2ISubList, index: Int -> list.listIterator(index) }
                ),
            )
            return listArgs + subListArgs
        }

        @JvmStatic
        fun nextVector2IArgs(): List<Arguments> = nextArgs()

        @JvmStatic
        fun nextVector2IExceptionArgs(): List<Arguments> = nextExceptionArgs()

        @JvmStatic
        fun previousArgs(): List<Arguments> {
            val listArgs = listOf(
                Arguments.of(
                    Vector2IArray(0).asList(),
                    0,
                    { list: Vector2IList, _: Int -> list.listIterator() },
                    emptyList<Vector2I>()
                ),
                Arguments.of(
                    Vector2IArray(0).asList(),
                    0,
                    { list: Vector2IList, index: Int -> list.listIterator(index) },
                    emptyList<Vector2I>()
                ),
                Arguments.of(
                    Vector2IArray(4) { Vector2I(it, 0) }.asList(),
                    4,
                    { list: Vector2IList, index: Int -> list.listIterator(index) },
                    List(4) { Vector2I(it, 0) }.reversed()
                ),
                Arguments.of(
                    Vector2IArray(4) { Vector2I(it, 0) }.asList(),
                    3,
                    { list: Vector2IList, index: Int -> list.listIterator(index) },
                    List(4) { Vector2I(it, 0) }.dropLast(1).reversed()
                ),
                Arguments.of(
                    Vector2IArray(4) { Vector2I(it, 0) }.asList(),
                    0,
                    { list: Vector2IList, _: Int -> list.listIterator() },
                    emptyList<Vector2I>()
                ),
            )
            val subListArgs = listOf(
                Arguments.of(
                    Vector2IArray(0).asList().subList(0, 0),
                    0,
                    { list: Vector2ISubList, _: Int -> list.listIterator() },
                    emptyList<Vector2I>()
                ),
                Arguments.of(
                    Vector2IArray(0).asList().subList(0, 0),
                    0,
                    { list: Vector2ISubList, index: Int -> list.listIterator(index) },
                    emptyList<Vector2I>()
                ),
                Arguments.of(
                    Vector2IArray(4) { Vector2I(it, 0) }.asList().subList(0, 4),
                    4,
                    { list: Vector2ISubList, index: Int -> list.listIterator(index) },
                    List(4) { Vector2I(it, 0) }.reversed()
                ),
                Arguments.of(
                    Vector2IArray(4) { Vector2I(it, 0) }.asList().subList(0, 4),
                    3,
                    { list: Vector2ISubList, index: Int -> list.listIterator(index) },
                    List(4) { Vector2I(it, 0) }.dropLast(1).reversed()
                ),
                Arguments.of(
                    Vector2IArray(4) { Vector2I(it, 0) }.asList().subList(0, 4),
                    0,
                    { list: Vector2ISubList, _: Int -> list.listIterator() },
                    emptyList<Vector2I>()
                ),
                Arguments.of(
                    Vector2IArray(4) { Vector2I(it, 0) }.asList().subList(1, 4),
                    3,
                    { list: Vector2ISubList, index: Int -> list.listIterator(index) },
                    List(4) { Vector2I(it, 0) }.subList(1, 4).reversed()
                ),
                Arguments.of(
                    Vector2IArray(4) { Vector2I(it, 0) }.asList().subList(1, 4),
                    2,
                    { list: Vector2ISubList, index: Int -> list.listIterator(index) },
                    List(4) { Vector2I(it, 0) }.subList(1, 4)
                        .dropLast(1).reversed()
                ),
                Arguments.of(
                    Vector2IArray(4) { Vector2I(it, 0) }.asList().subList(1, 4),
                    0,
                    { list: Vector2ISubList, _: Int -> list.listIterator() },
                    emptyList<Vector2I>()
                ),
                Arguments.of(
                    Vector2IArray(4) { Vector2I(it, 0) }.asList().subList(0, 2),
                    2,
                    { list: Vector2ISubList, index: Int -> list.listIterator(index) },
                    List(4) { Vector2I(it, 0) }.subList(0, 2).reversed()
                ),
                Arguments.of(
                    Vector2IArray(4) { Vector2I(it, 0) }.asList().subList(0, 2),
                    1,
                    { list: Vector2ISubList, index: Int -> list.listIterator(index) },
                    List(4) { Vector2I(it, 0) }.subList(0, 2)
                        .dropLast(1).reversed()
                ),
                Arguments.of(
                    Vector2IArray(4) { Vector2I(it, 0) }.asList().subList(0, 2),
                    0,
                    { list: Vector2ISubList, _: Int -> list.listIterator() },
                    emptyList<Vector2I>()
                ),
            )
            return listArgs + subListArgs
        }

        @JvmStatic
        fun previousExceptionArgs(): List<Arguments> {
            val listArgs = listOf(
                Arguments.of(
                    Vector2IArray(0).asList(),
                    0,
                    { list: Vector2IList, _: Int -> list.listIterator() }
                ),
                Arguments.of(
                    Vector2IArray(0).asList(),
                    0,
                    { list: Vector2IList, index: Int -> list.listIterator(index) }
                ),
                Arguments.of(
                    Vector2IArray(4) { Vector2I(it, 0) }.asList(),
                    4,
                    { list: Vector2IList, index: Int -> list.listIterator(index) }
                ),
                Arguments.of(
                    Vector2IArray(4) { Vector2I(it, 0) }.asList(),
                    3,
                    { list: Vector2IList, index: Int -> list.listIterator(index) }
                ),
                Arguments.of(
                    Vector2IArray(4) { Vector2I(it, 0) }.asList(),
                    0,
                    { list: Vector2IList, _: Int -> list.listIterator() }
                ),
            )
            val subListArgs = listOf(
                Arguments.of(
                    Vector2IArray(0).asList().subList(0, 0),
                    0,
                    { list: Vector2ISubList, _: Int -> list.listIterator() }
                ),
                Arguments.of(
                    Vector2IArray(0).asList().subList(0, 0),
                    0,
                    { list: Vector2ISubList, index: Int -> list.listIterator(index) }
                ),
                Arguments.of(
                    Vector2IArray(4) { Vector2I(it, 0) }.asList().subList(0, 4),
                    4,
                    { list: Vector2ISubList, index: Int -> list.listIterator(index) }
                ),
                Arguments.of(
                    Vector2IArray(4) { Vector2I(it, 0) }.asList().subList(0, 4),
                    3,
                    { list: Vector2ISubList, index: Int -> list.listIterator(index) }
                ),
                Arguments.of(
                    Vector2IArray(4) { Vector2I(it, 0) }.asList().subList(0, 4),
                    0,
                    { list: Vector2ISubList, _: Int -> list.listIterator() }
                ),
                Arguments.of(
                    Vector2IArray(4) { Vector2I(it, 0) }.asList().subList(1, 4),
                    3,
                    { list: Vector2ISubList, index: Int -> list.listIterator(index) }
                ),
                Arguments.of(
                    Vector2IArray(4) { Vector2I(it, 0) }.asList().subList(1, 4),
                    2,
                    { list: Vector2ISubList, index: Int -> list.listIterator(index) }
                ),
                Arguments.of(
                    Vector2IArray(4) { Vector2I(it, 0) }.asList().subList(1, 4),
                    0,
                    { list: Vector2ISubList, _: Int -> list.listIterator() }
                ),
                Arguments.of(
                    Vector2IArray(4) { Vector2I(it, 0) }.asList().subList(0, 2),
                    2,
                    { list: Vector2ISubList, index: Int -> list.listIterator(index) }
                ),
                Arguments.of(
                    Vector2IArray(4) { Vector2I(it, 0) }.asList().subList(0, 2),
                    1,
                    { list: Vector2ISubList, index: Int -> list.listIterator(index) }
                ),
                Arguments.of(
                    Vector2IArray(4) { Vector2I(it, 0) }.asList().subList(0, 2),
                    0,
                    { list: Vector2ISubList, _: Int -> list.listIterator() }
                ),
            )
            return listArgs + subListArgs
        }

        @JvmStatic
        fun previousVector2IExceptionArgs(): List<Arguments> = previousVector2IArgs()

        @JvmStatic
        fun previousVector2IArgs(): List<Arguments> = previousArgs()

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