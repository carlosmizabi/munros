typealias Range = ClosedFloatingPointRange<Double>

data class MunroCollection(val munros: List<Munro>) {

    fun filter(type: Munro.Type? = null, heightRange: Range? = null, sorting: Sorting? = null): List<Munro> {
        return munros
            .filter { it.filter(type, heightRange) }
            .let { sorting?.sort?.invoke(it) ?: it }
    }

    private fun Munro.filter(type: Munro.Type? = null, heightRange: Range? = null): Boolean
        = (if (type != null) this.type == type else this.type != Munro.Type.UNKNOWN)
            && heightRange?.contains(heightInMeters ?: 0.0) ?: true
}

sealed class Sorting(val sort: (List<Munro>) -> List<Munro>) {
    object NameAscending : Sorting({ list -> list.sortedBy { it.name } })
    object NameDescending : Sorting({ list -> list.sortedByDescending { it.name } })
    object HeightMetersAscending : Sorting({ list -> list.sortedBy { it.heightInMeters } })
    object HeightMetersDescending : Sorting({ list -> list.sortedByDescending { it.heightInMeters }})
}

