package com.sztorm.mathkit

import com.sztorm.mathkit.utils.Wrapper
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.math.absoluteValue
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.collections.forEach as forEachKt

class Vector2FArrayTests {
    @Test
    fun basicPropertiesAreValid() {
        val array = Vector2FArray(5) { Vector2F(it.toFloat(), -it.toFloat()) }
        val (a0, a1, a2, a3, a4) = array

        assertEquals(5, array.size)
        assertEquals(4, array.lastIndex)
        assertEquals(IntRange(0, 4), array.indices)
        assertEquals(Vector2F(0f, -0f), array[0])
        assertEquals(Vector2F(1f, -1f), array[1])
        assertEquals(Vector2F(2f, -2f), array[2])
        assertEquals(Vector2F(3f, -3f), array[3])
        assertEquals(Vector2F(4f, -4f), array[4])
        assertEquals(Vector2F(0f, -0f), a0)
        assertEquals(Vector2F(1f, -1f), a1)
        assertEquals(Vector2F(2f, -2f), a2)
        assertEquals(Vector2F(3f, -3f), a3)
        assertEquals(Vector2F(4f, -4f), a4)
    }

    @Test
    fun constructorThrowsWhenSizeIsNegative() {
        assertThrows<NegativeArraySizeException> { Vector2FArray(-1) }
    }

    @ParameterizedTest
    @MethodSource("allArgs")
    fun allReturnsCorrectValue(
        array: Wrapper<Vector2FArray>, predicate: (Vector2F) -> Boolean, expected: Boolean
    ) = assertEquals(expected, array.value.all(predicate))

    @ParameterizedTest
    @MethodSource("anyArgs")
    fun anyReturnsCorrectValue(array: Wrapper<Vector2FArray>, expected: Boolean) =
        assertEquals(expected, array.value.any())

    @ParameterizedTest
    @MethodSource("anyPredicateArgs")
    fun anyReturnsCorrectValue(
        array: Wrapper<Vector2FArray>, predicate: (Vector2F) -> Boolean, expected: Boolean
    ) = assertEquals(expected, array.value.any(predicate))

    @ParameterizedTest
    @MethodSource("asListArgs")
    fun asListReturnsCorrectValue(array: Wrapper<Vector2FArray>, expected: List<Vector2F>) =
        assertContentEquals(expected, array.value.asList())

    @ParameterizedTest
    @MethodSource("containsAllArgs")
    fun containsAllReturnsCorrectValue(
        array: Wrapper<Vector2FArray>, elements: Collection<Vector2F>, expected: Boolean
    ) = assertEquals(expected, array.value.containsAll(elements))

    @ParameterizedTest
    @MethodSource("copyIntoArgs")
    fun copyIntoReturnsCorrectValue(
        array: Wrapper<Vector2FArray>,
        destination: Wrapper<Vector2FArray>,
        destinationOffset: Int,
        startIndex: Int,
        endIndex: Int,
        expected: Wrapper<Vector2FArray>
    ) = assertContentEquals(
        expected.value, array.value.copyInto(
            destination.value,
            destinationOffset,
            startIndex,
            endIndex
        )
    )

    @ParameterizedTest
    @MethodSource("copyIntoThrowsExceptionArgs")
    fun copyIntoThrowsCorrectException(
        array: Wrapper<Vector2FArray>,
        destination: Wrapper<Vector2FArray>,
        destinationOffset: Int,
        startIndex: Int,
        endIndex: Int,
        expectedExceptionClass: Class<Throwable>
    ) {
        assertThrows(expectedExceptionClass) {
            array.value.copyInto(
                destination.value,
                destinationOffset,
                startIndex,
                endIndex
            )
        }
    }

    @ParameterizedTest
    @MethodSource("copyOfArgs")
    fun copyOfReturnsCorrectValue(
        array: Wrapper<Vector2FArray>, expected: Wrapper<Vector2FArray>
    ) = assertContentEquals(expected.value, array.value.copyOf())

    @ParameterizedTest
    @MethodSource("copyOfSizeArgs")
    fun copyOfReturnsCorrectValue(
        array: Wrapper<Vector2FArray>, newSize: Int, expected: Wrapper<Vector2FArray>
    ) = assertContentEquals(expected.value, array.value.copyOf(newSize))

    @ParameterizedTest
    @MethodSource("copyOfRangeArgs")
    fun copyOfRangeReturnsCorrectValue(
        array: Wrapper<Vector2FArray>,
        fromIndex: Int,
        toIndex: Int,
        expected: Wrapper<Vector2FArray>
    ) = assertContentEquals(expected.value, array.value.copyOfRange(fromIndex, toIndex))

    @ParameterizedTest
    @MethodSource("copyOfRangeThrowsExceptionArgs")
    fun copyOfRangeThrowsCorrectException(
        array: Wrapper<Vector2FArray>,
        fromIndex: Int,
        toIndex: Int,
        expectedExceptionClass: Class<Throwable>
    ) {
        assertThrows(expectedExceptionClass) {
            array.value.copyOfRange(fromIndex, toIndex)
        }
    }

    @ParameterizedTest
    @MethodSource("countArgs")
    fun countReturnsCorrectValue(array: Wrapper<Vector2FArray>, expected: Int) =
        assertEquals(expected, array.value.count())

    @ParameterizedTest
    @MethodSource("countPredicateArgs")
    fun countReturnsCorrectValue(
        array: Wrapper<Vector2FArray>, predicate: (Vector2F) -> Boolean, expected: Int
    ) = assertEquals(expected, array.value.count(predicate))

    @ParameterizedTest
    @MethodSource("distinctArgs")
    fun distinctReturnsCorrectValue(array: Wrapper<Vector2FArray>, expected: List<Vector2F>) =
        assertContentEquals(expected, array.value.distinct())

    @ParameterizedTest
    @MethodSource("distinctByArgs")
    fun <K> distinctByReturnsCorrectValue(
        array: Wrapper<Vector2FArray>, selector: (Vector2F) -> K, expected: List<Vector2F>
    ) = assertContentEquals(expected, array.value.distinctBy(selector))

    @ParameterizedTest
    @MethodSource("dropArgs")
    fun dropReturnsCorrectValue(
        array: Wrapper<Vector2FArray>, n: Int, expected: List<Vector2F>
    ) = assertContentEquals(expected, array.value.drop(n))

    @Test
    fun dropThrowsWhenRequestedCountIsNegative() {
        assertThrows<IllegalArgumentException> { Vector2FArray(0).drop(-1) }
    }

    @ParameterizedTest
    @MethodSource("dropLastArgs")
    fun dropLastReturnsCorrectValue(
        array: Wrapper<Vector2FArray>, n: Int, expected: List<Vector2F>
    ) = assertContentEquals(expected, array.value.dropLast(n))

    @Test
    fun dropLastThrowsWhenRequestedCountIsNegative() {
        assertThrows<IllegalArgumentException> { Vector2FArray(0).dropLast(-1) }
    }

    @ParameterizedTest
    @MethodSource("dropLastWhileArgs")
    fun dropLastWhileReturnsCorrectValue(
        array: Wrapper<Vector2FArray>,
        predicate: (Vector2F) -> Boolean,
        expected: List<Vector2F>
    ) = assertContentEquals(expected, array.value.dropLastWhile(predicate))

    @ParameterizedTest
    @MethodSource("dropWhileArgs")
    fun dropWhileReturnsCorrectValue(
        array: Wrapper<Vector2FArray>, predicate: (Vector2F) -> Boolean, expected: List<Vector2F>
    ) = assertContentEquals(expected, array.value.dropWhile(predicate))

    @ParameterizedTest
    @MethodSource("elementAtArgs")
    fun elementAtReturnsCorrectValue(
        array: Wrapper<Vector2FArray>, index: Int, expected: Wrapper<Vector2F>
    ) = assertEquals(expected.value, array.value.elementAt(index))

    @Test
    fun elementAtThrowsWhenIndexIsOutOfBounds() {
        val array = Vector2FArray(4)

        assertThrows<IndexOutOfBoundsException> { array.elementAt(-1) }
        assertThrows<IndexOutOfBoundsException> { array.elementAt(4) }
    }

    @ParameterizedTest
    @MethodSource("elementAtOrElseArgs")
    fun elementAtOrElseReturnsCorrectValue(
        array: Wrapper<Vector2FArray>,
        index: Int,
        defaultValue: (Int) -> Vector2F,
        expected: Wrapper<Vector2F>
    ) = assertEquals(expected.value, array.value.elementAtOrElse(index, defaultValue))

    @ParameterizedTest
    @MethodSource("elementAtOrNullArgs")
    fun elementAtOrNullReturnsCorrectValue(
        array: Wrapper<Vector2FArray>, index: Int, expected: Wrapper<Vector2F?>
    ) = assertEquals(expected.value, array.value.elementAtOrNull(index))

    @ParameterizedTest
    @MethodSource("fillArgs")
    fun fillMutatesArrayCorrectly(
        array: Wrapper<Vector2FArray>,
        element: Wrapper<Vector2F>,
        fromIndex: Int,
        toIndex: Int,
        expected: Wrapper<Vector2FArray>
    ) = assertContentEquals(expected.value, array.value.apply {
        fill(element.value, fromIndex, toIndex)
    })

    @ParameterizedTest
    @MethodSource("fillThrowsExceptionArgs")
    fun fillThrowsCorrectException(
        array: Wrapper<Vector2FArray>,
        element: Wrapper<Vector2F>,
        fromIndex: Int,
        toIndex: Int,
        expectedExceptionClass: Class<Throwable>
    ) {
        assertThrows(expectedExceptionClass) {
            array.value.fill(element.value, fromIndex, toIndex)
        }
    }

    @ParameterizedTest
    @MethodSource("findArgs")
    fun findReturnsCorrectValue(
        array: Wrapper<Vector2FArray>,
        predicate: (Vector2F) -> Boolean,
        expected: Wrapper<Vector2F?>
    ) = assertEquals(expected.value, array.value.find(predicate))

    @ParameterizedTest
    @MethodSource("findLastArgs")
    fun findLastReturnsCorrectValue(
        array: Wrapper<Vector2FArray>,
        predicate: (Vector2F) -> Boolean,
        expected: Wrapper<Vector2F?>
    ) = assertEquals(expected.value, array.value.findLast(predicate))

    @ParameterizedTest
    @MethodSource("firstArgs")
    fun firstReturnsCorrectValue(array: Wrapper<Vector2FArray>, expected: Wrapper<Vector2F>) =
        assertEquals(expected.value, array.value.first())

    @Test
    fun firstThrowsWhenArrayIsEmpty() {
        assertThrows<NoSuchElementException> { Vector2FArray(0).first() }
    }

    @ParameterizedTest
    @MethodSource("firstPredicateArgs")
    fun firstReturnsCorrectValue(
        array: Wrapper<Vector2FArray>,
        predicate: (Vector2F) -> Boolean,
        expected: Wrapper<Vector2F>
    ) = assertEquals(expected.value, array.value.first(predicate))

    @ParameterizedTest
    @MethodSource("firstPredicateThrowsExceptionArgs")
    fun firstThrowsWhenNoElementMatchesThePredicate(
        array: Wrapper<Vector2FArray>, predicate: (Vector2F) -> Boolean
    ) {
        assertThrows<NoSuchElementException> { array.value.first(predicate) }
    }

    @ParameterizedTest
    @MethodSource("firstOrNullArgs")
    fun firstOrNullReturnsCorrectValue(
        array: Wrapper<Vector2FArray>, expected: Wrapper<Vector2F?>
    ) = assertEquals(expected.value, array.value.firstOrNull())

