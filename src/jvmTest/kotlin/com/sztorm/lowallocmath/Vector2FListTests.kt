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

class Vector2FListTests {
    @ParameterizedTest
    @MethodSource("sizeArgs")
    fun sizeReturnsCorrectValue(list: Wrapper<Vector2FList>, expected: Int) =
        assertEquals(expected, list.value.size)

    @ParameterizedTest
    @MethodSource("containsAllArgs")
    fun containsAllReturnsCorrectValue(
        list: Wrapper<Vector2FList>, elements: Collection<Vector2F>, expected: Boolean
    ) = assertEquals(expected, list.value.containsAll(elements))

    @ParameterizedTest
    @MethodSource("elementAtArgs")
    fun elementAtReturnsCorrectValue(
        list: Wrapper<Vector2FList>, index: Int, expected: Wrapper<Vector2F>
    ) = assertEquals(expected.value, list.value.elementAt(index))

    @ParameterizedTest
    @MethodSource("lists")
    fun elementAtThrowsWhenIndexIsOutOfBounds(list: Wrapper<Vector2FList>) {
        val unwrappedList: Vector2FList = list.value

        assertThrows<IndexOutOfBoundsException> { unwrappedList.elementAt(-1) }
        assertThrows<IndexOutOfBoundsException> { unwrappedList.elementAt(unwrappedList.size) }
    }

    @ParameterizedTest
    @MethodSource("indexOfArgs")
    fun indexOfReturnsCorrectValue(
        list: Wrapper<Vector2FList>, element: Wrapper<Vector2F>, expected: Int
    ) = assertEquals(expected, list.value.indexOf(element.value))

    @ParameterizedTest
    @MethodSource("isEmptyArgs")
    fun isEmptyReturnsCorrectValue(list: Wrapper<Vector2FList>, expected: Boolean) =
        assertEquals(expected, list.value.isEmpty())

    @ParameterizedTest
    @MethodSource("lastIndexOfArgs")
    fun lastIndexOfReturnsCorrectValue(
        list: Wrapper<Vector2FList>, element: Wrapper<Vector2F>, expected: Int
    ) = assertEquals(expected, list.value.lastIndexOf(element.value))

    @ParameterizedTest
    @MethodSource("lists")
    fun listIteratorThrowsWhenPositionIndexIsOutOfBounds(list: Wrapper<Vector2FList>) {
        val unwrappedList: Vector2FList = list.value

        assertThrows<IndexOutOfBoundsException> { unwrappedList.listIterator(-1) }
        assertThrows<IndexOutOfBoundsException> {
            unwrappedList.listIterator(unwrappedList.size + 1)
        }
    }

    @ParameterizedTest
    @MethodSource("subListArgs")
    fun subListReturnsCorrectValue(
        list: Wrapper<Vector2FList>, fromIndex: Int, toIndex: Int, expected: List<Vector2F>
    ) = assertContentEquals(expected, list.value.subList(fromIndex, toIndex))

    @ParameterizedTest
    @MethodSource("subListExceptionArgs")
    fun <T : Throwable> subListThrowsCorrectException(
        list: Wrapper<Vector2FList>,
        fromIndex: Int,
        toIndex: Int,
        expectedExceptionClass: Class<T>
    ) {
        val unwrappedList: Vector2FList = list.value

        Assertions.assertThrows(expectedExceptionClass) {
            unwrappedList.subList(fromIndex, toIndex)
        }
    }

    @Test
    fun destructedListReturnCorrectValues() {
        val list = arrayOf(
            Vector2F(1f, 0f),
            Vector2F(-5f, 8f),
            Vector2F(2f, -9f),
            Vector2F(3f, 4f),
            Vector2F(1f, 2f),
        ).toVector2FArray().asList()
        val (v1, v2, v3, v4, v5) = list

        assertEquals(Vector2F(1f, 0f), v1)
        assertEquals(Vector2F(-5f, 8f), v2)
        assertEquals(Vector2F(2f, -9f), v3)
        assertEquals(Vector2F(3f, 4f), v4)
        assertEquals(Vector2F(1f, 2f), v5)
    }

    @ParameterizedTest
    @MethodSource("toStringArgs")
    fun toStringReturnsCorrectValue(list: Wrapper<Vector2FList>, expected: String) =
        assertEquals(expected, list.value.toString())

    @ParameterizedTest
    @MethodSource("containsArgs")
    fun containsReturnsCorrectValue(
        list: Wrapper<Vector2FList>, element: Wrapper<Vector2F>, expected: Boolean
    ) = assertEquals(expected, list.value.contains(element.value))

    @ParameterizedTest
    @MethodSource("getArgs")
    fun getReturnsCorrectValue(
        list: Wrapper<Vector2FList>, index: Int, expected: Wrapper<Vector2F>
    ) = assertEquals(expected.value, list.value[index])

    @Suppress("KotlinConstantConditions")
    @ParameterizedTest
    @MethodSource("lists")
    fun getThrowsWhenIndexIsOutOfBounds(list: Wrapper<Vector2FList>) {
        val unwrappedList: Vector2FList = list.value

        assertThrows<IndexOutOfBoundsException> { unwrappedList[-1] }
        assertThrows<IndexOutOfBoundsException> { unwrappedList[unwrappedList.size] }
    }

