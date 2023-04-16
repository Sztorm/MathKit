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

fun Array<out Vector2I>.toVector2IArray(): Vector2IArray =
    Vector2IArray(size) { index -> this[index] }

@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
@kotlin.jvm.JvmName("sumOfVector2I")
inline fun <T> Array<out T>.sumOf(selector: (T) -> Vector2I): Vector2I {
    var sum: Vector2I = Vector2I.ZERO

    for (i in 0..lastIndex) {
        val element: T = this[i]
        sum += selector(element)
    }
    return sum
}

@kotlin.jvm.JvmName("sumOfVector2I")
fun Array<out Vector2I>.sum(): Vector2I {
    var sum = Vector2I.ZERO

    for (i in 0..lastIndex) {
        val element: Vector2I = this[i]
        sum += element
    }
    return sum
}

@JvmInline
value class Vector2IArray private constructor(internal val data: LongArray) : Collection<Vector2I> {

    constructor(size: Int) : this(LongArray(size))

    /**
     * Creates a new array of the specified [size], where each element is calculated by calling the
     * specified [init] function.
     *
     * The function [init] is called for each array element sequentially starting from the first
     * one. It should return the value for an array element given its index.
     */
    constructor(size: Int, init: (Int) -> Vector2I) : this(create(size, init))

    inline val indices
        get() = IntRange(0, lastIndex)

    inline val lastIndex: Int
        get() = size - 1

    override val size: Int
        get() = data.size

    override fun contains(element: Vector2I): Boolean = data.contains(element.data)

    override fun containsAll(elements: Collection<Vector2I>): Boolean {
        for (v in elements) {
            if (!contains(v)) {
                return false
            }
        }
        return true
    }

    inline fun elementAt(index: Int): Vector2I = this[index]

    inline fun elementAtOrElse(index: Int, defaultValue: (Int) -> Vector2I): Vector2I =
        getOrElse(index, defaultValue)

    inline fun elementAtOrNull(index: Int): Vector2I? = getOrNull(index)

    inline fun find(predicate: (Vector2I) -> Boolean): Vector2I? = firstOrNull(predicate)

    inline fun findLast(predicate: (Vector2I) -> Boolean): Vector2I? = lastOrNull(predicate)

    fun first() = Vector2I(data.first())

    inline fun first(predicate: (Vector2I) -> Boolean): Vector2I {
        for (i in 0..lastIndex) {
            val vector = this[i]

            if (predicate(vector)) {
                return vector
            }
        }
        throw NoSuchElementException("Array contains no element matching the predicate.")
    }

    inline fun firstOrNull(): Vector2I? =
        if (isEmpty()) null
        else this[0]

    inline fun firstOrNull(predicate: (Vector2I) -> Boolean): Vector2I? {
        for (i in 0..lastIndex) {
            val vector = this[i]

            if (predicate(vector)) {
                return vector
            }
        }
        return null
    }

    inline fun getOrElse(index: Int, defaultValue: (Int) -> Vector2I): Vector2I =
        if (index >= 0 && index <= lastIndex) this[index]
        else defaultValue(index)

    inline fun getOrNull(index: Int): Vector2I? =
        if (index >= 0 && index <= lastIndex) this[index]
        else null

    fun indexOf(element: Vector2I): Int = data.indexOf(element.data)

    inline fun indexOfFirst(predicate: (Vector2I) -> Boolean): Int {
        for (index in 0..lastIndex) {
            if (predicate(this[index])) {
                return index
            }
        }
        return -1
    }

    fun last() = Vector2I(data.last())

    inline fun last(predicate: (Vector2I) -> Boolean): Vector2I {
        for (i in lastIndex downTo 0) {
            val vector = this[i]

            if (predicate(vector)) {
                return vector
            }
        }
        throw NoSuchElementException("Array contains no element matching the predicate.")
    }

    fun lastIndexOf(element: Vector2I): Int = data.lastIndexOf(element.data)

    inline fun lastOrNull(): Vector2I? =
        if (isEmpty()) null
        else this[size - 1]

    inline fun lastOrNull(predicate: (Vector2I) -> Boolean): Vector2I? {
        for (i in lastIndex downTo 0) {
            val vector = this[i]

            if (predicate(vector)) {
                return vector
            }
        }
        return null
    }

    inline fun random(): Vector2I = random(Random)

    inline fun random(random: Random): Vector2I =
        if (isEmpty()) throw NoSuchElementException("Array is empty.")
        else this[random.nextInt(size)]

    inline fun randomOrNull(): Vector2I? = randomOrNull(Random)

    inline fun randomOrNull(random: Random): Vector2I? =
        if (isEmpty()) null
        else this[random.nextInt(size)]

    fun single() = Vector2I(data.single())

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

    inline fun singleOrNull(): Vector2I? =
        if (size == 1) this[0]
        else null

    fun drop(n: Int): List<Vector2I> {
        require(n >= 0) { "Requested element count $n is less than zero." }
        return takeLastInternal((size - n).coerceAtLeast(0))
    }

    fun dropLast(n: Int): List<Vector2I> {
        require(n >= 0) { "Requested element count $n is less than zero." }
        return takeInternal((size - n).coerceAtLeast(0))
    }

    inline fun dropLastWhile(predicate: (Vector2I) -> Boolean): List<Vector2I> {
        for (index in lastIndex downTo 0) {
            if (!predicate(this[index])) {
                return take(index + 1)
            }
        }
        return emptyList()
    }

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

    fun slice(indices: IntRange): List<Vector2I> =
        if (indices.isEmpty()) emptyList()
        else copyOfRange(indices.first, indices.last + 1).asList()

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

    fun sliceArray(indices: Collection<Int>): Vector2IArray {
        val result = Vector2IArray(indices.size)
        var targetIndex = 0

        for (sourceIndex in indices) {
            result[targetIndex++] = this[sourceIndex]
        }
        return result
    }

    fun sliceArray(indices: IntRange): Vector2IArray =
        if (indices.isEmpty()) Vector2IArray(0)
        else copyOfRange(indices.first, indices.last + 1)

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

    fun takeLast(n: Int): List<Vector2I> {
        require(n >= 0) { "Requested element count $n is less than zero." }
        return takeLastInternal(n)
    }

    inline fun takeLastWhile(predicate: (Vector2I) -> Boolean): List<Vector2I> {
        for (index in lastIndex downTo 0) {
            if (!predicate(this[index])) {
                return drop(index + 1)
            }
        }
        return toList()
    }

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

    fun reversed(): List<Vector2I> {
        if (isEmpty()) {
            return emptyList()
        }
        val list = toMutableList()
        list.reverse()

        return list
    }

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

    inline fun shuffle() = shuffle(Random)

    fun shuffle(random: Random) {
        for (i in lastIndex downTo 1) {
            val j: Int = random.nextInt(i + 1)
            val copy: Vector2I = this[i]
            this[i] = this[j]
            this[j] = copy
        }
    }

    inline fun <R : Comparable<R>> sortedBy(
        crossinline selector: (Vector2I) -> R?
    ): List<Vector2I> = sortedWith(compareBy(selector))

    inline fun <R : Comparable<R>> sortedByDescending(
        crossinline selector: (Vector2I) -> R?
    ): List<Vector2I> = sortedWith(compareByDescending(selector))

    fun sortedWith(comparator: Comparator<in Vector2I>): List<Vector2I> =
        toTypedArray().apply { sortWith(comparator) }.asList()

    fun asList(): List<Vector2I> = object : AbstractList<Vector2I>(), RandomAccess {
        override val size: Int get() = this@Vector2IArray.size
        override fun isEmpty(): Boolean = this@Vector2IArray.isEmpty()
        override fun contains(element: Vector2I): Boolean = this@Vector2IArray.contains(element)
        override fun get(index: Int): Vector2I = this@Vector2IArray[index]
        override fun indexOf(element: Vector2I): Int = this@Vector2IArray.indexOf(element)
        override fun lastIndexOf(element: Vector2I): Int = this@Vector2IArray.lastIndexOf(element)
    }

    fun copyInto(
        destination: Vector2IArray,
        destinationOffset: Int = 0,
        startIndex: Int = 0,
        endIndex: Int = size
    ) = Vector2IArray(data.copyInto(destination.data, destinationOffset, startIndex, endIndex))

    fun copyOf() = Vector2IArray(data.copyOf())

    fun copyOf(newSize: Int) = Vector2IArray(data.copyOf(newSize))

    fun copyOfRange(fromIndex: Int, toIndex: Int) =
        Vector2IArray(data.copyOfRange(fromIndex, toIndex))

    fun fill(element: Vector2I, fromIndex: Int = 0, toIndex: Int = size) =
        data.fill(element.data, fromIndex, toIndex)

    override inline fun isEmpty(): Boolean = size == 0

    inline fun isNotEmpty(): Boolean = size != 0

    fun toTypedArray(): Array<Vector2I> {
        val result = arrayOfNulls<Vector2I>(size)

        for (index in 0..lastIndex) {
            result[index] = this[index]
        }
        @Suppress("UNCHECKED_CAST")
        return result as Array<Vector2I>
    }

    inline fun forEach(action: (Vector2I) -> Unit) {
        for (i in 0..lastIndex) {
            val element: Vector2I = this[i]

            action(element)
        }
    }

    inline fun forEachIndexed(action: (index: Int, Vector2I) -> Unit) {
        for (i in 0..lastIndex) {
            val element: Vector2I = this[i]

            action(i, element)
        }
    }

    inline fun none(): Boolean = isEmpty()

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

    fun sum(): Vector2I {
        var sum = Vector2I.ZERO

        for (i in 0..lastIndex) {
            val element: Vector2I = this[i]
            sum += element
        }
        return sum
    }

    override fun iterator(): Vector2IIterator = Vector2IIteratorImpl(this)

    inline operator fun component1(): Vector2I = this[0]

    inline operator fun component2(): Vector2I = this[1]

    inline operator fun component3(): Vector2I = this[2]

    inline operator fun component4(): Vector2I = this[3]

    inline operator fun component5(): Vector2I = this[4]

    operator fun get(index: Int) = Vector2I(data[index])

    operator fun set(index: Int, value: Vector2I) {
        data[index] = value.data
    }

    operator fun plus(element: Vector2I) = Vector2IArray(data + element.data)

    operator fun plus(elements: Collection<Vector2I>): Vector2IArray {
        var index: Int = size
        val result: LongArray = data.copyOf(index + elements.size)

        for (element in elements) {
            result[index++] = element.data
        }
        return Vector2IArray(result)
    }

    operator fun plus(elements: Vector2IArray) = Vector2IArray(data + elements.data)

    companion object {
        @JvmStatic
        private fun create(size: Int, init: (Int) -> Vector2I): LongArray {
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

abstract class Vector2IIterator : Iterator<Vector2I> {
    final override fun next() = nextVector2I()

    /** Returns the next value in the sequence without boxing. **/
    abstract fun nextVector2I(): Vector2I
}

private class Vector2IIteratorImpl(private val array: Vector2IArray) : Vector2IIterator() {
    private var index: Int = 0

    override fun hasNext(): Boolean = index < array.size

    override fun nextVector2I(): Vector2I =
        if (!hasNext()) throw NoSuchElementException("$index")
        else array[index++]
}