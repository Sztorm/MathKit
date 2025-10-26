@file:Suppress("NOTHING_TO_INLINE", "OVERRIDE_BY_INLINE")

package com.sztorm.mathkit

import kotlin.experimental.ExperimentalTypeInference
import kotlin.jvm.JvmInline
import kotlin.jvm.JvmStatic
import kotlin.random.Random

/** Returns the sum of all elements in the array. **/
@kotlin.jvm.JvmName("sumOfVector2I")
fun Array<out Vector2I>.sum(): Vector2I {
    var sum = Vector2I.ZERO

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
@kotlin.jvm.JvmName("sumOfVector2I")
inline fun <T> Array<out T>.sumOf(selector: (T) -> Vector2I): Vector2I {
    var sum: Vector2I = Vector2I.ZERO

    for (i in 0..lastIndex) {
        sum += selector(this[i])
    }
    return sum
}

/** Returns an array of [Vector2I] containing all the elements of this generic array. **/
fun Array<out Vector2I>.toVector2IArray(): Vector2IArray =
    Vector2IArray(size) { index -> this[index] }

/** Returns the sum of all elements in the collection. **/
@kotlin.jvm.JvmName("sumOfVector2I")
fun Iterable<Vector2I>.sum(): Vector2I {
    var sum = Vector2I.ZERO

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
@kotlin.jvm.JvmName("sumOfVector2I")
inline fun <T> Iterable<T>.sumOf(selector: (T) -> Vector2I): Vector2I {
    var sum = Vector2I.ZERO

    for (element in this) {
        sum += selector(element)
    }
    return sum
}

/** Returns an empty [Vector2IArray]. **/
inline fun vector2IArrayOf() = Vector2IArray(0)

/** Returns a new [Vector2IArray] containing only the specified [element]. **/
inline fun vector2IArrayOf(element: Vector2I) = Vector2IArray(1).apply { this[0] = element }

/** Returns a new [Vector2IArray] of given elements. **/
@Suppress("FINAL_UPPER_BOUND")
inline fun <reified T : Vector2I> vector2IArrayOf(vararg elements: T): Vector2IArray {
    val size = elements.size
    val result = Vector2IArray(size)

    for (i in 0..<size) {
        result[i] = elements[i]
    }
    return result
}

/**
 * An array of [Vector2I] values.
 *
 * When targeting the JVM, instances of this class are represented as `long[]`.
 */
@JvmInline
value class Vector2IArray private constructor(private val data: LongArray) : Collection<Vector2I> {
    /** Creates a new array of the specified [size], with all elements initialized to zero. **/
    constructor(size: Int) : this(LongArray(size))

    /**
     * Creates a new array of the specified [size], where each element is calculated by calling the
     * specified [init] function.
     *
     * The function [init] is called for each array element sequentially starting from the first
     * one. It should return the value for an array element given its index.
     */
    constructor(size: Int, init: (Int) -> Vector2I) : this(create(size, init))

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
    inline fun all(predicate: (Vector2I) -> Boolean): Boolean {
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
    inline fun any(predicate: (Vector2I) -> Boolean): Boolean {
        for (i in 0..lastIndex) {
            if (predicate(this[i])) {
                return true
            }
        }
        return false
    }

    /**
     * Returns a [Vector2IList] that wraps this array. Changes in the original array are reflected
     * in the returned list.
     */
    fun asList() = Vector2IList(this)

    /** Checks if all elements in the specified collection are contained in this array. **/
    override fun containsAll(elements: Collection<Vector2I>): Boolean {
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
        destination: Vector2IArray,
        destinationOffset: Int = 0,
        startIndex: Int = 0,
        endIndex: Int = size
    ) = Vector2IArray(data.copyInto(destination.data, destinationOffset, startIndex, endIndex))

    /** Returns new array which is a copy of the original array. **/
    fun copyOf() = Vector2IArray(data.copyOf())

    /**
     * Returns new array which is a copy of the original array, resized to the given [newSize]. The
     * copy is either truncated or padded at the end with [Vector2I.ZERO] values if necessary.
     * - If [newSize] is less than the size of the original array, the copy array is truncated to
     * the [newSize].
     * - If [newSize] is greater than the size of the original array, the extra elements in the
     * copy array are filled with [Vector2I.ZERO] values.
     */
    fun copyOf(newSize: Int) = Vector2IArray(data.copyOf(newSize))

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
        Vector2IArray(data.copyOfRange(fromIndex, toIndex))

    /** Returns the number of elements in this array. **/
    inline fun count(): Int = size

    /** Returns the number of elements matching the given [predicate]. **/
    inline fun count(predicate: (Vector2I) -> Boolean): Int {
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
    inline fun distinct(): List<Vector2I> = toMutableSet().toList()

    /**
     * Returns a list containing only elements from the given array having distinct keys returned
     * by the given [selector] function.
     *
     * The elements in the resulting list are in the same order as they were in the source array.
     */
    inline fun <K> distinctBy(selector: (Vector2I) -> K): List<Vector2I> {
        val set = HashSet<K>()
        val list = ArrayList<Vector2I>()

        for (i in 0..lastIndex) {
            val element: Vector2I = this[i]
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
    fun drop(n: Int): List<Vector2I> {
        require(n >= 0) { "Requested element count $n is less than zero." }
        return takeLastInternal((size - n).coerceAtLeast(0))
    }

    /**
     * Returns a list containing all elements except last [n] elements.
     *
     * @exception IllegalArgumentException if [n] is negative.
     */
    fun dropLast(n: Int): List<Vector2I> {
        require(n >= 0) { "Requested element count $n is less than zero." }
        return takeInternal((size - n).coerceAtLeast(0))
    }

    /**
     * Returns a list containing all elements except last elements that satisfy the given
     * [predicate].
     */
    inline fun dropLastWhile(predicate: (Vector2I) -> Boolean): List<Vector2I> {
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
    inline fun dropWhile(predicate: (Vector2I) -> Boolean): List<Vector2I> {
        var yielding = false
        var index = 0

        for (i in 0..lastIndex) {
            val item: Vector2I = this[i]

            if (!predicate(item)) {
                index = i
                yielding = true
                break
            }
        }
        if (!yielding) {
            return emptyList()
        }
        val list = ArrayList<Vector2I>(size - index)

        for (i in index..lastIndex) {
            val item: Vector2I = this[i]

            list.add(item)
        }
        return list
    }

    /**
     * Returns an element at the given [index].
     *
     * @exception IndexOutOfBoundsException if the [index] is out of bounds of this array.
     */
    inline fun elementAt(index: Int): Vector2I = this[index]

    /**
     * Returns an element at the given [index] or the result of calling the [defaultValue] function
     * if the [index] is out of bounds of this array.
     */
    inline fun elementAtOrElse(index: Int, defaultValue: (Int) -> Vector2I): Vector2I =
        getOrElse(index, defaultValue)

    /**
     * Returns an element at the given [index] or `null` if the [index] is out of bounds of this
     * array.
     */
    inline fun elementAtOrNull(index: Int): Vector2I? = getOrNull(index)

    /**
     * Fills this array or its subrange with the specified [element] value.
     *
     * @param fromIndex the start of the range (inclusive) to fill, 0 by default.
     * @param toIndex the end of the range (exclusive) to fill, size of this array by default.
     * @exception IndexOutOfBoundsException if [fromIndex] is less than zero or [toIndex] is
     * greater than the size of this array.
     * @exception IllegalArgumentException if [fromIndex] is greater than [toIndex].
     */
    fun fill(element: Vector2I, fromIndex: Int = 0, toIndex: Int = size) =
        data.fill(element.data, fromIndex, toIndex)

    /**
     * Returns the first element matching the given [predicate], or `null` if no such element was
     * found.
     */
    inline fun find(predicate: (Vector2I) -> Boolean): Vector2I? = firstOrNull(predicate)

    /**
     * Returns the last element matching the given [predicate], or `null` if no such element was
     * found.
     */
    inline fun findLast(predicate: (Vector2I) -> Boolean): Vector2I? = lastOrNull(predicate)

    /**
     * Returns the first element.
     *
     * @exception NoSuchElementException if the array is empty.
     */
    fun first() = Vector2I(data.first())

    /**
     * Returns the first element matching the given predicate.
     *
     * @exception NoSuchElementException if no such element is found.
     */
    inline fun first(predicate: (Vector2I) -> Boolean): Vector2I {
        for (i in 0..lastIndex) {
            val vector = this[i]

            if (predicate(vector)) {
                return vector
            }
        }
        throw NoSuchElementException("Array contains no element matching the predicate.")
    }

    /** Returns the first element, or `null` if the array is empty. **/
    inline fun firstOrNull(): Vector2I? =
        if (isEmpty()) null
        else this[0]

    /**
     * Returns the first element matching the given [predicate], or `null` if element was not
     * found.
     */
    inline fun firstOrNull(predicate: (Vector2I) -> Boolean): Vector2I? {
        for (i in 0..lastIndex) {
            val vector = this[i]

            if (predicate(vector)) {
                return vector
            }
        }
        return null
    }

    /** Performs the given [action] on each element. **/
    inline fun forEach(action: (Vector2I) -> Unit) {
        for (i in 0..lastIndex) {
            val element: Vector2I = this[i]

            action(element)
        }
    }

    /**
     * Performs the given [action] on each element, providing sequential index with the element.
     *
     * @param action function that takes the index of an element and the element itself and
     * performs the action on the element.
     */
    inline fun forEachIndexed(action: (index: Int, Vector2I) -> Unit) {
        for (i in 0..lastIndex) {
            val element: Vector2I = this[i]

            action(i, element)
        }
    }

    /**
     * Returns an element at the given [index] or the result of calling the [defaultValue]
     * function if the [index] is out of bounds of this array.
     */
    @Suppress("ConvertTwoComparisonsToRangeCheck")
    inline fun getOrElse(index: Int, defaultValue: (Int) -> Vector2I): Vector2I =
        if (index >= 0 && index < size) this[index]
        else defaultValue(index)

    /**
     * Returns an element at the given [index] or `null` if the [index] is out of bounds of this
     * array.
     */
    @Suppress("ConvertTwoComparisonsToRangeCheck")
    inline fun getOrNull(index: Int): Vector2I? =
        if (index >= 0 && index < size) this[index]
        else null

    /** Returns first index of [element], or -1 if the array does not contain element. **/
    fun indexOf(element: Vector2I): Int = data.indexOf(element.data)

    /**
     * Returns index of the first element matching the given [predicate], or -1 if the array does
     * not contain such element.
     */
    inline fun indexOfFirst(predicate: (Vector2I) -> Boolean): Int {
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
    inline fun indexOfLast(predicate: (Vector2I) -> Boolean): Int {
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
    fun last() = Vector2I(data.last())

    /**
     * Returns the last element matching the given [predicate].
     *
     * @exception NoSuchElementException if no such element is found.
     */
    inline fun last(predicate: (Vector2I) -> Boolean): Vector2I {
        for (i in lastIndex downTo 0) {
            val vector = this[i]

            if (predicate(vector)) {
                return vector
            }
        }
        throw NoSuchElementException("Array contains no element matching the predicate.")
    }

    /** Returns last index of [element], or -1 if the array does not contain element. **/
    fun lastIndexOf(element: Vector2I): Int = data.lastIndexOf(element.data)

    /** Returns the last element, or `null` if the array is empty. **/
    inline fun lastOrNull(): Vector2I? =
        if (isEmpty()) null
        else this[size - 1]

    /**
     * Returns the last element matching the given predicate, or `null` if no such element was
     * found.
     */
    inline fun lastOrNull(predicate: (Vector2I) -> Boolean): Vector2I? {
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
    inline fun none(predicate: (Vector2I) -> Boolean): Boolean {
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
    inline fun random(): Vector2I = random(Random)

    /**
     * Returns a random element from this array using the specified source of randomness.
     *
     * @exception NoSuchElementException if this array is empty.
     */
    inline fun random(random: Random): Vector2I =
        if (isEmpty()) throw NoSuchElementException("Array is empty.")
        else this[random.nextInt(size)]

    /** Returns a random element from this array, or `null` if this array is empty. **/
    inline fun randomOrNull(): Vector2I? = randomOrNull(Random)

    /**
     * Returns a random element from this array using the specified source of randomness, or `null`
     * if this array is empty.
     */
    inline fun randomOrNull(random: Random): Vector2I? =
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
            val tmp: Vector2I = this[index]
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
            val tmp: Vector2I = this[index]
            this[index] = this[reverseIndex]
            this[reverseIndex] = tmp
            reverseIndex--
        }
    }

    /** Returns a list with elements in reversed order. **/
    fun reversed(): List<Vector2I> {
        if (isEmpty()) {
            return emptyList()
        }
        val list = toMutableList()
        list.reverse()

        return list
    }

    /** Returns an array with elements of this array in reversed order. **/
    fun reversedArray(): Vector2IArray {
        if (isEmpty()) {
            return this
        }
        val result = Vector2IArray(size)
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
            val copy: Vector2I = this[i]
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
    fun single() = Vector2I(data.single())

    /**
     * Returns the single element matching the given predicate.
     *
     * @exception IllegalArgumentException if the array contains more than one matching element.
     * @exception NoSuchElementException if the array contains no element matching the predicate.
     */
    inline fun single(predicate: (Vector2I) -> Boolean): Vector2I {
        var single = Vector2I.ZERO
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
    inline fun singleOrNull(): Vector2I? =
        if (size == 1) this[0]
        else null

    /**
     * Returns the single element matching the given [predicate], or `null` if element was not
     * found or more than one element was found.
     */
    inline fun singleOrNull(predicate: (Vector2I) -> Boolean): Vector2I? {
        var single = Vector2I.ZERO
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
    fun slice(indices: IntRange): List<Vector2I> =
        if (indices.isEmpty()) emptyList()
        else copyOfRange(indices.first, indices.last + 1).asList()

    /** Returns a list containing elements at specified [indices]. **/
    fun slice(indices: Iterable<Int>): List<Vector2I> {
        val size: Int = if (indices is Collection<Int>) indices.size else 10

        if (size == 0) {
            return emptyList()
        }
        val list = ArrayList<Vector2I>(size)

        for (index in indices) {
            list.add(this[index])
        }
        return list
    }

    /** Returns an array containing elements of this array at specified [indices]. **/
    fun sliceArray(indices: Collection<Int>): Vector2IArray {
        val result = Vector2IArray(indices.size)
        var targetIndex = 0

        for (sourceIndex in indices) {
            result[targetIndex++] = this[sourceIndex]
        }
        return result
    }

    /** Returns an array containing elements at indices in the specified [indices] range. **/
    fun sliceArray(indices: IntRange): Vector2IArray =
        if (indices.isEmpty()) Vector2IArray(0)
        else copyOfRange(indices.first, indices.last + 1)

    /**
     * Returns a list of all elements sorted according to natural sort order of the value returned
     * by specified [selector] function.
     *
     * The sort is *stable*. It means that equal elements preserve their order relative to each
     * other after sorting.
     */
    inline fun <R : Comparable<R>> sortedBy(
        crossinline selector: (Vector2I) -> R?
    ): List<Vector2I> = sortedWith(compareBy(selector))

    /**
     * Returns a list of all elements sorted descending according to natural sort order of the
     * value returned by specified [selector] function.
     *
     * The sort is *stable*. It means that equal elements preserve their order relative to each
     * other after sorting.
     */
    inline fun <R : Comparable<R>> sortedByDescending(
        crossinline selector: (Vector2I) -> R?
    ): List<Vector2I> = sortedWith(compareByDescending(selector))

    /**
     * Returns a list of all elements sorted according to the specified [comparator].
     *
     * The sort is *stable*. It means that equal elements preserve their order relative to each
     * other after sorting.
     */
    fun sortedWith(comparator: Comparator<in Vector2I>): List<Vector2I> =
        toTypedArray().apply { sortWith(comparator) }.asList()

    /** Returns the sum of all elements in the array. **/
    fun sum(): Vector2I {
        var sum = Vector2I.ZERO

        for (i in 0..lastIndex) {
            val element: Vector2I = this[i]
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
    inline fun sumOf(selector: (Vector2I) -> Double): Double {
        var sum = 0.0

        for (i in 0..lastIndex) {
            val element: Vector2I = this[i]
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
    inline fun sumOf(selector: (Vector2I) -> Int): Int {
        var sum = 0

        for (i in 0..lastIndex) {
            val element: Vector2I = this[i]
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
    inline fun sumOf(selector: (Vector2I) -> UInt): UInt {
        var sum = 0u

        for (i in 0..lastIndex) {
            val element: Vector2I = this[i]
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
    inline fun sumOf(selector: (Vector2I) -> Long): Long {
        var sum = 0L

        for (i in 0..lastIndex) {
            val element: Vector2I = this[i]
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
    inline fun sumOf(selector: (Vector2I) -> ULong): ULong {
        var sum = 0uL

        for (i in 0..lastIndex) {
            val element: Vector2I = this[i]
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
    inline fun sumOf(selector: (Vector2I) -> Vector2I): Vector2I {
        var sum: Vector2I = Vector2I.ZERO

        for (i in 0..lastIndex) {
            val element: Vector2I = this[i]
            sum += selector(element)
        }
        return sum
    }

    private inline fun takeInternal(n: Int): List<Vector2I> = when {
        n == 0 -> emptyList()
        n >= size -> toList()
        n == 1 -> listOf(this[0])
        else -> {
            var count = 0
            val list = ArrayList<Vector2I>(n)

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
    fun take(n: Int): List<Vector2I> {
        require(n >= 0) { "Requested element count $n is less than zero." }
        return takeInternal(n)
    }

    private inline fun takeLastInternal(n: Int): List<Vector2I> {
        if (n == 0) {
            return emptyList()
        }
        val size: Int = size

        return when {
            n >= size -> toList()
            n == 1 -> listOf(this[size - 1])
            else -> {
                val list = ArrayList<Vector2I>(n)

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
    fun takeLast(n: Int): List<Vector2I> {
        require(n >= 0) { "Requested element count $n is less than zero." }
        return takeLastInternal(n)
    }

    /** Returns a list containing last elements satisfying the given [predicate]. **/
    inline fun takeLastWhile(predicate: (Vector2I) -> Boolean): List<Vector2I> {
        for (index in lastIndex downTo 0) {
            if (!predicate(this[index])) {
                return drop(index + 1)
            }
        }
        return toList()
    }

    /** Returns a list containing first elements satisfying the given [predicate]. **/
    inline fun takeWhile(predicate: (Vector2I) -> Boolean): List<Vector2I> {
        val list = ArrayList<Vector2I>()

        for (i in 0..lastIndex) {
            val item: Vector2I = this[i]

            if (!predicate(item)) {
                break
            }
            list.add(item)
        }
        return list
    }

    /** Appends all elements to the given [destination] collection. **/
    fun <C : MutableCollection<in Vector2I>> toCollection(destination: C): C {
        for (i in 0..lastIndex) {
            destination.add(this[i])
        }
        return destination
    }

    /** Returns a *typed* object array containing all the elements of this primitive array. **/
    fun toTypedArray(): Array<Vector2I> {
        val result = arrayOfNulls<Vector2I>(size)

        for (index in 0..lastIndex) {
            result[index] = this[index]
        }
        @Suppress("UNCHECKED_CAST")
        return result as Array<Vector2I>
    }

    override fun toString(): String = data.joinToString(
        prefix = "[",
        postfix = "]",
        separator = ", "
    ) { Vector2I(it).toString() }

    /** Returns `true` if [element] is found in the array. **/
    override operator fun contains(element: Vector2I): Boolean = data.contains(element.data)

    /**
     * Returns 1st *element* from the array.
     *
     * If the size of this array is less than 1, throws an [IndexOutOfBoundsException] except in
     * Kotlin/JS where the behavior is unspecified.
     */
    inline operator fun component1(): Vector2I = this[0]

    /**
     * Returns 2nd *element* from the array.
     *
     * If the size of this array is less than 2, throws an [IndexOutOfBoundsException] except in
     * Kotlin/JS where the behavior is unspecified.
     */
    inline operator fun component2(): Vector2I = this[1]

    /**
     * Returns 3rd *element* from the array.
     *
     * If the size of this array is less than 3, throws an [IndexOutOfBoundsException] except in
     * Kotlin/JS where the behavior is unspecified.
     */
    inline operator fun component3(): Vector2I = this[2]

    /**
     * Returns 4th *element* from the array.
     *
     * If the size of this array is less than 4, throws an [IndexOutOfBoundsException] except in
     * Kotlin/JS where the behavior is unspecified.
     */
    inline operator fun component4(): Vector2I = this[3]

    /**
     * Returns 5th *element* from the array.
     *
     * If the size of this array is less than 5, throws an [IndexOutOfBoundsException] except in
     * Kotlin/JS where the behavior is unspecified.
     */
    inline operator fun component5(): Vector2I = this[4]

    /**
     * Returns the array element at the given [index]. This method can be called using the index
     * operator.
     *
     * If the [index] is out of bounds of this array, throws an [IndexOutOfBoundsException] except
     * in Kotlin/JS where the behavior is unspecified.
     */
    operator fun get(index: Int) = Vector2I(data[index])

    /** Creates an iterator over the elements of the array. **/
    override operator fun iterator(): Vector2IIterator = IteratorImpl(this)

    /**
     * Returns an array containing all elements of the original array and then all elements of the
     * given [elements] collection.
     */
    operator fun plus(elements: Collection<Vector2I>): Vector2IArray {
        var index: Int = size
        val result: LongArray = data.copyOf(index + elements.size)

        for (element in elements) {
            result[index++] = element.data
        }
        return Vector2IArray(result)
    }

    /**
     * Returns an array containing all elements of the original array and then the given [element].
     */
    operator fun plus(element: Vector2I) = Vector2IArray(data + element.data)

    /**
     * Returns an array containing all elements of the original array and then all elements of the
     * given [elements] array.
     */
    operator fun plus(elements: Vector2IArray) = Vector2IArray(data + elements.data)

    /**
     * Sets the element at the given [index] to the given [value]. This method can be called using
     * the index operator.
     *
     * If the [index] is out of bounds of this array, throws an [IndexOutOfBoundsException] except
     * in Kotlin/JS where the behavior is unspecified.
     */
    operator fun set(index: Int, value: Vector2I) {
        data[index] = value.data
    }

    companion object {
        @JvmStatic
        private fun create(size: Int, init: (Int) -> Vector2I): LongArray {
            val data = LongArray(size)

            for (i in 0..<size) {
                data[i] = init(i).data
            }
            return data
        }
    }

    private class IteratorImpl(private val array: Vector2IArray) : Vector2IIterator() {
        private var index: Int = 0

        override fun hasNext(): Boolean = index < array.size

        override fun nextVector2I(): Vector2I =
            if (!hasNext()) throw NoSuchElementException("$index")
            else array[index++]
    }
}