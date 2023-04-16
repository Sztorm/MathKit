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
    @MethodSource("containsArgs")
    fun containsReturnsCorrectValue(
        array: Array<Vector2I>, element: Pair<Int, Int>, expected: Boolean
    ) {
        val actual: Boolean = array
            .toVector2IArray().contains(Vector2I(element.first, element.second))

        assertEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("containsAllArgs")
    fun containsAllReturnsCorrectValue(
        array: Array<Vector2I>, elements: Collection<Vector2I>, expected: Boolean
    ) {
        val actual: Boolean = array.toVector2IArray().containsAll(elements)

        assertEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("elementAtArgs")
    fun elementAtReturnsCorrectValue(
        array: Array<Vector2I>, index: Int, exp: Pair<Int, Int>
    ) {
        val expected = Vector2I(exp.first, exp.second)
        val actual: Vector2I = array.toVector2IArray().elementAt(index)

        assertEquals(expected, actual)
    }

    @Test
    fun elementAtThrowsWhenIndexIsOutOfBounds() {
        val array = Vector2IArray(4)

        assertThrows<IndexOutOfBoundsException> { array.elementAt(-1) }
        assertThrows<IndexOutOfBoundsException> { array.elementAt(4) }
    }

    @ParameterizedTest
    @MethodSource("elementAtOrElseArgs")
    fun elementAtOrElseReturnsCorrectValue(
        array: Array<Vector2I>,
        index: Int,
        defaultValue: (Int) -> Vector2I,
        exp: Pair<Int, Int>
    ) {
        val expected = Vector2I(exp.first, exp.second)
        val actual: Vector2I = array.toVector2IArray().elementAtOrElse(index, defaultValue)

        assertEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("elementAtOrNullArgs")
    fun elementAtOrNullReturnsCorrectValue(
        array: Array<Vector2I>,
        index: Int,
        exp: Pair<Int, Int>?
    ) {
        val expected: Vector2I? = exp?.let { Vector2I(exp.first, exp.second) }
        val actual: Vector2I? = array.toVector2IArray().elementAtOrNull(index)

        assertEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("findArgs")
    fun findReturnsCorrectValue(
        array: Array<Vector2I>,
        predicate: (Vector2I) -> Boolean,
        exp: Pair<Int, Int>?
    ) {
        val expected: Vector2I? = exp?.let { Vector2I(exp.first, exp.second) }
        val actual: Vector2I? = array.toVector2IArray().find(predicate)

        assertEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("findLastArgs")
    fun findLastReturnsCorrectValue(
        array: Array<Vector2I>,
        predicate: (Vector2I) -> Boolean,
        exp: Pair<Int, Int>?
    ) {
        val expected: Vector2I? = exp?.let { Vector2I(exp.first, exp.second) }
        val actual: Vector2I? = array.toVector2IArray().findLast(predicate)

        assertEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("firstArgs")
    fun firstReturnsCorrectValue(array: Array<Vector2I>, exp: Pair<Int, Int>) {
        val expected = Vector2I(exp.first, exp.second)
        val actual: Vector2I = array.toVector2IArray().first()

        assertEquals(expected, actual)
    }

    @Test
    fun firstThrowsWhenArrayIsEmpty() {
        assertThrows<NoSuchElementException> { Vector2IArray(0).first() }
    }

    @ParameterizedTest
    @MethodSource("firstPredicateArgs")
    fun firstReturnsCorrectValue(
        array: Array<Vector2I>, predicate: (Vector2I) -> Boolean, exp: Pair<Int, Int>
    ) {
        val expected = Vector2I(exp.first, exp.second)
        val actual: Vector2I = array.toVector2IArray().first(predicate)

        assertEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("firstPredicateNotMatchArgs")
    fun firstThrowsWhenNoElementMatchesThePredicate(
        array: Array<Vector2I>, predicate: (Vector2I) -> Boolean
    ) {
        assertThrows<NoSuchElementException> { array.first(predicate) }
    }

    @ParameterizedTest
    @MethodSource("firstOrNullArgs")
    fun firstOrNullReturnsCorrectValue(array: Array<Vector2I>, exp: Pair<Int, Int>?) {
        val expected: Vector2I? = exp?.let { Vector2I(exp.first, exp.second) }
        val actual: Vector2I? = array.toVector2IArray().firstOrNull()

        assertEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("firstOrNullPredicateArgs")
    fun firstOrNullReturnsCorrectValue(
        array: Array<Vector2I>, predicate: (Vector2I) -> Boolean, exp: Pair<Int, Int>?
    ) {
        val expected: Vector2I? = exp?.let { Vector2I(exp.first, exp.second) }
        val actual: Vector2I? = array.toVector2IArray().firstOrNull(predicate)

        assertEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("getOrElseArgs")
    fun getOrElseReturnsCorrectValue(
        array: Array<Vector2I>,
        index: Int,
        defaultValue: (Int) -> Vector2I,
        exp: Pair<Int, Int>
    ) {
        val expected = Vector2I(exp.first, exp.second)
        val actual: Vector2I = array.toVector2IArray().getOrElse(index, defaultValue)

        assertEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("getOrNullArgs")
    fun getOrNullReturnsCorrectValue(
        array: Array<Vector2I>, index: Int, exp: Pair<Int, Int>?
    ) {
        val expected: Vector2I? = exp?.let { Vector2I(exp.first, exp.second) }
        val actual: Vector2I? = array.toVector2IArray().getOrNull(index)

        assertEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("indexOfArgs")
    fun indexOfReturnsCorrectValue(
        array: Array<Vector2I>, el: Pair<Int, Int>, expected: Int
    ) {
        val element = Vector2I(el.first, el.second)
        val actual: Int = array.toVector2IArray().indexOf(element)

        assertEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("indexOfFirstArgs")
    fun indexOfFirstReturnsCorrectValue(
        array: Array<Vector2I>, predicate: (Vector2I) -> Boolean, expected: Int
    ) {
        val actual: Int = array.toVector2IArray().indexOfFirst(predicate)

        assertEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("lastArgs")
    fun lastReturnsCorrectValue(array: Array<Vector2I>, exp: Pair<Int, Int>) {
        val expected = Vector2I(exp.first, exp.second)
        val actual: Vector2I = array.toVector2IArray().last()

        assertEquals(expected, actual)
    }

    @Test
    fun lastThrowsWhenArrayIsEmpty() {
        assertThrows<NoSuchElementException> { Vector2IArray(0).last() }
    }

    @ParameterizedTest
    @MethodSource("lastPredicateArgs")
    fun lastReturnsCorrectValue(
        array: Array<Vector2I>, predicate: (Vector2I) -> Boolean, exp: Pair<Int, Int>
    ) {
        val expected = Vector2I(exp.first, exp.second)
        val actual: Vector2I = array.toVector2IArray().last(predicate)

        assertEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("lastPredicateNotMatchArgs")
    fun lastThrowsWhenNoElementMatchesThePredicate(
        array: Array<Vector2I>, predicate: (Vector2I) -> Boolean
    ) {
        assertThrows<NoSuchElementException> { array.last(predicate) }
    }

    @ParameterizedTest
    @MethodSource("lastIndexOfArgs")
    fun lastIndexOfReturnsCorrectValue(
        array: Array<Vector2I>, el: Pair<Int, Int>, expected: Int
    ) {
        val element = Vector2I(el.first, el.second)
        val actual: Int = array.toVector2IArray().lastIndexOf(element)

        assertEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("lastOrNullArgs")
    fun lastOrNullReturnsCorrectValue(array: Array<Vector2I>, exp: Pair<Int, Int>?) {
        val expected: Vector2I? = exp?.let { Vector2I(exp.first, exp.second) }
        val actual: Vector2I? = array.toVector2IArray().lastOrNull()

        assertEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("lastOrNullPredicateArgs")
    fun lastOrNullReturnsCorrectValue(
        array: Array<Vector2I>, predicate: (Vector2I) -> Boolean, exp: Pair<Int, Int>?
    ) {
        val expected: Vector2I? = exp?.let { Vector2I(exp.first, exp.second) }
        val actual: Vector2I? = array.toVector2IArray().lastOrNull(predicate)

        assertEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("randomArgs")
    fun randomReturnsCorrectValue(
        array: Array<Vector2I>, random: Random, exp: Pair<Int, Int>
    ) {
        val expected = Vector2I(exp.first, exp.second)
        val actual: Vector2I = array.toVector2IArray().random(random)

        assertEquals(expected, actual)
    }

    @Test
    fun randomThrowsWhenArrayIsEmpty() {
        assertThrows<NoSuchElementException> { Vector2IArray(0).random() }
        assertThrows<NoSuchElementException> { Vector2IArray(0).random(Random) }
    }

    @ParameterizedTest
    @MethodSource("randomOrNullArgs")
    fun randomOrNullReturnsCorrectValue(
        array: Array<Vector2I>, random: Random, exp: Pair<Int, Int>?
    ) {
        val expected: Vector2I? = exp?.let { Vector2I(exp.first, exp.second) }
        val actual: Vector2I? = array.toVector2IArray().randomOrNull(random)

        assertEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("singleArgs")
    fun singleReturnsCorrectValue(array: Array<Vector2I>, exp: Pair<Int, Int>) {
        val expected = Vector2I(exp.first, exp.second)
        val actual: Vector2I = array.toVector2IArray().single()

        assertEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("singleThrowsExceptionArgs")
    fun <T : Throwable> singleThrowsCorrectException(
        array: Array<Vector2I>, expectedExceptionClass: Class<T>
    ) {
        assertThrows(expectedExceptionClass) {
            array.toVector2IArray().single()
        }
    }

    @ParameterizedTest
    @MethodSource("singlePredicateArgs")
    fun singleReturnsCorrectValue(
        array: Array<Vector2I>, predicate: (Vector2I) -> Boolean, exp: Pair<Int, Int>
    ) {
        val expected = Vector2I(exp.first, exp.second)
        val actual: Vector2I = array.toVector2IArray().single(predicate)

        assertEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("singlePredicateThrowsExceptionArgs")
    fun <T : Throwable> singleThrowsCorrectException(
        array: Array<Vector2I>, predicate: (Vector2I) -> Boolean, expectedExceptionClass: Class<T>
    ) {
        assertThrows(expectedExceptionClass) {
            array.toVector2IArray().single(predicate)
        }
    }

    @ParameterizedTest
    @MethodSource("singleOrNullArgs")
    fun singleOrNullReturnsCorrectValue(array: Array<Vector2I>, exp: Pair<Int, Int>?) {
        val expected: Vector2I? = exp?.let { Vector2I(exp.first, exp.second) }
        val actual: Vector2I? = array.toVector2IArray().singleOrNull()

        assertEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("dropArgs")
    fun dropReturnsCorrectValue(array: Array<Vector2I>, n: Int, expected: List<Vector2I>) {
        val actual: List<Vector2I> = array.toVector2IArray().drop(n)

        assertContentEquals(expected, actual)
    }

    @Test
    fun dropThrowsWhenRequestedCountIsNegative() {
        assertThrows<IllegalArgumentException> { Vector2IArray(0).drop(-1) }
    }

    @ParameterizedTest
    @MethodSource("dropLastArgs")
    fun dropLastReturnsCorrectValue(array: Array<Vector2I>, n: Int, expected: List<Vector2I>) {
        val actual: List<Vector2I> = array.toVector2IArray().dropLast(n)

        assertContentEquals(expected, actual)
    }

    @Test
    fun dropLastThrowsWhenRequestedCountIsNegative() {
        assertThrows<IllegalArgumentException> { Vector2IArray(0).dropLast(-1) }
    }

    @ParameterizedTest
    @MethodSource("dropLastWhileArgs")
    fun dropLastWhileReturnsCorrectValue(
        array: Array<Vector2I>, predicate: (Vector2I) -> Boolean, expected: List<Vector2I>
    ) {
        val actual: List<Vector2I> = array.toVector2IArray().dropLastWhile(predicate)

        assertContentEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("dropWhileArgs")
    fun dropWhileReturnsCorrectValue(
        array: Array<Vector2I>, predicate: (Vector2I) -> Boolean, expected: List<Vector2I>
    ) {
        val actual: List<Vector2I> = array.toVector2IArray().dropWhile(predicate)

        assertContentEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("sliceRangeArgs")
    fun sliceReturnsCorrectValue(
        array: Array<Vector2I>, indices: IntRange, expected: List<Vector2I>
    ) {
        val actual: List<Vector2I> = array.toVector2IArray().slice(indices)

        assertContentEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("sliceRangeThrowsExceptionArgs")
    fun <T : Throwable> sliceThrowsCorrectException(
        array: Array<Vector2I>, indices: IntRange, expectedExceptionClass: Class<T>
    ) {
        assertThrows(expectedExceptionClass) {
            array.toVector2IArray().slice(indices)
        }
    }

    @ParameterizedTest
    @MethodSource("sliceIterableArgs")
    fun sliceReturnsCorrectValue(
        array: Array<Vector2I>, indices: Iterable<Int>, expected: List<Vector2I>
    ) {
        val actual: List<Vector2I> = array.toVector2IArray().slice(indices)

        assertContentEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("sliceIterableThrowsExceptionArgs")
    fun <T : Throwable> sliceThrowsCorrectException(
        array: Array<Vector2I>, indices: Iterable<Int>, expectedExceptionClass: Class<T>
    ) {
        assertThrows(expectedExceptionClass) {
            array.toVector2IArray().slice(indices)
        }
    }

    @ParameterizedTest
    @MethodSource("sliceArrayCollectionArgs")
    fun sliceArrayReturnsCorrectValue(
        array: Array<Vector2I>, indices: Collection<Int>, exp: Array<Vector2I>
    ) {
        val expected = exp.toVector2IArray()
        val actual: Vector2IArray = array.toVector2IArray().sliceArray(indices)

        assertContentEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("sliceArrayCollectionThrowsExceptionArgs")
    fun <T : Throwable> sliceArrayThrowsCorrectException(
        array: Array<Vector2I>, indices: Collection<Int>, expectedExceptionClass: Class<T>
    ) {
        assertThrows(expectedExceptionClass) {
            array.toVector2IArray().sliceArray(indices)
        }
    }

    @ParameterizedTest
    @MethodSource("sliceArrayRangeArgs")
    fun sliceArrayReturnsCorrectValue(
        array: Array<Vector2I>, indices: IntRange, exp: Array<Vector2I>
    ) {
        val expected = exp.toVector2IArray()
        val actual: Vector2IArray = array.toVector2IArray().sliceArray(indices)

        assertContentEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("sliceArrayRangeThrowsExceptionArgs")
    fun <T : Throwable> sliceArrayThrowsCorrectException(
        array: Array<Vector2I>, indices: IntRange, expectedExceptionClass: Class<T>
    ) {
        assertThrows(expectedExceptionClass) {
            array.toVector2IArray().sliceArray(indices)
        }
    }

    @ParameterizedTest
    @MethodSource("takeArgs")
    fun takeReturnsCorrectValue(array: Array<Vector2I>, n: Int, expected: List<Vector2I>) {
        val actual: List<Vector2I> = array.toVector2IArray().take(n)

        assertContentEquals(expected, actual)
    }

    @Test
    fun takeThrowsWhenRequestedCountIsNegative() {
        assertThrows<IllegalArgumentException> { Vector2IArray(0).take(-1) }
    }

    @ParameterizedTest
    @MethodSource("takeLastArgs")
    fun takeLastReturnsCorrectValue(array: Array<Vector2I>, n: Int, expected: List<Vector2I>) {
        val actual: List<Vector2I> = array.toVector2IArray().takeLast(n)

        assertContentEquals(expected, actual)
    }

    @Test
    fun takeLastThrowsWhenRequestedCountIsNegative() {
        assertThrows<IllegalArgumentException> { Vector2IArray(0).takeLast(-1) }
    }

    @ParameterizedTest
    @MethodSource("takeLastWhileArgs")
    fun takeLastWhileReturnsCorrectValue(
        array: Array<Vector2I>, predicate: (Vector2I) -> Boolean, expected: List<Vector2I>
    ) {
        val actual: List<Vector2I> = array.toVector2IArray().takeLastWhile(predicate)

        assertContentEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("takeWhileArgs")
    fun takeWhileReturnsCorrectValue(
        array: Array<Vector2I>, predicate: (Vector2I) -> Boolean, expected: List<Vector2I>
    ) {
        val actual: List<Vector2I> = array.toVector2IArray().takeWhile(predicate)

        assertContentEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("reverseArgs")
    fun reverseMutatesArrayCorrectly(array: Array<Vector2I>, exp: Array<Vector2I>) {
        val expected = exp.toVector2IArray()
        val actual: Vector2IArray = array.toVector2IArray().apply {
            reverse()
        }
        assertContentEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("reverseRangeArgs")
    fun reverseMutatesArrayCorrectly(
        array: Array<Vector2I>, fromIndex: Int, toIndex: Int, exp: Array<Vector2I>
    ) {
        val expected = exp.toVector2IArray()
        val actual: Vector2IArray = array.toVector2IArray().apply {
            reverse(fromIndex, toIndex)
        }
        assertContentEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("reverseRangeThrowsExceptionArgs")
    fun <T : Throwable> reverseThrowsCorrectException(
        array: Array<Vector2I>, fromIndex: Int, toIndex: Int, expectedExceptionClass: Class<T>
    ) {
        assertThrows(expectedExceptionClass) {
            array.toVector2IArray().reverse(fromIndex, toIndex)
        }
    }

    @ParameterizedTest
    @MethodSource("reversedArgs")
    fun reversedReturnsCorrectValue(array: Array<Vector2I>, expected: List<Vector2I>) {
        val actual: List<Vector2I> = array.toVector2IArray().reversed()

        assertContentEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("reversedArrayArgs")
    fun reversedArrayReturnsCorrectValue(array: Array<Vector2I>, exp: Array<Vector2I>) {
        val expected = exp.toVector2IArray()
        val actual: Vector2IArray = array.toVector2IArray().reversedArray()

        assertContentEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("shuffleArgs")
    fun shuffleReturnsCorrectValue(
        array: Array<Vector2I>, random: Random, exp: Array<Vector2I>
    ) {
        val expected = exp.toVector2IArray()
        val actual: Vector2IArray = array.toVector2IArray()
        actual.shuffle(random)

        assertContentEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("sortedByArgs")
    fun <R : Comparable<R>> sortedByReturnsCorrectValue(
        array: Array<Vector2I>, selector: (Vector2I) -> R?, expected: List<Vector2I>
    ) {
        val actual: List<Vector2I> = array.toVector2IArray().sortedBy(selector)

        assertContentEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("sortedByDescendingArgs")
    fun <R : Comparable<R>> sortedByDescendingReturnsCorrectValue(
        array: Array<Vector2I>, selector: (Vector2I) -> R?, expected: List<Vector2I>
    ) {
        val actual: List<Vector2I> = array.toVector2IArray().sortedByDescending(selector)

        assertContentEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("sortedWithArgs")
    fun sortedWithReturnsCorrectValue(
        array: Array<Vector2I>, comparator: Comparator<in Vector2I>, expected: List<Vector2I>
    ) {
        val actual: List<Vector2I> = array.toVector2IArray().sortedWith(comparator)

        assertContentEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("asListArgs")
    fun asListReturnsCorrectValue(array: Array<Vector2I>, expected: List<Vector2I>) {
        val actual: List<Vector2I> = array.toVector2IArray().asList()

        assertContentEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("copyIntoArgs")
    fun copyIntoReturnsCorrectValue(
        array: Array<Vector2I>,
        dest: Array<Vector2I>,
        destinationOffset: Int,
        startIndex: Int,
        endIndex: Int,
        exp: Array<Vector2I>
    ) {
        val destination: Vector2IArray = dest.toVector2IArray()
        val expected = exp.toVector2IArray()
        val actual: Vector2IArray = array.toVector2IArray().copyInto(
            destination,
            destinationOffset,
            startIndex,
            endIndex
        )
        assertContentEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("copyIntoThrowsExceptionArgs")
    fun <T : Throwable> copyIntoThrowsCorrectException(
        array: Array<Vector2I>,
        dest: Array<Vector2I>,
        destinationOffset: Int,
        startIndex: Int,
        endIndex: Int,
        expectedExceptionClass: Class<T>
    ) {
        val destination: Vector2IArray = dest.toVector2IArray()

        assertThrows(expectedExceptionClass) {
            array.toVector2IArray().copyInto(
                destination,
                destinationOffset,
                startIndex,
                endIndex
            )
        }
    }

    @ParameterizedTest
    @MethodSource("copyOfArgs")
    fun copyOfReturnsCorrectValue(array: Array<Vector2I>, exp: Array<Vector2I>) {
        val expected = exp.toVector2IArray()
        val actual: Vector2IArray = array.toVector2IArray().copyOf()

        assertContentEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("copyOfSizeArgs")
    fun copyOfReturnsCorrectValue(
        array: Array<Vector2I>, newSize: Int, exp: Array<Vector2I>
    ) {
        val expected = exp.toVector2IArray()
        val actual: Vector2IArray = array.toVector2IArray().copyOf(newSize)

        assertContentEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("copyOfRangeArgs")
    fun copyOfRangeReturnsCorrectValue(
        array: Array<Vector2I>, fromIndex: Int, toIndex: Int, exp: Array<Vector2I>
    ) {
        val expected = exp.toVector2IArray()
        val actual: Vector2IArray = array.toVector2IArray().copyOfRange(fromIndex, toIndex)

        assertContentEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("copyOfRangeThrowsExceptionArgs")
    fun <T : Throwable> copyOfRangeThrowsCorrectException(
        array: Array<Vector2I>, fromIndex: Int, toIndex: Int, expectedExceptionClass: Class<T>
    ) {
        assertThrows(expectedExceptionClass) {
            array.toVector2IArray().copyOfRange(fromIndex, toIndex)
        }
    }

    @ParameterizedTest
    @MethodSource("fillArgs")
    fun fillMutatesArrayCorrectly(
        array: Array<Vector2I>,
        el: Pair<Int, Int>,
        fromIndex: Int,
        toIndex: Int,
        exp: Array<Vector2I>
    ) {
        val element = Vector2I(el.first, el.second)
        val expected = exp.toVector2IArray()
        val actual: Vector2IArray = array.toVector2IArray().apply {
            fill(element, fromIndex, toIndex)
        }
        assertContentEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("fillThrowsExceptionArgs")
    fun <T : Throwable> fillThrowsCorrectException(
        array: Array<Vector2I>,
        el: Pair<Int, Int>,
        fromIndex: Int,
        toIndex: Int,
        expectedExceptionClass: Class<T>
    ) {
        val element = Vector2I(el.first, el.second)

        assertThrows(expectedExceptionClass) {
            array.toVector2IArray().fill(element, fromIndex, toIndex)
        }
    }

    @ParameterizedTest
    @MethodSource("isEmptyArgs")
    fun isEmptyReturnsCorrectValue(array: Array<Vector2I>, expected: Boolean) {
        val actual: Boolean = array.toVector2IArray().isEmpty()

        assertEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("isNotEmptyArgs")
    fun isNotEmptyReturnsCorrectValue(array: Array<Vector2I>, expected: Boolean) {
        val actual: Boolean = array.toVector2IArray().isNotEmpty()

        assertEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("toTypedArrayArgs")
    fun toTypedArrayReturnsCorrectValue(array: Wrapper<Vector2IArray>, expected: Array<Vector2I>) {
        val actual: Array<Vector2I> = array.value.toTypedArray()

        assertContentEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("arrays")
    fun forEachIteratesThroughArrayCorrectly(array: Array<Vector2I>) {
        var expectedIndex = 0

        array.forEach { actualItem ->
            val expectedItem: Vector2I = array[expectedIndex]

            assertEquals(expectedItem, actualItem)
            expectedIndex++
        }
    }

    @ParameterizedTest
    @MethodSource("arrays")
    fun forEachIndexedIteratesThroughArrayCorrectly(array: Array<Vector2I>) {
        var expectedIndex = 0

        array.forEachIndexed { actualIndex, actualItem ->
            val expectedItem: Vector2I = array[expectedIndex]

            assertEquals(expectedIndex, actualIndex)
            assertEquals(expectedItem, actualItem)
            expectedIndex++
        }
    }

    @ParameterizedTest
    @MethodSource("noneArgs")
    fun noneReturnsCorrectValue(array: Array<Vector2I>, expected: Boolean) {
        val actual: Boolean = array.toVector2IArray().none()

        assertEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("sumOfDoubleArgs")
    fun sumOfReturnsCorrectValue(
        array: Array<Vector2I>, selector: (Vector2I) -> Double, expected: Double
    ) {
        val actual: Double = array.toVector2IArray().sumOf(selector)

        assertTrue(expected.isApproximately(actual))
    }

    @ParameterizedTest
    @MethodSource("sumOfIntArgs")
    fun sumOfReturnsCorrectValue(
        array: Array<Vector2I>, selector: (Vector2I) -> Int, expected: Int
    ) {
        val actual: Int = array.toVector2IArray().sumOf(selector)

        assertEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("sumOfUIntArgs")
    fun sumOfUIntReturnsCorrectValue(
        array: Array<Vector2I>, selector: (Vector2I) -> UInt, exp: Wrapper<UInt>
    ) {
        val expected: UInt = exp.value
        val actual: UInt = array.toVector2IArray().sumOf(selector)

        assertEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("sumOfLongArgs")
    fun sumOfReturnsCorrectValue(
        array: Array<Vector2I>, selector: (Vector2I) -> Long, expected: Long
    ) {
        val actual: Long = array.toVector2IArray().sumOf(selector)

        assertEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("sumOfULongArgs")
    fun sumOfULongReturnsCorrectValue(
        array: Array<Vector2I>, selector: (Vector2I) -> ULong, exp: Wrapper<ULong>
    ) {
        val expected: ULong = exp.value
        val actual: ULong = array.toVector2IArray().sumOf(selector)

        assertEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("sumOfVector2IArgs")
    fun sumOfReturnsCorrectValue(
        array: Array<Vector2I>, selector: (Vector2I) -> Vector2I, exp: Pair<Int, Int>
    ) {
        val expected = Vector2I(exp.first, exp.second)
        val actual: Vector2I = array.toVector2IArray().sumOf(selector)

        assertEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("sumArgs")
    fun sumReturnsCorrectValue(array: Array<Vector2I>, exp: Pair<Int, Int>) {
        val expected = Vector2I(exp.first, exp.second)
        val actual: Vector2I = array.toVector2IArray().sum()

        assertEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("iteratorArgs")
    fun iteratorReturnsCorrectValue(array: Array<Vector2I>, expected: Iterator<Vector2I>) {
        val actual = array.toVector2IArray().iterator()

        for (i in 0..array.lastIndex) {
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
        array: Array<Vector2I>, index: Int, exp: Pair<Int, Int>
    ) {
        val expected = Vector2I(exp.first, exp.second)
        val actual: Vector2I = array.toVector2IArray()[index]

        assertEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("setArgs")
    fun setMutatesArrayCorrectly(
        array: Array<Vector2I>, index: Int, value: Pair<Int, Int>, exp: Array<Vector2I>
    ) {
        val expected = exp.toVector2IArray()
        val actual = array.toVector2IArray()
        actual[index] = Vector2I(value.first, value.second)

        assertContentEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("arrayPlusVector2IArgs")
    fun arrayPlusVector2IReturnsCorrectValue(
        array: Array<Vector2I>, el: Pair<Int, Int>, exp: Array<Vector2I>
    ) {
        val element = Vector2I(el.first, el.second)
        val expected = exp.toVector2IArray()
        val actual: Vector2IArray = array.toVector2IArray() + element

        assertContentEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("arrayPlusCollectionArgs")
    fun arrayPlusCollectionReturnsCorrectValue(
        array: Array<Vector2I>, elements: Collection<Vector2I>, exp: Array<Vector2I>
    ) {
        val expected = exp.toVector2IArray()
        val actual: Vector2IArray = array.toVector2IArray() + elements

        assertContentEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("arrayPlusArrayArgs")
    fun arrayPlusArrayReturnsCorrectValue(
        array: Array<Vector2I>, elems: Array<Vector2I>, exp: Array<Vector2I>
    ) {
        val elements = elems.toVector2IArray()
        val expected = exp.toVector2IArray()
        val actual: Vector2IArray = array.toVector2IArray() + elements

        assertContentEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("typedArrayToVector2IArrayArgs")
    fun typedArrayToVector2IArrayReturnsCorrectValue(
        array: Array<Vector2I>, exp: Wrapper<Vector2IArray>
    ) {
        val expected: Vector2IArray = exp.value
        val actual = array.toVector2IArray()

        assertContentEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("typedArraySumOfVector2IArgs")
    fun <T> typedArraySumOfReturnsCorrectValue(
        array: Array<T>, selector: (T) -> Vector2I, exp: Pair<Int, Int>
    ) {
        val expected = Vector2I(exp.first, exp.second)
        val actual: Vector2I = array.sumOf(selector)

        assertEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("typedArraySumArgs")
    fun typedArraySumReturnsCorrectValue(array: Array<Vector2I>, exp: Pair<Int, Int>) {
        val expected = Vector2I(exp.first, exp.second)
        val actual: Vector2I = array.sum()

        assertEquals(expected, actual)
    }

    companion object {
        @JvmStatic
        fun arrays(): List<Arguments> = listOf(
            Arguments.of(Array(4) { Vector2I(it, -it) }),
            Arguments.of(arrayOf(Vector2I(-3, 5), Vector2I(-1, 7))),
            Arguments.of(emptyArray<Vector2I>()),
        )

        @JvmStatic
        fun containsArgs(): List<Arguments> {
            val array = arrayOf(
                Vector2I(1, 0),
                Vector2I(-5, 8),
                Vector2I(2, -9),
                Vector2I(3, 4)
            )
            return listOf(
                Arguments.of(array, Pair(0, 0), false),
                Arguments.of(array, Pair(3, 4), true),
            )
        }

        @JvmStatic
        fun containsAllArgs(): List<Arguments> {
            val array = arrayOf(
                Vector2I(1, 0),
                Vector2I(-5, 8),
                Vector2I(2, -9),
            )
            return listOf(
                Arguments.of(array, array.toList(), true),
                Arguments.of(array, listOf(Vector2I(1, 0), Vector2I(-5, 8)), true),
                Arguments.of(array, emptyList<Vector2I>(), true),
                Arguments.of(array, listOf(Vector2I.ZERO), false),
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
            Arguments.of(arrayOf(Vector2I(4, 5), Vector2I(1, 2)), Pair(4, 5)),
            Arguments.of(Array(2) { Vector2I(it, 0) }, Pair(0, 0)),
        )

        @JvmStatic
        fun firstPredicateArgs(): List<Arguments> = listOf(
            Arguments.of(
                arrayOf(Vector2I(4, 5), Vector2I(1, 2)),
                { v: Vector2I -> v == Vector2I(1, 2) },
                Pair(1, 2)
            ),
            Arguments.of(
                Array(2) { Vector2I(it, 0) },
                { v: Vector2I -> v.x >= 1 },
                Pair(1, 0)
            ),
        )

        @JvmStatic
        fun firstPredicateNotMatchArgs() = listOf(
            Arguments.of(
                arrayOf(Vector2I(4, 5), Vector2I(1, 2)),
                { v: Vector2I -> v == Vector2I(4, 2) }),
            Arguments.of(
                Array(2) { Vector2I(it, 0) },
                { v: Vector2I -> v.x >= 2 }
            ),
        )

        @JvmStatic
        fun firstOrNullArgs(): List<Arguments> = listOf(
            Arguments.of(arrayOf(Vector2I(4, 5), Vector2I(1, 2)), Pair(4, 5)),
            Arguments.of(Array(2) { Vector2I(it, 0) }, Pair(0, 0)),
            Arguments.of(emptyArray<Vector2I>(), null),
        )

        @JvmStatic
        fun firstOrNullPredicateArgs(): List<Arguments> = listOf(
            Arguments.of(
                arrayOf(Vector2I(4, 5), Vector2I(1, 2)),
                { v: Vector2I -> v == Vector2I(1, 2) },
                Pair(1, 2)
            ),
            Arguments.of(
                Array(2) { Vector2I(it, 0) },
                { v: Vector2I -> v.x >= 1 },
                Pair(1, 0)
            ),
            Arguments.of(
                Array(2) { Vector2I(it, 0) },
                { v: Vector2I -> v.x >= 2 },
                null
            ),
            Arguments.of(
                emptyArray<Vector2I>(),
                { _: Vector2I -> true },
                null
            ),
        )

        @JvmStatic
        fun lastArgs(): List<Arguments> = listOf(
            Arguments.of(arrayOf(Vector2I(4, 5), Vector2I(1, 2)), Pair(1, 2)),
            Arguments.of(Array(2) { Vector2I(it, 0) }, Pair(1, 0)),
        )

        @JvmStatic
        fun lastPredicateArgs(): List<Arguments> = listOf(
            Arguments.of(
                arrayOf(Vector2I(4, 5), Vector2I(1, 2)),
                { v: Vector2I -> v == Vector2I(1, 2) },
                Pair(1, 2)
            ),
            Arguments.of(
                Array(2) { Vector2I(it, 0) },
                { v: Vector2I -> v.x <= 1 },
                Pair(1, 0)
            ),
        )

        @JvmStatic
        fun lastPredicateNotMatchArgs(): List<Arguments> = listOf(
            Arguments.of(
                arrayOf(Vector2I(4, 5), Vector2I(1, 2)),
                { v: Vector2I -> v == Vector2I(4, 2) }),
            Arguments.of(
                Array(2) { Vector2I(it, 0) },
                { v: Vector2I -> v.x >= 2 }
            ),
        )

        @JvmStatic
        fun lastOrNullArgs(): List<Arguments> = listOf(
            Arguments.of(arrayOf(Vector2I(4, 5), Vector2I(1, 2)), Pair(1, 2)),
            Arguments.of(Array(2) { Vector2I(it, 0) }, Pair(1, 0)),
            Arguments.of(emptyArray<Vector2I>(), null),
        )

        @JvmStatic
        fun lastOrNullPredicateArgs(): List<Arguments> = listOf(
            Arguments.of(
                arrayOf(Vector2I(4, 5), Vector2I(1, 2)),
                { v: Vector2I -> v == Vector2I(1, 2) },
                Pair(1, 2)
            ),
            Arguments.of(
                Array(2) { Vector2I(it, 0) },
                { v: Vector2I -> v.x <= 0 },
                Pair(0, 0)
            ),
            Arguments.of(
                Array(2) { Vector2I(it, 0) },
                { v: Vector2I -> v.x >= 2 },
                null
            ),
            Arguments.of(
                emptyArray<Vector2I>(),
                { _: Vector2I -> true },
                null
            ),
        )

        @JvmStatic
        fun indexOfArgs(): List<Arguments> = listOf(
            Arguments.of(arrayOf(Vector2I(4, 5), Vector2I(1, 2)), Pair(1, 2), 1),
            Arguments.of(arrayOf(Vector2I(4, 5), Vector2I(4, 5)), Pair(4, 5), 0),
            Arguments.of(arrayOf(Vector2I(4, 5), Vector2I(1, 2)), Pair(7, 4), -1),
            Arguments.of(emptyArray<Vector2I>(), Pair(7, 4), -1),
        )

        @JvmStatic
        fun indexOfFirstArgs(): List<Arguments> = listOf(
            Arguments.of(
                arrayOf(Vector2I(4, 5), Vector2I(1, 2)),
                { v: Vector2I -> v == Vector2I(1, 2) },
                1
            ),
            Arguments.of(
                Array(2) { Vector2I(it, 0) },
                { v: Vector2I -> v.x >= 2 },
                -1
            ),
            Arguments.of(
                emptyArray<Vector2I>(),
                { _: Vector2I -> true },
                -1
            ),
        )

        @JvmStatic
        fun lastIndexOfArgs(): List<Arguments> = listOf(
            Arguments.of(arrayOf(Vector2I(4, 5), Vector2I(1, 2)), Pair(1, 2), 1),
            Arguments.of(arrayOf(Vector2I(4, 5), Vector2I(4, 5)), Pair(4, 5), 1),
            Arguments.of(arrayOf(Vector2I(4, 5), Vector2I(1, 2)), Pair(7, 4), -1),
            Arguments.of(emptyArray<Vector2I>(), Pair(7, 4), -1),
        )

        @JvmStatic
        fun randomArgs(): List<Arguments> {
            val array = Array(10) { Vector2I(it, 0) }
            val seeds = intArrayOf(1234, 5678)
            val expectedVals = arrayOf(
                array.random(Random(seeds[0])).let { Pair(it.x, it.y) },
                array.random(Random(seeds[1])).let { Pair(it.x, it.y) },
            )
            return listOf(
                Arguments.of(array, Random(seeds[0]), expectedVals[0]),
                Arguments.of(array, Random(seeds[1]), expectedVals[1]),
            )
        }

        @JvmStatic
        fun randomOrNullArgs(): List<Arguments> {
            val array = Array(10) { Vector2I(it, 0) }
            val seeds = intArrayOf(1234, 5678)
            val expectedVals = arrayOf(
                array.random(Random(seeds[0])).let { Pair(it.x, it.y) },
                array.random(Random(seeds[1])).let { Pair(it.x, it.y) },
            )
            return listOf(
                Arguments.of(array, Random(seeds[0]), expectedVals[0]),
                Arguments.of(array, Random(seeds[1]), expectedVals[1]),
                Arguments.of(emptyArray<Vector2I>(), Random(42), null),
            )
        }

        @JvmStatic
        fun singleArgs(): List<Arguments> = listOf(
            Arguments.of(arrayOf(Vector2I(1, 2)), Pair(1, 2)),
            Arguments.of(arrayOf(Vector2I.ZERO), Pair(0, 0)),
        )

        @JvmStatic
        fun singleThrowsExceptionArgs(): List<Arguments> = listOf(
            Arguments.of(emptyArray<Vector2I>(), NoSuchElementException::class.java),
            Arguments.of(Array(size = 2) { Vector2I.ZERO }, IllegalArgumentException::class.java),
        )

        @JvmStatic
        fun singlePredicateArgs(): List<Arguments> = listOf(
            Arguments.of(
                Array(size = 3) { Vector2I(it, 0) },
                { v: Vector2I -> v == Vector2I(1, 0) },
                Pair(1, 0)
            ),
            Arguments.of(
                Array(size = 3) { Vector2I(it, 0) },
                { v: Vector2I -> v.x == 1 },
                Pair(1, 0)
            ),
        )

        @JvmStatic
        fun singlePredicateThrowsExceptionArgs(): List<Arguments> = listOf(
            Arguments.of(
                emptyArray<Vector2I>(),
                { v: Vector2I -> v == Vector2I.ZERO },
                NoSuchElementException::class.java
            ),
            Arguments.of(
                Array(size = 2) { Vector2I.ZERO },
                { v: Vector2I -> v == Vector2I.ZERO },
                IllegalArgumentException::class.java
            ),
        )

        @JvmStatic
        fun singleOrNullArgs(): List<Arguments> = listOf(
            Arguments.of(arrayOf(Vector2I(1, 2)), Pair(1, 2)),
            Arguments.of(arrayOf(Vector2I.ZERO), Pair(0, 0)),
            Arguments.of(emptyArray<Vector2I>(), null),
            Arguments.of(Array(size = 2) { Vector2I.ZERO }, null),
        )

        @JvmStatic
        fun dropArgs(): List<Arguments> {
            val array = Array(4) { Vector2I(it, 0) }

            return listOf(
                Arguments.of(array, 0, List(4) { Vector2I(it, 0) }),
                Arguments.of(array, 2, listOf(Vector2I(2, 0), Vector2I(3, 0))),
                Arguments.of(array, 5, emptyList<Vector2I>()),
            )
        }

        @JvmStatic
        fun dropLastArgs(): List<Arguments> {
            val array = Array(4) { Vector2I(it, 0) }

            return listOf(
                Arguments.of(array, 0, List(4) { Vector2I(it, 0) }),
                Arguments.of(array, 2, listOf(Vector2I(0, 0), Vector2I(1, 0))),
                Arguments.of(array, 5, emptyList<Vector2I>()),
            )
        }

        @JvmStatic
        fun dropWhileArgs(): List<Arguments> {
            val array = Array(4) { Vector2I(it, 0) }

            return listOf(
                Arguments.of(
                    emptyArray<Vector2I>(), { _: Vector2I -> false }, emptyList<Vector2I>(),
                ),
                Arguments.of(
                    array, { _: Vector2I -> false }, List(4) { Vector2I(it, 0) }
                ),
                Arguments.of(
                    array,
                    { v: Vector2I -> v.x != 2 },
                    listOf(Vector2I(2, 0), Vector2I(3, 0))
                ),
                Arguments.of(
                    array, { _: Vector2I -> true }, emptyList<Vector2I>()
                ),
            )
        }

        @JvmStatic
        fun dropLastWhileArgs(): List<Arguments> {
            val array = Array(4) { Vector2I(it, 0) }

            return listOf(
                Arguments.of(
                    emptyArray<Vector2I>(), { _: Vector2I -> false }, emptyList<Vector2I>(),
                ),
                Arguments.of(
                    array, { _: Vector2I -> false }, List(4) { Vector2I(it, 0) }
                ),
                Arguments.of(
                    array,
                    { v: Vector2I -> v.x != 1 },
                    listOf(Vector2I(0, 0), Vector2I(1, 0))
                ),
                Arguments.of(
                    array, { _: Vector2I -> true }, emptyList<Vector2I>()
                ),
            )
        }

        @JvmStatic
        fun sliceRangeArgs(): List<Arguments> {
            val array = Array(3) { Vector2I(it, 0) }

            return listOf(
                Arguments.of(
                    array,
                    0..2,
                    listOf(Vector2I(0, 0), Vector2I(1, 0), Vector2I(2, 0)),
                ),
                Arguments.of(
                    array,
                    1..2,
                    listOf(Vector2I(1, 0), Vector2I(2, 0)),
                ),
                Arguments.of(
                    array,
                    IntRange.EMPTY,
                    emptyList<Vector2I>(),
                ),
            )
        }

        @JvmStatic
        fun sliceRangeThrowsExceptionArgs(): List<Arguments> = listOf(
            Arguments.of(Array(3) { Vector2I.ZERO }, 0..3, IndexOutOfBoundsException::class.java),
            Arguments.of(Array(3) { Vector2I.ZERO }, -1..2, IndexOutOfBoundsException::class.java),
        )

        @JvmStatic
        fun sliceIterableArgs(): List<Arguments> {
            val array = Array(3) { Vector2I(it, 0) }

            return listOf(
                Arguments.of(
                    array,
                    listOf(0, 1, 2),
                    listOf(Vector2I(0, 0), Vector2I(1, 0), Vector2I(2, 0)),
                ),
                Arguments.of(
                    array,
                    listOf(0, 2),
                    listOf(Vector2I(0, 0), Vector2I(2, 0)),
                ),
                Arguments.of(
                    array,
                    emptyList<Int>(),
                    emptyList<Vector2I>(),
                ),
            )
        }

        @JvmStatic
        fun sliceIterableThrowsExceptionArgs(): List<Arguments> = listOf(
            Arguments.of(
                Array(3) { Vector2I.ZERO },
                listOf(-1, 0, 1),
                IndexOutOfBoundsException::class.java
            ),
            Arguments.of(
                Array(3) { Vector2I.ZERO },
                listOf(0, 1, 3),
                IndexOutOfBoundsException::class.java
            ),
        )

        @JvmStatic
        fun sliceArrayRangeArgs(): List<Arguments> {
            val array = Array(3) { Vector2I(it, 0) }

            return listOf(
                Arguments.of(
                    array,
                    0..2,
                    arrayOf(Vector2I(0, 0), Vector2I(1, 0), Vector2I(2, 0)),
                ),
                Arguments.of(
                    array,
                    1..2,
                    arrayOf(Vector2I(1, 0), Vector2I(2, 0)),
                ),
                Arguments.of(
                    array,
                    IntRange.EMPTY,
                    emptyArray<Vector2I>(),
                ),
            )
        }

        @JvmStatic
        fun sliceArrayRangeThrowsExceptionArgs(): List<Arguments> = listOf(
            Arguments.of(Array(3) { Vector2I.ZERO }, 0..3, IndexOutOfBoundsException::class.java),
            Arguments.of(Array(3) { Vector2I.ZERO }, -1..2, IndexOutOfBoundsException::class.java),
        )

        @JvmStatic
        fun sliceArrayCollectionArgs(): List<Arguments> {
            val array = Array(3) { Vector2I(it, 0) }

            return listOf(
                Arguments.of(
                    array,
                    listOf(0, 1, 2),
                    arrayOf(Vector2I(0, 0), Vector2I(1, 0), Vector2I(2, 0)),
                ),
                Arguments.of(
                    array,
                    listOf(0, 2),
                    arrayOf(Vector2I(0, 0), Vector2I(2, 0)),
                ),
                Arguments.of(
                    array,
                    emptyList<Int>(),
                    emptyArray<Vector2I>(),
                ),
            )
        }

        @JvmStatic
        fun sliceArrayCollectionThrowsExceptionArgs(): List<Arguments> = listOf(
            Arguments.of(
                Array(3) { Vector2I.ZERO },
                listOf(-1, 0, 1),
                IndexOutOfBoundsException::class.java
            ),
            Arguments.of(
                Array(3) { Vector2I.ZERO },
                listOf(0, 1, 3),
                IndexOutOfBoundsException::class.java
            ),
        )

        @JvmStatic
        fun takeArgs(): List<Arguments> {
            val array = Array(4) { Vector2I(it, 0) }

            return listOf(
                Arguments.of(array, 0, emptyList<Vector2I>()),
                Arguments.of(array, 2, listOf(Vector2I(0, 0), Vector2I(1, 0))),
                Arguments.of(array, 5, List(4) { Vector2I(it, 0) }),
            )
        }

        @JvmStatic
        fun takeLastArgs(): List<Arguments> {
            val array = Array(4) { Vector2I(it, 0) }

            return listOf(
                Arguments.of(array, 0, emptyList<Vector2I>()),
                Arguments.of(array, 2, listOf(Vector2I(2, 0), Vector2I(3, 0))),
                Arguments.of(array, 5, List(4) { Vector2I(it, 0) }),
            )
        }

        @JvmStatic
        fun takeWhileArgs(): List<Arguments> {
            val array = Array(4) { Vector2I(it, 0) }

            return listOf(
                Arguments.of(
                    emptyArray<Vector2I>(), { _: Vector2I -> true }, emptyList<Vector2I>(),
                ),
                Arguments.of(
                    array, { _: Vector2I -> false }, emptyList<Vector2I>()
                ),
                Arguments.of(
                    array,
                    { v: Vector2I -> v.x != 2 },
                    listOf(Vector2I(0, 0), Vector2I(1, 0))
                ),
                Arguments.of(
                    array, { _: Vector2I -> true }, List(4) { Vector2I(it, 0) }
                ),
            )
        }

        @JvmStatic
        fun takeLastWhileArgs(): List<Arguments> {
            val array = Array(4) { Vector2I(it, 0) }

            return listOf(
                Arguments.of(
                    emptyArray<Vector2I>(), { _: Vector2I -> true }, emptyList<Vector2I>(),
                ),
                Arguments.of(
                    array, { _: Vector2I -> false }, emptyList<Vector2I>()
                ),
                Arguments.of(
                    array,
                    { v: Vector2I -> v.x != 1 },
                    listOf(Vector2I(2, 0), Vector2I(3, 0))
                ),
                Arguments.of(
                    array, { _: Vector2I -> true }, List(4) { Vector2I(it, 0) }
                ),
            )
        }

        @JvmStatic
        fun reverseArgs(): List<Arguments> = listOf(
            Arguments.of(emptyArray<Vector2I>(), emptyArray<Vector2I>()),
            Arguments.of(
                arrayOf(Vector2I(1, 2), Vector2I(3, 4), Vector2I(5, 6)),
                arrayOf(Vector2I(5, 6), Vector2I(3, 4), Vector2I(1, 2))
            ),
            Arguments.of(
                arrayOf(Vector2I(1, 2), Vector2I(3, 4)),
                arrayOf(Vector2I(3, 4), Vector2I(1, 2))
            ),
        )

        @JvmStatic
        fun reverseRangeArgs(): List<Arguments> = listOf(
            Arguments.of(emptyArray<Vector2I>(), 0, 0, emptyArray<Vector2I>()),
            Arguments.of(
                arrayOf(Vector2I(1, 2), Vector2I(3, 4), Vector2I(5, 6)),
                1, 3,
                arrayOf(Vector2I(1, 2), Vector2I(5, 6), Vector2I(3, 4))
            ),
            Arguments.of(
                arrayOf(Vector2I(1, 2), Vector2I(3, 4)),
                0, 2,
                arrayOf(Vector2I(3, 4), Vector2I(1, 2))
            ),
        )

        @JvmStatic
        fun reverseRangeThrowsExceptionArgs(): List<Arguments> = listOf(
            Arguments.of(Array(3) { Vector2I.ZERO }, 0, 4, IndexOutOfBoundsException::class.java),
            Arguments.of(Array(3) { Vector2I.ZERO }, -1, 2, IndexOutOfBoundsException::class.java),
            Arguments.of(Array(3) { Vector2I.ZERO }, 2, 1, IllegalArgumentException::class.java),
        )

        @JvmStatic
        fun reversedArgs(): List<Arguments> = listOf(
            Arguments.of(emptyArray<Vector2I>(), emptyList<Vector2I>()),
            Arguments.of(
                arrayOf(Vector2I(1, 2), Vector2I(3, 4), Vector2I(5, 6)),
                listOf(Vector2I(5, 6), Vector2I(3, 4), Vector2I(1, 2))
            ),
            Arguments.of(
                arrayOf(Vector2I(1, 2), Vector2I(3, 4)),
                listOf(Vector2I(3, 4), Vector2I(1, 2))
            ),
        )

        @JvmStatic
        fun reversedArrayArgs(): List<Arguments> = reverseArgs()

        @JvmStatic
        fun shuffleArgs(): List<Arguments> {
            val array = Array(10) { Vector2I(it, 0) }
            val seeds = intArrayOf(1234, 5678)

            return listOf(
                Arguments.of(
                    array, Random(seeds[0]), array.copyOf().apply { shuffle(Random(seeds[0])) }
                ),
                Arguments.of(
                    array, Random(seeds[1]), array.copyOf().apply { shuffle(Random(seeds[1])) }
                ),
            )
        }

        @JvmStatic
        fun sortedByArgs(): List<Arguments> {
            val array = arrayOf(
                Vector2I(4, 2), Vector2I(7, 3), Vector2I(3, 1),
            )
            return listOf(
                Arguments.of(
                    array,
                    { v: Vector2I -> v.x },
                    listOf(Vector2I(3, 1), Vector2I(4, 2), Vector2I(7, 3))
                ),
                Arguments.of(
                    emptyArray<Vector2I>(),
                    { v: Vector2I -> v.x },
                    emptyList<Vector2I>()
                ),
            )
        }

        @JvmStatic
        fun sortedByDescendingArgs(): List<Arguments> {
            val array = arrayOf(
                Vector2I(4, 2), Vector2I(7, 3), Vector2I(3, 1),
            )
            return listOf(
                Arguments.of(
                    array,
                    { v: Vector2I -> v.y },
                    listOf(Vector2I(7, 3), Vector2I(4, 2), Vector2I(3, 1))
                ),
                Arguments.of(
                    emptyArray<Vector2I>(),
                    { v: Vector2I -> v.y },
                    emptyList<Vector2I>()
                ),
            )
        }

        @JvmStatic
        fun sortedWithArgs(): List<Arguments> {
            val array = arrayOf(
                Vector2I(4, 3), Vector2I(4, 2), Vector2I(3, 1),
            )
            val comparator = Comparator<Vector2I> { a, b ->
                val xToX = a.x.compareTo(b.x)

                if (xToX == 0) a.y.compareTo(b.y) else xToX
            }
            return listOf(
                Arguments.of(
                    array,
                    comparator,
                    listOf(Vector2I(3, 1), Vector2I(4, 2), Vector2I(4, 3))
                ),
                Arguments.of(
                    emptyArray<Vector2I>(),
                    comparator,
                    emptyList<Vector2I>()
                ),
            )
        }

        @JvmStatic
        fun asListArgs(): List<Arguments> = listOf(
            Arguments.of(emptyArray<Vector2I>(), emptyList<Vector2I>()),
            Arguments.of(
                Array(4) { Vector2I(it, 0) },
                List(4) { Vector2I(it, 0) }
            ),
        )

        @JvmStatic
        fun copyIntoArgs(): List<Arguments> = listOf(
            Arguments.of(
                Array(4) { Vector2I(it, 0) },
                Array(4) { Vector2I.ZERO },
                0, 0, 4,
                Array(4) { Vector2I(it, 0) }
            ),
            Arguments.of(
                Array(4) { Vector2I(it, 0) },
                Array(5) { Vector2I.ZERO },
                3, 1, 3,
                arrayOf(
                    Vector2I(0, 0),
                    Vector2I(0, 0),
                    Vector2I(0, 0),
                    Vector2I(1, 0),
                    Vector2I(2, 0),
                )
            ),
            Arguments.of(
                emptyArray<Vector2I>(),
                Array(4) { Vector2I.ZERO },
                0, 0, 0,
                Array(4) { Vector2I.ZERO },
            ),
        )

        @JvmStatic
        fun copyIntoThrowsExceptionArgs(): List<Arguments> = listOf(
            Arguments.of(
                Array(4) { Vector2I.ZERO },
                Array(4) { Vector2I.ZERO },
                0, 0, 5,
                IndexOutOfBoundsException::class.java
            ),
            Arguments.of(
                Array(4) { Vector2I.ZERO },
                Array(4) { Vector2I.ZERO },
                0, -1, 4,
                IndexOutOfBoundsException::class.java
            ),
            Arguments.of(
                Array(4) { Vector2I.ZERO },
                Array(4) { Vector2I.ZERO },
                0, 2, 1,
                IndexOutOfBoundsException::class.java
            ),
            Arguments.of(
                Array(4) { Vector2I.ZERO },
                Array(4) { Vector2I.ZERO },
                1, 0, 4,
                IndexOutOfBoundsException::class.java
            ),
            Arguments.of(
                Array(4) { Vector2I.ZERO },
                Array(4) { Vector2I.ZERO },
                4, 0, 1,
                IndexOutOfBoundsException::class.java
            ),
        )

        @JvmStatic
        fun copyOfArgs(): List<Arguments> = listOf(
            Arguments.of(emptyArray<Vector2I>(), emptyArray<Vector2I>()),
            Arguments.of(
                Array(4) { Vector2I(it, 0) },
                Array(4) { Vector2I(it, 0) }
            ),
        )

        @JvmStatic
        fun copyOfSizeArgs(): List<Arguments> = listOf(
            Arguments.of(emptyArray<Vector2I>(), 0, emptyArray<Vector2I>()),
            Arguments.of(emptyArray<Vector2I>(), 2, Array(2) { Vector2I.ZERO }),
            Arguments.of(
                Array(4) { Vector2I(it, 0) },
                3,
                Array(3) { Vector2I(it, 0) }
            ),
            Arguments.of(
                Array(4) { Vector2I(it, 0) },
                5,
                arrayOf(
                    Vector2I(0, 0),
                    Vector2I(1, 0),
                    Vector2I(2, 0),
                    Vector2I(3, 0),
                    Vector2I(0, 0),
                )
            ),
        )

        @JvmStatic
        fun copyOfRangeArgs(): List<Arguments> = listOf(
            Arguments.of(emptyArray<Vector2I>(), 0, 0, emptyArray<Vector2I>()),
            Arguments.of(
                Array(4) { Vector2I(it, 0) },
                0, 4,
                Array(4) { Vector2I(it, 0) }
            ),
            Arguments.of(
                Array(4) { Vector2I(it, 0) },
                1, 3,
                arrayOf(Vector2I(1, 0), Vector2I(2, 0))
            ),
        )

        @JvmStatic
        fun copyOfRangeThrowsExceptionArgs(): List<Arguments> = listOf(
            Arguments.of(emptyArray<Vector2I>(), 0, 1, IndexOutOfBoundsException::class.java),
            Arguments.of(
                Array(4) { Vector2I(it, 0) },
                0, 5,
                IndexOutOfBoundsException::class.java
            ),
            Arguments.of(
                Array(4) { Vector2I(it, 0) },
                -1, 4,
                IndexOutOfBoundsException::class.java
            ),
            Arguments.of(
                Array(4) { Vector2I(it, 0) },
                2, 1,
                IllegalArgumentException::class.java
            ),
        )

        @JvmStatic
        fun fillArgs(): List<Arguments> = listOf(
            Arguments.of(
                Array(4) { Vector2I.ZERO },
                Pair(1, 2),
                0, 4,
                Array(4) { Vector2I(1, 2) }
            ),
            Arguments.of(
                Array(4) { Vector2I.ZERO },
                Pair(1, 2),
                1, 3,
                arrayOf(Vector2I.ZERO, Vector2I(1, 2), Vector2I(1, 2), Vector2I.ZERO)
            ),
            Arguments.of(
                Array(4) { Vector2I.ZERO },
                Pair(1, 2),
                0, 0,
                Array(4) { Vector2I.ZERO }
            ),
            Arguments.of(
                emptyArray<Vector2I>(),
                Pair(1, 2),
                0, 0,
                emptyArray<Vector2I>(),
            ),
        )

        @JvmStatic
        fun fillThrowsExceptionArgs(): List<Arguments> = listOf(
            Arguments.of(
                Array(4) { Vector2I.ZERO },
                Pair(1, 2),
                0, 5,
                IndexOutOfBoundsException::class.java
            ),
            Arguments.of(
                Array(4) { Vector2I.ZERO },
                Pair(1, 2),
                -1, 4,
                IndexOutOfBoundsException::class.java
            ),
            Arguments.of(
                Array(4) { Vector2I.ZERO },
                Pair(1, 2),
                2, 1,
                IllegalArgumentException::class.java
            ),
        )

        @JvmStatic
        fun isEmptyArgs(): List<Arguments> = listOf(
            Arguments.of(Array(4) { Vector2I.ZERO }, false),
            Arguments.of(Array(0) { Vector2I.ZERO }, true),
        )

        @JvmStatic
        fun isNotEmptyArgs(): List<Arguments> = listOf(
            Arguments.of(Array(4) { Vector2I.ZERO }, true),
            Arguments.of(Array(0) { Vector2I.ZERO }, false),
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
            )
            return listOf(
                Arguments.of(
                    array, { v: Vector2I -> v.x.toDouble() + v.y.toDouble() }, 20.0
                ),
                Arguments.of(
                    array,
                    { v: Vector2I -> v.x.toDouble().absoluteValue + v.y.toDouble().absoluteValue },
                    26.0
                ),
            )
        }

        @JvmStatic
        fun sumOfIntArgs(): List<Arguments> {
            val array = arrayOf(
                Vector2I(1, 2), Vector2I(-2, -1), Vector2I(10, 10)
            )
            return listOf(
                Arguments.of(
                    array, { v: Vector2I -> v.x + v.y }, 20
                ),
                Arguments.of(
                    array,
                    { v: Vector2I -> v.x.absoluteValue + v.y.absoluteValue },
                    26
                ),
            )
        }

        @JvmStatic
        fun sumOfUIntArgs(): List<Arguments> {
            val array = arrayOf(
                Vector2I(3, 2), Vector2I(2, 1), Vector2I(10, 10)
            )
            return listOf(
                Arguments.of(array, { v: Vector2I -> v.x.toUInt() + v.y.toUInt() }, Wrapper(28u)),
                Arguments.of(array, { v: Vector2I -> v.x.toUInt() - v.y.toUInt() }, Wrapper(2u)),
            )
        }

        @JvmStatic
        fun sumOfLongArgs(): List<Arguments> {
            val array = arrayOf(
                Vector2I(1, 2), Vector2I(-2, -1), Vector2I(10, 10)
            )
            return listOf(
                Arguments.of(
                    array, { v: Vector2I -> v.x.toLong() + v.y.toLong() }, 20L
                ),
                Arguments.of(
                    array,
                    { v: Vector2I -> v.x.toLong().absoluteValue + v.y.toLong().absoluteValue },
                    26L
                ),
            )
        }

        @JvmStatic
        fun sumOfULongArgs(): List<Arguments> {
            val array = arrayOf(
                Vector2I(3, 2), Vector2I(2, 1), Vector2I(10, 10)
            )
            return listOf(
                Arguments.of(
                    array, { v: Vector2I -> v.x.toULong() + v.y.toULong() }, Wrapper(28uL)
                ),
                Arguments.of(
                    array, { v: Vector2I -> v.x.toULong() - v.y.toULong() }, Wrapper(2uL)
                ),
            )
        }

        @JvmStatic
        fun sumOfVector2IArgs(): List<Arguments> {
            val array = arrayOf(
                Vector2I(1, 2), Vector2I(-2, -1), Vector2I(10, 10)
            )
            return listOf(
                Arguments.of(
                    array, { v: Vector2I -> v }, Pair(9, 11)
                ),
                Arguments.of(
                    array,
                    { v: Vector2I -> Vector2I(v.x.absoluteValue, v.y.absoluteValue) },
                    Pair(13, 13)
                ),
            )
        }

        @JvmStatic
        fun sumArgs(): List<Arguments> {
            return listOf(
                Arguments.of(emptyArray<Vector2I>(), Pair(0, 0)),
                Arguments.of(
                    arrayOf(Vector2I(1, 2), Vector2I(-2, -1), Vector2I(10, 10)),
                    Pair(9, 11)
                ),
            )
        }

        @JvmStatic
        fun iteratorArgs(): List<Arguments> {
            val array = Array(4) { Vector2I(it, 0) }

            return listOf(
                Arguments.of(array, array.iterator()),
                Arguments.of(emptyArray<Vector2I>(), emptyArray<Vector2I>().iterator()),
            )
        }

        @JvmStatic
        fun getArgs(): List<Arguments> {
            val array = arrayOf(
                Vector2I(1, 0),
                Vector2I(-5, 8),
                Vector2I(2, -9),
                Vector2I(3, 4),
            )
            return listOf(
                Arguments.of(array, 0, Pair(1, 0)),
                Arguments.of(array, 3, Pair(3, 4)),
            )
        }

        @JvmStatic
        fun getOrElseArgs(): List<Arguments> {
            val array = arrayOf(
                Vector2I(1, 0),
                Vector2I(-5, 8),
                Vector2I(3, 4),
            )
            return listOf(
                Arguments.of(
                    array, 0, { i: Int -> Vector2I(i, i) }, Pair(1, 0)
                ),
                Arguments.of(
                    array, -1, { i: Int -> Vector2I(i, i) }, Pair(-1, -1)
                ),
                Arguments.of(
                    array, 3, { i: Int -> Vector2I(i, i) }, Pair(3, 3)
                ),
            )
        }

        @JvmStatic
        fun getOrNullArgs(): List<Arguments> {
            val array = arrayOf(
                Vector2I(1, 0),
                Vector2I(-5, 8),
                Vector2I(3, 4),
            )
            return listOf(
                Arguments.of(array, 0, Pair(1, 0)),
                Arguments.of(array, -1, null),
                Arguments.of(array, 3, null),
            )
        }

        @JvmStatic
        fun setArgs(): List<Arguments> = listOf(
            Arguments.of(
                Array(4) { Vector2I.ZERO },
                0,
                Pair(1, 0),
                arrayOf(Vector2I(1, 0), Vector2I.ZERO, Vector2I.ZERO, Vector2I.ZERO),
            ),
            Arguments.of(
                Array(4) { Vector2I.ZERO },
                3,
                Pair(3, 4),
                arrayOf(Vector2I.ZERO, Vector2I.ZERO, Vector2I.ZERO, Vector2I(3, 4))
            ),
        )

        @JvmStatic
        fun arrayPlusVector2IArgs(): List<Arguments> = listOf(
            Arguments.of(
                emptyArray<Vector2I>(), Pair(1, 0), arrayOf(Vector2I(1, 0))
            ),
            Arguments.of(
                arrayOf(Vector2I(1, 0), Vector2I(2, 0)),
                Pair(3, 0),
                arrayOf(Vector2I(1, 0), Vector2I(2, 0), Vector2I(3, 0)),
            ),
        )

        @JvmStatic
        fun arrayPlusCollectionArgs(): List<Arguments> = listOf(
            Arguments.of(
                emptyArray<Vector2I>(), listOf(Vector2I(1, 0)), arrayOf(Vector2I(1, 0)),
            ),
            Arguments.of(
                arrayOf(Vector2I(1, 0), Vector2I(2, 0)),
                listOf(Vector2I(3, 0), Vector2I(4, 0)),
                arrayOf(
                    Vector2I(1, 0),
                    Vector2I(2, 0),
                    Vector2I(3, 0),
                    Vector2I(4, 0)
                ),
            ),
        )

        @JvmStatic
        fun arrayPlusArrayArgs(): List<Arguments> = listOf(
            Arguments.of(
                emptyArray<Vector2I>(), arrayOf(Vector2I(1, 0)), arrayOf(Vector2I(1, 0)),
            ),
            Arguments.of(
                arrayOf(Vector2I(1, 0), Vector2I(2, 0)),
                arrayOf(Vector2I(3, 0), Vector2I(4, 0)),
                arrayOf(
                    Vector2I(1, 0),
                    Vector2I(2, 0),
                    Vector2I(3, 0),
                    Vector2I(4, 0)
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
        fun typedArraySumOfVector2IArgs(): List<Arguments> = sumOfVector2IArgs()

        @JvmStatic
        fun typedArraySumArgs(): List<Arguments> = sumArgs()
    }
}