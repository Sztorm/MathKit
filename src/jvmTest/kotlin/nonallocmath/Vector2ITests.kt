package nonallocmath

import com.sztorm.nonallocmath.Vector2I
import org.junit.jupiter.params.*
import org.junit.jupiter.params.provider.*
import kotlin.test.*

class Vector2ITests {

    @ParameterizedTest
    @MethodSource("vectors")
    fun vectorContentsAreValid(x: Int, y: Int) {
        val v = Vector2I(x, y)

        assertTrue(v.x == x && v.y == y)
    }

    companion object {
        @JvmStatic
        fun vectors(): List<Arguments> = listOf(
            Arguments.of(2, 4),
            Arguments.of(Int.MAX_VALUE, Int.MIN_VALUE),
            Arguments.of(Int.MIN_VALUE, Int.MAX_VALUE),
        )
    }
}