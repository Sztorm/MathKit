package com.sztorm.lowallocmath

import com.sztorm.lowallocmath.utils.Wrapper
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class Vector2FArrayIteratorTests {

    @ParameterizedTest
    @MethodSource("nextArgs")
    fun nextMutatesIteratorCorrectly(array: Wrapper<Vector2FArray>, expected: List<Vector2F>) {
        val iterator: Vector2FIterator = array.value.iterator()
        val actual = emptyList<Vector2F>().toMutableList()

        for (i in 0..array.value.lastIndex) {
            actual.add(iterator.next())
        }
        assertContentEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("nextVector2FArgs")
    fun nextVector2FMutatesIteratorCorrectly(
        array: Wrapper<Vector2FArray>, expected: List<Vector2F>
    ) {
        val iterator: Vector2FIterator = array.value.iterator()
        val actual = emptyList<Vector2F>().toMutableList()

        for (i in 0..array.value.lastIndex) {
            actual.add(iterator.nextVector2F())
        }
        assertContentEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("hasNextArgs")
    fun hasNextReturnsCorrectValue(array: Wrapper<Vector2FArray>, expected: List<Boolean>) {
        val iterator: Vector2FIterator = array.value.iterator()
        val actual = emptyList<Boolean>().toMutableList()

        for (i in 0..array.value.lastIndex) {
            actual.add(iterator.hasNext())
            iterator.next()
        }
        assertEquals(false, iterator.hasNext())
        assertContentEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("arrays")
    fun nextThrowsWhenDoesNotHaveNextElement(array: Wrapper<Vector2FArray>) {
        val iterator: Vector2FIterator = array.value.iterator()

        for (i in 0..array.value.lastIndex) {
            iterator.next()
        }
        assertThrows<NoSuchElementException> { iterator.next() }
    }

    @ParameterizedTest
    @MethodSource("arrays")
    fun nextThrowsWhenDoesNotHaveNextVector2F(array: Wrapper<Vector2FArray>) {
        val iterator: Vector2FIterator = array.value.iterator()

        for (i in 0..array.value.lastIndex) {
            iterator.nextVector2F()
        }
        assertThrows<NoSuchElementException> { iterator.nextVector2F() }
    }

    companion object {
        @JvmStatic
        fun arrays(): List<Arguments> = Vector2FArrayTests.arrays()

        @JvmStatic
        fun nextArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2FArray(0)),
                emptyList<Vector2F>(),
            ),
            Arguments.of(
                Wrapper(Vector2FArray(4) { Vector2F(it.toFloat(), 0f) }),
                List(4) { Vector2F(it.toFloat(), 0f) },
            ),
        )

        @JvmStatic
        fun nextVector2FArgs(): List<Arguments> = nextArgs()

        @JvmStatic
        fun hasNextArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2FArray(0)),
                emptyList<Boolean>(),
            ),
            Arguments.of(
                Wrapper(Vector2FArray(4) { Vector2F(it.toFloat(), 0f) }),
                List(4) { true },
            ),
        )
    }
}