    @ParameterizedTest
    @MethodSource("firstOrNullPredicateArgs")
    fun firstOrNullReturnsCorrectValue(
        array: Wrapper<Vector2FArray>,
        predicate: (Vector2F) -> Boolean,
        expected: Wrapper<Vector2F?>
    ) = assertEquals(expected.value, array.value.firstOrNull(predicate))

    @ParameterizedTest
    @MethodSource("arrays")
    fun forEachIteratesThroughArrayCorrectly(array: Wrapper<Vector2FArray>) {
        val unwrappedArray: Vector2FArray = array.value
        var expectedIndex = 0

        unwrappedArray.forEach { actualItem ->
            val expectedItem: Vector2F = unwrappedArray[expectedIndex]

            assertEquals(expectedItem, actualItem)
            expectedIndex++
        }
        expectedIndex = 0

        unwrappedArray.forEachKt { actualItem ->
            val expectedItem: Vector2F = unwrappedArray[expectedIndex]

            assertEquals(expectedItem, actualItem)
            expectedIndex++
        }
    }

    @ParameterizedTest
    @MethodSource("arrays")
    fun forEachIndexedIteratesThroughArrayCorrectly(array: Wrapper<Vector2FArray>) {
        val unwrappedArray: Vector2FArray = array.value
        var expectedIndex = 0

        unwrappedArray.forEachIndexed { actualIndex, actualItem ->
            val expectedItem: Vector2F = unwrappedArray[expectedIndex]

            assertEquals(expectedIndex, actualIndex)
            assertEquals(expectedItem, actualItem)
            expectedIndex++
        }
    }

    @ParameterizedTest
    @MethodSource("getOrElseArgs")
    fun getOrElseReturnsCorrectValue(
        array: Wrapper<Vector2FArray>,
        index: Int,
        defaultValue: (Int) -> Vector2F,
        expected: Wrapper<Vector2F>
    ) = assertEquals(expected.value, array.value.getOrElse(index, defaultValue))

    @ParameterizedTest
    @MethodSource("getOrNullArgs")
    fun getOrNullReturnsCorrectValue(
        array: Wrapper<Vector2FArray>, index: Int, expected: Wrapper<Vector2F?>
    ) = assertEquals(expected.value, array.value.getOrNull(index))

    @ParameterizedTest
    @MethodSource("indexOfArgs")
    fun indexOfReturnsCorrectValue(
        array: Wrapper<Vector2FArray>, element: Wrapper<Vector2F>, expected: Int
    ) = assertEquals(expected, array.value.indexOf(element.value))

    @ParameterizedTest
    @MethodSource("indexOfFirstArgs")
    fun indexOfFirstReturnsCorrectValue(
        array: Wrapper<Vector2FArray>, predicate: (Vector2F) -> Boolean, expected: Int
    ) = assertEquals(expected, array.value.indexOfFirst(predicate))

    @ParameterizedTest
    @MethodSource("indexOfLastArgs")
    fun indexOfLastReturnsCorrectValue(
        array: Wrapper<Vector2FArray>, predicate: (Vector2F) -> Boolean, expected: Int
    ) = assertEquals(expected, array.value.indexOfLast(predicate))

    @ParameterizedTest
    @MethodSource("isEmptyArgs")
    fun isEmptyReturnsCorrectValue(array: Wrapper<Vector2FArray>, expected: Boolean) =
        assertEquals(expected, array.value.isEmpty())

    @ParameterizedTest
    @MethodSource("isNotEmptyArgs")
    fun isNotEmptyReturnsCorrectValue(array: Wrapper<Vector2FArray>, expected: Boolean) =
        assertEquals(expected, array.value.isNotEmpty())

    @ParameterizedTest
    @MethodSource("lastArgs")
    fun lastReturnsCorrectValue(array: Wrapper<Vector2FArray>, expected: Wrapper<Vector2F>) =
        assertEquals(expected.value, array.value.last())

    @Test
    fun lastThrowsWhenArrayIsEmpty() {
        assertThrows<NoSuchElementException> { Vector2FArray(0).last() }
    }

    @ParameterizedTest
    @MethodSource("lastPredicateArgs")
    fun lastReturnsCorrectValue(
        array: Wrapper<Vector2FArray>,
        predicate: (Vector2F) -> Boolean,
        expected: Wrapper<Vector2F>
    ) = assertEquals(expected.value, array.value.last(predicate))

    @ParameterizedTest
    @MethodSource("lastPredicateThrowsExceptionArgs")
    fun lastThrowsWhenNoElementMatchesThePredicate(
        array: Wrapper<Vector2FArray>, predicate: (Vector2F) -> Boolean
    ) {
        assertThrows<NoSuchElementException> { array.value.last(predicate) }
    }

    @ParameterizedTest
    @MethodSource("lastIndexOfArgs")
    fun lastIndexOfReturnsCorrectValue(
        array: Wrapper<Vector2FArray>, element: Wrapper<Vector2F>, expected: Int
    ) = assertEquals(expected, array.value.lastIndexOf(element.value))

    @ParameterizedTest
    @MethodSource("lastOrNullArgs")
    fun lastOrNullReturnsCorrectValue(
        array: Wrapper<Vector2FArray>, expected: Wrapper<Vector2F?>
    ) = assertEquals(expected.value, array.value.lastOrNull())

    @ParameterizedTest
    @MethodSource("lastOrNullPredicateArgs")
    fun lastOrNullReturnsCorrectValue(
        array: Wrapper<Vector2FArray>,
        predicate: (Vector2F) -> Boolean,
        expected: Wrapper<Vector2F?>
    ) = assertEquals(expected.value, array.value.lastOrNull(predicate))

    @ParameterizedTest
    @MethodSource("noneArgs")
    fun noneReturnsCorrectValue(array: Wrapper<Vector2FArray>, expected: Boolean) =
        assertEquals(expected, array.value.none())

    @ParameterizedTest
    @MethodSource("nonePredicateArgs")
    fun noneReturnsCorrectValue(
        array: Wrapper<Vector2FArray>, predicate: (Vector2F) -> Boolean, expected: Boolean
    ) = assertEquals(expected, array.value.none(predicate))

    @ParameterizedTest
    @MethodSource("randomArgs")
    fun randomReturnsCorrectValue(
        array: Wrapper<Vector2FArray>, random: Random, expected: Wrapper<Vector2F>
    ) = assertEquals(expected.value, array.value.random(random))

    @Test
    fun randomThrowsWhenArrayIsEmpty() {
        assertThrows<NoSuchElementException> { Vector2FArray(0).random() }
        assertThrows<NoSuchElementException> { Vector2FArray(0).random(Random) }
    }

    @ParameterizedTest
    @MethodSource("randomOrNullArgs")
    fun randomOrNullReturnsCorrectValue(array: Wrapper<Vector2FArray>) {
        val randomOrNull: Vector2F? = assertDoesNotThrow {
            array.value.randomOrNull()
        }
        if (array.value.isEmpty()) {
            assertEquals(null, randomOrNull)
        }
    }

    @ParameterizedTest
    @MethodSource("randomOrNullRandomArgs")
    fun randomOrNullReturnsCorrectValue(
        array: Wrapper<Vector2FArray>, random: Random, expected: Wrapper<Vector2F?>
    ) = assertEquals(expected.value, array.value.randomOrNull(random))

    @ParameterizedTest
    @MethodSource("reverseArgs")
    fun reverseMutatesArrayCorrectly(
        array: Wrapper<Vector2FArray>, expected: Wrapper<Vector2FArray>
    ) = assertContentEquals(expected.value, array.value.apply {
        reverse()
    })

    @ParameterizedTest
    @MethodSource("reverseRangeArgs")
    fun reverseMutatesArrayCorrectly(
        array: Wrapper<Vector2FArray>,
        fromIndex: Int,
        toIndex: Int,
        expected: Wrapper<Vector2FArray>
    ) = assertContentEquals(expected.value, array.value.apply {
        reverse(fromIndex, toIndex)
    })

    @ParameterizedTest
    @MethodSource("reverseRangeThrowsExceptionArgs")
    fun reverseThrowsCorrectException(
        array: Wrapper<Vector2FArray>,
        fromIndex: Int,
        toIndex: Int,
        expectedExceptionClass: Class<Throwable>
    ) {
        assertThrows(expectedExceptionClass) {
            array.value.reverse(fromIndex, toIndex)
        }
    }

    @ParameterizedTest
    @MethodSource("reversedArgs")
    fun reversedReturnsCorrectValue(array: Wrapper<Vector2FArray>, expected: List<Vector2F>) =
        assertContentEquals(expected, array.value.reversed())

    @ParameterizedTest
    @MethodSource("reversedArrayArgs")
    fun reversedArrayReturnsCorrectValue(
        array: Wrapper<Vector2FArray>, expected: Wrapper<Vector2FArray>
    ) = assertContentEquals(expected.value, array.value.reversedArray())

    @ParameterizedTest
    @MethodSource("shuffleArgs")
    fun shuffleReturnsCorrectValue(array: Wrapper<Vector2FArray>) {
        assertDoesNotThrow {
            array.value.apply { shuffle() }
        }
    }

    @ParameterizedTest
    @MethodSource("shuffleRandomArgs")
    fun shuffleReturnsCorrectValue(
        array: Wrapper<Vector2FArray>, random: Random, expected: Wrapper<Vector2FArray>
    ) = assertContentEquals(expected.value, array.value.apply {
        shuffle(random)
    })

    @ParameterizedTest
    @MethodSource("singleArgs")
    fun singleReturnsCorrectValue(array: Wrapper<Vector2FArray>, expected: Wrapper<Vector2F>) =
        assertEquals(expected.value, array.value.single())

    @ParameterizedTest
    @MethodSource("singleThrowsExceptionArgs")
    fun singleThrowsCorrectException(
        array: Wrapper<Vector2FArray>, expectedExceptionClass: Class<Throwable>
    ) {
        assertThrows(expectedExceptionClass) {
            array.value.single()
        }
    }

    @ParameterizedTest
    @MethodSource("singlePredicateArgs")
    fun singleReturnsCorrectValue(
        array: Wrapper<Vector2FArray>,
        predicate: (Vector2F) -> Boolean,
        expected: Wrapper<Vector2F>
    ) = assertEquals(expected.value, array.value.single(predicate))

    @ParameterizedTest
    @MethodSource("singlePredicateThrowsExceptionArgs")
    fun singleThrowsCorrectException(
        array: Wrapper<Vector2FArray>,
        predicate: (Vector2F) -> Boolean,
        expectedExceptionClass: Class<Throwable>
    ) {
        assertThrows(expectedExceptionClass) {
            array.value.single(predicate)
        }
    }

    @ParameterizedTest
    @MethodSource("singleOrNullArgs")
    fun singleOrNullReturnsCorrectValue(
        array: Wrapper<Vector2FArray>, expected: Wrapper<Vector2F?>
    ) = assertEquals(expected.value, array.value.singleOrNull())

    @ParameterizedTest
    @MethodSource("singleOrNullPredicateArgs")
    fun singleOrNullReturnsCorrectValue(
        array: Wrapper<Vector2FArray>,
        predicate: (Vector2F) -> Boolean,
        expected: Wrapper<Vector2F?>
    ) = assertEquals(expected.value, array.value.singleOrNull(predicate))

    @ParameterizedTest
    @MethodSource("sliceRangeArgs")
    fun sliceReturnsCorrectValue(
        array: Wrapper<Vector2FArray>, indices: IntRange, expected: List<Vector2F>
    ) = assertContentEquals(expected, array.value.slice(indices))

    @ParameterizedTest
    @MethodSource("sliceRangeThrowsExceptionArgs")
    fun sliceThrowsCorrectException(
        array: Wrapper<Vector2FArray>, indices: IntRange, expectedExceptionClass: Class<Throwable>
    ) {
        assertThrows(expectedExceptionClass) {
            array.value.slice(indices)
        }
    }

