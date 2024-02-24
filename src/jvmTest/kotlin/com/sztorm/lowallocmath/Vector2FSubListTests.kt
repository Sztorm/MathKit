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

class Vector2FSubListTests {
    @ParameterizedTest
    @MethodSource("sizeArgs")
    fun sizeReturnsCorrectValue(list: Vector2FSubList, expected: Int) =
        assertEquals(expected, list.size)

    @ParameterizedTest
    @MethodSource("containsAllArgs")
    fun containsAllReturnsCorrectValue(
        list: Vector2FSubList, elements: Collection<Vector2F>, expected: Boolean
    ) = assertEquals(expected, list.containsAll(elements))

    @ParameterizedTest
    @MethodSource("elementAtArgs")
    fun elementAtReturnsCorrectValue(
        list: Vector2FSubList, index: Int, expected: Wrapper<Vector2F>
    ) = assertEquals(expected.value, list.elementAt(index))

    @ParameterizedTest
    @MethodSource("subLists")
    fun elementAtThrowsWhenIndexIsOutOfBounds(list: Vector2FSubList) {
        assertThrows<IndexOutOfBoundsException> { list.elementAt(-1) }
        assertThrows<IndexOutOfBoundsException> { list.elementAt(list.size) }
    }

    @ParameterizedTest
    @MethodSource("indexOfArgs")
    fun indexOfReturnsCorrectValue(
        list: Vector2FSubList, element: Wrapper<Vector2F>, expected: Int
    ) = assertEquals(expected, list.indexOf(element.value))

    @ParameterizedTest
    @MethodSource("isEmptyArgs")
    fun isEmptyReturnsCorrectValue(list: Vector2FSubList, expected: Boolean) =
        assertEquals(expected, list.isEmpty())

    @ParameterizedTest
    @MethodSource("lastIndexOfArgs")
    fun lastIndexOfReturnsCorrectValue(
        list: Vector2FSubList, element: Wrapper<Vector2F>, expected: Int
    ) = assertEquals(expected, list.lastIndexOf(element.value))

    @ParameterizedTest
    @MethodSource("subLists")
    fun listIteratorThrowsWhenPositionIndexIsOutOfBounds(list: Vector2FSubList) {
        assertThrows<IndexOutOfBoundsException> { list.listIterator(-1) }
        assertThrows<IndexOutOfBoundsException> {
            list.listIterator(list.size + 1)
        }
    }

    @ParameterizedTest
    @MethodSource("subListArgs")
    fun subListReturnsCorrectValue(
        list: Vector2FSubList, fromIndex: Int, toIndex: Int, expected: List<Vector2F>
    ) = assertContentEquals(expected, list.subList(fromIndex, toIndex))

    @ParameterizedTest
    @MethodSource("subListExceptionArgs")
    fun <T : Throwable> subListThrowsCorrectException(
        list: Vector2FSubList,
        fromIndex: Int,
        toIndex: Int,
        expectedExceptionClass: Class<T>
    ) {
        Assertions.assertThrows(expectedExceptionClass) {
            list.subList(fromIndex, toIndex)
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
        ).toVector2FArray().asList().subList(0, 5)
        val (v1, v2, v3, v4, v5) = list

        assertEquals(Vector2F(1f, 0f), v1)
        assertEquals(Vector2F(-5f, 8f), v2)
        assertEquals(Vector2F(2f, -9f), v3)
        assertEquals(Vector2F(3f, 4f), v4)
        assertEquals(Vector2F(1f, 2f), v5)
    }

    @ParameterizedTest
    @MethodSource("toStringArgs")
    fun toStringReturnsCorrectValue(list: Vector2FSubList, expected: String) =
        assertEquals(expected, list.toString())

    @ParameterizedTest
    @MethodSource("containsArgs")
    fun containsReturnsCorrectValue(
        list: Vector2FSubList, element: Wrapper<Vector2F>, expected: Boolean
    ) = assertEquals(expected, list.contains(element.value))

    @ParameterizedTest
    @MethodSource("getArgs")
    fun getReturnsCorrectValue(list: Vector2FSubList, index: Int, expected: Wrapper<Vector2F>) =
        assertEquals(expected.value, list[index])

    @Suppress("KotlinConstantConditions")
    @ParameterizedTest
    @MethodSource("subLists")
    fun getThrowsWhenIndexIsOutOfBounds(list: Vector2FSubList) {
        assertThrows<IndexOutOfBoundsException> { list[-1] }
        assertThrows<IndexOutOfBoundsException> { list[list.size] }
    }

