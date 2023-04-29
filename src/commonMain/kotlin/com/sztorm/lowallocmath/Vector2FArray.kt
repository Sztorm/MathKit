@file:Suppress(
    "ConvertTwoComparisonsToRangeCheck",
    "MemberVisibilityCanBePrivate",
    "OVERRIDE_BY_INLINE",
    "ReplaceManualRangeWithIndicesCalls",
    "ReplaceRangeToWithUntil",
    "ReplaceSizeCheckWithIsNotEmpty",
    "ReplaceSizeZeroCheckWithIsEmpty",
    "unused",
    "UseWithIndex",
)

package com.sztorm.lowallocmath

import kotlin.experimental.ExperimentalTypeInference
import kotlin.jvm.JvmInline
import kotlin.jvm.JvmStatic
import kotlin.random.Random

/** Returns the sum of all elements in the array. **/
@kotlin.jvm.JvmName("sumOfVector2F")
fun Array<out Vector2F>.sum(): Vector2F {
    var sum = Vector2F.ZERO

    for (i in 0..lastIndex) {
        sum += this[i]
    }
    return sum
}

/**
 * Returns the sum of all values produced by [selector] function applied to each element in the
 * array.
 */
@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
@kotlin.jvm.JvmName("sumOfVector2F")
inline fun <T> Array<out T>.sumOf(selector: (T) -> Vector2F): Vector2F {
    var sum: Vector2F = Vector2F.ZERO

    for (i in 0..lastIndex) {
        sum += selector(this[i])
    }
    return sum
}

/** Returns an array of [Vector2F] containing all the elements of this generic array. **/
fun Array<out Vector2F>.toVector2FArray(): Vector2FArray =
    Vector2FArray(size) { index -> this[index] }

/** Returns the sum of all elements in the collection. **/
@kotlin.jvm.JvmName("sumOfVector2F")
fun Iterable<Vector2F>.sum(): Vector2F {
    var sum = Vector2F.ZERO

    for (element in this) {
        sum += element
    }
    return sum
}

/**
 * Returns the sum of all values produced by [selector] function applied to each element in the
 * collection.
 */
@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
@kotlin.jvm.JvmName("sumOfVector2F")
inline fun <T> Iterable<T>.sumOf(selector: (T) -> Vector2F): Vector2F {
    var sum = Vector2F.ZERO

    for (element in this) {
        sum += selector(element)
    }
    return sum
}

/**
 * An array of [Vector2F] values.
 *
 * When targeting the JVM, instances of this class are represented as `long[]`.
 */
