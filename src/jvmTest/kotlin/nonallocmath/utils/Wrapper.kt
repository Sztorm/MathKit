package nonallocmath.utils

import java.io.Serializable

data class Wrapper<out T>(val value: T) : Serializable {
    override fun toString(): String = value.toString()
}