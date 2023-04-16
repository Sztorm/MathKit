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

package com.sztorm.nonallocmath

import kotlin.experimental.ExperimentalTypeInference
import kotlin.jvm.JvmInline
import kotlin.jvm.JvmStatic
import kotlin.random.Random

fun Array<out Vector2F>.toVector2FArray(): Vector2FArray =
    Vector2FArray(size) { index -> this[index] }

@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
@kotlin.jvm.JvmName("sumOfVector2F")
inline fun <T> Array<out T>.sumOf(selector: (T) -> Vector2F): Vector2F {
    var sum: Vector2F = Vector2F.ZERO

    for (i in 0..lastIndex) {
        val element: T = this[i]
        sum += selector(element)
    }
    return sum
}

@kotlin.jvm.JvmName("sumOfVector2F")
fun Array<out Vector2F>.sum(): Vector2F {
    var sum = Vector2F.ZERO

    for (i in 0..lastIndex) {
        val element: Vector2F = this[i]
        sum += element
    }
    return sum
}

@JvmInline
value class Vector2FArray private constructor(internal val data: LongArray) : Collection<Vector2F> {

    constructor(size: Int) : this(LongArray(size))

    /**
     * Creates a new array of the specified [size], where each element is calculated by calling the
     * specified [init] function.
     *
     * The function [init] is called for each array element sequentially starting from the first
     * one. It should return the value for an array element given its index.
     */
    constructor(size: Int, init: (Int) -> Vector2F) : this(create(size, init))

    inline val indices
        get() = IntRange(0, lastIndex)

    inline val lastIndex: Int
        get() = size - 1

    override val size: Int
        get() = data.size

    override fun contains(element: Vector2F): Boolean = data.contains(element.data)

    override fun containsAll(elements: Collection<Vector2F>): Boolean {
        for (v in elements) {
            if (!contains(v)) {
                return false
            }
        }
        return true
    }

    inline fun elementAt(index: Int): Vector2F = this[index]

    inline fun elementAtOrElse(index: Int, defaultValue: (Int) -> Vector2F): Vector2F =
        getOrElse(index, defaultValue)

    inline fun elementAtOrNull(index: Int): Vector2F? = getOrNull(index)

    inline fun find(predicate: (Vector2F) -> Boolean): Vector2F? = firstOrNull(predicate)

    inline fun findLast(predicate: (Vector2F) -> Boolean): Vector2F? = lastOrNull(predicate)

    fun first() = Vector2F(data.first())

    inline fun first(predicate: (Vector2F) -> Boolean): Vector2F {
        for (i in 0..lastIndex) {
            val vector = this[i]

            if (predicate(vector)) {
                return vector
            }
        }
        throw NoSuchElementException("Array contains no element matching the predicate.")
    }

    inline fun firstOrNull(): Vector2F? =
        if (isEmpty()) null
        else this[0]

    inline fun firstOrNull(predicate: (Vector2F) -> Boolean): Vector2F? {
        for (i in 0..lastIndex) {
            val vector = this[i]

            if (predicate(vector)) {
                return vector
            }
        }
        return null
    }

    inline fun getOrElse(index: Int, defaultValue: (Int) -> Vector2F): Vector2F =
        if (index >= 0 && index <= lastIndex) this[index]
        else defaultValue(index)

    inline fun getOrNull(index: Int): Vector2F? =
        if (index >= 0 && index <= lastIndex) this[index]
        else null

    fun indexOf(element: Vector2F): Int = data.indexOf(element.data)

    inline fun indexOfFirst(predicate: (Vector2F) -> Boolean): Int {
        for (index in 0..lastIndex) {
            if (predicate(this[index])) {
                return index
            }
        }
        return -1
    }

    fun last() = Vector2F(data.last())

    inline fun last(predicate: (Vector2F) -> Boolean): Vector2F {
        for (i in lastIndex downTo 0) {
            val vector = this[i]

            if (predicate(vector)) {
                return vector
            }
        }
        throw NoSuchElementException("Array contains no element matching the predicate.")
    }

    fun lastIndexOf(element: Vector2F): Int = data.lastIndexOf(element.data)

    inline fun lastOrNull(): Vector2F? =
        if (isEmpty()) null
        else this[size - 1]

    inline fun lastOrNull(predicate: (Vector2F) -> Boolean): Vector2F? {
        for (i in lastIndex downTo 0) {
            val vector = this[i]

            if (predicate(vector)) {
                return vector
            }
        }
        return null
    }

    inline fun random(): Vector2F = random(Random)

    inline fun random(random: Random): Vector2F =
        if (isEmpty()) throw NoSuchElementException("Array is empty.")
        else this[random.nextInt(size)]

    inline fun randomOrNull(): Vector2F? = randomOrNull(Random)

    inline fun randomOrNull(random: Random): Vector2F? =
        if (isEmpty()) null
        else this[random.nextInt(size)]

    fun single() = Vector2F(data.single())

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

    inline fun singleOrNull(): Vector2F? =
        if (size == 1) this[0]
        else null

    fun drop(n: Int): List<Vector2F> {
        require(n >= 0) { "Requested element count $n is less than zero." }
        return takeLastInternal((size - n).coerceAtLeast(0))
    }

    fun dropLast(n: Int): List<Vector2F> {
        require(n >= 0) { "Requested element count $n is less than zero." }
        return takeInternal((size - n).coerceAtLeast(0))
    }

    inline fun dropLastWhile(predicate: (Vector2F) -> Boolean): List<Vector2F> {
        for (index in lastIndex downTo 0) {
            if (!predicate(this[index])) {
                return take(index + 1)
            }
        }
        return emptyList()
    }

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

    fun slice(indices: IntRange): List<Vector2F> =
        if (indices.isEmpty()) emptyList()
        else copyOfRange(indices.first, indices.last + 1).asList()

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

    fun sliceArray(indices: Collection<Int>): Vector2FArray {
        val result = Vector2FArray(indices.size)
        var targetIndex = 0

        for (sourceIndex in indices) {
            result[targetIndex++] = this[sourceIndex]
        }
        return result
    }

    fun sliceArray(indices: IntRange): Vector2FArray =
        if (indices.isEmpty()) Vector2FArray(0)
        else copyOfRange(indices.first, indices.last + 1)

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

    fun takeLast(n: Int): List<Vector2F> {
        require(n >= 0) { "Requested element count $n is less than zero." }
        return takeLastInternal(n)
    }

    inline fun takeLastWhile(predicate: (Vector2F) -> Boolean): List<Vector2F> {
        for (index in lastIndex downTo 0) {
            if (!predicate(this[index])) {
                return drop(index + 1)
            }
        }
        return toList()
    }

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

    fun reversed(): List<Vector2F> {
        if (isEmpty()) {
            return emptyList()
        }
        val list = toMutableList()
        list.reverse()

        return list
    }

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

    inline fun shuffle() = shuffle(Random)

    fun shuffle(random: Random) {
        for (i in lastIndex downTo 1) {
            val j: Int = random.nextInt(i + 1)
            val copy: Vector2F = this[i]
            this[i] = this[j]
            this[j] = copy
        }
    }

    inline fun <R : Comparable<R>> sortedBy(
        crossinline selector: (Vector2F) -> R?
    ): List<Vector2F> = sortedWith(compareBy(selector))

    inline fun <R : Comparable<R>> sortedByDescending(
        crossinline selector: (Vector2F) -> R?
    ): List<Vector2F> = sortedWith(compareByDescending(selector))

    fun sortedWith(comparator: Comparator<in Vector2F>): List<Vector2F> =
        toTypedArray().apply { sortWith(comparator) }.asList()

    fun asList(): List<Vector2F> = object : AbstractList<Vector2F>(), RandomAccess {
        override val size: Int get() = this@Vector2FArray.size
        override fun isEmpty(): Boolean = this@Vector2FArray.isEmpty()
        override fun contains(element: Vector2F): Boolean = this@Vector2FArray.contains(element)
        override fun get(index: Int): Vector2F = this@Vector2FArray[index]
        override fun indexOf(element: Vector2F): Int = this@Vector2FArray.indexOf(element)
        override fun lastIndexOf(element: Vector2F): Int = this@Vector2FArray.lastIndexOf(element)
    }

    fun copyInto(
        destination: Vector2FArray,
        destinationOffset: Int = 0,
        startIndex: Int = 0,
        endIndex: Int = size
    ) = Vector2FArray(data.copyInto(destination.data, destinationOffset, startIndex, endIndex))

    fun copyOf() = Vector2FArray(data.copyOf())

    fun copyOf(newSize: Int) = Vector2FArray(data.copyOf(newSize))

    fun copyOfRange(fromIndex: Int, toIndex: Int) =
        Vector2FArray(data.copyOfRange(fromIndex, toIndex))

    fun fill(element: Vector2F, fromIndex: Int = 0, toIndex: Int = size) =
        data.fill(element.data, fromIndex, toIndex)

    override inline fun isEmpty(): Boolean = size == 0

    inline fun isNotEmpty(): Boolean = size != 0

    fun toTypedArray(): Array<Vector2F> {
        val result = arrayOfNulls<Vector2F>(size)

        for (index in 0..lastIndex) {
            result[index] = this[index]
        }
        @Suppress("UNCHECKED_CAST")
        return result as Array<Vector2F>
    }

    inline fun forEach(action: (Vector2F) -> Unit) {
        for (i in 0..lastIndex) {
            val element: Vector2F = this[i]

            action(element)
        }
    }

    inline fun forEachIndexed(action: (index: Int, Vector2F) -> Unit) {
        for (i in 0..lastIndex) {
            val element: Vector2F = this[i]

            action(i, element)
        }
    }

    inline fun none(): Boolean = isEmpty()

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

    fun sum(): Vector2F {
        var sum = Vector2F.ZERO

        for (i in 0..lastIndex) {
            val element: Vector2F = this[i]
            sum += element
        }
        return sum
    }

    override fun iterator(): Vector2FIterator = Vector2FIteratorImpl(this)

    inline operator fun component1(): Vector2F = this[0]

    inline operator fun component2(): Vector2F = this[1]

    inline operator fun component3(): Vector2F = this[2]

    inline operator fun component4(): Vector2F = this[3]

    inline operator fun component5(): Vector2F = this[4]

    operator fun get(index: Int) = Vector2F(data[index])

    operator fun set(index: Int, value: Vector2F) {
        data[index] = value.data
    }

    operator fun plus(element: Vector2F) = Vector2FArray(data + element.data)

    operator fun plus(elements: Collection<Vector2F>): Vector2FArray {
        var index: Int = size
        val result: LongArray = data.copyOf(index + elements.size)

        for (element in elements) {
            result[index++] = element.data
        }
        return Vector2FArray(result)
    }

    operator fun plus(elements: Vector2FArray) = Vector2FArray(data + elements.data)

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

abstract class Vector2FIterator : Iterator<Vector2F> {
    final override fun next() = nextVector2F()

    /** Returns the next value in the sequence without boxing. **/
    abstract fun nextVector2F(): Vector2F
}

private class Vector2FIteratorImpl(private val array: Vector2FArray) : Vector2FIterator() {
    private var index: Int = 0

    override fun hasNext(): Boolean = index < array.size

    override fun nextVector2F(): Vector2F =
        if (!hasNext()) throw NoSuchElementException("$index")
        else array[index++]
}