    companion object {
        @JvmStatic
        fun lists(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2FArray(4) { Vector2F(it.toFloat(), -it.toFloat()) }.asList())
            ),
            Arguments.of(
                Wrapper(
                    arrayOf(Vector2F(4f, 1f), Vector2F(2f, 7f))
                        .toVector2FArray().asList()
                )
            ),
            Arguments.of(
                Wrapper(Vector2FArray(0).asList())
            ),
        )

        @JvmStatic
        fun sizeArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2FArray(4) { Vector2F(it.toFloat(), -it.toFloat()) }.asList()),
                4
            ),
            Arguments.of(
                Wrapper(
                    arrayOf(Vector2F(4f, 1f), Vector2F(2f, 7f))
                        .toVector2FArray().asList()
                ),
                2
            ),
            Arguments.of(
                Wrapper(Vector2FArray(0).asList()),
                0
            ),
        )

        @JvmStatic
        fun containsAllArgs(): List<Arguments> {
            val list = arrayOf(
                Vector2F(1f, 0f),
                Vector2F(-5f, 8f),
                Vector2F(2f, -9f),
            ).toVector2FArray().asList()

            return listOf(
                Arguments.of(
                    Wrapper(list), list, true
                ),
                Arguments.of(
                    Wrapper(list), listOf(Vector2F(1f, 0f), Vector2F(-5f, 8f)), true
                ),
                Arguments.of(
                    Wrapper(list), emptyList<Vector2F>(), true
                ),
                Arguments.of(
                    Wrapper(list), listOf(Vector2F.ZERO), false
                ),
            )
        }

        @JvmStatic
        fun elementAtArgs(): List<Arguments> = getArgs()

        @JvmStatic
        fun indexOfArgs(): List<Arguments> {
            val list = arrayOf(
                Vector2F(1f, 0f),
                Vector2F(3f, 4f),
                Vector2F(3f, 4f),
                Vector2F(-5f, 8f),
            ).toVector2FArray().asList()

            return listOf(
                Arguments.of(Wrapper(list), Wrapper(Vector2F(0f, 0f)), -1),
                Arguments.of(Wrapper(list), Wrapper(Vector2F(3f, 4f)), 1),
            )
        }

        @JvmStatic
        fun isEmptyArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2FArray(4) { Vector2F(it.toFloat(), -it.toFloat()) }.asList()),
                false
            ),
            Arguments.of(
                Wrapper(
                    arrayOf(Vector2F(4f, 1f), Vector2F(2f, 7f))
                        .toVector2FArray().asList()
                ),
                false
            ),
            Arguments.of(
                Wrapper(Vector2FArray(0).asList()),
                true
            ),
        )

        @JvmStatic
        fun lastIndexOfArgs(): List<Arguments> {
            val list = arrayOf(
                Vector2F(1f, 0f),
                Vector2F(3f, 4f),
                Vector2F(3f, 4f),
                Vector2F(-5f, 8f),
            ).toVector2FArray().asList()

            return listOf(
                Arguments.of(Wrapper(list), Wrapper(Vector2F(0f, 0f)), -1),
                Arguments.of(Wrapper(list), Wrapper(Vector2F(3f, 4f)), 2),
            )
        }

        @JvmStatic
        fun subListArgs(): List<Arguments> {
            val list = arrayOf(
                Vector2F(1f, 0f),
                Vector2F(-5f, 8f),
                Vector2F(2f, -9f),
                Vector2F(3f, 4f)
            ).toVector2FArray().asList()

            return listOf(
                Arguments.of(
                    Wrapper(list),
                    0, 4,
                    listOf(
                        Vector2F(1f, 0f),
                        Vector2F(-5f, 8f),
                        Vector2F(2f, -9f),
                        Vector2F(3f, 4f)
                    )
                ),
                Arguments.of(
                    Wrapper(list),
                    1, 3,
                    listOf(Vector2F(-5f, 8f), Vector2F(2f, -9f))
                ),
                Arguments.of(
                    Wrapper(Vector2FArray(0).asList()),
                    0, 0,
                    emptyList<Vector2F>()
                ),
            )
        }

        @JvmStatic
        fun subListExceptionArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2FArray(0).asList()), 0, 1, IndexOutOfBoundsException::class.java
            ),
            Arguments.of(
                Wrapper(Vector2FArray(4).asList()), 0, 5, IndexOutOfBoundsException::class.java
            ),
            Arguments.of(
                Wrapper(Vector2FArray(4).asList()), -1, 4, IndexOutOfBoundsException::class.java
            ),
            Arguments.of(
                Wrapper(Vector2FArray(4).asList()), 2, 1, IllegalArgumentException::class.java
            ),
        )

        @JvmStatic
        fun toStringArgs(): List<Arguments> = Vector2FArrayTests.toStringArgs().map {
            val (wrappedArray, expected) = it.get()
            val array = (wrappedArray as Wrapper<*>).value as Vector2FArray

            Arguments.of(Wrapper(array.asList()), expected)
        }

        @JvmStatic
        fun containsArgs(): List<Arguments> {
            val list = arrayOf(
                Vector2F(1f, 0f),
                Vector2F(-5f, 8f),
                Vector2F(2f, -9f),
                Vector2F(3f, 4f)
            ).toVector2FArray().asList()

            return listOf(
                Arguments.of(Wrapper(list), Wrapper(Vector2F(0f, 0f)), false),
                Arguments.of(Wrapper(list), Wrapper(Vector2F(3f, 4f)), true),
            )
        }

        @JvmStatic
        fun getArgs(): List<Arguments> {
            val list = arrayOf(
                Vector2F(1f, 0f),
                Vector2F(-5f, 8f),
                Vector2F(2f, -9f),
            ).toVector2FArray().asList()

            return listOf(
                Arguments.of(Wrapper(list), 0, Wrapper(Vector2F(1f, 0f))),
                Arguments.of(Wrapper(list), 1, Wrapper(Vector2F(-5f, 8f))),
                Arguments.of(Wrapper(list), 2, Wrapper(Vector2F(2f, -9f))),
            )
        }
    }
}