    companion object {
        @JvmStatic
        fun subLists(): List<Arguments> {
            val list = Vector2FArray(5) { Vector2F(it.toFloat(), -it.toFloat()) }.asList()

            return listOf(
                Arguments.of(list.subList(0, 5)),
                Arguments.of(list.subList(1, 4)),
                Arguments.of(list.subList(1, 4).subList(0, 3)),
                Arguments.of(list.subList(1, 4).subList(1, 2)),
                Arguments.of(Vector2FArray(0).asList().subList(0, 0)),
            )
        }

        @JvmStatic
        fun sizeArgs(): List<Arguments> {
            val list = Vector2FArray(5) { Vector2F(it.toFloat(), -it.toFloat()) }.asList()

            return listOf(
                Arguments.of(list.subList(0, 5), 5),
                Arguments.of(list.subList(1, 4), 3),
                Arguments.of(list.subList(1, 4).subList(0, 3), 3),
                Arguments.of(list.subList(1, 4).subList(1, 2), 1),
                Arguments.of(Vector2FArray(0).asList().subList(0, 0), 0),
            )
        }

        @JvmStatic
        fun containsAllArgs(): List<Arguments> {
            val list = arrayOf(
                Vector2F(1f, 0f),
                Vector2F(-5f, 8f),
                Vector2F(2f, -9f),
                Vector2F(-4f, -2f),
            ).toVector2FArray().asList()

            return listOf(
                Arguments.of(
                    list.subList(0, 4), list, true
                ),
                Arguments.of(
                    list.subList(0, 4),
                    listOf(Vector2F(1f, 0f), Vector2F(-5f, 8f)),
                    true
                ),
                Arguments.of(
                    list.subList(1, 4),
                    listOf(Vector2F(1f, 0f), Vector2F(-5f, 8f)),
                    false
                ),
                Arguments.of(
                    list.subList(0, 4), emptyList<Vector2F>(), true
                ),
                Arguments.of(
                    list.subList(0, 4), listOf(Vector2F.ZERO), false
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
                Arguments.of(list.subList(0, 4), Wrapper(Vector2F(0f, 0f)), -1),
                Arguments.of(list.subList(0, 4), Wrapper(Vector2F(3f, 4f)), 1),
                Arguments.of(list.subList(1, 4), Wrapper(Vector2F(0f, 0f)), -1),
                Arguments.of(list.subList(1, 4), Wrapper(Vector2F(3f, 4f)), 0),
                Arguments.of(list.subList(1, 4), Wrapper(Vector2F(-5f, 8f)), 2),
                Arguments.of(list.subList(1, 3), Wrapper(Vector2F(3f, 4f)), 0),
                Arguments.of(list.subList(1, 3), Wrapper(Vector2F(-5f, 8f)), -1),
                Arguments.of(list.subList(0, 3), Wrapper(Vector2F(1f, 0f)), 0),
            )
        }

        @JvmStatic
        fun isEmptyArgs(): List<Arguments> {
            val list = Vector2FArray(5) { Vector2F(it.toFloat(), -it.toFloat()) }.asList()

            return listOf(
                Arguments.of(list.subList(0, 4), false),
                Arguments.of(list.subList(1, 2), false),
                Arguments.of(list.subList(4, 4), true),
                Arguments.of(list.subList(0, 0), true),
                Arguments.of(Vector2FArray(0).asList().subList(0, 0), true),
            )
        }

        @JvmStatic
        fun lastIndexOfArgs(): List<Arguments> {
            val list = arrayOf(
                Vector2F(1f, 0f),
                Vector2F(3f, 4f),
                Vector2F(3f, 4f),
                Vector2F(-5f, 8f),
            ).toVector2FArray().asList()

            return listOf(
                Arguments.of(list.subList(0, 4), Wrapper(Vector2F(0f, 0f)), -1),
                Arguments.of(list.subList(0, 4), Wrapper(Vector2F(3f, 4f)), 2),
                Arguments.of(list.subList(1, 4), Wrapper(Vector2F(0f, 0f)), -1),
                Arguments.of(list.subList(1, 4), Wrapper(Vector2F(3f, 4f)), 1),
                Arguments.of(list.subList(1, 4), Wrapper(Vector2F(-5f, 8f)), 2),
                Arguments.of(list.subList(1, 3), Wrapper(Vector2F(3f, 4f)), 1),
                Arguments.of(list.subList(1, 3), Wrapper(Vector2F(-5f, 8f)), -1),
                Arguments.of(list.subList(0, 3), Wrapper(Vector2F(1f, 0f)), 0),
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
                    list.subList(0, 4),
                    0, 4,
                    listOf(
                        Vector2F(1f, 0f),
                        Vector2F(-5f, 8f),
                        Vector2F(2f, -9f),
                        Vector2F(3f, 4f)
                    )
                ),
                Arguments.of(
                    list.subList(0, 4),
                    1, 3,
                    listOf(Vector2F(-5f, 8f), Vector2F(2f, -9f))
                ),
                Arguments.of(
                    list.subList(1, 4),
                    0, 2,
                    listOf(Vector2F(-5f, 8f), Vector2F(2f, -9f))
                ),
                Arguments.of(
                    Vector2FArray(0).asList().subList(0, 0),
                    0, 0,
                    emptyList<Vector2F>()
                ),
            )
        }

        @JvmStatic
        fun subListExceptionArgs(): List<Arguments> = listOf(
            Arguments.of(
                Vector2FArray(0).asList().subList(0, 0),
                0, 1,
                IndexOutOfBoundsException::class.java
            ),
            Arguments.of(
                Vector2FArray(4).asList().subList(0, 4),
                0, 5,
                IndexOutOfBoundsException::class.java
            ),
            Arguments.of(
                Vector2FArray(4).asList().subList(0, 4),
                -1, 4,
                IndexOutOfBoundsException::class.java
            ),
            Arguments.of(
                Vector2FArray(4).asList().subList(0, 4),
                2, 1,
                IllegalArgumentException::class.java
            ),
            Arguments.of(
                Vector2FArray(4).asList().subList(1, 4),
                0, 4,
                IndexOutOfBoundsException::class.java
            ),
            Arguments.of(
                Vector2FArray(4).asList().subList(1, 4),
                -1, 3,
                IndexOutOfBoundsException::class.java
            ),
        )

        @JvmStatic
        fun toStringArgs(): List<Arguments> = listOf(
            Arguments.of(
                arrayOf(
                    Vector2F(1f, 0f),
                    Vector2F(-5f, 8f),
                    Vector2F(2f, -9f),
                    Vector2F(3f, 4f)
                ).toVector2FArray().asList().subList(0, 4),
                "[${Vector2F(1f, 0f)}, ${Vector2F(-5f, 8f)}, " +
                        "${Vector2F(2f, -9f)}, ${Vector2F(3f, 4f)}]"
            ),
            Arguments.of(
                arrayOf(
                    Vector2F(1f, 0f),
                    Vector2F(-5f, 8f),
                    Vector2F(2f, -9f),
                    Vector2F(3f, 4f)
                ).toVector2FArray().asList().subList(1, 3),
                "[${Vector2F(-5f, 8f)}, ${Vector2F(2f, -9f)}]"
            ),
            Arguments.of(
                arrayOf(
                    Vector2F(1f, 0f),
                    Vector2F(-5f, 8f),
                    Vector2F(2f, -9f),
                    Vector2F(3f, 4f)
                ).toVector2FArray().asList().subList(0, 0),
                "[]"
            ),
        )

        @JvmStatic
        fun containsArgs(): List<Arguments> {
            val list = arrayOf(
                Vector2F(1f, 0f),
                Vector2F(3f, 4f),
                Vector2F(3f, 4f),
                Vector2F(-5f, 8f),
            ).toVector2FArray().asList()

            return listOf(
                Arguments.of(list.subList(0, 4), Wrapper(Vector2F(0f, 0f)), false),
                Arguments.of(list.subList(0, 4), Wrapper(Vector2F(3f, 4f)), true),
                Arguments.of(list.subList(1, 4), Wrapper(Vector2F(1f, 0f)), false),
                Arguments.of(list.subList(1, 4), Wrapper(Vector2F(3f, 4f)), true),
                Arguments.of(list.subList(1, 4), Wrapper(Vector2F(-5f, 8f)), true),
            )
        }

        @JvmStatic
        fun getArgs(): List<Arguments> {
            val list = arrayOf(
                Vector2F(1f, 0f),
                Vector2F(-5f, 8f),
                Vector2F(2f, -9f),
                Vector2F(3f, 4f)
            ).toVector2FArray().asList()

            return listOf(
                Arguments.of(list.subList(0, 4), 0, Wrapper(Vector2F(1f, 0f))),
                Arguments.of(list.subList(0, 4), 1, Wrapper(Vector2F(-5f, 8f))),
                Arguments.of(list.subList(0, 4), 2, Wrapper(Vector2F(2f, -9f))),
                Arguments.of(list.subList(0, 4), 3, Wrapper(Vector2F(3f, 4f))),
                Arguments.of(list.subList(1, 3), 0, Wrapper(Vector2F(-5f, 8f))),
                Arguments.of(list.subList(1, 3), 1, Wrapper(Vector2F(2f, -9f))),
                Arguments.of(list.subList(1, 4).subList(0, 2), 0, Wrapper(Vector2F(-5f, 8f))),
                Arguments.of(list.subList(1, 4).subList(0, 2), 1, Wrapper(Vector2F(2f, -9f))),
            )
        }
    }
}