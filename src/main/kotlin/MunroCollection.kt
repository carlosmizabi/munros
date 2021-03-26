typealias Range = ClosedFloatingPointRange<Double>

data class MunroCollection(val munros: List<Munro>) {

    fun filter(type: Munro.Type? = null, query: Query? = null): List<Munro> {
        return munros
            .filter { it.isSelectable(type, query?.heightRange) }
            .sort(query?.sorts)
            .sample(query?.sample)
    }

    private fun List<Munro>.sample(sample: Sample?) = when (sample) {
        is Sample.Head -> take(sample.size)
        is Sample.Slice -> slice(sample.range)
        is Sample.Tail -> takeLast(sample.size)
        else -> this
    }

    private fun Munro.isSelectable(type: Munro.Type? = null, heightRange: Range? = null): Boolean
        = isTypeMatch(type) && isInRange(heightRange)

    private fun List<Munro>.sort(sorts: List<Sort>?): List<Munro> {
        var sorted = this
        sorts?.forEach { sorted = it.sort(sorted) }
        return sorted
    }
}

data class Query(
    val sample: Sample = Sample.All,
    val heightRange: ClosedFloatingPointRange<Double>? = null,
    val sorts: List<Sort> = emptyList()
)

sealed class Sort(val sort: (List<Munro>) -> List<Munro>) {
    object NameAscending : Sort({ list -> list.sortedBy { it.name } })
    object NameDescending : Sort({ list -> list.sortedByDescending { it.name } })
    object HeightMetersAscending : Sort({ list -> list.sortedBy { it.height } })
    object HeightMetersDescending : Sort({ list -> list.sortedByDescending { it.height }})
}

sealed class Sample(val size: Int, val range: IntRange) {
    object All : Sample(size = -1, IntRange.EMPTY)
    class Head(size: Int) : Sample(size = size, IntRange.EMPTY)
    class Tail(size: Int) : Sample(size = size, IntRange.EMPTY)
    class Slice(size: Int, range: IntRange) : Sample(size = size, range = range)
}

