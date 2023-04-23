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
    @MethodSource("containsAllArgs")
    fun containsAllReturnsCorrectValue(
        array: Array<Vector2F>, elements: Collection<Vector2F>, expected: Boolean
    ) {
        val actual: Boolean = array.toVector2FArray().containsAll(elements)

        assertEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("elementAtArgs")
    fun elementAtReturnsCorrectValue(
        array: Array<Vector2F>, index: Int, exp: Wrapper<Vector2F>
    ) {
        val expected: Vector2F = exp.value
        val actual: Vector2F = array.toVector2FArray().elementAt(index)

        assertEquals(expected, actual)
    }

    @Test
    fun elementAtThrowsWhenIndexIsOutOfBounds() {
        val array = Vector2FArray(4)

        assertThrows<IndexOutOfBoundsException> { array.elementAt(-1) }
        assertThrows<IndexOutOfBoundsException> { array.elementAt(4) }
    }

    @ParameterizedTest
    @MethodSource("elementAtOrElseArgs")
    fun elementAtOrElseReturnsCorrectValue(
        array: Array<Vector2F>,
        index: Int,
        defaultValue: (Int) -> Vector2F,
        exp: Wrapper<Vector2F>
    ) {
        val expected: Vector2F = exp.value
        val actual: Vector2F = array.toVector2FArray().elementAtOrElse(index, defaultValue)

        assertEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("elementAtOrNullArgs")
    fun elementAtOrNullReturnsCorrectValue(
        array: Array<Vector2F>,
        index: Int,
        exp: Wrapper<Vector2F>?
    ) {
        val expected: Vector2F? = exp?.let { exp.value }
        val actual: Vector2F? = array.toVector2FArray().elementAtOrNull(index)

        assertEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("findArgs")
    fun findReturnsCorrectValue(
        array: Array<Vector2F>,
        predicate: (Vector2F) -> Boolean,
        exp: Wrapper<Vector2F>?
    ) {
        val expected: Vector2F? = exp?.let { exp.value }
        val actual: Vector2F? = array.toVector2FArray().find(predicate)

        assertEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("findLastArgs")
    fun findLastReturnsCorrectValue(
        array: Array<Vector2F>,
        predicate: (Vector2F) -> Boolean,
        exp: Wrapper<Vector2F>?
    ) {
        val expected: Vector2F? = exp?.let { exp.value }
        val actual: Vector2F? = array.toVector2FArray().findLast(predicate)

        assertEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("firstArgs")
    fun firstReturnsCorrectValue(array: Array<Vector2F>, exp: Wrapper<Vector2F>) {
        val expected: Vector2F = exp.value
        val actual: Vector2F = array.toVector2FArray().first()

        assertEquals(expected, actual)
    }

    @Test
    fun firstThrowsWhenArrayIsEmpty() {
        assertThrows<NoSuchElementException> { Vector2FArray(0).first() }
    }

    @ParameterizedTest
    @MethodSource("firstPredicateArgs")
    fun firstReturnsCorrectValue(
        array: Array<Vector2F>, predicate: (Vector2F) -> Boolean, exp: Wrapper<Vector2F>
    ) {
        val expected: Vector2F = exp.value
        val actual: Vector2F = array.toVector2FArray().first(predicate)

        assertEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("firstPredicateNotMatchArgs")
    fun firstThrowsWhenNoElementMatchesThePredicate(
        array: Array<Vector2F>, predicate: (Vector2F) -> Boolean
    ) {
        assertThrows<NoSuchElementException> { array.first(predicate) }
    }

    @ParameterizedTest
    @MethodSource("firstOrNullArgs")
    fun firstOrNullReturnsCorrectValue(array: Array<Vector2F>, exp: Wrapper<Vector2F>?) {
        val expected: Vector2F? = exp?.let { exp.value }
        val actual: Vector2F? = array.toVector2FArray().firstOrNull()

        assertEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("firstOrNullPredicateArgs")
    fun firstOrNullReturnsCorrectValue(
        array: Array<Vector2F>, predicate: (Vector2F) -> Boolean, exp: Wrapper<Vector2F>?
    ) {
        val expected: Vector2F? = exp?.let { exp.value }
        val actual: Vector2F? = array.toVector2FArray().firstOrNull(predicate)

        assertEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("getOrElseArgs")
    fun getOrElseReturnsCorrectValue(
        array: Array<Vector2F>,
        index: Int,
        defaultValue: (Int) -> Vector2F,
        exp: Wrapper<Vector2F>
    ) {
        val expected: Vector2F = exp.value
        val actual: Vector2F = array.toVector2FArray().getOrElse(index, defaultValue)

        assertEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("getOrNullArgs")
    fun getOrNullReturnsCorrectValue(
        array: Array<Vector2F>, index: Int, exp: Wrapper<Vector2F>?
    ) {
        val expected: Vector2F? = exp?.let { exp.value }
        val actual: Vector2F? = array.toVector2FArray().getOrNull(index)

        assertEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("indexOfArgs")
    fun indexOfReturnsCorrectValue(
        array: Array<Vector2F>, el: Wrapper<Vector2F>, expected: Int
    ) {
        val element: Vector2F = el.value
        val actual: Int = array.toVector2FArray().indexOf(element)

        assertEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("indexOfFirstArgs")
    fun indexOfFirstReturnsCorrectValue(
        array: Array<Vector2F>, predicate: (Vector2F) -> Boolean, expected: Int
    ) {
        val actual: Int = array.toVector2FArray().indexOfFirst(predicate)

        assertEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("lastArgs")
    fun lastReturnsCorrectValue(array: Array<Vector2F>, exp: Wrapper<Vector2F>) {
        val expected: Vector2F = exp.value
        val actual: Vector2F = array.toVector2FArray().last()

        assertEquals(expected, actual)
    }

    @Test
    fun lastThrowsWhenArrayIsEmpty() {
        assertThrows<NoSuchElementException> { Vector2FArray(0).last() }
    }

    @ParameterizedTest
    @MethodSource("lastPredicateArgs")
    fun lastReturnsCorrectValue(
        array: Array<Vector2F>, predicate: (Vector2F) -> Boolean, exp: Wrapper<Vector2F>
    ) {
        val expected: Vector2F = exp.value
        val actual: Vector2F = array.toVector2FArray().last(predicate)

        assertEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("lastPredicateNotMatchArgs")
    fun lastThrowsWhenNoElementMatchesThePredicate(
        array: Array<Vector2F>, predicate: (Vector2F) -> Boolean
    ) {
        assertThrows<NoSuchElementException> { array.last(predicate) }
    }

    @ParameterizedTest
    @MethodSource("lastIndexOfArgs")
    fun lastIndexOfReturnsCorrectValue(
        array: Array<Vector2F>, el: Wrapper<Vector2F>, expected: Int
    ) {
        val element: Vector2F = el.value
        val actual: Int = array.toVector2FArray().lastIndexOf(element)

        assertEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("lastOrNullArgs")
    fun lastOrNullReturnsCorrectValue(array: Array<Vector2F>, exp: Wrapper<Vector2F>?) {
        val expected: Vector2F? = exp?.let { exp.value }
        val actual: Vector2F? = array.toVector2FArray().lastOrNull()

        assertEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("lastOrNullPredicateArgs")
    fun lastOrNullReturnsCorrectValue(
        array: Array<Vector2F>, predicate: (Vector2F) -> Boolean, exp: Wrapper<Vector2F>?
    ) {
        val expected: Vector2F? = exp?.let { exp.value }
        val actual: Vector2F? = array.toVector2FArray().lastOrNull(predicate)

        assertEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("randomArgs")
    fun randomReturnsCorrectValue(
        array: Array<Vector2F>, random: Random, exp: Wrapper<Vector2F>
    ) {
        val expected: Vector2F = exp.value
        val actual: Vector2F = array.toVector2FArray().random(random)

        assertEquals(expected, actual)
    }

    @Test
    fun randomThrowsWhenArrayIsEmpty() {
        assertThrows<NoSuchElementException> { Vector2FArray(0).random() }
        assertThrows<NoSuchElementException> { Vector2FArray(0).random(Random) }
    }

    @ParameterizedTest
    @MethodSource("randomOrNullArgs")
    fun randomOrNullReturnsCorrectValue(
        array: Array<Vector2F>, random: Random, exp: Wrapper<Vector2F>?
    ) {
        val expected: Vector2F? = exp?.let { exp.value }
        val actual: Vector2F? = array.toVector2FArray().randomOrNull(random)

        assertEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("singleArgs")
    fun singleReturnsCorrectValue(array: Array<Vector2F>, exp: Wrapper<Vector2F>) {
        val expected: Vector2F = exp.value
        val actual: Vector2F = array.toVector2FArray().single()

        assertEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("singleThrowsExceptionArgs")
    fun <T : Throwable> singleThrowsCorrectException(
        array: Array<Vector2F>, expectedExceptionClass: Class<T>
    ) {
        assertThrows(expectedExceptionClass) {
            array.toVector2FArray().single()
        }
    }

    @ParameterizedTest
    @MethodSource("singlePredicateArgs")
    fun singleReturnsCorrectValue(
        array: Array<Vector2F>, predicate: (Vector2F) -> Boolean, exp: Wrapper<Vector2F>
    ) {
        val expected: Vector2F = exp.value
        val actual: Vector2F = array.toVector2FArray().single(predicate)

        assertEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("singlePredicateThrowsExceptionArgs")
    fun <T : Throwable> singleThrowsCorrectException(
        array: Array<Vector2F>, predicate: (Vector2F) -> Boolean, expectedExceptionClass: Class<T>
    ) {
        assertThrows(expectedExceptionClass) {
            array.toVector2FArray().single(predicate)
        }
    }

    @ParameterizedTest
    @MethodSource("singleOrNullArgs")
    fun singleOrNullReturnsCorrectValue(array: Array<Vector2F>, exp: Wrapper<Vector2F>?) {
        val expected: Vector2F? = exp?.let { exp.value }
        val actual: Vector2F? = array.toVector2FArray().singleOrNull()

        assertEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("dropArgs")
    fun dropReturnsCorrectValue(array: Array<Vector2F>, n: Int, expected: List<Vector2F>) {
        val actual: List<Vector2F> = array.toVector2FArray().drop(n)

        assertContentEquals(expected, actual)
    }

    @Test
    fun dropThrowsWhenRequestedCountIsNegative() {
        assertThrows<IllegalArgumentException> { Vector2FArray(0).drop(-1) }
    }

    @ParameterizedTest
    @MethodSource("dropLastArgs")
    fun dropLastReturnsCorrectValue(array: Array<Vector2F>, n: Int, expected: List<Vector2F>) {
        val actual: List<Vector2F> = array.toVector2FArray().dropLast(n)

        assertContentEquals(expected, actual)
    }

    @Test
    fun dropLastThrowsWhenRequestedCountIsNegative() {
        assertThrows<IllegalArgumentException> { Vector2FArray(0).dropLast(-1) }
    }

    @ParameterizedTest
    @MethodSource("dropLastWhileArgs")
    fun dropLastWhileReturnsCorrectValue(
        array: Array<Vector2F>, predicate: (Vector2F) -> Boolean, expected: List<Vector2F>
    ) {
        val actual: List<Vector2F> = array.toVector2FArray().dropLastWhile(predicate)

        assertContentEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("dropWhileArgs")
    fun dropWhileReturnsCorrectValue(
        array: Array<Vector2F>, predicate: (Vector2F) -> Boolean, expected: List<Vector2F>
    ) {
        val actual: List<Vector2F> = array.toVector2FArray().dropWhile(predicate)

        assertContentEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("sliceRangeArgs")
    fun sliceReturnsCorrectValue(
        array: Array<Vector2F>, indices: IntRange, expected: List<Vector2F>
    ) {
        val actual: List<Vector2F> = array.toVector2FArray().slice(indices)

        assertContentEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("sliceRangeThrowsExceptionArgs")
    fun <T : Throwable> sliceThrowsCorrectException(
        array: Array<Vector2F>, indices: IntRange, expectedExceptionClass: Class<T>
    ) {
        assertThrows(expectedExceptionClass) {
            array.toVector2FArray().slice(indices)
        }
    }

    @ParameterizedTest
    @MethodSource("sliceIterableArgs")
    fun sliceReturnsCorrectValue(
        array: Array<Vector2F>, indices: Iterable<Int>, expected: List<Vector2F>
    ) {
        val actual: List<Vector2F> = array.toVector2FArray().slice(indices)

        assertContentEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("sliceIterableThrowsExceptionArgs")
    fun <T : Throwable> sliceThrowsCorrectException(
        array: Array<Vector2F>, indices: Iterable<Int>, expectedExceptionClass: Class<T>
    ) {
        assertThrows(expectedExceptionClass) {
            array.toVector2FArray().slice(indices)
        }
    }

    @ParameterizedTest
    @MethodSource("sliceArrayCollectionArgs")
    fun sliceArrayReturnsCorrectValue(
        array: Array<Vector2F>, indices: Collection<Int>, exp: Array<Vector2F>
    ) {
        val expected = exp.toVector2FArray()
        val actual: Vector2FArray = array.toVector2FArray().sliceArray(indices)

        assertContentEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("sliceArrayCollectionThrowsExceptionArgs")
    fun <T : Throwable> sliceArrayThrowsCorrectException(
        array: Array<Vector2F>, indices: Collection<Int>, expectedExceptionClass: Class<T>
    ) {
        assertThrows(expectedExceptionClass) {
            array.toVector2FArray().sliceArray(indices)
        }
    }

    @ParameterizedTest
    @MethodSource("sliceArrayRangeArgs")
    fun sliceArrayReturnsCorrectValue(
        array: Array<Vector2F>, indices: IntRange, exp: Array<Vector2F>
    ) {
        val expected = exp.toVector2FArray()
        val actual: Vector2FArray = array.toVector2FArray().sliceArray(indices)

        assertContentEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("sliceArrayRangeThrowsExceptionArgs")
    fun <T : Throwable> sliceArrayThrowsCorrectException(
        array: Array<Vector2F>, indices: IntRange, expectedExceptionClass: Class<T>
    ) {
        assertThrows(expectedExceptionClass) {
            array.toVector2FArray().sliceArray(indices)
        }
    }

    @ParameterizedTest
    @MethodSource("takeArgs")
    fun takeReturnsCorrectValue(array: Array<Vector2F>, n: Int, expected: List<Vector2F>) {
        val actual: List<Vector2F> = array.toVector2FArray().take(n)

        assertContentEquals(expected, actual)
    }

    @Test
    fun takeThrowsWhenRequestedCountIsNegative() {
        assertThrows<IllegalArgumentException> { Vector2FArray(0).take(-1) }
    }

    @ParameterizedTest
    @MethodSource("takeLastArgs")
    fun takeLastReturnsCorrectValue(array: Array<Vector2F>, n: Int, expected: List<Vector2F>) {
        val actual: List<Vector2F> = array.toVector2FArray().takeLast(n)

        assertContentEquals(expected, actual)
    }

    @Test
    fun takeLastThrowsWhenRequestedCountIsNegative() {
        assertThrows<IllegalArgumentException> { Vector2FArray(0).takeLast(-1) }
    }

    @ParameterizedTest
    @MethodSource("takeLastWhileArgs")
    fun takeLastWhileReturnsCorrectValue(
        array: Array<Vector2F>, predicate: (Vector2F) -> Boolean, expected: List<Vector2F>
    ) {
        val actual: List<Vector2F> = array.toVector2FArray().takeLastWhile(predicate)

        assertContentEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("takeWhileArgs")
    fun takeWhileReturnsCorrectValue(
        array: Array<Vector2F>, predicate: (Vector2F) -> Boolean, expected: List<Vector2F>
    ) {
        val actual: List<Vector2F> = array.toVector2FArray().takeWhile(predicate)

        assertContentEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("reverseArgs")
    fun reverseMutatesArrayCorrectly(array: Array<Vector2F>, exp: Array<Vector2F>) {
        val expected = exp.toVector2FArray()
        val actual: Vector2FArray = array.toVector2FArray().apply {
            reverse()
        }
        assertContentEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("reverseRangeArgs")
    fun reverseMutatesArrayCorrectly(
        array: Array<Vector2F>, fromIndex: Int, toIndex: Int, exp: Array<Vector2F>
    ) {
        val expected = exp.toVector2FArray()
        val actual: Vector2FArray = array.toVector2FArray().apply {
            reverse(fromIndex, toIndex)
        }
        assertContentEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("reverseRangeThrowsExceptionArgs")
    fun <T : Throwable> reverseThrowsCorrectException(
        array: Array<Vector2F>, fromIndex: Int, toIndex: Int, expectedExceptionClass: Class<T>
    ) {
        assertThrows(expectedExceptionClass) {
            array.toVector2FArray().reverse(fromIndex, toIndex)
        }
    }

    @ParameterizedTest
    @MethodSource("reversedArgs")
    fun reversedReturnsCorrectValue(array: Array<Vector2F>, expected: List<Vector2F>) {
        val actual: List<Vector2F> = array.toVector2FArray().reversed()

        assertContentEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("reversedArrayArgs")
    fun reversedArrayReturnsCorrectValue(array: Array<Vector2F>, exp: Array<Vector2F>) {
        val expected = exp.toVector2FArray()
        val actual: Vector2FArray = array.toVector2FArray().reversedArray()

        assertContentEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("shuffleArgs")
    fun shuffleReturnsCorrectValue(
        array: Array<Vector2F>, random: Random, exp: Array<Vector2F>
    ) {
        val expected = exp.toVector2FArray()
        val actual: Vector2FArray = array.toVector2FArray()
        actual.shuffle(random)

        assertContentEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("sortedByArgs")
    fun <R : Comparable<R>> sortedByReturnsCorrectValue(
        array: Array<Vector2F>, selector: (Vector2F) -> R?, expected: List<Vector2F>
    ) {
        val actual: List<Vector2F> = array.toVector2FArray().sortedBy(selector)

        assertContentEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("sortedByDescendingArgs")
    fun <R : Comparable<R>> sortedByDescendingReturnsCorrectValue(
        array: Array<Vector2F>, selector: (Vector2F) -> R?, expected: List<Vector2F>
    ) {
        val actual: List<Vector2F> = array.toVector2FArray().sortedByDescending(selector)

        assertContentEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("sortedWithArgs")
    fun sortedWithReturnsCorrectValue(
        array: Array<Vector2F>, comparator: Comparator<in Vector2F>, expected: List<Vector2F>
    ) {
        val actual: List<Vector2F> = array.toVector2FArray().sortedWith(comparator)

        assertContentEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("asListArgs")
    fun asListReturnsCorrectValue(array: Array<Vector2F>, expected: List<Vector2F>) {
        val actual: List<Vector2F> = array.toVector2FArray().asList()

        assertContentEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("copyIntoArgs")
    fun copyIntoReturnsCorrectValue(
        array: Array<Vector2F>,
        dest: Array<Vector2F>,
        destinationOffset: Int,
        startIndex: Int,
        endIndex: Int,
        exp: Array<Vector2F>
    ) {
        val destination: Vector2FArray = dest.toVector2FArray()
        val expected = exp.toVector2FArray()
        val actual: Vector2FArray = array.toVector2FArray().copyInto(
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
        array: Array<Vector2F>,
        dest: Array<Vector2F>,
        destinationOffset: Int,
        startIndex: Int,
        endIndex: Int,
        expectedExceptionClass: Class<T>
    ) {
        val destination: Vector2FArray = dest.toVector2FArray()

        assertThrows(expectedExceptionClass) {
            array.toVector2FArray().copyInto(
                destination,
                destinationOffset,
                startIndex,
                endIndex
            )
        }
    }

    @ParameterizedTest
    @MethodSource("copyOfArgs")
    fun copyOfReturnsCorrectValue(array: Array<Vector2F>, exp: Array<Vector2F>) {
        val expected = exp.toVector2FArray()
        val actual: Vector2FArray = array.toVector2FArray().copyOf()

        assertContentEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("copyOfSizeArgs")
    fun copyOfReturnsCorrectValue(
        array: Array<Vector2F>, newSize: Int, exp: Array<Vector2F>
    ) {
        val expected = exp.toVector2FArray()
        val actual: Vector2FArray = array.toVector2FArray().copyOf(newSize)

        assertContentEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("copyOfRangeArgs")
    fun copyOfRangeReturnsCorrectValue(
        array: Array<Vector2F>, fromIndex: Int, toIndex: Int, exp: Array<Vector2F>
    ) {
        val expected = exp.toVector2FArray()
        val actual: Vector2FArray = array.toVector2FArray().copyOfRange(fromIndex, toIndex)

        assertContentEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("copyOfRangeThrowsExceptionArgs")
    fun <T : Throwable> copyOfRangeThrowsCorrectException(
        array: Array<Vector2F>, fromIndex: Int, toIndex: Int, expectedExceptionClass: Class<T>
    ) {
        assertThrows(expectedExceptionClass) {
            array.toVector2FArray().copyOfRange(fromIndex, toIndex)
        }
    }

    @ParameterizedTest
    @MethodSource("fillArgs")
    fun fillMutatesArrayCorrectly(
        array: Array<Vector2F>,
        el: Wrapper<Vector2F>,
        fromIndex: Int,
        toIndex: Int,
        exp: Array<Vector2F>
    ) {
        val element: Vector2F = el.value
        val expected = exp.toVector2FArray()
        val actual: Vector2FArray = array.toVector2FArray().apply {
            fill(element, fromIndex, toIndex)
        }
        assertContentEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("fillThrowsExceptionArgs")
    fun <T : Throwable> fillThrowsCorrectException(
        array: Array<Vector2F>,
        el: Wrapper<Vector2F>,
        fromIndex: Int,
        toIndex: Int,
        expectedExceptionClass: Class<T>
    ) {
        val element: Vector2F = el.value

        assertThrows(expectedExceptionClass) {
            array.toVector2FArray().fill(element, fromIndex, toIndex)
        }
    }

    @ParameterizedTest
    @MethodSource("isEmptyArgs")
    fun isEmptyReturnsCorrectValue(array: Array<Vector2F>, expected: Boolean) {
        val actual: Boolean = array.toVector2FArray().isEmpty()

        assertEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("isNotEmptyArgs")
    fun isNotEmptyReturnsCorrectValue(array: Array<Vector2F>, expected: Boolean) {
        val actual: Boolean = array.toVector2FArray().isNotEmpty()

        assertEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("toTypedArrayArgs")
    fun toTypedArrayReturnsCorrectValue(array: Wrapper<Vector2FArray>, expected: Array<Vector2F>) {
        val actual: Array<Vector2F> = array.value.toTypedArray()

        assertContentEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("arrays")
    fun forEachIteratesThroughArrayCorrectly(array: Array<Vector2F>) {
        var expectedIndex = 0

        array.forEach { actualItem ->
            val expectedItem: Vector2F = array[expectedIndex]

            assertEquals(expectedItem, actualItem)
            expectedIndex++
        }
    }

    @ParameterizedTest
    @MethodSource("arrays")
    fun forEachIndexedIteratesThroughArrayCorrectly(array: Array<Vector2F>) {
        var expectedIndex = 0

        array.forEachIndexed { actualIndex, actualItem ->
            val expectedItem: Vector2F = array[expectedIndex]

            assertEquals(expectedIndex, actualIndex)
            assertEquals(expectedItem, actualItem)
            expectedIndex++
        }
    }

    @ParameterizedTest
    @MethodSource("noneArgs")
    fun noneReturnsCorrectValue(array: Array<Vector2F>, expected: Boolean) {
        val actual: Boolean = array.toVector2FArray().none()

        assertEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("sumOfDoubleArgs")
    fun sumOfReturnsCorrectValue(
        array: Array<Vector2F>, selector: (Vector2F) -> Double, expected: Double
    ) {
        val actual: Double = array.toVector2FArray().sumOf(selector)

        assertTrue(expected.isApproximately(actual))
    }

    @ParameterizedTest
    @MethodSource("sumOfIntArgs")
    fun sumOfReturnsCorrectValue(
        array: Array<Vector2F>, selector: (Vector2F) -> Int, expected: Int
    ) {
        val actual: Int = array.toVector2FArray().sumOf(selector)

        assertEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("sumOfUIntArgs")
    fun sumOfUIntReturnsCorrectValue(
        array: Array<Vector2F>, selector: (Vector2F) -> UInt, exp: Wrapper<UInt>
    ) {
        val expected: UInt = exp.value
        val actual: UInt = array.toVector2FArray().sumOf(selector)

        assertEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("sumOfLongArgs")
    fun sumOfReturnsCorrectValue(
        array: Array<Vector2F>, selector: (Vector2F) -> Long, expected: Long
    ) {
        val actual: Long = array.toVector2FArray().sumOf(selector)

        assertEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("sumOfULongArgs")
    fun sumOfULongReturnsCorrectValue(
        array: Array<Vector2F>, selector: (Vector2F) -> ULong, exp: Wrapper<ULong>
    ) {
        val expected: ULong = exp.value
        val actual: ULong = array.toVector2FArray().sumOf(selector)

        assertEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("sumOfVector2FArgs")
    fun sumOfReturnsCorrectValue(
        array: Array<Vector2F>, selector: (Vector2F) -> Vector2F, exp: Wrapper<Vector2F>
    ) {
        val expected: Vector2F = exp.value
        val actual: Vector2F = array.toVector2FArray().sumOf(selector)

        assertTrue(expected.isApproximately(actual))
    }

    @ParameterizedTest
    @MethodSource("sumArgs")
    fun sumReturnsCorrectValue(array: Array<Vector2F>, exp: Wrapper<Vector2F>) {
        val expected: Vector2F = exp.value
        val actual: Vector2F = array.toVector2FArray().sum()

        assertTrue(expected.isApproximately(actual))
    }

    @ParameterizedTest
    @MethodSource("containsArgs")
    fun containsReturnsCorrectValue(
        array: Array<Vector2F>, element: Wrapper<Vector2F>, expected: Boolean
    ) {
        val actual: Boolean = array
            .toVector2FArray().contains(element.value)

        assertEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("iteratorArgs")
    fun iteratorReturnsCorrectValue(array: Array<Vector2F>, expected: Iterator<Vector2F>) {
        val actual = array.toVector2FArray().iterator()

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
        val array = Vector2FArray(4)

        assertThrows<IndexOutOfBoundsException> { array[-1] }
        assertThrows<IndexOutOfBoundsException> { array[4] }
    }

    @ParameterizedTest
    @MethodSource("getArgs")
    fun getReturnsCorrectValue(
        array: Array<Vector2F>, index: Int, exp: Wrapper<Vector2F>
    ) {
        val expected: Vector2F = exp.value
        val actual: Vector2F = array.toVector2FArray()[index]

        assertEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("setArgs")
    fun setMutatesArrayCorrectly(
        array: Array<Vector2F>, index: Int, value: Wrapper<Vector2F>, exp: Array<Vector2F>
    ) {
        val expected = exp.toVector2FArray()
        val actual = array.toVector2FArray()
        actual[index] = value.value

        assertContentEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("arrayPlusVector2FArgs")
    fun arrayPlusVector2FReturnsCorrectValue(
        array: Array<Vector2F>, el: Wrapper<Vector2F>, exp: Array<Vector2F>
    ) {
        val element: Vector2F = el.value
        val expected = exp.toVector2FArray()
        val actual: Vector2FArray = array.toVector2FArray() + element

        assertContentEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("arrayPlusCollectionArgs")
    fun arrayPlusCollectionReturnsCorrectValue(
        array: Array<Vector2F>, elements: Collection<Vector2F>, exp: Array<Vector2F>
    ) {
        val expected = exp.toVector2FArray()
        val actual: Vector2FArray = array.toVector2FArray() + elements

        assertContentEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("arrayPlusArrayArgs")
    fun arrayPlusArrayReturnsCorrectValue(
        array: Array<Vector2F>, elems: Array<Vector2F>, exp: Array<Vector2F>
    ) {
        val elements = elems.toVector2FArray()
        val expected = exp.toVector2FArray()
        val actual: Vector2FArray = array.toVector2FArray() + elements

        assertContentEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("typedArrayToVector2FArrayArgs")
    fun typedArrayToVector2FArrayReturnsCorrectValue(
        array: Array<Vector2F>, exp: Wrapper<Vector2FArray>
    ) {
        val expected: Vector2FArray = exp.value
        val actual = array.toVector2FArray()

        assertContentEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("typedArraySumOfVector2FArgs")
    fun <T> typedArraySumOfReturnsCorrectValue(
        array: Array<T>, selector: (T) -> Vector2F, exp: Wrapper<Vector2F>
    ) {
        val expected: Vector2F = exp.value
        val actual: Vector2F = array.sumOf(selector)

        assertEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("typedArraySumArgs")
    fun typedArraySumReturnsCorrectValue(array: Array<Vector2F>, exp: Wrapper<Vector2F>) {
        val expected: Vector2F = exp.value
        val actual: Vector2F = array.sum()

        assertEquals(expected, actual)
    }

    companion object {
        @JvmStatic
        fun arrays(): List<Arguments> = listOf(
            Arguments.of(Array(4) { Vector2F(it.toFloat(), -it.toFloat()) }),
            Arguments.of(arrayOf(Vector2F(-3f, 5f), Vector2F(-1f, 7f))),
            Arguments.of(emptyArray<Vector2F>()),
        )

        @JvmStatic
        fun containsAllArgs(): List<Arguments> {
            val array = arrayOf(
                Vector2F(1f, 0f),
                Vector2F(-5f, 8f),
                Vector2F(2f, -9f),
            )
            return listOf(
                Arguments.of(array, array.toList(), true),
                Arguments.of(array, listOf(Vector2F(1f, 0f), Vector2F(-5f, 8f)), true),
                Arguments.of(array, emptyList<Vector2F>(), true),
                Arguments.of(array, listOf(Vector2F.ZERO), false),
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
            Arguments.of(arrayOf(Vector2F(4f, 5f), Vector2F(1f, 2f)), Wrapper(Vector2F(4f, 5f))),
            Arguments.of(Array(2) { Vector2F(it.toFloat(), 0f) }, Wrapper(Vector2F(0f, 0f))),
        )

        @JvmStatic
        fun firstPredicateArgs(): List<Arguments> = listOf(
            Arguments.of(
                arrayOf(Vector2F(4f, 5f), Vector2F(1f, 2f)),
                { v: Vector2F -> v == Vector2F(1f, 2f) },
                Wrapper(Vector2F(1f, 2f))
            ),
            Arguments.of(
                Array(2) { Vector2F(it.toFloat(), 0f) },
                { v: Vector2F -> v.x >= 1f },
                Wrapper(Vector2F(1f, 0f))
            ),
        )

        @JvmStatic
        fun firstPredicateNotMatchArgs() = listOf(
            Arguments.of(
                arrayOf(Vector2F(4f, 5f), Vector2F(1f, 2f)),
                { v: Vector2F -> v == Vector2F(4f, 2f) }),
            Arguments.of(
                Array(2) { Vector2F(it.toFloat(), 0f) },
                { v: Vector2F -> v.x >= 2f }
            ),
        )

        @JvmStatic
        fun firstOrNullArgs(): List<Arguments> = listOf(
            Arguments.of(arrayOf(Vector2F(4f, 5f), Vector2F(1f, 2f)), Wrapper(Vector2F(4f, 5f))),
            Arguments.of(Array(2) { Vector2F(it.toFloat(), 0f) }, Wrapper(Vector2F(0f, 0f))),
            Arguments.of(emptyArray<Vector2F>(), null),
        )

        @JvmStatic
        fun firstOrNullPredicateArgs(): List<Arguments> = listOf(
            Arguments.of(
                arrayOf(Vector2F(4f, 5f), Vector2F(1f, 2f)),
                { v: Vector2F -> v == Vector2F(1f, 2f) },
                Wrapper(Vector2F(1f, 2f))
            ),
            Arguments.of(
                Array(2) { Vector2F(it.toFloat(), 0f) },
                { v: Vector2F -> v.x >= 1f },
                Wrapper(Vector2F(1f, 0f))
            ),
            Arguments.of(
                Array(2) { Vector2F(it.toFloat(), 0f) },
                { v: Vector2F -> v.x >= 2f },
                null
            ),
            Arguments.of(
                emptyArray<Vector2F>(),
                { _: Vector2F -> true },
                null
            ),
        )

        @JvmStatic
        fun lastArgs(): List<Arguments> = listOf(
            Arguments.of(arrayOf(Vector2F(4f, 5f), Vector2F(1f, 2f)), Wrapper(Vector2F(1f, 2f))),
            Arguments.of(Array(2) { Vector2F(it.toFloat(), 0f) }, Wrapper(Vector2F(1f, 0f))),
        )

        @JvmStatic
        fun lastPredicateArgs(): List<Arguments> = listOf(
            Arguments.of(
                arrayOf(Vector2F(4f, 5f), Vector2F(1f, 2f)),
                { v: Vector2F -> v == Vector2F(1f, 2f) },
                Wrapper(Vector2F(1f, 2f))
            ),
            Arguments.of(
                Array(2) { Vector2F(it.toFloat(), 0f) },
                { v: Vector2F -> v.x <= 1f },
                Wrapper(Vector2F(1f, 0f))
            ),
        )

        @JvmStatic
        fun lastPredicateNotMatchArgs(): List<Arguments> = listOf(
            Arguments.of(
                arrayOf(Vector2F(4f, 5f), Vector2F(1f, 2f)),
                { v: Vector2F -> v == Vector2F(4f, 2f) }),
            Arguments.of(
                Array(2) { Vector2F(it.toFloat(), 0f) },
                { v: Vector2F -> v.x >= 2f }
            ),
        )

        @JvmStatic
        fun lastOrNullArgs(): List<Arguments> = listOf(
            Arguments.of(arrayOf(Vector2F(4f, 5f), Vector2F(1f, 2f)), Wrapper(Vector2F(1f, 2f))),
            Arguments.of(Array(2) { Vector2F(it.toFloat(), 0f) }, Wrapper(Vector2F(1f, 0f))),
            Arguments.of(emptyArray<Vector2F>(), null),
        )

        @JvmStatic
        fun lastOrNullPredicateArgs(): List<Arguments> = listOf(
            Arguments.of(
                arrayOf(Vector2F(4f, 5f), Vector2F(1f, 2f)),
                { v: Vector2F -> v == Vector2F(1f, 2f) },
                Wrapper(Vector2F(1f, 2f))
            ),
            Arguments.of(
                Array(2) { Vector2F(it.toFloat(), 0f) },
                { v: Vector2F -> v.x <= 0f },
                Wrapper(Vector2F(0f, 0f))
            ),
            Arguments.of(
                Array(2) { Vector2F(it.toFloat(), 0f) },
                { v: Vector2F -> v.x >= 2f },
                null
            ),
            Arguments.of(
                emptyArray<Vector2F>(),
                { _: Vector2F -> true },
                null
            ),
        )

        @JvmStatic
        fun indexOfArgs(): List<Arguments> = listOf(
            Arguments.of(
                arrayOf(Vector2F(4f, 5f), Vector2F(1f, 2f)), Wrapper(Vector2F(1f, 2f)), 1
            ),
            Arguments.of(
                arrayOf(Vector2F(4f, 5f), Vector2F(4f, 5f)), Wrapper(Vector2F(4f, 5f)), 0
            ),
            Arguments.of(
                arrayOf(Vector2F(4f, 5f), Vector2F(1f, 2f)), Wrapper(Vector2F(7f, 4f)), -1
            ),
            Arguments.of(
                emptyArray<Vector2F>(), Wrapper(Vector2F(7f, 4f)), -1
            ),
        )

        @JvmStatic
        fun indexOfFirstArgs(): List<Arguments> = listOf(
            Arguments.of(
                arrayOf(Vector2F(4f, 5f), Vector2F(1f, 2f)),
                { v: Vector2F -> v == Vector2F(1f, 2f) },
                1
            ),
            Arguments.of(
                Array(2) { Vector2F(it.toFloat(), 0f) },
                { v: Vector2F -> v.x >= 2f },
                -1
            ),
            Arguments.of(
                emptyArray<Vector2F>(),
                { _: Vector2F -> true },
                -1
            ),
        )

        @JvmStatic
        fun lastIndexOfArgs(): List<Arguments> = listOf(
            Arguments.of(
                arrayOf(Vector2F(4f, 5f), Vector2F(1f, 2f)), Wrapper(Vector2F(1f, 2f)), 1
            ),
            Arguments.of(
                arrayOf(Vector2F(4f, 5f), Vector2F(4f, 5f)), Wrapper(Vector2F(4f, 5f)), 1
            ),
            Arguments.of(
                arrayOf(Vector2F(4f, 5f), Vector2F(1f, 2f)), Wrapper(Vector2F(7f, 4f)), -1
            ),
            Arguments.of(
                emptyArray<Vector2F>(), Wrapper(Vector2F(7f, 4f)), -1
            ),
        )

        @JvmStatic
        fun randomArgs(): List<Arguments> {
            val array = Array(10) { Vector2F(it.toFloat(), 0f) }
            val seeds = intArrayOf(1234, 5678)
            val expectedVals = arrayOf(
                array.random(Random(seeds[0])).let { Wrapper(Vector2F(it.x, it.y)) },
                array.random(Random(seeds[1])).let { Wrapper(Vector2F(it.x, it.y)) },
            )
            return listOf(
                Arguments.of(array, Random(seeds[0]), expectedVals[0]),
                Arguments.of(array, Random(seeds[1]), expectedVals[1]),
            )
        }

        @JvmStatic
        fun randomOrNullArgs(): List<Arguments> {
            val array = Array(10) { Vector2F(it.toFloat(), 0f) }
            val seeds = intArrayOf(1234, 5678)
            val expectedVals = arrayOf(
                array.random(Random(seeds[0])).let { Wrapper(Vector2F(it.x, it.y)) },
                array.random(Random(seeds[1])).let { Wrapper(Vector2F(it.x, it.y)) },
            )
            return listOf(
                Arguments.of(array, Random(seeds[0]), expectedVals[0]),
                Arguments.of(array, Random(seeds[1]), expectedVals[1]),
                Arguments.of(emptyArray<Vector2F>(), Random(42), null),
            )
        }

        @JvmStatic
        fun singleArgs(): List<Arguments> = listOf(
            Arguments.of(arrayOf(Vector2F(1f, 2f)), Wrapper(Vector2F(1f, 2f))),
            Arguments.of(arrayOf(Vector2F.ZERO), Wrapper(Vector2F(0f, 0f))),
        )

        @JvmStatic
        fun singleThrowsExceptionArgs(): List<Arguments> = listOf(
            Arguments.of(emptyArray<Vector2F>(), NoSuchElementException::class.java),
            Arguments.of(Array(size = 2) { Vector2F.ZERO }, IllegalArgumentException::class.java),
        )

        @JvmStatic
        fun singlePredicateArgs(): List<Arguments> = listOf(
            Arguments.of(
                Array(size = 3) { Vector2F(it.toFloat(), 0f) },
                { v: Vector2F -> v == Vector2F(1f, 0f) },
                Wrapper(Vector2F(1f, 0f))
            ),
            Arguments.of(
                Array(size = 3) { Vector2F(it.toFloat(), 0f) },
                { v: Vector2F -> v.x in 0.5f..1.5f },
                Wrapper(Vector2F(1f, 0f))
            ),
        )

        @JvmStatic
        fun singlePredicateThrowsExceptionArgs(): List<Arguments> = listOf(
            Arguments.of(
                emptyArray<Vector2F>(),
                { v: Vector2F -> v == Vector2F.ZERO },
                NoSuchElementException::class.java
            ),
            Arguments.of(
                Array(size = 2) { Vector2F.ZERO },
                { v: Vector2F -> v == Vector2F.ZERO },
                IllegalArgumentException::class.java
            ),
        )

        @JvmStatic
        fun singleOrNullArgs(): List<Arguments> = listOf(
            Arguments.of(arrayOf(Vector2F(1f, 2f)), Wrapper(Vector2F(1f, 2f))),
            Arguments.of(arrayOf(Vector2F.ZERO), Wrapper(Vector2F(0f, 0f))),
            Arguments.of(emptyArray<Vector2F>(), null),
            Arguments.of(Array(size = 2) { Vector2F.ZERO }, null),
        )

        @JvmStatic
        fun dropArgs(): List<Arguments> {
            val array = Array(4) { Vector2F(it.toFloat(), 0f) }

            return listOf(
                Arguments.of(array, 0, List(4) { Vector2F(it.toFloat(), 0f) }),
                Arguments.of(array, 2, listOf(Vector2F(2f, 0f), Vector2F(3f, 0f))),
                Arguments.of(array, 5, emptyList<Vector2F>()),
            )
        }

        @JvmStatic
        fun dropLastArgs(): List<Arguments> {
            val array = Array(4) { Vector2F(it.toFloat(), 0f) }

            return listOf(
                Arguments.of(array, 0, List(4) { Vector2F(it.toFloat(), 0f) }),
                Arguments.of(array, 2, listOf(Vector2F(0f, 0f), Vector2F(1f, 0f))),
                Arguments.of(array, 5, emptyList<Vector2F>()),
            )
        }

        @JvmStatic
        fun dropWhileArgs(): List<Arguments> {
            val array = Array(4) { Vector2F(it.toFloat(), 0f) }

            return listOf(
                Arguments.of(
                    emptyArray<Vector2F>(), { _: Vector2F -> false }, emptyList<Vector2F>(),
                ),
                Arguments.of(
                    array, { _: Vector2F -> false }, List(4) { Vector2F(it.toFloat(), 0f) }
                ),
                Arguments.of(
                    array,
                    { v: Vector2F -> v.x !in 1.5f..2.5f },
                    listOf(Vector2F(2f, 0f), Vector2F(3f, 0f))
                ),
                Arguments.of(
                    array, { _: Vector2F -> true }, emptyList<Vector2F>()
                ),
            )
        }

        @JvmStatic
        fun dropLastWhileArgs(): List<Arguments> {
            val array = Array(4) { Vector2F(it.toFloat(), 0f) }

            return listOf(
                Arguments.of(
                    emptyArray<Vector2F>(), { _: Vector2F -> false }, emptyList<Vector2F>(),
                ),
                Arguments.of(
                    array, { _: Vector2F -> false }, List(4) { Vector2F(it.toFloat(), 0f) }
                ),
                Arguments.of(
                    array,
                    { v: Vector2F -> v.x !in 0.5f..1.5f },
                    listOf(Vector2F(0f, 0f), Vector2F(1f, 0f))
                ),
                Arguments.of(
                    array, { _: Vector2F -> true }, emptyList<Vector2F>()
                ),
            )
        }

        @JvmStatic
        fun sliceRangeArgs(): List<Arguments> {
            val array = Array(3) { Vector2F(it.toFloat(), 0f) }

            return listOf(
                Arguments.of(
                    array,
                    0..2,
                    listOf(Vector2F(0f, 0f), Vector2F(1f, 0f), Vector2F(2f, 0f)),
                ),
                Arguments.of(
                    array,
                    1..2,
                    listOf(Vector2F(1f, 0f), Vector2F(2f, 0f)),
                ),
                Arguments.of(
                    array,
                    IntRange.EMPTY,
                    emptyList<Vector2F>(),
                ),
            )
        }

        @JvmStatic
        fun sliceRangeThrowsExceptionArgs(): List<Arguments> = listOf(
            Arguments.of(Array(3) { Vector2F.ZERO }, 0..3, IndexOutOfBoundsException::class.java),
            Arguments.of(Array(3) { Vector2F.ZERO }, -1..2, IndexOutOfBoundsException::class.java),
        )

        @JvmStatic
        fun sliceIterableArgs(): List<Arguments> {
            val array = Array(3) { Vector2F(it.toFloat(), 0f) }

            return listOf(
                Arguments.of(
                    array,
                    listOf(0, 1, 2),
                    listOf(Vector2F(0f, 0f), Vector2F(1f, 0f), Vector2F(2f, 0f)),
                ),
                Arguments.of(
                    array,
                    listOf(0, 2),
                    listOf(Vector2F(0f, 0f), Vector2F(2f, 0f)),
                ),
                Arguments.of(
                    array,
                    emptyList<Int>(),
                    emptyList<Vector2F>(),
                ),
            )
        }

        @JvmStatic
        fun sliceIterableThrowsExceptionArgs(): List<Arguments> = listOf(
            Arguments.of(
                Array(3) { Vector2F.ZERO },
                listOf(-1, 0, 1),
                IndexOutOfBoundsException::class.java
            ),
            Arguments.of(
                Array(3) { Vector2F.ZERO },
                listOf(0, 1, 3),
                IndexOutOfBoundsException::class.java
            ),
        )

        @JvmStatic
        fun sliceArrayRangeArgs(): List<Arguments> {
            val array = Array(3) { Vector2F(it.toFloat(), 0f) }

            return listOf(
                Arguments.of(
                    array,
                    0..2,
                    arrayOf(Vector2F(0f, 0f), Vector2F(1f, 0f), Vector2F(2f, 0f)),
                ),
                Arguments.of(
                    array,
                    1..2,
                    arrayOf(Vector2F(1f, 0f), Vector2F(2f, 0f)),
                ),
                Arguments.of(
                    array,
                    IntRange.EMPTY,
                    emptyArray<Vector2F>(),
                ),
            )
        }

        @JvmStatic
        fun sliceArrayRangeThrowsExceptionArgs(): List<Arguments> = listOf(
            Arguments.of(Array(3) { Vector2F.ZERO }, 0..3, IndexOutOfBoundsException::class.java),
            Arguments.of(Array(3) { Vector2F.ZERO }, -1..2, IndexOutOfBoundsException::class.java),
        )

        @JvmStatic
        fun sliceArrayCollectionArgs(): List<Arguments> {
            val array = Array(3) { Vector2F(it.toFloat(), 0f) }

            return listOf(
                Arguments.of(
                    array,
                    listOf(0, 1, 2),
                    arrayOf(Vector2F(0f, 0f), Vector2F(1f, 0f), Vector2F(2f, 0f)),
                ),
                Arguments.of(
                    array,
                    listOf(0, 2),
                    arrayOf(Vector2F(0f, 0f), Vector2F(2f, 0f)),
                ),
                Arguments.of(
                    array,
                    emptyList<Int>(),
                    emptyArray<Vector2F>(),
                ),
            )
        }

        @JvmStatic
        fun sliceArrayCollectionThrowsExceptionArgs(): List<Arguments> = listOf(
            Arguments.of(
                Array(3) { Vector2F.ZERO },
                listOf(-1, 0, 1),
                IndexOutOfBoundsException::class.java
            ),
            Arguments.of(
                Array(3) { Vector2F.ZERO },
                listOf(0, 1, 3),
                IndexOutOfBoundsException::class.java
            ),
        )

        @JvmStatic
        fun takeArgs(): List<Arguments> {
            val array = Array(4) { Vector2F(it.toFloat(), 0f) }

            return listOf(
                Arguments.of(array, 0, emptyList<Vector2F>()),
                Arguments.of(array, 2, listOf(Vector2F(0f, 0f), Vector2F(1f, 0f))),
                Arguments.of(array, 5, List(4) { Vector2F(it.toFloat(), 0f) }),
            )
        }

        @JvmStatic
        fun takeLastArgs(): List<Arguments> {
            val array = Array(4) { Vector2F(it.toFloat(), 0f) }

            return listOf(
                Arguments.of(array, 0, emptyList<Vector2F>()),
                Arguments.of(array, 2, listOf(Vector2F(2f, 0f), Vector2F(3f, 0f))),
                Arguments.of(array, 5, List(4) { Vector2F(it.toFloat(), 0f) }),
            )
        }

        @JvmStatic
        fun takeWhileArgs(): List<Arguments> {
            val array = Array(4) { Vector2F(it.toFloat(), 0f) }

            return listOf(
                Arguments.of(
                    emptyArray<Vector2F>(), { _: Vector2F -> true }, emptyList<Vector2F>(),
                ),
                Arguments.of(
                    array, { _: Vector2F -> false }, emptyList<Vector2F>()
                ),
                Arguments.of(
                    array,
                    { v: Vector2F -> v.x !in 1.5f..2.5f },
                    listOf(Vector2F(0f, 0f), Vector2F(1f, 0f))
                ),
                Arguments.of(
                    array, { _: Vector2F -> true }, List(4) { Vector2F(it.toFloat(), 0f) }
                ),
            )
        }

        @JvmStatic
        fun takeLastWhileArgs(): List<Arguments> {
            val array = Array(4) { Vector2F(it.toFloat(), 0f) }

            return listOf(
                Arguments.of(
                    emptyArray<Vector2F>(), { _: Vector2F -> true }, emptyList<Vector2F>(),
                ),
                Arguments.of(
                    array, { _: Vector2F -> false }, emptyList<Vector2F>()
                ),
                Arguments.of(
                    array,
                    { v: Vector2F -> v.x !in 0.5f..1.5f },
                    listOf(Vector2F(2f, 0f), Vector2F(3f, 0f))
                ),
                Arguments.of(
                    array, { _: Vector2F -> true }, List(4) { Vector2F(it.toFloat(), 0f) }
                ),
            )
        }

        @JvmStatic
        fun reverseArgs(): List<Arguments> = listOf(
            Arguments.of(emptyArray<Vector2F>(), emptyArray<Vector2F>()),
            Arguments.of(
                arrayOf(Vector2F(1f, 2f), Vector2F(3f, 4f), Vector2F(5f, 6f)),
                arrayOf(Vector2F(5f, 6f), Vector2F(3f, 4f), Vector2F(1f, 2f))
            ),
            Arguments.of(
                arrayOf(Vector2F(1f, 2f), Vector2F(3f, 4f)),
                arrayOf(Vector2F(3f, 4f), Vector2F(1f, 2f))
            ),
        )

        @JvmStatic
        fun reverseRangeArgs(): List<Arguments> = listOf(
            Arguments.of(emptyArray<Vector2F>(), 0, 0, emptyArray<Vector2F>()),
            Arguments.of(
                arrayOf(Vector2F(1f, 2f), Vector2F(3f, 4f), Vector2F(5f, 6f)),
                1, 3,
                arrayOf(Vector2F(1f, 2f), Vector2F(5f, 6f), Vector2F(3f, 4f))
            ),
            Arguments.of(
                arrayOf(Vector2F(1f, 2f), Vector2F(3f, 4f)),
                0, 2,
                arrayOf(Vector2F(3f, 4f), Vector2F(1f, 2f))
            ),
        )

        @JvmStatic
        fun reverseRangeThrowsExceptionArgs(): List<Arguments> = listOf(
            Arguments.of(Array(3) { Vector2F.ZERO }, 0, 4, IndexOutOfBoundsException::class.java),
            Arguments.of(Array(3) { Vector2F.ZERO }, -1, 2, IndexOutOfBoundsException::class.java),
            Arguments.of(Array(3) { Vector2F.ZERO }, 2, 1, IllegalArgumentException::class.java),
        )

        @JvmStatic
        fun reversedArgs(): List<Arguments> = listOf(
            Arguments.of(emptyArray<Vector2F>(), emptyList<Vector2F>()),
            Arguments.of(
                arrayOf(Vector2F(1f, 2f), Vector2F(3f, 4f), Vector2F(5f, 6f)),
                listOf(Vector2F(5f, 6f), Vector2F(3f, 4f), Vector2F(1f, 2f))
            ),
            Arguments.of(
                arrayOf(Vector2F(1f, 2f), Vector2F(3f, 4f)),
                listOf(Vector2F(3f, 4f), Vector2F(1f, 2f))
            ),
        )

        @JvmStatic
        fun reversedArrayArgs(): List<Arguments> = reverseArgs()

        @JvmStatic
        fun shuffleArgs(): List<Arguments> {
            val array = Array(10) { Vector2F(it.toFloat(), 0f) }
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
                Vector2F(4f, 2f), Vector2F(7f, 3f), Vector2F(3f, 1f),
            )
            return listOf(
                Arguments.of(
                    array,
                    { v: Vector2F -> v.x },
                    listOf(Vector2F(3f, 1f), Vector2F(4f, 2f), Vector2F(7f, 3f))
                ),
                Arguments.of(
                    emptyArray<Vector2F>(),
                    { v: Vector2F -> v.x },
                    emptyList<Vector2F>()
                ),
            )
        }

        @JvmStatic
        fun sortedByDescendingArgs(): List<Arguments> {
            val array = arrayOf(
                Vector2F(4f, 2f), Vector2F(7f, 3f), Vector2F(3f, 1f),
            )
            return listOf(
                Arguments.of(
                    array,
                    { v: Vector2F -> v.y },
                    listOf(Vector2F(7f, 3f), Vector2F(4f, 2f), Vector2F(3f, 1f))
                ),
                Arguments.of(
                    emptyArray<Vector2F>(),
                    { v: Vector2F -> v.y },
                    emptyList<Vector2F>()
                ),
            )
        }

        @JvmStatic
        fun sortedWithArgs(): List<Arguments> {
            val array = arrayOf(
                Vector2F(4f, 3f), Vector2F(4f, 2f), Vector2F(3f, 1f),
            )
            val comparator = Comparator<Vector2F> { a, b ->
                val xToX = a.x.compareTo(b.x)

                if (xToX == 0) a.y.compareTo(b.y) else xToX
            }
            return listOf(
                Arguments.of(
                    array,
                    comparator,
                    listOf(Vector2F(3f, 1f), Vector2F(4f, 2f), Vector2F(4f, 3f))
                ),
                Arguments.of(
                    emptyArray<Vector2F>(),
                    comparator,
                    emptyList<Vector2F>()
                ),
            )
        }

        @JvmStatic
        fun asListArgs(): List<Arguments> = listOf(
            Arguments.of(emptyArray<Vector2F>(), emptyList<Vector2F>()),
            Arguments.of(
                Array(4) { Vector2F(it.toFloat(), 0f) },
                List(4) { Vector2F(it.toFloat(), 0f) }
            ),
        )

        @JvmStatic
        fun copyIntoArgs(): List<Arguments> = listOf(
            Arguments.of(
                Array(4) { Vector2F(it.toFloat(), 0f) },
                Array(4) { Vector2F.ZERO },
                0, 0, 4,
                Array(4) { Vector2F(it.toFloat(), 0f) }
            ),
            Arguments.of(
                Array(4) { Vector2F(it.toFloat(), 0f) },
                Array(5) { Vector2F.ZERO },
                3, 1, 3,
                arrayOf(
                    Vector2F(0f, 0f),
                    Vector2F(0f, 0f),
                    Vector2F(0f, 0f),
                    Vector2F(1f, 0f),
                    Vector2F(2f, 0f),
                )
            ),
            Arguments.of(
                emptyArray<Vector2F>(),
                Array(4) { Vector2F.ZERO },
                0, 0, 0,
                Array(4) { Vector2F.ZERO },
            ),
        )

        @JvmStatic
        fun copyIntoThrowsExceptionArgs(): List<Arguments> = listOf(
            Arguments.of(
                Array(4) { Vector2F.ZERO },
                Array(4) { Vector2F.ZERO },
                0, 0, 5,
                IndexOutOfBoundsException::class.java
            ),
            Arguments.of(
                Array(4) { Vector2F.ZERO },
                Array(4) { Vector2F.ZERO },
                0, -1, 4,
                IndexOutOfBoundsException::class.java
            ),
            Arguments.of(
                Array(4) { Vector2F.ZERO },
                Array(4) { Vector2F.ZERO },
                0, 2, 1,
                IndexOutOfBoundsException::class.java
            ),
            Arguments.of(
                Array(4) { Vector2F.ZERO },
                Array(4) { Vector2F.ZERO },
                1, 0, 4,
                IndexOutOfBoundsException::class.java
            ),
            Arguments.of(
                Array(4) { Vector2F.ZERO },
                Array(4) { Vector2F.ZERO },
                4, 0, 1,
                IndexOutOfBoundsException::class.java
            ),
        )

        @JvmStatic
        fun copyOfArgs(): List<Arguments> = listOf(
            Arguments.of(emptyArray<Vector2F>(), emptyArray<Vector2F>()),
            Arguments.of(
                Array(4) { Vector2F(it.toFloat(), 0f) },
                Array(4) { Vector2F(it.toFloat(), 0f) }
            ),
        )

        @JvmStatic
        fun copyOfSizeArgs(): List<Arguments> = listOf(
            Arguments.of(emptyArray<Vector2F>(), 0, emptyArray<Vector2F>()),
            Arguments.of(emptyArray<Vector2F>(), 2, Array(2) { Vector2F.ZERO }),
            Arguments.of(
                Array(4) { Vector2F(it.toFloat(), 0f) },
                3,
                Array(3) { Vector2F(it.toFloat(), 0f) }
            ),
            Arguments.of(
                Array(4) { Vector2F(it.toFloat(), 0f) },
                5,
                arrayOf(
                    Vector2F(0f, 0f),
                    Vector2F(1f, 0f),
                    Vector2F(2f, 0f),
                    Vector2F(3f, 0f),
                    Vector2F(0f, 0f),
                )
            ),
        )

        @JvmStatic
        fun copyOfRangeArgs(): List<Arguments> = listOf(
            Arguments.of(emptyArray<Vector2F>(), 0, 0, emptyArray<Vector2F>()),
            Arguments.of(
                Array(4) { Vector2F(it.toFloat(), 0f) },
                0, 4,
                Array(4) { Vector2F(it.toFloat(), 0f) }
            ),
            Arguments.of(
                Array(4) { Vector2F(it.toFloat(), 0f) },
                1, 3,
                arrayOf(Vector2F(1f, 0f), Vector2F(2f, 0f))
            ),
        )

        @JvmStatic
        fun copyOfRangeThrowsExceptionArgs(): List<Arguments> = listOf(
            Arguments.of(emptyArray<Vector2F>(), 0, 1, IndexOutOfBoundsException::class.java),
            Arguments.of(
                Array(4) { Vector2F(it.toFloat(), 0f) },
                0, 5,
                IndexOutOfBoundsException::class.java
            ),
            Arguments.of(
                Array(4) { Vector2F(it.toFloat(), 0f) },
                -1, 4,
                IndexOutOfBoundsException::class.java
            ),
            Arguments.of(
                Array(4) { Vector2F(it.toFloat(), 0f) },
                2, 1,
                IllegalArgumentException::class.java
            ),
        )

        @JvmStatic
        fun fillArgs(): List<Arguments> = listOf(
            Arguments.of(
                Array(4) { Vector2F.ZERO },
                Wrapper(Vector2F(1f, 2f)),
                0, 4,
                Array(4) { Vector2F(1f, 2f) }
            ),
            Arguments.of(
                Array(4) { Vector2F.ZERO },
                Wrapper(Vector2F(1f, 2f)),
                1, 3,
                arrayOf(Vector2F.ZERO, Vector2F(1f, 2f), Vector2F(1f, 2f), Vector2F.ZERO)
            ),
            Arguments.of(
                Array(4) { Vector2F.ZERO },
                Wrapper(Vector2F(1f, 2f)),
                0, 0,
                Array(4) { Vector2F.ZERO }
            ),
            Arguments.of(
                emptyArray<Vector2F>(),
                Wrapper(Vector2F(1f, 2f)),
                0, 0,
                emptyArray<Vector2F>(),
            ),
        )

        @JvmStatic
        fun fillThrowsExceptionArgs(): List<Arguments> = listOf(
            Arguments.of(
                Array(4) { Vector2F.ZERO },
                Wrapper(Vector2F(1f, 2f)),
                0, 5,
                IndexOutOfBoundsException::class.java
            ),
            Arguments.of(
                Array(4) { Vector2F.ZERO },
                Wrapper(Vector2F(1f, 2f)),
                -1, 4,
                IndexOutOfBoundsException::class.java
            ),
            Arguments.of(
                Array(4) { Vector2F.ZERO },
                Wrapper(Vector2F(1f, 2f)),
                2, 1,
                IllegalArgumentException::class.java
            ),
        )

        @JvmStatic
        fun isEmptyArgs(): List<Arguments> = listOf(
            Arguments.of(Array(4) { Vector2F.ZERO }, false),
            Arguments.of(Array(0) { Vector2F.ZERO }, true),
        )

        @JvmStatic
        fun isNotEmptyArgs(): List<Arguments> = listOf(
            Arguments.of(Array(4) { Vector2F.ZERO }, true),
            Arguments.of(Array(0) { Vector2F.ZERO }, false),
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
        fun noneArgs(): List<Arguments> = isEmptyArgs()

        @JvmStatic
        fun sumOfDoubleArgs(): List<Arguments> {
            val array = arrayOf(
                Vector2F(1f, 2f), Vector2F(-2f, -1f), Vector2F(10f, 10f)
            )
            return listOf(
                Arguments.of(
                    array, { v: Vector2F -> v.x.toDouble() + v.y.toDouble() }, 20.0
                ),
                Arguments.of(
                    array,
                    { v: Vector2F -> v.x.toDouble().absoluteValue + v.y.toDouble().absoluteValue },
                    26.0
                ),
            )
        }

        @JvmStatic
        fun sumOfIntArgs(): List<Arguments> {
            val array = arrayOf(
                Vector2F(1f, 2f), Vector2F(-2f, -1f), Vector2F(10f, 10f)
            )
            return listOf(
                Arguments.of(
                    array, { v: Vector2F -> v.x.toInt() + v.y.toInt() }, 20
                ),
                Arguments.of(
                    array,
                    { v: Vector2F -> v.x.toInt().absoluteValue + v.y.toInt().absoluteValue },
                    26
                ),
            )
        }

        @JvmStatic
        fun sumOfUIntArgs(): List<Arguments> {
            val array = arrayOf(
                Vector2F(3f, 2f), Vector2F(2f, 1f), Vector2F(10f, 10f)
            )
            return listOf(
                Arguments.of(array, { v: Vector2F -> v.x.toUInt() + v.y.toUInt() }, Wrapper(28u)),
                Arguments.of(array, { v: Vector2F -> v.x.toUInt() - v.y.toUInt() }, Wrapper(2u)),
            )
        }

        @JvmStatic
        fun sumOfLongArgs(): List<Arguments> {
            val array = arrayOf(
                Vector2F(1f, 2f), Vector2F(-2f, -1f), Vector2F(10f, 10f)
            )
            return listOf(
                Arguments.of(
                    array, { v: Vector2F -> v.x.toLong() + v.y.toLong() }, 20L
                ),
                Arguments.of(
                    array,
                    { v: Vector2F -> v.x.toLong().absoluteValue + v.y.toLong().absoluteValue },
                    26L
                ),
            )
        }

        @JvmStatic
        fun sumOfULongArgs(): List<Arguments> {
            val array = arrayOf(
                Vector2F(3f, 2f), Vector2F(2f, 1f), Vector2F(10f, 10f)
            )
            return listOf(
                Arguments.of(
                    array, { v: Vector2F -> v.x.toULong() + v.y.toULong() }, Wrapper(28uL)
                ),
                Arguments.of(
                    array, { v: Vector2F -> v.x.toULong() - v.y.toULong() }, Wrapper(2uL)
                ),
            )
        }

        @JvmStatic
        fun sumOfVector2FArgs(): List<Arguments> {
            val array = arrayOf(
                Vector2F(1f, 2f), Vector2F(-2f, -1f), Vector2F(10f, 10f)
            )
            return listOf(
                Arguments.of(
                    array, { v: Vector2F -> v }, Wrapper(Vector2F(9f, 11f))
                ),
                Arguments.of(
                    array,
                    { v: Vector2F -> Vector2F(v.x.absoluteValue, v.y.absoluteValue) },
                    Wrapper(Vector2F(13f, 13f))
                ),
            )
        }

        @JvmStatic
        fun sumArgs(): List<Arguments> {
            return listOf(
                Arguments.of(emptyArray<Vector2F>(), Wrapper(Vector2F(0f, 0f))),
                Arguments.of(
                    arrayOf(Vector2F(1f, 2f), Vector2F(-2f, -1f), Vector2F(10f, 10f)),
                    Wrapper(Vector2F(9f, 11f))
                ),
            )
        }

        @JvmStatic
        fun containsArgs(): List<Arguments> {
            val array = arrayOf(
                Vector2F(1f, 0f),
                Vector2F(-5f, 8f),
                Vector2F(2f, -9f),
                Vector2F(3f, 4f)
            )
            return listOf(
                Arguments.of(array, Wrapper(Vector2F(0f, 0f)), false),
                Arguments.of(array, Wrapper(Vector2F(3f, 4f)), true),
            )
        }

        @JvmStatic
        fun iteratorArgs(): List<Arguments> {
            val array = Array(4) { Vector2F(it.toFloat(), 0f) }

            return listOf(
                Arguments.of(array, array.iterator()),
                Arguments.of(emptyArray<Vector2F>(), emptyArray<Vector2F>().iterator()),
            )
        }

        @JvmStatic
        fun getArgs(): List<Arguments> {
            val array = arrayOf(
                Vector2F(1f, 0f),
                Vector2F(-5f, 8f),
                Vector2F(2f, -9f),
                Vector2F(3f, 4f),
            )
            return listOf(
                Arguments.of(array, 0, Wrapper(Vector2F(1f, 0f))),
                Arguments.of(array, 3, Wrapper(Vector2F(3f, 4f))),
            )
        }

        @JvmStatic
        fun getOrElseArgs(): List<Arguments> {
            val array = arrayOf(
                Vector2F(1f, 0f),
                Vector2F(-5f, 8f),
                Vector2F(3f, 4f),
            )
            return listOf(
                Arguments.of(
                    array, 0, { i: Int -> Vector2F(i.toFloat(), i.toFloat()) }, Wrapper(Vector2F(1f, 0f))
                ),
                Arguments.of(
                    array, -1, { i: Int -> Vector2F(i.toFloat(), i.toFloat()) }, Wrapper(Vector2F(-1f, -1f))
                ),
                Arguments.of(
                    array, 3, { i: Int -> Vector2F(i.toFloat(), i.toFloat()) }, Wrapper(Vector2F(3f, 3f))
                ),
            )
        }

        @JvmStatic
        fun getOrNullArgs(): List<Arguments> {
            val array = arrayOf(
                Vector2F(1f, 0f),
                Vector2F(-5f, 8f),
                Vector2F(3f, 4f),
            )
            return listOf(
                Arguments.of(array, 0, Wrapper(Vector2F(1f, 0f))),
                Arguments.of(array, -1, null),
                Arguments.of(array, 3, null),
            )
        }

        @JvmStatic
        fun setArgs(): List<Arguments> = listOf(
            Arguments.of(
                Array(4) { Vector2F.ZERO },
                0,
                Wrapper(Vector2F(1f, 0f)),
                arrayOf(Vector2F(1f, 0f), Vector2F.ZERO, Vector2F.ZERO, Vector2F.ZERO),
            ),
            Arguments.of(
                Array(4) { Vector2F.ZERO },
                3,
                Wrapper(Vector2F(3f, 4f)),
                arrayOf(Vector2F.ZERO, Vector2F.ZERO, Vector2F.ZERO, Vector2F(3f, 4f))
            ),
        )

        @JvmStatic
        fun arrayPlusVector2FArgs(): List<Arguments> = listOf(
            Arguments.of(
                emptyArray<Vector2F>(), Wrapper(Vector2F(1f, 0f)), arrayOf(Vector2F(1f, 0f))
            ),
            Arguments.of(
                arrayOf(Vector2F(1f, 0f), Vector2F(2f, 0f)),
                Wrapper(Vector2F(3f, 0f)),
                arrayOf(Vector2F(1f, 0f), Vector2F(2f, 0f), Vector2F(3f, 0f)),
            ),
        )

        @JvmStatic
        fun arrayPlusCollectionArgs(): List<Arguments> = listOf(
            Arguments.of(
                emptyArray<Vector2F>(), listOf(Vector2F(1f, 0f)), arrayOf(Vector2F(1f, 0f)),
            ),
            Arguments.of(
                arrayOf(Vector2F(1f, 0f), Vector2F(2f, 0f)),
                listOf(Vector2F(3f, 0f), Vector2F(4f, 0f)),
                arrayOf(
                    Vector2F(1f, 0f),
                    Vector2F(2f, 0f),
                    Vector2F(3f, 0f),
                    Vector2F(4f, 0f)
                ),
            ),
        )

        @JvmStatic
        fun arrayPlusArrayArgs(): List<Arguments> = listOf(
            Arguments.of(
                emptyArray<Vector2F>(), arrayOf(Vector2F(1f, 0f)), arrayOf(Vector2F(1f, 0f)),
            ),
            Arguments.of(
                arrayOf(Vector2F(1f, 0f), Vector2F(2f, 0f)),
                arrayOf(Vector2F(3f, 0f), Vector2F(4f, 0f)),
                arrayOf(
                    Vector2F(1f, 0f),
                    Vector2F(2f, 0f),
                    Vector2F(3f, 0f),
                    Vector2F(4f, 0f)
                ),
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
        fun typedArraySumOfVector2FArgs(): List<Arguments> = sumOfVector2FArgs()

        @JvmStatic
        fun typedArraySumArgs(): List<Arguments> = sumArgs()
    }
}