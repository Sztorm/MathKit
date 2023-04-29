package com.sztorm.lowallocmath

/** An iterator over a sequence of values of type [Vector2F] **/
abstract class Vector2FIterator : Iterator<Vector2F> {

    /** Returns the next value in the sequence. **/
    final override fun next() = nextVector2F()

    /** Returns the next value in the sequence without boxing. **/
    abstract fun nextVector2F(): Vector2F
}