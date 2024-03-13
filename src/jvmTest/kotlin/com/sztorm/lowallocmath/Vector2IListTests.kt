package com.sztorm.lowallocmath

import com.sztorm.lowallocmath.utils.Wrapper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class Vector2IListTests {
    @ParameterizedTest
    @MethodSource("sizeArgs")
    fun sizeReturnsCorrectValue(list: Wrapper<Vector2IList>, expected: Int) =
        assertEquals(expected, list.value.size)

    @ParameterizedTest
    @MethodSource("containsAllArgs")
    fun containsAllReturnsCorrectValue(
        list: Wrapper<Vector2IList>, elements: Collection<Vector2I>, expected: Boolean
    ) = assertEquals(expected, list.value.containsAll(elements))

    @ParameterizedTest
    @MethodSource("elementAtArgs")
    fun elementAtReturnsCorrectValue(
        list: Wrapper<Vector2IList>, index: Int, expected: Wrapper<Vector2I>
    ) = assertEquals(expected.value, list.value.elementAt(index))

    @ParameterizedTest
    @MethodSource("lists")
    fun elementAtThrowsWhenIndexIsOutOfBounds(list: Wrapper<Vector2IList>) {
        val unwrappedList: Vector2IList = list.value

        assertThrows<IndexOutOfBoundsException> { unwrappedList.elementAt(-1) }
        assertThrows<IndexOutOfBoundsException> { unwrappedList.elementAt(unwrappedList.size) }
    }

    @ParameterizedTest
    @MethodSource("indexOfArgs")
    fun indexOfReturnsCorrectValue(
        list: Wrapper<Vector2IList>, element: Wrapper<Vector2I>, expected: Int
    ) = assertEquals(expected, list.value.indexOf(element.value))

    @ParameterizedTest
    @MethodSource("isEmptyArgs")
    fun isEmptyReturnsCorrectValue(list: Wrapper<Vector2IList>, expected: Boolean) =
        assertEquals(expected, list.value.isEmpty())

    @ParameterizedTest
    @MethodSource("lastIndexOfArgs")
    fun lastIndexOfReturnsCorrectValue(
        list: Wrapper<Vector2IList>, element: Wrapper<Vector2I>, expected: Int
    ) = assertEquals(expected, list.value.lastIndexOf(element.value))

    @ParameterizedTest
    @MethodSource("lists")
    fun listIteratorThrowsWhenPositionIndexIsOutOfBounds(list: Wrapper<Vector2IList>) {
        val unwrappedList: Vector2IList = list.value

        assertThrows<IndexOutOfBoundsException> { unwrappedList.listIterator(-1) }
        assertThrows<IndexOutOfBoundsException> {
            unwrappedList.listIterator(unwrappedList.size + 1)
        }
    }

    @ParameterizedTest
    @MethodSource("subListArgs")
    fun subListReturnsCorrectValue(
        list: Wrapper<Vector2IList>, fromIndex: Int, toIndex: Int, expected: List<Vector2I>
    ) = assertContentEquals(expected, list.value.subList(fromIndex, toIndex))

    @ParameterizedTest
    @MethodSource("subListExceptionArgs")
    fun subListThrowsCorrectException(
        list: Wrapper<Vector2IList>,
        fromIndex: Int,
        toIndex: Int,
        expectedExceptionClass: Class<Throwable>
    ) {
        val unwrappedList: Vector2IList = list.value

        Assertions.assertThrows(expectedExceptionClass) {
            unwrappedList.subList(fromIndex, toIndex)
        }
    }

    @Test
    fun destructedListReturnCorrectValues() {
        val list = arrayOf(
            Vector2I(1, 0),
            Vector2I(-5, 8),
            Vector2I(2, -9),
            Vector2I(3, 4),
            Vector2I(1, 2),
        ).toVector2IArray().asList()
        val (v1, v2, v3, v4, v5) = list

        assertEquals(Vector2I(1, 0), v1)
        assertEquals(Vector2I(-5, 8), v2)
        assertEquals(Vector2I(2, -9), v3)
        assertEquals(Vector2I(3, 4), v4)
        assertEquals(Vector2I(1, 2), v5)
    }

    @ParameterizedTest
    @MethodSource("toStringArgs")
    fun toStringReturnsCorrectValue(list: Wrapper<Vector2IList>, expected: String) =
        assertEquals(expected, list.value.toString())

    @ParameterizedTest
    @MethodSource("containsArgs")
    fun containsReturnsCorrectValue(
        list: Wrapper<Vector2IList>, element: Wrapper<Vector2I>, expected: Boolean
    ) = assertEquals(expected, list.value.contains(element.value))

    @ParameterizedTest
    @MethodSource("getArgs")
    fun getReturnsCorrectValue(
        list: Wrapper<Vector2IList>, index: Int, expected: Wrapper<Vector2I>
    ) = assertEquals(expected.value, list.value[index])

    @Suppress("KotlinConstantConditions")
    @ParameterizedTest
    @MethodSource("lists")
    fun getThrowsWhenIndexIsOutOfBounds(list: Wrapper<Vector2IList>) {
        val unwrappedList: Vector2IList = list.value

        assertThrows<IndexOutOfBoundsException> { unwrappedList[-1] }
        assertThrows<IndexOutOfBoundsException> { unwrappedList[unwrappedList.size] }
    }

    companion object {
        @JvmStatic
        fun lists(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2IArray(4) { Vector2I(it, -it) }.asList())
            ),
            Arguments.of(
                Wrapper(
                    arrayOf(Vector2I(4, 1), Vector2I(2, 7))
                        .toVector2IArray().asList()
                )
            ),
            Arguments.of(
                Wrapper(Vector2IArray(0).asList())
            ),
        )

        @JvmStatic
        fun sizeArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2IArray(4) { Vector2I(it, -it) }.asList()),
                4
            ),
            Arguments.of(
                Wrapper(
                    arrayOf(Vector2I(4, 1), Vector2I(2, 7))
                        .toVector2IArray().asList()
                ),
                2
            ),
            Arguments.of(
                Wrapper(Vector2IArray(0).asList()),
                0
            ),
        )

        @JvmStatic
        fun containsAllArgs(): List<Arguments> {
            val list = arrayOf(
                Vector2I(1, 0),
                Vector2I(-5, 8),
                Vector2I(2, -9),
            ).toVector2IArray().asList()

            return listOf(
                Arguments.of(
                    Wrapper(list), list, true
                ),
                Arguments.of(
                    Wrapper(list), listOf(Vector2I(1, 0), Vector2I(-5, 8)), true
                ),
                Arguments.of(
                    Wrapper(list), emptyList<Vector2I>(), true
                ),
                Arguments.of(
                    Wrapper(list), listOf(Vector2I.ZERO), false
                ),
            )
        }

        @JvmStatic
        fun elementAtArgs(): List<Arguments> = getArgs()

        @JvmStatic
        fun indexOfArgs(): List<Arguments> {
            val list = arrayOf(
                Vector2I(1, 0),
                Vector2I(3, 4),
                Vector2I(3, 4),
                Vector2I(-5, 8),
            ).toVector2IArray().asList()

            return listOf(
                Arguments.of(Wrapper(list), Wrapper(Vector2I(0, 0)), -1),
                Arguments.of(Wrapper(list), Wrapper(Vector2I(3, 4)), 1),
            )
        }

        @JvmStatic
        fun isEmptyArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2IArray(4) { Vector2I(it, -it) }.asList()),
                false
            ),
            Arguments.of(
                Wrapper(
                    arrayOf(Vector2I(4, 1), Vector2I(2, 7))
                        .toVector2IArray().asList()
                ),
                false
            ),
            Arguments.of(
                Wrapper(Vector2IArray(0).asList()),
                true
            ),
        )

        @JvmStatic
        fun lastIndexOfArgs(): List<Arguments> {
            val list = arrayOf(
                Vector2I(1, 0),
                Vector2I(3, 4),
                Vector2I(3, 4),
                Vector2I(-5, 8),
            ).toVector2IArray().asList()

            return listOf(
                Arguments.of(Wrapper(list), Wrapper(Vector2I(0, 0)), -1),
                Arguments.of(Wrapper(list), Wrapper(Vector2I(3, 4)), 2),
            )
        }

        @JvmStatic
        fun subListArgs(): List<Arguments> {
            val list = arrayOf(
                Vector2I(1, 0),
                Vector2I(-5, 8),
                Vector2I(2, -9),
                Vector2I(3, 4)
            ).toVector2IArray().asList()

            return listOf(
                Arguments.of(
                    Wrapper(list),
                    0, 4,
                    listOf(
                        Vector2I(1, 0),
                        Vector2I(-5, 8),
                        Vector2I(2, -9),
                        Vector2I(3, 4)
                    )
                ),
                Arguments.of(
                    Wrapper(list),
                    1, 3,
                    listOf(Vector2I(-5, 8), Vector2I(2, -9))
                ),
                Arguments.of(
                    Wrapper(Vector2IArray(0).asList()),
                    0, 0,
                    emptyList<Vector2I>()
                ),
            )
        }

        @JvmStatic
        fun subListExceptionArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2IArray(0).asList()), 0, 1, IndexOutOfBoundsException::class.java
            ),
            Arguments.of(
                Wrapper(Vector2IArray(4).asList()), 0, 5, IndexOutOfBoundsException::class.java
            ),
            Arguments.of(
                Wrapper(Vector2IArray(4).asList()), -1, 4, IndexOutOfBoundsException::class.java
            ),
            Arguments.of(
                Wrapper(Vector2IArray(4).asList()), 2, 1, IllegalArgumentException::class.java
            ),
        )

        @JvmStatic
        fun toStringArgs(): List<Arguments> = Vector2IArrayTests.toStringArgs().map {
            val (wrappedArray, expected) = it.get()
            val array = (wrappedArray as Wrapper<*>).value as Vector2IArray

            Arguments.of(Wrapper(array.asList()), expected)
        }

        @JvmStatic
        fun containsArgs(): List<Arguments> {
            val list = arrayOf(
                Vector2I(1, 0),
                Vector2I(-5, 8),
                Vector2I(2, -9),
                Vector2I(3, 4)
            ).toVector2IArray().asList()

            return listOf(
                Arguments.of(Wrapper(list), Wrapper(Vector2I(0, 0)), false),
                Arguments.of(Wrapper(list), Wrapper(Vector2I(3, 4)), true),
            )
        }

        @JvmStatic
        fun getArgs(): List<Arguments> {
            val list = arrayOf(
                Vector2I(1, 0),
                Vector2I(-5, 8),
                Vector2I(2, -9),
            ).toVector2IArray().asList()

            return listOf(
                Arguments.of(Wrapper(list), 0, Wrapper(Vector2I(1, 0))),
                Arguments.of(Wrapper(list), 1, Wrapper(Vector2I(-5, 8))),
                Arguments.of(Wrapper(list), 2, Wrapper(Vector2I(2, -9))),
            )
        }
    }
}