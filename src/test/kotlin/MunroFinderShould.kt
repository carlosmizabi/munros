import org.hamcrest.CoreMatchers.`is` as Is
import org.hamcrest.MatcherAssert.assertThat as that
import org.junit.jupiter.api.Test
import testing.TestDataLoader
import testing.TestMunros.assertMatch

internal class MunroFinderShould {

    @Test
    fun `give us a list of all munros converted from a list of string lines`() {
        val collection = MunroFinder.parse(PARSED_TEST_DATA)
        val first = collection.munros.first()
        val last = collection.munros.last()
        that(collection.munros.size, Is(602))
        assertMatch(first, FIRST_MUNRO)
        assertMatch(last, LAST_MUNRO)
    }

    companion object {
        val PARSED_TEST_DATA = TestDataLoader.readFullDateFile()
        val FIRST_MUNRO = Munro(
            name = "Ben Chonzie",
            height = 931.0,
            gridReference = "NN773308",
            type = Munro.Type.MUNRO
        )
        val LAST_MUNRO = Munro(
            name = "Ben More",
            height = 966.0,
            gridReference = "NM525330",
            type = Munro.Type.MUNRO
        )
    }
}