import org.hamcrest.CoreMatchers.`is` as Is
import org.hamcrest.MatcherAssert.assertThat as that
import org.junit.jupiter.api.Test

internal class MunroFinderShould {

    @Test
    fun `hello`() {
        that(MunroFinder().hello(), Is("Hello"))
    }
}