    @ParameterizedTest
    @MethodSource("sliceIterableArgs")
    fun sliceReturnsCorrectValue(
        array: Wrapper<Vector2FArray>, indices: Iterable<Int>, expected: List<Vector2F>
    ) = assertContentEquals(expected, array.value.slice(indices))

    @ParameterizedTest
    @MethodSource("sliceIterableThrowsExceptionArgs")
    fun sliceThrowsCorrectException(
        array: Wrapper<Vector2FArray>,
        indices: Iterable<Int>,
        expectedExceptionClass: Class<Throwable>
    ) {
        assertThrows(expectedExceptionClass) {
            array.value.slice(indices)
        }
    }

    @ParameterizedTest
    @MethodSource("sliceArrayCollectionArgs")
    fun sliceArrayReturnsCorrectValue(
        array: Wrapper<Vector2FArray>,
        indices: Collection<Int>,
        expected: Wrapper<Vector2FArray>
    ) = assertContentEquals(expected.value, array.value.sliceArray(indices))

    @ParameterizedTest
    @MethodSource("sliceArrayCollectionThrowsExceptionArgs")
    fun sliceArrayThrowsCorrectException(
        array: Wrapper<Vector2FArray>,
        indices: Collection<Int>,
        expectedExceptionClass: Class<Throwable>
    ) {
        assertThrows(expectedExceptionClass) {
            array.value.sliceArray(indices)
        }
    }

    @ParameterizedTest
    @MethodSource("sliceArrayRangeArgs")
    fun sliceArrayReturnsCorrectValue(
        array: Wrapper<Vector2FArray>, indices: IntRange, expected: Wrapper<Vector2FArray>
    ) = assertContentEquals(expected.value, array.value.sliceArray(indices))

    @ParameterizedTest
    @MethodSource("sliceArrayRangeThrowsExceptionArgs")
    fun sliceArrayThrowsCorrectException(
        array: Wrapper<Vector2FArray>, indices: IntRange, expectedExceptionClass: Class<Throwable>
    ) {
        assertThrows(expectedExceptionClass) {
            array.value.sliceArray(indices)
        }
    }

    @ParameterizedTest
    @MethodSource("sortedByArgs")
    fun <R : Comparable<R>> sortedByReturnsCorrectValue(
        array: Wrapper<Vector2FArray>, selector: (Vector2F) -> R?, expected: List<Vector2F>
    ) = assertContentEquals(expected, array.value.sortedBy(selector))

    @ParameterizedTest
    @MethodSource("sortedByDescendingArgs")
    fun <R : Comparable<R>> sortedByDescendingReturnsCorrectValue(
        array: Wrapper<Vector2FArray>, selector: (Vector2F) -> R?, expected: List<Vector2F>
    ) = assertContentEquals(expected, array.value.sortedByDescending(selector))

    @ParameterizedTest
    @MethodSource("sortedWithArgs")
    fun sortedWithReturnsCorrectValue(
        array: Wrapper<Vector2FArray>,
        comparator: Comparator<in Vector2F>,
        expected: List<Vector2F>
    ) = assertContentEquals(expected, array.value.sortedWith(comparator))

    @ParameterizedTest
    @MethodSource("sumArgs")
    fun sumReturnsCorrectValue(array: Wrapper<Vector2FArray>, expected: Wrapper<Vector2F>) =
        assertTrue(expected.value.isApproximately(array.value.sum()))

    @ParameterizedTest
    @MethodSource("sumOfDoubleArgs")
    fun sumOfReturnsCorrectValue(
        array: Wrapper<Vector2FArray>, selector: (Vector2F) -> Double, expected: Double
    ) = assertTrue(expected.isApproximately(array.value.sumOf(selector)))

    @ParameterizedTest
    @MethodSource("sumOfIntArgs")
    fun sumOfReturnsCorrectValue(
        array: Wrapper<Vector2FArray>, selector: (Vector2F) -> Int, expected: Int
    ) = assertEquals(expected, array.value.sumOf(selector))

    @ParameterizedTest
    @MethodSource("sumOfUIntArgs")
    fun sumOfUIntReturnsCorrectValue(
        array: Wrapper<Vector2FArray>, selector: (Vector2F) -> UInt, expected: Wrapper<UInt>
    ) = assertEquals(expected.value, array.value.sumOf(selector))

    @ParameterizedTest
    @MethodSource("sumOfLongArgs")
    fun sumOfReturnsCorrectValue(
        array: Wrapper<Vector2FArray>, selector: (Vector2F) -> Long, expected: Long
    ) = assertEquals(expected, array.value.sumOf(selector))

    @ParameterizedTest
    @MethodSource("sumOfULongArgs")
    fun sumOfULongReturnsCorrectValue(
        array: Wrapper<Vector2FArray>, selector: (Vector2F) -> ULong, expected: Wrapper<ULong>
    ) = assertEquals(expected.value, array.value.sumOf(selector))

    @ParameterizedTest
    @MethodSource("sumOfVector2FArgs")
    fun sumOfReturnsCorrectValue(
        array: Wrapper<Vector2FArray>,
        selector: (Vector2F) -> Vector2F,
        expected: Wrapper<Vector2F>
    ) = assertTrue(expected.value.isApproximately(array.value.sumOf(selector)))

    @ParameterizedTest
    @MethodSource("takeArgs")
    fun takeReturnsCorrectValue(array: Wrapper<Vector2FArray>, n: Int, expected: List<Vector2F>) =
        assertContentEquals(expected, array.value.take(n))

    @Test
    fun takeThrowsWhenRequestedCountIsNegative() {
        assertThrows<IllegalArgumentException> { Vector2FArray(0).take(-1) }
    }

    @ParameterizedTest
    @MethodSource("takeLastArgs")
    fun takeLastReturnsCorrectValue(
        array: Wrapper<Vector2FArray>, n: Int, expected: List<Vector2F>
    ) = assertContentEquals(expected, array.value.takeLast(n))

    @Test
    fun takeLastThrowsWhenRequestedCountIsNegative() {
        assertThrows<IllegalArgumentException> { Vector2FArray(0).takeLast(-1) }
    }

    @ParameterizedTest
    @MethodSource("takeLastWhileArgs")
    fun takeLastWhileReturnsCorrectValue(
        array: Wrapper<Vector2FArray>, predicate: (Vector2F) -> Boolean, expected: List<Vector2F>
    ) = assertContentEquals(expected, array.value.takeLastWhile(predicate))

    @ParameterizedTest
    @MethodSource("takeWhileArgs")
    fun takeWhileReturnsCorrectValue(
        array: Wrapper<Vector2FArray>, predicate: (Vector2F) -> Boolean, expected: List<Vector2F>
    ) = assertContentEquals(expected, array.value.takeWhile(predicate))

    @ParameterizedTest
    @MethodSource("toCollectionArgs")
    fun <C : MutableCollection<in Vector2F>> toCollectionReturnsCorrectValue(
        array: Wrapper<Vector2FArray>, destination: C, expected: C
    ) = assertContentEquals(expected, array.value.toCollection(destination))

    @ParameterizedTest
    @MethodSource("toTypedArrayArgs")
    fun toTypedArrayReturnsCorrectValue(array: Wrapper<Vector2FArray>, expected: Array<Vector2F>) =
        assertContentEquals(expected, array.value.toTypedArray())

    @ParameterizedTest
    @MethodSource("toStringArgs")
    fun toStringReturnsCorrectValue(array: Wrapper<Vector2FArray>, expected: String) =
        assertEquals(expected, array.value.toString())

    @ParameterizedTest
    @MethodSource("containsArgs")
    fun containsReturnsCorrectValue(
        array: Wrapper<Vector2FArray>, element: Wrapper<Vector2F>, expected: Boolean
    ) = assertEquals(expected, array.value.contains(element.value))

    @ParameterizedTest
    @MethodSource("getArgs")
    fun getReturnsCorrectValue(
        array: Wrapper<Vector2FArray>, index: Int, expected: Wrapper<Vector2F>
    ) = assertEquals(expected.value, array.value[index])

    @Test
    fun getThrowsWhenIndexIsOutOfArrayBounds() {
        val array = Vector2FArray(4)

        assertThrows<IndexOutOfBoundsException> { array[-1] }
        assertThrows<IndexOutOfBoundsException> { array[4] }
    }

    @ParameterizedTest
    @MethodSource("arrayPlusCollectionArgs")
    fun arrayPlusCollectionReturnsCorrectValue(
        array: Wrapper<Vector2FArray>,
        elements: Collection<Vector2F>,
        expected: Wrapper<Vector2FArray>
    ) = assertContentEquals(expected.value, array.value + elements)

    @ParameterizedTest
    @MethodSource("arrayPlusVector2FArgs")
    fun arrayPlusVector2FReturnsCorrectValue(
        array: Wrapper<Vector2FArray>, element: Wrapper<Vector2F>, expected: Wrapper<Vector2FArray>
    ) = assertContentEquals(expected.value, array.value + element.value)

    @ParameterizedTest
    @MethodSource("arrayPlusArrayArgs")
    fun arrayPlusArrayReturnsCorrectValue(
        array: Wrapper<Vector2FArray>,
        elements: Wrapper<Vector2FArray>,
        expected: Wrapper<Vector2FArray>
    ) = assertContentEquals(expected.value, array.value + elements.value)

    @ParameterizedTest
    @MethodSource("setArgs")
    fun setMutatesArrayCorrectly(
        array: Wrapper<Vector2FArray>,
        index: Int,
        value: Wrapper<Vector2F>,
        expected: Wrapper<Vector2FArray>
    ) = assertContentEquals(expected.value, array.value.apply {
        this[index] = value.value
    })

    @ParameterizedTest
    @MethodSource("typedArraySumArgs")
    fun typedArraySumReturnsCorrectValue(array: Array<Vector2F>, expected: Wrapper<Vector2F>) =
        assertEquals(expected.value, array.sum())

    @ParameterizedTest
    @MethodSource("typedArraySumOfVector2FArgs")
    fun <T> typedArraySumOfReturnsCorrectValue(
        array: Array<T>, selector: (T) -> Vector2F, expected: Wrapper<Vector2F>
    ) = assertEquals(expected.value, array.sumOf(selector))

    @ParameterizedTest
    @MethodSource("typedArrayToVector2FArrayArgs")
    fun typedArrayToVector2FArrayReturnsCorrectValue(
        array: Array<Vector2F>, expected: Wrapper<Vector2FArray>
    ) = assertContentEquals(expected.value, array.toVector2FArray())

    @ParameterizedTest
    @MethodSource("iterableSumArgs")
    fun iterableSumReturnsCorrectValue(iterable: Iterable<Vector2F>, expected: Wrapper<Vector2F>) =
        assertEquals(expected.value, iterable.sum())

    @ParameterizedTest
    @MethodSource("iterableSumOfVector2FArgs")
    fun <T> iterableSumOfReturnsCorrectValue(
        iterable: Iterable<T>, selector: (T) -> Vector2F, expected: Wrapper<Vector2F>
    ) = assertEquals(expected.value, iterable.sumOf(selector))

    @Test
    fun vector2FArrayOfReturnsCorrectValue() =
        assertContentEquals(Vector2FArray(0), vector2FArrayOf())

    @ParameterizedTest
    @MethodSource("vector2FArrayOfVector2FArgs")
    fun vector2FArrayOfVector2FReturnsCorrectValue(
        element: Wrapper<Vector2F>, expected: Wrapper<Vector2FArray>
    ) = assertContentEquals(expected.value, vector2FArrayOf(element.value))

    @ParameterizedTest
    @MethodSource("vector2FArrayOfTypedArrayArgs")
    fun vector2FArrayOfTypedArrayReturnsCorrectValue(
        elements: Array<Vector2F>, expected: Wrapper<Vector2FArray>
    ) = assertContentEquals(expected.value, vector2FArrayOf(*elements))

