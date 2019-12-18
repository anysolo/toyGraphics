import com.anysolo.toyGraphics.vector.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test


class PointAndVectorTests {
    @Test
    fun `p + V`() {
        Assertions.assertEquals(
            Point(150, 125),
            Point(100, 100) + Vector(50, 25)
        )
    }

    @Test
    fun `p - V`() {
        Assertions.assertEquals(
            Point(50, 75),
            Point(100, 100) - Vector(50, 25)
        )
    }
}
