import org.junit.jupiter.api.Test
import testing.TestMunros
import org.hamcrest.CoreMatchers.`is` as Is
import org.hamcrest.MatcherAssert.assertThat as that

internal class MunroCollectionShould {

    @Test
    fun `give us only the munros with the given type`() {
        val items = makeForEachType(munros = 5, tops = 3, unknown = 2)
        val collection = MunroCollection(items)

        val munros = collection.filter(Munro.Type.MUNRO)
        that(munros.size, Is(5))
        that(munros.all { it.type == Munro.Type.MUNRO }, Is(true))

        val tops = collection.filter(Munro.Type.TOP)
        that(tops.size, Is(3))
        that(tops.all { it.type == Munro.Type.TOP }, Is(true))

        val unknown = collection.filter(Munro.Type.UNKNOWN)
        that(unknown.size, Is(2))
        that(unknown.all { it.type == Munro.Type.UNKNOWN }, Is(true))
    }

    @Test
    fun `gives the munros with the correct type from data set` () {
        val collection = MunroFinder.parse(TestMunros.PARSED_TEST_DATA)
        val munros = collection.filter(Munro.Type.MUNRO)
        that(munros.size, Is(282))
        that("all munros", munros.all { it.type == Munro.Type.MUNRO }, Is(true))

        val tops = collection.filter(Munro.Type.TOP)
        that(tops.size, Is(227))
        that("all tops", tops.all { it.type == Munro.Type.TOP }, Is(true))

        val allKnown = collection.filter()
        that(allKnown.size, Is(509))
        that("no unknowns", allKnown.all { it.type == Munro.Type.TOP || it.type == Munro.Type.MUNRO }, Is(true))

        val unknown = collection.filter(Munro.Type.UNKNOWN)
        that(unknown.size, Is(93))
        that("only unknowns", unknown.all { it.type == Munro.Type.UNKNOWN }, Is(true))
    }

    private fun makeForEachType(munros: Int = 0, tops: Int = 0, unknown: Int = 0)
            = makeMunros(Munro.Type.MUNRO, munros) +
            makeMunros(Munro.Type.TOP, tops) +
            makeMunros(Munro.Type.UNKNOWN, unknown)

    private fun makeMunros(type: Munro.Type, quantity: Int): List<Munro> {
        return IntRange(1, quantity).map {
            Munro(
                type = type
            )
        }
    }

}