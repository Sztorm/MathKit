package com.sztorm.lowallocmath

/** An iterator over a sequence of values of type [Vector2I] **/
abstract class Vector2IIterator : Iterator<Vector2I> {
    final override fun next() = nextVector2I()

    /** Returns the next element in the iteration without boxing. **/
    abstract fun nextVector2I(): Vector2I
}