    companion object {
        @JvmStatic
        fun arrays(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2FArray(4) { Vector2F(it.toFloat(), -it.toFloat()) })
            ),
            Arguments.of(
                Wrapper(
                    vector2FArrayOf(Vector2F(-3f, 5f), Vector2F(-1f, 7f))
                )
            ),
            Arguments.of(
                Wrapper(Vector2FArray(0))
            ),
        )

        @JvmStatic
        fun allArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2FArray(3) { Vector2F.ZERO }),
                { v: Vector2F -> v == Vector2F.ZERO },
                true
            ),
            Arguments.of(
                Wrapper(
                    vector2FArrayOf(Vector2F.ZERO, Vector2F.ZERO, Vector2F(2f, 1f))
                ),
                { v: Vector2F -> v == Vector2F.ZERO },
                false
            ),
            Arguments.of(
                Wrapper(Vector2FArray(0)),
                { v: Vector2F -> v == Vector2F.ZERO },
                true
            ),
        )

        @JvmStatic
        fun anyArgs(): List<Arguments> = isNotEmptyArgs()

        @JvmStatic
        fun anyPredicateArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2FArray(3) { Vector2F.ZERO }),
                { v: Vector2F -> v == Vector2F(2f, 1f) },
                false
            ),
            Arguments.of(
                Wrapper(
                    vector2FArrayOf(Vector2F.ZERO, Vector2F.ZERO, Vector2F(2f, 1f))
                ),
                { v: Vector2F -> v == Vector2F(2f, 1f) },
                true
            ),
            Arguments.of(
                Wrapper(Vector2FArray(0)),
                { v: Vector2F -> v == Vector2F(2f, 1f) },
                false
            ),
        )

        @JvmStatic
        fun asListArgs(): List<Arguments> = listOf(
            Arguments.of(Wrapper(Vector2FArray(0)), emptyList<Vector2F>()),
            Arguments.of(
                Wrapper(Vector2FArray(4) { Vector2F(it.toFloat(), 0f) }),
                List(4) { Vector2F(it.toFloat(), 0f) }
            ),
        )

        @JvmStatic
        fun containsAllArgs(): List<Arguments> {
            val array = vector2FArrayOf(
                Vector2F(1f, 0f),
                Vector2F(-5f, 8f),
                Vector2F(2f, -9f),
            )

            return listOf(
                Arguments.of(
                    Wrapper(array), array.toList(), true
                ),
                Arguments.of(
                    Wrapper(array), listOf(Vector2F(1f, 0f), Vector2F(-5f, 8f)), true
                ),
                Arguments.of(
                    Wrapper(array), emptyList<Vector2F>(), true
                ),
                Arguments.of(
                    Wrapper(array), listOf(Vector2F.ZERO), false
                ),
            )
        }

        @JvmStatic
        fun copyIntoArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2FArray(4) { Vector2F(it.toFloat(), 0f) }),
                Wrapper(Vector2FArray(4) { Vector2F.ZERO }),
                0, 0, 4,
                Wrapper(Vector2FArray(4) { Vector2F(it.toFloat(), 0f) })
            ),
            Arguments.of(
                Wrapper(Vector2FArray(4) { Vector2F(it.toFloat(), 0f) }),
                Wrapper(Vector2FArray(5) { Vector2F.ZERO }),
                3, 1, 3,
                Wrapper(
                    vector2FArrayOf(
                        Vector2F(0f, 0f),
                        Vector2F(0f, 0f),
                        Vector2F(0f, 0f),
                        Vector2F(1f, 0f),
                        Vector2F(2f, 0f),
                    )
                )
            ),
            Arguments.of(
                Wrapper(Vector2FArray(0)),
                Wrapper(Vector2FArray(4) { Vector2F.ZERO }),
                0, 0, 0,
                Wrapper(Vector2FArray(4) { Vector2F.ZERO }),
            ),
        )

        @JvmStatic
        fun copyIntoThrowsExceptionArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2FArray(4) { Vector2F.ZERO }),
                Wrapper(Vector2FArray(4) { Vector2F.ZERO }),
                0, 0, 5,
                IndexOutOfBoundsException::class.java
            ),
            Arguments.of(
                Wrapper(Vector2FArray(4) { Vector2F.ZERO }),
                Wrapper(Vector2FArray(4) { Vector2F.ZERO }),
                0, -1, 4,
                IndexOutOfBoundsException::class.java
            ),
            Arguments.of(
                Wrapper(Vector2FArray(4) { Vector2F.ZERO }),
                Wrapper(Vector2FArray(4) { Vector2F.ZERO }),
                0, 2, 1,
                IndexOutOfBoundsException::class.java
            ),
            Arguments.of(
                Wrapper(Vector2FArray(4) { Vector2F.ZERO }),
                Wrapper(Vector2FArray(4) { Vector2F.ZERO }),
                1, 0, 4,
                IndexOutOfBoundsException::class.java
            ),
            Arguments.of(
                Wrapper(Vector2FArray(4) { Vector2F.ZERO }),
                Wrapper(Vector2FArray(4) { Vector2F.ZERO }),
                4, 0, 1,
                IndexOutOfBoundsException::class.java
            ),
        )

        @JvmStatic
        fun copyOfArgs(): List<Arguments> = listOf(
            Arguments.of(Wrapper(Vector2FArray(0)), Wrapper(Vector2FArray(0))),
            Arguments.of(
                Wrapper(Vector2FArray(4) { Vector2F(it.toFloat(), 0f) }),
                Wrapper(Vector2FArray(4) { Vector2F(it.toFloat(), 0f) })
            ),
        )

        @JvmStatic
        fun copyOfSizeArgs(): List<Arguments> = listOf(
            Arguments.of(Wrapper(Vector2FArray(0)), 0, Wrapper(Vector2FArray(0))),
            Arguments.of(
                Wrapper(Vector2FArray(0)),
                2,
                Wrapper(Vector2FArray(2) { Vector2F.ZERO })
            ),
            Arguments.of(
                Wrapper(Vector2FArray(4) { Vector2F(it.toFloat(), 0f) }),
                3,
                Wrapper(Vector2FArray(3) { Vector2F(it.toFloat(), 0f) })
            ),
            Arguments.of(
                Wrapper(Vector2FArray(4) { Vector2F(it.toFloat(), 0f) }),
                5,
                Wrapper(
                    vector2FArrayOf(
                        Vector2F(0f, 0f),
                        Vector2F(1f, 0f),
                        Vector2F(2f, 0f),
                        Vector2F(3f, 0f),
                        Vector2F(0f, 0f),
                    )
                )
            ),
        )

        @JvmStatic
        fun copyOfRangeArgs(): List<Arguments> = listOf(
            Arguments.of(Wrapper(Vector2FArray(0)), 0, 0, Wrapper(Vector2FArray(0))),
            Arguments.of(
                Wrapper(Vector2FArray(4) { Vector2F(it.toFloat(), 0f) }),
                0, 4,
                Wrapper(Vector2FArray(4) { Vector2F(it.toFloat(), 0f) })
            ),
            Arguments.of(
                Wrapper(Vector2FArray(4) { Vector2F(it.toFloat(), 0f) }),
                1, 3,
                Wrapper(vector2FArrayOf(Vector2F(1f, 0f), Vector2F(2f, 0f)))
            ),
        )

        @JvmStatic
        fun copyOfRangeThrowsExceptionArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2FArray(0)), 0, 1, IndexOutOfBoundsException::class.java
            ),
            Arguments.of(
                Wrapper(Vector2FArray(4) { Vector2F(it.toFloat(), 0f) }),
                0, 5,
                IndexOutOfBoundsException::class.java
            ),
            Arguments.of(
                Wrapper(Vector2FArray(4) { Vector2F(it.toFloat(), 0f) }),
                -1, 4,
                IndexOutOfBoundsException::class.java
            ),
            Arguments.of(
                Wrapper(Vector2FArray(4) { Vector2F(it.toFloat(), 0f) }),
                2, 1,
                IllegalArgumentException::class.java
            ),
        )

        @JvmStatic
        fun countArgs(): List<Arguments> = listOf(
            Arguments.of(Wrapper(Vector2FArray(0)), 0),
            Arguments.of(Wrapper(Vector2FArray(3)), 3),
        )

        @JvmStatic
        fun countPredicateArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2FArray(3) { Vector2F.ZERO }),
                { v: Vector2F -> v == Vector2F.ZERO },
                3
            ),
            Arguments.of(
                Wrapper(
                    vector2FArrayOf(Vector2F.ZERO, Vector2F.ZERO, Vector2F(2f, 1f))
                ),
                { v: Vector2F -> v == Vector2F.ZERO },
                2
            ),
            Arguments.of(
                Wrapper(Vector2FArray(0)),
                { v: Vector2F -> v == Vector2F.ZERO },
                0
            ),
        )

        @JvmStatic
        fun distinctArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(
                    vector2FArrayOf(Vector2F(1f, 2f), Vector2F(1f, 2f), Vector2F(3f, 4f))

                ),
                listOf(Vector2F(1f, 2f), Vector2F(3f, 4f))
            ),
            Arguments.of(
                Wrapper(
                    vector2FArrayOf(Vector2F(3f, 4f), Vector2F(1f, 2f), Vector2F(3f, 4f))

                ),
                listOf(Vector2F(3f, 4f), Vector2F(1f, 2f))
            ),
            Arguments.of(Wrapper(Vector2FArray(0)), emptyList<Vector2F>()),
        )

        @JvmStatic
        fun distinctByArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(
                    vector2FArrayOf(Vector2F(1f, 2f), Vector2F(2f, 1f), Vector2F(3f, 4f))

                ),
                { v: Vector2F -> Vector2F(min(v.x, v.y), max(v.x, v.y)) },
                listOf(Vector2F(1f, 2f), Vector2F(3f, 4f))
            ),
            Arguments.of(
                Wrapper(
                    vector2FArrayOf(Vector2F(3f, 4f), Vector2F(1f, 2f), Vector2F(4f, 3f))

                ),
                { v: Vector2F -> Vector2F(min(v.x, v.y), max(v.x, v.y)) },
                listOf(Vector2F(3f, 4f), Vector2F(1f, 2f))
            ),
            Arguments.of(
                Wrapper(Vector2FArray(0)),
                { v: Vector2F -> Vector2F(min(v.x, v.y), max(v.x, v.y)) },
                emptyList<Vector2F>()
            ),
        )

        @JvmStatic
        fun dropArgs(): List<Arguments> {
            val array = Vector2FArray(4) { Vector2F(it.toFloat(), 0f) }

            return listOf(
                Arguments.of(
                    Wrapper(array), 0, List(4) { Vector2F(it.toFloat(), 0f) }
                ),
                Arguments.of(
                    Wrapper(array), 2, listOf(Vector2F(2f, 0f), Vector2F(3f, 0f))
                ),
                Arguments.of(
                    Wrapper(array), 5, emptyList<Vector2F>()
                ),
            )
        }

        @JvmStatic
        fun dropLastArgs(): List<Arguments> {
            val array = Vector2FArray(4) { Vector2F(it.toFloat(), 0f) }

            return listOf(
                Arguments.of(
                    Wrapper(array), 0, List(4) { Vector2F(it.toFloat(), 0f) }
                ),
                Arguments.of(
                    Wrapper(array), 2, listOf(Vector2F(0f, 0f), Vector2F(1f, 0f))
                ),
                Arguments.of(
                    Wrapper(array), 5, emptyList<Vector2F>()
                ),
            )
        }

        @JvmStatic
        fun dropLastWhileArgs(): List<Arguments> {
            val array = Vector2FArray(4) { Vector2F(it.toFloat(), 0f) }

            return listOf(
                Arguments.of(
                    Wrapper(Vector2FArray(0)),
                    { _: Vector2F -> false },
                    emptyList<Vector2F>(),
                ),
                Arguments.of(
                    Wrapper(array),
                    { _: Vector2F -> false },
                    List(4) { Vector2F(it.toFloat(), 0f) }
                ),
                Arguments.of(
                    Wrapper(array),
                    { v: Vector2F -> v.x !in 0.5f..1.5f },
                    listOf(Vector2F(0f, 0f), Vector2F(1f, 0f))
                ),
                Arguments.of(
                    Wrapper(array), { _: Vector2F -> true }, emptyList<Vector2F>()
                ),
            )
        }

        @JvmStatic
        fun dropWhileArgs(): List<Arguments> {
            val array = Vector2FArray(4) { Vector2F(it.toFloat(), 0f) }

            return listOf(
                Arguments.of(
                    Wrapper(Vector2FArray(0)),
                    { _: Vector2F -> false },
                    emptyList<Vector2F>(),
                ),
                Arguments.of(
                    Wrapper(array),
                    { _: Vector2F -> false },
                    List(4) { Vector2F(it.toFloat(), 0f) }
                ),
                Arguments.of(
                    Wrapper(array),
                    { v: Vector2F -> v.x !in 1.5f..2.5f },
                    listOf(Vector2F(2f, 0f), Vector2F(3f, 0f))
                ),
                Arguments.of(
                    Wrapper(array), { _: Vector2F -> true }, emptyList<Vector2F>()
                ),
            )
        }

        @JvmStatic
        fun elementAtArgs(): List<Arguments> = getArgs()

        @JvmStatic
        fun elementAtOrElseArgs(): List<Arguments> = getOrElseArgs()

        @JvmStatic
        fun elementAtOrNullArgs(): List<Arguments> = getOrNullArgs()

        @JvmStatic
        fun fillArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2FArray(4) { Vector2F.ZERO }),
                Wrapper(Vector2F(1f, 2f)),
                0, 4,
                Wrapper(Vector2FArray(4) { Vector2F(1f, 2f) })
            ),
            Arguments.of(
                Wrapper(Vector2FArray(4) { Vector2F.ZERO }),
                Wrapper(Vector2F(1f, 2f)),
                1, 3,
                Wrapper(
                    vector2FArrayOf(
                        Vector2F.ZERO,
                        Vector2F(1f, 2f),
                        Vector2F(1f, 2f),
                        Vector2F.ZERO
                    )
                )
            ),
            Arguments.of(
                Wrapper(Vector2FArray(4) { Vector2F.ZERO }),
                Wrapper(Vector2F(1f, 2f)),
                0, 0,
                Wrapper(Vector2FArray(4) { Vector2F.ZERO })
            ),
            Arguments.of(
                Wrapper(Vector2FArray(0)),
                Wrapper(Vector2F(1f, 2f)),
                0, 0,
                Wrapper(Vector2FArray(0)),
            ),
        )

        @JvmStatic
        fun fillThrowsExceptionArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2FArray(4) { Vector2F.ZERO }),
                Wrapper(Vector2F(1f, 2f)),
                0, 5,
                IndexOutOfBoundsException::class.java
            ),
            Arguments.of(
                Wrapper(Vector2FArray(4) { Vector2F.ZERO }),
                Wrapper(Vector2F(1f, 2f)),
                -1, 4,
                IndexOutOfBoundsException::class.java
            ),
            Arguments.of(
                Wrapper(Vector2FArray(4) { Vector2F.ZERO }),
                Wrapper(Vector2F(1f, 2f)),
                2, 1,
                IllegalArgumentException::class.java
            ),
        )

        @JvmStatic
        fun findArgs(): List<Arguments> = firstOrNullPredicateArgs()

        @JvmStatic
        fun findLastArgs(): List<Arguments> = lastOrNullPredicateArgs()

        @JvmStatic
        fun firstArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(vector2FArrayOf(Vector2F(4f, 5f), Vector2F(1f, 2f))),
                Wrapper(Vector2F(4f, 5f))
            ),
            Arguments.of(
                Wrapper(Vector2FArray(2) { Vector2F(it.toFloat(), 0f) }),
                Wrapper(Vector2F(0f, 0f))
            ),
        )

        @JvmStatic
        fun firstPredicateArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(vector2FArrayOf(Vector2F(4f, 5f), Vector2F(1f, 2f))),
                { v: Vector2F -> v == Vector2F(1f, 2f) },
                Wrapper(Vector2F(1f, 2f))
            ),
            Arguments.of(
                Wrapper(Vector2FArray(2) { Vector2F(it.toFloat(), 0f) }),
                { v: Vector2F -> v.x >= 1f },
                Wrapper(Vector2F(1f, 0f))
            ),
        )

        @JvmStatic
        fun firstPredicateThrowsExceptionArgs() = listOf(
            Arguments.of(
                Wrapper(vector2FArrayOf(Vector2F(4f, 5f), Vector2F(1f, 2f))),
                { v: Vector2F -> v == Vector2F(4f, 2f) }),
            Arguments.of(
                Wrapper(Vector2FArray(2) { Vector2F(it.toFloat(), 0f) }),
                { v: Vector2F -> v.x >= 2f }
            ),
        )

        @JvmStatic
        fun firstOrNullArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(vector2FArrayOf(Vector2F(4f, 5f), Vector2F(1f, 2f))),
                Wrapper(Vector2F(4f, 5f))
            ),
            Arguments.of(
                Wrapper(Vector2FArray(2) { Vector2F(it.toFloat(), 0f) }),
                Wrapper(Vector2F(0f, 0f))
            ),
            Arguments.of(Wrapper(Vector2FArray(0)), Wrapper<Vector2F?>(null)),
        )

        @JvmStatic
        fun firstOrNullPredicateArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(vector2FArrayOf(Vector2F(4f, 5f), Vector2F(1f, 2f))),
                { v: Vector2F -> v == Vector2F(1f, 2f) },
                Wrapper(Vector2F(1f, 2f))
            ),
            Arguments.of(
                Wrapper(Vector2FArray(2) { Vector2F(it.toFloat(), 0f) }),
                { v: Vector2F -> v.x >= 1f },
                Wrapper(Vector2F(1f, 0f))
            ),
            Arguments.of(
                Wrapper(Vector2FArray(2) { Vector2F(it.toFloat(), 0f) }),
                { v: Vector2F -> v.x >= 2f },
                Wrapper<Vector2F?>(null)
            ),
            Arguments.of(
                Wrapper(Vector2FArray(0)),
                { _: Vector2F -> true },
                Wrapper<Vector2F?>(null)
            ),
        )

        @JvmStatic
        fun getOrElseArgs(): List<Arguments> {
            val array = vector2FArrayOf(
                Vector2F(1f, 0f),
                Vector2F(-5f, 8f),
                Vector2F(3f, 4f),
            )

            return listOf(
                Arguments.of(
                    Wrapper(array),
                    0,
                    { i: Int -> Vector2F(i.toFloat(), i.toFloat()) },
                    Wrapper(Vector2F(1f, 0f))
                ),
                Arguments.of(
                    Wrapper(array),
                    -1,
                    { i: Int -> Vector2F(i.toFloat(), i.toFloat()) },
                    Wrapper(Vector2F(-1f, -1f))
                ),
                Arguments.of(
                    Wrapper(array),
                    3,
                    { i: Int -> Vector2F(i.toFloat(), i.toFloat()) },
                    Wrapper(Vector2F(3f, 3f))
                ),
            )
        }

        @JvmStatic
        fun getOrNullArgs(): List<Arguments> {
            val array = vector2FArrayOf(
                Vector2F(1f, 0f),
                Vector2F(-5f, 8f),
                Vector2F(3f, 4f),
            )

            return listOf(
                Arguments.of(Wrapper(array), 0, Wrapper(Vector2F(1f, 0f))),
                Arguments.of(Wrapper(array), -1, Wrapper<Vector2F?>(null)),
                Arguments.of(Wrapper(array), 3, Wrapper<Vector2F?>(null)),
            )
        }

        @JvmStatic
        fun indexOfArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(vector2FArrayOf(Vector2F(4f, 5f), Vector2F(1f, 2f))),
                Wrapper(Vector2F(1f, 2f)),
                1
            ),
            Arguments.of(
                Wrapper(vector2FArrayOf(Vector2F(4f, 5f), Vector2F(4f, 5f))),
                Wrapper(Vector2F(4f, 5f)),
                0
            ),
            Arguments.of(
                Wrapper(vector2FArrayOf(Vector2F(4f, 5f), Vector2F(1f, 2f))),
                Wrapper(Vector2F(7f, 4f)),
                -1
            ),
            Arguments.of(
                Wrapper(Vector2FArray(0)),
                Wrapper(Vector2F(7f, 4f)),
                -1
            ),
        )

        @JvmStatic
        fun indexOfFirstArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(
                    vector2FArrayOf(Vector2F(4f, 5f), Vector2F(1f, 2f), Vector2F(1f, 2f))
                ),
                { v: Vector2F -> v == Vector2F(1f, 2f) },
                1
            ),
            Arguments.of(
                Wrapper(Vector2FArray(2) { Vector2F(it.toFloat(), 0f) }),
                { v: Vector2F -> v.x >= 2f },
                -1
            ),
            Arguments.of(
                Wrapper(Vector2FArray(0)),
                { _: Vector2F -> true },
                -1
            ),
        )

        @JvmStatic
        fun indexOfLastArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(
                    vector2FArrayOf(Vector2F(4f, 5f), Vector2F(1f, 2f), Vector2F(1f, 2f))
                ),
                { v: Vector2F -> v == Vector2F(1f, 2f) },
                2
            ),
            Arguments.of(
                Wrapper(Vector2FArray(2) { Vector2F(it.toFloat(), 0f) }),
                { v: Vector2F -> v.x >= 2f },
                -1
            ),
            Arguments.of(
                Wrapper(Vector2FArray(0)),
                { _: Vector2F -> true },
                -1
            ),
        )

        @JvmStatic
        fun isEmptyArgs(): List<Arguments> = listOf(
            Arguments.of(Wrapper(Vector2FArray(4) { Vector2F.ZERO }), false),
            Arguments.of(Wrapper(Vector2FArray(0)), true),
        )

        @JvmStatic
        fun isNotEmptyArgs(): List<Arguments> = listOf(
            Arguments.of(Wrapper(Vector2FArray(4) { Vector2F.ZERO }), true),
            Arguments.of(Wrapper(Vector2FArray(0)), false),
        )

        @JvmStatic
        fun lastArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(vector2FArrayOf(Vector2F(4f, 5f), Vector2F(1f, 2f))),
                Wrapper(Vector2F(1f, 2f))
            ),
            Arguments.of(
                Wrapper(Vector2FArray(2) { Vector2F(it.toFloat(), 0f) }),
                Wrapper(Vector2F(1f, 0f))
            ),
        )

        @JvmStatic
        fun lastPredicateArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(vector2FArrayOf(Vector2F(4f, 5f), Vector2F(1f, 2f))),
                { v: Vector2F -> v == Vector2F(1f, 2f) },
                Wrapper(Vector2F(1f, 2f))
            ),
            Arguments.of(
                Wrapper(Vector2FArray(2) { Vector2F(it.toFloat(), 0f) }),
                { v: Vector2F -> v.x <= 1f },
                Wrapper(Vector2F(1f, 0f))
            ),
        )

        @JvmStatic
        fun lastPredicateThrowsExceptionArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(vector2FArrayOf(Vector2F(4f, 5f), Vector2F(1f, 2f))),
                { v: Vector2F -> v == Vector2F(4f, 2f) }),
            Arguments.of(
                Wrapper(Vector2FArray(2) { Vector2F(it.toFloat(), 0f) }),
                { v: Vector2F -> v.x >= 2f }
            ),
        )

        @JvmStatic
        fun lastIndexOfArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(vector2FArrayOf(Vector2F(4f, 5f), Vector2F(1f, 2f))),
                Wrapper(Vector2F(1f, 2f)),
                1
            ),
            Arguments.of(
                Wrapper(vector2FArrayOf(Vector2F(4f, 5f), Vector2F(4f, 5f))),
                Wrapper(Vector2F(4f, 5f)),
                1
            ),
            Arguments.of(
                Wrapper(vector2FArrayOf(Vector2F(4f, 5f), Vector2F(1f, 2f))),
                Wrapper(Vector2F(7f, 4f)),
                -1
            ),
            Arguments.of(
                Wrapper(Vector2FArray(0)),
                Wrapper(Vector2F(7f, 4f)),
                -1
            ),
        )

        @JvmStatic
        fun lastOrNullArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(vector2FArrayOf(Vector2F(4f, 5f), Vector2F(1f, 2f))),
                Wrapper(Vector2F(1f, 2f))
            ),
            Arguments.of(
                Wrapper(Vector2FArray(2) { Vector2F(it.toFloat(), 0f) }),
                Wrapper(Vector2F(1f, 0f))
            ),
            Arguments.of(Wrapper(Vector2FArray(0)), Wrapper<Vector2F?>(null)),
        )

        @JvmStatic
        fun lastOrNullPredicateArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(vector2FArrayOf(Vector2F(4f, 5f), Vector2F(1f, 2f))),
                { v: Vector2F -> v == Vector2F(1f, 2f) },
                Wrapper(Vector2F(1f, 2f))
            ),
            Arguments.of(
                Wrapper(Vector2FArray(2) { Vector2F(it.toFloat(), 0f) }),
                { v: Vector2F -> v.x <= 0f },
                Wrapper(Vector2F(0f, 0f))
            ),
            Arguments.of(
                Wrapper(Vector2FArray(2) { Vector2F(it.toFloat(), 0f) }),
                { v: Vector2F -> v.x >= 2f },
                Wrapper<Vector2F?>(null)
            ),
            Arguments.of(
                Wrapper(Vector2FArray(0)),
                { _: Vector2F -> true },
                Wrapper<Vector2F?>(null)
            ),
        )

        @JvmStatic
        fun noneArgs(): List<Arguments> = isEmptyArgs()

        @JvmStatic
        fun nonePredicateArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2FArray(3) { Vector2F.ZERO }),
                { v: Vector2F -> v == Vector2F(2f, 1f) },
                true
            ),
            Arguments.of(
                Wrapper(
                    vector2FArrayOf(Vector2F.ZERO, Vector2F.ZERO, Vector2F(2f, 1f))
                ),
                { v: Vector2F -> v == Vector2F(2f, 1f) },
                false
            ),
            Arguments.of(
                Wrapper(Vector2FArray(0)),
                { v: Vector2F -> v == Vector2F(2f, 1f) },
                true
            ),
        )

        @JvmStatic
        fun randomArgs(): List<Arguments> {
            val array = Array(10) { Vector2F(it.toFloat(), 0f) }
            val seeds = intArrayOf(1234, 5678)
            val expectedValues = arrayOf(
                array.random(Random(seeds[0])).let { Wrapper(Vector2F(it.x, it.y)) },
                array.random(Random(seeds[1])).let { Wrapper(Vector2F(it.x, it.y)) },
            )
            return listOf(
                Arguments.of(Wrapper(array.toVector2FArray()), Random(seeds[0]), expectedValues[0]),
                Arguments.of(Wrapper(array.toVector2FArray()), Random(seeds[1]), expectedValues[1]),
            )
        }

        @JvmStatic
        fun randomOrNullArgs(): List<Arguments> = arrays()

        @JvmStatic
        fun randomOrNullRandomArgs(): List<Arguments> {
            val array = Array(10) { Vector2F(it.toFloat(), 0f) }
            val seeds = intArrayOf(1234, 5678)
            val expectedValues = arrayOf(
                array.random(Random(seeds[0])).let { Wrapper(Vector2F(it.x, it.y)) },
                array.random(Random(seeds[1])).let { Wrapper(Vector2F(it.x, it.y)) },
            )
            return listOf(
                Arguments.of(Wrapper(array.toVector2FArray()), Random(seeds[0]), expectedValues[0]),
                Arguments.of(Wrapper(array.toVector2FArray()), Random(seeds[1]), expectedValues[1]),
                Arguments.of(
                    Wrapper(Vector2FArray(0)),
                    Random(42),
                    Wrapper<Vector2F?>(null)
                ),
            )
        }

        @JvmStatic
        fun reverseArgs(): List<Arguments> = listOf(
            Arguments.of(Wrapper(Vector2FArray(0)), Wrapper(Vector2FArray(0))),
            Arguments.of(
                Wrapper(
                    vector2FArrayOf(
                        Vector2F(1f, 2f), Vector2F(3f, 4f), Vector2F(5f, 6f)
                    )
                ),
                Wrapper(
                    vector2FArrayOf(
                        Vector2F(5f, 6f), Vector2F(3f, 4f), Vector2F(1f, 2f)
                    )
                )
            ),
            Arguments.of(
                Wrapper(vector2FArrayOf(Vector2F(1f, 2f), Vector2F(3f, 4f))),
                Wrapper(vector2FArrayOf(Vector2F(3f, 4f), Vector2F(1f, 2f)))
            ),
        )

        @JvmStatic
        fun reverseRangeArgs(): List<Arguments> = listOf(
            Arguments.of(Wrapper(Vector2FArray(0)), 0, 0, Wrapper(Vector2FArray(0))),
            Arguments.of(
                Wrapper(
                    vector2FArrayOf(
                        Vector2F(1f, 2f), Vector2F(3f, 4f), Vector2F(5f, 6f)
                    )
                ),
                1, 3,
                Wrapper(
                    vector2FArrayOf(
                        Vector2F(1f, 2f), Vector2F(5f, 6f), Vector2F(3f, 4f)
                    )
                )
            ),
            Arguments.of(
                Wrapper(vector2FArrayOf(Vector2F(1f, 2f), Vector2F(3f, 4f))),
                0, 2,
                Wrapper(vector2FArrayOf(Vector2F(3f, 4f), Vector2F(1f, 2f)))
            ),
        )

        @JvmStatic
        fun reverseRangeThrowsExceptionArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2FArray(3) { Vector2F.ZERO }),
                0, 4,
                IndexOutOfBoundsException::class.java
            ),
            Arguments.of(
                Wrapper(Vector2FArray(3) { Vector2F.ZERO }),
                -1, 2,
                IndexOutOfBoundsException::class.java
            ),
            Arguments.of(
                Wrapper(Vector2FArray(3) { Vector2F.ZERO }),
                2, 1,
                IllegalArgumentException::class.java
            ),
        )

        @JvmStatic
        fun reversedArgs(): List<Arguments> = listOf(
            Arguments.of(Wrapper(Vector2FArray(0)), emptyList<Vector2F>()),
            Arguments.of(
                Wrapper(
                    vector2FArrayOf(
                        Vector2F(1f, 2f), Vector2F(3f, 4f), Vector2F(5f, 6f)
                    )
                ),
                listOf(Vector2F(5f, 6f), Vector2F(3f, 4f), Vector2F(1f, 2f))
            ),
            Arguments.of(
                Wrapper(vector2FArrayOf(Vector2F(1f, 2f), Vector2F(3f, 4f))),
                listOf(Vector2F(3f, 4f), Vector2F(1f, 2f))
            ),
        )

        @JvmStatic
        fun reversedArrayArgs(): List<Arguments> = reverseArgs()

        @JvmStatic
        fun shuffleArgs(): List<Arguments> = arrays()

        @JvmStatic
        fun shuffleRandomArgs(): List<Arguments> {
            val array = Vector2FArray(10) { Vector2F(it.toFloat(), 0f) }
            val seeds = intArrayOf(1234, 5678)

            return listOf(
                Arguments.of(
                    Wrapper(array.copyOf()),
                    Random(seeds[0]),
                    Wrapper(array.copyOf().apply { shuffle(Random(seeds[0])) })
                ),
                Arguments.of(
                    Wrapper(array.copyOf()),
                    Random(seeds[1]),
                    Wrapper(array.copyOf().apply { shuffle(Random(seeds[1])) })
                ),
            )
        }

        @JvmStatic
        fun singleArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(vector2FArrayOf(Vector2F(1f, 2f))),
                Wrapper(Vector2F(1f, 2f))
            ),
            Arguments.of(
                Wrapper(vector2FArrayOf(Vector2F.ZERO)),
                Wrapper(Vector2F(0f, 0f))
            ),
        )

        @JvmStatic
        fun singleThrowsExceptionArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2FArray(0)), NoSuchElementException::class.java
            ),
            Arguments.of(
                Wrapper(Vector2FArray(2) { Vector2F.ZERO }),
                IllegalArgumentException::class.java
            ),
        )

        @JvmStatic
        fun singlePredicateArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2FArray(3) { Vector2F(it.toFloat(), 0f) }),
                { v: Vector2F -> v == Vector2F(1f, 0f) },
                Wrapper(Vector2F(1f, 0f))
            ),
            Arguments.of(
                Wrapper(Vector2FArray(3) { Vector2F(it.toFloat(), 0f) }),
                { v: Vector2F -> v.x in 0.5f..1.5f },
                Wrapper(Vector2F(1f, 0f))
            ),
        )

        @JvmStatic
        fun singlePredicateThrowsExceptionArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2FArray(0)),
                { v: Vector2F -> v == Vector2F.ZERO },
                NoSuchElementException::class.java
            ),
            Arguments.of(
                Wrapper(Vector2FArray(2) { Vector2F.ZERO }),
                { v: Vector2F -> v == Vector2F.ZERO },
                IllegalArgumentException::class.java
            ),
        )

        @JvmStatic
        fun singleOrNullArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(vector2FArrayOf(Vector2F(1f, 2f))),
                Wrapper(Vector2F(1f, 2f))
            ),
            Arguments.of(
                Wrapper(vector2FArrayOf(Vector2F.ZERO)),
                Wrapper(Vector2F(0f, 0f))
            ),
            Arguments.of(
                Wrapper(Vector2FArray(0)), Wrapper<Vector2F?>(null)
            ),
            Arguments.of(
                Wrapper(Vector2FArray(2) { Vector2F.ZERO }), Wrapper<Vector2F?>(null)
            ),
        )

        @JvmStatic
        fun singleOrNullPredicateArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2FArray(3) { Vector2F(it.toFloat(), 0f) }),
                { v: Vector2F -> v == Vector2F(1f, 0f) },
                Wrapper(Vector2F(1f, 0f))
            ),
            Arguments.of(
                Wrapper(Vector2FArray(3) { Vector2F(it.toFloat(), 0f) }),
                { v: Vector2F -> v.x in 0.5f..1.5f },
                Wrapper(Vector2F(1f, 0f))
            ),
            Arguments.of(
                Wrapper(Vector2FArray(0)),
                { v: Vector2F -> v == Vector2F.ZERO },
                Wrapper<Vector2F?>(null)
            ),
            Arguments.of(
                Wrapper(Vector2FArray(2) { Vector2F.ZERO }),
                { v: Vector2F -> v == Vector2F.ZERO },
                Wrapper<Vector2F?>(null)
            ),
        )

        @JvmStatic
        fun sliceRangeArgs(): List<Arguments> {
            val array = Vector2FArray(3) { Vector2F(it.toFloat(), 0f) }

            return listOf(
                Arguments.of(
                    Wrapper(array),
                    0..2,
                    listOf(Vector2F(0f, 0f), Vector2F(1f, 0f), Vector2F(2f, 0f)),
                ),
                Arguments.of(
                    Wrapper(array),
                    1..2,
                    listOf(Vector2F(1f, 0f), Vector2F(2f, 0f)),
                ),
                Arguments.of(
                    Wrapper(array),
                    IntRange.EMPTY,
                    emptyList<Vector2F>(),
                ),
            )
        }

        @JvmStatic
        fun sliceRangeThrowsExceptionArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2FArray(3) { Vector2F.ZERO }),
                0..3,
                IndexOutOfBoundsException::class.java
            ),
            Arguments.of(
                Wrapper(Vector2FArray(3) { Vector2F.ZERO }),
                -1..2,
                IndexOutOfBoundsException::class.java
            ),
        )

        @JvmStatic
        fun sliceIterableArgs(): List<Arguments> {
            val array = Vector2FArray(3) { Vector2F(it.toFloat(), 0f) }

            return listOf(
                Arguments.of(
                    Wrapper(array),
                    listOf(0, 1, 2),
                    listOf(Vector2F(0f, 0f), Vector2F(1f, 0f), Vector2F(2f, 0f)),
                ),
                Arguments.of(
                    Wrapper(array),
                    listOf(0, 2),
                    listOf(Vector2F(0f, 0f), Vector2F(2f, 0f)),
                ),
                Arguments.of(
                    Wrapper(array),
                    emptyList<Int>(),
                    emptyList<Vector2F>(),
                ),
            )
        }

        @JvmStatic
        fun sliceIterableThrowsExceptionArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2FArray(3) { Vector2F.ZERO }),
                listOf(-1, 0, 1),
                IndexOutOfBoundsException::class.java
            ),
            Arguments.of(
                Wrapper(Vector2FArray(3) { Vector2F.ZERO }),
                listOf(0, 1, 3),
                IndexOutOfBoundsException::class.java
            ),
        )

        @JvmStatic
        fun sliceArrayCollectionArgs(): List<Arguments> {
            val array = Vector2FArray(3) { Vector2F(it.toFloat(), 0f) }

            return listOf(
                Arguments.of(
                    Wrapper(array),
                    listOf(0, 1, 2),
                    Wrapper(
                        vector2FArrayOf(
                            Vector2F(0f, 0f), Vector2F(1f, 0f), Vector2F(2f, 0f)
                        )
                    ),
                ),
                Arguments.of(
                    Wrapper(array),
                    listOf(0, 2),
                    Wrapper(
                        vector2FArrayOf(Vector2F(0f, 0f), Vector2F(2f, 0f))
                    ),
                ),
                Arguments.of(
                    Wrapper(array),
                    emptyList<Int>(),
                    Wrapper(Vector2FArray(0)),
                ),
            )
        }

        @JvmStatic
        fun sliceArrayCollectionThrowsExceptionArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2FArray(3) { Vector2F.ZERO }),
                listOf(-1, 0, 1),
                IndexOutOfBoundsException::class.java
            ),
            Arguments.of(
                Wrapper(Vector2FArray(3) { Vector2F.ZERO }),
                listOf(0, 1, 3),
                IndexOutOfBoundsException::class.java
            ),
        )

        @JvmStatic
        fun sliceArrayRangeArgs(): List<Arguments> {
            val array = Vector2FArray(3) { Vector2F(it.toFloat(), 0f) }

            return listOf(
                Arguments.of(
                    Wrapper(array),
                    0..2,
                    Wrapper(
                        vector2FArrayOf(
                            Vector2F(0f, 0f), Vector2F(1f, 0f), Vector2F(2f, 0f)
                        )
                    ),
                ),
                Arguments.of(
                    Wrapper(array),
                    1..2,
                    Wrapper(
                        vector2FArrayOf(Vector2F(1f, 0f), Vector2F(2f, 0f))
                    ),
                ),
                Arguments.of(
                    Wrapper(array),
                    IntRange.EMPTY,
                    Wrapper(Vector2FArray(0)),
                ),
            )
        }

        @JvmStatic
        fun sliceArrayRangeThrowsExceptionArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2FArray(3) { Vector2F.ZERO }),
                0..3,
                IndexOutOfBoundsException::class.java
            ),
            Arguments.of(
                Wrapper(Vector2FArray(3) { Vector2F.ZERO }),
                -1..2,
                IndexOutOfBoundsException::class.java
            ),
        )

        @JvmStatic
        fun sortedByArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(
                    vector2FArrayOf(
                        Vector2F(4f, 2f), Vector2F(7f, 3f), Vector2F(3f, 1f),
                    )
                ),
                { v: Vector2F -> v.x },
                listOf(Vector2F(3f, 1f), Vector2F(4f, 2f), Vector2F(7f, 3f))
            ),
            Arguments.of(
                Wrapper(Vector2FArray(0)),
                { v: Vector2F -> v.x },
                emptyList<Vector2F>()
            ),
        )

        @JvmStatic
        fun sortedByDescendingArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(
                    vector2FArrayOf(
                        Vector2F(4f, 2f), Vector2F(7f, 3f), Vector2F(3f, 1f),
                    )
                ),
                { v: Vector2F -> v.y },
                listOf(Vector2F(7f, 3f), Vector2F(4f, 2f), Vector2F(3f, 1f))
            ),
            Arguments.of(
                Wrapper(Vector2FArray(0)),
                { v: Vector2F -> v.y },
                emptyList<Vector2F>()
            ),
        )

        @JvmStatic
        fun sortedWithArgs(): List<Arguments> {
            val comparator = Comparator<Vector2F> { a, b ->
                val xToX = a.x.compareTo(b.x)

                if (xToX == 0) a.y.compareTo(b.y) else xToX
            }
            return listOf(
                Arguments.of(
                    Wrapper(
                        vector2FArrayOf(
                            Vector2F(4f, 3f), Vector2F(4f, 2f), Vector2F(3f, 1f),
                        )
                    ),
                    comparator,
                    listOf(Vector2F(3f, 1f), Vector2F(4f, 2f), Vector2F(4f, 3f))
                ),
                Arguments.of(
                    Wrapper(Vector2FArray(0)),
                    comparator,
                    emptyList<Vector2F>()
                ),
            )
        }

        @JvmStatic
        fun sumArgs(): List<Arguments> = listOf(
            Arguments.of(Wrapper(Vector2FArray(0)), Wrapper(Vector2F(0f, 0f))),
            Arguments.of(
                Wrapper(
                    vector2FArrayOf(
                        Vector2F(1f, 2f),
                        Vector2F(-2f, -1f),
                        Vector2F(10f, 10f)
                    )
                ),
                Wrapper(Vector2F(9f, 11f))
            ),
        )

        @JvmStatic
        fun sumOfDoubleArgs(): List<Arguments> {
            val array = vector2FArrayOf(
                Vector2F(1f, 2f), Vector2F(-2f, -1f), Vector2F(10f, 10f)
            )

            return listOf(
                Arguments.of(
                    Wrapper(array), { v: Vector2F -> v.x.toDouble() + v.y.toDouble() }, 20.0
                ),
                Arguments.of(
                    Wrapper(array),
                    { v: Vector2F -> v.x.toDouble().absoluteValue + v.y.toDouble().absoluteValue },
                    26.0
                ),
            )
        }

        @JvmStatic
        fun sumOfIntArgs(): List<Arguments> {
            val array = vector2FArrayOf(
                Vector2F(1f, 2f), Vector2F(-2f, -1f), Vector2F(10f, 10f)
            )

            return listOf(
                Arguments.of(
                    Wrapper(array), { v: Vector2F -> v.x.toInt() + v.y.toInt() }, 20
                ),
                Arguments.of(
                    Wrapper(array),
                    { v: Vector2F -> v.x.toInt().absoluteValue + v.y.toInt().absoluteValue },
                    26
                ),
            )
        }

        @JvmStatic
        fun sumOfUIntArgs(): List<Arguments> {
            val array = vector2FArrayOf(
                Vector2F(3f, 2f), Vector2F(2f, 1f), Vector2F(10f, 10f)
            )

            return listOf(
                Arguments.of(
                    Wrapper(array), { v: Vector2F -> v.x.toUInt() + v.y.toUInt() },
                    Wrapper(28u)
                ),
                Arguments.of(
                    Wrapper(array), { v: Vector2F -> v.x.toUInt() - v.y.toUInt() },
                    Wrapper(2u)
                ),
            )
        }

        @JvmStatic
        fun sumOfLongArgs(): List<Arguments> {
            val array = vector2FArrayOf(
                Vector2F(1f, 2f), Vector2F(-2f, -1f), Vector2F(10f, 10f)
            )

            return listOf(
                Arguments.of(
                    Wrapper(array), { v: Vector2F -> v.x.toLong() + v.y.toLong() }, 20L
                ),
                Arguments.of(
                    Wrapper(array),
                    { v: Vector2F -> v.x.toLong().absoluteValue + v.y.toLong().absoluteValue },
                    26L
                ),
            )
        }

        @JvmStatic
        fun sumOfULongArgs(): List<Arguments> {
            val array = vector2FArrayOf(
                Vector2F(3f, 2f), Vector2F(2f, 1f), Vector2F(10f, 10f)
            )

            return listOf(
                Arguments.of(
                    Wrapper(array),
                    { v: Vector2F -> v.x.toULong() + v.y.toULong() },
                    Wrapper(28uL)
                ),
                Arguments.of(
                    Wrapper(array),
                    { v: Vector2F -> v.x.toULong() - v.y.toULong() },
                    Wrapper(2uL)
                ),
            )
        }

        @JvmStatic
        fun sumOfVector2FArgs(): List<Arguments> {
            val array = vector2FArrayOf(
                Vector2F(1f, 2f), Vector2F(-2f, -1f), Vector2F(10f, 10f)
            )

            return listOf(
                Arguments.of(
                    Wrapper(array), { v: Vector2F -> v }, Wrapper(Vector2F(9f, 11f))
                ),
                Arguments.of(
                    Wrapper(array),
                    { v: Vector2F -> Vector2F(v.x.absoluteValue, v.y.absoluteValue) },
                    Wrapper(Vector2F(13f, 13f))
                ),
            )
        }

        @JvmStatic
        fun takeArgs(): List<Arguments> {
            val array = Vector2FArray(4) { Vector2F(it.toFloat(), 0f) }

            return listOf(
                Arguments.of(
                    Wrapper(array), 0, emptyList<Vector2F>()
                ),
                Arguments.of(
                    Wrapper(array), 2, listOf(Vector2F(0f, 0f), Vector2F(1f, 0f))
                ),
                Arguments.of(
                    Wrapper(array), 5, List(4) { Vector2F(it.toFloat(), 0f) }
                ),
            )
        }

        @JvmStatic
        fun takeLastArgs(): List<Arguments> {
            val array = Vector2FArray(4) { Vector2F(it.toFloat(), 0f) }

            return listOf(
                Arguments.of(
                    Wrapper(array), 0, emptyList<Vector2F>()
                ),
                Arguments.of(
                    Wrapper(array), 2, listOf(Vector2F(2f, 0f), Vector2F(3f, 0f))
                ),
                Arguments.of(
                    Wrapper(array), 5, List(4) { Vector2F(it.toFloat(), 0f) }
                ),
            )
        }

        @JvmStatic
        fun takeLastWhileArgs(): List<Arguments> {
            val array = Vector2FArray(4) { Vector2F(it.toFloat(), 0f) }

            return listOf(
                Arguments.of(
                    Wrapper(Vector2FArray(0)), { _: Vector2F -> true }, emptyList<Vector2F>(),
                ),
                Arguments.of(
                    Wrapper(array), { _: Vector2F -> false }, emptyList<Vector2F>()
                ),
                Arguments.of(
                    Wrapper(array),
                    { v: Vector2F -> v.x !in 0.5f..1.5f },
                    listOf(Vector2F(2f, 0f), Vector2F(3f, 0f))
                ),
                Arguments.of(
                    Wrapper(array),
                    { _: Vector2F -> true },
                    List(4) { Vector2F(it.toFloat(), 0f) }
                ),
            )
        }

        @JvmStatic
        fun takeWhileArgs(): List<Arguments> {
            val array = Vector2FArray(4) { Vector2F(it.toFloat(), 0f) }

            return listOf(
                Arguments.of(
                    Wrapper(Vector2FArray(0)), { _: Vector2F -> true }, emptyList<Vector2F>(),
                ),
                Arguments.of(
                    Wrapper(array), { _: Vector2F -> false }, emptyList<Vector2F>()
                ),
                Arguments.of(
                    Wrapper(array),
                    { v: Vector2F -> v.x !in 1.5f..2.5f },
                    listOf(Vector2F(0f, 0f), Vector2F(1f, 0f))
                ),
                Arguments.of(
                    Wrapper(array),
                    { _: Vector2F -> true },
                    List(4) { Vector2F(it.toFloat(), 0f) }
                ),
            )
        }

        @JvmStatic
        fun toCollectionArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2FArray(0)),
                mutableListOf<Vector2F>(),
                mutableListOf<Vector2F>(),
            ),
            Arguments.of(
                Wrapper(
                    vector2FArrayOf(Vector2F(0f, 1f), Vector2F(2f, 3f), Vector2F(4f, 5f))

                ),
                mutableListOf<Vector2F>(),
                mutableListOf(
                    Vector2F(0f, 1f), Vector2F(2f, 3f), Vector2F(4f, 5f)
                ),
            ),
            Arguments.of(
                Wrapper(
                    vector2FArrayOf(Vector2F(0f, 1f), Vector2F(2f, 3f), Vector2F(4f, 5f))

                ),
                mutableListOf(Vector2F(1f, 1f), Vector2F(2f, 2f)),
                mutableListOf(
                    Vector2F(1f, 1f),
                    Vector2F(2f, 2f),
                    Vector2F(0f, 1f),
                    Vector2F(2f, 3f),
                    Vector2F(4f, 5f)
                ),
            ),
        )

        @JvmStatic
        fun toTypedArrayArgs(): List<Arguments> = listOf(
            Arguments.of(Wrapper(Vector2FArray(0)), emptyArray<Vector2F>()),
            Arguments.of(
                Wrapper(Vector2FArray(4) { Vector2F(it.toFloat(), 0f) }),
                Array(4) { Vector2F(it.toFloat(), 0f) }
            ),
        )

        @JvmStatic
        fun toStringArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(
                    vector2FArrayOf(
                        Vector2F(1f, 0f),
                        Vector2F(-5f, 8f),
                        Vector2F(2f, -9f),
                        Vector2F(3f, 4f)
                    ),
                ),
                "[${Vector2F(1f, 0f)}, ${Vector2F(-5f, 8f)}, " +
                    "${Vector2F(2f, -9f)}, ${Vector2F(3f, 4f)}]"
            ),
            Arguments.of(
                Wrapper(
                    vector2FArrayOf(
                        Vector2F(1f, 2f), Vector2F(-2f, -1f), Vector2F(10f, 10f)
                    )
                ),
                "[${Vector2F(1f, 2f)}, ${Vector2F(-2f, -1f)}, " +
                    "${Vector2F(10f, 10f)}]"
            ),
            Arguments.of(Wrapper(Vector2FArray(0)), "[]"),
        )

        @JvmStatic
        fun containsArgs(): List<Arguments> {
            val array = vector2FArrayOf(
                Vector2F(1f, 0f),
                Vector2F(-5f, 8f),
                Vector2F(2f, -9f),
                Vector2F(3f, 4f)
            )

            return listOf(
                Arguments.of(Wrapper(array), Wrapper(Vector2F(0f, 0f)), false),
                Arguments.of(Wrapper(array), Wrapper(Vector2F(3f, 4f)), true),
            )
        }

        @JvmStatic
        fun getArgs(): List<Arguments> {
            val array = vector2FArrayOf(
                Vector2F(1f, 0f),
                Vector2F(-5f, 8f),
                Vector2F(2f, -9f),
                Vector2F(3f, 4f),
            )

            return listOf(
                Arguments.of(Wrapper(array), 0, Wrapper(Vector2F(1f, 0f))),
                Arguments.of(Wrapper(array), 3, Wrapper(Vector2F(3f, 4f))),
            )
        }

        @JvmStatic
        fun arrayPlusCollectionArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2FArray(0)),
                listOf(Vector2F(1f, 0f)),
                Wrapper(vector2FArrayOf(Vector2F(1f, 0f))),
            ),
            Arguments.of(
                Wrapper(vector2FArrayOf(Vector2F(1f, 0f), Vector2F(2f, 0f))),
                listOf(Vector2F(3f, 0f), Vector2F(4f, 0f)),
                Wrapper(
                    vector2FArrayOf(
                        Vector2F(1f, 0f),
                        Vector2F(2f, 0f),
                        Vector2F(3f, 0f),
                        Vector2F(4f, 0f)
                    )
                ),
            ),
        )

        @JvmStatic
        fun arrayPlusVector2FArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2FArray(0)),
                Wrapper(Vector2F(1f, 0f)),
                Wrapper(vector2FArrayOf(Vector2F(1f, 0f)))
            ),
            Arguments.of(
                Wrapper(vector2FArrayOf(Vector2F(1f, 0f), Vector2F(2f, 0f))),
                Wrapper(Vector2F(3f, 0f)),
                Wrapper(
                    vector2FArrayOf(Vector2F(1f, 0f), Vector2F(2f, 0f), Vector2F(3f, 0f))

                ),
            ),
        )

        @JvmStatic
        fun arrayPlusArrayArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2FArray(0)),
                Wrapper(vector2FArrayOf(Vector2F(1f, 0f))),
                Wrapper(vector2FArrayOf(Vector2F(1f, 0f))),
            ),
            Arguments.of(
                Wrapper(vector2FArrayOf(Vector2F(1f, 0f), Vector2F(2f, 0f))),
                Wrapper(vector2FArrayOf(Vector2F(3f, 0f), Vector2F(4f, 0f))),
                Wrapper(
                    vector2FArrayOf(
                        Vector2F(1f, 0f),
                        Vector2F(2f, 0f),
                        Vector2F(3f, 0f),
                        Vector2F(4f, 0f)
                    )
                ),
            ),
        )

        @JvmStatic
        fun setArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2FArray(4) { Vector2F.ZERO }),
                0,
                Wrapper(Vector2F(1f, 0f)),
                Wrapper(
                    vector2FArrayOf(Vector2F(1f, 0f), Vector2F.ZERO, Vector2F.ZERO, Vector2F.ZERO)

                ),
            ),
            Arguments.of(
                Wrapper(Vector2FArray(4) { Vector2F.ZERO }),
                3,
                Wrapper(Vector2F(3f, 4f)),
                Wrapper(
                    vector2FArrayOf(Vector2F.ZERO, Vector2F.ZERO, Vector2F.ZERO, Vector2F(3f, 4f))

                )
            ),
        )

        @JvmStatic
        fun typedArraySumArgs(): List<Arguments> = listOf(
            Arguments.of(emptyArray<Vector2F>(), Wrapper(Vector2F.ZERO)),
            Arguments.of(
                arrayOf(
                    Vector2F(1f, 2f), Vector2F(-2f, -1f), Vector2F(10f, 10f)
                ),
                Wrapper(Vector2F(9f, 11f))
            ),
        )

        @JvmStatic
        fun typedArraySumOfVector2FArgs(): List<Arguments> = listOf(
            Arguments.of(
                arrayOf(
                    Vector2F(1f, 2f), Vector2F(-2f, -1f), Vector2F(10f, 10f)
                ),
                { v: Vector2F -> v },
                Wrapper(Vector2F(9f, 11f))
            ),
            Arguments.of(
                arrayOf(1f, -2f, 10f),
                { v: Float -> Vector2F(v, v) },
                Wrapper(Vector2F(9f, 9f))
            ),
            Arguments.of(
                arrayOf(
                    Vector2F(1f, 2f), Vector2F(-2f, -1f), Vector2F(10f, 10f)
                ),
                { v: Vector2F -> Vector2F(v.x.absoluteValue, v.y.absoluteValue) },
                Wrapper(Vector2F(13f, 13f))
            ),
        )

        @JvmStatic
        fun typedArrayToVector2FArrayArgs(): List<Arguments> = listOf(
            Arguments.of(emptyArray<Vector2F>(), Wrapper(Vector2FArray(0))),
            Arguments.of(
                Array(4) { Vector2F(it.toFloat(), 0f) },
                Wrapper(Vector2FArray(4) { Vector2F(it.toFloat(), 0f) })
            ),
        )

        @JvmStatic
        fun iterableSumArgs(): List<Arguments> = listOf(
            Arguments.of(emptyList<Vector2F>(), Wrapper(Vector2F.ZERO)),
            Arguments.of(
                listOf(
                    Vector2F(1f, 2f), Vector2F(-2f, -1f), Vector2F(10f, 10f)
                ),
                Wrapper(Vector2F(9f, 11f))
            ),
        )

        @JvmStatic
        fun iterableSumOfVector2FArgs(): List<Arguments> = listOf(
            Arguments.of(
                listOf(
                    Vector2F(1f, 2f), Vector2F(-2f, -1f), Vector2F(10f, 10f)
                ),
                { v: Vector2F -> v },
                Wrapper(Vector2F(9f, 11f))
            ),
            Arguments.of(
                listOf(1f, -2f, 10f),
                { v: Float -> Vector2F(v, v) },
                Wrapper(Vector2F(9f, 9f))
            ),
            Arguments.of(
                listOf(
                    Vector2F(1f, 2f), Vector2F(-2f, -1f), Vector2F(10f, 10f)
                ),
                { v: Vector2F -> Vector2F(v.x.absoluteValue, v.y.absoluteValue) },
                Wrapper(Vector2F(13f, 13f))
            ),
        )

        @JvmStatic
        fun vector2FArrayOfVector2FArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2F(3f, 4f)),
                Wrapper(arrayOf(Vector2F(3f, 4f)).toVector2FArray())
            ),
            Arguments.of(
                Wrapper(Vector2F(-2f, -1f)),
                Wrapper(arrayOf(Vector2F(-2f, -1f)).toVector2FArray())
            ),
        )

        @JvmStatic
        fun vector2FArrayOfTypedArrayArgs(): List<Arguments> = listOf(
            Arguments.of(
                emptyArray<Vector2F>(),
                Wrapper(emptyArray<Vector2F>().toVector2FArray())
            ),
            Arguments.of(
                arrayOf(Vector2F(1f, 2f)),
                Wrapper(arrayOf(Vector2F(1f, 2f)).toVector2FArray())
            ),
            Arguments.of(
                arrayOf(Vector2F(1f, 2f), Vector2F(-2f, -1f)),
                Wrapper(
                    arrayOf(Vector2F(1f, 2f), Vector2F(-2f, -1f))
                        .toVector2FArray()
                )
            ),
            Arguments.of(
                arrayOf(
                    Vector2F(1f, 2f),
                    Vector2F(-2f, -1f),
                    Vector2F(10f, -10f)
                ),
                Wrapper(
                    arrayOf(
                        Vector2F(1f, 2f),
                        Vector2F(-2f, -1f),
                        Vector2F(10f, -10f)
                    ).toVector2FArray()
                )
            ),
        )
    }
}