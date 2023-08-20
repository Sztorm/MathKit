package com.sztorm.lowallocmath

/** An iterator over a sequence of values of type [Vector2F] **/
abstract class Vector2FIterator : Iterator<Vector2F> {
    final override fun next() = nextVector2F()

    /** Returns the next element in the iteration without boxing. **/
    abstract fun nextVector2F(): Vector2F
}