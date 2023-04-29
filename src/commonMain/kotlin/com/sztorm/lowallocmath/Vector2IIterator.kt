package com.sztorm.lowallocmath

/** An iterator over a sequence of values of type [Vector2I] **/
abstract class Vector2IIterator : Iterator<Vector2I> {

    /** Returns the next value in the sequence. **/
    final override fun next() = nextVector2I()

    /** Returns the next value in the sequence without boxing. **/
    abstract fun nextVector2I(): Vector2I
}