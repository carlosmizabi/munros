import org.junit.jupiter.api.Test
import testing.TestMunros
import org.hamcrest.CoreMatchers.`is` as Is
import org.hamcrest.MatcherAssert.assertThat as that

internal class MunroCollectionShould {

    companion object {
        const val TOTAL_MUNROS_FROM_DATA = 282
        const val TOTAL_TOPS_FROM_DATA = 227
        const val TOTAL_UNKNOWNS_FROM_DATA = 93
    }

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
        that(munros.size, Is(TOTAL_MUNROS_FROM_DATA))
        that("all munros", munros.all { it.type == Munro.Type.MUNRO }, Is(true))

        val tops = collection.filter(Munro.Type.TOP)
        that(tops.size, Is(TOTAL_TOPS_FROM_DATA))
        that("all tops", tops.all { it.type == Munro.Type.TOP }, Is(true))

        val allKnown = collection.filter()
        that(allKnown.size, Is(509))
        that("no unknowns", allKnown.all { it.type == Munro.Type.TOP || it.type == Munro.Type.MUNRO }, Is(true))

        val unknown = collection.filter(Munro.Type.UNKNOWN)
        that(unknown.size, Is(TOTAL_UNKNOWNS_FROM_DATA))
        that("only unknowns", unknown.all { it.type == Munro.Type.UNKNOWN }, Is(true))
    }

    @Test
    fun `give us Munros within the required range` () {
        val range = 900.00.rangeTo(950.00)
        val collection = MunroFinder.parse(TestMunros.PARSED_TEST_DATA)
        val query = Query(heightRange = range)
        val munros = collection.filter(Munro.Type.MUNRO, query)

        that(munros.size, Is(68))
        that(munros.all { range.contains(it.heightInMeters ?: 0.0)}, Is(true))

        val underQuery = Query(heightRange =0.0.rangeTo(899.99))
        val overQuery = Query(heightRange = 950.01.rangeTo(5000.00))
        val underRange = collection.filter(Munro.Type.MUNRO, underQuery)
        val overRange = collection.filter(Munro.Type.MUNRO, overQuery)
        val total = munros.size + underRange.size + overRange.size
        that(total, Is(TOTAL_MUNROS_FROM_DATA))
    }

    @Test
    fun `sort munros by name ascending`() {
        val collection = MunroFinder.parse(TestMunros.PARSED_TEST_DATA)
        val query = Query(sorts = listOf(Sort.NameAscending))
        val sorted = collection.filter(query = query)
        val assertFirstIsSmaller = { first: Munro, second: Munro ->
            val info = "${first.name} < ${second.name}"
            that(info, first.name <= second.name, Is(true))
        }
        for (i in sorted.indices) {
            if ( i + 1 < sorted.size) assertFirstIsSmaller(sorted[i], sorted[i + 1])
        }
    }

    @Test
    fun `sort munros by name descending`() {
        val collection = MunroFinder.parse(TestMunros.PARSED_TEST_DATA)
        val query =  Query(sorts = listOf(Sort.NameDescending))
        val sorted = collection.filter(query = query)
        val assertFirstIsLarger = { first: Munro, second: Munro ->
            val info = "${first.name} > ${second.name}"
            that(info, first.name >= second.name, Is(true))
        }
        for (i in sorted.indices) {
            if ( i + 1 < sorted.size) assertFirstIsLarger(sorted[i], sorted[i + 1])
        }
    }

    @Test
    fun `sort munros by height descending`() {
        val collection = MunroFinder.parse(TestMunros.PARSED_TEST_DATA)
        val query = Query(sorts = listOf(Sort.HeightMetersDescending))
        val sorted = collection.filter(query = query)
        val assertFirstIsLarger = { first: Munro, second: Munro ->
            val info = "${first.heightInMeters} > ${second.heightInMeters}"
            that(info, first.safeHeight >= second.safeHeight, Is(true))
        }
        for (i in sorted.indices) {
            if ( i + 1 < sorted.size) assertFirstIsLarger(sorted[i], sorted[i + 1])
        }
    }

    @Test
    fun `sort munros by height ascending`() {
        val collection = MunroFinder.parse(TestMunros.PARSED_TEST_DATA)
        val query = Query(sorts = listOf(Sort.HeightMetersAscending))
        val sorted = collection.filter(query = query)
        val assertFirstIsLarger = { first: Munro, second: Munro ->
            val info = "${first.heightInMeters} > ${second.heightInMeters}"
            that(info, first.safeHeight <= second.safeHeight, Is(true))
        }
        for (i in sorted.indices) {
            if ( i + 1 < sorted.size) assertFirstIsLarger(sorted[i], sorted[i + 1])
        }
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

    val Munro.safeHeight get() = heightInMeters ?: -1.0

}