typealias Range = ClosedFloatingPointRange<Double>

data class MunroCollection(val munros: List<Munro>) {

    fun filter(type: Munro.Type? = null, query: Query? = null): List<Munro> {
        return munros
            .filter { it.isSelectable(type, query?.heightRange) }
            .sort(query?.sorts)
    }

    private fun Munro.isSelectable(type: Munro.Type? = null, heightRange: Range? = null): Boolean
        = isTypeMatch(type) && isInRange(heightRange)

    private fun List<Munro>.sort(sorts: List<Sort>?): List<Munro> {
        var sorted = this
        sorts?.forEach { sorting ->
            sorted = sorting.sort(this)
        }
        return sorted
    }
}

data class Query(
    val heightRange: ClosedFloatingPointRange<Double>? = null,
    val sorts: List<Sort> = emptyList()
)

sealed class Sort(val sort: (List<Munro>) -> List<Munro>) {
    object NameAscending : Sort({ list -> list.sortedBy { it.name } })
    object NameDescending : Sort({ list -> list.sortedByDescending { it.name } })
    object HeightMetersAscending : Sort({ list -> list.sortedBy { it.heightInMeters } })
    object HeightMetersDescending : Sort({ list -> list.sortedByDescending { it.heightInMeters }})
}

