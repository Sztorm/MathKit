package nonallocmath

import com.sztorm.nonallocmath.*
import nonallocmath.utils.Wrapper
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.math.absoluteValue
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class Vector2IArrayTests {

    @Test
    fun basicPropertiesAreValid() {
        val array = Vector2IArray(5) { Vector2I(it, -it) }
        val (a0, a1, a2, a3, a4) = array

        assertEquals(5, array.size)
        assertEquals(4, array.lastIndex)
        assertEquals(IntRange(0, 4), array.indices)
        assertEquals(Vector2I(0, -0), array[0])
        assertEquals(Vector2I(1, -1), array[1])
        assertEquals(Vector2I(2, -2), array[2])
        assertEquals(Vector2I(3, -3), array[3])
        assertEquals(Vector2I(4, -4), array[4])
        assertEquals(Vector2I(0, -0), a0)
        assertEquals(Vector2I(1, -1), a1)
        assertEquals(Vector2I(2, -2), a2)
        assertEquals(Vector2I(3, -3), a3)
        assertEquals(Vector2I(4, -4), a4)
    }

    @Test
    fun constructorThrowsWhenSizeIsNegative() {
        assertThrows<NegativeArraySizeException> { Vector2IArray(-1) }
    }

    @ParameterizedTest
    @MethodSource("containsAllArgs")
    fun containsAllReturnsCorrectValue(
        array: Wrapper<Vector2IArray>, elements: Collection<Vector2I>, expected: Boolean
    ) = assertEquals(expected, array.value.containsAll(elements))

    @ParameterizedTest
    @MethodSource("elementAtArgs")
    fun elementAtReturnsCorrectValue(
        array: Wrapper<Vector2IArray>, index: Int, expected: Wrapper<Vector2I>
    ) = assertEquals(expected.value, array.value.elementAt(index))

    @Test
    fun elementAtThrowsWhenIndexIsOutOfBounds() {
        val array = Vector2IArray(4)

        assertThrows<IndexOutOfBoundsException> { array.elementAt(-1) }
        assertThrows<IndexOutOfBoundsException> { array.elementAt(4) }
    }

    @ParameterizedTest
    @MethodSource("elementAtOrElseArgs")
    fun elementAtOrElseReturnsCorrectValue(
        array: Wrapper<Vector2IArray>,
        index: Int,
        defaultValue: (Int) -> Vector2I,
        expected: Wrapper<Vector2I>
    ) = assertEquals(expected.value, array.value.elementAtOrElse(index, defaultValue))

    @ParameterizedTest
    @MethodSource("elementAtOrNullArgs")
    fun elementAtOrNullReturnsCorrectValue(
        array: Wrapper<Vector2IArray>, index: Int, expected: Wrapper<Vector2I?>
    ) = assertEquals(expected.value, array.value.elementAtOrNull(index))

    @ParameterizedTest
    @MethodSource("findArgs")
    fun findReturnsCorrectValue(
        array: Wrapper<Vector2IArray>,
        predicate: (Vector2I) -> Boolean,
        expected: Wrapper<Vector2I?>
    ) = assertEquals(expected.value, array.value.find(predicate))

    @ParameterizedTest
    @MethodSource("findLastArgs")
    fun findLastReturnsCorrectValue(
        array: Wrapper<Vector2IArray>,
        predicate: (Vector2I) -> Boolean,
        expected: Wrapper<Vector2I?>
    ) = assertEquals(expected.value, array.value.findLast(predicate))

    @ParameterizedTest
    @MethodSource("firstArgs")
    fun firstReturnsCorrectValue(array: Wrapper<Vector2IArray>, expected: Wrapper<Vector2I>) =
        assertEquals(expected.value, array.value.first())

    @Test
    fun firstThrowsWhenArrayIsEmpty() {
        assertThrows<NoSuchElementException> { Vector2IArray(0).first() }
    }

    @ParameterizedTest
    @MethodSource("firstPredicateArgs")
    fun firstReturnsCorrectValue(
        array: Wrapper<Vector2IArray>,
        predicate: (Vector2I) -> Boolean,
        expected: Wrapper<Vector2I>
    ) = assertEquals(expected.value, array.value.first(predicate))

    @ParameterizedTest
    @MethodSource("firstPredicateNotMatchArgs")
    fun firstThrowsWhenNoElementMatchesThePredicate(
        array: Wrapper<Vector2IArray>, predicate: (Vector2I) -> Boolean
    ) {
        assertThrows<NoSuchElementException> { array.value.first(predicate) }
    }

    @ParameterizedTest
    @MethodSource("firstOrNullArgs")
    fun firstOrNullReturnsCorrectValue(
        array: Wrapper<Vector2IArray>, expected: Wrapper<Vector2I?>
    ) = assertEquals(expected.value, array.value.firstOrNull())

    @ParameterizedTest
    @MethodSource("firstOrNullPredicateArgs")
    fun firstOrNullReturnsCorrectValue(
        array: Wrapper<Vector2IArray>,
        predicate: (Vector2I) -> Boolean,
        expected: Wrapper<Vector2I?>
    ) = assertEquals(expected.value, array.value.firstOrNull(predicate))

    @ParameterizedTest
    @MethodSource("getOrElseArgs")
    fun getOrElseReturnsCorrectValue(
        array: Wrapper<Vector2IArray>,
        index: Int,
        defaultValue: (Int) -> Vector2I,
        expected: Wrapper<Vector2I>
    ) = assertEquals(expected.value, array.value.getOrElse(index, defaultValue))

    @ParameterizedTest
    @MethodSource("getOrNullArgs")
    fun getOrNullReturnsCorrectValue(
        array: Wrapper<Vector2IArray>, index: Int, expected: Wrapper<Vector2I?>
    ) = assertEquals(expected.value, array.value.getOrNull(index))

    @ParameterizedTest
    @MethodSource("indexOfArgs")
    fun indexOfReturnsCorrectValue(
        array: Wrapper<Vector2IArray>, element: Wrapper<Vector2I>, expected: Int
    ) = assertEquals(expected, array.value.indexOf(element.value))

    @ParameterizedTest
    @MethodSource("indexOfFirstArgs")
    fun indexOfFirstReturnsCorrectValue(
        array: Wrapper<Vector2IArray>, predicate: (Vector2I) -> Boolean, expected: Int
    ) = assertEquals(expected, array.value.indexOfFirst(predicate))

    @ParameterizedTest
    @MethodSource("lastArgs")
    fun lastReturnsCorrectValue(array: Wrapper<Vector2IArray>, expected: Wrapper<Vector2I>) =
        assertEquals(expected.value, array.value.last())

    @Test
    fun lastThrowsWhenArrayIsEmpty() {
        assertThrows<NoSuchElementException> { Vector2IArray(0).last() }
    }

    @ParameterizedTest
    @MethodSource("lastPredicateArgs")
    fun lastReturnsCorrectValue(
        array: Wrapper<Vector2IArray>,
        predicate: (Vector2I) -> Boolean,
        expected: Wrapper<Vector2I>
    ) = assertEquals(expected.value, array.value.last(predicate))

    @ParameterizedTest
    @MethodSource("lastPredicateNotMatchArgs")
    fun lastThrowsWhenNoElementMatchesThePredicate(
        array: Wrapper<Vector2IArray>, predicate: (Vector2I) -> Boolean
    ) {
        assertThrows<NoSuchElementException> { array.value.last(predicate) }
    }

    @ParameterizedTest
    @MethodSource("lastIndexOfArgs")
    fun lastIndexOfReturnsCorrectValue(
        array: Wrapper<Vector2IArray>, element: Wrapper<Vector2I>, expected: Int
    ) = assertEquals(expected, array.value.lastIndexOf(element.value))

    @ParameterizedTest
    @MethodSource("lastOrNullArgs")
    fun lastOrNullReturnsCorrectValue(
        array: Wrapper<Vector2IArray>, expected: Wrapper<Vector2I?>
    ) = assertEquals(expected.value, array.value.lastOrNull())

    @ParameterizedTest
    @MethodSource("lastOrNullPredicateArgs")
    fun lastOrNullReturnsCorrectValue(
        array: Wrapper<Vector2IArray>,
        predicate: (Vector2I) -> Boolean,
        expected: Wrapper<Vector2I?>
    ) = assertEquals(expected.value, array.value.lastOrNull(predicate))

    @ParameterizedTest
    @MethodSource("randomArgs")
    fun randomReturnsCorrectValue(
        array: Wrapper<Vector2IArray>, random: Random, expected: Wrapper<Vector2I>
    ) = assertEquals(expected.value, array.value.random(random))

    @Test
    fun randomThrowsWhenArrayIsEmpty() {
        assertThrows<NoSuchElementException> { Vector2IArray(0).random() }
        assertThrows<NoSuchElementException> { Vector2IArray(0).random(Random) }
    }

    @ParameterizedTest
    @MethodSource("randomOrNullArgs")
    fun randomOrNullReturnsCorrectValue(
        array: Wrapper<Vector2IArray>, random: Random, expected: Wrapper<Vector2I?>
    ) = assertEquals(expected.value, array.value.randomOrNull(random))

    @ParameterizedTest
    @MethodSource("singleArgs")
    fun singleReturnsCorrectValue(array: Wrapper<Vector2IArray>, expected: Wrapper<Vector2I>) =
        assertEquals(expected.value, array.value.single())

    @ParameterizedTest
    @MethodSource("singleThrowsExceptionArgs")
    fun <T : Throwable> singleThrowsCorrectException(
        array: Wrapper<Vector2IArray>, expectedExceptionClass: Class<T>
    ) {
        assertThrows(expectedExceptionClass) {
            array.value.single()
        }
    }

    @ParameterizedTest
    @MethodSource("singlePredicateArgs")
    fun singleReturnsCorrectValue(
        array: Wrapper<Vector2IArray>,
        predicate: (Vector2I) -> Boolean,
        expected: Wrapper<Vector2I>
    ) = assertEquals(expected.value, array.value.single(predicate))

    @ParameterizedTest
    @MethodSource("singlePredicateThrowsExceptionArgs")
    fun <T : Throwable> singleThrowsCorrectException(
        array: Wrapper<Vector2IArray>,
        predicate: (Vector2I) -> Boolean,
        expectedExceptionClass: Class<T>
    ) {
        assertThrows(expectedExceptionClass) {
            array.value.single(predicate)
        }
    }

    @ParameterizedTest
    @MethodSource("singleOrNullArgs")
    fun singleOrNullReturnsCorrectValue(
        array: Wrapper<Vector2IArray>, expected: Wrapper<Vector2I?>
    ) = assertEquals(expected.value, array.value.singleOrNull())

    @ParameterizedTest
    @MethodSource("dropArgs")
    fun dropReturnsCorrectValue(
        array: Wrapper<Vector2IArray>, n: Int, expected: List<Vector2I>
    ) = assertContentEquals(expected, array.value.drop(n))

    @Test
    fun dropThrowsWhenRequestedCountIsNegative() {
        assertThrows<IllegalArgumentException> { Vector2IArray(0).drop(-1) }
    }

    @ParameterizedTest
    @MethodSource("dropLastArgs")
    fun dropLastReturnsCorrectValue(
        array: Wrapper<Vector2IArray>, n: Int, expected: List<Vector2I>
    ) = assertContentEquals(expected, array.value.dropLast(n))

    @Test
    fun dropLastThrowsWhenRequestedCountIsNegative() {
        assertThrows<IllegalArgumentException> { Vector2IArray(0).dropLast(-1) }
    }

    @ParameterizedTest
    @MethodSource("dropLastWhileArgs")
    fun dropLastWhileReturnsCorrectValue(
        array: Wrapper<Vector2IArray>,
        predicate: (Vector2I) -> Boolean,
        expected: List<Vector2I>
    ) = assertContentEquals(expected, array.value.dropLastWhile(predicate))

    @ParameterizedTest
    @MethodSource("dropWhileArgs")
    fun dropWhileReturnsCorrectValue(
        array: Wrapper<Vector2IArray>, predicate: (Vector2I) -> Boolean, expected: List<Vector2I>
    ) = assertContentEquals(expected, array.value.dropWhile(predicate))

    @ParameterizedTest
    @MethodSource("sliceRangeArgs")
    fun sliceReturnsCorrectValue(
        array: Wrapper<Vector2IArray>, indices: IntRange, expected: List<Vector2I>
    ) = assertContentEquals(expected, array.value.slice(indices))

    @ParameterizedTest
    @MethodSource("sliceRangeThrowsExceptionArgs")
    fun <T : Throwable> sliceThrowsCorrectException(
        array: Wrapper<Vector2IArray>, indices: IntRange, expectedExceptionClass: Class<T>
    ) {
        assertThrows(expectedExceptionClass) {
            array.value.slice(indices)
        }
    }

    @ParameterizedTest
    @MethodSource("sliceIterableArgs")
    fun sliceReturnsCorrectValue(
        array: Wrapper<Vector2IArray>, indices: Iterable<Int>, expected: List<Vector2I>
    ) = assertContentEquals(expected, array.value.slice(indices))

    @ParameterizedTest
    @MethodSource("sliceIterableThrowsExceptionArgs")
    fun <T : Throwable> sliceThrowsCorrectException(
        array: Wrapper<Vector2IArray>, indices: Iterable<Int>, expectedExceptionClass: Class<T>
    ) {
        assertThrows(expectedExceptionClass) {
            array.value.slice(indices)
        }
    }

    @ParameterizedTest
    @MethodSource("sliceArrayCollectionArgs")
    fun sliceArrayReturnsCorrectValue(
        array: Wrapper<Vector2IArray>,
        indices: Collection<Int>,
        expected: Wrapper<Vector2IArray>
    ) = assertContentEquals(expected.value, array.value.sliceArray(indices))

    @ParameterizedTest
    @MethodSource("sliceArrayCollectionThrowsExceptionArgs")
    fun <T : Throwable> sliceArrayThrowsCorrectException(
        array: Wrapper<Vector2IArray>,
        indices: Collection<Int>,
        expectedExceptionClass: Class<T>
    ) {
        assertThrows(expectedExceptionClass) {
            array.value.sliceArray(indices)
        }
    }

    @ParameterizedTest
    @MethodSource("sliceArrayRangeArgs")
    fun sliceArrayReturnsCorrectValue(
        array: Wrapper<Vector2IArray>, indices: IntRange, expected: Wrapper<Vector2IArray>
    ) = assertContentEquals(expected.value, array.value.sliceArray(indices))

    @ParameterizedTest
    @MethodSource("sliceArrayRangeThrowsExceptionArgs")
    fun <T : Throwable> sliceArrayThrowsCorrectException(
        array: Wrapper<Vector2IArray>, indices: IntRange, expectedExceptionClass: Class<T>
    ) {
        assertThrows(expectedExceptionClass) {
            array.value.sliceArray(indices)
        }
    }

    @ParameterizedTest
    @MethodSource("takeArgs")
    fun takeReturnsCorrectValue(array: Wrapper<Vector2IArray>, n: Int, expected: List<Vector2I>) =
        assertContentEquals(expected, array.value.take(n))

    @Test
    fun takeThrowsWhenRequestedCountIsNegative() {
        assertThrows<IllegalArgumentException> { Vector2IArray(0).take(-1) }
    }

    @ParameterizedTest
    @MethodSource("takeLastArgs")
    fun takeLastReturnsCorrectValue(
        array: Wrapper<Vector2IArray>, n: Int, expected: List<Vector2I>
    ) = assertContentEquals(expected, array.value.takeLast(n))

    @Test
    fun takeLastThrowsWhenRequestedCountIsNegative() {
        assertThrows<IllegalArgumentException> { Vector2IArray(0).takeLast(-1) }
    }

    @ParameterizedTest
    @MethodSource("takeLastWhileArgs")
    fun takeLastWhileReturnsCorrectValue(
        array: Wrapper<Vector2IArray>, predicate: (Vector2I) -> Boolean, expected: List<Vector2I>
    ) = assertContentEquals(expected, array.value.takeLastWhile(predicate))

    @ParameterizedTest
    @MethodSource("takeWhileArgs")
    fun takeWhileReturnsCorrectValue(
        array: Wrapper<Vector2IArray>, predicate: (Vector2I) -> Boolean, expected: List<Vector2I>
    ) = assertContentEquals(expected, array.value.takeWhile(predicate))

    @ParameterizedTest
    @MethodSource("reverseArgs")
    fun reverseMutatesArrayCorrectly(
        array: Wrapper<Vector2IArray>, expected: Wrapper<Vector2IArray>
    ) = assertContentEquals(expected.value, array.value.apply {
        reverse()
    })

    @ParameterizedTest
    @MethodSource("reverseRangeArgs")
    fun reverseMutatesArrayCorrectly(
        array: Wrapper<Vector2IArray>,
        fromIndex: Int,
        toIndex: Int,
        expected: Wrapper<Vector2IArray>
    ) = assertContentEquals(expected.value, array.value.apply {
        reverse(fromIndex, toIndex)
    })

    @ParameterizedTest
    @MethodSource("reverseRangeThrowsExceptionArgs")
    fun <T : Throwable> reverseThrowsCorrectException(
        array: Wrapper<Vector2IArray>,
        fromIndex: Int,
        toIndex: Int,
        expectedExceptionClass: Class<T>
    ) {
        assertThrows(expectedExceptionClass) {
            array.value.reverse(fromIndex, toIndex)
        }
    }

    @ParameterizedTest
    @MethodSource("reversedArgs")
    fun reversedReturnsCorrectValue(array: Wrapper<Vector2IArray>, expected: List<Vector2I>) =
        assertContentEquals(expected, array.value.reversed())

    @ParameterizedTest
    @MethodSource("reversedArrayArgs")
    fun reversedArrayReturnsCorrectValue(
        array: Wrapper<Vector2IArray>, expected: Wrapper<Vector2IArray>
    ) = assertContentEquals(expected.value, array.value.reversedArray())

    @ParameterizedTest
    @MethodSource("shuffleArgs")
    fun shuffleReturnsCorrectValue(
        array: Wrapper<Vector2IArray>, random: Random, expected: Wrapper<Vector2IArray>
    ) = assertContentEquals(expected.value, array.value.apply {
        shuffle(random)
    })

    @ParameterizedTest
    @MethodSource("sortedByArgs")
    fun <R : Comparable<R>> sortedByReturnsCorrectValue(
        array: Wrapper<Vector2IArray>, selector: (Vector2I) -> R?, expected: List<Vector2I>
    ) = assertContentEquals(expected, array.value.sortedBy(selector))

    @ParameterizedTest
    @MethodSource("sortedByDescendingArgs")
    fun <R : Comparable<R>> sortedByDescendingReturnsCorrectValue(
        array: Wrapper<Vector2IArray>, selector: (Vector2I) -> R?, expected: List<Vector2I>
    ) = assertContentEquals(expected, array.value.sortedByDescending(selector))

    @ParameterizedTest
    @MethodSource("sortedWithArgs")
    fun sortedWithReturnsCorrectValue(
        array: Wrapper<Vector2IArray>,
        comparator: Comparator<in Vector2I>,
        expected: List<Vector2I>
    ) = assertContentEquals(expected, array.value.sortedWith(comparator))

    @ParameterizedTest
    @MethodSource("asListArgs")
    fun asListReturnsCorrectValue(array: Wrapper<Vector2IArray>, expected: List<Vector2I>) =
        assertContentEquals(expected, array.value.asList())

    @ParameterizedTest
    @MethodSource("copyIntoArgs")
    fun copyIntoReturnsCorrectValue(
        array: Wrapper<Vector2IArray>,
        destination: Wrapper<Vector2IArray>,
        destinationOffset: Int,
        startIndex: Int,
        endIndex: Int,
        expected: Wrapper<Vector2IArray>
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
    fun <T : Throwable> copyIntoThrowsCorrectException(
        array: Wrapper<Vector2IArray>,
        destination: Wrapper<Vector2IArray>,
        destinationOffset: Int,
        startIndex: Int,
        endIndex: Int,
        expectedExceptionClass: Class<T>
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
        array: Wrapper<Vector2IArray>, expected: Wrapper<Vector2IArray>
    ) = assertContentEquals(expected.value, array.value.copyOf())

    @ParameterizedTest
    @MethodSource("copyOfSizeArgs")
    fun copyOfReturnsCorrectValue(
        array: Wrapper<Vector2IArray>, newSize: Int, expected: Wrapper<Vector2IArray>
    ) = assertContentEquals(expected.value, array.value.copyOf(newSize))

    @ParameterizedTest
    @MethodSource("copyOfRangeArgs")
    fun copyOfRangeReturnsCorrectValue(
        array: Wrapper<Vector2IArray>,
        fromIndex: Int,
        toIndex: Int,
        expected: Wrapper<Vector2IArray>
    ) = assertContentEquals(expected.value, array.value.copyOfRange(fromIndex, toIndex))

    @ParameterizedTest
    @MethodSource("copyOfRangeThrowsExceptionArgs")
    fun <T : Throwable> copyOfRangeThrowsCorrectException(
        array: Wrapper<Vector2IArray>,
        fromIndex: Int,
        toIndex: Int,
        expectedExceptionClass: Class<T>
    ) {
        assertThrows(expectedExceptionClass) {
            array.value.copyOfRange(fromIndex, toIndex)
        }
    }

    @ParameterizedTest
    @MethodSource("fillArgs")
    fun fillMutatesArrayCorrectly(
        array: Wrapper<Vector2IArray>,
        element: Wrapper<Vector2I>,
        fromIndex: Int,
        toIndex: Int,
        expected: Wrapper<Vector2IArray>
    ) = assertContentEquals(expected.value, array.value.apply {
        fill(element.value, fromIndex, toIndex)
    })

    @ParameterizedTest
    @MethodSource("fillThrowsExceptionArgs")
    fun <T : Throwable> fillThrowsCorrectException(
        array: Wrapper<Vector2IArray>,
        element: Wrapper<Vector2I>,
        fromIndex: Int,
        toIndex: Int,
        expectedExceptionClass: Class<T>
    ) {
        assertThrows(expectedExceptionClass) {
            array.value.fill(element.value, fromIndex, toIndex)
        }
    }

    @ParameterizedTest
    @MethodSource("isEmptyArgs")
    fun isEmptyReturnsCorrectValue(array: Wrapper<Vector2IArray>, expected: Boolean) =
        assertEquals(expected, array.value.isEmpty())

    @ParameterizedTest
    @MethodSource("isNotEmptyArgs")
    fun isNotEmptyReturnsCorrectValue(array: Wrapper<Vector2IArray>, expected: Boolean) =
        assertEquals(expected, array.value.isNotEmpty())

    @ParameterizedTest
    @MethodSource("toTypedArrayArgs")
    fun toTypedArrayReturnsCorrectValue(array: Wrapper<Vector2IArray>, expected: Array<Vector2I>) =
        assertContentEquals(expected, array.value.toTypedArray())

    @ParameterizedTest
    @MethodSource("arrays")
    fun forEachIteratesThroughArrayCorrectly(array: Wrapper<Vector2IArray>) {
        val unwrappedArray: Vector2IArray = array.value
        var expectedIndex = 0

        unwrappedArray.forEach { actualItem ->
            val expectedItem: Vector2I = unwrappedArray[expectedIndex]

            assertEquals(expectedItem, actualItem)
            expectedIndex++
        }
    }

    @ParameterizedTest
    @MethodSource("arrays")
    fun forEachIndexedIteratesThroughArrayCorrectly(array: Wrapper<Vector2IArray>) {
        val unwrappedArray: Vector2IArray = array.value
        var expectedIndex = 0

        unwrappedArray.forEachIndexed { actualIndex, actualItem ->
            val expectedItem: Vector2I = unwrappedArray[expectedIndex]

            assertEquals(expectedIndex, actualIndex)
            assertEquals(expectedItem, actualItem)
            expectedIndex++
        }
    }

    @ParameterizedTest
    @MethodSource("noneArgs")
    fun noneReturnsCorrectValue(array: Wrapper<Vector2IArray>, expected: Boolean) =
        assertEquals(expected, array.value.none())

    @ParameterizedTest
    @MethodSource("sumOfDoubleArgs")
    fun sumOfReturnsCorrectValue(
        array: Wrapper<Vector2IArray>, selector: (Vector2I) -> Double, expected: Double
    ) = assertTrue(expected.isApproximately(array.value.sumOf(selector)))

    @ParameterizedTest
    @MethodSource("sumOfIntArgs")
    fun sumOfReturnsCorrectValue(
        array: Wrapper<Vector2IArray>, selector: (Vector2I) -> Int, expected: Int
    ) = assertEquals(expected, array.value.sumOf(selector))

    @ParameterizedTest
    @MethodSource("sumOfUIntArgs")
    fun sumOfUIntReturnsCorrectValue(
        array: Wrapper<Vector2IArray>, selector: (Vector2I) -> UInt, expected: Wrapper<UInt>
    ) = assertEquals(expected.value, array.value.sumOf(selector))

    @ParameterizedTest
    @MethodSource("sumOfLongArgs")
    fun sumOfReturnsCorrectValue(
        array: Wrapper<Vector2IArray>, selector: (Vector2I) -> Long, expected: Long
    ) = assertEquals(expected, array.value.sumOf(selector))

    @ParameterizedTest
    @MethodSource("sumOfULongArgs")
    fun sumOfULongReturnsCorrectValue(
        array: Wrapper<Vector2IArray>, selector: (Vector2I) -> ULong, expected: Wrapper<ULong>
    ) = assertEquals(expected.value, array.value.sumOf(selector))

    @ParameterizedTest
    @MethodSource("sumOfVector2IArgs")
    fun sumOfReturnsCorrectValue(
        array: Wrapper<Vector2IArray>,
        selector: (Vector2I) -> Vector2I,
        expected: Wrapper<Vector2I>
    ) = assertEquals(expected.value, array.value.sumOf(selector))

    @ParameterizedTest
    @MethodSource("sumArgs")
    fun sumReturnsCorrectValue(array: Wrapper<Vector2IArray>, expected: Wrapper<Vector2I>) =
        assertEquals(expected.value, array.value.sum())

    @ParameterizedTest
    @MethodSource("containsArgs")
    fun containsReturnsCorrectValue(
        array: Wrapper<Vector2IArray>, element: Wrapper<Vector2I>, expected: Boolean
    ) = assertEquals(expected, array.value.contains(element.value))

    @ParameterizedTest
    @MethodSource("iteratorArgs")
    fun iteratorReturnsCorrectValue(
        array: Wrapper<Vector2IArray>, expected: Iterator<Vector2I>
    ) {
        val unwrappedArray: Vector2IArray = array.value
        val actual = unwrappedArray.iterator()

        for (i in 0..unwrappedArray.lastIndex) {
            assertEquals(expected.hasNext(), actual.hasNext())

            if (expected.hasNext() && actual.hasNext()) {
                assertEquals(expected.next(), actual.next())
            }
        }
        assertEquals(expected.hasNext(), actual.hasNext())
    }

    @Test
    fun getThrowsWhenIndexIsOutOfArrayBounds() {
        val array = Vector2IArray(4)

        assertThrows<IndexOutOfBoundsException> { array[-1] }
        assertThrows<IndexOutOfBoundsException> { array[4] }
    }

    @ParameterizedTest
    @MethodSource("getArgs")
    fun getReturnsCorrectValue(
        array: Wrapper<Vector2IArray>, index: Int, expected: Wrapper<Vector2I>
    ) = assertEquals(expected.value, array.value[index])

    @ParameterizedTest
    @MethodSource("setArgs")
    fun setMutatesArrayCorrectly(
        array: Wrapper<Vector2IArray>,
        index: Int,
        value: Wrapper<Vector2I>,
        expected: Wrapper<Vector2IArray>
    ) = assertContentEquals(expected.value, array.value.apply {
        this[index] = value.value
    })

    @ParameterizedTest
    @MethodSource("arrayPlusVector2IArgs")
    fun arrayPlusVector2IReturnsCorrectValue(
        array: Wrapper<Vector2IArray>, element: Wrapper<Vector2I>, expected: Wrapper<Vector2IArray>
    ) = assertContentEquals(expected.value, array.value + element.value)

    @ParameterizedTest
    @MethodSource("arrayPlusCollectionArgs")
    fun arrayPlusCollectionReturnsCorrectValue(
        array: Wrapper<Vector2IArray>,
        elements: Collection<Vector2I>,
        expected: Wrapper<Vector2IArray>
    ) = assertContentEquals(expected.value, array.value + elements)

    @ParameterizedTest
    @MethodSource("arrayPlusArrayArgs")
    fun arrayPlusArrayReturnsCorrectValue(
        array: Wrapper<Vector2IArray>,
        elements: Wrapper<Vector2IArray>,
        expected: Wrapper<Vector2IArray>
    ) = assertContentEquals(expected.value, array.value + elements.value)

    @ParameterizedTest
    @MethodSource("typedArrayToVector2IArrayArgs")
    fun typedArrayToVector2IArrayReturnsCorrectValue(
        array: Array<Vector2I>, expected: Wrapper<Vector2IArray>
    ) = assertContentEquals(expected.value, array.toVector2IArray())

    @ParameterizedTest
    @MethodSource("typedArraySumOfVector2IArgs")
    fun <T> typedArraySumOfReturnsCorrectValue(
        array: Array<T>, selector: (T) -> Vector2I, expected: Wrapper<Vector2I>
    ) = assertEquals(expected.value, array.sumOf(selector))

    @ParameterizedTest
    @MethodSource("typedArraySumArgs")
    fun typedArraySumReturnsCorrectValue(array: Array<Vector2I>, expected: Wrapper<Vector2I>) =
        assertEquals(expected.value, array.sum())

    companion object {
        @JvmStatic
        fun arrays(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2IArray(4) { Vector2I(it, -it) })
            ),
            Arguments.of(
                Wrapper(
                    arrayOf(Vector2I(-3, 5), Vector2I(-1, 7)).toVector2IArray()
                )
            ),
            Arguments.of(
                Wrapper(Vector2IArray(0))
            ),
        )

        @JvmStatic
        fun containsAllArgs(): List<Arguments> {
            val array = arrayOf(
                Vector2I(1, 0),
                Vector2I(-5, 8),
                Vector2I(2, -9),
            ).toVector2IArray()

            return listOf(
                Arguments.of(
                    Wrapper(array), array.toList(), true
                ),
                Arguments.of(
                    Wrapper(array), listOf(Vector2I(1, 0), Vector2I(-5, 8)), true
                ),
                Arguments.of(
                    Wrapper(array), emptyList<Vector2I>(), true
                ),
                Arguments.of(
                    Wrapper(array), listOf(Vector2I.ZERO), false
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
        fun findArgs(): List<Arguments> = firstOrNullPredicateArgs()

        @JvmStatic
        fun findLastArgs(): List<Arguments> = lastOrNullPredicateArgs()

        @JvmStatic
        fun firstArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(arrayOf(Vector2I(4, 5), Vector2I(1, 2)).toVector2IArray()),
                Wrapper(Vector2I(4, 5))
            ),
            Arguments.of(
                Wrapper(Vector2IArray(2) { Vector2I(it, 0) }),
                Wrapper(Vector2I(0, 0))
            ),
        )

        @JvmStatic
        fun firstPredicateArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(arrayOf(Vector2I(4, 5), Vector2I(1, 2)).toVector2IArray()),
                { v: Vector2I -> v == Vector2I(1, 2) },
                Wrapper(Vector2I(1, 2))
            ),
            Arguments.of(
                Wrapper(Vector2IArray(2) { Vector2I(it, 0) }),
                { v: Vector2I -> v.x >= 1 },
                Wrapper(Vector2I(1, 0))
            ),
        )

        @JvmStatic
        fun firstPredicateNotMatchArgs() = listOf(
            Arguments.of(
                Wrapper(arrayOf(Vector2I(4, 5), Vector2I(1, 2)).toVector2IArray()),
                { v: Vector2I -> v == Vector2I(4, 2) }),
            Arguments.of(
                Wrapper(Vector2IArray(2) { Vector2I(it, 0) }),
                { v: Vector2I -> v.x >= 2 }
            ),
        )

        @JvmStatic
        fun firstOrNullArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(arrayOf(Vector2I(4, 5), Vector2I(1, 2)).toVector2IArray()),
                Wrapper(Vector2I(4, 5))
            ),
            Arguments.of(
                Wrapper(Vector2IArray(2) { Vector2I(it, 0) }),
                Wrapper(Vector2I(0, 0))
            ),
            Arguments.of(Wrapper(Vector2IArray(0)), Wrapper<Vector2I?>(null)),
        )

        @JvmStatic
        fun firstOrNullPredicateArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(arrayOf(Vector2I(4, 5), Vector2I(1, 2)).toVector2IArray()),
                { v: Vector2I -> v == Vector2I(1, 2) },
                Wrapper(Vector2I(1, 2))
            ),
            Arguments.of(
                Wrapper(Vector2IArray(2) { Vector2I(it, 0) }),
                { v: Vector2I -> v.x >= 1 },
                Wrapper(Vector2I(1, 0))
            ),
            Arguments.of(
                Wrapper(Vector2IArray(2) { Vector2I(it, 0) }),
                { v: Vector2I -> v.x >= 2 },
                Wrapper<Vector2I?>(null)
            ),
            Arguments.of(
                Wrapper(Vector2IArray(0)),
                { _: Vector2I -> true },
                Wrapper<Vector2I?>(null)
            ),
        )

        @JvmStatic
        fun lastArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(arrayOf(Vector2I(4, 5), Vector2I(1, 2)).toVector2IArray()),
                Wrapper(Vector2I(1, 2))
            ),
            Arguments.of(
                Wrapper(Vector2IArray(2) { Vector2I(it, 0) }),
                Wrapper(Vector2I(1, 0))
            ),
        )

        @JvmStatic
        fun lastPredicateArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(arrayOf(Vector2I(4, 5), Vector2I(1, 2)).toVector2IArray()),
                { v: Vector2I -> v == Vector2I(1, 2) },
                Wrapper(Vector2I(1, 2))
            ),
            Arguments.of(
                Wrapper(Vector2IArray(2) { Vector2I(it, 0) }),
                { v: Vector2I -> v.x <= 1 },
                Wrapper(Vector2I(1, 0))
            ),
        )

        @JvmStatic
        fun lastPredicateNotMatchArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(arrayOf(Vector2I(4, 5), Vector2I(1, 2)).toVector2IArray()),
                { v: Vector2I -> v == Vector2I(4, 2) }),
            Arguments.of(
                Wrapper(Vector2IArray(2) { Vector2I(it, 0) }),
                { v: Vector2I -> v.x >= 2 }
            ),
        )

        @JvmStatic
        fun lastOrNullArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(arrayOf(Vector2I(4, 5), Vector2I(1, 2)).toVector2IArray()),
                Wrapper(Vector2I(1, 2))
            ),
            Arguments.of(
                Wrapper(Vector2IArray(2) { Vector2I(it, 0) }),
                Wrapper(Vector2I(1, 0))
            ),
            Arguments.of(Wrapper(Vector2IArray(0)), Wrapper<Vector2I?>(null)),
        )

        @JvmStatic
        fun lastOrNullPredicateArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(arrayOf(Vector2I(4, 5), Vector2I(1, 2)).toVector2IArray()),
                { v: Vector2I -> v == Vector2I(1, 2) },
                Wrapper(Vector2I(1, 2))
            ),
            Arguments.of(
                Wrapper(Vector2IArray(2) { Vector2I(it, 0) }),
                { v: Vector2I -> v.x <= 0 },
                Wrapper(Vector2I(0, 0))
            ),
            Arguments.of(
                Wrapper(Vector2IArray(2) { Vector2I(it, 0) }),
                { v: Vector2I -> v.x >= 2 },
                Wrapper<Vector2I?>(null)
            ),
            Arguments.of(
                Wrapper(Vector2IArray(0)),
                { _: Vector2I -> true },
                Wrapper<Vector2I?>(null)
            ),
        )

        @JvmStatic
        fun indexOfArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(arrayOf(Vector2I(4, 5), Vector2I(1, 2)).toVector2IArray()),
                Wrapper(Vector2I(1, 2)),
                1
            ),
            Arguments.of(
                Wrapper(arrayOf(Vector2I(4, 5), Vector2I(4, 5)).toVector2IArray()),
                Wrapper(Vector2I(4, 5)),
                0
            ),
            Arguments.of(
                Wrapper(arrayOf(Vector2I(4, 5), Vector2I(1, 2)).toVector2IArray()),
                Wrapper(Vector2I(7, 4)),
                -1
            ),
            Arguments.of(
                Wrapper(Vector2IArray(0)),
                Wrapper(Vector2I(7, 4)),
                -1
            ),
        )

        @JvmStatic
        fun indexOfFirstArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(arrayOf(Vector2I(4, 5), Vector2I(1, 2)).toVector2IArray()),
                { v: Vector2I -> v == Vector2I(1, 2) },
                1
            ),
            Arguments.of(
                Wrapper(Vector2IArray(2) { Vector2I(it, 0) }),
                { v: Vector2I -> v.x >= 2 },
                -1
            ),
            Arguments.of(
                Wrapper(Vector2IArray(0)),
                { _: Vector2I -> true },
                -1
            ),
        )

        @JvmStatic
        fun lastIndexOfArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(arrayOf(Vector2I(4, 5), Vector2I(1, 2)).toVector2IArray()),
                Wrapper(Vector2I(1, 2)),
                1
            ),
            Arguments.of(
                Wrapper(arrayOf(Vector2I(4, 5), Vector2I(4, 5)).toVector2IArray()),
                Wrapper(Vector2I(4, 5)),
                1
            ),
            Arguments.of(
                Wrapper(arrayOf(Vector2I(4, 5), Vector2I(1, 2)).toVector2IArray()),
                Wrapper(Vector2I(7, 4)),
                -1
            ),
            Arguments.of(
                Wrapper(Vector2IArray(0)),
                Wrapper(Vector2I(7, 4)),
                -1
            ),
        )

        @JvmStatic
        fun randomArgs(): List<Arguments> {
            val array = Array(10) { Vector2I(it, 0) }
            val seeds = intArrayOf(1234, 5678)
            val expectedVals = arrayOf(
                array.random(Random(seeds[0])).let { Wrapper(Vector2I(it.x, it.y)) },
                array.random(Random(seeds[1])).let { Wrapper(Vector2I(it.x, it.y)) },
            )
            return listOf(
                Arguments.of(Wrapper(array.toVector2IArray()), Random(seeds[0]), expectedVals[0]),
                Arguments.of(Wrapper(array.toVector2IArray()), Random(seeds[1]), expectedVals[1]),
            )
        }

        @JvmStatic
        fun randomOrNullArgs(): List<Arguments> {
            val array = Array(10) { Vector2I(it, 0) }
            val seeds = intArrayOf(1234, 5678)
            val expectedVals = arrayOf(
                array.random(Random(seeds[0])).let { Wrapper(Vector2I(it.x, it.y)) },
                array.random(Random(seeds[1])).let { Wrapper(Vector2I(it.x, it.y)) },
            )
            return listOf(
                Arguments.of(Wrapper(array.toVector2IArray()), Random(seeds[0]), expectedVals[0]),
                Arguments.of(Wrapper(array.toVector2IArray()), Random(seeds[1]), expectedVals[1]),
                Arguments.of(
                    Wrapper(Vector2IArray(0)),
                    Random(42),
                    Wrapper<Vector2I?>(null)
                ),
            )
        }

        @JvmStatic
        fun singleArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(arrayOf(Vector2I(1, 2)).toVector2IArray()),
                Wrapper(Vector2I(1, 2))
            ),
            Arguments.of(
                Wrapper(arrayOf(Vector2I.ZERO).toVector2IArray()),
                Wrapper(Vector2I(0, 0))
            ),
        )

        @JvmStatic
        fun singleThrowsExceptionArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2IArray(0)), NoSuchElementException::class.java
            ),
            Arguments.of(
                Wrapper(Vector2IArray(2) { Vector2I.ZERO }),
                IllegalArgumentException::class.java
            ),
        )

        @JvmStatic
        fun singlePredicateArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2IArray(3) { Vector2I(it, 0) }),
                { v: Vector2I -> v == Vector2I(1, 0) },
                Wrapper(Vector2I(1, 0))
            ),
            Arguments.of(
                Wrapper(Vector2IArray(3) { Vector2I(it, 0) }),
                { v: Vector2I -> v.x == 1 },
                Wrapper(Vector2I(1, 0))
            ),
        )

        @JvmStatic
        fun singlePredicateThrowsExceptionArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2IArray(0)),
                { v: Vector2I -> v == Vector2I.ZERO },
                NoSuchElementException::class.java
            ),
            Arguments.of(
                Wrapper(Vector2IArray(2) { Vector2I.ZERO }),
                { v: Vector2I -> v == Vector2I.ZERO },
                IllegalArgumentException::class.java
            ),
        )

        @JvmStatic
        fun singleOrNullArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(arrayOf(Vector2I(1, 2)).toVector2IArray()),
                Wrapper(Vector2I(1, 2))
            ),
            Arguments.of(
                Wrapper(arrayOf(Vector2I.ZERO).toVector2IArray()),
                Wrapper(Vector2I(0, 0))
            ),
            Arguments.of(
                Wrapper(Vector2IArray(0)), Wrapper<Vector2I?>(null)
            ),
            Arguments.of(
                Wrapper(Vector2IArray(2) { Vector2I.ZERO }), Wrapper<Vector2I?>(null)
            ),
        )

        @JvmStatic
        fun dropArgs(): List<Arguments> {
            val array = Vector2IArray(4) { Vector2I(it, 0) }

            return listOf(
                Arguments.of(
                    Wrapper(array), 0, List(4) { Vector2I(it, 0) }
                ),
                Arguments.of(
                    Wrapper(array), 2, listOf(Vector2I(2, 0), Vector2I(3, 0))
                ),
                Arguments.of(
                    Wrapper(array), 5, emptyList<Vector2I>()
                ),
            )
        }

        @JvmStatic
        fun dropLastArgs(): List<Arguments> {
            val array = Vector2IArray(4) { Vector2I(it, 0) }

            return listOf(
                Arguments.of(
                    Wrapper(array), 0, List(4) { Vector2I(it, 0) }
                ),
                Arguments.of(
                    Wrapper(array), 2, listOf(Vector2I(0, 0), Vector2I(1, 0))
                ),
                Arguments.of(
                    Wrapper(array), 5, emptyList<Vector2I>()
                ),
            )
        }

        @JvmStatic
        fun dropWhileArgs(): List<Arguments> {
            val array = Vector2IArray(4) { Vector2I(it, 0) }

            return listOf(
                Arguments.of(
                    Wrapper(Vector2IArray(0)),
                    { _: Vector2I -> false },
                    emptyList<Vector2I>(),
                ),
                Arguments.of(
                    Wrapper(array),
                    { _: Vector2I -> false },
                    List(4) { Vector2I(it, 0) }
                ),
                Arguments.of(
                    Wrapper(array),
                    { v: Vector2I -> v.x != 2 },
                    listOf(Vector2I(2, 0), Vector2I(3, 0))
                ),
                Arguments.of(
                    Wrapper(array), { _: Vector2I -> true }, emptyList<Vector2I>()
                ),
            )
        }

        @JvmStatic
        fun dropLastWhileArgs(): List<Arguments> {
            val array = Vector2IArray(4) { Vector2I(it, 0) }

            return listOf(
                Arguments.of(
                    Wrapper(Vector2IArray(0)),
                    { _: Vector2I -> false },
                    emptyList<Vector2I>(),
                ),
                Arguments.of(
                    Wrapper(array),
                    { _: Vector2I -> false },
                    List(4) { Vector2I(it, 0) }
                ),
                Arguments.of(
                    Wrapper(array),
                    { v: Vector2I -> v.x != 1 },
                    listOf(Vector2I(0, 0), Vector2I(1, 0))
                ),
                Arguments.of(
                    Wrapper(array), { _: Vector2I -> true }, emptyList<Vector2I>()
                ),
            )
        }

        @JvmStatic
        fun sliceRangeArgs(): List<Arguments> {
            val array = Vector2IArray(3) { Vector2I(it, 0) }

            return listOf(
                Arguments.of(
                    Wrapper(array),
                    0..2,
                    listOf(Vector2I(0, 0), Vector2I(1, 0), Vector2I(2, 0)),
                ),
                Arguments.of(
                    Wrapper(array),
                    1..2,
                    listOf(Vector2I(1, 0), Vector2I(2, 0)),
                ),
                Arguments.of(
                    Wrapper(array),
                    IntRange.EMPTY,
                    emptyList<Vector2I>(),
                ),
            )
        }

        @JvmStatic
        fun sliceRangeThrowsExceptionArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2IArray(3) { Vector2I.ZERO }),
                0..3,
                IndexOutOfBoundsException::class.java
            ),
            Arguments.of(
                Wrapper(Vector2IArray(3) { Vector2I.ZERO }),
                -1..2,
                IndexOutOfBoundsException::class.java
            ),
        )

        @JvmStatic
        fun sliceIterableArgs(): List<Arguments> {
            val array = Vector2IArray(3) { Vector2I(it, 0) }

            return listOf(
                Arguments.of(
                    Wrapper(array),
                    listOf(0, 1, 2),
                    listOf(Vector2I(0, 0), Vector2I(1, 0), Vector2I(2, 0)),
                ),
                Arguments.of(
                    Wrapper(array),
                    listOf(0, 2),
                    listOf(Vector2I(0, 0), Vector2I(2, 0)),
                ),
                Arguments.of(
                    Wrapper(array),
                    emptyList<Int>(),
                    emptyList<Vector2I>(),
                ),
            )
        }

        @JvmStatic
        fun sliceIterableThrowsExceptionArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2IArray(3) { Vector2I.ZERO }),
                listOf(-1, 0, 1),
                IndexOutOfBoundsException::class.java
            ),
            Arguments.of(
                Wrapper(Vector2IArray(3) { Vector2I.ZERO }),
                listOf(0, 1, 3),
                IndexOutOfBoundsException::class.java
            ),
        )

        @JvmStatic
        fun sliceArrayRangeArgs(): List<Arguments> {
            val array = Vector2IArray(3) { Vector2I(it, 0) }

            return listOf(
                Arguments.of(
                    Wrapper(array),
                    0..2,
                    Wrapper(
                        arrayOf(
                            Vector2I(0, 0), Vector2I(1, 0), Vector2I(2, 0)
                        ).toVector2IArray()
                    ),
                ),
                Arguments.of(
                    Wrapper(array),
                    1..2,
                    Wrapper(
                        arrayOf(Vector2I(1, 0), Vector2I(2, 0)).toVector2IArray()
                    ),
                ),
                Arguments.of(
                    Wrapper(array),
                    IntRange.EMPTY,
                    Wrapper(Vector2IArray(0)),
                ),
            )
        }

        @JvmStatic
        fun sliceArrayRangeThrowsExceptionArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2IArray(3) { Vector2I.ZERO }),
                0..3,
                IndexOutOfBoundsException::class.java
            ),
            Arguments.of(
                Wrapper(Vector2IArray(3) { Vector2I.ZERO }),
                -1..2,
                IndexOutOfBoundsException::class.java
            ),
        )

        @JvmStatic
        fun sliceArrayCollectionArgs(): List<Arguments> {
            val array = Vector2IArray(3) { Vector2I(it, 0) }

            return listOf(
                Arguments.of(
                    Wrapper(array),
                    listOf(0, 1, 2),
                    Wrapper(
                        arrayOf(
                            Vector2I(0, 0), Vector2I(1, 0), Vector2I(2, 0)
                        ).toVector2IArray()
                    ),
                ),
                Arguments.of(
                    Wrapper(array),
                    listOf(0, 2),
                    Wrapper(
                        arrayOf(Vector2I(0, 0), Vector2I(2, 0)).toVector2IArray()
                    ),
                ),
                Arguments.of(
                    Wrapper(array),
                    emptyList<Int>(),
                    Wrapper(Vector2IArray(0)),
                ),
            )
        }

        @JvmStatic
        fun sliceArrayCollectionThrowsExceptionArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2IArray(3) { Vector2I.ZERO }),
                listOf(-1, 0, 1),
                IndexOutOfBoundsException::class.java
            ),
            Arguments.of(
                Wrapper(Vector2IArray(3) { Vector2I.ZERO }),
                listOf(0, 1, 3),
                IndexOutOfBoundsException::class.java
            ),
        )

        @JvmStatic
        fun takeArgs(): List<Arguments> {
            val array = Vector2IArray(4) { Vector2I(it, 0) }

            return listOf(
                Arguments.of(
                    Wrapper(array), 0, emptyList<Vector2I>()
                ),
                Arguments.of(
                    Wrapper(array), 2, listOf(Vector2I(0, 0), Vector2I(1, 0))
                ),
                Arguments.of(
                    Wrapper(array), 5, List(4) { Vector2I(it, 0) }
                ),
            )
        }

        @JvmStatic
        fun takeLastArgs(): List<Arguments> {
            val array = Vector2IArray(4) { Vector2I(it, 0) }

            return listOf(
                Arguments.of(
                    Wrapper(array), 0, emptyList<Vector2I>()
                ),
                Arguments.of(
                    Wrapper(array), 2, listOf(Vector2I(2, 0), Vector2I(3, 0))
                ),
                Arguments.of(
                    Wrapper(array), 5, List(4) { Vector2I(it, 0) }
                ),
            )
        }

        @JvmStatic
        fun takeWhileArgs(): List<Arguments> {
            val array = Vector2IArray(4) { Vector2I(it, 0) }

            return listOf(
                Arguments.of(
                    Wrapper(Vector2IArray(0)), { _: Vector2I -> true }, emptyList<Vector2I>(),
                ),
                Arguments.of(
                    Wrapper(array), { _: Vector2I -> false }, emptyList<Vector2I>()
                ),
                Arguments.of(
                    Wrapper(array),
                    { v: Vector2I -> v.x != 2 },
                    listOf(Vector2I(0, 0), Vector2I(1, 0))
                ),
                Arguments.of(
                    Wrapper(array),
                    { _: Vector2I -> true },
                    List(4) { Vector2I(it, 0) }
                ),
            )
        }

        @JvmStatic
        fun takeLastWhileArgs(): List<Arguments> {
            val array = Vector2IArray(4) { Vector2I(it, 0) }

            return listOf(
                Arguments.of(
                    Wrapper(Vector2IArray(0)), { _: Vector2I -> true }, emptyList<Vector2I>(),
                ),
                Arguments.of(
                    Wrapper(array), { _: Vector2I -> false }, emptyList<Vector2I>()
                ),
                Arguments.of(
                    Wrapper(array),
                    { v: Vector2I -> v.x != 1 },
                    listOf(Vector2I(2, 0), Vector2I(3, 0))
                ),
                Arguments.of(
                    Wrapper(array),
                    { _: Vector2I -> true },
                    List(4) { Vector2I(it, 0) }
                ),
            )
        }

        @JvmStatic
        fun reverseArgs(): List<Arguments> = listOf(
            Arguments.of(Wrapper(Vector2IArray(0)), Wrapper(Vector2IArray(0))),
            Arguments.of(
                Wrapper(
                    arrayOf(
                        Vector2I(1, 2), Vector2I(3, 4), Vector2I(5, 6)
                    ).toVector2IArray()
                ),
                Wrapper(
                    arrayOf(
                        Vector2I(5, 6), Vector2I(3, 4), Vector2I(1, 2)
                    ).toVector2IArray()
                )
            ),
            Arguments.of(
                Wrapper(arrayOf(Vector2I(1, 2), Vector2I(3, 4)).toVector2IArray()),
                Wrapper(arrayOf(Vector2I(3, 4), Vector2I(1, 2)).toVector2IArray())
            ),
        )

        @JvmStatic
        fun reverseRangeArgs(): List<Arguments> = listOf(
            Arguments.of(Wrapper(Vector2IArray(0)), 0, 0, Wrapper(Vector2IArray(0))),
            Arguments.of(
                Wrapper(
                    arrayOf(
                        Vector2I(1, 2), Vector2I(3, 4), Vector2I(5, 6)
                    ).toVector2IArray()
                ),
                1, 3,
                Wrapper(
                    arrayOf(
                        Vector2I(1, 2), Vector2I(5, 6), Vector2I(3, 4)
                    ).toVector2IArray()
                )
            ),
            Arguments.of(
                Wrapper(arrayOf(Vector2I(1, 2), Vector2I(3, 4)).toVector2IArray()),
                0, 2,
                Wrapper(arrayOf(Vector2I(3, 4), Vector2I(1, 2)).toVector2IArray())
            ),
        )

        @JvmStatic
        fun reverseRangeThrowsExceptionArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2IArray(3) { Vector2I.ZERO }),
                0, 4,
                IndexOutOfBoundsException::class.java
            ),
            Arguments.of(
                Wrapper(Vector2IArray(3) { Vector2I.ZERO }),
                -1, 2,
                IndexOutOfBoundsException::class.java
            ),
            Arguments.of(
                Wrapper(Vector2IArray(3) { Vector2I.ZERO }),
                2, 1,
                IllegalArgumentException::class.java
            ),
        )

        @JvmStatic
        fun reversedArgs(): List<Arguments> = listOf(
            Arguments.of(Wrapper(Vector2IArray(0)), emptyList<Vector2I>()),
            Arguments.of(
                Wrapper(
                    arrayOf(
                        Vector2I(1, 2), Vector2I(3, 4), Vector2I(5, 6)
                    ).toVector2IArray()
                ),
                listOf(Vector2I(5, 6), Vector2I(3, 4), Vector2I(1, 2))
            ),
            Arguments.of(
                Wrapper(arrayOf(Vector2I(1, 2), Vector2I(3, 4)).toVector2IArray()),
                listOf(Vector2I(3, 4), Vector2I(1, 2))
            ),
        )

        @JvmStatic
        fun reversedArrayArgs(): List<Arguments> = reverseArgs()

        @JvmStatic
        fun shuffleArgs(): List<Arguments> {
            val array = Vector2IArray(10) { Vector2I(it, 0) }
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
        fun sortedByArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(
                    arrayOf(
                        Vector2I(4, 2), Vector2I(7, 3), Vector2I(3, 1),
                    ).toVector2IArray()
                ),
                { v: Vector2I -> v.x },
                listOf(Vector2I(3, 1), Vector2I(4, 2), Vector2I(7, 3))
            ),
            Arguments.of(
                Wrapper(Vector2IArray(0)),
                { v: Vector2I -> v.x },
                emptyList<Vector2I>()
            ),
        )

        @JvmStatic
        fun sortedByDescendingArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(
                    arrayOf(
                        Vector2I(4, 2), Vector2I(7, 3), Vector2I(3, 1),
                    ).toVector2IArray()
                ),
                { v: Vector2I -> v.y },
                listOf(Vector2I(7, 3), Vector2I(4, 2), Vector2I(3, 1))
            ),
            Arguments.of(
                Wrapper(Vector2IArray(0)),
                { v: Vector2I -> v.y },
                emptyList<Vector2I>()
            ),
        )

        @JvmStatic
        fun sortedWithArgs(): List<Arguments> {
            val comparator = Comparator<Vector2I> { a, b ->
                val xToX = a.x.compareTo(b.x)

                if (xToX == 0) a.y.compareTo(b.y) else xToX
            }
            return listOf(
                Arguments.of(
                    Wrapper(
                        arrayOf(
                            Vector2I(4, 3), Vector2I(4, 2), Vector2I(3, 1),
                        ).toVector2IArray()
                    ),
                    comparator,
                    listOf(Vector2I(3, 1), Vector2I(4, 2), Vector2I(4, 3))
                ),
                Arguments.of(
                    Wrapper(Vector2IArray(0)),
                    comparator,
                    emptyList<Vector2I>()
                ),
            )
        }

        @JvmStatic
        fun asListArgs(): List<Arguments> = listOf(
            Arguments.of(Wrapper(Vector2IArray(0)), emptyList<Vector2I>()),
            Arguments.of(
                Wrapper(Vector2IArray(4) { Vector2I(it, 0) }),
                List(4) { Vector2I(it, 0) }
            ),
        )

        @JvmStatic
        fun copyIntoArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2IArray(4) { Vector2I(it, 0) }),
                Wrapper(Vector2IArray(4) { Vector2I.ZERO }),
                0, 0, 4,
                Wrapper(Vector2IArray(4) { Vector2I(it, 0) })
            ),
            Arguments.of(
                Wrapper(Vector2IArray(4) { Vector2I(it, 0) }),
                Wrapper(Vector2IArray(5) { Vector2I.ZERO }),
                3, 1, 3,
                Wrapper(
                    arrayOf(
                        Vector2I(0, 0),
                        Vector2I(0, 0),
                        Vector2I(0, 0),
                        Vector2I(1, 0),
                        Vector2I(2, 0),
                    ).toVector2IArray()
                )
            ),
            Arguments.of(
                Wrapper(Vector2IArray(0)),
                Wrapper(Vector2IArray(4) { Vector2I.ZERO }),
                0, 0, 0,
                Wrapper(Vector2IArray(4) { Vector2I.ZERO }),
            ),
        )

        @JvmStatic
        fun copyIntoThrowsExceptionArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2IArray(4) { Vector2I.ZERO }),
                Wrapper(Vector2IArray(4) { Vector2I.ZERO }),
                0, 0, 5,
                IndexOutOfBoundsException::class.java
            ),
            Arguments.of(
                Wrapper(Vector2IArray(4) { Vector2I.ZERO }),
                Wrapper(Vector2IArray(4) { Vector2I.ZERO }),
                0, -1, 4,
                IndexOutOfBoundsException::class.java
            ),
            Arguments.of(
                Wrapper(Vector2IArray(4) { Vector2I.ZERO }),
                Wrapper(Vector2IArray(4) { Vector2I.ZERO }),
                0, 2, 1,
                IndexOutOfBoundsException::class.java
            ),
            Arguments.of(
                Wrapper(Vector2IArray(4) { Vector2I.ZERO }),
                Wrapper(Vector2IArray(4) { Vector2I.ZERO }),
                1, 0, 4,
                IndexOutOfBoundsException::class.java
            ),
            Arguments.of(
                Wrapper(Vector2IArray(4) { Vector2I.ZERO }),
                Wrapper(Vector2IArray(4) { Vector2I.ZERO }),
                4, 0, 1,
                IndexOutOfBoundsException::class.java
            ),
        )

        @JvmStatic
        fun copyOfArgs(): List<Arguments> = listOf(
            Arguments.of(Wrapper(Vector2IArray(0)), Wrapper(Vector2IArray(0))),
            Arguments.of(
                Wrapper(Vector2IArray(4) { Vector2I(it, 0) }),
                Wrapper(Vector2IArray(4) { Vector2I(it, 0) })
            ),
        )

        @JvmStatic
        fun copyOfSizeArgs(): List<Arguments> = listOf(
            Arguments.of(Wrapper(Vector2IArray(0)), 0, Wrapper(Vector2IArray(0))),
            Arguments.of(
                Wrapper(Vector2IArray(0)),
                2,
                Wrapper(Vector2IArray(2) { Vector2I.ZERO })
            ),
            Arguments.of(
                Wrapper(Vector2IArray(4) { Vector2I(it, 0) }),
                3,
                Wrapper(Vector2IArray(3) { Vector2I(it, 0) })
            ),
            Arguments.of(
                Wrapper(Vector2IArray(4) { Vector2I(it, 0) }),
                5,
                Wrapper(
                    arrayOf(
                        Vector2I(0, 0),
                        Vector2I(1, 0),
                        Vector2I(2, 0),
                        Vector2I(3, 0),
                        Vector2I(0, 0),
                    ).toVector2IArray()
                )
            ),
        )

        @JvmStatic
        fun copyOfRangeArgs(): List<Arguments> = listOf(
            Arguments.of(Wrapper(Vector2IArray(0)), 0, 0, Wrapper(Vector2IArray(0))),
            Arguments.of(
                Wrapper(Vector2IArray(4) { Vector2I(it, 0) }),
                0, 4,
                Wrapper(Vector2IArray(4) { Vector2I(it, 0) })
            ),
            Arguments.of(
                Wrapper(Vector2IArray(4) { Vector2I(it, 0) }),
                1, 3,
                Wrapper(arrayOf(Vector2I(1, 0), Vector2I(2, 0)).toVector2IArray())
            ),
        )

        @JvmStatic
        fun copyOfRangeThrowsExceptionArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2IArray(0)), 0, 1, IndexOutOfBoundsException::class.java
            ),
            Arguments.of(
                Wrapper(Vector2IArray(4) { Vector2I(it, 0) }),
                0, 5,
                IndexOutOfBoundsException::class.java
            ),
            Arguments.of(
                Wrapper(Vector2IArray(4) { Vector2I(it, 0) }),
                -1, 4,
                IndexOutOfBoundsException::class.java
            ),
            Arguments.of(
                Wrapper(Vector2IArray(4) { Vector2I(it, 0) }),
                2, 1,
                IllegalArgumentException::class.java
            ),
        )

        @JvmStatic
        fun fillArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2IArray(4) { Vector2I.ZERO }),
                Wrapper(Vector2I(1, 2)),
                0, 4,
                Wrapper(Vector2IArray(4) { Vector2I(1, 2) })
            ),
            Arguments.of(
                Wrapper(Vector2IArray(4) { Vector2I.ZERO }),
                Wrapper(Vector2I(1, 2)),
                1, 3,
                Wrapper(
                    arrayOf(
                        Vector2I.ZERO,
                        Vector2I(1, 2),
                        Vector2I(1, 2),
                        Vector2I.ZERO
                    ).toVector2IArray()
                )
            ),
            Arguments.of(
                Wrapper(Vector2IArray(4) { Vector2I.ZERO }),
                Wrapper(Vector2I(1, 2)),
                0, 0,
                Wrapper(Vector2IArray(4) { Vector2I.ZERO })
            ),
            Arguments.of(
                Wrapper(Vector2IArray(0)),
                Wrapper(Vector2I(1, 2)),
                0, 0,
                Wrapper(Vector2IArray(0)),
            ),
        )

        @JvmStatic
        fun fillThrowsExceptionArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2IArray(4) { Vector2I.ZERO }),
                Wrapper(Vector2I(1, 2)),
                0, 5,
                IndexOutOfBoundsException::class.java
            ),
            Arguments.of(
                Wrapper(Vector2IArray(4) { Vector2I.ZERO }),
                Wrapper(Vector2I(1, 2)),
                -1, 4,
                IndexOutOfBoundsException::class.java
            ),
            Arguments.of(
                Wrapper(Vector2IArray(4) { Vector2I.ZERO }),
                Wrapper(Vector2I(1, 2)),
                2, 1,
                IllegalArgumentException::class.java
            ),
        )

        @JvmStatic
        fun isEmptyArgs(): List<Arguments> = listOf(
            Arguments.of(Wrapper(Vector2IArray(4) { Vector2I.ZERO }), false),
            Arguments.of(Wrapper(Vector2IArray(0)), true),
        )

        @JvmStatic
        fun isNotEmptyArgs(): List<Arguments> = listOf(
            Arguments.of(Wrapper(Vector2IArray(4) { Vector2I.ZERO }), true),
            Arguments.of(Wrapper(Vector2IArray(0)), false),
        )

        @JvmStatic
        fun toTypedArrayArgs(): List<Arguments> = listOf(
            Arguments.of(Wrapper(Vector2IArray(0)), emptyArray<Vector2I>()),
            Arguments.of(
                Wrapper(Vector2IArray(4) { Vector2I(it, 0) }),
                Array(4) { Vector2I(it, 0) }
            ),
        )

        @JvmStatic
        fun noneArgs(): List<Arguments> = isEmptyArgs()

        @JvmStatic
        fun sumOfDoubleArgs(): List<Arguments> {
            val array = arrayOf(
                Vector2I(1, 2), Vector2I(-2, -1), Vector2I(10, 10)
            ).toVector2IArray()

            return listOf(
                Arguments.of(
                    Wrapper(array), { v: Vector2I -> v.x.toDouble() + v.y.toDouble() }, 20.0
                ),
                Arguments.of(
                    Wrapper(array),
                    { v: Vector2I -> v.x.toDouble().absoluteValue + v.y.toDouble().absoluteValue },
                    26.0
                ),
            )
        }

        @JvmStatic
        fun sumOfIntArgs(): List<Arguments> {
            val array = arrayOf(
                Vector2I(1, 2), Vector2I(-2, -1), Vector2I(10, 10)
            ).toVector2IArray()

            return listOf(
                Arguments.of(
                    Wrapper(array), { v: Vector2I -> v.x + v.y }, 20
                ),
                Arguments.of(
                    Wrapper(array),
                    { v: Vector2I -> v.x.absoluteValue + v.y.absoluteValue },
                    26
                ),
            )
        }

        @JvmStatic
        fun sumOfUIntArgs(): List<Arguments> {
            val array = arrayOf(
                Vector2I(3, 2), Vector2I(2, 1), Vector2I(10, 10)
            ).toVector2IArray()

            return listOf(
                Arguments.of(
                    Wrapper(array), { v: Vector2I -> v.x.toUInt() + v.y.toUInt() },
                    Wrapper(28u)
                ),
                Arguments.of(
                    Wrapper(array), { v: Vector2I -> v.x.toUInt() - v.y.toUInt() },
                    Wrapper(2u)
                ),
            )
        }

        @JvmStatic
        fun sumOfLongArgs(): List<Arguments> {
            val array = arrayOf(
                Vector2I(1, 2), Vector2I(-2, -1), Vector2I(10, 10)
            ).toVector2IArray()

            return listOf(
                Arguments.of(
                    Wrapper(array), { v: Vector2I -> v.x.toLong() + v.y.toLong() }, 20L
                ),
                Arguments.of(
                    Wrapper(array),
                    { v: Vector2I -> v.x.toLong().absoluteValue + v.y.toLong().absoluteValue },
                    26L
                ),
            )
        }

        @JvmStatic
        fun sumOfULongArgs(): List<Arguments> {
            val array = arrayOf(
                Vector2I(3, 2), Vector2I(2, 1), Vector2I(10, 10)
            ).toVector2IArray()

            return listOf(
                Arguments.of(
                    Wrapper(array),
                    { v: Vector2I -> v.x.toULong() + v.y.toULong() },
                    Wrapper(28uL)
                ),
                Arguments.of(
                    Wrapper(array),
                    { v: Vector2I -> v.x.toULong() - v.y.toULong() },
                    Wrapper(2uL)
                ),
            )
        }

        @JvmStatic
        fun sumOfVector2IArgs(): List<Arguments> {
            val array = arrayOf(
                Vector2I(1, 2), Vector2I(-2, -1), Vector2I(10, 10)
            ).toVector2IArray()

            return listOf(
                Arguments.of(
                    Wrapper(array), { v: Vector2I -> v }, Wrapper(Vector2I(9, 11))
                ),
                Arguments.of(
                    Wrapper(array),
                    { v: Vector2I -> Vector2I(v.x.absoluteValue, v.y.absoluteValue) },
                    Wrapper(Vector2I(13, 13))
                ),
            )
        }

        @JvmStatic
        fun sumArgs(): List<Arguments> = listOf(
            Arguments.of(Wrapper(Vector2IArray(0)), Wrapper(Vector2I(0, 0))),
            Arguments.of(
                Wrapper(
                    arrayOf(
                        Vector2I(1, 2),
                        Vector2I(-2, -1),
                        Vector2I(10, 10)
                    ).toVector2IArray()
                ),
                Wrapper(Vector2I(9, 11))
            ),
        )

        @JvmStatic
        fun containsArgs(): List<Arguments> {
            val array = arrayOf(
                Vector2I(1, 0),
                Vector2I(-5, 8),
                Vector2I(2, -9),
                Vector2I(3, 4)
            ).toVector2IArray()

            return listOf(
                Arguments.of(Wrapper(array), Wrapper(Vector2I(0, 0)), false),
                Arguments.of(Wrapper(array), Wrapper(Vector2I(3, 4)), true),
            )
        }

        @JvmStatic
        fun iteratorArgs(): List<Arguments> {
            val array = Vector2IArray(4) { Vector2I(it, 0) }

            return listOf(
                Arguments.of(Wrapper(array), array.iterator()),
                Arguments.of(Wrapper(Vector2IArray(0)), Vector2IArray(0).iterator()),
            )
        }

        @JvmStatic
        fun getArgs(): List<Arguments> {
            val array = arrayOf(
                Vector2I(1, 0),
                Vector2I(-5, 8),
                Vector2I(2, -9),
                Vector2I(3, 4),
            ).toVector2IArray()

            return listOf(
                Arguments.of(Wrapper(array), 0, Wrapper(Vector2I(1, 0))),
                Arguments.of(Wrapper(array), 3, Wrapper(Vector2I(3, 4))),
            )
        }

        @JvmStatic
        fun getOrElseArgs(): List<Arguments> {
            val array = arrayOf(
                Vector2I(1, 0),
                Vector2I(-5, 8),
                Vector2I(3, 4),
            ).toVector2IArray()

            return listOf(
                Arguments.of(
                    Wrapper(array),
                    0,
                    { i: Int -> Vector2I(i, i) },
                    Wrapper(Vector2I(1, 0))
                ),
                Arguments.of(
                    Wrapper(array),
                    -1,
                    { i: Int -> Vector2I(i, i) },
                    Wrapper(Vector2I(-1, -1))
                ),
                Arguments.of(
                    Wrapper(array),
                    3,
                    { i: Int -> Vector2I(i, i) },
                    Wrapper(Vector2I(3, 3))
                ),
            )
        }

        @JvmStatic
        fun getOrNullArgs(): List<Arguments> {
            val array = arrayOf(
                Vector2I(1, 0),
                Vector2I(-5, 8),
                Vector2I(3, 4),
            ).toVector2IArray()

            return listOf(
                Arguments.of(Wrapper(array), 0, Wrapper(Vector2I(1, 0))),
                Arguments.of(Wrapper(array), -1, Wrapper<Vector2I?>(null)),
                Arguments.of(Wrapper(array), 3, Wrapper<Vector2I?>(null)),
            )
        }

        @JvmStatic
        fun setArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2IArray(4) { Vector2I.ZERO }),
                0,
                Wrapper(Vector2I(1, 0)),
                Wrapper(
                    arrayOf(Vector2I(1, 0), Vector2I.ZERO, Vector2I.ZERO, Vector2I.ZERO)
                        .toVector2IArray()
                ),
            ),
            Arguments.of(
                Wrapper(Vector2IArray(4) { Vector2I.ZERO }),
                3,
                Wrapper(Vector2I(3, 4)),
                Wrapper(
                    arrayOf(Vector2I.ZERO, Vector2I.ZERO, Vector2I.ZERO, Vector2I(3, 4))
                        .toVector2IArray()
                )
            ),
        )

        @JvmStatic
        fun arrayPlusVector2IArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2IArray(0)),
                Wrapper(Vector2I(1, 0)),
                Wrapper(arrayOf(Vector2I(1, 0)).toVector2IArray())
            ),
            Arguments.of(
                Wrapper(arrayOf(Vector2I(1, 0), Vector2I(2, 0)).toVector2IArray()),
                Wrapper(Vector2I(3, 0)),
                Wrapper(
                    arrayOf(Vector2I(1, 0), Vector2I(2, 0), Vector2I(3, 0))
                        .toVector2IArray()
                ),
            ),
        )

        @JvmStatic
        fun arrayPlusCollectionArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2IArray(0)),
                listOf(Vector2I(1, 0)),
                Wrapper(arrayOf(Vector2I(1, 0)).toVector2IArray()),
            ),
            Arguments.of(
                Wrapper(arrayOf(Vector2I(1, 0), Vector2I(2, 0)).toVector2IArray()),
                listOf(Vector2I(3, 0), Vector2I(4, 0)),
                Wrapper(
                    arrayOf(
                        Vector2I(1, 0),
                        Vector2I(2, 0),
                        Vector2I(3, 0),
                        Vector2I(4, 0)
                    ).toVector2IArray()
                ),
            ),
        )

        @JvmStatic
        fun arrayPlusArrayArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2IArray(0)),
                Wrapper(arrayOf(Vector2I(1, 0)).toVector2IArray()),
                Wrapper(arrayOf(Vector2I(1, 0)).toVector2IArray()),
            ),
            Arguments.of(
                Wrapper(arrayOf(Vector2I(1, 0), Vector2I(2, 0)).toVector2IArray()),
                Wrapper(arrayOf(Vector2I(3, 0), Vector2I(4, 0)).toVector2IArray()),
                Wrapper(
                    arrayOf(
                        Vector2I(1, 0),
                        Vector2I(2, 0),
                        Vector2I(3, 0),
                        Vector2I(4, 0)
                    ).toVector2IArray()
                ),
            ),
        )

        @JvmStatic
        fun typedArrayToVector2IArrayArgs(): List<Arguments> = listOf(
            Arguments.of(emptyArray<Vector2I>(), Wrapper(Vector2IArray(0))),
            Arguments.of(
                Array(4) { Vector2I(it, 0) },
                Wrapper(Vector2IArray(4) { Vector2I(it, 0) })
            ),
        )

        @JvmStatic
        fun typedArraySumOfVector2IArgs(): List<Arguments> {
            val array = arrayOf(
                Vector2I(1, 2), Vector2I(-2, -1), Vector2I(10, 10)
            )
            return listOf(
                Arguments.of(
                    array, { v: Vector2I -> v }, Wrapper(Vector2I(9, 11))
                ),
                Arguments.of(
                    array,
                    { v: Vector2I -> Vector2I(v.x.absoluteValue, v.y.absoluteValue) },
                    Wrapper(Vector2I(13, 13))
                ),
            )
        }

        @JvmStatic
        fun typedArraySumArgs(): List<Arguments> = listOf(
            Arguments.of(emptyArray<Vector2I>(), Wrapper(Vector2I(0, 0))),
            Arguments.of(
                arrayOf(
                    Vector2I(1, 2), Vector2I(-2, -1), Vector2I(10, 10)
                ),
                Wrapper(Vector2I(9, 11))
            ),
        )
    }
}