@JvmInline
value class Vector2FArray private constructor(private val data: LongArray) : Collection<Vector2F> {

    /** Creates a new array of the specified [size], with all elements initialized to zero. **/
    constructor(size: Int) : this(LongArray(size))

    /**
     * Creates a new array of the specified [size], where each element is calculated by calling the
     * specified [init] function.
     *
     * The function [init] is called for each array element sequentially starting from the first
     * one. It should return the value for an array element given its index.
     */
    constructor(size: Int, init: (Int) -> Vector2F) : this(create(size, init))

    /** Returns the range of valid indices for the array. **/
    inline val indices
        get() = IntRange(0, lastIndex)

    /** Returns the last valid index for the array. **/
    inline val lastIndex: Int
        get() = size - 1

    /** Returns the number of elements in the array. **/
    override val size: Int
        get() = data.size

    /**
     * Returns `true` if all elements match the given [predicate].
     *
     * Note that if the array contains no elements, the function returns `true` because there are
     * no elements in it that _do not_ match the predicate. See a more detailed explanation of this
     * logic concept in [Vacuous truth](https://en.wikipedia.org/wiki/Vacuous_truth) article.
     */
    inline fun all(predicate: (Vector2F) -> Boolean): Boolean {
        for (i in 0..lastIndex) {
            if (!predicate(this[i])) {
                return false
            }
        }
        return true
    }

    /** Returns `true` if array has at least one element. **/
    inline fun any(): Boolean = !isEmpty()

    /** Returns `true` if at least one element matches the given [predicate]. **/
    inline fun any(predicate: (Vector2F) -> Boolean): Boolean {
        for (i in 0..lastIndex) {
            if (predicate(this[i])) {
                return true
            }
        }
        return false
    }

    /** Returns a [List] that wraps the original array. **/
    fun asList(): List<Vector2F> = object : AbstractList<Vector2F>(), RandomAccess {
        override val size: Int get() = this@Vector2FArray.size
        override fun isEmpty(): Boolean = this@Vector2FArray.isEmpty()
        override fun contains(element: Vector2F): Boolean = this@Vector2FArray.contains(element)
        override fun get(index: Int): Vector2F = this@Vector2FArray[index]
        override fun indexOf(element: Vector2F): Int = this@Vector2FArray.indexOf(element)
        override fun lastIndexOf(element: Vector2F): Int = this@Vector2FArray.lastIndexOf(element)
    }

    /** Checks if all elements in the specified collection are contained in this array. **/
    override fun containsAll(elements: Collection<Vector2F>): Boolean {
        for (v in elements) {
            if (!contains(v)) {
                return false
            }
        }
        return true
    }

    /**
     * Copies this array or its subrange into the [destination] array and returns that array.
     *
     * It's allowed to pass the same array in the [destination] and even specify the subrange so
     * that it overlaps with the destination range.
     *
     * @param destination the array to copy to.
     * @param destinationOffset the position in the [destination] array to copy to, 0 by default.
     * @param startIndex the beginning (inclusive) of the subrange to copy, 0 by default.
     * @param endIndex the end (exclusive) of the subrange to copy, size of this array by default.
     * @exception IndexOutOfBoundsException or [IllegalArgumentException] when [startIndex] or
     * [endIndex] is out of range of this array indices or when `startIndex > endIndex`.
     * @exception IndexOutOfBoundsException when the subrange doesn't fit into the [destination]
     * array starting at the specified [destinationOffset], or when that index is out of the
     * [destination] array indices range.
     */
    fun copyInto(
        destination: Vector2FArray,
        destinationOffset: Int = 0,
        startIndex: Int = 0,
        endIndex: Int = size
    ) = Vector2FArray(data.copyInto(destination.data, destinationOffset, startIndex, endIndex))

    /** Returns new array which is a copy of the original array. **/
    fun copyOf() = Vector2FArray(data.copyOf())

    /**
     * Returns new array which is a copy of the original array, resized to the given [newSize]. The
     * copy is either truncated or padded at the end with [Vector2F.ZERO] values if necessary.
     * - If [newSize] is less than the size of the original array, the copy array is truncated to
     * the [newSize].
     * - If [newSize] is greater than the size of the original array, the extra elements in the
     * copy array are filled with [Vector2F.ZERO] values.
     */
    fun copyOf(newSize: Int) = Vector2FArray(data.copyOf(newSize))

    /**
     * Returns a new array which is a copy of the specified range of the original array.
     *
     * @param fromIndex the start of the range (inclusive) to copy.
     * @param toIndex the end of the range (exclusive) to copy.
     * @exception IndexOutOfBoundsException if [fromIndex] is less than zero or [toIndex] is
     * greater than the size of this array.
     * @exception IllegalArgumentException if [fromIndex] is greater than [toIndex].
     */
    fun copyOfRange(fromIndex: Int, toIndex: Int) =
        Vector2FArray(data.copyOfRange(fromIndex, toIndex))

    /** Returns the number of elements in this array. **/
    inline fun count(): Int = size

    /** Returns the number of elements matching the given [predicate]. **/
    inline fun count(predicate: (Vector2F) -> Boolean): Int {
        var count = 0

        for (i in 0..lastIndex) {
            if (predicate(this[i])) {
                ++count
            }
        }
        return count
    }

    /**
     * Returns a list containing only distinct elements from the given array.
     *
     * The elements in the resulting list are in the same order as they were in the source array.
     */
    inline fun distinct(): List<Vector2F> = toMutableSet().toList()

    /**
     * Returns a list containing only elements from the given array having distinct keys returned
     * by the given [selector] function.
     *
     * The elements in the resulting list are in the same order as they were in the source array.
     */
    inline fun <K> distinctBy(selector: (Vector2F) -> K): List<Vector2F> {
        val set = HashSet<K>()
        val list = ArrayList<Vector2F>()

        for (i in 0..lastIndex) {
            val element: Vector2F = this[i]
            val key = selector(element)

            if (set.add(key)) {
                list.add(element)
            }
        }
        return list
    }

    /**
     * Returns a list containing all elements except first [n] elements.
     *
     * @exception IllegalArgumentException if [n] is negative.
     */
    fun drop(n: Int): List<Vector2F> {
        require(n >= 0) { "Requested element count $n is less than zero." }
        return takeLastInternal((size - n).coerceAtLeast(0))
    }

    /**
     * Returns a list containing all elements except last [n] elements.
     *
     * @exception IllegalArgumentException if [n] is negative.
     */
    fun dropLast(n: Int): List<Vector2F> {
        require(n >= 0) { "Requested element count $n is less than zero." }
        return takeInternal((size - n).coerceAtLeast(0))
    }

    /**
     * Returns a list containing all elements except last elements that satisfy the given
     * [predicate].
     */
    inline fun dropLastWhile(predicate: (Vector2F) -> Boolean): List<Vector2F> {
        for (index in lastIndex downTo 0) {
            if (!predicate(this[index])) {
                return take(index + 1)
            }
        }
        return emptyList()
    }

    /**
     * Returns a list containing all elements except first elements that satisfy the given
     * [predicate].
     */
    inline fun dropWhile(predicate: (Vector2F) -> Boolean): List<Vector2F> {
        var yielding = false
        var index = 0

        for (i in 0..lastIndex) {
            val item: Vector2F = this[i]

            if (!predicate(item)) {
                index = i
                yielding = true
                break
            }
        }
        if (!yielding) {
            return emptyList()
        }
        val list = ArrayList<Vector2F>(size - index)

        for (i in index..lastIndex) {
            val item: Vector2F = this[i]

            list.add(item)
        }
        return list
    }

    /**
     * Returns an element at the given [index].
     *
     * @exception IndexOutOfBoundsException if the [index] is out of bounds of this array.
     */
    inline fun elementAt(index: Int): Vector2F = this[index]

    /**
     * Returns an element at the given [index] or the result of calling the [defaultValue] function
     * if the [index] is out of bounds of this array.
     */
    inline fun elementAtOrElse(index: Int, defaultValue: (Int) -> Vector2F): Vector2F =
        getOrElse(index, defaultValue)

    /**
     * Returns an element at the given [index] or `null` if the [index] is out of bounds of this
     * array.
     */
    inline fun elementAtOrNull(index: Int): Vector2F? = getOrNull(index)

    /**
     * Fills this array or its subrange with the specified [element] value.
     *
     * @param fromIndex the start of the range (inclusive) to fill, 0 by default.
     * @param toIndex the end of the range (exclusive) to fill, size of this array by default.
     * @exception IndexOutOfBoundsException if [fromIndex] is less than zero or [toIndex] is
     * greater than the size of this array.
     * @exception IllegalArgumentException if [fromIndex] is greater than [toIndex].
     */
    fun fill(element: Vector2F, fromIndex: Int = 0, toIndex: Int = size) =
        data.fill(element.data, fromIndex, toIndex)

    /**
     * Returns the first element matching the given [predicate], or `null` if no such element was
     * found.
     */
    inline fun find(predicate: (Vector2F) -> Boolean): Vector2F? = firstOrNull(predicate)

    /**
     * Returns the last element matching the given [predicate], or `null` if no such element was
     * found.
     */
    inline fun findLast(predicate: (Vector2F) -> Boolean): Vector2F? = lastOrNull(predicate)

    /**
     * Returns the first element.
     *
     * @exception NoSuchElementException if the array is empty.
     */
    fun first() = Vector2F(data.first())

    /**
     * Returns the first element matching the given predicate.
     *
     * @exception NoSuchElementException if no such element is found.
     */
    inline fun first(predicate: (Vector2F) -> Boolean): Vector2F {
        for (i in 0..lastIndex) {
            val vector = this[i]

            if (predicate(vector)) {
                return vector
            }
        }
        throw NoSuchElementException("Array contains no element matching the predicate.")
    }

    /** Returns the first element, or `null` if the array is empty. **/
    inline fun firstOrNull(): Vector2F? =
        if (isEmpty()) null
        else this[0]

    /**
     * Returns the first element matching the given [predicate], or `null` if element was not
     * found.
     */
    inline fun firstOrNull(predicate: (Vector2F) -> Boolean): Vector2F? {
        for (i in 0..lastIndex) {
            val vector = this[i]

            if (predicate(vector)) {
                return vector
            }
        }
        return null
    }

    /** Performs the given [action] on each element. **/
    inline fun forEach(action: (Vector2F) -> Unit) {
        for (i in 0..lastIndex) {
            val element: Vector2F = this[i]

            action(element)
        }
    }

    /**
     * Performs the given [action] on each element, providing sequential index with the element.
     *
     * @param action function that takes the index of an element and the element itself and
     * performs the action on the element.
     */
    inline fun forEachIndexed(action: (index: Int, Vector2F) -> Unit) {
        for (i in 0..lastIndex) {
            val element: Vector2F = this[i]

            action(i, element)
        }
    }

    /**
     * Returns an element at the given [index] or the result of calling the [defaultValue]
     * function if the [index] is out of bounds of this array.
     */
    inline fun getOrElse(index: Int, defaultValue: (Int) -> Vector2F): Vector2F =
        if (index >= 0 && index <= lastIndex) this[index]
        else defaultValue(index)

    /**
     * Returns an element at the given [index] or `null` if the [index] is out of bounds of this
     * array.
     */
    inline fun getOrNull(index: Int): Vector2F? =
        if (index >= 0 && index <= lastIndex) this[index]
        else null

    /** Returns first index of [element], or -1 if the array does not contain element. **/
    fun indexOf(element: Vector2F): Int = data.indexOf(element.data)

    /**
     * Returns index of the first element matching the given [predicate], or -1 if the array does
     * not contain such element.
     */
    inline fun indexOfFirst(predicate: (Vector2F) -> Boolean): Int {
        for (index in 0..lastIndex) {
            if (predicate(this[index])) {
                return index
            }
        }
        return -1
    }

    /**
     * Returns index of the last element matching the given [predicate], or -1 if the array does
     * not contain such element.
     */
    inline fun indexOfLast(predicate: (Vector2F) -> Boolean): Int {
        for (index in lastIndex downTo 0) {
            if (predicate(this[index])) {
                return index
            }
        }
        return -1
    }

    /** Returns `true` if the array is empty. **/
    override inline fun isEmpty(): Boolean = size == 0

    /** Returns `true` if the array is not empty. **/
    inline fun isNotEmpty(): Boolean = size != 0

    /**
     * Returns the last element.
     *
     * @exception NoSuchElementException if the array is empty.
     */
    fun last() = Vector2F(data.last())

    /**
     * Returns the last element matching the given [predicate].
     *
     * @exception NoSuchElementException if no such element is found.
     */
    inline fun last(predicate: (Vector2F) -> Boolean): Vector2F {
        for (i in lastIndex downTo 0) {
            val vector = this[i]

            if (predicate(vector)) {
                return vector
            }
        }
        throw NoSuchElementException("Array contains no element matching the predicate.")
    }

    /** Returns last index of [element], or -1 if the array does not contain element. **/
    fun lastIndexOf(element: Vector2F): Int = data.lastIndexOf(element.data)

    /** Returns the last element, or `null` if the array is empty. **/
    inline fun lastOrNull(): Vector2F? =
        if (isEmpty()) null
        else this[size - 1]

    /**
     * Returns the last element matching the given predicate, or `null` if no such element was
     * found.
     */
    inline fun lastOrNull(predicate: (Vector2F) -> Boolean): Vector2F? {
        for (i in lastIndex downTo 0) {
            val vector = this[i]

            if (predicate(vector)) {
                return vector
            }
        }
        return null
    }

    /** Returns `true` if the array has no elements. **/
    inline fun none(): Boolean = isEmpty()

    /** Returns `true` if no elements match the given [predicate]. **/
    inline fun none(predicate: (Vector2F) -> Boolean): Boolean {
        for (i in 0..lastIndex) {
            if (predicate(this[i])) {
                return false
            }
        }
        return true
    }

    /**
     * Returns a random element from this array.
     *
     * @exception NoSuchElementException if this array is empty.
     */
    inline fun random(): Vector2F = random(Random)

    /**
     * Returns a random element from this array using the specified source of randomness.
     *
     * @exception NoSuchElementException if this array is empty.
     */
    inline fun random(random: Random): Vector2F =
        if (isEmpty()) throw NoSuchElementException("Array is empty.")
        else this[random.nextInt(size)]

    /** Returns a random element from this array, or `null` if this array is empty. **/
    inline fun randomOrNull(): Vector2F? = randomOrNull(Random)

    /**
     * Returns a random element from this array using the specified source of randomness, or `null`
     * if this array is empty.
     */
    inline fun randomOrNull(random: Random): Vector2F? =
        if (isEmpty()) null
        else this[random.nextInt(size)]

    /** Reverses elements in the array in-place. **/
    fun reverse() {
        val midPoint: Int = (size / 2) - 1

        if (midPoint < 0) {
            return
        }
        var reverseIndex = lastIndex

        for (index in 0..midPoint) {
            val tmp: Vector2F = this[index]
            this[index] = this[reverseIndex]
            this[reverseIndex] = tmp
            reverseIndex--
        }
    }

    /**
     * Reverses elements of the array in the specified range in-place.
     *
     * @param fromIndex the start of the range (inclusive) to reverse.
     * @param toIndex the end of the range (exclusive) to reverse.
     * @exception IndexOutOfBoundsException if [fromIndex] is less than zero or [toIndex] is
     * greater than the size of this array.
     * @exception IllegalArgumentException if [fromIndex] is greater than [toIndex].
     */
    fun reverse(fromIndex: Int, toIndex: Int) {
        checkRangeIndexes(fromIndex, toIndex, size)
        val midPoint: Int = (fromIndex + toIndex) / 2

        if (fromIndex == midPoint) {
            return
        }
        var reverseIndex: Int = toIndex - 1

        for (index in fromIndex until midPoint) {
            val tmp: Vector2F = this[index]
            this[index] = this[reverseIndex]
            this[reverseIndex] = tmp
            reverseIndex--
        }
    }

    /** Returns a list with elements in reversed order. **/
    fun reversed(): List<Vector2F> {
        if (isEmpty()) {
            return emptyList()
        }
        val list = toMutableList()
        list.reverse()

        return list
    }

    /** Returns an array with elements of this array in reversed order. **/
    fun reversedArray(): Vector2FArray {
        if (isEmpty()) {
            return this
        }
        val result = Vector2FArray(size)
        val lastIndex: Int = lastIndex

        for (i in 0..lastIndex) {
            result[lastIndex - i] = this[i]
        }
        return result
    }

    /** Randomly shuffles elements in this array in-place. **/
    inline fun shuffle() = shuffle(Random)

    /**
     * Randomly shuffles elements in this array in-place using the specified [random] instance as
     * the source of randomness.
     */
    fun shuffle(random: Random) {
        for (i in lastIndex downTo 1) {
            val j: Int = random.nextInt(i + 1)
            val copy: Vector2F = this[i]
            this[i] = this[j]
            this[j] = copy
        }
    }

    /**
     * Returns the single element.
     *
     * @exception IllegalArgumentException if the array has more than one element.
     * @exception NoSuchElementException if the array is empty.
     */
    fun single() = Vector2F(data.single())

    /**
     * Returns the single element matching the given predicate.
     *
     * @exception IllegalArgumentException if the array contains more than one matching element.
     * @exception NoSuchElementException if the array contains no element matching the predicate.
     */
    inline fun single(predicate: (Vector2F) -> Boolean): Vector2F {
        var single = Vector2F.ZERO
        var found = false

        for (i in 0..lastIndex) {
            val vector = this[i]

            if (predicate(vector)) {
                if (found) {
                    throw IllegalArgumentException(
                        "Array contains more than one matching element."
                    )
                }
                single = vector
                found = true
            }
        }
        if (!found) {
            throw NoSuchElementException("Array contains no element matching the predicate.")
        }
        return single
    }

    /** Returns single element, or `null` if the array is empty or has more than one element. **/
    inline fun singleOrNull(): Vector2F? =
        if (size == 1) this[0]
        else null

    /**
     * Returns the single element matching the given [predicate], or `null` if element was not
     * found or more than one element was found.
     */
    inline fun singleOrNull(predicate: (Vector2F) -> Boolean): Vector2F? {
        var single = Vector2F.ZERO
        var found = false

        for (i in 0..lastIndex) {
            val vector = this[i]

            if (predicate(vector)) {
                if (found) {
                    return null
                }
                single = vector
                found = true
            }
        }
        if (!found) {
            return null
        }
        return single
    }

    /** Returns a list containing elements at indices in the specified [indices] range. **/
    fun slice(indices: IntRange): List<Vector2F> =
        if (indices.isEmpty()) emptyList()
        else copyOfRange(indices.first, indices.last + 1).asList()

    /** Returns a list containing elements at specified [indices]. **/
    fun slice(indices: Iterable<Int>): List<Vector2F> {
        val size: Int = if (indices is Collection<Int>) indices.size else 10

        if (size == 0) {
            return emptyList()
        }
        val list = ArrayList<Vector2F>(size)

        for (index in indices) {
            list.add(this[index])
        }
        return list
    }

    /** Returns an array containing elements of this array at specified [indices]. **/
    fun sliceArray(indices: Collection<Int>): Vector2FArray {
        val result = Vector2FArray(indices.size)
        var targetIndex = 0

        for (sourceIndex in indices) {
            result[targetIndex++] = this[sourceIndex]
        }
        return result
    }

    /** Returns an array containing elements at indices in the specified [indices] range. **/
    fun sliceArray(indices: IntRange): Vector2FArray =
        if (indices.isEmpty()) Vector2FArray(0)
        else copyOfRange(indices.first, indices.last + 1)

    /**
     * Returns a list of all elements sorted according to natural sort order of the value returned
     * by specified [selector] function.
     *
     * The sort is *stable*. It means that equal elements preserve their order relative to each
     * other after sorting.
     */
    inline fun <R : Comparable<R>> sortedBy(
        crossinline selector: (Vector2F) -> R?
    ): List<Vector2F> = sortedWith(compareBy(selector))

    /**
     * Returns a list of all elements sorted descending according to natural sort order of the
     * value returned by specified [selector] function.
     *
     * The sort is *stable*. It means that equal elements preserve their order relative to each
     * other after sorting.
     */
    inline fun <R : Comparable<R>> sortedByDescending(
        crossinline selector: (Vector2F) -> R?
    ): List<Vector2F> = sortedWith(compareByDescending(selector))

    /**
     * Returns a list of all elements sorted according to the specified [comparator].
     *
     * The sort is *stable*. It means that equal elements preserve their order relative to each
     * other after sorting.
     */
    fun sortedWith(comparator: Comparator<in Vector2F>): List<Vector2F> =
        toTypedArray().apply { sortWith(comparator) }.asList()

    /** Returns the sum of all elements in the array. **/
    fun sum(): Vector2F {
        var sum = Vector2F.ZERO

        for (i in 0..lastIndex) {
            val element: Vector2F = this[i]
            sum += element
        }
        return sum
    }

    /**
     * Returns the sum of all values produced by [selector] function applied to each element in the
     * array.
     */
    @OptIn(ExperimentalTypeInference::class)
    @OverloadResolutionByLambdaReturnType
    inline fun sumOf(selector: (Vector2F) -> Double): Double {
        var sum = 0.0

        for (i in 0..lastIndex) {
            val element: Vector2F = this[i]
            sum += selector(element)
        }
        return sum
    }

    /**
     * Returns the sum of all values produced by [selector] function applied to each element in the
     * array.
     */
    @OptIn(ExperimentalTypeInference::class)
    @OverloadResolutionByLambdaReturnType
    inline fun sumOf(selector: (Vector2F) -> Int): Int {
        var sum = 0

        for (i in 0..lastIndex) {
            val element: Vector2F = this[i]
            sum += selector(element)
        }
        return sum
    }

    /**
     * Returns the sum of all values produced by [selector] function applied to each element in the
     * array.
     */
    @OptIn(ExperimentalTypeInference::class)
    @OverloadResolutionByLambdaReturnType
    inline fun sumOf(selector: (Vector2F) -> UInt): UInt {
        var sum = 0u

        for (i in 0..lastIndex) {
            val element: Vector2F = this[i]
            sum += selector(element)
        }
        return sum
    }

    /**
     * Returns the sum of all values produced by [selector] function applied to each element in the
     * array.
     */
    @OptIn(ExperimentalTypeInference::class)
    @OverloadResolutionByLambdaReturnType
    inline fun sumOf(selector: (Vector2F) -> Long): Long {
        var sum = 0L

        for (i in 0..lastIndex) {
            val element: Vector2F = this[i]
            sum += selector(element)
        }
        return sum
    }

    /**
     * Returns the sum of all values produced by [selector] function applied to each element in the
     * array.
     */
    @OptIn(ExperimentalTypeInference::class)
    @OverloadResolutionByLambdaReturnType
    inline fun sumOf(selector: (Vector2F) -> ULong): ULong {
        var sum = 0uL

        for (i in 0..lastIndex) {
            val element: Vector2F = this[i]
            sum += selector(element)
        }
        return sum
    }

    /**
     * Returns the sum of all values produced by [selector] function applied to each element in the
     * array.
     */
    @OptIn(ExperimentalTypeInference::class)
    @OverloadResolutionByLambdaReturnType
    inline fun sumOf(selector: (Vector2F) -> Vector2F): Vector2F {
        var sum: Vector2F = Vector2F.ZERO

        for (i in 0..lastIndex) {
            val element: Vector2F = this[i]
            sum += selector(element)
        }
        return sum
    }

    private inline fun takeInternal(n: Int): List<Vector2F> = when {
        n == 0 -> emptyList()
        n >= size -> toList()
        n == 1 -> listOf(this[0])
        else -> {
            var count = 0
            val list = ArrayList<Vector2F>(n)

            for (item in this) {
                list.add(item)

                if (++count == n) {
                    break
                }
            }
            list
        }
    }

    /**
     * Returns a list containing first [n] elements.
     *
     * @exception IllegalArgumentException if [n] is negative.
     */
    fun take(n: Int): List<Vector2F> {
        require(n >= 0) { "Requested element count $n is less than zero." }
        return takeInternal(n)
    }

    private inline fun takeLastInternal(n: Int): List<Vector2F> {
        if (n == 0) {
            return emptyList()
        }
        val size: Int = size

        return when {
            n >= size -> toList()
            n == 1 -> listOf(this[size - 1])
            else -> {
                val list = ArrayList<Vector2F>(n)

                for (index in size - n until size) {
                    list.add(this[index])
                }
                list
            }
        }
    }

    /**
     * Returns a list containing last [n] elements.
     *
     * @exception IllegalArgumentException if [n] is negative.
     */
    fun takeLast(n: Int): List<Vector2F> {
        require(n >= 0) { "Requested element count $n is less than zero." }
        return takeLastInternal(n)
    }

    /** Returns a list containing last elements satisfying the given [predicate]. **/
    inline fun takeLastWhile(predicate: (Vector2F) -> Boolean): List<Vector2F> {
        for (index in lastIndex downTo 0) {
            if (!predicate(this[index])) {
                return drop(index + 1)
            }
        }
        return toList()
    }

    /** Returns a list containing first elements satisfying the given [predicate]. **/
    inline fun takeWhile(predicate: (Vector2F) -> Boolean): List<Vector2F> {
        val list = ArrayList<Vector2F>()

        for (i in 0..lastIndex) {
            val item: Vector2F = this[i]

            if (!predicate(item)) {
                break
            }
            list.add(item)
        }
        return list
    }

    /** Appends all elements to the given [destination] collection. **/
    fun <C : MutableCollection<in Vector2F>> toCollection(destination: C): C {
        for (i in 0..lastIndex) {
            destination.add(this[i])
        }
        return destination
    }

    /** Returns a *typed* object array containing all the elements of this primitive array. **/
    fun toTypedArray(): Array<Vector2F> {
        val result = arrayOfNulls<Vector2F>(size)

        for (index in 0..lastIndex) {
            result[index] = this[index]
        }
        @Suppress("UNCHECKED_CAST")
        return result as Array<Vector2F>
    }

    /** Returns `true` if [element] is found in the array. **/
    override operator fun contains(element: Vector2F): Boolean = data.contains(element.data)

    /**
     * Returns 1st *element* from the array.
     *
     * If the size of this array is less than 1, throws an [IndexOutOfBoundsException] except in
     * Kotlin/JS where the behavior is unspecified.
     */
    inline operator fun component1(): Vector2F = this[0]

    /**
     * Returns 2nd *element* from the array.
     *
     * If the size of this array is less than 2, throws an [IndexOutOfBoundsException] except in
     * Kotlin/JS where the behavior is unspecified.
     */
    inline operator fun component2(): Vector2F = this[1]

    /**
     * Returns 3rd *element* from the array.
     *
     * If the size of this array is less than 3, throws an [IndexOutOfBoundsException] except in
     * Kotlin/JS where the behavior is unspecified.
     */
    inline operator fun component3(): Vector2F = this[2]

    /**
     * Returns 4th *element* from the array.
     *
     * If the size of this array is less than 4, throws an [IndexOutOfBoundsException] except in
     * Kotlin/JS where the behavior is unspecified.
     */
    inline operator fun component4(): Vector2F = this[3]

    /**
     * Returns 5th *element* from the array.
     *
     * If the size of this array is less than 5, throws an [IndexOutOfBoundsException] except in
     * Kotlin/JS where the behavior is unspecified.
     */
    inline operator fun component5(): Vector2F = this[4]

    /**
     * Returns the array element at the given [index]. This method can be called using the index
     * operator.
     *
     * If the [index] is out of bounds of this array, throws an [IndexOutOfBoundsException] except
     * in Kotlin/JS where the behavior is unspecified.
     */
    operator fun get(index: Int) = Vector2F(data[index])

    /** Creates an iterator over the elements of the array. **/
    override operator fun iterator(): Vector2FIterator = Vector2FIteratorImpl(this)

    /**
     * Returns an array containing all elements of the original array and then all elements of the
     * given [elements] collection.
     */
    operator fun plus(elements: Collection<Vector2F>): Vector2FArray {
        var index: Int = size
        val result: LongArray = data.copyOf(index + elements.size)

        for (element in elements) {
            result[index++] = element.data
        }
        return Vector2FArray(result)
    }

    /**
     * Returns an array containing all elements of the original array and then the given [element].
     */
    operator fun plus(element: Vector2F) = Vector2FArray(data + element.data)

    /**
     * Returns an array containing all elements of the original array and then all elements of the
     * given [elements] array.
     */
    operator fun plus(elements: Vector2FArray) = Vector2FArray(data + elements.data)

    /**
     * Sets the element at the given [index] to the given [value]. This method can be called using
     * the index operator.
     *
     * If the [index] is out of bounds of this array, throws an [IndexOutOfBoundsException] except
     * in Kotlin/JS where the behavior is unspecified.
     */
    operator fun set(index: Int, value: Vector2F) {
        data[index] = value.data
    }

    companion object {
        @JvmStatic
        private fun create(size: Int, init: (Int) -> Vector2F): LongArray {
            val data = LongArray(size)

            for (i in 0..size - 1) {
                data[i] = init(i).data
            }
            return data
        }

        @JvmStatic
        private fun checkRangeIndexes(fromIndex: Int, toIndex: Int, size: Int) {
            if (fromIndex < 0 || toIndex > size) {
                throw IndexOutOfBoundsException(
                    "fromIndex: $fromIndex, toIndex: $toIndex, size: $size"
                )
            }
            if (fromIndex > toIndex) {
                throw IllegalArgumentException("fromIndex: $fromIndex > toIndex: $toIndex")
            }
        }
    }
}

private class Vector2FIteratorImpl(private val array: Vector2FArray) : Vector2FIterator() {
    private var index: Int = 0

    override fun hasNext(): Boolean = index < array.size

    override fun nextVector2F(): Vector2F =
        if (!hasNext()) throw NoSuchElementException("$index")
        else array[index++]
}